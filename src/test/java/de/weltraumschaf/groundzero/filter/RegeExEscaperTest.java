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

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link RegeExEscaper}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class RegeExEscaperTest {

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final RegeExEscaper sut = new RegeExEscaper();

    @Test
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(
            value = "NP_NULL_PARAM_DEREF",
            justification = "I want to test NPE.")
    public void process_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        sut.process(null);
    }

    @Test
    public void process_doesNothingIfEmpty() {
        assertThat(sut.process(""), is(equalTo("")));
    }

    @Test
    public void process_escapesDots() {
        assertThat(sut.process("foobar"), is(equalTo("foobar")));
        assertThat(sut.process("foo.bar"), is(equalTo("foo\\.bar")));
        assertThat(sut.process("foo..bar"), is(equalTo("foo\\.\\.bar")));
        assertThat(sut.process("foo.bar.baz"), is(equalTo("foo\\.bar\\.baz")));
    }

}
