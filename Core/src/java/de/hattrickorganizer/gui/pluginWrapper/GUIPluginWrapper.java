// %3994494499:de.hattrickorganizer.gui.pluginWrapper%
package de.hattrickorganizer.gui.pluginWrapper;

import java.awt.Color;

import javax.swing.JPanel;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Wrapped die Methoden des IGUI -Interfaces auf die Methoden innerhalb von HO!
 */
public class GUIPluginWrapper implements plugins.IGUI {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static GUIPluginWrapper m_clInstance;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new GUIPluginWrapper object.
     */
    private GUIPluginWrapper() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static GUIPluginWrapper instance() {
        if (m_clInstance == null) {
            m_clInstance = new GUIPluginWrapper();
        }

        return m_clInstance;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.IInfoPanel getInfoPanel() {
        return HOMainFrame.instance().getInfoPanel();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public javax.swing.JFrame getOwner4Dialog() {
        return HOMainFrame.instance();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param table TODO Missing Method Parameter Documentation
     */
    public void addHOTableRenderer(javax.swing.JTable table) {
        table.setDefaultRenderer(java.lang.Object.class,
                                 new de.hattrickorganizer.gui.model.SpielerTableRenderer());
        table.setSelectionBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param listener TODO Missing Method Parameter Documentation
     */
    public void addMainFrameListener(java.awt.event.WindowListener listener) {
        HOMainFrame.instance().addMainFrameListener(listener);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param menu TODO Missing Method Parameter Documentation
     */
    public void addMenu(javax.swing.JMenu menu) {
        HOMainFrame.instance().addMenu(menu);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param name TODO Missing Method Parameter Documentation
     * @param optionpanel TODO Missing Method Parameter Documentation
     */
    public void addOptionPanel(String name, javax.swing.JPanel optionpanel) {
        HOMainFrame.instance().addOptionPanel(name, optionpanel);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param combobox TODO Missing Method Parameter Documentation
     */
    public void addPlayerComboboxRenderer(javax.swing.JComboBox combobox) {
        combobox.setRenderer(new de.hattrickorganizer.gui.model.SpielerCBItemRenderer());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param name TODO Missing Method Parameter Documentation
     * @param panel TODO Missing Method Parameter Documentation
     */
    public void addTab(String name, JPanel panel) {
        HOMainFrame.instance().getTabbedPane().addTab(name, panel);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param menu TODO Missing Method Parameter Documentation
     */
    public void addTopLevelMenu(javax.swing.JMenu menu) {
        HOMainFrame.instance().addTopLevelMenu(menu);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param ballcount TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel createBallPanel(int ballcount) {
        return (JPanel) new de.hattrickorganizer.gui.templates.TorLabelEntry(ballcount)
               .getComponent(false);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param location TODO Missing Method Parameter Documentation
     * @param size TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.IDebugWindow createDebugWindow(java.awt.Point location, java.awt.Dimension size) {
        return new de.hattrickorganizer.gui.pluginWrapper.DebugWindow(location, size);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public javax.swing.JPanel createGrassPanel() {
        return new de.hattrickorganizer.gui.templates.RasenPanel();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public javax.swing.JPanel createImagePanel() {
        return new de.hattrickorganizer.gui.templates.ImagePanel();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param hometeam TODO Missing Method Parameter Documentation
     * @param guestteam TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel createMatchPredictionPanel(plugins.IMPTeamData hometeam,
                                             plugins.IMPTeamData guestteam) {
        return new de.hattrickorganizer.gui.matchprediction.MatchEnginePanel(hometeam, guestteam);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param player TODO Missing Method Parameter Documentation
     * @param positionid TODO Missing Method Parameter Documentation
     * @param taktic TODO Missing Method Parameter Documentation
     * @param positionvalue TODO Missing Method Parameter Documentation
     * @param showTrikot TODO Missing Method Parameter Documentation
     * @param showWeatherwarning TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.IHOTableEntry createPlayerTableEntry(plugins.ISpieler player, int positionid,
                                                        byte taktic, float positionvalue,
                                                        boolean showTrikot,
                                                        boolean showWeatherwarning) {
        final de.hattrickorganizer.model.SpielerPosition spielerpos = new de.hattrickorganizer.model.SpielerPosition(positionid,
                                                                                                                     player
                                                                                                                     .getSpielerID(),
                                                                                                                     taktic);
        return new de.hattrickorganizer.gui.templates.SpielerLabelEntry(player, spielerpos,
                                                                        positionvalue, showTrikot,
                                                                        showWeatherwarning);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     * @param posvalue TODO Missing Method Parameter Documentation
     * @param player TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.ISpielerComboboxItem createSpielerComboboxItem(String text, float posvalue,
                                                                  plugins.ISpieler player) {
        return new de.hattrickorganizer.gui.model.SpielerCBItem(text, posvalue, player);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param starcount TODO Missing Method Parameter Documentation
     * @param yellowstar TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel createStarPanel(int starcount, boolean yellowstar) {
        return (JPanel) new de.hattrickorganizer.gui.templates.RatingTableEntry(starcount,
                                                                                yellowstar)
               .getComponent(false);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param owner TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public javax.swing.JWindow createWaitDialog(java.awt.Window owner) {
        if (owner != null) {
            return new de.hattrickorganizer.gui.login.LoginWaitDialog(owner);
        } else {
            return new de.hattrickorganizer.gui.login.LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame
                                                                      .instance());
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public void doLineupRefresh() {
        //Das Aufstellungspanel aktualisiert sich selber und die Spielerübersichtstabelle!
        de.hattrickorganizer.gui.HOMainFrame.instance().getAufstellungsPanel().update();
    }

    /**
     * TODO Missing Method Documentation
     */
    public void doRefresh() {
        //Refresh in den Plugins ist ein ReInit intern!
        de.hattrickorganizer.gui.RefreshManager.instance().doReInit();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param name TODO Missing Method Parameter Documentation
     * @param panel TODO Missing Method Parameter Documentation
     */
    public void print(String name, JPanel panel) {
        try {
            final de.hattrickorganizer.gui.print.PrintController printController = de.hattrickorganizer.gui.print.PrintController
                                                                                   .getInstance();

            printController.add(new de.hattrickorganizer.gui.print.ComponentPrintObject(printController
                                                                                        .getPf(),
                                                                                        name,
                                                                                        panel,
                                                                                        de.hattrickorganizer.gui.print.ComponentPrintObject.SICHTBAR));

            printController.print();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param refreshable TODO Missing Method Parameter Documentation
     */
    public void registerRefreshable(plugins.IRefreshable refreshable) {
        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(refreshable);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param listener TODO Missing Method Parameter Documentation
     */
    public void removeMainFrameListener(java.awt.event.WindowListener listener) {
        HOMainFrame.instance().removeMainFrameListener(listener);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param refreshable TODO Missing Method Parameter Documentation
     */
    public void unregisterRefreshable(plugins.IRefreshable refreshable) {
        de.hattrickorganizer.gui.RefreshManager.instance().unregisterRefreshable(refreshable);
    }
    
    /**
     * return a HO Theme Color
     * 
     * @param String color key text for example "table.player.skill.background"
     */
    public Color getColor(String key){
    	return ThemeManager.getColor(key);
    }
}
