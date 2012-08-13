// %733429640:de.hattrickorganizer.gui.playeroverview%
package ho.module.playerOverview;

import ho.core.gui.RefreshManager;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.renderer.HODefaultTableCellRenderer;
import ho.core.gui.comp.table.TableSorter;
import ho.core.gui.model.ReduzedTableModel;
import ho.core.model.player.Spieler;

import javax.swing.JTable;

/**
 * TODO Missing Class Documentation
 * 
 * @author TODO Author Name
 */
public class SpielerUebersichtNamenTable extends JTable implements Refreshable, PlayerTable {

	private static final long serialVersionUID = -7686660400379157142L;
	private TableSorter tableSorter;

	/**
	 * Nur Namensspalte anzeigen
	 * 
	 */
	public SpielerUebersichtNamenTable(TableSorter model) {
		super();
		tableSorter = model;
		model.addMouseListenerToHeaderInTable(this);
		model.addTableModelListener(this);
		setSelectionMode(0);
		setModel(new ReduzedTableModel(model, 0));
		setDefaultRenderer(java.lang.Object.class, new HODefaultTableCellRenderer());
		RefreshManager.instance().registerRefreshable(this);
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------

	@Override
	public Spieler getSpieler(int row) {
		return this.tableSorter.getSpieler(row);
	}

	@Override
	public final void setSpieler(int spielerid) {
		final int index = tableSorter.getRow4Spieler(spielerid);

		if (index >= 0) {
			this.setRowSelectionInterval(index, index);
		}
	}

	@Override
	public final void reInit() {
		initModelNamen();

		repaint();
	}

	@Override
	public final void refresh() {
		// Datenanpassung wird vom SpielerUbersichtsTable erledigt
		repaint();
	}

	/**
	 * Initialisiert das Model für die Namen
	 */
	private void initModelNamen() {
		setSelectionMode(0);
		setRowSelectionAllowed(true);
		getColumnModel().getColumn(0).setMinWidth(167);
	}
}
