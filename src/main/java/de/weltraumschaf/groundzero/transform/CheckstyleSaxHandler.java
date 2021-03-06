/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <ich@weltraumschaf.de> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 */
package de.weltraumschaf.groundzero.transform;

import com.google.common.collect.Maps;
import de.weltraumschaf.groundzero.model.CheckstyleFile;
import de.weltraumschaf.groundzero.model.CheckstyleReport;
import de.weltraumschaf.groundzero.model.CheckstyleSeverity;
import de.weltraumschaf.groundzero.model.CheckstyleViolation;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX based parser for Checkstyle log files.
 *
 * Format of log:
 * <pre>
 *      &lt;checkstyle version="5.4"&gt;
 *          &lt;file name="..."&gt;
 *              &lt;error line="2" column="22" severity="error" message="Line contains a tab character."
 *                  source="com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck"/&gt;
 *              ...
 *          &lt;/file&gt;
 *          ...
 *      &lt;/checkstyle&gt;
 * </pre>
 *
 * @author Sven Strittmatter <ich@weltraumschaf.de>
 */
class CheckstyleSaxHandler extends DefaultHandler {

    /**
     * The current parsed tag.
     */
    private ReportTags currentTag;
    /**
     * Current parsed report.
     */
    private CheckstyleReport currentReport;
    /**
     * Current parsed file tag data.
     */
    private CheckstyleFile currentFile;

    /**
     * Get the current parsed report.
     *
     * @return may return {@code null}, if nothing parsed
     */
    public CheckstyleReport getReport() {
        return currentReport;
    }

    /**
     * Tries to recognize the {@link ReportTags tag} by it's name and set it as {@link #currentTag}.
     *
     * Throws a {@link IllegalStateException} if a unrecognizable tag name was given.
     *
     * @param tagName must not be {@code null} or empty
     */
    void recognizeTag(final String tagName) {
        Validate.notEmpty(tagName);
        final ReportTags tag = ReportTags.forTagName(tagName);

        if (null == tag) {
            throw new IllegalStateException(String.format("Unrecognized tag <%s>!", tagName));
        } else {
            currentTag = tag;
        }
    }

    /**
     * Whether the {@link #currentTag} is of a particular {@link ReportTags tag}.
     *
     * @param tag must not be {@code null}
     * @return {@code true} if {@link #currentTag} is same as given tag, else {@code false}
     */
    private boolean isCurrentTag(final ReportTags tag) {
        Validate.notNull(tag);
        return currentTag == tag;
    }

    @Override
    public void startElement(final String uri, final String name, final String qName, final Attributes atts) {
        recognizeTag(qName);

        if (isCurrentTag(ReportTags.CHECKSTYLE)) {
            currentReport = new CheckstyleReport(atts.getValue(CheckstyleTagAttribute.VERSION.getName()));
        } else if (isCurrentTag(ReportTags.FILE)) {
            final String fileName = atts.getValue(FileTagAttribute.NAME.getName());
            currentFile = new CheckstyleFile(fileName);
        } else if (isCurrentTag(ReportTags.ERROR)) {
            parseError(atts);
        }
    }

    @Override
    public void endElement(final String uri, final String name, final String qName) {
        recognizeTag(qName);

        if (isCurrentTag(ReportTags.FILE)) {
            currentReport.addFile(currentFile);
        }
    }

    /**
     * Parse a {@literal <error/>} tag.
     *
     * @param atts must not be {@code null}
     */
    private void parseError(final Attributes atts) {
        Validate.notNull(atts);
        final CheckstyleViolation currentViolation = new CheckstyleViolation();
        final String line = atts.getValue(ErrorTagAttribute.LINE.getName());

        try {
            currentViolation.setLine(Integer.valueOf(line));
        } catch (final NumberFormatException ex) { //NOPMD
            // If there is nothing we can format to a number the default of CheckstyleViolation is used.
        } catch (final IllegalArgumentException ex) { //NOPMD
            // If there is nothing we can set the default of CheckstyleViolation is used.
        }

        final String column = atts.getValue(ErrorTagAttribute.COLUMN.getName());

        try {
            currentViolation.setColumn(Integer.valueOf(column));
        } catch (final NumberFormatException ex) { //NOPMD
            // If there is nothing we can format to a number the default of CheckstyleViolation is used.
        } catch (final IllegalArgumentException ex) { //NOPMD
            // If there is nothing we can set the default of CheckstyleViolation is used.
        }

        final String severity = atts.getValue(ErrorTagAttribute.SEVERITY.getName());
        currentViolation.setSeverity(CheckstyleSeverity.valueOf(severity.toUpperCase()));
        currentViolation.setMessage(atts.getValue(ErrorTagAttribute.MESSAGE.getName()));
        currentViolation.setSource(atts.getValue(ErrorTagAttribute.SOURCE.getName()));
        currentFile.addViolation(currentViolation);
    }

