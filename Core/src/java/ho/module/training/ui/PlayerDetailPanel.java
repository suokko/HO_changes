// %4263391236:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.constants.player.PlayerAbility;
import ho.core.constants.player.PlayerSkill;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.model.player.FuturePlayer;
import ho.core.model.player.Spieler;
import ho.core.training.FutureTrainingManager;
import ho.core.training.TrainingPerWeek;
import ho.module.training.Skills;
import ho.module.training.ui.comp.ColorBar;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Panel where the future training predictions are shown.
 * 
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class PlayerDetailPanel extends ImagePanel {

	private static final long serialVersionUID = -6606934473344186243L;
	private JLabel playerLabel = new JLabel("", SwingConstants.CENTER);
	private ColorBar[] levelBar = new ColorBar[8];
	private JLabel[] skillLabel = new JLabel[8];

	/**
	 * Creates the panel and its components
	 */
	public PlayerDetailPanel() {
		initComponents();
	}

	/**
	 * Method that populate this panel for the selected player
	 * 
	 * @param spieler
	 *            player
	 */
	public void reload(Spieler spieler) {
		if (spieler == null) {
			playerLabel.setText(HOVerwaltung.instance().getLanguageString("PlayerSelect"));
			for (int i = 0; i < 8; i++) {
				skillLabel[i].setText("");
				levelBar[i].setLevel(0f);
			}
			return;
		}

		// sets player number
		playerLabel.setText(spieler.getName());

		// gets the list of user defined future trainings
		List<TrainingPerWeek> trainings = ho.module.training.TrainingPanel.getTrainPanel()
				.getFutureTrainings();

		StaffPanel sp = ho.module.training.TrainingPanel.getStaffPanel();
		// instantiate a future train manager to calculate the previsions */
		FutureTrainingManager ftm = new FutureTrainingManager(spieler, trainings,
				sp.getCoTrainerNumber(), sp.getTrainerLevelNumber());

		for (int i = 0; i < 8; i++) {
			int skillIndex = Skills.getSkillAtPosition(i);
			skillLabel[i].setText(PlayerAbility.getNameForSkill(
					Skills.getSkillValue(spieler, skillIndex), true));

			FuturePlayer fp = ftm.previewPlayer(UserParameter.instance().futureWeeks);
			double finalValue = getSkillValue(fp, skillIndex);
			levelBar[i].setLevel((float) finalValue / getSkillMaxValue(i));
		}

		updateUI();
	}

	/**
	 * Get maximum value of the skill.
	 * 
	 * @param index
	 * 
	 * @return float Max value
	 */
	private float getSkillMaxValue(int index) {
		if (index == 7) {
			return 9f;
		} else {
			return 20f;
		}
	}

	private double getSkillValue(FuturePlayer spieler, int skillIndex) {
		switch (skillIndex) {
		case PlayerSkill.KEEPER:
			return spieler.getGoalkeeping();

		case PlayerSkill.SCORING:
			return spieler.getAttack();

		case PlayerSkill.DEFENDING:
			return spieler.getDefense();

		case PlayerSkill.PASSING:
			return spieler.getPassing();

		case PlayerSkill.PLAYMAKING:
			return spieler.getPlaymaking();

		case PlayerSkill.SET_PIECES:
			return spieler.getSetpieces();

		case PlayerSkill.STAMINA:
			return spieler.getStamina();

		case PlayerSkill.WINGER:
			return spieler.getCross();
		default:
			return 0;
		}
	}

	/**
	 * Initialize the object layout
	 */
	private void initComponents() {
		setOpaque(false);
		setLayout(new GridBagLayout());

		GridBagConstraints maingbc = new GridBagConstraints();
		maingbc.anchor = GridBagConstraints.NORTH;
		maingbc.insets = new Insets(10, 10, 5, 10);
		add(playerLabel, maingbc);

		JPanel bottom = new JPanel(new GridBagLayout());
		bottom.setOpaque(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 4, 2, 4);
		for (int i = 0; i < 8; i++) {
			gbc.gridy = i;
			gbc.weightx = 0.0;
			gbc.fill = GridBagConstraints.HORIZONTAL;

			int skillIndex = Skills.getSkillAtPosition(i);
			gbc.gridx = 0;
			bottom.add(new JLabel(PlayerSkill.toString(skillIndex)), gbc);

			skillLabel[i] = new JLabel("");
			skillLabel[i].setOpaque(false);
			gbc.gridx = 1;
			bottom.add(skillLabel[i], gbc);

			levelBar[i] = new ColorBar(0f, 200, 16);
			levelBar[i].setOpaque(false);
			levelBar[i].setMinimumSize(new Dimension(200, 16));
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 2;
			gbc.weightx = 1.0;
			bottom.add(levelBar[i], gbc);
		}
		JPanel dummyPanelToConsumeAllExtraSpace = new JPanel();
		dummyPanelToConsumeAllExtraSpace.setOpaque(false);
		gbc.gridy++;
		gbc.weighty = 1.0;
		bottom.add(dummyPanelToConsumeAllExtraSpace, gbc);

		maingbc.gridy = 1;
		maingbc.insets = new Insets(0, 0, 0, 0);
		maingbc.fill = GridBagConstraints.BOTH;
		maingbc.weightx = 1.0;
		maingbc.weighty = 1.0;
		add(bottom, maingbc);
	}
}
