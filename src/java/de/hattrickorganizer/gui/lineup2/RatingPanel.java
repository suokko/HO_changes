package de.hattrickorganizer.gui.lineup2;

import gui.HOColorName;
import ho.core.gui.theme.ThemeManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.hattrickorganizer.tools.Helper;
import de.hattrickorganizer.tools.PlayerHelper;

public class RatingPanel extends JPanel {

	private static final long serialVersionUID = -8083628255407967880L;
	private JLabel ratingTextLabel;
	private JLabel ratingValueLabel;
	private JLabel ratingCompareLabel;
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

	public void setRating(double value) {
		this.ratingTextLabel.setText(getNameForSkill(value));
		this.ratingValueLabel.setText(this.numberFormat.format(value));
		updateRatingCompareLabel(value - this.rating);
		this.rating = value;
	}

	public void setToolTip(String toolTip) {
		this.ratingTextLabel.setToolTipText(toolTip);
		this.ratingValueLabel.setToolTipText(toolTip);
		this.ratingCompareLabel.setToolTipText(toolTip);
	}

	private void initComponents() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());

		JPanel temppanel = new JPanel(new GridLayout(1, 2));
		temppanel.setOpaque(true);
		this.ratingValueLabel = new JLabel();
		this.ratingValueLabel.setBackground(Color.WHITE);
		this.ratingValueLabel.setFont(this.ratingValueLabel.getFont().deriveFont(Font.BOLD));
		this.ratingValueLabel.setOpaque(true);
		this.ratingValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		temppanel.add(this.ratingValueLabel);

		this.ratingCompareLabel = new JLabel();
		this.ratingCompareLabel.setBackground(Color.WHITE);
		this.ratingCompareLabel.setOpaque(true);
		this.ratingCompareLabel.setHorizontalAlignment(SwingConstants.CENTER);
		temppanel.add(this.ratingCompareLabel);

		JPanel innerpanel = new JPanel(new GridLayout(2, 1));
		innerpanel.setBackground(Color.white);
		innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));

		this.ratingTextLabel = new JLabel("", SwingConstants.LEFT);
		Font font = this.ratingTextLabel.getFont();
		this.ratingTextLabel.setFont(font.deriveFont(font.getSize2D() - 1f));
		this.ratingTextLabel.setOpaque(true);
		innerpanel.add(this.ratingTextLabel);
		innerpanel.add(temppanel);
		add(innerpanel, BorderLayout.CENTER);
	}

	private void updateRatingCompareLabel(double diff) {
		Color color = null;
		String sign;
		if (diff < 0) {
			color = ThemeManager.getColor(HOColorName.TABLEENTRY_DECLINE_FG);
			sign = "-";
		} else if (diff > 0) {
			color = ThemeManager.getColor(HOColorName.TABLEENTRY_IMPROVEMENT_FG);
			sign = "+";
		} else {
			color = ThemeManager.getColor(HOColorName.TABLEENTRY_FG);
			sign = "";
		}
		this.ratingCompareLabel.setForeground(color);
		this.ratingCompareLabel.setText(sign + this.numberFormat.format(diff));
	}

	private String getNameForSkill(double skill) {
		return PlayerHelper.getNameForSkill(getIntValue4Rating(skill), false, true);
	}

	// TODO move to static utility class (same method in Lineup is non-static)
	private int getIntValue4Rating(double rating) {
		return (int) (((float) (rating - 1) * 4f) + 1);
	}
}
