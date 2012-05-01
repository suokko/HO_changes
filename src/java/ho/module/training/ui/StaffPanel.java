// %1476100437:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.constants.player.PlayerAbility;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 562783276216709022L;

	/** Number of Assistant ComboBox */
    private JComboBox coTrainer;


    /** The combo boxes */
    private JComboBox trainerLevel;

    /** the current number of coTrainer */
    private int coTrainerNumber;

    /** the current level of the coach */
    private int trainerLevelNumber;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StaffPanel object.
     */
    public StaffPanel() {
        super();
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

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

    /**
     * Populate the Assistant Trainer combobox
     */
    private void setAssistantComboBox() {
        coTrainer = new JComboBox();

        for (int i = 0; i < 11; i++) {
            coTrainer.addItem(new Integer(i));
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    private void setCoTrainer(int value) {
        if (value > 10) {
            value = 10;
        }

        try {
            coTrainer.setSelectedIndex(value);
        } catch (Exception e) {
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    private void setTrainer(int value) {
        if (value > 10) {
            value = 10;
        }

        try {
            trainerLevel.setSelectedIndex(value);
        } catch (Exception e) {
        }
    }

    /**
     * Populate the Trainer Level combobox
     */
    private void setTrainerComboBox() {
        trainerLevel = new JComboBox();

        for (int i = 1; i < 9; i++) {
            trainerLevel.addItem(PlayerAbility.getNameForSkill(i, false));
        }
    }

    /**
     * Initialize the object layout
     */
    private void jbInit() {
        HOVerwaltung hoV = HOVerwaltung.instance();
        JPanel main = new ImagePanel();

        main.setLayout(new GridLayout(3, 2));

        // initiates the co trainer combo box with the actual number of co trainer
        setAssistantComboBox();

        try {
            coTrainerNumber = hoV.getModel().getVerein().getCoTrainer();
        } catch (RuntimeException e3) {
            coTrainerNumber = 0;
        }

        setCoTrainer(coTrainerNumber);
        coTrainer.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    // Sets new number of cotrainer
                    Integer n = (Integer) coTrainer.getSelectedItem();

                    coTrainerNumber = n.intValue();
                    // refresh player detail and prevision with the new staff settings
                    ho.module.training.TrainingPanel.refreshPlayerDetail();
                }
            });

        // initiates the coach combo box with the actual level of coach
        setTrainerComboBox();

        try {
            trainerLevelNumber = hoV.getModel().getTrainer().getTrainer();
        } catch (RuntimeException e1) {
            trainerLevelNumber = 4;
        }

        setTrainer(trainerLevelNumber - 1);
        trainerLevel.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    // sets the new coach ability, recalculate data and  refresh
                    trainerLevelNumber = trainerLevel.getSelectedIndex() + 1;
                    ho.module.training.TrainingPanel.refreshPlayerDetail();
                }
            });

        main.add(new JLabel(hoV.getLanguageString("Trainerlevel"))); //$NON-NLS-1$
        main.add(trainerLevel);
        main.add(new JLabel(hoV.getLanguageString("CoTrainer"))); //$NON-NLS-1$
        main.add(coTrainer);
        setOpaque(false);
        setLayout(new BorderLayout());
        add(new JScrollPane(main), BorderLayout.CENTER);
    }
}
