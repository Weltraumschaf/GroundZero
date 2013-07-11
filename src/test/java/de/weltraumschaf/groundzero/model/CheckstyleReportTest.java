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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link CheckstyleReport}.
 * 
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CheckstyleReportTest {
    
    @Rule public final ExpectedException thrown = ExpectedException.none();
    private final CheckstyleReport sut = new CheckstyleReport("foobar");
    
    @Test
    public void addFile_throwsExceptionIfNul() {
        thrown.expect(NullPointerException.class);
        sut.addFile(null);
    }
    
    @Test
    public void addFile() {
        final CheckstyleFile f1 = new CheckstyleFile("foo");
        sut.addFile(f1);
        assertThat(sut.getFiles(), hasSize(1));
        assertThat(sut.getFiles(), containsInAnyOrder(f1));
        
        final CheckstyleFile f2 = new CheckstyleFile("bar");
        sut.addFile(f2);
        assertThat(sut.getFiles(), hasSize(2));
        assertThat(sut.getFiles(), containsInAnyOrder(f1, f2));
    }

    @Test
    public void getFiles() {
        assertThat(sut.getFiles(), empty());
    }

    @Test 
    public void testHashCode() {
        final CheckstyleReport sut1 = new CheckstyleReport("foobar");
        final CheckstyleReport sut2 = new CheckstyleReport("snafu");
        
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
        final CheckstyleReport sut1 = new CheckstyleReport("foobar");
        final CheckstyleReport sut2 = new CheckstyleReport("snafu");
        
        assertThat(sut.equals(null), is(false));
        assertThat(sut.equals("foo"), is(false));
        
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
        assertThat(sut.toString(), is(equalTo("CheckstyleReport{version=foobar, files=[]}")));
        sut.addFile(new CheckstyleFile("foo"));
        assertThat(sut.toString(), is(equalTo("CheckstyleReport{version=foobar, files=["
                + "CheckstyleFile{fileName=foo, violations=[]}"
                + "]}")));
        sut.addFile(new CheckstyleFile("bar"));
        assertThat(sut.toString(), is(equalTo("CheckstyleReport{version=foobar, files=["
                + "CheckstyleFile{fileName=foo, violations=[]}, "
                + "CheckstyleFile{fileName=bar, violations=[]}"
                + "]}")));
    }

    @Test 
    public void testHasFiles() {
        assertThat(sut.hasFiles(), is(false));
        sut.addFile(new CheckstyleFile("foo"));
        assertThat(sut.hasFiles(), is(true));
        sut.addFile(new CheckstyleFile("bar"));
        assertThat(sut.hasFiles(), is(true));
    }
}