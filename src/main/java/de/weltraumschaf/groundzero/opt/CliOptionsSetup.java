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

import de.weltraumschaf.commons.ApplicationException;
import static de.weltraumschaf.groundzero.opt.CliStrategy.COMMONS;
import static de.weltraumschaf.groundzero.opt.CliStrategy.JCOMMANDER;
import de.weltraumschaf.groundzero.opt.commons.CommonsImplementation;
import de.weltraumschaf.groundzero.opt.jcommander.JCommanderImplementation;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public abstract class CliOptionsSetup {

    public abstract CliOptions parse(String[] args) throws ApplicationException;
    public abstract String help();

    public static CliOptionsSetup create(final CliStrategy strategy) {
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
