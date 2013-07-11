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
import de.weltraumschaf.groundzero.model.CheckstyleViolation;
import java.util.Collection;
import org.apache.commons.lang3.Validate;

/**
 * Generates a suppression XML for a {@link CheckstyleReport report}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SupressionGenerator {

    /**
     * Format of a suppression tag.
     */
    private static final char NL = '\n';
    private static final String SUPRESS_TAG_FORMAT =
            "    <suppress files=\"%s\" lines=\"%s\" columns=\"%s\" checks=\"%s\"/>";
    private static final String XML_PREAMBLE = "<?xml version=\"1.0\" encoding\"UTF-8\"?>";
    private static final String XML_DTD = "<!DOCTYPE suppressions PUBLIC "
            + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
            + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">";
    private static final String TAG_OPEN_SUPPRESSIONS = "<suppressions>";
    private static final String TAG_CLOSE_SUPPRESSIONS = "</suppressions>";
    private static final String TAG_EMPTY_SUPPRESSIONS = "<suppressions/>";

    /**
     * Generates the report string.
     *
     * @param report must not be {@code null}
     * @return never {@code null}.
     */
    public String generate(final CheckstyleReport report) {
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

        return buffer.toString();
    }

    private void generateFileSuppressions(final StringBuilder buffer, final Collection<CheckstyleFile> files) {
        for (final CheckstyleFile file : files) {
            generateFileSuppression(buffer, file);
        }
    }

    private void generateFileSuppression(final StringBuilder buffer, final CheckstyleFile file) {
        for (final CheckstyleViolation violation : file.getViolations()) {
            generateErrorSupression(buffer, file.getFileName(), violation);
        }
    }

    private void generateErrorSupression(final StringBuilder buffer, final String fileName,
            final CheckstyleViolation violation) {
        buffer.append(String.format(SUPRESS_TAG_FORMAT,
                escapeFileName(fileName), violation.getCheck(), violation.getLine(), violation.getColumn()));
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
}
