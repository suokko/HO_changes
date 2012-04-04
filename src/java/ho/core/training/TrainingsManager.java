// %2732728450:de.hattrickorganizer.logik%
package ho.core.training;

import ho.core.db.DBManager;
import ho.core.gui.HOMainFrame;
import ho.core.model.HOVerwaltung;
import ho.core.model.ISpielerPosition;
import ho.core.model.Spieler;
import ho.core.model.UserParameter;
import ho.core.util.HOLogger;
import ho.core.util.HTCalendar;
import ho.core.util.HelperWrapper;
import ho.module.matches.model.IMatchHighlight;
import ho.module.matches.model.MatchHighlight;
import ho.module.matches.model.MatchLineupPlayer;
import ho.module.matches.model.Matchdetails;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;


/**
 * Class that extract data from Database and calculates TrainingWeek and TrainingPoints earned from
 * players
 *
 * @author humorlos, Dragettho, thetom
 */
public class TrainingsManager {
    //~ Static fields/initializers -----------------------------------------------------------------

	static final int PLAYERSTATUS_OK = 0;
	static final int PLAYERSTATUS_NO_MATCHDATA = -1;
	static final int PLAYERSTATUS_NO_MATCHDETAILS = -2;
	static final int PLAYERSTATUS_NOT_IN_LINEUP = -3;
	static final int PLAYERSTATUS_RED_CARD = -4;
	static final int PLAYERSTATUS_SUBSTITUTED_IN = -5;
	static final int PLAYERSTATUS_SUBSTITUTED_OUT = -6;
	static final int PLAYERSTATUS_TACTIC_CHANGE = -7;
    private static TrainingsManager m_clInstance;

    /** Base values for training duration */
	public static final float BASE_DURATION_GOALKEEPING = (float)2.0;
	public static final float BASE_DURATION_DEFENDING = (float)3.6;
	public static final float BASE_DURATION_PLAYMAKING = (float)3.1;
	public static final float BASE_DURATION_PASSING = (float)2.8;
	public static final float BASE_DURATION_WINGER = (float)2.2;
	public static final float BASE_DURATION_SCORING = (float)3.2;
	public static final float BASE_DURATION_SET_PIECES = (float)0.9;
	public static final float BASE_AGE_FACTOR = (float)0.9;
	public static final float BASE_COACH_FACTOR = (float)1.0;
	public static final float BASE_ASSISTANT_COACH_FACTOR = (float)1.0;
	public static final float BASE_INTENSITY_FACTOR = (float)1.0;
    
    //~ Instance fields ----------------------------------------------------------------------------

    private Map<String,Map<Integer,Integer>> matchMap;

