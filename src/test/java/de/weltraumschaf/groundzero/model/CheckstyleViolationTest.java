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
 * Tests for {@link CheckstyleViolation}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CheckstyleViolationTest {

    // CHECKSTYLE:OFF Must be public for JUnit.
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final CheckstyleViolation sut = new CheckstyleViolation();

    @Test
    public void getCheck() {
        sut.setSource("com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck");
        assertThat(sut.getCheck(), is(equalTo("FileTabCharacterCheck")));
    }

    @Test
    public void setAndGetLine() {
        assertThat(sut.getLine(), is(0));
        sut.setLine(23);
        assertThat(sut.getLine(), is(23));
    }

    @Test
    public void setLine_throwsExceptionIfLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        sut.setLine(0);
    }

    @Test
    public void testSetAndGetColumn() {
        assertThat(sut.getColumn(), is(0));
        sut.setColumn(42);
        assertThat(sut.getColumn(), is(42));
    }

    @Test
    public void setColumn_throwsExceptionIfLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        sut.setColumn(0);
    }

    @Test
    public void setAndGetSeverity() {
        assertThat(sut.getSeverity(), is(CheckstyleSeverity.IGNORE));
        sut.setSeverity(CheckstyleSeverity.WARNING);
        assertThat(sut.getSeverity(), is(CheckstyleSeverity.WARNING));
    }

    @Test
    public void setSeverity_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        sut.setSeverity(null);
    }

    @Test
    public void setAndGetMessage() {
        assertThat(sut.getMessage(), is(""));
        sut.setMessage("foobar");
        assertThat(sut.getMessage(), is("foobar"));
    }

    @Test
    public void setMessage_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        sut.setMessage(null);
    }

    @Test
    public void setMessage_throwsExceptionIfEmpty() {
        thrown.expect(IllegalArgumentException.class);
        sut.setMessage("");
    }

    @Test
    public void setAndGetSource() {
        assertThat(sut.getSource(), is(""));
        sut.setSource("foobar");
        assertThat(sut.getSource(), is("foobar"));
    }

    @Test
    public void setSource_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        sut.setSource(null);
    }

    @Test
    public void setSource_throwsExceptionIfEmpty() {
        thrown.expect(IllegalArgumentException.class);
        sut.setSource("");
    }

    @Test
    public void testHashCode() {
        sut.setLine(23);
        sut.setColumn(42);
        sut.setMessage("foo");
        sut.setSource("bar");
        final CheckstyleViolation sut1 = new CheckstyleViolation();
        sut1.setLine(23);
        sut1.setColumn(42);
        sut1.setMessage("foo");
        sut1.setSource("bar");
        final CheckstyleViolation sut2 = new CheckstyleViolation();
        sut2.setLine(1);
        sut2.setColumn(2);
        sut2.setMessage("foo");
        sut2.setSource("bar");

        assertThat(sut.hashCode(), is(sut.hashCode()));
        assertThat(sut.hashCode(), is(sut1.hashCode()));
        assertThat(sut1.hashCode(), is(sut1.hashCode()));

        assertThat(sut2.hashCode(), is(sut2.hashCode()));
        assertThat(sut2.hashCode(), is(not(sut.hashCode())));
        assertThat(sut2.hashCode(), is(not(sut1.hashCode())));
    }

    @Test
    public void testEquals() {
        sut.setLine(23);
        sut.setColumn(42);
        sut.setMessage("foo");
        sut.setSource("bar");
        final CheckstyleViolation sut1 = new CheckstyleViolation();
        sut1.setLine(23);
        sut1.setColumn(42);
        sut1.setMessage("foo");
        sut1.setSource("bar");
        final CheckstyleViolation sut2 = new CheckstyleViolation();
        sut2.setLine(1);
        sut2.setColumn(2);
        sut2.setMessage("foo");
        sut2.setSource("bar");

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

        sut.setMessage("message");
        assertThat(sut.equals(sut1), is(false));
        sut1.setMessage("message");
        assertThat(sut.equals(sut1), is(true));
        sut.setSeverity(CheckstyleSeverity.WARNING);
        assertThat(sut.equals(sut1), is(false));
        sut1.setSeverity(CheckstyleSeverity.WARNING);
        assertThat(sut.equals(sut1), is(true));
        sut.setSource("source");
        assertThat(sut.equals(sut1), is(false));
        sut1.setSource("source");
        assertThat(sut.equals(sut1), is(true));
    }

    @Test
    public void testToString() {
        assertThat(sut.toString(), is("CheckstyleViolation{line=0, column=0, severity=ignore, message=, source=}"));
        sut.setLine(23);
        sut.setColumn(42);
        sut.setSeverity(CheckstyleSeverity.WARNING);
        sut.setMessage("foo");
        sut.setSource("bar");
        assertThat(sut.toString(),
                is("CheckstyleViolation{line=23, column=42, severity=warning, message=foo, source=bar}"));
    }
}
