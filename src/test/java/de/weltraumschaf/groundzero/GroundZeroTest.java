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
import de.weltraumschaf.commons.system.NullExiter;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;
import org.xml.sax.SAXException;

/**
 * Tests for {@link GroundZero}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class GroundZeroTest {

    // CHECKSTYLE:OFF Must be public for JUnit
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final CapturingOutputStream out = new CapturingOutputStream();
    private final CapturingOutputStream err = new CapturingOutputStream();
    private final InputStream in = mock(InputStream.class);
    private final IOStreams io = new IOStreams(in, new PrintStream(out), new PrintStream(err));
    private final ReportProcessor processorSpy = spy(new ReportProcessor());

    public GroundZeroTest() throws SAXException {
        super();
    }

    private GroundZero createSut() throws SAXException {
        return createSut(new String[]{});
    }

    private GroundZero createSut(final String[] args) throws SAXException {
        final GroundZero sut = new GroundZero(args);
        sut.setIoStreams(io);
        sut.setProcessor(processorSpy);
        sut.setExiter(new NullExiter());
        return spy(sut);
    }

    @Test
    public void showHelpMessage_withShortOption() throws Exception {
        final GroundZero sut = createSut(new String[]{"-h"});
        sut.execute();
        assertThat(out.getCapturedOutput(),
                is(equalTo(String.format("Usage: groundzero [-h|--help] [-v|--version] [file1 .. fileN]%n"
                + "%n"
                + "A tool to generate line based suppression files for Checkstyle.%n"
                + "%n"
                + "Parses the Checkstyle report files given as command line argument%n"
                + "and generates suppression XML configuration files from them. The suppression%n"
                + "configurations are saved into files in the current working directory. The file%n"
                + "names are the same as the report filename with the addition of '.suppressions'%n"
                + "before the '.xml' file extension. So the report file 'foobar.xml' will produce%n"
                + "a suppression file named 'foobar.suppressions.xml'.%n"
                + "%n"
                + "  -h | --help     Show this help.%n"
                + "  -v | --version  Show version information%n"
                + "%n"
                + "Project site: http://weltraumschaf.github.io/GroundZero/%n"
                + "Report bugs here: https://github.com/Weltraumschaf/GroundZero/issues%n"))));
        verify(processorSpy, never()).process(anyString());
        verify(sut, never()).showVersionMessage();
    }

    @Test
    public void showHelpMessage_withLongOption() throws Exception {
        final GroundZero sut = createSut(new String[]{"--help"});
        sut.execute();
        verify(processorSpy, never()).process(anyString());
        assertThat(out.getCapturedOutput(),
                is(equalTo(String.format("Usage: groundzero [-h|--help] [-v|--version] [file1 .. fileN]%n"
                + "%n"
                + "A tool to generate line based suppression files for Checkstyle.%n"
                + "%n"
                + "Parses the Checkstyle report files given as command line argument%n"
                + "and generates suppression XML configuration files from them. The suppression%n"
                + "configurations are saved into files in the current working directory. The file%n"
                + "names are the same as the report filename with the addition of '.suppressions'%n"
                + "before the '.xml' file extension. So the report file 'foobar.xml' will produce%n"
                + "a suppression file named 'foobar.suppressions.xml'.%n"
                + "%n"
                + "  -h | --help     Show this help.%n"
                + "  -v | --version  Show version information%n"
                + "%n"
                + "Project site: http://weltraumschaf.github.io/GroundZero/%n"
                + "Report bugs here: https://github.com/Weltraumschaf/GroundZero/issues%n"))));
        verify(sut, never()).showVersionMessage();
    }

    @Test
    public void showVersionMessage_withShortOption() throws Exception {
        final GroundZero sut = createSut(new String[]{"-v"});
        sut.execute();
        verify(sut, times(1)).initializeVersionInformation();
        assertThat(out.getCapturedOutput(), is(equalTo(String.format("Version: TEST%n"))));
        verify(processorSpy, never()).process(anyString());
        verify(sut, never()).showHelpMessage();
    }

    @Test
    public void showVersionMessage_withLongOption() throws Exception {
        final GroundZero sut = createSut(new String[]{"--version"});
        sut.execute();
        verify(sut, times(1)).initializeVersionInformation();
        assertThat(out.getCapturedOutput(), is(equalTo(String.format("Version: TEST%n"))));
        verify(processorSpy, never()).process(anyString());
        verify(sut, never()).showHelpMessage();
    }

    @Test
    public void doNothingIfNoReportFilesGiven() throws Exception {
        final GroundZero sut = createSut();
        sut.execute();
        verify(processorSpy, never()).process(anyString());
        verify(sut, never()).showHelpMessage();
        verify(sut, never()).showVersionMessage();
    }

    @Test @Ignore
    public void processOneFile() throws Exception {
        final String fileName = "/one/file/name";
        final GroundZero sut = createSut(new String[]{fileName});
        sut.execute();
        verify(sut, never()).showHelpMessage();
        verify(sut, never()).showVersionMessage();
        verify(processorSpy, times(1)).process(fileName);
    }

    @Test @Ignore
    public void processMultipleFile() throws Exception {
        final String fileName1 = "/one/file/name1";
        final String fileName2 = "one/file/name2";
        final String fileName3 = "/one/file3";
        final GroundZero sut = createSut(new String[]{fileName1, fileName2, fileName3});
        sut.execute();
        verify(sut, never()).showHelpMessage();
        verify(sut, never()).showVersionMessage();
        verify(processorSpy, times(1)).process(fileName1);
        verify(processorSpy, times(1)).process(fileName2);
        verify(processorSpy, times(1)).process(fileName3);
    }

    @Test
    public void setProcessor_throwsExceptionIfNull() throws SAXException {
        thrown.expect(NullPointerException.class);
        createSut().setProcessor(null);
    }

    @Test
    public void extendFileName() {
        assertThat(GroundZero.extendFileName("foo.xml", ".suppressions"), is(equalTo("foo.suppressions.xml")));
    }
}
