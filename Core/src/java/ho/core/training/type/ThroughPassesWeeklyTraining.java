package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;

public class ThroughPassesWeeklyTraining extends WeeklyTrainingType {
	private ThroughPassesWeeklyTraining()
	{
		_Name = "Through Passes";
		_TrainingType = TrainingType.THROUGH_PASSES;
		_PrimaryTrainingSkill = PlayerSkill.PASSING;
		_PrimaryTrainingSkillPositions = new int[]{ ISpielerPosition.leftBack, 
				ISpielerPosition.rightBack, ISpielerPosition.leftCentralDefender,
				ISpielerPosition.middleCentralDefender, ISpielerPosition.rightCentralDefender,
				ISpielerPosition.rightWinger, ISpielerPosition.leftWinger, 
				ISpielerPosition.leftInnerMidfield, ISpielerPosition.centralInnerMidfield, 
				ISpielerPosition.rightInnerMidfield };
		_PrimaryTrainingSkillOsmosisTrainingPositions = new int[] { ISpielerPosition.keeper,
				ISpielerPosition.leftForward, ISpielerPosition.centralForward, ISpielerPosition.rightForward}; 
		_PrimaryTrainingSkillBaseSpeed = ((float) 2.8 + UserParameter.instance().TRAINING_OFFSET_PASSING) / (float) 0.8;
		_PrimaryTrainingSkillOsmosisSpeed = _PrimaryTrainingSkillBaseSpeed / (float) 0.16;
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new ThroughPassesWeeklyTraining();
        }
        return m_ciInstance;
    }
}