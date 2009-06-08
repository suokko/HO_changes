// %3954697342:hoplugins%
package hoplugins;

import hoplugins.commons.ui.DebugWindow;
import hoplugins.commons.utils.PluginProperty;

import hoplugins.evilCard.ui.FilterPanel;
import hoplugins.evilCard.ui.MainPanel;

import plugins.IOfficialPlugin;

import java.awt.BorderLayout;

import java.io.File;

import javax.swing.JPanel;
import javax.swing.JSplitPane;


/**
 * DOCUMENT ME!
 *
 * @author zaza
 */
public class EvilCard implements plugins.IPlugin, plugins.IRefreshable, IOfficialPlugin {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    private static final String PLUGIN_NAME = "Evil Card";

    /** TODO Missing Parameter Documentation */
    private static final double PLUGIN_VERSION = 0.954d;

    /** TODO Missing Parameter Documentation */
    private static final String PLUGIN_PACKAGE = "evilCard";

    //~ Instance fields ----------------------------------------------------------------------------

    private FilterPanel filterPanel = null;
    private MainPanel mainPanel = null;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * return pluginName
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getName() {
        return getPluginName() + " " + getVersion(); //$NON-NLS-1$
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getPluginID() {
        return 21; // this is your pluginID
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getPluginName() {
        return PLUGIN_NAME;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public File[] getUnquenchableFiles() {
        return new File[0]; // for undeleteable File, but your plugin does not

        //have an option file or so.
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getVersion() {
        return PLUGIN_VERSION;
    }

    /**
     * what to do if ho informs there're new data available
     */
    public void refresh() {
    }

    /**
     * Is called by HO! to start the plugin
     *
     * @param hoMiniModel TODO Missing Constructuor Parameter Documentation
     */
    public void start(plugins.IHOMiniModel hoMiniModel) {
        try {
            PluginProperty.loadPluginProperties(PLUGIN_PACKAGE);

            // Panels
            mainPanel = new MainPanel();
            filterPanel = new FilterPanel(mainPanel);

            // splitPane
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filterPanel,
                                                  mainPanel);
            splitPane.setDividerLocation(180);
            splitPane.setOpaque(false);

            // pluginPanel
            JPanel pluginPanel = hoMiniModel.getGUI().createGrassPanel();
            pluginPanel.setLayout(new BorderLayout());
            pluginPanel.add(splitPane);

            // Add a the plugin panel to HO.
            Commons.getModel().getGUI().addTab(getPluginName(), pluginPanel);

            // We'd like to get informed by changes from HO.
            Commons.getModel().getGUI().registerRefreshable(this);
        } catch (Exception e) {
            DebugWindow.debug(e);
        }
    }
}
