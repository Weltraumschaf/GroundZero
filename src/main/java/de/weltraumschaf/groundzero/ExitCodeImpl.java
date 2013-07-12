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

import de.weltraumschaf.commons.system.ExitCode;

/**
 * Exit codes for {@link GroundZero CLI application}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum ExitCodeImpl implements ExitCode {

    /**
     * No errors.
     */
    OK(0),
    /**
     * Errors while parsing a Checkstyle report occurred.
     */
    XML_INPUT_PARSE_ERROR(1),
    /**
     * Errors while reading a Checkstyle report file occurred.
     */
    XML_INPUT_FILE_READ_ERROR(2),
    XML_OUTOUT_FILE_WRITE_ERROR(3),
    /**
     * Unspecified fatal error occurred.
     */
    FATAL(-1);

    /**
     * Exit code number returned as exit code to JVM.
     */
    private final int code;

    /**
     * Dedicated constructor.
     *
     * @param code exit code number
     */
    ExitCodeImpl(final int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
