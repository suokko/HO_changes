// %1605218128:de.hattrickorganizer.gui.playeroverview%
package de.hattrickorganizer.gui.playeroverview;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

import de.hattrickorganizer.gui.templates.ImagePanel;


/**
 * Bietet Übersicht über alle Spieler
 * (main class of the package)
 */
public class SpielerUebersichtsPanel extends ImagePanel implements MouseListener, KeyListener {

	private static final long serialVersionUID = -5795792661614081193L;
	
	//~ Instance fields ----------------------------------------------------------------------------

    //Die Namen sind nicht mehr aktuelle!!
    //private JSplitPane                      horizontalLeftSplitPane             = null;
    private JSplitPane horizontalRightSplitPane;
    private JSplitPane verticalSplitPane;
    private SpielerDetailPanel m_jpSpielerDetailPanel;
    private SpielerTrainingsSimulatorPanel m_jpSpielerTrainingsSimulatorPanel;
    private SpielerTrainingsVergleichsPanel m_jpSpielerTrainingsVergleichsPanel;
    private SpielerUebersichtNamenTable m_jtSpielerUebersichtTableName;
    private PlayerOverviewTable m_jtSpielerUebersichtTable;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerUebersichtsPanel object.
     */
    public SpielerUebersichtsPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Aktuell markierter Spieler
     *
     * @return TODO Missing Return Method Documentation
     */
    public final de.hattrickorganizer.model.Spieler getAktuellenSpieler() {
        final int row = m_jtSpielerUebersichtTable.getSelectedRow();

        if (row > -1) {
            final de.hattrickorganizer.gui.utils.TableSorter smodel = m_jtSpielerUebersichtTable
                                                                      .getSorter();
            return smodel.getSpieler(row);
        }

        return null;
    }

