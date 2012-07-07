package ho.module.lineup.penalties;

import ho.core.constants.player.PlayerSpeciality;
import ho.core.model.player.Spieler;

public class PenaltyUtils {

	/**
	 * Utility class - private constructor enforces noninstantiability.
	 */
	private PenaltyUtils() {
	}

	public static float getAbility(Spieler player) {
		float ability = 0;
		if (player != null) {
			ability = (player.getErfahrung() * 1.5f)
					+ (((player.getStandards() * 7.0f) / 10.0f) + ((player.getTorschuss() * 3.0f) / 10.0f));

			if (player.getSpezialitaet() == PlayerSpeciality.TECHNICAL) {
				ability *= 1.1;
			}
		}
		return ability;
	}

}
