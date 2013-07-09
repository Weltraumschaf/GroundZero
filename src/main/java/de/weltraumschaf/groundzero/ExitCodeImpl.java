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
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum ExitCodeImpl implements ExitCode {

    OK(0),
    XML_INPUT_PARSE_ERROR(1),
    XML_INPUT_FILE_READ_ERROR(2),
    FATAL(-1);
    private final int code;

    ExitCodeImpl(final int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
