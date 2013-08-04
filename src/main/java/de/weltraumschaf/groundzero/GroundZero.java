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

import de.weltraumschaf.groundzero.opt.CliOptions;
import de.weltraumschaf.groundzero.transform.ReportProcessor;
import de.weltraumschaf.commons.ApplicationException;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.groundzero.opt.OptionsSetup;
import de.weltraumschaf.groundzero.opt.Strategy;
import java.io.IOException;
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
     * Processes the report files.
     */
    private ReportProcessor processor;
    /**
     * Version of the application.
     */
    private Version version;
    /**
     * USed to parse CLI options and generate help message.
     */
    private OptionsSetup optionsSetup;
    /**
     * Holds the parsed CLI options.
     */
    private CliOptions options;

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
        try {
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
        } catch (final Exception ex) {
            if (options.isDebug()) {
                getIoStreams().printStackTrace(ex);
            }

            throw ex;
        }
    }

    /**
     * Find the command line arguments.
     *
     * @throws ApplicationException if command line arguments were not possible to parse
     */
    private void examineCommandLineOptions() throws ApplicationException {
        final String envStrategy = System.getenv("GROUNDZERO_OPTIONS_STRATEGY");

        final Strategy strategy;

        if ("commons".equalsIgnoreCase(envStrategy)) {
            strategy = Strategy.COMMONS;
        } else if ("jcommander".equalsIgnoreCase(envStrategy)) {
            strategy = Strategy.JCOMMANDER;
        } else {
            strategy = Strategy.COMMONS;
            getIoStreams().errorln(
                    String.format("Warning: Unsupported strategy '%s' Fall back to '%s'.",
                    envStrategy, strategy));
        }

        optionsSetup = OptionsSetup.create(strategy);
        options = optionsSetup.parse(getArgs());

        if (options.isDebug()) {
            getIoStreams().println(String.format("Used CLI options:\n%s", options));
        }
    }

    /**
     * Prints help and usage information on STDOUT.
     */
    void showHelpMessage() {
        getIoStreams().print(optionsSetup.help());
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

        processor.setInputEncoding(options.getInputEncoding());
        processor.setOutputEncoding(options.getOutputEncoding());
        processor.setPathPrefix(options.getPathPrefix());
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
     * or {@link IOException} was thrown.
     *
     * @param reportFile file name of Checkstyle report
     * @throws ApplicationException if any I/O or XML parse error occurs
     */
    private void processReport(final String reportFile) throws ApplicationException {
        getIoStreams().println(String.format("Process report %s ...", reportFile));
        processor.process(reportFile);
    }
}
