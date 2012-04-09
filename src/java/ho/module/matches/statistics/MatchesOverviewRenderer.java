package ho.module.matches.statistics;

import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.match.MatchesHighlightsStat;
import ho.core.model.match.MatchesOverviewRow;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.hsqldb.lib.StringUtil;


class MatchesOverviewRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setText(value.toString());
		setIcon(null);
		setHorizontalAlignment(SwingConstants.CENTER);
		
		if(StringUtil.isEmpty(value.toString()))
			setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
		else
			setBackground(ThemeManager.getColor(HOColorName.MATCHTYPE_LEAGUE_BG));
		
		setForeground(ThemeManager.getColor(HOColorName.TABLEENTRY_FG));
		
		if(value instanceof MatchesOverviewRow){
			MatchesOverviewRow mrow = (MatchesOverviewRow)value;
			setHorizontalAlignment(SwingConstants.LEFT);
			if(mrow.getType() == MatchesOverviewRow.TYPE_WEATHER){
				setIcon(ThemeManager.getIcon(HOIconName.WEATHER[mrow.getTypeValue()]));
				setText("");
			}
			if(mrow.getType() == MatchesOverviewRow.TYPE_TITLE)
				setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
		}
		if(value instanceof MatchesHighlightsStat){
			MatchesHighlightsStat mrow = (MatchesHighlightsStat)value;
			if(mrow.isTitle())
				setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
		}
		
		
		return this;
	}

}
