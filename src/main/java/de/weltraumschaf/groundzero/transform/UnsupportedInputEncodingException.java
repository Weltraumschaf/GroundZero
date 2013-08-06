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
package de.weltraumschaf.groundzero.transform;

/**
 * Will be thrown, if an unsupported input encoding was given.
 */
public class UnsupportedInputEncodingException extends Exception {

    /**
     * Dedicated constructor.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A
     * <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public UnsupportedInputEncodingException(String message, Throwable cause) {
        super(message, cause);
    }
}
