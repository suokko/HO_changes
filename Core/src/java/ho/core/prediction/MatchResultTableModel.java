// %1590051216:de.hattrickorganizer.gui.model%
/*
 * MatchPredictionSpieleTableModel.java
 *
 * Created on 4. Januar 2005, 13:19
 */
package ho.core.prediction;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ImageUtilities;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.prediction.engine.MatchResult;

import java.awt.Color;

import javax.swing.SwingConstants;

/**
 * DOCUMENT ME!
 *
 * @author Pirania
 */
public class MatchResultTableModel extends AbstractMatchTableModel {
	//~ Instance fields ----------------------------------------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = 5568369952809628251L;
	/** TODO Missing Parameter Documentation */
	public final static String[] columnNames = {
		HOVerwaltung.instance().getLanguageString("Ergebnis"), 
		HOVerwaltung.instance().getLanguageString("frequency"),
		"" };


	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new MatchPredictionSpieleTableModel object.
	 *
	 * @param vErgebnisse TODO Missing Constructuor Parameter Documentation
	 */
	public MatchResultTableModel(MatchResult matchresults,boolean isHome) {
		super(matchresults,isHome);
	}

	//~ Methods ------------------------------------------------------------------------------------


	/**
	 * Erzeugt einen Data[][] aus dem Spielervector
	 */
	@Override
	protected void initData() {
		m_clData = new Object[25][getColumnNames().length];
		double number = matchResult.getMatchNumber();

		if (number == 0.0) {
			number = 1.0;
		}

		for (int home = 0; home < 5; home++) {
			for (int away = 0; away < 5; away++) {
				final int res = matchResult.getResultDetail()[(home * 5) + away];

				// result
				m_clData[(home * 5) + away][0] = new ColorLabelEntry("" + home + " - " + away, ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);

				//Ergebnis
				m_clData[(home * 5) + away][1] = getProgressBar(res / number * 1.0d);				

				m_clData[(home * 5) + away][2] = new ColorLabelEntry(1, "", ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);

				if (home > away) {
					((ColorLabelEntry) m_clData[(home * 5) + away][2]).setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR, Color.WHITE));
				} else if (home < away) {
					((ColorLabelEntry) m_clData[(home * 5) + away][2]).setIcon(ImageUtilities.NOIMAGEICON);
				} else {
					((ColorLabelEntry) m_clData[(home * 5) + away][2]).setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR_GRAY, Color.WHITE));
				}
			}
		}
	}

	@Override
	public String[] getColumnNames() {
		return columnNames;
	}


}
