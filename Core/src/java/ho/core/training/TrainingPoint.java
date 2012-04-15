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
     * @param trtype Training type code
     * @param trainingPosition player position code
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
            value = new Double(0);
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

        CROSSING = new Hashtable<Integer,Double>();
        CROSSING.put(new Integer(TrainingPosition.wingBack), new Double(0.5));
        CROSSING.put(new Integer(TrainingPosition.winger), new Double(1.0));

        DEFENSE = new Hashtable<Integer,Double>();
        DEFENSE.put(new Integer(TrainingPosition.wingBack), new Double(1.0));
        DEFENSE.put(new Integer(TrainingPosition.centralDefender), new Double(1.0));

        SHORTPASS = new Hashtable<Integer,Double>();
        SHORTPASS.put(new Integer(TrainingPosition.winger), new Double(1.0));
        SHORTPASS.put(new Integer(TrainingPosition.innerMidfielder), new Double(1.0));
        SHORTPASS.put(new Integer(TrainingPosition.forward), new Double(1.0));

        KEEPER = new Hashtable<Integer,Double>();
        KEEPER.put(new Integer(TrainingPosition.keeper), new Double(1.0));

        SCORING = new Hashtable<Integer,Double>();
        SCORING.put(new Integer(TrainingPosition.forward), new Double(1.0));

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

        THROUGHPASS = new Hashtable<Integer,Double>();
        THROUGHPASS.put(new Integer(TrainingPosition.wingBack), new Double(0.85));
        THROUGHPASS.put(new Integer(TrainingPosition.centralDefender), new Double(0.85));
        THROUGHPASS.put(new Integer(TrainingPosition.winger), new Double(0.85));
        THROUGHPASS.put(new Integer(TrainingPosition.innerMidfielder), new Double(0.85));

        WINGATTACK = new Hashtable<Integer,Double>();
        WINGATTACK.put(new Integer(TrainingPosition.winger), new Double(0.6));
        WINGATTACK.put(new Integer(TrainingPosition.forward), new Double(0.6));
        
        SETPIECES = new Hashtable<Integer,Double>();
        SETPIECES.put(new Integer(TrainingPosition.keeper), new Double(1.25)); // Goalkeepers train 25% faster
        SETPIECES.put(new Integer(TrainingPosition.wingBack), new Double(1.0));
        SETPIECES.put(new Integer(TrainingPosition.centralDefender), new Double(1.0));
        SETPIECES.put(new Integer(TrainingPosition.winger), new Double(1.0));
        SETPIECES.put(new Integer(TrainingPosition.innerMidfielder), new Double(1.0));
        SETPIECES.put(new Integer(TrainingPosition.forward), new Double(1.0));
        SETPIECES.put(new Integer(TrainingPosition.setPiece), new Double(0.25));

        //die einzelnen Trainingsarten hinzuf?gen
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
			int minutes = 0;
			int tmp = 0;
			double basePoints = 0;
			switch (trainWeek.getTrainingType())
			{
				case TrainingType.GOALKEEPING:
					minutes += tp.getMinutesPlayedAsGK();
					if (minutes > 0) {
						basePoints = getTrainingPoint(TrainingType.GOALKEEPING, new Integer(TrainingPosition.keeper)).doubleValue();
					}
					break;
				case TrainingType.DEFENDING:
					minutes = tp.getMinutesPlayedAsCD() + tp.getMinutesPlayedAsWB();
					if (minutes > 0) {
						basePoints = getTrainingPoint(TrainingType.DEFENDING, new Integer(TrainingPosition.centralDefender)).doubleValue();
					}
					break;
				case TrainingType.DEF_POSITIONS:
					tmp = tp.getMinutesPlayedAsGK();
					minutes = tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.DEF_POSITIONS, new Integer(TrainingPosition.keeper)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsCD();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.DEF_POSITIONS, new Integer(TrainingPosition.centralDefender)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsWB();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.DEF_POSITIONS, new Integer(TrainingPosition.wingBack)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsW();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.DEF_POSITIONS, new Integer(TrainingPosition.winger)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsIM();
					minutes += tmp;
					if (minutes > 0) {
						basePoints += getTrainingPoint(TrainingType.DEF_POSITIONS, new Integer(TrainingPosition.innerMidfielder)).doubleValue();
					}
					break;
				case TrainingType.CROSSING_WINGER:
					tmp = tp.getMinutesPlayedAsW();
					minutes = tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.CROSSING_WINGER, new Integer(TrainingPosition.winger)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsWB();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.CROSSING_WINGER, new Integer(TrainingPosition.wingBack)).doubleValue();
					}
					break;
				case TrainingType.WING_ATTACKS:
					tmp = tp.getMinutesPlayedAsW();
					minutes = tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.WING_ATTACKS, new Integer(TrainingPosition.winger)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsFW();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.WING_ATTACKS, new Integer(TrainingPosition.forward)).doubleValue();
					}
					break;
				case TrainingType.PLAYMAKING:
					tmp = tp.getMinutesPlayedAsIM();
					minutes = tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.PLAYMAKING, new Integer(TrainingPosition.innerMidfielder)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsW();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.PLAYMAKING, new Integer(TrainingPosition.winger)).doubleValue();
					}
					break;
				case TrainingType.SHORT_PASSES:
					tmp = tp.getMinutesPlayedAsIM();
					minutes = tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SHORT_PASSES, new Integer(TrainingPosition.innerMidfielder)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsW();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SHORT_PASSES, new Integer(TrainingPosition.winger)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsFW();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SHORT_PASSES, new Integer(TrainingPosition.forward)).doubleValue();
					}
					break;
				case TrainingType.THROUGH_PASSES:
					tmp = tp.getMinutesPlayedAsWB();
					minutes = tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.THROUGH_PASSES, new Integer(TrainingPosition.wingBack)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsCD();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.THROUGH_PASSES, new Integer(TrainingPosition.centralDefender)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsW();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.THROUGH_PASSES, new Integer(TrainingPosition.winger)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsIM();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.THROUGH_PASSES, new Integer(TrainingPosition.innerMidfielder)).doubleValue();
					}
					break;
				case TrainingType.SCORING:
					minutes = tp.getMinutesPlayedAsFW();
					if (minutes > 0) {
						basePoints += getTrainingPoint(TrainingType.SCORING, new Integer(TrainingPosition.forward)).doubleValue();
					}
					break;
				case TrainingType.SHOOTING:
					tmp = tp.getMinutesPlayedAsGK();
					minutes = tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SHOOTING, new Integer(TrainingPosition.keeper)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsWB();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SHOOTING, new Integer(TrainingPosition.wingBack)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsCD();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SHOOTING, new Integer(TrainingPosition.centralDefender)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsW();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SHOOTING, new Integer(TrainingPosition.winger)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsIM();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SHOOTING, new Integer(TrainingPosition.innerMidfielder)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsFW();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SHOOTING, new Integer(TrainingPosition.forward)).doubleValue();
					}
					break;
				case TrainingType.SET_PIECES:
					tmp = tp.getMinutesPlayedAsGK();
					minutes = tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SET_PIECES, new Integer(TrainingPosition.keeper)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsWB();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SET_PIECES, new Integer(TrainingPosition.wingBack)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsCD();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SET_PIECES, new Integer(TrainingPosition.centralDefender)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsW();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SET_PIECES, new Integer(TrainingPosition.winger)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsIM();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SET_PIECES, new Integer(TrainingPosition.innerMidfielder)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsFW();
					minutes += tmp;
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SET_PIECES, new Integer(TrainingPosition.forward)).doubleValue();
					}
					tmp = tp.getMinutesPlayedAsSP();
					// Don't add to minutes played, as it's already added elsewhere.
					if (tmp > 0) {
						basePoints += getTrainingPoint(TrainingType.SET_PIECES, new Integer(TrainingPosition.setPiece)).doubleValue();
					}
					break;
			}
			//System.out.println ("Match added: train="+trainWeek.getTyp()+", min="+minutes+", bP="+basePoints);
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
}
