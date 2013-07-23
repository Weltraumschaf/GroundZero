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

/**
 * Parses the command line arguments.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class CliOptionsParser {

    /**
     * Short option for path prefix.
     */
    public static final String OPT_PATH_PREFIX = "p";

    /**
     * Short option to enable debug output.
     */
    public static final String OPT_DEBUG = "d";

    /**
     * Short option for help message.
     */
    public static final String OPT_HELP = "h";

    /**
     * Short option to show the version.
     */
    public static final String OPT_VERSION = "v";
    /**
     * Long option for path prefix.
     */
    public static final String OPT_PATH_PREFIX_LONG = "path-prefix";

    /**
     * Long option to enable debug output.
     */
    public static final String OPT_DEBUG_LONG = "debug";

    /**
     * Long option for help message.
     */
    public static final String OPT_HELP_LONG = "help";

    /**
     * Long option to show the version.
     */
    public static final String OPT_VERSION_LONG = "version";

    /**
     * The parsed and found options.
     */
    private final CliOptions options;

    /**
     * Initializes the parser with a new options object.
     *
     * @param options Newly created options object.
     */
    public CliOptionsParser(final CliOptions options) {
        this.options = options;
    }

    /**
     * PArses the given command line arguments strings.
     *
     * @param args Array of argument strings.
     * @throws ParseException On parse errors.
     */
    public void parse(final String[] args) throws ParseException {
        final CommandLineParser parser = new PosixParser();
        final CommandLine cmd = parser.parse(options.getParseOptions(), args);
        optPathPrefix(cmd);
        optDebug(cmd);
        optHelp(cmd);
        optVersion(cmd);
        reportFileArguments(cmd);
    }

    /**
     * Determines if path prefix option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd Parsed command line.
     */
    private void optPathPrefix(final CommandLine cmd) {
        if (cmd.hasOption(OPT_PATH_PREFIX)) {
            options.setPathPrefix(cmd.getOptionValue(OPT_PATH_PREFIX));
        } else if (cmd.hasOption(OPT_PATH_PREFIX_LONG)) {
            options.setPathPrefix(cmd.getOptionValue(OPT_PATH_PREFIX_LONG));
        }
    }

    /**
     * Determines if debug option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd Parsed command line.
     */
    private void optDebug(final CommandLine cmd) {
        if (cmd.hasOption(OPT_DEBUG) || cmd.hasOption(OPT_DEBUG_LONG)) {
            options.setDebug(true);
        }
    }

    /**
     * Determines if help option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd Parsed command line.
     */
    private void optHelp(final CommandLine cmd) {
        if (cmd.hasOption(OPT_HELP) || cmd.hasOption(OPT_HELP_LONG)) {
            options.setHelp(true);
        }
    }

    /**
     * Determines if version option is set and prepares the {@link CliOptions "options"} object.
     *
     * @param cmd Parsed command line.
     */
    private void optVersion(final CommandLine cmd) {
        if (cmd.hasOption(OPT_VERSION) || cmd.hasOption(OPT_VERSION_LONG)) {
            options.setVersion(true);
        }
    }

    private void reportFileArguments(final CommandLine cmd) {
        options.setReportFiles(cmd.getArgList());
    }

}
