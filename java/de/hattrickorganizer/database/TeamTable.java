package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;

import de.hattrickorganizer.model.Team;
import de.hattrickorganizer.tools.HOLogger;

/**
 * 
 * @author Thorsten Dietz
 *
 */
public final class TeamTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "TEAM";
	
	protected TeamTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}

	protected void initColumns() {
		columns = new ColumnDescriptor[15];
		columns[0]= new ColumnDescriptor("HRF_ID",Types.INTEGER,false,true);
		columns[1]= new ColumnDescriptor("TrainingsIntensitaet",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("TrainingsArt",Types.INTEGER,false);
		columns[3]= new ColumnDescriptor("sTrainingsArt",Types.VARCHAR,true,127);
		columns[4]= new ColumnDescriptor("iStimmung",Types.INTEGER,false);
		columns[5]= new ColumnDescriptor("sStimmung",Types.VARCHAR,true,127);
		columns[6]= new ColumnDescriptor("iSelbstvertrauen",Types.INTEGER,false);
		columns[7]= new ColumnDescriptor("sSelbstvertrauen",Types.VARCHAR,true,127);
		columns[8]= new ColumnDescriptor("iErfahrung541",Types.INTEGER,false);
		columns[9]= new ColumnDescriptor("iErfahrung433",Types.INTEGER,false);
		columns[10]= new ColumnDescriptor("iErfahrung352",Types.INTEGER,false);
		columns[11]= new ColumnDescriptor("iErfahrung451",Types.INTEGER,false);
		columns[12]= new ColumnDescriptor("iErfahrung532",Types.INTEGER,false);
		columns[13]= new ColumnDescriptor("iErfahrung343",Types.INTEGER,false);
		columns[14]= new ColumnDescriptor("StaminaTrainingPart",Types.INTEGER,false);
	}

	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX ITEAM_1 ON " + getTableName() + "(" + columns[0].getColumnName() + ")"};
	}
	/**
	 * speichert das Team
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param team TODO Missing Constructuor Parameter Documentation
	 */
	public void saveTeam(int hrfId, Team team) {
		String statement = null;
		final String[] awhereS = { "HRF_ID" };
		final String[] awhereV = { "" + hrfId };

		if (team != null) {
			//erst Vorhandene Aufstellung löschen
			delete( awhereS, awhereV );
			//insert vorbereiten
			statement =
				"INSERT INTO "+getTableName()+" ( TrainingsIntensitaet , StaminaTrainingPart, TrainingsArt, sTrainingsArt , iStimmung, sStimmung , iSelbstvertrauen, sSelbstvertrauen , iErfahrung541 , iErfahrung433 , iErfahrung352 , iErfahrung451 , iErfahrung532 , iErfahrung343, HRF_ID ) VALUES(";
			statement
				+= (""
					+ team.getTrainingslevel()
					+ ","
					+ team.getStaminaTrainingPart()
					+ ","
					+ team.getTrainingsArtAsInt()
					+ ",'"
					+ de.hattrickorganizer.database.DBZugriff.insertEscapeSequences(team.getTrainingsArt())
					+ "',"
					+ team.getStimmungAsInt()
					+ ",'"
					+ de.hattrickorganizer.database.DBZugriff.insertEscapeSequences(team.getStimmung())
					+ "',"
					+ team.getSelbstvertrauenAsInt()
					+ ",'"
					+ de.hattrickorganizer.database.DBZugriff.insertEscapeSequences(team.getSelbstvertrauen())
					+ "',"
					+ team.getErfahrung541()
					+ ","
					+ team.getErfahrung433()
					+ ","
					+ team.getErfahrung352()
					+ ","
					+ team.getErfahrung451()
					+ ","
					+ team.getErfahrung532()
					+ ","
					+ team.getErfahrung343()
					+ ","
					+ hrfId
					+ " )");
			adapter.executeUpdate(statement);
		}
	}

	/**
	 * Gibt die Teamstimmung und das Selbstvertrauen für ein HRFID zurück [0] = Stimmung [1] =
	 * Selbstvertrauen
	 *
	 * @param hrfid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public String[] getStimmmungSelbstvertrauen(int hrfid) {
		final int[] intvalue = new int[2];
		final String[] returnvalue = new String[2];
		final String sql = "SELECT iStimmung, iSelbstvertrauen, sStimmung, sSelbstvertrauen FROM "+getTableName()+" WHERE HRF_ID=" + hrfid;

		try {
			final ResultSet rs = adapter.executeQuery(sql);

			if (rs.first()) {
				intvalue[0] = rs.getInt("iStimmung");
				intvalue[1] = rs.getInt("iSelbstvertrauen");

				//Keine Sinnvollen Werte in der DB -> Strings holen
				if ((intvalue[0] <= 0) && (intvalue[1] <= 0)) {
					returnvalue[0] = rs.getString("sStimmung");
					returnvalue[1] = rs.getString("sSelbstvertrauen");
				} else {
					returnvalue[0] = de.hattrickorganizer.model.Team.getNameForStimmung(intvalue[0]);
					returnvalue[1] = de.hattrickorganizer.model.Team.getNameForSelbstvertrauen(intvalue[1]);
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getStimmmungSelbstvertrauen : " + e);
		}

		return returnvalue;
	}

	/**
	 * Gibt die Teamstimmung und das Selbstvertrauen für ein HRFID zurück [0] = Stimmung [1] =
	 * Selbstvertrauen
	 *
	 * @param hrfid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int[] getStimmmungSelbstvertrauenValues(int hrfid) {
		final int[] intvalue = new int[2];
		final String sql = "SELECT iStimmung, iSelbstvertrauen, sStimmung, sSelbstvertrauen FROM "+getTableName()+" WHERE HRF_ID=" + hrfid;

		try {
			final ResultSet rs = adapter.executeQuery(sql);

			if (rs.first()) {
				intvalue[0] = rs.getInt("iStimmung");
				intvalue[1] = rs.getInt("iSelbstvertrauen");
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getStimmmungSelbstvertrauenValues : " + e);
		}

		return intvalue;
	}
	
	/**
	 * lädt die Basics zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Team getTeam(int hrfID) {
		ResultSet rs = null;
		Team team = null;

		rs = getSelectByHrfID(hrfID);

		try {
			if (rs != null) {
				rs.first();
				team = new Team(rs);
				rs.close();
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getTeam: " + e);
		}

		return team;
	}

//	/**
//	 * liefert die Trainingsart für das angeforderte HRF
//	 *
//	 * @param hrfID TODO Missing Constructuor Parameter Documentation
//	 *
//	 * @return TODO Missing Return Method Documentation
//	 */
//	public int getTrainingsartByHRFID(int hrfID) {
//		ResultSet rs = null;
//		String sql = null;
//		int trTyp = -1;
//
//		sql = "SELECT TrainingsArt FROM Team WHERE HRF_ID = " + hrfID;
//		rs = adapter.executeQuery(sql);
//
//		try {
//			if (rs != null) {
//				rs.first();
//				trTyp = rs.getInt("TrainingsArt");
//			}
//		} catch (Exception e) {
//			HOLogger.instance().log(getClass(),"DatenbankZugriff.getTrainingsartByHRFID: " + e);
//		}
//
//		return trTyp;
//	}	
}
