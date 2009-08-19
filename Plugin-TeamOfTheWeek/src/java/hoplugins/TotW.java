// %3421472745:hoplugins%
package hoplugins;

import hoplugins.toTW.PotwUI;
import hoplugins.toTW.listener.ImportMatchListener;
import hoplugins.toTW.listener.ImportTeamListener;

import plugins.IHOMiniModel;
import plugins.IOfficialPlugin;
import plugins.IPlugin;
import plugins.IRefreshable;

import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TotW implements IPlugin, IRefreshable, IOfficialPlugin {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static IHOMiniModel model;
    private static PotwUI ui;
    private static int week;
    private static int season;
    private static int liga;
    private static int maxWeek;
    private static double VERSION = 0.201d;
    private static final int PLUGIN_ID = 43;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public static void setLiga(int i) {
        liga = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int getLiga() {
        return liga;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int getMaxWeek() {
        return maxWeek;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static IHOMiniModel getModel() {
        return model;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getName() {
        return getPluginName() + " " + getVersion();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public static void setSeason(int i) {
        season = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int getSeason() {
        return season;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public static void setWeek(int i) {
        week = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int getWeek() {
        return week;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param isSeason TODO Missing Constructuor Parameter Documentation
     */
    public static void forceRefresh(boolean isSeason) {
        ui.reloadData(isSeason);
        ui.updateUI();
    }

    /**
     * Get the plugin Id of this plugin.
     */
    public final int getPluginID() {
        return PLUGIN_ID;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getPluginName() {
        return "Team of the Week";
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public File[] getUnquenchableFiles() {
        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getVersion() {
        return VERSION;
    }

    /**
     * TODO Missing Method Documentation
     */
    public void refresh() {
        ui.reloadData(true);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param hOMiniModel TODO Missing Method Parameter Documentation
     */
    public void start(IHOMiniModel hOMiniModel) {
        model = hOMiniModel;
        week = model.getBasics().getSpieltag() - 1;

        if (week < 1) {
            week = 1;
        }

        if (week > 14) {
            week = 14;
        }

        maxWeek = week;
        season = model.getBasics().getSeason();
        ui = new PotwUI();
        model.getGUI().addTab(getPluginName(), ui);
        model.getGUI().registerRefreshable(this);

        JMenu menu = new JMenu(getPluginName());
        JMenuItem item = new javax.swing.JMenuItem("Import Team Data");
        JMenuItem item2 = new javax.swing.JMenuItem("Import Week Matches");
        item.addActionListener(new ImportTeamListener());
        item2.addActionListener(new ImportMatchListener());
        menu.add(item);
        menu.add(item2);

        Commons.getModel().getGUI().addMenu(menu);
    }
}
