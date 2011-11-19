package de.hattrickorganizer.gui.lineup.substitution;

import gui.UserParameter;

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

import plugins.ISubstitution;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.GUIUtilities;

public class SubstitutionOverview extends JPanel {

	private static final long serialVersionUID = -625638866350314110L;
	private JTable substitutionTable;
	private DetailsView detailsView;
	private JButton editButton;
	private JButton removeButton;
	private EditAction editAction;

	public SubstitutionOverview() {
		initComponents();
		addListeners();
		refresh();
	}

	private void refresh() {
		SubstitutionsTableModel model = (SubstitutionsTableModel) this.substitutionTable.getModel();
		model.setData(HOVerwaltung.instance().getModel().getAufstellung().getSubstitutionList());
	}

	private void addListeners() {
		this.substitutionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					tableSelectionChanged();
				}
			}
		});

		this.editAction = new EditAction();
		this.editAction.setEnabled(false);
		this.editButton.setAction(this.editAction);
	}

	private void tableSelectionChanged() {
		int selectedRowIndex = this.substitutionTable.getSelectedRow();
		boolean enable = false;
		ISubstitution substitution = null;
		if (selectedRowIndex != -1) {
			substitution = ((SubstitutionsTableModel) this.substitutionTable.getModel())
					.getSubstitution(selectedRowIndex);
			if (substitution.getOrderType() == ISubstitution.BEHAVIOUR) {
				enable = true;
			}
		}
		this.editButton.setEnabled(enable);
		this.removeButton.setEnabled(enable);
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

		this.editButton = new JButton();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 10, 2, 10);
		buttonPanel.add(this.editButton, gbc);

		this.removeButton = new JButton(HOVerwaltung.instance().getLanguageString("subs.Remove"));
		gbc.gridy++;
		gbc.insets = new Insets(2, 10, 10, 10);
		buttonPanel.add(this.removeButton, gbc);

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

		GUIUtilities.equalizeComponentSizes(this.editButton, this.removeButton, substitutionButton,
				behaviorButton, positionSwapButton);

		this.removeButton.setEnabled(false);
		substitutionButton.setEnabled(false);
		positionSwapButton.setEnabled(false);
	}

	private class SubstitutionsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 6969656858380680460L;
		private List<ISubstitution> data = new ArrayList<ISubstitution>();
		private String[] columnNames = new String[] { "Order", "Affected Player", "Second aff. player",
				"When", "New behaviour", "New position", "Standing", "Red cards" };

		public ISubstitution getSubstitution(int rowIndex) {
			return this.data.get(rowIndex);
		}

		public void setData(List<ISubstitution> data) {
			this.data = data;
			fireTableDataChanged();
		}

		public int getRowCount() {
			return this.data.size();
		}

		public int getColumnCount() {
			return this.columnNames.length;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			ISubstitution sub = this.data.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return Lookup.getOrderType(sub.getOrderType());
			case 1:
				if (sub.getPlayerIn() != -1) {
					return HOVerwaltung.instance().getModel().getSpieler(sub.getPlayerIn()).getName();
				}
				return "";
			case 2:
				if (sub.getPlayerOut() != -1 && sub.getPlayerIn() != sub.getPlayerOut()) {
					return HOVerwaltung.instance().getModel().getSpieler(sub.getPlayerOut()).getName();
				}
				return "";
			case 3:
				if (sub.getMatchMinuteCriteria() > 0) {
					return MessageFormat.format(HOVerwaltung.instance()
							.getLanguageString("subs.MinuteAfterX"), Integer.valueOf(sub
							.getMatchMinuteCriteria()));
				}
				return HOVerwaltung.instance().getLanguageString("subs.MinuteAnytime");
			case 4:
				return Lookup.getBehaviour(sub.getBehaviour());
			case 5:
				return Lookup.getPosition(sub.getPos());
			case 6:
				return Lookup.getStanding(sub.getStanding());
			case 7:
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

		public void actionPerformed(ActionEvent e) {
			BehaviourDialog dlg = null;
			Window windowAncestor = SwingUtilities.getWindowAncestor(SubstitutionOverview.this);
			if (windowAncestor instanceof Frame) {
				dlg = new BehaviourDialog((Frame) windowAncestor);
			} else {
				dlg = new BehaviourDialog((Dialog) windowAncestor);
			}

			dlg.setTitle(HOVerwaltung.instance().getLanguageString("subs.TypeOrder"));
			dlg.setLocationRelativeTo(SubstitutionOverview.this);
			dlg.setVisible(true);

			if (!dlg.isCanceled()) {
				ISubstitution sub = dlg.getSubstitution();
				HOVerwaltung.instance().getModel().getAufstellung().getSubstitutionList().add(sub);
				refresh();
			}
		}
	}

	private class PositionSwapAction extends AbstractAction {

		private static final long serialVersionUID = 3753611559396928213L;

		public PositionSwapAction() {
			super(HOVerwaltung.instance().getLanguageString("subs.TypeSwap"));
		}

		public void actionPerformed(ActionEvent e) {
		}
	}

	private class SubstitutionAction extends AbstractAction {

		private static final long serialVersionUID = 2005264416271904159L;

		public SubstitutionAction() {
			super(HOVerwaltung.instance().getLanguageString("subs.TypeSub"));
		}

		public void actionPerformed(ActionEvent e) {
		}
	}

	private class EditAction extends AbstractAction {

		private static final long serialVersionUID = 715531467677812457L;

		public EditAction() {
			super(HOVerwaltung.instance().getLanguageString("subs.Edit"));
		}

		public void actionPerformed(ActionEvent e) {
			int selectedRowIndex = substitutionTable.getSelectedRow();
			final ISubstitution sub = ((SubstitutionsTableModel) substitutionTable.getModel())
					.getSubstitution(selectedRowIndex);

			BehaviourDialog dlg = null;
			Window windowAncestor = SwingUtilities.getWindowAncestor(SubstitutionOverview.this);
			if (windowAncestor instanceof Frame) {
				dlg = new BehaviourDialog((Frame) windowAncestor);
			} else {
				dlg = new BehaviourDialog((Dialog) windowAncestor);
			}
			dlg.setTitle(HOVerwaltung.instance().getLanguageString("subs.TypeOrder"));
			dlg.setLocationRelativeTo(SubstitutionOverview.this);
			dlg.init(sub);
			dlg.setVisible(true);

			if (!dlg.isCanceled()) {
				sub.merge(dlg.getSubstitution());
				((SubstitutionsTableModel) substitutionTable.getModel()).fireTableRowsUpdated(
						selectedRowIndex, selectedRowIndex);
			}
		}
	}

	/**
	 * For development only, should be removed later.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				HOVerwaltung.instance().setResource(UserParameter.instance().sprachDatei,
						HOVerwaltung.instance().getClass().getClassLoader());
				HOVerwaltung.instance().loadLatestHoModel();

				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
					// use standard LaF
				}
				JDialog dlg = new JDialog();
				dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dlg.getContentPane().add(new SubstitutionOverview());
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
				DBZugriff.instance().disconnect();
			}
		});
	}
}