    /**
     * Breite der BestPosSpalte zurückgeben
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getBestPosWidth() {
        return m_jtSpielerUebersichtTable.getBestPosWidth();
    }

    /**
     * Gibt die aktuellen DividerLocations zurück, damit sie gespeichert werden können
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int[] getDividerLocations() {
        final int[] locations = new int[3];

        //horizontalLeftSplitPane.getDividerLocation ();
        locations[0] = 0;
        locations[1] = horizontalRightSplitPane.getDividerLocation();
        locations[2] = verticalSplitPane.getDividerLocation();

        return locations;
    }

    public final void saveColumnOrder(){
    	m_jtSpielerUebersichtTable.saveColumnOrder();
    }
    
    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final SpielerUebersichtNamenTable getSpielerUebersichtNamenTable() {
        return m_jtSpielerUebersichtTableName;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final PlayerOverviewTable getSpielerUebersichtTable() {
        return m_jtSpielerUebersichtTable;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public final void keyPressed(java.awt.event.KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(m_jtSpielerUebersichtTable)) {
            final int row = m_jtSpielerUebersichtTable.getSelectedRow();

            if (row > -1) {
                m_jtSpielerUebersichtTableName.setRowSelectionInterval(row, row);

                final de.hattrickorganizer.model.Spieler spieler = m_jtSpielerUebersichtTable.getSorter()
                                                                                             .getSpieler(row);

                if (spieler != null) {
                    de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                     .getSpielerID());
                }
            }
        } else if (keyEvent.getSource().equals(m_jtSpielerUebersichtTableName)) {
            final int row = m_jtSpielerUebersichtTableName.getSelectedRow();

            if (row > -1) {
                m_jtSpielerUebersichtTable.setRowSelectionInterval(row, row);

                final de.hattrickorganizer.model.Spieler spieler = m_jtSpielerUebersichtTable.getSorter()
                                                                                             .getSpieler(row);

                if (spieler != null) {
                    de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                     .getSpielerID());
                }
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public final void keyReleased(java.awt.event.KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(m_jtSpielerUebersichtTable)) {
            final int row = m_jtSpielerUebersichtTable.getSelectedRow();

            if (row > -1) {
                m_jtSpielerUebersichtTableName.setRowSelectionInterval(row, row);

                final de.hattrickorganizer.model.Spieler spieler = m_jtSpielerUebersichtTable.getSorter()
                                                                                             .getSpieler(row);

                if (spieler != null) {
                    de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                     .getSpielerID());
                }
            }
        } else if (keyEvent.getSource().equals(m_jtSpielerUebersichtTableName)) {
            final int row = m_jtSpielerUebersichtTableName.getSelectedRow();

            if (row > -1) {
                m_jtSpielerUebersichtTable.setRowSelectionInterval(row, row);

                final de.hattrickorganizer.model.Spieler spieler = m_jtSpielerUebersichtTable.getSorter()
                                                                                             .getSpieler(row);

                if (spieler != null) {
                    de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                     .getSpielerID());
                }
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

    //----------------------Listener    
    public final void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(m_jtSpielerUebersichtTable)) {
            final int row = m_jtSpielerUebersichtTable.getSelectedRow();

            if (row > -1) {
                m_jtSpielerUebersichtTableName.setRowSelectionInterval(row, row);

                final de.hattrickorganizer.model.Spieler spieler = m_jtSpielerUebersichtTable.getSorter()
                                                                                             .getSpieler(row);

                if (spieler != null) {
                    de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                     .getSpielerID());
                }
            }
        } else if (mouseEvent.getSource().equals(m_jtSpielerUebersichtTableName)) {
            final int row = m_jtSpielerUebersichtTableName.getSelectedRow();

            if (row > -1) {
                m_jtSpielerUebersichtTable.setRowSelectionInterval(row, row);

                final de.hattrickorganizer.model.Spieler spieler = m_jtSpielerUebersichtTable.getSorter()
                                                                                             .getSpieler(row);

                if (spieler != null) {
                    de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                     .getSpielerID());
                }
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
        if (mouseEvent.getSource().equals(m_jtSpielerUebersichtTable)) {
            final int row = m_jtSpielerUebersichtTable.getSelectedRow();

            if (row > -1) {
                m_jtSpielerUebersichtTableName.setRowSelectionInterval(row, row);

                final de.hattrickorganizer.model.Spieler spieler = m_jtSpielerUebersichtTable.getSorter()
                                                                                             .getSpieler(row);

                if (spieler != null) {
                    de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                     .getSpielerID());
                }
            }
        } else if (mouseEvent.getSource().equals(m_jtSpielerUebersichtTableName)) {
            final int row = m_jtSpielerUebersichtTableName.getSelectedRow();

            if (row > -1) {
                m_jtSpielerUebersichtTable.setRowSelectionInterval(row, row);

                final de.hattrickorganizer.model.Spieler spieler = m_jtSpielerUebersichtTable.getSorter()
                                                                                             .getSpieler(row);

                if (spieler != null) {
                    de.hattrickorganizer.gui.HOMainFrame.instance().setActualSpieler(spieler
                                                                                     .getSpielerID());
                }
            }
        }
    }

    //----------------------------------------------------    
    public final void newSelectionInform() {
        final int row = m_jtSpielerUebersichtTable.getSelectedRow();

        if (row > -1) {
            final de.hattrickorganizer.gui.utils.TableSorter model = m_jtSpielerUebersichtTable
                                                                     .getSorter();
            m_jpSpielerDetailPanel.setSpieler(model.getSpieler(row));
            m_jpSpielerTrainingsSimulatorPanel.setSpieler(model.getSpieler(row));
        } else {
            m_jpSpielerDetailPanel.setSpieler(null);
            m_jpSpielerTrainingsSimulatorPanel.setSpieler(null);
        }
    }

    //----------------------Refresh--

    /**
     * Refresh, wenn ein Spieler in der Aufstellung geändert wird
     */
    public final void refresh() {
        m_jpSpielerDetailPanel.refresh();
        m_jtSpielerUebersichtTable.refresh();
    }

