// %1378604643:de.hattrickorganizer.gui.matchprediction%
package de.hattrickorganizer.gui.matchprediction;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import plugins.IMatchResult;

import de.hattrickorganizer.gui.model.MatchResultTableModel;
import de.hattrickorganizer.gui.utils.TableSorter;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MatchResultTable extends JTable {
	
	private static final long serialVersionUID = 8245352979596154576L;
	
	//~ Instance fields ----------------------------------------------------------------------------

	private MatchResultTableModel m_clTableModel;
	private TableSorter m_clTableSorter;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new MatchPredictionSpieleTable object.
	 *
	 * @param vErgebnisse TODO Missing Constructuor Parameter Documentation
	 */
	public MatchResultTable(IMatchResult matchresults,boolean isHome) {
		super();
		initModel(matchresults,isHome);
		setDefaultRenderer(java.lang.Object.class, new ho.core.gui.comp.renderer.HODefaultTableCellRenderer());
		setSelectionBackground(ho.core.gui.comp.renderer.HODefaultTableCellRenderer.SELECTION_BG);
		setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public final TableSorter getSorter() {
		return m_clTableSorter;
	}

	//----------------Refresh-------------------------------------------

	/**
	 * DOCUMENT ME!
	 *
	 * @param vErgebnisse Die Typen der Matches ( Im SpielePanel definiert )
	 */
	public final void refresh(IMatchResult matchresults,boolean isHome) {
		initModel(matchresults,isHome);

		repaint();
	}

	/**
	 * Initialisiert das Model
	 *
	 * @param vErgebnisse Die Typen der Matches ( Im SpielePanel definiert )
	 */
	private void initModel(IMatchResult matchresults,boolean isHome) {
		setOpaque(false);

		if (m_clTableModel == null) {
			m_clTableModel = new MatchResultTableModel(matchresults,isHome);
			m_clTableSorter = new TableSorter(m_clTableModel, 1, -1);

			final de.hattrickorganizer.gui.utils.ToolTipHeader header = new de.hattrickorganizer.gui.utils.ToolTipHeader(getColumnModel());
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
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(0))).setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(100));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(0))).setMaxWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(100));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(1))).setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(250));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(1))).setMaxWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(250));		
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(2))).setMaxWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(100));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(2))).setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(100));

		setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		setRowSelectionAllowed(true);

		m_clTableSorter.initsort();
	}
}
