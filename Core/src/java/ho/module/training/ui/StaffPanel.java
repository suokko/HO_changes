// %1476100437:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.constants.player.PlayerAbility;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;

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
 * Panel where the user can specify a different number of assistant and coach level for testing
 * effects
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class StaffPanel extends JPanel {

	private static final long serialVersionUID = 562783276216709022L;

	/** Number of Assistant ComboBox */
    private JComboBox coTrainerCombo;
    /** The combo boxes */
    private JComboBox trainerLevelCombo;
    /** the current number of coTrainer */
    private int coTrainerNumber;
    /** the current level of the coach */
    private int trainerLevelNumber;

    /**
     * Creates a new StaffPanel object.
     */
    public StaffPanel() {
        super();
        jbInit();
    }

    /**
     * Returns the number of Assistant Trainer
     *
     * @return
     */
    public int getCoTrainerNumber() {
        return coTrainerNumber;
    }

    /**
     * Returns the ability of your trainer
     *
     * @return
     */
    public int getTrainerLevelNumber() {
        return trainerLevelNumber;
    }

    /**
     * Populate the staff with HO actual value, called after each Ho refresh command!
     */
    public void reload() {
        setCoTrainer(HOVerwaltung.instance().getModel().getVerein().getCoTrainer());
        setTrainer(HOVerwaltung.instance().getModel().getTrainer().getTrainer() - 1);
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

        // initiates the co trainer combo box with the actual number of co trainer
        coTrainerCombo = new JComboBox();
        for (int i = 0; i < 11; i++) {
            coTrainerCombo.addItem(new Integer(i));
        }

        try {
            coTrainerNumber = hoV.getModel().getVerein().getCoTrainer();
        } catch (RuntimeException e3) {
        	HOLogger.instance().log(StaffPanel.class, e3);
            coTrainerNumber = 0;
        }

        setCoTrainer(coTrainerNumber);
        coTrainerCombo.addItemListener(new ItemListener() {
                @Override
				public void itemStateChanged(ItemEvent e) {
                    // Sets new number of cotrainer
                    Integer n = (Integer) coTrainerCombo.getSelectedItem();

                    coTrainerNumber = n.intValue();
                    // refresh player detail and prevision with the new staff settings
                    ho.module.training.TrainingPanel.refreshPlayerDetail();
                }
            });

        // initiates the coach combo box with the actual level of coach
        trainerLevelCombo = new JComboBox();
        for (int i = 1; i < 9; i++) {
            trainerLevelCombo.addItem(PlayerAbility.getNameForSkill(i, false));
        }

        try {
            trainerLevelNumber = hoV.getModel().getTrainer().getTrainer();
        } catch (RuntimeException e1) {
        	HOLogger.instance().log(StaffPanel.class, e1);
            trainerLevelNumber = 4;
        }

        setTrainer(trainerLevelNumber - 1);
        trainerLevelCombo.addItemListener(new ItemListener() {
                @Override
				public void itemStateChanged(ItemEvent e) {
                    // sets the new coach ability, recalculate data and  refresh
                    trainerLevelNumber = trainerLevelCombo.getSelectedIndex() + 1;
                    ho.module.training.TrainingPanel.refreshPlayerDetail();
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
