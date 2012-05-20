package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;

public class WingAttacksWeeklyTraining extends WeeklyTrainingType {
	private WingAttacksWeeklyTraining()
	{
		_Name = "Wing Attacks";
		_TrainingType = TrainingType.WING_ATTACKS;
		_PrimaryTrainingSkill = PlayerSkill.WINGER;
		_PrimaryTrainingSkillPositions = new int[]{ ISpielerPosition.rightWinger, ISpielerPosition.leftWinger, 
				ISpielerPosition.leftForward, ISpielerPosition.centralForward, ISpielerPosition.rightForward };
		_PrimaryTrainingSkillOsmosisTrainingPositions = new int[] { ISpielerPosition.keeper, ISpielerPosition.leftBack, 
				ISpielerPosition.rightBack, ISpielerPosition.leftCentralDefender,
				ISpielerPosition.middleCentralDefender, ISpielerPosition.rightCentralDefender,
				ISpielerPosition.leftInnerMidfield, ISpielerPosition.centralInnerMidfield, 
				ISpielerPosition.rightInnerMidfield}; 
		_PrimaryTrainingSkillBaseSpeed = ((float) 2.2 + UserParameter.instance().TRAINING_OFFSET_PLAYMAKING) / (float) 0.8 ;
		_PrimaryTrainingSkillOsmosisSpeed = _PrimaryTrainingSkillBaseSpeed / (float) 0.16;
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new WingAttacksWeeklyTraining();
        }
        return m_ciInstance;
    }
}
