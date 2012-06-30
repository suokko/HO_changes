package ho.module.lineup.substitution;

import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.util.GUIUtilities;
import ho.module.lineup.Lineup;
import ho.module.lineup.substitution.model.MatchOrderType;
import ho.module.lineup.substitution.model.Substitution;
import ho.module.lineup.substitution.plausibility.Error;
import ho.module.lineup.substitution.plausibility.PlausibilityCheck;
import ho.module.lineup.substitution.plausibility.Problem;
import ho.module.lineup.substitution.plausibility.Uncertainty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class SubstitutionOverview extends JPanel {

	private static final long serialVersionUID = -625638866350314110L;
	private JTable substitutionTable;
	private JTextArea commentsTextArea;
	private DetailsView detailsView;
	private EditAction editAction;
	private RemoveAction removeAction;
	private BehaviorAction behaviorAction;
	private PositionSwapAction positionSwapAction;
	private SubstitutionAction substitutionAction;
	private Lineup lineup;

	public SubstitutionOverview(Lineup lineup) {
		this.lineup = lineup;
		createActions();
		initComponents();
		addListeners();
		refresh();

		if (this.substitutionTable.getRowCount() > 0) {
			this.substitutionTable.getSelectionModel().setSelectionInterval(0, 0);
		}
	}

	public void setLineup(Lineup lineup) {
		this.lineup = lineup;
		if (this.substitutionTable.getRowCount() > 0) {
			this.substitutionTable.getSelectionModel().setSelectionInterval(0, 0);
		}
		refresh();
	}

	private void createActions() {
		this.editAction = new EditAction();
		this.editAction.setEnabled(false);
		this.removeAction = new RemoveAction();
		this.removeAction.setEnabled(false);
		this.behaviorAction = new BehaviorAction();
		this.positionSwapAction = new PositionSwapAction();
		this.substitutionAction = new SubstitutionAction();
	}

	private void refresh() {
		SubstitutionsTableModel model = (SubstitutionsTableModel) this.substitutionTable.getModel();
		model.setData(this.lineup.getSubstitutionList());

		for (int i = 0; i < model.getRowCount(); i++) {
			TableRow row = model.getRow(i);
			row.setProblem(PlausibilityCheck.checkForProblem(this.lineup, row.getSubstitution()));
		}
		detailsView.refresh();

		// allow a max. of 5 subs/orders
		boolean enableCreateActions = this.lineup.getSubstitutionList().size() < 5;
		this.behaviorAction.setEnabled(enableCreateActions);
		this.positionSwapAction.setEnabled(enableCreateActions);
		this.substitutionAction.setEnabled(enableCreateActions);
	}

	private void addListeners() {
		this.substitutionTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (!e.getValueIsAdjusting()) {
							tableSelectionChanged();
						}
					}
				});
	}

	private void tableSelectionChanged() {
		int selectedRowIndex = this.substitutionTable.getSelectedRow();
		boolean enable = false;
		TableRow tableRow = null;
		if (selectedRowIndex != -1) {
			tableRow = ((SubstitutionsTableModel) this.substitutionTable.getModel())
					.getRow(selectedRowIndex);
			enable = true;
		}
		this.editAction.setEnabled(enable);
		this.removeAction.setEnabled(enable);
		if (tableRow != null) {
			this.detailsView.setSubstitution(tableRow.getSubstitution());
		} else {
			this.detailsView.setSubstitution(null);
		}

		if (tableRow != null && (tableRow.isError() || tableRow.isUncertain())) {
			this.commentsTextArea.setText(PlausibilityCheck.getComment(tableRow.getProblem(),
					tableRow.getSubstitution()));
		} else {
			this.commentsTextArea.setText("");
		}
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		this.substitutionTable = new JTable();
		this.substitutionTable.setModel(new SubstitutionsTableModel());
		this.substitutionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.substitutionTable.getColumnModel().getColumn(0).setCellRenderer(new WarningRenderer());
		this.substitutionTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		this.substitutionTable.getColumnModel().getColumn(0).setMaxWidth(25);

		JPanel lowerPanel = new JPanel(new GridBagLayout());
		this.detailsView = new DetailsView();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weighty = 1.0;
		lowerPanel.add(this.detailsView, gbc);

		this.commentsTextArea = new JTextArea();
		this.commentsTextArea.setEditable(false);
		this.commentsTextArea.setOpaque(false);
		this.commentsTextArea.setBackground(new Color(0, 0, 0, 0));
		this.commentsTextArea.setBorder(null);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 10, 10, 10);
		lowerPanel.add(this.commentsTextArea, gbc);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.add(new JScrollPane(this.substitutionTable), 0);
		splitPane.add(lowerPanel, 1);
		splitPane.setDividerLocation(160);

		add(splitPane, BorderLayout.CENTER);
		add(getButtonPanel(), BorderLayout.EAST);
	}

	private JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel(new GridBagLayout());

		JButton editButton = new JButton();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 10, 2, 10);
		editButton.setAction(this.editAction);
		buttonPanel.add(editButton, gbc);

		JButton removeButton = new JButton();
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 10, 10);
		removeButton.setAction(this.removeAction);
		buttonPanel.add(removeButton, gbc);

		JButton substitutionButton = new JButton();
		gbc.gridy++;
		gbc.insets = new Insets(10, 10, 2, 10);
		buttonPanel.add(substitutionButton, gbc);
		substitutionButton.setAction(this.substitutionAction);

		JButton behaviorButton = new JButton();
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 10);
		buttonPanel.add(behaviorButton, gbc);
		behaviorButton.setAction(this.behaviorAction);

		JButton positionSwapButton = new JButton();
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 10);
		gbc.weighty = 1.0;
		buttonPanel.add(positionSwapButton, gbc);
		positionSwapButton.setAction(this.positionSwapAction);

		GUIUtilities.equalizeComponentSizes(editButton, removeButton, substitutionButton,
				behaviorButton, positionSwapButton);

		return buttonPanel;
	}

	private void doNewOrder(MatchOrderType orderType) {
		SubstitutionEditDialog dlg = getSubstitutionEditDialog(orderType);
		dlg.setLocationRelativeTo(SubstitutionOverview.this);
		dlg.setVisible(true);

		if (!dlg.isCanceled()) {
			Substitution sub = dlg.getSubstitution();
			this.lineup.getSubstitutionList().add(sub);
			refresh();

			SubstitutionsTableModel model = (SubstitutionsTableModel) this.substitutionTable
					.getModel();

			for (int i = 0; i < model.getRowCount(); i++) {
				if (model.getRow(i).getSubstitution() == sub) {
					this.substitutionTable.getSelectionModel().setSelectionInterval(i, i);
				}
			}

		}
	}

	private SubstitutionEditDialog getSubstitutionEditDialog(MatchOrderType orderType) {
		SubstitutionEditDialog dlg = null;
		Window windowAncestor = SwingUtilities.getWindowAncestor(SubstitutionOverview.this);
		if (windowAncestor instanceof Frame) {
			dlg = new SubstitutionEditDialog((Frame) windowAncestor, orderType);
		} else {
			dlg = new SubstitutionEditDialog((Dialog) windowAncestor, orderType);
		}
		return dlg;
	}

	private void selectSubstitution(Substitution sub) {
		SubstitutionsTableModel tblModel = (SubstitutionsTableModel) this.substitutionTable
				.getModel();
		for (int i = 0; i < tblModel.getRowCount(); i++) {
			if (tblModel.getRow(i).getSubstitution() == sub) {
				this.substitutionTable.getSelectionModel().setSelectionInterval(i, i);
			}
		}
	}

	/**
	 * TableModel for the overview table where existing substitutions are
	 * listed.
	 * 
	 */
	private class SubstitutionsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 6969656858380680460L;
		private List<TableRow> rows = new ArrayList<TableRow>();
		private String[] columnNames;

		public SubstitutionsTableModel() {
			this.columnNames = new String[5];
			this.columnNames[0] = "";
			this.columnNames[1] = HOVerwaltung.instance().getLanguageString(
					"subs.orders.colheadline.order");
			this.columnNames[2] = HOVerwaltung.instance().getLanguageString(
					"subs.orders.colheadline.when");
			this.columnNames[3] = HOVerwaltung.instance().getLanguageString(
					"subs.orders.colheadline.standing");
			this.columnNames[4] = HOVerwaltung.instance().getLanguageString(
					"subs.orders.colheadline.cards");
		}

		public void setData(List<Substitution> data) {
			this.rows.clear();
			for (Substitution sub : data) {
				TableRow row = new TableRow();
				row.setSub((Substitution) sub);
				this.rows.add(row);
			}
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return this.rows.size();
		}

		@Override
		public int getColumnCount() {
			return this.columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Substitution sub = this.rows.get(rowIndex).getSubstitution();
			switch (columnIndex) {
			case 1:
				return LanguageStringLookup.getOrderType(sub.getOrderType());
			case 2:
				if (sub.getMatchMinuteCriteria() > 0) {
					return HOVerwaltung.instance().getLanguageString("subs.MinuteAfterX",
							Integer.valueOf(sub.getMatchMinuteCriteria()));
				}
				return HOVerwaltung.instance().getLanguageString("subs.MinuteAnytime");
			case 3:
				return LanguageStringLookup.getStanding(sub.getStanding());
			case 4:
				return LanguageStringLookup.getRedCard(sub.getRedCardCriteria());
			}

			return "";
		}

		@Override
		public String getColumnName(int column) {
			return this.columnNames[column];
		}

		public TableRow getRow(int rowIndex) {
			return this.rows.get(rowIndex);
		}
	}

	/*
	 * This class is a simple container for row data.
	 */
	private class TableRow {

		private Substitution sub;
		private Problem problem;

		public Substitution getSubstitution() {
			return sub;
		}

		public void setSub(Substitution sub) {
			this.sub = sub;
		}

		public boolean isUncertain() {
			return this.problem != null && this.problem instanceof Uncertainty;
		}

		public boolean isError() {
			return this.problem != null && this.problem instanceof Error;
		}

		public Problem getProblem() {
			return problem;
		}

		public void setProblem(Problem problem) {
			this.problem = problem;
		}
	}

	private class BehaviorAction extends AbstractAction {

		private static final long serialVersionUID = 3753611559396928213L;

		public BehaviorAction() {
			super(HOVerwaltung.instance().getLanguageString("subs.Behavior"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			doNewOrder(MatchOrderType.NEW_BEHAVIOUR);
		}
	}

	private class PositionSwapAction extends AbstractAction {

		private static final long serialVersionUID = 3753611559396928213L;

		public PositionSwapAction() {
			super(HOVerwaltung.instance().getLanguageString("subs.TypeSwap"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			doNewOrder(MatchOrderType.POSITION_SWAP);
		}
	}

	private class SubstitutionAction extends AbstractAction {

		private static final long serialVersionUID = 2005264416271904159L;

		public SubstitutionAction() {
			super(HOVerwaltung.instance().getLanguageString("subs.TypeSub"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			doNewOrder(MatchOrderType.SUBSTITUTION);
		}
	}

	private class RemoveAction extends AbstractAction {

		private static final long serialVersionUID = 715531467612457L;

		public RemoveAction() {
			super(HOVerwaltung.instance().getLanguageString("subs.Remove"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			SubstitutionsTableModel model = (SubstitutionsTableModel) substitutionTable.getModel();
			TableRow row = model.getRow(substitutionTable.getSelectedRow());
			lineup.getSubstitutionList().remove(row.getSubstitution());
			refresh();
		}
	}

	private class EditAction extends AbstractAction {

		private static final long serialVersionUID = 715531467677812457L;

		public EditAction() {
			super(HOVerwaltung.instance().getLanguageString("subs.Edit"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRowIndex = substitutionTable.getSelectedRow();
			final Substitution sub = ((SubstitutionsTableModel) substitutionTable.getModel())
					.getRow(selectedRowIndex).getSubstitution();

			SubstitutionEditDialog dlg = getSubstitutionEditDialog(sub.getOrderType());
			dlg.setLocationRelativeTo(SubstitutionOverview.this);
			dlg.init(sub);
			dlg.setVisible(true);

			if (!dlg.isCanceled()) {
				sub.merge(dlg.getSubstitution());
				((SubstitutionsTableModel) substitutionTable.getModel()).fireTableRowsUpdated(
						selectedRowIndex, selectedRowIndex);
				refresh();
				selectSubstitution(sub);
			}
		}
	}

	private class WarningRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 7013869782046646283L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {

			JLabel component = (JLabel) super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			SubstitutionsTableModel tblModel = (SubstitutionsTableModel) table.getModel();
			TableRow tblRow = tblModel.getRow(row);
			if (tblRow.isUncertain()) {
				component.setIcon(ThemeManager.getIcon(HOIconName.EXCLAMATION));
			} else if (tblRow.isError()) {
				component.setIcon(ThemeManager.getIcon(HOIconName.EXCLAMATION_RED));
			} else {
				component.setIcon(null);
			}
			return component;
		}
	}
}
