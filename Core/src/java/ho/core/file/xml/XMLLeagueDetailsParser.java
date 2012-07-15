// %127961774:de.hattrickorganizer.logik.xml%
/*
 * xmlLeagueDetailsParser.java
 *
 * Created on 12. Januar 2004, 14:04
 */
package ho.core.file.xml;


import ho.core.util.HOLogger;

import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class XMLLeagueDetailsParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of xmlLeagueDetailsParser
     */
    public XMLLeagueDetailsParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////    
    public final Hashtable<?, ?> parseLeagueDetailsFromString(String inputStream, String teamID) {
        Document doc = null;

        doc = XMLManager.parseString(inputStream);

        return parseDetails(doc, teamID);
    }

    /////////////////////////////////////////////////////////////////////////////////    
    //Parser Helper private
    ////////////////////////////////////////////////////////////////////////////////       

    /**
     * erstellt das MAtchlineup Objekt
     *
     * @param doc TODO Missing Constructuor Parameter Documentation
     * @param teamID TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final Hashtable<?, ?> parseDetails(Document doc, String teamID) {
        Element ele = null;
        Element root = null;
        final ho.core.file.xml.MyHashtable hash = new ho.core.file.xml.MyHashtable();
        NodeList list = null;

        if (doc == null) {
            return hash;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen            
            //root  =   (Element) root.getElementsByTagName ( "Team" ).item (0);    
            ele = (Element) root.getElementsByTagName("LeagueLevelUnitName").item(0);
            hash.put("LeagueLevelUnitName", (XMLManager.getFirstChildNodeValue(ele)));

            //Einträge adden
            list = root.getElementsByTagName("Team");

            for (int i = 0; (list != null) && (i < list.getLength()); i++) {
                //Team suchen
                if (XMLManager.getFirstChildNodeValue((Element) ((Element) list.item(i)).getElementsByTagName("TeamID")
                                                                 .item(0)).equals(teamID)) {
                    root = (Element) list.item(i);

                    //Land
                    ele = (Element) root.getElementsByTagName("TeamID").item(0);
                    hash.put("TeamID", (XMLManager.getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("Position").item(0);
                    hash.put("Position", (XMLManager.getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("TeamName").item(0);
                    hash.put("TeamName", (XMLManager.getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("Matches").item(0);
                    hash.put("Matches", (XMLManager.getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("GoalsFor").item(0);
                    hash.put("GoalsFor", (XMLManager.getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("GoalsAgainst").item(0);
                    hash.put("GoalsAgainst", (XMLManager.getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("Points").item(0);
                    hash.put("Points", (XMLManager.getFirstChildNodeValue(ele)));

                    //fertig
                    break;
                }
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLTeamDetailsParser.parseDetails Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return hash;
    }
}
