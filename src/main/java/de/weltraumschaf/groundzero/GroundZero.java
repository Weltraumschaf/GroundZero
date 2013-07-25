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

import de.weltraumschaf.groundzero.transform.ReportProcessor;
import de.weltraumschaf.commons.ApplicationException;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import java.io.IOException;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.Validate;

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
     * Configured command line options.
     */
    private final CliOptionsConfiguration cliOptionsConfiguration = new CliOptionsConfiguration();
    /**
     * Current command line options.
     *
     * Initialized with default object.
     */
    private CliOptions options = new CliOptions();
    /**
     * Processes the report files.
     */
    private ReportProcessor processor;
    /**
     * Version of the application.
     */
    private Version version;

    /**
     * Dedicated constructor.
     *
     * @param args command line arguments provided by JVM
     * @throws ApplicationException if {@link #processor} can't be instantiated
     */
    public GroundZero(final String[] args) throws ApplicationException {
        super(args);
        processor = new ReportProcessor();
    }

    /**
     * Main entry point of application invoked by JVM.
     *
     * @param args command line arguments provided by JVM
     * @throws ApplicationException if errors occurs while creating report processor
     */
    public static void main(final String[] args) throws ApplicationException {
        main(new GroundZero(args));
    }

    /**
     * Setup and invokes the application.
     *
     * @param app invoked application, must not be {@code}
     * @throws ApplicationException if errors occurs while creating report processor
     */
    static void main(final GroundZero app) throws ApplicationException {
        Validate.notNull(app);
        InvokableAdapter.main(app);
    }

    @Override
    public void execute() throws Exception {
        examineCommandLineOptions();

        if (options.isHelp()) {
            showHelpMessage();
            return;
        }

        if (options.isVersion()) {
            initializeVersionInformation();
            showVersionMessage();
            return;
        }

        processReports();
    }

    /**
     * Find the command line arguments.
     *
     * @throws ApplicationException if command line arguments were not possible to parse
     */
    private void examineCommandLineOptions() throws ApplicationException {
        try {
            final CliOptionsParser parser = new CliOptionsParser(cliOptionsConfiguration);
            options = parser.parse(getArgs());
        } catch (ParseException ex) {
            throw new ApplicationException(ExitCodeImpl.BAD_ARGUMENTS, ex.getMessage(), ex);
        }
    }

    /**
     * Prints help and usage information on STDOUT.
     */
    void showHelpMessage() {
        cliOptionsConfiguration.format(new HelpFormatter(), getIoStreams().getStdout());
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
     * @throws ApplicationException if any I/O or XML parse error occurs
     */
    void processReports() throws ApplicationException {
        if (!options.hasReportFiles()) {
            getIoStreams().println("Nothing to do.");
            return;
        }

        processor.setEncoding(options.getInputEncoding());
        processor.setIo(getIoStreams());

        for (final String reportFile : options.getReportFiles()) {
            processReport(reportFile);
        }

        getIoStreams().println(String.format("All %d reports proccesed.", options.getReportFiles().size()));
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
     * Processes a single report file.
     *
     * Exits the application with exit code != {@link ExitCodeImpl#OK}, if {@link org.xml.sax.SAXException.SAXException}
     * or {@link IOException}
     * was thrown.
     *
     * @param reportFile file name of Checkstyle report
     * @throws ApplicationException if any I/O or XML parse error occurs
     */
    private void processReport(final String reportFile) throws ApplicationException {
        getIoStreams().println(String.format("Process report %s ...", reportFile));
        processor.process(reportFile);
    }

}
