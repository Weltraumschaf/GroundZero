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
import java.io.File;
import org.apache.commons.lang3.Validate;
import de.weltraumschaf.commons.IO;

/**
 * Process a single Checkstyle report file.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ReportProcessor {

    /**
     * Used to generate suppressions configuration.
     */
    private final SuppressionGenerator generator;

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
     */
    public ReportProcessor(final String inputEncoding) {
        super();
        Validate.notEmpty(inputEncoding, "Parameter handler must not be null or empty!");
        this.inputEncoding = inputEncoding;
        generator = new SuppressionGenerator(inputEncoding);
    }

    /**
     * Process the report given as file name.
     *
     * @param reportFileName file name of the report, must not be {@code null} or empty
     * @throws UnsupportedInputEncodingException if unsupported encoding is used
     * @throws XmlInputParseException if SAX handler throws a parse exception
     * @throws XmlInputFileReadException if input file can't be read
     * @throws XmlOutputFileWriteException if output file can't be written
     * @throws CreateXmlReaderException if creation of XML reader fails
     * @return always new instance, never {@code null}
     */
    public CheckstyleReport process(final String reportFileName) throws CreateXmlReaderException,
            XmlInputParseException,
            UnsupportedInputEncodingException,
            XmlInputFileReadException,
            XmlOutputFileWriteException {
        Validate.notEmpty(reportFileName);
        return process(new File(reportFileName));
    }

    /**
     * Process the report given as file.
     *
     * @param input file name of the report, must not be {@code null}
     * @throws UnsupportedInputEncodingException if unsupported encoding is used
     * @throws XmlInputParseException if SAX handler throws a parse exception
     * @throws XmlInputFileReadException if input file can't be read
     * @throws XmlOutputFileWriteException if output file can't be written
     * @throws CreateXmlReaderException if creation of XML reader fails
     * @return always new instance, never {@code null}
     */
    public CheckstyleReport process(final File input) throws CreateXmlReaderException,
            UnsupportedInputEncodingException,
            XmlInputParseException,
            XmlInputFileReadException,
            XmlOutputFileWriteException {
        final CheckstyleReport report = new ReportReader().read(input, inputEncoding);
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
        new SuppressionsWriter().write(suppression);
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

}
