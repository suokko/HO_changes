package ho.module.specialEvents;

import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.match.IMatchHighlight;
import ho.core.model.match.MatchHighlight;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ChanceTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -7036262083065436L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		JLabel component = (JLabel) super.getTableCellRendererComponent(table, "", isSelected,
				hasFocus, row, column);

		Icon icon = null;
		MatchLine matchRow = (MatchLine)value;
		MatchHighlight highlight = matchRow.getMatchHighlight();
		if (highlight != null) {
			if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_ERFOLGREICH) {
				icon = ThemeManager.getIcon(HOIconName.GOAL);
			} else if (highlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_FEHLGESCHLAGEN) {
				icon = ThemeManager.getIcon(HOIconName.NOGOAL);
			} else if (SpecialEventsDM.isWeatherSE(highlight)) {
				icon = ThemeManager.getIcon(HOIconName.WEATHER[matchRow.getMatch().getWeather().getId()]);
			}
		}
		component.setIcon(icon);
		return component;
	}

}
