package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;

public class ShootingWeeklyTraining extends WeeklyTrainingType {
	protected static ShootingWeeklyTraining m_ciInstance = null;
	private ShootingWeeklyTraining()
	{
		_Name = "Shooting";
		_TrainingType = TrainingType.SHOOTING;
		_PrimaryTrainingSkill = PlayerSkill.SCORING;
		_SecondaryTrainingSkill = PlayerSkill.SET_PIECES;
		_PrimaryTrainingSkillPositions = new int[]{ ISpielerPosition.keeper,
				ISpielerPosition.leftBack, ISpielerPosition.rightBack, ISpielerPosition.leftCentralDefender,
				ISpielerPosition.middleCentralDefender, ISpielerPosition.rightCentralDefender,
				ISpielerPosition.rightWinger, ISpielerPosition.leftWinger, 
				ISpielerPosition.leftInnerMidfield, ISpielerPosition.centralInnerMidfield, 
				ISpielerPosition.rightInnerMidfield, ISpielerPosition.leftForward, 
				ISpielerPosition.centralForward, ISpielerPosition.rightForward};
		_PrimaryTrainingBaseLength = (float) 3.2 / (float) 0.6;
		_PrimaryTrainingSkillBaseLength = _PrimaryTrainingBaseLength + UserParameter.instance().TRAINING_OFFSET_SCORING / (float) 0.6;
		_SecondaryTrainingSkillPositions = new int[]{ ISpielerPosition.keeper,
				ISpielerPosition.leftBack, ISpielerPosition.rightBack, ISpielerPosition.leftCentralDefender,
				ISpielerPosition.middleCentralDefender, ISpielerPosition.rightCentralDefender,
				ISpielerPosition.rightWinger, ISpielerPosition.leftWinger, 
				ISpielerPosition.leftInnerMidfield, ISpielerPosition.centralInnerMidfield, 
				ISpielerPosition.rightInnerMidfield, ISpielerPosition.leftForward, 
				ISpielerPosition.centralForward, ISpielerPosition.rightForward};
		_SecondaryTrainingSkillBaseLength = ((float) 0.9 + UserParameter.instance().TRAINING_OFFSET_SETPIECES) / (float) 0.6;
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new ShootingWeeklyTraining();
        }
        return m_ciInstance;
    }
}
