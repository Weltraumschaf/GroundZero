/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <ich@weltraumschaf.de> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 */
package de.weltraumschaf.groundzero;

import com.google.common.base.Objects;

/**
 *
 * @author Sven Strittmatter <ich@weltraumschaf.de>
 */
public final class CheckstyleError {

    private int line;
    private int column;
    private CheckstyleSeverity severity;
    private String message;
    private String source;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public CheckstyleSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(CheckstyleSeverity severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(line, column, severity, message, source);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CheckstyleError)) {
            return false;
        }

        final CheckstyleError other = (CheckstyleError) obj;
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