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

import de.weltraumschaf.groundzero.opt.commons.OptionComparator;
import org.apache.commons.cli.Option;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link OptionComparator}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CliOptionComparatorTest {
    private static final Option FIXTURE_SHORT_OPT_PATHPREFIX = new Option("p", "");
    private static final Option FIXTURE_SHORT_OPT_INPUTENCODING = new Option("i", "");
    private static final Option FIXTURE_SHORT_OPT_OUTPUTENCODING = new Option("o", "");
    private static final Option FIXTURE_SHORT_OPT_HELP = new Option("h", "");
    private static final Option FIXTURE_SHORT_OPT_VERSION = new Option("v", "");
    private static final Option FIXTURE_SHORT_OPT_DEBUG = new Option("d", "");
    private static final Option FIXTURE_LONG_OPT_PATHPREFIX = new Option("", "path-prefix", false, "");
    private static final Option FIXTURE_LONG_OPT_INPUTENCODING = new Option("", "input-encoding", false, "");
    private static final Option FIXTURE_LONG_OPT_OUTPUTENCODING = new Option("", "output-encoding", false, "");
    private static final Option FIXTURE_LONG_OPT_HELP = new Option("", "help", false, "");
    private static final Option FIXTURE_LONG_OPT_VERSION = new Option("", "version", false, "");
    private static final Option FIXTURE_LONG_OPT_DEBUG = new Option("", "debug", false, "");

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    private final OptionComparator sut = new OptionComparator();

    @Test
    public void findPositionForKey_throwsExceptionIfNullPassedIn() {
        thrown.expect(NullPointerException.class);
        sut.findPositionForKey(null);
    }

    @Test
    public void findPositionForKey_throwsExceptionIfOptionNotConfigured() {
        thrown.expect(IllegalArgumentException.class);
        sut.findPositionForKey(new Option("foo", "bar"));
    }

    @Test
    public void findPositionForKey_byShortOption() {
        assertThat(sut.findPositionForKey(FIXTURE_SHORT_OPT_PATHPREFIX), is(0));
        assertThat(sut.findPositionForKey(FIXTURE_SHORT_OPT_INPUTENCODING), is(1));
        assertThat(sut.findPositionForKey(FIXTURE_SHORT_OPT_OUTPUTENCODING), is(2));
        assertThat(sut.findPositionForKey(FIXTURE_SHORT_OPT_HELP), is(3));
        assertThat(sut.findPositionForKey(FIXTURE_SHORT_OPT_VERSION), is(4));
        assertThat(sut.findPositionForKey(FIXTURE_SHORT_OPT_DEBUG), is(5));
    }

    @Test
    public void findPositionForKey_byLongOption() {
        assertThat(sut.findPositionForKey(FIXTURE_LONG_OPT_PATHPREFIX), is(0));
        assertThat(sut.findPositionForKey(FIXTURE_LONG_OPT_INPUTENCODING), is(1));
        assertThat(sut.findPositionForKey(FIXTURE_LONG_OPT_OUTPUTENCODING), is(2));
        assertThat(sut.findPositionForKey(FIXTURE_LONG_OPT_HELP), is(3));
        assertThat(sut.findPositionForKey(FIXTURE_LONG_OPT_VERSION), is(4));
        assertThat(sut.findPositionForKey(FIXTURE_LONG_OPT_DEBUG), is(5));
    }

    @Test
    public void compare_equalShortOptions() {
        assertThat(sut.compare(FIXTURE_SHORT_OPT_PATHPREFIX, FIXTURE_SHORT_OPT_PATHPREFIX), is(0));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_INPUTENCODING, FIXTURE_SHORT_OPT_INPUTENCODING), is(0));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_OUTPUTENCODING, FIXTURE_SHORT_OPT_OUTPUTENCODING), is(0));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_HELP, FIXTURE_SHORT_OPT_HELP), is(0));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_VERSION, FIXTURE_SHORT_OPT_VERSION), is(0));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_DEBUG, FIXTURE_SHORT_OPT_DEBUG), is(0));
    }

    @Test
    public void compare_equalLongOptions() {
        assertThat(sut.compare(FIXTURE_LONG_OPT_PATHPREFIX, FIXTURE_LONG_OPT_PATHPREFIX), is(0));
        assertThat(sut.compare(FIXTURE_LONG_OPT_INPUTENCODING, FIXTURE_LONG_OPT_INPUTENCODING), is(0));
        assertThat(sut.compare(FIXTURE_LONG_OPT_OUTPUTENCODING, FIXTURE_LONG_OPT_OUTPUTENCODING), is(0));
        assertThat(sut.compare(FIXTURE_LONG_OPT_HELP, FIXTURE_LONG_OPT_HELP), is(0));
        assertThat(sut.compare(FIXTURE_LONG_OPT_VERSION, FIXTURE_LONG_OPT_VERSION), is(0));
        assertThat(sut.compare(FIXTURE_LONG_OPT_DEBUG, FIXTURE_LONG_OPT_DEBUG), is(0));
    }

    @Test
    public void compare_equalLongAndShotOptionsMixed() {
        assertThat(sut.compare(FIXTURE_LONG_OPT_PATHPREFIX, FIXTURE_SHORT_OPT_PATHPREFIX), is(0));
        assertThat(sut.compare(FIXTURE_LONG_OPT_INPUTENCODING, FIXTURE_SHORT_OPT_INPUTENCODING), is(0));
        assertThat(sut.compare(FIXTURE_LONG_OPT_OUTPUTENCODING, FIXTURE_SHORT_OPT_OUTPUTENCODING), is(0));
        assertThat(sut.compare(FIXTURE_LONG_OPT_HELP, FIXTURE_SHORT_OPT_HELP), is(0));
        assertThat(sut.compare(FIXTURE_LONG_OPT_VERSION, FIXTURE_SHORT_OPT_VERSION), is(0));
        assertThat(sut.compare(FIXTURE_LONG_OPT_DEBUG, FIXTURE_SHORT_OPT_DEBUG), is(0));
    }

    @Test
    public void compare_lessShortOptions() {
        assertThat(sut.compare(FIXTURE_SHORT_OPT_PATHPREFIX, FIXTURE_SHORT_OPT_INPUTENCODING), is(-1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_INPUTENCODING, FIXTURE_SHORT_OPT_OUTPUTENCODING), is(-1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_OUTPUTENCODING, FIXTURE_SHORT_OPT_HELP), is(-1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_HELP, FIXTURE_SHORT_OPT_VERSION), is(-1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_VERSION, FIXTURE_SHORT_OPT_DEBUG), is(-1));
    }

    @Test
    public void compare_lessLongOptions() {
        assertThat(sut.compare(FIXTURE_LONG_OPT_PATHPREFIX, FIXTURE_LONG_OPT_INPUTENCODING), is(-1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_INPUTENCODING, FIXTURE_LONG_OPT_OUTPUTENCODING), is(-1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_OUTPUTENCODING, FIXTURE_LONG_OPT_HELP), is(-1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_HELP, FIXTURE_LONG_OPT_VERSION), is(-1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_VERSION, FIXTURE_LONG_OPT_DEBUG), is(-1));
    }

    @Test
    public void compare_lessLongAndShotOptionsMixed() {
        assertThat(sut.compare(FIXTURE_LONG_OPT_PATHPREFIX, FIXTURE_SHORT_OPT_INPUTENCODING), is(-1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_PATHPREFIX, FIXTURE_LONG_OPT_INPUTENCODING), is(-1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_INPUTENCODING, FIXTURE_SHORT_OPT_OUTPUTENCODING), is(-1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_INPUTENCODING, FIXTURE_LONG_OPT_OUTPUTENCODING), is(-1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_OUTPUTENCODING, FIXTURE_SHORT_OPT_HELP), is(-1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_OUTPUTENCODING, FIXTURE_LONG_OPT_HELP), is(-1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_HELP, FIXTURE_SHORT_OPT_VERSION), is(-1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_HELP, FIXTURE_LONG_OPT_VERSION), is(-1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_VERSION, FIXTURE_SHORT_OPT_DEBUG), is(-1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_VERSION, FIXTURE_LONG_OPT_DEBUG), is(-1));
    }

    @Test
    public void compare_greaterShortOptions() {
        assertThat(sut.compare(FIXTURE_SHORT_OPT_INPUTENCODING, FIXTURE_SHORT_OPT_PATHPREFIX), is(1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_OUTPUTENCODING, FIXTURE_SHORT_OPT_INPUTENCODING), is(1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_HELP, FIXTURE_SHORT_OPT_OUTPUTENCODING), is(1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_VERSION, FIXTURE_SHORT_OPT_HELP), is(1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_DEBUG, FIXTURE_SHORT_OPT_VERSION), is(1));
    }

    @Test
    public void compare_greaterLongOptions() {
        assertThat(sut.compare(FIXTURE_LONG_OPT_INPUTENCODING, FIXTURE_LONG_OPT_PATHPREFIX), is(1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_OUTPUTENCODING, FIXTURE_LONG_OPT_INPUTENCODING), is(1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_HELP, FIXTURE_LONG_OPT_OUTPUTENCODING), is(1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_VERSION, FIXTURE_LONG_OPT_HELP), is(1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_DEBUG, FIXTURE_LONG_OPT_VERSION), is(1));
    }

    @Test
    public void compare_greaterLongAndShotOptionsMixed() {
        assertThat(sut.compare(FIXTURE_SHORT_OPT_INPUTENCODING, FIXTURE_LONG_OPT_PATHPREFIX), is(1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_INPUTENCODING, FIXTURE_SHORT_OPT_PATHPREFIX), is(1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_OUTPUTENCODING, FIXTURE_LONG_OPT_INPUTENCODING), is(1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_OUTPUTENCODING, FIXTURE_SHORT_OPT_INPUTENCODING), is(1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_HELP, FIXTURE_LONG_OPT_OUTPUTENCODING), is(1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_HELP, FIXTURE_SHORT_OPT_OUTPUTENCODING), is(1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_VERSION, FIXTURE_LONG_OPT_HELP), is(1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_VERSION, FIXTURE_SHORT_OPT_HELP), is(1));
        assertThat(sut.compare(FIXTURE_SHORT_OPT_DEBUG, FIXTURE_LONG_OPT_VERSION), is(1));
        assertThat(sut.compare(FIXTURE_LONG_OPT_DEBUG, FIXTURE_SHORT_OPT_VERSION), is(1));
    }

}
