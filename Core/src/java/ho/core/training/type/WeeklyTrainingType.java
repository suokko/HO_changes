package ho.core.training.type;

import ho.core.training.TrainingWeekPlayer;

public abstract class WeeklyTrainingType {
	protected String _Name = ""; 
	protected int _TrainingType = 0;
	protected int _PrimaryTrainingSkill = 0;
	protected int _SecondaryTrainingSkill = 0;
	protected int[] _PrimaryTrainingSkillPositions = null;
	protected int[] _PrimaryTrainingSkillBonusPositions = null;
	protected int[] _PrimaryTrainingSkillSecondaryTrainingPositions = null;
	protected int[] _PrimaryTrainingSkillOsmosisTrainingPositions = null;
	protected float _PrimaryTrainingSkillBonus = 0;
	protected float _PrimaryTrainingSkillBaseLength = 0;
	protected float _PrimaryTrainingSkillSecondaryLengthRate = 0;
	protected float _PrimaryTrainingSkillOsmosisLengthRate = 0;
	protected int[] _SecondaryTrainingSkillPositions = null;
	protected int[] _SecondaryTrainingSkillBonusPositions = null;
	protected int[] _SecondaryTrainingSkillSecondaryTrainingPositions = null;
	protected int[] _SecondaryTrainingSkillOsmosisTrainingPositions = null;
	protected float _SecondaryTrainingSkillBonus = 0;
	protected float _SecondaryTrainingSkillBaseLength = 0;
	protected float _SecondaryTrainingSkillSecondaryLengthRate = 0;
	protected float _SecondaryTrainingSkillOsmosisLengthRate = 0;
	protected float _PrimaryTrainingBaseLength = 0;
	public static final float BASE_AGE_FACTOR = (float) 0.9;
	public static final float BASE_COACH_FACTOR = (float) 1.0;
	public static final float BASE_ASSISTANT_COACH_FACTOR = (float) 1.0;
	public static final float BASE_INTENSITY_FACTOR = (float) 1.0;
	
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
		double dPoints = 0;
		int iMinutes = 0;
		int tmp = tp.getPrimarySkillPositionMinutes();
		if (tmp > 0)
		{
			iMinutes += tmp;
			if (tmp >= 90)
				dPoints = 1;
			else
				dPoints = tmp / 90;
		}
		tmp = tp.getPrimarySkillBonusPositionMinutes();
		if (tmp > 0) {
			dPoints += (tmp / 90) * _PrimaryTrainingSkillBonus; 
		}
		dPrimaryTraining = dPoints;
		if (iMinutes < 90) {
			 tmp = tp.getPrimarySkillSecondaryPositionMinutes();
			 if (tmp > 0) {
				 if (iMinutes + tmp > 90) {
					 tmp = 90 - iMinutes;
				 }
				 iMinutes += tmp;
				 dPrimaryTraining = (tmp / 90) / _PrimaryTrainingSkillSecondaryLengthRate;
			 }
			 if (iMinutes < 90) {
				 tmp = tp.getPrimarySkillOsmosisPositionMinutes();
				 if (tmp > 0) {
					 if (iMinutes + tmp > 90) {
						 tmp = 90 - iMinutes;
					 }
					 iMinutes += tmp;
					 dPrimaryTraining = (tmp / 90) / _PrimaryTrainingSkillOsmosisLengthRate;
				 }
			 }	 
		}
		return dPrimaryTraining;
	}
	public double getSecondaryTraining(TrainingWeekPlayer tp)
	{
		double dSecondaryTraining = 0;
		double dPoints = 0;
		if (_SecondaryTrainingSkill > 0) {
			int iMinutes = 0;
			int tmp = tp.getSecondarySkillPrimaryMinutes();
			if (tmp > 0)
			{
				iMinutes += tmp;
				if (tmp >= 90)
					dPoints = 1;
				else
					dPoints = tmp / 90;
			}
			tmp = tp.getSecondarySkillBonusMinutes();
			if (tmp > 0) {
				dPoints += (tmp / 90) * _SecondaryTrainingSkillBonus; 
			}
			dSecondaryTraining = dPoints;
			if (iMinutes < 90) {
				tmp = tp.getSecondarySkillSecondaryPositionMinutes();
				if (tmp > 0) {
					if (iMinutes + tmp > 90) {
						tmp = 90 - iMinutes;
					}
					iMinutes += tmp;
					dSecondaryTraining = (tmp / 90) / _SecondaryTrainingSkillSecondaryLengthRate;
				}
				 if (iMinutes < 90) {
					 tmp = tp.getSecondarySkillOsmosisPositionMinutes();
					 if (tmp > 0) {
						 if (iMinutes + tmp > 90) {
							 tmp = 90 - iMinutes;
						 }
						 iMinutes += tmp;
						 dSecondaryTraining = (tmp / 90) / _SecondaryTrainingSkillOsmosisLengthRate;
					 }
				 }	 
			}
		}
		return dSecondaryTraining;
	}
}
