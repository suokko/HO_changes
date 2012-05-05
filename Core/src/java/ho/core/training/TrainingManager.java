package ho.core.training;

import ho.core.constants.TrainingType;
import ho.core.db.DBManager;
import ho.core.gui.HOMainFrame;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.model.match.MatchLineupTeam;
import ho.core.model.match.MatchStatistics;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.util.HOLogger;
import ho.core.util.HTCalendar;
import ho.core.util.HTCalendarFactory;

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
public class TrainingManager {
    //~ Static fields/initializers -----------------------------------------------------------------

	private static TrainingManager m_clInstance;

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

    private TrainingWeekManager weekManager;
    static final public boolean TRAININGDEBUG = true;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of TrainingsManager
     */
    private TrainingManager() {
        this.weekManager = TrainingWeekManager.instance();
        this.matchMap = new HashMap<String,Map<Integer,Integer>>();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns a singleton TrainingManager object
     *
     * @return instance of TrainingManager
     */
    public static TrainingManager instance() {
        if (m_clInstance == null) {
            m_clInstance = new TrainingManager();
        }
        return m_clInstance;
    }

    /**
     * returns an empty TrainingPerPlayer instance
     */
	public TrainingPerPlayer getTrainingPerPlayer() {
		return (new TrainingPerPlayer());
	}

    /**
     * returns a TrainingPerPlayer instance for a specific player
     */
	public TrainingPerPlayer getTrainingPerPlayer(Spieler player) {
		return (new TrainingPerPlayer(player));
	}

    /**
     * Get a new training point instance
     *
     * @return new training point
     */
    public TrainingPoint getTrainingPoint() {
    	return new TrainingPoint();
    }

    /**
     * get a new training point instance
     * initialized with existing TrainingWeek
     *
     * @return new training point
     */
    public TrainingPoint getTrainingPoint(TrainingPerWeek trainWeek) {
    	return new TrainingPoint(trainWeek);
    }

    /**
     * get a new training point instance
     * initialized with a new TrainingWeek created by the arguments
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
     * Calculates Training for a given Player for each skill
     *
     * @param inputPlayer Player to use
     * @param inputTraining preset Trainingweeks
     * @param timestamp calc trainings up to timestamp, null for all
     *
     * @return TrainingPerPlayer
     */
    public TrainingPerPlayer calculateFullTrainingForPlayer(Spieler inputPlayer,
                                                             Vector<TrainingPerWeek> inputTraining,
                                                             Timestamp timestamp) {
        if (timestamp == null) {
        	Calendar c = Calendar.getInstance();
        	c.add(Calendar.HOUR, UserParameter.instance().TimeZoneDifference);
            timestamp = new Timestamp(c.getTimeInMillis());
        }
        TrainingPerPlayer output = getTrainingPerPlayer(inputPlayer);

        if (TRAININGDEBUG)
        	HOLogger.instance().debug(getClass(), "Start calcFullTraining for "+inputPlayer.getName()+", output="+output);

        //Iterate through all training
        Iterator<TrainingPerWeek> i = inputTraining.iterator();
        while (i.hasNext()) {
        	TrainingPerPlayer curTraining = new TrainingPerPlayer();
            //get training to consider this round of the loop
            final TrainingPerWeek train = i.next();
            if (train.getTrainingType() == -1) {
                continue;
            }
            final Calendar trainingDate = train.getTrainingDate();
            // if (trainingDate > timestamp) then ignore training and quit, because all following 
            // training would be after the timestamp too
            if (trainingDate.getTimeInMillis() >= timestamp.getTime()) {
                return output;
            }
            curTraining = (TrainingPerPlayer)calculateWeeklyTrainingForPlayer(inputPlayer, train, timestamp);
            output.addValues(curTraining);
            if (TRAININGDEBUG)
            	HOLogger.instance().debug(getClass(), "Mid calcFullTraining for "+inputPlayer.getName()+", "+train+", cur="+(curTraining==null?"null":curTraining.toString())+", output="+output);
        }
        if (TRAININGDEBUG)
        	HOLogger.instance().debug(getClass(), "End calcFullTraining for "+inputPlayer.getName()+", output="+output);
        return output;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param inputTrainings TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector<TrainingPerWeek> calculateTraining(Vector<?> inputTrainings) {
        return this.weekManager.calculateTrainings(inputTrainings);
    }

    public TrainingPerPlayer calculateWeeklyTrainingForPlayer(Spieler inputSpieler,
    		TrainingPerWeek train) {
    	return calculateWeeklyTrainingForPlayer(inputSpieler, train, null);
    }

    /**
     * Training for given player for each skill
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
        	HTCalendar htc1 = HTCalendarFactory.createTrainingCalendar();
        	HTCalendar htc2 = HTCalendarFactory.createTrainingCalendar();
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
        if (train == null || train.getTrainingType() < 0) {
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
                tp.Name(spieler.getName());
                tp.setMinutesPlayedAsGK(tp.getMinutesPlayedAsGK() + ms.getMinutesPlayedInPositions(playerID, gkPos));
                tp.setMinutesPlayedAsWB(tp.getMinutesPlayedAsWB() + ms.getMinutesPlayedInPositions(playerID, wbPos));
                tp.setMinutesPlayedAsCD(tp.getMinutesPlayedAsCD() + ms.getMinutesPlayedInPositions(playerID, cdPos));
                tp.setMinutesPlayedAsW(tp.getMinutesPlayedAsW() + ms.getMinutesPlayedInPositions(playerID, wPos));
                tp.setMinutesPlayedAsIM(tp.getMinutesPlayedAsIM() + ms.getMinutesPlayedInPositions(playerID, mPos));
                tp.setMinutesPlayedAsFW(tp.getMinutesPlayedAsFW() + ms.getMinutesPlayedInPositions(playerID, fwPos));
                tp.setMinutesPlayedAsSP(tp.getMinutesPlayedAsSP() + ms.getMinutesPlayedInPositions(playerID, spPos));

                if (tp.PlayerHasPlayed()) {
                	// Player has played
                    HOLogger.instance().debug(getClass(), "Match "+matchId+": "
                    		+"Player "+spieler.getName()+" ("+playerID+")"
                    		+" played "+ tp.getMinutesPlayedAsGK() + " mins as GK"
                    		+" played "+ tp.getMinutesPlayedAsWB() + " mins as WB"
                    		+" played "+ tp.getMinutesPlayedAsCD() + " mins as CD"
                    		+" played "+ tp.getMinutesPlayedAsW() + " mins as W"
                    		+" played "+ tp.getMinutesPlayedAsIM() + " mins as IM"
                    		+" played "+ tp.getMinutesPlayedAsFW() + " mins as FW"
                    		+" played "+ tp.getMinutesPlayedAsSP() + " mins as SP"
                    		+" played total " + tp.getMinutesPlayed() + " mins"
                    );
                    trainPoints.addTrainingPlayer(tp);
                }
            }
            output.setTrainingPoint(trainPoints);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }

        if (TRAININGDEBUG)
        	HOLogger.instance().debug(getClass(),
        			"End calcWeeklyTraining for "+spieler.getName()+", "+train+", output="+output);
        return output;
    }

    /*
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
        		// in case of no return values
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
    /**
     * internal calculation of training duration
     *
     * @param baseLength base length
     * 		(i.e. the normalized length for a skillup for a 17Y trainee,
     * 		solid coach, 10 co, 100% TI, 0% Stamina)
     * @param age player's age
     * @param trTyp training type
     * @param assistants number of assistants
     * @param trainerLevel trainer level
     * @param intensity training intensity
     * @param stamina stamina share
     *
     * @return length for a single skillup
     */
    protected static double calcTraining(double baseLength, int age, int assistants, int trainerLevel,
                               int intensity, int stamina, int curSkill) {
//    	System.out.println ("calcTraining for "+getName()+", base="+baseLength+", alter="+age+", anzCo="+cotrainer+", train="+trainerLvl+", ti="+intensitaet+", ss="+staminaTrainingPart+", curSkill="+curSkill);
    	double ageFactor = Math.pow(1.0404, age-17) * (ho.core.model.UserParameter.instance().TRAINING_OFFSET_AGE + BASE_AGE_FACTOR);
//    	double skillFactor = 1 + Math.log((curSkill+0.5)/7) / Math.log(5);
    	double skillFactor = - 1.4595 * Math.pow((curSkill+1d)/20, 2) + 3.7535 * (curSkill+1d)/20 - 0.1349d;
    	if (skillFactor < 0) {
    		skillFactor = 0;
    	}
    	double trainerFactor = (1 + (7 - Math.min(trainerLevel, 7.5)) * 0.091) * (ho.core.model.UserParameter.instance().TrainerFaktor + BASE_COACH_FACTOR);
    	double coFactor = (1 + (Math.log(11)/Math.log(10) - Math.log(assistants+1)/Math.log(10)) * 0.2749) * (UserParameter.instance().TRAINING_OFFSET_ASSISTANTS + TrainingManager.BASE_ASSISTANT_COACH_FACTOR);
    	double tiFactor = Double.MAX_VALUE;
    	if (intensity > 0)
    		tiFactor = (1 / (intensity/100d)) * (ho.core.model.UserParameter.instance().TRAINING_OFFSET_INTENSITY + BASE_INTENSITY_FACTOR);
    	double staminaFactor = Double.MAX_VALUE;
    	if (stamina < 100)
    		staminaFactor = 1 / (1 - stamina/100d);
    	double trainLength = baseLength * ageFactor * skillFactor * trainerFactor * coFactor * tiFactor * staminaFactor;
    	if (trainLength < 1)
    		trainLength = 1;
//    	System.out.println ("Factors for "+getName()+": "+ageFactor+"/"+skillFactor+"/"+trainerFactor+"/"+coFactor+"/"+tiFactor+"/"+staminaFactor+" -> "+trainLength);
    	return trainLength;
    }
/////////////////////////////////////////////////7
    //Training ( reflected to skills)
    //TORSCHUSS is skipped due it trains two skills, won't be displayed in gui. Real calc for this is done in Trainingsmanager
    //TA_EXTERNALATTACK skipped due it trains two skills, calc must be done in trainingmanager for each skill seperate like Torschuss.no display in options gui.
    // @return duration of training based on settings and tr type, calls calcTraining for nested calculation
    ///////////////////////////////////////////////////77
    public static double getTrainingLength(Spieler player, int trTyp, int assistants, int trainerLevel, int intensity, int stamina) {
    	UserParameter up = UserParameter.instance();
    	switch (trTyp) {
            case TrainingType.GOALKEEPING:
                return calcTraining(up.TRAINING_OFFSET_GOALKEEPING + BASE_DURATION_GOALKEEPING, 
                		player.getAlter(), assistants, trainerLevel, intensity, stamina, player.getTorwart());

            case TrainingType.PLAYMAKING:
                return calcTraining(up.TRAINING_OFFSET_PLAYMAKING + BASE_DURATION_PLAYMAKING,
                		player.getAlter(), assistants, trainerLevel, intensity, stamina, player.getSpielaufbau());

            case TrainingType.CROSSING_WINGER:
                return calcTraining(up.TRAINING_OFFSET_WINGER + BASE_DURATION_WINGER,
                		player.getAlter(), assistants, trainerLevel, intensity, stamina, player.getFluegelspiel());

            case TrainingType.SCORING:
                return calcTraining(up.TRAINING_OFFSET_SCORING + BASE_DURATION_SCORING,
                		player.getAlter(), assistants, trainerLevel, intensity, stamina, player.getTorschuss());

            case TrainingType.DEFENDING:
                return calcTraining(up.TRAINING_OFFSET_DEFENDING + BASE_DURATION_DEFENDING,
                		player.getAlter(), assistants, trainerLevel, intensity, stamina, player.getVerteidigung());

            case TrainingType.SHORT_PASSES:
				return calcTraining(up.TRAINING_OFFSET_PASSING + BASE_DURATION_PASSING,
						player.getAlter(), assistants, trainerLevel, intensity, stamina, player.getPasspiel());

            case TrainingType.THROUGH_PASSES:
                return calcTraining((100d/85d)*(up.TRAINING_OFFSET_PASSING + BASE_DURATION_PASSING), 
                		player.getAlter(), assistants, trainerLevel, intensity, stamina, player.getPasspiel());

            case TrainingType.SET_PIECES:
                return calcTraining(up.TRAINING_OFFSET_SETPIECES + BASE_DURATION_SET_PIECES,
                		player.getAlter(), assistants, trainerLevel, intensity, stamina, player.getStandards());

            case TrainingType.DEF_POSITIONS:
                return calcTraining(2 * (up.TRAINING_OFFSET_DEFENDING + BASE_DURATION_DEFENDING),
                		player.getAlter(), assistants, trainerLevel, intensity, stamina, player.getVerteidigung());

			case TrainingType.WING_ATTACKS:
				return calcTraining((100d/60d) * (up.TRAINING_OFFSET_WINGER + BASE_DURATION_WINGER),
						player.getAlter(), assistants, trainerLevel, intensity, stamina, player.getFluegelspiel());

            default:
                return -1;
        }
    }
}
