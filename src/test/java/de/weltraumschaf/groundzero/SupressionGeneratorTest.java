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
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;

/**
 * Tests for {@link SupressionGenerator}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SupressionGeneratorTest {

    private final SupressionGenerator sut = new SupressionGenerator();

    @Test
    public void escapeFileName() {
        assertThat(sut.escapeFileName("foo/bar.java"), is(equalTo("foo/bar\\.java")));
    }

    @Test @Ignore
    public void generateEmpty() {

    }

    @Test @Ignore
    public void generateOneFileOneError() {

    }

    @Test @Ignore
    public void generateOneFileThreeError() {

    }

    @Test @Ignore
    public void generateThreeFileOneError() {

    }

    @Test @Ignore
    public void generateThreeFileThreeError() {

    }

}
