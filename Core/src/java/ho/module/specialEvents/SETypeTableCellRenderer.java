package ho.module.specialEvents;

import java.awt.Component;

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
			component.setIcon(SpecialEventsDM.getOwnerIcon(matchRow.getMatchHighlight(),
					!this.away, matchRow.getMatch().getHostingTeamId(), matchRow.getMatch()
							.getVisitingTeamId()));
		} else {
			component.setIcon(null);
		}
		return component;
	}

}
