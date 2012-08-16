package ho.module.lineup.penalties;

import ho.core.constants.player.PlayerSpeciality;
import ho.core.model.player.Spieler;

public class PenaltyUtils {

	/**
	 * Utility class - private constructor enforces noninstantiability.
	 */
	private PenaltyUtils() {
	}

	public static double getAbility(Spieler player) {
		if (player == null)
			return 0.0;

		return getAbility(new PenaltyTaker(player));
	}

	public static double getAbility(PenaltyTaker taker) {
		double ability = 0;
		if (taker != null) {
			ability = (taker.getExperience() * 1.5)
					+ (((taker.getSetPieces() * 7.0) / 10.0) + ((taker
							.getScoring() * 3.0) / 10.0));

			if (taker.getPlayer().getSpezialitaet() == PlayerSpeciality.TECHNICAL) {
				ability *= 1.1;
			}
		}
		return ability;
	}
}
