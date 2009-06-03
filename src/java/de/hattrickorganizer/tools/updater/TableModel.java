// %390576536:de.hattrickorganizer.tools.updater%
package de.hattrickorganizer.tools.updater;

import javax.swing.table.DefaultTableModel;


/**
 * TableModel for the UpdaterDialogs
 *
 * @author Thorsten Dietz
 *
 * @since 1.35
 */
public final class TableModel extends DefaultTableModel {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TableModel object.
     *
     * @param daten TODO Missing Constructuor Parameter Documentation
     * @param headers TODO Missing Constructuor Parameter Documentation
     */
    public TableModel(Object[][] daten, Object[] headers) {
        super(daten, headers);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param col TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isCellEditable(int row, int col) {
        return ((col == 0) || (col == 4)) ? true : false;
    }
}
