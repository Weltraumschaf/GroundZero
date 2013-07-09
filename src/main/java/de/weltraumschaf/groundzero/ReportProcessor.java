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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
    private static final String ENCODING = "UTF-8";

    /**
     * Handler to intercept SAX parser events.
     */
    private final CheckstyleSaxHandler handler = new CheckstyleSaxHandler();
    /**
     * XML reader.
     */
    private final XMLReader xmlReader = XMLReaderFactory.createXMLReader();

    public ReportProcessor() throws SAXException {
        super();
        xmlReader.setContentHandler(handler);
        xmlReader.setErrorHandler(handler);
    }

    /**
     * Process the file given as file name.
     *
     * @param fileName file name of the report, must not be {@code null} or empty
     * @throws SAXException if XML parse errors occurs
     * @throws IOException if file I/O errors occurs
     */
    public CheckstyleReport process(final String fileName) throws SAXException, IOException {
        Validate.notEmpty(fileName);

        try (final InputStream inputStream = new FileInputStream(new File(fileName))) {
            final Reader reader = new InputStreamReader(inputStream, ENCODING);
            xmlReader.parse(new InputSource(reader));
        }

        return handler.getReport();
    }
}
