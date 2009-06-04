// %4263391236:hoplugins.trainingExperience.ui%
package hoplugins.trainingExperience.ui;

import hoplugins.Commons;
import hoplugins.TrainingExperience;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.trainingExperience.constants.Skills;
import hoplugins.trainingExperience.ui.bar.ColorBar;

import plugins.IFuturePlayer;
import plugins.IFutureTrainingManager;
import plugins.IHOMiniModel;
import plugins.ISkillup;
import plugins.ISpieler;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Panel where are shown future trainings predictions
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class PlayerDetailPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private JLabel playerLabel = new JLabel("", JLabel.CENTER); //$NON-NLS-1$
    private ColorBar[] levelBar = new ColorBar[8];
    private JLabel[] skillLabel = new JLabel[8];

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates the panel and its components
     */
    public PlayerDetailPanel() {
        super();
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Method that populate this panel for the selected player
     *
     * @param spieler player
     */
    public void reload(ISpieler spieler) {
        if (spieler == null) {
            playerLabel.setText(PluginProperty.getString("PlayerSelect")); //$NON-NLS-1$

            for (int i = 0; i < 8; i++) {
                skillLabel[i].setText(""); //$NON-NLS-1$
                levelBar[i].setLevel(0f);
            }

            return;
        }

        // sets player number
        playerLabel.setText(spieler.getName());

        // gets the list of user defined future trainings
        List trainings = TrainingExperience.getTrainPanel().getFutureTrainings();

        // instantiate a future train manager to calculate the previsions */ 
        IFutureTrainingManager ftm = Commons.getModel().getFutureTrainingManager(spieler,
                                                                                 trainings,
                                                                                 TrainingExperience.getStaffPanel()
                                                                                                   .getCoTrainerNumber(),
                                                                                 TrainingExperience.getStaffPanel()
                                                                                                   .getKeeperTrainerNumber(),
                                                                                 TrainingExperience.getStaffPanel()
                                                                                                   .getTrainerLevelNumber());

        // Add future skillups
        for (Iterator iter = ftm.getFutureSkillups().iterator(); iter.hasNext();) {
            ISkillup element = (ISkillup) iter.next();

            TrainingExperience.getSkillupPanel().addRow(element);
        }

        for (int i = 0; i < 8; i++) {
            int skillIndex = Skills.getSkillAtPosition(i);
            skillLabel[i].setText(Commons.getModel().getHelper().getNameForSkill(Skills
                                                                                 .getSkillValue(spieler,
                                                                                                skillIndex),
                                                                                 true));

            IFuturePlayer fp = ftm.previewPlayer(IFutureTrainingManager.FUTUREWEEKS);
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

    /**
     * TODO Missing Method Documentation
     *
     * @param spieler TODO Missing Method Parameter Documentation
     * @param skillIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private double getSkillValue(IFuturePlayer spieler, int skillIndex) {
        switch (skillIndex) {
            case ISpieler.SKILL_TORWART:
                return spieler.getGoalkeeping();

            case ISpieler.SKILL_TORSCHUSS:
                return spieler.getAttack();

            case ISpieler.SKILL_VERTEIDIGUNG:
                return spieler.getDefense();

            case ISpieler.SKILL_PASSSPIEL:
                return spieler.getPassing();

            case ISpieler.SKILL_SPIELAUFBAU:
                return spieler.getPlaymaking();

            case ISpieler.SKILL_STANDARDS:
                return spieler.getSetpieces();

            case ISpieler.SKILL_KONDITION:
                return spieler.getStamina();

            case ISpieler.SKILL_FLUEGEL:
                return spieler.getCross();
        }

        return 0;
    }

    /**
     * Initialize the object layout
     */
    private void jbInit() {
        IHOMiniModel model = Commons.getModel();
        JPanel mainPanel = model.getGUI().createImagePanel();

        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout());
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel namePanel = model.getGUI().createImagePanel();

        namePanel.setLayout(new BorderLayout());
        playerLabel.setText(PluginProperty.getString("PlayerSelect")); //$NON-NLS-1$
        namePanel.add(playerLabel, BorderLayout.CENTER);
        mainPanel.add(namePanel, BorderLayout.NORTH);

        JPanel bottom = new JPanel(new GridLayout(8, 2));

        bottom.setOpaque(false);

        //        bottom.add(new JLabel(model.getLanguageString("Training"), JLabel.CENTER)); //$NON-NLS-1$+
        //        bottom.add(new JLabel(model.getLanguageString("Aktuell"), JLabel.CENTER), //$NON-NLS-1$
        //                   BorderLayout.WEST); //$NON-NLS-1$+
        //        bottom.add(new JLabel(PluginProperty.getString("Skill"), JLabel.CENTER)); //$NON-NLS-1$
        for (int i = 0; i < 8; i++) {
            int skillIndex = Skills.getSkillAtPosition(i);
            JPanel left = new JPanel(new GridLayout(1, 2));
            left.setOpaque(false);
            left.add(new JLabel(Skills.getSkillDescription(skillIndex)));

            skillLabel[i] = new JLabel(""); //$NON-NLS-1$
            skillLabel[i].setOpaque(false);
            left.add(skillLabel[i]);
            bottom.add(left);
            levelBar[i] = new ColorBar(0f, 200, 20);
            levelBar[i].setOpaque(false);
            bottom.add(levelBar[i]);
        }

        mainPanel.add(bottom, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }
}
