// %3623758565:de.hattrickorganizer.logik.xml%
/*
 * XMLSpielplanParser.java
 *
 * Created on 7. Oktober 2003, 13:42
 */
package de.hattrickorganizer.logik.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.hattrickorganizer.model.matchlist.Paarung;
import de.hattrickorganizer.model.matchlist.Spielplan;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.MyHelper;
import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class XMLSpielplanParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of XMLSpielplanParser
     */
    public XMLSpielplanParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////77    

    /**
     * TODO Missing Method Documentation
     *
     * @param dateiname TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Spielplan parseSpielplan(String dateiname) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(dateiname);

        return createSpielplan(doc);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param datei TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Spielplan parseSpielplan(java.io.File datei) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(datei);

        return createSpielplan(doc);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param input TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Spielplan parseSpielplanFromString(String input) {
        Document doc = null;

        doc = XMLManager.instance().parseString(input);

        return createSpielplan(doc);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param ele TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws Exception TODO Missing Method Exception Documentation
     */
    protected final Paarung createPaarung(Element ele) throws Exception {
        Element tmp = null;
        final Paarung spiel = new Paarung();

        tmp = (Element) ele.getElementsByTagName("MatchID").item(0);
        spiel.setMatchId(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("MatchRound").item(0);
        spiel.setSpieltag(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("HomeTeamID").item(0);
        spiel.setHeimId(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("AwayTeamID").item(0);
        spiel.setGastId(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("HomeTeamName").item(0);
        spiel.setHeimName(tmp.getFirstChild().getNodeValue());
        tmp = (Element) ele.getElementsByTagName("AwayTeamName").item(0);
        spiel.setGastName(tmp.getFirstChild().getNodeValue());
        tmp = (Element) ele.getElementsByTagName("MatchDate").item(0);
        spiel.setDatum(tmp.getFirstChild().getNodeValue());

        //Zum Schluss weil nicht immer vorhanden
        if (ele.getElementsByTagName("AwayGoals").getLength() > 0) {
            tmp = (Element) ele.getElementsByTagName("AwayGoals").item(0);
            spiel.setToreGast(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
            tmp = (Element) ele.getElementsByTagName("HomeGoals").item(0);
            spiel.setToreHeim(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        }

        return spiel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param doc TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final Spielplan createSpielplan(Document doc) {
        Spielplan plan = new Spielplan();
        Element ele = null;
        Element root = null;
        NodeList list = null;

        if (doc == null) {
            return plan;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten f�llen            
            ele = (Element) root.getElementsByTagName("LeagueLevelUnitID").item(0);
            plan.setLigaId(Integer.parseInt(ele.getFirstChild().getNodeValue()));

            try {
                ele = (Element) root.getElementsByTagName("LeagueLevelUnitName").item(0);
                plan.setLigaName(ele.getFirstChild().getNodeValue());
            } catch (Exception e) {
                plan.setLigaName("");
            }

            ele = (Element) root.getElementsByTagName("Season").item(0);
            plan.setSaison(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            plan.setFetchDate(MyHelper.parseDate(ele.getFirstChild().getNodeValue()));

            //Eintr�ge adden
            list = root.getElementsByTagName("Match");

            for (int i = 0; (list != null) && (i < list.getLength()); i++) {
                plan.addEintrag(createPaarung((Element) list.item(i)));
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLSpielplanParser.createSpielplan Exception gefangen : " + e);
            HOLogger.instance().log(getClass(),e);
            plan = null;
        }

        return plan;
    }
}
