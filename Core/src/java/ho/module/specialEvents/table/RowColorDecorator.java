package ho.module.specialEvents.table;

import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.ThemeManager;
import ho.module.specialEvents.MatchRow;
import ho.module.specialEvents.SpecialEventsTableModel;

import java.awt.Component;

import javax.swing.JTable;

public class RowColorDecorator {

	static MatchRow getMatchLine(JTable table, int row) {
		SpecialEventsTableModel model = (SpecialEventsTableModel) table.getModel();
		return model.getMatchRow(table.convertRowIndexToModel(row));
	}

	static void decorate(JTable table, int row, Component component, boolean isSelected) {
		if (!isSelected) {
			MatchRow matchRow = getMatchLine(table, row);
			if (matchRow.getMatchCount() % 2 == 0) {
				component.setBackground(ThemeManager.getColor(HOColorName.PLAYER_SUBPOS_BG));
			} else {
				component.setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
			}
		} else {
			component.setBackground(table.getSelectionBackground());
		}
	}

}
