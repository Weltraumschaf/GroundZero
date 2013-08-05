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

package de.weltraumschaf.groundzero.opt;

import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link CommonsOptions}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CliOptionsImplementationTest {

    private final CliOptions sut = new CliOptionsImplementation();

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
        assertThat(sut.hasOnlyDefaultOptions(), is(true));
    }

    @Test
    public void hasOnlyDefaultOptions_pathPrefixIsSet() {
        assertThat(sut.hasOnlyDefaultOptions(), is(true));
        sut.setPathPrefix("foobar");
        assertThat(sut.hasOnlyDefaultOptions(), is(false));
    }

    @Test
    public void hasOnlyDefaultOptions_debugIsSet() {
        assertThat(sut.hasOnlyDefaultOptions(), is(true));
        sut.setDebug(true);
        assertThat(sut.hasOnlyDefaultOptions(), is(false));
    }

    @Test
    public void hasOnlyDefaultOptions_helpIsSet() {
        assertThat(sut.hasOnlyDefaultOptions(), is(true));
        sut.setHelp(true);
        assertThat(sut.hasOnlyDefaultOptions(), is(false));
    }

    @Test
    public void hasOnlyDefaultOptions_versionIsSet() {
        assertThat(sut.hasOnlyDefaultOptions(), is(true));
        sut.setVersion(true);
        assertThat(sut.hasOnlyDefaultOptions(), is(false));
    }

    @Test
    public void hasOnlyDefaultOptions_reportFilesNotEmpty() {
        assertThat(sut.hasOnlyDefaultOptions(), is(true));
        @SuppressWarnings("unchecked") final Collection<String> files = mock(Collection.class);
        when(files.isEmpty()).thenReturn(Boolean.FALSE);
        sut.setReportFiles(files);
        assertThat(sut.hasOnlyDefaultOptions(), is(false));
    }

    @Test
    public void hasOnlyDefaultOptions_inputEncodingIsSet() {
        assertThat(sut.hasOnlyDefaultOptions(), is(true));
        sut.setInputEncoding("foobar");
        assertThat(sut.hasOnlyDefaultOptions(), is(false));
    }

    @Test
    public void hasOnlyDefaultOptions_outputEncodingIsSet() {
        assertThat(sut.hasOnlyDefaultOptions(), is(true));
        sut.setOutputEncoding("foobar");
        assertThat(sut.hasOnlyDefaultOptions(), is(false));
    }

}
