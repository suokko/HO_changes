// %1127327738025:hoplugins%
package hoplugins;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.sql.ResultSet;


//implement IPlugin for integration into HO
//Refreshable to get informed by data updates
//Actionlistner for Button interaction
public class DummyPlugIn implements plugins.IPlugin, plugins.IRefreshable,
                                    java.awt.event.ActionListener
{
    //~ Instance fields ----------------------------------------------------------------------------

    private plugins.IHOMiniModel m_clModel = null;
    private javax.swing.JButton m_jbChangeLineup = null;
    private javax.swing.JButton m_jbDebugWindow = null;
    private javax.swing.JButton m_jbDownload = null;
    private javax.swing.JPanel m_jpPanel = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of DummyPlugIn
     */
    public DummyPlugIn() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * return pluginName
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getName() {
        return "Dummy";
    }

    //Accesses to Database
    public boolean getSpielerSpielberechtigt(int spielerId) {
        ResultSet rs = null;
        String sql = null;

        //prepare sql statement
        sql = "SELECT Spielberechtigt FROM SpielerNotiz WHERE SpielerID = " + spielerId;

        //execute and Store Resultset
        rs = m_clModel.getAdapter().executeQuery(sql);

        try {
            if (rs != null) {
                //go to first entry in result set
                rs.first();

                //fetch boolean named "Spielberechtigt"
                return rs.getBoolean("Spielberechtigt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //default value
        return true;
    }

    /**
     * handle events
     *
     * @param e TODO Missing Constructuor Parameter Documentation
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
        //Downloadexample
        if (e.getSource().equals(m_jbDownload)) {
            String clubXML = "";

            //Download example
            try {
                clubXML = m_clModel.getDownloadHelper().getHattrickXMLFile("/common/club.asp?outputType=XML&actionType=view");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //call parser func
            parseXML(m_clModel.getXMLParser().parseString(clubXML));
        }
        //Debugwindow
        else if (e.getSource().equals(m_jbDebugWindow)) {
            //Create a DebugWindow and shows some messages 
            plugins.IDebugWindow debugWindow = m_clModel.getGUI().createDebugWindow(new java.awt.Point(100,
                                                                                                       200),
                                                                                    new java.awt.Dimension(700,
                                                                                                           400));
            debugWindow.setVisible(true);

            //append a text
            debugWindow.append("This is the fist line of Text");

            //append a text as HTML
            debugWindow.append("Write it <b>bold</b> or <i>italic</i> if you <font color=#ff0000>want</font>");

            //append a empty line
            debugWindow.append("");

            //append a Exception
            try {
                //Something stupid to create a Exception and a Stacktrace
                String[] s = new String[2];
                s[3] = "indexfailure";
            } catch (Exception ex) {
                //Append the Exception
                debugWindow.append(ex);
            }

            //Another text
            debugWindow.append("That Exception was just an example, no bug!");
        }
        //Set Keeper -> change Lineup
        else if (e.getSource().equals(m_jbChangeLineup)) {
            //Get the first player in line
            java.util.Vector playerlist = m_clModel.getAllSpieler();

            //List has some player
            if (playerlist.size() > 0) {
                //Get the first player in line
                plugins.ISpieler player = (plugins.ISpieler) playerlist.get(0);

                //Set that player as keeper in the lineup
                byte positionid = m_clModel.getLineUP().setSpielerAtPosition(plugins.ISpielerPosition.keeper,
                                                                             player.getSpielerID(),
                                                                             plugins.ISpielerPosition.NORMAL);

                //The positionid is ISpielerPosition.TORWART
                //Inform HO! that the lineup has been changed. 
                //Call that methode only once after your have done all changes in the lineup (Maybe change the whole lineup at once)
                //If you do not call that methode the new keeper will not been shown in the lineup
                m_clModel.getGUI().doLineupRefresh();

                //Show a message, what has happend
                m_clModel.getHelper().showMessage(m_jpPanel,
                                                  player.getName() + " has been set as keeper",
                                                  "keeper changed",
                                                  javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * what to do if ho informs there're new data available
     */
    public void refresh() {
        //        if ( ! m_clModel.getAllSpieler ().isEmpty () )
        //        {
        //            plugins.ISpieler player = (plugins.ISpieler) m_clModel.getAllSpieler ().elementAt ( 0 );
        //            //now perform db-access
        //            boolean couldPlay = getSpielerSpielberechtigt( player.getSpielerID () );
        //            if ( couldPlay )
        //            {
        //                m_clModel.getHelper ().showMessage ( m_jpPanel, player.getName () + " can play", m_clModel.getBasics().getManager(), javax.swing.JOptionPane.INFORMATION_MESSAGE );                       
        //            }
        //            else
        //            {
        //                 m_clModel.getHelper ().showMessage ( m_jpPanel, player.getName () + "can't play", m_clModel.getBasics().getManager(), javax.swing.JOptionPane.INFORMATION_MESSAGE );                       
        //            }
        //        }        
    }

    /**
     * Is called by HO! to start the plugin
     *
     * @param hOMiniModel TODO Missing Constructuor Parameter Documentation
     */
    public void start(plugins.IHOMiniModel hOMiniModel) {
        //Var for Name displayed in Tab
        String sTabName = "";

        //Save model in member Var
        m_clModel = hOMiniModel;

        /*
         * create an optionpanel
         */
        javax.swing.JPanel optionpanel = new javax.swing.JPanel();
        optionpanel.add(new javax.swing.JTextArea(10, 10));
        hOMiniModel.getGUI().addOptionPanel(this.getName(), optionpanel);

        /*
         * create an grass panel Background
         */
        m_jpPanel = hOMiniModel.getGUI().createGrassPanel();

        //use null layout ( better use a Layoutmanager for your plugins! )
        m_jpPanel.setLayout(null);

        /*
         * Download XML-Button
         */
        //prepare button
        m_jbDownload = new javax.swing.JButton();
        m_jbDownload.setText("Download");
        m_jbDownload.setToolTipText("Downloads club.asp and displays teamname");
        m_jbDownload.addActionListener(this);
        m_jbDownload.setLocation(25, 5);
        m_jbDownload.setSize(100, 25);

        //add button for download xml file
        m_jpPanel.add(m_jbDownload);

        /**
         * Show DebugWindow-Button
         */
        m_jbDebugWindow = new javax.swing.JButton();
        m_jbDebugWindow.setText("DebugWindow");
        m_jbDebugWindow.setToolTipText("Shows the DebugWindow");
        m_jbDebugWindow.addActionListener(this);
        m_jbDebugWindow.setLocation(25, 300);
        m_jbDebugWindow.setSize(120, 25);

        //add button to show the DebugWindow
        m_jpPanel.add(m_jbDebugWindow);

        /**
         * Change the keeper of the actual lineup
         */
        m_jbChangeLineup = new javax.swing.JButton();
        m_jbChangeLineup.setText("Change keeper");
        m_jbChangeLineup.setToolTipText("set the first player as keeper");
        m_jbChangeLineup.addActionListener(this);
        m_jbChangeLineup.setLocation(25, 340);
        m_jbChangeLineup.setSize(120, 25);

        //add button to show the DebugWindow
        m_jpPanel.add(m_jbChangeLineup);

        /*
         * Example on how to access images from ho
         *
         * fullstar :   Yellow star with white pixels made transparent
         * fullstar2:   Yellow star with specified colorrange made transparent
         * greystar :   Grey star with white pixels made transparent
         * greystar2:   Grey star with specified colorrange made transparent
         */

        // Load icon and make white-background transparent 
        javax.swing.ImageIcon fullstar = new javax.swing.ImageIcon(m_clModel.getHelper()
                                                                            .makeColorTransparent(m_clModel.getHelper()
                                                                                                           .loadImage("gui/bilder/star.gif"),
                                                                                                  java.awt.Color.white));

        //put icon in container
        javax.swing.JLabel container = new javax.swing.JLabel(fullstar);

        //position container
        container.setLocation(150, 5);
        container.setSize(fullstar.getIconWidth(), fullstar.getIconHeight());

        //add it to panel
        m_jpPanel.add(container);

        //Load icon and make specified colorrange transparent
        javax.swing.ImageIcon fullstar2 = new javax.swing.ImageIcon(m_clModel.getHelper()
                                                                             .makeColorTransparent(m_clModel.getHelper()
                                                                                                            .loadImage("gui/bilder/star.gif"),
                                                                                                   210,
                                                                                                   210,
                                                                                                   185,
                                                                                                   255,
                                                                                                   255,
                                                                                                   255));
        container = new javax.swing.JLabel(fullstar2);

        //position container
        container.setLocation(180, 5);
        container.setSize(fullstar.getIconWidth(), fullstar.getIconHeight());

        //add it to panel
        m_jpPanel.add(container);

        // Load icon and make white-background transparent 
        javax.swing.ImageIcon greystar = new javax.swing.ImageIcon(m_clModel.getHelper()
                                                                            .makeColorTransparent(m_clModel.getHelper()
                                                                                                           .loadImage("gui/bilder/star_grey.png"),
                                                                                                  java.awt.Color.white));

        //put icon in container
        container = new javax.swing.JLabel(greystar);

        //position container
        container.setLocation(150, 25);
        container.setSize(fullstar.getIconWidth(), fullstar.getIconHeight());

        //add it to panel
        m_jpPanel.add(container);

        //Load icon and make specified colorrange transparent
        javax.swing.ImageIcon greystar2 = new javax.swing.ImageIcon(m_clModel.getHelper()
                                                                             .makeColorTransparent(m_clModel.getHelper()
                                                                                                            .loadImage("gui/bilder/star_grey.png"),
                                                                                                   215,
                                                                                                   215,
                                                                                                   215,
                                                                                                   255,
                                                                                                   255,
                                                                                                   255));
        container = new javax.swing.JLabel(greystar2);

        //position container
        container.setLocation(180, 25);
        container.setSize(fullstar.getIconWidth(), fullstar.getIconHeight());

        //add it to panel
        m_jpPanel.add(container);

        /*
         * Use the properties, getNameFor-Methodes and special panels
         */

        //Local panel with background
        javax.swing.JPanel localpanel = m_clModel.getGUI().createImagePanel();

        //localpanel.setOpaque ( false ); //Would make the panel opaque, so the Grass of the m_jpPanel would be shown
        localpanel.setLayout(new java.awt.GridLayout(8, 2, 3, 3));
        localpanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        //Add labels with the caption and related components
        javax.swing.JPanel starpanel = null;

        javax.swing.JLabel label = new javax.swing.JLabel();
        label.setForeground(java.awt.Color.white); //Better to read on the Grass-Background
        label.setFont(label.getFont().deriveFont(java.awt.Font.BOLD)); //Make Font bold
        label.setText(m_clModel.getResource().getProperty("Aggressivitaet", "Defaultlabletext"));
        localpanel.add(label); //Font white and bold
        label = new javax.swing.JLabel(m_clModel.getHelper().getNameForAggressivness(1));
        localpanel.add(label); //Font black and plain

        localpanel.add(new javax.swing.JLabel(m_clModel.getResource().getProperty("Ansehen",
                                                                                  "Defaultlabletext")));
        localpanel.add(new javax.swing.JLabel(m_clModel.getHelper().getNameForGentleness(1)));

        localpanel.add(new javax.swing.JLabel(m_clModel.getResource().getProperty("Charakter",
                                                                                  "Defaultlabletext")));
        localpanel.add(new javax.swing.JLabel(m_clModel.getHelper().getNameForCharacter(1)));

        localpanel.add(new javax.swing.JLabel(m_clModel.getResource().getProperty("Bewertung",
                                                                                  "Defaultlabletext")));
        localpanel.add(new javax.swing.JLabel(m_clModel.getHelper().getNameForSkill(1, false)));

        localpanel.add(new javax.swing.JLabel(m_clModel.getResource().getProperty("Spezialitaet",
                                                                                  "Defaultlabletext")));
        localpanel.add(new javax.swing.JLabel(m_clModel.getHelper().getNameForSpeciality(1)));

        localpanel.add(new javax.swing.JLabel(m_clModel.getResource().getProperty("Bewertung",
                                                                                  "Defaultlabletext")));
        starpanel = m_clModel.getGUI().createStarPanel(37, true);
        localpanel.add(starpanel);

        localpanel.add(new javax.swing.JLabel(m_clModel.getResource().getProperty("Bewertung",
                                                                                  "Defaultlabletext")));
        starpanel = m_clModel.getGUI().createStarPanel(13, false);
        starpanel.setOpaque(false); //Don´t Paint Background
        localpanel.add(starpanel);

        localpanel.add(new javax.swing.JLabel(m_clModel.getResource().getProperty("Tore",
                                                                                  "Defaultlabletext")));
        localpanel.add(m_clModel.getGUI().createBallPanel(5));

        //Set Location and Size of that panel
        localpanel.setLocation(25, 60);
        localpanel.setSize(200, 150);

        //add to mainpanel
        m_jpPanel.add(localpanel);

        /**
         * Two Panels, that show a dummylineup
         */
        localpanel = new javax.swing.JPanel();
        localpanel.setOpaque(false); // The Grass shines through
        localpanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        localpanel.setLayout(new java.awt.GridLayout(2, 1));

        //Create the first Panel with Teamname and injured
        plugins.LineupPanel lineupPanel1 = new plugins.LineupPanel(plugins.LineupPanel.LINEUP_NORMAL_SEQUENCE);
        fillLineup(lineupPanel1, true);

        //Create the second Panel without Teamname and injured
        plugins.LineupPanel lineupPanel2 = new plugins.LineupPanel(plugins.LineupPanel.LINEUP_REVERSE_SEQUENCE);
        fillLineup(lineupPanel2, false);

        localpanel.add(lineupPanel1);
        localpanel.add(lineupPanel2);

        //Set Location and Size of that panel
        localpanel.setLocation(250, 5);
        localpanel.setSize(400, 400);

        //add to mainpanel
        m_jpPanel.add(localpanel);

        /*
         * Show the actual LanguageID and Name
         */
        label = new javax.swing.JLabel("LanguageID");
        label.setForeground(java.awt.Color.WHITE);
        label.setSize(120, 25);
        label.setLocation(25, 390);
        m_jpPanel.add(label);

        label = new javax.swing.JLabel(m_clModel.getHelper().getLanguageID() + "");
        label.setForeground(java.awt.Color.WHITE);
        label.setSize(120, 25);
        label.setLocation(160, 390);
        m_jpPanel.add(label);

        label = new javax.swing.JLabel("LanguageName");
        label.setForeground(java.awt.Color.WHITE);
        label.setSize(120, 25);
        label.setLocation(25, 420);
        m_jpPanel.add(label);

        label = new javax.swing.JLabel(m_clModel.getHelper().getLanguageName());
        label.setForeground(java.awt.Color.WHITE);
        label.setSize(120, 25);
        label.setLocation(160, 420);
        m_jpPanel.add(label);

        /** Add a JMenu to the HO! Mainframe, but ignore all events (no Listener) */
        javax.swing.JMenu menu = new javax.swing.JMenu("DummyPlugin");
        javax.swing.JMenuItem item = new javax.swing.JMenuItem("do nothing");

        //item.addActionListener ( what? ); To get the klick, ignored here!
        menu.add(item);
        m_clModel.getGUI().addMenu(menu);

        /*
         * Demo : Read a property from Resource
         * The text "Version" will be searched in the actual languagefile and the value related to it will be returned
         *
         * Don´t use such long Tabnames for your plugin!
         */
        sTabName = "DummyPlugin in HO! V."
                   + m_clModel.getResource().getProperty("Version",
                                                         "place your choosen default value here");

        /*
         * Finally, add the panel to HO!s TabbedPane
         */
        //add a new Panel to ho
        m_clModel.getGUI().addTab(sTabName, m_jpPanel);

        //we'd like to get informed by changes from ho
        m_clModel.getGUI().registerRefreshable(this);

        //To Stop information use this code
        // m_clModel.getGUI ().unregisterRefreshable ( this );
        //XtraDataDemo
        xtraDataDemo();

        //Is User Match check
        //this func checks out if given MatchId is a match of User-Team
        boolean userMatch = m_clModel.getHelper().isUserMatch("123456");

        //display info in statusbar of HO.
        m_clModel.getGUI().getInfoPanel().setLangInfoText("DummyPlugin Test");
    }

    /**
     * TODO Missing Method Documentation
     */
    public void xtraDataDemo() {
        //check if Plugin Version supports xtraData
        if ((this.VERSION < 1.05d) || (m_clModel.getXtraDaten().getCurrencyRate() == -1.0d)) //check wheather user had updated Data, so xtraData are available
         {
            //xtra Data not present
            return;
        }

        //Play around with data ...
        String CurrencyName = m_clModel.getXtraDaten().getCurrencyName();
        java.sql.Timestamp trainingDate = m_clModel.getXtraDaten().getTrainingDate();
    }

    /**
     * parse the xml doc
     *
     * @param doc TODO Missing Constructuor Parameter Documentation
     */
    protected void parseXML(Document doc) {
        Element ele = null;
        Element tmpEle = null;

        try {
            //get Root element :
            ele = doc.getDocumentElement();

            //get specific sub element of root element
            tmpEle = (Element) ele.getElementsByTagName("Team").item(0);

            //get specific sub element of team element
            tmpEle = (Element) tmpEle.getElementsByTagName("TeamName").item(0);

            //get it's value
            String value = m_clModel.getXMLParser().getFirstChildNodeValue(tmpEle);
            m_clModel.getHelper().showMessage(m_jpPanel, value, "XML Parsed",
                                              javax.swing.JOptionPane.YES_OPTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------
    private void fillLineup(plugins.LineupPanel lineupPanel, boolean mode) {
        fillPanel(lineupPanel.getKeeperPanel(), "Keeper");
        fillPanel(lineupPanel.getLeftWingbackPanel(), "Right Wingback");
        fillPanel(lineupPanel.getLeftCentralDefenderPanel(), "Left Defender");
        fillPanel(lineupPanel.getRightCentralDefenderPanel(), "Right Defender");
        fillPanel(lineupPanel.getRightWingbackPanel(), "Right Wingback");
        fillPanel(lineupPanel.getLeftWingPanel(), "Left Wing");
        fillPanel(lineupPanel.getLeftMidfieldPanel(), "Left Midfield");
        fillPanel(lineupPanel.getRightMidfieldPanel(), "Right Midfield");
        fillPanel(lineupPanel.getRightWingPanel(), "Right Wing");
        fillPanel(lineupPanel.getLeftForwardPanel(), "Left Forward");
        fillPanel(lineupPanel.getRightForwardPanel(), "Right Forward");
        fillPanel(lineupPanel.getSetPiecesPanel(), "Set Pieces");
        fillPanel(lineupPanel.getTeamLeaderPanel(), "Team Leader");
        fillPanel(lineupPanel.getReserveKeeperPanel(), "Reserve Keeper");
        fillPanel(lineupPanel.getReserveDefenderPanel(), "Reserve Defender");
        fillPanel(lineupPanel.getReserveMidfieldPanel(), "Reserve Midfield");
        fillPanel(lineupPanel.getReserveForwardPanel(), "Reserve Forward");
        fillPanel(lineupPanel.getReserveWingPanel(), "Reserve Wing");

        if (mode) {
            fillPanel(lineupPanel.getInjured1Panel(), "Injured1");
            fillPanel(lineupPanel.getInjured2Panel(), "Injured2");
            fillPanel(lineupPanel.getInjured3Panel(), "Injured3");
            lineupPanel.setTeamName("Teamname");
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param panel TODO Missing Method Parameter Documentation
     * @param text TODO Missing Method Parameter Documentation
     */
    private void fillPanel(javax.swing.JPanel panel, String text) {
        //Create a label and place it in the middle
        javax.swing.JLabel label = new javax.swing.JLabel(text);
        label.setForeground(java.awt.Color.white);
        label.setVerticalAlignment(javax.swing.JLabel.CENTER);
        label.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        //Borderlayout, for it is only one label
        panel.setLayout(new java.awt.BorderLayout());

        //Lineborder to see the size
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        //The size should be set ( or it looks strange )
        panel.setPreferredSize(new java.awt.Dimension(90, 20));

        //add the label
        panel.add(label, java.awt.BorderLayout.CENTER);
    }
}
