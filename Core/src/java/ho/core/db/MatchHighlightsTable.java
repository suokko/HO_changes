package ho.core.db;

import ho.core.model.match.MatchHighlight;
import ho.core.model.match.Matchdetails;
import ho.core.util.HOLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

final class MatchHighlightsTable extends AbstractTable {
	final static String TABLENAME = "MATCHHIGHLIGHTS";

	protected MatchHighlightsTable(JDBCAdapter adapter) {
		super(TABLENAME, adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[14];
		columns[0] = new ColumnDescriptor("MatchID", Types.INTEGER, false);
		columns[1] = new ColumnDescriptor("GastTore", Types.INTEGER, false);
		columns[2] = new ColumnDescriptor("HeimTore", Types.VARCHAR, false, 256);
		columns[3] = new ColumnDescriptor("Typ", Types.INTEGER, false);
		columns[4] = new ColumnDescriptor("Minute", Types.INTEGER, false);
		columns[5] = new ColumnDescriptor("SpielerId", Types.INTEGER, false);
		columns[6] = new ColumnDescriptor("SpielerName", Types.VARCHAR, false, 256);
		columns[7] = new ColumnDescriptor("TeamId", Types.INTEGER, false);
		columns[8] = new ColumnDescriptor("SubTyp", Types.INTEGER, false);
		columns[9] = new ColumnDescriptor("SpielerHeim", Types.BOOLEAN, false);
		columns[10] = new ColumnDescriptor("GehilfeID", Types.INTEGER, false);
		columns[11] = new ColumnDescriptor("GehilfeName", Types.VARCHAR, false, 256);
		columns[12] = new ColumnDescriptor("GehilfeHeim", Types.BOOLEAN, false);
		columns[13] = new ColumnDescriptor("EventText", Types.VARCHAR, false, 512);
	}

	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX iMATCHHIGHLIGHTS_1 ON "+ getTableName()+ "("+ columns[0].getColumnName()+ ")",
			"CREATE INDEX IMATCHHIGHLIGHTS_SPIELERID_TYP ON "+ getTableName()+ "("+ columns[5].getColumnName()+ ", " + columns[3].getColumnName() + ")"};
	}

	void storeMatchHighlights(Matchdetails details) {
		if (details != null) {
			//Vorhandene Einträge entfernen
			final String[] where = { "MatchID" };
			final String[] werte = { "" + details.getMatchID()};
			delete(where, werte);
			
			
			try {
				final Vector<MatchHighlight> vHighlights = details.getHighlights();
				HOLogger.instance().debug(getClass(),"count of highlights: " + vHighlights.size());
				for (int i = 0; i < vHighlights.size(); i++) {
					final MatchHighlight highlight = (MatchHighlight) vHighlights.get(i);
					StringBuilder sql = new StringBuilder(100);
					//insert vorbereiten
					sql.append("INSERT INTO ").append(getTableName());
					sql.append(" ( MatchId, GastTore, HeimTore, Typ, Minute, SpielerId, SpielerName, TeamId, SubTyp, SpielerHeim, GehilfeID, GehilfeName, GehilfeHeim, EventText ) VALUES (");
					sql.append(details.getMatchID()).append(", ");
					sql.append(highlight.getGastTore()).append(", ");
					sql.append(highlight.getHeimTore()).append(", ");
					sql.append(highlight.getHighlightTyp()).append(", ");
					sql.append(highlight.getMinute()).append(", ");
					sql.append(highlight.getSpielerID()).append(", '");
					sql.append(DBManager.insertEscapeSequences(highlight.getSpielerName())).append("', ");
					sql.append(highlight.getTeamID()).append(", ");
					sql.append(highlight.getHighlightSubTyp()).append(", ");
					sql.append(highlight.getSpielerHeim()).append(", ");
					sql.append(highlight.getGehilfeID()).append(", '");
					sql.append(DBManager.insertEscapeSequences(highlight.getGehilfeName())).append("', ");
					sql.append(highlight.getGehilfeHeim()).append(", '");
					sql.append(DBManager.insertEscapeSequences(highlight.getEventText())).append("') ");
					adapter.executeUpdate(sql.toString());
				}
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"DB.storeMatchHighlights Error" + e);
				HOLogger.instance().log(getClass(),e);
			}
		}
	}

	static PreparedStatement sMatchHighlights = null;

	Vector<MatchHighlight> getMatchHighlights(int matchId) {
		try {
			//Highlights holen
			final Vector<MatchHighlight> vMatchHighlights = new Vector<MatchHighlight>();

			if (sMatchHighlights == null) {
				sMatchHighlights = adapter.prepareStatement("SELECT * FROM " + getTableName() +
						" WHERE MatchId = ? ORDER BY Minute, HeimTore, GastTore");
			}

			ResultSet rs = sMatchHighlights.executeQuery();

			//Alle Highlights des Spieles holen
			while (rs.next()) {
				vMatchHighlights.add(createObject(rs));
			}
			return vMatchHighlights;

		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
		}
		return new Vector<MatchHighlight>();
	}
	
	
	private MatchHighlight createObject(ResultSet rs) throws SQLException {
		final MatchHighlight highlight = new MatchHighlight();

		int idx = 1;
		highlight.setMatchId(rs.getInt(idx++));
		highlight.setGastTore(rs.getInt(idx++));
		highlight.setHeimTore(rs.getInt(idx++));
		highlight.setHighlightTyp(rs.getInt(idx++));
		highlight.setMinute(rs.getInt(idx++));
		highlight.setSpielerID(rs.getInt(idx++));
		highlight.setSpielerName(DBManager.deleteEscapeSequences(rs.getString(idx++)));
		highlight.setTeamID(rs.getInt(idx++));
		highlight.setHighlightSubTyp(rs.getInt(idx++));
		highlight.setSpielerHeim(rs.getBoolean(idx++));
		highlight.setGehilfeID(rs.getInt(idx++));
		highlight.setGehilfeName(DBManager.deleteEscapeSequences(rs.getString(idx++)));
		highlight.setGehilfeHeim(rs.getBoolean(idx++));
		highlight.setEventText(DBManager.deleteEscapeSequences(rs.getString(idx++)));
		
		return highlight;
	}
	
	Vector<MatchHighlight> getMatchHighlightsByTypIdAndPlayerId(int type, int playerId) {
		final Vector<MatchHighlight> vMatchHighlights = new Vector<MatchHighlight>();

		try {
		String sql =
			"SELECT * FROM "+getTableName()+" WHERE TYP="
				+ type
				+ " AND "
				+ "SpielerId="
				+ playerId
				+ " ORDER BY Minute, HeimTore, GastTore";
		ResultSet rs = adapter.executeQuery(sql);

		rs.beforeFirst();

		while (rs.next()) {
			vMatchHighlights.add(createObject(rs));
		}
		return vMatchHighlights;
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
		}
		return new Vector<MatchHighlight>();
	}
	
}