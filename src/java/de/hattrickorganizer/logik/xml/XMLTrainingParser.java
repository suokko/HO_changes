// %3623740893:de.hattrickorganizer.logik.xml%
/*
 * XMLTrainingParser.java
 *
 * Created on 27. Mai 2004, 07:43
 */
package de.hattrickorganizer.logik.xml;

import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class XMLTrainingParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of XMLTrainingParser
     */
    public XMLTrainingParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param dateiname TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Hashtable parseTraining(String dateiname) {
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
    public final Hashtable parseTraining(java.io.File datei) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(datei);

        return parseDetails(doc);
    }

    /////////////////////////////////////////////////////////////////////////////////
    //parse public
    ////////////////////////////////////////////////////////////////////////////////
    public final Hashtable parseTrainingFromString(String inputStream) {
        Document doc = null;

        doc = XMLManager.instance().parseString(inputStream);

        return parseDetails(doc);
    }

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
    protected final Hashtable parseDetails(Document doc) {
        Element ele = null;
        Element root = null;
        final de.hattrickorganizer.model.MyHashtable hash = new de.hattrickorganizer.model.MyHashtable();

        if (doc == null) {
            return hash;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen
            //Fetchdate
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            hash.put("FetchedDate", (XMLManager.instance().getFirstChildNodeValue(ele)));

            //Root wechseln
            root = (Element) root.getElementsByTagName("Team").item(0);
            ele = (Element) root.getElementsByTagName("TeamID").item(0);
            hash.put("TeamID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("TeamName").item(0);
            hash.put("TeamName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("TrainingLevel").item(0);
            hash.put("TrainingLevel", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("NewTrainingLevel ").item(0);

            if (XMLManager.instance().getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("NewTrainingLevel ", (XMLManager.instance().getFirstChildNodeValue(ele)));
            }

            ele = (Element) root.getElementsByTagName("TrainingType").item(0);
            hash.put("TrainingType", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Morale").item(0);

            if (XMLManager.instance().getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("Morale", (XMLManager.instance().getFirstChildNodeValue(ele)));
            }

            ele = (Element) root.getElementsByTagName("SelfConfidence").item(0);

            if (XMLManager.instance().getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("SelfConfidence", (XMLManager.instance().getFirstChildNodeValue(ele)));
            }

            ele = (Element) root.getElementsByTagName("Experience433").item(0);
            hash.put("Experience433", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Experience451").item(0);
            hash.put("Experience451", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Experience352").item(0);
            hash.put("Experience352", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Experience532").item(0);
            hash.put("Experience532", (XMLManager.instance().getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("Experience343").item(0);
            hash.put("Experience343", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Experience541").item(0);
            hash.put("Experience541", (XMLManager.instance().getFirstChildNodeValue(ele)));

            //Root wechseln
            root = (Element) root.getElementsByTagName("Trainer").item(0);
            ele = (Element) root.getElementsByTagName("TrainerID").item(0);
            hash.put("TrainerID", (XMLManager.instance().getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("TrainerName").item(0);
            hash.put("TrainerName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("ArrivalDate").item(0);
            hash.put("ArrivalDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLTrainingParser.parseDetails Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return hash;
    }
}
