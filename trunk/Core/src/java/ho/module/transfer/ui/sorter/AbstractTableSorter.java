// %1126721045932:hoplugins.commons.ui.sorter%
package ho.module.transfer.ui.sorter;

import javax.swing.Icon;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TableSorter is a decorator for TableModels; adding sorting functionality to a supplied
 * TableModel. TableSorter does not store or copy the data in its TableModel; instead it maintains
 * a map from the row indexes of the view to the row indexes of the model. As requests are made of
 * the sorter (like getValueAt(row, col)) they are passed to the underlying model after the row
 * numbers have been translated via the internal mapping array. This way, the TableSorter appears
 * to hold another copy of the table with the rows in a different order.  TableSorter registers
 * itself as a listener to the underlying model, just as the JTable itself would. Events recieved
 * from the model are examined, sometimes manipulated (typically widened), and then passed on to
 * the TableSorter's listeners (typically the JTable). If a change to the model has invalidated
 * the order of TableSorter's rows, a note of this is made and the sorter will resort the rows the
 * next time a value is requested.  When the tableHeader property is set, either by using the
 * setTableHeader() method or the two argument constructor, the table header may be used as a
 * complete UI for TableSorter. The default renderer of the tableHeader is decorated with a
 * renderer that indicates the sorting status of each column. In addition, a mouse listener is
 * installed with the following behavior:   Mouse-click: Clears the sorting status of all other
 * columns and advances the sorting status of that column through three values: {NOT_SORTED,
 * ASCENDING, DESCENDING} (then back to NOT_SORTED again).  SHIFT-mouse-click: Clears the sorting
 * status of all other columns and cycles the sorting status of the column through the same three
 * values, in the opposite order: {NOT_SORTED, DESCENDING, ASCENDING}.  CONTROL-mouse-click and
 * CONTROL-SHIFT-mouse-click: as above except that the changes to the column do not cancel the
 * statuses of columns that are already sorting - giving a way to initiate a compound sort.   This
 * is a long overdue rewrite of a class of the same name that first appeared in the swing table
 * demos in 1997.
 *
 * @author Philip Milne
 * @author Brendon McLean
 * @author Dan van Enckevort
 * @author Parwinder Sekhon
 * @version 2.0 02/27/04
 */
