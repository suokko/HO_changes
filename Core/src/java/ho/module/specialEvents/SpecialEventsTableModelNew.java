package ho.module.specialEvents;

import ho.core.model.HOVerwaltung;

import java.sql.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

class SpecialEventsTableModelNew extends AbstractTableModel {

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
	private List<MatchLine> data;

	public void setData(List<MatchLine> data) {
		this.data = data;
		fireTableDataChanged();
	}

	@Override
	public Object getValueAt(int row, int column) {
		MatchLine line = this.data.get(row);
		switch (column) {
		case MATCHDATECOLUMN:
			if (line.isMatchHeaderLine()) {
				return line.getMatch().getMatchDate();
			}
			break;
		case MATCHIDCOLUMN:
			if (line.isMatchHeaderLine()) {
				return line.getMatch().getMatchId();
			}
			break;
		case HOMETACTICCOLUMN:
			if (line.isMatchHeaderLine()) {
				return line.getMatch().getHostingTeamTactic();
			}
			break;
		case HOMEEVENTCOLUMN:
			return line;
		case HOMETEAMCOLUMN:
			if (line.isMatchHeaderLine()) {
				return line.getMatch().getHostingTeam();
			}
			break;
		case RESULTCOLUMN:
			if (line.isMatchHeaderLine()) {
				return line.getMatch().getMatchResult();
			}
			break;
		case AWAYTEAMCOLUMN:
			if (line.isMatchHeaderLine()) {
				return line.getMatch().getVisitingTeam();
			}
			break;
		case AWAYEVENTCOLUMN:
			return line;
		case AWAYTACTICCOLUMN:
			if (line.isMatchHeaderLine()) {
				return line.getMatch().getVisitingTeamTactic();
			}
			break;
		case MINUTECOLUMN:
			if (line.getMatchHighlight() != null) {
				return line.getMatchHighlight().getMinute();
			}
			break;
		case CHANCECOLUMN:
			return line;
		case EVENTTYPCOLUMN:
			return line.getMatchHighlight();
		case SETEXTCOLUMN:
			if (line.getMatchHighlight() != null) {
				return SpecialEventsDM.getSEText(line.getMatchHighlight());
			}
			break;
		case NAMECOLUMN:
			if (line.getMatchHighlight() != null) {
				return SpecialEventsDM.getSpielerName(line.getMatchHighlight());
			}
			break;
		}

		return null;
	}

	@Override
	public int getRowCount() {
		if (this.data == null) {
			return 0;
		}
		return this.data.size();
	}

	@Override
	public int getColumnCount() {
		return 15;
	}

	@Override
	public Class<?> getColumnClass(int col) {
		switch (col) {
		case MATCHDATECOLUMN:
			return Date.class;
		}
		// if (col == HOMEEVENTCOLUMN || col == AWAYEVENTCOLUMN || col ==
		// CHANCECOLUMN
		// || col == EVENTTYPCOLUMN) {
		// return javax.swing.ImageIcon.class;
		// }
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
