package ho.module.lineup.substitution;

import ho.module.lineup.Lineup;

public class PlausibilityCheck {

	public static boolean areBothPlayersInLineup(Lineup lineup, Substitution substitution) {
		return lineup.isSpielerAufgestellt(substitution.getPlayerIn())
				&& lineup.isSpielerAufgestellt(substitution.getPlayerOut());
	}

}
