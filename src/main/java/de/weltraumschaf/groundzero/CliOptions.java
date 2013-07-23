/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.groundzero;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Available command line arguments.
 *
 * TODO Move configuration out as value object.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class CliOptions {

    /**
     * Name of the CLI executable.
     */
    static final String EXECUTABLE = "groundzero";
    /**
     * Usage header.
     *
     * FIXME Better formatting with newlines.
     */
    static final String HEADER = String.format("%n"
            + "A tool to generate line based suppression files for Checkstyle.%n%n"
            + "Parses the Checkstyle report files given as command line argument%n"
            + "and generates suppression XML configuration files from them. The suppression%n"
            + "configurations are saved into files in the current working directory. The file%n"
            + "names are the same as the report filename with the addition of '.suppressions'%n"
            + "before the '.xml' file extension. So the report file 'foobar.xml' will produce%n"
            + "a suppression file named 'foobar.suppressions.xml'.");
    /**
     * Author name and email address.
     */
    private static final String AUTHOR = "Sven Strittmatter <weltraumschaf@googlemail.com>";
    /**
     * URI to issue tracker.
     */
    private static final String ISSUE_TRACKER = "https://github.com/Weltraumschaf/GroundZero/issues";
    /**
     * Usage footer.
     */
    private static final String FOOTER = String.format("%nWritten 2013 by %s%nReport bugs here %s",
            AUTHOR, ISSUE_TRACKER);
    /**
     * Options configuration.
     */
    private final Options options;
    /**
     * Path prefix to stript of suppressed file names.
     */
    private String pathPrefix = "";
    /**
     * Print debug information or not.
     */
    private boolean debug;
    /**
     * Print help message or not.
     *
     * Ignores all other options.
     */
    private boolean help;
    /**
     * Show version or net.
     */
    private boolean version;
    private Collection<String> reportFiles = Collections.emptyList();

    /**
     * Configures the {@link #options}.
     */
    public CliOptions() {
        options = new Options();
        // w/ argument
        options.addOption(OptionBuilder.withDescription(
                "PRefix to strip from checked file paths.")
                .withArgName("path")
                .hasArg()
                .withLongOpt(OptionsParser.OPT_PATH_PREFIX_LONG)
                .create(OptionsParser.OPT_PATH_PREFIX));

        // w/o argument
        options.addOption(OptionsParser.OPT_DEBUG, OptionsParser.OPT_DEBUG_LONG, false, "Enables debug output.");
        options.addOption(OptionsParser.OPT_HELP, OptionsParser.OPT_HELP_LONG, false, "This help.");
        options.addOption(OptionsParser.OPT_VERSION, OptionsParser.OPT_VERSION_LONG, false, "Show version information.");
    }

    /**
     * Get the configured options.
     *
     * @return Commons CLI object.
     */
    public Options getOptions() {
        return options;
    }

    public void setPathPrefix(final String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setReportFiles(final Collection<String> argList) {
        reportFiles = argList;
    }

    public boolean hasReportFiles() {
        return reportFiles.isEmpty() == false;
    }

    public Collection<String> getReportFiles() {
        return reportFiles;
    }

    /**
     * Set print debugging.
     *
     * @param onOrOff True enables, false disables (default).
     */
    public void setDebug(final boolean onOrOff) {
        this.debug = onOrOff;
    }

    /**
     * Whether debug is enabled or not.
     *
     * @return Return true if debug is enabled, unless false.
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Set version output option.
     *
     * @param onOrOff True will shows version, false not.
     */
    public void setVersion(final boolean onOrOff) {
        this.version = onOrOff;
    }

    /**
     * Whether show version information is enabled or not.
     *
     * @return Return true if show version is enabled, unless false.
     */
    public boolean isVersion() {
        return version;
    }

    /**
     * Set show help message.
     *
     * @param onOrOff True enables, false disables (default).
     */
    public void setHelp(final boolean onOrOff) {
        this.help = onOrOff;
    }

    /**
     * Whether show help message is enabled or not.
     *
     * @return Return true if show help message is enabled, unless false.
     */
    public boolean isHelp() {
        return help;
    }

    /**
     * Parse given argument strings.
     *
     * @param args Argument strings.
     * @throws ParseException On parse errors.
     */
    public void parse(final String[] args) throws ParseException {
        final OptionsParser parser = new OptionsParser(this);
        parser.parse(args);
    }

    /**
     * Format the command line options.
     *
     * Useful to show help message.
     *
     * @param formatter Formatter to format with.
     * @param out Stream to print formatted output.
     */
    public void format(final HelpFormatter formatter, final PrintStream out) {
        final PrintWriter writer = new PrintWriter(out);
        formatter.printHelp(
                writer,
                HelpFormatter.DEFAULT_WIDTH,
                EXECUTABLE,
                HEADER,
                options,
                HelpFormatter.DEFAULT_LEFT_PAD,
                HelpFormatter.DEFAULT_DESC_PAD,
                FOOTER,
                true);
        writer.flush();
    }

}