    /**
     * Enumerates the report XML tags.
     */
    enum ReportTags {

        /**
         * Tag {@literal <checkstyle>}.
         */
        CHECKSTYLE("checkstyle"),
        /**
         * Tag {@literal <file>}.
         */
        FILE("file"),
        /**
         * Tag {@literal <error>}.
         */
        ERROR("error");
        /**
         * Lookup of string literal to tag.
         */
        private static final Map<String, ReportTags> LOOKUP = Maps.newHashMap();

        static {
            for (final ReportTags tag : ReportTags.values()) {
                LOOKUP.put(tag.getTagName().toLowerCase(), tag);
            }
        }
        /**
         * Literal tag name.
         */
        private final String tagName;

        /**
         * Dedicated constructor.
         *
         * @param tagName the string between the angle brackets. Must not be {@code null} or empty.
         */
        private ReportTags(final String tagName) {
            Validate.notEmpty(tagName);
            this.tagName = tagName;
        }

        /**
         * Get the tag name.
         *
         * @return the tag name
         */
        public String getTagName() {
            return tagName;
        }

        /**
         * Returns the tag enum to a literal tag name.
         *
         * @param tagName literal tag name, part between the angle brackets
         * @return may return null, if tag name is unknown
         */
        static ReportTags forTagName(final String tagName) {
            if (LOOKUP.containsKey(tagName.toLowerCase())) {
                return LOOKUP.get(tagName);
            }

            return null;
        }
    }

    /**
     * Attributes of the {@literal <checkstyle>} tag.
     */
    private enum CheckstyleTagAttribute {

        /**
         * Version attribute of {@link ReportTags#CHECKSTYLE}.
         */
        VERSION("version");
        /**
         * Name of the attribute.
         */
        private final String name;

        /**
         * Dedicated constructor.
         *
         * @param name of the attribute. Must not be {@code null} or empty.
         */
        private CheckstyleTagAttribute(final String name) {
            Validate.notEmpty(name);
            this.name = name;
        }

        /**
         * Get the attribute name.
         *
         * @return lower cased attribute name
         */
        public String getName() {
            return name;
        }
    }

    /**
     * Attributes of the {@literal <file>} tag.
     */
    private enum FileTagAttribute {

        /**
         * Name attribute of {@link ReportTags#FILE}.
         */
        NAME("name");
        /**
         * Name of the attribute.
         */
        private final String name;

        /**
         * Dedicated constructor.
         *
         * @param name of the attribute. Must not be {@code null} or empty.
         */
        private FileTagAttribute(final String name) {
            Validate.notEmpty(name);
            this.name = name;
        }

        /**
         * Get the attribute name.
         *
         * @return lower cased attribute name
         */
        public String getName() {
            return name;
        }
    }

    /**
     * Attributes of the {@literal <error>} tag.
     */
    private enum ErrorTagAttribute {

        /**
         * Line attribute of {@link ReportTags#ERROR}.
         */
        LINE("line"),
        /**
         * Column attribute of {@link ReportTags#ERROR}.
         */
        COLUMN("column"),
        /**
         * Severity attribute of {@link ReportTags#ERROR}.
         */
        SEVERITY("severity"),
        /**
         * Message attribute of {@link ReportTags#ERROR}.
         */
        MESSAGE("message"),
        /**
         * Source attribute of {@link ReportTags#ERROR}.
         */
        SOURCE("source");
        /**
         * Name of the attribute.
         */
        private final String name;

        /**
         * Dedicated constructor.
         *
         * @param name of the attribute. Must not be {@code null} or empty.
         */
        private ErrorTagAttribute(final String name) {
            Validate.notEmpty(name);
            this.name = name;
        }

        /**
         * Get the attribute name.
         *
         * @return lower cased attribute name
         */
        public String getName() {
            return name;
        }
    }
}
