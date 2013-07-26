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
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@code FileNameExtender}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FileNameExtenderTest {

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final FileNameExtender sut = new FileNameExtender();

    public FileNameExtenderTest() {
        super();
        sut.setExtension(".suppressions");
    }

    @Test
    public void setExtension_throwExcpetionIfNull() {
        thrown.expect(NullPointerException.class);
        sut.setExtension(null);
    }

    @Test
    public void process_throwExcpetionIfNull() {
        thrown.expect(NullPointerException.class);
        sut.process(null);
    }

    @Test
    public void process_extendFileName() {
        assertThat(sut.process("foo.xml"), is(equalTo("foo.suppressions.xml")));
    }
}
