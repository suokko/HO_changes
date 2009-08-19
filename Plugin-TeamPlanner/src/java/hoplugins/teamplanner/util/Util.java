// %1360785724:hoplugins.teamplanner.util%
package hoplugins.teamplanner.util;

import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.model.OperationData;

import java.awt.Container;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.TableModel;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class Util {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Util object.
     */
    protected Util() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param model Missing Method Parameter Documentation
     * @param row Missing Method Parameter Documentation
     * @param column Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static OperationCell getOperationCell(TableModel model, int row, int column) {
        return (OperationCell) model.getValueAt(row, column);
    }

    /**
     * Missing Method Documentation
     *
     * @param model Missing Method Parameter Documentation
     * @param row Missing Method Parameter Documentation
     * @param column Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static OperationData getOperationData(TableModel model, int row, int column) {
        OperationCell cell = getOperationCell(model, row, column);
        return cell.getOperation();
    }

    /**
     * Creates row header for table with row number (starting with 1) displayed
     *
     * @param table Missing Constructuor Parameter Documentation
     * @param header Missing Constructuor Parameter Documentation
     */
    public static void setRowHeader(JTable table, List<String> header) {
        Container p = table.getParent();

        if (p instanceof JViewport) {
            Container gp = p.getParent();

            if (gp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) gp;

                scrollPane.setRowHeaderView(new TableRowHeader(table, header));
            }
        }
    }

    /**
     * Missing Method Documentation
     *
     * @param table Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static boolean isRowHeaderVisible(JTable table) {
        Container p = table.getParent();

        if (p instanceof JViewport) {
            Container gp = p.getParent();

            if (gp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) gp;
                JViewport rowHeaderViewPort = scrollPane.getRowHeader();

                if (rowHeaderViewPort != null) {
                    return rowHeaderViewPort.getView() != null;
                }
            }
        }

        return false;
    }

    /**
     * Creates row header for table with row number (starting with 1) displayed
     *
     * @param table Missing Constructuor Parameter Documentation
     */
    public static void removeRowHeader(JTable table) {
        Container p = table.getParent();

        if (p instanceof JViewport) {
            Container gp = p.getParent();

            if (gp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) gp;

                scrollPane.setRowHeader(null);
            }
        }
    }
}
