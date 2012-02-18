// %3267207196:de.hattrickorganizer.gui.matchprediction%
package de.hattrickorganizer.gui.matchprediction;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import plugins.IMatchResult;
import de.hattrickorganizer.gui.model.MatchScoreTableModel;
import de.hattrickorganizer.gui.utils.TableSorter;
import de.hattrickorganizer.tools.Helper;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MatchScoreTable extends JTable {
	
	private static final long serialVersionUID = -729565361708303943L;
	
	//~ Instance fields ----------------------------------------------------------------------------

	private MatchScoreTableModel m_clTableModel;
	private TableSorter m_clTableSorter;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new MatchPredictionSpieleTable object.
	 *
	 * @param vErgebnisse TODO Missing Constructuor Parameter Documentation
	 */
	public MatchScoreTable(IMatchResult mr,boolean isHome) {
		super();
		initModel(mr,isHome);
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
	public final void refresh(IMatchResult mr,boolean isHome) {
		initModel(mr,isHome);

		repaint();
	}

	/**
	 * Initialisiert das Model
	 *
	 * @param vErgebnisse Die Typen der Matches ( Im SpielePanel definiert )
	 */
	private void initModel(IMatchResult mr,boolean isHome) {
		setOpaque(false);

		if (m_clTableModel == null) {
			m_clTableModel = new MatchScoreTableModel(mr,isHome);
			m_clTableSorter = new TableSorter(m_clTableModel, 1, -1);

			final de.hattrickorganizer.gui.utils.ToolTipHeader header = new de.hattrickorganizer.gui.utils.ToolTipHeader(getColumnModel());
			header.setToolTipStrings(m_clTableModel.getColumnNames());
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
			m_clTableModel.setValues(mr);
			m_clTableSorter.reallocateIndexes();
		}

		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		final TableColumnModel tableColumnModel = getColumnModel();
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(0))).setMaxWidth(Helper.calcCellWidth(100));		
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(0))).setPreferredWidth(Helper.calcCellWidth(100));		
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(1))).setMaxWidth(Helper.calcCellWidth(200));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(1))).setPreferredWidth(Helper.calcCellWidth(200));
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(2))).setMaxWidth(Helper.calcCellWidth(200));		
		tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(2))).setPreferredWidth(Helper.calcCellWidth(200));

		setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		setRowSelectionAllowed(true);

		m_clTableSorter.initsort();
	}
}
