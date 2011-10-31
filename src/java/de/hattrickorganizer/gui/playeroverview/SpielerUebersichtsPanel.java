// %1605218128:de.hattrickorganizer.gui.playeroverview%
package de.hattrickorganizer.gui.playeroverview;

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

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.utils.TableSorter;
import de.hattrickorganizer.model.Spieler;

/**
 * Bietet Übersicht über alle Spieler (main class of the package)
 */
public class SpielerUebersichtsPanel extends ImagePanel {

	private static final long serialVersionUID = -5795792661614081193L;

	// ~ Instance fields
	// ----------------------------------------------------------------------------

	// Die Namen sind nicht mehr aktuelle!!
	private JSplitPane horizontalRightSplitPane;
	private JSplitPane verticalSplitPane;
	private SpielerDetailPanel m_jpSpielerDetailPanel;
	private SpielerTrainingsSimulatorPanel m_jpSpielerTrainingsSimulatorPanel;
	private SpielerTrainingsVergleichsPanel m_jpSpielerTrainingsVergleichsPanel;
	private SpielerUebersichtNamenTable m_jtSpielerUebersichtTableName;
	private PlayerOverviewTable m_jtSpielerUebersichtTable;

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Creates a new SpielerUebersichtsPanel object.
	 */
	public SpielerUebersichtsPanel() {
		initComponents();
		addTableSelectionListeners();
	}

	/**
	 * Selects the player with the given id.
	 * 
	 * @param idPlayer
	 *            the id of the player to select.
	 */
	public void setPlayer(int idPlayer) {
		m_jtSpielerUebersichtTableName.setSpieler(idPlayer);
		m_jtSpielerUebersichtTable.setSpieler(idPlayer);
	}

	/**
	 * Aktuell markierter Spieler
	 */
	public final Spieler getAktuellenSpieler() {
		final int row = m_jtSpielerUebersichtTable.getSelectedRow();

		if (row > -1) {
			final TableSorter smodel = m_jtSpielerUebersichtTable.getSorter();
			return smodel.getSpieler(row);
		}

		return null;
	}

	/**
	 * Breite der BestPosSpalte zurückgeben
	 */
	public final int getBestPosWidth() {
		return m_jtSpielerUebersichtTable.getBestPosWidth();
	}

	/**
	 * Gibt die aktuellen DividerLocations zurück, damit sie gespeichert werden
	 * können.
	 */
	public final int[] getDividerLocations() {
		final int[] locations = new int[3];
		locations[0] = 0;
		locations[1] = horizontalRightSplitPane.getDividerLocation();
		locations[2] = verticalSplitPane.getDividerLocation();

		return locations;
	}

	public final void saveColumnOrder() {
		m_jtSpielerUebersichtTable.saveColumnOrder();
	}

	public final void newSelectionInform() {
		final int row = m_jtSpielerUebersichtTable.getSelectedRow();

		if (row > -1) {
			final de.hattrickorganizer.gui.utils.TableSorter model = m_jtSpielerUebersichtTable
					.getSorter();
			m_jpSpielerDetailPanel.setSpieler(model.getSpieler(row));
			m_jpSpielerTrainingsSimulatorPanel
					.setSpieler(model.getSpieler(row));
		} else {
			m_jpSpielerDetailPanel.setSpieler(null);
			m_jpSpielerTrainingsSimulatorPanel.setSpieler(null);
		}
	}

	// ----------------------Refresh--

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

