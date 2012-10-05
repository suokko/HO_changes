package ho.module.ifa.table;

import ho.core.model.HOVerwaltung;
import ho.module.ifa.PluginIfaUtils;
import ho.module.ifa.model.IfaStatistic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class IfaTableModel extends AbstractTableModel {

	static final int COL_COUNTRY = 0;
	static final int COL_PLAYED = 1;
	static final int COL_WON = 2;
	static final int COL_DRAW = 3;
	static final int COL_LOST = 4;
	static final int COL_LASTMATCH = 5;
	static final int COL_COOLNESS = 6;
	private static final long serialVersionUID = -5838533232544239799L;
	private List<IfaStatistic> list;
	private Summary summary;

	public IfaTableModel() {
		setData(new ArrayList<IfaStatistic>());
	}

	public void setData(List<IfaStatistic> data) {
		this.list = new ArrayList<IfaStatistic>(data);
		this.summary = new Summary(this.list);
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		if (list.size() > 0) {
			return this.list.size() + 1;
		}
		return 0;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		if (rowIndex < this.list.size()) {
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
			case COL_LASTMATCH:
				return new Date(stat.getLastMatchDate());
			case COL_COOLNESS:
				return PluginIfaUtils.getCoolness(stat.getCountry().getCountryId());
			}
		} else {
			switch (columnIndex) {
			case COL_COUNTRY:
				return this.summary.getCountriesTotal();
			case COL_PLAYED:
				return this.summary.getPlayedTotal();
			case COL_WON:
				return this.summary.getWonTotal();
			case COL_DRAW:
				return this.summary.getDrawTotal();
			case COL_LOST:
				return this.summary.getLostTotal();
			case COL_LASTMATCH:
				return new Date(this.summary.getLastMatch());
			case COL_COOLNESS:
				return this.summary.getCoolnessTotal();
			}
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case COL_PLAYED:
			return Integer.class;
		case COL_WON:
			return Integer.class;
		case COL_DRAW:
			return Integer.class;
		case COL_LOST:
			return Integer.class;
		case COL_LASTMATCH:
			return Date.class;
		case COL_COOLNESS:
			return Double.class;
		}
		return super.getColumnClass(columnIndex);
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
		case COL_LASTMATCH:
			return HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.col.lastMatch");
		case COL_COOLNESS:
			return HOVerwaltung.instance().getLanguageString("ifa.statisticsTable.col.coolness");
		}

		return null;
	}

	private class Summary {

		private int countriesTotal;
		private int playedTotal;
		private int wonTotal;
		private int drawTotal;
		private int lostTotal;
		private long lastMatch;
		private double coolnessTotal;

		Summary(List<IfaStatistic> data) {
			init(data);
		}

		public int getCountriesTotal() {
			return countriesTotal;
		}

		public int getPlayedTotal() {
			return playedTotal;
		}

		public int getWonTotal() {
			return wonTotal;
		}

		public int getDrawTotal() {
			return drawTotal;
		}

		public int getLostTotal() {
			return lostTotal;
		}

		public long getLastMatch() {
			return lastMatch;
		}

		public double getCoolnessTotal() {
			return coolnessTotal;
		}

		private void init(List<IfaStatistic> data) {
			for (IfaStatistic stat : data) {
				countriesTotal++;
				playedTotal += stat.getMatchesPlayed();
				wonTotal += stat.getMatchesWon();
				drawTotal += stat.getMatchesDraw();
				lostTotal += stat.getMatchesLost();
				coolnessTotal += PluginIfaUtils.getCoolness(stat.getCountry().getCountryId());
				if (lastMatch < stat.getLastMatchDate()) {
					lastMatch = stat.getLastMatchDate();
				}
			}
		}

	}
}
