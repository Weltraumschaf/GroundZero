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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang3.Validate;

/**
 * Parses the command line arguments.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class CliOptionsParser {

    /**
     * The parsed and found options.
     */
    private final CliOptionsConfiguration config;

    /**
     * Initializes the parser with a new options object.
     *
     * @param config Newly created options object.
     */
    public CliOptionsParser(final CliOptionsConfiguration config) {
        this.config = config;
    }

    /**
     * PArses the given command line arguments strings.
     *
     * @param args Array of argument strings.
     * @throws ParseException On parse errors.
     */
    public CliOptions parse(final String[] args) throws ParseException {
        final CommandLineParser parser = new PosixParser();
        final CommandLine cmd = parser.parse(config.getParseOptions(), args);
        final CliOptions options = new CliOptions();
        optPathPrefix(cmd, options);
        optDebug(cmd, options);
        optHelp(cmd, options);
        optVersion(cmd, options);
        reportFileArguments(cmd, options);
        return options;
    }

    /**
     * Determines if path prefix option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd {@code must not be null}
     */
    private void optPathPrefix(final CommandLine cmd, final CliOptions options) {
        Validate.notNull(cmd);

        if (cmd.hasOption(CliOptionsConfiguration.OPT_PATH_PREFIX)) {
            options.setPathPrefix(cmd.getOptionValue(CliOptionsConfiguration.OPT_PATH_PREFIX));
        } else if (cmd.hasOption(CliOptionsConfiguration.OPT_PATH_PREFIX_LONG)) {
            options.setPathPrefix(cmd.getOptionValue(CliOptionsConfiguration.OPT_PATH_PREFIX_LONG));
        }
    }

    /**
     * Determines if debug option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd {@code must not be null}
     */
    private void optDebug(final CommandLine cmd, final CliOptions options) {
        Validate.notNull(cmd);

        if (cmd.hasOption(CliOptionsConfiguration.OPT_DEBUG) || 
            cmd.hasOption(CliOptionsConfiguration.OPT_DEBUG_LONG)) {
            options.setDebug(true);
        }
    }

    /**
     * Determines if help option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd {@code must not be null}
     */
    private void optHelp(final CommandLine cmd, final CliOptions options) {
        Validate.notNull(cmd);
        if (cmd.hasOption(CliOptionsConfiguration.OPT_HELP) || cmd.hasOption(CliOptionsConfiguration.OPT_HELP_LONG)) {
            options.setHelp(true);
        }
    }

    /**
     * Determines if version option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd {@code must not be null}
     */
    private void optVersion(final CommandLine cmd, final CliOptions options) {
        Validate.notNull(cmd);

        if (cmd.hasOption(CliOptionsConfiguration.OPT_VERSION) || 
            cmd.hasOption(CliOptionsConfiguration.OPT_VERSION_LONG)) {
            options.setVersion(true);
        }
    }

    /**
     * Set all left-over non-recognized options and arguments as file names to reports.
     *
     * @param cmd {@code must not be null}
     */
    @SuppressWarnings("unchecked") // Commons CLI uses no generics.
    private void reportFileArguments(final CommandLine cmd, final CliOptions options) {
        Validate.notNull(cmd);
        options.setReportFiles(cmd.getArgList());
    }

}
