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

import com.google.common.collect.Sets;
import com.google.common.base.Objects;
import java.util.Collection;
import java.util.Set;
import org.apache.commons.lang3.Validate;

/**
 * Represents the generated Checkstyle reports.
 *
 * A Checkstyle report consists of a set of {@link CheckstyleFile checked files}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CheckstyleReport {

    /**
     * Version of Checkstyle which created the report.
     */
    private final String version;

    /**
     * Checked files with violations.
     */
    private final Set<CheckstyleFile> files = Sets.newLinkedHashSet();

    public CheckstyleReport(final String version) {
        super();
        this.version = version;
    }

    /**
     * Add reported file.
     *
     * @param file must no be {@code null}
     */
    public void addFile(final CheckstyleFile file) {
        Validate.notNull(file);
        files.add(file);
    }

    /**
     * Get reported files.
     *
     * @return never {@code nul}, may be empty
     */
    public Collection<CheckstyleFile> getFiles() {
        return files;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(files, version);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CheckstyleReport)) {
            return false;
        }

        final CheckstyleReport other = (CheckstyleReport) obj;
        return Objects.equal(files, other.files) && Objects.equal(version, other.version);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("version", version)
                .add("files", files)
                .toString();
    }

    public boolean hasFiles() {
        return files.size() > 0;
    }

}
