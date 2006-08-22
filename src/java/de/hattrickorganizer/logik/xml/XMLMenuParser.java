// %2260631617:de.hattrickorganizer.logik.xml%
/*
 * XMLMenuParser.java
 *
 * Created on 12. März 2004, 07:53
 */
package de.hattrickorganizer.logik.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class XMLMenuParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of XMLMenuParser
     */
    public XMLMenuParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////    
    public final String parseMenu(String dateiname) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(dateiname);

        return parseDetails(doc);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param datei TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String parseMenuFromFile(java.io.File datei) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(datei);

        return parseDetails(doc);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param inputStream TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String parseMenuFromString(String inputStream) {
        Document doc = null;

        doc = XMLManager.instance().parseString(inputStream);

        return parseDetails(doc);
    }

    /////////////////////////////////////////////////////////////////////////////////    
    //Parser Helper private
    ////////////////////////////////////////////////////////////////////////////////  
    /////////////////////////////////////////////////////////////////////////////////    
    //Parser Helper private
    ////////////////////////////////////////////////////////////////////////////////    

    /**
     * erstellt das MAtchlineup Objekt
     *
     * @param doc TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final String parseDetails(Document doc) {
        Element ele = null;
        Element root = null;

        if (doc == null) {
            return "";
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen
            ele = (Element) root.getElementsByTagName("RecommendedURL").item(0);

            String ip = ele.getFirstChild().getNodeValue();

            if (ip.startsWith("http://")) {
                ip = ip.substring(7, ip.length());
            }

            return ip;
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMenuParserparseDetails  Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return "";
    }
}
