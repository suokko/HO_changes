package ho.module.specialEvents;

import ho.core.model.HOVerwaltung;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

class SpecialEventsTableModel extends AbstractTableModel {

	static final int MATCHDATECOLUMN = 0;
	static final int MATCHIDCOLUMN = 1;
	static final int HOMETACTICCOLUMN = 2;
	static final int HOMEEVENTCOLUMN = 3;
	static final int HOMETEAMCOLUMN = 4;
	static final int RESULTCOLUMN = 5;
	static final int AWAYTEAMCOLUMN = 6;
	static final int AWAYEVENTCOLUMN = 7;
	static final int AWAYTACTICCOLUMN = 8;
	static final int MINUTECOLUMN = 9;
	static final int CHANCECOLUMN = 10;
	static final int EVENTTYPCOLUMN = 11;
	static final int SETEXTCOLUMN = 12;
	static final int NAMECOLUMN = 13;
	static final int HIDDENCOLUMN = 14;
	static final int NUMCOLUMNS = 15;
	private static final long serialVersionUID = 8499826497766216534L;
	private List<List<Object>> data = new ArrayList<List<Object>>();

	public void setData(List<List<Object>> data) {
		this.data = data;
		fireTableDataChanged();
	}

	@Override
	public Object getValueAt(int row, int column) {
			return this.data.get(row).get(column);
	}

	@Override
	public int getRowCount() {
		return this.data.size();
	}

	@Override
	public int getColumnCount() {
		return 15;
	}

	@Override
	public Class<?> getColumnClass(int col) {
		if (col == HOMEEVENTCOLUMN || col == AWAYEVENTCOLUMN || col == CHANCECOLUMN
				|| col == EVENTTYPCOLUMN) {
			return javax.swing.ImageIcon.class;
		}
		return super.getColumnClass(col);
	}

	@Override
	public String getColumnName(int columnIndex) {

		switch (columnIndex) {
		case MATCHDATECOLUMN:
			return HOVerwaltung.instance().getLanguageString("Datum");
		case MATCHIDCOLUMN:
			return HOVerwaltung.instance().getLanguageString("GameID");
		case HOMETACTICCOLUMN:
			return HOVerwaltung.instance().getLanguageString("ls.team.tactic");
		case HOMETEAMCOLUMN:
			return HOVerwaltung.instance().getLanguageString("Heim");
		case AWAYTEAMCOLUMN:
			return HOVerwaltung.instance().getLanguageString("Gast");
		case AWAYTACTICCOLUMN:
			return HOVerwaltung.instance().getLanguageString("ls.team.tactic");
		case MINUTECOLUMN:
			return HOVerwaltung.instance().getLanguageString("Min");
		case SETEXTCOLUMN:
			return HOVerwaltung.instance().getLanguageString("Event");
		case NAMECOLUMN:
			return HOVerwaltung.instance().getLanguageString("Spieler");
		case HIDDENCOLUMN:
		case CHANCECOLUMN:
		case AWAYEVENTCOLUMN:
		case RESULTCOLUMN:
		case HOMEEVENTCOLUMN:
		case EVENTTYPCOLUMN:
			return " ";
		default:
			return super.getColumnName(columnIndex);
		}
	}
}
