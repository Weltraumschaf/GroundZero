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
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(
            value = "NP_NULL_PARAM_DEREF",
            justification = "I want to test NPE.")
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

    @Test
    public void help() {
        assertThat(sut.help(),
                is(equalTo("usage: groundzero [-p <PATH>] [-i <ENCODING>] [-o <ENCODING>] [-h] [-v]\n"
                + "       [-d]\n"
                + "A tool to generate line based suppression files for Checkstyle.\n"
                + "Parses the Checkstyle report files given as command line argument and\n"
                + "generates suppression XML configuration files from them. The suppression\n"
                + "configurations are saved into files in the current working directory.\n"
                + "The file names are the same as the report filename with the addition of\n"
                + "'.suppressions' before the '.xml' file extension. So the report file\n"
                + "'foobar.xml' will produce a suppression file named\n"
                + "'foobar.suppressions.xml'.\n"
                + " -p,--path-prefix <PATH>           Prefix to strip from checked file\n"
                + "                                   paths.\n"
                + " -i,--input-encoding <ENCODING>    Input encoding of the report files.\n"
                + " -o,--output-encoding <ENCODING>   Output encoding of the suppressions\n"
                + "                                   files.\n"
                + " -h,--help                         This help.\n"
                + " -v,--version                      Show version information.\n"
                + " -d,--debug                        Enables debug output.\n\n"
                + "Written 2013 by Sven Strittmatter <weltraumschaf@googlemail.com>\n"
                + "Report bugs here https://github.com/Weltraumschaf/GroundZero/issues\n")));
    }

}
