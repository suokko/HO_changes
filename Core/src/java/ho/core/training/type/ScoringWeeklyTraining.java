package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;

public class ScoringWeeklyTraining extends WeeklyTrainingType {
	private ScoringWeeklyTraining()
	{
		_Name = "Scoring";
		_TrainingType = TrainingType.SCORING;
		_PrimaryTrainingSkill = PlayerSkill.SCORING;
		_PrimaryTrainingSkillPositions = new int[]{ 
				ISpielerPosition.leftForward, ISpielerPosition.rightForward, ISpielerPosition.centralForward };
		_PrimaryTrainingSkillOsmosisTrainingPositions = new int[]{
				ISpielerPosition.keeper, ISpielerPosition.leftBack, ISpielerPosition.rightBack,
				ISpielerPosition.leftCentralDefender, ISpielerPosition.middleCentralDefender,
				ISpielerPosition.rightCentralDefender, ISpielerPosition.rightWinger, 
				ISpielerPosition.leftWinger, ISpielerPosition.leftInnerMidfield, 
				ISpielerPosition.centralInnerMidfield, ISpielerPosition.rightInnerMidfield};
		_PrimaryTrainingSkillBaseSpeed = (float) 3.2 + UserParameter.instance().TRAINING_OFFSET_SCORING; // 100%
		_PrimaryTrainingSkillSecondarySpeed = _PrimaryTrainingSkillBaseSpeed / (float) 0.5; // 50%
		_PrimaryTrainingSkillOsmosisSpeed = _PrimaryTrainingSkillBaseSpeed / (float) 0.16; // 16%
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new ScoringWeeklyTraining();
        }
        return m_ciInstance;
    }
}
