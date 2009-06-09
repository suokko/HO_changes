// %1412607374:de.hattrickorganizer.logik.xml%
/*
 * XMLPlayerParser.java
 *
 * Created on 9. Dezember 2003, 08:56
 */
package de.hattrickorganizer.logik.xml;

import java.sql.Timestamp;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import plugins.ISpieler;

import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class XMLPlayerParser {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of XMLPlayerParser
     */
    public XMLPlayerParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////    
    public final Vector<ISpieler> parsePlayer(String dateiname) {
        Vector<ISpieler> liste = null;
        Document doc = null;

        doc = XMLManager.instance().parseFile(dateiname);
        liste = parseSpieler(doc);

        return liste;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param datei TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Vector<ISpieler> parsePlayer(java.io.File datei) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(datei);

        return parseSpieler(doc);
    }

    /**
     * erzeugt einen Spieler aus dem xml
     *
     * @param ele TODO Missing Constructuor Parameter Documentation
     * @param fetchdate TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    protected final Spieler createPlayer(Element ele, Timestamp fetchdate)
      throws Exception
    {
        Element tmp = null;
        final Spieler player = new Spieler();

        //player.setFetchDate ( fetchdate );
        tmp = (Element) ele.getElementsByTagName("PlayerID").item(0);
        player.setSpielerID(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("PlayerName").item(0);
        player.setName(tmp.getFirstChild().getNodeValue());

        try {
            tmp = (Element) ele.getElementsByTagName("PlayerNumber").item(0);
            player.setTrikotnummer(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        } catch (Exception e) {
            //halt kein SUpporter , egal weiter
        }

        tmp = (Element) ele.getElementsByTagName("TSI").item(0);
        player.setTSI(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("PlayerForm").item(0);
        player.setForm(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("Age").item(0);
        player.setAlter(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("AgeDays").item(0);
        player.setAgeDays(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("Experience").item(0);
        player.setErfahrung(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("Leadership").item(0);
        player.setFuehrung(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("Specialty").item(0);
        player.setSpezialitaet(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("TransferListed").item(0);
        player.setTransferlisted(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("CountryID").item(0);
        player.setNationalitaet(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("Caps").item(0);
        player.setLaenderspiele(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("CapsU20").item(0);
        player.setU20Laenderspiele(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("Cards").item(0);
        player.setGelbeKarten(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("InjuryLevel").item(0);
        player.setVerletzt(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("StaminaSkill").item(0);
        player.setKondition(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("PlaymakerSkill").item(0);
        player.setSpielaufbau(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("ScorerSkill").item(0);
        player.setTorschuss(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("PassingSkill").item(0);
        player.setPasspiel(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("WingerSkill").item(0);
        player.setFluegelspiel(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("DefenderSkill").item(0);
        player.setVerteidigung(Integer.parseInt(tmp.getFirstChild().getNodeValue()));
        tmp = (Element) ele.getElementsByTagName("SetPiecesSkill").item(0);
        player.setStandards(Integer.parseInt(tmp.getFirstChild().getNodeValue()));

        return player;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Helper
    //////////////////////////////////////////////////////////////////////////////      

    /**
     * parsed nen String ins DateFormat
     *
     * @param date TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final Timestamp parseDateFromString(String date) {
        try {
            //Hattrick
            final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                                                                           java.util.Locale.GERMANY);

            return new java.sql.Timestamp(simpleFormat.parse(date).getTime());
        } catch (Exception e) {
            try {
                //Hattrick
                final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd",
                                                                                               java.util.Locale.GERMANY);

                return new java.sql.Timestamp(simpleFormat.parse(date).getTime());
            } catch (Exception ex) {
                HOLogger.instance().log(getClass(),ex);
            }
        }

        return null;
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
    protected final Vector<ISpieler> parseSpieler(Document doc) {
        Element ele = null;
        Element root = null;
        final Vector<ISpieler> liste = new Vector<ISpieler>();
        Timestamp fetchDate = null;
        NodeList list = null;

        if (doc == null) {
            return liste;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            fetchDate = parseDateFromString(ele.getFirstChild().getNodeValue());

            //Root wechseln(Team)
            root = (Element) root.getElementsByTagName("Team").item(0);

            //Root wechseln(Playerlist)
            root = (Element) root.getElementsByTagName("PlayerList").item(0);

            //Player auslesen
            list = root.getElementsByTagName("Player");

            for (int i = 0; (list != null) && (i < list.getLength()); i++) {
                liste.add(createPlayer((Element) list.item(i), fetchDate));
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLPlayerParser.parseSpieler Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            liste.removeAllElements();
        }

        return liste;
    }
}
