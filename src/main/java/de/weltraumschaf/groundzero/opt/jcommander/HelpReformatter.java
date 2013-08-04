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
public class HelpReformatter {

    private static final String NEWLINE_REGEX = "\\r?\\n";
    private static final char NL = '\n';
    private List<String> input = Collections.emptyList();

    public void setInput(final StringBuilder input) {
        Validate.notNull(input);
        setInput(input.toString());

    }

    public void setInput(final String input) {
        Validate.notNull(input);
        this.input = Lists.newArrayList(input.split(NEWLINE_REGEX));

    }

    public String getUsage() {
        if (input.isEmpty()) {
            return "";
        }

        return input.get(0);
    }

    public String getOptions() {
        if (input.size() < 2) {
            return "";
        }

        final StringBuilder buffer = new StringBuilder();
        buffer.append(input.get(0).trim()).append(NL);
        buffer.append(input.get(1).trim()).append(NL);

        final Entries entries = extractEntries(input.subList(2, input.size()));
        final int switchPadLength = entries.maxSwitchesLength();

        for (final Entry e : entries.getEntries()) {
            buffer.append(rightPad(e.getSwitches(), switchPadLength))
                    .append(e.getDescription()).append(' ')
                    .append(e.getDefaultValue()).append(NL);
        }

        return buffer.toString();
    }

    private Entries extractEntries(final Collection<String> input) {
        final Entries entries = new Entries();
        int i = 0;
        String switches = "";
        String description = "";
        String defaultValue = "";

        for (final String line : input) {
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
                defaultValue = "";
                i = 0;
            }
        }

        return entries;
    }

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

    private static class Entry {

        private final String switches;
        private final String description;
        private final String defaultValue;

        public Entry(final String switches, final String description, final String defaultValue) {
            this.switches = switches;
            this.description = description;
            this.defaultValue = defaultValue;
        }

        public String getSwitches() {
            return switches;
        }

        public String getDescription() {
            return description;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    private static class Entries {

        private final Collection<Entry> entries = Lists.newArrayList();

        public boolean add(final Entry e) {
            return entries.add(e);
        }

        public Collection<Entry> getEntries() {
            return entries;
        }

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
