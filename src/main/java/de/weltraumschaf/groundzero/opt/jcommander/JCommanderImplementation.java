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

import de.weltraumschaf.groundzero.opt.CliOptionsImplementation;
import com.beust.jcommander.JCommander;
import de.weltraumschaf.groundzero.opt.OptionsSetup;
import de.weltraumschaf.groundzero.opt.CliOptions;
import org.apache.commons.lang3.Validate;

/**
 * Implementation based on JCommander.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class JCommanderImplementation extends OptionsSetup {

    /**
     * Used to parse arguments and create help message.
     */
    private final JCommander commander = new JCommander();
    private final CliOptions options = new CliOptionsImplementation();

    /**
     * Sets the executable name for JCommander.
     */
    public JCommanderImplementation() {
        super();
        commander.setProgramName(CliOptions.EXECUTABLE);
        commander.addObject(options);
    }

    @Override
    public CliOptions parse(final String[] args) {
        Validate.notNull(args);

        if (args.length > 0) {
            commander.parse(args);
        }

        return options;
    }
    private final HelpReformatter formatter = new HelpReformatter();

    @Override
    public String help() {
        final StringBuilder usage = new StringBuilder();
        commander.usage(usage);
        formatter.setInput(usage);

        final StringBuilder buffer = new StringBuilder();
        buffer.append(CliOptions.HEADER)
                .append(formatter.getUsage()).append(HelpReformatter.NL).append(HelpReformatter.NL)
                .append(CliOptions.DESCRIPTION)
                .append(formatter.getOptions()).append(HelpReformatter.NL)
                .append(CliOptions.FOOTER).append(HelpReformatter.NL);
        return buffer.toString();
    }
}
