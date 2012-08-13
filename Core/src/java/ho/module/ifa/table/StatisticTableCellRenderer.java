package ho.module.ifa.table;

import ho.core.gui.theme.ImageUtilities;
import ho.core.model.WorldDetailLeague;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class StatisticTableCellRenderer extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = -4623597445367469673L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		setIcon(null);
		setHorizontalAlignment(SwingConstants.CENTER);
		
		if ((value instanceof WorldDetailLeague)) {
			int countryId = ((WorldDetailLeague)value).getCountryId();
			setIcon(ImageUtilities.getFlagIcon(countryId));
			setHorizontalAlignment(SwingConstants.LEADING);
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}
