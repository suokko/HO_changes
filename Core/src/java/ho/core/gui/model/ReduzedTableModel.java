// %3331837056:de.hattrickorganizer.gui.model%
package ho.core.gui.model;

import javax.swing.table.TableModel;


/**
 * TableModel, das nur eine Spalte anzeigt
 */
public class ReduzedTableModel implements TableModel {
    //~ Instance fields ----------------------------------------------------------------------------

    private TableModel m_clTablemodel;
    private int m_iSpaltenindex;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param tablemodel TODO Missing Constructuor Parameter Documentation
     * @param spaltenindex Spaltenindex der anzuzeigenden Spalte
     */
    public ReduzedTableModel(TableModel tablemodel, int spaltenindex) {
        m_clTablemodel = tablemodel;
        m_iSpaltenindex = spaltenindex;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param col TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param param TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Class<?> getColumnClass(int param) {
        return m_clTablemodel.getColumnClass(m_iSpaltenindex);
    }

    /**
     * Nur eine Spalte
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getColumnCount() {
        return 1;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param param TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getColumnName(int param) {
        return m_clTablemodel.getColumnName(m_iSpaltenindex);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getRowCount() {
        return m_clTablemodel.getRowCount();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     * @param param TODO Missing Method Parameter Documentation
     * @param param2 TODO Missing Method Parameter Documentation
     */
    public final void setValueAt(Object obj, int param, int param2) {
        m_clTablemodel.setValueAt(obj, param, param2);
    }

    /**
     * Immer Wert der einen Spalte zurückgeben
     *
     * @param row TODO Missing Constructuor Parameter Documentation
     * @param col TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Object getValueAt(int row, int col) {
        return m_clTablemodel.getValueAt(row, m_iSpaltenindex);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param tableModelListener TODO Missing Method Parameter Documentation
     */
    public final void addTableModelListener(javax.swing.event.TableModelListener tableModelListener) {
        m_clTablemodel.addTableModelListener(tableModelListener);
    }

    //Listener
    public final void removeTableModelListener(javax.swing.event.TableModelListener tableModelListener) {
        m_clTablemodel.removeTableModelListener(tableModelListener);
    }
}
