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

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;;

/**
 * Tests for {@link CliOptions}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CliOptionsTest {

    private final CliOptions sut = new CliOptions();

    @Test
    public void defaults() {
        assertThat(sut.isDebug(), is(false));
        assertThat(sut.isHelp(), is(false));
        assertThat(sut.isVersion(), is(false));
        assertThat(sut.hasReportFiles(), is(false));
        assertThat(sut.getReportFiles(), hasSize(0));
        assertThat(sut.getPathPrefix(), is(equalTo("")));
    }

}
