// %1476100437:hoplugins.trainingExperience.ui%
package hoplugins.trainingExperience.ui;

import hoplugins.Commons;
import hoplugins.TrainingExperience;

import plugins.IHOMiniModel;

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

    /** Number of Assistant ComboBox */
    private JComboBox coTrainer;

    /** Number of Keeper Assistant ComboBox */
    private JComboBox keeperTrainer;

    /** The combo boxes */
    private JComboBox trainerLevel;

    /** the current number of coTrainer */
    private int coTrainerNumber;

    /** the current number of keeper Trainer */
    private int keeperTrainerNumber;

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
     * Returns the number of keeper Trainer
     *
     * @return
     */
    public int getKeeperTrainerNumber() {
        return keeperTrainerNumber;
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
        setKeeper(Commons.getModel().getVerein().getTorwartTrainer());
        setCoTrainer(Commons.getModel().getVerein().getCoTrainer());
        setTrainer(Commons.getModel().getTrainer().getTrainer() - 1);
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
    private void setKeeper(int value) {
        if (value > 10) {
            value = 10;
        }

        try {
            keeperTrainer.setSelectedIndex(value);
        } catch (Exception e) {
        }
    }

    /**
     * Populate the Keeper Trainer combobox
     */
    private void setKeeperAssistantComboBox() {
        keeperTrainer = new JComboBox();

        for (int i = 0; i < 11; i++) {
            keeperTrainer.addItem(new Integer(i));
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
        IHOMiniModel model = Commons.getModel();

        trainerLevel = new JComboBox();

        for (int i = 1; i < 9; i++) {
            trainerLevel.addItem(model.getHelper().getNameForSkill(i, false));
        }
    }

    /**
     * Initialize the object layout
     */
    private void jbInit() {
        IHOMiniModel model = Commons.getModel();
        JPanel main = model.getGUI().createImagePanel();

        main.setLayout(new GridLayout(3, 2));

        // initiates the co trainer combo box with the actual number of co trainer
        setAssistantComboBox();

        try {
            coTrainerNumber = model.getVerein().getCoTrainer();
        } catch (RuntimeException e3) {
            coTrainerNumber = 0;
        }

        setCoTrainer(coTrainerNumber);
        coTrainer.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    // Sets new number of cotrainer
                    Integer n = (Integer) coTrainer.getSelectedItem();

                    coTrainerNumber = n.intValue();

                    // sets number of keeper trainer i exceded 10
                    if ((keeperTrainerNumber + coTrainerNumber) > 10) {
                        keeperTrainerNumber = 10 - coTrainerNumber;
                        keeperTrainer.setSelectedIndex(keeperTrainerNumber);
                    }

                    // refresh player detail and prevision with the new staff settings
                    TrainingExperience.refreshPlayerDetail();
                }
            });

        // initiates the coach combo box with the actual level of coach
        setTrainerComboBox();

        try {
            trainerLevelNumber = model.getTrainer().getTrainer();
        } catch (RuntimeException e1) {
            trainerLevelNumber = 4;
        }

        setTrainer(trainerLevelNumber - 1);
        trainerLevel.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    // sets the new coach ability, recalculate data and  refresh
                    trainerLevelNumber = trainerLevel.getSelectedIndex() + 1;
                    TrainingExperience.refreshPlayerDetail();
                }
            });

        // initiates the keeper trainer combo box with the actual number of keeper trainer
        setKeeperAssistantComboBox();

        try {
            keeperTrainerNumber = model.getVerein().getTorwartTrainer();
        } catch (RuntimeException e2) {
            keeperTrainerNumber = 0;
        }

        setKeeper(keeperTrainerNumber);
        keeperTrainer.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    // Sets new number of keeper trainer
                    Integer n = (Integer) keeperTrainer.getSelectedItem();

                    keeperTrainerNumber = n.intValue();

                    // sets number of co trainer if exceded 10
                    if ((coTrainerNumber + keeperTrainerNumber) > 10) {
                        coTrainerNumber = 10 - keeperTrainerNumber;
                        coTrainer.setSelectedIndex(coTrainerNumber);
                    }

                    // refresh player detail and prevision with the new staff settings				
                    TrainingExperience.refreshPlayerDetail();
                }
            });

        main.add(new JLabel(model.getLanguageString("Trainerlevel"))); //$NON-NLS-1$
        main.add(trainerLevel);
        main.add(new JLabel(model.getLanguageString("CoTrainer"))); //$NON-NLS-1$
        main.add(coTrainer);
        main.add(new JLabel(model.getLanguageString("Torwarttrainer"))); //$NON-NLS-1$
        main.add(keeperTrainer);
        setOpaque(false);
        setLayout(new BorderLayout());
        add(new JScrollPane(main), BorderLayout.CENTER);
    }
}
