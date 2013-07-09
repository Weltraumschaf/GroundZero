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

import de.weltraumschaf.groundzero.model.CheckstyleReport;
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
        final StringReader reader = new StringReader("<checkstyle version=\"5.4\"/>");
        final InputSource input = new InputSource(reader);
        xmlReader.parse(input);
        assertThat(sut.getReport(), is(new CheckstyleReport("5.4")));
    }
}