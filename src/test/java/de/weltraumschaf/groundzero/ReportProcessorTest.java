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

import de.weltraumschaf.groundzero.model.CheckstyleFile;
import de.weltraumschaf.groundzero.model.CheckstyleReport;
import de.weltraumschaf.groundzero.model.CheckstyleSeverity;
import de.weltraumschaf.groundzero.model.CheckstyleViolation;
import java.io.File;
import java.net.URL;
import org.junit.Test;
import org.xml.sax.SAXException;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link ReportProcessor}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ReportProcessorTest {

    private final ReportProcessor sut = new ReportProcessor();

    public ReportProcessorTest() throws SAXException {
        super();
    }

    @Test
    public void process() throws Exception {
        final CheckstyleReport expected = new CheckstyleReport("5.4");
        expected.setFile("foo.xml");
        final CheckstyleFile file = new CheckstyleFile("foo/Bar.java");
        expected.addFile(file);
        final CheckstyleViolation errorOne = new CheckstyleViolation();
        errorOne.setLine(1);
        errorOne.setSeverity(CheckstyleSeverity.ERROR);
        errorOne.setMessage("File length is 1,664 lines (max allowed is 1,500).");
        errorOne.setSource("com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheck");
        file.addViolation(errorOne);
        final CheckstyleViolation errorTwo = new CheckstyleViolation();
        errorTwo.setLine(2);
        errorTwo.setColumn(22);
        errorTwo.setSeverity(CheckstyleSeverity.ERROR);
        errorTwo.setMessage("Line contains a tab character.");
        errorTwo.setSource("com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck");
        file.addViolation(errorTwo);

        final URL testdata = getClass().getResource("/de/weltraumschaf/groundzero/testdata.xml");
        final CheckstyleReport actual = sut.process(new File(testdata.toURI()));
        actual.setFile("foo.xml");
        assertThat(actual, is(equalTo(expected)));
    }

}
