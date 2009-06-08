// %3665758638:hoplugins.teamplanner.ui%
package hoplugins.teamplanner.ui;

import hoplugins.teamplanner.ui.model.FinancesTableModel;

import java.awt.BorderLayout;

import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


// Referenced classes of package hoplugins.teamPlanner.ui:
//			BasicSorter

/**
 * DOCUMENT ME!
 *
 * @deprecated
 */
@Deprecated
public class FuturePane extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 7121742552351716621L;
	private FinancesTableModel futureTableModel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FuturePane object.
     */
    public FuturePane() {
        futureTableModel = null;
        futureTableModel = new FinancesTableModel(new Vector());

        BasicSorter sorter = new BasicSorter(futureTableModel);
        JTable historyTable = new JTable(sorter);

        sorter.setTableHeader(historyTable.getTableHeader());
        sorter.setSortingStatus(0, -1);
        setLayout(new BorderLayout());
        add(new JScrollPane(historyTable), "Center");
        refresh();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     */
    public void refresh() {
    }
}
