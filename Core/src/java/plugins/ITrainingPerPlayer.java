// %1127327012447:plugins%
/*
 * ITrainingPerPlayer.java
 *
 * Created on 21. Oktober 2004, 15:17
 */
package plugins;

import java.util.Date;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface ITrainingPerPlayer {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Getter for property FL.
     *
     * @return Value of property FL.
     */
    public double getFL();

    /**
     * returns stamania
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getKO();

    /**
     * Getter for property PS.
     *
     * @return Value of property PS.
     */
    public double getPS();

    /**
     * Getter for property SA.
     *
     * @return Value of property SA.
     */
    public double getSA();

    /**
     * Getter for property ST.
     *
     * @return Value of property ST.
     */
    public double getST();

    /**
     * Getter for property spieler.
     *
     * @return Value of property spieler.
     */
    public plugins.ISpieler getSpieler();

    /**
     * Getter for property TS.
     *
     * @return Value of property TS.
     */
    public double getTS();

    /**
     * Getter for property TW.
     *
     * @return Value of property TW.
     */
    public double getTW();

    /**
     * Getter for property VE.
     *
     * @return Value of property VE.
     */
    public double getVE();

    /**
     * add sub values of another ITrainingPerPlayer instance to this instance
     * @param values	the instance we take the values from
     */
    public void addValues (ITrainingPerPlayer values);

    /**
     * Returns the training (sub)skill for a specific skill type
     * 
     * @param skillType		skill type from ISpieler.SKILL_*
     * @return				the (sub)skill for this skill type
     */
    public double getSkillValue (int skillType);

	/**
	 * Set the timestamp
	 * if not null, calculate sub increase for this training date only
	 * 
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp);

    /**
	 * get the training point for this instance
	 * @return	training point
	 */
	public ITrainingPoint getTrainPoint();

	/**
	 * set the training point for this instance and
	 * calculate the sub skills for the player using 
	 * the training week from this training point
	 *  
	 * @param trainPoint	training point
	 */
	public void setTrainPoint(ITrainingPoint trainPoint);

}
