/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <ich@weltraumschaf.de> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 */
package de.weltraumschaf.groundzero;

import de.weltraumschaf.groundzero.model.CheckstyleSuppressions;
import com.google.common.collect.Sets;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.groundzero.model.CheckstyleReport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.xml.sax.SAXException;

/**
 * Main application class.
 *
 * TODO Print to STDOUT which file is parsed and which one is saved.
 *
 * @author Sven Strittmatter <ich@weltraumschaf.de>
 */
public class GroundZero extends InvokableAdapter {

    /**
     * JAR relative path to version property file.
     */
    private static final String VERSION_FILE = "/de/weltraumschaf/groundzero/version.properties";
    /**
     * System dependent new line.
     */
    private static final String NL = String.format("%n");
    /**
     * Usage string.
     */
    private static final String HELP_USAGE = "Usage: groundzero [-h|--help] [-v|--version] [file1 .. fileN]";
    /**
     * Tool short description.
     */
    private static final String HELP_DESCRIPTION =
            "A tool to generate line based suppression files for Checkstyle." + NL + NL
            + "Parses the Checkstyle report files given as command line argument" + NL
            + "and generates suppression XML configuration files from them. The suppression" + NL
            + "configurations are saved into files in the current working directory. The file" + NL
            + "names are the same as the report filename with the addition of '.suppressions'" + NL
            + "before the '.xml' file extension. So the report file 'foobar.xml' will produce" + NL
            + "a suppression file named 'foobar.suppressions.xml'.";
    /**
     * Options help.
     */
    private static final String HELP_OPTIONS =
            "  -h | --help     Show this help." + NL
            + "  -v | --version  Show version information";
    /**
     * Footer information for help.
     */
    private static final String HELP_FOOTER = "Project site: http://weltraumschaf.github.io/GroundZero/" + NL
            + "Report bugs here: https://github.com/Weltraumschaf/GroundZero/issues";

    /**
     * Holds the set of report files to process from CLI arguments.
     */
    private final Set<String> reportFiles = Sets.newHashSet();
    /**
     * Processes the report files.
     */
    private ReportProcessor processor = new ReportProcessor();
    /**
     * Version of the application.
     */
    private Version version;
    /**
     * Whether to help version message.
     */
    private boolean showHelp;
    /**
     * Whether to show version message.
     */
    private boolean showVersion;

    /**
     * Dedicated constructor.
     *
     * @param args command line arguments provided by JVM
     * @throws SAXException if SAX exception occurs on {@link #processor} instantiation
     */
    public GroundZero(final String[] args) throws SAXException {
        super(args);
    }

    /**
     * Main entry point of application invoked by JVM.
     *
     * @param args command line arguments provided by JVM
     * @throws SAXException if SAX exception occurs on {@link #processor} instantiation
     */
    public static void main(final String[] args) throws SAXException {
        InvokableAdapter.main(new GroundZero(args));
    }

    @Override
    public void execute() throws Exception {
        initializeVersionInformation();
        examineCommandLineOptions();

        if (showHelp) {
            showHelpMessage();
            return;
        }

        if (showVersion) {
            showVersionMessage();
            return;
        }

        // FIXME Generate and save directly after parsing.
        final Collection<CheckstyleReport> reports = processReports();
        final Collection<CheckstyleSuppressions> suppressions = generateSuppressions(reports);
        saveSuppressionFiles(suppressions);
    }

    /**
     * Find the command line arguments.
     *
     * Command line arguments are:
     * <ul>
     * <li>-h or --help</li>
     * <li>-v or --version</li>
     * <li>everything else is treated as file name argument</li>
     * </ul>
     */
    private void examineCommandLineOptions() {
        for (final String option : getArgs()) {
            switch (option) {
                case "-h":
                case "--help":
                    showHelp = true;
                    break;
                case "-v":
                case "--version":
                    showVersion = true;
                    break;
                default:
                    reportFiles.add(option);
                    break;
            }
        }
    }

