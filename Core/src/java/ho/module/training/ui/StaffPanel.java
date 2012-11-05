// %1476100437:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.constants.player.PlayerAbility;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;
import ho.module.training.ui.model.TrainingModel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Panel where the user can specify a different number of assistant and coach
 * level for testing effects
 * 
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class StaffPanel extends JPanel {

	private static final long serialVersionUID = 562783276216709022L;

	/** Number of Assistant ComboBox */
	private JComboBox coTrainerCombo;
	/** The combo boxes */
	private JComboBox trainerLevelCombo;
	private final TrainingModel model;

	/**
	 * Creates a new StaffPanel object.
	 */
	public StaffPanel(TrainingModel model) {
		this.model = model;
		jbInit();
	}

	private void setCoTrainer(int value) {
		if (value > 10) {
			value = 10;
		}

		try {
			coTrainerCombo.setSelectedIndex(value);
		} catch (Exception e) {
			HOLogger.instance().log(StaffPanel.class, e);
		}
	}

	private void setTrainer(int value) {
		if (value > 10) {
			value = 10;
		}

		try {
			trainerLevelCombo.setSelectedIndex(value);
		} catch (Exception e) {
			HOLogger.instance().log(StaffPanel.class, e);
		}
	}

	/**
	 * Initialize the object layout
	 */
	private void jbInit() {
		HOVerwaltung hoV = HOVerwaltung.instance();
		JPanel main = new ImagePanel();
		main.setLayout(new GridBagLayout());

		// initiates the co trainer combo box with the actual number of co
		// trainer
		coTrainerCombo = new JComboBox();
		for (int i = 0; i < 11; i++) {
			coTrainerCombo.addItem(new Integer(i));
		}

		setCoTrainer(this.model.getNumberOfCoTrainers());
		coTrainerCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Integer n = (Integer) coTrainerCombo.getSelectedItem();
				model.setNumberOfCoTrainers(n.intValue());
			}
		});

		// initiates the coach combo box with the actual level of coach
		trainerLevelCombo = new JComboBox();
		for (int i = 1; i < 9; i++) {
			trainerLevelCombo.addItem(PlayerAbility.getNameForSkill(i, false));
		}

		setTrainer(this.model.getTrainerLevel() - 1);
		trainerLevelCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				model.setTrainerLevel(trainerLevelCombo.getSelectedIndex() + 1);
			}
		});

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(6, 6, 4, 2);
		main.add(new JLabel(hoV.getLanguageString("Trainerlevel")), gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(6, 2, 2, 6);
		main.add(trainerLevelCombo, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(2, 6, 6, 2);
		main.add(new JLabel(hoV.getLanguageString("ls.club.staff.assistantcoaches")), gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(2, 2, 6, 6);
		main.add(coTrainerCombo, gbc);
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		// dummy component to consume all extra space
		main.add(new JPanel(), gbc);
		setOpaque(false);
		setLayout(new BorderLayout());
		add(new JScrollPane(main), BorderLayout.CENTER);
	}
}
