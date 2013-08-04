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

package de.weltraumschaf.groundzero.opt;

import de.weltraumschaf.commons.ApplicationException;
import static de.weltraumschaf.groundzero.opt.Strategy.COMMONS;
import static de.weltraumschaf.groundzero.opt.Strategy.JCOMMANDER;
import de.weltraumschaf.groundzero.opt.commons.CommonsImplementation;
import de.weltraumschaf.groundzero.opt.jcommander.JCommanderImplementation;
import org.apache.commons.lang3.Validate;

/**
 * Abstract base setup for command line option parsing.
 *
 * Concrete implementation {@link Strategy strategies} must provide functionality.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public abstract class OptionsSetup {

    /**
     * Parse the given command line arguments.
     *
     * @param args must not be {@code null}
     * @return new object initialized with recognized options
     * @throws ApplicationException if parse error occurs
     */
    public abstract CliOptions parse(String[] args) throws ApplicationException;
    /**
     * Creates and returns the help message string.
     *
     * @return constant string
     */
    public abstract String help();

    /**
     * Factory method to create concrete implementation.
     *
     * @param strategy must not be {@code null}
     * @return always new instance
     */
    public static OptionsSetup create(final Strategy strategy) {
        Validate.notNull(strategy, "Strategy must not be null!");

        switch (strategy) {
            case COMMONS:
                return new CommonsImplementation();
            case JCOMMANDER:
                return new JCommanderImplementation();
            default:
                throw new IllegalArgumentException(String.format("Unsupported strategy: '%s'!", strategy));
        }
    }

}