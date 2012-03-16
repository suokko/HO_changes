package ho.core.db;

import java.sql.Timestamp;

import plugins.ITeam;
import plugins.IVerein;

/**
 * Adapter to make some basic DBZugriff functions accessible for plugins.
 *
 * @author aik
 */
public class DBAdapter {

	/**
	 * Returns an HRF before the matchData and after previous TrainingTime.
	 *
	 * @param timestamp matchData
	 * @return the ID of the HRF dataset
	 */
	public int getHrfIDSameTraining(Timestamp time) {
		return DBManager.instance().getHrfIDSameTraining(time);
	}

	/**
	 * Returns the Team object for the given hrf dataset.
	 *
	 * @param hrfID HRF for which to load Team
	 * @return the team information for to the given hrf id
	 */
	public ITeam getTeam(int hrfID) {
		return DBManager.instance().getTeam(hrfID);
	}

	/**
	 * Returns the trainer code for the specified hrf. -99 if error.
	 *
	 * @param hrfID HRF for which to load TrainerType
	 * @return constant for the trainer type
	 */
	public int getTrainerType(int hrfID) {
		return DBManager.instance().getTrainerType(hrfID);
	}

	/**
	 * Returns the Verein object for the given hrf dataset
	 *
	 * @param hrfID
	 * @return
	 */
	public IVerein getVerein(int hrfID) {
		return DBManager.instance().getVerein(hrfID);
	}

}
