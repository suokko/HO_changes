// %3200370881:hoplugins.teamplanner.ui.model%
package hoplugins.teamplanner.ui.model;

import hoplugins.commons.ui.BaseTableModel;

import hoplugins.teamplanner.ui.model.inner.InnerData;
import hoplugins.teamplanner.ui.model.inner.NumericInner;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


/**
 * Custom RatingTable model
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato </a>
 */
public class OperationTableModel extends BaseTableModel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -5834318479352507933L;
	private Map rowClass = new HashMap();
    private Map rowEditable = new HashMap();
    private Map rowMulti = new HashMap();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new UiRatingTableModel object.
     *
     * @param vector Vector of table data
     * @param vector2 Vector of column names
     */
    public OperationTableModel(Vector vector, Vector vector2) {
        super(vector, vector2);
    }

    /**
     * Creates a new instance of UiFilterTableModel
     */
    public OperationTableModel() {
        super();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Method that returns if the cell if editable, false by default
     *
     * @param row the row index of the cell
     * @param column the column index of the cell
     *
     * @return true if editable, false if not
     */
    @Override
	public boolean isCellEditable(int row, int column) {
        String val = (String) rowEditable.get("" + row);

        if ((val != null) && (val.equalsIgnoreCase("true"))) {
            return true;
        }

        return false;
    }

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param editable Missing Method Parameter Documentation
     */
    public void setEditable(int row, boolean editable) {
        rowEditable.put("" + row, "" + editable);
    }

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param c Missing Method Parameter Documentation
     */
    public void setInner(int row, Class c) {
        rowClass.put("" + row, c);
    }

    /**
     * Method that returns if the cell if editable, false by default
     *
     * @param row the row index of the cell
     *
     * @return true if editable, false if not
     */
    public InnerData getInnerData(int row) {
        Class val = (Class) rowClass.get("" + row);

        try {
            return (InnerData) val.newInstance();
        } catch (Exception e) {
            return new NumericInner();
        }
    }

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param multi Missing Method Parameter Documentation
     */
    public void setMulti(int row, boolean multi) {
        rowMulti.put("" + row, "" + multi);
    }

    /**
     * Method that returns if the cell if editable, false by default
     *
     * @param row the row index of the cell
     *
     * @return true if editable, false if not
     */
    public boolean isMulti(int row) {
        String val = (String) rowMulti.get("" + row);

        if ((val != null) && (val.equalsIgnoreCase("true"))) {
            return true;
        }

        return false;
    }

    /**
     * Missing Method Documentation
     *
     * @param value Missing Method Parameter Documentation
     * @param row Missing Method Parameter Documentation
     * @param col Missing Method Parameter Documentation
     */
    @Override
	public void setValueAt(Object value, int row, int col) {
        if (value instanceof String) {
            int input = 0;

            try {
                input = Integer.parseInt((String) value);
            } catch (Exception e) {
                // Ignore.
            } finally {
                OperationCell cell = (OperationCell) getValueAt(row, col);

                if (!cell.isMulti()) {
                    NumericInner inner = (NumericInner) cell.getOperation().getInner();
                    inner.setMoney(input);
                } else {
                    //MultiPArt not enabled for direct wrinting
                    // Skip it
                    return;
                }

                //super.setValueAt(cell, row, col);
            }
        } else {
            super.setValueAt(value, row, col);
        }

        this.fireTableDataChanged();
    }
}
