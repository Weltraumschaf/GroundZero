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
import static org.hamcrest.Matchers.*;import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;

/**
 * Tests for {@lin FilterChain}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FilterChainTest {

    //CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final FilterChain<Object> sut = new FilterChain<Object>();

    @Test
    public void process_throwsExcpetionNoInput() {
        thrown.expect(NullPointerException.class);
        sut.process(null);
    }

    @Test
    public void process_nofilters() {
        final Object fixture = new Object();
        assertThat(sut.process(fixture), is(sameInstance(fixture)));
    }

    @Test
    public void process_oneFilter() {
        final Object fixture = new Object();
        final Filter<Object> filter = spy(new FilterStub());
        sut.add(filter);
        assertThat(sut.process(fixture), is(sameInstance(fixture)));
        verify(filter, times(1)).process(fixture);
    }

    @Test
    public void process_threeFilter() {
        final Object fixture = new Object();
        final Filter<Object> filte1 = spy(new FilterStub());
        sut.add(filte1);
        final Filter<Object> filte2 = spy(new FilterStub());
        sut.add(filte2);
        final Filter<Object> filte3 = spy(new FilterStub());
        sut.add(filte3);
        assertThat(sut.process(fixture), is(sameInstance(fixture)));
        verify(filte1, times(1)).process(fixture);
        verify(filte2, times(1)).process(fixture);
        verify(filte3, times(1)).process(fixture);
    }

    private static class FilterStub implements Filter<Object> {

        @Override
        public Object process(final Object input) {
            return input;
        }

    }
}