/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <ich@weltraumschaf.de> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 */
package de.weltraumschaf.groundzero;

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
public class CheckStyleSaxHandler extends DefaultHandler {
    
    public void getViolations() {
        
    }
}
