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

package de.weltraumschaf.groundzero.filter;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;;

/**
 * Tests for {@link PathPrefixRemover}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PathPrefixRemoverTest {

    private final PathPrefixRemover sut  = new PathPrefixRemover();

    @Test
    public void process_removeAtBeginning() {
        sut.setPrefix("/foo/bar/baz/");
        assertThat(sut.process("/foo/bar/baz/de/snafu/Foo.java"), is(equalTo("de/snafu/Foo.java")));
    }

    @Test
    public void process_dontRemoveNotAtBeginning() {
        sut.setPrefix("/foo/bar/baz/");
        assertThat(sut.process("/snafu/foo/bar/baz/de/snafu/Foo.java"),
                is(equalTo("/snafu/foo/bar/baz/de/snafu/Foo.java")));
    }

}
