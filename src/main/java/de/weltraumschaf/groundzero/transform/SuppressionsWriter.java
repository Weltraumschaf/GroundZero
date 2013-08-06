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

import de.weltraumschaf.groundzero.model.CheckstyleSuppressions;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.commons.lang3.Validate;

/**
 * Responsible for writing suppressions XML to a file.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class SuppressionsWriter {

    /**
     * Writes the given suppressions to {@link CheckstyleSuppressions#getFileName() file}.
     *
     * @param suppression must not be {@code null}
     * @throws XmlOutputFileWriteException if the file can't be written
     */
    public void write(final CheckstyleSuppressions suppression) throws XmlOutputFileWriteException {
        Validate.notNull(suppression);

        try (FileOutputStream fos = new FileOutputStream(new File(suppression.getFileName()), false)) {
            try (BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fos, suppression.getEncoding()))) {
                br.write(suppression.getXmlContent());
                br.flush();
            }
            fos.flush();
        } catch (final IOException ex) {
            throw new XmlOutputFileWriteException(
                    String.format("ERROR: Excpetion thrown while writing suppresions file '%s'!",
                    suppression.getFileName()),
                    ex);
        }
    }
}
