// %4116796863:hoplugins.evilCard%
package hoplugins.evilCard.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


/**
 * <p>
 * TableSorter is a decorator for TableModels; adding sorting functionality to a supplied
 * TableModel. TableSorter does not store or copy the data in its TableModel; instead it maintains
 * a map from the row indexes of the view to the row indexes of the model. As requests are made of
 * the sorter (like getValueAt(row, col)) they are passed to the underlying model after the row
 * numbers have been translated via the internal mapping array. This way, the TableSorter appears
 * to hold another copy of the table with the rows in a different order.
 * </p>
 * 
 * <p>
 * TableSorter registers itself as a listener to the underlying model, just as the JTable itself
 * would. Events recieved from the model are examined, sometimes manipulated (typically widened),
 * and then passed on to the TableSorter's listeners (typically the JTable). If a change to the
 * model has invalidated the order of TableSorter's rows, a note of this is made and the sorter
 * will resort the rows the next time a value is requested.
 * </p>
 * 
 * <p>
 * When the tableHeader property is set, either by using the setTableHeader() method or the two
 * argument constructor, the table header may be used as a complete UI for TableSorter. The
 * default renderer of the tableHeader is decorated with a renderer that indicates the sorting
 * status of each column. In addition, a mouse listener is installed with the following behavior:
 * 
 * <ul>
 * <li>
 * Mouse-click: Clears the sorting status of all other columns and advances the sorting status of
 * that column through three values: {NOT_SORTED, ASCENDING, DESCENDING} (then back to NOT_SORTED
 * again).
 * </li>
 * <li>
 * SHIFT-mouse-click: Clears the sorting status of all other columns and cycles the sorting status
 * of the column through the same three values, in the opposite order: {NOT_SORTED, DESCENDING,
 * ASCENDING}.
 * </li>
 * <li>
 * CONTROL-mouse-click and CONTROL-SHIFT-mouse-click: as above except that the changes to the
 * column do not cancel the statuses of columns that are already sorting - giving a way to
 * initiate a compound sort.
 * </li>
 * </ul>
 * </p>
 * This is a long overdue rewrite of a class of the same name that first appeared in the swing
 * table demos in 1997.
 *
 * @author Philip Milne
 * @author Brendon McLean
 * @author Dan van Enckevort
 * @author Parwinder Sekhon
 * @version 2.0 02/27/04
 */
