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

import de.weltraumschaf.groundzero.model.CheckstyleFile;
import de.weltraumschaf.groundzero.model.CheckstyleReport;
import de.weltraumschaf.groundzero.model.CheckstyleSuppressions;
import de.weltraumschaf.groundzero.model.CheckstyleViolation;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link SuppressionGenerator}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SuppressionGeneratorTest {

    // CHECKSTYLE:OFF Must be public for JUnit.
    @Rule public final ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON
    private final SuppressionGenerator sut = new SuppressionGenerator();

    @Test
    public void escapeFileName() {
        assertThat(sut.escapeFileName("foo/bar.java"), is(equalTo("foo/bar\\.java")));
        assertThat(sut.escapeFileName("foo.bar.java"), is(equalTo("foo\\.bar\\.java")));
        assertThat(sut.escapeFileName("foo/bar"), is(equalTo("foo/bar")));
    }

    @Test
    public void generate_throwsExceptionIfNullPassedIn() {
        thrown.expect(NullPointerException.class);
        sut.generate(null);
    }

    @Test
    public void generate_emptyReport() {
        final CheckstyleReport report = new CheckstyleReport("4.3");
        report.setFile("foo.xml");
        final CheckstyleSuppressions suppressions = sut.generate(report);
        assertThat(suppressions.getXmlContent(), is(equalTo(
                "<?xml version=\"1.0\" encoding\"UTF-8\"?>\n"
                + "<!DOCTYPE suppressions PUBLIC "
                + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
                + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">\n"
                + "<suppressions/>\n")));
        assertThat(suppressions.getFileName(), is(equalTo("foo.suppressions.xml")));
    }

    @Test
    public void generateOneFileOneError() {
        final CheckstyleReport report = new CheckstyleReport("4.3");
        report.setFile("foo.xml");
        final CheckstyleFile file = new CheckstyleFile("Foo.java");
        report.addFile(file);
        final CheckstyleViolation violation = new CheckstyleViolation();
        file.addViolation(violation);
        violation.setLine(23);
        violation.setColumn(42);
        violation.setMessage("foo");
        violation.setSource("bar");
        final CheckstyleSuppressions suppressions = sut.generate(report);
        assertThat(suppressions.getXmlContent(), is(equalTo(
                "<?xml version=\"1.0\" encoding\"UTF-8\"?>\n"
                + "<!DOCTYPE suppressions PUBLIC "
                + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
                + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">\n"
                + "<suppressions>\n"
                + "    <suppress files=\"Foo\\.java\" lines=\"23\" columns=\"42\" checks=\"bar\"/>\n"
                + "</suppressions>\n")));
        assertThat(suppressions.getFileName(), is(equalTo("foo.suppressions.xml")));
    }

    @Test
    public void generateOneFileThreeError() {
        final CheckstyleReport report = new CheckstyleReport("4.3");
        report.setFile("foo.xml");
        final CheckstyleFile file = new CheckstyleFile("Foo.java");
        report.addFile(file);
        final CheckstyleViolation violation1 = new CheckstyleViolation();
        file.addViolation(violation1);
        violation1.setLine(23);
        violation1.setColumn(42);
        violation1.setMessage("foo");
        violation1.setSource("bar");
        final CheckstyleViolation violation2 = new CheckstyleViolation();
        violation2.setLine(11);
        violation2.setColumn(12);
        violation2.setMessage("blaa");
        violation2.setSource("foobar");
        file.addViolation(violation2);
        final CheckstyleViolation violation3 = new CheckstyleViolation();
        violation3.setLine(14);
        violation3.setColumn(15);
        violation3.setMessage("blub");
        violation3.setSource("foobar");
        file.addViolation(violation3);
        final CheckstyleSuppressions suppressions = sut.generate(report);

        assertThat(suppressions.getXmlContent(), is(equalTo(
                "<?xml version=\"1.0\" encoding\"UTF-8\"?>\n"
                + "<!DOCTYPE suppressions PUBLIC "
                + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
                + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">\n"
                + "<suppressions>\n"
                + "    <suppress files=\"Foo\\.java\" lines=\"23\" columns=\"42\" checks=\"bar\"/>\n"
                + "    <suppress files=\"Foo\\.java\" lines=\"11\" columns=\"12\" checks=\"foobar\"/>\n"
                + "    <suppress files=\"Foo\\.java\" lines=\"14\" columns=\"15\" checks=\"foobar\"/>\n"
                + "</suppressions>\n")));
        assertThat(suppressions.getFileName(), is(equalTo("foo.suppressions.xml")));
    }

    @Test
    public void generateThreeFileOneError() {
        final CheckstyleViolation violation = new CheckstyleViolation();
        violation.setLine(23);
        violation.setColumn(42);
        violation.setMessage("foo");
        violation.setSource("bar");

        final CheckstyleFile file1 = new CheckstyleFile("Foo.java");
        file1.addViolation(violation);
        final CheckstyleFile file2 = new CheckstyleFile("Bar.java");
        file2.addViolation(violation);
        final CheckstyleFile file3 = new CheckstyleFile("Baz.java");
        file3.addViolation(violation);

        final CheckstyleReport report = new CheckstyleReport("4.3");
        report.setFile("foo.xml");
        report.addFile(file1);
        report.addFile(file2);
        report.addFile(file3);
        final CheckstyleSuppressions suppressions = sut.generate(report);

        assertThat(suppressions.getXmlContent(), is(equalTo(
                "<?xml version=\"1.0\" encoding\"UTF-8\"?>\n"
                + "<!DOCTYPE suppressions PUBLIC "
                + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
                + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">\n"
                + "<suppressions>\n"
                + "    <suppress files=\"Foo\\.java\" lines=\"23\" columns=\"42\" checks=\"bar\"/>\n"
                + "    <suppress files=\"Bar\\.java\" lines=\"23\" columns=\"42\" checks=\"bar\"/>\n"
                + "    <suppress files=\"Baz\\.java\" lines=\"23\" columns=\"42\" checks=\"bar\"/>\n"
                + "</suppressions>\n")));
        assertThat(suppressions.getFileName(), is(equalTo("foo.suppressions.xml")));
    }

    @Test
    public void generateThreeFileThreeError() {
        final CheckstyleViolation violation1 = new CheckstyleViolation();
        violation1.setLine(23);
        violation1.setColumn(42);
        violation1.setMessage("foo");
        violation1.setSource("bar");
        final CheckstyleViolation violation2 = new CheckstyleViolation();
        violation2.setLine(11);
        violation2.setColumn(12);
        violation2.setMessage("blaa");
        violation2.setSource("foobar");
        final CheckstyleViolation violation3 = new CheckstyleViolation();
        violation3.setLine(14);
        violation3.setColumn(15);
        violation3.setMessage("foobar");
        violation3.setSource("blub");

        final CheckstyleFile file1 = new CheckstyleFile("Foo.java");
        file1.addViolation(violation1);
        file1.addViolation(violation2);
        file1.addViolation(violation3);
        final CheckstyleFile file2 = new CheckstyleFile("Bar.java");
        file2.addViolation(violation1);
        file2.addViolation(violation2);
        file2.addViolation(violation3);
        final CheckstyleFile file3 = new CheckstyleFile("Baz.java");
        file3.addViolation(violation1);
        file3.addViolation(violation2);
        file3.addViolation(violation3);

        final CheckstyleReport report = new CheckstyleReport("4.3");
        report.setFile("foo.xml");
        report.addFile(file1);
        report.addFile(file2);
        report.addFile(file3);
        final CheckstyleSuppressions suppressions = sut.generate(report);

        assertThat(suppressions.getXmlContent(), is(equalTo(
                "<?xml version=\"1.0\" encoding\"UTF-8\"?>\n"
                + "<!DOCTYPE suppressions PUBLIC "
                + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
                + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">\n"
                + "<suppressions>\n"
                + "    <suppress files=\"Foo\\.java\" lines=\"23\" columns=\"42\" checks=\"bar\"/>\n"
                + "    <suppress files=\"Foo\\.java\" lines=\"11\" columns=\"12\" checks=\"foobar\"/>\n"
                + "    <suppress files=\"Foo\\.java\" lines=\"14\" columns=\"15\" checks=\"blub\"/>\n"
                + "    <suppress files=\"Bar\\.java\" lines=\"23\" columns=\"42\" checks=\"bar\"/>\n"
                + "    <suppress files=\"Bar\\.java\" lines=\"11\" columns=\"12\" checks=\"foobar\"/>\n"
                + "    <suppress files=\"Bar\\.java\" lines=\"14\" columns=\"15\" checks=\"blub\"/>\n"
                + "    <suppress files=\"Baz\\.java\" lines=\"23\" columns=\"42\" checks=\"bar\"/>\n"
                + "    <suppress files=\"Baz\\.java\" lines=\"11\" columns=\"12\" checks=\"foobar\"/>\n"
                + "    <suppress files=\"Baz\\.java\" lines=\"14\" columns=\"15\" checks=\"blub\"/>\n"
                + "</suppressions>\n")));
        assertThat(suppressions.getFileName(), is(equalTo("foo.suppressions.xml")));
    }

    @Test
    public void extendFileName() {
        assertThat(SuppressionGenerator.extendFileName("foo.xml", ".suppressions"),
                is(equalTo("foo.suppressions.xml")));
    }

}
