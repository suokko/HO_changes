package ho.module.lineup.penalties;

import ho.core.model.HOVerwaltung;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class PenaltyShootersView extends JPanel {

	private static final long serialVersionUID = -5089904466636200088L;
	private JTable shootersTable;

	public PenaltyShootersView() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GridBagLayout());

		this.shootersTable = new JTable();
		this.shootersTable.setModel(new PenaltyShootersTableModel());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		add(new JScrollPane(this.shootersTable), gbc);
	}

	private class PenaltyShootersTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 3044881352777003621L;
		private String[] columnNames;
		private List<Object> data = new ArrayList<Object>();

		public PenaltyShootersTableModel() {
			this.columnNames = new String[5];
			this.columnNames[0] = HOVerwaltung.instance().getLanguageString("Name");
			this.columnNames[1] = HOVerwaltung.instance().getLanguageString("Erfahrung");
			this.columnNames[2] = HOVerwaltung.instance().getLanguageString("Standards");
			this.columnNames[3] = HOVerwaltung.instance().getLanguageString("Torschuss");
			this.columnNames[4] = HOVerwaltung.instance().getLanguageString(
					"lineup.penaltyshooters.colheadline.ability");
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
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getColumnName(int column) {
			return this.columnNames[column];
		}
	}
}
