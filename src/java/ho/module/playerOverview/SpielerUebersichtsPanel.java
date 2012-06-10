// %1605218128:de.hattrickorganizer.gui.playeroverview%
package ho.module.playerOverview;

import ho.core.gui.HOMainFrame;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.model.player.Spieler;

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
 * Bietet Übersicht über alle Spieler (main class of the package)
 */
public class SpielerUebersichtsPanel extends ImagePanel {

	private static final long serialVersionUID = -5795792661614081193L;
	private JSplitPane horizontalRightSplitPane;
	private JSplitPane verticalSplitPane;
	private SpielerDetailPanel spielerDetailPanel;
	private SpielerTrainingsSimulatorPanel spielerTrainingsSimulatorPanel;
	private SpielerTrainingsVergleichsPanel spielerTrainingsVergleichsPanel;
	private SpielerUebersichtNamenTable spielerUebersichtTableName;
	private PlayerOverviewTable spielerUebersichtTable;

	/**
	 * Creates a new SpielerUebersichtsPanel object. (Players view panel)
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
	public void setPlayer(Spieler spieler) {
		spielerUebersichtTableName.setSpieler(spieler.getSpielerID());
		spielerUebersichtTable.setSpieler(spieler.getSpielerID());
		spielerDetailPanel.setSpieler(spieler);
		spielerTrainingsSimulatorPanel.setSpieler(spieler);
	}

	/**
	 * Returns Width of the best position column
	 */
	public final int getBestPosWidth() {
		return spielerUebersichtTable.getBestPosWidth();
	}

	/**
	 * Returns the current Divider Locations so they can be stored.
	 */
	public final int[] getDividerLocations() {
		int[] locations = new int[3];
		locations[0] = 0;
		locations[1] = horizontalRightSplitPane.getDividerLocation();
		locations[2] = verticalSplitPane.getDividerLocation();
		return locations;
	}

	public final void saveColumnOrder() {
		spielerUebersichtTable.saveColumnOrder();
	}

	// ----------------------Refresh--

	/**
	 * Refresh, wenn ein Spieler in der Aufstellung geändert wird (Google
	 * translate) Refresh, if a player is changed in the statement
	 */
	public final void refresh() {
		spielerDetailPanel.refresh();
		spielerUebersichtTable.refresh();
	}

	/**
	 * Erneuert alle Spalten, die bei von einem Vergleich betroffen sind (Google
	 * translate) Renewed all the columns that are affected by a comparison with
	 */
	public final void refreshHRFVergleich() {
		spielerUebersichtTable.refreshHRFVergleich();

		Spieler spieler = spielerUebersichtTable.getSorter().getSpieler(
				spielerUebersichtTable.getSelectedRow());
		spielerDetailPanel.setSpieler(spieler);
	}

	/**
	 * Refeshed die Tabelle hier und im Aufstellungspanel, wenn die Gruppen/Info
	 * geändert wurde (Google translate) Refeshed the table here and in the
	 * installation panel, where the groups / info was changed
	 */
	public final void update() {
		refresh();
		HOMainFrame.instance().getAufstellungsPanel().refresh();
	}

	// ----------init-----------------------------------------------
	private void initComponents() {
		setLayout(new BorderLayout());

		Component tabelle = initSpielerTabelle();
		horizontalRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false);
		horizontalRightSplitPane.setLeftComponent(initSpielerDetail());
		horizontalRightSplitPane.setRightComponent(initSpielerHistory());

		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false);
		verticalSplitPane.setLeftComponent(tabelle);
		verticalSplitPane.setRightComponent(horizontalRightSplitPane);

		horizontalRightSplitPane
				.setDividerLocation(UserParameter.instance().spielerUebersichtsPanel_horizontalRightSplitPane);
		verticalSplitPane
				.setDividerLocation(UserParameter.instance().spielerUebersichtsPanel_verticalSplitPane);

		add(verticalSplitPane, BorderLayout.CENTER);
	}

	/*
	 * Initialise the players details
	 */
	private Component initSpielerDetail() {
		JTabbedPane tabbedPane = new JTabbedPane();
		spielerDetailPanel = new SpielerDetailPanel();

		JScrollPane scrollPane = new JScrollPane(spielerDetailPanel);
		scrollPane.getVerticalScrollBar().setBlockIncrement(100);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("SpielerDetails"), scrollPane);

		spielerTrainingsSimulatorPanel = new SpielerTrainingsSimulatorPanel();
		scrollPane = new JScrollPane(spielerTrainingsSimulatorPanel);
		scrollPane.getVerticalScrollBar().setBlockIncrement(100);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Skilltester"), scrollPane);

		return tabbedPane;
	}

	/*
	 * Initialise the players history
	 */
	private Component initSpielerHistory() {
		JPanel panel = new ImagePanel();
		panel.setLayout(new BorderLayout());
		spielerTrainingsVergleichsPanel = new SpielerTrainingsVergleichsPanel();

		final JScrollPane scrollPane = new JScrollPane(spielerTrainingsVergleichsPanel);
		scrollPane.getVerticalScrollBar().setBlockIncrement(100);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		panel.add(spielerTrainingsVergleichsPanel, BorderLayout.CENTER);
		panel.add(new JScrollPane(new RemoveGruppenPanel(spielerUebersichtTable)),
				BorderLayout.NORTH);
		return panel;
	}

	/*
	 * Initialise the players table
	 */
	private Component initSpielerTabelle() {

		// table with the player's details
		spielerUebersichtTable = new PlayerOverviewTable();

		// table with the player's name
		spielerUebersichtTableName = new SpielerUebersichtNamenTable(
				spielerUebersichtTable.getSorter());

		JScrollPane scrollpane = new JScrollPane(spielerUebersichtTableName);
		scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setPreferredSize(new Dimension(170, 100));

		JScrollPane scrollpane2 = new JScrollPane(spielerUebersichtTable);
		scrollpane2.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		scrollpane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		final JScrollBar bar = scrollpane.getVerticalScrollBar();
		final JScrollBar bar2 = scrollpane2.getVerticalScrollBar();
		// setVisibile(false) does not have an effect, so we set the size to
		// false. We can't disable the scrollbar with VERTICAL_SCROLLBAR_NEVER
		// because this will disable mouse wheel scrolling.
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

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false);
		splitPane.setLeftComponent(scrollpane);
		splitPane.setRightComponent(scrollpane2);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(UserParameter.instance().playerTablePanel_horizontalSplitPane);

		return splitPane;
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
		spielerUebersichtTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						selectRow(spielerUebersichtTableName,
								spielerUebersichtTable.getSelectedRow());
					}
				});

		spielerUebersichtTableName.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						int row = spielerUebersichtTableName.getSelectedRow();
						selectRow(spielerUebersichtTable, row);
						if (row > -1) {
							Spieler spieler = spielerUebersichtTable.getSorter().getSpieler(row);
							if (spieler != null) {
								HOMainFrame.instance().setActualSpieler(spieler);
							}
						}
					}
				});
	}
}
