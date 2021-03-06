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

import de.weltraumschaf.groundzero.opt.OptionsSetup;
import de.weltraumschaf.groundzero.opt.CliOptions;
import de.weltraumschaf.groundzero.opt.CliOptionsImplementation;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.Validate;

/**
 * Implementation based on Apache Commons CLI.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CommonsImplementation implements OptionsSetup {

    /**
     * Holds the options configuration.
     */
    private static final OptionsConfiguration CONFIGURATION = new OptionsConfiguration();
    /**
     * Formats help and usage.
     */
    private static final HelpFormatter HELP_FORMATTER = new HelpFormatter();

    static {
        HELP_FORMATTER.setOptionComparator(new OptionComparator());
    }
    /**
     * Default parser.
     */
    private static final OptionsParser DEFAULT_PARSER = new OptionsParser(CONFIGURATION);
    /**
     * Parses the options.
     */
    private final OptionsParser parser;

    /**
     * Initializes {@link #parser} with {@link #DEFAULT_PARSER}.
     */
    public CommonsImplementation() {
        this(DEFAULT_PARSER);
    }

    /**
     * Dedicated constructor.
     *
     * @param parser must not be {@code null}
     */
    CommonsImplementation(final OptionsParser parser) {
        super();
        Validate.notNull(parser);
        this.parser = parser;
    }

    @Override
    public CliOptions parse(final String[] args) throws BadArgumentsException  {
        Validate.notNull(args);
        final CliOptionsImplementation options = new CliOptionsImplementation();

        if (args.length > 0) {
            try {
                parser.parse(args, options);
            } catch (ParseException ex) {
                throw new BadArgumentsException(ex.getMessage(), ex);
            }
        }

        return options;
    }

    @Override
    public String help() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        CONFIGURATION.format(HELP_FORMATTER, new PrintStream(out));
        return out.toString();
    }
}
