package ho.module.lineup.penalties;

import ho.core.gui.comp.table.RowNumberTable;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;
import ho.core.util.GUIUtils;
import ho.module.lineup.Lineup;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class PenaltyTakersView extends JPanel {

	private static final long serialVersionUID = -5089904466636200088L;
	private JTable penaltyTakersTable;
	private Lineup lineup;
	private JButton autoButton;
	private JButton moveUpButton;
	private JButton moveDownButton;
	private JCheckBox showAnfangsElfCheckBox;
	private JCheckBox showReserveCheckBox;
	private JCheckBox showOthersCheckBox;

	public PenaltyTakersView() {
		initComponents();
		addListeners();
	}

	public void setPlayers(List<Spieler> players) {
		List<PenaltyTaker> takers = new ArrayList<PenaltyTaker>();
		for (Spieler player : players) {
			takers.add(new PenaltyTaker(player));
		}
		getTableModel().setPenaltyTakers(takers);
		getTableModel().fireTableDataChanged();
	}

	public void setLineup(Lineup lineup) {
		this.lineup = lineup;
		getTableModel().fireTableDataChanged();
	}

	public void auto() {
		disableSortingAndFiltering();
		getTableModel().bestFit();
		enableSortingAndFiltering();
	}

	private void enableSortingAndFiltering() {
		this.penaltyTakersTable.setAutoCreateRowSorter(true);
		setRowFilter();
		// without repaint the sort indicator will not disappear
		this.penaltyTakersTable.getTableHeader().repaint();
	}

	private void disableSortingAndFiltering() {
		this.penaltyTakersTable.setRowSorter(null);
		this.penaltyTakersTable.setAutoCreateRowSorter(false);
	}

	private void initComponents() {
		setLayout(new GridBagLayout());

		this.penaltyTakersTable = new JTable();
		this.penaltyTakersTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.penaltyTakersTable.setModel(new PenaltyTakersTableModel());
		this.penaltyTakersTable.setAutoCreateRowSorter(true);

		TableColumn abilityColumn = this.penaltyTakersTable.getColumnModel()
				.getColumn(5);
		abilityColumn.setCellRenderer(new AbilityRenderer());
		TableColumn inLineupColumn = this.penaltyTakersTable.getColumnModel()
				.getColumn(0);
		inLineupColumn.setCellRenderer(new InLineupRenderer());
		inLineupColumn.setMaxWidth(20);

		JPanel filterPanel = new JPanel(new GridBagLayout());
		filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));
		this.showAnfangsElfCheckBox = new JCheckBox("Show Anfangself");
		this.showAnfangsElfCheckBox.setSelected(true);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 10, 2, 10);
		filterPanel.add(this.showAnfangsElfCheckBox, gbc);
		this.showReserveCheckBox = new JCheckBox("Show reserve");
		this.showReserveCheckBox.setSelected(true);

		gbc.gridy = 1;
		gbc.insets = new Insets(2, 10, 2, 10);
		filterPanel.add(this.showReserveCheckBox, gbc);
		this.showOthersCheckBox = new JCheckBox("Show other");
		this.showOthersCheckBox.setSelected(true);
		gbc.gridy = 2;
		gbc.insets = new Insets(2, 10, 10, 10);
		gbc.weightx = 1;
		gbc.weighty = 1;
		filterPanel.add(this.showOthersCheckBox, gbc);

		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		JScrollPane scrollPane = new JScrollPane(this.penaltyTakersTable);
		JTable rowTable = new RowNumberTable(this.penaltyTakersTable);
		scrollPane.setRowHeaderView(rowTable);
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
				rowTable.getTableHeader());
		add(scrollPane, gbc);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.NONE;
		add(createButtonsPanel(), gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(filterPanel, gbc);
		setRowFilter();
	}

	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel(new GridBagLayout());
		this.autoButton = new JButton("let me do it for you");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 8, 4, 10);
		buttonsPanel.add(this.autoButton, gbc);

		this.moveUpButton = new JButton("move up");
		this.moveUpButton.setIcon(ThemeManager.getIcon(HOIconName.MOVE_UP));
		this.moveUpButton.setEnabled(false);
		gbc = new GridBagConstraints();
		gbc.gridy = 1;
		gbc.insets = new Insets(4, 8, 4, 10);
		buttonsPanel.add(this.moveUpButton, gbc);

		this.moveDownButton = new JButton("move down");
		this.moveDownButton.setIcon(ThemeManager.getIcon(HOIconName.MOVE_DOWN));
		this.moveDownButton.setEnabled(false);
		gbc = new GridBagConstraints();
		gbc.gridy = 2;
		gbc.insets = new Insets(4, 8, 4, 10);
		gbc.weighty = 1.0;
		buttonsPanel.add(this.moveDownButton, gbc);

		GUIUtils.equalizeComponentSizes(this.autoButton, this.moveUpButton,
				this.moveDownButton);

		return buttonsPanel;
	}

	@SuppressWarnings("unchecked")
	private void setRowFilter() {
		TableRowSorter<PenaltyTakersTableModel> rowSorter = ((TableRowSorter<PenaltyTakersTableModel>) this.penaltyTakersTable
				.getRowSorter());

		rowSorter
				.setRowFilter(new RowFilter<PenaltyTakersTableModel, Integer>() {

					@Override
					public boolean include(
							RowFilter.Entry<? extends PenaltyTakersTableModel, ? extends Integer> entry) {
						PenaltyTakersTableModel personModel = entry.getModel();
						PenaltyTaker taker = personModel.getPenaltyTaker(entry
								.getIdentifier());
						if (showAnfangsElfCheckBox.isSelected()
								&& getInLineupVal(taker.getPlayer()).intValue() == 1) {
							return true;
						}
						if (showReserveCheckBox.isSelected()
								&& getInLineupVal(taker.getPlayer()).intValue() == 2) {
							return true;
						}
						if (showOthersCheckBox.isSelected()
								&& getInLineupVal(taker.getPlayer()).intValue() == 3) {
							return true;
						}
						return false;
					}
				});
	}

	private PenaltyTakersTableModel getTableModel() {
		return (PenaltyTakersTableModel) this.penaltyTakersTable.getModel();
	}

	private void addListeners() {
		this.penaltyTakersTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent evt) {
						if (!evt.getValueIsAdjusting()) {
							int selectedRow = penaltyTakersTable
									.getSelectedRow();
							if (selectedRow == -1) {
								moveUpButton.setEnabled(false);
								moveDownButton.setEnabled(false);
							} else {
								moveUpButton.setEnabled((selectedRow > 0));
								moveDownButton
										.setEnabled(selectedRow < penaltyTakersTable
												.getRowCount() - 1);
							}
						}
					}
				});

		this.autoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				auto();
			}
		});

		this.moveUpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				moveTaker(Move.UP);
			}
		});

		this.moveDownButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				moveTaker(Move.DOWN);
			}
		});

		ItemListener filterCheckBoxListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				getTableModel().fireTableDataChanged();
			}
		};
		this.showAnfangsElfCheckBox.addItemListener(filterCheckBoxListener);
		this.showReserveCheckBox.addItemListener(filterCheckBoxListener);
		this.showOthersCheckBox.addItemListener(filterCheckBoxListener);
	}

	private PenaltyTaker getSelectedTaker() {
		int viewRowIndex = this.penaltyTakersTable.getSelectedRow();
		if (viewRowIndex != -1) {
			int modelRowIndex = this.penaltyTakersTable
					.convertRowIndexToModel(viewRowIndex);
			return getTableModel().getPenaltyTaker(modelRowIndex);
		}
		return null;
	}

	private void moveTaker(Move move) {
		int viewRowIndex = this.penaltyTakersTable.getSelectedRow();
		if ((move == Move.UP && viewRowIndex > 0)
				|| (move == Move.DOWN && viewRowIndex < this.penaltyTakersTable
						.getRowCount() - 1)) {
			PenaltyTakersTableModel model = getTableModel();
			// get the taker before the current one
			int otherViewRowIndex;
			if (move == Move.UP) {
				otherViewRowIndex = --viewRowIndex;
			} else {
				otherViewRowIndex = ++viewRowIndex;
			}
			PenaltyTaker otherTaker = model
					.getPenaltyTaker(this.penaltyTakersTable
							.convertRowIndexToModel(otherViewRowIndex));

			PenaltyTaker taker = getSelectedTaker();

			// disable row filtering
			((TableRowSorter<PenaltyTakersTableModel>) this.penaltyTakersTable
					.getRowSorter()).setRowFilter(null);

			// recreate the model data based on the current (unfiltered) view
			// and move the take to his new position
			List<PenaltyTaker> list = new ArrayList<PenaltyTaker>(
					model.getRowCount());
			for (int i = 0; i < this.penaltyTakersTable.getRowCount(); i++) {
				list.add(model.getPenaltyTaker(this.penaltyTakersTable
						.convertRowIndexToModel(i)));
			}
			list.remove(taker);
			if (move == Move.UP) {
				list.add(list.indexOf(otherTaker), taker);
			} else {
				list.add(list.indexOf(otherTaker) + 1, taker);
			}

			// clear current sort settings in the tabel
			this.penaltyTakersTable.setRowSorter(null);
			// enable sorting and filtering
			this.penaltyTakersTable.setAutoCreateRowSorter(true);
			setRowFilter();

			model.setPenaltyTakers(list);
			selectPenaltyTaker(taker);
		}
	}

	private void selectPenaltyTaker(PenaltyTaker taker) {
		PenaltyTakersTableModel model = getTableModel();
		int modelIndex = -1;
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getPenaltyTaker(i) == taker) {
				modelIndex = i;
				break;
			}
		}
		if (modelIndex != -1) {
			int viewIndex = this.penaltyTakersTable
					.convertRowIndexToView(modelIndex);
			this.penaltyTakersTable.getSelectionModel().setSelectionInterval(
					viewIndex, viewIndex);
		}
	}

	private Integer getInLineupVal(Spieler player) {
		if (lineup != null) {
			int playerId = player.getSpielerID();
			if (lineup.isSpielerInAnfangsElf(playerId)) {
				return Integer.valueOf(1);
			} else if (lineup.isSpielerInReserve(playerId)) {
				return Integer.valueOf(2);
			}
		}
		return Integer.valueOf(3);
	}

	private class PenaltyTakersTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 3044881352777003621L;
		private String[] columnNames;
		private List<PenaltyTaker> data = new ArrayList<PenaltyTaker>();

		public PenaltyTakersTableModel() {
			this.columnNames = new String[6];
			this.columnNames[0] = "";
			this.columnNames[1] = HOVerwaltung.instance().getLanguageString(
					"Name");
			this.columnNames[2] = HOVerwaltung.instance().getLanguageString(
					"Erfahrung");
			this.columnNames[3] = HOVerwaltung.instance().getLanguageString(
					"Standards");
			this.columnNames[4] = HOVerwaltung.instance().getLanguageString(
					"Torschuss");
			this.columnNames[5] = HOVerwaltung.instance().getLanguageString(
					"lineup.penaltytakers.colheadline.ability");
		}

		public void bestFit() {
			Comparator<PenaltyTaker> comparator = new Comparator<PenaltyTaker>() {

				@Override
				public int compare(PenaltyTaker o1, PenaltyTaker o2) {
					int inLineupVal1 = getInLineupVal(o1.getPlayer())
							.intValue();
					int inLineupVal2 = getInLineupVal(o2.getPlayer())
							.intValue();
					if (inLineupVal1 == inLineupVal2) {
						if (o1.getAbility() > o2.getAbility()) {
							return -1;
						} else if (o1.getAbility() < o2.getAbility()) {
							return 1;
						}
					} else {
						return inLineupVal1 - inLineupVal2;
					}
					return 0;
				}
			};
			Collections.sort(this.data, comparator);
			fireTableDataChanged();
		}

		public void setPenaltyTakers(List<PenaltyTaker> takers) {
			this.data = takers;
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return this.data.size();
		}

		@Override
		public int getColumnCount() {
			return this.columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			PenaltyTaker taker = this.data.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return getInLineupVal(taker.getPlayer());
			case 1:
				return taker.getPlayer().getName();
			case 2:
				return taker.getPlayer().getErfahrung();
			case 3:
				return taker.getPlayer().getStandards();
			case 4:
				return taker.getPlayer().getTorschuss();
			case 5:
				return taker.getAbility();
			default:
				return "";
			}
		}

		@Override
		public String getColumnName(int column) {
			return this.columnNames[column];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Integer.class;
			case 1:
				return String.class;
			case 2:
			case 3:
			case 4:
				return Integer.class;
			case 5:
				return Float.class;
			default:
				return Object.class;
			}
		}

		public PenaltyTaker getPenaltyTaker(int rowIndex) {
			return this.data.get(rowIndex);
		}
	}

	// private class MyRowFilter extends
	// RowFilter<PenaltyTakersTableModel, Integer> {
	//
	// @Override
	// public boolean include(
	// RowFilter.Entry<? extends PenaltyTakersTableModel, ? extends Integer>
	// entry) {
	// PenaltyTakersTableModel personModel = entry.getModel();
	// PenaltyTaker taker = personModel.getPenaltyTaker(entry
	// .getIdentifier());
	// System.out.println("####- " + entry.getIdentifier() + " - " +
	// taker.getPlayer().getName());
	// if (showAnfangsElfCheckBox.isSelected()
	// && getInLineupVal(taker.getPlayer()).intValue() == 1) {
	// return true;
	// }
	// if (showReserveCheckBox.isSelected()
	// && getInLineupVal(taker.getPlayer()).intValue() == 2) {
	// return true;
	// }
	// if (showOthersCheckBox.isSelected()
	// && getInLineupVal(taker.getPlayer()).intValue() == 3) {
	// return true;
	// }
	// return false;
	// }
	// }

	private class InLineupRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 2815809080926324953L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			JLabel component = (JLabel) super.getTableCellRendererComponent(
					table, "", isSelected, hasFocus, row, column);
			int val = ((Integer) value).intValue();
			switch (val) {
			case 1:
				component.setIcon(ThemeManager
						.getIcon(HOIconName.PLAYS_AT_BEGINNING));
				break;
			case 2:
				component.setIcon(ThemeManager.getIcon(HOIconName.IS_RESERVE));
				break;
			case 3:
				component.setIcon(ThemeManager
						.getIcon(HOIconName.NOT_IN_LINEUP));
				break;
			default:
				component.setIcon(null);
			}
			return component;
		}

	}

	private class AbilityRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = -9094435304652745951L;
		private NumberFormat format;

		public AbilityRenderer() {
			this.format = NumberFormat.getNumberInstance();
			this.format.setMinimumFractionDigits(2);
			this.format.setMaximumFractionDigits(2);
			setHorizontalAlignment(SwingConstants.RIGHT);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Float ability = (Float) value;
			String str = this.format.format(ability);
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	private enum Move {
		UP, DOWN;
	}
}
