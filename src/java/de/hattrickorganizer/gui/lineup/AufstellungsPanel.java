// %3969157412:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

import de.hattrickorganizer.gui.playeroverview.SpielerUebersichtNamenTable;
import de.hattrickorganizer.tools.extension.FileExtensionManager;


/**
 * Panel zum Darstellen aller SpielerPositionen
 */
public class AufstellungsPanel extends de.hattrickorganizer.gui.templates.ImagePanel
    implements MouseListener, KeyListener
{
    //~ Instance fields ----------------------------------------------------------------------------

    private AufstellungsAssistentPanel m_jpAufstellungsAssistentPanel;
    private AufstellungsDetailPanel m_jpAufstellungsDetailPanel;
    private AufstellungsPositionsPanel m_jpAufstellungsPositionsPanel;
    private AufstellungsVergleichHistoryPanel m_jpAufstellungsVergleichHistoryPanel;
    private AustellungSpielerTable m_jtAufstellungSpielerTable;
    private JSplitPane horizontalLeftSplitPane;
    private JSplitPane horizontalRightSplitPane;
    private JSplitPane verticalSplitPane;
    private JSplitPane verticalSplitPaneLow;
    private SpielerUebersichtNamenTable m_jtAufstellungSpielerTableName;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungsPanel object.
     */
    public AufstellungsPanel() {
        initComponents();		
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gibt das AufstellungsAssistentPanel zur�ck
     *
     * @return TODO Missing Return Method Documentation
     */
    public final AufstellungsAssistentPanel getAufstellungsAssitentPanel() {
        return m_jpAufstellungsAssistentPanel;
    }

    /**
     * Gibt das AufstellungsDetailPanel zur�ck
     *
     * @return TODO Missing Return Method Documentation
     */
    public final AufstellungsDetailPanel getAufstellungsDetailPanel() {
        return m_jpAufstellungsDetailPanel;
    }

    /**
     * Gibt die NamesTabelle zur�ck
     *
     * @return TODO Missing Return Method Documentation
     */
    public final SpielerUebersichtNamenTable getAufstellungsNamensTabelle() {
        return m_jtAufstellungSpielerTableName;
    }

    /**
     * Gibt das AufstellungsPositionsPanel zur�ck
     *
     * @return TODO Missing Return Method Documentation
     */
    public final AufstellungsPositionsPanel getAufstellungsPositionsPanel() {
        return m_jpAufstellungsPositionsPanel;
    }

    /**
     * Gibt die Tabelle zur�ck
     *
     * @return TODO Missing Return Method Documentation
     */
    public final AustellungSpielerTable getAufstellungsTabelle() {
        return m_jtAufstellungSpielerTable;
    }

    /**
     * Breite der BestPosSpalte zur�ckgeben
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getBestPosWidth() {
        return m_jtAufstellungSpielerTable.getBestPosWidth();
    }

    //--------------------------------------------------------

    /**
     * Gibt die aktuellen DividerLocations zur�ck, damit sie gespeichert werden k�nnen
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int[] getDividerLocations() {
        final int[] locations = new int[4];

        locations[0] = verticalSplitPaneLow.getDividerLocation();
        locations[1] = horizontalLeftSplitPane.getDividerLocation();
        locations[2] = horizontalRightSplitPane.getDividerLocation();
        locations[3] = verticalSplitPane.getDividerLocation();

        return locations;
    }

    /**
     * Gibt die Reihenfolge der Spalten in der Tabelle zur�ck
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int[][] getSpaltenreihenfolge() {
        return m_jtAufstellungSpielerTable.getSpaltenreihenfolge();
    }

    public void saveColumnOrder(){
    	m_jtAufstellungSpielerTable.saveColumnOrder();
    }
    
    //---Listener zum Markieren der beiden Tabellen
    public final void keyPressed(java.awt.event.KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(m_jtAufstellungSpielerTable)) {
            final int row = m_jtAufstellungSpielerTable.getSelectedRow();
            m_jtAufstellungSpielerTableName.setRowSelectionInterval(row, row);

            final de.hattrickorganizer.model.Spieler spieler = m_jtAufstellungSpielerTable.getSorter()
                                                                                          .getSpieler(row);

            if (spieler != null) {
                de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                 .getSpielerID());
            }
        } else if (keyEvent.getSource().equals(m_jtAufstellungSpielerTableName)) {
            final int row = m_jtAufstellungSpielerTableName.getSelectedRow();
            m_jtAufstellungSpielerTable.setRowSelectionInterval(row, row);

            final de.hattrickorganizer.model.Spieler spieler = m_jtAufstellungSpielerTableName.getSorter()
                                                                                              .getSpieler(row);

            if (spieler != null) {
                de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                 .getSpielerID());
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public final void keyReleased(java.awt.event.KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(m_jtAufstellungSpielerTable)) {
            final int row = m_jtAufstellungSpielerTable.getSelectedRow();
            m_jtAufstellungSpielerTableName.setRowSelectionInterval(row, row);

            final de.hattrickorganizer.model.Spieler spieler = m_jtAufstellungSpielerTable.getSorter()
                                                                                          .getSpieler(row);

            if (spieler != null) {
                de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                 .getSpielerID());
            }
        } else if (keyEvent.getSource().equals(m_jtAufstellungSpielerTableName)) {
            final int row = m_jtAufstellungSpielerTableName.getSelectedRow();
            m_jtAufstellungSpielerTable.setRowSelectionInterval(row, row);

            final de.hattrickorganizer.model.Spieler spieler = m_jtAufstellungSpielerTableName.getSorter()
                                                                                              .getSpieler(row);

            if (spieler != null) {
                de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                 .getSpielerID());
            }
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
    public final void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(m_jtAufstellungSpielerTable)) {
            final int row = m_jtAufstellungSpielerTable.getSelectedRow();
            m_jtAufstellungSpielerTableName.setRowSelectionInterval(row, row);

            final de.hattrickorganizer.model.Spieler spieler = m_jtAufstellungSpielerTable.getSorter()
                                                                                          .getSpieler(row);

            if (spieler != null) {
                de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                 .getSpielerID());
            }
        } else if (mouseEvent.getSource().equals(m_jtAufstellungSpielerTableName)) {
            final int row = m_jtAufstellungSpielerTableName.getSelectedRow();
            m_jtAufstellungSpielerTable.setRowSelectionInterval(row, row);

            final de.hattrickorganizer.model.Spieler spieler = m_jtAufstellungSpielerTableName.getSorter()
                                                                                              .getSpieler(row);

            if (spieler != null) {
                de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                 .getSpielerID());
            }
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
    public final void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(m_jtAufstellungSpielerTable)) {
            final int row = m_jtAufstellungSpielerTable.getSelectedRow();
            m_jtAufstellungSpielerTableName.setRowSelectionInterval(row, row);

            final de.hattrickorganizer.model.Spieler spieler = m_jtAufstellungSpielerTable.getSorter()
                                                                                          .getSpieler(row);

            if (spieler != null) {
                de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                 .getSpielerID());
            }
        } else if (mouseEvent.getSource().equals(m_jtAufstellungSpielerTableName)) {
            final int row = m_jtAufstellungSpielerTableName.getSelectedRow();
            m_jtAufstellungSpielerTable.setRowSelectionInterval(row, row);

            final de.hattrickorganizer.model.Spieler spieler = m_jtAufstellungSpielerTableName.getSorter()
                                                                                              .getSpieler(row);

            if (spieler != null) {
                de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                 .getSpielerID());
            }
        }
    }

    /**
     * Setzt die Spieler und Taktiken der einzelnen PositionsPanels neu
     */
    public final void update() {
        m_jpAufstellungsPositionsPanel.refresh();
        m_jpAufstellungsDetailPanel.refresh();
        m_jtAufstellungSpielerTable.refresh();
        m_jtAufstellungSpielerTableName.refresh();
		
        //Tabelle und Details der Spieler�bersicht refreshen
        de.hattrickorganizer.gui.HOMainFrame.instance().getSpielerUebersichtPanel().refresh();
        
		m_jpAufstellungsPositionsPanel.exportOldLineup("Actual");
		FileExtensionManager.extractLineup("Actual");                             
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initAufstellungsDetail() {
        m_jpAufstellungsDetailPanel = new AufstellungsDetailPanel();

        return new JScrollPane(m_jpAufstellungsDetailPanel);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initAufstellungsHistory() {
        m_jpAufstellungsVergleichHistoryPanel = new AufstellungsVergleichHistoryPanel();

        return m_jpAufstellungsVergleichHistoryPanel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initButtons() {
        m_jpAufstellungsAssistentPanel = new AufstellungsAssistentPanel();

        return new JScrollPane(m_jpAufstellungsAssistentPanel);
    }

    //----------init-----------------------------------------------
    private void initComponents() {
        setLayout(new BorderLayout());

        //,initButtons(), initAufstellungsHistory() );
        verticalSplitPaneLow = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false);

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("",
                          new ImageIcon(de.hattrickorganizer.tools.Helper.makeColorTransparent(de.hattrickorganizer.tools.Helper
                                                                                               .loadImage("gui/bilder/credits/Ball.png"),
                                                                                               Color.red)
                                                                         .getScaledInstance(13, 13,
                                                                                            Image.SCALE_SMOOTH)),
                          initButtons());
        tabbedPane.addTab("",
                          new ImageIcon(de.hattrickorganizer.tools.Helper.loadImage("gui/bilder/disk.png")),
                          initAufstellungsHistory());
        horizontalLeftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false,
                                                 initSpielerPositionen(), initSpielerTabelle());
        horizontalRightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false,
                                                  
        //verticalSplitPaneLow );
        initAufstellungsDetail(), tabbedPane);
        verticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false,
                                           horizontalLeftSplitPane, horizontalRightSplitPane);

        verticalSplitPaneLow.setDividerLocation(gui.UserParameter.instance().aufstellungsPanel_verticalSplitPaneLow);
        horizontalLeftSplitPane.setDividerLocation(gui.UserParameter.instance().aufstellungsPanel_horizontalLeftSplitPane);
        horizontalRightSplitPane.setDividerLocation(gui.UserParameter.instance().aufstellungsPanel_horizontalRightSplitPane);
        verticalSplitPane.setDividerLocation(gui.UserParameter.instance().aufstellungsPanel_verticalSplitPane);

        add(verticalSplitPane, BorderLayout.CENTER);		       
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initSpielerPositionen() {
        m_jpAufstellungsPositionsPanel = new AufstellungsPositionsPanel(this);

        return new JScrollPane(m_jpAufstellungsPositionsPanel);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initSpielerTabelle() {
        final JPanel panel = new JPanel(new BorderLayout());

        m_jtAufstellungSpielerTable = new AustellungSpielerTable();
        m_jtAufstellungSpielerTable.addMouseListener(this);
        m_jtAufstellungSpielerTable.addKeyListener(this);

        final JScrollPane scrollpane = new JScrollPane(m_jtAufstellungSpielerTable);
        scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

        m_jtAufstellungSpielerTableName = new SpielerUebersichtNamenTable(m_jtAufstellungSpielerTable
                                                                          .getSorter());
        m_jtAufstellungSpielerTableName.addMouseListener(this);
        m_jtAufstellungSpielerTableName.addKeyListener(this);

        final JScrollPane scrollpane2 = new JScrollPane(m_jtAufstellungSpielerTableName);

        //scrollpane2.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        scrollpane2.setPreferredSize(new Dimension(170, 100));

        scrollpane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        //Weil auch im scrollpane immer!
        scrollpane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        //Weil auch im scrollpane2 immer!
        scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Make horizontal scrollbar move top table also
        final JScrollBar bar1 = scrollpane2.getVerticalScrollBar();
        final JScrollBar bar2 = scrollpane.getVerticalScrollBar();
        bar2.addAdjustmentListener(new AdjustmentListener() {
                public void adjustmentValueChanged(AdjustmentEvent e) {
                    bar1.setValue(e.getValue());
                }
            });

        panel.add(scrollpane, BorderLayout.CENTER);
        panel.add(scrollpane2, BorderLayout.WEST);

        return panel;
    }
}
