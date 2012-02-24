// %1126721452151:hoplugins.trainingExperience.vo%
package ho.core.model;

import plugins.IFutureTrainingWeek;


/**
 * Training week object, different than the ITrainingWeek since we want to use HT season and week
 * instead of datum, year and solar week
 */
public class FutureTrainingWeek implements IFutureTrainingWeek {
	
    /** Training staminaTrainingPart */
    private int staminaTrainingPart;

    /** Training intensity */
    private int intensitaet;

    /** HT season */
    private int season;

    /** Training type */
    private int typ;

    /** HT week */
    private int week;

    /**
     * Set the StaminaTrainingPart
     *
     * @param staminaTrainingPart
     */
    public void setStaminaTrainingPart(int staminaTrainingPart) {
        this.staminaTrainingPart = staminaTrainingPart;
    }

    /**
     * Returns the StaminaTrainingPart
     *
     * @return staminaTrainingPart
     */
    public int getStaminaTrainingPart() {
        return staminaTrainingPart;
    }
    
    /**
* Set the training intensity
*
* @param intensity
*/
    public void setIntensitaet(int intensity)
    {
        intensitaet = intensity;
    }

    /**
* Returns the training intensity
*
* @return intensity
*/
    public int getIntensitaet()
    {
        return intensitaet;
    }

    /**
* Set Hattrick season of the training
*
* @param season
*/
    public void setSeason(int season)
    {
        this.season = season;
    }

    /**
* Get Hattrick season of the training
*
* @return
*/
    public int getSeason()
    {
        return season;
    }

    /**
* Set Training type
*
* @param type
*/
    public void setTyp(int type)
    {
        typ = type;
    }

    /**
* Get Training type
*
* @return type
*/
    public int getTyp()
    {
        return typ;
    }

    /**
* Set Hattrick week of the training
*
* @param week
*/
    public void setWeek(int week)
    {
        this.week = week;
    }

    /**
* Get Hattrick week of the training
*
* @return week
*/
    public int getWeek()
    {
        return week;
    }

    /**
* toString methode: creates a String representation of the object
*
* @return the String representation
*/
    @Override
	public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("HattrickTrainingWeek[");
        buffer.append("intensitaet = " + intensitaet);
        buffer.append(", staminaTrainingPart  = " + staminaTrainingPart);
        buffer.append(", season = " + season);
        buffer.append(", typ = " + typ);
        buffer.append(", week = " + week);
        buffer.append("]");

        return buffer.toString();
    }
}
