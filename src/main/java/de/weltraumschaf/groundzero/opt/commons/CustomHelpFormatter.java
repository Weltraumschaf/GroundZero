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
package de.weltraumschaf.groundzero.opt.commons;

import java.io.PrintWriter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Custom help formatter.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class CustomHelpFormatter extends HelpFormatter {

    /**
     * Used for new line for all platforms.
     */
    private static final String NL = "\n";

    /**
     * Overrides the default delimiter with an empty string.
     */
    public CustomHelpFormatter() {
        super();
        defaultNewLine = "";
    }

    @Override
    //CHECKSTYLE:OFF
    public void printHelp(final PrintWriter pw, final int width, final String cmdLineSyntax,
            final String header, final Options options, final int leftPad,
            final int descPad, final String footer, final boolean autoUsage) {
        //CHECKSTYLE:ON
        super.printHelp(pw, width, cmdLineSyntax, header + NL + NL, options, leftPad, descPad, footer + NL, autoUsage);
    }

    @Override
    public void printUsage(final PrintWriter pw, final int width, final String app, final Options options) {
        super.printUsage(pw, width, app, options);
        final String fileArgs = " report1.xml [report2.xml ... reportN.xml]" + NL + NL;
        printWrapped(pw, 0, 0, fileArgs);
    }

    @Override
    public void printOptions(PrintWriter pw, int width, Options options, int leftPad, int descPad) {
        super.printOptions(pw, width, options, leftPad, descPad);
        pw.print(NL);
    }


    @Override
    public void printWrapped(final PrintWriter pw, final int width, final int nextLineTabStop, final String text) {
        pw.print(text);
    }

    @Override
    protected String rtrim(final String s) {
        return s;
    }
}
