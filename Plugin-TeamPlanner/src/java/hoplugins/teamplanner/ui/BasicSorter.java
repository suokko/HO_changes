// %3977876415:hoplugins.teamplanner.ui%
package hoplugins.teamplanner.ui;

import hoplugins.commons.ui.DefaultTableSorter;

import java.util.Comparator;

import javax.swing.table.TableModel;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class BasicSorter extends DefaultTableSorter {
    //~ Constructors -------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -6727663365988082402L;

	/**
     * Creates a new BasicSorter object.
     *
     * @param model Missing Constructuor Parameter Documentation
     */
    public BasicSorter(TableModel model) {
        super(model);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param column Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    @Override
	public Comparator getCustomComparator(int column) {
        return null;
    }
}
