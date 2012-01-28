// %2336472602:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * Renderer for the manual match selection.
 *
 * @author Draghetto
 */
public class ManualFilterTableRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -685008864266149099L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		int type = 0;

		// (blaghaid fixes selection colors)
		setForeground(Color.black);
		
		try {
			type = Integer.parseInt((String) table.getValueAt(row, 7));
		} catch (NumberFormatException e) {
		}

		setBackground(UIColors.getColor4Matchtyp(type));
		String available = (String) table.getValueAt(row, 6);

		if (isSelected) {
			setBackground(UIColors.SelectedRowColor);
		}

		if (!available.equalsIgnoreCase("true")) {
			setEnabled(false);
		} else {
			setEnabled(true);
		}
		
		if (value instanceof ImageIcon) {
			setIcon(Commons.getModel().getHelper().getImageIcon4Spieltyp(type));
			setText(null);
		} else {
			setIcon(null);
		}

		
		return this;
		
	}
}