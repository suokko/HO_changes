package ho.core.db;

import ho.core.util.HOLogger;
import ho.module.matches.model.MatchKurzInfo;
import ho.module.matches.statistics.MatchesOverviewCommonPanel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Vector;

import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import plugins.ISpielePanel;

final class MatchesKurzInfoTable extends AbstractTable {
	final static String TABLENAME = "MATCHESKURZINFO";
	
	protected MatchesKurzInfoTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}
	
	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[11];
		columns[0]= new ColumnDescriptor("MatchID",Types.INTEGER,false,true);
		columns[1]= new ColumnDescriptor("MatchTyp",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("HeimName",Types.VARCHAR,false,256);
		columns[3]= new ColumnDescriptor("HeimID",Types.INTEGER,false);
		columns[4]= new ColumnDescriptor("GastName",Types.VARCHAR,false,256);
		columns[5]= new ColumnDescriptor("GastID",Types.INTEGER,false);
		columns[6]= new ColumnDescriptor("MatchDate",Types.VARCHAR,false,256);
		columns[7]= new ColumnDescriptor("HeimTore",Types.INTEGER,false);
		columns[8]= new ColumnDescriptor("GastTore",Types.INTEGER,false);
		columns[9]= new ColumnDescriptor("Aufstellung",Types.BOOLEAN,false);
		columns[10]= new ColumnDescriptor("Status",Types.INTEGER,false);

	}

	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] { "CREATE INDEX IMATCHKURZINFO_1 ON " + getTableName() + "(" + columns[0].getColumnName() + ")" };
	}
	

	
	MatchKurzInfo getMatchesKurzInfo(int teamId, int matchtyp, int statistic, boolean home){

		MatchKurzInfo match = null;
		StringBuilder sql = new StringBuilder(200);
		ResultSet rs = null;
		String column = "";
		String column2 = "";
		try {
			switch(statistic){
			case MatchesOverviewCommonPanel.HighestVictory:
				column = home?"(HEIMTORE-GASTTORE) AS DIFF ":"(GASTTORE-HEIMTORE) AS DIFF ";
				column2 = home?">":"<";
				break;
			case MatchesOverviewCommonPanel.HighestDefeat:
				column = home?"(GASTTORE-HEIMTORE) AS DIFF ":"(HEIMTORE-GASTTORE) AS DIFF ";
				column2 = home?"<":">";
				break;
				
			}
			sql.append("SELECT *,");
			sql.append(column);
			sql.append(" FROM ").append(getTableName());
			sql.append(" WHERE ").append(home?"HEIMID":"GASTID").append(" = ");
			sql.append(teamId);
			sql.append(" AND HEIMTORE "+column2+" GASTTORE ");
			sql.append(getMatchTypWhereClause(matchtyp));
			
			sql.append(" ORDER BY DIFF DESC ");
			rs = adapter.executeQuery(sql.toString());

			rs.beforeFirst();

			if (rs.next()) {
				match =createMatchKurzInfo(rs);
			}
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"DB.getMatchesKurzInfo Error" + e);
			}
			return match; 
	}
	
	/**
	 * Wichtig: Wenn die Teamid = -1 ist muss der Matchtyp ALLE_SPIELE sein!
	 *
	 * @param teamId Die Teamid oder -1 für alle
	 * @param matchtyp Welche Matches? Konstanten im SpielePanel!
	 *
	 */
	MatchKurzInfo[] getMatchesKurzInfo(int teamId, int matchtyp, boolean asc) {
		StringBuilder sql = new StringBuilder(100);
		ResultSet rs = null;
		final ArrayList<IMatchKurzInfo> liste = new ArrayList<IMatchKurzInfo>();

		//Ohne Matchid nur AlleSpiele möglich!
		if ((teamId < 0) && (matchtyp != ISpielePanel.ALLE_SPIELE)) {
			return new MatchKurzInfo[0];
		}

		try {
			sql.append("SELECT * FROM ").append(getTableName());

			if ((teamId > -1) && (matchtyp != ISpielePanel.ALLE_SPIELE) && (matchtyp != ISpielePanel.NUR_FREMDE_SPIELE)) {
				sql.append(" WHERE ( GastID = " + teamId + " OR HeimID = " + teamId + " )");
			}

			if ((teamId > -1) && (matchtyp == ISpielePanel.NUR_FREMDE_SPIELE)) {
				sql.append(" WHERE ( GastID != " + teamId + " AND HeimID != " + teamId + " )");
			}

			//Nur eigene gewählt
			if (matchtyp >= 10) {
				matchtyp = matchtyp - 10;

				sql.append(" AND Status=" + IMatchKurzInfo.FINISHED);
			}
			sql.append( getMatchTypWhereClause(matchtyp));

			//nicht desc
			sql.append( " ORDER BY MatchDate");

			if (!asc) {
				sql.append(" DESC");
			}

			rs = adapter.executeQuery(sql.toString());

			rs.beforeFirst();

			while (rs.next()) {
				liste.add(createMatchKurzInfo(rs));
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DB.getMatchesKurzInfo Error" + e);
		}

//		matches = new MatchKurzInfo[liste.size()];
//		Helper.copyVector2Array(liste, matches);

		return liste.toArray(new MatchKurzInfo[liste.size()]);
	}
	
	
	private StringBuilder getMatchTypWhereClause(int matchtype){
		StringBuilder sql = new StringBuilder(50);
		switch (matchtype) {
			case ISpielePanel.NUR_EIGENE_SPIELE :

				//Nix zu tun, da die teamId die einzige Einschränkung ist
				break;
			case ISpielePanel.NUR_EIGENE_PFLICHTSPIELE :
				sql.append(" AND ( MatchTyp=" + IMatchLineup.QUALISPIEL);
				sql.append(" OR MatchTyp=" + IMatchLineup.LIGASPIEL);
				sql.append(" OR MatchTyp=" + IMatchLineup.POKALSPIEL + " )");
				break;
			case ISpielePanel.NUR_EIGENE_POKALSPIELE :
				sql.append(" AND MatchTyp=" + IMatchLineup.POKALSPIEL);
				break;
			case ISpielePanel.NUR_EIGENE_LIGASPIELE :
				sql.append(" AND MatchTyp=" + IMatchLineup.LIGASPIEL);
				break;
			case ISpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE :
				sql.append(" AND ( MatchTyp=" + IMatchLineup.TESTSPIEL);
				sql.append(" OR MatchTyp=" + IMatchLineup.TESTPOKALSPIEL);
				sql.append(" OR MatchTyp=" + IMatchLineup.INT_TESTCUPSPIEL);
				sql.append(" OR MatchTyp=" + IMatchLineup.INT_TESTSPIEL + " )");
				break;
			}
		return sql;
	}
	
	private MatchKurzInfo createMatchKurzInfo(ResultSet rs) throws SQLException {
		MatchKurzInfo match = new MatchKurzInfo();
		match.setMatchDate(rs.getString("MatchDate"));
		match.setGastID(rs.getInt("GastID"));
		match.setGastName(DBManager.deleteEscapeSequences(rs.getString("GastName")));
		match.setHeimID(rs.getInt("HeimID"));
		match.setHeimName(DBManager.deleteEscapeSequences(rs.getString("HeimName")));
		match.setMatchID(rs.getInt("MatchID"));
		match.setGastTore(rs.getInt("GastTore"));
		match.setHeimTore(rs.getInt("HeimTore"));
		match.setMatchTyp(rs.getInt("MatchTyp"));
		match.setMatchStatus(rs.getInt("Status"));
		match.setAufstellung(rs.getBoolean("Aufstellung"));
		return match;
	}
	
	/**
	 * Check if a match is already in the database.
	 */
	boolean isMatchVorhanden(int matchid) {
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

	/////////////////////////////////////////////////////////////////////////////////
	//MatchesASP MatchKurzInfo
	////////////////////////////////////////////////////////////////////////////////

	/**
	 * Get all matches with a certain status for the given team from the database.
	 * 
	 * @param teamId the teamid or -1 for all matches
	 * @param matchStatus the match status (e.g. IMatchKurzInfo.UPCOMING) or -1 to ignore this parameter
	 */
	MatchKurzInfo[] getMatchesKurzInfo(final int teamId, final int matchStatus) {
		MatchKurzInfo[] matches = new MatchKurzInfo[0];
		String sql = null;
		ResultSet rs = null;
		final Vector<IMatchKurzInfo> liste = new Vector<IMatchKurzInfo>();

		try {
			sql = "SELECT * FROM "+getTableName();

			if (teamId > -1 && matchStatus > -1) {
				sql += (" WHERE (GastID=" + teamId + " OR HeimID=" + teamId + ") AND Status=" + matchStatus);
			} else if (teamId > -1) {
				sql += (" WHERE GastID=" + teamId + " OR HeimID=" + teamId);
			} else if (matchStatus > -1) {
				sql += (" WHERE Status=" + matchStatus);
			}

			sql += " ORDER BY MatchDate DESC";
			rs = adapter.executeQuery(sql);

			while (rs.next()) {
				liste.add(createMatchKurzInfo(rs));
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DB.getMatchesKurzInfo Error" + e);
		}

		matches = new MatchKurzInfo[liste.size()];
		matches = liste.toArray(matches);
		return matches;
	}
	
	/**
	 * Get all matches for the given team from the database.
	 * 
	 * @param teamId the teamid or -1 for all matches
	 */
	MatchKurzInfo[] getMatchesKurzInfo(final int teamId) {
		return getMatchesKurzInfo(teamId, -1);
	}

	/**
	 * Saves matches into the databse.
	 */
	void storeMatchKurzInfos(MatchKurzInfo[] matches) {
		String sql = null;
		final String[] where = { "MatchID" };
		final String[] werte = new String[1];

		for (int i = 0;(matches != null) && (i < matches.length); i++) {
			werte[0] = "" + matches[i].getMatchID();
			delete(where, werte);
		
			try {
				//insert vorbereiten
				sql = "INSERT INTO "+getTableName()+" (  MatchID, MatchTyp, HeimName, HeimID, GastName, GastID, MatchDate, HeimTore, GastTore, Aufstellung, Status ) VALUES(";
				sql += (matches[i].getMatchID() + "," + matches[i].getMatchTyp() + ", '"
						+ DBManager.insertEscapeSequences(matches[i].getHeimName()) + "', " + matches[i].getHeimID() + ", '"
						+ DBManager.insertEscapeSequences(matches[i].getGastName()) + "', ");
				sql += (matches[i].getGastID() + ", '" + matches[i].getMatchDate() + "', " + matches[i].getHeimTore() + ", "
						+ matches[i].getGastTore() + ", " + matches[i].isAufstellung() + ", " + matches[i].getMatchStatus() + " )");
				adapter.executeUpdate(sql);
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"DB.storeMatchKurzInfos Error" + e);
				HOLogger.instance().log(getClass(),e);
			}
		}
	}
	
}
