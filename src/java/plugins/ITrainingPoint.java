// %1127327012462:plugins%
/*
 * ITrainingPoint.java
 *
 * Created on 29. März 2005, 15:10
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface ITrainingPoint {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Mathod that returns the correct training point
     *
     * @param trtype Training type code
     * @param playerMatchPosition player position code
     *
     * @return training points earned in that match
     */
    public Double getTrainingPoint(int trtype, Integer playerMatchPosition);

    /**
	 * Adds a match to the internal list
	 * 
	 * @param minutes	How long was the player on the field in this match
	 * @param posId		Position of the player in this match
	 */
    public void addTrainingMatch (int minutes, int posId);
	
    /**
     * Calculate how many points the player gets for this week,
     * using the matches previously added with addTrainingMatch(min, posId)
     * 
     * Optimal: 100% position + 90mins -> points = 1.0
     * 
     * @param ignorePosition	Ignore players position, otherwise use only minutes on correct position
     * @return training points for this player and week 
     */
    public double calcTrainingPoints (boolean ignorePosition);

    /**
     * Returns the training week for this training point
     */
	public ITrainingWeek getTrainWeek();
}
