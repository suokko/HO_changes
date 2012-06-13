package ho.module.lineup.substitution;

import ho.module.lineup.Lineup;
import ho.module.lineup.substitution.model.Substitution;

public class PlausibilityCheck {

	public static boolean areBothPlayersInLineup(Lineup lineup, Substitution substitution) {
		return lineup.isSpielerAufgestellt(substitution.getPlayerIn())
				&& lineup.isSpielerAufgestellt(substitution.getPlayerOut());
	}

}