    /**
     * Prints help and usage information on STDOUT.
     */
    void showHelpMessage() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(HELP_USAGE).append(NL).append(NL)
                .append(HELP_DESCRIPTION).append(NL).append(NL)
                .append(HELP_OPTIONS).append(NL).append(NL)
                .append(HELP_FOOTER);
        getIoStreams().println(buffer.toString());
    }

    /**
     * Prints version information on STDOUT.
     */
    void showVersionMessage() {
        getIoStreams().println(String.format("Version: %s", version.getVersion()));
    }

    /**
     * Load version information from {@link #VERSION_FILE properties}.
     *
     * @throws IOException if properties can't be load
     */
    void initializeVersionInformation() throws IOException {
        version = new Version(VERSION_FILE);
        version.load();
    }

    /**
     * Process all given report files.
     *
     * @return never {@code null}, maybe empty
     */
    Collection<CheckstyleReport> processReports() {
        if (reportFiles.isEmpty()) {
            getIoStreams().println("Nothing to do.");
            return Collections.<CheckstyleReport>emptySet();
        }

        final Set<CheckstyleReport> reports = Sets.newHashSet();

        for (final String reportFile : reportFiles) {
            reports.add(processReport(reportFile));
        }

        getIoStreams().println(String.format("All reports %d proccesed.", reportFiles.size()));
        return reports;
    }

    /**
     * Injection point for report processor.
     *
     * @param processor must not be {@code null}
     */
    public void setProcessor(final ReportProcessor processor) {
        Validate.notNull(processor);
        this.processor = processor;
    }

    /**
     * Get report processor.
     *
     * @return never {@code null}
     */
    public ReportProcessor getProcessor() {
        return processor;
    }

    /**
     * Processes a single report file.
     *
     * Exits the application with exit code != {@link ExitCodeImpl#OK}, if {@link SAXException} or {@link IOException}
     * was thrown.
     *
     * @param reportFile file name of Checkstyle report
     * @return never {@code null}
     */
    private CheckstyleReport processReport(final String reportFile) {
        try {
            return processor.process(reportFile);
        } catch (SAXException ex) {
            getIoStreams()
                    .errorln(String.format("ERROR: Excpetion thrown while parsing input file '%s'! %s",
                    reportFile,
                    ex.getMessage()));
            exit(ExitCodeImpl.XML_INPUT_PARSE_ERROR);
            return null;
        } catch (IOException ex) {
            getIoStreams()
                    .errorln(String.format("ERROR: Excpetion thrown while reading input file'%s'! %s",
                    reportFile,
                    ex.getMessage()));
            exit(ExitCodeImpl.XML_INPUT_FILE_READ_ERROR);
            return null;
        }
    }

    private Collection<CheckstyleSuppressions> generateSuppressions(final Collection<CheckstyleReport> reports) {
        Collection<CheckstyleSuppressions> suppressions = Sets.newHashSet();

        for (final CheckstyleReport report : reports) {
            suppressions.add(generateSuppression(report));
        }

        return suppressions;
    }
    private static final SuppressionGenerator GENERATOR = new SuppressionGenerator();

    private CheckstyleSuppressions generateSuppression(final CheckstyleReport report) {
        return GENERATOR.generate(report);
    }

    private void saveSuppressionFiles(final Collection<CheckstyleSuppressions> suppressions) {
        for (final CheckstyleSuppressions suppression : suppressions) {
            saveSuppressionFile(suppression);
        }
    }

    private void saveSuppressionFile(final CheckstyleSuppressions suppression) {
        try {
            try (FileOutputStream fos = new FileOutputStream(new File(suppression.getFileName()), false)) {
                try (BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fos))) {
                    br.write(suppression.getXmlContent());
                    br.flush();
                }
                fos.flush();
            }
        } catch (final IOException ex) {
            getIoStreams()
                    .errorln(String.format("ERROR: Excpetion thrown while writing suppresions file '%s'! %s",
                    ex.getMessage(),
                    suppression.getFileName()));
            exit(ExitCodeImpl.XML_OUTOUT_FILE_WRITE_ERROR);
        }
    }

}
