// %1896649635:de.hattrickorganizer.gui.statistic%
package de.hattrickorganizer.gui.statistic;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import de.hattrickorganizer.gui.model.ArenaStatistikTableModel;
import de.hattrickorganizer.gui.utils.TableSorter;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class ArenaStatistikTable extends JTable {
    //~ Instance fields ----------------------------------------------------------------------------

    private ArenaStatistikTableModel m_clTableModel;
    private TableSorter m_clTableSorter;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ArenaStatistikTable object.
     *
     * @param matchtyp TODO Missing Constructuor Parameter Documentation
     */
    public ArenaStatistikTable(int matchtyp) {
        super();

        //initModel( matchtyp );
        setDefaultRenderer(java.lang.Object.class,
                           new de.hattrickorganizer.gui.model.SpielerTableRenderer());
        setSelectionBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
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

    /**
     * Markiert ein Match, wenn es in der Tabelle vorhanden ist, sonst wird die Selektion gelöscht
     *
     * @param matchid TODO Missing Constructuor Parameter Documentation
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
     * DOCUMENT ME!
     *
     * @param matchtypen Die Typen der Matches ( Im SpielePanel definiert )
     */
    public final void refresh(int matchtypen) {
        initModel(matchtypen);

        repaint();
    }

    /**
     * Initialisiert das Model
     *
     * @param matchtyp Die Typen der Matches ( Im SpielePanel definiert )
     */
    private void initModel(int matchtyp) {
        setOpaque(false);

        m_clTableModel = de.hattrickorganizer.database.DBZugriff.instance().getArenaStatistikModel(matchtyp);
        m_clTableSorter = new TableSorter(m_clTableModel, 5, -1);

        final de.hattrickorganizer.gui.utils.ToolTipHeader header = new de.hattrickorganizer.gui.utils.ToolTipHeader(getColumnModel());
        header.setToolTipStrings(m_clTableModel.m_sToolTipStrings);
        header.setToolTipText("");
        setTableHeader(header);

        setModel(m_clTableSorter);

        final TableColumnModel tableColumnModel = getColumnModel();

        for (int i = 0; i < 14; i++) {
            tableColumnModel.getColumn(i).setIdentifier(new Integer(i));
        }

        m_clTableSorter.addMouseListenerToHeaderInTable(this);

        setAutoResizeMode(this.AUTO_RESIZE_OFF);
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(0)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(70));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(0))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(70));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(1)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(20));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(1))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(20));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(2))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(55));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(2)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(120));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(3))).setMinWidth(de.hattrickorganizer.tools.Helper
                                                                                                .calcCellWidth(55));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(3)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(60));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(4)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(30));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(5)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(55));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(6)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(150));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(7)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(150));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(8)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(110));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(9)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(110));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(10)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(90));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(11)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(90));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(12)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(90));
        tableColumnModel.getColumn(tableColumnModel.getColumnIndex(new Integer(13)))
                        .setPreferredWidth(de.hattrickorganizer.tools.Helper.calcCellWidth(90));

        setSelectionMode(0);
        setRowSelectionAllowed(true);

        m_clTableSorter.initsort();
    }
}
