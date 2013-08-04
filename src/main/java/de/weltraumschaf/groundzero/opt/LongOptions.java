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

/**
 * Declares the multi character switches.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface LongOptions {

    /**
     * Prefix for all switches.
     */
    String PFX = "--";
    /**
     * Switch for path prefix options.
     */
    String PATH_PREFIX = "path-prefix";
    /**
     * Switch for debug options.
     */
    String DEBUG = "debug";
    /**
     * Switch for help options.
     */
    String HELP = "help";
    /**
     * Switch for version options.
     */
    String VERSION = "version";
    /**
     * Switch for input encoding options.
     */
    String INPUT_ENCODING = "input-encoding";
    /**
     * Switch for output encoding options.
     */
    String OUTPUT_ENCODING = "output-encoding";

}
