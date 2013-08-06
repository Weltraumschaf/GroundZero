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
package de.weltraumschaf.groundzero.model;

import com.google.common.base.Objects;
import org.apache.commons.lang3.Validate;

/**
 * Represents a Checkstyle suppressions configuration.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CheckstyleSuppressions {

    /**
     * The XML suppressions configuration as String.
     */
    private final String xmlContent;
    /**
     * File name of the suppression configuration.
     */
    private final String fileName;
    /**
     * Encoding of the XML content.
     */
    private final String encoding;

    /**
     * Dedicated constructor.
     *
     * The parameter encoding must math the encoding in the preamble of the the parameter xmlContent.
     *
     * @param xmlContent must not be {@code null} or empty
     * @param fileName must not be {@code null} or empty
     * @param encoding must not be {@code null} or empty
     */
    public CheckstyleSuppressions(final String xmlContent, final String fileName, final String encoding) {
        super();
        Validate.notEmpty(xmlContent, "Parameter xmlContent must not be nul or empty!");
        this.xmlContent = xmlContent;
        Validate.notEmpty(fileName, "Parameter fileName must not be nul or empty!");
        this.fileName = fileName;
        Validate.notEmpty(encoding, "Parameter encoding must not be nul or empty!");
        this.encoding = encoding;
    }

    /**
     * Get the formatted suppressions XML as string.
     *
     * @return never {@code null} nor empty
     */
    public String getXmlContent() {
        return xmlContent;
    }

    /**
     * Get the file name of the suppressions file.
     *
     * @return never {@code null} nor empty
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Get the encoding of the the XML content.
     *
     * @return never {@code null} or empty
     */
    public String getEncoding() {
        return encoding;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(xmlContent, fileName, encoding);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CheckstyleSuppressions)) {
            return false;
        }

        final CheckstyleSuppressions other = (CheckstyleSuppressions) obj;
        return Objects.equal(xmlContent, other.xmlContent)
                && Objects.equal(fileName, other.fileName)
                && Objects.equal(encoding, other.encoding);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("xmlContent", xmlContent)
                .add("fileName", fileName)
                .add("encoding", encoding)
                .toString();
    }
}
