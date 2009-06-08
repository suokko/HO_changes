// %3252003863:de.hattrickorganizer.gui.utils%
package de.hattrickorganizer.gui.utils;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TableMap extends AbstractTableModel implements TableModelListener {
	
	private static final long serialVersionUID = 5022212679370349761L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	/** TODO Missing Parameter Documentation */
    protected TableModel model;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TableMap object.
     */
    public TableMap() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     * @param j TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final boolean isCellEditable(int i, int j) {
        return model.isCellEditable(i, j);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final Class<?> getColumnClass(int i) {
        return model.getColumnClass(i);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getColumnCount() {
        return (model != null) ? model.getColumnCount() : 0;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final String getColumnName(int i) {
        return model.getColumnName(i);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param tablemodel TODO Missing Method Parameter Documentation
     */
    public void setModel(TableModel tablemodel) {
        model = tablemodel;
        tablemodel.addTableModelListener(this);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final TableModel getModel() {
        return model;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getRowCount() {
        return (model != null) ? model.getRowCount() : 0;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     * @param i TODO Missing Method Parameter Documentation
     * @param j TODO Missing Method Parameter Documentation
     */
    @Override
	public void setValueAt(Object obj, int i, int j) {
        model.setValueAt(obj, i, j);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     * @param j TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Object getValueAt(int i, int j) {
        return model.getValueAt(i, j);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param tablemodelevent TODO Missing Method Parameter Documentation
     */
    public void tableChanged(TableModelEvent tablemodelevent) {
        fireTableChanged(tablemodelevent);
    }
}
