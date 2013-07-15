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

package de.weltraumschaf.groundzero;

import de.weltraumschaf.commons.system.ExitCode;
import org.apache.commons.lang3.Validate;

/**
 * Used to signal abnormal exceptional application states.
 *
 * If an exception if this type is thrown should cause program termination with the exit code.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ApplicationException extends RuntimeException {

    /**
     * Exit code used to return to JVM.
     */
    private final ExitCode exitCode;

    /**
     * Dedicated constructor.
     *
     * @param exitCode must not be {@code null}
     * @param message passed to {@link RuntimeException#RuntimeException(java.lang.String, java.lang.Throwable)}
     * @param cause passed to {@link RuntimeException#RuntimeException(java.lang.String, java.lang.Throwable)}
     */
    public ApplicationException(final ExitCode exitCode, final String message, final Throwable cause) {
        super(message, cause);
        Validate.notNull(exitCode);
        this.exitCode = exitCode;
    }

    /**
     * Get program exit code.
     *
     * @return never {@code null}
     */
    public ExitCode getExitCode() {
        return exitCode;
    }

}
