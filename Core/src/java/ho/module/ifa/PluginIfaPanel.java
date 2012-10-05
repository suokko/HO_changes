package ho.module.ifa;

import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailsManager;
import ho.core.module.config.ModuleConfig;
import ho.module.ifa.config.Config;
import ho.module.ifa.model.Country;
import ho.module.ifa.model.IfaModel;
import ho.module.ifa.model.IfaStatistic;
import ho.module.ifa.model.ModelChangeListener;
import ho.module.ifa.table.IfaTableCellRenderer;
import ho.module.ifa.table.IfaTableModel;
import ho.module.ifa.table.SummaryTableSorter;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PluginIfaPanel extends JPanel {

	private static final long serialVersionUID = 3806181337290704445L;
	private JLabel visitedLabel;
	private JLabel visitedCoolnessLabel;
	private JLabel hostedLabel;
	private JLabel hostedCoolnessLabel;
	private JSplitPane splitPane;
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

		this.splitPane.addPropertyChangeListener("dividerLocation", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (splitPane.getSize().height > 0 && splitPane.getDividerLocation() > 0) {
					double proportionalDividerLocation = 1.0 / (splitPane.getSize().height / splitPane
							.getDividerLocation());
					ModuleConfig.instance().setBigDecimal(
							Config.STATS_TABLES_DIVIDER_LOCATION.toString(),
							BigDecimal.valueOf(proportionalDividerLocation));
				}
			}
		});

		// setDividerLocation(double proportionalLocation) will only have an
		// effect if the split pane is correctly realized and on screen
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				double dividerLocation = ModuleConfig
						.instance()
						.getBigDecimal(Config.STATS_TABLES_DIVIDER_LOCATION.toString(),
								BigDecimal.valueOf(0.5)).doubleValue();
				splitPane.setDividerLocation(dividerLocation);
				removeComponentListener(this);
			}
		});
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel visitedStatsPanel = new JPanel();
		visitedStatsPanel.setLayout(new GridBagLayout());

		this.visitedLabel = new JLabel();
		Font boldFont = this.visitedLabel.getFont().deriveFont(
				this.visitedLabel.getFont().getStyle() ^ Font.BOLD);
		this.visitedLabel.setFont(boldFont);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 10, 3, 10);
		visitedStatsPanel.add(this.visitedLabel, gbc);

		this.visitedCoolnessLabel = new JLabel();
		this.visitedCoolnessLabel.setFont(boldFont);
		gbc.gridx = 1;
		visitedStatsPanel.add(this.visitedCoolnessLabel, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(3, 10, 5, 10);
		visitedStatsPanel.add(new JScrollPane(createTable(true, model)), gbc);

		JPanel hostedStatsPanel = new JPanel();
		hostedStatsPanel.setLayout(new GridBagLayout());
		this.hostedLabel = new JLabel();
		this.hostedLabel.setFont(boldFont);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 10, 3, 10);
		hostedStatsPanel.add(this.hostedLabel, gbc);

		this.hostedCoolnessLabel = new JLabel();
		this.hostedCoolnessLabel.setFont(boldFont);
		gbc.gridx = 1;
		hostedStatsPanel.add(this.hostedCoolnessLabel, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(3, 10, 10, 10);
		hostedStatsPanel.add(new JScrollPane(createTable(false, model)), gbc);

		RightPanel rightPanel = new RightPanel(model);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 4;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weightx = 0;

		this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.splitPane.add(visitedStatsPanel, 0);
		this.splitPane.add(hostedStatsPanel, 1);

		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(this.splitPane, gbc);

		gbc.gridx = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.BOTH;
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
		tblModel.setData(model, away);

		JTable table = new JTable(tblModel);
		IfaTableCellRenderer renderer = new IfaTableCellRenderer();
		table.getColumnModel().getColumn(0).setCellRenderer(renderer);
		table.getColumnModel().getColumn(1).setCellRenderer(renderer);
		table.getColumnModel().getColumn(2).setCellRenderer(renderer);
		table.getColumnModel().getColumn(3).setCellRenderer(renderer);
		table.getColumnModel().getColumn(4).setCellRenderer(renderer);
		table.getColumnModel().getColumn(5).setCellRenderer(renderer);
		table.getColumnModel().getColumn(6).setCellRenderer(renderer);

		TableRowSorter<TableModel> sorter = new SummaryTableSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
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

	private void setHeaderTexts() {
		NumberFormat doubleFormat = NumberFormat.getInstance();
		doubleFormat.setMaximumFractionDigits(2);
		doubleFormat.setMinimumFractionDigits(2);

		int totalCountries = WorldDetailsManager.instance().size();
		String txt = HOVerwaltung.instance().getLanguageString(
				"ifa.statisticsTable.header.visited", this.model.getVistedCountriesCount(),
				totalCountries);
		this.visitedLabel.setText(txt);

		String currentCoolness = doubleFormat.format(this.model.getVisitedSummary()
				.getCoolnessTotal());
		String maxCoolness = doubleFormat.format(this.model.getMaxCoolness());
		txt = HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.header.coolness",
				currentCoolness, maxCoolness);
		this.visitedCoolnessLabel.setText(txt);

		txt = HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.header.hosted",
				this.model.getHostedCountriesCount(), totalCountries);
		this.hostedLabel.setText(txt);

		currentCoolness = doubleFormat.format(this.model.getHostedSummary().getCoolnessTotal());
		txt = HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.header.coolness",
				currentCoolness, maxCoolness);
		this.hostedCoolnessLabel.setText(txt);
	}
}
