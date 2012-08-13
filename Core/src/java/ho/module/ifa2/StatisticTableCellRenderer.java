package ho.module.ifa2;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class StatisticTableCellRenderer implements TableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if ((value instanceof JLabel)) {
			return (JLabel) value;
		}

		return new JLabel(value.toString());
	}
}
