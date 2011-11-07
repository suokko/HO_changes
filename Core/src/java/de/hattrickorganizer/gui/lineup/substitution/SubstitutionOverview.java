package de.hattrickorganizer.gui.lineup.substitution;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import de.hattrickorganizer.tools.GUIUtilities;

public class SubstitutionOverview extends JPanel {

	private static final long serialVersionUID = -625638866350314110L;
	private JTable substitutionTable;

	public SubstitutionOverview() {
		initComponents();
		this.substitutionTable.setModel(new SubstitutionsTableModel());
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		this.substitutionTable = new JTable();
		this.substitutionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.getViewport().add(this.substitutionTable);

		add(tableScrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new GridBagLayout());

		JButton editButton = new JButton("Edit");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(8, 10, 2, 4);
		buttonPanel.add(editButton, gbc);

		JButton removeButton = new JButton("Remove");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(2, 10, 2, 4);
		buttonPanel.add(removeButton, gbc);

		JButton substitutionButton = new JButton("New substitution");
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(8, 4, 2, 10);
		buttonPanel.add(substitutionButton, gbc);

		JButton behaviorButton = new JButton();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(2, 4, 2, 10);
		buttonPanel.add(behaviorButton, gbc);
		behaviorButton.setAction(new BehaviorAction());

		JButton positionSwapButton = new JButton("New position swap");
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(2, 4, 10, 10);
		gbc.weightx = 1.0;
		buttonPanel.add(positionSwapButton, gbc);

		add(buttonPanel, BorderLayout.SOUTH);

		GUIUtilities.equalizeComponentSizes(editButton, removeButton);
		GUIUtilities.equalizeComponentSizes(substitutionButton, behaviorButton, positionSwapButton);

		editButton.setEnabled(false);
		removeButton.setEnabled(false);
		substitutionButton.setEnabled(false);
		positionSwapButton.setEnabled(false);
	}

	private class SubstitutionsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 6969656858380680460L;
		private String[] columnNames = new String[] { "Type", "Affected Player", "When" };

		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		public int getColumnCount() {
			return this.columnNames.length;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getColumnName(int column) {
			return this.columnNames[column];
		}
	}

	private class BehaviorAction extends AbstractAction {

		private static final long serialVersionUID = 3753611559396928213L;

		public BehaviorAction() {
			super("New behavior");
		}

		public void actionPerformed(ActionEvent e) {
			BehaviorDialog dlg = new BehaviorDialog(
					SwingUtilities.getWindowAncestor(SubstitutionOverview.this));
			dlg.setVisible(true);
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
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
					// use standard LaF
				}
				JDialog dlg = new JDialog();
				dlg.getContentPane().add(new SubstitutionOverview());
				dlg.pack();
				dlg.setSize(new Dimension(800, 600));
				dlg.setVisible(true);
			}
		});
	}
}
