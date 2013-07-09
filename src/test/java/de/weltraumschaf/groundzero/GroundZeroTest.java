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

import de.weltraumschaf.commons.CapturingOutputStream;
import de.weltraumschaf.commons.IOStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import static org.mockito.Mockito.*;
import org.xml.sax.SAXException;

/**
 * Tests for {@link GroundZero}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class GroundZeroTest {

    private final CapturingOutputStream out = new CapturingOutputStream();
    private final CapturingOutputStream err = new CapturingOutputStream();
    private final InputStream in = mock(InputStream.class);
    private final IOStreams io = new IOStreams(in, new PrintStream(out), new PrintStream(err));
    private final ReportProcessor processorSpy = spy(new ReportProcessor());

    private GroundZero createSut() {
        return createSut(new String[] {});
    }

    private GroundZero createSut(final String[] args) {
        final GroundZero sut = new GroundZero(args);
        sut.setIoStreams(io);
        sut.setProcessor(processorSpy);
        return spy(sut);
    }

    @Test
    public void showHelpMessage_withShortOption() throws SAXException, IOException {
        final GroundZero sut = createSut(new String[] {"-v"});
        assertThat(out.getCapturedOutput(), is(equalTo("")));
        verify(processorSpy, never()).process(anyString());
        verify(sut, never()).showVersionMessage();
    }

    @Test
    public void showHelpMessage_withLongOption() throws SAXException, IOException {
        verify(processorSpy, never()).process(anyString());
        final GroundZero sut = createSut(new String[] {"--help"});
        assertThat(out.getCapturedOutput(), is(equalTo("")));
        verify(sut, never()).showVersionMessage();
    }

    @Test
    public void showHelpVersion_withShortOption() throws SAXException, IOException {
        final GroundZero sut = createSut(new String[] {"-v"});
        assertThat(out.getCapturedOutput(), is(equalTo("")));
        verify(processorSpy, never()).process(anyString());
        verify(sut, never()).showHelpMessage();
    }

    @Test
    public void showHelpVersion_withLongOption() throws SAXException, IOException {
        final GroundZero sut = createSut(new String[] {"--version"});
        assertThat(out.getCapturedOutput(), is(equalTo("")));
        verify(processorSpy, never()).process(anyString());
        verify(sut, never()).showHelpMessage();
    }

    @Test
    public void doNothingIfNoReportFilesGiven() throws SAXException, IOException {
        final GroundZero sut = createSut();
        verify(processorSpy, never()).process(anyString());
        verify(sut, never()).showHelpMessage();
        verify(sut, never()).showVersionMessage();
    }

    @Test @Ignore
    public void processOneFile() throws SAXException, IOException {
        final ReportProcessor spy = spy(new ReportProcessor());
        final String fileName = "/one/file/name";
        final GroundZero sut = createSut(new String[] {fileName});
        sut.setProcessor(spy);
        verify(spy, times(1)).process(fileName);
    }

    @Test @Ignore
    public void processMultipleFile() throws SAXException, IOException {
        final ReportProcessor spy = spy(new ReportProcessor());
        final String fileName1 = "/one/file/name1";
        final String fileName2 = "one/file/name2";
        final String fileName3 = "/one/file3";
        final GroundZero sut = createSut(new String[] {fileName1, fileName2, fileName3});
        sut.setProcessor(spy);
        verify(spy, times(1)).process(fileName1);
        verify(spy, times(1)).process(fileName2);
        verify(spy, times(1)).process(fileName3);
    }

}