    private TrainingsWeekManager weekManager;
    static final public boolean TRAININGDEBUG = false;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of TrainingsManager
     */
    private TrainingsManager() {
        this.weekManager = TrainingsWeekManager.instance();
        this.matchMap = new HashMap<String,Map<Integer,Integer>>();
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
	public TrainingPerPlayer getTrainingPerPlayer() {
		return (new TrainingPerPlayer());
	}

    /**
     * returns a ITrainingPerPlayer instance for a specific player
     */
	public TrainingPerPlayer getTrainingPerPlayer(Spieler player) {
		return (new TrainingPerPlayer(player));
	}

    /**
     * TODO Missing Method Documentation
     *
     * @param hrfId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public TrainingPerWeek getLastTrainingWeek(int hrfId) {
        return this.weekManager.getTrainingWeek(hrfId);
    }

    /**
     * get a new training point instance
     *
     * @return new training point
     */
    public TrainingPoint getTrainingPoint() {
    	return new TrainingPoint();
    }

    /**
     * get a new training point instance
     * initialized with existing ITrainingWeek
     *
     * @return new training point
     */
    public TrainingPoint getTrainingPoint(TrainingPerWeek trainWeek) {
    	return new TrainingPoint(trainWeek);
    }

    /**
     * get a new training point instance
     * initialized with a new ITrainingWeek created by the arguments
     *
     * @return new training point
     * @
     */
    public TrainingPoint getTrainingPoint(int year, int week, int type, int intensity, int staminaTrainingPart) {
    	return new TrainingPoint(year, week, type, intensity, staminaTrainingPart);
    }

    // ------------------------------ Training Week Calculation ----------------------------------------------------
    public Vector<TrainingPerWeek> getTrainingsVector() {
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
    public TrainingPerPlayer calculateFullTrainingForPlayer(Spieler inputSpieler,
                                                             Vector<TrainingPerWeek> inputTrainings,
                                                             Timestamp timestamp) {
        //playerID HIER SETZEN
        final Spieler spieler = inputSpieler;

        if (timestamp == null) {
        	Calendar c = Calendar.getInstance();
        	c.add(Calendar.HOUR,UserParameter.instance().TimeZoneDifference);
            timestamp = new Timestamp(c.getTimeInMillis());
        }

        TrainingPerPlayer output = getTrainingPerPlayer(spieler);

        if (TRAININGDEBUG)
        	HOLogger.instance().debug(getClass(), "Start calcFullTraining for "+spieler.getName()+", output="+output);

        //alle Trainings durchlaufen
        //run through all trainings
        Iterator<TrainingPerWeek> i = inputTrainings.iterator();
        while (i.hasNext()) {
        	TrainingPerPlayer curTraining = new TrainingPerPlayer();
            //holen des gerade abzuarbeitenden trainings
            //get training to consider this round of the loop
            final TrainingPerWeek train = i.next();

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
            if (TRAININGDEBUG)
            	HOLogger.instance().debug(getClass(), "Mid calcFullTraining for "+spieler.getName()+", "+train+", cur="+(curTraining==null?"null":curTraining.toString())+", output="+output);
        }
        if (TRAININGDEBUG)
        	HOLogger.instance().debug(getClass(), "End calcFullTraining for "+spieler.getName()+", output="+output);
        return output;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param inputTrainings TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector<TrainingPerWeek> calculateTrainings(Vector<?> inputTrainings) {
        return this.weekManager.calculateTrainings(inputTrainings);
    }

    public TrainingPerPlayer calculateWeeklyTrainingForPlayer(Spieler inputSpieler,
    		TrainingPerWeek train) {
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
    public TrainingPerPlayer calculateWeeklyTrainingForPlayer(Spieler inputSpieler,
    		TrainingPerWeek train,
                                                               Timestamp timestamp) {
        //playerID HIER SETZEN
        final Spieler spieler = inputSpieler;
        final int playerID = spieler.getSpielerID();

        TrainingPerPlayer output = getTrainingPerPlayer(spieler);
        if (timestamp != null)
        	output.setTimestamp(timestamp);

        if (TRAININGDEBUG) {
        	HTCalendar htc1 = HelperWrapper.instance().createTrainingCalendar();
        	HTCalendar htc2 = HelperWrapper.instance().createTrainingCalendar();
        	String c1s = "";
        	String c2s = "";
        	if (timestamp != null) {
        		htc1.setTime(timestamp);
        		c1s = " ("+htc1.getHTSeason()+"."+htc1.getHTWeek()+")";
        	}
        	htc2.setTime(train.getTrainingDate());
        	c2s = " ("+htc2.getHTSeason()+"."+htc2.getHTWeek()+")";

        	HOLogger.instance().debug(getClass(),
        			"Start calcWeeklyTraining for "+spieler.getName()+", zeitpunkt="+((timestamp!=null)?timestamp.toString()+c1s:"")
        			+ ", trainDate="+train.getTrainingDate().getTime().toLocaleString()+c2s);
        }
        if (train == null || train.getTyp() < 0) {
            return output;
        }

        TrainingPoint trainPoints = getTrainingPoint(train);
        Calendar trainingDate = train.getTrainingDate();

        try {
        	List<Integer> matches = getMatchesForTraining(trainingDate);

        	for (int i=0; i<matches.size(); i++) {
                final int matchId = (matches.get(i)).intValue();
                int playerPos = getMatchPosition(matchId, playerID);
                // TODO if the player got a red card, playerPos is PLAYERSTATUS_RED_CARD
                // Perhaps we could try to guess the real position using the
                // startup lineup from this game?
                // For now, players with red card DO NOT get any training at all :(
                if (playerPos > 0) {
                	// Player has played -> check how long he was on the field
                	// (i.e. if he got a red card or injured)
                    int minutesPlayed = getMinutesPlayed (matchId, playerID);
					if (TRAININGDEBUG) HOLogger.instance().debug(getClass(), "Match "+matchId+": "
                    		+"Player "+spieler.getName()+" ("+playerID+")"
                    		+" played "+minutesPlayed+"mins at pos "+playerPos);
                    trainPoints.addTrainingMatch (minutesPlayed, playerPos);
                }
            }
            output.setTrainPoint(trainPoints);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        if (TRAININGDEBUG)
        	HOLogger.instance().debug(getClass(),
        			"End calcWeeklyTraining for "+spieler.getName()+", "+train+", output="+output);
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
        if (JOptionPane.showConfirmDialog(HOMainFrame.instance(),
                                          "Depending on database volume this process takes several minutes. Start recalculation ?",
                                          "Subskill Recalculation", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
            HOVerwaltung.instance().recalcSubskills(showBar, null);

            //tools.Helper.showMessage ( this, model.HOVerwaltung.instance().getLanguageString( "NeustartErforderlich" ), "", JOptionPane.INFORMATION_MESSAGE );
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
        return (point.getTrainingPoint(trainType, Integer.valueOf(position)).doubleValue());
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
		// OR
		// No Matchdetails found, probably not downloaded...
		// Let's expect the worst and assume that the player
		// did not play (-> 0 minutes)

    	// TODO: Manually substituted players (in or out)
    	// and players with a tactic change
    	// also get 0 minutes training
    	// (Should be improved)
    	if (posId == PLAYERSTATUS_NOT_IN_LINEUP ||
    			posId == PLAYERSTATUS_NO_MATCHDATA ||
    			posId == PLAYERSTATUS_NO_MATCHDETAILS ||
    			posId == PLAYERSTATUS_SUBSTITUTED_IN ||
    			posId == PLAYERSTATUS_SUBSTITUTED_OUT ||
    			posId == PLAYERSTATUS_TACTIC_CHANGE )
    		return 0;
    	Matchdetails details = DBManager.instance().getMatchDetails(matchId);
        Vector<MatchHighlight> highlights = details.getHighlights();
        for (int i=0; i<highlights.size(); i++) {
        	MatchHighlight curHighlight = highlights.get(i);
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
       			switch (curHighlight.getHighlightSubTyp()) {
       			/**
       			 * Check for Walkover
       			 */
       			case IMatchHighlight.HIGHLIGHT_SUB_WALKOVER_HOMETEAM_WINS:
       			case IMatchHighlight.HIGHLIGHT_SUB_WALKOVER_AWAYTEAM_WINS:
       				boolean home = false;
       				if (details.getHeimId() == HOVerwaltung.instance().getModel().getBasics().getTeamId())
       					home = true;
       				// Check if our team has fielded at least 9 players
       				if (details.getLineup(home).size() >= 9)
       					endMinute = 90;
       				else
       					endMinute = 0;
       				break;
       			/**
       			 * Check for Red Cards
       			 *
       			 * Unfortunately, this does not work very well, because players with a red card
       			 * are not transmitted in lineup from Hattrick. Therefore, we don't know
       			 * on which position the player played. :(
       			 * Nevertheless, we check how long he played in this match.
       			 */
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
    public List<Integer> getMatchesForTraining (Calendar trainingDate) {
        List<Integer> matches = new ArrayList<Integer>();

        try {
        	final ResultSet matchRS = DBManager.instance().getAdapter().executeQuery(createQuery(trainingDate));

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
     * Returns the player status (PLAYERSTATUS_*) for a player in a specific match
     * @param matchId	match id
     * @param playerId 	player id
     * @return	player status
     */
    public int getPlayerStatus (int matchId, int playerId) {
    	Map<Integer,Integer> matchData = getMatchLineup(matchId);
    	// No Lineup for this match
    	if (matchData == null)
    		return PLAYERSTATUS_NO_MATCHDATA;
    	Matchdetails details = DBManager.instance().getMatchDetails(matchId);
    	if (details == null)
    		// No Matchdetails found, probably not downloaded...
    		return PLAYERSTATUS_NO_MATCHDETAILS;
    	Integer posId = matchData.get(new Integer(playerId));
    	// Player not in lineup
    	if (posId == null) {
    		// Check if he got a red card
            Vector<MatchHighlight> highlights = details.getHighlights();
            for (int i=0; i<highlights.size(); i++) {
            	MatchHighlight curHighlight = highlights.get(i);
           		if (curHighlight.getSpielerID() == playerId) {
           			if (curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_KARTEN &&
           					(curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_HARTER_EINSATZ ||
           							curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_GELB_ROT_UNFAIR ||
           							curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_ROT)
           						) {
           				return PLAYERSTATUS_RED_CARD;
           			}
           		}
            }
            // He did not get a red card, i.e. he is really not in lineup
   			return PLAYERSTATUS_NOT_IN_LINEUP;
    	}

        Vector<MatchHighlight> highlights = details.getHighlights();
        for (int i=0; i<highlights.size(); i++) {
        	MatchHighlight curHighlight = highlights.get(i);
        	// Check for manual substitutions (in)
       		if (curHighlight.getGehilfeID() == playerId) {
       			if (curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_SPEZIAL &&
       					(curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_SUBSTITUTION_DEFICIT ||
       							curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_SUBSTITUTION_EVEN ||
       							curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_SUBSTITUTION_LEAD)
           					) {
       				return PLAYERSTATUS_SUBSTITUTED_IN;
       			}
       		} else if (curHighlight.getSpielerID() == playerId) {
       			// Check for manual substitutions (out)
       			if (curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_SPEZIAL &&
   					(curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_SUBSTITUTION_DEFICIT ||
   							curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_SUBSTITUTION_EVEN ||
   							curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_SUBSTITUTION_LEAD)
       					) {
       				return PLAYERSTATUS_SUBSTITUTED_OUT;
   				// Check for tactic change
       			} else if (curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_SPEZIAL &&
       					(curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_TACTICCHANGE_DEFICIT||
       							curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_TACTICCHANGE_EVEN ||
       							curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_TACTICCHANGE_LEAD)
           					) {
       				return PLAYERSTATUS_TACTIC_CHANGE;
       			}
       		}
        }

    	return PLAYERSTATUS_OK;
    }

    /**
     * Returns the positionId for a player in a specific match
     * If he is not in the lineup, return the player status (PLAYERSTATUS_*)
     * @param matchId	match id
     * @param playerId 	player id
     * @return	position id
     */
    public int getMatchPosition (int matchId, int playerId) {
    	int playerStatus = getPlayerStatus(matchId, playerId);
    	if (playerStatus == PLAYERSTATUS_OK) {
        	Map<Integer,Integer> matchData = getMatchLineup(matchId);
        	Integer posId = matchData.get(new Integer(playerId));
        	return posId.intValue();
    	} else {
    		return playerStatus;
    	}
    }

    /**
     * Fetches the MatchLineup from the cache (matchMap) or - if not in cache - from the database
     * Key = playerId as Integer
     * Value = posId as Integer
     * @param matchId	match id
     * @return	Map	(playerId -> posId) of a match lineup
     */
    private Map<Integer,Integer> getMatchLineup(int matchId) {
    	Map<Integer,Integer> matchData = this.matchMap.get("" + matchId);

    	if (matchData == null) {
    		matchData = new HashMap<Integer,Integer>();
    		MatchLineupPlayer player;

    		Vector<MatchLineupPlayer> playerVec = 
    			DBManager.instance().getMatchLineupPlayers(matchId, 
    					HOVerwaltung.instance().getModel().getBasics().getTeamId());	

    		for (int i = 0 ; i < playerVec.size() ; i++) {
    			player = playerVec.get(i);
    			// Ignore everyone but the 11 on field
    			if ((player.getFieldPos() >= ISpielerPosition.startLineup) &&
    					(player.getFieldPos() < ISpielerPosition.startReserves)) {
    				matchData.put(player.getSpielerId(), (int)player.getFieldPos());
    			}

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
        final int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
        final String sdbquery = "SELECT MATCHID FROM MATCHDETAILS WHERE " + "( HEIMID=" + teamId
                                + " OR GASTID=" + teamId + " ) " + "AND SPIELDATUM BETWEEN '"
                                + ots.toString() + "' AND '" + ts.toString() + "' "
                                + "ORDER BY SPIELDATUM DESC";

        return sdbquery;
    }

}
