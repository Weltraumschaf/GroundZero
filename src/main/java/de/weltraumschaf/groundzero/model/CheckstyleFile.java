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
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import org.apache.commons.lang3.Validate;

/**
 * Represents a source code file checked by Checkstyle which has {@link CheckstyleViolation violations}.
 *
 * A file must belong to a {@link CheckstyleReport report}.
 *
 * @author Sven Strittmatter <ich@weltraumschaf.de>
 */
public final class CheckstyleFile {

    /**
     * Name of checked file.
     */
    private final String fileName;
    /**
     * Set of found violations.
     */
    private final Set<CheckstyleViolation> violations = Sets.newLinkedHashSet();

    /**
     * Dedicated constructor.
     *
     * @param fileName must not be {@code null} or empty.
     */
    public CheckstyleFile(final String fileName) {
        super();
        Validate.notEmpty(fileName);
        this.fileName = fileName;
    }

    /**
     * Get the file name.
     *
     * @return never {@code null} or empty.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Get the violations.
     *
     * @return never {@code nul}, maybe empty collection.
     */
    public Collection<CheckstyleViolation> getViolations() {
        return violations;
    }

    /**
     * Add violation to file.
     * 
     * @param violation must not be {@code null}
     */
    public void addViolation(final CheckstyleViolation violation) {
        Validate.notNull(violation);
        violations.add(violation);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(fileName, violations);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CheckstyleFile)) {
            return false;
        }

        final CheckstyleFile other = (CheckstyleFile) obj;
        return Objects.equal(fileName, other.fileName)
                && Objects.equal(violations, other.violations);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("fileName", fileName)
                .add("violations", violations)
                .toString();
    }

}
