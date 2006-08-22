// %393632151:de.hattrickorganizer.gui.matches%
package de.hattrickorganizer.gui.matches;

import gui.UserParameter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;

import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.Refreshable;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.gui.model.MatchesColumnModel;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Bietet Übersicht über alle Spieler
 */
public final class SpielePanel extends ImagePanel implements MouseListener, KeyListener,
                                                             Refreshable, ItemListener,
                                                             ActionListener, plugins.ISpielePanel
{
    //~ Instance fields ----------------------------------------------------------------------------

    private AufstellungsSternePanel m_jpAufstellungGastPanel;
    private AufstellungsSternePanel m_jpAufstellungHeimPanel;
    private JButton m_jbAufstellungUebernehmen = new JButton(new ImageIcon(de.hattrickorganizer.tools.Helper
                                                                           .loadImage("gui/bilder/AufstellungUebernehmen.png")));
    private JButton m_jbDrucken = new JButton(new ImageIcon(de.hattrickorganizer.tools.Helper
                                                            .loadImage("gui/bilder/Drucken.png")));
    private JButton m_jbLoeschen = new JButton(new ImageIcon(de.hattrickorganizer.tools.Helper
                                                             .getImageDurchgestrichen(new java.awt.image.BufferedImage(20,
                                                                                                                       20,
                                                                                                                       java.awt.image.BufferedImage.TYPE_INT_ARGB),
                                                                                      Color.red,
                                                                                      new Color(200,
                                                                                                0, 0))));
    private JButton m_jbReloadMatch = new JButton(new ImageIcon(de.hattrickorganizer.tools.Helper
                                                                .loadImage("gui/bilder/Reload.png")));
    private JComboBox m_jcbSpieleFilter;

    //private JSplitPane                      horizontalRightSplitPane            = null;
    private JPanel aufstellungsPanel;
    private JSplitPane horizontalLeftSplitPane;
    private JSplitPane verticalSplitPane;
    private JTabbedPane m_jtpSpieldetails;
    private ManschaftsBewertungsPanel m_jpManschaftsBewertungsPanel;
    private de.hattrickorganizer.model.matches.MatchKurzInfo m_clMatchKurzInfo;
    private MatchberichtPanel m_jpMatchberichtPanel;
    private SpielHighlightPanel m_jpSpielHighlightPanel;
    private MatchesTable m_jtSpieleTable;
    private StaerkenvergleichPanel m_jpStaerkenvergleichsPanel;

    /*
       public static final int             ALLE_SPIELE                       = 0;
       public static final int             NUR_EIGENE_SPIELE                 = 1;
       public static final int             NUR_EIGENE_PFLICHTSPIELE          = 2;
       public static final int             NUR_EIGENE_POKALSPIELE            = 3;
       public static final int             NUR_EIGENE_LIGASPIELE             = 4;
       public static final int             NUR_EIGENE_FREUNDSCHAFTSSPIELE    = 5;
       public static final int             NUR_FREMDE_SPIELE                 = 6;
       public static final int             NUR_GESPIELTEN_SPIELE             = 10;
     */
    private CBItem[] SPIELEFILTER = {
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("AlleSpiele"),
                                                   ALLE_SPIELE),
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("NurEigeneSpiele"),
                                                   NUR_EIGENE_SPIELE),
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("NurEigenePflichtspiele"),
                                                   NUR_EIGENE_PFLICHTSPIELE),
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("NurEigenePokalspiele"),
                                                   NUR_EIGENE_POKALSPIELE),
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("NurEigeneLigaspiele"),
                                                   NUR_EIGENE_LIGASPIELE),
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("NurEigeneFreundschaftsspiele"),
                                                   NUR_EIGENE_FREUNDSCHAFTSSPIELE),
                                        new CBItem(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("NurFremdeSpiele"),
                                                   NUR_FREMDE_SPIELE)
                                    };

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielePanel object.
     */
    public SpielePanel() {
        RefreshManager.instance().registerRefreshable(this);

        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gibt die aktuellen DividerLocations zurück, damit sie gespeichert werden können
     *
     * @return TODO Missing Return Method Documentation
     */
    public int[] getDividerLocations() {
        final int[] locations = new int[2];

        locations[0] = horizontalLeftSplitPane.getDividerLocation();
        locations[1] = verticalSplitPane.getDividerLocation();

        return locations;
    }

    /**
     * Gibt die Reihenfolge der Spalten in der Tabelle zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public int[][] getSpaltenreihenfolge() {
        return m_jtSpieleTable.getSpaltenreihenfolge();
    }

    public void saveColumnOrder(){
    	m_jtSpieleTable.saveColumnOrder();
    }
    //----------------------Listener    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(m_jbReloadMatch)) {
            final int matchid = m_clMatchKurzInfo.getMatchID();
            de.hattrickorganizer.gui.HOMainFrame.instance().getOnlineWorker().getMatchlineup(m_clMatchKurzInfo
                                                                                             .getMatchID(),
                                                                                             m_clMatchKurzInfo
                                                                                             .getHeimID(),
                                                                                             m_clMatchKurzInfo
                                                                                             .getGastID());
            de.hattrickorganizer.gui.HOMainFrame.instance().getOnlineWorker().getMatchDetails(m_clMatchKurzInfo
                                                                                              .getMatchID());
            de.hattrickorganizer.logik.MatchUpdater.updateMatch(de.hattrickorganizer.model.HOMiniModel
                                                                .instance(),
                                                                
            //Dragettho werte setzen
            m_clMatchKurzInfo.getMatchID());
            de.hattrickorganizer.gui.RefreshManager.instance().doReInit();
            de.hattrickorganizer.gui.HOMainFrame.instance().showMatch(matchid);
        } else if (e.getSource().equals(m_jbLoeschen)) {
            final int[] rows = m_jtSpieleTable.getSelectedRows();
            final de.hattrickorganizer.model.matches.MatchKurzInfo[] infos = new de.hattrickorganizer.model.matches.MatchKurzInfo[rows.length];

            for (int i = 0; i < rows.length; i++) {
                infos[i] = ((MatchesColumnModel) m_jtSpieleTable.getSorter()
                                                                                             .getModel())
                           .getMatch((int) ((ColorLabelEntry) m_jtSpieleTable.getSorter()
                                                                             .getValueAt(rows[i], 5))
                                     .getZahl());
            }

            String text = de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("loeschen");

            if (infos.length > 1) {
                text += (" (" + infos.length + " "
                + de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Spiele")
                + ") : ");
            } else {
                text += " : ";
            }

            for (int i = 0; (i < infos.length) && (i < 11); i++) {
                text += ("\n" + infos[i].getHeimName() + " - " + infos[i].getGastName());

                if (i == 10) {
                    text += "\n ... ";
                }
            }

            final int value = JOptionPane.showConfirmDialog(this, text,
                                                            de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                   .getResource()
                                                                                                   .getProperty("loeschen"),
                                                            JOptionPane.YES_NO_OPTION);

            if (value == JOptionPane.YES_OPTION) {
                for (int i = 0; i < infos.length; i++) {
                    de.hattrickorganizer.database.DBZugriff.instance().deleteMatch(infos[i]
                                                                                   .getMatchID());
                }

                de.hattrickorganizer.gui.RefreshManager.instance().doReInit();
            }
        } else if (e.getSource().equals(m_jbDrucken)) {
            if (m_clMatchKurzInfo != null) {
                final SpielePrintDialog printDialog = new SpielePrintDialog(m_clMatchKurzInfo);
                printDialog.doPrint(m_clMatchKurzInfo.getHeimName() + " : "
                                    + m_clMatchKurzInfo.getGastName() + " - "
                                    + m_clMatchKurzInfo.getMatchDate());
                printDialog.setVisible(false);
                printDialog.dispose();
            }
        } else if (e.getSource().equals(m_jbAufstellungUebernehmen)) {
            if ((m_clMatchKurzInfo != null)
                && (m_clMatchKurzInfo.getMatchStatus() == de.hattrickorganizer.model.matches.MatchKurzInfo.FINISHED)) {
                final int teamid = de.hattrickorganizer.model.HOVerwaltung.instance().getModel()
                                                                          .getBasics().getTeamId();
                final java.util.Vector vteamspieler = de.hattrickorganizer.database.DBZugriff.instance()
                                                                                             .getMatchLineupPlayers(m_clMatchKurzInfo
                                                                                                                    .getMatchID(),
                                                                                                                    teamid);
                final de.hattrickorganizer.model.Aufstellung aufstellung = de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                  .getModel()
                                                                                                                  .getAufstellung();

                for (int i = 0; (vteamspieler != null) && (i < vteamspieler.size()); i++) {
                    final de.hattrickorganizer.model.matches.MatchLineupPlayer player = (de.hattrickorganizer.model.matches.MatchLineupPlayer) vteamspieler
                                                                                        .get(i);

                    if (player.getId() == de.hattrickorganizer.model.SpielerPosition.standard) {
                        aufstellung.setKicker(player.getSpielerId());
                    } else if (player.getId() == de.hattrickorganizer.model.SpielerPosition.spielfuehrer) {
                        aufstellung.setKapitaen(player.getSpielerId());
                    } else {
                        aufstellung.setSpielerAtPosition(player.getId(), player.getSpielerId(),
                                                         player.getTaktik());
                    }
                }

                //Alles Updaten
                de.hattrickorganizer.gui.HOMainFrame.instance().getAufstellungsPanel().update();

                //Aufstellung zeigen
                de.hattrickorganizer.gui.HOMainFrame.instance().showTab(de.hattrickorganizer.gui.HOMainFrame.AUFSTELLUNG);
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            //Änderung der Tabelle -> Anderer Filter!
            reInit();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public void keyPressed(java.awt.event.KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(m_jtSpieleTable)) {
            //manageSelectionRow (  );
            newSelectionInform();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public void keyReleased(java.awt.event.KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(m_jtSpieleTable)) {
            //manageSelectionRow (  );
            newSelectionInform();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public void keyTyped(java.awt.event.KeyEvent keyEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(m_jtSpieleTable)) {
            //manageSelectionRow (  );
            newSelectionInform();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(m_jtSpieleTable)) {
            //manageSelectionRow (  );
            newSelectionInform();
        }
    }

    /**
     * ReInit
     */
    public void reInit() {
        if (m_jcbSpieleFilter.getSelectedIndex() > -1) {
            //Tabelle updaten
            m_jtSpieleTable.refresh(((CBItem) m_jcbSpieleFilter.getSelectedItem()).getId());
            UserParameter.instance().spieleFilter = ((CBItem) m_jcbSpieleFilter.getSelectedItem())
                                                    .getId();

            //Dann alle anderen Panels
            newSelectionInform();
        }
    }

    //----------------------Refresh--

    /**
     * Refresh
     */
    public void refresh() {
        //nix
    }

    /**
     * Zeigt das Match mit der ID an
     *
     * @param matchid TODO Missing Constructuor Parameter Documentation
     */
    public void showMatch(int matchid) {
        m_jtSpieleTable.markiereMatch(matchid);

        //Wenn kein Match in Tabelle gefunden
        if (m_jtSpieleTable.getSelectedRow() < 0) {
            //Alle Spiele auswählen, damit die Markierung funktioniert  
            m_jcbSpieleFilter.setSelectedIndex(0);
            UserParameter.instance().spieleFilter = 0;
            m_jtSpieleTable.markiereMatch(matchid);
        }

        newSelectionInform();
    }

    /**
     * Für die Button
     */
    private void clear() {
        m_jbReloadMatch.setEnabled(false);
        m_jbLoeschen.setEnabled(false);
        m_jbDrucken.setEnabled(false);
        m_jbAufstellungUebernehmen.setEnabled(false);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initAufstellungGast() {
        m_jpAufstellungGastPanel = new AufstellungsSternePanel(false);
        return m_jpAufstellungGastPanel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initAufstellungHeim() {
        m_jpAufstellungHeimPanel = new AufstellungsSternePanel(true);
        return m_jpAufstellungHeimPanel;
    }

    //----------init-----------------------------------------------
    private void initComponents() {
        setLayout(new BorderLayout());

        horizontalLeftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false,
                                                 initSpieleTabelle(), initSpieldetails());

        //horizontalRightSplitPane =   new JSplitPane( JSplitPane.VERTICAL_SPLIT, false, initAufstellungHeim(), initAufstellungGast() );
        aufstellungsPanel = new JPanel(new GridLayout(2, 1));
        aufstellungsPanel.add(initAufstellungHeim());
        aufstellungsPanel.add(initAufstellungGast());
        verticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false,
                                           horizontalLeftSplitPane,
                                           new JScrollPane(aufstellungsPanel));

        horizontalLeftSplitPane.setDividerLocation(gui.UserParameter.instance().spielePanel_horizontalLeftSplitPane);

        //horizontalRightSplitPane.setDividerLocation( gui.UserParameter.instance ().spielePanel_horizontalRightSplitPane );
        verticalSplitPane.setDividerLocation(gui.UserParameter.instance().spielePanel_verticalSplitPane);

        add(verticalSplitPane, BorderLayout.CENTER);

        m_jbLoeschen.setBackground(Color.WHITE);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initSpieldetails() {
        final GridBagLayout mainlayout = new GridBagLayout();
        final GridBagConstraints mainconstraints = new GridBagConstraints();
        mainconstraints.anchor = GridBagConstraints.NORTH;
        mainconstraints.fill = GridBagConstraints.BOTH;
        mainconstraints.weighty = 1.0;
        mainconstraints.weightx = 1.0;
        mainconstraints.insets = new Insets(4, 6, 4, 6);

        final JPanel mainpanel = new ImagePanel(mainlayout);

        m_jtpSpieldetails = new JTabbedPane();

        //Allgemein
        m_jpStaerkenvergleichsPanel = new StaerkenvergleichPanel();
        m_jtpSpieldetails.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                        .getProperty("Allgemein"),
                                 new JScrollPane(m_jpStaerkenvergleichsPanel));

        //Bewertung
        m_jpManschaftsBewertungsPanel = new ManschaftsBewertungsPanel();
        m_jtpSpieldetails.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                        .getProperty("Bewertung"),
                                 new JScrollPane(m_jpManschaftsBewertungsPanel));

        //Highlights
        m_jpSpielHighlightPanel = new SpielHighlightPanel();
        m_jtpSpieldetails.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                        .getProperty("Highlights"),
                                 new JScrollPane(m_jpSpielHighlightPanel));

        //Matchbericht
        m_jpMatchberichtPanel = new MatchberichtPanel(true);
        m_jtpSpieldetails.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                        .getProperty("Matchbericht"),
                                 m_jpMatchberichtPanel);

        mainconstraints.gridx = 0;
        mainconstraints.gridy = 0;
        mainlayout.setConstraints(m_jtpSpieldetails, mainconstraints);
        mainpanel.add(m_jtpSpieldetails);

        final JPanel buttonPanel = new ImagePanel();
        final GridBagLayout buttonlayout = new GridBagLayout();
        final GridBagConstraints buttonconstraints = new GridBagConstraints();
        buttonconstraints.anchor = GridBagConstraints.NORTH;
        buttonconstraints.fill = GridBagConstraints.NONE;
        buttonconstraints.weighty = 0.0;
        buttonconstraints.weightx = 0.0;
        buttonconstraints.insets = new Insets(4, 6, 4, 6);
        buttonPanel.setLayout(buttonlayout);

        //Reloadbutton
        m_jbReloadMatch.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                              .getResource()
                                                                              .getProperty("tt_Spiel_reload"));
        m_jbReloadMatch.addActionListener(this);
        m_jbReloadMatch.setPreferredSize(new Dimension(24, 24));
        m_jbReloadMatch.setEnabled(false);
        buttonconstraints.gridx = 0;
        buttonconstraints.gridy = 0;
        buttonlayout.setConstraints(m_jbReloadMatch, buttonconstraints);
        buttonPanel.add(m_jbReloadMatch);

        m_jbLoeschen.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                           .getProperty("tt_Spiel_loeschen"));
        m_jbLoeschen.addActionListener(this);
        m_jbLoeschen.setPreferredSize(new Dimension(24, 24));
        m_jbLoeschen.setEnabled(false);
        buttonconstraints.gridx = 1;
        buttonconstraints.gridy = 0;
        buttonlayout.setConstraints(m_jbLoeschen, buttonconstraints);
        buttonPanel.add(m_jbLoeschen);

        m_jbDrucken.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                          .getProperty("tt_Spiel_drucken"));
        m_jbDrucken.addActionListener(this);
        m_jbDrucken.setPreferredSize(new Dimension(24, 24));
        m_jbDrucken.setEnabled(false);
        buttonconstraints.gridx = 2;
        buttonconstraints.gridy = 0;
        buttonlayout.setConstraints(m_jbDrucken, buttonconstraints);
        buttonPanel.add(m_jbDrucken);

        m_jbAufstellungUebernehmen.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                         .getResource()
                                                                                         .getProperty("tt_Spiel_aufstellunguebernehmen"));
        m_jbAufstellungUebernehmen.addActionListener(this);
        m_jbAufstellungUebernehmen.setPreferredSize(new Dimension(24, 24));
        m_jbAufstellungUebernehmen.setEnabled(false);
        buttonconstraints.gridx = 3;
        buttonconstraints.gridy = 0;
        buttonlayout.setConstraints(m_jbAufstellungUebernehmen, buttonconstraints);
        buttonPanel.add(m_jbAufstellungUebernehmen);

        mainconstraints.gridx = 0;
        mainconstraints.gridy = 1;
        mainconstraints.weighty = 0.0;
        mainconstraints.fill = GridBagConstraints.NONE;
        mainconstraints.anchor = GridBagConstraints.SOUTHWEST;
        mainlayout.setConstraints(buttonPanel, mainconstraints);
        mainpanel.add(buttonPanel);

        return mainpanel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initSpieleTabelle() {
        final ImagePanel panel = new ImagePanel(new BorderLayout());

        m_jcbSpieleFilter = new JComboBox(SPIELEFILTER);
        de.hattrickorganizer.tools.Helper.markierenComboBox(m_jcbSpieleFilter,
                                                            UserParameter.instance().spieleFilter);
        m_jcbSpieleFilter.addItemListener(this);
        m_jcbSpieleFilter.setFont(m_jcbSpieleFilter.getFont().deriveFont(Font.BOLD));
        panel.add(m_jcbSpieleFilter, BorderLayout.NORTH);

        m_jtSpieleTable = new MatchesTable(UserParameter.instance().spieleFilter);
        m_jtSpieleTable.addMouseListener(this);
        m_jtSpieleTable.addKeyListener(this);

        final JScrollPane scrollpane = new JScrollPane(m_jtSpieleTable);
        scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

        panel.add(scrollpane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void manageSelectionRow() {
        final int row = m_jtSpieleTable.getSelectedRow();

        if (row > -1) {
            m_jtSpieleTable.setRowSelectionInterval(row, row);
            newSelectionInform();
        } else {
            m_jtSpieleTable.clearSelection();
            newSelectionInform();
        }
    }

    //----------------------------------------------------    
    private void newSelectionInform() {
        final int row = m_jtSpieleTable.getSelectedRow();

        if (row > -1) {
            //Selektiertes Spiel des Models holen und alle 3 Panel informieren 
            try {
                final de.hattrickorganizer.model.matches.MatchKurzInfo info = ((MatchesColumnModel) m_jtSpieleTable.getSorter()
                                                                                                                                                .getModel())
                                                                              .getMatch((int) ((ColorLabelEntry) m_jtSpieleTable.getSorter()
                                                                                                                                .getValueAt(row,
                                                                                                                                            5))
                                                                                        .getZahl());
                refresh(info);
                m_jpStaerkenvergleichsPanel.refresh(info);
                m_jpManschaftsBewertungsPanel.refresh(info);
                m_jpSpielHighlightPanel.refresh(info);
                m_jpMatchberichtPanel.refresh(info);

                if (info.getMatchStatus() == de.hattrickorganizer.model.matches.MatchKurzInfo.FINISHED) {
                    m_jpAufstellungHeimPanel.refresh(info.getMatchID(), info.getHeimID());
                    m_jpAufstellungGastPanel.refresh(info.getMatchID(), info.getGastID());
                } else {
                    m_jpAufstellungHeimPanel.clearAll();
                    m_jpAufstellungGastPanel.clearAll();
                }
            } catch (Exception e) {
                clear();
                m_jpStaerkenvergleichsPanel.clear();
                m_jpManschaftsBewertungsPanel.clear();
                m_jpSpielHighlightPanel.clear();
                m_jpMatchberichtPanel.clear();
                m_jpAufstellungHeimPanel.clearAll();
                m_jpAufstellungGastPanel.clearAll();
                HOLogger.instance().log(getClass(),"SpielePanel.newSelectionInform: Keine Match zum Eintrag in der Tabelle gefunden! "
                                   + e);
            }
        } else {
            //Alle Panels zurücksetzen
            clear();
            m_jpStaerkenvergleichsPanel.clear();
            m_jpManschaftsBewertungsPanel.clear();
            m_jpSpielHighlightPanel.clear();
            m_jpMatchberichtPanel.clear();
            m_jpAufstellungHeimPanel.clearAll();
            m_jpAufstellungGastPanel.clearAll();
        }
    }

    /**
     * Für die Buttons
     *
     * @param info TODO Missing Constructuor Parameter Documentation
     */
    private void refresh(de.hattrickorganizer.model.matches.MatchKurzInfo info) {
        m_clMatchKurzInfo = info;

        m_jbLoeschen.setEnabled(true);
        m_jbDrucken.setEnabled(true);

        //Reload möglich?
        HOLogger.instance().log(getClass(),info.getMatchDateAsTimestamp() + " "
                           + new java.sql.Timestamp(System.currentTimeMillis()) + " "
                           + info.getMatchDateAsTimestamp().before(new java.sql.Timestamp(System
                                                                                          .currentTimeMillis()))
                           + " " + info.getMatchStatus());

        if (info.getMatchDateAsTimestamp().before(new java.sql.Timestamp(System.currentTimeMillis()))) {
            m_jbReloadMatch.setEnabled(true);
        } else {
            m_jbReloadMatch.setEnabled(false);
        }

        //Eigenes Spiel dabei
        final int teamid = de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getBasics()
                                                                  .getTeamId();

        if ((info.getHeimID() == teamid) || (info.getGastID() == teamid)) {
            m_jbAufstellungUebernehmen.setEnabled(true);
        } else {
            m_jbAufstellungUebernehmen.setEnabled(false);
        }
    }
}
