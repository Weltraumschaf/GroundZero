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

import org.apache.commons.lang3.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum OptionDescriptor {

    /**
     * Path prefix option.
     */
    PATH_PREFIX(ShortOptions.PATH_PREFIX, LongOptions.PATH_PREFIX, OptionDescriptions.PATH_PREFIX),
    /**
     * Debug option.
     */
    DEBUG(ShortOptions.DEBUG, LongOptions.DEBUG, OptionDescriptions.DEBUG),
    /**
     * Help option.
     */
    HELP(ShortOptions.HELP, LongOptions.HELP, OptionDescriptions.HELP),
    /**
     * Version option.
     */
    VERSION(ShortOptions.VERSION, LongOptions.VERSION, OptionDescriptions.VERSION),
    /**
     * File input encoding.
     */
    INPUT_ENCODING(ShortOptions.INPUT_ENCODING, LongOptions.INPUT_ENCODING, OptionDescriptions.INPUT_ENCODING),
    /**
     * File output encoding.
     */
    OUTPUT_ENCODING(ShortOptions.OUTPUT_ENCODING, LongOptions.OUTPUT_ENCODING, OptionDescriptions.OUTPUT_ENCODING);
    /**
     * Short option w/o preceding -.
     */
    private final String shortOption;
    /**
     * Long option w/o preceding --.
     */
    private final String longOption;
    /**
     * Description text.
     */
    private final String description;

    /**
     * Dedicated constructor.
     *
     * @param shortOption must not be {@code null} or empty
     * @param longOption must not be {@code null} or empty
     * @param description must not be {@code null} or empty
     */
    private OptionDescriptor(final String shortOption, final String longOption, final String description) {
        Validate.notEmpty(shortOption, "Parameter shortOption must not be null or empty!");
        Validate.notEmpty(longOption, "Parameter longOption must not be null or empty!");
        Validate.notEmpty(description, "Parameter description must not be null or empty!");
        this.shortOption = shortOption;
        this.longOption = longOption;
        this.description = description;
    }

    /**
     * Get short option.
     *
     * @return never {@code null} or empty
     */
    public String getShortOption() {
        return shortOption;
    }

    /**
     * Get long option.
     *
     * @return never {@code null} or empty
     */
    public String getLongOption() {
        return longOption;
    }

    /**
     * Get description.
     *
     * @return never {@code null} or empty
     */
    public String getDescription() {
        return description;
    }

}
