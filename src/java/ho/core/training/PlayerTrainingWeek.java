package ho.core.training;
import ho.core.constants.TrainingType;
import ho.core.model.player.Spieler;
import ho.core.training.type.*;
public class PlayerTrainingWeek {
	Spieler _Player = null;
	WeeklyTrainingType _TrainingType = null;
	int _PrimaryPositionTrainingMinutes = 0;
	int _SecondaryPositionTrainingMinutes = 0;
	int _PrimaryOsmosisPositionTrainingMinutes = 0;
	int _SecondaryOsmosisPositionTrainingMinutes = 0;
	int _TotalMinutesPlayed = 0;
	
	public PlayerTrainingWeek(Spieler oPlayer, int iTrainingType)
	{
		_Player = oPlayer;
		switch (iTrainingType) {
			case TrainingType.CROSSING_WINGER:
				_TrainingType = CrossingWeeklyTraining.instance();
				break;
			case TrainingType.DEF_POSITIONS:
				_TrainingType = DefensivePositionsWeeklyTraining.instance();
				break;
			case TrainingType.DEFENDING:
				_TrainingType = DefendingWeeklyTraining.instance();
				break;
			case TrainingType.GOALKEEPING:
				_TrainingType = GoalkeepingWeeklyTraining.instance();
				break;
			case TrainingType.PLAYMAKING:
				_TrainingType = PlaymakingWeeklyTraining.instance();
				break;
			case TrainingType.SCORING:
				_TrainingType = ScoringWeeklyTraining.instance();
				break;
			case TrainingType.SET_PIECES:
				_TrainingType = SetPiecesWeeklyTraining.instance();
				break;
			case TrainingType.SHOOTING:
				_TrainingType = ShootingWeeklyTraining.instance();
				break;
			case TrainingType.SHORT_PASSES:
				_TrainingType = ShortPassesWeeklyTraining.instance();
				break;
			case TrainingType.THROUGH_PASSES:
				_TrainingType = ThroughPassesWeeklyTraining.instance();
				break;
			case TrainingType.WING_ATTACKS:
				_TrainingType = WingAttacksWeeklyTraining.instance();
				break;
		}
	}
	public void AddMinutes(int iPosition, int iMinutes)
	{
		_TotalMinutesPlayed += iMinutes;
		switch (_TrainingType.isTrained(iPosition)) {
			case 1:
				_PrimaryPositionTrainingMinutes += iMinutes;
				break;
			case 2:
				_PrimaryOsmosisPositionTrainingMinutes += iMinutes;
				break;
			case 3:
				_SecondaryPositionTrainingMinutes += iMinutes;
				break;
			case 4:
				_SecondaryOsmosisPositionTrainingMinutes += iMinutes;
				break;
		}
	}
}
