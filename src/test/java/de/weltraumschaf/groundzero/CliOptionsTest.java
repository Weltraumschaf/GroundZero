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
 * Tests for {@link CliOptions}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CliOptionsTest {

    //CHECKSTYLE: OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE: ON

    private CliOptions setUpSut(final String[] args) throws ParseException {
        final CliOptions options = new CliOptions();
        options.parse(args);
        return options;
    }

    @Test
    public void defaults() {
        final CliOptions options = new CliOptions();
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
        assertThat(options.hasReportFiles(), is(false));
        assertThat(options.getReportFiles(), hasSize(0));
        assertThat(options.getPathPrefix(), is(equalTo("")));
    }

    @Test
    public void parse_debugShortOptions() throws ParseException {
        final CliOptions options = setUpSut(new String[]{"-d"});
        assertThat(options.isDebug(), is(true));
    }

    @Test
    public void parse_debugLongOptions() throws ParseException {
        final CliOptions options = setUpSut(new String[]{"--debug"});
        assertThat(options.isDebug(), is(true));
    }

    @Test
    public void parse_helpShortOptions() throws ParseException {
        final CliOptions options = setUpSut(new String[]{"-h"});
        assertThat(options.isHelp(), is(true));
    }

    @Test
    public void parse_helpLongOptions() throws ParseException {
        final CliOptions options = setUpSut(new String[]{"--help"});
        assertThat(options.isHelp(), is(true));
    }

    @Test
    public void parse_versionShortOptions() throws ParseException {
        final CliOptions options = setUpSut(new String[]{"-v"});
        assertThat(options.isVersion(), is(true));
    }

    @Test
    public void parse_versionLongOptions() throws ParseException {
        final CliOptions options = setUpSut(new String[]{"--version"});
        assertThat(options.isVersion(), is(true));
    }


    @Test
    public void parse_pathPrefixShortOptionWithArg() throws ParseException {
        final CliOptions options = setUpSut(new String[]{"-p", "foo"});
        assertThat(options.getPathPrefix(), is(equalTo("foo")));
    }

    @Test
    public void parse_pathPrefixShortOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        setUpSut(new String[]{"-p"});
    }

    @Test
    public void parse_pathPrefixLongOptionWithArg() throws ParseException {
        final CliOptions options = setUpSut(new String[]{"--path-prefix", "foo"});
        assertThat(options.getPathPrefix(), is(equalTo("foo")));
    }

    @Test
    public void parse_pathPrefixLongOptionWithNoArg() throws ParseException {
        thrown.expect(MissingArgumentException.class);
        setUpSut(new String[]{"--path-prefix"});
    }
    
}
