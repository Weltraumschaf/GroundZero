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

import org.apache.commons.lang3.Validate;

/**
 * Represents Checkstyle warning severity.
 *
 * @link http://checkstyle.sourceforge.net/property_types.html#severity
 *
 * @author sven.strittmatter
 */
public enum CheckstyleSeverity {
    /**
     * Ignored violation.
     */
    IGNORE("ignore"),
    /**
     * Info severity.
     */
    INFO("info"),
    /**
     * Warning severity.
     */
    WARNING("warning"),
    /**
     * Error severity.
     */
    ERROR("error");

    /**
     * Textual representation of severity.
     */
    private final String name;

    /**
     * Initializes the {@link #name textual representation.
     *
     * @param name must not be {@code null} nor empty.
     */
    private CheckstyleSeverity(final String name) {
        Validate.notEmpty(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
