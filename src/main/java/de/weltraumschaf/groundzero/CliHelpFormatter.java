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

import java.io.PrintWriter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Custom help formatter.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class CliHelpFormatter extends HelpFormatter {

    @Override
    public void printUsage(PrintWriter pw, int width, String app, Options options) {
        super.printUsage(pw, width, app, options);
        final String fileArgs = " report1.xml [report2.xml ... reportN.xml]" + getNewLine();
        printWrapped(pw, width, fileArgs.indexOf(' ') + 1, fileArgs);
    }

    @Override
    public void printWrapped(PrintWriter pw, int width, int nextLineTabStop, String text) {
        final StringBuffer sb = new StringBuffer(text.length());
        renderWrappedText(sb, width, nextLineTabStop, text);
        pw.print(sb.toString());
    }

    @Override
    protected String rtrim(final String s) {
        return s;
    }

}
