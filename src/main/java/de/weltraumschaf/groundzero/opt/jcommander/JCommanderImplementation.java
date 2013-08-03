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
package de.weltraumschaf.groundzero.opt.jcommander;

import com.beust.jcommander.JCommander;
import de.weltraumschaf.groundzero.opt.CliOptionsSetup;
import de.weltraumschaf.groundzero.opt.CliOptions;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class JCommanderImplementation extends CliOptionsSetup {

    private final CliOptions options = new JCommanderOptions();
    private final JCommander commander = new JCommander(options);

    public JCommanderImplementation() {
        super();
        commander.setProgramName(CliOptions.EXECUTABLE);
    }

    @Override
    public CliOptions parse(final String[] args) {
        commander.parse(args);
        return options;
    }

    @Override
    public String help() {
        final StringBuilder buffer = new StringBuilder();
        commander.usage(buffer);
        final int offset = buffer.indexOf("\n");
        buffer.insert(offset, CliOptions.DESCRIPTION);
        buffer.insert(0, CliOptions.HEADER + "\n");
        buffer.append(CliOptions.FOOTER).append('\n');
        return buffer.toString();
    }
}
