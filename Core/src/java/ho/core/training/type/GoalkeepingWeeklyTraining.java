package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;

public final class GoalkeepingWeeklyTraining extends WeeklyTrainingType {
	private GoalkeepingWeeklyTraining()
	{
		_Name = "Goalkeeping";
		_TrainingType = TrainingType.GOALKEEPING;
		_PrimaryTrainingSkill = PlayerSkill.KEEPER;
		_PrimaryTrainingSkillPositions = new int[]{ ISpielerPosition.keeper };
		_PrimaryTrainingSkillBaseSpeed = (float) 2 + UserParameter.instance().TRAINING_OFFSET_GOALKEEPING; // 100%
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new GoalkeepingWeeklyTraining();
        }
        return m_ciInstance;
    }
}