    /**
     * Erneuert alle Spalten, die bei von einem Vergleich betroffen sind
     */
    public final void refreshHRFVergleich() {
        m_jtSpielerUebersichtTable.refreshHRFVergleich();

        final de.hattrickorganizer.model.Spieler spieler = m_jtSpielerUebersichtTable.getSorter()
                                                                                     .getSpieler(m_jtSpielerUebersichtTable
                                                                                                 .getSelectedRow());
        m_jpSpielerDetailPanel.setSpieler(spieler);
    }

    /**
     * Refeshed die Tabelle hier und im Aufstellungspanel, wenn die Gruppen/Info geändert wurde
     */
    public final void update() {
        refresh();

        de.hattrickorganizer.gui.HOMainFrame.instance().getAufstellungsPanel()
                                            .getAufstellungsTabelle().refresh();
    }

    //----------init-----------------------------------------------
    private void initComponents() {
        setLayout(new BorderLayout());

        final Component tabelle = initSpielerTabelle();

        //horizontalLeftSplitPane  =   new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, false, initSpielerTabelle(), initSpielerDetail() );
        horizontalRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false,
                                                  initSpielerDetail(), initSpielerHistory());
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, tabelle,
                                           horizontalRightSplitPane);

        //horizontalLeftSplitPane.setDividerLocation( gui.UserParameter.instance ().spielerUebersichtsPanel_horizontalLeftSplitPane );
        horizontalRightSplitPane.setDividerLocation(gui.UserParameter.instance().spielerUebersichtsPanel_horizontalRightSplitPane);
        verticalSplitPane.setDividerLocation(gui.UserParameter.instance().spielerUebersichtsPanel_verticalSplitPane);

        add(verticalSplitPane, BorderLayout.CENTER);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initSpielerDetail() {
        final JTabbedPane tabbedPane = new JTabbedPane();
        m_jpSpielerDetailPanel = new SpielerDetailPanel();

        JScrollPane scrollPane = new JScrollPane(m_jpSpielerDetailPanel);
        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        tabbedPane.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("SpielerDetails"),
                          scrollPane);

        m_jpSpielerTrainingsSimulatorPanel = new SpielerTrainingsSimulatorPanel();
        scrollPane = new JScrollPane(m_jpSpielerTrainingsSimulatorPanel);
        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        tabbedPane.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Skilltester"),
                          scrollPane);

        return tabbedPane;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initSpielerHistory() {
        final JPanel panel = new de.hattrickorganizer.gui.templates.ImagePanel();
        panel.setLayout(new BorderLayout());
        m_jpSpielerTrainingsVergleichsPanel = new SpielerTrainingsVergleichsPanel();

        final JScrollPane scrollPane = new JScrollPane(m_jpSpielerTrainingsVergleichsPanel);
        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        panel.add(m_jpSpielerTrainingsVergleichsPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(new RemoveGruppenPanel(m_jtSpielerUebersichtTable)),
                  BorderLayout.NORTH);
        return panel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initSpielerTabelle() {
        final JPanel panel = new JPanel(new BorderLayout());

        m_jtSpielerUebersichtTable = new PlayerOverviewTable();
        m_jtSpielerUebersichtTable.addMouseListener(this);
        m_jtSpielerUebersichtTable.addKeyListener(this);

        final JScrollPane scrollpane = new JScrollPane(m_jtSpielerUebersichtTable);
        scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

        m_jtSpielerUebersichtTableName = new SpielerUebersichtNamenTable(m_jtSpielerUebersichtTable
                                                                         .getSorter());
        m_jtSpielerUebersichtTableName.addMouseListener(this);
        m_jtSpielerUebersichtTableName.addKeyListener(this);

        final JScrollPane scrollpane2 = new JScrollPane(m_jtSpielerUebersichtTableName);

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
