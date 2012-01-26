// %3969157412:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import gui.HOIconName;

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
import de.hattrickorganizer.gui.theme.ImageUtilities;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.tools.extension.FileExtensionManager;


/**
 * Panel zum Darstellen aller SpielerPositionen
 */
public class LineupPanel extends de.hattrickorganizer.gui.templates.ImagePanel
    implements MouseListener, KeyListener
{
	
	private static final long serialVersionUID = -8522462525789028842L;
	
    //~ Instance fields ----------------------------------------------------------------------------
	private AufstellungsAssistentPanel m_jpAufstellungsAssistentPanel;
    private AufstellungsDetailPanel m_jpAufstellungsDetailPanel;
    private LineupPositionsPanel m_jpAufstellungsPositionsPanel;
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
    public LineupPanel() {
        initComponents();		
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Selects the player with the given id.
     * 
     * @param idPlayer the id of the player to select.
     */
    public void setPlayer(int idPlayer) {
    	m_jtAufstellungSpielerTableName.setSpieler(idPlayer);
    	m_jtAufstellungSpielerTable.setSpieler(idPlayer);
    }
    
    /**
     * Refreshes the view.
     */
    public void refresh() {
    	m_jtAufstellungSpielerTable.refresh();
    }
    
    /**
     * Gibt das AufstellungsAssistentPanel zurück
     *
     */
    public final AufstellungsAssistentPanel getAufstellungsAssitentPanel() {
        return m_jpAufstellungsAssistentPanel;
    }

    /**
     * Gibt das AufstellungsDetailPanel zurück
     *
    */
    public final AufstellungsDetailPanel getAufstellungsDetailPanel() {
        return m_jpAufstellungsDetailPanel;
    }

    /**
     * Gibt das AufstellungsPositionsPanel zurück
     *
     */
    public final LineupPositionsPanel getAufstellungsPositionsPanel() {
        return m_jpAufstellungsPositionsPanel;
    }

    /**
     * Breite der BestPosSpalte zurückgeben
     *
     */
    public final int getBestPosWidth() {
        return m_jtAufstellungSpielerTable.getBestPosWidth();
    }

    //--------------------------------------------------------

    /**
     * Gibt die aktuellen DividerLocations zurück, damit sie gespeichert werden können
     *
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
     * Gibt die Reihenfolge der Spalten in der Tabelle zurück
     *
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

 
    public void keyTyped(java.awt.event.KeyEvent keyEvent) {
    }

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

    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
    }

    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
    }


    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
    }

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
		
        //Tabelle und Details der Spielerübersicht refreshen
        de.hattrickorganizer.gui.HOMainFrame.instance().getSpielerUebersichtPanel().refresh();
        
		m_jpAufstellungsPositionsPanel.exportOldLineup("Actual");
		FileExtensionManager.extractLineup("Actual");                             
    }

 
    private Component initAufstellungsDetail() {
        m_jpAufstellungsDetailPanel = new AufstellungsDetailPanel();

        return new JScrollPane(m_jpAufstellungsDetailPanel);
    }

 
    private Component initAufstellungsHistory() {
        m_jpAufstellungsVergleichHistoryPanel = new AufstellungsVergleichHistoryPanel();

        return m_jpAufstellungsVergleichHistoryPanel;
    }


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
                          new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/Ball.png"),
                                                                                               Color.red)
                                                                         .getScaledInstance(13, 13,
                                                                                            Image.SCALE_SMOOTH)),
                          initButtons());
        tabbedPane.addTab("",ThemeManager.getIcon(HOIconName.DISK),initAufstellungsHistory());
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


    private Component initSpielerPositionen() {
        m_jpAufstellungsPositionsPanel = new LineupPositionsPanel(this);

        return new JScrollPane(m_jpAufstellungsPositionsPanel);
    }

    private Component initSpielerTabelle() {
        final JPanel panel = new JPanel(new BorderLayout());

        m_jtAufstellungSpielerTable = new AustellungSpielerTable();
        m_jtAufstellungSpielerTable.addMouseListener(this);
        m_jtAufstellungSpielerTable.addKeyListener(this);

        m_jtAufstellungSpielerTableName = new SpielerUebersichtNamenTable(m_jtAufstellungSpielerTable
                                                                          .getSorter());
        m_jtAufstellungSpielerTableName.addMouseListener(this);
        m_jtAufstellungSpielerTableName.addKeyListener(this);

        final JScrollPane scrollpane = new JScrollPane(m_jtAufstellungSpielerTableName);
        scrollpane.setPreferredSize(new Dimension(170, 100));
        scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        final JScrollPane scrollpane2 = new JScrollPane(m_jtAufstellungSpielerTable);
        scrollpane2.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        scrollpane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		final JScrollBar bar = scrollpane.getVerticalScrollBar();
        final JScrollBar bar2 = scrollpane2.getVerticalScrollBar();
		// setVisibile(false) does not have an effect, so we set the size to false
		// we can' disable the scrollbar with VERTICAL_SCROLLBAR_NEVER because this 
		// will disable mouse wheel scrolling
		bar.setPreferredSize(new Dimension(0,0)); 
		
		// Synchronize vertical scrolling
		AdjustmentListener adjustmentListener = new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if (e.getSource() == bar2) {
					bar.setValue(e.getValue());
				} else {
					bar2.setValue(e.getValue());
				}
			}
		};
		bar.addAdjustmentListener(adjustmentListener);
		bar2.addAdjustmentListener(adjustmentListener);

		panel.add(scrollpane, BorderLayout.WEST);
		panel.add(scrollpane2, BorderLayout.CENTER);

        return panel;
    }
}
