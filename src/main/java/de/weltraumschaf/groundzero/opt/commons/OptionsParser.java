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
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang3.Validate;
import static de.weltraumschaf.groundzero.opt.OptionDescriptor.*;

/**
 * Parses the command line arguments.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class OptionsParser {

    /**
     * The parsed and found options.
     */
    private final OptionsConfiguration config;

    /**
     * Initializes the parser with a new options object.
     *
     * @param config Newly created options object.
     */
    public OptionsParser(final OptionsConfiguration config) {
        this.config = config;
    }

    /**
     * Parses the given command line arguments strings.
     *
     * @param args Array of argument strings
     * @return never {@code null}
     * @throws ParseException On parse errors
     */
    public void parse(final String[] args, final CliOptions options) throws ParseException {
        final CommandLineParser parser = new PosixParser();
        final CommandLine cmd = parser.parse(config.getParseOptions(), args);
        optPathPrefix(cmd, options);
        optInputEncoding(cmd, options);
        optOutputEncoding(cmd, options);
        optDebug(cmd, options);
        optHelp(cmd, options);
        optVersion(cmd, options);
        reportFileArguments(cmd, options);
    }

    /**
     * Determines if path prefix option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd must not be {@code null}
     * @param options must not be {@code null}
     */
    private void optPathPrefix(final CommandLine cmd, final CliOptions options) {
        Validate.notNull(cmd);
        Validate.notNull(options);

        if (cmd.hasOption(PATH_PREFIX.getLongOption())) {
            options.setPathPrefix(cmd.getOptionValue(PATH_PREFIX.getLongOption()));
        } else if (cmd.hasOption(PATH_PREFIX.getShortOption())) {
            options.setPathPrefix(cmd.getOptionValue(PATH_PREFIX.getShortOption()));
        }
    }

    /**
     * Determines if debug option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd must not be {@code null}
     * @param options must not be {@code null}
     */
    private void optDebug(final CommandLine cmd, final CliOptions options) {
        Validate.notNull(cmd);
        Validate.notNull(options);

        if (cmd.hasOption(DEBUG.getShortOption()) || cmd.hasOption(DEBUG.getLongOption())) {
            options.setDebug(true);
        }
    }

    /**
     * Determines if help option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd must not be {@code null}
     * @param options must not be {@code null}
     */
    private void optHelp(final CommandLine cmd, final CliOptions options) {
        Validate.notNull(cmd);
        Validate.notNull(options);

        if (cmd.hasOption(HELP.getShortOption()) || cmd.hasOption(HELP.getLongOption())) {
            options.setHelp(true);
        }
    }

    /**
     * Determines if version option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd must not be {@code null}
     * @param options must not be {@code null}
     */
    private void optVersion(final CommandLine cmd, final CliOptions options) {
        Validate.notNull(cmd);
        Validate.notNull(options);

        if (cmd.hasOption(VERSION.getShortOption()) || cmd.hasOption(VERSION.getLongOption())) {
            options.setVersion(true);
        }
    }

    /**
     * Set all left-over non-recognized options and arguments as file names to reports.
     *
     * @param cmd must not be {@code null}
     * @param options must not be {@code null}
     */
    @SuppressWarnings("unchecked") // Commons CLI uses no generics.
    private void reportFileArguments(final CommandLine cmd, final CliOptions options) {
        Validate.notNull(cmd);
        Validate.notNull(options);
        options.setReportFiles(cmd.getArgList());
    }

    /**
     * Determine and set the input encoding of the read report files.
     *
     * @param cmd must not be {@code null}
     * @param options must not be {@code null}
     */
    private void optInputEncoding(final CommandLine cmd, final CliOptions options) {
        Validate.notNull(cmd);
        Validate.notNull(options);

        if (cmd.hasOption(INPUT_ENCODING.getLongOption())) {
            options.setInputEncoding(cmd.getOptionValue(INPUT_ENCODING.getLongOption()));
        } else if (cmd.hasOption(INPUT_ENCODING.getShortOption())) {
            options.setInputEncoding(cmd.getOptionValue(INPUT_ENCODING.getShortOption()));
        }
    }

    /**
     * Determine and set the output encoding of the written suppressions files.
     *
     * @param cmd must not be {@code null}
     * @param options must not be {@code null}
     */
    private void optOutputEncoding(CommandLine cmd, CliOptions options) {
        Validate.notNull(cmd);
        Validate.notNull(options);

        if (cmd.hasOption(OUTPUT_ENCODING.getLongOption())) {
            options.setOutputEncoding(cmd.getOptionValue(OUTPUT_ENCODING.getLongOption()));
        } else if (cmd.hasOption(OUTPUT_ENCODING.getShortOption())) {
            options.setOutputEncoding(cmd.getOptionValue(OUTPUT_ENCODING.getShortOption()));
        }
    }
}
