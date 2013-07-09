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

import java.io.IOException;
import org.apache.commons.lang3.Validate;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Process a single Checkstyle report file.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ReportProcessor {

    /**
     * Process the file given as file name.
     *
     * @param fileName file name of the report, must not be {@code null} or empty
     * @throws SAXException if XML parse errors occurs
     * @throws IOException if file I/O errors occurs
     */
    public void process(final String fileName) throws SAXException, IOException {
        Validate.notEmpty(fileName);
        final CheckStyleSaxHandler handler = new CheckStyleSaxHandler();
        final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        xmlReader.setContentHandler(handler);
        xmlReader.setErrorHandler(handler);
        final InputSource input = new InputSource();
        xmlReader.parse(input);
        handler.getViolations();
    }
}
