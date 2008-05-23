// %1127327741290:plugins%
/*
 * ITrainingsManager.java
 *
 * Created on 15. Oktober 2004, 13:14
 */
package plugins;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
/**
 * encapsulates Trainingscalculation and data
 */
public interface ITrainingsManager {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * returns an empty ITrainingPerPlayer instance
     */
	public ITrainingPerPlayer getTrainingPerPlayer();

    /**
     * returns a ITrainingPerPlayer instance for a specific player
     */
	public ITrainingPerPlayer getTrainingPerPlayer(ISpieler player);
	
    /**
     * get a new training point instance
     *
     * @return new training point
     */
    public ITrainingPoint getTrainingPoint();

    /**
     * get a new training point instance
     * initialized with existing ITrainingWeek
     *
     * @return new training point
     */
    public ITrainingPoint getTrainingPoint(ITrainingWeek trainWeek);

    /**
     * get a new training point instance
     * initialized with a new ITrainingWeek created by the arguments
     *
     * @return new training point
     */
    public ITrainingPoint getTrainingPoint (int year, int week, int type, int intensity, int staminaTrainingPart);

    /**
     * returns current TrainingVector calculates new vector if current is null
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector getTrainingsVector();


    ///////////////////////// Lower API, just call if you know what you'return doing :) ///////////////////////////

    /**
     * calculates TrainingWeeks based on given input Vector and sets actual trainingsVector to
     * result of calcultaion
     *
     * @param inputTrainings must be != null, empty vector if no trainings are preset
     *
     * @return Vector of trainingweeks
     */
    public Vector calculateTrainings(Vector inputTrainings);

    /**
     * Method to force a recalculation of decimal subskills
     *
     * @param showBar TODO Missing Constructuor Parameter Documentation
     */
    public void recalcSubskills(boolean showBar);

    /**
     * Calculates how long the player was on the field in the specified match
     * @param matchId	the match to check
     * @param playerId	the player to check
     * @return	number of minutes the player was on the field
     */
    public int getMinutesPlayed (int matchId, int playerId);

    /**
     * Creates a list of matches for the specified training
     * 
     * @param trainingDate	use this trainingDate
     * @return	list of matchIds (type Integer)
     */
    public List getMatchesForTraining (Calendar trainingDate);
    
    /**
     * Returns the base points a player gets for this training type 
     * in a full match at this position
     * @param trainType 	training type
     * @param position		player position id
     * @return base points
     */
    public double getBasePoints (int trainType, int position);

    /**
     * Calculates how long the player was on the field in the specified match
     * @param matchId	the match to check
     * @param playerId	the player to check
     * @return	number of minutes the player was on the field
     */
    public int getMatchPosition (int matchId, int playerId);
}
