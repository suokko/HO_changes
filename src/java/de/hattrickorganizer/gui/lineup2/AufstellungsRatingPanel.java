// %1814492999:de.hattrickorganizer.gui.lineup%
/*
 * AufstellungsRatingPanel.java
 *
 * Created on 23. November 2004, 09:11
 */
package de.hattrickorganizer.gui.lineup2;

import gui.HOIconName;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Lineup;
import de.hattrickorganizer.tools.Helper;
import de.hattrickorganizer.tools.PlayerHelper;

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
	private ColorLabelEntry m_clBottomCenterCompare = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.CENTER);
	private ColorLabelEntry m_clBottomCenterMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.RIGHT);
	private ColorLabelEntry m_clBottomLeftCompare = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.CENTER);
	private ColorLabelEntry m_clBottomLeftMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.RIGHT);
	private ColorLabelEntry m_clBottomRightCompare = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.CENTER);
	private ColorLabelEntry m_clBottomRightMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.RIGHT);
	private ColorLabelEntry m_clMiddleCompare = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.CENTER);
	private ColorLabelEntry m_clMiddleMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.RIGHT);
	private ColorLabelEntry m_clTopCenterCompare = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.CENTER);
	private ColorLabelEntry m_clTopCenterMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.RIGHT);
	private ColorLabelEntry m_clTopLeftCompare = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.CENTER);
	private ColorLabelEntry m_clTopLeftMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.RIGHT);
	private ColorLabelEntry m_clTopRightCompare = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.CENTER);
	private ColorLabelEntry m_clTopRightMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
			SwingConstants.RIGHT);
	private Dimension GROESSE = new Dimension(Helper.calcCellWidth(80), Helper.calcCellWidth(25));
	private JLabel bottomCenterLabel = new JLabel("", SwingConstants.LEFT);
	private JLabel bottomLeftLabel = new JLabel("", SwingConstants.LEFT);
	private JLabel bottomRightLabel = new JLabel("", SwingConstants.LEFT);
	private JLabel midFieldLabel = new JLabel("", SwingConstants.LEFT);
	private JLabel topCenterLabel = new JLabel("", SwingConstants.LEFT);
	private JLabel topLeftLabel = new JLabel("", SwingConstants.LEFT);
	private JLabel topRightLabel = new JLabel("", SwingConstants.LEFT);
	private JPanel m_clBottomCenterPanel = new JPanel(new BorderLayout());
	private JPanel m_clBottomLeftPanel = new JPanel(new BorderLayout());
	private JPanel m_clBottomRightPanel = new JPanel(new BorderLayout());
	private JPanel m_clMiddlePanel = new JPanel(new BorderLayout());
	private JPanel m_clTopCenterPanel = new JPanel(new BorderLayout());
	private JPanel m_clTopLeftPanel = new JPanel(new BorderLayout());
	private JPanel m_clTopRightPanel = new JPanel(new BorderLayout());
	private NumberFormat m_clFormat;
	private boolean m_bReihenfolge = REIHENFOLGE_STURM2VERTEIDIGUNG;
	private final JButton copyButton = new JButton();
	private Lineup lineup;

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Creates a new instance of AufstellungsRatingPanel
	 */
	protected AufstellungsRatingPanel(Lineup lineup) {
		this.lineup = lineup;
		initComponents();

		if (gui.UserParameter.instance().anzahlNachkommastellen == 1) {
			m_clFormat = Helper.DEFAULTDEZIMALFORMAT;
		} else {
			m_clFormat = Helper.DEZIMALFORMAT_2STELLEN;
		}

		refresh();
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------

	/**
	 * Clear all fields.
	 */
	public void clear() {
		topLeftLabel.setText("");
		m_clTopLeftMain.clear();
		m_clTopLeftCompare.clear();
		topCenterLabel.setText("");
		m_clTopCenterMain.clear();
		m_clTopCenterCompare.clear();
		topRightLabel.setText("");
		m_clTopRightMain.clear();
		m_clTopRightCompare.clear();
		midFieldLabel.setText("");
		m_clMiddleMain.clear();
		m_clMiddleCompare.clear();
		bottomLeftLabel.setText("");
		m_clBottomLeftMain.clear();
		m_clBottomLeftCompare.clear();
		bottomCenterLabel.setText("");
		m_clBottomCenterMain.clear();
		m_clBottomCenterCompare.clear();
		bottomRightLabel.setText("");
		m_clBottomRightMain.clear();
		m_clBottomRightCompare.clear();
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

	private String getNameForSkill(double skill) {
		return PlayerHelper.getNameForSkill(this.lineup.getIntValue4Rating(skill), false, true);
	}

	private void setBottomCenter(double value) {
		this.bottomCenterLabel.setText(getNameForSkill(value));
		m_clBottomCenterMain.setText(m_clFormat.format(value));
		m_clBottomCenterCompare.setSpezialNumber((float) (value - bottomCenterValue), false);
		bottomCenterValue = value;
	}

	private void setBottomLeft(double value) {
		this.bottomLeftLabel.setText(getNameForSkill(value));
		m_clBottomLeftMain.setText(m_clFormat.format(value));
		m_clBottomLeftCompare.setSpezialNumber((float) (value - bottomLeftValue), false);
		bottomLeftValue = value;
	}

	private void setBottomRight(double value) {
		this.bottomRightLabel.setText(getNameForSkill(value));
		m_clBottomRightMain.setText(m_clFormat.format(value));
		m_clBottomRightCompare.setSpezialNumber((float) (value - bottomRightValue), false);
		bottomRightValue = value;
	}

	private void setMiddle(double value) {
		this.midFieldLabel.setText(getNameForSkill(value));
		m_clMiddleMain.setText(m_clFormat.format(value));
		m_clMiddleCompare.setSpezialNumber((float) (value - middleValue), false);
		middleValue = value;
	}

	private void setTopCenter(double value) {
		this.topCenterLabel.setText(getNameForSkill(value));
		m_clTopCenterMain.setText(m_clFormat.format(value));
		m_clTopCenterCompare.setSpezialNumber((float) (value - topCenterValue), false);
		topCenterValue = value;
	}

	private void setTopLeft(double value) {
		this.topLeftLabel.setText(getNameForSkill(value));
		m_clTopLeftMain.setText(m_clFormat.format(value));
		m_clTopLeftCompare.setSpezialNumber((float) (value - topLeftValue), false);
		topLeftValue = value;
	}

	private void setTopRight(double value) {
		this.topRightLabel.setText(getNameForSkill(value));
		m_clTopRightMain.setText(m_clFormat.format(value));
		m_clTopRightCompare.setSpezialNumber((float) (value - topRightValue), false);
		topRightValue = value;
	}

	protected void calcColorBorders() {
		final int faktor = 60;
		double temp = 0d;
		Color tempcolor = null;
		final double durchschnitt = (topLeftValue + topCenterValue + topRightValue + middleValue + bottomLeftValue + bottomCenterValue + bottomRightValue) / 7d;

		// Topleft
		temp = topLeftValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		m_clTopLeftPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));
		
		// Topcenter
		temp = topCenterValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		m_clTopCenterPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

		// TopRight
		temp = topRightValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		m_clTopRightPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

		// Middel
		temp = middleValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		m_clMiddlePanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

		// Bottomleft
		temp = bottomLeftValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		m_clBottomLeftPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

		// BottomCenter
		temp = bottomCenterValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		m_clBottomCenterPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

		// Bottomricht
		temp = bottomRightValue - durchschnitt;
		if (temp < 0) {
			tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
		} else {
			tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
		}
		m_clBottomRightPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));
	}

	/**
	 * Initialize GUI components.
	 */
	private void initComponents() {
		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.insets = new Insets(1, 1, 1, 1);

//		setBackground(ThemeManager.getColor(HOColorName.PANEL_BG));
		setLayout(layout);

		JComponent tempcomponent;
		JPanel temppanel;
		JPanel mainpanel;
		JPanel innerpanel;
		JPanel subpanel;

		GridBagLayout sublayout = new GridBagLayout();
		GridBagConstraints subconstraints = new GridBagConstraints();
		subconstraints.anchor = GridBagConstraints.CENTER;
		subconstraints.fill = GridBagConstraints.HORIZONTAL;
		subconstraints.weightx = 1.0;
		subconstraints.weighty = 0.0;
		subconstraints.insets = new Insets(1, 1, 1, 1);
		subpanel = new JPanel(sublayout);
		subpanel.setOpaque(false);

		// Platzhalter
		tempcomponent = new JLabel();
		tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D() - 2f));
		tempcomponent.setPreferredSize(new Dimension(Helper.calcCellWidth(10), Helper.calcCellWidth(2)));
		subconstraints.gridx = 1;
		subconstraints.gridy = 1;
		subconstraints.gridwidth = 1;

		sublayout.setConstraints(tempcomponent, subconstraints);
		subpanel.add(tempcomponent);

		// Top Center
		temppanel = new JPanel(new GridLayout(1, 2));
		temppanel.setOpaque(true);
		m_clTopCenterMain.setFontStyle(Font.BOLD);
		tempcomponent = m_clTopCenterMain.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);
		tempcomponent = m_clTopCenterCompare.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);

		innerpanel = new JPanel(new GridLayout(2, 1));
		innerpanel.setBackground(Color.white);
		innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		innerpanel.add(topCenterLabel);
		innerpanel.add(temppanel);

		m_clTopCenterPanel.setBackground(Color.WHITE);
		topCenterLabel
				.setFont(topCenterLabel.getFont().deriveFont(topCenterLabel.getFont().getSize2D() - 1f));
		topCenterLabel.setOpaque(true);
		m_clTopCenterPanel.add(innerpanel, BorderLayout.CENTER);
		m_clTopCenterPanel.setPreferredSize(GROESSE);

		mainpanel = new JPanel(new BorderLayout());
		mainpanel.setBackground(Color.white);
		mainpanel.add(m_clTopCenterPanel, BorderLayout.CENTER);
		mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		subconstraints.gridx = 2;
		subconstraints.gridy = 1;
		subconstraints.gridwidth = 3;
		sublayout.setConstraints(mainpanel, subconstraints);
		subpanel.add(mainpanel);

		// Platzhalter
		tempcomponent = new JLabel();
		tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D() - 2f));
		tempcomponent.setPreferredSize(new Dimension(Helper.calcCellWidth(10), Helper.calcCellWidth(2)));
		subconstraints.gridx = 5;
		subconstraints.gridy = 1;
		subconstraints.gridwidth = 1;

		sublayout.setConstraints(tempcomponent, subconstraints);
		subpanel.add(tempcomponent);

		constraints.gridx = 0;
		constraints.gridy = 0;
		layout.setConstraints(subpanel, constraints);
		add(subpanel);

		// //////////////////////////////////////////////////////////////////////
		sublayout = new GridBagLayout();
		subconstraints = new GridBagConstraints();
		subconstraints.anchor = GridBagConstraints.CENTER;
		subconstraints.fill = GridBagConstraints.HORIZONTAL;
		subconstraints.weightx = 1.0;
		subconstraints.weighty = 0.0;
		subconstraints.insets = new Insets(1, 1, 1, 1);
		subpanel = new JPanel(sublayout);
		subpanel.setOpaque(false);

		// Top Left
		temppanel = new JPanel(new GridLayout(1, 2));
		temppanel.setOpaque(true);
		m_clTopLeftMain.setFontStyle(Font.BOLD);
		tempcomponent = m_clTopLeftMain.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);
		tempcomponent = m_clTopLeftCompare.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);

		innerpanel = new JPanel(new GridLayout(2, 1));
		innerpanel.setBackground(Color.white);
		innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		innerpanel.add(topLeftLabel);
		innerpanel.add(temppanel);

		m_clTopLeftPanel.setBackground(Color.WHITE);
		topLeftLabel.setFont(topLeftLabel.getFont().deriveFont(topLeftLabel.getFont().getSize2D() - 1f));
		topLeftLabel.setOpaque(true);
		m_clTopLeftPanel.add(innerpanel, BorderLayout.CENTER);
		m_clTopLeftPanel.setPreferredSize(GROESSE);

		mainpanel = new JPanel(new BorderLayout());
		mainpanel.setBackground(Color.white);
		mainpanel.add(m_clTopLeftPanel, BorderLayout.CENTER);
		mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		subconstraints.gridx = 1;
		subconstraints.gridy = 2;
		subconstraints.gridwidth = 2;
		sublayout.setConstraints(mainpanel, subconstraints);
		subpanel.add(mainpanel);

		// Platzhalter
		tempcomponent = new JLabel();
		tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D() - 2f));
		tempcomponent.setPreferredSize(new Dimension(Helper.calcCellWidth(10), Helper.calcCellWidth(2)));
		subconstraints.gridx = 3;
		subconstraints.gridy = 2;
		subconstraints.gridwidth = 1;
		subconstraints.weightx = 0.0;

		sublayout.setConstraints(tempcomponent, subconstraints);
		subpanel.add(tempcomponent);

		// Top Right
		temppanel = new JPanel(new GridLayout(1, 2));
		temppanel.setOpaque(true);
		m_clTopRightMain.setFontStyle(Font.BOLD);
		tempcomponent = m_clTopRightMain.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);
		tempcomponent = m_clTopRightCompare.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);

		innerpanel = new JPanel(new GridLayout(2, 1));
		innerpanel.setBackground(Color.white);
		innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		innerpanel.add(topRightLabel);
		innerpanel.add(temppanel);

		m_clTopRightPanel.setBackground(Color.WHITE);
		topRightLabel.setFont(topRightLabel.getFont().deriveFont(topRightLabel.getFont().getSize2D() - 1f));
		topRightLabel.setOpaque(true);
		m_clTopRightPanel.add(innerpanel, BorderLayout.CENTER);
		m_clTopRightPanel.setPreferredSize(GROESSE);

		mainpanel = new JPanel(new BorderLayout());
		mainpanel.setBackground(Color.white);
		mainpanel.add(m_clTopRightPanel, BorderLayout.CENTER);
		mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		subconstraints.gridx = 4;
		subconstraints.gridy = 2;
		subconstraints.gridwidth = 2;
		subconstraints.weightx = 1.0;
		sublayout.setConstraints(mainpanel, subconstraints);
		subpanel.add(mainpanel);

		constraints.gridx = 0;
		constraints.gridy = 1;
		layout.setConstraints(subpanel, constraints);
		add(subpanel);

		// //////////////////////////////////////////////////////////////////////
		// Platzhalter
		tempcomponent = new JLabel();
		tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D() - 2f));
		tempcomponent.setPreferredSize(new Dimension(Helper.calcCellWidth(10), Helper.calcCellWidth(2)));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 0.0;

		layout.setConstraints(tempcomponent, constraints);
		add(tempcomponent);

		// //////////////////////////////////////////////////////////////////////
		sublayout = new GridBagLayout();
		subconstraints = new GridBagConstraints();
		subconstraints.anchor = GridBagConstraints.CENTER;
		subconstraints.fill = GridBagConstraints.HORIZONTAL;
		subconstraints.weightx = 1.0;
		subconstraints.weighty = 0.0;
		subconstraints.insets = new Insets(1, 1, 1, 1);
		subpanel = new JPanel(sublayout);
		subpanel.setOpaque(false);

		// Platzhalter
		tempcomponent = new JLabel();
		tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D() - 2f));
		tempcomponent.setPreferredSize(new Dimension(Helper.calcCellWidth(10), Helper.calcCellWidth(2)));
		subconstraints.gridx = 1;
		subconstraints.gridy = 4;
		subconstraints.gridwidth = 1;

		sublayout.setConstraints(tempcomponent, subconstraints);
		subpanel.add(tempcomponent);

		// Middle
		temppanel = new JPanel(new GridLayout(1, 2));
		temppanel.setOpaque(true);
		m_clMiddleMain.setFontStyle(Font.BOLD);
		tempcomponent = m_clMiddleMain.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);
		tempcomponent = m_clMiddleCompare.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);

		innerpanel = new JPanel(new GridLayout(2, 1));
		innerpanel.setBackground(Color.white);
		innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		innerpanel.add(midFieldLabel);
		innerpanel.add(temppanel);

		m_clMiddlePanel.setBackground(Color.WHITE);
		midFieldLabel.setFont(midFieldLabel.getFont().deriveFont(midFieldLabel.getFont().getSize2D() - 1f));
		midFieldLabel.setOpaque(true);
		m_clMiddlePanel.add(innerpanel, BorderLayout.CENTER);
		m_clMiddlePanel.setPreferredSize(GROESSE);

		mainpanel = new JPanel(new BorderLayout());
		mainpanel.setBackground(Color.white);
		mainpanel.add(m_clMiddlePanel, BorderLayout.CENTER);
		mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		subconstraints.gridx = 2;
		subconstraints.gridy = 4;
		subconstraints.gridwidth = 3;
		sublayout.setConstraints(mainpanel, subconstraints);
		subpanel.add(mainpanel);

		// Platzhalter
		tempcomponent = new JLabel();
		tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D() - 2f));
		tempcomponent.setPreferredSize(new Dimension(Helper.calcCellWidth(10), Helper.calcCellWidth(2)));
		subconstraints.gridx = 5;
		subconstraints.gridy = 4;
		subconstraints.gridwidth = 1;

		sublayout.setConstraints(tempcomponent, subconstraints);
		subpanel.add(tempcomponent);

		constraints.gridx = 0;
		constraints.gridy = 3;
		layout.setConstraints(subpanel, constraints);
		add(subpanel);

		// //////////////////////////////////////////////////////////////////////
		// Platzhalter
		tempcomponent = new JLabel();
		tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D() - 2f));
		tempcomponent.setPreferredSize(new Dimension(Helper.calcCellWidth(10), Helper.calcCellWidth(2)));
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weightx = 0.0;

		layout.setConstraints(tempcomponent, constraints);
		add(tempcomponent);

		// //////////////////////////////////////////////////////////////////////
		sublayout = new GridBagLayout();
		subconstraints = new GridBagConstraints();
		subconstraints.anchor = GridBagConstraints.CENTER;
		subconstraints.fill = GridBagConstraints.HORIZONTAL;
		subconstraints.weightx = 1.0;
		subconstraints.weighty = 0.0;
		subconstraints.insets = new Insets(1, 1, 1, 1);
		subpanel = new JPanel(sublayout);
		subpanel.setOpaque(false);

		// Bottom Left
		temppanel = new JPanel(new GridLayout(1, 2));
		temppanel.setOpaque(true);
		m_clBottomLeftMain.setFontStyle(Font.BOLD);
		tempcomponent = m_clBottomLeftMain.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);
		tempcomponent = m_clBottomLeftCompare.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);

		innerpanel = new JPanel(new GridLayout(2, 1));
		innerpanel.setBackground(Color.white);
		innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		innerpanel.add(bottomLeftLabel);
		innerpanel.add(temppanel);

		m_clBottomLeftPanel.setBackground(Color.WHITE);
		bottomLeftLabel.setFont(bottomLeftLabel.getFont().deriveFont(bottomLeftLabel.getFont().getSize2D() - 1f));
		bottomLeftLabel.setOpaque(true);
		m_clBottomLeftPanel.add(innerpanel, BorderLayout.CENTER);
		m_clBottomLeftPanel.setPreferredSize(GROESSE);

		mainpanel = new JPanel(new BorderLayout());
		mainpanel.setBackground(Color.white);
		mainpanel.add(m_clBottomLeftPanel, BorderLayout.CENTER);
		mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		subconstraints.gridx = 1;
		subconstraints.gridy = 6;
		subconstraints.gridwidth = 2;
		subconstraints.weightx = 1.0;
		sublayout.setConstraints(mainpanel, subconstraints);
		subpanel.add(mainpanel);

		// Platzhalter
		tempcomponent = new JLabel();
		tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D() - 2f));
		tempcomponent.setPreferredSize(new Dimension(Helper.calcCellWidth(10), Helper.calcCellWidth(2)));
		subconstraints.gridx = 3;
		subconstraints.gridy = 6;
		subconstraints.gridwidth = 1;
		subconstraints.weightx = 0.0;

		sublayout.setConstraints(tempcomponent, subconstraints);
		subpanel.add(tempcomponent);

		// Bottom Right
		temppanel = new JPanel(new GridLayout(1, 2));
		temppanel.setOpaque(true);
		m_clBottomRightMain.setFontStyle(Font.BOLD);
		tempcomponent = m_clBottomRightMain.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);
		tempcomponent = m_clBottomRightCompare.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);

		innerpanel = new JPanel(new GridLayout(2, 1));
		innerpanel.setBackground(Color.white);
		innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		innerpanel.add(bottomRightLabel);
		innerpanel.add(temppanel);

		m_clBottomRightPanel.setBackground(Color.WHITE);
		bottomRightLabel.setFont(bottomRightLabel.getFont().deriveFont(bottomRightLabel.getFont().getSize2D() - 1f));
		bottomRightLabel.setOpaque(true);
		m_clBottomRightPanel.add(innerpanel, BorderLayout.CENTER);
		m_clBottomRightPanel.setPreferredSize(GROESSE);

		mainpanel = new JPanel(new BorderLayout());
		mainpanel.setBackground(Color.white);
		mainpanel.add(m_clBottomRightPanel, BorderLayout.CENTER);
		mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		subconstraints.gridx = 4;
		subconstraints.gridy = 6;
		subconstraints.gridwidth = 2;
		subconstraints.weightx = 1.0;
		sublayout.setConstraints(mainpanel, subconstraints);
		subpanel.add(mainpanel);

		constraints.gridx = 0;
		constraints.gridy = 5;
		layout.setConstraints(subpanel, constraints);
		add(subpanel);

		// //////////////////////////////////////////////////////////////////////
		sublayout = new GridBagLayout();
		subconstraints = new GridBagConstraints();
		subconstraints.anchor = GridBagConstraints.CENTER;
		subconstraints.fill = GridBagConstraints.HORIZONTAL;
		subconstraints.weightx = 1.0;
		subconstraints.weighty = 0.0;
		subconstraints.insets = new Insets(1, 1, 1, 1);
		subpanel = new JPanel(sublayout);
		subpanel.setOpaque(false);

		// left bottom spacer
		tempcomponent = new JLabel();
		tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D() - 2f));
		tempcomponent.setPreferredSize(new Dimension(Helper.calcCellWidth(10), 1));
		subconstraints.gridx = 1;
		subconstraints.gridy = 7;
		subconstraints.gridwidth = 1;

		sublayout.setConstraints(tempcomponent, subconstraints);
		subpanel.add(tempcomponent);

		// Bottom Center
		temppanel = new JPanel(new GridLayout(1, 2));
		temppanel.setOpaque(true);
		m_clBottomCenterMain.setFontStyle(Font.BOLD);
		tempcomponent = m_clBottomCenterMain.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);
		tempcomponent = m_clBottomCenterCompare.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);

		innerpanel = new JPanel(new GridLayout(2, 1));
		innerpanel.setBackground(Color.white);
		innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		innerpanel.add(bottomCenterLabel);
		innerpanel.add(temppanel);

		m_clBottomCenterPanel.setBackground(Color.WHITE);
		bottomCenterLabel.setFont(bottomCenterLabel.getFont().deriveFont(bottomCenterLabel.getFont().getSize2D() - 1f));
		bottomCenterLabel.setOpaque(true);
		m_clBottomCenterPanel.add(innerpanel, BorderLayout.CENTER);
		m_clBottomCenterPanel.setPreferredSize(GROESSE);

		mainpanel = new JPanel(new BorderLayout());
		mainpanel.setBackground(Color.white);
		mainpanel.add(m_clBottomCenterPanel, BorderLayout.CENTER);
		mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		subconstraints.gridx = 2;
		subconstraints.gridy = 7;
		subconstraints.gridwidth = 3;
		sublayout.setConstraints(mainpanel, subconstraints);
		subpanel.add(mainpanel);

		subconstraints.gridx = 5;
		subconstraints.gridy = 7;
		subconstraints.gridwidth = 1;

		// --- copy ratings button start
		temppanel = new JPanel(new BorderLayout());
		temppanel.setOpaque(false);
		copyButton.setToolTipText(HOVerwaltung.instance().getLanguageString("Lineup.CopyRatings.ToolTip"));
		copyButton.setIcon(ThemeManager.getIcon(HOIconName.INFO));
		copyButton.addActionListener(new CopyListener(this));
		copyButton.setPreferredSize(new Dimension(18, 18));
		copyButton.setMaximumSize(new Dimension(18, 18));
		temppanel.add(copyButton, BorderLayout.EAST);
		// --- copy ratings button end

		// sublayout.setConstraints(tempcomponent, subconstraints);
		// subpanel.add(tempcomponent);
		sublayout.setConstraints(temppanel, subconstraints);
		subpanel.add(temppanel);

		constraints.gridx = 0;
		constraints.gridy = 6;
		layout.setConstraints(subpanel, constraints);
		add(subpanel);

		// //////////////////////////////////////////////////////////////////////
		initToolTips();

		// Alle zahlen auf 0, Default ist -oo
		m_clTopLeftCompare.setSpezialNumber(0f, false);
		m_clTopCenterCompare.setSpezialNumber(0f, false);
		m_clTopRightCompare.setSpezialNumber(0f, false);
		m_clMiddleCompare.setSpezialNumber(0f, false);
		m_clBottomLeftCompare.setSpezialNumber(0f, false);
		m_clBottomCenterCompare.setSpezialNumber(0f, false);
		m_clBottomRightCompare.setSpezialNumber(0f, false);
	}

	/**
	 * Initialize all tool tips.
	 */
	private void initToolTips() {
		if (m_bReihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			topLeftLabel.setToolTipText(getLanguageString("rechteAbwehrseite"));
			m_clTopLeftMain.setToolTipText(getLanguageString("rechteAbwehrseite"));
			m_clTopLeftCompare.setToolTipText(getLanguageString("rechteAbwehrseite"));
			topCenterLabel.setToolTipText(getLanguageString("Abwehrzentrum"));
			m_clTopCenterMain.setToolTipText(getLanguageString("Abwehrzentrum"));
			m_clTopCenterCompare.setToolTipText(getLanguageString("Abwehrzentrum"));
			topRightLabel.setToolTipText(getLanguageString("linkeAbwehrseite"));
			m_clTopRightMain.setToolTipText(getLanguageString("linkeAbwehrseite"));
			m_clTopRightCompare.setToolTipText(getLanguageString("linkeAbwehrseite"));
			midFieldLabel.setToolTipText(getLanguageString("MatchMittelfeld"));
			m_clMiddleMain.setToolTipText(getLanguageString("MatchMittelfeld"));
			m_clMiddleCompare.setToolTipText(getLanguageString("MatchMittelfeld"));
			bottomLeftLabel.setToolTipText(getLanguageString("rechteAngriffsseite"));
			m_clBottomLeftMain.setToolTipText(getLanguageString("rechteAngriffsseite"));
			m_clBottomLeftCompare.setToolTipText(getLanguageString("rechteAngriffsseite"));
			bottomCenterLabel.setToolTipText(getLanguageString("Angriffszentrum"));
			m_clBottomCenterMain.setToolTipText(getLanguageString("Angriffszentrum"));
			m_clBottomCenterCompare.setToolTipText(getLanguageString("Angriffszentrum"));
			bottomRightLabel.setToolTipText(getLanguageString("linkeAngriffsseite"));
			m_clBottomRightMain.setToolTipText(getLanguageString("linkeAngriffsseite"));
			m_clBottomRightCompare.setToolTipText(getLanguageString("linkeAngriffsseite"));
		} else {
			topLeftLabel.setToolTipText(getLanguageString("linkeAngriffsseite"));
			m_clTopLeftMain.setToolTipText(getLanguageString("linkeAngriffsseite"));
			m_clTopLeftCompare.setToolTipText(getLanguageString("linkeAngriffsseite"));
			topCenterLabel.setToolTipText(getLanguageString("Angriffszentrum"));
			m_clTopCenterMain.setToolTipText(getLanguageString("Angriffszentrum"));
			m_clTopCenterCompare.setToolTipText(getLanguageString("Angriffszentrum"));
			topRightLabel.setToolTipText(getLanguageString("rechteAngriffsseite"));
			m_clTopRightMain.setToolTipText(getLanguageString("rechteAngriffsseite"));
			m_clTopRightCompare.setToolTipText(getLanguageString("rechteAngriffsseite"));
			midFieldLabel.setToolTipText(getLanguageString("MatchMittelfeld"));
			m_clMiddleMain.setToolTipText(getLanguageString("MatchMittelfeld"));
			m_clMiddleCompare.setToolTipText(getLanguageString("MatchMittelfeld"));
			bottomLeftLabel.setToolTipText(getLanguageString("linkeAbwehrseite"));
			m_clBottomLeftMain.setToolTipText(getLanguageString("linkeAbwehrseite"));
			m_clBottomLeftCompare.setToolTipText(getLanguageString("linkeAbwehrseite"));
			bottomCenterLabel.setToolTipText(getLanguageString("Abwehrzentrum"));
			m_clBottomCenterMain.setToolTipText(getLanguageString("Abwehrzentrum"));
			m_clBottomCenterCompare.setToolTipText(getLanguageString("Abwehrzentrum"));
			bottomRightLabel.setToolTipText(getLanguageString("rechteAbwehrseite"));
			m_clBottomRightMain.setToolTipText(getLanguageString("rechteAbwehrseite"));
			m_clBottomRightCompare.setToolTipText(getLanguageString("rechteAbwehrseite"));
		}
	}

	private String getLanguageString(String key) {
		return HOVerwaltung.instance().getLanguageString(key);
	}

	String getMidfieldRating() {
		return m_clFormat.format(middleValue);
	}

	String getLeftDefenseRating() {
		if (m_bReihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return m_clFormat.format(topRightValue);
		} else {
			return m_clFormat.format(bottomLeftValue);
		}
	}

	String getCentralDefenseRating() {
		if (m_bReihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return m_clFormat.format(topCenterValue);
		} else {
			return m_clFormat.format(bottomCenterValue);
		}
	}

	String getRightDefenseRating() {
		if (m_bReihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return m_clFormat.format(topLeftValue);
		} else {
			return m_clFormat.format(bottomRightValue);
		}
	}

	String getLeftAttackRating() {
		if (m_bReihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return m_clFormat.format(bottomRightValue);
		} else {
			return m_clFormat.format(topLeftValue);
		}
	}

	String getCentralAttackRating() {
		if (m_bReihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return m_clFormat.format(bottomCenterValue);
		} else {
			return m_clFormat.format(topCenterValue);
		}
	}

	String getRightAttackRating() {
		if (m_bReihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
			return m_clFormat.format(bottomLeftValue);
		} else {
			return m_clFormat.format(topRightValue);
		}
	}

}
