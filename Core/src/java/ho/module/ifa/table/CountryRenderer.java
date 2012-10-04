package ho.module.ifa.table;

import ho.module.ifa.model.Country;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CountryRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 571839185052133812L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
		Country country = (Country) value;
		label.setText(country.getName());
		label.setIcon(country.getCountryFlag());
		return label;
	}
}
