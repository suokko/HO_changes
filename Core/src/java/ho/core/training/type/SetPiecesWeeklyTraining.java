package ho.core.training.type;

import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerSkill;
import ho.core.model.UserParameter;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.Spieler;
import ho.core.training.WeeklyTrainingType;

public class SetPiecesWeeklyTraining extends WeeklyTrainingType {
	protected static SetPiecesWeeklyTraining m_ciInstance = null;
	private SetPiecesWeeklyTraining()
	{
		_Name = "Set Pieces";
		_TrainingType = TrainingType.SET_PIECES;
		_PrimaryTrainingSkill = PlayerSkill.SET_PIECES;
		_SecondaryTrainingSkill = 0;
		_PrimaryTrainingSkillPositions = new int[]{ ISpielerPosition.keeper,
				ISpielerPosition.leftBack, ISpielerPosition.rightBack, ISpielerPosition.leftCentralDefender,
				ISpielerPosition.middleCentralDefender, ISpielerPosition.rightCentralDefender,
				ISpielerPosition.rightWinger, ISpielerPosition.leftWinger, 
				ISpielerPosition.leftInnerMidfield, ISpielerPosition.centralInnerMidfield, 
				ISpielerPosition.rightInnerMidfield, ISpielerPosition.leftForward, 
				ISpielerPosition.centralForward, ISpielerPosition.rightForward};
		_PrimaryTrainingSkillBonusPositions = new int[]{ ISpielerPosition.keeper, ISpielerPosition.setPieces };
		_PrimaryTrainingBaseLength = (float) 0.665;
		_PrimaryTrainingSkillBaseLength = _PrimaryTrainingBaseLength + UserParameter.instance().TRAINING_OFFSET_SETPIECES; // 100%
		_PrimaryTrainingSkillBonus = (float) 0.25;
		_PrimaryTrainingSkillOsmosisLengthRate = 0;
	}
	public static WeeklyTrainingType instance() {
        if (m_ciInstance == null) {
        	m_ciInstance = new SetPiecesWeeklyTraining();
        }
        return m_ciInstance;
    }
	@Override
	public double getTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina) 
	{
		return calcTraining(getPrimaryTrainingSkillBaseLength(), player.getAlter(), assistants, trainerLevel, 
				intensity, stamina, player.getStandards());
	}
	@Override
	public double getSecondaryTrainingLength(Spieler player, int assistants, int trainerLevel, int intensity, int stamina)
	{
		return (double) -1;
	}
}
