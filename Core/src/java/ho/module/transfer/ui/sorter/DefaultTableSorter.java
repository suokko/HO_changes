// %764330590:hoplugins.commons.ui%
package ho.module.transfer.ui.sorter;

import java.util.Comparator;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;


/**
 * Default Table sorter
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class DefaultTableSorter extends AbstractTableSorter {
    //~ Constructors -------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -2329161467834277249L;

	/**
     * Creates a new DefaultTableSorter object.
     */
    public DefaultTableSorter() {
        super();
    }

    /**
     * Creates a new DefaultTableSorter object.
     *
     * @param tableModel
     */
    public DefaultTableSorter(TableModel tableModel) {
        super(tableModel);
    }

    /**
     * Creates a new DefaultTableSorter object.
     *
     * @param tableModel
     * @param tableHeader
     */
    public DefaultTableSorter(TableModel tableModel, JTableHeader tableHeader) {
        super(tableModel, tableHeader);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param column
     *
     * @return
     */
    @Override
	public Comparator getCustomComparator(int column) {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    @Override
	public boolean hasHeaderLine() {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    @Override
	public int minSortableColumn() {
        return 0;
    }
}
