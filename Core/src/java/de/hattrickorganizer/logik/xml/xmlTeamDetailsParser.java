// %168276825:de.hattrickorganizer.logik.xml%
/*
 * xmlTeamDetailsParser.java
 *
 * Created on 12. Januar 2004, 10:26
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
public class xmlTeamDetailsParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of xmlTeamDetailsParser
     */
    public xmlTeamDetailsParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param xmlFile TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String fetchRegionID(String xmlFile) {
        try {
            final Document doc = XMLManager.instance().parseString(xmlFile);
            Element ele = null;
            Element root = null;

            if (doc == null) {
                return "-1";
            }

            //Tabelle erstellen
            root = doc.getDocumentElement();

            //Root wechseln
            root = (Element) root.getElementsByTagName("Team").item(0);
            root = (Element) root.getElementsByTagName("Region").item(0);
            ele = (Element) root.getElementsByTagName("RegionID").item(0);
            return XMLManager.instance().getFirstChildNodeValue(ele);
        } catch (Exception ex) {
            HOLogger.instance().log(getClass(),ex);
        }

        return "-1";
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param dateiname TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Hashtable<String, String> parseTeamdetails(String dateiname) {
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
    public final Hashtable<?, ?> parseTeamdetails(java.io.File datei) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(datei);

        return parseDetails(doc);
    }

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////    
    public final Hashtable<?, ?> parseTeamdetailsFromString(String inputStream) {
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
    protected final Hashtable<String, String> parseDetails(Document doc) {
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
            root = (Element) root.getElementsByTagName("User").item(0);
            ele = (Element) root.getElementsByTagName("Loginname").item(0);
            hash.put("Loginname", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LastLoginDate").item(0);
            hash.put("LastLoginDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
            
            // Is this in the xml? - Blaghaid
            ele = (Element) root.getElementsByTagName("Email").item(0);
            hash.put("Email", (XMLManager.instance().getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("ICQ").item(0);
            hash.put("ICQ", (XMLManager.instance().getFirstChildNodeValue(ele)));

            ele = (Element) root.getElementsByTagName("HasSupporter").item(0);
            hash.put("HasSupporter", (XMLManager.instance().getFirstChildNodeValue(ele)));
      
            //Root wechseln
            root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
            ele = (Element) root.getElementsByTagName("TeamID").item(0);
            hash.put("TeamID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("TeamName").item(0);
            hash.put("TeamName", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("HomePage").item(0);
            hash.put("HomePage", (XMLManager.instance().getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("LogoURL").item(0);
            hash.put("LogoURL", (XMLManager.instance().getFirstChildNodeValue(ele)));

            //Root wechseln
            root = (Element) root.getElementsByTagName("League").item(0);
            ele = (Element) root.getElementsByTagName("LeagueID").item(0);
            hash.put("LeagueID", (XMLManager.instance().getFirstChildNodeValue(ele)));

            try {
                //Root wechseln
                root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
                root = (Element) root.getElementsByTagName("LeagueLevelUnit").item(0);
                ele = (Element) root.getElementsByTagName("LeagueLevel").item(0);
                hash.put("LeagueLevel", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("LeagueLevelUnitName").item(0);
                hash.put("LeagueLevelUnitName", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("LeagueLevelUnitID").item(0);
                hash.put("LeagueLevelUnitID", (XMLManager.instance().getFirstChildNodeValue(ele)));
            } catch (Exception ex) {
                HOLogger.instance().log(getClass(),ex);
            }

            try {
                //Root wechseln
                root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
                ele = (Element) root.getElementsByTagName("NumberOfVictories").item(0);
                hash.put("NumberOfVictories", (XMLManager.instance().getFirstChildNodeValue(ele)));
                ele = (Element) root.getElementsByTagName("NumberOfUndefeated").item(0);
                hash.put("NumberOfUndefeated", (XMLManager.instance().getFirstChildNodeValue(ele)));
            } catch (Exception exp) {
                HOLogger.instance().log(getClass(),exp);
            }

            //Root wechseln  //TrainerID adden
            root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
            root = (Element) root.getElementsByTagName("Trainer").item(0);
            ele = (Element) root.getElementsByTagName("PlayerID").item(0);
            hash.put("TrainerID", (XMLManager.instance().getFirstChildNodeValue(ele)));

            //Root wechseln  //StadionName adden
            root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
            root = (Element) root.getElementsByTagName("Arena").item(0);
            ele = (Element) root.getElementsByTagName("ArenaName").item(0);
            hash.put("ArenaName", (XMLManager.instance().getFirstChildNodeValue(ele)));

            //Root wechseln  //RegionId
            root = (Element) doc.getDocumentElement().getElementsByTagName("Team").item(0);
            root = (Element) root.getElementsByTagName("Region").item(0);
            ele = (Element) root.getElementsByTagName("RegionID").item(0);
            hash.put("RegionID", (XMLManager.instance().getFirstChildNodeValue(ele)));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLTeamDetailsParser.parseDetails Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return hash;
    }
}
