// %1043029125:de.hattrickorganizer.logik%
package ho.core.training;

import ho.core.model.ISpielerPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;

import plugins.ITeam;


/**
 * Class that manages the relation between position and training points
 * 
 * It also is manages the calculation of played minutes for a player 
 * and its relation to the base points.  
 *
 * @author Draghetto HO
 */
class TrainingPoint {
	/**
	 * Private class to represent a single match for the training,
	 * it consists of a minutes/value pair
	 * 
	 * @author flattermann <drake79@users.sourceforge.net>
	 *
	 */
	private class MatchForTraining implements Comparable<Object> {
		private int minutes;
		private double basePoints;
		
		public MatchForTraining(int minutes, double basePoints) {
			this.minutes = minutes;
			this.basePoints = basePoints;
		}
		public int getMinutes() {
			return minutes;
		}
		public void setMinutes(int minutes) {
			this.minutes = minutes;
		}
		public double getBasePoints() {
			return basePoints;
		}
		public void setBasePoints(double basePoints) {
			this.basePoints = basePoints;
		}
		/**
		 * Compare function to sort the elements in descending order
		 */
		public int compareTo (Object obj) {
			MatchForTraining comparing = (MatchForTraining)obj;
			if (this.basePoints > comparing.basePoints)
				return -1;
			else if (this.basePoints < comparing.basePoints)
				return 1;
			else
				return 0;
		}
	}
    //~ Instance fields ----------------------------------------------------------------------------

    //Properties of matches to process for this training (minutes & basePoints)
    //The list is automatically sorted by basePoints (high to low)
    private ArrayList<MatchForTraining> matchesForTraining = new ArrayList<MatchForTraining>();

    //	Abwehrverhalten alle Verteidiger und Mittelfeldspieler in Verteidigung mit 50% 
    //Defensive Positions: all defenders and playmakers 50% defense
    private Hashtable<Integer,Double> DEFPOS;

    //	Chancenauswertung -> Torschuss 100% St?rmer 
    //Scoring: 100% scoring forwards 
    private Hashtable<Integer,Double> SCORING;

    private Hashtable<Integer,Double> WINGATTACK = null; //External Attacker
    
    //Flankel?ufe // Wing
    private Hashtable<Integer,Double> CROSSING;

    //Passspiel //Passing
    private Hashtable<Integer,Double> SHORTPASS;

    //Spielaufbau // playmaking
    private Hashtable<Integer,Double> PLAYMAKING;

    //scoring
    private Hashtable<Integer,Double> SHOOTING;

    //Steilpaesse : Passspiel f?r alle Verteidiger und MFler mit 85% // Through passess: passing for all defenders and midfielders with 85%
    private Hashtable<Integer,Double> THROUGHPASS;

    //Torwart //Keeper
    private Hashtable<Integer,Double> KEEPER;

    //Verteidigung //Defense
    private Hashtable<Integer,Double> DEFENSE;
    
    //Standards //Set Pieces
    private Hashtable<Integer,Double> SETPIECES;

    //Order is the same as in Data. As value only SpielerIDS (do i have to translate this?) -> same index for same players
    private Hashtable<Integer,Hashtable<Integer,Double>> p_Ht_trainPositionen = new Hashtable<Integer,Hashtable<Integer,Double>>();

	private TrainingPerWeek trainWeek;

	//~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingPoint object.
     */
    public TrainingPoint() {
        init();
    }

    public TrainingPoint(TrainingPerWeek trainWeek) {
    	this.trainWeek = trainWeek;
        init();
    }
    public TrainingPoint (int year, int week, int type, int intensity, int staminaTrainingPart) {
    	this.trainWeek = new TrainingPerWeek(week, year, type, intensity, staminaTrainingPart);
    	init();
    }
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Mathod that returns the correct training point
     *
     * @param trtype Training type code
     * @param playerMatchPosition player position code
     *
     * @return training points earned in that match
     */
    public final Double getTrainingPoint(int trtype, Integer playerMatchPosition) {
        final Hashtable<Integer,Double> positions = p_Ht_trainPositionen.get(new Integer(trtype));

        if (positions == null) {
            return new Double(0);
        }

        Double value = positions.get(playerMatchPosition);

        if (value == null) {
            value = new Double(0);
        }

        return value;
    }

