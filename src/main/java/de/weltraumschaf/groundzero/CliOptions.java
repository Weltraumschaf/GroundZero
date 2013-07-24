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
import org.apache.commons.lang3.Validate;

/**
 * Available command line arguments.
 *
 * Set up an {@link Options parser configuration} and provides it and also works as 
 * configuration value object.
 *
 * TODO Split options config and options value in two seperate objects.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class CliOptions {

    /**
     * URI to issue tracker.
     */
    static final String ISSUE_TRACKER = "https://github.com/Weltraumschaf/GroundZero/issues";
    /**
     * Name of the CLI executable.
     */
    private static final String EXECUTABLE = "groundzero";
    /**
     * Usage header.
     *
     * FIXME Better formatting with newlines.
     */
    private static final String HEADER = String.format("%n"
            + "A tool to generate line based suppression files for Checkstyle."
            + "Parses the Checkstyle report files given as command line argument"
            + "and generates suppression XML configuration files from them. The suppression"
            + "configurations are saved into files in the current working directory. The file"
            + "names are the same as the report filename with the addition of '.suppressions'"
            + "before the '.xml' file extension. So the report file 'foobar.xml' will produce"
            + "a suppression file named 'foobar.suppressions.xml'.");

    /**
     * Author name and email address.
     */
    private static final String AUTHOR = "Sven Strittmatter <weltraumschaf@googlemail.com>";
    /**
     * Usage footer.
     */
    private static final String FOOTER = String.format("%nWritten 2013 by %s%nReport bugs here %s",
            AUTHOR, ISSUE_TRACKER);
    /**
     * Options configuration.
     */
    private final Options options = new Options();
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
    /**
     * Collect all not recognized arguments as file pats.
     */
    private Collection<String> reportFiles = Collections.emptyList();

    /**
     * Configures the {@link #options parse options}.
     */
    public CliOptions() {
        super();
        // w/ argument
        OptionBuilder.withDescription("Prefix to strip from checked file paths.");
        OptionBuilder.withArgName("path");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt(CliOptionsParser.OPT_PATH_PREFIX_LONG);
        options.addOption(OptionBuilder.create(CliOptionsParser.OPT_PATH_PREFIX));

        // w/o argument
        options.addOption(CliOptionsParser.OPT_DEBUG, CliOptionsParser.OPT_DEBUG_LONG, false, "Enables debug output.");
        options.addOption(CliOptionsParser.OPT_HELP, CliOptionsParser.OPT_HELP_LONG, false, "This help.");
        options.addOption(CliOptionsParser.OPT_VERSION, CliOptionsParser.OPT_VERSION_LONG, false,
                "Show version information.");
    }

    /**
     * Get the configured options.
     *
     * @return never {@code null}.
     */
    public Options getParseOptions() {
        return options;
    }

    /**
     * Set path prefix option.
     *
     * @param pathPrefix must not be {@code null}
     */
    public void setPathPrefix(final String pathPrefix) {
        Validate.notNull(pathPrefix);
        this.pathPrefix = pathPrefix;
    }

    /**
     * Get the path prefix option.
     *
     * @return never {@code null} maybe empty
     */
    public String getPathPrefix() {
        return pathPrefix;
    }

    /**
     * Set report files.
     *
     * @param files must not be {@code null}
     */
    public void setReportFiles(final Collection<String> files) {
        Validate.notNull(files);
        reportFiles = files;
    }

    /**
     * Whether there are report files or not.
     *
     * @return {@code true} if {@link #getReportFiles()} is not empty, else {@code false}
     */
    public boolean hasReportFiles() {
        return !reportFiles.isEmpty();
    }

    /**
     * Get report files.
     *
     * @return never {@code null} maybe empty
     */
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
