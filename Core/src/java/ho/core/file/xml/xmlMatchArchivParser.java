// %2347050307:de.hattrickorganizer.logik.xml%
/*
 * xmlMatchArchivParser.java
 *
 * Created on 28. Juli 2004, 15:32
 */
package ho.core.file.xml;


import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.MatchType;
import ho.core.util.HOLogger;
import ho.core.util.Helper;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



/**
 * DOCUMENT ME!
 *
 * @author TheTom
 */
public class xmlMatchArchivParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of xmlMatchArchivParser
     */
    public xmlMatchArchivParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////    
    public final MatchKurzInfo[] parseMatches(String dateiname) {
        Document doc = null;

        doc = XMLManager.parseFile(dateiname);

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

        doc = XMLManager.parseFile(datei);

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

        doc = XMLManager.parseString(input);

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
        return MatchKurzInfo.FINISHED;
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
        final Vector<MatchKurzInfo> liste = new Vector<MatchKurzInfo>();
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
                spiel.setMatchTyp(MatchType.getById(Integer.parseInt(tmp.getFirstChild().getNodeValue())));
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
                tmp = (Element) ele.getElementsByTagName("HomeGoals").item(0);
                spiel.setHeimTore(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
                tmp = (Element) ele.getElementsByTagName("AwayGoals").item(0);
                spiel.setGastTore(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
                spiel.setOrdersGiven(true);
                spiel.setMatchStatus(MatchKurzInfo.FINISHED);

                //In Vector adden
                liste.add(spiel);
            }

            //liste in Array kopieren
            matches = new MatchKurzInfo[liste.size()];
            Helper.copyVector2Array(liste, matches);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchArchivParser.createMatches Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            liste.removeAllElements();
        }

        return matches;
    }
}
