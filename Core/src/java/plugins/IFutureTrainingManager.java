package plugins;

import ho.core.model.UserParameter;

import java.util.List;

/**
 * Main class for prediction of Training Effect
 * @author Draghetto
 */
public interface IFutureTrainingManager {

	public static int FUTUREWEEKS = UserParameter.instance().futureWeeks; 
	/**
	 * Return the predicted player skills after n weeks
	 * @param weekNumber the number of weeks
	 * @return The prediction
	 */	
	IFuturePlayer previewPlayer(int weekNumber);

	/**
	 * Return the predicted player skills after n weeks
	 * @param startWeekNumber week to start calculating training from
	 * @param finalWeekNumber week to end calculating training from 
	 * @return The prediction
	 */	
	IFuturePlayer previewPlayer(int startWeekNumber,int finalWeekNumber);
	
	/**
	* Returns a list of all future skillups as predicted
	*
	* @return List of Skillups
	*/
	List<ISkillup> getFutureSkillups();
	
}