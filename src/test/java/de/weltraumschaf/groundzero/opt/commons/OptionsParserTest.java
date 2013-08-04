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
package de.weltraumschaf.groundzero.opt.commons;

import de.weltraumschaf.groundzero.opt.CliOptions;
import de.weltraumschaf.groundzero.opt.CliOptionsImplementation;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link OptionsParser}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class OptionsParserTest {

    //CHECKSTYLE:OFF Must be public for JUnit's sake.
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final OptionsParser sut = new OptionsParser(new OptionsConfiguration());

    @Test
    public void parse_debugShortOptions() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"-d"}, options);
        assertThat(options.isDebug(), is(true));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_debugLongOptions() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"--debug"}, options);
        assertThat(options.isDebug(), is(true));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_helpShortOptions() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"-h"}, options);
        assertThat(options.isHelp(), is(true));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_helpLongOptions() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"--help"}, options);
        assertThat(options.isHelp(), is(true));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_versionShortOptions() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"-v"}, options);
        assertThat(options.isVersion(), is(true));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isDebug(), is(false));
    }

    @Test
    public void parse_versionLongOptions() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"--version"}, options);
        assertThat(options.isVersion(), is(true));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isDebug(), is(false));
    }

    @Test
    public void parse_pathPrefixShortOptionWithArg() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"-p", "foo"}, options);
        assertThat(options.getPathPrefix(), is(equalTo("foo")));
    }

    @Test
    public void parse_pathPrefixShortOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"-p"}, options);
    }

    @Test
    public void parse_pathPrefixLongOptionWithArg() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"--path-prefix", "foo"}, options);
        assertThat(options.getPathPrefix(), is(equalTo("foo")));
    }

    @Test
    public void parse_pathPrefixLongOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"--path-prefix"}, options);
    }

    @Test
    public void parse_inputEncodingShortOptionWithArg() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"-i", "foo"}, options);
        assertThat(options.getInputEncoding(), is(equalTo("foo")));
    }

    @Test
    public void parse_inputEncodingShortOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"-i"}, options);
    }

    @Test
    public void parse_inputEncodingLongOptionWithArg() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"--input-encoding", "foo"}, options);
        assertThat(options.getInputEncoding(), is(equalTo("foo")));
    }

    @Test
    public void parse_inputEncodingLongOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"--input-encoding"}, options);
    }

    @Test
    public void parse_outputEncodingShortOptionWithArg() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"-o", "foo"}, options);
        assertThat(options.getOutputEncoding(), is(equalTo("foo")));
    }

    @Test
    public void parse_outputEncodingShortOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"-o"}, options);
    }

    @Test
    public void parse_outputEncodingLongOptionWithArg() throws ParseException {
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"--output-encoding", "foo"}, options);
        assertThat(options.getOutputEncoding(), is(equalTo("foo")));
    }

    @Test
    public void parse_outputEncodingLongOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        final CliOptions options = new CliOptionsImplementation();
        sut.parse(new String[]{"--output-encoding"}, options);
    }
}
