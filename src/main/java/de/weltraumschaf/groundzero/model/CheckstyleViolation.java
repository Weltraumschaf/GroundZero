/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <ich@weltraumschaf.de> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 */
package de.weltraumschaf.groundzero.model;

import com.google.common.base.Objects;
import org.apache.commons.lang3.Validate;

/**
 * Represents a violation found in a file.
 *
 * A violation must belong to a {@link CheckstyleFile file}.
 *
 * @author Sven Strittmatter <ich@weltraumschaf.de>
 */
public final class CheckstyleViolation {

    /**
     * Line of occurrence in source code file.
     */
    private int line;
    /**
     * Column of occurrence in source code file.
     */
    private int column;
    /**
     * Severity of violation.
     */
    private CheckstyleSeverity severity = CheckstyleSeverity.IGNORE;
    /**
     * Human readable message which describes the violation.
     */
    private String message = "";
    /**
     * Full qualified class name of the Checkstyle checker which found the violation.
     */
    private String source = "";

    public int getLine() {
        return line;
    }

    /**
     * Set the line.
     *
     * @param line must not be less than 1
     */
    public void setLine(final int line) {
        Validate.isTrue(line > 0, "Line must not be less than 1! Was %d.", line);
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    /**
     * Set the column.
     *
     * @param column must not be less than 1
     */
    public void setColumn(final int column) {
        Validate.isTrue(column > 0, "Column must not be less than 1! Was %d.", column);
        this.column = column;
    }

    public CheckstyleSeverity getSeverity() {
        return severity;
    }

    /**
     * Set the severity.
     *
     * @param severity must not be {@code null}
     */
    public void setSeverity(final CheckstyleSeverity severity) {
        Validate.notNull(severity);
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Set the  message.
     *
     * @param message must not be {@code null} or empty
     */
    public void setMessage(final String message) {
        Validate.notEmpty(message);
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    /**
     * Set the source.
     *
     * @param source must not be {@code null} or empty
     */
    public void setSource(final String source) {
        Validate.notEmpty(source);
        this.source = source;
    }

    /**
     * Returns the portion after the last dot of {@link #getSource() source}.
     *
     * @return never {@code null}, maybe empty
     */
    public String getCheck() {
        final int pos = source.lastIndexOf(".") + 1;
        return source.substring(pos, source.length());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(line, column, severity, message, source);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CheckstyleViolation)) {
            return false;
        }

        final CheckstyleViolation other = (CheckstyleViolation) obj;
        return Objects.equal(other.line, other.line)
                && Objects.equal(other.column, other.column)
                && Objects.equal(other.severity, other.severity)
                && Objects.equal(other.message, other.message)
                && Objects.equal(other.source, other.source);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("line", line)
                .add("column", column)
                .add("severity", severity)
                .add("message", message)
                .add("source", source)
                .toString();
    }
}