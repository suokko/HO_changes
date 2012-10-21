package ho.module.specialEvents;

import ho.core.model.match.MatchHighlight;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class EventTypeTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -70362602083065436L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		JLabel component = (JLabel) super.getTableCellRendererComponent(table, "", isSelected,
				hasFocus, row, column);

		MatchHighlight matchHighlight = (MatchHighlight) value;
		if (matchHighlight != null) {
			component.setIcon(SpecialEventsDM.getEventTypIcon(matchHighlight));
		} else {
			component.setIcon(null);
		}
		return component;
	}

}
