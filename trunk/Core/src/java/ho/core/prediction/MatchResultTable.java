// %1378604643:de.hattrickorganizer.gui.matchprediction%
package ho.core.prediction;

import ho.core.gui.comp.table.TableSorter;
import ho.core.prediction.engine.MatchResult;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;


class MatchResultTable extends JTable {
	
	private static final long serialVersionUID = 8245352979596154576L;
	
	private MatchResultTableModel m_clTableModel;
	private TableSorter m_clTableSorter;

	MatchResultTable(MatchResult matchresults,boolean isHome) {
		super();
		initModel(matchresults,isHome);
		setDefaultRenderer(java.lang.Object.class, new ho.core.gui.comp.renderer.HODefaultTableCellRenderer());
		setSelectionBackground(ho.core.gui.comp.renderer.HODefaultTableCellRenderer.SELECTION_BG);
		setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	}

	public final void refresh(MatchResult matchresults,boolean isHome) {
		initModel(matchresults,isHome);

		repaint();
	}

	private void initModel(MatchResult matchresults,boolean isHome) {
		setOpaque(false);

		if (m_clTableModel == null) {
			m_clTableModel = new MatchResultTableModel(matchresults,isHome);
			m_clTableSorter = new TableSorter(m_clTableModel, 1, -1);

			final ho.core.gui.comp.table.ToolTipHeader header = new ho.core.gui.comp.table.ToolTipHeader(getColumnModel());
			header.setToolTipStrings(MatchResultTableModel.columnNames);
			header.setToolTipText("");
			setTableHeader(header);

			setModel(m_clTableSorter);

			final TableColumnModel tableColumnModel = getColumnModel();

			for (int i = 0; i < 3; i++) {
				tableColumnModel.getColumn(i).setIdentifier(new Integer(i));
			}

			m_clTableSorter.addMouseListenerToHeaderInTable(this);
		} else {
			//Werte neu setzen
			m_clTableModel.setValues(matchresults);
			m_clTableSorter.reallocateIndexes();
		}

		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		final TableColumnModel tableColumnModel = getColumnModel();
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(0))).setPreferredWidth(ho.core.util.Helper.calcCellWidth(100));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(0))).setMaxWidth(ho.core.util.Helper.calcCellWidth(100));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(1))).setPreferredWidth(ho.core.util.Helper.calcCellWidth(250));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(1))).setMaxWidth(ho.core.util.Helper.calcCellWidth(250));		
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(2))).setMaxWidth(ho.core.util.Helper.calcCellWidth(100));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(2))).setPreferredWidth(ho.core.util.Helper.calcCellWidth(100));

		setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		setRowSelectionAllowed(true);

		m_clTableSorter.initsort();
	}
}
