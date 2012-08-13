package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.training.WeeklyTrainingType;

public final class GoalkeepingWeeklyTraining extends WeeklyTrainingType {
	protected static GoalkeepingWeeklyTraining m_ciInstance = null;
	private GoalkeepingWeeklyTraining()
	{
		_Name = "Goalkeeping";
		_TrainingType = TrainingType.GOALKEEPING;
		_PrimaryTrainingSkill = PlayerSkill.KEEPER;
		_PrimaryTrainingSkillPositions = new int[]{ ISpielerPosition.keeper };
		_PrimaryTrainingBaseLength = (float) 2;
		_PrimaryTrainingSkillBaseLength = _PrimaryTrainingBaseLength + UserParameter.instance().TRAINING_OFFSET_GOALKEEPING; // 100%
		_PrimaryTrainingSkillOsmosisLengthRate = 0;
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new GoalkeepingWeeklyTraining();
        }
        return m_ciInstance;
    }
	@Override
	public double getTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina) {
		return calcTraining(getPrimaryTrainingSkillBaseLength(), player.getAlter(), assistants, trainerLevel, 
				intensity, stamina, player.getTorwart());
	}
	@Override
	public double getSecondaryTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina)
	{
		return (double) -1;
	}
}
