// %2987956632:hoplugins%
package hoplugins;

import hoplugins.commons.ui.DebugWindow;
import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.dao.DividerDAO;
import hoplugins.teamAnalyzer.ui.FilterPanel;
import hoplugins.teamAnalyzer.ui.MainPanel;
import hoplugins.teamAnalyzer.ui.RatingPanel;
import hoplugins.teamAnalyzer.ui.RecapPanel;
import hoplugins.teamAnalyzer.ui.component.FavouriteMenu;
import hoplugins.teamAnalyzer.ui.component.SettingPanel;
import hoplugins.teamAnalyzer.ui.controller.AboutItemListener;
import hoplugins.teamAnalyzer.ui.controller.DividerListener;
import hoplugins.teamAnalyzer.ui.controller.DownloadItemListener;
import hoplugins.teamAnalyzer.ui.controller.HelpItemListener;
import hoplugins.teamAnalyzer.ui.controller.ImportItemListener;
import hoplugins.teamAnalyzer.ui.controller.SimButtonListener;

import plugins.IHOMiniModel;
import plugins.IOfficialPlugin;
import plugins.IPlugin;
import plugins.IRefreshable;

import java.awt.BorderLayout;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;


/**
 * TeamAnalyzer Plugin main Class
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class TeamAnalyzer implements IPlugin, IRefreshable, IOfficialPlugin {
    //~ Instance fields ----------------------------------------------------------------------------

    /** PLugin String name */
    private final String PLUGIN_NAME = "TeamAnalyzer";

    /** Plugin version constant */
    private final double PLUGIN_VERSION = 2.70;

    /** Plugin Id */
    private final int PLUGIN_ID = 14;

    /** Favourite Team Menu Reference */
    private FavouriteMenu submenu;
    private FilterPanel filterPanel;
    private JButton simButton = new JButton();
    private JPanel pluginPanel;
    private MainPanel mainPanel;
    private RatingPanel ratingPanel;
    private RecapPanel recapPanel;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the Filter Panel
     *
     * @return
     */
    public FilterPanel getFilterPanel() {
        return filterPanel;
    }

    /**
     * Returns the Team panel
     *
     * @return
     */
    public MainPanel getMainPanel() {
        return mainPanel;
    }

    /* (non-Javadoc)
     * @see plugins.IPlugin#getName()
     */
    public String getName() {
        return getPluginName() + " " + getVersion();
    }

    /**
     * Returns the pluginId
     *
     * @return
     */
    public int getPluginID() {
        return PLUGIN_ID;
    }

    /* (non-Javadoc)
     * @see plugins.IOfficialPlugin#getPluginName()
     */
    public String getPluginName() {
        return PLUGIN_NAME;
    }

    /**
     * Returns the main plugin panel
     *
     * @return
     */
    public JPanel getPluginPanel() {
        return pluginPanel;
    }

    /**
     * Returns the rating panel
     *
     * @return
     */
    public RatingPanel getRatingPanel() {
        return ratingPanel;
    }

    /**
     * Returns the recap panel
     *
     * @return
     */
    public RecapPanel getRecapPanel() {
        return recapPanel;
    }

    /**
     * Returns the Simulate Button reference
     *
     * @return
     */
    public JButton getSimButton() {
        return simButton;
    }

    /* (non-Javadoc)
     * @see plugins.IOfficialPlugin#getUnquenchableFiles()
     */
    public File[] getUnquenchableFiles() {
        return null;
    }

    /**
     * Returns the plugin version
     *
     * @return
     */
    public double getVersion() {
        return PLUGIN_VERSION;
    }

    /**
     * MEthod that refresh the data after an event
     */
    public void refresh() {
        try {
            SystemManager.refreshData();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see plugins.IPlugin#start(plugins.IHOMiniModel)
     */
    public void start(IHOMiniModel arg0) {
        try {
            SystemManager.initialize(this);
            jbInit();
            simButton.addActionListener(new SimButtonListener(mainPanel.getMyTeamLineupPanel(),
            		mainPanel.getOpponentTeamLineupPanel(), recapPanel));
            SystemManager.refreshData();
        } catch (RuntimeException e) {
            DebugWindow.debug(e);
        }
    }

    /**
     * Initialize the GUI
     */
    private void jbInit() {
        filterPanel = new FilterPanel();
        recapPanel = new RecapPanel();
        mainPanel = new MainPanel();
        pluginPanel = new JPanel();
        ratingPanel = new RatingPanel();
        pluginPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = Commons.getModel().getGUI().createImagePanel();

        buttonPanel.setLayout(new BorderLayout());
        simButton.setText(PluginProperty.getString("Simulate"));
        buttonPanel.add(simButton, BorderLayout.CENTER);

        JSplitPane panel = new JSplitPane(0, ratingPanel, buttonPanel);

        panel.setDividerSize(1);
        panel.setResizeWeight(1);
        panel.setDividerLocation(DividerDAO.getDividerPosition("LowerLeftDivider"));
        panel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                        new DividerListener("LowerLeftDivider"));

        //JScrollPane teamScrollPanel = new JScrollPane(mainPanel);
        JSplitPane leftPanel = new JSplitPane(0, filterPanel, panel);

        leftPanel.setDividerLocation(DividerDAO.getDividerPosition("UpperLeftDivider"));
        leftPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                            new DividerListener("UpperLeftDivider"));

        JSplitPane mainPanel2 = new JSplitPane(1, leftPanel, mainPanel);

        mainPanel2.setDividerLocation(DividerDAO.getDividerPosition("MainDivider"));
        mainPanel2.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                             new DividerListener("MainDivider"));

        JSplitPane m_splitPane = new JSplitPane(0, mainPanel2, recapPanel);

        m_splitPane.setDividerLocation(DividerDAO.getDividerPosition("BottomDivider"));
        m_splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                              new DividerListener("BottomDivider"));
        pluginPanel.add(m_splitPane, BorderLayout.CENTER);

        JMenu menu = new JMenu(getPluginName());
        JMenuItem item2 = new javax.swing.JMenuItem(PluginProperty.getString("Menu.About"));
        JMenuItem item3 = new javax.swing.JMenuItem(PluginProperty.getString("Menu.DownloadMatch"));
        JMenuItem item4 = new javax.swing.JMenuItem(PluginProperty.getString("Help"));
        JMenuItem item5 = new javax.swing.JMenuItem("Import All Teams");

        item2.addActionListener(new AboutItemListener());
        item3.addActionListener(new DownloadItemListener());
        item4.addActionListener(new HelpItemListener());
        item5.addActionListener(new ImportItemListener());

        //menu.add ( item );
        menu.add(item3);
        menu.add(new JSeparator());
        menu.add(item4);
        menu.add(item2);

        if (Commons.getModel().getHelper().isDevVersion()) {
            menu.add(item5);
        }

        menu.add(new JSeparator());
        submenu = new FavouriteMenu();
        menu.add(submenu);

        Commons.getModel().getGUI().addOptionPanel(getPluginName(), new SettingPanel());
        Commons.getModel().getGUI().addMenu(menu);

        Commons.getModel().getGUI().addTab(getPluginName(), pluginPanel);
        Commons.getModel().getGUI().registerRefreshable(this);
    }
}
