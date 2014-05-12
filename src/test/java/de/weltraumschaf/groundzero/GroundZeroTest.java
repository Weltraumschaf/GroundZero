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

import de.weltraumschaf.commons.application.ApplicationException;
import de.weltraumschaf.commons.application.IOStreams;
import de.weltraumschaf.groundzero.transform.ReportProcessor;
import de.weltraumschaf.commons.system.NullExiter;
import de.weltraumschaf.commons.testing.CapturingPrintStream;
import de.weltraumschaf.groundzero.opt.commons.CommonsImplementation;
import de.weltraumschaf.groundzero.opt.jcommander.JCommanderImplementation;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
    private final CapturingPrintStream out = new CapturingPrintStream();
    private final CapturingPrintStream err = new CapturingPrintStream();
    private final InputStream in = mock(InputStream.class);
    private final IOStreams io = new IOStreams(in, out, err);
    private final ReportProcessor processor = mock(ReportProcessor.class);

    public GroundZeroTest() throws SAXException, UnsupportedEncodingException {
        super();
    }

    private GroundZero createSut() throws ApplicationException {
        return createSut(new String[]{});
    }

    private GroundZero createSut(final String[] args) throws ApplicationException {
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
        verify(sut, never()).initializeVersionInformation();
        verify(sut, never()).showVersionMessage();
    }

    @Test
    public void showHelpMessage_withLongOption() throws Exception {
        final GroundZero sut = createSut(new String[]{"--help"});
        sut.execute();
        verify(processor, never()).process(anyString());
        verify(sut, times(1)).showHelpMessage();
        verify(sut, never()).initializeVersionInformation();
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
    public void inputEncoding_withShortOption() throws Exception {
        final GroundZero sut = createSut(new String[]{"-i", "foo", "file"});
        sut.execute();
        verify(sut, never()).initializeVersionInformation();
        verify(sut, never()).showVersionMessage();
        verify(sut, never()).showHelpMessage();
        verify(processor, times(1)).setInputEncoding("foo");
        verify(processor, times(1)).process("file");
        assertThat(out.getCapturedOutput(),
                is(equalTo(String.format("Process report file ...%nAll 1 reports proccesed.%n"))));
    }

    @Test
    public void doNothingIfNoReportFilesGiven() throws Exception {
        final GroundZero sut = createSut();
        sut.execute();
        verify(processor, never()).process(anyString());
        verify(sut, never()).showHelpMessage();
        verify(sut, never()).showVersionMessage();
        assertThat(out.getCapturedOutput(), is(equalTo(String.format("Nothing to do.%n"))));
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
    public void setProcessor_throwsExceptionIfNull() throws ApplicationException {
        thrown.expect(NullPointerException.class);
        createSut().setProcessor(null);
    }

    @Test
    public void main() throws Exception {
        // XXX Do not log to STDOUT/STDERR
        final GroundZero spy = createSut();
        GroundZero.main(spy);
        verify(spy, times(1)).execute();
    }

    @Test @Ignore
    public void execute_printStackTraceIfDebugIsOn() {

    }

    @Test
    public void examineCommandLineOptions_chooseCommonsStrategy() throws ApplicationException {
        final GroundZero spy = createSut();
        when(spy.getEnv()).thenReturn("commons");
        spy.examineCommandLineOptions();
        assertThat(spy.getOptionsSetup(), is(instanceOf(CommonsImplementation.class)));
    }

    @Test
    public void examineCommandLineOptions_chooseJCommanderStrategy() throws ApplicationException {
        final GroundZero spy = createSut();
        when(spy.getEnv()).thenReturn("jcommander");
        spy.examineCommandLineOptions();
        assertThat(spy.getOptionsSetup(), is(instanceOf(JCommanderImplementation.class)));
    }

}
