// %1127326955619:plugins%
package plugins;

import java.sql.Timestamp;

/**
 * Interface to make some basic DBZugriff functions accessible for plugins.
 *
 * @author aik
 */
public interface IDBAdapter {

	/**
	 * Returns an HRF before the matchData and after previous TrainingTime.
	 * @param timestamp matchData
	 * @return the ID of the HRF dataset
	 */
	int getHrfIDSameTraining(Timestamp time);

	/**
	 * Returns the Team object for the given hrf dataset
	 * @param hrfID HRF for which to load Team
	 * @return the team information for to the given hrf id
	 */
	ITeam getTeam(int hrfID);

	/**
	 * Returns the trainer code for the specified hrf. -99 if error
	 * @param hrfID HRF for which to load TrainerType
	 * @return constant for the trainer type
	 */
	int getTrainerType(int hrfID);
}
