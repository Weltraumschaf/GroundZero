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
package de.weltraumschaf.groundzero.opt.commons;

import de.weltraumschaf.groundzero.opt.CliOptions;
import de.weltraumschaf.groundzero.opt.OptionDescriptor;
import java.io.PrintStream;
import java.io.PrintWriter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 * Configuration for {@link OptionsParser options parser}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class OptionsConfiguration {

    /**
     * Options configuration.
     */
    private final Options options = new Options();

    /**
     * Initializes {@link #options} with all information the options parser needs.
     */
    public OptionsConfiguration() {
        super();
        // w/ argument
        OptionBuilder.withDescription(OptionDescriptor.PATH_PREFIX.getDescription());
        OptionBuilder.withArgName("PATH");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt(OptionDescriptor.PATH_PREFIX.getLongOption());
        options.addOption(OptionBuilder.create(OptionDescriptor.PATH_PREFIX.getShortOption()));
        OptionBuilder.withDescription(OptionDescriptor.INPUT_ENCODING.getDescription());
        OptionBuilder.withArgName("ENCODING");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt(OptionDescriptor.INPUT_ENCODING.getLongOption());
        options.addOption(OptionBuilder.create(OptionDescriptor.INPUT_ENCODING.getShortOption()));
        OptionBuilder.withDescription(OptionDescriptor.OUTPUT_ENCODING.getDescription());
        OptionBuilder.withArgName("ENCODING");
        OptionBuilder.hasArg();
        OptionBuilder.withLongOpt(OptionDescriptor.OUTPUT_ENCODING.getLongOption());
        options.addOption(OptionBuilder.create(OptionDescriptor.OUTPUT_ENCODING.getShortOption()));

        // w/o argument
        options.addOption(
                OptionDescriptor.DEBUG.getShortOption(),
                OptionDescriptor.DEBUG.getLongOption(),
                false,
                OptionDescriptor.DEBUG.getDescription());
        options.addOption(
                OptionDescriptor.HELP.getShortOption(),
                OptionDescriptor.HELP.getLongOption(),
                false,
                OptionDescriptor.HELP.getDescription());
        options.addOption(
                OptionDescriptor.VERSION.getShortOption(),
                OptionDescriptor.VERSION.getLongOption(),
                false,
                OptionDescriptor.VERSION.getDescription());
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
                CliOptions.EXECUTABLE,
                CliOptions.HEADER + CliOptions.DESCRIPTION,
                options,
                HelpFormatter.DEFAULT_LEFT_PAD,
                HelpFormatter.DEFAULT_DESC_PAD,
                CliOptions.FOOTER,
                true);
        writer.flush();
    }

}
