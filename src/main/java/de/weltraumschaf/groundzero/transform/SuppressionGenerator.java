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

import de.weltraumschaf.groundzero.filter.FileNameExtender;
import de.weltraumschaf.groundzero.filter.FilterChain;
import de.weltraumschaf.groundzero.filter.PathPrefixRemover;
import de.weltraumschaf.groundzero.filter.StringFilters;
import de.weltraumschaf.groundzero.model.CheckstyleFile;
import de.weltraumschaf.groundzero.model.CheckstyleReport;
import de.weltraumschaf.groundzero.model.CheckstyleSuppressions;
import de.weltraumschaf.groundzero.model.CheckstyleViolation;
import java.util.Collection;
import org.apache.commons.lang3.Validate;

/**
 * Generates a suppression XML for a {@link CheckstyleReport report}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SuppressionGenerator {

    /**
     * Used to extend the report file name to generate the suppressions file name.
     */
    private static final String SUPPRESSIONS_FILE_EXTENSION = ".suppressions";
    /**
     * Format of a suppression tag.
     */
    private static final String NL = String.format("%n");
    /**
     * Format string for suppress tag.
     */
    private static final String SUPRESS_TAG_FORMAT =
            "    <suppress files=\"%s$\" lines=\"%s\" columns=\"%s\" checks=\"%s\"/>";
    /**
     * XML preamble string.
     */
    private static final String XML_PREAMBLE_FORMAT = "<?xml version=\"1.0\" encoding=\"%s\"?>";
    /**
     * DTD for Checkstyle suppression file.
     */
    private static final String XML_DTD = "<!DOCTYPE suppressions PUBLIC "
            + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
            + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">";
    /**
     * Open root tag.
     */
    private static final String TAG_OPEN_SUPPRESSIONS = "<suppressions>";
    /**
     * Close root tag.
     */
    private static final String TAG_CLOSE_SUPPRESSIONS = "</suppressions>";
    /**
     * Empty root tag.
     */
    private static final String TAG_EMPTY_SUPPRESSIONS = "<suppressions/>";
    /**
     * Used to extend file names.
     */
    private final FileNameExtender extender = new FileNameExtender();
    /**
     * Hold as a separate reference so that we can set the path prefix later.
     */
    private final PathPrefixRemover pathPrefixRemover = StringFilters.pathPrefixRemover();
    /**
     * Chain of filters to prepare reported file names.
     */
    private final FilterChain<String> filter = FilterChain.newChain();
    /**
     * Output encoding of suppressions.
     */
    private String encoding;

    /**
     * Dedicated constructor.
     *
     * @param encoding file output encoding, must not be {@code null} or empty
     */
    public SuppressionGenerator(final String encoding) {
        super();
        Validate.notEmpty(encoding);
        this.encoding = encoding;
        filter.add(pathPrefixRemover);
        filter.add(StringFilters.regeExEscaper());
        extender.setExtension(SUPPRESSIONS_FILE_EXTENSION);
    }

    /**
     * Generates the report string.
     *
     * @param report must not be {@code null}
     * @return never {@code null}.
     */
    public CheckstyleSuppressions generate(final CheckstyleReport report) {
        Validate.notNull(report);
        final StringBuilder buffer = new StringBuilder();
        buffer.append(String.format(XML_PREAMBLE_FORMAT, encoding)).append(NL)
                .append(XML_DTD).append(NL);

        if (report.hasFiles()) {
            buffer.append(TAG_OPEN_SUPPRESSIONS).append(NL);
            generateFileSuppressions(buffer, report.getFiles());
            buffer.append(TAG_CLOSE_SUPPRESSIONS).append(NL);
        } else {
            buffer.append(TAG_EMPTY_SUPPRESSIONS).append(NL);
        }

        final String targetFileName = extender.process(report.getFileName());
        return new CheckstyleSuppressions(buffer.toString(), targetFileName);
    }

    /**
     * Generate suppressions for a collection of {@code CheckstyleFile reported files}.
     *
     * @param buffer used to collect generated XML
     * @param files must not be {@code null}
     */
    private void generateFileSuppressions(final StringBuilder buffer, final Collection<CheckstyleFile> files) {
        Validate.notNull(files);

        for (final CheckstyleFile file : files) {
            generateFileSuppression(buffer, file);
        }
    }

    /**
     * Generate suppressions for a {@code CheckstyleFile reported file}.
     *
     * @param buffer used to collect generated XML
     * @param file must not be {@code null}
     */
    private void generateFileSuppression(final StringBuilder buffer, final CheckstyleFile file) {
        Validate.notNull(file);

        for (final CheckstyleViolation violation : file.getViolations()) {
            generateErrorSupression(buffer, file.getFileName(), violation);
        }
    }

    /**
     * Generates the suppress tag for a violation.
     *
     * @param buffer used to collect generated XML
     * @param fileName name of reported file which has the violation
     * @param violation violation of the file
     */
    private void generateErrorSupression(final StringBuilder buffer, final String fileName,
            final CheckstyleViolation violation) {
        buffer.append(
                String.format(SUPRESS_TAG_FORMAT,
                filter.process(fileName),
                violation.getLine(),
                violation.getColumn(),
                violation.getCheck()))
                .append(NL);
    }

    /**
     * Set output encoding of generated suppressions file.
     *
     * @param encoding must not be {@code null} or empty
     */
    public void setEncoding(final String encoding) {
        Validate.notEmpty(encoding);
        this.encoding = encoding;
    }

    /**
     * Get the output encoding.
     *
     * @return never {@code null} or empty
     */
    String getEncoding() {
        return encoding;
    }


    /**
     * Set path prefix to strip from file names.
     *
     * @param pathPrefix file must not be {@code null}
     */
    public void setPathPrefix(final String pathPrefix) {
        Validate.notNull(pathPrefix);
        this.pathPrefixRemover.setPrefix(pathPrefix);
    }
}
