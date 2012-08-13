package ho.core.model.player;

import java.util.Date;

public interface ISkillup {
	
	/** Real Skillup happened in the past */
	public static final int SKILLUP_REAL = 0;

	/** PRedicted Skillup at maximum training */
	public static final int SKILLUP_FUTURE = 1;
		
	/**
	* Document me!
	*
	* @return
	*/
	public abstract Date getDate();

	/**
	* Document me!
	*
	* @return
	*/
	public abstract int getHtSeason();

	/**
	* Document me!
	*
	* @return
	*/
	public abstract int getHtWeek();

	/**
	* DOCUMENT ME!
	*
	* @return
	*/
	public abstract int getTrainType();

	/**
	* Get Training type
	*
	* @return type
	*/
	public abstract int getType();

	/**
	* Set the new value of the skill
	*
	* @return value
	*/
	public abstract int getValue();
}