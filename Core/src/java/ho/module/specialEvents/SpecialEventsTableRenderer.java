package ho.module.specialEvents;

import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.ThemeManager;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

class SpecialEventsTableRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 6621498528069679319L;

	SpecialEventsTableRenderer() {
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		if (column == SpecialEventsTable.RESULTCOLUMN) {
			setHorizontalAlignment(SwingConstants.CENTER);
		} else if (column == SpecialEventsTable.MINUTECOLUMN) {
			setHorizontalAlignment(SwingConstants.RIGHT);
		} else {
			setHorizontalAlignment(SwingConstants.LEFT);
		}
		setForeground(Color.BLACK);
		if (column == SpecialEventsTable.NAMECOLUMN) {
			String name = (String) value;
			if (!name.equals("")) {
				String rName = name.substring(0, name.length() - 2);
				String rType = name.substring(name.length() - 1);
				setText(rName);
				if (rType.equals("-")) {
					setForeground(Color.RED);
				} else if (rType.equals("#")) {
					setForeground(Color.LIGHT_GRAY);
				}
			}
		}
		int type = 0;
		try {
			type = Integer.parseInt((String) table.getValueAt(row,
					SpecialEventsTable.HIDDENCOLUMN));
		} catch (NumberFormatException numberformatexception) {
		}
		setBackground(ThemeManager.getColor(HOColorName.PLAYER_SUBPOS_BG));
		if (type == 1) {
			setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
		}
		if (column == SpecialEventsTable.HIDDENCOLUMN) {
			setIcon(null);
			setText(null);
		} else if (value != null && value instanceof ImageIcon) {
			setIcon((ImageIcon) value);
			setText(null);
		} else {
			setIcon(null);
		}
		return this;
	}

}
