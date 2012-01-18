// %1126721046088:hoplugins.commons.ui.sorter%
/*
 * Created on 7-apr-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ho.module.transfer.ui.sorter;

import java.util.Iterator;

// Helper classes
class Row implements Comparable<Row> {
    /** TODO Missing Parameter Documentation */
    private final AbstractTableSorter sorter;
    private int modelIndex;

    /**
     * Creates a new Row object.
     *
     * @param sorter
     * @param index
     */
    public Row(AbstractTableSorter sorter, int index) {
        this.modelIndex = index;
        this.sorter = sorter;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public int getModelIndex() {
        return modelIndex;
    }

    /**
     * DOCUMENT ME!
     *
     * @param o
     *
     * @return
     */
    public int compareTo(Row o) {
        int row1 = modelIndex;
        int row2 = o.modelIndex;

        if (this.sorter.hasHeaderLine()) {
            if (row1 == 0) {
                return -1;
            }

            if (row2 == 0) {
                return 1;
            }
        }

        for (Iterator<Directive> it = this.sorter.getSortingColumns().iterator();
            it.hasNext();) {
            Directive directive = it.next();
            int column = directive.getColumn();
            Object o1 = this.sorter.tableModel.getValueAt(row1, column);
            Object o2 = this.sorter.tableModel.getValueAt(row2, column);

            int comparison = 0;

            // Define null less than everything, except null.
            if ((o1 == null) && (o2 == null)) {
                comparison = 0;
            }
            else if (o1 == null) {
                comparison = -1;
            }
            else if (o2 == null) {
                comparison = 1;
            }
            else {
                comparison = this.sorter.getComparator(column).compare(o1, o2);
            }

            if (comparison != 0) {
                return (directive.getDirection() == AbstractTableSorter.DESCENDING)
                ? (-comparison) : comparison;
            }
        }

        return 0;
    }
}
