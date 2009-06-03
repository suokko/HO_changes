package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import plugins.IMatchLineup;
import plugins.ISpielePanel;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.MyHelper;

public final class MatchesKurzInfoTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "MATCHESKURZINFO";
	
	protected MatchesKurzInfoTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}
	
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

	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX IMATCHKURZINFO_1 ON " + getTableName() + "(" + columns[0].getColumnName() + ")"};
	}
	
	/**
	 * Wichtig: Wenn die Teamid = -1 ist muss der Matchtyp ALLE_SPIELE sein!
	 *
	 * @param teamId Die Teamid oder -1 für alle
	 * @param matchtyp Welche Matches? Konstanten im SpielePanel!
	 * @param asc TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public MatchKurzInfo[] getMatchesKurzInfo(int teamId, int matchtyp, boolean asc) {
		MatchKurzInfo[] matches = new MatchKurzInfo[0];
		MatchKurzInfo match = null;
		String sql = null;
		ResultSet rs = null;
		final Vector liste = new Vector();

		//Ohne Matchid nur AlleSpiele möglich!
		if ((teamId < 0) && (matchtyp != ISpielePanel.ALLE_SPIELE)) {
			return matches;
		}

		try {
			sql = "SELECT * FROM "+getTableName();

			if ((teamId > -1) && (matchtyp != ISpielePanel.ALLE_SPIELE) && (matchtyp != ISpielePanel.NUR_FREMDE_SPIELE)) {
				sql += (" WHERE ( GastID = " + teamId + " OR HeimID = " + teamId + " )");
			}

			if ((teamId > -1) && (matchtyp == ISpielePanel.NUR_FREMDE_SPIELE)) {
				sql += (" WHERE ( GastID != " + teamId + " AND HeimID != " + teamId + " )");
			}

			//Nur eigene gewählt
			if (matchtyp >= 10) {
				matchtyp = matchtyp - 10;

				sql += (" AND Status=" + MatchKurzInfo.FINISHED);
			}

			//Matchtypen
			switch (matchtyp) {
				case ISpielePanel.NUR_EIGENE_SPIELE :

					//Nix zu tun, da die teamId die einzige Einschränkung ist
					break;

				case ISpielePanel.NUR_EIGENE_PFLICHTSPIELE :
					sql += (" AND ( MatchTyp=" + IMatchLineup.QUALISPIEL);
					sql += (" OR MatchTyp=" + IMatchLineup.LIGASPIEL);
					sql += (" OR MatchTyp=" + IMatchLineup.POKALSPIEL + " )");
					break;

				case ISpielePanel.NUR_EIGENE_POKALSPIELE :
					sql += (" AND MatchTyp=" + IMatchLineup.POKALSPIEL);
					break;

				case ISpielePanel.NUR_EIGENE_LIGASPIELE :
					sql += (" AND MatchTyp=" + IMatchLineup.LIGASPIEL);
					break;

				case ISpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE :
					sql += (" AND ( MatchTyp=" + IMatchLineup.TESTSPIEL);
					sql += (" OR MatchTyp=" + IMatchLineup.TESTPOKALSPIEL);
					sql += (" OR MatchTyp=" + IMatchLineup.INT_TESTCUPSPIEL);
					sql += (" OR MatchTyp=" + IMatchLineup.INT_TESTSPIEL + " )");
					break;
			}

			//nicht desc
			sql += " ORDER BY MatchDate";

			if (!asc) {
				sql += " DESC";
			}

			rs = adapter.executeQuery(sql);

			rs.beforeFirst();

			while (rs.next()) {
				//Paarung auslesen
				match = new MatchKurzInfo();
				match.setMatchDate(rs.getString("MatchDate"));
				match.setGastID(rs.getInt("GastID"));
				match.setGastName(DBZugriff.deleteEscapeSequences(rs.getString("GastName")));
				match.setHeimID(rs.getInt("HeimID"));
				match.setHeimName(DBZugriff.deleteEscapeSequences(rs.getString("HeimName")));
				match.setMatchID(rs.getInt("MatchID"));
				match.setGastTore(rs.getInt("GastTore"));
				match.setHeimTore(rs.getInt("HeimTore"));

				match.setMatchTyp(rs.getInt("MatchTyp"));
				match.setMatchStatus(rs.getInt("Status"));
				match.setAufstellung(rs.getBoolean("Aufstellung"));

				//Adden
				liste.add(match);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DB.getMatchesKurzInfo Error" + e);

			//HOLogger.instance().log(getClass(),e);
		}

		matches = new MatchKurzInfo[liste.size()];
		MyHelper.copyVector2Array(liste, matches);

		return matches;
	}
	
	/**
	 * Ist das Match schon in der Datenbank vorhanden?
	 *
	 * @param matchid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public boolean isMatchVorhanden(int matchid) {
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
	 * holt die MAtches zu einem Team aus der DB
	 *
	 * @param teamId Die Teamid oder -1 für alle
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public MatchKurzInfo[] getMatchesKurzInfo(int teamId) {
		MatchKurzInfo[] matches = new MatchKurzInfo[0];
		MatchKurzInfo match = null;
		String sql = null;
		ResultSet rs = null;
		final Vector liste = new Vector();

		try {
			sql = "SELECT * FROM "+getTableName();

			if (teamId > -1) {
				sql += (" WHERE GastID = " + teamId + " OR HeimID = " + teamId);
			}

			sql += " ORDER BY MatchDate DESC";

			rs = adapter.executeQuery(sql);

			rs.beforeFirst();

			while (rs.next()) {
				//Paarung auslesen
				match = new MatchKurzInfo();
				match.setMatchDate(rs.getString("MatchDate"));
				match.setGastID(rs.getInt("GastID"));
				match.setGastName(DBZugriff.deleteEscapeSequences(rs.getString("GastName")));
				match.setHeimID(rs.getInt("HeimID"));
				match.setHeimName(DBZugriff.deleteEscapeSequences(rs.getString("HeimName")));
				match.setMatchID(rs.getInt("MatchID"));
				match.setGastTore(rs.getInt("GastTore"));
				match.setHeimTore(rs.getInt("HeimTore"));

				match.setMatchTyp(rs.getInt("MatchTyp"));
				match.setMatchStatus(rs.getInt("Status"));
				match.setAufstellung(rs.getBoolean("Aufstellung"));

				//Adden
				liste.add(match);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DB.getMatchesKurzInfo Error" + e);

			//HOLogger.instance().log(getClass(),e);
		}

		matches = new MatchKurzInfo[liste.size()];
		MyHelper.copyVector2Array(liste, matches);

		return matches;
	}

	/**
	 * speichert die Matches
	 *
	 * @param matches TODO Missing Constructuor Parameter Documentation
	 */
	public void storeMatchKurzInfos(MatchKurzInfo[] matches) {
		String sql = null;
		final String[] where = { "MatchID" };
		final String[] werte = new String[1];

		for (int i = 0;(matches != null) && (i < matches.length); i++) {
			werte[0] = "" + matches[i].getMatchID();
			delete(where, werte);
		
			try {
				//insert vorbereiten
				sql = "INSERT INTO "+getTableName()+" (  MatchID, MatchTyp, HeimName, HeimID, GastName, GastID, MatchDate, HeimTore, GastTore, Aufstellung, Status ) VALUES(";
				sql
					+= (matches[i].getMatchID()
						+ ","
						+ matches[i].getMatchTyp()
						+ ", '"
						+ DBZugriff.insertEscapeSequences(matches[i].getHeimName())
						+ "', "
						+ matches[i].getHeimID()
						+ ", '"
						+ DBZugriff.insertEscapeSequences(matches[i].getGastName())
						+ "', ");
				sql
					+= (matches[i].getGastID()
						+ ", '"
						+ matches[i].getMatchDate()
						+ "', "
						+ matches[i].getHeimTore()
						+ ", "
						+ matches[i].getGastTore()
						+ ", "
						+ matches[i].isAufstellung()
						+ ", "
						+ matches[i].getMatchStatus()
						+ " )");
				adapter.executeUpdate(sql);
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"DB.storeMatchKurzInfos Error" + e);
				HOLogger.instance().log(getClass(),e);
			}
		}
	}
	
}
