package de.hattrickorganizer.logik;

import java.util.ArrayList;
import java.util.List;

import plugins.IFuturePlayer;
import plugins.IFutureTrainingManager;
import plugins.IFutureTrainingWeek;
import plugins.ISkillup;
import plugins.ISpieler;
import plugins.ITeam;
import de.hattrickorganizer.model.FuturePlayer;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.PlayerSkillup;

/**
 * Class that manages the prevision of training effect in the future
 *
 * @author Draghetto
 */
public class FutureTrainingManager implements IFutureTrainingManager {
	/** Actual Training points */
	public double[] actual = new double[8];
		
	/** Maximum training points after future trainings */
	public double[] finalPoints = new double[8];

	/** Number of skillup with maximum training */
	public int[] finalSkillup = new int[8];

	/** Active player */
	private ISpieler player;
	private List futureTrainings;
	private List futureSkillups;
	private int seasonPassed = 0;

	private int keeperTrainer;
	private int coTrainer;
	private int trainer;

	/**
	* Calculates the effects of the future trainings for the provived player
	*
	* @param p The active player
	* @param trainings The future trainings
	*/
	public FutureTrainingManager(ISpieler p, List trainings, int cotrainer, int keeper, int trainerLvl) {
		this.player = p;
		this.futureSkillups = new ArrayList();
		this.keeperTrainer = keeper;
		this.coTrainer = cotrainer;
		this.trainer = trainerLvl;
		this.futureTrainings = trainings;
		previewPlayer(FUTUREWEEKS);
	}

	public IFuturePlayer previewPlayer(int startWeekNumber,int finalWeekNumber) {

		this.futureSkillups = new ArrayList();
				
		// Sets the actual training levels
		actual[0] = getOffset(ISpieler.SKILL_TORWART);
		actual[1] = getOffset(ISpieler.SKILL_SPIELAUFBAU);
		actual[2] = getOffset(ISpieler.SKILL_PASSSPIEL);
		actual[3] = getOffset(ISpieler.SKILL_FLUEGEL);
		actual[4] = getOffset(ISpieler.SKILL_VERTEIDIGUNG);
		actual[5] = getOffset(ISpieler.SKILL_TORSCHUSS);
		actual[6] = getOffset(ISpieler.SKILL_STANDARDS);
		actual[7] = getOffset(ISpieler.SKILL_KONDITION);
		
		// rest the other 4 arrays min and max level are equals to actual at beginning
		for (int i = 0; i < 8; i++) {
			finalPoints[i] = actual[i];
			finalSkillup[i] = 0;
		}

		seasonPassed = 0;
		int position = HOMiniModel.instance().getHelper().getPosition(player.getIdealPosition());
		int actualSeason = HOMiniModel.instance().getBasics().getSeason();		
		// Iterate thru all the future training weeks
		for (int index = startWeekNumber; index <= finalWeekNumber; index++) {
			IFutureTrainingWeek tw = (IFutureTrainingWeek) this.futureTrainings.get(index-1);
			
			// check that the actual simulated week is after end of year
			// if so player is one year older and increase the needed point by one due to age            			

			if (tw.getSeason()> actualSeason + seasonPassed) {
				seasonPassed++;
				updatePoints(ISpieler.SKILL_TORWART);
				updatePoints(ISpieler.SKILL_SPIELAUFBAU);
				updatePoints(ISpieler.SKILL_PASSSPIEL);
				updatePoints(ISpieler.SKILL_FLUEGEL);
				updatePoints(ISpieler.SKILL_VERTEIDIGUNG);
				updatePoints(ISpieler.SKILL_TORSCHUSS);
				updatePoints(ISpieler.SKILL_STANDARDS);
				updatePoints(ISpieler.SKILL_KONDITION);
			}

			double point = HOMiniModel.instance().getTrainingsManager().getTrainingPoint().getTrainingPoint(tw.getTyp(), new Integer(position)).doubleValue();
			//HOLogger.instance().log(getClass(),position + " " + point + " " + tw.getTyp());
			// Depending on the type of training, update the proper skill with the provived min and max training points
			switch (tw.getTyp()) {
				case ITeam.TA_TORWART :
					processTraining(ISpieler.SKILL_TORWART, point, tw);

					break;

				case ITeam.TA_SPIELAUFBAU :
					processTraining(ISpieler.SKILL_SPIELAUFBAU, point, tw);

					break;

				case ITeam.TA_PASSSPIEL :
					processTraining(ISpieler.SKILL_PASSSPIEL, point, tw);

					break;

				case ITeam.TA_STEILPAESSE :
					processTraining(ISpieler.SKILL_PASSSPIEL, point, tw);

					break;

				case ITeam.TA_FLANKEN :
					processTraining(ISpieler.SKILL_FLUEGEL, point, tw);

					break;

				case ITeam.TA_VERTEIDIGUNG :
					processTraining(ISpieler.SKILL_VERTEIDIGUNG, point, tw);

					break;

				case ITeam.TA_ABWEHRVERHALTEN :
					processTraining(ISpieler.SKILL_VERTEIDIGUNG, point, tw);

					break;

				case ITeam.TA_CHANCEN :
					processTraining(ISpieler.SKILL_TORSCHUSS, point, tw);

					break;

				case ITeam.TA_SCHUSSTRAINING :
					processTraining(ISpieler.SKILL_TORSCHUSS, point, tw);
					processTraining(ISpieler.SKILL_STANDARDS, 0.5d, tw);

					break;

				case ITeam.TA_STANDARD :
					processTraining(ISpieler.SKILL_STANDARDS, point, tw);

					break;

				case ITeam.TA_KONDITION :
					processTraining(ISpieler.SKILL_KONDITION, point, tw);
					break;

				case ITeam.TA_EXTERNALATTACK :
					processTraining(ISpieler.SKILL_FLUEGEL, point, tw);
					break;
			}
		}		
		FuturePlayer fp = new FuturePlayer();				
		fp.setAttack(getFinalValue(ISpieler.SKILL_TORSCHUSS));		
		fp.setCross(getFinalValue(ISpieler.SKILL_FLUEGEL));
		fp.setDefense(getFinalValue(ISpieler.SKILL_VERTEIDIGUNG));
		fp.setGoalkeeping(getFinalValue(ISpieler.SKILL_TORWART));
		fp.setPassing(getFinalValue(ISpieler.SKILL_PASSSPIEL));
		fp.setPlaymaking(getFinalValue(ISpieler.SKILL_SPIELAUFBAU));
		fp.setSetpieces(getFinalValue(ISpieler.SKILL_STANDARDS));
		fp.setStamina(getFinalValue(ISpieler.SKILL_KONDITION));
		fp.setAge(player.getAlter()+seasonPassed);
		fp.setPlayerId(player.getSpielerID());
		return fp;
	}
	
