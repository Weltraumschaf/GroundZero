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

import de.weltraumschaf.groundzero.transform.ReportProcessor;
import de.weltraumschaf.commons.CapturingOutputStream;
import de.weltraumschaf.commons.IOStreams;
import de.weltraumschaf.commons.system.NullExiter;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.xml.sax.SAXException;
import de.weltraumschaf.commons.ApplicationException;

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
    private final ReportProcessor processor = mock(ReportProcessor.class);

    public GroundZeroTest() throws SAXException {
        super();
    }

    private GroundZero createSut() {
        return createSut(new String[]{});
    }

    private GroundZero createSut(final String[] args) {
        final GroundZero sut = new GroundZero(args);
        sut.setIoStreams(io);
        sut.setProcessor(processor);
        sut.setExiter(new NullExiter());
        return spy(sut);
    }

    @Test
    public void showHelpMessage_withShortOption() throws Exception {
        final GroundZero sut = createSut(new String[]{"-h"});
        sut.execute();
        verify(sut, times(1)).showHelpMessage();
        verify(processor, never()).process(anyString());
        verify(sut, never()).showVersionMessage();
    }

    @Test
    public void showHelpMessage_withLongOption() throws Exception {
        final GroundZero sut = createSut(new String[]{"--help"});
        sut.execute();
        verify(processor, never()).process(anyString());
        verify(sut, times(1)).showHelpMessage();
        verify(sut, never()).showVersionMessage();
    }

    @Test
    public void showVersionMessage_withShortOption() throws Exception {
        final GroundZero sut = createSut(new String[]{"-v"});
        sut.execute();
        verify(sut, times(1)).initializeVersionInformation();
        verify(sut, times(1)).showVersionMessage();
        assertThat(out.getCapturedOutput(), is(equalTo(String.format("Version: TEST%n"))));
        verify(processor, never()).process(anyString());
        verify(sut, never()).showHelpMessage();
    }

    @Test
    public void showVersionMessage_withLongOption() throws Exception {
        final GroundZero sut = createSut(new String[]{"--version"});
        sut.execute();
        verify(sut, times(1)).initializeVersionInformation();
        verify(sut, times(1)).showVersionMessage();
        assertThat(out.getCapturedOutput(), is(equalTo(String.format("Version: TEST%n"))));
        verify(processor, never()).process(anyString());
        verify(sut, never()).showHelpMessage();
    }

    @Test
    public void doNothingIfNoReportFilesGiven() throws Exception {
        final GroundZero sut = createSut();
        sut.execute();
        verify(processor, never()).process(anyString());
        verify(sut, never()).showHelpMessage();
        verify(sut, never()).showVersionMessage();
    }

    @Test
    public void processOneFile() throws Exception {
        final String fileName = "/one/file/name";
        final GroundZero sut = createSut(new String[]{fileName});
        sut.execute();
        verify(sut, never()).showHelpMessage();
        verify(sut, never()).showVersionMessage();
        verify(processor, times(1)).process(fileName);
    }

    @Test
    public void processMultipleFile() throws Exception {
        final String fileName1 = "/one/file/name1";
        final String fileName2 = "one/file/name2";
        final String fileName3 = "/one/file3";
        final GroundZero sut = createSut(new String[]{fileName1, fileName2, fileName3});
        sut.execute();
        verify(sut, never()).showHelpMessage();
        verify(sut, never()).showVersionMessage();
        verify(processor, times(1)).process(fileName1);
        verify(processor, times(1)).process(fileName2);
        verify(processor, times(1)).process(fileName3);
    }

    @Test
    public void setProcessor_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        createSut().setProcessor(null);
    }

    @Test
    public void execute_throwsExcpetionIfNoProcessorSet() throws Exception {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("The report processor must not be null! "
                + "This is a serious program bug. Please report it at "
                + "https://github.com/Weltraumschaf/GroundZero/issues");
        new GroundZero(new String[] {}).execute();
    }

    @Test
    public void main() throws ApplicationException, Exception {
        final GroundZero spy = createSut();
        GroundZero.main(spy);
        verify(spy, times(1)).setProcessor(Mockito.<ReportProcessor>anyObject());
        verify(spy, times(1)).execute();
    }
}