public abstract class AbstractTableSorter extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1943995728912103888L;

	/**  */
    /** TODO Missing Parameter Documentation */
    public static final int DESCENDING = -1;

    /**  */
    /** TODO Missing Parameter Documentation */
    public static final int NOT_SORTED = 0;

    /**  */
    /** TODO Missing Parameter Documentation */
    public static final int ASCENDING = 1;
    private static Directive EMPTY_DIRECTIVE = new Directive(-1, NOT_SORTED);

    /**  */
    /** TODO Missing Parameter Documentation */
    public static final Comparator COMPARABLE_COMAPRATOR = new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) o1).compareTo(o2);
            }
        };

    /**  */
    /** TODO Missing Parameter Documentation */
    public static final Comparator LEXICAL_COMPARATOR = new Comparator() {
            public int compare(Object o1, Object o2) {
                return o1.toString().compareTo(o2.toString());
            }
        };

    /**  */
    /** TODO Missing Parameter Documentation */
    protected TableModel tableModel;
    private JTableHeader tableHeader;
    private List<Directive> sortingColumns = new ArrayList<Directive>();
    private Map<Class<?>,Comparator> columnComparators = new HashMap<Class<?>,Comparator>();
    private MouseListener mouseListener;
    private TableModelListener tableModelListener;
    private int[] modelToView;
    private Row[] viewToModel;

    /**
     * Creates a new TableSorter object.
     */
    public AbstractTableSorter() {
        this.mouseListener = new MouseHandler(this);
        this.tableModelListener = new TableModelHandler(this);
    }

    /**
     * Creates a new TableSorter object.
     *
     * @param tableModel
     */
    public AbstractTableSorter(TableModel tableModel) {
        this();
        setTableModel(tableModel);
    }

    /**
     * Creates a new TableSorter object.
     *
     * @param tableModel
     * @param tableHeader
     */
    public AbstractTableSorter(TableModel tableModel, JTableHeader tableHeader) {
        this();
        setTableHeader(tableHeader);
        setTableModel(tableModel);
    }

    /**
     * DOCUMENT ME!
     *
     * @param row
     * @param column
     *
     * @return
     */
    @Override
	public boolean isCellEditable(int row, int column) {
        return tableModel.isCellEditable(modelIndex(row), column);
    }

    /**
     * DOCUMENT ME!
     *
     * @param column
     *
     * @return
     */
    @Override
	public Class<?> getColumnClass(int column) {
        return tableModel.getColumnClass(column);
    }

    /**
     * DOCUMENT ME!
     *
     * @param type
     * @param comparator
     */
    public void setColumnComparator(Class<?> type, Comparator comparator) {
        if (comparator == null) {
            columnComparators.remove(type);
        }
        else {
            columnComparators.put(type, comparator);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public int getColumnCount() {
        return (tableModel == null) ? 0 : tableModel.getColumnCount();
    }

    /**
     * DOCUMENT ME!
     *
     * @param column
     *
     * @return
     */
    @Override
	public String getColumnName(int column) {
        return tableModel.getColumnName(column);
    }

    /**
     * DOCUMENT ME!
     *
     * @param column
     *
     * @return
     */
    public abstract Comparator getCustomComparator(int column);

    // TableModel interface methods 
    public int getRowCount() {
        return (tableModel == null) ? 0 : tableModel.getRowCount();
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public boolean isSorting() {
        return sortingColumns.size() != 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public List<Directive> getSortingColumns() {
        return sortingColumns;
    }

    /**
     * DOCUMENT ME!
     *
     * @param column
     * @param status
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
     * DOCUMENT ME!
     *
     * @param column
     *
     * @return
     */
    public int getSortingStatus(int column) {
        return getDirective(column).getDirection();
    }

    /**
     * DOCUMENT ME!
     *
     * @param tableHeader
     */
    public void setTableHeader(JTableHeader tableHeader) {
        if (this.tableHeader != null) {
            this.tableHeader.removeMouseListener(mouseListener);

            TableCellRenderer defaultRenderer = this.tableHeader
                .getDefaultRenderer();

            if (defaultRenderer instanceof SortableHeaderRenderer) {
                this.tableHeader.setDefaultRenderer(((SortableHeaderRenderer) defaultRenderer)
                    .getTableCellRenderer());
            }
        }

        this.tableHeader = tableHeader;

        if (this.tableHeader != null) {
            this.tableHeader.addMouseListener(mouseListener);
            this.tableHeader.setDefaultRenderer(new SortableHeaderRenderer(
                    this, this.tableHeader.getDefaultRenderer()));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public JTableHeader getTableHeader() {
        return tableHeader;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tableModel
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
     * DOCUMENT ME!
     *
     * @return
     */
    public TableModel getTableModel() {
        return tableModel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param aValue
     * @param row
     * @param column
     */
    @Override
	public void setValueAt(Object aValue, int row, int column) {
        tableModel.setValueAt(aValue, modelIndex(row), column);
    }

    /**
     * DOCUMENT ME!
     *
     * @param row
     * @param column
     *
     * @return
     */
    public Object getValueAt(int row, int column) {
        return tableModel.getValueAt(modelIndex(row), column);
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public abstract boolean hasHeaderLine();

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public abstract int minSortableColumn();

    /**
     * DOCUMENT ME!
     *
     * @param viewIndex
     *
     * @return
     */
    public int modelIndex(int viewIndex) {
        return getViewToModel()[viewIndex].getModelIndex();
    }

    /**
     * DOCUMENT ME!
     *
     * @param column
     *
     * @return
     */
    protected Comparator getComparator(int column) {
        Comparator comparator = getCustomComparator(column);

        if (comparator != null) {
            return comparator;
        }

        Class<?> columnType = tableModel.getColumnClass(column);

        comparator = (Comparator) columnComparators.get(columnType);

        if (comparator != null) {
            return comparator;
        }

        if (Comparable.class.isAssignableFrom(columnType)) {
            return COMPARABLE_COMAPRATOR;
        }

        return LEXICAL_COMPARATOR;
    }

    /**
     * DOCUMENT ME!
     *
     * @param column
     * @param size
     *
     * @return
     */
    protected Icon getHeaderRendererIcon(int column, int size) {
        Directive directive = getDirective(column);

        if (directive == EMPTY_DIRECTIVE) {
            return null;
        }

        return new Arrow(directive.getDirection() == DESCENDING, size,
            sortingColumns.indexOf(directive));
    }

    /**
     *
     */
    protected void cancelSorting() {
        sortingColumns.clear();
        sortingStatusChanged();
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    int[] getModelToView() {
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
     *
     */
    void clearSortingState() {
        viewToModel = null;
        modelToView = null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param column
     *
     * @return
     */
    private Directive getDirective(int column) {
        for (int i = 0; i < sortingColumns.size(); i++) {
            Directive directive = (Directive) sortingColumns.get(i);

            if (directive.getColumn() == column) {
                return directive;
            }
        }

        return EMPTY_DIRECTIVE;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    private Row[] getViewToModel() {
        if (viewToModel == null) {
            int tableModelRowCount = tableModel.getRowCount();

            viewToModel = new Row[tableModelRowCount];

            for (int row = 0; row < tableModelRowCount; row++) {
                viewToModel[row] = new Row(this, row);
            }

            if (isSorting()) {
                Arrays.sort(viewToModel);
            }
        }

        return viewToModel;
    }

    /**
     *
     */
    private void sortingStatusChanged() {
        clearSortingState();
        fireTableDataChanged();

        if (tableHeader != null) {
            tableHeader.repaint();
        }
    }
}
