package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;
import ho.core.training.type.WeeklyTrainingType;

public final class PlaymakingWeeklyTraining extends WeeklyTrainingType {
	private PlaymakingWeeklyTraining()
	{
		_Name = "Playmaking";
		_TrainingType = TrainingType.PLAYMAKING;
		_PrimaryTrainingSkill = PlayerSkill.PLAYMAKING;
		_PrimaryTrainingSkillPositions = new int[]{ 
				ISpielerPosition.leftInnerMidfield, ISpielerPosition.rightInnerMidfield, 
				ISpielerPosition.centralInnerMidfield};
		_PrimaryTrainingSkillSecondaryTrainingPositions = new int[]{
				ISpielerPosition.leftWinger, ISpielerPosition.rightWinger};
		_PrimaryTrainingSkillOsmosisTrainingPositions = new int[]{
				ISpielerPosition.keeper, ISpielerPosition.rightBack, ISpielerPosition.leftBack, 
				ISpielerPosition.leftCentralDefender, ISpielerPosition.middleCentralDefender, 
				ISpielerPosition.rightCentralDefender, ISpielerPosition.leftForward, 
				ISpielerPosition.centralForward, ISpielerPosition.rightForward};
		_PrimaryTrainingSkillBaseSpeed = (float) 3.1 + UserParameter.instance().TRAINING_OFFSET_PLAYMAKING; // 100%
		_PrimaryTrainingSkillSecondarySpeed = _PrimaryTrainingSkillBaseSpeed / (float) 0.5; // 50%
		_PrimaryTrainingSkillOsmosisSpeed = _PrimaryTrainingSkillBaseSpeed / (float) 0.16; // 16%
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new PlaymakingWeeklyTraining();
        }
        return m_ciInstance;
    }
}
