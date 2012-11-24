// %1304476644:de.hattrickorganizer.tools.updater%
/*
 * Created on 27.12.2004
 *
 */
package ho.tool.updater;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.renderer.HODefaultTableCellRenderer;
import ho.core.util.HOLogger;

import java.awt.Component;
import java.util.EventObject;
import java.util.HashMap;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;



/**
 * TableEditor for UpdateDialogs
 *
 * @author tdietz
 *
 * @since 1.35
 */
public final class TableEditor extends AbstractCellEditor implements TableCellEditor {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 204969955618566382L;

	/** TODO Missing Parameter Documentation */
    protected HashMap<Integer,TableCellEditor> editors;

    /** TODO Missing Parameter Documentation */
    protected TableCellEditor defaultEditor;

    /** TODO Missing Parameter Documentation */
    protected TableCellEditor editor;
    private JTextField textField;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TableEditor object.
     */
	public TableEditor() {
		editors = new HashMap<Integer, TableCellEditor>();

		textField = new JTextField();
		textField.setBorder(null);
		textField.setFocusCycleRoot(true);

		defaultEditor = new DefaultCellEditor(textField);
	}

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isCellEditable() {
        return true;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Object getCellEditorValue() {
        return editor.getCellEditorValue();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param table TODO Missing Method Parameter Documentation
     * @param value TODO Missing Method Parameter Documentation
     * @param isSelected TODO Missing Method Parameter Documentation
     * @param row TODO Missing Method Parameter Documentation
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                 int row, int column) {
        editor = editors.get(Integer.valueOf(row));

        if (editor == null) {
            editor = defaultEditor;
        }

        if (value != null) {
            try {
            	if(value instanceof JComponent){
            		((JComponent)value).setBackground(isSelected?HODefaultTableCellRenderer.SELECTION_BG:ColorLabelEntry.BG_STANDARD);
            		((JComponent)value).setForeground(isSelected?HODefaultTableCellRenderer.SELECTION_FG:ColorLabelEntry.FG_STANDARD);
            	}
            		
                if (value instanceof JTextField) {
                    ((JTextField) value).setFocusCycleRoot(true);
                    return (JTextField) value;
                }

                if (value instanceof JComboBox) {
                    return (JComboBox) value;
                }
                
                if (value instanceof JButton) {
                    return (JButton) value;
                }

                if (value instanceof JCheckBox) {
                    return (JCheckBox) value;
                }
            } catch (IllegalArgumentException e) {
                HOLogger.instance().log(getClass(),e);
            }
        }

        return editor.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param teditor TODO Missing Method Parameter Documentation
     */
    public void add(int row, TableCellEditor teditor) {
        editors.put(new Integer(row), teditor);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param l TODO Missing Method Parameter Documentation
     */
    @Override
	public void addCellEditorListener(CellEditorListener l) {
        editor.addCellEditorListener(l);
    }

    /**
     * TODO Missing Method Documentation
     */
    @Override
	public void cancelCellEditing() {
        super.cancelCellEditing();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param l TODO Missing Method Parameter Documentation
     */
    @Override
	public void removeCellEditorListener(CellEditorListener l) {
        editor.removeCellEditorListener(l);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param anEvent TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public boolean shouldSelectCell(EventObject anEvent) {
        return super.shouldSelectCell(anEvent);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public boolean stopCellEditing() {
        return super.stopCellEditing();
    }
}
