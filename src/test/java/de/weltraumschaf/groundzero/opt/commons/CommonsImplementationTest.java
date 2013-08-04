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
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link CommonsImplementation}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CommonsImplementationTest {

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final CommonsImplementation sut = new CommonsImplementation();

    @Test
    public void parse_null() throws Exception {
        thrown.expect(NullPointerException.class);
        sut.parse(null);
    }

    @Test
    public void parse_empty() throws Exception {
        final CliOptions options = sut.parse(new String[]{});
        assertThat(options.getInputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getOutputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getPathPrefix(), is(equalTo("")));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_pathPrefixShort() throws Exception {
        final CliOptions options = sut.parse(new String[]{"-p", "foobar"});
        assertThat(options.getInputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getOutputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getPathPrefix(), is(equalTo("foobar")));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_pathPrefixLong() throws Exception {
        final CliOptions options = sut.parse(new String[]{"--path-prefix", "foobar"});
        assertThat(options.getInputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getOutputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getPathPrefix(), is(equalTo("foobar")));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_inputEncodingShort() throws Exception {
        final CliOptions options = sut.parse(new String[]{"-i", "foobar"});
        assertThat(options.getInputEncoding(), is(equalTo("foobar")));
        assertThat(options.getOutputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getPathPrefix(), is(equalTo("")));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_inputEncodingLong() throws Exception {
        final CliOptions options = sut.parse(new String[]{"--input-encoding", "foobar"});
        assertThat(options.getInputEncoding(), is(equalTo("foobar")));
        assertThat(options.getOutputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getPathPrefix(), is(equalTo("")));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_outputEncodingShort() throws Exception {
        final CliOptions options = sut.parse(new String[]{"-o", "foobar"});
        assertThat(options.getInputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getOutputEncoding(), is(equalTo("foobar")));
        assertThat(options.getPathPrefix(), is(equalTo("")));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_outputEncodingLong() throws Exception {
        final CliOptions options = sut.parse(new String[]{"--output-encoding", "foobar"});
        assertThat(options.getInputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getOutputEncoding(), is(equalTo("foobar")));
        assertThat(options.getPathPrefix(), is(equalTo("")));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_helpShort() throws Exception {
        final CliOptions options = sut.parse(new String[]{"-h"});
        assertThat(options.getInputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getOutputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getPathPrefix(), is(equalTo("")));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(true));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_helpLong() throws Exception {
        final CliOptions options = sut.parse(new String[]{"--help"});
        assertThat(options.getInputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getOutputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getPathPrefix(), is(equalTo("")));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(true));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_versionShort() throws Exception {
        final CliOptions options = sut.parse(new String[]{"-v"});
        assertThat(options.getInputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getOutputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getPathPrefix(), is(equalTo("")));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(true));
    }

    @Test
    public void parse_versionLong() throws Exception {
        final CliOptions options = sut.parse(new String[]{"--version"});
        assertThat(options.getInputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getOutputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getPathPrefix(), is(equalTo("")));
        assertThat(options.isDebug(), is(false));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(true));
    }

    @Test
    public void parse_debugShort() throws Exception {
        final CliOptions options = sut.parse(new String[]{"-d"});
        assertThat(options.getInputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getOutputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getPathPrefix(), is(equalTo("")));
        assertThat(options.isDebug(), is(true));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test
    public void parse_debugLong() throws Exception {
        final CliOptions options = sut.parse(new String[]{"--debug"});
        assertThat(options.getInputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getOutputEncoding(), is(equalTo(CliOptions.DEFAULT_ENCODING)));
        assertThat(options.getPathPrefix(), is(equalTo("")));
        assertThat(options.isDebug(), is(true));
        assertThat(options.isHelp(), is(false));
        assertThat(options.isVersion(), is(false));
    }

    @Test @Ignore
    public void help() {
        assertThat(sut.help(), is(equalTo("")));
    }

}
