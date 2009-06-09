// %1924977221:hoplugins.teamplanner.ui.tabs%
/*
 * Created on 22.9.2005
 */
package hoplugins.teamplanner.ui.tabs;

import hoplugins.Commons;

import hoplugins.teamplanner.ui.controller.OperationListener;
import hoplugins.teamplanner.ui.controller.calculator.Calculator;
import hoplugins.teamplanner.ui.controller.input.DefaultInputListener;
import hoplugins.teamplanner.ui.controller.input.InputListener;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.model.OperationData;
import hoplugins.teamplanner.ui.model.OperationTableModel;
import hoplugins.teamplanner.ui.model.inner.InnerData;
import hoplugins.teamplanner.ui.model.inner.NumericInner;
import hoplugins.teamplanner.ui.renderer.OperationDataCellRenderer;
import hoplugins.teamplanner.util.Util;
import hoplugins.teamplanner.vo.HTWeek;

import plugins.IFutureTrainingManager;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava </a>
 */
public abstract class AbstractOperationPane extends JPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -3465500372855821956L;

	/** Missing Parameter Documentation */
    private static final int MIN_COLUMN_WIDTH = 50;

    //~ Instance fields ----------------------------------------------------------------------------

    /** Missing Parameter Documentation */
    protected OperationTableModel model;

    //private JPanel actionPanel;
    private JPanel tabPanel;
    private JTable table;
    private List<OperationListener> calcRows = new ArrayList<OperationListener>();
    private List<OperationListener> inputRows = new ArrayList<OperationListener>();
    private Vector<String> rowHeader;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AbstractOperationPane object.
     */
    public AbstractOperationPane() {
        super();
        this.rowHeader = new Vector<String>();
        this.model = new OperationTableModel(new Vector(), WeekHeader.instance().getValues());
        setRows();
        jbInit();
        setColumnWidths();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public abstract int getBaseBalance(HTWeek week);

    /**
     * Missing Method Documentation
     *
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public abstract int getFullBalance(HTWeek week);

    /**
     * Missing Method Documentation
     */
    public void initialize() {
        loadInputData();
        refreshTable();
        initializeListeners();
    }

    /**
     * Missing Method Documentation
     */
    public void refreshTable() {
        int count = 0;

        // Changed input field, recalculate all calculated rows
        for (Iterator<OperationListener> iter = calcRows.iterator(); iter.hasNext();) {
            Calculator listener = (Calculator) iter.next();
            listener.doCalculate(inputRows.size() + count, model);
            count++;
        }

        table.updateUI();
    }

    /**
     * Missing Method Documentation
     *
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    protected int getColumnIndex(HTWeek week) {
        return WeekHeader.instance().getColumnIndex(week);
    }

    /**
     * Missing Method Documentation
     *
     * @param columnIndex Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    protected HTWeek getColumnWeek(int columnIndex) {
        return WeekHeader.instance().getColumnWeek(columnIndex);
    }

    /**
     * Missing Method Documentation
     */
    protected abstract void setRows();

    /**
     * Missing Method Documentation
     *
     * @param text Missing Method Parameter Documentation
     * @param listener Missing Method Parameter Documentation
     */
    protected void addCalculatedRow(String text, Calculator listener) {
        addCalculatedRow(text, listener, new NumericInner());
    }

    /**
     * Missing Method Documentation
     *
     * @param text Missing Method Parameter Documentation
     * @param listener Missing Method Parameter Documentation
     * @param inner Missing Constructuor Parameter Documentation
     */
    protected void addCalculatedRow(String text, Calculator listener, InnerData inner) {
        model.setEditable(inputRows.size(), false);
        model.setMulti(inputRows.size(), false);
        model.setInner(inputRows.size(), inner.getClass());
        calcRows.add(listener);
        rowHeader.add(text);
    }

    /**
     * Missing Method Documentation
     */
    protected abstract void loadInputData();

    /**
     * Missing Method Documentation
     */
    protected abstract void onChange();

    /**
     * Missing Method Documentation
     *
     * @param text Missing Method Parameter Documentation
     * @param listener Missing Method Parameter Documentation
     * @param multi Missing Method Parameter Documentation
     * @param inner Missing Method Parameter Documentation
     */
    protected void addInputRow(String text, InputListener listener, boolean multi, InnerData inner) {
        model.setEditable(inputRows.size(), false);
        model.setMulti(inputRows.size(), multi);
        model.setInner(inputRows.size(), inner.getClass());
        inputRows.add(listener);
        rowHeader.add(text);
    }

    /**
     * Missing Method Documentation
     *
     * @param text Missing Method Parameter Documentation
     */
    protected void addManualRow(String text) {
        model.setEditable(inputRows.size(), true);
        model.setMulti(inputRows.size(), false);
        model.setInner(inputRows.size(), NumericInner.class);
        inputRows.add(new DefaultInputListener());
        rowHeader.add(text);
    }

    /**
     * Missing Method Documentation
     */
    private void setColumnWidths() {
        for (int i = table.getColumnCount() - 1; i >= 0; i--) {
            table.getColumnModel().getColumn(i).setMinWidth(MIN_COLUMN_WIDTH);
        }
    }

    /**
     * Missing Method Documentation
     */
    private void initializeListeners() {
        table.addMouseListener(new MouseActionListener());
        model.addTableModelListener(new OperationModelListener());
    }

    /**
     * Initialization.
     */
    private void jbInit() {
        setOpaque(false);
        setLayout(new BorderLayout());
        tabPanel = Commons.getModel().getGUI().createImagePanel();
        tabPanel.setLayout(new BorderLayout());
        tabPanel.setOpaque(false);
        add(tabPanel, BorderLayout.CENTER);

        Vector<Vector<OperationCell>> data = new Vector<Vector<OperationCell>>();

        for (int i = 0; i < (inputRows.size() + calcRows.size()); i++) {
            Vector<OperationCell> row = new Vector<OperationCell>();

            for (int j = 0; j < IFutureTrainingManager.FUTUREWEEKS; j++) {
                row.add(new OperationCell(model.getInnerData(i), model.isMulti(i)));
            }

            data.add(row);
        }

        WeekHeader.instance().reload();
        model.setDataVector(data, WeekHeader.instance().getValues());
        table = new JTable(model);
        table.setDefaultEditor(OperationData.class, new DefaultCellEditor(new JTextField()));
        table.setDefaultRenderer(Object.class, new OperationTableRenderer());

        JScrollPane pane = new JScrollPane(table);

        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tabPanel.add(pane, BorderLayout.CENTER);

        Util.setRowHeader(table, rowHeader);

        table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));

        table.setDefaultRenderer(Object.class, new OperationDataCellRenderer());
    }

    //~ Inner Classes ------------------------------------------------------------------------------

    /**
     * Mouse action listener.
     */
    private class MouseActionListener extends MouseAdapter {
        //~ Methods --------------------------------------------------------------------------------

        /**
         * Missing Method Documentation
         *
         * @param e Missing Method Parameter Documentation
         */
        @Override
		public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                int row = table.getSelectedRow();

                if (row >= inputRows.size()) {
                    return;
                }

                int column = table.getSelectedColumn();
                InputListener listener = (InputListener) inputRows.get(row);
                OperationCell cell = (OperationCell) model.getValueAt(row, column);

                listener.doExecute(cell, getColumnWeek(column));
                table.updateUI();
                model.fireTableDataChanged();
            }
        }
    }

    /**
     * Missing Class Documentation
     *
     * @author Draghetto
     */
    private class OperationModelListener implements TableModelListener {
        //~ Methods --------------------------------------------------------------------------------

        /**
         * Missing Method Documentation
         *
         * @param e Missing Method Parameter Documentation
         */
        public void tableChanged(TableModelEvent e) {
            int row = table.getSelectedRow();

            if (row < inputRows.size()) {
                refreshTable();
            }

            onChange();
        }
    }
}
