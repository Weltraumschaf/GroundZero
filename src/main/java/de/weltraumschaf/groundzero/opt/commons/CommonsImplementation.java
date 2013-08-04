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

import de.weltraumschaf.commons.ApplicationException;
import de.weltraumschaf.groundzero.ExitCodeImpl;
import de.weltraumschaf.groundzero.opt.OptionsSetup;
import de.weltraumschaf.groundzero.opt.CliOptions;
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
public final class CommonsImplementation extends OptionsSetup {

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
     * Parses the options.
     */
    private static final OptionsParser PARSER = new OptionsParser(CONFIGURATION);

    @Override
    public CliOptions parse(final String[] args) throws ApplicationException {
        Validate.notNull(args);

        if (args.length == 0) {
            return new CommonsOptions();
        }

        try {
            return PARSER.parse(args);
        } catch (ParseException ex) {
            throw new ApplicationException(ExitCodeImpl.BAD_ARGUMENTS, ex.getMessage(), ex);
        }
    }

    @Override
    public String help() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        CONFIGURATION.format(HELP_FORMATTER, new PrintStream(out));
        return out.toString();
    }
}
