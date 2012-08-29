package ho.module.training;

import ho.core.gui.IRefreshable;
import ho.core.gui.RefreshManager;
import ho.core.model.UserParameter;
import ho.core.model.player.Spieler;
import ho.core.util.HOLogger;
import ho.module.training.ui.MainPanel;
import ho.module.training.ui.PlayerDetailPanel;
import ho.module.training.ui.SkillupPanel;
import ho.module.training.ui.StaffPanel;
import ho.module.training.ui.comp.DividerListener;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class TrainingPanel extends JPanel implements IRefreshable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1313192105835561643L;

	/** The currently selected player */
    private static Spieler activePlayer;

    /** Class that keep track of the past skillup */
    private static OldTrainingManager manager;

    /** Main table panel */
    private static MainPanel tabbedPanel;

    /** Prevision table */
    private static PlayerDetailPanel playerDetailPanel;

    /** Table of past skillups */
    private static SkillupPanel skillupPanel;

    /** Table of old past and future trainings */
    private static ho.module.training.ui.TrainingPanel trainPanel;

    /** Staff panel */
    private static StaffPanel staffPanel;

    
    public TrainingPanel(){
    	initialize();
    }
    
    
    private void initialize() {
        try {
            setLayout(new BorderLayout());
            skillupPanel = new SkillupPanel();
            playerDetailPanel = new PlayerDetailPanel();
            trainPanel = new ho.module.training.ui.TrainingPanel();
            staffPanel = new StaffPanel();
            tabbedPanel = new MainPanel();

            JSplitPane leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, skillupPanel, staffPanel);

            leftPane.setResizeWeight(1);
            leftPane.setDividerLocation(UserParameter.instance().training_lowerLeftSplitPane); //$NON-NLS-1$
            leftPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                               new DividerListener(DividerListener.training_lowerLeftSplitPane)); //$NON-NLS-1$

            JSplitPane bottomPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane,
                                                  new JScrollPane(playerDetailPanel));

            bottomPanel.setDividerLocation(UserParameter.instance().training_bottomSplitPane); //$NON-NLS-1$
            bottomPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                                  new DividerListener(DividerListener.training_bottomSplitPane)); //$NON-NLS-1$

            JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPanel,
                                                   bottomPanel);

            splitPanel.setDividerLocation(UserParameter.instance().training_mainSplitPane); //$NON-NLS-1$
            splitPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                                 new DividerListener(DividerListener.training_mainSplitPane)); //$NON-NLS-1$

            JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPanel,
                                                  trainPanel);

            mainPanel.setDividerLocation(UserParameter.instance().training_rightSplitPane); //$NON-NLS-1$
            mainPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                                new DividerListener(DividerListener.training_rightSplitPane)); //$NON-NLS-1$

            mainPanel.setOpaque(false);
            add(mainPanel, BorderLayout.CENTER);
            
            RefreshManager.instance().registerRefreshable(this);
        } catch (RuntimeException e) {
            HOLogger.instance().log(TrainingPanel.class, e);
        }
		
	}


	/**
     * Returns the SkillupManager
     *
     * @return
     */
    public static OldTrainingManager getSkillupManager() {
        return manager;
    }

    /**
     * Returns the Skillup Panel where the past skillup are shown
     *
     * @return
     */
    public static SkillupPanel getSkillupPanel() {
        return skillupPanel;
    }

    /**
     * Returns the Staff Panel where the staff settings are shown
     *
     * @return
     */
    public static StaffPanel getStaffPanel() {
        return staffPanel;
    }

    /**
     * Returns the Training Panel where the past and future training are shown
     *
     * @return
     */
    public static ho.module.training.ui.TrainingPanel getTrainPanel() {
        return trainPanel;
    }

    /**
     * When called by HO reload everything!
     */
    public void refresh() {
        // reset the selected player
        activePlayer = null;

        // reload the trainingPanel
        trainPanel.reload();

        // reload the staff, could have changed
        staffPanel.reload();

        // recalculate and update the main panel		
        tabbedPanel.reload();

        // and finally recalculate the player previsions
        refreshPlayerDetail();
    }

    /**
     * Refresh all the previsions, this is used when we haven't downloaded anything from HT but
     * the user has changed something in the staff or in the future training
     */
    public static void refreshPlayerDetail() {
        // reload the past skillups		
        skillupPanel.reload(activePlayer);

        // recalculate and show the previsions
        playerDetailPanel.reload(activePlayer);

        //update upper table!
        tabbedPanel.getOutput().reload();
        tabbedPanel.getRecap().reload();
    }

    /**
     * Sets the new active player and recalculate everything
     *
     * @param player the new selected player
     */
    public static void selectPlayer(Spieler player) {
        activePlayer = player;

        // regenerate the past skillup using the manager
        manager = new OldTrainingManager(player);

        // reload the past skillups		
        skillupPanel.reload(activePlayer);

        // recalculate and show the previsions
        playerDetailPanel.reload(activePlayer);
    }
    
    /**
     * Return the main tab panel
     *
     * @return MainPanel
     */
    public static MainPanel getTabbedPanel() {
        return tabbedPanel;
    }
}
