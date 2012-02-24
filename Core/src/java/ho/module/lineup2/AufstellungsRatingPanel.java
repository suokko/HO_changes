// %1814492999:de.hattrickorganizer.gui.lineup%
/*
 * AufstellungsRatingPanel.java
 *
 * Created on 23. November 2004, 09:11
 */
package ho.module.lineup2;

import ho.core.model.HOVerwaltung;
import ho.core.util.Helper;
import ho.module.lineup.Lineup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * Zeigt das Rating für eine Aufstellung an
 * 
 * @author Pirania
 */
final class AufstellungsRatingPanel extends JPanel {

	private static final long serialVersionUID = -8938268226990652913L;
	public static final boolean REIHENFOLGE_STURM2VERTEIDIGUNG = false;
	public static final boolean REIHENFOLGE_VERTEIDIGUNG2STURM = true;
	private double bottomCenterValue;
	private double bottomLeftValue;
	private double bottomRightValue;
	private double middleValue;
	private double topCenterValue;
	private double topLeftValue;
	private double topRightValue;
	private Dimension GROESSE = new Dimension(Helper.calcCellWidth(80), Helper.calcCellWidth(25));
	private RatingPanel bottomCenterPanel;
	private RatingPanel bottomLeftPanel;
	private RatingPanel bottomRightPanel;
	private RatingPanel middlePanel;
	private RatingPanel topCenterPanel;
	private RatingPanel topLeftPanel;
	private RatingPanel topRightPanel;
	private NumberFormat numberFormat;
	private boolean reihenfolge = REIHENFOLGE_STURM2VERTEIDIGUNG;
	private final JButton copyButton = new JButton();
	private Lineup lineup;

	/**
	 * Creates a new instance of AufstellungsRatingPanel
	 */
	public AufstellungsRatingPanel(Lineup lineup) {
		this.lineup = lineup;
		initComponents();

		if (gui.UserParameter.instance().anzahlNachkommastellen == 1) {
			numberFormat = Helper.DEFAULTDEZIMALFORMAT;
		} else {
			numberFormat = Helper.DEZIMALFORMAT_2STELLEN;
		}
		refresh();
	}

	/**
	 * Clear all fields.
	 */
	public void clear() {
	}

	public void refresh() {
		setTopRight(this.lineup.getLeftDefenseRating());
		setTopCenter(this.lineup.getCentralDefenseRating());
		setTopLeft(this.lineup.getRightDefenseRating());
		setMiddle(this.lineup.getMidfieldRating());
		setBottomRight(this.lineup.getLeftAttackRating());
		setBottomCenter(this.lineup.getCentralAttackRating());
		setBottomLeft(this.lineup.getRightAttackRating());

		// Farben neu berechnen
		calcColorBorders();
	}

	String getCentralDefenseRating() {
		if (reihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return numberFormat.format(topCenterValue);
		} else {
			return numberFormat.format(bottomCenterValue);
		}
	}

	String getRightDefenseRating() {
		if (reihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return numberFormat.format(topLeftValue);
		} else {
			return numberFormat.format(bottomRightValue);
		}
	}

	String getLeftAttackRating() {
		if (reihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return numberFormat.format(bottomRightValue);
		} else {
			return numberFormat.format(topLeftValue);
		}
	}

	String getCentralAttackRating() {
		if (reihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return numberFormat.format(bottomCenterValue);
		} else {
			return numberFormat.format(topCenterValue);
		}
	}

	String getRightAttackRating() {
		if (reihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return numberFormat.format(bottomLeftValue);
		} else {
			return numberFormat.format(topRightValue);
		}
	}

	private void setBottomCenter(double value) {
		this.bottomCenterPanel.setRating(value);
		this.bottomCenterValue = value;
	}

	private void setBottomLeft(double value) {
		this.bottomLeftPanel.setRating(value);
		this.bottomLeftValue = value;
	}

	private void setBottomRight(double value) {
		this.bottomRightPanel.setRating(value);
		this.bottomRightValue = value;
	}

	private void setMiddle(double value) {
		this.middlePanel.setRating(value);
		this.middleValue = value;
	}

