package ho.module.training.ui.model;

import ho.core.model.player.Spieler;
import ho.core.training.FutureTrainingManager;
import ho.core.training.TrainingPerWeek;
import ho.module.training.OldTrainingManager;
import ho.module.training.TrainingPanel;
import ho.module.training.ui.StaffPanel;

import java.util.ArrayList;
import java.util.List;

public class TrainingModel {

	/** The currently selected player */
	private Spieler activePlayer;
	private int numberOfCoTrainers;
	/** the current level of the coach */
	private int trainerLevel;
	private OldTrainingManager skillupManager;
	private FutureTrainingManager futureTrainingManager;
	private final List<ModelChangeListener> listeners = new ArrayList<ModelChangeListener>();

	public Spieler getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(Spieler player) {
		if ((this.activePlayer == null && player != null)
				|| (this.activePlayer != null && player == null)
				|| (this.activePlayer != null && player != null && this.activePlayer.getSpielerID() != player
						.getSpielerID())) {
			this.activePlayer = player;
			this.skillupManager = null;
			this.futureTrainingManager = null;
			fireActivePlayerChanged(ModelChange.ACTIVE_PLAYER);
		}
	}

	public int getNumberOfCoTrainers() {
		return numberOfCoTrainers;
	}

	public void setNumberOfCoTrainers(int numberOfCoTrainers) {
		if (this.numberOfCoTrainers != numberOfCoTrainers) {
			this.numberOfCoTrainers = numberOfCoTrainers;
			fireActivePlayerChanged(ModelChange.NUMBER_OF_CO_TRAINERS);
		}
	}

	public int getTrainerLevel() {
		return trainerLevel;
	}

	public void setTrainerLevel(int trainerLevel) {
		if (this.trainerLevel != trainerLevel) {
			this.trainerLevel = trainerLevel;
			fireActivePlayerChanged(ModelChange.TRAINER_LEVEL);
		}
	}

	public OldTrainingManager getSkillupManager() {
		if (this.skillupManager == null) {
			this.skillupManager = new OldTrainingManager(this.activePlayer);
		}
		return this.skillupManager;
	}

	public void addModelChangeListener(ModelChangeListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	public void removeModelChangeListener(ModelChangeListener listener) {
		this.listeners.remove(listener);
	}

	public FutureTrainingManager getFutureTrainingManager() {
		// if (this.futureTrainingManager == null) {
		// gets the list of user defined future trainings
		List<TrainingPerWeek> trainings = TrainingPanel.getTrainPanel().getFutureTrainings();

		// instantiate a future train manager to calculate the previsions */
		this.futureTrainingManager = new FutureTrainingManager(this.activePlayer, trainings,
				this.numberOfCoTrainers, this.trainerLevel);
		// }
		return this.futureTrainingManager;
	}

	private void fireActivePlayerChanged(ModelChange change) {
		for (int i = this.listeners.size() - 1; i >= 0; i--) {
			this.listeners.get(i).modelChanged(change);
		}
	}
}
