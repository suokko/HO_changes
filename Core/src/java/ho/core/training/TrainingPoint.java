// %1043029125:de.hattrickorganizer.logik%
package ho.core.training;

import ho.core.constants.TrainingType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;


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
		private double basePoints;
		
		public MatchForTraining(double basePoints) {
			this.basePoints = basePoints;
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

    //Defensive Positions
    private Hashtable<Integer,Double> DEFPOS;

    //Scoring 
    private Hashtable<Integer,Double> SCORING;
    
    //Wing Attacks
    private Hashtable<Integer,Double> WINGATTACK;
    
    //Crossing
    private Hashtable<Integer,Double> CROSSING;

    //Short Passes
    private Hashtable<Integer,Double> SHORTPASS;

    //Playmaking
    private Hashtable<Integer,Double> PLAYMAKING;

    //Scoring
    private Hashtable<Integer,Double> SHOOTING;

    //Through Passes
    private Hashtable<Integer,Double> THROUGHPASS;

    //Keeper
    private Hashtable<Integer,Double> KEEPER;

    //Defense
    private Hashtable<Integer,Double> DEFENSE;
    
    //Set Pieces
    private Hashtable<Integer,Double> SETPIECES;

    //Order is the same as in Data. As value only SpielerIDs (do i have to translate this?) -> same index for same players
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
     * @param trtype Training type
     * @param trainingPosition player position
     *
     * @return training points earned in that match
     */
    public final Double getTrainingPoint(int trtype, Integer trainingPosition) {
        final Hashtable<Integer,Double> positions = p_Ht_trainPositionen.get(new Integer(trtype));

        if (positions == null) {
            return new Double(0);
        }

        Double value = positions.get(trainingPosition);
        if (value == null) {
        	trainingPosition = TrainingPosition.osmosis;
        	value = positions.get(trainingPosition);
        	if (value == null) {
        		value = new Double(0);
        	}
        }

        return value;
    }

    /**
     * Method that initialize the Class Data
     */
    private void init() {
        //Define training for every position
    	PLAYMAKING = new Hashtable<Integer,Double>();
        PLAYMAKING.put(new Integer(TrainingPosition.winger), new Double(0.5));
        PLAYMAKING.put(new Integer(TrainingPosition.innerMidfielder), new Double(1.0));
    	PLAYMAKING.put(new Integer(TrainingPosition.osmosis), new Double(0.16));

        CROSSING = new Hashtable<Integer,Double>();
        CROSSING.put(new Integer(TrainingPosition.wingBack), new Double(0.5));
        CROSSING.put(new Integer(TrainingPosition.winger), new Double(1.0));
        CROSSING.put(new Integer(TrainingPosition.osmosis), new Double(0.16));
        
        DEFENSE = new Hashtable<Integer,Double>();
        DEFENSE.put(new Integer(TrainingPosition.wingBack), new Double(1.0));
        DEFENSE.put(new Integer(TrainingPosition.centralDefender), new Double(1.0));
        DEFENSE.put(new Integer(TrainingPosition.osmosis), new Double(0.16));
        
        SHORTPASS = new Hashtable<Integer,Double>();
        SHORTPASS.put(new Integer(TrainingPosition.winger), new Double(1.0));
        SHORTPASS.put(new Integer(TrainingPosition.innerMidfielder), new Double(1.0));
        SHORTPASS.put(new Integer(TrainingPosition.forward), new Double(1.0));
        SHORTPASS.put(new Integer(TrainingPosition.osmosis), new Double(0.16));

        KEEPER = new Hashtable<Integer,Double>();
        KEEPER.put(new Integer(TrainingPosition.keeper), new Double(1.0));

        SCORING = new Hashtable<Integer,Double>();
        SCORING.put(new Integer(TrainingPosition.forward), new Double(1.0));
        SCORING.put(new Integer(TrainingPosition.osmosis), new Double(0.16));

        SHOOTING = new Hashtable<Integer,Double>();
        SHOOTING.put(new Integer(TrainingPosition.keeper), new Double(0.6));
        SHOOTING.put(new Integer(TrainingPosition.wingBack), new Double(0.6));
        SHOOTING.put(new Integer(TrainingPosition.centralDefender), new Double(0.6));
        SHOOTING.put(new Integer(TrainingPosition.winger), new Double(0.6));
        SHOOTING.put(new Integer(TrainingPosition.innerMidfielder), new Double(0.6));
        SHOOTING.put(new Integer(TrainingPosition.forward), new Double(0.6));

        DEFPOS = new Hashtable<Integer,Double>();
        DEFPOS.put(new Integer(TrainingPosition.keeper), new Double(0.5));
        DEFPOS.put(new Integer(TrainingPosition.wingBack), new Double(0.5));
        DEFPOS.put(new Integer(TrainingPosition.centralDefender), new Double(0.5));
        DEFPOS.put(new Integer(TrainingPosition.winger), new Double(0.5));
        DEFPOS.put(new Integer(TrainingPosition.innerMidfielder), new Double(0.5));
        DEFPOS.put(new Integer(TrainingPosition.osmosis), new Double(0.16));

        THROUGHPASS = new Hashtable<Integer,Double>();
        THROUGHPASS.put(new Integer(TrainingPosition.wingBack), new Double(0.85));
        THROUGHPASS.put(new Integer(TrainingPosition.centralDefender), new Double(0.85));
        THROUGHPASS.put(new Integer(TrainingPosition.winger), new Double(0.85));
        THROUGHPASS.put(new Integer(TrainingPosition.innerMidfielder), new Double(0.85));
        THROUGHPASS.put(new Integer(TrainingPosition.osmosis), new Double(0.16));
        
        WINGATTACK = new Hashtable<Integer,Double>();
        WINGATTACK.put(new Integer(TrainingPosition.winger), new Double(0.6));
        WINGATTACK.put(new Integer(TrainingPosition.forward), new Double(0.6));
        WINGATTACK.put(new Integer(TrainingPosition.osmosis), new Double(0.16));
        
        SETPIECES = new Hashtable<Integer,Double>();
        SETPIECES.put(new Integer(TrainingPosition.keeper), new Double(1.25)); // Goalkeepers train 25% faster
        SETPIECES.put(new Integer(TrainingPosition.wingBack), new Double(1.0));
        SETPIECES.put(new Integer(TrainingPosition.centralDefender), new Double(1.0));
        SETPIECES.put(new Integer(TrainingPosition.winger), new Double(1.0));
        SETPIECES.put(new Integer(TrainingPosition.innerMidfielder), new Double(1.0));
        SETPIECES.put(new Integer(TrainingPosition.forward), new Double(1.0));
        SETPIECES.put(new Integer(TrainingPosition.setPiece), new Double(0.25));

        //add all traintypes to one hashtable with all trainingstypes
        p_Ht_trainPositionen.put(new Integer(TrainingType.PLAYMAKING), PLAYMAKING);
        p_Ht_trainPositionen.put(new Integer(TrainingType.CROSSING_WINGER), CROSSING);
        p_Ht_trainPositionen.put(new Integer(TrainingType.DEFENDING), DEFENSE);
        p_Ht_trainPositionen.put(new Integer(TrainingType.SHORT_PASSES), SHORTPASS);
        p_Ht_trainPositionen.put(new Integer(TrainingType.GOALKEEPING), KEEPER);
        p_Ht_trainPositionen.put(new Integer(TrainingType.SCORING), SCORING);
        p_Ht_trainPositionen.put(new Integer(TrainingType.SHOOTING), SHOOTING);
        p_Ht_trainPositionen.put(new Integer(TrainingType.DEF_POSITIONS), DEFPOS);
        p_Ht_trainPositionen.put(new Integer(TrainingType.THROUGH_PASSES), THROUGHPASS);
        p_Ht_trainPositionen.put(new Integer(TrainingType.WING_ATTACKS), WINGATTACK);
        p_Ht_trainPositionen.put(new Integer(TrainingType.SET_PIECES), SETPIECES);
    }

    /**
	 * Adds a player to the internal list
	 * 
	 * @param tp	TrainingPlayer
	 */
	public void addTrainingPlayer(TrainingPlayer tp) {
		if (tp.PlayerHasPlayed()) {
			tp.CalculateTrainingPoints(this, trainWeek.getTrainingType());
			//System.out.println ("Match added: train="+trainWeek.getTyp()+", min="+minutes+", bP="+basePoints);
			//System.out.println("Name " + tp.Name() + " Minutes played " + tp.getMinutesPlayed() + " base point: " + basePoints);
			matchesForTraining.add(new MatchForTraining(tp.getTrainingPoints()));
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
    public double calcTrainingPoints() {
    	double points = 0;
    	// matchesForTraining is already sorted by baseLength in descending order 
    	Iterator<MatchForTraining> iter = matchesForTraining.iterator();
    	while (iter.hasNext()) {
    		MatchForTraining curMatch = iter.next();
    		double curBasePoints = curMatch.getBasePoints();
    		//System.out.println("calcTrP: curPoints="+curPoints);
   			points += curMatch.getBasePoints();
    	}
    	//System.out.println ("calcTrP: final points="+points);
    	return points;
    }

    /**
     * Returns the training week for this training point
     */
	public TrainingPerWeek getTrainWeek() {
		return trainWeek;
	}
}
