// %2732728450:de.hattrickorganizer.logik%
package de.hattrickorganizer.logik;

import gui.UserParameter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import plugins.IHOMiniModel;
import plugins.IMatchDetails;
import plugins.IMatchHighlight;
import plugins.ISpieler;
import plugins.ITrainingPerPlayer;
import plugins.ITrainingPoint;
import plugins.ITrainingWeek;
import plugins.ITrainingsManager;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.TrainingPerPlayer;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Class that extract data from Database and calculates TrainingWeek and TrainingPoints earned from
 * players
 *
 * @author humorlos, Dragettho, thetom
 */
public class TrainingsManager implements ITrainingsManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static TrainingsManager m_clInstance;

    //~ Instance fields ----------------------------------------------------------------------------

    /** HO Model */
    private IHOMiniModel p_IHMM_HOMiniModel;
    private Map matchMap;

    private TrainingsWeekManager weekManager;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of TrainingsManager
     */
    private TrainingsManager() {
        this.p_IHMM_HOMiniModel = HOMiniModel.instance();
        this.weekManager = TrainingsWeekManager.instance();
        this.matchMap = new HashMap();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * liefert instance vom Trainingsmanager  vor der ersten Nutzun noch fillWithData(
     * vorgabeTrainings ) aufrufen
     *
     * @return instance of NewTrainingManager
     */
    public static TrainingsManager instance() {
        if (m_clInstance == null) {
            m_clInstance = new TrainingsManager();
        }

        return m_clInstance;
    }

    /**
     * returns an empty ITrainingPerPlayer instance
     */
	public ITrainingPerPlayer getTrainingPerPlayer() {
		return (new TrainingPerPlayer());
	}

    /**
     * returns a ITrainingPerPlayer instance for a specific player
     */
	public ITrainingPerPlayer getTrainingPerPlayer(ISpieler player) {
		return (new TrainingPerPlayer(player));
	}

    /**
     * TODO Missing Method Documentation
     *
     * @param hrfId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public ITrainingWeek getLastTrainingWeek(int hrfId) {
        return this.weekManager.getTrainingWeek(hrfId);
    }

    /**
     * get a new training point instance
     *
     * @return new training point
     */
    public ITrainingPoint getTrainingPoint() {
    	return new TrainingPoint();
    }

    /**
     * get a new training point instance
     * initialized with existing ITrainingWeek
     *
     * @return new training point
     */
    public ITrainingPoint getTrainingPoint(ITrainingWeek trainWeek) {
    	return new TrainingPoint(trainWeek);
    }

    /**
     * get a new training point instance
     * initialized with a new ITrainingWeek created by the arguments
     *
     * @return new training point
     * @
     */
    public ITrainingPoint getTrainingPoint(int year, int week, int type, int intensity, int staminaTrainingPart) {
    	return new TrainingPoint(year, week, type, intensity, staminaTrainingPart);
    }

    // ------------------------------ Training Week Calculation ----------------------------------------------------
    public Vector getTrainingsVector() {
        return this.weekManager.getTrainingsVector();
    }


    //------------------------------------------ Deprecated Methods -------------------------------------------------

    /**
     * liefert die komplette Trainings in jedem skill eines Spielers calculates TRaining for given
     * Player for each skill
     *
     * @param inputSpieler Player to use
     * @param inputTrainings preset Trainingweeks
     * @param timestamp calc trainings up to timestamp, null for all
     *
     * @return TrainingPerPlayer
     */
    public ITrainingPerPlayer calculateFullTrainingForPlayer(ISpieler inputSpieler,
                                                             Vector inputTrainings,
                                                             Timestamp timestamp) {
        //playerID HIER SETZEN
        final ISpieler spieler = inputSpieler;

        if (timestamp == null) {
        	Calendar c = Calendar.getInstance();
        	c.add(Calendar.HOUR,UserParameter.instance().TimeZoneDifference);
            timestamp = new Timestamp(c.getTimeInMillis());
        }

        ITrainingPerPlayer output = getTrainingPerPlayer(spieler);

//        System.out.println ("Start calcFullTraining for "+spieler.getName()+", output="+output);

        //alle Trainings durchlaufen
        //run through all trainings
        Iterator i = inputTrainings.iterator();
        while (i.hasNext()) {
        	TrainingPerPlayer curTraining = new TrainingPerPlayer();
            //holen des gerade abzuarbeitenden trainings
            //get training to consider this round of the loop
            final ITrainingWeek train = (ITrainingWeek) i.next();

            if (train.getTyp() == -1) {
                continue;
            }

            final Calendar trainingDate = train.getTrainingDate();
            
            // if (trainingDate > timestamp) then ignore training and quit, because all following trainings would be after the timestamp too
            if (trainingDate.getTimeInMillis() >= timestamp.getTime()) {
                return output;
            }

            curTraining = (TrainingPerPlayer)calculateWeeklyTrainingForPlayer(spieler, train, timestamp);
            output.addValues(curTraining);
//            System.out.println ("Mid calcFullTraining for "+spieler.getName()+", "+train+", cur="+(curTraining==null?"null":curTraining.toString())+", output="+output);
        }
//        System.out.println ("End calcFullTraining for "+spieler.getName()+", output="+output);
        return output;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param inputTrainings TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector calculateTrainings(Vector inputTrainings) {
        return this.weekManager.calculateTrainings(inputTrainings);
    }

    public ITrainingPerPlayer calculateWeeklyTrainingForPlayer(ISpieler inputSpieler,
            ITrainingWeek train) {
    	return calculateWeeklyTrainingForPlayer(inputSpieler, train, null);
    }
    
    /**
     * liefert die komplette Trainings in jedem skill eines Spielers calculates Training for given
     * Player for each skill
     *
     * @param inputSpieler Player to use
     * @param train preset Trainingweeks
     * @param timestamp if not null, calculate training for this training date only
     *
     * @return TrainingPerPlayer
     */
    public ITrainingPerPlayer calculateWeeklyTrainingForPlayer(ISpieler inputSpieler,
                                                               ITrainingWeek train,
                                                               Timestamp timestamp) {
        //playerID HIER SETZEN
        final ISpieler spieler = inputSpieler;
        final int playerID = spieler.getSpielerID();

        ITrainingPerPlayer output = getTrainingPerPlayer(spieler);
        if (timestamp != null)
        	output.setTimestamp(timestamp);
//        System.out.println ("Start calcWeeklyTraining for "+spieler.getName()+", zeitpunkt="+((zeitpunkt!=null)?zeitpunkt.toString():"")+", trainDate="+train.getTrainingDate().getTime().toLocaleString());
        
        if (train == null || train.getTyp() == -1) {
            return output;
        }

        TrainingPoint trainPoints = new TrainingPoint(train);
        Calendar trainingDate = train.getTrainingDate();

        try {
        	List matches = getMatchesForTraining(trainingDate);

        	for (int i=0; i<matches.size(); i++) {
                final int matchId = ((Integer)matches.get(i)).intValue();
                final Map matchData = getMatchLineup(matchId);
                Integer playerMatchPosition = (Integer) matchData.get(new Integer(playerID));
                // Minutes played in the current match
                int minutesPlayed = 0;
                
                // Player not played, position is 0
                if (playerMatchPosition == null) {
                    playerMatchPosition = new Integer(0);
                } else {
                	// Player has played -> check how long he was on the field
                	// (i.e. if he got a red card or injured)
                    minutesPlayed = getMinutesPlayed (matchId, playerID);
//                    HOLogger.instance().debug(getClass(), "Match "+matchId+": "
//                    		+"Player "+playerID
//                    		+" played "+minutesPlayed+"mins at pos "+playerMatchPosition.intValue());
                    trainPoints.addTrainingMatch (minutesPlayed, playerMatchPosition.intValue());
                }
            }
            output.setTrainPoint(trainPoints);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

//        System.out.println ("End calcWeeklyTraining for "+spieler.getName()+", "+train+", output="+output);
        return output;
    }

    // ------------------------ Deprecated Methods -----------------------------------------------------------------

    /**
     * Recalculates all sub skills for all players
     *
     * @param showBar show progress bar
     */
    public void recalcSubskills(boolean showBar) {
    	HOMainFrame.setHOStatus(HOMainFrame.BUSY);
        if (JOptionPane.showConfirmDialog(p_IHMM_HOMiniModel.getGUI().getOwner4Dialog(),
                                          "Depending on database volume this process takes several minutes. Start recalculation ?",
                                          "Subskill Recalculation", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
            HOVerwaltung.instance().recalcSubskills(showBar, null);

            //tools.Helper.showMessage ( this, model.HOVerwaltung.instance ().getResource ().getProperty( "NeustartErforderlich" ), "", JOptionPane.INFORMATION_MESSAGE );
        }
    }

    //----------------------------------- Utility Methods ----------------------------------------------------------

    /**
     * Returns the base points a player gets for this training type 
     * in a full match at this position
     * @param trainType 	training type
     * @param position		player position id
     * @return base points
     */
    public double getBasePoints (int trainType, int position) {
    	TrainingPoint point = new TrainingPoint();
        return (point.getTrainingPoint(trainType, new Integer (position)).doubleValue());
    }
    
    /**
     * Calculates how long the player was on the field in the specified match
     * @param matchId	the match to check
     * @param playerId	the player to check
     * @return	number of minutes the player was on the field
     */
    public int getMinutesPlayed (int matchId, int playerId) {
    	int startMinute = 0;
    	int endMinute = -1;
    	int posId = getMatchPosition(matchId, playerId);
    	// Player was not on the field -> 0 minutes
    	if (posId < 0)
    		return 0;
       	IMatchDetails details = HOMiniModel.instance().getMatchDetails(matchId);
    	if (details == null) {
    		// No Matchdetails found, probably not downloaded... 
    		// Let's hope the best and assume that the player
    		// could play the full game (=90 mins)
    		return 90;
    	}
        Vector highlights = details.getHighlights();
        for (int i=0; i<highlights.size(); i++) {
        	IMatchHighlight curHighlight = (IMatchHighlight)highlights.get(i);
       		if (curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_INFORMATION) {
       			// Player left the field because of an injury
       			switch (curHighlight.getHighlightSubTyp()) {
       			case IMatchHighlight.HIGHLIGHT_SUB_VERLETZT:
       			case IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_EINS:
       			case IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_KEIN_ERSATZ_ZWEI:
       			case IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_LEICHT:
       			case IMatchHighlight.HIGHLIGHT_SUB_VERLETZT_SCHWER:
       				if (curHighlight.getSpielerID() == playerId)
           				// This player got injured -> he left the field in this minute
       	        		endMinute = curHighlight.getMinute();
       	        	else if (curHighlight.getGehilfeID() == playerId)
       	        		// Other player got injured, this player is his substitute
       	        		startMinute = curHighlight.getMinute();
       	        	break;
       			}
       		} else if (curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_KARTEN) {
       			// Player left the field because of a red card
       			switch (curHighlight.getHighlightSubTyp()) {
       			case IMatchHighlight.HIGHLIGHT_SUB_ROT:
       			case IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ:
       			case IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR:
       				// This player got a red card -> he left the field in this minute
       				if (curHighlight.getSpielerID() == playerId)
       					endMinute = curHighlight.getMinute();
       				break;
        		}
        	}
        	// End of match reached and the player is still on the field
        	// Set minutesPlayed to length of match
        	if (endMinute == -1 &&
        			curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_KARTEN &&
        			curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_SPIELENDE) {
        		endMinute = curHighlight.getMinute();
        	}
        }
        // Calculate how long he was on the field
        int minutesPlayed = endMinute - startMinute;
//    	System.out.println ("getMinPlayer: matchId="+matchId+", playerId="+playerId+", posId="+posId+", startMin="+startMinute+", endMin="+endMinute+", mins="+minutesPlayed);
        return minutesPlayed;
    }

    /**
     * Creates a list of matches for the specified training
     * 
     * @param trainingDate	use this trainingDate
     * @return	list of matchIds (type Integer)
     */
    public List getMatchesForTraining (Calendar trainingDate) {
        List matches = new ArrayList();

        try {
        	final ResultSet matchRS = p_IHMM_HOMiniModel.getAdapter().executeQuery(createQuery(trainingDate));

        	if (matchRS == null) {
        		//Falls nichts geliefert wurde // in case of no return values
        		return matches;
        	}

        	while (matchRS.next()) {
        		final int matchId = matchRS.getInt("MATCHID");
        		matches.add(new Integer(matchId));
        	}

            matchRS.close();
        } catch (Exception e1) {
            HOLogger.instance().log(getClass(),e1);
        }

        return matches;
    }

    /**
     * Returns the positionId for a player in a specific match
     * @param matchId	match id
     * @param playerId 	player id
     * @return	position id
     */
    public int getMatchPosition (int matchId, int playerId) {
    	Map matchData = getMatchLineup(matchId);
    	// No Lineup for this match
    	if (matchData == null)
    		return -1;
    	Integer posId = (Integer)matchData.get(new Integer(playerId));
    	// Player not in lineup
    	if (posId == null)
    		return -2;
    	return posId.intValue();
    }
    
    /**
     * Fetches the MatchLineup from the cache (matchMap) or - if not in cache - from the database
     * Key = playerId as Integer
     * Value = posId as Integer
     * @param matchId	match id
     * @return	Map	(playerId -> posId) of a match lineup  
     */
    private Map getMatchLineup(int matchId) {
        Map matchData = (Map) this.matchMap.get("" + matchId);

        if (matchData == null) {
            matchData = new HashMap();

            final String query =
                "select SPIELERID,FieldPos from MATCHLINEUPPLAYER where FIELDPOS>-1 AND MATCHID = "
                + matchId + "  and TEAMID = " + p_IHMM_HOMiniModel.getBasics().getTeamId();
            final ResultSet rs = p_IHMM_HOMiniModel.getAdapter().executeQuery(query);

            if (rs == null) {
                this.matchMap.put("" + matchId, matchData);
                return matchData;
            }

            try {
                while (rs.next()) {
                    final int id = rs.getInt("SPIELERID");
                    final int pos = rs.getInt("FIELDPOS");
                    matchData.put(new Integer(id), new Integer(pos));
                }
            } catch (SQLException e) {
            }

            this.matchMap.put("" + matchId, matchData);
        }

        return matchData;
    }

    /**
     * Creates the query to extract the list of matchId for each Training
     *
     * @param calendar TrainingDate
     *
     * @return the query
     */
    private String createQuery(Calendar calendar) {
        final Timestamp ts = new Timestamp(calendar.getTimeInMillis());
        final Calendar old = (Calendar) calendar.clone();

        //Set Time 1 Woche zur?ck // set time one week back
        old.add(Calendar.WEEK_OF_YEAR, -1);

        final Timestamp ots = new Timestamp(old.getTimeInMillis());
        final int teamId = p_IHMM_HOMiniModel.getBasics().getTeamId();
        final String sdbquery = "SELECT MATCHID FROM MATCHDETAILS WHERE " + "( HEIMID=" + teamId
                                + " OR GASTID=" + teamId + " ) " + "AND SPIELDATUM BETWEEN '"
                                + ots.toString() + "' AND '" + ts.toString() + "' "
                                + "ORDER BY SPIELDATUM DESC";

        return sdbquery;
    }

}