public class TableSorter extends AbstractTableModel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 8174876181452800423L;

	/** TODO Missing Parameter Documentation */
    public static final int DESCENDING = -1;

    /** TODO Missing Parameter Documentation */
    public static final int NOT_SORTED = 0;

    /** TODO Missing Parameter Documentation */
    public static final int ASCENDING = 1;
    private static Directive EMPTY_DIRECTIVE = new Directive(-1, NOT_SORTED);

    /** TODO Missing Parameter Documentation */
    public static final Comparator COMPARABLE_COMAPRATOR = new Comparator() {
        public int compare(Object o1, Object o2) {
            return ((Comparable) o1).compareTo(o2);
        }
    };

    /** TODO Missing Parameter Documentation */
    public static final Comparator LEXICAL_COMPARATOR = new Comparator() {
        public int compare(Object o1, Object o2) {
            return o1.toString().compareTo(o2.toString());
        }
    };


    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected TableModel tableModel;
    private JTableHeader tableHeader;
    private List sortingColumns = new ArrayList();
    private Map columnComparators = new HashMap();
    private MouseListener mouseListener;
    private TableModelListener tableModelListener;
    private int[] modelToView;
    private Row[] viewToModel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TableSorter object.
     */
    public TableSorter() {
        this.mouseListener = new MouseHandler();
        this.tableModelListener = new TableModelHandler();
    }

    /**
     * Creates a new TableSorter object.
     *
     * @param tableModel TODO Missing Constructuor Parameter Documentation
     */
    public TableSorter(TableModel tableModel) {
        this();
        setTableModel(tableModel);
    }

    /**
     * Creates a new TableSorter object.
     *
     * @param tableModel TODO Missing Constructuor Parameter Documentation
     * @param tableHeader TODO Missing Constructuor Parameter Documentation
     */
    public TableSorter(TableModel tableModel, JTableHeader tableHeader) {
        this();
        setTableHeader(tableHeader);
        setTableModel(tableModel);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public boolean isCellEditable(int row, int column) {
        return tableModel.isCellEditable(modelIndex(row), column);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public Class getColumnClass(int column) {
        return tableModel.getColumnClass(column);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param type TODO Missing Method Parameter Documentation
     * @param comparator TODO Missing Method Parameter Documentation
     */
    public void setColumnComparator(Class type, Comparator comparator) {
        if (comparator == null) {
            columnComparators.remove(type);
        } else {
            columnComparators.put(type, comparator);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getColumnCount() {
        return (tableModel == null) ? 0 : tableModel.getColumnCount();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public String getColumnName(int column) {
        return tableModel.getColumnName(column);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public TableModel getModel() {
        return tableModel;
    }

    // TableModel interface methods 
    public int getRowCount() {
        return (tableModel == null) ? 0 : tableModel.getRowCount();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isSorting() {
        return sortingColumns.size() != 0;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param column TODO Missing Method Parameter Documentation
     * @param status TODO Missing Method Parameter Documentation
     */
    public void setSortingStatus(int column, int status) {
        Directive directive = getDirective(column);

        if (directive != EMPTY_DIRECTIVE) {
            sortingColumns.remove(directive);
        }

        if (status != NOT_SORTED) {
            sortingColumns.add(new Directive(column, status));
        }

        sortingStatusChanged();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getSortingStatus(int column) {
        return getDirective(column).direction;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param tableHeader TODO Missing Method Parameter Documentation
     */
    public void setTableHeader(JTableHeader tableHeader) {
        if (this.tableHeader != null) {
            this.tableHeader.removeMouseListener(mouseListener);

            TableCellRenderer defaultRenderer = this.tableHeader.getDefaultRenderer();

            if (defaultRenderer instanceof SortableHeaderRenderer) {
                this.tableHeader.setDefaultRenderer(((SortableHeaderRenderer) defaultRenderer).tableCellRenderer);
            }
        }

        this.tableHeader = tableHeader;

        if (this.tableHeader != null) {
            this.tableHeader.addMouseListener(mouseListener);
            this.tableHeader.setDefaultRenderer(new SortableHeaderRenderer(this.tableHeader
                                                                           .getDefaultRenderer()));
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public JTableHeader getTableHeader() {
        return tableHeader;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param tableModel TODO Missing Method Parameter Documentation
     */
    public void setTableModel(TableModel tableModel) {
        if (this.tableModel != null) {
            this.tableModel.removeTableModelListener(tableModelListener);
        }

        this.tableModel = tableModel;

        if (this.tableModel != null) {
            this.tableModel.addTableModelListener(tableModelListener);
        }

        clearSortingState();
        fireTableStructureChanged();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public TableModel getTableModel() {
        return tableModel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param aValue TODO Missing Method Parameter Documentation
     * @param row TODO Missing Method Parameter Documentation
     * @param column TODO Missing Method Parameter Documentation
     */
    @Override
	public void setValueAt(Object aValue, int row, int column) {
        tableModel.setValueAt(aValue, modelIndex(row), column);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Object getValueAt(int row, int column) {
        return tableModel.getValueAt(modelIndex(row), column);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param viewIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int modelIndex(int viewIndex) {
        return getViewToModel()[viewIndex].modelIndex;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected Comparator getComparator(int column) {
        Class columnType = tableModel.getColumnClass(column);
        Comparator comparator = (Comparator) columnComparators.get(columnType);

        if (comparator != null) {
            return comparator;
        }

        if (Comparable.class.isAssignableFrom(columnType)) {
            return COMPARABLE_COMAPRATOR;
        }

        return LEXICAL_COMPARATOR;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param column TODO Missing Method Parameter Documentation
     * @param size TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected Icon getHeaderRendererIcon(int column, int size) {
        Directive directive = getDirective(column);

        if (directive == EMPTY_DIRECTIVE) {
            return null;
        }

        return new Arrow(directive.direction == DESCENDING, size, sortingColumns.indexOf(directive));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Directive getDirective(int column) {
        for (int i = 0; i < sortingColumns.size(); i++) {
            Directive directive = (Directive) sortingColumns.get(i);

            if (directive.column == column) {
                return directive;
            }
        }

        return EMPTY_DIRECTIVE;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private int[] getModelToView() {
        if (modelToView == null) {
            int n = getViewToModel().length;
            modelToView = new int[n];

            for (int i = 0; i < n; i++) {
                modelToView[modelIndex(i)] = i;
            }
        }

        return modelToView;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Row[] getViewToModel() {
        if (viewToModel == null) {
            int tableModelRowCount = tableModel.getRowCount();
            viewToModel = new Row[tableModelRowCount];

            for (int row = 0; row < tableModelRowCount; row++) {
                viewToModel[row] = new Row(row);
            }

            if (isSorting()) {
                Arrays.sort(viewToModel);
            }
        }

        return viewToModel;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void cancelSorting() {
        sortingColumns.clear();
        sortingStatusChanged();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void clearSortingState() {
        viewToModel = null;
        modelToView = null;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void sortingStatusChanged() {
        clearSortingState();
        fireTableDataChanged();

        if (tableHeader != null) {
            tableHeader.repaint();
        }
    }

    //~ Inner Classes ------------------------------------------------------------------------------

    /**
     * TODO Missing Class Documentation
     *
     * @author TODO Author Name
     */
    private static class Arrow implements Icon {
        //~ Instance fields ------------------------------------------------------------------------

        private boolean descending;
        private int priority;
        private int size;

        //~ Constructors ---------------------------------------------------------------------------

        /**
         * Creates a new Arrow object.
         *
         * @param descending TODO Missing Constructuor Parameter Documentation
         * @param size TODO Missing Constructuor Parameter Documentation
         * @param priority TODO Missing Constructuor Parameter Documentation
         */
        public Arrow(boolean descending, int size, int priority) {
            this.descending = descending;
            this.size = size;
            this.priority = priority;
        }

        //~ Methods --------------------------------------------------------------------------------

        /**
         * TODO Missing Method Documentation
         *
         * @return TODO Missing Return Method Documentation
         */
        public int getIconHeight() {
            return size;
        }

        /**
         * TODO Missing Method Documentation
         *
         * @return TODO Missing Return Method Documentation
         */
        public int getIconWidth() {
            return size;
        }

        /**
         * TODO Missing Method Documentation
         *
         * @param c TODO Missing Method Parameter Documentation
         * @param g TODO Missing Method Parameter Documentation
         * @param x TODO Missing Method Parameter Documentation
         * @param y TODO Missing Method Parameter Documentation
         */
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Color color = (c == null) ? Color.GRAY : c.getBackground();

            // In a compound sort, make each succesive triangle 20% 
            // smaller than the previous one. 
            int dx = (int) (size / 2 * Math.pow(0.8, priority));
            int dy = descending ? dx : (-dx);

            // Align icon (roughly) with font baseline. 
            y = y + ((5 * size) / 6) + (descending ? (-dy) : 0);

            int shift = descending ? 1 : (-1);
            g.translate(x, y);

            // Right diagonal. 
            g.setColor(color.darker());
            g.drawLine(dx / 2, dy, 0, 0);
            g.drawLine(dx / 2, dy + shift, 0, shift);

            // Left diagonal. 
            g.setColor(color.brighter());
            g.drawLine(dx / 2, dy, dx, 0);
            g.drawLine(dx / 2, dy + shift, dx, shift);

            // Horizontal line. 
            if (descending) {
                g.setColor(color.darker().darker());
            } else {
                g.setColor(color.brighter().brighter());
            }

            g.drawLine(dx, 0, 0, 0);

            g.setColor(color);
            g.translate(-x, -y);
        }
    }

    /**
     * TODO Missing Class Documentation
     *
     * @author TODO Author Name
     */
    private static class Directive {
        //~ Instance fields ------------------------------------------------------------------------

        private int column;
        private int direction;

        //~ Constructors ---------------------------------------------------------------------------

        /**
         * Creates a new Directive object.
         *
         * @param column TODO Missing Constructuor Parameter Documentation
         * @param direction TODO Missing Constructuor Parameter Documentation
         */
        public Directive(int column, int direction) {
            this.column = column;
            this.direction = direction;
        }
    }

    /**
     * TODO Missing Class Documentation
     *
     * @author TODO Author Name
     */
    private class MouseHandler extends MouseAdapter {
        //~ Methods --------------------------------------------------------------------------------

        /**
         * TODO Missing Method Documentation
         *
         * @param e TODO Missing Method Parameter Documentation
         */
        @Override
		public void mouseClicked(MouseEvent e) {
            JTableHeader h = (JTableHeader) e.getSource();
            TableColumnModel columnModel = h.getColumnModel();
            int viewColumn = columnModel.getColumnIndexAtX(e.getX());
            int column = columnModel.getColumn(viewColumn).getModelIndex();

            if (column != -1) {
                int status = getSortingStatus(column);

                if (!e.isControlDown()) {
                    cancelSorting();
                }

                // Cycle the sorting states through {NOT_SORTED, ASCENDING, DESCENDING} or 
                // {NOT_SORTED, DESCENDING, ASCENDING} depending on whether shift is pressed. 
                status = status + (e.isShiftDown() ? (-1) : 1);
                status = ((status + 4) % 3) - 1; // signed mod, returning {-1, 0, 1}
                setSortingStatus(column, status);
            }
        }
    }

    // Helper classes
    private class Row implements Comparable {
        //~ Instance fields ------------------------------------------------------------------------

        private int modelIndex;

        //~ Constructors ---------------------------------------------------------------------------

        /**
         * Creates a new Row object.
         *
         * @param index TODO Missing Constructuor Parameter Documentation
         */
        public Row(int index) {
            this.modelIndex = index;
        }

        //~ Methods --------------------------------------------------------------------------------

        /**
         * TODO Missing Method Documentation
         *
         * @param o TODO Missing Method Parameter Documentation
         *
         * @return TODO Missing Return Method Documentation
         */
        public int compareTo(Object o) {
            int row1 = modelIndex;
            int row2 = ((Row) o).modelIndex;

            for (Iterator it = sortingColumns.iterator(); it.hasNext();) {
                Directive directive = (Directive) it.next();
                int column = directive.column;
                Object o1 = tableModel.getValueAt(row1, column);
                Object o2 = tableModel.getValueAt(row2, column);

                int comparison = 0;

                // Define null less than everything, except null.
                if ((o1 == null) && (o2 == null)) {
                    comparison = 0;
                } else if (o1 == null) {
                    comparison = -1;
                } else if (o2 == null) {
                    comparison = 1;
                } else {
                    comparison = getComparator(column).compare(o1, o2);
                }

                if (comparison != 0) {
                    return (directive.direction == DESCENDING) ? (-comparison) : comparison;
                }
            }

            return 0;
        }
    }

    /**
     * TODO Missing Class Documentation
     *
     * @author TODO Author Name
     */
    private class SortableHeaderRenderer implements TableCellRenderer {
        //~ Instance fields ------------------------------------------------------------------------

        private TableCellRenderer tableCellRenderer;

        //~ Constructors ---------------------------------------------------------------------------

        /**
         * Creates a new SortableHeaderRenderer object.
         *
         * @param tableCellRenderer TODO Missing Constructuor Parameter Documentation
         */
        public SortableHeaderRenderer(TableCellRenderer tableCellRenderer) {
            this.tableCellRenderer = tableCellRenderer;
        }

        //~ Methods --------------------------------------------------------------------------------

        /**
         * TODO Missing Method Documentation
         *
         * @param table TODO Missing Method Parameter Documentation
         * @param value TODO Missing Method Parameter Documentation
         * @param isSelected TODO Missing Method Parameter Documentation
         * @param hasFocus TODO Missing Method Parameter Documentation
         * @param row TODO Missing Method Parameter Documentation
         * @param column TODO Missing Method Parameter Documentation
         *
         * @return TODO Missing Return Method Documentation
         */
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = tableCellRenderer.getTableCellRendererComponent(table, value, isSelected,
                                                                          hasFocus, row, column);

            if (c instanceof JLabel) {
                JLabel l = (JLabel) c;
                l.setHorizontalTextPosition(SwingConstants.LEFT);

                int modelColumn = table.convertColumnIndexToModel(column);
                l.setIcon(getHeaderRendererIcon(modelColumn, l.getFont().getSize()));
            }

            return c;
        }
    }

    /**
     * TODO Missing Class Documentation
     *
     * @author TODO Author Name
     */
    private class TableModelHandler implements TableModelListener {
        //~ Methods --------------------------------------------------------------------------------

        /**
         * TODO Missing Method Documentation
         *
         * @param e TODO Missing Method Parameter Documentation
         */
        public void tableChanged(TableModelEvent e) {
            // If we're not sorting by anything, just pass the event along.             
            if (!isSorting()) {
                clearSortingState();
                fireTableChanged(e);
                return;
            }

            // If the table structure has changed, cancel the sorting; the             
            // sorting columns may have been either moved or deleted from             
            // the model. 
            if (e.getFirstRow() == TableModelEvent.HEADER_ROW) {
                cancelSorting();
                fireTableChanged(e);
                return;
            }

            // We can map a cell event through to the view without widening             
            // when the following conditions apply: 
            // 
            // a) all the changes are on one row (e.getFirstRow() == e.getLastRow()) and, 
            // b) all the changes are in one column (column != TableModelEvent.ALL_COLUMNS) and,
            // c) we are not sorting on that column (getSortingStatus(column) == NOT_SORTED) and, 
            // d) a reverse lookup will not trigger a sort (modelToView != null)
            //
            // Note: INSERT and DELETE events fail this test as they have column == ALL_COLUMNS.
            // 
            // The last check, for (modelToView != null) is to see if modelToView 
            // is already allocated. If we don't do this check; sorting can become 
            // a performance bottleneck for applications where cells  
            // change rapidly in different parts of the table. If cells 
            // change alternately in the sorting column and then outside of             
            // it this class can end up re-sorting on alternate cell updates - 
            // which can be a performance problem for large tables. The last 
            // clause avoids this problem. 
            int column = e.getColumn();

            if ((e.getFirstRow() == e.getLastRow())
                && (column != TableModelEvent.ALL_COLUMNS)
                && (getSortingStatus(column) == NOT_SORTED)
                && (modelToView != null)) {
                int viewIndex = getModelToView()[e.getFirstRow()];
                fireTableChanged(new TableModelEvent(TableSorter.this, viewIndex, viewIndex,
                                                     column, e.getType()));
                return;
            }

            // Something has happened to the data that may have invalidated the row order. 
            clearSortingState();
            fireTableDataChanged();
            return;
        }
    }
}
