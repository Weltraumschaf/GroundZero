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
package de.weltraumschaf.groundzero.opt.jcommander;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.Validate;

/**
 * Consumes a string or string buffer from {@link com.beust.jcommander.JCommander#usage(java.lang.StringBuilder)} and
 * reformats it.
 *
 * For each new reformatting string the input must be set.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class HelpReformatter {

    /**
     * Used as line break in output strings.
     */
    static final char NL = '\n';
    /**
     * Regular expression to split string by line brakes.
     */
    private static final String NEWLINE_REGEX = "\\r?\\n";
    /**
     * Number of white spaces to precede each option line.
     */
    private static final int SWITCH_PAD = 4;
    /**
     * List of strings to reformat.
     */
    private List<String> input = Collections.emptyList();

    /**
     * Forwards to {@link #setInput(java.lang.String)}.
     *
     * @param input must not be {@code null}
     */
    public void setInput(final StringBuilder input) {
        Validate.notNull(input);
        setInput(input.toString());

    }

    /**
     * Splits input string by line brakes and sets {@link #input}.
     *
     * @param raw must not be {@code null}
     */
    public void setInput(final String raw) {
        Validate.notNull(raw);
        this.input = Lists.newArrayList(raw.split(NEWLINE_REGEX));

    }

    /**
     * Get the usage line of the raw input.
     *
     * @return may be empty, never {@code null}
     */
    public String getUsage() {
        if (input.isEmpty()) {
            return "";
        }

        return input.get(0).trim();
    }

    /**
     * Get the reformatted options of the raw input.
     *
     * @return may be empty, never {@code null}
     */
    public String getOptions() {
        if (input.size() < 2) {
            return "";
        }

        final StringBuilder buffer = new StringBuilder();
        buffer.append(input.get(1).trim()).append(NL);

        final Entries entries = extractEntries(input.subList(2, input.size()));
        final int switchPadLength = entries.maxSwitchesLength() + SWITCH_PAD;

        for (final Entry e : entries.getEntries()) {
            buffer.append("    ").append(rightPad(e.getSwitches(), switchPadLength))
                    .append(e.getDescription()).append(' ')
                    .append(e.getDefaultValue()).append(NL);
        }

        return buffer.toString();
    }

    /**
     * Extracts option entries from list of lines.
     *
     * @param lines must not be {@code null}
     * @return never {@code null}
     */
    private Entries extractEntries(final Collection<String> lines) {
        Validate.notNull(lines);
        final Entries entries = new Entries();
        int i = 0;
        String switches = "";
        String description = "";
        String defaultValue;

        for (final String line : lines) {
            if (0 == i) {
                switches = line.trim();
                ++i;
            } else if (1 == i) {
                description = line.trim();
                ++i;
            } else if (2 == i) {
                defaultValue = line.trim();
                entries.add(new Entry(switches, description, defaultValue));
                switches = "";
                description = "";
                i = 0;
            }
        }

        return entries;
    }

    /**
     * Pads a given string on the right side with white spaces.
     *
     * @param in string to pad
     * @param length length to pad up, must not be negative
     * @return never {@code null} may be empty
     */
    String rightPad(final String in, final int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must not be less than 0!");
        }

        final StringBuilder buffer = null == in ? new StringBuilder() : new StringBuilder(in);

        while (buffer.length() < length) {
            buffer.append(' ');
        }

        return buffer.toString();
    }

    /**
     * Helper entity which holds all values of one option.
     */
    private static class Entry {

        /**
         * The switches string (e.g. "-d, --debug").
         */
        private final String switches;
        /**
         * The options description.
         */
        private final String description;
        /**
         * The options default value.
         */
        private final String defaultValue;

        /**
         * Dedicated constructor.
         *
         * @param switches must not be {@code null}
         * @param description must not be {@code null}
         * @param defaultValue must not be {@code null}
         */
        public Entry(final String switches, final String description, final String defaultValue) {
            Validate.notNull(switches, "Parameter switches must not be null!");
            Validate.notNull(description, "Parameter description must not be null!");
            Validate.notNull(defaultValue, "Parameter defaultValue must not be null!");
            this.switches = switches;
            this.description = description;
            this.defaultValue = defaultValue;
        }

        /**
         * Get switches string.
         *
         * @return never {@code nul}, may be empty
         */
        public String getSwitches() {
            return switches;
        }

        /**
         * Get description string.
         *
         * @return never {@code nul}, may be empty
         */
        public String getDescription() {
            return description;
        }

        /**
         * Get default value string.
         *
         * @return never {@code nul}, may be empty
         */
        public String getDefaultValue() {
            return defaultValue;
        }
    }

    /**
     * Holds a whole collection of entries.
     */
    private static class Entries {

        /**
         * The collected entries.
         */
        private final Collection<Entry> entries = Lists.newArrayList();

        /**
         * Add an entry.
         *
         * @param e must not be {@code null}
         */
        public void add(final Entry e) {
            Validate.notNull(e);
            entries.add(e);
        }

        /**
         * Get collected entries.
         *
         * @return never {@code null}, may be empty
         */
        public Collection<Entry> getEntries() {
            return entries;
        }

        /**
         * Determines the length of the longest {@link Entry#getSwitches() switches} string of all collected entries.
         *
         * @return integer of range [0..n]
         */
        public int maxSwitchesLength() {
            int length = 0;

            for (final Entry e : entries) {
                if (e.getSwitches().length() > length) {
                    length = e.getSwitches().length();
                }
            }

            return length;
        }

    }
}
