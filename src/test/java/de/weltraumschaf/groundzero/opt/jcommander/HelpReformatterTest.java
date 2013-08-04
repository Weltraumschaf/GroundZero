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
package de.weltraumschaf.groundzero.opt.jcommander;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link HelpReformatter}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class HelpReformatterTest {

    private static final String INPUT = "Usage: groundzero [options] report1.xml [report2.xml ... reportN.xml]\n"
            + "  Options:\n"
            + "    -d, --debug\n"
            + "       Enables debug output.\n"
            + "       Default: false\n"
            + "    -h, --help\n"
            + "       This help.\n"
            + "       Default: false\n"
            + "    -i, --input-encoding\n"
            + "       Input encoding of the report files.\n"
            + "       Default: UTF-8\n"
            + "    -o, --output-encoding\n"
            + "       Output encoding of the suppressions files.\n"
            + "       Default: UTF-8\n"
            + "    -p, --path-prefix\n"
            + "       Prefix to strip from checked file paths.\n"
            + "       Default: <empty string>\n"
            + "    -v, --version\n"
            + "       Show version information.\n"
            + "       Default: false\n";
    //CHECKSTYLE:OFF
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final HelpReformatter sut = new HelpReformatter();

    @Test
    public void setInput_throwsExceptionIfStringIsNull() {
        thrown.expect(NullPointerException.class);
        final String input = null;
        sut.setInput(input);
    }

    @Test
    public void setInput_throwsExceptionIfStringBuilderIsNull() {
        thrown.expect(NullPointerException.class);
        final StringBuilder input = null;
        sut.setInput(input);
    }

    @Test
    public void getUsage_default() {
        assertThat(sut.getUsage(),
                is(equalTo("")));
    }

    @Test
    public void getUsage() {
        sut.setInput(INPUT);
        assertThat(sut.getUsage(),
                is(equalTo("Usage: groundzero [options] report1.xml [report2.xml ... reportN.xml]")));
    }

    @Test
    public void getOptions_default() {
        assertThat(sut.getOptions(),
                is(equalTo("")));
    }

    @Test
    public void getOptions() {
        sut.setInput(INPUT);
        assertThat(sut.getOptions(),
                is(equalTo("Options:\n"
                + "    -d, --debug              Enables debug output. Default: false\n"
                + "    -h, --help               This help. Default: false\n"
                + "    -i, --input-encoding     Input encoding of the report files. Default: UTF-8\n"
                + "    -o, --output-encoding    Output encoding of the suppressions files. Default: UTF-8\n"
                + "    -p, --path-prefix        Prefix to strip from checked file paths. Default: <empty string>\n"
                + "    -v, --version            Show version information. Default: false\n")));
    }

    @Test public void rightPad_throwsExceptionIfLengthLessThanZero() {
        thrown.expect(IllegalArgumentException.class);
        sut.rightPad("", -1);
    }

    @Test public void rightPad() {
        assertThat(sut.rightPad(null, 0), is(equalTo("")));
        assertThat(sut.rightPad("", 0), is(equalTo("")));
        assertThat(sut.rightPad("", 0), is(equalTo("")));
        assertThat(sut.rightPad("foobar", 0), is(equalTo("foobar")));
        assertThat(sut.rightPad("foobar", 3), is(equalTo("foobar")));
        assertThat(sut.rightPad("foobar", 10), is(equalTo("foobar    ")));
    }
}