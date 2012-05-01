// %4263391236:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.constants.player.PlayerAbility;
import ho.core.constants.player.PlayerSkill;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.model.player.FuturePlayer;
import ho.core.model.player.ISkillup;
import ho.core.model.player.Spieler;
import ho.core.training.FutureTrainingManager;
import ho.module.training.FutureTrainingWeek;
import ho.module.training.Skills;
import ho.module.training.ui.comp.ColorBar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * Panel where are shown future trainings predictions
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class PlayerDetailPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -6606934473344186243L;
	private JLabel playerLabel = new JLabel("", SwingConstants.CENTER); //$NON-NLS-1$
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
    public void reload(Spieler spieler) {
        if (spieler == null) {
            playerLabel.setText(HOVerwaltung.instance().getLanguageString("PlayerSelect")); //$NON-NLS-1$

            for (int i = 0; i < 8; i++) {
                skillLabel[i].setText(""); //$NON-NLS-1$
                levelBar[i].setLevel(0f);
            }

            return;
        }

        // sets player number
        playerLabel.setText(spieler.getName());

        // gets the list of user defined future trainings
        List<FutureTrainingWeek> trainings = ho.module.training.TrainingPanel.getTrainPanel().getFutureTrainings();

        // instantiate a future train manager to calculate the previsions */ 
        FutureTrainingManager ftm = new FutureTrainingManager(spieler, trainings,
                                                                                 ho.module.training.TrainingPanel.getStaffPanel()
                                                                                                   .getCoTrainerNumber(),
                                                                                                   ho.module.training.TrainingPanel.getStaffPanel()
                                                                                                   .getTrainerLevelNumber());

        // Add future skillups
        for (Iterator<ISkillup> iter = ftm.getFutureSkillups().iterator(); iter.hasNext();) {
            ISkillup element = iter.next();

            ho.module.training.TrainingPanel.getSkillupPanel().addRow(element);
        }

        for (int i = 0; i < 8; i++) {
            int skillIndex = Skills.getSkillAtPosition(i);
            skillLabel[i].setText(PlayerAbility.getNameForSkill(Skills.getSkillValue(spieler,skillIndex),
                                                                                 true));

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

    /**
     * TODO Missing Method Documentation
     *
     * @param spieler TODO Missing Method Parameter Documentation
     * @param skillIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
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
        }

        return 0;
    }

    /**
     * Initialize the object layout
     */
    private void jbInit() {
        JPanel mainPanel = new ImagePanel();

        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout());
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel namePanel = new ImagePanel();
        namePanel.setLayout(new BorderLayout());
        playerLabel.setText(HOVerwaltung.instance().getLanguageString("PlayerSelect")); //$NON-NLS-1$
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
            left.add(new JLabel(PlayerSkill.toString(skillIndex)));

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
