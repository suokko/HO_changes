// %2675225597:de.hattrickorganizer.logik.xml%
/*
 * XMLArenaParser.java
 *
 * Created on 5. Juni 2004, 15:40
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
 * @author thetom
 */
public class XMLArenaParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of XMLArenaParser
     */
    public XMLArenaParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param dateiname TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Hashtable parseArena(String dateiname) {
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
    public final Hashtable parseArena(java.io.File datei) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(datei);

        return parseDetails(doc);
    }

    /////////////////////////////////////////////////////////////////////////////////
    //parse public
    ////////////////////////////////////////////////////////////////////////////////
    public final Hashtable parseArenaFromString(String inputStream) {
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
        Element tmpRoot = null;
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
            root = (Element) root.getElementsByTagName("Arena").item(0);
            ele = (Element) root.getElementsByTagName("ArenaID").item(0);
            hash.put("ArenaID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("ArenaName").item(0);
            hash.put("ArenaName", (XMLManager.instance().getFirstChildNodeValue(ele)));

            tmpRoot = (Element) root.getElementsByTagName("Team").item(0);
            ele = (Element) tmpRoot.getElementsByTagName("TeamID").item(0);
            hash.put("TeamID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("TeamName").item(0);
            hash.put("TeamName", (XMLManager.instance().getFirstChildNodeValue(ele)));

            tmpRoot = (Element) root.getElementsByTagName("League").item(0);
            ele = (Element) tmpRoot.getElementsByTagName("LeagueID").item(0);
            hash.put("LeagueID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("LeagueName").item(0);
            hash.put("LeagueName", (XMLManager.instance().getFirstChildNodeValue(ele)));

            tmpRoot = (Element) root.getElementsByTagName("Region").item(0);
            ele = (Element) tmpRoot.getElementsByTagName("RegionID").item(0);
            hash.put("RegionID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("RegionName").item(0);
            hash.put("RegionName", (XMLManager.instance().getFirstChildNodeValue(ele)));

            tmpRoot = (Element) root.getElementsByTagName("CurrentCapacity").item(0);
            ele = (Element) tmpRoot.getElementsByTagName("RebuiltDate").item(0);

            if (XMLManager.instance().getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("RebuiltDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
            }

            ele = (Element) tmpRoot.getElementsByTagName("Terraces").item(0);
            hash.put("Terraces", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("Basic").item(0);
            hash.put("Basic", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("Roof").item(0);
            hash.put("Roof", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("VIP").item(0);
            hash.put("VIP", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("Total").item(0);
            hash.put("Total", (XMLManager.instance().getFirstChildNodeValue(ele)));

            tmpRoot = (Element) root.getElementsByTagName("ExpandedCapacity").item(0);

            if (XMLManager.instance().getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("isExpanding", "1");
                ele = (Element) tmpRoot.getElementsByTagName("ExpansionDate").item(0);
                hash.put("ExpansionDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) tmpRoot.getElementsByTagName("Terraces").item(0);
                hash.put("ExTerraces", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) tmpRoot.getElementsByTagName("Basic").item(0);
                hash.put("ExBasic", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) tmpRoot.getElementsByTagName("Roof").item(0);
                hash.put("ExRoof", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) tmpRoot.getElementsByTagName("VIP").item(0);
                hash.put("ExVIP", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) tmpRoot.getElementsByTagName("Total").item(0);
                hash.put("ExTotal", (XMLManager.instance().getFirstChildNodeValue(ele)));
            } else {
                hash.put("isExpanding", "0");
                hash.put("ExpansionDate", "0");
                hash.put("ExTerraces", "0");
                hash.put("ExBasic", "0");
                hash.put("ExRoof", "0");
                hash.put("ExVIP", "0");
                hash.put("ExTotal", "0");
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLExonom<Parser.parseDetails Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return hash;
    }
}
