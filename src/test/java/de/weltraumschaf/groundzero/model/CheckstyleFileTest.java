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
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link CheckstyleFile}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CheckstyleFileTest {

    // CHECKSTYLE:OFF Must be public for JUnit.
    @Rule public final ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON

    private final CheckstyleFile sut = new CheckstyleFile("foo.bar");

    @Test
    public void getFileName() {
        assertThat(sut.getFileName(), is(equalTo("foo.bar")));
    }

    @Test
    public void getViolations() {
        assertThat(sut.getViolations(), empty());
    }

    @Test
    public void addViolation_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        sut.addViolation(null);
    }

    @Test
    public void addViolation() {
        final CheckstyleViolation v1 = new CheckstyleViolation();
        v1.setMessage("foo");
        sut.addViolation(v1);
        assertThat(sut.getViolations(), hasSize(1));
        assertThat(sut.getViolations(), contains(v1));

        final CheckstyleViolation v2 = new CheckstyleViolation();
        v2.setMessage("bar");
        sut.addViolation(v2);
        assertThat(sut.getViolations(), hasSize(2));
        assertThat(sut.getViolations(), containsInAnyOrder(v1, v2));
    }

    @Test
    public void testHashCode() {
        final CheckstyleFile sut1 = new CheckstyleFile("foo.bar");
        final CheckstyleFile sut2 = new CheckstyleFile("snafu");

        assertThat(sut.hashCode(), is(sut.hashCode()));
        assertThat(sut.hashCode(), is(sut1.hashCode()));
        assertThat(sut1.hashCode(), is(sut.hashCode()));
        assertThat(sut1.hashCode(), is(sut1.hashCode()));

        assertThat(sut2.hashCode(), is(sut2.hashCode()));
        assertThat(sut2.hashCode(), is(not(sut.hashCode())));
        assertThat(sut2.hashCode(), is(not(sut1.hashCode())));
    }

    @Test
    public void testEquals() {
        final CheckstyleFile sut1 = new CheckstyleFile("foo.bar");
        final CheckstyleFile sut2 = new CheckstyleFile("snafu");

        //CHECKSTYLE:OFF Pirpose to check null returns false.
        assertThat(sut.equals(null), is(false));
        //CHECKSTYLE:ON
        assertThat(sut.equals(new Object()), is(false));

        assertThat(sut.equals(sut), is(true));
        assertThat(sut1.equals(sut), is(true));
        assertThat(sut.equals(sut1), is(true));
        assertThat(sut1.equals(sut1), is(true));

        assertThat(sut2.equals(sut2), is(true));
        assertThat(sut2.equals(sut), is(false));
        assertThat(sut2.equals(sut1), is(false));
    }

    @Test
    public void testToString() {
        assertThat(sut.toString(), is(equalTo("CheckstyleFile{fileName=foo.bar, violations=[]}")));

        final CheckstyleViolation v1 = new CheckstyleViolation();
        v1.setMessage("foo");
        sut.addViolation(v1);
        assertThat(sut.toString(), is(equalTo("CheckstyleFile{fileName=foo.bar, "
                + "violations=[CheckstyleViolation{line=0, column=0, severity=ignore, message=foo, source=}]}")));

        final CheckstyleViolation v2 = new CheckstyleViolation();
        v2.setMessage("bar");
        sut.addViolation(v2);
        assertThat(sut.toString(), is(equalTo("CheckstyleFile{fileName=foo.bar, violations=["
                + "CheckstyleViolation{line=0, column=0, severity=ignore, message=foo, source=}, "
                + "CheckstyleViolation{line=0, column=0, severity=ignore, message=bar, source=}"
                + "]}")));
    }

}