	private void setTopCenter(double value) {
		this.topCenterPanel.setRating(value);
		this.topCenterValue = value;
	}

	private void setTopLeft(double value) {
		this.topLeftPanel.setRating(value);
		this.topLeftValue = value;
	}

	private void setTopRight(double value) {
		this.topRightPanel.setRating(value);
		this.topRightValue = value;
	}

	private void calcColorBorders() {
		final int faktor = 60;
		double temp = 0d;
		Color tempcolor = null;
		final double durchschnitt = (topLeftValue + topCenterValue + topRightValue + middleValue
				+ bottomLeftValue + bottomCenterValue + bottomRightValue) / 7d;

		// Topleft
		temp = topLeftValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		topLeftPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

		// Topcenter
		temp = topCenterValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		topCenterPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

		// TopRight
		temp = topRightValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		topRightPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

		// Middel
		temp = middleValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		middlePanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

		// Bottomleft
		temp = bottomLeftValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		bottomLeftPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

		// BottomCenter
		temp = bottomCenterValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		bottomCenterPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

		// Bottomricht
		temp = bottomRightValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		bottomRightPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));
	}

	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;

		// ========== ROW 1 ==========
		JPanel row1 = new JPanel(new GridBagLayout());		
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(row1, gbc);

		GridBagConstraints row1gbc = new GridBagConstraints();
		Insets rowInsets = new Insets(0, 2, 0, 2);
		row1gbc.insets = rowInsets;
		row1gbc.fill = GridBagConstraints.HORIZONTAL;
		row1gbc.anchor = GridBagConstraints.CENTER;
		// dummy panel to consume 1/4 of the space on the left
		row1gbc.gridx = 0;
		row1gbc.gridy = 0;
		row1gbc.weightx = 0.25;
		row1.add(new JPanel(), row1gbc);
		// visible panel in the middle of the row
		this.topCenterPanel = new RatingPanel();
		this.topCenterPanel.setPreferredSize(GROESSE);
		row1gbc.gridx = 1;
		row1gbc.gridy = 0;
		row1gbc.weightx = 0.5;
		row1.add(this.topCenterPanel, row1gbc);
		// dummy panel to consume 1/4 of the space on the right
		row1gbc.gridx = 2;
		row1gbc.gridy = 0;
		row1gbc.weightx = 0.25;
		row1.add(new JPanel(), row1gbc);

		// ========== ROW 2 ==========
		JPanel row2 = new JPanel(new GridBagLayout());
		gbc.gridy = 1;
		add(row2, gbc);

		GridBagConstraints row2gbc = new GridBagConstraints();
		row2gbc.insets = rowInsets;
		row2gbc.fill = GridBagConstraints.HORIZONTAL;
		row2gbc.anchor = GridBagConstraints.CENTER;
		row2gbc.weightx = 0.5;
		// left panel
		this.topLeftPanel = new RatingPanel();
		this.topLeftPanel.setPreferredSize(GROESSE);
		row2gbc.gridx = 0;
		row2.add(this.topLeftPanel, row2gbc);
		// right panel
		this.topRightPanel = new RatingPanel();
		this.topRightPanel.setPreferredSize(GROESSE);
		row2gbc.gridx = 1;
		row2.add(this.topRightPanel, row2gbc);

		// ========== ROW 3 ==========
		JPanel row3 = new JPanel(new GridBagLayout());
		gbc.gridy = 2;
		add(row3, gbc);

		GridBagConstraints row3gbc = new GridBagConstraints();
		row3gbc.insets = rowInsets;
		row3gbc.fill = GridBagConstraints.HORIZONTAL;
		row3gbc.anchor = GridBagConstraints.CENTER;
		// dummy panel to consume 1/4 of the space on the left
		row3gbc.gridx = 0;
		row3gbc.gridy = 0;
		row3gbc.weightx = 0.25;
		row3.add(new JPanel(), row3gbc);
		// visible panel in the middle of the row
		this.middlePanel = new RatingPanel();
		this.middlePanel.setPreferredSize(GROESSE);
		row3gbc.gridx = 1;
		row3gbc.gridy = 0;
		row3gbc.weightx = 0.5;
		row3.add(this.middlePanel, row3gbc);
		// dummy panel to consume 1/4 of the space on the right
		row3gbc.gridx = 2;
		row3gbc.gridy = 0;
		row3gbc.weightx = 0.25;
		row3.add(new JPanel(), row3gbc);

		// ========== ROW 4 ==========
		JPanel row4 = new JPanel(new GridBagLayout());
		gbc.gridy = 3;
		add(row4, gbc);

		GridBagConstraints row4gbc = new GridBagConstraints();
		row4gbc.insets = rowInsets;
		row4gbc.fill = GridBagConstraints.HORIZONTAL;
		row4gbc.anchor = GridBagConstraints.CENTER;
		row4gbc.weightx = 0.5;
		// left panel
		this.bottomLeftPanel = new RatingPanel();
		this.bottomLeftPanel.setPreferredSize(GROESSE);
		row4gbc.gridx = 0;
		row4.add(this.bottomLeftPanel, row4gbc);
		// right panel
		this.bottomRightPanel = new RatingPanel();
		this.bottomRightPanel.setPreferredSize(GROESSE);
		row4gbc.gridx = 1;
		row4.add(this.bottomRightPanel, row4gbc);

		// ========== ROW 5 ==========
		JPanel row5 = new JPanel(new GridBagLayout());
		gbc.gridy = 4;
		add(row5, gbc);

		GridBagConstraints row5gbc = new GridBagConstraints();
		row5gbc.insets = rowInsets;
		row5gbc.fill = GridBagConstraints.HORIZONTAL;
		row5gbc.anchor = GridBagConstraints.CENTER;
		// dummy panel to consume 1/4 of the space on the left
		row5gbc.gridx = 0;
		row5gbc.gridy = 0;
		row5gbc.weightx = 0.25;
		row5.add(new JPanel(), row5gbc);
		// visible panel in the middle of the row
		this.bottomCenterPanel = new RatingPanel();
		this.bottomCenterPanel.setPreferredSize(GROESSE);
		row5gbc.gridx = 1;
		row5gbc.gridy = 0;
		row5gbc.weightx = 0.5;
		row5.add(this.bottomCenterPanel, row5gbc);
		// dummy panel to consume 1/4 of the space on the right
		row5gbc.gridx = 2;
		row5gbc.gridy = 0;
		row5gbc.weightx = 0.25;
		row5.add(new JPanel(), row5gbc);

		initToolTips();
	}

	/**
	 * Initialize all tool tips.
	 */
	private void initToolTips() {
		if (reihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			this.topLeftPanel.setToolTip(getLanguageString("rechteAbwehrseite"));
			this.topCenterPanel.setToolTip(getLanguageString("Abwehrzentrum"));
			this.topRightPanel.setToolTip(getLanguageString("linkeAbwehrseite"));
			this.middlePanel.setToolTip(getLanguageString("MatchMittelfeld"));
			this.bottomLeftPanel.setToolTip(getLanguageString("rechteAngriffsseite"));
			this.bottomCenterPanel.setToolTip(getLanguageString("Angriffszentrum"));
			this.bottomRightPanel.setToolTip(getLanguageString("linkeAngriffsseite"));
		} else {
			this.topLeftPanel.setToolTip(getLanguageString("linkeAngriffsseite"));
			this.topCenterPanel.setToolTip(getLanguageString("Angriffszentrum"));
			this.topRightPanel.setToolTip(getLanguageString("rechteAngriffsseite"));
			this.middlePanel.setToolTip(getLanguageString("MatchMittelfeld"));
			this.bottomLeftPanel.setToolTip(getLanguageString("linkeAbwehrseite"));
			this.bottomCenterPanel.setToolTip(getLanguageString("Abwehrzentrum"));
			this.bottomRightPanel.setToolTip(getLanguageString("rechteAbwehrseite"));
		}
	}

	private String getLanguageString(String key) {
		return HOVerwaltung.instance().getLanguageString(key);
	}

	String getMidfieldRating() {
		return numberFormat.format(middleValue);
	}

	String getLeftDefenseRating() {
		if (reihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return numberFormat.format(topRightValue);
		} else {
			return numberFormat.format(bottomLeftValue);
		}
	}
}
