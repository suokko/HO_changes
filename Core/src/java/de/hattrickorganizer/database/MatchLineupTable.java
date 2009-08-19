package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;

import de.hattrickorganizer.model.matches.MatchLineup;
import de.hattrickorganizer.tools.HOLogger;

public final class MatchLineupTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "MATCHLINEUP";
	
	protected MatchLineupTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[10];
		columns[0]= new ColumnDescriptor("MatchID",Types.INTEGER,false,true);
		columns[1]= new ColumnDescriptor("MatchTyp",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("HeimName",Types.VARCHAR,false,256);
		columns[3]= new ColumnDescriptor("HeimID",Types.INTEGER,false);
		columns[4]= new ColumnDescriptor("GastName",Types.VARCHAR,false,256);
		columns[5]= new ColumnDescriptor("GastID",Types.INTEGER,false);
		columns[6]= new ColumnDescriptor("FetchDate",Types.VARCHAR,false,256);
		columns[7]= new ColumnDescriptor("MatchDate",Types.VARCHAR,false,256);
		columns[8]= new ColumnDescriptor("ArenaID",Types.INTEGER,false);
		columns[9]= new ColumnDescriptor("ArenaName",Types.VARCHAR,false,256);

	}
	
	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX IMATCHLINEUP_1 ON " + getTableName() + "(" + columns[0].getColumnName() + ")"};
	}	

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param matchID TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public MatchLineup getMatchLineup(int matchID) {
		MatchLineup lineup = null;
		String sql = null;
		ResultSet rs = null;

		try {
			sql = "SELECT * FROM "+getTableName()+" WHERE MatchID = " + matchID;

			rs = adapter.executeQuery(sql);

			rs.first();

			// Plan auslesen
			lineup = new MatchLineup();
			lineup.setArenaID(rs.getInt("ArenaID"));
			lineup.setArenaName(DBZugriff.deleteEscapeSequences(rs.getString("ArenaName")));
			lineup.setFetchDatum(rs.getString("FetchDate"));
			lineup.setGastId(rs.getInt("GastID"));
			lineup.setGastName(DBZugriff.deleteEscapeSequences(rs.getString("GastName")));
			lineup.setHeimId(rs.getInt("HeimID"));
			lineup.setHeimName(DBZugriff.deleteEscapeSequences(rs.getString("HeimName")));
			lineup.setMatchID(matchID);
			lineup.setMatchTyp(rs.getInt("MatchTyp"));
			lineup.setSpielDatum(rs.getString("MatchDate"));

			lineup.setHeim(DBZugriff.instance().getMatchLineupTeam(matchID, lineup.getHeimId()));
			lineup.setGast(DBZugriff.instance().getMatchLineupTeam(matchID, lineup.getGastId()));
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DB.getMatchLineup Error" + e);

			//HOLogger.instance().log(getClass(),e);
			lineup = null;
		}

		return lineup;
	}

	/**
	 * Ist das Match schon in der Datenbank vorhanden?
	 *
	 * @param matchid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public boolean isMatchLineupVorhanden(int matchid) {
		boolean vorhanden = false;

		try {
			final String sql = "SELECT MatchId FROM "+getTableName()+" WHERE MatchId=" + matchid;
			final ResultSet rs = adapter.executeQuery(sql);

			rs.beforeFirst();

			if (rs.next()) {
				vorhanden = true;
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.isMatchVorhanden : " + e);
		}

		return vorhanden;
	}

	/**
	 * speichert ein Matchlineup
	 *
	 * @param lineup TODO Missing Constructuor Parameter Documentation
	 */
	public void storeMatchLineup(MatchLineup lineup) {
		if (lineup != null) {
			//Vorhandene Einträge entfernen
			final String[] where = { "MatchID" };
			final String[] werte = { "" + lineup.getMatchID()};			
			delete(where, werte);

			String sql = null;
			//saven
			try {
				//insert vorbereiten
				sql = "INSERT INTO "+getTableName()+" ( MatchID, MatchTyp, HeimName, HeimID, GastName, GastID, FetchDate, MatchDate, ArenaID, ArenaName ) VALUES(";
				sql
					+= (lineup.getMatchID()
						+ ","
						+ lineup.getMatchTyp()
						+ ", '"
						+ DBZugriff.insertEscapeSequences(lineup.getHeimName())
						+ "',"
						+ lineup.getHeimId()
						+ ",'"
						+ DBZugriff.insertEscapeSequences(lineup.getGastName())
						+ "', "
						+ lineup.getGastId()
						+ ", '"
						+ lineup.getStringFetchDate()
						+ "', '"
						+ lineup.getStringSpielDate()
						+ "', "
						+ lineup.getArenaID()
						+ ", '"
						+ DBZugriff.insertEscapeSequences(lineup.getArenaName())
						+ "' )");
				adapter.executeUpdate(sql);

				//Einträge noch saven
				DBZugriff.instance().storeMatchLineupTeam((de.hattrickorganizer.model.matches.MatchLineupTeam) lineup.getHeim(), lineup.getMatchID());
				DBZugriff.instance().storeMatchLineupTeam((de.hattrickorganizer.model.matches.MatchLineupTeam) lineup.getGast(), lineup.getMatchID());
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"DB.storeMatchLineup Error" + e);
				HOLogger.instance().log(getClass(),e);
			}
		}
	}

}
