package ho.module.ifa2;

import ho.module.ifa2.model.Country;
import ho.module.ifa2.model.IfaModel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Comparator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PluginIfaPanel extends JPanel {

	private static final long serialVersionUID = 3806181337290704445L;

	public PluginIfaPanel() {
		initialize();
	}

	private void initialize() {
		IfaModel model = new IfaModel();
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(10, 10, 10, 10);

		add(new JScrollPane(createTable(true, model)), gbc);

		gbc.gridy = 1;
		add(new JScrollPane(createTable(false, model)), gbc);

		RightPanel rightPanel = new RightPanel(model);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weightx = 0;
		add(rightPanel, gbc);
	}

	private JTable createTable(boolean away, IfaModel model) {
		IfaTableModel tblModel;
		if (away) {
			tblModel = new IfaTableModel(model.getVisitedStatistic());
		} else {
			tblModel = new IfaTableModel(model.getHostedStatistic());
		}
		JTable table = new JTable(tblModel);
		table.getColumnModel().getColumn(0).setCellRenderer(new CountryRenderer());

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
		table.setRowSorter(sorter);
		sorter.setModel(tblModel);
		sorter.setComparator(0, new Comparator<Country>() {

			@Override
			public int compare(Country o1, Country o2) {
				return o1.getName().compareTo(o2.getName());
			}

		});
		return table;
	}

	private class CountryRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 571839185052133812L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {

			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			Country country = (Country) value;
			label.setText(country.getName());
			label.setIcon(country.getCountryFlag());
			return label;
		}
	}
}
