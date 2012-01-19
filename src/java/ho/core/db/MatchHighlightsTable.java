package ho.core.db;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import plugins.IMatchHighlight;
import de.hattrickorganizer.model.matches.MatchHighlight;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.tools.HOLogger;

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
			"CREATE INDEX iMATCHHIGHLIGHTS_1 ON "+ getTableName()+ "("+ columns[0].getColumnName()+ ")" };
	}

	void storeMatchHighlights(Matchdetails details) {
		if (details != null) {
			//Vorhandene Einträge entfernen
			final String[] where = { "MatchID" };
			final String[] werte = { "" + details.getMatchID()};
			delete(where, werte);
			StringBuilder sql = new StringBuilder(100);
			
			try {
				final Vector<IMatchHighlight> vHighlights = details.getHighlights();
				HOLogger.instance().debug(getClass(),"count of highlights: " + vHighlights.size());
				for (int i = 0; i < vHighlights.size(); i++) {
					final MatchHighlight highlight = (MatchHighlight) vHighlights.get(i);

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

	Vector<IMatchHighlight> getMatchHighlights(int matchId) {
		try {
			//Highlights holen
			final Vector<IMatchHighlight> vMatchHighlights = new Vector<IMatchHighlight>();

			String sql =
				"SELECT * FROM "+getTableName()+" WHERE MatchId="
					+ matchId
					+ " ORDER BY Minute, HeimTore, GastTore";
			ResultSet rs = adapter.executeQuery(sql);

			rs.beforeFirst();

			//Alle Highlights des Spieles holen
			while (rs.next()) {
				final MatchHighlight highlight = new MatchHighlight();

				highlight.setGastTore(rs.getInt("GastTore"));
				highlight.setHeimTore(rs.getInt("HeimTore"));
				highlight.setHighlightTyp(rs.getInt("Typ"));
				highlight.setMinute(rs.getInt("Minute"));
				highlight.setSpielerID(rs.getInt("SpielerId"));
				highlight.setSpielerName(DBManager.deleteEscapeSequences(rs.getString("SpielerName")));
				highlight.setTeamID(rs.getInt("TeamId"));
				highlight.setHighlightSubTyp(rs.getInt("SubTyp"));
				highlight.setSpielerHeim(rs.getBoolean("SpielerHeim"));
				highlight.setGehilfeID(rs.getInt("GehilfeID"));
				highlight.setGehilfeName(DBManager.deleteEscapeSequences(rs.getString("GehilfeName")));
				highlight.setGehilfeHeim(rs.getBoolean("GehilfeHeim"));
				highlight.setEventText(DBManager.deleteEscapeSequences(rs.getString("EventText")));
				
				vMatchHighlights.add(highlight);
			}
			return vMatchHighlights;

		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
		}
		return new Vector<IMatchHighlight>();
	}
}