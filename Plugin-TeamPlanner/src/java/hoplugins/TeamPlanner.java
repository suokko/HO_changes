// %4124929208:hoplugins%
package hoplugins;

import hoplugins.commons.ui.DebugWindow;
import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamplanner.ui.ExtrasPane;
import hoplugins.teamplanner.ui.FansPane;
import hoplugins.teamplanner.ui.FutureSettingsPane;
import hoplugins.teamplanner.ui.GraphPane;
import hoplugins.teamplanner.ui.PlayersPane;
import hoplugins.teamplanner.ui.RecapPane;
import hoplugins.teamplanner.ui.StadiumPane;
import hoplugins.teamplanner.vo.HTWeek;

import plugins.IHOMiniModel;
import plugins.IOfficialPlugin;
import plugins.IPlugin;
import plugins.IRefreshable;

import java.awt.BorderLayout;

import java.io.File;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;


/**
 * Main class for Training Plugin
 */
public class TeamPlanner implements IPlugin, IRefreshable, IOfficialPlugin {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Missing Parameter Documentation */
    public static HTWeek ACTUALWEEK;

    /** Missing Parameter Documentation */
    private static ExtrasPane extrasPane = null;

    /** Missing Parameter Documentation */
    private static GraphPane graphPane = null;

    /** Missing Parameter Documentation */
    private static PlayersPane playersPane = null;

    /** Missing Parameter Documentation */
    private static RecapPane recapPane = null;

    /** Missing Parameter Documentation */
    private static StadiumPane stadiumPane = null;

    /** Missing Parameter Documentation */
    private static FutureSettingsPane futurePane = null;

    /** Missing Parameter Documentation */
    private static FansPane fansPane = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TeamPlanner object.
     */
    public TeamPlanner() {
        reload();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the plugin full name, with version
     *
     * @return plugin fullname
     */
    public final String getName() {
        return getPluginName() + " " + getVersion(); //$NON-NLS-1$
    }

    /**
     * Returns the plugin id
     *
     * @return plugin id
     */
    public final int getPluginID() {
        return 30;
    }

    /**
     * Returns name of the plugin without version.
     *
     * @return plugin name
     */
    public final String getPluginName() {
        return "Team Planner"; //$NON-NLS-1$
    }

    /**
     * Get the list of files the PluginUpdater is not allowed to delete.
     *
     * @return Array of Files
     */
    public final File[] getUnquenchableFiles() {
        return null;
    }

    /**
     * Returns the plugin version
     *
     * @return the plugin version
     */
    public final double getVersion() {
        return 0.1;
    }

    /**
     * When called by HO reload everything!
     */
    public void refresh() {
        // TODO Reload
        reload();
    }

    /**
     * {@inheritDoc}
     */
    public final void start(IHOMiniModel hoMiniModel) {
        try {
            JTabbedPane tabPane = new JTabbedPane();
            futurePane = new FutureSettingsPane();
            fansPane = new FansPane();
            extrasPane = new ExtrasPane();
            graphPane = new GraphPane();
            playersPane = new PlayersPane();
            recapPane = new RecapPane();
            stadiumPane = new StadiumPane();
            initializeTables();

            JSplitPane topTopSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, stadiumPane,
                                                    playersPane);
            topTopSplit.setResizeWeight(0.5D);

            JSplitPane topSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topTopSplit, extrasPane);
            topSplit.setResizeWeight(0.67D);

            JSplitPane futureSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, futurePane, fansPane);
            topSplit.setResizeWeight(0.50D);

            tabPane.add(PluginProperty.getString("tab.Details"), topSplit);
            tabPane.add(PluginProperty.getString("tab.Future"), futureSplit);
            tabPane.add(PluginProperty.getString("tab.Graphs"), graphPane);

            JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabPane, recapPane);
            split.setResizeWeight(0.75D);

            JPanel pluginPanel = hoMiniModel.getGUI().createImagePanel();
            pluginPanel.setLayout(new BorderLayout());
            pluginPanel.add(split, BorderLayout.CENTER);

            hoMiniModel.getGUI().addTab(getPluginName(), pluginPanel);
            hoMiniModel.getGUI().registerRefreshable(this);
        } catch (Exception e) {
            e.printStackTrace();
            DebugWindow.debug(e);
        }
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static ExtrasPane getExtrasPane() {
        return extrasPane;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static FansPane getFansPane() {
        return fansPane;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static FutureSettingsPane getFuturePane() {
        return futurePane;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static PlayersPane getPlayersPane() {
        return playersPane;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static RecapPane getRecapPane() {
        return recapPane;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static StadiumPane getStadiumPane() {
        return stadiumPane;
    }

    /**
     * Missing Method Documentation
     */
    private void initializeTables() {
        fansPane.initialize();
        stadiumPane.initialize();
        playersPane.initialize();
        extrasPane.initialize();
        recapPane.initialize();
    }

    /**
     * Missing Method Documentation
     */
    private static void reload() {
        int actualSeason = Commons.getModel().getBasics().getSeason();
        int actualWeek = Commons.getModel().getBasics().getSpieltag();

        // We are in the middle where season has not been updated!
        try {
            if (Commons.getModel().getXtraDaten().getTrainingDate().after(Commons.getModel()
                                                                                 .getXtraDaten()
                                                                                 .getSeriesMatchDate())) {
                actualWeek++;

                if (actualWeek == 17) {
                    actualWeek = 1;
                    actualSeason++;
                }
            }
        } catch (Exception e1) {
            // Null when first time HO is launched		
        }

        ACTUALWEEK = new HTWeek(actualSeason, actualWeek);
        ACTUALWEEK.addWeek(-1);
    }
}
