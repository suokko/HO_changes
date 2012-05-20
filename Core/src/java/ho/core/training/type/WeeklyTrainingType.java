package ho.core.training.type;

import ho.core.model.UserParameter;
import ho.core.training.TrainingManager;

public abstract class WeeklyTrainingType {
	protected static WeeklyTrainingType m_ciInstance = null;
	protected String _Name = ""; 
	protected int _TrainingType = 0;
	protected int _PrimaryTrainingSkill = 0;
	protected int _SecondaryTrainingSkill = 0;
	protected int[] _PrimaryTrainingSkillPositions = null;
	protected int[] _PrimaryTrainingSkillBonusPositions = null;
	protected int[] _PrimaryTrainingSkillSecondaryTrainingPositions = null;
	protected int[] _PrimaryTrainingSkillOsmosisTrainingPositions = null;
	protected float _PrimaryTrainingSkillBonus = 0;
	protected float _PrimaryTrainingSkillBaseSpeed = 0;
	protected float _PrimaryTrainingSkillSecondarySpeed = 0;
	protected float _PrimaryTrainingSkillOsmosisSpeed = 0;
	protected int[] _SecondaryTrainingSkillPositions = null;
	protected int[] _SecondaryTrainingSkillBonusPositions = null;
	protected int[] _SecondaryTrainingSkillSecondaryTrainingPositions = null;
	protected int[] _SecondaryTrainingSkillOsmosisTrainingPositions = null;
	protected float _SecondaryTrainingSkillBonus = 0;
	protected float _SecondaryTrainingSkillBaseSpeed = 0;
	protected float _SecondaryTrainingSkillSecondarySpeed = 0;
	protected float _SecondaryTrainingSkillOsmosisSpeed = 0;
	private static final float BASE_AGE_FACTOR = (float)0.9;
	private static final float BASE_COACH_FACTOR = (float)1.0;
	private static final float BASE_ASSISTANT_COACH_FACTOR = (float)1.0;
	private static final float BASE_INTENSITY_FACTOR = (float)1.0;
	
	public String Name() {
		return _Name;
	}

	public int TrainingType() {
		return _TrainingType;
	}
	public float PrimaryTrainingSkillBaseSpeed() {
		return _PrimaryTrainingSkillBaseSpeed;
	}

	public float PrimaryTrainingSkillSecondaryBaseSpeed() {
		return _PrimaryTrainingSkillSecondarySpeed;
	}
	
	public float PrimaryTrainingSkillOsmosisBaseSpeed() {
		return _PrimaryTrainingSkillOsmosisSpeed;
	}

	public float SecondaryTrainingSkillBaseSpeed() {
		return _SecondaryTrainingSkillBaseSpeed;
	}

	public float SecondaryTrainingSkillSecondaryBaseSpeed() {
		return 0;
	}
	
	public float SecondaryTrainingSkillOsmosisBaseSpeed() {
		return _SecondaryTrainingSkillOsmosisSpeed;
	}
	
	public int isTrained(int iPosition) {
		int iIsTrained = 0;
		if (_PrimaryTrainingSkillPositions != null) {
			for (int i = 0; i < _PrimaryTrainingSkillPositions.length; i++) {
				if (iPosition == _PrimaryTrainingSkillPositions[i]) {
					iIsTrained = 1;
					break;
				}
			}
		}
		if (iIsTrained == 0) {
			if (_PrimaryTrainingSkillOsmosisTrainingPositions != null) {
				for (int i = 0; i < _PrimaryTrainingSkillOsmosisTrainingPositions.length; i++) {
					if (iPosition == _PrimaryTrainingSkillOsmosisTrainingPositions[i]) {
						iIsTrained = 2;
						break;
					}
				}
			}
		}
		if (iIsTrained == 0) {
			if (_SecondaryTrainingSkillPositions != null) {
				for (int i = 0; i < _SecondaryTrainingSkillPositions.length; i++) {
					if (iPosition == _SecondaryTrainingSkillPositions[i]) {
						iIsTrained = 3;
						break;
					}
				}
			}
		}
		if (iIsTrained == 0) {
			if (_SecondaryTrainingSkillOsmosisTrainingPositions != null) {
				for (int i = 0; i < _SecondaryTrainingSkillOsmosisTrainingPositions.length; i++) {
					if (iPosition == _SecondaryTrainingSkillOsmosisTrainingPositions[i]) {
						iIsTrained = 4;
						break;
					}
				}
			}
		}
		return iIsTrained;
	}
	
	protected static double calcTraining(double baseLength, int age, int assistants, int trainerLevel,
            int intensity, int stamina, int curSkill) {
		double ageFactor = Math.pow(1.0404, age-17) * (UserParameter.instance().TRAINING_OFFSET_AGE + BASE_AGE_FACTOR);
		double skillFactor = - 1.4595 * Math.pow((curSkill+1d)/20, 2) + 3.7535 * (curSkill+1d)/20 - 0.1349d;
		if (skillFactor < 0) {
			skillFactor = 0;
		}
		double trainerFactor = (1 + (7 - Math.min(trainerLevel, 7.5)) * 0.091) * (UserParameter.instance().TrainerFaktor + BASE_COACH_FACTOR);
		double coFactor = (1 + (Math.log(11)/Math.log(10) - Math.log(assistants+1)/Math.log(10)) * 0.2749) * (UserParameter.instance().TRAINING_OFFSET_ASSISTANTS + TrainingManager.BASE_ASSISTANT_COACH_FACTOR);
		double tiFactor = Double.MAX_VALUE;
		if (intensity > 0) {
			tiFactor = (1 / (intensity/100d)) * (UserParameter.instance().TRAINING_OFFSET_INTENSITY + BASE_INTENSITY_FACTOR);
		}
		double staminaFactor = Double.MAX_VALUE;
		if (stamina < 100) {
			staminaFactor = 1 / (1 - stamina/100d);
		}
		double trainLength = baseLength * ageFactor * skillFactor * trainerFactor * coFactor * tiFactor * staminaFactor;
		if (trainLength < 1) {
			trainLength = 1;
		}
		return trainLength;
	}
}
