// %3205245740:de.hattrickorganizer.logik.xml%
/*
 * xmlLeagureFixturesMiniParser.java
 *
 * Created on 12. Januar 2004, 13:38
 */
package de.hattrickorganizer.logik.xml;

import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class xmlWorldDetailsParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of xmlLeagureFixturesMiniParser
     */
    public xmlWorldDetailsParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////    

    /**
     * parst die Welt Details
     *
     * @param inputStream xml Src
     * @param leagueID ID der Liga die von Interesse ist
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Hashtable parseWorldDetailsFromString(String inputStream, String leagueID) {
        Document doc = null;

        doc = XMLManager.instance().parseString(inputStream);

        return parseDetails(doc, leagueID);
    }

    /////////////////////////////////////////////////////////////////////////////////    
    //Parser Helper private
    ////////////////////////////////////////////////////////////////////////////////       

    /**
     * erstellt das MAtchlineup Objekt
     *
     * @param doc TODO Missing Constructuor Parameter Documentation
     * @param leagueID TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final Hashtable parseDetails(Document doc, String leagueID) {
        Element ele = null;
        Element root = null;
        final de.hattrickorganizer.model.MyHashtable hash = new de.hattrickorganizer.model.MyHashtable();
        NodeList list = null;
        String tempLeagueID = null;

        if (doc == null) {
            return hash;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten f�llen            
            root = (Element) root.getElementsByTagName("LeagueList").item(0);

            //Eintr�ge adden
            list = root.getElementsByTagName("League");

            for (int i = 0; (list != null) && (i < list.getLength()); i++) {
                tempLeagueID = XMLManager.instance().getFirstChildNodeValue((Element) ((Element) list
                                                                                       .item(i)).getElementsByTagName("LeagueID")
                                                                                       .item(0));

                //Liga suchen
                if (tempLeagueID.equals(leagueID)) {
                    root = (Element) list.item(i);

                    //Land
                    ele = (Element) root.getElementsByTagName("LeagueID").item(0);
                    hash.put("LeagueID", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("Season").item(0);
                    hash.put("Season", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("MatchRound").item(0);
                    hash.put("MatchRound", (XMLManager.instance().getFirstChildNodeValue(ele)));

                    //Dati
                    ele = (Element) root.getElementsByTagName("TrainingDate").item(0);
                    hash.put("TrainingDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("EconomyDate").item(0);
                    hash.put("EconomyDate", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("SeriesMatchDate").item(0);
                    hash.put("SeriesMatchDate", (XMLManager.instance().getFirstChildNodeValue(ele)));

                    //Country
                    root = (Element) root.getElementsByTagName("Country").item(0);
                    ele = (Element) root.getElementsByTagName("CountryID").item(0);
                    hash.put("CountryID", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("CurrencyName").item(0);
                    hash.put("CurrencyName", (XMLManager.instance().getFirstChildNodeValue(ele)));
                    ele = (Element) root.getElementsByTagName("CurrencyRate").item(0);
                    hash.put("CurrencyRate", (XMLManager.instance().getFirstChildNodeValue(ele)));

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
