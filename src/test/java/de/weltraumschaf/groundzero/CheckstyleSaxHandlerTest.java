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
import java.io.IOException;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test for {@link CheckstyleSaxHandler}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CheckstyleSaxHandlerTest {

    private final CheckstyleSaxHandler sut = new CheckstyleSaxHandler();
    private final XMLReader xmlReader = XMLReaderFactory.createXMLReader();

    public CheckstyleSaxHandlerTest() throws SAXException {
        super();
    }

    @Before
    public void setHandlerToReader() {
        xmlReader.setContentHandler(sut);
        xmlReader.setErrorHandler(sut);
    }

    @Test
    public void parseEmptyCheckstyleTag() throws SAXException, IOException {
        final StringReader reader = new StringReader("<checkstyle version=\"5.4\"/>\n");
        xmlReader.parse(new InputSource(reader));
        assertThat(sut.getReport(), is(new CheckstyleReport("5.4")));
    }

    @Test
    public void parseCheckstyleTagWithOneEmptyFile() throws SAXException, IOException {
        final StringReader reader = new StringReader("<checkstyle version=\"5.4\">\n"
                + "    <file name=\"foo/bar.java\"/>\n"
                + "</checkstyle>\n");
        xmlReader.parse(new InputSource(reader));

        final CheckstyleReport expectedReport = new CheckstyleReport("5.4");
        expectedReport.addFile(new CheckstyleFile("foo/bar.java"));
    }

    @Test
    public void parseCheckstyleTagWithOneNonEmptyFile() throws SAXException, IOException {
        final StringReader reader = new StringReader("<checkstyle version=\"5.4\">\n"
                + "    <file name=\"foo/bar.java\">\n"
                + "    </file>\n"
                + "</checkstyle>\n");
        xmlReader.parse(new InputSource(reader));

        final CheckstyleReport expectedReport = new CheckstyleReport("5.4");
        expectedReport.addFile(new CheckstyleFile("foo/bar.java"));

        assertThat(sut.getReport(), is(equalTo(expectedReport)));
    }

    @Test
    public void parseCheckstyleTagWithOneNonEmptyFileAndThreeErrors() throws SAXException, IOException {
        final StringReader reader = new StringReader("<checkstyle version=\"5.4\">\n"
                + "    <file name=\"foo/bar.java\">\n"
                + "        <error line=\"2\" column=\"22\" severity=\"error\" message=\"Line contains a tab character.\" "
                + "source=\"com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck\"/>\n"
                + "        <error line=\"4\" column=\"23\" severity=\"warning\" message=\"Line contains a tab character.\" "
                + "source=\"com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck\"/>\n"
                + "        <error line=\"6\" column=\"24\" severity=\"info\" message=\"Line contains a tab character.\" "
                + "source=\"com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck\"/>\n"
                + "    </file>\n"
                + "</checkstyle>\n");
        xmlReader.parse(new InputSource(reader));

        final CheckstyleReport expectedReport = new CheckstyleReport("5.4");
        final CheckstyleFile expectedFile = new CheckstyleFile("foo/bar.java");
        expectedReport.addFile(expectedFile);
        final CheckstyleViolation expectedViolation1 = new CheckstyleViolation();
        expectedViolation1.setLine(2);
        expectedViolation1.setColumn(22);
        expectedViolation1.setSeverity(CheckstyleSeverity.ERROR);
        expectedViolation1.setMessage("Line contains a tab character.");
        expectedViolation1.setSource("com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck");
        expectedFile.addViolation(expectedViolation1);

        final CheckstyleViolation expectedViolation2 = new CheckstyleViolation();
        expectedViolation2.setLine(4);
        expectedViolation2.setColumn(23);
        expectedViolation2.setSeverity(CheckstyleSeverity.WARNING);
        expectedViolation2.setMessage("Line contains a tab character.");
        expectedViolation2.setSource("com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck");
        expectedFile.addViolation(expectedViolation2);

        final CheckstyleViolation expectedViolation3 = new CheckstyleViolation();
        expectedViolation3.setLine(6);
        expectedViolation3.setColumn(24);
        expectedViolation3.setSeverity(CheckstyleSeverity.INFO);
        expectedViolation3.setMessage("Line contains a tab character.");
        expectedViolation3.setSource("com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck");
        expectedFile.addViolation(expectedViolation3);

        assertThat(sut.getReport(), is(equalTo(expectedReport)));
    }
}