	private double getFinalValue(int skillIndex) {		
		double value = getSkillValue(player,skillIndex);
		int pos = getSkillPosition(skillIndex);
		value = value + finalSkillup[pos];
		value = value + finalPoints[pos]/getTrainingLength(getTrainedSkillCode(skillIndex));		
		return value;
	}

	private void updatePoints(int skillIndex) {
		double length = player.getTrainingLength(getTrainedSkillCode(skillIndex), coTrainer, keeperTrainer, trainer, 100);

		double modifier = ((double) (length + seasonPassed )) / ((double) (length + seasonPassed - 1));
		int pos = getSkillPosition(skillIndex);
		actual[pos] = actual[pos] * modifier;
		finalPoints[pos] = finalPoints[pos] * modifier;
	}

	/**
	* Get the array of the actual training points
	*
	* @return
	*/
	public double[] getActual() {
		return actual;
	}

	/**
	* Returns a list of all future skillups as predicted
	*
	* @return List of Skillups
	*/
	public List getFutureSkillups() {
		return futureSkillups;
	}

	/**
	* Get the array of the maximum training points
	*
	* @return
	*/
	public double[] getMax() {
		return finalPoints;
	}

	/**
	* Get the array of the maximum number of skillup
	*
	* @return
	*/
	public int[] getMaxup() {
		return finalSkillup;
	}

	/**
	* Return the offset points for the skill
	*
	* @param skill the skill index to analyze
	*
	* @return the decimal of a player
	*/
	private double getOffset(int skillIndex) {
		double offset = player.getSubskill4SkillWithOffset(skillIndex);
		double length = player.getTrainingLength(getTrainedSkillCode(skillIndex), coTrainer, keeperTrainer, trainer, 100);
		return offset * length;
	}

	/**
	* Calculates the number of points needed for a skillup
	*
	* @param trType
	*
	* @return
	*/
	private double getTrainingLength(int trType) {		
		int age = player.getAlter();
		player.setAlter(age+seasonPassed);		
		double limit = player.getTrainingLength(trType, coTrainer, keeperTrainer, trainer, 100);
		player.setAlter(age);			
		return limit;
	}

