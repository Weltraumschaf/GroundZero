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

/**
 * Abstract base setup for command line option parsing.
 *
 * Concrete implementation {@link Strategy strategies} must provide functionality.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface OptionsSetup {

    /**
     * Parse the given command line arguments.
     *
     * @param args must not be {@code null}
     * @return new object initialized with recognized options
     * @throws ApplicationException if parse error occurs
     */
    CliOptions parse(String[] args) throws Exception;
    /**
     * Creates and returns the help message string.
     *
     * @return constant string
     */
    String help();

}
