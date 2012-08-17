package ho.core.training;

import ho.core.constants.TrainingType;
import ho.core.model.UserParameter;
import ho.core.model.player.Spieler;
import ho.core.training.TrainingWeekPlayer;
import ho.core.training.type.*;

public abstract class WeeklyTrainingType {
	protected String _Name = ""; 
	protected int _TrainingType = 0;
	protected int _PrimaryTrainingSkill = -1;
	protected int _SecondaryTrainingSkill = -1;
	protected int[] _PrimaryTrainingSkillPositions = null;
	protected int[] _PrimaryTrainingSkillBonusPositions = null;
	protected int[] _PrimaryTrainingSkillSecondaryTrainingPositions = null;
	protected int[] _PrimaryTrainingSkillOsmosisTrainingPositions = null;
	protected float _PrimaryTrainingSkillBonus = 0;
	protected float _PrimaryTrainingSkillBaseLength = 0;
	protected float _PrimaryTrainingSkillSecondaryLengthRate = 0;
	protected float _PrimaryTrainingSkillOsmosisLengthRate = (float) 100 / (OSMOSIS_BASE_PERCENTAGE + UserParameter.instance().TRAINING_OFFSET_OSMOSIS); // 16%
	protected int[] _SecondaryTrainingSkillPositions = null;
	protected int[] _SecondaryTrainingSkillBonusPositions = null;
	protected int[] _SecondaryTrainingSkillSecondaryTrainingPositions = null;
	protected int[] _SecondaryTrainingSkillOsmosisTrainingPositions = null;
	protected float _SecondaryTrainingSkillBonus = 0;
	protected float _SecondaryTrainingSkillBaseLength = 0;
	protected float _SecondaryTrainingSkillSecondaryLengthRate = 0;
	protected float _SecondaryTrainingSkillOsmosisLengthRate = 0;
	protected float _PrimaryTrainingBaseLength = 0;
	public static final float OSMOSIS_BASE_PERCENTAGE = (float) 16;
	public static final float BASE_AGE_FACTOR = (float) 0.9;
	public static final float BASE_COACH_FACTOR = (float) 1.0;
	public static final float BASE_ASSISTANT_COACH_FACTOR = (float) 1.0;
	public static final float BASE_INTENSITY_FACTOR = (float) 1.0;
	
	private static double[] skillFactorArray = {
		// Skill 1
		0.24,
		0.41,
		0.58,
		0.74,
		0.89,
		// Passable...
		1.04, 
		1.18,
		1.31,
		1.50,
		// Outstanding
		1.71,
		1.93,
		2.17,
		2.42,
		2.71,
		// Titanic
		3.01,
		3.46,
		4.02,
		4.71,
		// Skill 19
		5.62,
		// Extrapolated using second derivatives
		6.84,
		8.49,
		10.79,
		14.00,
		18};
	
	public static WeeklyTrainingType instance(int iTrainingType)
	{
		WeeklyTrainingType wt = null;
		switch (iTrainingType) {
	        case TrainingType.CROSSING_WINGER:
				wt = CrossingWeeklyTraining.instance();
				break;
			case TrainingType.DEF_POSITIONS:
				wt = DefensivePositionsWeeklyTraining.instance();
				break;
			case TrainingType.DEFENDING:
				wt = DefendingWeeklyTraining.instance();
				break;
			case TrainingType.GOALKEEPING:
				wt = GoalkeepingWeeklyTraining.instance();
				break;
			case TrainingType.PLAYMAKING:
				wt = PlaymakingWeeklyTraining.instance();
				break;
			case TrainingType.SCORING:
				wt = ScoringWeeklyTraining.instance();
				break;
			case TrainingType.SET_PIECES:
				wt = SetPiecesWeeklyTraining.instance();
				break;
			case TrainingType.SHOOTING:
				wt = ShootingWeeklyTraining.instance();
				break;
			case TrainingType.SHORT_PASSES:
				wt = ShortPassesWeeklyTraining.instance();
				break;
			case TrainingType.THROUGH_PASSES:
				wt = ThroughPassesWeeklyTraining.instance();
				break;
			case TrainingType.WING_ATTACKS:
				wt = WingAttacksWeeklyTraining.instance();
				break; 
        }
		return wt;
	}
	public String getName() {
		return _Name;
	}

