package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;

public class ThroughPassesWeeklyTraining extends WeeklyTrainingType {
	protected static ThroughPassesWeeklyTraining m_ciInstance = null;
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
		_PrimaryTrainingBaseLength = (float) 2.8 / (float) 0.85;
		_PrimaryTrainingSkillBaseLength = _PrimaryTrainingBaseLength + UserParameter.instance().TRAINING_OFFSET_PASSING / (float) 0.85;
		_PrimaryTrainingSkillOsmosisLengthRate = (float) 6.25;
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new ThroughPassesWeeklyTraining();
        }
        return m_ciInstance;
    }
}