// %1896649635:de.hattrickorganizer.gui.statistic%
package de.hattrickorganizer.gui.statistic;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.model.ArenaStatistikTableModel;
import de.hattrickorganizer.gui.model.SpielerTableRenderer;
import de.hattrickorganizer.gui.utils.TableSorter;
import de.hattrickorganizer.gui.utils.ToolTipHeader;
import de.hattrickorganizer.tools.Helper;


/**
 * Table for arena statistics.
 */
public class ArenaStatistikTable extends JTable {

	private static final long serialVersionUID = -6319111452810917050L;

    //~ Instance fields ----------------------------------------------------------------------------

	private ArenaStatistikTableModel m_clTableModel;
    private TableSorter m_clTableSorter;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ArenaStatistikTable object.
     *
     * @param matchtyp matches typ (e.g. own league matches only)
     */
    public ArenaStatistikTable(int matchtyp) {
        super();
        setDefaultRenderer(Object.class, new SpielerTableRenderer());
        setSelectionBackground(SpielerTableRenderer.SELECTION_BG);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Get the table sorter.
     */
    public final TableSorter getSorter() {
        return m_clTableSorter;
    }

    /**
     * Markiert ein Match, wenn es in der Tabelle vorhanden ist, sonst wird die Selektion gelöscht
     */
    public final void markiereMatch(int matchid) {
        final int row = m_clTableSorter.getRow4Match(matchid);

        if (row > -1) {
            setRowSelectionInterval(row, row);
        } else {
            clearSelection();
        }
    }

    //----------------Refresh-------------------------------------------

    /**
     * Refresh all data.
     *
     * @param matchtypen matches typ (e.g. own league matches only)
     */
    public final void refresh(int matchtypen) {
        initModel(matchtypen);
        repaint();
    }

    /**
     * Initialisiert das Model
     *
     * @param matchtyp matches typ (e.g. own league matches only)
     */
    private void initModel(int matchtyp) {
        setOpaque(false);

        m_clTableModel = DBZugriff.instance().getArenaStatistikModel(matchtyp);
        m_clTableSorter = new TableSorter(m_clTableModel, 5, -1);

        final ToolTipHeader header = new ToolTipHeader(getColumnModel());
        header.setToolTipStrings(m_clTableModel.m_sToolTipStrings);
        header.setToolTipText("");
        setTableHeader(header);

        setModel(m_clTableSorter);

        final TableColumnModel tableColumnModel = getColumnModel();

        for (int i = 0; i < 14; i++) {
            tableColumnModel.getColumn(i).setIdentifier(new Integer(i));
        }

        m_clTableSorter.addMouseListenerToHeaderInTable(this);

        setAutoResizeMode(AUTO_RESIZE_OFF);
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(0))).setPreferredWidth(Helper.calcCellWidth(75));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(0))).setMinWidth(Helper.calcCellWidth(70));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(1))).setPreferredWidth(Helper.calcCellWidth(20));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(1))).setMinWidth(Helper.calcCellWidth(20));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(2))).setMinWidth(Helper.calcCellWidth(55));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(2))).setPreferredWidth(Helper.calcCellWidth(120));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(3))).setMinWidth(Helper.calcCellWidth(55));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(3))).setPreferredWidth(Helper.calcCellWidth(60));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(4))).setPreferredWidth(Helper.calcCellWidth(30));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(5))).setPreferredWidth(Helper.calcCellWidth(55));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(6))).setPreferredWidth(Helper.calcCellWidth(150));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(7))).setPreferredWidth(Helper.calcCellWidth(150));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(8))).setPreferredWidth(Helper.calcCellWidth(110));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(9))).setPreferredWidth(Helper.calcCellWidth(110));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(10))).setPreferredWidth(Helper.calcCellWidth(90));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(11))).setPreferredWidth(Helper.calcCellWidth(90));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(12))).setPreferredWidth(Helper.calcCellWidth(90));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(Integer.valueOf(13))).setPreferredWidth(Helper.calcCellWidth(90));

        setSelectionMode(0);
        setRowSelectionAllowed(true);

        m_clTableSorter.initsort();
    }
}
