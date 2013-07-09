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

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Sven Strittmatter <ich@weltraumschaf.de>
 */
public class App {

    private final PrintStream out;
    private final PrintStream err;
    private final List<String> args;
    
    public App(final List<String> args, final PrintStream out, final PrintStream err) {
        this.args = args;
        this.out = out;
        this.err = err;
    }
    
    
    public static void main(final String[] args) {
        System.exit(new App(Arrays.asList(args), System.out, System.err).execute());
    }
    
    public final int execute() {
        try {
            final CheckStyleSaxHandler handler = new CheckStyleSaxHandler();
            final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.setContentHandler(handler);
            xmlReader.setErrorHandler(handler);
            final InputSource input = new InputSource();
            xmlReader.parse(input);
            handler.getViolations();
            return 0;
        } catch (SAXException ex) {
            err.format("ERROR: Excpetion thrown while parsing input XMl! %s", ex.getMessage());
            return 1;
        } catch (IOException ex) {
            err.format("ERROR: Excpetion thrown while reading input file! %s", ex.getMessage());
            return 2;
        }
    }
}
