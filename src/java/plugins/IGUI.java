// %1127326826915:plugins%
package plugins;

import javax.swing.JPanel;
import javax.swing.JWindow;


/**
 * This Interface define methodes to include the plugin into the GUI of HO! For loadImage-methodes
 * see IHelper
 */
public interface IGUI {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Get the information (status) barpanel at the bottom of the MainFrame
     *
     * @return TODO Missing Return Method Documentation
     */
    public IInfoPanel getInfoPanel();

    /**
     * Get the HO!-Mainframe as the owner for a Dialog
     *
     * @return TODO Missing Return Method Documentation
     */
    public javax.swing.JFrame getOwner4Dialog();

    //---Rendere Methods----------------------------------------------------------

    /**
     * Set the default-Renderer of the table so you can put IHOTableEntries in the table. You can
     * show javax.swing.JComponent instead of simple text.
     *
     * @param table TODO Missing Constructuor Parameter Documentation
     */
    public void addHOTableRenderer(javax.swing.JTable table);

    /**
     * Add a Windowlistener to the HO!-MainFrame to receive an Event by exiting HO!
     *
     * @param listener TODO Missing Constructuor Parameter Documentation
     */
    public void addMainFrameListener(java.awt.event.WindowListener listener);

    /**
     * Add a Plugin Menu to the HO!-Frame
     *
     * @param menu the Menu you want to add
     */
    public void addMenu(javax.swing.JMenu menu);

    /**
     * Add a panel to the Option-Dialog where the User can set the settings for  this plugin
     *
     * @param name Name für the Tab in the OptionDialog
     * @param optionpanel The panel with the options for the plugin
     */
    public void addOptionPanel(String name, javax.swing.JPanel optionpanel);

    /**
     * Set a Renderer to a JCombobox, so you can put ISpielerComboboxItems in it.
     *
     * @param combobox TODO Missing Constructuor Parameter Documentation
     */
    public void addPlayerComboboxRenderer(javax.swing.JComboBox combobox);

    /**
     * Adds the Panel to the JTabbedPane on the HO!-Mainframe.
     *
     * @param name Name für the Tab
     * @param panel The panel, you want to add
     */
    public void addTab(String name, JPanel panel);

    /**
     * Add a Menu to the HO!-Frame
     *
     * @param menu the Menu you want to add
     */
    public void addTopLevelMenu(javax.swing.JMenu menu);

    /**
     * Returns a panel that shows a number of soccerballs
     *
     * @param ballcount Number of balls, that shall be shown on the panel
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel createBallPanel(int ballcount);

    /**
     * Returns a DebugWindow for the Debuginformations. System.out can not be used so use this
     * Window instead
     *
     * @param position Position, where the DebugWindow shall be shown
     * @param size Size of the DebugWindow
     *
     * @return TODO Missing Return Method Documentation
     */
    public IDebugWindow createDebugWindow(java.awt.Point position, java.awt.Dimension size);

    /**
     * Returns a panel with the grass-background, used for the lineup
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel createGrassPanel();

    //---Additional Methods-----------------------------------------------------

    /**
     * Returns a panel with the gray-Image-background, usualy used in HO!
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel createImagePanel();

    /**
     * Returns a MatchPredictionpanel (IMatchPredictionPanel) Create the parameter with
     * IMatchPredictionManager you receive from IHOMiniModel.getMatchPredictionManager
     *
     * @param hometeam Teamdata for the hometeam
     * @param guestteam Teamdata for the guestteam
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel createMatchPredictionPanel(plugins.IMPTeamData hometeam,
                                             plugins.IMPTeamData guestteam);

    /**
     * Returns a IHOTableEntry for a player. You can use the getComponent()-methode to display the
     * Component elsewhere.
     *
     * @param player the player
     * @param positionid the actual position of the player ( ISpielerPosition.keeper, .. )
     * @param taktic the taktic for the position ( ISpielerPosition.NORMAL, ... )
     * @param positionvalue HO!Rating for the player at that position, shown as (x.x) behind the
     *        name, if showTrikot=true and this values != 0
     * @param showTrikot shows Trikot before the playername
     * @param showWeatherwarning acourding to the weather set in the lineupassist a warningicon may
     *        appear right of the playername For instance: Playersquadtable: positionvalue=0f,
     *        showTrikot=false, showWeatherwarning=false LineupComboBoxes:
     *
     * @return TODO Missing Return Method Documentation
     */
    public IHOTableEntry createPlayerTableEntry(plugins.ISpieler player, int positionid,
                                                byte taktic, float positionvalue,
                                                boolean showTrikot, boolean showWeatherwarning);

    /**
     * Returns a Item for a JCombobox with the addPlayerComboboxRenderer() set.
     *
     * @param text Shown text, usually spieler.getName()
     * @param posvalue Rating for the actual position
     * @param player The player
     *
     * @return TODO Missing Return Method Documentation
     */
    public ISpielerComboboxItem createSpielerComboboxItem(String text, float posvalue,
                                                          ISpieler player);

    /**
     * Returns a panel that shows a number of stars. For large numbers of stars a star with a 5, 10
     * or 50 written on it is used
     *
     * @param starcount Number of half(!)stars, that shall be shown on the panel
     * @param yellowstar true: the stars are yellow, false: the stars are grey
     *
     * @return TODO Missing Return Method Documentation
     */
    public JPanel createStarPanel(int starcount, boolean yellowstar);

    /**
     * Returns a WaitDialog with a (not accessable) Progressbar.  Just use the setVisible( boolean
     * ) Methode to show or hide the Dialog.
     *
     * @param owner Owner-Window for the Wait-Dialog, or null, if the HO!-MainFrame shall be the
     *        owner
     *
     * @return TODO Missing Return Method Documentation
     */
    public JWindow createWaitDialog(java.awt.Window owner);

    /**
     * Informs the player- and lineuptables and the lineupscreen, that the lineup  has been
     * changed. This methode is much faster than doRefresh and should be used, if a plugin changes
     * the lineup
     */
    public void doLineupRefresh();

    /**
     * Inform all registered Refreshable-Objects in HO! and call their refresh- methods
     */
    public void doRefresh();

    /**
     * Prints the panel
     *
     * @param name Name for the print
     * @param panel The panel, that shall be printed
     */
    public void print(String name, JPanel panel);

    //---Update Methods---------------------------------------------------------

    /**
     * Register the IRefreshable, so the refresh-method is called, if the data is changed ( new HRF
     * downloaded, etc. )
     *
     * @param refreshable The refreshable-objekt, that shall be informed
     */
    public void registerRefreshable(IRefreshable refreshable);

    /**
     * Remove the Windowlistener from the HO!-MainFrame
     *
     * @param listener TODO Missing Constructuor Parameter Documentation
     */
    public void removeMainFrameListener(java.awt.event.WindowListener listener);

    /**
     * Remove the registration for that Objekt
     *
     * @param refreshable The refreshable-objekt, that shall be removed
     */
    public void unregisterRefreshable(IRefreshable refreshable);
}
