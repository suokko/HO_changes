// %3994494499:de.hattrickorganizer.gui.pluginWrapper%
package ho.core.plugins;

import ho.core.gui.print.ComponentPrintObject;
import ho.core.gui.print.PrintController;
import ho.core.gui.theme.ThemeManager;

import java.awt.Color;

import javax.swing.JPanel;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.login.LoginWaitDialog;
import de.hattrickorganizer.gui.model.SpielerTableRenderer;
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

    public static GUIPluginWrapper instance() {
        if (m_clInstance == null) {
            m_clInstance = new GUIPluginWrapper();
        }

        return m_clInstance;
    }

    public plugins.IInfoPanel getInfoPanel() {
        return HOMainFrame.instance().getInfoPanel();
    }

    public javax.swing.JFrame getOwner4Dialog() {
        return HOMainFrame.instance();
    }

    public void addHOTableRenderer(javax.swing.JTable table) {
        table.setDefaultRenderer(java.lang.Object.class,
                                 new SpielerTableRenderer());
        table.setSelectionBackground(SpielerTableRenderer.SELECTION_BG);
    }

    public void addMainFrameListener(java.awt.event.WindowListener listener) {
        HOMainFrame.instance().addMainFrameListener(listener);
    }

    public void addMenu(javax.swing.JMenu menu) {
        HOMainFrame.instance().addMenu(menu);
    }

    public void addOptionPanel(String name, javax.swing.JPanel optionpanel) {
        HOMainFrame.instance().addOptionPanel(name, optionpanel);
    }

    public void addPlayerComboboxRenderer(javax.swing.JComboBox combobox) {
        combobox.setRenderer(new de.hattrickorganizer.gui.model.SpielerCBItemRenderer());
    }

    public void addTab(String name, JPanel panel) {
        HOMainFrame.instance().getTabbedPane().addTab(name, panel);
    }

    public void addTopLevelMenu(javax.swing.JMenu menu) {
        HOMainFrame.instance().addTopLevelMenu(menu);
    }

    public JPanel createBallPanel(int ballcount) {
        return (JPanel) new de.hattrickorganizer.gui.templates.TorLabelEntry(ballcount)
               .getComponent(false);
    }

    public plugins.IDebugWindow createDebugWindow(java.awt.Point location, java.awt.Dimension size) {
        return new ho.core.plugins.DebugWindow(location, size);
    }

    public javax.swing.JPanel createGrassPanel() {
        return new de.hattrickorganizer.gui.templates.RasenPanel();
    }

    public javax.swing.JPanel createImagePanel() {
        return new de.hattrickorganizer.gui.templates.ImagePanel();
    }

    public JPanel createMatchPredictionPanel(plugins.IMPTeamData hometeam,
                                             plugins.IMPTeamData guestteam) {
        return new de.hattrickorganizer.gui.matchprediction.MatchEnginePanel(hometeam, guestteam);
    }

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

    public plugins.ISpielerComboboxItem createSpielerComboboxItem(String text, float posvalue,
                                                                  plugins.ISpieler player) {
        return new de.hattrickorganizer.gui.model.SpielerCBItem(text, posvalue, player);
    }

    public JPanel createStarPanel(int starcount, boolean yellowstar) {
        return (JPanel) new de.hattrickorganizer.gui.templates.RatingTableEntry(starcount,
                                                                                yellowstar)
               .getComponent(false);
    }

    public javax.swing.JWindow createWaitDialog(java.awt.Window owner) {
        if (owner != null) {
            return new LoginWaitDialog(owner);
        }
        return new LoginWaitDialog(HOMainFrame.instance());
    }

    public void doLineupRefresh() {
        //Das Aufstellungspanel aktualisiert sich selber und die Spielerübersichtstabelle!
        HOMainFrame.instance().getAufstellungsPanel().update();
    }

    public void doRefresh() {
        //Refresh in den Plugins ist ein ReInit intern!
       RefreshManager.instance().doReInit();
    }

    public void print(String name, JPanel panel) {
        try {
            final PrintController printController = PrintController.getInstance();

            printController.add(new ComponentPrintObject(printController.getPf(),name,
                                                         panel,
                                                         ho.core.gui.print.ComponentPrintObject.SICHTBAR));

            printController.print();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    public void registerRefreshable(plugins.IRefreshable refreshable) {
       RefreshManager.instance().registerRefreshable(refreshable);
    }

    public void removeMainFrameListener(java.awt.event.WindowListener listener) {
        HOMainFrame.instance().removeMainFrameListener(listener);
    }

    public void unregisterRefreshable(plugins.IRefreshable refreshable) {
        RefreshManager.instance().unregisterRefreshable(refreshable);
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
