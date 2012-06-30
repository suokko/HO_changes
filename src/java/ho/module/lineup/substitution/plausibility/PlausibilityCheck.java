package ho.module.lineup.substitution.plausibility;

import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;
import ho.core.model.player.SpielerPosition;
import ho.module.lineup.Lineup;
import ho.module.lineup.LineupAssistant;
import ho.module.lineup.substitution.LanguageStringLookup;
import static ho.module.lineup.substitution.model.MatchOrderType.*;
import ho.module.lineup.substitution.model.Substitution;

public class PlausibilityCheck {

	public static Problem checkForProblem(Lineup lineup, Substitution substitution) {
		int subjectPlayerID = substitution.getSubjectPlayerID();
		int objectPlayerID = substitution.getObjectPlayerID();

		// check if there is a subjectPlayer
		if (subjectPlayerID <= 0 || !LineupAssistant.isPlayerInTeam(subjectPlayerID)) {
			if (substitution.getOrderType() == SUBSTITUTION) {
				return Error.SUBSTITUTION_PLAYER_MISSING;
			} else if (substitution.getOrderType() == POSITION_SWAP) {
				return Error.POSITIONSWAP_PLAYER_MISSING;
			} else if (substitution.getOrderType() == NEW_BEHAVIOUR) {
				return Error.NEWBEHAVIOUR_PLAYER_MISSING;
			}
		}

		// check if there is a objectPlayerID (not relevant for NEW_BEHAVIOUR)
		if (substitution.getOrderType() != NEW_BEHAVIOUR) {
			if (objectPlayerID <= 0 || !LineupAssistant.isPlayerInTeam(objectPlayerID)) {
				if (substitution.getOrderType() == SUBSTITUTION) {
					return Error.SUBSTITUTION_PLAYER_MISSING;
				} else if (substitution.getOrderType() == POSITION_SWAP) {
					return Error.POSITIONSWAP_PLAYER_MISSING;
				}
			}
		}

		// check if players in lineup. (for NEW_BEHAVIOUR, there is only one
		// player involved)
		if (substitution.getOrderType() != NEW_BEHAVIOUR
				&& !lineup.isSpielerAufgestellt(objectPlayerID)) {
			return Error.PLAYERIN_NOT_IN_LINEUP;
		} else if (!lineup.isSpielerAufgestellt(subjectPlayerID)) {
			return Error.PLAYEROUT_NOT_IN_LINEUP;
		}

		// when NEW_BEHAVIOUR, check that behaviour there is really a change
		if (substitution.getOrderType() == NEW_BEHAVIOUR) {
			SpielerPosition pos = lineup.getPositionBySpielerId(subjectPlayerID);
			if (pos.getTaktik() == substitution.getBehaviour()) {
				return Uncertainty.SAME_TACTIC;
			}
		}
		return null;
	}

	public static String getComment(Problem problem, Substitution substitution) {
		if (problem instanceof Error) {
			switch ((Error) problem) {
			case PLAYERIN_NOT_IN_LINEUP:
				return HOVerwaltung.instance().getLanguageString(problem.getLanguageKey(),
						getPlayerIn(substitution).getName());
			case PLAYEROUT_NOT_IN_LINEUP:
				return HOVerwaltung.instance().getLanguageString(problem.getLanguageKey(),
						getPlayerOut(substitution).getName());
			default:
				return HOVerwaltung.instance().getLanguageString(problem.getLanguageKey());
			}
		} else if (problem instanceof Uncertainty) {
			switch ((Uncertainty) problem) {
			case SAME_TACTIC:
				return HOVerwaltung.instance().getLanguageString(problem.getLanguageKey(),
						getPlayerOut(substitution).getName(),
						LanguageStringLookup.getBehaviour(substitution.getBehaviour()));
			}
		}

		return null;
	}

	private static Spieler getPlayerIn(Substitution substitution) {
		return HOVerwaltung.instance().getModel().getSpieler(substitution.getObjectPlayerID());
	}

	private static Spieler getPlayerOut(Substitution substitution) {
		return HOVerwaltung.instance().getModel().getSpieler(substitution.getSubjectPlayerID());
	}
}
