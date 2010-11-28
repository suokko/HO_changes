package hoplugins.specialEvents;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class SpecialEventsTableRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 6621498528069679319L;
	public static Color Color1 = new Color(220, 220, 255);
	public static Color Color2 = new Color(255, 255, 255);

	public SpecialEventsTableRenderer() {
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (column == SpecialEventsPanel.RESULTCOLUMN) {
			setHorizontalAlignment(SwingConstants.CENTER);
		} else if (column == SpecialEventsPanel.MINUTECOLUMN) {
			setHorizontalAlignment(SwingConstants.RIGHT);
		} else {
			setHorizontalAlignment(SwingConstants.LEFT);
		}
		setForeground(Color.BLACK);
		if (column == SpecialEventsPanel.NAMECOLUMN) {
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
			type = Integer.parseInt((String) table.getValueAt(row, SpecialEventsPanel.HIDDENCOLUMN));
		} catch (NumberFormatException numberformatexception) {
		}
		setBackground(Color1);
		if (type == -1) {
			setBackground(Color1);
		}
		if (type == 1) {
			setBackground(Color2);
		}
		if (column == SpecialEventsPanel.HIDDENCOLUMN) {
			setIcon(null);
			setText(null);
		} else if (value != null && value instanceof ImageIcon) {
			setIcon((ImageIcon)value);
			setText(null);
		} else {
			setIcon(null);
		}
		return this;
	}

}
