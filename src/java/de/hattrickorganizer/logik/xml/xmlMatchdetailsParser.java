// %3047011827:de.hattrickorganizer.logik.xml%
/*
 * xmlMatchdetailsParser.java
 *
 * Created on 8. Januar 2004, 14:11
 */
package de.hattrickorganizer.logik.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.hattrickorganizer.model.matches.MatchHighlight;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class xmlMatchdetailsParser {
    //~ Constructors -------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //KONSTRUKTOR
    ////////////////////////////////////////////////////////////////////////////////    

    /**
     * Creates a new instance of xmlMatchdetailsParser
     */
    public xmlMatchdetailsParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param input TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Matchdetails parseMachtdetailsFromString(String input) {
        return createMatchdetails(XMLManager.instance().parseString(input));
    }

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////    
    public final Matchdetails parseMatchdetails(String dateiname) {
        Matchdetails md = null;
        Document doc = null;

        doc = XMLManager.instance().parseFile(dateiname);
        md = createMatchdetails(doc);

        return md;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param datei TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Matchdetails parseMatchdetails(java.io.File datei) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(datei);

        return createMatchdetails(doc);
    }

    /////////////////////////////////////////////////////////////////////////////////    
    //Parser Helper private
    ////////////////////////////////////////////////////////////////////////////////    
    protected final Matchdetails createMatchdetails(Document doc) {
        Matchdetails md = null;

        if (doc != null) {
            md = new Matchdetails();

            readGeneral(doc, md);
            readArena(doc, md);
            readGuestTeam(doc, md);
            readHomeTeam(doc, md);

            //read matchhighlights
            //            readGoals( doc, md );
            //            readCards( doc, md );
        }

        return md;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param doc TODO Missing Method Parameter Documentation
     * @param md TODO Missing Method Parameter Documentation
     */
    protected final void readArena(Document doc, Matchdetails md) {
        Element ele = null;
        Element root = null;

        root = doc.getDocumentElement();

        try {
            //Daten füllen            
            //MatchData
            root = (Element) root.getElementsByTagName("Match").item(0);
            root = (Element) root.getElementsByTagName("Arena").item(0);
            ele = (Element) root.getElementsByTagName("ArenaID").item(0);
            md.setArenaID(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("ArenaName").item(0);
            md.setArenaName(ele.getFirstChild().getNodeValue());
            ele = (Element) root.getElementsByTagName("WeatherID").item(0);
            md.setWetterId(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("SoldTotal").item(0);
            md.setZuschauer(Integer.parseInt(ele.getFirstChild().getNodeValue()));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchdetailsParser.readGeneral Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            md = null;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param doc TODO Missing Method Parameter Documentation
     * @param md TODO Missing Method Parameter Documentation
     */
    protected final void readCards(Document doc, Matchdetails md) {
        Element ele = null;
        Element root = null;
        MatchHighlight high = null;
        NodeList list = null;

        try {
            //Daten füllen                        
            root = doc.getDocumentElement();
            root = (Element) root.getElementsByTagName("Match").item(0);
            root = (Element) root.getElementsByTagName("Bookings").item(0);
            list = root.getElementsByTagName("Booking");

            for (int i = 0; (list != null) && (i < list.getLength()); i++) {
                high = new MatchHighlight();

                root = (Element) list.item(i);

                //Data
                ele = (Element) root.getElementsByTagName("BookingPlayerID").item(0);
                high.setSpielerID(Integer.parseInt(ele.getFirstChild().getNodeValue()));
                ele = (Element) root.getElementsByTagName("BookingPlayerName").item(0);
                high.setSpielerName(ele.getFirstChild().getNodeValue());
                ele = (Element) root.getElementsByTagName("BookingTeamID").item(0);
                high.setTeamID(Integer.parseInt(ele.getFirstChild().getNodeValue()));
                ele = (Element) root.getElementsByTagName("BookingType").item(0);
                high.setHighlightTyp(Integer.parseInt(ele.getFirstChild().getNodeValue()));
                ele = (Element) root.getElementsByTagName("BookingMinute").item(0);
                high.setMinute(Integer.parseInt(ele.getFirstChild().getNodeValue()));

                md.getHighlights().add(high);
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchdetailsParser.readCards Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            md = null;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param doc TODO Missing Method Parameter Documentation
     * @param md TODO Missing Method Parameter Documentation
     */
    protected final void readGeneral(Document doc, Matchdetails md) {
        Element ele = null;
        Element root = null;

        root = doc.getDocumentElement();

        try {
            //Daten füllen
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            md.setFetchDatumFromString(ele.getFirstChild().getNodeValue());

            //MatchData
            root = (Element) root.getElementsByTagName("Match").item(0);
            ele = (Element) root.getElementsByTagName("MatchID").item(0);
            md.setMatchID(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("MatchDate").item(0);
            md.setSpielDatumFromString(ele.getFirstChild().getNodeValue());
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchdetailsParser.readGeneral Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            md = null;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param doc TODO Missing Method Parameter Documentation
     * @param md TODO Missing Method Parameter Documentation
     */
    protected final void readGoals(Document doc, Matchdetails md) {
        Element ele = null;
        Element root = null;
        MatchHighlight high = null;
        NodeList list = null;

        try {
            //Daten füllen                        
            root = doc.getDocumentElement();
            root = (Element) root.getElementsByTagName("Match").item(0);
            root = (Element) root.getElementsByTagName("Scorers").item(0);
            list = root.getElementsByTagName("Goal");

            for (int i = 0; (list != null) && (i < list.getLength()); i++) {
                high = new MatchHighlight();

                high.setHighlightTyp(MatchHighlight.HIGHLIGHT_ERFOLGREICH);

                root = (Element) list.item(i);

                //Data
                ele = (Element) root.getElementsByTagName("ScorerPlayerID").item(0);
                high.setSpielerID(Integer.parseInt(ele.getFirstChild().getNodeValue()));
                ele = (Element) root.getElementsByTagName("ScorerPlayerName").item(0);
                high.setSpielerName(ele.getFirstChild().getNodeValue());
                ele = (Element) root.getElementsByTagName("ScorerTeamID").item(0);
                high.setTeamID(Integer.parseInt(ele.getFirstChild().getNodeValue()));
                ele = (Element) root.getElementsByTagName("ScorerHomeGoals").item(0);
                high.setHeimTore(Integer.parseInt(ele.getFirstChild().getNodeValue()));
                ele = (Element) root.getElementsByTagName("ScorerAwayGoals").item(0);
                high.setGastTore(Integer.parseInt(ele.getFirstChild().getNodeValue()));
                ele = (Element) root.getElementsByTagName("ScorerMinute").item(0);
                high.setMinute(Integer.parseInt(ele.getFirstChild().getNodeValue()));

                md.getHighlights().add(high);
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchdetailsParser.readGoals Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            md = null;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param doc TODO Missing Method Parameter Documentation
     * @param md TODO Missing Method Parameter Documentation
     */
    protected final void readGuestTeam(Document doc, Matchdetails md) {
        Element ele = null;
        Element root = null;

        try {
            //Daten füllen                        
            root = doc.getDocumentElement();
            root = (Element) root.getElementsByTagName("Match").item(0);
            root = (Element) root.getElementsByTagName("AwayTeam").item(0);

            //Data
            ele = (Element) root.getElementsByTagName("AwayTeamID").item(0);
            md.setGastId(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("AwayTeamName").item(0);
            md.setGastName(ele.getFirstChild().getNodeValue());
            ele = (Element) root.getElementsByTagName("AwayGoals").item(0);
            md.setGuestGoals(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("TacticType").item(0);
            md.setGuestTacticType(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("TacticSkill").item(0);
            md.setGuestTacticSkill(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingMidfield").item(0);
            md.setGuestMidfield(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingRightDef").item(0);
            md.setGuestRightDef(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingMidDef").item(0);
            md.setGuestMidDef(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingLeftDef").item(0);
            md.setGuestLeftDef(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingRightAtt").item(0);
            md.setGuestRightAtt(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingMidAtt").item(0);
            md.setGuestMidAtt(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingLeftAtt").item(0);
            md.setGuestLeftAtt(Integer.parseInt(ele.getFirstChild().getNodeValue()));

            /*
               //subRatings
               ele     =   (Element) root.getElementsByTagName ( "SubRatingMidfield" ).item (0);
               md.setGuestMidfieldSub ( XMLManager.instance().getFirstChildNodeValue ( ele ) );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingRightDef" ).item (0);
               md.setGuestRightDefSub ( XMLManager.instance().getFirstChildNodeValue ( ele ) );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingMidDef" ).item (0);
               md.setGuestMidDefSub ( XMLManager.instance().getFirstChildNodeValue ( ele ) );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingLeftDef" ).item (0);
               md.setGuestLeftDefSub ( XMLManager.instance().getFirstChildNodeValue ( ele ) );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingRightAtt" ).item (0);
               md.setGuestRightAttSub ( XMLManager.instance().getFirstChildNodeValue ( ele ) );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingMidAtt" ).item (0);
               md.setGuestMidAttSub ( XMLManager.instance().getFirstChildNodeValue ( ele )  );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingLeftAtt" ).item (0);
               md.setGuestLeftAttSub ( XMLManager.instance().getFirstChildNodeValue ( ele )  );
             */
            try {
                ele = (Element) root.getElementsByTagName("TeamAttitude").item(0);
                md.setGuestEinstellung(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            } catch (Exception e) {
                //nicht tragisch da Attrib nur bei eigenem Team vorhanden wäre
                md.setGuestEinstellung(Matchdetails.EINSTELLUNG_UNBEKANNT);
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchdetailsParser.readGuestTeam Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            md = null;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param doc TODO Missing Method Parameter Documentation
     * @param md TODO Missing Method Parameter Documentation
     */
    protected final void readHomeTeam(Document doc, Matchdetails md) {
        Element ele = null;
        Element root = null;

        try {
            //Daten füllen                        
            root = doc.getDocumentElement();
            root = (Element) root.getElementsByTagName("Match").item(0);
            root = (Element) root.getElementsByTagName("HomeTeam").item(0);

            //Data
            ele = (Element) root.getElementsByTagName("HomeTeamID").item(0);
            md.setHeimId(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("HomeTeamName").item(0);
            md.setHeimName(ele.getFirstChild().getNodeValue());
            ele = (Element) root.getElementsByTagName("HomeGoals").item(0);
            md.setHomeGoals(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("TacticType").item(0);
            md.setHomeTacticType(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("TacticSkill").item(0);
            md.setHomeTacticSkill(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingMidfield").item(0);
            md.setHomeMidfield(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingRightDef").item(0);
            md.setHomeRightDef(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingMidDef").item(0);
            md.setHomeMidDef(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingLeftDef").item(0);
            md.setHomeLeftDef(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingRightAtt").item(0);
            md.setHomeRightAtt(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingMidAtt").item(0);
            md.setHomeMidAtt(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("RatingLeftAtt").item(0);
            md.setHomeLeftAtt(Integer.parseInt(ele.getFirstChild().getNodeValue()));

            /*
               //subRatings
               ele     =   (Element) root.getElementsByTagName ( "SubRatingMidfield" ).item (0);
               md.setHomeMidfieldSub ( XMLManager.instance().getFirstChildNodeValue ( ele ) );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingRightDef" ).item (0);
               md.setHomeRightDefSub ( XMLManager.instance().getFirstChildNodeValue ( ele ) );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingMidDef" ).item (0);
               md.setHomeMidDefSub ( XMLManager.instance().getFirstChildNodeValue ( ele ) );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingLeftDef" ).item (0);
               md.setHomeLeftDefSub ( XMLManager.instance().getFirstChildNodeValue ( ele ) );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingRightAtt" ).item (0);
               md.setHomeRightAttSub ( XMLManager.instance().getFirstChildNodeValue ( ele ) );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingMidAtt" ).item (0);
               md.setHomeMidAttSub ( XMLManager.instance().getFirstChildNodeValue ( ele )  );
               ele     =   (Element) root.getElementsByTagName ( "SubRatingLeftAtt" ).item (0);
               md.setHomeLeftAttSub ( XMLManager.instance().getFirstChildNodeValue ( ele )  );
             */
            try {
                ele = (Element) root.getElementsByTagName("TeamAttitude").item(0);
                md.setHomeEinstellung(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            } catch (Exception e) {
                //nicht tragisch da Attrib nur bei eigenem Team vorhanden wäre, als Unbekannt markieren
                md.setHomeEinstellung(Matchdetails.EINSTELLUNG_UNBEKANNT);
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchdetailsParser.readHomeTeam Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            md = null;
        }
    }
}
