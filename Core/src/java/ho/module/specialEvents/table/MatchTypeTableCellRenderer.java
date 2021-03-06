package ho.module.specialEvents.table;

import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.match.MatchType;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MatchTypeTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -703626020830654L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		JLabel component = (JLabel) super.getTableCellRendererComponent(table, "", isSelected,
				hasFocus, row, column);

		MatchType matchType = (MatchType) value;
		if (matchType != null) {
			component.setIcon(ThemeManager.getIcon(HOIconName.MATCHTYPES[matchType
					.getIconArrayIndex()]));
		} else {
			component.setIcon(null);
		}
		RowColorDecorator.decorate(table, row, component, isSelected);
		return component;
	}
}
