// %2336472602:hoplugins.teamAnalyzer.ui%
package ho.module.teamAnalyzer.ui;

import gui.HOColorName;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.hattrickorganizer.gui.model.MatchesColumnModel;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.tools.HelperWrapper;


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

		setBackground(MatchesColumnModel.getColor4Matchtyp(type));
		String available = (String) table.getValueAt(row, 6);

		if (isSelected) {
			setBackground(ThemeManager.getColor(HOColorName.TABLE_SELECTION_BG));
		}

		if (!available.equalsIgnoreCase("true")) {
			setEnabled(false);
		} else {
			setEnabled(true);
		}
		
		if (value instanceof ImageIcon) {
			setIcon(HelperWrapper.instance().getImageIcon4Spieltyp(type));
			setText(null);
		} else {
			setIcon(null);
		}

		
		return this;
		
	}
}