		final Spieler spieler = m_jtSpielerUebersichtTable.getSorter()
				.getSpieler(m_jtSpielerUebersichtTable.getSelectedRow());
		m_jpSpielerDetailPanel.setSpieler(spieler);
	}

	/**
	 * Refeshed die Tabelle hier und im Aufstellungspanel, wenn die Gruppen/Info
	 * geändert wurde
	 */
	public final void update() {
		refresh();
		HOMainFrame.instance().getAufstellungsPanel().refresh();
	}

	// ----------init-----------------------------------------------
	private void initComponents() {
		setLayout(new BorderLayout());

		final Component tabelle = initSpielerTabelle();
		horizontalRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				false, initSpielerDetail(), initSpielerHistory());
		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false,
				tabelle, horizontalRightSplitPane);

		horizontalRightSplitPane.setDividerLocation(gui.UserParameter
				.instance().spielerUebersichtsPanel_horizontalRightSplitPane);
		verticalSplitPane
				.setDividerLocation(gui.UserParameter.instance().spielerUebersichtsPanel_verticalSplitPane);

		add(verticalSplitPane, BorderLayout.CENTER);
	}

	private Component initSpielerDetail() {
		final JTabbedPane tabbedPane = new JTabbedPane();
		m_jpSpielerDetailPanel = new SpielerDetailPanel();

		JScrollPane scrollPane = new JScrollPane(m_jpSpielerDetailPanel);
		scrollPane.getVerticalScrollBar().setBlockIncrement(100);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		tabbedPane.addTab(de.hattrickorganizer.model.HOVerwaltung.instance()
				.getLanguageString("SpielerDetails"), scrollPane);

		m_jpSpielerTrainingsSimulatorPanel = new SpielerTrainingsSimulatorPanel();
		scrollPane = new JScrollPane(m_jpSpielerTrainingsSimulatorPanel);
		scrollPane.getVerticalScrollBar().setBlockIncrement(100);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		tabbedPane.addTab(de.hattrickorganizer.model.HOVerwaltung.instance()
				.getLanguageString("Skilltester"), scrollPane);

		return tabbedPane;
	}

	private Component initSpielerHistory() {
		final JPanel panel = new de.hattrickorganizer.gui.templates.ImagePanel();
		panel.setLayout(new BorderLayout());
		m_jpSpielerTrainingsVergleichsPanel = new SpielerTrainingsVergleichsPanel();

		final JScrollPane scrollPane = new JScrollPane(
				m_jpSpielerTrainingsVergleichsPanel);
		scrollPane.getVerticalScrollBar().setBlockIncrement(100);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		panel.add(m_jpSpielerTrainingsVergleichsPanel, BorderLayout.CENTER);
		panel.add(new JScrollPane(new RemoveGruppenPanel(
				m_jtSpielerUebersichtTable)), BorderLayout.NORTH);
		return panel;
	}

	private Component initSpielerTabelle() {
		final JPanel panel = new JPanel(new BorderLayout());

		// table with the player's details
		m_jtSpielerUebersichtTable = new PlayerOverviewTable();

		// table with the player's name
		m_jtSpielerUebersichtTableName = new SpielerUebersichtNamenTable(
				m_jtSpielerUebersichtTable.getSorter());

		JScrollPane scrollpane = new JScrollPane(m_jtSpielerUebersichtTableName);
		scrollpane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setPreferredSize(new Dimension(170, 100));

		JScrollPane scrollpane2 = new JScrollPane(m_jtSpielerUebersichtTable);
		scrollpane2.getViewport().setScrollMode(
				JViewport.BACKINGSTORE_SCROLL_MODE);
		scrollpane2
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		final JScrollBar bar = scrollpane.getVerticalScrollBar();
		final JScrollBar bar2 = scrollpane2.getVerticalScrollBar();
		// setVisibile(false) does not have an effect, so we set the size to
		// false. We can't disable the scrollbar with VERTICAL_SCROLLBAR_NEVER
		// because this will disable mouse wheel scrolling.
		bar.setPreferredSize(new Dimension(0, 0));

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

	private void selectRow(JTable table, int row) {
		if (row > -1) {
			table.setRowSelectionInterval(row, row);
		} else {
			table.clearSelection();
		}
	}

	/**
	 * Adds ListSelectionListener which keep the row selection of the table with
	 * the players name and the table with the players details in sync.
	 */
	private void addTableSelectionListeners() {
		m_jtSpielerUebersichtTable.getSelectionModel()
				.addListSelectionListener(new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent e) {
						if (!e.getValueIsAdjusting()) {
							selectRow(m_jtSpielerUebersichtTableName,
									m_jtSpielerUebersichtTable.getSelectedRow());
						}
					}
				});

		m_jtSpielerUebersichtTableName.getSelectionModel()
				.addListSelectionListener(new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent e) {
						if (!e.getValueIsAdjusting()) {
							int row = m_jtSpielerUebersichtTableName
									.getSelectedRow();
							selectRow(m_jtSpielerUebersichtTable, row);
							if (row > -1) {
								Spieler spieler = m_jtSpielerUebersichtTable
										.getSorter().getSpieler(row);

								if (spieler != null) {
									HOMainFrame.instance().setActualSpieler(
											spieler.getSpielerID());
								}
							}
						}
					}
				});
	}
}
