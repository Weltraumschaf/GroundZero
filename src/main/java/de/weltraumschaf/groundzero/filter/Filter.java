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

/**
 * Processes a given input and returns the result.
 *
 * @param <T> type of processed value
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Filter<T> {

    /**
     * Processes the given input.
     *
     * @param input must not be {@code null}
     * @return processed output, must not be {@code null}
     */
    T process(T input);

}
