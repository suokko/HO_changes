package ho.module.ifa;

import ho.core.gui.comp.renderer.DateTimeTableCellRenderer;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailsManager;
import ho.module.ifa.model.Country;
import ho.module.ifa.model.IfaModel;
import ho.module.ifa.model.IfaStatistic;
import ho.module.ifa.model.ModelChangeListener;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PluginIfaPanel extends JPanel {

	private static final long serialVersionUID = 3806181337290704445L;
	private JLabel visitedHeaderLabel;
	private JLabel hostedHeaderLabel;
	private final IfaModel model;

	public PluginIfaPanel() {
		this.model = new IfaModel();
		initialize();
		setHeaderTexts();
		addListeners();
	}

	private void addListeners() {
		this.model.addModelChangeListener(new ModelChangeListener() {

			@Override
			public void modelChanged() {
				setHeaderTexts();
			}
		});
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		this.visitedHeaderLabel = new JLabel();
		Font boldFont = this.visitedHeaderLabel.getFont().deriveFont(
				this.visitedHeaderLabel.getFont().getStyle() ^ Font.BOLD);
		this.visitedHeaderLabel.setFont(boldFont);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 10, 3, 10);
		add(this.visitedHeaderLabel, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0.5;
		gbc.insets = new Insets(3, 10, 5, 10);
		add(new JScrollPane(createTable(true, model)), gbc);

		this.hostedHeaderLabel = new JLabel();
		this.hostedHeaderLabel.setFont(boldFont);
		gbc.gridy = 2;
		gbc.weighty = 0;
		gbc.insets = new Insets(5, 10, 3, 10);
		add(this.hostedHeaderLabel, gbc);

		gbc.gridy = 3;
		gbc.weighty = 0.5;
		gbc.insets = new Insets(3, 10, 10, 10);
		add(new JScrollPane(createTable(false, model)), gbc);

		RightPanel rightPanel = new RightPanel(model);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 4;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weightx = 0;
		add(rightPanel, gbc);
	}

	private JTable createTable(final boolean away, final IfaModel model) {
		List<IfaStatistic> data;
		if (away) {
			data = model.getVisitedStatistic();
		} else {
			data = model.getHostedStatistic();
		}

		final IfaTableModel tblModel = new IfaTableModel();
		tblModel.setData(data);

		// refresh tables on model changes
		model.addModelChangeListener(new ModelChangeListener() {

			@Override
			public void modelChanged() {
				if (away) {
					tblModel.setData(model.getVisitedStatistic());
				} else {
					tblModel.setData(model.getHostedStatistic());
				}
			}
		});

		JTable table = new JTable(tblModel);
		table.getColumnModel().getColumn(0).setCellRenderer(new CountryRenderer());
		table.getColumnModel().getColumn(5).setCellRenderer(new DateTimeTableCellRenderer());

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
		table.setRowSorter(sorter);
		sorter.setModel(tblModel);
		sorter.setComparator(0, new Comparator<Country>() {

			@Override
			public int compare(Country o1, Country o2) {
				return o1.getName().compareTo(o2.getName());
			}

		});
		List<SortKey> list = new ArrayList<SortKey>();
		list.add(new SortKey(5, SortOrder.DESCENDING));
		sorter.setSortKeys(list);
		
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

	private void setHeaderTexts() {
		int totalCountries = WorldDetailsManager.instance().size();
		String txt = HOVerwaltung.instance().getLanguageString(
				"ifa.statisticsTable.header.visited", this.model.getVistedCountriesCount(),
				totalCountries);
		this.visitedHeaderLabel.setText(txt);

		txt = HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.header.hosted",
				this.model.getHostedCountriesCount(), totalCountries);
		this.hostedHeaderLabel.setText(txt);
	}
}
