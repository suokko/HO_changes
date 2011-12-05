// %1818257735:hoplugins%
package hoplugins;

import hoplugins.commons.ui.DebugWindow;

import hoplugins.trainingExperience.OldTrainingManager;
import hoplugins.trainingExperience.dao.DividerDAO;
import hoplugins.trainingExperience.ui.MainPanel;
import hoplugins.trainingExperience.ui.PlayerDetailPanel;
import hoplugins.trainingExperience.ui.SkillupPanel;
import hoplugins.trainingExperience.ui.StaffPanel;
import hoplugins.trainingExperience.ui.TrainingPanel;
import hoplugins.trainingExperience.ui.component.DividerListener;

import plugins.IHOMiniModel;
import plugins.IOfficialPlugin;
import plugins.IPlugin;
import plugins.IRefreshable;
import plugins.ISpieler;

import java.awt.BorderLayout;

import java.io.File;

import javax.swing.JPanel;
import javax.swing.JSplitPane;


/**
 * Main class for Training Plugin
 */
public class TrainingExperience implements IPlugin, IRefreshable, IOfficialPlugin {
    //~ Static fields/initializers -----------------------------------------------------------------

	/** Plugin ID */
	private static int PLUGINID = 23;
	
	/** Plugin Version */
	private static double PLUGINVERSION = 1.255d;
	
	/** Plugin Name */
	private static String PLUGINNAME = "Training Experience";
	
    /** The currently selected player */
    private static ISpieler activePlayer;

    /** Class that keep track of the past skillup */
    private static OldTrainingManager manager;

    /** Main table panel */
    private static MainPanel tabbedPanel;

    /** Prevision table */
    private static PlayerDetailPanel playerDetailPanel;

    /** Table of past skillups */
    private static SkillupPanel skillupPanel;

    /** Table of old past and future trainings */
    private static TrainingPanel trainPanel;

    /** Staff panel */
    private static StaffPanel staffPanel;

    /** Plugin Panel */
    private static JPanel pluginPanel;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the Plugin name
     *
     * @return plugin name
     */
    public String getPluginName() {
        return PLUGINNAME; 
    }

    /**
     * Returns the main Plugin Panel
     *
     * @return
     */
    public static JPanel getPluginPanel() {
        return pluginPanel;
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
    public static TrainingPanel getTrainPanel() {
        return trainPanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return null
     */
    public File[] getUnquenchableFiles() {
        return null;
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
    public static void selectPlayer(ISpieler player) {
        activePlayer = player;

        // regenerate the past skillup using the manager
        manager = new OldTrainingManager(player);

        // reload the past skillups		
        skillupPanel.reload(activePlayer);

        // recalculate and show the previsions
        playerDetailPanel.reload(activePlayer);
    }

    /**
     * Returns the plugin full name, with version
     *
     * @return plugin fullname
     */
    public String getName() {
        return getPluginName() + " " + getVersion(); //$NON-NLS-1$
    }

    /**
     * Returns the plugin id
     *
     * @return plugin id
     */
    public int getPluginID() {
        return PLUGINID;
    }

    /**
     * Return the main tab panel
     *
     * @return MainPanel
     */
    public static MainPanel getTabbedPanel() {
        return tabbedPanel;
    }

    /**
     * Returns the plugin version
     *
     * @return the plugin version
     */
    public double getVersion() {
        return PLUGINVERSION;
    }

    /*
     * (non-Javadoc)
     * @see plugins.IPlugin#start(plugins.IHOMiniModel)
     * Instantiate the panels and organize them in the gui!
     */
    public void start(IHOMiniModel hOMiniModel) {
        try {
            pluginPanel = Commons.getModel().getGUI().createImagePanel();
            pluginPanel.setLayout(new BorderLayout());
            skillupPanel = new SkillupPanel();
            playerDetailPanel = new PlayerDetailPanel();
            trainPanel = new TrainingPanel();
            staffPanel = new StaffPanel();
            tabbedPanel = new MainPanel();

            JSplitPane leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, skillupPanel, staffPanel);

            leftPane.setResizeWeight(1);
            leftPane.setDividerLocation(DividerDAO.getDividerPosition("LowerLeftDivider")); //$NON-NLS-1$
            leftPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                               new DividerListener("LowerLeftDivider")); //$NON-NLS-1$

            JSplitPane bottomPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane,
                                                    playerDetailPanel);

            bottomPanel.setDividerLocation(DividerDAO.getDividerPosition("BottomDivider")); //$NON-NLS-1$
            bottomPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                                  new DividerListener("BottomDivider")); //$NON-NLS-1$

            JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPanel,
                                                   bottomPanel);

            splitPanel.setDividerLocation(DividerDAO.getDividerPosition("MainDivider")); //$NON-NLS-1$
            splitPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                                 new DividerListener("MainDivider")); //$NON-NLS-1$

            JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPanel,
                                                  trainPanel);

            mainPanel.setDividerLocation(DividerDAO.getDividerPosition("RightDivider")); //$NON-NLS-1$
            mainPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                                new DividerListener("RightDivider")); //$NON-NLS-1$

            mainPanel.setOpaque(false);
            pluginPanel.add(mainPanel, BorderLayout.CENTER);
            Commons.getModel().getGUI().addTab(getPluginName(), pluginPanel);
            Commons.getModel().getGUI().registerRefreshable(this);
        } catch (RuntimeException e) {
            e.printStackTrace();
            DebugWindow.debug(e);
        }
    }
}
