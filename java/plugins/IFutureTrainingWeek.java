package plugins;

public interface IFutureTrainingWeek {
	/**
	* Set the StaminaTrainingPart
	*
	* @param staminaTrainingPart
	*/
	public abstract void setStaminaTrainingPart(int staminaTrainingPart);
	/**
	* Returns the StaminaTrainingPart
	*
	* @return staminaTrainingPart
	*/
	public abstract int getStaminaTrainingPart();
	/**
	* Set the training intensity
	*
	* @param intensity
	*/
	public abstract void setIntensitaet(int intensity);
	/**
	* Returns the training intensity
	*
	* @return intensity
	*/
	public abstract int getIntensitaet();
	/**
	* Set Hattrick season of the training
	*
	* @param season
	*/
	public abstract void setSeason(int season);
	/**
	* Get Hattrick season of the training
	*
	* @return
	*/
	public abstract int getSeason();
	/**
	* Set Training type
	*
	* @param type
	*/
	public abstract void setTyp(int type);
	/**
	* Get Training type
	*
	* @return type
	*/
	public abstract int getTyp();
	/**
	* Set Hattrick week of the training
	*
	* @param week
	*/
	public abstract void setWeek(int week);
	/**
	* Get Hattrick week of the training
	*
	* @return week
	*/
	public abstract int getWeek();
}