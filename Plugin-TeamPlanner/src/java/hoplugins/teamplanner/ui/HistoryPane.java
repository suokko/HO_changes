// %1740020060:hoplugins.teamplanner.ui%
package hoplugins.teamplanner.ui;

import hoplugins.teamplanner.dao.FinanzenDAO;
import hoplugins.teamplanner.ui.model.FinancesTableModel;
import hoplugins.teamplanner.vo.HTWeek;

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
public class HistoryPane extends JPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    //~ Instance fields ----------------------------------------------------------------------------

    private FinancesTableModel historyTableModel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new HistoryPane object.
     */
    public HistoryPane() {
        historyTableModel = null;
        historyTableModel = new FinancesTableModel(new Vector());

        BasicSorter sorter = new BasicSorter(historyTableModel);
        JTable historyTable = new JTable(sorter);

        sorter.setTableHeader(historyTable.getTableHeader());
        sorter.setSortingStatus(1, -1);


        sorter.setColumnComparator(HTWeek.class, BasicSorter.COMPARABLE_COMAPRATOR);
        setLayout(new BorderLayout());
        add(new JScrollPane(historyTable), "Center");
        refresh();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     */
    public void refresh() {
        Vector values = FinanzenDAO.getFinancesHistory(16);

        historyTableModel.refresh(values);
    }
}
