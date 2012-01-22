package de.hattrickorganizer.gui.lineup2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.tools.Helper;
import de.hattrickorganizer.tools.PlayerHelper;

public class RatingPanel extends JPanel {

	private static final long serialVersionUID = -8083628255407967880L;
	private JLabel topLeftLabel = new JLabel("", SwingConstants.LEFT);
	private ColorLabelEntry topLeftMain;
	private ColorLabelEntry topLeftCompare;
	private NumberFormat numberFormat;
	private double rating;

	public RatingPanel() {
		if (gui.UserParameter.instance().anzahlNachkommastellen == 1) {
			numberFormat = Helper.DEFAULTDEZIMALFORMAT;
		} else {
			numberFormat = Helper.DEZIMALFORMAT_2STELLEN;
		}
		initComponents();
	}

	private void initComponents() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());

		JPanel temppanel = new JPanel(new GridLayout(1, 2));
		temppanel.setOpaque(true);
		this.topLeftMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE, SwingConstants.RIGHT);
		this.topLeftMain.setFontStyle(Font.BOLD);
		JComponent tempcomponent = this.topLeftMain.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);
		this.topLeftCompare = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
				SwingConstants.CENTER);
		tempcomponent = this.topLeftCompare.getComponent(false);
		tempcomponent.setOpaque(true);
		temppanel.add(tempcomponent);

		JPanel innerpanel = new JPanel(new GridLayout(2, 1));
		innerpanel.setBackground(Color.white);
		innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
		innerpanel.add(this.topLeftLabel);
		innerpanel.add(temppanel);
		add(innerpanel, BorderLayout.CENTER);

		this.topLeftLabel.setFont(this.topLeftLabel.getFont().deriveFont(
				this.topLeftLabel.getFont().getSize2D() - 1f));
		this.topLeftLabel.setOpaque(true);
		this.topLeftCompare.setSpezialNumber(0f, false);
	}

	public void setRating(double value) {
		this.topLeftLabel.setText(getNameForSkill(value));
		this.topLeftMain.setText(this.numberFormat.format(value));
		this.topLeftCompare.setSpezialNumber((float) (value - this.rating), false);
		this.rating = value;
	}

	public void setToolTip(String toolTip) {
		this.topLeftLabel.setToolTipText(toolTip);
		this.topLeftMain.setToolTipText(toolTip);
		this.topLeftCompare.setToolTipText(toolTip);
	}

	private String getNameForSkill(double skill) {
		return PlayerHelper.getNameForSkill(getIntValue4Rating(skill), false, true);
	}

	// TODO move to static utility class (same method in Lineup is non-static)
	private int getIntValue4Rating(double rating) {
		return (int) (((float) (rating - 1) * 4f) + 1);
	}
}
