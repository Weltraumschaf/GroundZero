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

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link StringFilters}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class StringFiltersTest {

    @Test
    public void pathPrefixRemover() {
        assertThat(StringFilters.pathPrefixRemover(), is(not(nullValue())));
    }

    @Test
    public void pathNormalizer() {
        assertThat(StringFilters.pathNormalizer(), is(not(nullValue())));
    }

    @Test
    public void regeExEscaper() {
        assertThat(StringFilters.regeExEscaper(), is(not(nullValue())));
    }

    @Test
    public void fileNameExtender() {
        assertThat(StringFilters.fileNameExtender(), is(not(nullValue())));
    }

}