	/**
	* Checks if a skillup on min or max trainings has happened
	*
	* @param skillIndex the skill to consider
	* @param limit the number of needed point for that skill to have a skillup
	*
	* @return true if skillup happened
	*/
	private boolean checkSkillup(int pos, double limit) {
		if (finalPoints[pos] >= limit) {
			//finalPoints[skillIndex] = finalPoints[skillIndex] - limit;
			finalPoints[pos] = 0;
			finalSkillup[pos]++;

			return true;
		}

		return false;
	}

	/**
	* Updates the training situation
	*
	* @param skillIndex The skill to be updated
	* @param point The minimum points added
	* @param tw the training week settings for the considered week
	*/
	private void processTraining(int skillIndex, double point, IFutureTrainingWeek tw) {
		// recalculate point value with the intensity for the current week 			
		point = (point * tw.getIntensitaet()) / 100d;
		int pos = getSkillPosition(skillIndex);
		finalPoints[pos] = finalPoints[pos] + point;

		// number of points necessary to have a skillup
		double trainLength = getTrainingLength(getTrainedSkillCode(skillIndex));

		// check if skillup happened!
		if (checkSkillup(pos, trainLength)) {
			PlayerSkillup su = new PlayerSkillup();

			su.setHtSeason(tw.getSeason());
			su.setHtWeek(tw.getWeek());
			su.setType(skillIndex);
			su.setValue(getSkillValue(player, skillIndex) + finalSkillup[pos]);
			su.setTrainType(ISkillup.SKILLUP_FUTURE);

			if ((skillIndex == ISpieler.SKILL_KONDITION) && (su.getValue() > 9)) {
				return;
			}

			futureSkillups.add(su);
		}
	}

	private int getTrainedSkillCode(int skillIndex) {
		switch (skillIndex) {
			case ISpieler.SKILL_TORWART :
				return ISpieler.TORWART;

			case ISpieler.SKILL_SPIELAUFBAU :
				return ISpieler.SPIELAUFBAU;

			case ISpieler.SKILL_PASSSPIEL :
				return ISpieler.PASSPIEL;

			case ISpieler.SKILL_FLUEGEL :
				return ISpieler.FLUEGELSPIEL;

			case ISpieler.SKILL_VERTEIDIGUNG :
				return ISpieler.VERTEIDIGUNG;

			case ISpieler.SKILL_TORSCHUSS :
				return ISpieler.CHANCENVERWERTUNG;

			case ISpieler.SKILL_STANDARDS :
				return ISpieler.STANDARDS;

			case ISpieler.SKILL_KONDITION :
				return ISpieler.KONDITION;
		}

		return 0;
	}

	public static int getSkillValue(ISpieler spieler, int skillIndex) {
		switch (skillIndex) {
			case ISpieler.SKILL_TORWART :
				return spieler.getTorwart();

			case ISpieler.SKILL_SPIELAUFBAU :
				return spieler.getSpielaufbau();

			case ISpieler.SKILL_PASSSPIEL :
				return spieler.getPasspiel();

			case ISpieler.SKILL_FLUEGEL :
				return spieler.getFluegelspiel();

			case ISpieler.SKILL_VERTEIDIGUNG :
				return spieler.getVerteidigung();

			case ISpieler.SKILL_TORSCHUSS :
				return spieler.getTorschuss();

			case ISpieler.SKILL_STANDARDS :
				return spieler.getStandards();

			case ISpieler.SKILL_KONDITION :
				return spieler.getKondition();

		}

		return 0;
	}

	private int getSkillPosition(int skillIndex) {
		switch (skillIndex) {
			case ISpieler.SKILL_TORWART :
				return 0;

			case ISpieler.SKILL_SPIELAUFBAU :
				return 1;

			case ISpieler.SKILL_PASSSPIEL :
				return 2;

			case ISpieler.SKILL_FLUEGEL :
				return 3;

			case ISpieler.SKILL_VERTEIDIGUNG :
				return 4;

			case ISpieler.SKILL_TORSCHUSS :
				return 5;

			case ISpieler.SKILL_STANDARDS :
				return 6;

			case ISpieler.SKILL_KONDITION :
				return 7;
		}
		return 0;

	}

	public IFuturePlayer previewPlayer(int weekNumber) {
		return previewPlayer(1,weekNumber);
	}

}
