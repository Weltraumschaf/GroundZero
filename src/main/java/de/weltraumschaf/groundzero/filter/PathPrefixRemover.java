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
 * Removes a given prefix from the input string.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class PathPrefixRemover implements Filter<String> {
    /**
     * USed to replace the prefix.
     */
    private static final String REPLACEMENT = "";

    /**
     * Prefix to replace.
     */
    private String prefix = REPLACEMENT;

    /**
     * Use {@link StringFilters} to get an instance.
     */
    PathPrefixRemover() {
        super();
    }

    @Override
    public String process(final String input) {
        if (input.startsWith(prefix)) {
            return input.replaceFirst(prefix, REPLACEMENT);
        }

        return input;
    }

    /**
     * Set the prefix.
     *
     * By default the prefix is an empty string.
     *
     * @param prefix must not be {@code null}
     */
    public void setPrefix(final String prefix) {
        Validate.notNull(prefix);
        this.prefix = prefix;
    }

}
