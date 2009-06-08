// %2866794018:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/**
 * GroupableTableHeader
 *
 * @author Nobuo Tamemasa
 * @version 1.0 10/20/98
 */
public class GroupableTableHeader extends JTableHeader {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -3171206308970166009L;
	/** TODO Missing Parameter Documentation */
    /** TODO Missing Parameter Documentation */
    protected Vector columnGroups;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new GroupableTableHeader object.
     *
     * @param model TODO Missing Constructuor Parameter Documentation
     */
    public GroupableTableHeader(TableColumnModel model) {
        super(model);
        setUI(new GroupableTableHeaderUI());
        setReorderingAllowed(false);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param col TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Enumeration getColumnGroups(TableColumn col) {
        if (columnGroups == null) {
            return null;
        }

        final Enumeration enumi = columnGroups.elements();

        while (enumi.hasMoreElements()) {
            final ColumnGroup cGroup = (ColumnGroup) enumi.nextElement();
            final Vector v_ret = cGroup.getColumnGroups(col, new Vector());

            if (v_ret != null) {
                return v_ret.elements();
            }
        }

        return null;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void setColumnMargin() {
        if (columnGroups == null) {
            return;
        }

        final int columnMargin = getColumnModel().getColumnMargin();
        final Enumeration enumi = columnGroups.elements();

        while (enumi.hasMoreElements()) {
            final ColumnGroup cGroup = (ColumnGroup) enumi.nextElement();
            cGroup.setColumnMargin(columnMargin);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param b TODO Missing Method Parameter Documentation
     */
    @Override
	public final void setReorderingAllowed(boolean b) {
        reorderingAllowed = false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     */
    public final void addColumnGroup(ColumnGroup g) {
        if (columnGroups == null) {
            columnGroups = new Vector();
        }

        columnGroups.addElement(g);
    }
}
