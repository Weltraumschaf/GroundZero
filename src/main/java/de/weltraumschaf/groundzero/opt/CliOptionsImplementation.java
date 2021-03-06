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
import org.apache.commons.lang3.Validate;
import com.beust.jcommander.Parameter;
import com.google.common.collect.Lists;
import static de.weltraumschaf.groundzero.opt.CliOptions.DEFAULT_ENCODING;

/**
 * Holds the given command line arguments.
 *
 * Implementation for Apache Commons CLI and JComander.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CliOptionsImplementation implements CliOptions {

    /**
     * Path prefix to stript of suppressed file names.
     */
    @Parameter(names = {ShortOptions.PFX + ShortOptions.PATH_PREFIX,
        LongOptions.PFX + LongOptions.PATH_PREFIX },
        description = OptionDescriptions.PATH_PREFIX)
    private String pathPrefix = "";
    /**
     * Print debug information or not.
     */
    @Parameter(names = {ShortOptions.PFX + ShortOptions.DEBUG,
        LongOptions.PFX + LongOptions.DEBUG },
        description = OptionDescriptions.DEBUG)
    private boolean debug;
    /**
     * Print help message or not.
     *
     * Ignores all other options.
     */
    @Parameter(names = {ShortOptions.PFX + ShortOptions.HELP,
        LongOptions.PFX + LongOptions.HELP },
        description = OptionDescriptions.HELP, help = true)
    private boolean help;
    /**
     * Show version or net.
     */
    @Parameter(names = {ShortOptions.PFX + ShortOptions.VERSION,
        LongOptions.PFX + LongOptions.VERSION },
        description = OptionDescriptions.VERSION)
    private boolean version;
    /**
     * Collect all not recognized arguments as file pats.
     */
    @Parameter(description = "report1.xml [report2.xml ... reportN.xml]")
    private Collection<String> reportFiles = Lists.newArrayList();
    /**
     * Input file encoding.
     */
    @Parameter(names = {ShortOptions.PFX + ShortOptions.INPUT_ENCODING,
        LongOptions.PFX + LongOptions.INPUT_ENCODING },
        description = OptionDescriptions.INPUT_ENCODING)
    private String inputEncoding = DEFAULT_ENCODING;
    /**
     * Output file encoding.
     */
    @Parameter(names = {ShortOptions.PFX + ShortOptions.OUTPUT_ENCODING,
        LongOptions.PFX + LongOptions.OUTPUT_ENCODING },
        description = OptionDescriptions.OUTPUT_ENCODING)
    private String outputEncoding = DEFAULT_ENCODING;

    /**
     * Set path prefix option.
     *
     * @param pathPrefix must not be {@code null}
     */
    @Override
    public void setPathPrefix(final String pathPrefix) {
        Validate.notNull(pathPrefix);
        this.pathPrefix = pathPrefix;
    }

    /**
     * Get the path prefix option.
     *
     * @return never {@code null} maybe empty
     */
    @Override
    public String getPathPrefix() {
        return pathPrefix;
    }

    /**
     * Set report files.
     *
     * @param files must not be {@code null}
     */
    @Override
    public void setReportFiles(final Collection<String> files) {
        Validate.notNull(files);
        reportFiles = files;
    }

    /**
     * Whether there are report files or not.
     *
     * @return {@code true} if {@link #getReportFiles()} is not empty, else {@code false}
     */
    @Override
    public boolean hasReportFiles() {
        return !reportFiles.isEmpty();
    }

    /**
     * Get report files.
     *
     * @return never {@code null} maybe empty
     */
    @Override
    public Collection<String> getReportFiles() {
        return reportFiles;
    }

    /**
     * Set print debugging.
     *
     * @param onOrOff True enables, false disables (default).
     */
    @Override
    public void setDebug(final boolean onOrOff) {
        this.debug = onOrOff;
    }

    /**
     * Whether debug is enabled or not.
     *
     * @return Return true if debug is enabled, unless false.
     */
    @Override
    public boolean isDebug() {
        return debug;
    }

    /**
     * Set version output option.
     *
     * @param onOrOff True will shows version, false not.
     */
    @Override
    public void setVersion(final boolean onOrOff) {
        this.version = onOrOff;
    }

    /**
     * Whether show version information is enabled or not.
     *
     * @return Return true if show version is enabled, unless false.
     */
    @Override
    public boolean isVersion() {
        return version;
    }

    /**
     * Set show help message.
     *
     * @param onOrOff True enables, false disables (default)
     */
    @Override
    public void setHelp(final boolean onOrOff) {
        this.help = onOrOff;
    }

    /**
     * Whether show help message is enabled or not.
     *
     * @return Return true if show help message is enabled, unless false
     */
    @Override
    public boolean isHelp() {
        return help;
    }

    /**
     * Get the input encoding.
     *
     * @return by default it is {@link #DEFAULT_ENCODING}
     */
    @Override
    public String getInputEncoding() {
        return inputEncoding;
    }

    /**
     * Set the input encoding.
     *
     * @param inputEncoding must not be {@code null} or empty
     */
    @Override
    public void setInputEncoding(final String inputEncoding) {
        Validate.notEmpty(inputEncoding);
        this.inputEncoding = inputEncoding;
    }

    /**
     * Get the output encoding.
     *
     * @return by default it is {@link #DEFAULT_ENCODING}
     */
    @Override
    public String getOutputEncoding() {
        return outputEncoding;
    }

    /**
     * Set the output encoding.
     *
     * @param outputEncoding must not be {@code null} or empty
     */
    @Override
    public void setOutputEncoding(final String outputEncoding) {
        Validate.notEmpty(outputEncoding);
        this.outputEncoding = outputEncoding;
    }

    @Override
    public String toString() {
        return "  pathPrefix:     " + pathPrefix + "\n"
                + "  debug:          " + debug + "\n"
                + "  help:           " + help + "\n"
                + "  version:        " + version + "\n"
                + "  reportFiles:    " + reportFiles + "\n"
                + "  inputEncoding:  " + inputEncoding + "\n"
                + "  outputEncoding: " + outputEncoding;
    }

    @Override
    public boolean hasOnlyDefaultOptions() {
        if (!DEFAULT_PATH_PREFIX.equals(pathPrefix)) {
            return false;
        }

        if (debug != DEFAULT_DEBUG) {
            return false;
        }

        if (help != DEFAULT_HELP) {
            return false;
        }

        if (version != DEFAULT_VERSION) {
            return false;
        }

        if (!DEFAULT_ENCODING.equalsIgnoreCase(inputEncoding)) {
            return false;
        }

        if (!DEFAULT_ENCODING.equalsIgnoreCase(outputEncoding)) {
            return false;
        }

        if (reportFiles.isEmpty()) {
            return true;
        }

        return false;
    }
}
