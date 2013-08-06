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
import de.weltraumschaf.groundzero.model.CheckstyleSuppressions;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import org.apache.commons.lang3.Validate;
import de.weltraumschaf.commons.ApplicationException;
import de.weltraumschaf.commons.IO;
import java.io.UnsupportedEncodingException;
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
     * Default dependency.
     */
    private static final CheckstyleSaxHandler DEFAULT_HANDLER = new CheckstyleSaxHandler();
    /**
     * Handler to intercept SAX parser events.
     */
    private final CheckstyleSaxHandler handler;
    /**
     * Used to generate suppressions configuration.
     */
    private final SuppressionGenerator generator;
    /**
     * XML reader.
     */
    private final XMLReader xmlReader;
    /**
     * Used to print to STDOUT.
     */
    private IO io;
    /**
     * Input encoding of report files.
     */
    private String inputEncoding;
    /**
     * Path prefix to strip from reported file names.
     */
    private String pathPrefix = "";

    /**
     * Initializes {@link #handler} with {@link #DEFAULT_HANDLER}.
     *
     * @param inputEncoding must not be {@code null} or empty
     * @throws CreateXmlReaderException if creation of XML reader fails
     */
    public ReportProcessor(final String inputEncoding) throws CreateXmlReaderException {
        this(inputEncoding, DEFAULT_HANDLER);
    }

    /**
     * Dedicated constructor.
     *
     * Set up the XML reader with the SAX handler.
     *
     * @param inputEncoding must not be {@code null} or empty
     * @param handler must not be {@code null}
     * @throws CreateXmlReaderException if creation of XML reader fails
     */
    ReportProcessor(final String inputEncoding, final CheckstyleSaxHandler handler) throws CreateXmlReaderException {
        super();
        Validate.notEmpty(inputEncoding, "Parameter handler must not be null or empty!");
        this.inputEncoding = inputEncoding;
        Validate.notNull(handler, "Parameter handler must not be null!");
        this.handler = handler;

        try {
            xmlReader = XMLReaderFactory.createXMLReader();
        } catch (final SAXException ex) {
            throw new CreateXmlReaderException(ex.getMessage(), ex);
        }

        xmlReader.setContentHandler(handler);
        xmlReader.setErrorHandler(handler);
        generator = new SuppressionGenerator(inputEncoding);
    }

    /**
     * Process the report given as file name.
     *
     * @param reportFileName file name of the report, must not be {@code null} or empty
     * @throws ApplicationException if file I/O or XML parse errors occurs
     * @return always new instance, never {@code null}
     */
    public CheckstyleReport process(final String reportFileName) throws ApplicationException, UnsupportedInputEncodingException, XmlInputParseException, XmlInputFileReadException, XmlOutputFileWriteException {
        Validate.notEmpty(reportFileName);
        return process(new File(reportFileName));
    }

    /**
     * Process the report given as file.
     *
     * @param input file name of the report, must not be {@code null}
     * @throws ApplicationException if file I/O or XML parse errors occurs
     * @return always new instance, never {@code null}
     */
    public CheckstyleReport process(final File input) throws UnsupportedInputEncodingException, XmlInputParseException, XmlInputFileReadException, XmlOutputFileWriteException {
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

        final CheckstyleReport report = handler.getReport();
        report.setFileName(input.getAbsolutePath());
        final CheckstyleSuppressions suppression = generateSuppression(report);
        saveSuppressionFile(suppression);
        return report;
    }

    /**
     * Generate suppressions configuration from report.
     *
     * @param report must not be {@code null}
     * @return never {@code null}
     */
    private CheckstyleSuppressions generateSuppression(final CheckstyleReport report) {
        generator.setPathPrefix(pathPrefix);
        return generator.generate(report);
    }

    /**
     * Save suppressions configuration to file.
     *
     * @param suppression must not be {@code null}
     * @throws XmlOutputFileWriteException if an error occurs while writing suppressions configuration to file
     */
    private void saveSuppressionFile(final CheckstyleSuppressions suppression) throws XmlOutputFileWriteException {
        Validate.notNull(suppression);
        io.println(String.format("Save suppressions configuration %s ...", suppression.getFileName()));

        try (FileOutputStream fos = new FileOutputStream(new File(suppression.getFileName()), false)) {
            try (BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fos, suppression.getEncoding()))) {
                br.write(suppression.getXmlContent());
                br.flush();
            }
            fos.flush();
        } catch (final IOException ex) {
            throw new XmlOutputFileWriteException(
                    String.format("ERROR: Excpetion thrown while writing suppresions file '%s'!",
                    suppression.getFileName()),
                    ex);
        }
    }

    /**
     * Set output encoding of generated suppressions file.
     *
     * @param encoding must not be {@code null} or empty
     */
    public void setInputEncoding(final String encoding) {
        Validate.notEmpty(encoding);
        this.inputEncoding = encoding;
    }

    /**
     * Set IO streams.
     *
     * @param io must not be {@code null}
     */
    public void setIo(final IO io) {
        Validate.notNull(io);
        this.io = io;
    }

    /**
     * Set the path prefix to strip from reported file names.
     *
     * @param pathPrefix must not be {@code null}
     */
    public void setPathPrefix(final String pathPrefix) {
        Validate.notNull(pathPrefix);
        this.pathPrefix = pathPrefix;
    }

    /**
     * Set the output encoding.
     *
     * @param encoding must not be {@code null} or empty
     */
    public void setOutputEncoding(final String encoding) {
        Validate.notEmpty(encoding);
        this.generator.setEncoding(encoding);
    }

    /**
     * Will be thrown, if XML reader can't be created.
     */
    public static final class CreateXmlReaderException extends Exception {

        /**
         * Dedicated constructor.
         *
         * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
         * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A
         * <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
         */
        public CreateXmlReaderException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Will be thrown, if a parse exception occurs during XML parsing.
     */
    public static final class XmlInputParseException extends Exception {

        /**
         * Dedicated constructor.
         *
         * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
         * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A
         * <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
         */
        public XmlInputParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Will be thrown, if XML report file can not be read.
     */
    public static final class XmlInputFileReadException extends Exception {

        /**
         * Dedicated constructor.
         *
         * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
         * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A
         * <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
         */
        public XmlInputFileReadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Will be thrown, if suppression XML file can not be written.
     */
    public static final class XmlOutputFileWriteException extends Exception {

        /**
         * Dedicated constructor.
         *
         * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
         * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A
         * <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
         */
        public XmlOutputFileWriteException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Will be thrown, if an unsupported input encoding was given.
     */
    public static final class UnsupportedInputEncodingException extends Exception {

        /**
         * Dedicated constructor.
         *
         * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
         * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A
         * <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
         */
        public UnsupportedInputEncodingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
