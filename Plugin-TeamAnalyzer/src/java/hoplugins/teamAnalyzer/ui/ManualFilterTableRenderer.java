// %2336472602:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class ManualFilterTableRenderer extends DefaultTableCellRenderer {
    //~ Methods ------------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -685008864266149099L;

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
    @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                                                             row, column);
        int type = 0;

        try {
            type = Integer.parseInt((String) table.getValueAt(row, 7));
        } catch (NumberFormatException e) {
        }

        cell.setBackground(UIColors.getColor4Matchtyp(type));

        String available = (String) table.getValueAt(row, 6);

        if (!available.equalsIgnoreCase("true")) {
            cell.setEnabled(false);
        } else {
            cell.setEnabled(true);
        }

        return cell;
    }
}
