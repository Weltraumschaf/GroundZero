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

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;;

/**
 * Tests for {@link CommonsOptions}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CommonsOptionsTest {

    private final CommonsOptions sut = new CommonsOptions();

    @Test
    public void defaults() {
        assertThat(sut.isDebug(), is(false));
        assertThat(sut.isHelp(), is(false));
        assertThat(sut.isVersion(), is(false));
        assertThat(sut.hasReportFiles(), is(false));
        assertThat(sut.getReportFiles(), hasSize(0));
        assertThat(sut.getPathPrefix(), is(equalTo("")));
        assertThat(sut.getInputEncoding(), is(equalTo("UTF-8")));
        assertThat(sut.getOutputEncoding(), is(equalTo("UTF-8")));
    }

}
