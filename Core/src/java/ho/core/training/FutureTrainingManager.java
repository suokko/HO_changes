package ho.core.training;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.PlayerSkillup;
import ho.core.model.UserParameter;
import ho.core.model.player.FuturePlayer;
import ho.core.model.player.ISkillup;
import ho.core.model.player.Spieler;
import ho.core.util.HelperWrapper;
import ho.module.training.FutureTrainingWeek;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages the prevision of training effect in the future
 *
 * @author Draghetto
 */
public class FutureTrainingManager {
	/** Actual Training sub */
	public double[] actual = new double[8];
		
	/** Maximum training sub after future trainings */
	public double[] finalSub = new double[8];

	/** Number of skillup with maximum training */
	public int[] finalSkillup = new int[8];

	/** Active player */
	private Spieler player;
	private List<FutureTrainingWeek> futureTrainings;
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
	public FutureTrainingManager(Spieler p, List<FutureTrainingWeek> trainings, int cotrainer, int trainerLvl) {
		this.player = p;
		this.futureSkillups = new ArrayList<ISkillup>();
		this.coTrainer = cotrainer;
		this.trainer = trainerLvl;
		this.futureTrainings = trainings;
		previewPlayer(UserParameter.instance().futureWeeks);
	}

	public FuturePlayer previewPlayer(int startWeekNumber,int finalWeekNumber) {

		this.futureSkillups = new ArrayList<ISkillup>();
				
		// Sets the actual training levels
		actual[0] = getOffset(PlayerSkill.KEEPER);
		actual[1] = getOffset(PlayerSkill.PLAYMAKING);
		actual[2] = getOffset(PlayerSkill.PASSING);
		actual[3] = getOffset(PlayerSkill.WINGER);
		actual[4] = getOffset(PlayerSkill.DEFENDING);
		actual[5] = getOffset(PlayerSkill.SCORING);
		actual[6] = getOffset(PlayerSkill.SET_PIECES);
		actual[7] = getOffset(PlayerSkill.STAMINA);
		
		// rest the other 4 arrays min and max level are equals to actual at beginning
		for (int i = 0; i < 8; i++) {
			finalSub[i] = actual[i];
			finalSkillup[i] = 0;
		}

		weeksPassed = 0;
		int position = HelperWrapper.instance().getPosition(player.getIdealPosition());
		// Iterate thru all the future training weeks
		for (int index = startWeekNumber; index <= finalWeekNumber; index++) {
			weeksPassed++;
			FutureTrainingWeek tw = this.futureTrainings.get(index-1);
			
			double point = TrainingsManager.instance().getTrainingPoint().getTrainingPoint(tw.getTyp(), Integer.valueOf(position)).doubleValue();
//			HOLogger.instance().log(getClass(),position + " " + point + " " + tw.getTyp());
			// Depending on the type of training, update the proper skill with the provided training points
			switch (tw.getTyp()) {
				case TrainingType.GOALKEEPING :
				case TrainingType.PLAYMAKING :
				case TrainingType.SHORT_PASSES :
				case TrainingType.THROUGH_PASSES :
				case TrainingType.CROSSING_WINGER :
				case TrainingType.DEFENDING :
				case TrainingType.DEF_POSITIONS :
				case TrainingType.SCORING :
				case TrainingType.SET_PIECES :
				case TrainingType.WING_ATTACKS :
					processTraining(getSkillForTraining(tw.getTyp()), point, tw);
					break;
				case TrainingType.SHOOTING :
					processTraining(PlayerSkill.SCORING, point, tw);
					processTraining(PlayerSkill.SET_PIECES, 0.5d, tw);
					break;
			}
		}		
		FuturePlayer fp = new FuturePlayer();				
		fp.setAttack(getFinalValue(PlayerSkill.SCORING));		
		fp.setCross(getFinalValue(PlayerSkill.WINGER));
		fp.setDefense(getFinalValue(PlayerSkill.DEFENDING));
		fp.setGoalkeeping(getFinalValue(PlayerSkill.KEEPER));
		fp.setPassing(getFinalValue(PlayerSkill.PASSING));
		fp.setPlaymaking(getFinalValue(PlayerSkill.PLAYMAKING));
		fp.setSetpieces(getFinalValue(PlayerSkill.SET_PIECES));
		fp.setStamina(getFinalValue(PlayerSkill.STAMINA));
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
	private void processTraining(int skillIndex, double point, FutureTrainingWeek tw) {
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

			if ((skillIndex == PlayerSkill.STAMINA) && (su.getValue() > 9)) {
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
			case PlayerSkill.KEEPER :
				return TrainingType.GOALKEEPING;

			case PlayerSkill.PLAYMAKING :
				return TrainingType.PLAYMAKING;

			case PlayerSkill.PASSING :
				return TrainingType.SHORT_PASSES;

			case PlayerSkill.WINGER :
				return TrainingType.CROSSING_WINGER;

			case PlayerSkill.DEFENDING :
				return TrainingType.DEFENDING;

			case PlayerSkill.SCORING :
				return TrainingType.SCORING;

			case PlayerSkill.SET_PIECES :
				return TrainingType.SET_PIECES;

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
			case TrainingType.GOALKEEPING:
				return PlayerSkill.KEEPER;

			case TrainingType.PLAYMAKING:
				return PlayerSkill.PLAYMAKING;

			case TrainingType.SHORT_PASSES:
			case TrainingType.THROUGH_PASSES:
				return PlayerSkill.PASSING;

			case TrainingType.CROSSING_WINGER:
			case TrainingType.WING_ATTACKS:
				return PlayerSkill.WINGER;

			case TrainingType.DEFENDING:
			case TrainingType.DEF_POSITIONS:
				return PlayerSkill.DEFENDING;

			case TrainingType.SCORING:
			case TrainingType.SHOOTING:
				return PlayerSkill.SCORING;

			case TrainingType.SET_PIECES:
				return PlayerSkill.SET_PIECES;

		}

		return 0;
	}

	private int getSkillPosition(int skillIndex) {
		switch (skillIndex) {
			case PlayerSkill.KEEPER :
				return 0;

			case PlayerSkill.PLAYMAKING :
				return 1;

			case PlayerSkill.PASSING :
				return 2;

			case PlayerSkill.WINGER :
				return 3;

			case PlayerSkill.DEFENDING :
				return 4;

			case PlayerSkill.SCORING :
				return 5;

			case PlayerSkill.SET_PIECES :
				return 6;

			case PlayerSkill.STAMINA :
				return 7;
		}
		return 0;

	}

	public FuturePlayer previewPlayer(int weekNumber) {
		return previewPlayer(1,weekNumber);
	}

}
