// %2675225597:de.hattrickorganizer.logik.xml%
/*
 * XMLArenaParser.java
 *
 * Created on 5. Juni 2004, 15:40
 */
package ho.core.file.xml;


import ho.core.util.HOLogger;

import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



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
    public final Hashtable<?, ?> parseArena(String dateiname) {
        return parseDetails(XMLManager.parseFile(dateiname));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param datei TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Hashtable<?, ?> parseArena(java.io.File datei) {
        return parseDetails(XMLManager.parseFile(datei));
    }

    /////////////////////////////////////////////////////////////////////////////////
    //parse public
    ////////////////////////////////////////////////////////////////////////////////
    public final Hashtable<?, ?> parseArenaFromString(String inputStream) {
        return parseDetails(XMLManager.parseString(inputStream));
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
    protected final Hashtable<?, ?> parseDetails(Document doc) {
        Element ele = null;
        Element root = null;
        Element tmpRoot = null;
        final ho.core.file.xml.MyHashtable hash = new ho.core.file.xml.MyHashtable();

        if (doc == null) {
            return hash;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen
            //Fetchdate
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            hash.put("FetchedDate", (XMLManager.getFirstChildNodeValue(ele)));

            //Root wechseln
            root = (Element) root.getElementsByTagName("Arena").item(0);
            ele = (Element) root.getElementsByTagName("ArenaID").item(0);
            hash.put("ArenaID", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("ArenaName").item(0);
            hash.put("ArenaName", (XMLManager.getFirstChildNodeValue(ele)));

            tmpRoot = (Element) root.getElementsByTagName("Team").item(0);
            ele = (Element) tmpRoot.getElementsByTagName("TeamID").item(0);
            hash.put("TeamID", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("TeamName").item(0);
            hash.put("TeamName", (XMLManager.getFirstChildNodeValue(ele)));

            tmpRoot = (Element) root.getElementsByTagName("League").item(0);
            ele = (Element) tmpRoot.getElementsByTagName("LeagueID").item(0);
            hash.put("LeagueID", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("LeagueName").item(0);
            hash.put("LeagueName", (XMLManager.getFirstChildNodeValue(ele)));

            tmpRoot = (Element) root.getElementsByTagName("Region").item(0);
            ele = (Element) tmpRoot.getElementsByTagName("RegionID").item(0);
            hash.put("RegionID", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("RegionName").item(0);
            hash.put("RegionName", (XMLManager.getFirstChildNodeValue(ele)));

            tmpRoot = (Element) root.getElementsByTagName("CurrentCapacity").item(0);
            ele = (Element) tmpRoot.getElementsByTagName("RebuiltDate").item(0);

            if (XMLManager.getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("RebuiltDate", (XMLManager.getFirstChildNodeValue(ele)));
            }

            ele = (Element) tmpRoot.getElementsByTagName("Terraces").item(0);
            hash.put("Terraces", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("Basic").item(0);
            hash.put("Basic", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("Roof").item(0);
            hash.put("Roof", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("VIP").item(0);
            hash.put("VIP", (XMLManager.getFirstChildNodeValue(ele)));
            ele = (Element) tmpRoot.getElementsByTagName("Total").item(0);
            hash.put("Total", (XMLManager.getFirstChildNodeValue(ele)));

            tmpRoot = (Element) root.getElementsByTagName("ExpandedCapacity").item(0);

            if (XMLManager.getAttributeValue(ele, "Available").trim().equalsIgnoreCase("true")) {
                hash.put("isExpanding", "1");
                ele = (Element) tmpRoot.getElementsByTagName("ExpansionDate").item(0);
                hash.put("ExpansionDate", (XMLManager.getFirstChildNodeValue(ele)));
                ele = (Element) tmpRoot.getElementsByTagName("Terraces").item(0);
                hash.put("ExTerraces", (XMLManager.getFirstChildNodeValue(ele)));
                ele = (Element) tmpRoot.getElementsByTagName("Basic").item(0);
                hash.put("ExBasic", (XMLManager.getFirstChildNodeValue(ele)));
                ele = (Element) tmpRoot.getElementsByTagName("Roof").item(0);
                hash.put("ExRoof", (XMLManager.getFirstChildNodeValue(ele)));
                ele = (Element) tmpRoot.getElementsByTagName("VIP").item(0);
                hash.put("ExVIP", (XMLManager.getFirstChildNodeValue(ele)));
                ele = (Element) tmpRoot.getElementsByTagName("Total").item(0);
                hash.put("ExTotal", (XMLManager.getFirstChildNodeValue(ele)));
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
