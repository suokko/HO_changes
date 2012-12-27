package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.training.WeeklyTrainingType;

public class CrossingWeeklyTraining extends WeeklyTrainingType {
	protected static CrossingWeeklyTraining m_ciInstance = null;
	private CrossingWeeklyTraining()
	{
		_Name = "Crossing";
		_TrainingType = TrainingType.CROSSING_WINGER;
		_PrimaryTrainingSkill = PlayerSkill.WINGER;
		_PrimaryTrainingSkillPositions = new int[]{ 
				ISpielerPosition.leftWinger, ISpielerPosition.rightWinger };
		_PrimaryTrainingSkillSecondaryTrainingPositions = new int[]{
				ISpielerPosition.leftBack, ISpielerPosition.rightBack};
		_PrimaryTrainingSkillOsmosisTrainingPositions = new int[]{
				ISpielerPosition.keeper, ISpielerPosition.leftCentralDefender, 
				ISpielerPosition.middleCentralDefender, ISpielerPosition.rightCentralDefender,
				ISpielerPosition.leftInnerMidfield, ISpielerPosition.centralInnerMidfield,
				ISpielerPosition.rightInnerMidfield, ISpielerPosition.leftForward, 
				ISpielerPosition.centralForward, ISpielerPosition.rightForward};
		_PrimaryTrainingBaseLength = (float) 2.2;
		_PrimaryTrainingSkillBaseLength = _PrimaryTrainingBaseLength + UserParameter.instance().TRAINING_OFFSET_WINGER; // 100%
		_PrimaryTrainingSkillSecondaryLengthRate = (float) 2; // 50%
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new CrossingWeeklyTraining();
        }
        return m_ciInstance;
    }
	@Override
	public double getTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina) {
        return calcTraining(getPrimaryTrainingSkillBaseLength(), player.getAlter(), assistants, trainerLevel, 
        		intensity, stamina, player.getFluegelspiel());
	}
	@Override
	public double getSecondaryTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina)
	{
		return (double) -1;
	}
}
