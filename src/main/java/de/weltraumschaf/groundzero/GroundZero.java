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
import de.weltraumschaf.commons.ShutDownHook;
import de.weltraumschaf.commons.Version;
import java.io.IOException;
import java.util.Set;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Sven Strittmatter <ich@weltraumschaf.de>
 */
public class GroundZero extends InvokableAdapter {

    private static final String VERSION_FILE = "/de/weltraumschaf/groundzero/version.properties";
    private static final String USAGE = "Usage: groundzero [-h|--help] [-v|--version] [file1 .. fileN]";
    private final Set<String> reportFiles = Sets.newHashSet();
    private ReportProcessor processor = new ReportProcessor();
    private Version version;
    private boolean showHelp;
    private boolean showVersion;

    public GroundZero(String[] args) {
        super(args);
    }

    public static void main(final String[] args) {
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

        processReports();
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

    private void processReports() {
        if (reportFiles.isEmpty()) {
            getIoStreams().println("Nothing to do.");
            return;
        }

        for (final String reportFile : reportFiles) {
            processReport(reportFile);
        }

        getIoStreams().println(String.format("All reports %d proccesed.", reportFiles.size()));
    }

    public void setProcessor(ReportProcessor processor) {
        this.processor = processor;
    }

    public ReportProcessor getProcessor() {
        return processor;
    }

    private void processReport(final String reportFile) {
        try {
            processor.process(reportFile);
        } catch (SAXException ex) {
            getIoStreams()
                    .errorln(String.format("ERROR: Excpetion thrown while parsing input XMl! %s", ex.getMessage()));
            exit(1);
        } catch (IOException ex) {
            getIoStreams()
                    .errorln(String.format("ERROR: Excpetion thrown while reading input file! %s", ex.getMessage()));
            exit(2);
        }
    }
}
