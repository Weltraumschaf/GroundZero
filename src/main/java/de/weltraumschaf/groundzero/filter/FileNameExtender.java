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
 * Extend a given file name with an extension before the actual file extension.
 *
 * Example:
 * <pre>
 * input     == "foo.xml"
 * extension == ".suppressions"
 *           -> "foo.suppressions.xml"
 * </pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FileNameExtender implements Filter<String> {

    /**
     * Used to be inserted into the string.
     */
    private String extension = "";

    /**
     * Sets the used extension string.
     *
     * B default it is an empty string.
     *
     * @param extension must not be {@code null}
     */
    public void setExtension(final String extension) {
        Validate.notNull(extension);
        this.extension = extension;
    }

    @Override
    public String process(final String input) {
        Validate.notNull(input);
        final StringBuilder buffer = new StringBuilder(input);
        buffer.insert(input.lastIndexOf('.'), extension);
        return buffer.toString();
    }
}
