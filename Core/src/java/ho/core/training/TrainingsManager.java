// %2732728450:de.hattrickorganizer.logik%
package ho.core.training;

import ho.core.db.DBManager;
import ho.core.gui.HOMainFrame;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchLineupTeam;
import ho.core.model.UserParameter;
import ho.core.model.match.MatchStatistics;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.util.HOLogger;
import ho.core.util.HTCalendar;
import ho.core.util.HelperWrapper;

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
    static final public boolean TRAININGDEBUG = true;

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
        	c.add(Calendar.HOUR, UserParameter.instance().TimeZoneDifference);
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
    		TrainingPerWeek train, Timestamp timestamp) {
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
        			+ ", trainDate="+train.getTrainingDate().toString()+c2s);
        }
        if (train == null || train.getTyp() < 0) {
            return output;
        }

        TrainingPoint trainPoints = getTrainingPoint(train);
        Calendar trainingDate = train.getTrainingDate();

        try {
        	List<Integer> matches = getMatchesForTraining(trainingDate);
        	int gkPos[] = new int[]{ISpielerPosition.keeper};
        	int wbPos[] = new int[]{ISpielerPosition.leftBack, ISpielerPosition.rightBack};
        	int cdPos[] = new int[]{ISpielerPosition.leftCentralDefender, ISpielerPosition.middleCentralDefender, ISpielerPosition.rightCentralDefender};
        	int wPos[] = new int[]{ISpielerPosition.leftWinger, ISpielerPosition.rightWinger};
        	int mPos[] = new int[]{ISpielerPosition.leftInnerMidfield, ISpielerPosition.rightInnerMidfield, ISpielerPosition.centralInnerMidfield};
        	int fwPos[] = new int[] {ISpielerPosition.leftForward, ISpielerPosition.centralForward, ISpielerPosition.rightForward};
        	int spPos[] = new int[] {ISpielerPosition.setPieces};
        	int myID = HOVerwaltung.instance().getModel().getBasics().getTeamId();
        	for (int i=0; i<matches.size(); i++) {
                final int matchId = (matches.get(i)).intValue();
                
                //Get the MatchLineup by id
                MatchLineupTeam mlt = DBManager.instance().getMatchLineupTeam(matchId, myID);
                MatchStatistics ms = new MatchStatistics(matchId, mlt);
                TrainingPlayer tp = new TrainingPlayer();
                tp.setMinutesPlayedAsGK(tp.getMinutesPlayedAsGK() + ms.getMinutesPlayedInPositions(playerID, gkPos));
                tp.setMinutesPlayedAsWB(tp.getMinutesPlayedAsWB() + ms.getMinutesPlayedInPositions(playerID, wbPos));
                tp.setMinutesPlayedAsCD(tp.getMinutesPlayedAsCD() + ms.getMinutesPlayedInPositions(playerID, cdPos));
                tp.setMinutesPlayedAsW(tp.getMinutesPlayedAsW() + ms.getMinutesPlayedInPositions(playerID, wPos));
                tp.setMinutesPlayedAsIM(tp.getMinutesPlayedAsIM() + ms.getMinutesPlayedInPositions(playerID, mPos));
                tp.setMinutesPlayedAsFW(tp.getMinutesPlayedAsFW() + ms.getMinutesPlayedInPositions(playerID, fwPos));
                tp.setMinutesPlayedAsSP(tp.getMinutesPlayedAsSP() + ms.getMinutesPlayedInPositions(playerID, spPos));

                if (tp.PlayerHasPlayed()) {
                	// Player has played -> check how long he was on the field
                	// (i.e. if he got a red card or injured)
                    HOLogger.instance().debug(getClass(), "Match "+matchId+": "
                    		+"Player "+spieler.getName()+" ("+playerID+")"
                    		+" played "+ tp.getMinutesPlayedAsGK() + " mins as GK"
                    		+" played "+ tp.getMinutesPlayedAsWB() + " mins as WB"
                    		+" played "+ tp.getMinutesPlayedAsCD() + " mins as CD"
                    		+" played "+ tp.getMinutesPlayedAsW() + " mins as W"
                    		+" played "+ tp.getMinutesPlayedAsIM() + " mins as IM"
                    		+" played "+ tp.getMinutesPlayedAsFW() + " mins as FW"
                    		+" played "+ tp.getMinutesPlayedAsSP() + " mins as SP"
                    );
                    trainPoints.addTrainingMatch(tp);
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
