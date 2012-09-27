package ho.module.ifa2;

import ho.core.model.HOVerwaltung;
import ho.module.ifa2.model.IfaStatistic;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class IfaTableModel extends AbstractTableModel {

	static final int COL_COUNTRY = 0;
	static final int COL_PLAYED = 1;
	static final int COL_WON = 2;
	static final int COL_DRAW = 3;
	static final int COL_LOST = 4;
	static final int COL_LAST = 5;
	private static final long serialVersionUID = -5838533232544239799L;
	private List<IfaStatistic> list;

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
		case COL_COUNTRY:
			return stat.getCountry();
		case COL_PLAYED:
			return stat.getMatchesPlayed();
		case COL_WON:
			return stat.getMatchesWon();
		case COL_DRAW:
			return stat.getMatchesDraw();
		case COL_LOST:
			return stat.getMatchesLost();
		case COL_LAST:
			return stat.getLastMatchDate();
		}

		return null;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case COL_COUNTRY:
			return HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.col.country");
		case COL_PLAYED:
			return HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.col.played");
		case COL_WON:
			return HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.col.won");
		case COL_DRAW:
			return HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.col.draw");
		case COL_LOST:
			return HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.col.lost");
		case COL_LAST:
			return HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.col.lastMatch");
		}

		return null;
	}
}