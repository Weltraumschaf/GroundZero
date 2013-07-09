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

import com.google.common.collect.Lists;
import java.util.List;
import com.google.common.base.Objects;

/**
 *
 * @author Sven Strittmatter <ich@weltraumschaf.de>
 */
public final class CheckstyleFile {

    private final String fileName;
    private final List<CheckstyleError> violations = Lists.newArrayList();

    public CheckstyleFile(final String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public List<CheckstyleError> getViolations() {
        return violations;
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
        return Objects.equal(other.fileName, other.fileName)
                && Objects.equal(other.violations, other.violations);
    }

    
}
