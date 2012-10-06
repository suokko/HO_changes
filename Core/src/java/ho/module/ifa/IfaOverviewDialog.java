package ho.module.ifa;

import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.module.ifa.model.Country;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class IfaOverviewDialog extends JDialog {

	private static final long serialVersionUID = 5745450861289812050L;

	public IfaOverviewDialog() {
		initComponents();
		pack();
	}

	private void initComponents() {
		getContentPane().setLayout(new BorderLayout());
		JTable table = new JTable(new MyTableModel());
		table.getColumnModel().getColumn(0).setCellRenderer(new CountryTableCellRenderer());
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
	}

	private class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 4643461935740184896L;
		private final List<Entry> list;
		private String[] columns = { "Country", "Active users", "Coolness" };

		MyTableModel() {
			WorldDetailLeague[] leagues = WorldDetailsManager.instance().getLeagues();
			this.list = new ArrayList<Entry>(leagues.length);

			for (WorldDetailLeague league : leagues) {
				Entry entry = new Entry();
				entry.country = new Country(league.getCountryId());
				entry.league = league;
				entry.coolness = PluginIfaUtils.getCoolness(entry.country.getCountryId());
				this.list.add(entry);
			}
		}

		@Override
		public String getColumnName(int columnIndex) {
			return this.columns[columnIndex];
		}

		@Override
		public int getRowCount() {
			return this.list.size();
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Entry entry = this.list.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return entry.country;
			case 1:
				return entry.league.getActiveUsers();
			case 2:
				return entry.coolness;
			default:
				return null;
			}
		}
	}

	private class Entry {
		Country country;
		WorldDetailLeague league;
		double coolness;
	}

	private class CountryTableCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = -5212837673330509051L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {

			Country country = (Country) value;
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, country.getName(),
					isSelected, hasFocus, row, column);
			label.setIcon(country.getCountryFlag());
			return label;
		}
	}
}
