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
;

/**
 * Tests for {@link CheckstyleViolation}.
 * 
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CheckstyleViolationTest {

    @Test
    public void getCheck() {
        final CheckstyleViolation sut = new CheckstyleViolation();
        sut.setSource("com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck");
        assertThat(sut.getCheck(), is(equalTo("FileTabCharacterCheck")));
    }

    @Test @Ignore
    public void testGetLine() {
    }

    @Test @Ignore
    public void testSetLine() {
    }

    @Test @Ignore
    public void testGetColumn() {
    }

    @Test @Ignore
    public void testSetColumn() {
    }

    @Test @Ignore
    public void testGetSeverity() {
    }

    @Test @Ignore
    public void testSetSeverity() {
    }

    @Test @Ignore
    public void testGetMessage() {
    }

    @Test @Ignore
    public void testSetMessage() {
    }

    @Test @Ignore
    public void testGetSource() {
    }

    @Test @Ignore
    public void testSetSource() {
    }

    @Test @Ignore
    public void testGetCheck() {
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