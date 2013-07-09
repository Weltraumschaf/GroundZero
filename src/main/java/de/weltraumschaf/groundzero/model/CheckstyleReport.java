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
import java.util.Set;

/**
 * Represents the generated Checkstyle reports.
 *
 * A Checkstyle report consists of a set of {@link CheckstyleFile checked files}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CheckstyleReport {

    private final String version;

    public CheckstyleReport(String version) {
        this.version = version;
    }

    /**
     * Checked files with violations.
     */
    private final Set<CheckstyleFile> files = Sets.newHashSet();

    public void addFile(final CheckstyleFile file) {
        files.add(file);
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
        return Objects.toStringHelper(this).add("files", files).add("version", version).toString();
    }



}
