// %597232359:de.hattrickorganizer.logik.xml%
/*
 * XMLMatchLineupParser.java
 *
 * Created on 20. Oktober 2003, 08:08
 */
package de.hattrickorganizer.logik.xml;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import plugins.ISpielerPosition;

import de.hattrickorganizer.model.matches.MatchLineup;
import de.hattrickorganizer.model.matches.MatchLineupPlayer;
import de.hattrickorganizer.model.matches.MatchLineupTeam;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class XMLMatchLineupParser {
    //~ Constructors -------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //KONSTRUKTOR
    ////////////////////////////////////////////////////////////////////////////////    

    /**
     * Creates a new instance of XMLMatchLineupParser
     */
    public XMLMatchLineupParser() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //parse public
    ////////////////////////////////////////////////////////////////////////////////    
    public final MatchLineup parseMatchLineup(String dateiname) {
        MatchLineup ml = null;
        Document doc = null;

        doc = XMLManager.instance().parseFile(dateiname);
        ml = createLineup(doc);

        return ml;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param datei TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final MatchLineup parseMatchLineup(java.io.File datei) {
        Document doc = null;

        doc = XMLManager.instance().parseFile(datei);

        return createLineup(doc);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param inputStream TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final MatchLineup parseMatchLineupFromString(String inputStream) {
        Document doc = null;

        doc = XMLManager.instance().parseString(inputStream);

        return createLineup(doc);
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
    protected final MatchLineup createLineup(Document doc) {
        Element ele = null;
        Element root = null;
        MatchLineup ml = new MatchLineup();
        MatchLineupTeam team = null;

        if (doc == null) {
            return ml;
        }

        //Tabelle erstellen
        root = doc.getDocumentElement();

        try {
            //Daten füllen
            ele = (Element) root.getElementsByTagName("FetchedDate").item(0);
            ml.setFetchDatum(ele.getFirstChild().getNodeValue());
            ele = (Element) root.getElementsByTagName("MatchID").item(0);
            ml.setMatchID(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("HomeTeam").item(0);
            ml.setHeimId(Integer.parseInt(ele.getElementsByTagName("HomeTeamID").item(0)
                                             .getFirstChild().getNodeValue()));
            ml.setHeimName(ele.getElementsByTagName("HomeTeamName").item(0).getFirstChild()
                              .getNodeValue());
            ele = (Element) root.getElementsByTagName("AwayTeam").item(0);
            ml.setGastId(Integer.parseInt(ele.getElementsByTagName("AwayTeamID").item(0)
                                             .getFirstChild().getNodeValue()));
            ml.setGastName(ele.getElementsByTagName("AwayTeamName").item(0).getFirstChild()
                              .getNodeValue());
            ele = (Element) root.getElementsByTagName("MatchType").item(0);
            ml.setMatchTyp(Integer.parseInt(ele.getFirstChild().getNodeValue()));
            ele = (Element) root.getElementsByTagName("Arena").item(0);
            ml.setArenaID(Integer.parseInt(ele.getElementsByTagName("ArenaID").item(0)
                                              .getFirstChild().getNodeValue()));
            ml.setArenaName(ele.getElementsByTagName("ArenaName").item(0).getFirstChild()
                               .getNodeValue());
            ele = (Element) root.getElementsByTagName("MatchDate").item(0);
            ml.setSpielDatum(ele.getFirstChild().getNodeValue());

            //team adden
            team = createTeam((Element) root.getElementsByTagName("Team").item(0));

            if (team.getTeamID() == ml.getHeimId()) {
                ml.setHeim(team);
            } else {
                ml.setGast(team);
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLMatchLineupParser.createLineup Exception gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
            ml = null;
        }

        return ml;
    }

    /**
     * erzeugt einen Spieler aus dem xml
     *
     * @param ele TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    protected final MatchLineupPlayer createPlayer(Element ele) throws Exception {
        Element tmp = null;
        MatchLineupPlayer player = null;
        int roleID = -1;
        int behavior = 0;
        int spielerID = -1;
        double rating = -1.0d;
        double ratingStarsEndOfMatch = -1.0d;
        String name = "";
     //   int positionsCode = -1;

        tmp = (Element) ele.getElementsByTagName("PlayerID").item(0);
        spielerID = Integer.parseInt(tmp.getFirstChild().getNodeValue());
        tmp = (Element) ele.getElementsByTagName("RoleID").item(0);
        if (tmp != null) {
        	roleID = Integer.parseInt(tmp.getFirstChild().getNodeValue());
        }

        
        // This is the right spot to wash the old role IDs if arrived by xml.
        // Position code is not include in 1.6 xml. It is not needed from the older ones,
        // what is necessary is to check for old reposition values in the Behaviour.
        // We do move all repositions to central slot, and go happily belly up if we find more
        // than one repositioning to the same position (old setup where more than 3 forwards was possible)
        // 
        
//        if (roleID == 17 || roleID == 14) {
//        	System.out.println("Give me somewhere to put a breakpoint");
//        }
        
//        HOLogger.instance().debug(getClass(),"RoleID in: " + roleID);
        
        //nur wenn Spieler existiert
        if (spielerID > 0) {
            tmp = (Element) ele.getElementsByTagName("PlayerName").item(0);

            //Fix für xml BUG von HT
            if (tmp.getFirstChild() != null) {
                name = tmp.getFirstChild().getNodeValue();
            }

            //tactic is only set for those in the lineup (and not for the keeper).
            if (roleID == ISpielerPosition.keeper || roleID == ISpielerPosition.oldKeeper)  {
                //Diese Werte sind von HT vorgegeben aber nicht garantiert  mitgeliefert in xml, daher selbst setzen!
            	behavior = 0;
            	roleID = ISpielerPosition.keeper; // takes care of the old keeper ID.
            
            } else if ((roleID >= 0) && (roleID < ISpielerPosition.setPieces) 
            		|| ((roleID < ISpielerPosition.startReserves) && (roleID > ISpielerPosition.keeper))) {
                tmp = (Element) ele.getElementsByTagName("Behaviour").item(0);
                behavior = Integer.parseInt(tmp.getFirstChild().getNodeValue());
                
//                HOLogger.instance().debug(getClass(),"Behavior found: " + behavior);
                
                switch (behavior) {
                  case ISpielerPosition.OLD_EXTRA_DEFENDER :
                	  roleID = ISpielerPosition.middleCentralDefender;
                	  behavior = ISpielerPosition.NORMAL;
                	  break;
                  case ISpielerPosition.OLD_EXTRA_MIDFIELD :
                	  roleID = ISpielerPosition.centralInnerMidfield;
                	  behavior = ISpielerPosition.NORMAL;
                	  break;
                  case ISpielerPosition.OLD_EXTRA_FORWARD :
                	  roleID = ISpielerPosition.centralForward;
                	  behavior = ISpielerPosition.NORMAL;
                	  break;
                  case ISpielerPosition.OLD_EXTRA_DEFENSIVE_FORWARD :
                	  roleID = ISpielerPosition.centralForward;
                	  behavior = ISpielerPosition.DEFENSIVE;
                }
                
                // Wash the remaining old positions
                if (roleID < ISpielerPosition.setPieces) {
                	roleID = convertOldRoleToNew(roleID);
                }
            }

            //rating nur für leute die gespielt haben
            if ((roleID >= ISpielerPosition.startLineup) &&(roleID < ISpielerPosition.startReserves) || 
            		((roleID >= ISpielerPosition.ausgewechselt) && (roleID < ISpielerPosition.ausgewechseltEnd))) {
                tmp = (Element) ele.getElementsByTagName("RatingStars").item(0);
                rating = Double.parseDouble(tmp.getFirstChild().getNodeValue().replaceAll(",","."));
                tmp = (Element) ele.getElementsByTagName("RatingStarsEndOfMatch").item(0);
                ratingStarsEndOfMatch = Double.parseDouble(tmp.getFirstChild().getNodeValue().replaceAll(",","."));
                
            }
        }

//        HOLogger.instance().debug(getClass(),"RoleID out: " + roleID);
//        HOLogger.instance().debug(getClass(),"Behavior out: " + behavior);
//        HOLogger.instance().debug(getClass(),"--------------- Debug by XMLMatchLineupParse if you want it gone");      
        
        player = new MatchLineupPlayer(roleID, behavior, spielerID, rating, name, 0);
        player.setRatingStarsEndOfMatch(ratingStarsEndOfMatch);
        return player;
    }

    private int convertOldRoleToNew(int roleID) {
    	switch (roleID) {
    		case ISpielerPosition.oldKeeper :
    			return ISpielerPosition.keeper;
    		case ISpielerPosition.oldRightBack :
    			return ISpielerPosition.rightBack;
    		case ISpielerPosition.oldLeftCentralDefender :
    			return ISpielerPosition.leftCentralDefender;
    		case ISpielerPosition.oldRightCentralDefender :
    			return ISpielerPosition.rightCentralDefender;
    		case ISpielerPosition.oldLeftBack :
    			return ISpielerPosition.leftBack;
    		case ISpielerPosition.oldRightWinger :
    			return ISpielerPosition.rightWinger;
    		case ISpielerPosition.oldRightInnerMidfielder :
    			return ISpielerPosition.rightInnerMidfield;
    		case ISpielerPosition.oldLeftInnerMidfielder :
    			return ISpielerPosition.leftInnerMidfield;
    		case ISpielerPosition.oldLeftWinger:
    			return ISpielerPosition.leftWinger;
    		case ISpielerPosition.oldRightForward :
    			return ISpielerPosition.rightForward;
    		case ISpielerPosition.oldLeftForward :
    			return ISpielerPosition.leftForward;
    		case ISpielerPosition.oldSubstKeeper :
    			return ISpielerPosition.substKeeper;
    		case ISpielerPosition.oldSubstDefender :
    			return ISpielerPosition.substDefender;
    		case ISpielerPosition.oldSubstMidfielder :
    			return ISpielerPosition.substInnerMidfield;
    		case ISpielerPosition.oldSubstWinger :
    			return ISpielerPosition.substWinger;
    		case ISpielerPosition.oldSubstForward :
    			return ISpielerPosition.substForward;
    		default :
    			return roleID;
    	}
    		
    }
    /**
     * erzeugt das Team aus dem xml
     *
     * @param ele TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    protected final MatchLineupTeam createTeam(Element ele) throws Exception {
        Element tmp = null;
        NodeList list = null;

        //new MatchLineupTeam();
        MatchLineupTeam team = null;
        int teamId = -1;
        int erfahrung = -1;
        String teamName = "";

        tmp = (Element) ele.getElementsByTagName("TeamID").item(0);
        teamId = Integer.parseInt(tmp.getFirstChild().getNodeValue());
        tmp = (Element) ele.getElementsByTagName("ExperienceLevel").item(0);
        erfahrung = Integer.parseInt(tmp.getFirstChild().getNodeValue());
        tmp = (Element) ele.getElementsByTagName("TeamName").item(0);
        teamName = tmp.getFirstChild().getNodeValue();
        team = new MatchLineupTeam(teamName, teamId, erfahrung);
        tmp = (Element) ele.getElementsByTagName("Lineup").item(0);

        //Einträge adden
        list = tmp.getElementsByTagName("Player");

        HashMap<Integer, Integer> tempPlayerList = new HashMap<Integer, Integer>();
        
        for (int i = 0; (list != null) && (i < list.getLength()); i++) {
 
        	// We want to stop an api error that has repositioned players as substituted.
        	// They are both shown as substituted and in a position. (hopefully) substituted
        	// players are always last in the API, there are at least signs of a fixed order.
        	MatchLineupPlayer player = createPlayer((Element) list.item(i));
        	if (tempPlayerList.get(player.getSpielerId()) != null) {
        		if ((player.getId() >= ISpielerPosition.ausgewechselt) 
            			&& (player.getId() < ISpielerPosition.ausgewechseltEnd)) {
        			
        			// MatchLineup API bug, he is still on the pitch, so skip
        			continue;
        		}
        	}
        	
        	tempPlayerList.put(player.getSpielerId(), player.getId());
        	team.add2Aufstellung(player);
        }

        return team;
    }
}
