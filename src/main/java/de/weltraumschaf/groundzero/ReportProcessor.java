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

    /**
     * Input encoding of report files.
     */
    private static final String ENCODING = "UTF-8";
    /**
     * Handler to intercept SAX parser events.
     */
    private final CheckstyleSaxHandler handler = new CheckstyleSaxHandler();
    /**
     * XML reader.
     */
    private final XMLReader xmlReader = XMLReaderFactory.createXMLReader();

    /**
     * Dedicated constructor.
     *
     * Set up the XML reader with the SAX handler.
     *
     * @throws SAXException if creation of XML reader fails
     */
    public ReportProcessor() throws SAXException {
        super();
        xmlReader.setContentHandler(handler);
        xmlReader.setErrorHandler(handler);
    }

    /**
     * Process the report given as file name.
     *
     * @param reportFileName file name of the report, must not be {@code null} or empty
     * @throws SAXException if XML parse errors occurs
     * @throws IOException if file I/O errors occurs
     * @return always new instance, never {@code null}
     */
    public CheckstyleReport process(final String reportFileName) throws SAXException, IOException {
        Validate.notEmpty(reportFileName);
        return process(new File(reportFileName));
    }

    /**
     * Process the report given as file.
     *
     * @param input file name of the report, must not be {@code null}
     * @throws SAXException if XML parse errors occurs
     * @throws IOException if file I/O errors occurs
     * @return always new instance, never {@code null}
     */
    public CheckstyleReport process(final File input) throws SAXException, IOException {
        Validate.notNull(input);

        try (final InputStream inputStream = new FileInputStream(input)) {
            final Reader reader = new InputStreamReader(inputStream, ENCODING);
            xmlReader.parse(new InputSource(reader));
        }

        final CheckstyleReport report = handler.getReport();
        report.setFile(input.getAbsolutePath());
        return report;
    }
}
