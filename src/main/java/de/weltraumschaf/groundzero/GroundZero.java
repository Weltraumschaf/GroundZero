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

import de.weltraumschaf.commons.InvokableAdapter;
import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Sven Strittmatter <ich@weltraumschaf.de>
 */
public final class GroundZero extends InvokableAdapter {

    public GroundZero(String[] args) {
        super(args);
    }

    public static void main(final String[] args) {
        InvokableAdapter.main(new GroundZero(args));
    }

    @Override
    public void execute() throws Exception {
        getIoStreams().println("helo");
    
//        try {
//            final CheckStyleSaxHandler handler = new CheckStyleSaxHandler();
//            final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
//            xmlReader.setContentHandler(handler);
//            xmlReader.setErrorHandler(handler);
//            final InputSource input = new InputSource();
//            xmlReader.parse(input);
//            handler.getViolations();
//        } catch (SAXException ex) {
//            getIoStreams()
//                    .errorln(String.format("ERROR: Excpetion thrown while parsing input XMl! %s", ex.getMessage()));
//            exit(1);
//        } catch (IOException ex) {
//            getIoStreams()
//                    .errorln(String.format("ERROR: Excpetion thrown while reading input file! %s", ex.getMessage()));
//            exit(2);
//        }
    }
}
