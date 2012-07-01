// %2655100108:de.hattrickorganizer.gui.playeranalysis%
package ho.module.playeranalysis;

import ho.core.db.DBManager;
import ho.core.gui.comp.renderer.HODefaultTableCellRenderer;
import ho.core.gui.comp.table.TableSorter;
import ho.core.gui.comp.table.ToolTipHeader;
import ho.core.gui.model.SpielerPositionTableModel;
import ho.core.util.Helper;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 * TODO Missing Class Documentation
 * 
 * @author TODO Author Name
 */
final class SpielerPositionTable extends JTable {

	private static final long serialVersionUID = 6625601251606134493L;
	private SpielerPositionTableModel m_clTableModel;
	private TableSorter m_clTableSorter;
	private int m_iSpielerId = -1;

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Creates a new SpielerPositionTable object.
	 * 
	 * @param spielerid
	 *            TODO Missing Constructuor Parameter Documentation
	 */
	protected SpielerPositionTable(int spielerid) {
		super();
		m_iSpielerId = spielerid;
		initModel();
		setDefaultRenderer(Object.class, new HODefaultTableCellRenderer());
		setSelectionBackground(HODefaultTableCellRenderer.SELECTION_BG);
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 * 
	 * @return TODO Missing Return Method Documentation
	 */
	public TableSorter getSorter() {
		return m_clTableSorter;
	}

	// ----------------Listener-------------------------------------------
	public void refresh(int spielerid) {
		m_iSpielerId = spielerid;
		initModel();
		repaint();
	}

	/**
	 * Initialisiert das Model
	 */
	private void initModel() {
		setOpaque(false);

		if (m_clTableModel == null) {
			Vector<float[]> bewertungen;
			if (m_iSpielerId != -1) {
				bewertungen = DBManager.instance().getAlleBewertungen(m_iSpielerId);
			} else {
				bewertungen = new Vector<float[]>();
			}
			m_clTableModel = new SpielerPositionTableModel(bewertungen);
			m_clTableSorter = new TableSorter(m_clTableModel, -1, -1);

			ToolTipHeader header = new ToolTipHeader(getColumnModel());
			header.setToolTipStrings(m_clTableModel.m_sToolTipStrings);
			header.setToolTipText("");
			setTableHeader(header);

			setModel(m_clTableSorter);

			final TableColumnModel tableColumnModel = getColumnModel();

			for (int i = 0; i < 4; i++) {
				tableColumnModel.getColumn(i).setIdentifier(new Integer(i));
			}

			m_clTableSorter.addMouseListenerToHeaderInTable(this);
		} else {
			// Werte neu setzen
			m_clTableModel.setValues(ho.core.db.DBManager.instance().getAlleBewertungen(
					m_iSpielerId));
			m_clTableSorter.reallocateIndexes();
		}

		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		final TableColumnModel tableColumnModel = getColumnModel();
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(0)))
				.setPreferredWidth(Helper.calcCellWidth(200));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(0)))
				.setMinWidth(Helper.calcCellWidth(20));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(1)))
				.setMinWidth(Helper.calcCellWidth(20));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(1)))
				.setPreferredWidth(Helper.calcCellWidth(180));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(2)))
				.setMinWidth(Helper.calcCellWidth(20));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(2)))
				.setPreferredWidth(Helper.calcCellWidth(180));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(3)))
				.setMinWidth(Helper.calcCellWidth(20));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(3)))
				.setPreferredWidth(Helper.calcCellWidth(180));
		setSelectionMode(0);
		setRowSelectionAllowed(true);

		m_clTableSorter.initsort();
	}
}
