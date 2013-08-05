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
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@code PathNormalizer}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PathNormalizerTest {

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final PathNormalizer sut = new PathNormalizer();

    @Test
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(
            value = "NP_NULL_PARAM_DEREF",
            justification = "I want to test NPE.")
    public void process_throwsExceptionifNullPassedIn() {
        thrown.expect(NullPointerException.class);
        sut.process(null);
    }

    @Test
    public void process_singleDotDirs() {
        assertThat(sut.process(""), is(equalTo("")));
        assertThat(sut.process("/foo/bar/baz"), is(equalTo("/foo/bar/baz")));
        assertThat(sut.process("/foo/.bar/baz"), is(equalTo("/foo/.bar/baz")));
        assertThat(sut.process("/foo/./bar/baz"), is(equalTo("/foo/bar/baz")));
        assertThat(sut.process("/foo/./bar/./baz"), is(equalTo("/foo/bar/baz")));
        assertThat(sut.process("/foo/./././bar/./baz"), is(equalTo("/foo/bar/baz")));
    }

}

