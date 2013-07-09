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
import org.xml.sax.SAXException;

/**
 * Main application class.
 *
 * @author Sven Strittmatter <ich@weltraumschaf.de>
 */
public class GroundZero extends InvokableAdapter {

    /**
     * JAR relative path to version property file.
     */
    private static final String VERSION_FILE = "/de/weltraumschaf/groundzero/version.properties";
    /**
     * Usage string.
     */
    private static final String USAGE = "Usage: groundzero [-h|--help] [-v|--version] [file1 .. fileN]";
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

        final SupressionGenerator generator = new SupressionGenerator();
        for (final CheckstyleReport report : processReports()) {
            if (null != report) { // FIXME must not be null!
                getIoStreams().println(generator.generate(report));
            }
        }
    }

    private void examineCommandLineOptions() {
        for (final String option : getArgs()) {
            if ("-h".equals(option) || "--help".equals(option)) {
                showHelp = true;
            } else if ("-v".equals(option) || "--version".equals(option)) {
                showVersion = true;
            } else {
                reportFiles.add(option);
            }
        }
    }

    /**
     * Prints help and usage information on STDOUT.
     */
    void showHelpMessage() {
        getIoStreams().println(USAGE);
    }

    /**
     * Prints version information on STDOUT.
     */
    void showVersionMessage() {
        getIoStreams().println(String.format("Version: %s", version.getVersion()));
    }

    void initializeVersionInformation() throws IOException {
        version = new Version(VERSION_FILE);
        version.load();
    }

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

    public void setProcessor(ReportProcessor processor) {
        this.processor = processor;
    }

    public ReportProcessor getProcessor() {
        return processor;
    }

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
