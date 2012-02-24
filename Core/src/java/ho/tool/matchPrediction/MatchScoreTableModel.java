// %1590051216:de.hattrickorganizer.gui.model%
/*
 * MatchPredictionSpieleTableModel.java
 *
 * Created on 4. Januar 2005, 13:19
 */
package ho.tool.matchPrediction;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.model.HOVerwaltung;

import javax.swing.SwingConstants;

import plugins.IMatchResult;


/**
 * DOCUMENT ME!
 *
 * @author Pirania
 */
public class MatchScoreTableModel extends AbstractMatchTableModel {
	//~ Instance fields ----------------------------------------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = -2007343001155380888L;
	/** TODO Missing Parameter Documentation */
	protected static String[] columnNames =
		{
			HOVerwaltung.instance().getLanguageString("Tore"),
			HOVerwaltung.instance().getLanguageString("Heim"),
			HOVerwaltung.instance().getLanguageString("Gast")};

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new MatchPredictionSpieleTableModel object.
	 *
	 * @param vErgebnisse TODO Missing Constructuor Parameter Documentation
	 */
	public MatchScoreTableModel(IMatchResult matchResult,boolean ishome ) {
		super(matchResult,ishome);
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * Erzeugt einen Data[][] aus dem Spielervector
	 */
	@Override
	protected void initData() {
		m_clData = new Object[5][columnNames.length];
		double number = matchResult.getMatchNumber();
		if (number == 0.0) {
			number = 1.0;
		}

		int[][] goal = new int[5][2];
		for (int i = 0; i < 25; i++) {
			int n = matchResult.getResultDetail()[i];
			int home = i / 5;
			int away = i - home*5;
			goal[home][0] += n;
			goal[away][1] += n;
		}

		for (int score = 0; score < 5; score++) {

			m_clData[score][0] =
				new ColorLabelEntry(
						String.valueOf(score),
					ColorLabelEntry.FG_STANDARD,
					ColorLabelEntry.BG_STANDARD,
					SwingConstants.LEFT);

			m_clData[score][1] = getProgressBar(goal[score][0] / number);
			m_clData[score][2] = getProgressBar(goal[score][1] / number);

		}
	}

	@Override
	public String[] getColumnNames() {
		return columnNames;
	}

}
