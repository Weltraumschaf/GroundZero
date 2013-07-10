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

import de.weltraumschaf.groundzero.model.CheckstyleReport;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link SupressionGenerator}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SupressionGeneratorTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private final SupressionGenerator sut = new SupressionGenerator();

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
        assertThat(sut.generate(new CheckstyleReport("foo")), is(equalTo(
                "<?xml version=\"1.0\" encoding\"UTF-8\"?>\n"
                + "<!DOCTYPE suppressions PUBLIC "
                + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
                + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">\n"
                + "<suppressions/>\n")));
    }

    @Test
    @Ignore
    public void generateOneFileOneError() {
        assertThat(sut.generate(new CheckstyleReport("foo")), is(equalTo(
                "<?xml version=\"1.0\" encoding\"UTF-8\"?>\n"
                + "<!DOCTYPE suppressions PUBLIC "
                + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
                + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">\n")));
    }

    @Test
    @Ignore
    public void generateOneFileThreeError() {
        assertThat(sut.generate(new CheckstyleReport("foo")), is(equalTo(
                "<?xml version=\"1.0\" encoding\"UTF-8\"?>\n"
                + "<!DOCTYPE suppressions PUBLIC "
                + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
                + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">\n")));
    }

    @Test
    @Ignore
    public void generateThreeFileOneError() {
        assertThat(sut.generate(new CheckstyleReport("foo")), is(equalTo(
                "<?xml version=\"1.0\" encoding\"UTF-8\"?>\n"
                + "<!DOCTYPE suppressions PUBLIC "
                + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
                + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">\n")));
    }

    @Test
    @Ignore
    public void generateThreeFileThreeError() {
        assertThat(sut.generate(new CheckstyleReport("foo")), is(equalTo(
                "<?xml version=\"1.0\" encoding\"UTF-8\"?>\n"
                + "<!DOCTYPE suppressions PUBLIC "
                + "\"-//Puppy Crawl//DTD Suppressions 1.1//EN\" "
                + "\"http://www.puppycrawl.com/dtds/suppressions_1_1.dtd\">\n")));
    }
}
