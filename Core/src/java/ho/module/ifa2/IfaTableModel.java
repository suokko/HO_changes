package ho.module.ifa2;

import ho.module.ifa2.model.IfaStatistic;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class IfaTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -5838533232544239799L;
	private List<IfaStatistic> list;
	private String[] columnNames = new String[] { "Country", "Matches played", "Won", "Draw",
			"Lost", "Last Match" };

	public IfaTableModel(List<IfaStatistic> data) {
		this.list = new ArrayList<IfaStatistic>(data);
	}

	@Override
	public int getRowCount() {
		return this.list.size();
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		IfaStatistic stat = this.list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return stat.getCountry();
		case 1:
			return stat.getMatchesPlayed();
		case 2:
			return stat.getMatchesWon();
		case 3:
			return stat.getMatchesDraw();
		case 4:
			return stat.getMatchesLost();
		case 5:
			return stat.getLastMatchDate();
		}

		return null;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return this.columnNames[columnIndex];
	}
}
