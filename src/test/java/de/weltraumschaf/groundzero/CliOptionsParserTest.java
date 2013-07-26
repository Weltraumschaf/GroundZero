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
package de.weltraumschaf.groundzero;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link CliOptionsParser}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CliOptionsParserTest {

    //CHECKSTYLE:OFF Must be public for JUnit's sake.
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final CliOptionsParser sut = new CliOptionsParser(new CliOptionsConfiguration());

    @Test
    public void parse_debugShortOptions() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"-d"});
        assertThat(options.isDebug(), is(true));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_debugLongOptions() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"--debug"});
        assertThat(options.isDebug(), is(true));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_helpShortOptions() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"-h"});
        assertThat(options.isHelp(), is(true));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_helpLongOptions() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"--help"});
        assertThat(options.isHelp(), is(true));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_versionShortOptions() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"-v"});
        assertThat(options.isVersion(), is(true));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isDebug(), is(false));
    }

    @Test
    public void parse_versionLongOptions() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"--version"});
        assertThat(options.isVersion(), is(true));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isDebug(), is(false));
    }


    @Test
    public void parse_pathPrefixShortOptionWithArg() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"-p", "foo"});
        assertThat(options.getPathPrefix(), is(equalTo("foo")));
    }

    @Test
    public void parse_pathPrefixShortOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        sut.parse(new String[]{"-p"});
    }

    @Test
    public void parse_pathPrefixLongOptionWithArg() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"--path-prefix", "foo"});
        assertThat(options.getPathPrefix(), is(equalTo("foo")));
    }

    @Test
    public void parse_pathPrefixLongOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        sut.parse(new String[]{"--path-prefix"});
    }

    @Test
    public void parse_inputEncodingShortOptionWithArg() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"-i", "foo"});
        assertThat(options.getInputEncoding(), is(equalTo("foo")));
    }

    @Test
    public void parse_inputEncodingShortOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        sut.parse(new String[]{"-i"});
    }

    @Test
    public void parse_inputEncodingLongOptionWithArg() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"--input-encoding", "foo"});
        assertThat(options.getInputEncoding(), is(equalTo("foo")));
    }

    @Test
    public void parse_inputEncodingLongOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        sut.parse(new String[]{"--input-encoding"});
    }

    @Test
    public void parse_outputEncodingShortOptionWithArg() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"-o", "foo"});
        assertThat(options.getOutputEncoding(), is(equalTo("foo")));
    }

    @Test
    public void parse_outputEncodingShortOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        sut.parse(new String[]{"-o"});
    }

    @Test
    public void parse_outputEncodingLongOptionWithArg() throws ParseException {
        final CliOptions options = sut.parse(new String[]{"--output-encoding", "foo"});
        assertThat(options.getOutputEncoding(), is(equalTo("foo")));
    }

    @Test
    public void parse_outputEncodingLongOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        sut.parse(new String[]{"--output-encoding"});
    }

}
