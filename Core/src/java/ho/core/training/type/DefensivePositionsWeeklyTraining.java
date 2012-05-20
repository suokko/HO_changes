package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;

public class DefensivePositionsWeeklyTraining extends WeeklyTrainingType {
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
		_PrimaryTrainingSkillBaseSpeed = ((float) 3.6 + UserParameter.instance().TRAINING_OFFSET_DEFENDING) / (float) 0.6 ;
		_PrimaryTrainingSkillOsmosisSpeed = _PrimaryTrainingSkillBaseSpeed / (float) 0.16;
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new DefensivePositionsWeeklyTraining();
        }
        return m_ciInstance;
    }
}
