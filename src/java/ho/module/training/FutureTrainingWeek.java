// %68286616:hoplugins.trainingExperience.vo%
package ho.module.training;



/**
 * Training week object, different than the ITrainingWeek since we want to use HT season and week
 * instead of datum, year and solar week
 */
public class FutureTrainingWeek  {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Training staminaTrainingPart */
    private int staminaTraining;

    /** Training intensity */
    private int trainingIntensity;

    /** HT season */
    private int season;

    /** Training type */
    private int trainingType;

    /** HT week */
    private int week;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Set the StaminaTrainingPart
     *
     * @param staminaTrainingPart
     */
    public void setStaminaTrainingPart(int staminaTrainingPart) {
        this.staminaTraining = staminaTrainingPart;
    }

    /**
     * Returns the StaminaTrainingPart
     *
     * @return staminaTrainingPart
     */
    public int getStaminaTrainingPart() {
        return staminaTraining;
    }

    /**
     * Set the training intensity
     *
     * @param intensity
     */
    public void setTrainingIntensity(int intensity) {
        trainingIntensity = intensity;
    }

    /**
     * Returns the training intensity
     *
     * @return intensity
     */
    public int getTrainingIntensity() {
        return trainingIntensity;
    }

    /**
     * Set Hattrick season of the training
     *
     * @param season
     */
    public void setSeason(int season) {
        this.season = season;
    }

    /**
     * Get Hattrick season of the training
     *
     * @return
     */
    public int getSeason() {
        return season;
    }

    /**
     * Set Training type
     *
     * @param type
     */
    public void setTrainingType(int type) {
        trainingType = type;
    }

    /**
     * Get Training type
     *
     * @return type
     */
    public int getTrainingType() {
        return trainingType;
    }

    /**
     * Set Hattrick week of the training
     *
     * @param week
     */
    public void setWeek(int week) {
        this.week = week;
    }

    /**
     * Get Hattrick week of the training
     *
     * @return week
     */
    public int getWeek() {
        return week;
    }

    /**
     * toString method: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("FutureTrainingWeek[");
        buffer.append(" intensity = " + trainingIntensity);
        buffer.append(", stamina = " + staminaTraining);
        buffer.append(", season = " + season);
        buffer.append(", typ = " + trainingType);
        buffer.append(", week = " + week);
        buffer.append("]");

        return buffer.toString();
    }
}
