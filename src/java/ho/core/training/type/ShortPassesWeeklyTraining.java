package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;

public class ShortPassesWeeklyTraining extends WeeklyTrainingType {
	private ShortPassesWeeklyTraining()
	{
		_Name = "Short Passes";
		_TrainingType = TrainingType.SHORT_PASSES;
		_PrimaryTrainingSkill = PlayerSkill.PASSING;
		_PrimaryTrainingSkillPositions = new int[]{ 
				ISpielerPosition.rightWinger, ISpielerPosition.leftWinger, 
				ISpielerPosition.leftInnerMidfield, ISpielerPosition.centralInnerMidfield, 
				ISpielerPosition.rightInnerMidfield, ISpielerPosition.leftForward, 
				ISpielerPosition.centralForward, ISpielerPosition.rightForward};
		_PrimaryTrainingSkillOsmosisTrainingPositions = new int[] { ISpielerPosition.keeper,
				ISpielerPosition.leftBack, ISpielerPosition.rightBack, ISpielerPosition.leftCentralDefender,
				ISpielerPosition.middleCentralDefender, ISpielerPosition.rightCentralDefender }; 
		_PrimaryTrainingSkillBaseSpeed = (float) 2.8 + UserParameter.instance().TRAINING_OFFSET_PASSING;
		_PrimaryTrainingSkillOsmosisSpeed = _PrimaryTrainingSkillBaseSpeed / (float) 0.16;
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new ShortPassesWeeklyTraining();
        }
        return m_ciInstance;
    }
}