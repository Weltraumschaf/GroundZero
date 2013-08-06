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

package de.weltraumschaf.groundzero.transform;

import de.weltraumschaf.groundzero.model.CheckstyleReport;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.Validate;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Composes all objects needed to parse a report file with SAX.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class ReportReader {

    /**
     * Default dependency.
     */
    private static final CheckstyleSaxHandler DEFAULT_HANDLER = new CheckstyleSaxHandler();
    /**
     * XML reader.
     */
    private final XMLReader xmlReader;
    /**
     * Handler to intercept SAX parser events.
     */
    private final CheckstyleSaxHandler handler;

    /**
     * Initializes {@link #handler} with {@link #DEFAULT_HANDLER}.
     *
     * @throws CreateXmlReaderException if creation of XML reader fails
     */
    ReportReader() throws CreateXmlReaderException {
        this(DEFAULT_HANDLER);
    }

    /**
     * Dedicated constructor.
     *
     * Set up the XML reader with the SAX handler.
     *
     * @param handler must not be {@code null}
     * @throws CreateXmlReaderException if creation of XML reader fails
     */
    ReportReader(final CheckstyleSaxHandler handler) throws CreateXmlReaderException {
        super();
        Validate.notNull(handler, "Parameter handler must not be null!");
        this.handler = handler;

        try {
            xmlReader = XMLReaderFactory.createXMLReader();
        } catch (final SAXException ex) {
            throw new CreateXmlReaderException(ex.getMessage(), ex);
        }

        xmlReader.setContentHandler(handler);
        xmlReader.setErrorHandler(handler);
    }

    /**
     * Read and parses the XML from file and returns a report model object.
     *
     * @param input must not be {@code null}
     * @param inputEncoding must not be {@code null} or empty
     * @return may return {@code null}
     * @throws UnsupportedInputEncodingException if unsupported encoding is used
     * @throws XmlInputParseException if SAX handler throws a parse exception
     * @throws XmlInputFileReadException if input file can't be read
     */
    public CheckstyleReport read(final File input, final String inputEncoding) throws UnsupportedInputEncodingException,
            XmlInputParseException,
            XmlInputFileReadException {
        Validate.notNull(input);

        try (final InputStream inputStream = new FileInputStream(input)) {
            final Reader reader = new InputStreamReader(inputStream, inputEncoding);
            xmlReader.parse(new InputSource(reader));
        } catch (final UnsupportedEncodingException ex) {
            throw new UnsupportedInputEncodingException(
                    String.format("ERROR: Unsuported input encoding '%s'!", inputEncoding),
                    ex);
        } catch (final SAXException ex) {
            throw new XmlInputParseException(
                    String.format("ERROR: Excpetion thrown while parsing input file '%s'! %s",
                    input.getAbsolutePath(),
                    ex.getMessage()),
                    ex);
        } catch (final IOException ex) {
            throw new XmlInputFileReadException(
                    String.format("ERROR: Excpetion thrown while reading input file '%s'! %s",
                    input.getAbsolutePath(),
                    ex.getMessage()),
                    ex);
        }

        return handler.getReport();
    }

}
