// %3934770789:de.hattrickorganizer.logik.xml%
/*
 * XMLMAtchesParser.java
 *
 * Created on 23. Oktober 2003, 07:57
 */
package de.hattrickorganizer.logik.xml;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import plugins.IMatchKurzInfo;

import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.MyHelper;
import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class XMLMatchesParser {
    //~ Constructors -------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //KONSTRUKTOR
    ////////////////////////////////////////////////////////////////////////////////     

    /**
     * Creates a new instance of XMLMAtchesParser
     */
    public XMLMatchesParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////
    //test
    //////////////////////////////////////////////////////////////////////////////     

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////    
    public final MatchKurzInfo[] parseMatches(String dateiname) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(dateiname);

        return createMatches(doc);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param datei TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final MatchKurzInfo[] parseMatches(java.io.File datei) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(datei);

        return createMatches(doc);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param input TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final MatchKurzInfo[] parseMatchesFromString(String input) {
        Document doc = null;

        doc = XMLManager.instance().parseString(input);

        return createMatches(doc);
    }

    /**
     * Wertet den StatusString aus und liefert einen INT
     *
     * @param status TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final int getStatus(String status) {
        if (status.equalsIgnoreCase("FINISHED")) {
            return IMatchKurzInfo.FINISHED;
        } else if (status.equalsIgnoreCase("ONGOING")) {
            return IMatchKurzInfo.ONGOING;
        } else if (status.equalsIgnoreCase("UPCOMING")) {
            return IMatchKurzInfo.UPCOMING;
        }

        return -1;
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
    protected final MatchKurzInfo[] createMatches(Document doc) {
        Element ele = null;
        Element root = null;
        Element tmp = null;
        MatchKurzInfo[] matches = new MatchKurzInfo[0];
        MatchKurzInfo spiel = null;
        final Vector liste = new Vector();
        NodeList list = null;

        if (doc == null) {
            return matches;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            list = root.getElementsByTagName("Match");

            for (int i = 0; (list != null) && (i < list.getLength()); i++) {
                spiel = new MatchKurzInfo();
                ele = (Element) list.item(i);

                //Daten füllen
                tmp = (Element) ele.getElementsByTagName("MatchDate").item(0);
                spiel.setMatchDate(tmp.getFirstChild().getNodeValue());
                tmp = (Element) ele.getElementsByTagName("MatchID").item(0);
                spiel.setMatchID(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
                tmp = (Element) ele.getElementsByTagName("MatchType").item(0);
                spiel.setMatchTyp(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
                tmp = (Element) ele.getElementsByTagName("HomeTeam").item(0);
                spiel.setHeimID(Integer.parseInt(((Element) tmp.getElementsByTagName("HomeTeamID")
                                                  .item(0)).getFirstChild().getNodeValue()));
                spiel.setHeimName(((Element) tmp.getElementsByTagName("HomeTeamName").item(0)).getFirstChild()
                                   .getNodeValue());
                tmp = (Element) ele.getElementsByTagName("AwayTeam").item(0);
                spiel.setGastID(Integer.parseInt(((Element) tmp.getElementsByTagName("AwayTeamID")
                                                  .item(0)).getFirstChild().getNodeValue()));
                spiel.setGastName(((Element) tmp.getElementsByTagName("AwayTeamName").item(0)).getFirstChild()
                                   .getNodeValue());
                tmp = (Element) ele.getElementsByTagName("Status").item(0);
                spiel.setMatchStatus(getStatus(tmp.getFirstChild().getNodeValue()));

                if (spiel.getMatchStatus() == IMatchKurzInfo.FINISHED) {
                    tmp = (Element) ele.getElementsByTagName("HomeGoals").item(0);
                    spiel.setHeimTore(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
                    tmp = (Element) ele.getElementsByTagName("AwayGoals").item(0);
                    spiel.setGastTore(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
                } else if (spiel.getMatchStatus() == IMatchKurzInfo.UPCOMING) {
                    tmp = (Element) ele.getElementsByTagName("OrdersGiven").item(0);
                    spiel.setAufstellung(tmp.getFirstChild().getNodeValue().equalsIgnoreCase("TRUE"));
                }

                //In Vector adden
                liste.add(spiel);
            }

            //liste in Array kopieren
            matches = new MatchKurzInfo[liste.size()];
            MyHelper.copyVector2Array(liste, matches);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchesParser.createMatches Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            liste.removeAllElements();
        }

        return matches;
    }
}
