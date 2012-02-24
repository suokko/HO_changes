// %3623740893:de.hattrickorganizer.logik.xml%
/*
 * XMLTrainingParser.java
 *
 * Created on 27. Mai 2004, 07:43
 */
package ho.core.file.xml;


import ho.core.util.HOLogger;

import java.io.File;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



/**
 * Parser for trainig xml data.
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
     * Parse the training from the given xml file name.
     */
    public final Hashtable<?, ?> parseTraining(String filename) {
    	final Document doc = XMLManager.instance().parseFile(filename);
        return parseDetails(doc);
    }

    /**
     * Parse the training from the given xml file.
     */
    public final Hashtable<?, ?> parseTraining(File file) {
    	final Document doc = XMLManager.instance().parseFile(file);
        return parseDetails(doc);
    }

    /**
     * Parse the training from the given xml string.
     */
    public final Hashtable<?, ?> parseTrainingFromString(String inputStream) {
        final Document doc = XMLManager.instance().parseString(inputStream);
        return parseDetails(doc);
    }

	/**
	 * erstellt das MAtchlineup Objekt
	 */
	protected final Hashtable<?, ?> parseDetails(Document doc) {
        Element ele = null;
        Element root = null;
        final MyHashtable hash = new MyHashtable();

        if (doc == null) {
            return hash;
        }

        root = doc.getDocumentElement();

        try {
        	final XMLManager xml = XMLManager.instance();
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            hash.put("FetchedDate", (xml.getFirstChildNodeValue(ele)));

            root = (Element) root.getElementsByTagName("Team").item(0);
            ele = (Element) root.getElementsByTagName("TeamID").item(0);
            hash.put("TeamID", (xml.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("TeamName").item(0);
            hash.put("TeamName", (xml.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("TrainingLevel").item(0);
            hash.put("TrainingLevel", (xml.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("StaminaTrainingPart").item(0);
            hash.put("StaminaTrainingPart", (xml.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("NewTrainingLevel ").item(0);

            if (xml.getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("NewTrainingLevel ", (xml.getFirstChildNodeValue(ele)));
            }

            ele = (Element) root.getElementsByTagName("TrainingType").item(0);
            hash.put("TrainingType", (xml.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Morale").item(0);

            if (xml.getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("Morale", (xml.getFirstChildNodeValue(ele)));
            }

            ele = (Element) root.getElementsByTagName("SelfConfidence").item(0);

            if (xml.getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("SelfConfidence", (xml.getFirstChildNodeValue(ele)));
            }

            ele = (Element) root.getElementsByTagName("Experience433").item(0);
            hash.put("Experience433", (xml.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Experience451").item(0);
            hash.put("Experience451", (xml.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Experience352").item(0);
            hash.put("Experience352", (xml.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Experience532").item(0);
            hash.put("Experience532", (xml.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Experience343").item(0);
            hash.put("Experience343", (xml.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("Experience541").item(0);
            hash.put("Experience541", (xml.getFirstChildNodeValue(ele)));
        
            // new formation experiences since training xml version 1.5
            try {
            	ele = (Element) root.getElementsByTagName("Experience442").item(0);
                hash.put("Experience442", (xml.getFirstChildNodeValue(ele)));
            } catch (Exception e) {
            	HOLogger.instance().log(getClass(), "Err(Experience442): " + e);
            }
            try {
            	ele = (Element) root.getElementsByTagName("Experience523").item(0);
                hash.put("Experience523", (xml.getFirstChildNodeValue(ele)));
            } catch (Exception e) {
            	HOLogger.instance().log(getClass(), "Err(Experience523): " + e);
            }
            try {
            	ele = (Element) root.getElementsByTagName("Experience550").item(0);
                hash.put("Experience550", (xml.getFirstChildNodeValue(ele)));
            } catch (Exception e) {
            	HOLogger.instance().log(getClass(), "Err(Experience550): " + e);
            }
            try {
            	ele = (Element) root.getElementsByTagName("Experience253").item(0);
                hash.put("Experience253", (xml.getFirstChildNodeValue(ele)));
            } catch (Exception e) {
            	HOLogger.instance().log(getClass(), "Err(Experience253): " + e);
            }

            root = (Element) root.getElementsByTagName("Trainer").item(0);
            ele = (Element) root.getElementsByTagName("TrainerID").item(0);
            hash.put("TrainerID", (xml.getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("TrainerName").item(0);
            hash.put("TrainerName", (xml.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("ArrivalDate").item(0);
            hash.put("ArrivalDate", (xml.getFirstChildNodeValue(ele)));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLTrainingParser.parseDetails Exception: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return hash;
    }
}
