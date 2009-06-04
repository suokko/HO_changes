package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import plugins.IMatchHighlight;

import de.hattrickorganizer.model.matches.MatchHighlight;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.tools.HOLogger;

public final class MatchHighlightsTable extends AbstractTable {

	/** tablename **/						
	public final static String TABLENAME = "MATCHHIGHLIGHTS";

	protected MatchHighlightsTable(JDBCAdapter adapter) {
		super(TABLENAME, adapter);
	}

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

	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX iMATCHHIGHLIGHTS_1 ON "
				+ getTableName()
				+ "("
				+ columns[0].getColumnName()
				+ ")" };
	}

	/**
	 * Speichert die Highlights zu einem Spiel
	 *
	 * @param details TODO Missing Constructuor Parameter Documentation
	 */
	public void storeMatchHighlights(Matchdetails details) {
		if (details != null) {
			//Vorhandene Einträge entfernen
			final String[] where = { "MatchID" };
			final String[] werte = { "" + details.getMatchID()};
			delete(where, werte);
			String sql = null;

			//saven
			try {
				final Vector<IMatchHighlight> vHighlights = details.getHighlights();

				HOLogger.instance().log(getClass(),"Anzahl an Highlights: " + vHighlights.size());

				for (int i = 0; i < vHighlights.size(); i++) {
					final MatchHighlight highlight = (MatchHighlight) vHighlights.get(i);

					//insert vorbereiten
					sql =
						"INSERT INTO "+getTableName()+" ( MatchId, GastTore, HeimTore, Typ, Minute, SpielerId, SpielerName, TeamId, SubTyp, SpielerHeim, GehilfeID, GehilfeName, GehilfeHeim, EventText ) VALUES ("
							+ details.getMatchID()
							+ ", "
							+ highlight.getGastTore()
							+ ", "
							+ highlight.getHeimTore()
							+ ", "
							+ highlight.getHighlightTyp()
							+ ", "
							+ highlight.getMinute()
							+ ", "
							+ highlight.getSpielerID()
							+ ", '"
							+ DBZugriff.insertEscapeSequences(highlight.getSpielerName())
							+ "', "
							+ highlight.getTeamID()
							+ ", "
							+ highlight.getHighlightSubTyp()
							+ ", "
							+ highlight.getSpielerHeim()
							+ ", "
							+ highlight.getGehilfeID()
							+ ", '"
							+ DBZugriff.insertEscapeSequences(highlight.getGehilfeName())
							+ "', "
							+ highlight.getGehilfeHeim()
							+ ", '"
							+ DBZugriff.insertEscapeSequences(highlight.getEventText())
							+ "' )";

					adapter.executeUpdate(sql);
				}
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"DB.storeMatchHighlights Error" + e);
				HOLogger.instance().log(getClass(),e);
			}
		}
	}

	/**
	 * Gibt die MatchDetails zu einem Match zurück
	 *
	 * @param matchId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector<IMatchHighlight> getMatchHighlights(int matchId) {
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
				highlight.setSpielerName(
					de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(
						rs.getString("SpielerName")));
				highlight.setTeamID(rs.getInt("TeamId"));
				highlight.setHighlightSubTyp(rs.getInt("SubTyp"));
				highlight.setSpielerHeim(rs.getBoolean("SpielerHeim"));
				highlight.setGehilfeID(rs.getInt("GehilfeID"));
				highlight.setGehilfeName(
					de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(
						rs.getString("GehilfeName")));
				highlight.setGehilfeHeim(rs.getBoolean("GehilfeHeim"));
				highlight.setEventText(
					de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(
						rs.getString("EventText")));

				//Highlight in den Vector packen
				vMatchHighlights.add(highlight);
			}
			return vMatchHighlights;

		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getMatchDetails : " + e);
			HOLogger.instance().log(getClass(),e);
		}
		return new Vector<IMatchHighlight>();
	}
}