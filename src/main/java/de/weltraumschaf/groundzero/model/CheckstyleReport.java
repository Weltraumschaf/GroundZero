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
import java.util.Set;

/**
 * Represents the generated Checkstyle reports.
 *
 * A Checkstyle report consists of a set of {@link CheckstyleFile checked files}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CheckstyleReport {

    /**
     * Checked files with violations.
     */
    private final Set<CheckstyleFile> files = Sets.newHashSet();

}
