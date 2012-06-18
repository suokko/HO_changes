package ho.module.lineup.substitution.plausibility;

import ho.module.lineup.Lineup;
import ho.module.lineup.substitution.model.Substitution;

public class PlausibilityCheck {
	
	public static Problem checkForProblem(Lineup lineup, Substitution substitution) {
		if (!lineup.isSpielerAufgestellt(substitution.getPlayerIn())) {
			return Error.PLAYERIN_NOT_IN_LINEUP;
		} else if (!lineup.isSpielerAufgestellt(substitution.getPlayerOut())) {
			return Error.PLAYEROUT_NOT_IN_LINEUP;
		}
		return null;
	}

}
