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
 * Declares the single character switches.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
//CHECKSTYLE:OFF
public interface ShortOptions {
//CHECKSTYLE:ON
    /**
     * Prefix for all switches.
     */
    String PFX = "-";
    /**
     * Switch for path prefix options.
     */
    String PATH_PREFIX = "p";
    /**
     * Switch for debug options.
     */
    String DEBUG = "d";
    /**
     * Switch for help options.
     */
    String HELP = "h";
    /**
     * Switch for version options.
     */
    String VERSION = "v";
    /**
     * Switch for input encoding options.
     */
    String INPUT_ENCODING = "i";
    /**
     * Switch for output encoding options.
     */
    String OUTPUT_ENCODING = "o";

}
