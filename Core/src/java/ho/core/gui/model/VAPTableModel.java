// %3690693536:de.hattrickorganizer.gui.model%
package ho.core.gui.model;

import javax.swing.table.AbstractTableModel;


/**
 * DOCUMENT ME!
 *
 * @author Stefan Gawlick/ Volker Fischer
 * @version 0.2a    31.10.2001
 */
public class VAPTableModel extends AbstractTableModel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -8731149650305126908L;

	/** TODO Missing Parameter Documentation */
    private String[] columnNames;

    /** TODO Missing Parameter Documentation */
    private Object[][] data;

    /** TODO Missing Parameter Documentation */
    private boolean editable;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new VAPTableModel object.
     *
     * @param columnNames TODO Missing Constructuor Parameter Documentation
     * @param data TODO Missing Constructuor Parameter Documentation
     */
    public VAPTableModel(String[] columnNames, Object[][] data) {
        this(columnNames, data, false);
    }

    /**
     * Creates a new VAPTableModel object.
     *
     * @param columnNames TODO Missing Constructuor Parameter Documentation
     * @param data TODO Missing Constructuor Parameter Documentation
     * @param editable TODO Missing Constructuor Parameter Documentation
     */
    public VAPTableModel(String[] columnNames, Object[][] data, boolean editable) {
        this.columnNames = columnNames;
        this.data = data;
        this.editable = editable;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param editable TODO Missing Method Parameter Documentation
     */
    public final void setCellEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param col TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final boolean isCellEditable(int row, int col) {
        return editable;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param columnIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final Class<?> getColumnClass(int columnIndex) {
        final Object obj = getValueAt(0, columnIndex);

        return (obj != null) ? obj.getClass() : "".getClass();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getColumnCount() {
        return ((data != null) && (data[0] != null)) ? data[0].length : 0;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param columnIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final String getColumnName(int columnIndex) {
        if ((columnNames != null) && (columnNames.length > columnIndex)) {
            return columnNames[columnIndex];
        }

        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getRowCount() {
        return (data != null) ? data.length : 0;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param columnName TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Object getValue(int row, String columnName) {
        if ((columnNames != null) && (data != null)) {
            int i = 0;

            while ((i < columnNames.length) && !columnNames[i].equals(columnName)) {
                i++;
            }

            return data[row][i];
        }

        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     * @param row TODO Missing Method Parameter Documentation
     * @param column TODO Missing Method Parameter Documentation
     */
    @Override
	public final void setValueAt(Object value, int row, int column) {
        data[row][column] = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Object getValueAt(int row, int column) {
        return (data != null) ? data[row][column] : null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param columNames TODO Missing Method Parameter Documentation
     * @param data TODO Missing Method Parameter Documentation
     */
    public final void setValues(String[] columNames, Object[][] data) {
        this.columnNames = columNames;
        this.data = data;
    }
}
