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
package de.weltraumschaf.groundzero.opt.commons;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.cli.Option;
import static de.weltraumschaf.groundzero.opt.commons.OptionsConfiguration.Option.*;
import java.util.Collections;

/**
 * Custom comparator for defining order of options.
 *
 * Implements hard coded order of options as followed:
 * <ol>
 *  <li>{@link de.weltraumschaf.groundzero.OptionsConfiguration.Option#PATH_PREFIX}</li>
 *  <li>{@link de.weltraumschaf.groundzero.OptionsConfiguration.Option#INPUT_ENCODING}</li>
 *  <li>{@link de.weltraumschaf.groundzero.OptionsConfiguration.Option#OUTPUT_ENCODING}</li>
 *  <li>{@link de.weltraumschaf.groundzero.OptionsConfiguration.Option#HELP}</li>
 *  <li>{@link de.weltraumschaf.groundzero.OptionsConfiguration.Option#VERSION}</li>
 *  <li>{@link de.weltraumschaf.groundzero.OptionsConfiguration.Option#DEBUG}</li>
 * </ol>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class OptionComparator implements Comparator<Option> {

    /**
     * Signals that optionOne comes before optionTwo.
     */
    private static final int LESS = -1;
    /**
     * Signals that optionOne is equal to optionTwo.
     */
    private static final int EQUAL = 0;
    /**
     * Signals that optionOne comes after optionTwo.
     */
    private static final int GREATER = 1;
    /**
     * Holds the options in their order.
     */
    private static final List<OptionsConfiguration.Option> ORDER;
    static {
        final List<OptionsConfiguration.Option> l = Lists.newArrayList();
        l.add(PATH_PREFIX);
        l.add(INPUT_ENCODING);
        l.add(OUTPUT_ENCODING);
        l.add(HELP);
        l.add(VERSION);
        l.add(DEBUG);
        ORDER = Collections.unmodifiableList(l);
    }

    @Override
    public int compare(final Option optionOne, final Option optionTwo) {
        final int positionOne = findPositionForKey(optionOne);
        final int positionTwo = findPositionForKey(optionTwo);

        if (positionOne < positionTwo) {
            return LESS;
        }

        if (positionOne > positionTwo) {
            return GREATER;
        }

        return EQUAL;
    }

    /**
     * Finds the order position of a option configuration.
     *
     * The method throws a {@link IllegalArgumentException}, if the position can not be determined
     * of a given option.
     *
     * @param option must not be {@code null}
     * @return non negative integer
     */
    int findPositionForKey(final Option option) {
        final String shortOption = option.getOpt();
        final String longOption = option.getLongOpt();

        for (OptionsConfiguration.Option config : ORDER) {
            if (config.getShortOption().equals(shortOption) || config.getLongOption().equals(longOption)) {
                return ORDER.indexOf(config);
            }
        }

        throw new IllegalArgumentException(String.format("Unconfigured option: %s!", option));
    }
}
