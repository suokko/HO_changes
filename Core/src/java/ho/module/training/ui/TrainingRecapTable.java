// %1126721452104:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumnModel;


/**
 * A Table with frozen columns
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class TrainingRecapTable extends JScrollPane {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 7666144967819145973L;
	private JTable fixed;
    private JTable scroll;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingRecapTable object.
     *
     * @param main Table to show
     * @param fixedColumns number of fixed columns
     */
    public TrainingRecapTable(JTable main, int fixedColumns) {
        super(main);
        scroll = main;

        fixed = new JTable(scroll.getModel());
        fixed.setFocusable(false);
        fixed.setSelectionModel(scroll.getSelectionModel());
        fixed.getTableHeader().setReorderingAllowed(false);
        fixed.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); //$NON-NLS-1$
        scroll.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); //$NON-NLS-1$

        //  Remove the fixed columns from the main table
        for (int i = 0; i < fixedColumns; i++) {
            TableColumnModel columnModel = scroll.getColumnModel();

            columnModel.removeColumn(columnModel.getColumn(0));
        }

        scroll.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fixed.setSelectionModel(scroll.getSelectionModel());

        //  Remove the non-fixed columns from the fixed table
        while (fixed.getColumnCount() > fixedColumns) {
            TableColumnModel columnModel = fixed.getColumnModel();

            columnModel.removeColumn(columnModel.getColumn(fixedColumns));
        }

        fixed.getColumnModel().getColumn(0).setMaxWidth(120);
        fixed.getColumnModel().getColumn(0).setMinWidth(120);
        fixed.getColumnModel().getColumn(0).setWidth(120);

        //  Add the fixed table to the scroll pane
        fixed.setPreferredScrollableViewportSize(fixed.getPreferredSize());
        setRowHeaderView(fixed);
        setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, fixed.getTableHeader());
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the Locked LeftTable
     *
     * @return Jtable
     */
    public JTable getLockedTable() {
        return fixed;
    }

    /**
     * Returns the Scrollable RightTable
     *
     * @return Jtable
     */
    public JTable getScrollTable() {
        return scroll;
    }
}
