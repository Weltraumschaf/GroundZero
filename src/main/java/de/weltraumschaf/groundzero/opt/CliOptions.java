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
package de.weltraumschaf.groundzero.opt;

import java.util.Collection;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface CliOptions {

    /**
     * Default encoding for whole application.
     */
    String DEFAULT_ENCODING = "UTF-8";
    /**
     * URI to issue tracker.
     */
    String ISSUE_TRACKER = "https://github.com/Weltraumschaf/GroundZero/issues";
    /**
     * Name of the CLI executable.
     */
    String EXECUTABLE = "groundzero";
    String HEADER = "A tool to generate line based suppression files for Checkstyle.\n";
    /**
     * Usage header.
     */
    String DESCRIPTION = "\n\n"
            + "Parses the Checkstyle report files given as command line argument and\n"
            + "generates suppression XML configuration files from them. The suppression\n"
            + "configurations are saved into files in the current working directory.\n"
            + "The file names are the same as the report filename with the addition of\n"
            + "'.suppressions' before the '.xml' file extension. So the report file\n"
            + "'foobar.xml' will produce a suppression file named 'foobar.suppressions.xml'.\n";
    /**
     * Author name and email address.
     */
    String AUTHOR = "Sven Strittmatter <weltraumschaf@googlemail.com>";
    /**
     * Usage footer.
     */
    String FOOTER = String.format("%nWritten 2013 by %s%nReport bugs here %s", AUTHOR, ISSUE_TRACKER);

    /**
     * Get the input encoding.
     *
     * @return by default it is {@link #DEFAULT_ENCODING}
     */
    String getInputEncoding();

    /**
     * Get the output encoding.
     *
     * @return by default it is {@link #DEFAULT_ENCODING}
     */
    String getOutputEncoding();

    /**
     * Get the path prefix option.
     *
     * @return never {@code null} maybe empty
     */
    String getPathPrefix();

    /**
     * Get report files.
     *
     * @return never {@code null} maybe empty
     */
    Collection<String> getReportFiles();

    /**
     * Whether there are report files or not.
     *
     * @return {@code true} if {@link #getReportFiles()} is not empty, else {@code false}
     */
    boolean hasReportFiles();

    /**
     * Whether debug is enabled or not.
     *
     * @return Return true if debug is enabled, unless false.
     */
    boolean isDebug();

    /**
     * Whether show help message is enabled or not.
     *
     * @return Return true if show help message is enabled, unless false
     */
    boolean isHelp();

    /**
     * Whether show version information is enabled or not.
     *
     * @return Return true if show version is enabled, unless false.
     */
    boolean isVersion();

    /**
     * Set print debugging.
     *
     * @param onOrOff True enables, false disables (default).
     */
    void setDebug(final boolean onOrOff);

    /**
     * Set show help message.
     *
     * @param onOrOff True enables, false disables (default)
     */
    void setHelp(final boolean onOrOff);

    /**
     * Set the input encoding.
     *
     * @param inputEncoding must not be {@code null} or empty
     */
    void setInputEncoding(final String inputEncoding);

    /**
     * Set the output encoding.
     *
     * @param outputEncoding must not be {@code null} or empty
     */
    void setOutputEncoding(final String outputEncoding);

    /**
     * Set path prefix option.
     *
     * @param pathPrefix must not be {@code null}
     */
    void setPathPrefix(final String pathPrefix);

    /**
     * Set report files.
     *
     * @param files must not be {@code null}
     */
    void setReportFiles(final Collection<String> files);

    /**
     * Set version output option.
     *
     * @param onOrOff True will shows version, false not.
     */
    void setVersion(final boolean onOrOff);
}