	public int getTrainingType() {
		return _TrainingType;
	}
	
	public int getPrimaryTrainingSkill() {
		return _PrimaryTrainingSkill;
	}

	public int getSecondaryTrainingSkill() {
		return _SecondaryTrainingSkill;
	}

	public float getBaseTrainingLength() {
		return _PrimaryTrainingBaseLength;
	}
	public float getPrimaryTrainingSkillBaseLength() {
		return _PrimaryTrainingSkillBaseLength;
	}

	public float getPrimaryTrainingSkillBonus() {
		return _PrimaryTrainingSkillBonus;
	}

	public float getPrimaryTrainingSkillSecondaryBaseLengthRate() {
		return _PrimaryTrainingSkillSecondaryLengthRate;
	}
	
	public float getPrimaryTrainingSkillOsmosisBaseLengthRate() {
		return _PrimaryTrainingSkillOsmosisLengthRate;
	}

	public float getSecondaryTrainingSkillBaseLength() {
		return _SecondaryTrainingSkillBaseLength;
	}

	public float getSecondaryTrainingSkillSecondaryLengthRate() {
		return _SecondaryTrainingSkillSecondaryLengthRate;
	}
	
	public float getSecondaryTrainingSkillOsmosisLengthRate() {
		return _SecondaryTrainingSkillOsmosisLengthRate;
	}
	
	public int[] getPrimaryTrainingSkillPositions() { 
	 return _PrimaryTrainingSkillPositions;
	}
	public int[] getPrimaryTrainingSkillBonusPositions() {
		return _PrimaryTrainingSkillBonusPositions;
	}
	public int[] getPrimaryTrainingSkillSecondaryTrainingPositions() {
		return _PrimaryTrainingSkillSecondaryTrainingPositions;
	}
	public int[] getPrimaryTrainingSkillOsmosisTrainingPositions() {
		return _PrimaryTrainingSkillOsmosisTrainingPositions;
	}
	public int[] getSecondaryTrainingSkillPositions() {
		return _SecondaryTrainingSkillPositions;
	}
	public int[] getSecondaryTrainingSkillBonusPositions() {
		return _SecondaryTrainingSkillBonusPositions;
	}
	public int[] getSecondaryTrainingSkillSecondaryTrainingPositions() {
		return _SecondaryTrainingSkillSecondaryTrainingPositions;
	}
	public int[] getSecondaryTrainingSkillOsmosisTrainingPositions() {
		return _SecondaryTrainingSkillOsmosisTrainingPositions;
	}

