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

import org.junit.Ignore;
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

    @Rule public final ExpectedException thrown = ExpectedException.none();
    
    private final CheckstyleFile sut = new CheckstyleFile("foo.bar");
    
    @Test
    public void testGetFileName() {
        assertThat(sut.getFileName(), is(equalTo("foo.bar")));
    }

    @Test
    public void testGetViolations() {
        assertThat(sut.getViolations(), empty());
    }
    
    @Test 
    public void testAddViolation_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        sut.addViolation(null);
    }
    
    @Test
    public void testAddViolation() {
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

    @Test @Ignore
    public void testHashCode() {
    }

    @Test @Ignore
    public void testEquals() {
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
                + "CheckstyleViolation{line=0, column=0, severity=ignore, message=bar, source=}, "
                + "CheckstyleViolation{line=0, column=0, severity=ignore, message=foo, source=}"
                + "]}")));
    }

}