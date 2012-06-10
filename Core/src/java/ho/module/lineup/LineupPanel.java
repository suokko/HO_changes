package ho.module.lineup;

import ho.core.file.extension.FileExtensionManager;
import ho.core.gui.HOMainFrame;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.player.Spieler;
import ho.module.playerOverview.PlayerTable;
import ho.module.playerOverview.SpielerUebersichtNamenTable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Panel zum Darstellen aller SpielerPositionen
 */
public class LineupPanel extends ho.core.gui.comp.panel.ImagePanel {

	private static final long serialVersionUID = -8522462525789028842L;

	// ~ Instance fields
	// ----------------------------------------------------------------------------
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

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Creates a new AufstellungsPanel object.
	 */
	public LineupPanel() {
		initComponents();
		addListeners();
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------

	/**
	 * Selects the player with the given id.
	 * 
	 * @param idPlayer
	 *            the id of the player to select.
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

	// --------------------------------------------------------

	/**
	 * Gibt die aktuellen DividerLocations zurück, damit sie gespeichert werden
	 * können
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

	public void saveColumnOrder() {
		m_jtAufstellungSpielerTable.saveColumnOrder();
	}

	/**
	 * Setzt die Spieler und Taktiken der einzelnen PositionsPanels neu
	 */
	public final void update() {
		m_jpAufstellungsPositionsPanel.refresh();
		m_jpAufstellungsDetailPanel.refresh();
		m_jtAufstellungSpielerTable.refresh();
		m_jtAufstellungSpielerTableName.refresh();

		// Tabelle und Details der Spielerübersicht refreshen
		ho.core.gui.HOMainFrame.instance().getSpielerUebersichtPanel().refresh();

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

	// ----------init-----------------------------------------------
	private void initComponents() {
		setLayout(new BorderLayout());

		// ,initButtons(), initAufstellungsHistory() );
		verticalSplitPaneLow = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false);

		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("", ThemeManager.getScaledIcon(HOIconName.BALL, 13, 13), initButtons());
		tabbedPane.addTab("", ThemeManager.getIcon(HOIconName.DISK), initAufstellungsHistory());
		horizontalLeftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false,
				initSpielerPositionen(), initSpielerTabelle());
		horizontalRightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false,

		// verticalSplitPaneLow );
				initAufstellungsDetail(), tabbedPane);
		verticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false,
				horizontalLeftSplitPane, horizontalRightSplitPane);

		verticalSplitPaneLow
				.setDividerLocation(ho.core.model.UserParameter.instance().aufstellungsPanel_verticalSplitPaneLow);
		horizontalLeftSplitPane
				.setDividerLocation(ho.core.model.UserParameter.instance().aufstellungsPanel_horizontalLeftSplitPane);
		horizontalRightSplitPane
				.setDividerLocation(ho.core.model.UserParameter.instance().aufstellungsPanel_horizontalRightSplitPane);
		verticalSplitPane
				.setDividerLocation(ho.core.model.UserParameter.instance().aufstellungsPanel_verticalSplitPane);

		add(verticalSplitPane, BorderLayout.CENTER);
	}

	private Component initSpielerPositionen() {
		m_jpAufstellungsPositionsPanel = new LineupPositionsPanel(this);

		return new JScrollPane(m_jpAufstellungsPositionsPanel);
	}

	private Component initSpielerTabelle() {
		final JPanel panel = new JPanel(new BorderLayout());

		m_jtAufstellungSpielerTable = new AustellungSpielerTable();

		m_jtAufstellungSpielerTableName = new SpielerUebersichtNamenTable(
				m_jtAufstellungSpielerTable.getSorter());

		final JScrollPane scrollpane = new JScrollPane(m_jtAufstellungSpielerTableName);
		scrollpane.setPreferredSize(new Dimension(170, 100));
		scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		final JScrollPane scrollpane2 = new JScrollPane(m_jtAufstellungSpielerTable);
		scrollpane2.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		scrollpane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		final JScrollBar bar = scrollpane.getVerticalScrollBar();
		final JScrollBar bar2 = scrollpane2.getVerticalScrollBar();
		// setVisibile(false) does not have an effect, so we set the size to
		// false
		// we can't disable the scrollbar with VERTICAL_SCROLLBAR_NEVER
		// because this will disable mouse wheel scrolling
		bar.setPreferredSize(new Dimension(0, 0));

		// Synchronize vertical scrolling
		AdjustmentListener adjustmentListener = new AdjustmentListener() {
			@Override
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

	private void addListeners() {

		ListSelectionListener lsl = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getSource() == m_jtAufstellungSpielerTable.getSelectionModel()) {
					synchronizeSelection(m_jtAufstellungSpielerTable);
				} else if (e.getSource() == m_jtAufstellungSpielerTableName.getSelectionModel()) {
					synchronizeSelection(m_jtAufstellungSpielerTableName);
				}
			}

			private void synchronizeSelection(JTable sourceTable) {
				JTable targetTable;
				if (sourceTable == m_jtAufstellungSpielerTable) {
					targetTable = m_jtAufstellungSpielerTableName;
				} else {
					targetTable = m_jtAufstellungSpielerTable;
				}

				int row = sourceTable.getSelectedRow();
				if (targetTable.getSelectedRow() != row) {
					targetTable.setRowSelectionInterval(row, row);
				}
				Spieler spieler = ((PlayerTable) sourceTable).getSpieler(row);
				if (spieler != null) {
					HOMainFrame.instance().setActualSpieler(spieler.getSpielerID());
				}
			}
		};

		this.m_jtAufstellungSpielerTable.getSelectionModel().addListSelectionListener(lsl);
		this.m_jtAufstellungSpielerTableName.getSelectionModel().addListSelectionListener(lsl);
	}
}
