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
    private static final char NL = '\n';
    /**
     * Format string for suppress tag.
     */
    private static final String SUPRESS_TAG_FORMAT =
            "    <suppress files=\"%s\" lines=\"%s\" columns=\"%s\" checks=\"%s\"/>";
    /**
     * XML preamble string.
     */
    private static final String XML_PREAMBLE = "<?xml version=\"1.0\" encoding\"UTF-8\"?>";
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
     * Generates the report string.
     *
     * @param report must not be {@code null}
     * @return never {@code null}.
     */
    public CheckstyleSuppressions generate(final CheckstyleReport report) {
        Validate.notNull(report);
        final StringBuilder buffer = new StringBuilder();
        buffer.append(XML_PREAMBLE).append(NL)
                .append(XML_DTD).append(NL);

        if (report.hasFiles()) {
            buffer.append(TAG_OPEN_SUPPRESSIONS).append(NL);
            generateFileSuppressions(buffer, report.getFiles());
            buffer.append(TAG_CLOSE_SUPPRESSIONS).append(NL);
        } else {
            buffer.append(TAG_EMPTY_SUPPRESSIONS).append(NL);
        }

        final String targetFileName = extendFileName(report.getFileName(), SUPPRESSIONS_FILE_EXTENSION);
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
        buffer.append(String.format(SUPRESS_TAG_FORMAT,
                escapeFileName(fileName), violation.getLine(), violation.getColumn(), violation.getCheck())).append(NL);
    }

    /**
     * Escapes dots in given string with backslash.
     *
     * @param fileName must not be {@code null}
     * @return never {@code null}, maybe empty
     */
    String escapeFileName(final String fileName) {
        Validate.notNull(fileName);
        return fileName.replace(".", "\\.");
    }

    /**
     * Extend a given file name with an extension before the actual file extension.
     *
     * Example:
     * <pre>
     * fileName  == "foo.xml"
     * extension == ".suppressions"
     *           -> "foo.suppressions.xml"
     * </pre>
     *
     * @param fileName must not be {@code null}
     * @param extension must not be {@code null}
     * @return never {@code null} maybe empty
     */
    static String extendFileName(final String fileName, final String extension) {
        Validate.notNull(fileName);
        Validate.notNull(extension);
        final StringBuilder buffer = new StringBuilder(fileName);
        buffer.insert(fileName.lastIndexOf('.'), extension);
        return buffer.toString();
    }
}
