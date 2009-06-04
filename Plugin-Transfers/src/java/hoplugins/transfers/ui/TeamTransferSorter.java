// %1126721330698:hoplugins.transfers.ui%
package hoplugins.transfers.ui;

import hoplugins.commons.ui.DefaultTableSorter;

import java.util.Comparator;

import javax.swing.table.TableModel;


/**
 * Sorter for the team transfer table.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class TeamTransferSorter extends DefaultTableSorter {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Create a TransferTypeSorter
     *
     * @param model Table model to sort.
     */
    public TeamTransferSorter(TableModel model) {
        super(model);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Method that define Custom Comparator
     *
     * @param column column to sort
     *
     * @return A custom comparator if any, null if not specified
     */
    public final Comparator getCustomComparator(int column) {
        if ((column == 1) || (column == 2) || (column >= 6)) {
            return new Comparator() {
                    public boolean equals(Object arg0) {
                        return false;
                    }

                    public int compare(Object arg0, Object arg1) {
                        final Integer d1 = (Integer) arg0;
                        final Integer d2 = (Integer) arg1;
                        return d1.compareTo(d2);
                    }
                };
        }

        return null;
    }
}
