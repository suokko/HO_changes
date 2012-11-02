package ho.module.statistics;

import ho.core.datatype.CBItem;
import ho.core.model.HOVerwaltung;
import ho.module.matches.SpielePanel;

public class Helper {

	private Helper() {

	}

	public static double getMaxValue(double[] werte) {
		double max = 0;
		for (int i = 0; (werte != null) && (i < werte.length); i++) {
			if (werte[i] > max) {
				max = werte[i];
			}
		}
		return (max);
	}
}
