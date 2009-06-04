/*
 * Created on 30.11.2004
 */
package hoplugins.seriesstats;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.lang.String;

import plugins.IDebugWindow;
import plugins.IHOMiniModel;

/**
 * @author Stefan Cyris
 */
public class MiniPanel {
	private JLabel Rating;
	private JLabel Midfield;
	private JLabel AttackLeft;
	private JLabel AttackRight;
	private JLabel AttackCenter;
	private JLabel DefenseLeft;
	private JLabel DefenseRight;
	private JLabel DefenseCenter;

	private IHOMiniModel hOMiniModel;
	private IDebugWindow IDB;
	private boolean doDebug = false;

	public MiniPanel(IHOMiniModel myhOMiniModel) {

		this(myhOMiniModel, null);
	}

	public MiniPanel(IHOMiniModel myhOMiniModel, IDebugWindow myIDB) {

		hOMiniModel = myhOMiniModel;
		
		if (myIDB != null) {
			this.doDebug = true;
			this.IDB = myIDB;
		}
	}
	
	public JPanel getPanel() {
		JPanel d3 = new JPanel();

		d3.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		d3.setBackground(Color.WHITE);

		GridBagLayout gridbaglayout1 = new GridBagLayout();
		GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
		gridbagconstraints1.weightx = 0.001;
		gridbagconstraints1.weighty = 0.001;
		gridbagconstraints1.insets = new Insets(2, 5, 2, 5);
		d3.setLayout(gridbaglayout1);

		JLabel jlabel = new JLabel(hOMiniModel.getLanguageString(
				"Bewertung")
				+ ":");
		jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
		gridbagconstraints1.gridx = 0;
		gridbagconstraints1.gridy = 1;
		gridbagconstraints1.anchor = GridBagConstraints.LINE_START;
		d3.add(jlabel, gridbagconstraints1);

		Rating = new JLabel("");
		gridbagconstraints1.gridx = 1;
		gridbagconstraints1.gridy = 1;
		d3.add(Rating, gridbagconstraints1);

		jlabel = new JLabel(hOMiniModel.getLanguageString(
				"MatchMittelfeld")
				+ ":");
		jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
		gridbagconstraints1.gridx = 0;
		gridbagconstraints1.gridy = 2;
		gridbagconstraints1.anchor = GridBagConstraints.LINE_START;
		d3.add(jlabel, gridbagconstraints1);

		Midfield = new JLabel("");
		gridbagconstraints1.gridx = 1;
		gridbagconstraints1.gridy = 2;
		d3.add(Midfield, gridbagconstraints1);

		jlabel = new JLabel(hOMiniModel.getLanguageString(
				"rechteAbwehrseite")
				+ ":");
		jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
		gridbagconstraints1.gridx = 2;
		gridbagconstraints1.gridy = 1;
		gridbagconstraints1.anchor = GridBagConstraints.LINE_START;
		d3.add(jlabel, gridbagconstraints1);

		DefenseRight = new JLabel("");
		gridbagconstraints1.gridx = 3;
		gridbagconstraints1.gridy = 1;
		d3.add(DefenseRight, gridbagconstraints1);

		jlabel = new JLabel(hOMiniModel.getLanguageString(
				"Abwehrzentrum")
				+ ":");
		jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
		gridbagconstraints1.gridx = 2;
		gridbagconstraints1.gridy = 2;
		gridbagconstraints1.anchor = GridBagConstraints.LINE_START;
		d3.add(jlabel, gridbagconstraints1);

		DefenseCenter = new JLabel("");
		gridbagconstraints1.gridx = 3;
		gridbagconstraints1.gridy = 2;
		d3.add(DefenseCenter, gridbagconstraints1);

		jlabel = new JLabel(hOMiniModel.getLanguageString(
				"linkeAbwehrseite")
				+ ":");
		jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
		gridbagconstraints1.gridx = 2;
		gridbagconstraints1.gridy = 3;
		gridbagconstraints1.anchor = GridBagConstraints.LINE_START;
		d3.add(jlabel, gridbagconstraints1);

		DefenseLeft = new JLabel("");
		gridbagconstraints1.gridx = 3;
		gridbagconstraints1.gridy = 3;
		d3.add(DefenseLeft, gridbagconstraints1);

		jlabel = new JLabel(hOMiniModel.getLanguageString(
				"rechteAngriffsseite")
				+ ":");
		jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
		gridbagconstraints1.gridx = 4;
		gridbagconstraints1.gridy = 1;
		gridbagconstraints1.anchor = GridBagConstraints.LINE_START;
		d3.add(jlabel, gridbagconstraints1);

		AttackRight = new JLabel("");
		gridbagconstraints1.gridx = 5;
		gridbagconstraints1.gridy = 1;
		d3.add(AttackRight, gridbagconstraints1);

		jlabel = new JLabel(hOMiniModel.getLanguageString(
				"Angriffszentrum")
				+ ":");
		jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
		gridbagconstraints1.gridx = 4;
		gridbagconstraints1.gridy = 2;
		gridbagconstraints1.anchor = GridBagConstraints.LINE_START;
		d3.add(jlabel, gridbagconstraints1);

		AttackCenter = new JLabel("");
		gridbagconstraints1.gridx = 5;
		gridbagconstraints1.gridy = 2;
		d3.add(AttackCenter, gridbagconstraints1);

		jlabel = new JLabel(hOMiniModel.getLanguageString(
				"linkeAngriffsseite")
				+ ":");
		jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD));
		gridbagconstraints1.gridx = 4;
		gridbagconstraints1.gridy = 3;
		gridbagconstraints1.anchor = GridBagConstraints.LINE_START;
		d3.add(jlabel, gridbagconstraints1);

		AttackLeft = new JLabel("");
		gridbagconstraints1.gridx = 5;
		gridbagconstraints1.gridy = 3;
		d3.add(AttackLeft, gridbagconstraints1);

		return d3;

	}

	/**
	 * @param value The value generating label string for.
	 */
	private String generateText(double value) {
		String text = "";
		
		try {
			text = hOMiniModel.getHelper().getNameForBewertung(
				(int) hOMiniModel.getHelper().round(value, 1), true, true);
		}
	        catch(Exception e) {
	        	if (doDebug) {
	        		IDB.append("---ooo---");
	        		IDB.append(e);
	        	}
	        }
		
		return text;
	}

	/**
	 * @param value The value to set.
	 */
	public void setAttackCenter(double value) {
		AttackCenter.setText(this.generateText(value));
	}

	/**
	 * @param value The value to set.
	 */
	public void setAttackLeft(double value) {
		AttackLeft.setText(this.generateText(value));
	}

	/**
	 * @param value The value to set.
	 */
	public void setAttackRight(double value) {
		AttackRight.setText(this.generateText(value));
	}

	/**
	 * @param value The value to set.
	 */
	public void setDefenseCenter(double value) {
		DefenseCenter.setText(this.generateText(value));
	}

	/**
	 * @param value The value to set.
	 */
	public void setDefenseLeft(double value) {
		DefenseLeft.setText(this.generateText(value));
	}

	/**
	 * @param value The value to set.
	 */
	public void setDefenseRight(double value) {
		DefenseRight.setText(this.generateText(value));
	}

	/**
	 * @param value The value to set.
	 */
	public void setMidfield(double value) {
		Midfield.setText(this.generateText(value));
	}

	/**
	 * @param value The value to set.
	 */
	public void setRating(double value) {
		Rating.setText(this.generateText(value));
	}
}