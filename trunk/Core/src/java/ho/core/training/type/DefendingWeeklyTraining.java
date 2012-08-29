package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.training.WeeklyTrainingType;

public final class DefendingWeeklyTraining extends WeeklyTrainingType {
	protected static DefendingWeeklyTraining m_ciInstance = null;
	private DefendingWeeklyTraining()
	{
		_Name = "Defending";
		_TrainingType = TrainingType.DEFENDING;
		_PrimaryTrainingSkill = PlayerSkill.DEFENDING;
		_PrimaryTrainingSkillPositions = new int[]{ 
				ISpielerPosition.leftBack, ISpielerPosition.rightBack, ISpielerPosition.leftCentralDefender,
				ISpielerPosition.middleCentralDefender, ISpielerPosition.rightCentralDefender};
		_PrimaryTrainingSkillOsmosisTrainingPositions = new int[]{
				ISpielerPosition.keeper, ISpielerPosition.rightWinger, ISpielerPosition.leftWinger, 
				ISpielerPosition.leftInnerMidfield, ISpielerPosition.centralInnerMidfield, 
				ISpielerPosition.rightInnerMidfield, ISpielerPosition.leftForward, 
				ISpielerPosition.centralForward, ISpielerPosition.rightForward};
		_PrimaryTrainingBaseLength = (float) 3.6;
		_PrimaryTrainingSkillBaseLength = _PrimaryTrainingBaseLength + UserParameter.instance().TRAINING_OFFSET_DEFENDING; // 100%
		_PrimaryTrainingSkillSecondaryLengthRate = (float) 2; // 50%
	}
	public static DefendingWeeklyTraining instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new DefendingWeeklyTraining();
        }
        return m_ciInstance;
    }
	@Override
	 public double getTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina) 
	 {
		 return calcTraining(getPrimaryTrainingSkillBaseLength(), player.getAlter(), assistants, trainerLevel, 
				 intensity, stamina, player.getVerteidigung());
	 }
	 @Override
	 public double getSecondaryTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina)
	 {
		 return (double) -1;
	 }	 
}
