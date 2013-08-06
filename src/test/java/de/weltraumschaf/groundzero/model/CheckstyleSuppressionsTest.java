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

package de.weltraumschaf.groundzero.model;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;;

/**
 * Tests for {@link CheckstyleSuppressions}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CheckstyleSuppressionsTest {

    @Test
    public void testHashCode() {
        final CheckstyleSuppressions sut1 = new  CheckstyleSuppressions("foo1", "bar1", "UTF-8");
        final CheckstyleSuppressions sut2 = new  CheckstyleSuppressions("foo1", "bar1", "UTF-8");
        final CheckstyleSuppressions sut3 = new  CheckstyleSuppressions("foo2", "bar1", "UTF-8");

        assertThat(sut1.hashCode(), is(sut1.hashCode()));
        assertThat(sut1.hashCode(), is(sut2.hashCode()));
        assertThat(sut2.hashCode(), is(sut1.hashCode()));
        assertThat(sut2.hashCode(), is(sut2.hashCode()));

        assertThat(sut3.hashCode(), is(sut3.hashCode()));
        assertThat(sut3.hashCode(), is(not(sut1.hashCode())));
        assertThat(sut3.hashCode(), is(not(sut2.hashCode())));
    }

    @Test
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(
            value = "NP_NULL_PARAM_DEREF",
            justification = "I want to test equals null.")
    public void testEquals() {
        final CheckstyleSuppressions sut1 = new  CheckstyleSuppressions("foo1", "bar1", "UTF-8");
        final CheckstyleSuppressions sut2 = new  CheckstyleSuppressions("foo1", "bar1", "UTF-8");
        final CheckstyleSuppressions sut3 = new  CheckstyleSuppressions("foo2", "bar1", "UTF-8");

        //CHECKSTYLE:OFF
        assertThat(sut1.equals(null), is(false));
        assertThat(sut1.equals("foo"), is(false));
        //CHECKSTYLE:ON

        assertThat(sut1.equals(sut1), is(true));
        assertThat(sut1.equals(sut2), is(true));
        assertThat(sut2.equals(sut1), is(true));
        assertThat(sut2.equals(sut2), is(true));

        assertThat(sut3.equals(sut3), is(true));
        assertThat(sut3.equals(sut1), is(false));
        assertThat(sut3.equals(sut2), is(false));
    }

    @Test
    public void testToString() {
        final CheckstyleSuppressions sut = new  CheckstyleSuppressions("foo", "bar", "UTF-8");
        assertThat(sut.toString(), is(equalTo("CheckstyleSuppressions{xmlContent=foo, fileName=bar, encoding=UTF-8}")));
    }

}
