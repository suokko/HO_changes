// %3987013374:hoplugins.stt%
package de.hattrickorganizer.logik.price;

import plugins.IEPVData;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class RoleCalculator extends Calculator {
	//~ Static fields/initializers -----------------------------------------------------------------
	public RoleCalculator(Net netData) {
		super(netData);
	}

	protected double[] normalizeParameter(IEPVData player, int WEEK) {

		double[] normalizedParams = new double[LAYER[0]];
		normalizedParams[0] = getFullAge(player.getAge(),WEEK);
		normalizedParams[1] = player.getForm() / 8d;
		normalizedParams[2] = player.getStamina() / 9d;
		normalizedParams[3] = player.getGoalKeeping() / 20d;
		normalizedParams[4] = player.getPlayMaking() / 20d;
		normalizedParams[5] = player.getPassing() / 20d;
		normalizedParams[6] = player.getWing() / 20d;
		normalizedParams[7] = player.getDefense() / 20d;
		normalizedParams[8] = player.getAttack() / 20d;
		normalizedParams[9] = player.getSetPieces() / 20d;
		normalizedParams[10] = WEEK / 16d;
		normalizedParams[11] = Math.pow(player.getExperience() / 20d,2.0);
		return normalizedParams;
	}

}
