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
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.Validate;

/**
 * Configuration for {@link CliOptionsParser options parser}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class CliOptionsConfiguration {

    /**
     * URI to issue tracker.
     */
    private static final String ISSUE_TRACKER = "https://github.com/Weltraumschaf/GroundZero/issues";
    /**
     * Name of the CLI executable.
     */
    private static final String EXECUTABLE = "groundzero";
    /**
     * Usage header.
     *
     * TODO Better formatting with newlines.
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
     * Initializes {@link #options} with all information the options parser needs.
     */
    public CliOptionsConfiguration() {
        super();
        // w/ argument
        OptionBuilder.withDescription(Option.PATH_PREFIX.getDescription());
        OptionBuilder.withArgName("PATH");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt(Option.PATH_PREFIX.getLongOption());
        options.addOption(OptionBuilder.create(Option.PATH_PREFIX.getShortOption()));
        OptionBuilder.withDescription(Option.INPUT_ENCODING.getDescription());
        OptionBuilder.withArgName("ENCODING");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt(Option.INPUT_ENCODING.getLongOption());
        options.addOption(OptionBuilder.create(Option.INPUT_ENCODING.getShortOption()));

        // w/o argument
        options.addOption(
                Option.DEBUG.getShortOption(),
                Option.DEBUG.getLongOption(),
                false,
                Option.DEBUG.getDescription());
        options.addOption(
                Option.HELP.getShortOption(),
                Option.HELP.getLongOption(),
                false,
                Option.HELP.getDescription());
        options.addOption(
                Option.VERSION.getShortOption(),
                Option.VERSION.getLongOption(),
                false,
                Option.VERSION.getDescription());
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

    /**
     * Enumerates all available CLI options.
     */
    static enum Option {
        /** Path prefix option. */
        PATH_PREFIX("p", "path-prefix", "Prefix to strip from checked file paths."),
        /** Debug option. */
        DEBUG("d", "debug", "Enables debug output."),
        /** Help option. */
        HELP("h", "help", "This help."),
        /** Version option. */
        VERSION("v", "version", "Show version information."),
        /** File input encoding. */
        INPUT_ENCODING("i", "input-encoding", "Input encoding of the report files.");

        /** Short option w/o preceding -. */
        private final String shortOption;
        /** Long option w/o preceding --. */
        private final String longOption;
        /** Description text. */
        private final String description;

        /**
         * Dedicated constructor.
         *
         * @param shortOption must not be {@code null} or empty
         * @param longOption must not be {@code null} or empty
         * @param description must not be {@code null} or empty
         */
        private Option(final String shortOption, final String longOption, final String description) {
            Validate.notEmpty(shortOption, "Parameter shortOption must not be null or empty!");
            Validate.notEmpty(longOption, "Parameter longOption must not be null or empty!");
            Validate.notEmpty(description, "Parameter description must not be null or empty!");
            this.shortOption = shortOption;
            this.longOption = longOption;
            this.description = description;
        }

        /**
         * Get short option.
         *
         * @return never {@code null} or empty
         */
        public String getShortOption() {
            return shortOption;
        }

        /**
         * Get long option.
         *
         * @return never {@code null} or empty
         */
        public String getLongOption() {
            return longOption;
        }

        /**
         * Get description.
         *
         * @return never {@code null} or empty
         */
        public String getDescription() {
            return description;
        }

    }
}
