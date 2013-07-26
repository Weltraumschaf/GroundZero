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

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang3.Validate;

/**
 * Combine {@link Filter filters} to a chain.
 *
 * The filters will be executed in the order they were added.
 * The output of a filter is passed to as input to the next.
 *
 * @param <T> type of processed value
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FilterChain<T> implements Filter<T> {

    /**
     * Holds the filters.
     */
    private final List<Filter<T>> filters = Lists.newArrayList();

    /**
     * Use {@link #newChain()} to get instances.
     */
    private FilterChain() {
        super();
    }

    @Override
    public T process(final T input) {
        Validate.notNull(input);

        if (filters.isEmpty()) {
            return input;
        }

        T output = input;

        for (final Filter<T> filter : filters) {
            output = filter.process(output);
        }

        return output;
    }

    /**
     * Add a filter.
     *
     * @param filter must not be {@code null}
     */
    public void add(final Filter<T> filter) {
        Validate.notNull(filter);
        filters.add(filter);
    }

    /**
     * Create new instance.
     *
     * @param <T> filtered type
     * @return always new instance
     */
    public static <T> FilterChain<T> newChain() {
        return new FilterChain<T>();
    }

}
