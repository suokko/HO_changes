// %1126721046119:hoplugins.commons.ui.sorter%
/*
 * Created on 7-apr-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ho.modul.transfer.ui.sorter;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import java.awt.Component;

/**
 * DOCUMENT ME!
 *
 * @author
 */
class SortableHeaderRenderer implements TableCellRenderer {
    /** TODO Missing Parameter Documentation */
    private final AbstractTableSorter sorter;
    private TableCellRenderer tableCellRenderer;

    /**
     * Creates a new SortableHeaderRenderer object.
     *
     * @param sorter
     * @param tableCellRenderer
     */
    public SortableHeaderRenderer(AbstractTableSorter sorter,
        TableCellRenderer tableCellRenderer) {
        this.tableCellRenderer = tableCellRenderer;
        this.sorter = sorter;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public TableCellRenderer getTableCellRenderer() {
        return tableCellRenderer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param table
     * @param value
     * @param isSelected
     * @param hasFocus
     * @param row
     * @param column
     *
     * @return
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = tableCellRenderer.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, column);

        if (c instanceof JLabel) {
            JLabel l = (JLabel) c;

            l.setHorizontalTextPosition(SwingConstants.LEFT);

            int modelColumn = table.convertColumnIndexToModel(column);

            l.setIcon(this.sorter.getHeaderRendererIcon(modelColumn,
                    l.getFont().getSize()));
        }

        return c;
    }
}
