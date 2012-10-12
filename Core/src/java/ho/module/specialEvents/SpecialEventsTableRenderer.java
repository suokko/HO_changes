package ho.module.specialEvents;

import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.ThemeManager;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

class SpecialEventsTableRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 6621498528069679319L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int col) {

		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, col);
		int column = table.convertColumnIndexToModel(col);
		if (column == SpecialEventsTableModel.RESULTCOLUMN) {
			label.setHorizontalAlignment(SwingConstants.CENTER);
		} else if (column == SpecialEventsTableModel.MINUTECOLUMN) {
			label.setHorizontalAlignment(SwingConstants.RIGHT);
		} else {
			label.setHorizontalAlignment(SwingConstants.LEFT);
		}
		label.setForeground(Color.BLACK);
		if (column == SpecialEventsTableModel.NAMECOLUMN) {
			String name = (String) value;
			if (!name.equals("")) {
				String rName = name.substring(0, name.length() - 2);
				String rType = name.substring(name.length() - 1);
				label.setText(rName);
				if (rType.equals("-")) {
					label.setForeground(Color.RED);
				} else if (rType.equals("#")) {
					label.setForeground(Color.LIGHT_GRAY);
				}
			}
		}
		int type = 0;
		try {
			type = Integer.parseInt((String) table.getValueAt(row,
					SpecialEventsTableModel.HIDDENCOLUMN));
		} catch (NumberFormatException numberformatexception) {
		}
		label.setBackground(ThemeManager.getColor(HOColorName.PLAYER_SUBPOS_BG));
		if (type == 1) {
			label.setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
		}
		if (column == SpecialEventsTableModel.HIDDENCOLUMN) {
			label.setIcon(null);
			label.setText(null);
		} else if (value != null && value instanceof ImageIcon) {
			label.setIcon((ImageIcon) value);
			label.setText(null);
		} else {
			label.setIcon(null);
		}
		return this;
	}

}
