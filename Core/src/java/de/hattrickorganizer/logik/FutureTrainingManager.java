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
	/** Actual Training sub */
	public double[] actual = new double[8];
		
	/** Maximum training sub after future trainings */
	public double[] finalSub = new double[8];

	/** Number of skillup with maximum training */
	public int[] finalSkillup = new int[8];

	/** Active player */
	private ISpieler player;
	private List<IFutureTrainingWeek> futureTrainings;
	private List<ISkillup> futureSkillups;
	private int weeksPassed = 0;

	private int coTrainer;
	private int trainer;

	/**
	* Calculates the effects of the future trainings for the provided player
	*
	* @param p The active player
	* @param trainings The future trainings
	*/
	public FutureTrainingManager(ISpieler p, List<IFutureTrainingWeek> trainings, int cotrainer, int trainerLvl) {
		this.player = p;
		this.futureSkillups = new ArrayList<ISkillup>();
		this.coTrainer = cotrainer;
		this.trainer = trainerLvl;
		this.futureTrainings = trainings;
		previewPlayer(FUTUREWEEKS);
	}

	public IFuturePlayer previewPlayer(int startWeekNumber,int finalWeekNumber) {

		this.futureSkillups = new ArrayList<ISkillup>();
				
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
			finalSub[i] = actual[i];
			finalSkillup[i] = 0;
		}

		weeksPassed = 0;
		int position = HOMiniModel.instance().getHelper().getPosition(player.getIdealPosition());
		// Iterate thru all the future training weeks
		for (int index = startWeekNumber; index <= finalWeekNumber; index++) {
			weeksPassed++;
			IFutureTrainingWeek tw = this.futureTrainings.get(index-1);
			
			double point = HOMiniModel.instance().getTrainingsManager().getTrainingPoint().getTrainingPoint(tw.getTyp(), Integer.valueOf(position)).doubleValue();
//			HOLogger.instance().log(getClass(),position + " " + point + " " + tw.getTyp());
			// Depending on the type of training, update the proper skill with the provided training points
			switch (tw.getTyp()) {
				case ITeam.TA_TORWART :
				case ITeam.TA_SPIELAUFBAU :
				case ITeam.TA_PASSSPIEL :
				case ITeam.TA_STEILPAESSE :
				case ITeam.TA_FLANKEN :
				case ITeam.TA_VERTEIDIGUNG :
				case ITeam.TA_ABWEHRVERHALTEN :
				case ITeam.TA_CHANCEN :
				case ITeam.TA_STANDARD :
				case ITeam.TA_EXTERNALATTACK :
					processTraining(getSkillForTraining(tw.getTyp()), point, tw);
					break;
				case ITeam.TA_SCHUSSTRAINING :
					processTraining(ISpieler.SKILL_TORSCHUSS, point, tw);
					processTraining(ISpieler.SKILL_STANDARDS, 0.5d, tw);
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
		fp.setAge(player.getAlter()+(int)(Math.floor((player.getAgeDays()+7*weeksPassed)/112d)));
		fp.setPlayerId(player.getSpielerID());
		return fp;
	}

	/**
	 * get the final value (incl. skillups and sub) for a specific skill
	 * 
	 * @param skillIndex	index of the skill
	 * @return				value for this skill
	 */
	private double getFinalValue(int skillIndex) {		
		double value = player.getValue4Skill4(skillIndex);
		int pos = getSkillPosition(skillIndex);
		value = value + finalSkillup[pos];
		value = value + finalSub[pos];		
		return value;
	}

	/**
	* Get the array of the actual training sub
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
	public List<ISkillup> getFutureSkillups() {
		return futureSkillups;
	}

	/**
	* Get the array of the maximum training sub
	*
	* @return
	*/
	public double[] getMax() {
		return finalSub;
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
	* Return the offset and sub for the skill
	*
	* @param skillIndex the skill index to analyze
	*
	* @return the sub with offset of a player
	*/
	private double getOffset(int skillIndex) {
		double offset = player.getSubskill4SkillWithOffset(skillIndex);
		return offset;
	}

	/**
	* Calculates the number of weeks needed for a future skillup
	* 
	* @param skillIndex		skill to use (from ISpieler.SKILL*)
	* @param	intensity	training intensity
	* @param	staminaTrainingPart	stamina share
	*
	* @return	the predicted length
	*/
	private double getTrainingLength(int skillIndex, int intensity, int staminaTrainingPart) {
		int trType = getPrimaryTrainingForSkill(skillIndex);
		int pos = getSkillPosition(skillIndex);
		int curSkillUps = finalSkillup[pos];
		int age = player.getAlter();
		int ageDays = player.getAgeDays();
		int realSkill = player.getValue4Skill4(skillIndex);
		// Set age and skill for simulation
		player.setAlter (age + (int)Math.floor((ageDays + 7*weeksPassed)/112d));
		player.setValue4Skill4 (skillIndex, realSkill+curSkillUps);
		double limit = player.getTrainingLength(trType, coTrainer, trainer, intensity, staminaTrainingPart);
//		HOLogger.instance().debug(getClass(), "getTrLen for "+player.getName()+": weeksPassed="+weeksPassed+", age="+player.getAlter()+", skill="+getSkillValue(player, skillIndex)+", limit="+limit);
		// Undo simulation changes on player
		player.setAlter(age);
		player.setValue4Skill4 (skillIndex, realSkill);
		return limit;
	}

	/**
	* Checks if a skillup has happened
	*
	* @param skillIndex the skill to consider
	*
	* @return true if skillup happened
	*/
	private boolean checkSkillup(int pos) {
		if (finalSub[pos] >= 1) {
//			Alternative 1: Set sub=0 after a skillup 
//			(We will use this, until the training speed formula is optimized)
			finalSub[pos] = 0;

//			TODO flattermann
//			Alternative 2: Use overflow sub after a skillup
//			(This would be more accurate. But only if the underlaying formula is exact) 
//			finalSub[pos] -= 1;

			finalSkillup[pos]++;

			return true;
		}

		return false;
	}

	/**
	* Updates the training situation
	*
	* @param skillIndex The skill to be updated
	* @param point The points to be added
	* @param tw the training week settings for the considered week
	*/
	private void processTraining(int skillIndex, double point, IFutureTrainingWeek tw) {
		// number of weeks necessary to have a skillup
		double trainLength = getTrainingLength(skillIndex, tw.getIntensitaet(), tw.getStaminaTrainingPart());

		// If invalid training (trType does not train this skill)
		if (trainLength == -1)
			return;
		
		// calculate increase in sub
		double subForThisWeek = point/trainLength;
		
		int pos = getSkillPosition(skillIndex);
		// add sub to skill
		finalSub[pos] = finalSub[pos] + subForThisWeek;


		// check if skillup happened!
		if (checkSkillup(pos)) {
			PlayerSkillup su = new PlayerSkillup();

			su.setHtSeason(tw.getSeason());
			su.setHtWeek(tw.getWeek());
			su.setType(skillIndex);
			su.setValue(player.getValue4Skill4(skillIndex) + finalSkillup[pos]);
			su.setTrainType(ISkillup.SKILLUP_FUTURE);

			if ((skillIndex == ISpieler.SKILL_KONDITION) && (su.getValue() > 9)) {
				return;
			}

			futureSkillups.add(su);
		}
	}

	/**
	 * Gets the primary training for a specific skill
	 * (e.g. ISpieler.SKILL_SPIELAUFBAU -> ITeam.TA_SPIELAUFBAU)
	 *  
	 * @param skillIndex	the skill to use
	 * @return				the primary training type
	 */
	private int getPrimaryTrainingForSkill (int skillIndex) {
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

		}

		return 0;
	}

	/**
	 * Gets the skill trained by a specific training type
	 * (ITeam.TA_* -> ISpieler.SKILL_*)
	 * 
	 * @param trType	training type
	 * @return			the trained skill
	 */
	private int getSkillForTraining (int trType) {
		switch (trType) {
			case ITeam.TA_TORWART:
				return ISpieler.SKILL_TORWART;

			case ITeam.TA_SPIELAUFBAU:
				return ISpieler.SKILL_SPIELAUFBAU;

			case ITeam.TA_PASSSPIEL:
			case ITeam.TA_STEILPAESSE:
				return ISpieler.SKILL_PASSSPIEL;

			case ITeam.TA_FLANKEN:
			case ITeam.TA_EXTERNALATTACK:
				return ISpieler.SKILL_FLUEGEL;

			case ITeam.TA_VERTEIDIGUNG:
			case ITeam.TA_ABWEHRVERHALTEN:
				return ISpieler.SKILL_VERTEIDIGUNG;

			case ITeam.TA_CHANCEN:
			case ITeam.TA_SCHUSSTRAINING:
				return ISpieler.SKILL_TORSCHUSS;

			case ITeam.TA_STANDARD:
				return ISpieler.SKILL_STANDARDS;

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
