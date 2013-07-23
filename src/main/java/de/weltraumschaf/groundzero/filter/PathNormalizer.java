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

package de.weltraumschaf.groundzero.filter;

import org.apache.commons.lang3.Validate;

/**
 * Normalizes a given path.
 *
 * Removes unnecessary /./ from path strings.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class PathNormalizer implements Filter<String> {

    /**
     * Single dot dirs to replace.
     */
    private static final String SINGLE_DOT_DIR = "/./";

    /**
     * Use {@link StringFilters} to get an instance.
     */
    PathNormalizer() {
        super();
    }

    @Override
    public String process(final String input) {
        Validate.notNull(input);

        if (input.isEmpty()) {
            return input;
        }

        String output = input;

        while (output.contains(SINGLE_DOT_DIR)) {
            output = output.replace(SINGLE_DOT_DIR, "/");
        }

        return output;
    }

}
