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
import java.util.Set;
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
    private static final String SUPRESS_FORMAT =
            "    <suppress files=\"%s\" lines=\"%s\" columns=\"%s\" checks=\"%s\"/>\n";

    /**
     * Generates the report string.
     *
     * @param report must not be {@code null}
     * @return never {@code null}.
     */
    public String generate(final CheckstyleReport report) {
        Validate.notNull(report);
        final StringBuilder buffer = new StringBuilder();
        buffer.append("<?xml version=\"1.0\" encoding\"UTF-8\"?>\n")
                .append("<!DOCTYPE suppressions PUBLIC\n"
                + "    \"-//Puppy Crawl//DTD Suppressions 1.1//EN\"\n"
                + "    \"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">\n")
                .append("<suppressions>\n");
        generateFileSuppressions(buffer, report.getFiles());
        buffer.append("</suppressions>\n");
        return buffer.toString();
    }

    private void generateFileSuppressions(final StringBuilder buffer, final Set<CheckstyleFile> files) {
        for (final CheckstyleFile file : files) {
            generateFileSuppression(buffer, file);
        }
    }

    private void generateFileSuppression(final StringBuilder buffer, final CheckstyleFile file) {
        for (final CheckstyleViolation violation : file.getViolations()) {
            generateErrorSupression(buffer, file.getFileName(), violation);
        }
    }

    private void generateErrorSupression(final StringBuilder buffer, final String fileName, final CheckstyleViolation violation) {
        buffer.append(String.format(SUPRESS_FORMAT,
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
