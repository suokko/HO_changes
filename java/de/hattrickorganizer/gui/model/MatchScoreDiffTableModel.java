// %1590051216:de.hattrickorganizer.gui.model%
/*
 * MatchPredictionSpieleTableModel.java
 *
 * Created on 4. Januar 2005, 13:19
 */
package de.hattrickorganizer.gui.model;

import java.util.Vector;

import javax.swing.JLabel;

import plugins.IMatchResult;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.model.HOVerwaltung;

/**
 * DOCUMENT ME!
 *
 * @author Pirania
 */
public class MatchScoreDiffTableModel extends AbstractMatchTableModel {
	//~ Instance fields ----------------------------------------------------------------------------

	/** TODO Missing Parameter Documentation */
	protected static String[] columnNames =
		{
			HOVerwaltung.instance().getResource().getProperty("Ergebnis"),
			HOVerwaltung.instance().getResource().getProperty("frequency")};

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new MatchPredictionSpieleTableModel object.
	 *
	 * @param vErgebnisse TODO Missing Constructuor Parameter Documentation
	 */
	public MatchScoreDiffTableModel(IMatchResult matchresult,boolean ishome) {
		super(matchresult,ishome);
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * Erzeugt einen Data[][] aus dem Spielervector
	 */
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
				
		String homeWin = HOVerwaltung.instance().getResource().getProperty("Winby");
		String awayWin = HOVerwaltung.instance().getResource().getProperty("Lostby");
		if (!isHomeMatch()) {
			awayWin = HOVerwaltung.instance().getResource().getProperty("Winby");
			homeWin  = HOVerwaltung.instance().getResource().getProperty("Lostby");
			
		}
		for (int i = 8; i > 4; i--) {

			m_clData[8 - i][0] =
				new ColorLabelEntry(
					homeWin +" "+ (i - 4),
					ColorLabelEntry.FG_STANDARD,
					ColorLabelEntry.BG_STANDARD,
					JLabel.LEFT);

			m_clData[8 - i][1] = getProgressBar(result[i] / number);

		}

		m_clData[4][0] =
			new ColorLabelEntry(
				HOVerwaltung.instance().getResource().getProperty("Unendschieden"),
				ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_STANDARD,
				JLabel.LEFT);

		m_clData[4][1] = getProgressBar(result[4] / number);

		for (int i = 3; i >= 0; i--) {

			m_clData[8 - i][0] =
				new ColorLabelEntry(
		awayWin + " " + (4 - i),
					ColorLabelEntry.FG_STANDARD,
					ColorLabelEntry.BG_STANDARD,
					JLabel.LEFT);

			m_clData[8 - i][1] = getProgressBar(result[i] / number);

		}

	}

	public String[] getColumnNames() {
		return columnNames;
	}

}
