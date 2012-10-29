package ho.module.specialEvents.table;

import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.match.MatchHighlight;
import ho.module.specialEvents.MatchLine;
import ho.module.specialEvents.SpecialEventsDM;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class SETypeTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -70362602083065436L;
	private final boolean away;

	public SETypeTableCellRenderer(boolean away) {
		this.away = away;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		JLabel component = (JLabel) super.getTableCellRendererComponent(table, "", isSelected,
				hasFocus, row, column);

		MatchLine matchRow = (MatchLine) value;
		if (matchRow.getMatchHighlight() != null) {
			component.setIcon(getOwnerIcon(matchRow.getMatchHighlight(), !this.away, matchRow
					.getMatch().getHostingTeamId(), matchRow.getMatch().getVisitingTeamId()));
		} else {
			component.setIcon(null);
		}
		RowColorDecorator.decorate(table, row, component);
		return component;
	}

	private ImageIcon getOwnerIcon(MatchHighlight highlight, boolean home, int heimId, int gastId) {
		ImageIcon icon = null;
		if (home) {
			// Create home icon
			if (highlight.getTeamID() == heimId) {
				if (SpecialEventsDM.isPositiveWeatherSE(highlight)) {
					// Positive weather SE for home team
					return ThemeManager.getIcon(HOIconName.ARROW_RIGHT1);
				} else if (SpecialEventsDM.isNegativeWeatherSE(highlight)) {
					// Negative weather SE for home team
					return ThemeManager.getIcon(HOIconName.ARROW_RIGHT2);
				} else if (!SpecialEventsDM.isNegativeSE(highlight)) {
					// Positive non-weather SE for home
					return ThemeManager.getIcon(HOIconName.ARROW_RIGHT1);
				}
			} else {
				if (!SpecialEventsDM.isWeatherSE(highlight)
						&& SpecialEventsDM.isNegativeSE(highlight)) {
					// Negative non-weather SE against home team
					return ThemeManager.getIcon(HOIconName.ARROW_RIGHT2);
				}
			}
		} else {
			// Create guest icon
			if (highlight.getTeamID() == gastId) {
				if (SpecialEventsDM.isPositiveWeatherSE(highlight)) {
					// Positive weather SE for guest team
					ThemeManager.getIcon(HOIconName.ARROW_LEFT1);
				} else if (SpecialEventsDM.isNegativeWeatherSE(highlight)) {
					// Negative weather SE for guest team
					return ThemeManager.getIcon(HOIconName.ARROW_LEFT2);
				} else if (!SpecialEventsDM.isNegativeSE(highlight)) {
					// Positive non-weather SE for guest
					return ThemeManager.getIcon(HOIconName.ARROW_LEFT1);
				}
			} else {
				if (!SpecialEventsDM.isWeatherSE(highlight)
						&& SpecialEventsDM.isNegativeSE(highlight)) {
					// Negative non-weather SE against guest team
					return ThemeManager.getIcon(HOIconName.ARROW_LEFT2);
				}
			}
		}

		return null;
	}
}
