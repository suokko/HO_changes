// %653086895:hoplugins.teamplanner.ui%
package hoplugins.teamplanner.ui;

import hoplugins.teamplanner.vo.HTWeek;

import java.util.Comparator;

import javax.swing.table.TableModel;


// Referenced classes of package hoplugins.teamPlanner.ui:
//			BasicSorter
public class FinancesTableSorter extends BasicSorter {
    //~ Constructors -------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -248682705140932131L;

	/**
     * Creates a new FinancesTableSorter object.
     *
     * @param model Missing Constructuor Parameter Documentation
     */
    public FinancesTableSorter(TableModel model) {
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
        if (column == 1) {
            return new HTWeekComparator();
        } else {
            return null;
        }
    }

    //~ Inner Classes ------------------------------------------------------------------------------

    /**
     * Missing Class Documentation
     *
     * @author Draghetto
     */
    private class HTWeekComparator implements Comparator {
        //~ Methods --------------------------------------------------------------------------------

        /**
         * Missing Method Documentation
         *
         * @param arg0 Missing Method Parameter Documentation
         * @param arg1 Missing Method Parameter Documentation
         *
         * @return Missing Return Method Documentation
         */
        public int compare(Object arg0, Object arg1) {
            HTWeek d1 = (HTWeek) arg0;
            HTWeek d2 = (HTWeek) arg1;

            return d1.compareTo(d2);
        }

        /**
         * Missing Method Documentation
         *
         * @param arg0 Missing Method Parameter Documentation
         *
         * @return Missing Return Method Documentation
         */
        @Override
		public boolean equals(Object arg0) {
            return false;
        }
    }
}
