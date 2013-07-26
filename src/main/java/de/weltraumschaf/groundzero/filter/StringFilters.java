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

package de.weltraumschaf.groundzero.filter;

/**
 * Factory to get string normalizers.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class StringFilters {

    /**
     * Reusable instance.
     */
    private static final PathPrefixRemover PATH_PREFIX_REMOVER = new PathPrefixRemover();
    /**
     * Reusable instance.
     */
    private static final PathNormalizer PATH_NORMALIZER = new PathNormalizer();
    /**
     * Reusable instance.
     */
    private static final RegeExEscaper REGEX_ESCAPER = new RegeExEscaper();
    /**
     * Reusable instance.
     */
    private static final FileNameExtender FILE_NAME_EXTENDER = new FileNameExtender();

    /**
     * Hidden because pure static factory.
     */
    private StringFilters() {
        super();
    }

    /**
     * Get a path prefix remover.
     *
     * @return always same instance
     */
    public static PathPrefixRemover pathPrefixRemover() {
        return PATH_PREFIX_REMOVER;
    }

    /**
     * Get a path normalizer.
     *
     * @return always same instance
     */
    public static PathNormalizer pathNormalizer() {
        return PATH_NORMALIZER;
    }

    /**
     * Get a regular expression escaper.
     *
     * @return always same instance
     */
    public static RegeExEscaper regeExEscaper() {
        return REGEX_ESCAPER;
    }

    /**
     * Get a file name extender.
     *
     * @return always same instance
     */
    public static FileNameExtender fileNameExtender() {
        return FILE_NAME_EXTENDER;
    }

}
