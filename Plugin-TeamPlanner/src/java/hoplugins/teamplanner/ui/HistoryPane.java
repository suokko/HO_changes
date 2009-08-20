// %1740020060:hoplugins.teamplanner.ui%
package hoplugins.teamplanner.ui;

import hoplugins.commons.ui.sorter.AbstractTableSorter;
import hoplugins.teamplanner.dao.FinanzenDAO;
import hoplugins.teamplanner.ui.model.FinancesTableModel;
import hoplugins.teamplanner.vo.FinancesOfWeek;
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
@Deprecated
public class HistoryPane extends JPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -3391672999457732272L;
	private FinancesTableModel historyTableModel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new HistoryPane object.
     */
    public HistoryPane() {
        historyTableModel = null;
        historyTableModel = new FinancesTableModel(new Vector<FinancesOfWeek>());

        BasicSorter sorter = new BasicSorter(historyTableModel);
        JTable historyTable = new JTable(sorter);

        sorter.setTableHeader(historyTable.getTableHeader());
        sorter.setSortingStatus(1, -1);


        sorter.setColumnComparator(HTWeek.class, AbstractTableSorter.COMPARABLE_COMPARATOR);
        setLayout(new BorderLayout());
        add(new JScrollPane(historyTable), "Center");
        refresh();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     */
    public void refresh() {
        Vector<FinancesOfWeek> values = FinanzenDAO.getFinancesHistory(16);

        historyTableModel.refresh(values);
    }
}
