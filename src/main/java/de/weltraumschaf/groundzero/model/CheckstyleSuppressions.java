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
     * Dedicated constructor.
     *
     * @param xmlContent must not be {@code null} or empty
     * @param fileName must not be {@code null} or empty
     */
    public CheckstyleSuppressions(final String xmlContent, final String fileName) {
        super();
        Validate.notEmpty(xmlContent);
        Validate.notEmpty(fileName);
        this.xmlContent = xmlContent;
        this.fileName = fileName;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(xmlContent, fileName);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CheckstyleSuppressions)) {
            return false;
        }

        final CheckstyleSuppressions other = (CheckstyleSuppressions) obj;
        return Objects.equal(xmlContent, other.xmlContent) && Objects.equal(fileName, other.fileName);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("xmlContent", xmlContent)
                .add("fileName", fileName)
                .toString();
    }

    public String getXmlContent() {
        return xmlContent;
    }

    public String getFileName() {
        return fileName;
    }

}