    /**
     * Method that initialize the Class Data
     */
    private void init() {
        //F?r jedes TRainingsart die Positionen und Werte festlegen
        //for every traintype position and values definition
       
    	
    	PLAYMAKING = new Hashtable<Integer,Double>();
        PLAYMAKING.put(new Integer(ISpielerPosition.leftWinger), new Double(0.5));
        PLAYMAKING.put(new Integer(ISpielerPosition.leftInnerMidfield), new Double(1.0));
        PLAYMAKING.put(new Integer(ISpielerPosition.centralInnerMidfield), new Double(1.0));
        PLAYMAKING.put(new Integer(ISpielerPosition.rightInnerMidfield), new Double(1.0));
        PLAYMAKING.put(new Integer(ISpielerPosition.rightWinger), new Double(0.5));

        CROSSING = new Hashtable<Integer,Double>();
        CROSSING.put(new Integer(ISpielerPosition.rightBack), new Double(0.5));
        CROSSING.put(new Integer(ISpielerPosition.leftBack), new Double(0.5));
        CROSSING.put(new Integer(ISpielerPosition.rightWinger), new Double(1.0));
        CROSSING.put(new Integer(ISpielerPosition.leftWinger), new Double(1.0));

        DEFENSE = new Hashtable<Integer,Double>();
        DEFENSE.put(new Integer(ISpielerPosition.leftBack), new Double(1.0));
        DEFENSE.put(new Integer(ISpielerPosition.leftCentralDefender), new Double(1.0));
        DEFENSE.put(new Integer(ISpielerPosition.middleCentralDefender), new Double(1.0));
        DEFENSE.put(new Integer(ISpielerPosition.rightCentralDefender), new Double(1.0));
        DEFENSE.put(new Integer(ISpielerPosition.rightBack), new Double(1.0));

        SHORTPASS = new Hashtable<Integer,Double>();
        SHORTPASS.put(new Integer(ISpielerPosition.leftWinger), new Double(1.0));
        SHORTPASS.put(new Integer(ISpielerPosition.leftInnerMidfield), new Double(1.0));
        SHORTPASS.put(new Integer(ISpielerPosition.centralInnerMidfield), new Double(1.0));
        SHORTPASS.put(new Integer(ISpielerPosition.rightInnerMidfield), new Double(1.0));
        SHORTPASS.put(new Integer(ISpielerPosition.rightWinger), new Double(1.0));
        SHORTPASS.put(new Integer(ISpielerPosition.leftForward), new Double(1.0));
        SHORTPASS.put(new Integer(ISpielerPosition.centralForward), new Double(1.0));
        SHORTPASS.put(new Integer(ISpielerPosition.rightForward), new Double(1.0));

        KEEPER = new Hashtable<Integer,Double>();
        KEEPER.put(new Integer(ISpielerPosition.keeper), new Double(1.0));

        SCORING = new Hashtable<Integer,Double>();
        SCORING.put(new Integer(ISpielerPosition.leftForward), new Double(1.0));
        SCORING.put(new Integer(ISpielerPosition.centralForward), new Double(1.0));
        SCORING.put(new Integer(ISpielerPosition.rightForward), new Double(1.0));

        SHOOTING = new Hashtable<Integer,Double>();
        SHOOTING.put(new Integer(ISpielerPosition.keeper), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.leftBack), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.leftCentralDefender), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.middleCentralDefender), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.rightCentralDefender), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.rightBack), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.leftWinger), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.leftInnerMidfield), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.centralInnerMidfield), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.rightInnerMidfield), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.rightWinger), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.leftForward), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.centralForward), new Double(0.6));
        SHOOTING.put(new Integer(ISpielerPosition.rightForward), new Double(0.6));

        DEFPOS = new Hashtable<Integer,Double>();
        DEFPOS.put(new Integer(ISpielerPosition.keeper), new Double(0.5));
        DEFPOS.put(new Integer(ISpielerPosition.leftBack), new Double(0.5));
        DEFPOS.put(new Integer(ISpielerPosition.leftCentralDefender), new Double(0.5));
        DEFPOS.put(new Integer(ISpielerPosition.middleCentralDefender), new Double(0.5));
        DEFPOS.put(new Integer(ISpielerPosition.rightCentralDefender), new Double(0.5));
        DEFPOS.put(new Integer(ISpielerPosition.rightBack), new Double(0.5));
        DEFPOS.put(new Integer(ISpielerPosition.leftWinger), new Double(0.5));
        DEFPOS.put(new Integer(ISpielerPosition.leftInnerMidfield), new Double(0.5));
        DEFPOS.put(new Integer(ISpielerPosition.centralInnerMidfield), new Double(0.5));
        DEFPOS.put(new Integer(ISpielerPosition.rightInnerMidfield), new Double(0.5));
        DEFPOS.put(new Integer(ISpielerPosition.rightWinger), new Double(0.5));

        THROUGHPASS = new Hashtable<Integer,Double>();
        THROUGHPASS.put(new Integer(ISpielerPosition.leftBack), new Double(0.85));
        THROUGHPASS.put(new Integer(ISpielerPosition.leftCentralDefender), new Double(0.85));
        THROUGHPASS.put(new Integer(ISpielerPosition.middleCentralDefender), new Double(0.85));
        THROUGHPASS.put(new Integer(ISpielerPosition.rightCentralDefender), new Double(0.85));
        THROUGHPASS.put(new Integer(ISpielerPosition.rightBack), new Double(0.85));
        THROUGHPASS.put(new Integer(ISpielerPosition.leftWinger), new Double(0.85));
        THROUGHPASS.put(new Integer(ISpielerPosition.leftInnerMidfield), new Double(0.85));
        THROUGHPASS.put(new Integer(ISpielerPosition.centralInnerMidfield), new Double(0.85));
        THROUGHPASS.put(new Integer(ISpielerPosition.rightInnerMidfield), new Double(0.85));
        THROUGHPASS.put(new Integer(ISpielerPosition.rightWinger), new Double(0.85));

        WINGATTACK = new Hashtable<Integer,Double>();
        WINGATTACK.put(new Integer(ISpielerPosition.leftWinger), new Double(0.6));
        WINGATTACK.put(new Integer(ISpielerPosition.rightWinger), new Double(0.6));        
        WINGATTACK.put(new Integer(ISpielerPosition.leftForward), new Double(0.6));
        WINGATTACK.put(new Integer(ISpielerPosition.centralForward), new Double(0.6));
        WINGATTACK.put(new Integer(ISpielerPosition.rightForward), new Double(0.6));
        
        // TODO flattermann: The default SetPieces taker gets 25% bonus as well
        SETPIECES = new Hashtable<Integer,Double>();
        SETPIECES.put(new Integer(ISpielerPosition.keeper), new Double(1.25)); // Goalkeepers train 25% faster
        SETPIECES.put(new Integer(ISpielerPosition.leftBack), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.leftCentralDefender), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.middleCentralDefender), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.rightCentralDefender), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.rightBack), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.leftWinger), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.leftInnerMidfield), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.centralInnerMidfield), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.rightInnerMidfield), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.rightWinger), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.leftForward), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.centralForward), new Double(1.0));
        SETPIECES.put(new Integer(ISpielerPosition.rightForward), new Double(1.0));

        //die einzelnen Trainingsarten hinzuf?gen
        //add all traintypes to one hashtable with all trainingstypes
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_SPIELAUFBAU), PLAYMAKING);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_FLANKEN), CROSSING);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_VERTEIDIGUNG), DEFENSE);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_PASSSPIEL), SHORTPASS);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_TORWART), KEEPER);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_CHANCEN), SCORING);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_SCHUSSTRAINING), SHOOTING);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_ABWEHRVERHALTEN), DEFPOS);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_STEILPAESSE), THROUGHPASS);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_EXTERNALATTACK), WINGATTACK);
        p_Ht_trainPositionen.put(new Integer(ITeam.TA_STANDARD), SETPIECES);
    }

    /**
	 * Adds a match to the internal list
	 * 
	 * @param minutes	How long was the player on the field in this match
	 * @param posId		Position of the player in this match
	 */
	public void addTrainingMatch (int minutes, int posId) {
		if (minutes > 0 && posId > 0) {
			double basePoints = getTrainingPoint(trainWeek.getTyp(), new Integer (posId)).doubleValue();
//			System.out.println ("Match added: train="+trainWeek.getTyp()+", min="+minutes+", pos="+posId+", bP="+basePoints);
			matchesForTraining.add(new MatchForTraining(minutes, basePoints));
			Collections.sort(matchesForTraining);
		}
	}

    /**
     * Calculate how many points the player gets for this week,
     * using the matches previously added with addTrainingMatch(min, posId)
     * 
     * Optimal: 100% position + 90mins -> points = 1.0
     * 
     * @param ignorePosition	Ignore players position, otherwise use only minutes on correct position
     * @return training points for this player and week 
     */
    public double calcTrainingPoints (boolean ignorePosition) {
    	int minutesLeft = 90;
    	double points = 0;
    	// matchesForTraining is already sorted by baseLength in descending order 
    	Iterator<MatchForTraining> iter = matchesForTraining.iterator();
    	while (iter.hasNext() && minutesLeft > 0) {
    		MatchForTraining curMatch = iter.next();
    		double curBasePoints = curMatch.getBasePoints();
    		int curMinutes = curMatch.getMinutes();
    		// use up to minutesLeft mins
    		curMinutes = Math.min (curMinutes, minutesLeft);
    		// Shall we ignore the player position?
    		if (ignorePosition)
    			curBasePoints = 1;
    		double curPoints = curBasePoints * curMinutes/90d;
//    		System.out.println("calcTrP: curPoints="+curPoints);
   			points += curPoints;
   			minutesLeft -= curMinutes;
    	}
//    	System.out.println ("calcTrP: final points="+points);
    	return points;
    }

    /**
     * Returns the training week for this training point
     */
	public TrainingPerWeek getTrainWeek() {
		return trainWeek;
	}

    /**
     * Sets the training week for this training point
     */
	public void setTrainWeek(TrainingPerWeek trainWeek) {
		this.trainWeek = trainWeek;
	}
}
