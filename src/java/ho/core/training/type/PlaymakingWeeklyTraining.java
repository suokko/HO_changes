package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.training.WeeklyTrainingType;

public final class PlaymakingWeeklyTraining extends WeeklyTrainingType {
	protected static PlaymakingWeeklyTraining m_ciInstance = null;
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
		_PrimaryTrainingBaseLength = (float) 3.1;
		_PrimaryTrainingSkillBaseLength = _PrimaryTrainingBaseLength + UserParameter.instance().TRAINING_OFFSET_PLAYMAKING; // 100%
		_PrimaryTrainingSkillSecondaryLengthRate = (float) 2; // 50%
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new PlaymakingWeeklyTraining();
        }
        return m_ciInstance;
    }
	@Override
	public double getTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina) 
	{
		return calcTraining(getPrimaryTrainingSkillBaseLength(), player.getAlter(), assistants, trainerLevel, 
				intensity, stamina, player.getSpielaufbau());
	}
	@Override
	public double getSecondaryTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina)
	{
		return (double) -1;
	}
}
