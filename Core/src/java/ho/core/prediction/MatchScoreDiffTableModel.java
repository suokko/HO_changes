// %1590051216:de.hattrickorganizer.gui.model%
/*
 * MatchPredictionSpieleTableModel.java
 *
 * Created on 4. Januar 2005, 13:19
 */
package ho.core.prediction;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.model.HOVerwaltung;
import ho.core.prediction.engine.MatchResult;

import javax.swing.SwingConstants;


/**
 * DOCUMENT ME!
 *
 * @author Pirania
 */
public class MatchScoreDiffTableModel extends AbstractMatchTableModel {
	//~ Instance fields ----------------------------------------------------------------------------

	/**
	 *
	 */
	private static final long serialVersionUID = -2114489862284776054L;
	/** TODO Missing Parameter Documentation */
	protected static String[] columnNames =
		{
			HOVerwaltung.instance().getLanguageString("ls.match.result"),
			HOVerwaltung.instance().getLanguageString("frequency")};

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new MatchPredictionSpieleTableModel object.
	 *
	 * @param vErgebnisse TODO Missing Constructuor Parameter Documentation
	 */
	public MatchScoreDiffTableModel(MatchResult matchresult,boolean ishome) {
		super(matchresult,ishome);
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * Erzeugt einen Data[][] aus dem Spielervector
	 */
	@Override
	protected void initData() {
		m_clData = new Object[9][columnNames.length];
		double number = matchResult.getMatchNumber();
		if (number == 0.0) {
			number = 1.0;
		}

		int[] result = new int[9];
		for (int i = 0; i < 25; i++) {
			int n = matchResult.getResultDetail()[i];
			int home = i / 5;
			int away = i - home*5;
			int diff = home - away;
			result[diff + 4] += n;
		}

		String homeWin = HOVerwaltung.instance().getLanguageString("Winby");
		String awayWin = HOVerwaltung.instance().getLanguageString("Lostby");
		if (!isHomeMatch()) {
			awayWin = HOVerwaltung.instance().getLanguageString("Winby");
			homeWin  = HOVerwaltung.instance().getLanguageString("Lostby");

		}
		for (int i = 8; i > 4; i--) {

			m_clData[8 - i][0] =
				new ColorLabelEntry(
					homeWin +" "+ (i - 4),
					ColorLabelEntry.FG_STANDARD,
					ColorLabelEntry.BG_STANDARD,
					SwingConstants.LEFT);

			m_clData[8 - i][1] = getProgressBar(result[i] / number);

		}

		m_clData[4][0] =
			new ColorLabelEntry(
				HOVerwaltung.instance().getLanguageString("Unendschieden"),
				ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD,
				SwingConstants.LEFT);

		m_clData[4][1] = getProgressBar(result[4] / number);

		for (int i = 3; i >= 0; i--) {

			m_clData[8 - i][0] =
				new ColorLabelEntry(
		awayWin + " " + (4 - i),
					ColorLabelEntry.FG_STANDARD,
					ColorLabelEntry.BG_STANDARD,
					SwingConstants.LEFT);

			m_clData[8 - i][1] = getProgressBar(result[i] / number);

		}

	}

	@Override
	public String[] getColumnNames() {
		return columnNames;
	}

}
