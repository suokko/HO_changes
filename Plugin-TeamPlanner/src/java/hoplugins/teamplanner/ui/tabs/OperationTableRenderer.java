// %2516246874:hoplugins.teamplanner.ui.tabs%
package hoplugins.teamplanner.ui.tabs;

import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.model.OperationData;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class OperationTableRenderer extends DefaultTableCellRenderer {
    //~ Methods ------------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 2594103869116180091L;

	/*
     * (non-Javadoc)
     *
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        //JComponent component = null;
        if (value instanceof OperationCell) {
            //			component = new JPanel();
            //			component.add(new JLabel(cell.getDescriptions(), JLabel.LEFT));
            //
            //			component.setOpaque(true);
            //		} else {
            //			if (value != null) {
            //				component = new JLabel(value.toString());
            //			} else {
            //				component = new JLabel("");
            //			}
            //
            //			component.setOpaque(true);
            //			return component;
            OperationCell cell = (OperationCell) value;

            //            for (Iterator iter = cell.values().iterator(); iter.hasNext();) {
            //                String element = (String) iter.next();
            //                component.add(new JLabel(element, JLabel.LEFT));
            //            }
            if (!cell.isMulti()) {
                this.setText(cell.getOperation().toString());
                this.setForeground(Color.BLACK);

                if (!cell.isValid()) {
                    this.setForeground(Color.RED);
                }
            } else {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(cell.getOperationList().size(), 1));

                for (Iterator iter = cell.getOperationList().iterator(); iter.hasNext();) {
                    OperationData data = (OperationData) iter.next();
                    JLabel label = new JLabel();
                    label.setText(data.getInner().getDescription());
                    panel.add(label);
                }

                panel.setForeground(Color.BLACK);

                if (!cell.isValid()) {
                    panel.setForeground(Color.RED);
                }

                return panel;
            }
        }

        return this;
    }
}
