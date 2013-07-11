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
import static org.hamcrest.Matchers.*;import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
;

/**
 * Tests for {@link CheckstyleViolation}.
 * 
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CheckstyleViolationTest {

    @Rule public final ExpectedException thrown = ExpectedException.none();
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

    @Test @Ignore
    public void testHashCode() {
    }

    @Test @Ignore
    public void testEquals() {
    }

    @Test @Ignore
    public void testToString() {
    }

}