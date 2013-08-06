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

import de.weltraumschaf.groundzero.opt.OptionsSetup;
import de.weltraumschaf.groundzero.opt.Strategy;
import static de.weltraumschaf.groundzero.opt.Strategy.COMMONS;
import static de.weltraumschaf.groundzero.opt.Strategy.JCOMMANDER;
import de.weltraumschaf.groundzero.opt.commons.CommonsImplementation;
import de.weltraumschaf.groundzero.opt.jcommander.JCommanderImplementation;
import org.apache.commons.lang3.Validate;

/**
 * Factory to create a concrete CLI options parser {@link Strategy strategy}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class OptionsSetups {

    /**
     * Hidden for pure static factory.
     */
    private OptionsSetups() {
        super();
    }

    /**
     * Factory method to create concrete implementation.
     *
     * @param strategy must not be {@code null}
     * @return always new instance
     */
    public static OptionsSetup create(final Strategy strategy) {
        Validate.notNull(strategy, "Strategy must not be null!");

        switch (strategy) {
            case COMMONS:
                return new CommonsImplementation();
            case JCOMMANDER:
                return new JCommanderImplementation();
            default:
                throw new IllegalArgumentException(String.format("Unsupported strategy: '%s'!", strategy));
        }
    }
}
