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
 * Declares all options description texts.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
//CHECKSTYLE:OFF
public interface OptionDescriptions {
//CHECKSTYLE:ON
    /**
     * Description of path prefix option.
     */
    String PATH_PREFIX = "Prefix to strip from checked file paths.";
    /**
     * Description of debug option.
     */
    String DEBUG = "Enables debug output.";
    /**
     * Description of help option.
     */
    String HELP = "This help.";
    /**
     * Description of version option.
     */
    String VERSION = "Show version information.";
    /**
     * Description of input encoding option.
     */
    String INPUT_ENCODING = "Input encoding of the report files.";
    /**
     * Description of output encoding option.
     */
    String OUTPUT_ENCODING = "Output encoding of the suppressions files.";

}
