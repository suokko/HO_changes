package ho.module.lineup.substitution;

import ho.core.db.DBManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.util.GUIUtilities;
import ho.module.lineup.Lineup;
import ho.module.lineup2.Helper;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public class SubstitutionOverview extends JPanel {

	private static final long serialVersionUID = -625638866350314110L;
	private JTable substitutionTable;
	private DetailsView detailsView;
	private EditAction editAction;
	private RemoveAction removeAction;
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
	}

	private void createActions() {
		this.editAction = new EditAction();
		this.editAction.setEnabled(false);
		this.removeAction = new RemoveAction();
		this.removeAction.setEnabled(false);
	}

	private void refresh() {
		SubstitutionsTableModel model = (SubstitutionsTableModel) this.substitutionTable.getModel();
		model.setData(this.lineup.getSubstitutionList());
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
		ISubstitution substitution = null;
		if (selectedRowIndex != -1) {
			substitution = ((SubstitutionsTableModel) this.substitutionTable.getModel())
					.getSubstitution(selectedRowIndex);
			enable = true;
		}
		this.editAction.setEnabled(enable);
		this.removeAction.setEnabled(enable);
		this.detailsView.setSubstitution(substitution);
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		this.substitutionTable = new JTable();
		this.substitutionTable.setModel(new SubstitutionsTableModel());
		this.substitutionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.getViewport().add(this.substitutionTable);

		this.detailsView = new DetailsView();
		add(this.detailsView, BorderLayout.SOUTH);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.add(tableScrollPane, 0);
		splitPane.add(this.detailsView, 1);
		splitPane.setDividerLocation(160);
		add(splitPane, BorderLayout.CENTER);

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
		substitutionButton.setAction(new SubstitutionAction());

		JButton behaviorButton = new JButton();
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 10);
		buttonPanel.add(behaviorButton, gbc);
		behaviorButton.setAction(new BehaviorAction());

		JButton positionSwapButton = new JButton();
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 2, 10);
		gbc.weighty = 1.0;
		buttonPanel.add(positionSwapButton, gbc);
		positionSwapButton.setAction(new PositionSwapAction());

		add(buttonPanel, BorderLayout.EAST);

		GUIUtilities.equalizeComponentSizes(editButton, removeButton, substitutionButton,
				behaviorButton, positionSwapButton);
	}

	private void doNewOrder(MatchOrderType orderType) {
		SubstitutionEditDialog dlg = getSubstitutionEditDialog(orderType);
		dlg.setLocationRelativeTo(SubstitutionOverview.this);
		dlg.setVisible(true);

		if (!dlg.isCanceled()) {
			ISubstitution sub = dlg.getSubstitution();
			this.lineup.getSubstitutionList().add(sub);
			SubstitutionsTableModel model = (SubstitutionsTableModel) this.substitutionTable
					.getModel();
			int idx = model.getRowCount() - 1;
			model.fireTableRowsInserted(idx, idx);

			for (int i = 0; i < model.getRowCount(); i++) {
				if (model.getSubstitution(i) == sub) {
					this.substitutionTable.getSelectionModel().setSelectionInterval(i, i);
				}
			}

		}
	}

	private class SubstitutionsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 6969656858380680460L;
		private List<ISubstitution> data = new ArrayList<ISubstitution>();
		private String[] columnNames = new String[] { "Order", "When", "Standing", "Red cards" };

		public ISubstitution getSubstitution(int rowIndex) {
			return this.data.get(rowIndex);
		}

		public void setData(List<ISubstitution> data) {
			this.data = data;
			fireTableDataChanged();
		}

		public void select(ISubstitution substitution) {
			for (int i = 0; i < this.data.size(); i++) {

			}
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
			ISubstitution sub = this.data.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return Lookup.getOrderType(sub.getOrderType());
			case 1:
				if (sub.getMatchMinuteCriteria() > 0) {
					return MessageFormat.format(
							HOVerwaltung.instance().getLanguageString("subs.MinuteAfterX"),
							Integer.valueOf(sub.getMatchMinuteCriteria()));
				}
				return HOVerwaltung.instance().getLanguageString("subs.MinuteAnytime");
			case 2:
				return Lookup.getStanding(sub.getStanding());
			case 3:
				return Lookup.getRedCard(sub.getCard());
			}

			return "";
		}

		@Override
		public String getColumnName(int column) {
			return this.columnNames[column];
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
			int selectedRowIndex = substitutionTable.getSelectedRow();
			ISubstitution sub = ((SubstitutionsTableModel) substitutionTable.getModel())
					.getSubstitution(selectedRowIndex);

			lineup.getSubstitutionList().remove(sub);
			((SubstitutionsTableModel) substitutionTable.getModel()).fireTableRowsDeleted(
					selectedRowIndex, selectedRowIndex);
			detailsView.refresh();
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
			final ISubstitution sub = ((SubstitutionsTableModel) substitutionTable.getModel())
					.getSubstitution(selectedRowIndex);

			SubstitutionEditDialog dlg = getSubstitutionEditDialog(sub.getOrderType());
			dlg.setLocationRelativeTo(SubstitutionOverview.this);
			dlg.init(sub);
			dlg.setVisible(true);

			if (!dlg.isCanceled()) {
				sub.merge(dlg.getSubstitution());
				((SubstitutionsTableModel) substitutionTable.getModel()).fireTableRowsUpdated(
						selectedRowIndex, selectedRowIndex);
				detailsView.refresh();
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

	/**
	 * For development only, should be removed later.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DBManager.instance().loadUserParameter();
				HOVerwaltung.instance().setResource(UserParameter.instance().sprachDatei);
				HOVerwaltung.instance().loadLatestHoModel();

				Lineup lineup = null;
				try {
					lineup = Helper
							.getLineup(new File(
									"/home/chr/tmp/matchorders_version_1_8_matchID_362419131_isYouth_false.xml"));
					// lineup = Helper.getLineup(new
					// File("/home/chr/tmp/matchorders_version_1_8_matchID_362217696_isYouth_false.xml"));
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
					// use standard LaF
				}
				JDialog dlg = new JDialog();
				dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dlg.getContentPane().add(new SubstitutionOverview(lineup));
				dlg.pack();
				dlg.setSize(new Dimension(800, 600));
				dlg.setVisible(true);

				dlg.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						System.exit(0);
					}
				});
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				DBManager.instance().disconnect();
			}
		});
	}
}
