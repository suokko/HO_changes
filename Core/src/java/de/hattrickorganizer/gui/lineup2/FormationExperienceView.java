package de.hattrickorganizer.gui.lineup2;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Team;
import de.hattrickorganizer.tools.PlayerHelper;

public class FormationExperienceView extends JPanel {

	private static final long serialVersionUID = -797807278278758927L;
	private ColorLabelEntry entryXP343;
	private ColorLabelEntry entryXP352;
	private ColorLabelEntry entryXP433;
	private ColorLabelEntry entryXP442;
	private ColorLabelEntry entryXP451;
	private ColorLabelEntry entryXP532;
	private ColorLabelEntry entryXP541;
	private ColorLabelEntry entryXP523;
	private ColorLabelEntry entryXP550;
	private ColorLabelEntry entryXP253;

	public FormationExperienceView() {
		initComponents();
		setValues();
	}

	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridy = 0;
		gbc1.anchor = GridBagConstraints.NORTHWEST;
		gbc1.insets = new Insets(2, 6, 2, 3);

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		gbc2.anchor = GridBagConstraints.NORTHWEST;
		gbc2.insets = new Insets(2, 3, 2, 6);

		add(new JLabel(HOVerwaltung.instance().getLanguageString("Lineup.FormationXp550")), gbc1);
		this.entryXP550 = getColorLabelEntry();
		add(this.entryXP550.getComponent(false), gbc2);

		gbc1.gridy++;
		gbc2.gridy++;
		add(new JLabel(HOVerwaltung.instance().getLanguageString("Erfahrung541")), gbc1);
		this.entryXP541 = getColorLabelEntry();
		add(this.entryXP541.getComponent(false), gbc2);

		gbc1.gridy++;
		gbc2.gridy++;
		add(new JLabel(HOVerwaltung.instance().getLanguageString("Erfahrung532")), gbc1);
		this.entryXP532 = getColorLabelEntry();
		add(this.entryXP532.getComponent(false), gbc2);

		gbc1.gridy++;
		gbc2.gridy++;
		add(new JLabel(HOVerwaltung.instance().getLanguageString("Lineup.FormationXp523")), gbc1);
		this.entryXP523 = getColorLabelEntry();
		add(this.entryXP523.getComponent(false), gbc2);

		gbc1.gridy++;
		gbc2.gridy++;
		add(new JLabel(HOVerwaltung.instance().getLanguageString("Erfahrung451")), gbc1);
		this.entryXP451 = getColorLabelEntry();
		add(this.entryXP451.getComponent(false), gbc2);

		gbc1.gridy++;
		gbc2.gridy++;
		add(new JLabel(HOVerwaltung.instance().getLanguageString("Erfahrung442")), gbc1);
		this.entryXP442 = getColorLabelEntry();
		add(this.entryXP442.getComponent(false), gbc2);

		gbc1.gridy++;
		gbc2.gridy++;
		add(new JLabel(HOVerwaltung.instance().getLanguageString("Erfahrung433")), gbc1);
		this.entryXP433 = getColorLabelEntry();
		add(this.entryXP433.getComponent(false), gbc2);

		gbc1.gridy++;
		gbc2.gridy++;
		add(new JLabel(HOVerwaltung.instance().getLanguageString("Erfahrung352")), gbc1);
		this.entryXP352 = getColorLabelEntry();
		add(this.entryXP352.getComponent(false), gbc2);

		gbc1.gridy++;
		gbc2.gridy++;
		add(new JLabel(HOVerwaltung.instance().getLanguageString("Erfahrung343")), gbc1);
		this.entryXP343 = getColorLabelEntry();
		add(this.entryXP343.getComponent(false), gbc2);

		gbc1.gridy++;
		gbc2.gridy++;
		add(new JLabel(HOVerwaltung.instance().getLanguageString("Lineup.FormationXp253")), gbc1);
		this.entryXP253 = getColorLabelEntry();
		add(this.entryXP253.getComponent(false), gbc2);
	}

	private void setValues() {
		Team team = HOVerwaltung.instance().getModel().getTeam();
		this.entryXP550.setText(PlayerHelper.getNameForSkill(team.getFormationExperience550()));
		this.entryXP541.setText(PlayerHelper.getNameForSkill(team.getFormationExperience541()));
		this.entryXP532.setText(PlayerHelper.getNameForSkill(team.getFormationExperience532()));
		this.entryXP523.setText(PlayerHelper.getNameForSkill(team.getFormationExperience523()));
		this.entryXP451.setText(PlayerHelper.getNameForSkill(team.getFormationExperience451()));
		this.entryXP442.setText(PlayerHelper.getNameForSkill(team.getFormationExperience442()));
		this.entryXP433.setText(PlayerHelper.getNameForSkill(team.getFormationExperience433()));
		this.entryXP352.setText(PlayerHelper.getNameForSkill(team.getFormationExperience352()));
		this.entryXP343.setText(PlayerHelper.getNameForSkill(team.getFormationExperience343()));
		this.entryXP253.setText(PlayerHelper.getNameForSkill(team.getFormationExperience253()));

		this.entryXP550.setFGColor(getEntryColor(team.getFormationExperience550()));
		this.entryXP541.setFGColor(getEntryColor(team.getFormationExperience541()));
		this.entryXP532.setFGColor(getEntryColor(team.getFormationExperience532()));
		this.entryXP523.setFGColor(getEntryColor(team.getFormationExperience523()));
		this.entryXP451.setFGColor(getEntryColor(team.getFormationExperience451()));
		this.entryXP442.setFGColor(getEntryColor(team.getFormationExperience442()));
		this.entryXP433.setFGColor(getEntryColor(team.getFormationExperience433()));
		this.entryXP352.setFGColor(getEntryColor(team.getFormationExperience352()));
		this.entryXP343.setFGColor(getEntryColor(team.getFormationExperience343()));
		this.entryXP253.setFGColor(getEntryColor(team.getFormationExperience253()));
	}

	private Color getEntryColor(int exp) {
		return new Color(Math.min(Math.max(((8 - exp) * 32) - 1, 0), 255), 0, 0);
	}

	private ColorLabelEntry getColorLabelEntry() {
		return new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
				ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE, SwingConstants.CENTER);
	}
}
