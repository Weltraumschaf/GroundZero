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

import com.google.common.collect.Sets;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.groundzero.model.CheckstyleReport;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.xml.sax.SAXException;

/**
 * Main application class.
 *
 * TODO Add option -f|--file-out for writing generated suppressons to file.
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
    private static final String HELP_DESCRIPTION = "A tool to generate line based suppression files for Checkstyle.";
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

        final SuppressionGenerator generator = new SuppressionGenerator();
        for (final CheckstyleReport report : processReports()) {
            if (null != report) { // FIXME must not be null!
                getIoStreams().println(generator.generate(report));
            }
        }
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
                    .errorln(String.format("ERROR: Excpetion thrown while parsing input XMl! %s", ex.getMessage()));
            exit(1);
            return null;
        } catch (IOException ex) {
            getIoStreams()
                    .errorln(String.format("ERROR: Excpetion thrown while reading input file! %s", ex.getMessage()));
            exit(2);
            return null;
        }
    }
}
