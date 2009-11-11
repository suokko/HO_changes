// %1117664849218:hoplugins.conv%
package hoplugins.conv;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;


/**
 * DOCUMENT ME!
 *
 * @author Thorsten Dietz
 */
final class SelectObjectTableModel extends DefaultTableModel {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SelectObjectTableModel object.
     *
     * @param daten TODO Missing Constructuor Parameter Documentation
     * @param headers TODO Missing Constructuor Parameter Documentation
     */
    protected SelectObjectTableModel(Object[][] daten, Object[] headers) {
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
        return (col == 0) ? true : false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param col TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Class getColumnClass(int col) {
        Vector vector = (Vector) dataVector.elementAt(0);
        return (vector != null) ? vector.elementAt(col).getClass() : null;
    }
}
