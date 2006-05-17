// %3987013374:hoplugins.stt%
package de.hattrickorganizer.logik.price;

import plugins.IEPVData;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class KeeperCalculator extends Calculator {
	//~ Static fields/initializers -----------------------------------------------------------------
	public KeeperCalculator(Net netData) {
		super(netData);
	}

	protected double[] normalizeParameter(IEPVData player, int week) {

		double[] normalizedParams = new double[LAYER[0]];
		normalizedParams[0] = getFullAge(player.getAge(),week);
		normalizedParams[1] = player.getForm() / 8d;
		normalizedParams[2] = player.getGoalKeeping() / 20d;
		normalizedParams[3] = player.getSetPieces() / 20d;
		normalizedParams[4] = week / 16d;
		normalizedParams[5] = Math.pow(player.getExperience() / 20d,2.0);

		return normalizedParams;
	}


}
