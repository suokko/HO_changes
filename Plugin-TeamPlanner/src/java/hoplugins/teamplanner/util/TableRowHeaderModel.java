// %3910391482:hoplugins.teamplanner.util%
package hoplugins.teamplanner.util;

import javax.swing.AbstractListModel;
import javax.swing.JTable;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class TableRowHeaderModel extends AbstractListModel {
    //~ Instance fields ----------------------------------------------------------------------------

    private JTable table;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TableRowHeaderModel object.
     *
     * @param table Missing Constructuor Parameter Documentation
     */
    public TableRowHeaderModel(JTable table) {
        this.table = table;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param index Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public Object getElementAt(int index) {
        return null;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getSize() {
        return table.getRowCount();
    }
}
