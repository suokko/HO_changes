package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;

public class DefensivePositionsWeeklyTraining extends WeeklyTrainingType {
	protected static DefensivePositionsWeeklyTraining m_ciInstance = null;
	private DefensivePositionsWeeklyTraining()
	{
		_Name = "Defensive Positions";
		_TrainingType = TrainingType.DEF_POSITIONS;
		_PrimaryTrainingSkill = PlayerSkill.DEFENDING;
		_PrimaryTrainingSkillPositions = new int[]{ ISpielerPosition.keeper, ISpielerPosition.leftBack, 
				ISpielerPosition.rightBack, ISpielerPosition.leftCentralDefender,
				ISpielerPosition.middleCentralDefender, ISpielerPosition.rightCentralDefender,
				ISpielerPosition.rightWinger, ISpielerPosition.leftWinger, 
				ISpielerPosition.leftInnerMidfield, ISpielerPosition.centralInnerMidfield, 
				ISpielerPosition.rightInnerMidfield };
		_PrimaryTrainingSkillOsmosisTrainingPositions = new int[] { ISpielerPosition.leftForward, 
				ISpielerPosition.centralForward, ISpielerPosition.rightForward}; 
		_PrimaryTrainingBaseLength = (float) 7.2;
		_PrimaryTrainingSkillBaseLength = _PrimaryTrainingBaseLength + UserParameter.instance().TRAINING_OFFSET_DEFENDING;
		_PrimaryTrainingSkillOsmosisLengthRate = (float) 6.25;
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new DefensivePositionsWeeklyTraining();
        }
        return m_ciInstance;
    }
}