	public double getPrimaryTraining(TrainingWeekPlayer tp)
	{
		double dPrimaryTraining = 0;
		int iMinutes = 0;
		int tmp = tp.getPrimarySkillPositionMinutes();
		if (tmp > 0)
		{
			if (tmp > 90) {
				tmp = 90;
			}
			iMinutes = tmp;
			dPrimaryTraining = (double) tmp / (double) 90;
		}
		if (iMinutes > 0 && _PrimaryTrainingSkillBonus > 0) {
			tmp = tp.getPrimarySkillBonusPositionMinutes();
			if (tmp > 0) {
				dPrimaryTraining += ((double) tmp / (double) 90) * _PrimaryTrainingSkillBonus; 
			}
		}
		if (iMinutes < 90) {
			if (_PrimaryTrainingSkillSecondaryLengthRate > 0) {
				 tmp = tp.getPrimarySkillSecondaryPositionMinutes();
				 if (tmp > 0) {
					 if (iMinutes + tmp > 90) {
						 tmp = 90 - iMinutes;
					 }
					 iMinutes += tmp;
					 dPrimaryTraining += ((double) tmp / (double) 90) / _PrimaryTrainingSkillSecondaryLengthRate;
				 }
			}
			if (iMinutes < 90) {
				if (_PrimaryTrainingSkillOsmosisLengthRate > 0) {
					tmp = tp.getPrimarySkillOsmosisPositionMinutes();
					if (tmp > 0) {
						if (iMinutes + tmp > 90) {
							tmp = 90 - iMinutes;
						}
						iMinutes += tmp;
						dPrimaryTraining += ((double) tmp / (double) 90) / _PrimaryTrainingSkillOsmosisLengthRate;
					}
				}
			}	 
		}
		return dPrimaryTraining;
	}
	public double getSecondaryTraining(TrainingWeekPlayer tp)
	{
		double dSecondaryTraining = 0;
		if (_SecondaryTrainingSkill > 0) {
			int iMinutes = 0;
			int tmp = tp.getSecondarySkillPrimaryMinutes();
			if (tmp > 0)
			{
				if (tmp >= 90) {
					tmp = 90;
				}
				iMinutes = tmp;
				dSecondaryTraining = (double) tmp / (double) 90;
			}
			if (iMinutes > 0 && _SecondaryTrainingSkillBonus > 0) {
				tmp = tp.getSecondarySkillBonusMinutes();
				if (tmp > 0) {
					dSecondaryTraining += ((double) tmp / (double) 90) * _SecondaryTrainingSkillBonus; 
				}
			}
			if (iMinutes < 90) {
				if (_SecondaryTrainingSkillSecondaryLengthRate > 0)
				{
					tmp = tp.getSecondarySkillSecondaryPositionMinutes();
					if (tmp > 0) {
						if (iMinutes + tmp > 90) {
							tmp = 90 - iMinutes;
						}
						iMinutes += tmp;
						dSecondaryTraining += ((double) tmp / (double) 90) / _SecondaryTrainingSkillSecondaryLengthRate;
					}
				}
				if (iMinutes < 90) {
					if (_SecondaryTrainingSkillOsmosisLengthRate > 0) {
						tmp = tp.getSecondarySkillOsmosisPositionMinutes();
						if (tmp > 0) {
							if (iMinutes + tmp > 90) {
								tmp = 90 - iMinutes;
							}
							iMinutes += tmp;
							dSecondaryTraining += ((double) tmp / (double) 90) / _SecondaryTrainingSkillOsmosisLengthRate;
						}
					}
				}	 
			}
		}
		return dSecondaryTraining;
	}
	 public static double calcTraining(double baseLength, int age, int assistants, int trainerLevel, int intensity, int stamina, int curSkill) 
	 {
		double ageFactor = Math.pow(1.0404, age - 17) * (UserParameter.instance().TRAINING_OFFSET_AGE + BASE_AGE_FACTOR);
//		double skillFactor = - 1.4595 * Math.pow((curSkill+1d)/20, 2) + 3.7535 * (curSkill + 1d) / 20 - 0.1349d;
//		if (skillFactor < 0) {
//			skillFactor = 0;
//		}
		
		// skill between 1 and 22
		curSkill = Math.max(1, curSkill);
		curSkill = Math.min(skillFactorArray.length, curSkill);
		double skillFactor = skillFactorArray[curSkill - 1];
	
		// age between 17 and 30
//		age = Math.max(17, age);
//		age = Math.min(30, age);
////		double ageFactor = ageFactorArray[age - 17];
		
		
		double trainerFactor = (1 + (7 - Math.min(trainerLevel, 7.5)) * 0.091) * (UserParameter.instance().TrainerFaktor + BASE_COACH_FACTOR);
		double coFactor = (1 + (Math.log(11)/Math.log(10) - Math.log(assistants+1)/Math.log(10)) * 0.2749) * (UserParameter.instance().TRAINING_OFFSET_ASSISTANTS + BASE_ASSISTANT_COACH_FACTOR);
		double tiFactor = Double.MAX_VALUE;
		if (intensity > 0) {
			tiFactor = (1 / (intensity / 100d)) * (UserParameter.instance().TRAINING_OFFSET_INTENSITY + BASE_INTENSITY_FACTOR);
		}
		double staminaFactor = Double.MAX_VALUE;
		if (stamina < 100) {
			staminaFactor = 1 / (1 - stamina / 100d);
		}
		double trainLength = baseLength * ageFactor * skillFactor * trainerFactor * coFactor * tiFactor * staminaFactor;
		if (trainLength < 1) {
			trainLength = 1;
		}
		return trainLength;
	 }
	 
	 public abstract double getTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina);
	 public abstract double getSecondaryTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina);
}
