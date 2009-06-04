package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import plugins.IMatchHighlight;

import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.tools.HOLogger;

public final class MatchDetailsTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "MATCHDETAILS";
	
	protected MatchDetailsTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}
	
	protected void initColumns() {
		columns = new ColumnDescriptor[35];
		columns[0]= new ColumnDescriptor("MatchID",Types.INTEGER,false,true);
		columns[1]= new ColumnDescriptor("ArenaId",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("ArenaName",Types.VARCHAR,false,256);
		columns[3]= new ColumnDescriptor("Fetchdatum",Types.TIMESTAMP,false);
		columns[4]= new ColumnDescriptor("GastName",Types.VARCHAR,false,256);
		columns[5]= new ColumnDescriptor("GastID",Types.INTEGER,false);
		columns[6]= new ColumnDescriptor("GastEinstellung",Types.INTEGER,false);
		columns[7]= new ColumnDescriptor("GastTore",Types.INTEGER,false);
		columns[8]= new ColumnDescriptor("GastLeftAtt",Types.INTEGER,false);
		columns[9]= new ColumnDescriptor("GastLeftDef",Types.INTEGER,false);
		columns[10]= new ColumnDescriptor("GastMidAtt",Types.INTEGER,false);
		columns[11]= new ColumnDescriptor("GastMidDef",Types.INTEGER,false);
		columns[12]= new ColumnDescriptor("GastMidfield",Types.INTEGER,false);
		columns[13]= new ColumnDescriptor("GastRightAtt",Types.INTEGER,false);
		columns[14]= new ColumnDescriptor("GastRightDef",Types.INTEGER,false);
		columns[15]= new ColumnDescriptor("GastTacticSkill",Types.INTEGER,false);
		columns[16]= new ColumnDescriptor("GastTacticType",Types.INTEGER,false);
		columns[17]= new ColumnDescriptor("HeimName",Types.VARCHAR,false,256);
		columns[18]= new ColumnDescriptor("HeimId",Types.INTEGER,false);
		columns[19]= new ColumnDescriptor("HeimEinstellung",Types.INTEGER,false);
		columns[20]= new ColumnDescriptor("HeimTore",Types.INTEGER,false);
		columns[21]= new ColumnDescriptor("HeimLeftAtt",Types.INTEGER,false);
		columns[22]= new ColumnDescriptor("HeimLeftDef",Types.INTEGER,false);
		columns[23]= new ColumnDescriptor("HeimMidAtt",Types.INTEGER,false);
		columns[24]= new ColumnDescriptor("HeimMidDef",Types.INTEGER,false);
		columns[25]= new ColumnDescriptor("HeimMidfield",Types.INTEGER,false);
		columns[26]= new ColumnDescriptor("HeimRightAtt",Types.INTEGER,false);
		columns[27]= new ColumnDescriptor("HeimRightDef",Types.INTEGER,false);
		columns[28]= new ColumnDescriptor("HeimTacticSkill",Types.INTEGER,false);
		columns[29]= new ColumnDescriptor("HeimTacticType",Types.INTEGER,false);
		columns[30]= new ColumnDescriptor("SpielDatum",Types.TIMESTAMP,false);
		columns[31]= new ColumnDescriptor("WetterId",Types.INTEGER,false);
		columns[32]= new ColumnDescriptor("Zuschauer",Types.INTEGER,false);
		columns[33]= new ColumnDescriptor("Matchreport",Types.VARCHAR,false,8196);
		columns[34]= new ColumnDescriptor("RegionID",Types.INTEGER,false);
	}
	
	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX IMATCHDETAILS_1 ON " + getTableName() + "(" + columns[0].getColumnName() + ")"};
	}	

	/**
	 * Gibt die MatchDetails zu einem Match zurück
	 *
	 * @param matchId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Matchdetails getMatchDetails(int matchId) {
		final Matchdetails details = new Matchdetails();

		try {
			String sql = "SELECT * FROM "+getTableName()+" WHERE MatchID=" + matchId;
			ResultSet rs = adapter.executeQuery(sql);

			if (rs.first()) {
				details.setArenaID(rs.getInt("ArenaId"));
				details.setArenaName(de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("ArenaName")));
				details.setRegionId(rs.getInt("RegionID"));
				details.setFetchDatum(rs.getTimestamp("Fetchdatum"));
				details.setGastId(rs.getInt("GastId"));
				details.setGastName(de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("GastName")));
				details.setGuestEinstellung(rs.getInt("GastEinstellung"));
				details.setGuestGoals(rs.getInt("GastTore"));
				details.setGuestLeftAtt(rs.getInt("GastLeftAtt"));
				details.setGuestLeftDef(rs.getInt("GastLeftDef"));
				details.setGuestMidAtt(rs.getInt("GastMidAtt"));
				details.setGuestMidDef(rs.getInt("GastMidDef"));
				details.setGuestMidfield(rs.getInt("GastMidfield"));
				details.setGuestRightAtt(rs.getInt("GastRightAtt"));
				details.setGuestRightDef(rs.getInt("GastRightDef"));
				details.setGuestTacticSkill(rs.getInt("GastTacticSkill"));
				details.setGuestTacticType(rs.getInt("GastTacticType"));
				details.setHeimId(rs.getInt("HeimId"));
				details.setHeimName(de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("HeimName")));
				details.setHomeEinstellung(rs.getInt("HeimEinstellung"));
				details.setHomeGoals(rs.getInt("HeimTore"));
				details.setHomeLeftAtt(rs.getInt("HeimLeftAtt"));
				details.setHomeLeftDef(rs.getInt("HeimLeftDef"));
				details.setHomeMidAtt(rs.getInt("HeimMidAtt"));
				details.setHomeMidDef(rs.getInt("HeimMidDef"));
				details.setHomeMidfield(rs.getInt("HeimMidfield"));
				details.setHomeRightAtt(rs.getInt("HeimRightAtt"));
				details.setHomeRightDef(rs.getInt("HeimRightDef"));
				details.setHomeTacticSkill(rs.getInt("HeimTacticSkill"));
				details.setHomeTacticType(rs.getInt("HeimTacticType"));
				details.setMatchID(matchId);
				details.setSpielDatum(rs.getTimestamp("SpielDatum"));
				details.setWetterId(rs.getInt("WetterId"));
				details.setZuschauer(rs.getInt("Zuschauer"));
				details.setMatchreport(DBZugriff.deleteEscapeSequences(rs.getString("Matchreport")));
				Vector<IMatchHighlight> vMatchHighlights = DBZugriff.instance().getMatchHighlights(matchId);
				//Alle Highlights in die Details packen
				details.setHighlights(vMatchHighlights);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DatenbankZugriff.getMatchDetails : " + e);
			HOLogger.instance().log(getClass(),e);
		}

		return details;
	}
	
	/**
	 * speichert die MatchDetails
	 *
	 * @param details TODO Missing Constructuor Parameter Documentation
	 */
	public void storeMatchDetails(Matchdetails details) {
		if (details != null) {
			//Vorhandene Einträge entfernen
			final String[] where = { "MatchID" };
			final String[] werte = { "" + details.getMatchID()};
			delete(where, werte);
			String sql = null;

			//saven
			try {
				//insert vorbereiten
				sql =
					"INSERT INTO "+getTableName()+" ( MatchID, ArenaId, ArenaName, Fetchdatum, GastId, GastName, GastEinstellung, GastTore, "
						+ "GastLeftAtt, GastLeftDef, GastMidAtt, GastMidDef, GastMidfield, GastRightAtt, GastRightDef, GastTacticSkill, GastTacticType, "
						+ "HeimId, HeimName, HeimEinstellung, HeimTore, HeimLeftAtt, HeimLeftDef, HeimMidAtt, HeimMidDef, HeimMidfield, HeimRightAtt, HeimRightDef, "
						+ "HeimTacticSkill, HeimTacticType, SpielDatum, WetterId, Zuschauer, "
						+ "Matchreport, RegionID"
						+ ") VALUES ("
						+ details.getMatchID()
						+ ", "
						+ details.getArenaID()
						+ ", '"
						+ DBZugriff.insertEscapeSequences(details.getArenaName())
						+ "', '"
						+ details.getFetchDatum().toString()
						+ "', "
						+ details.getGastId()
						+ ", '"
						+ DBZugriff.insertEscapeSequences(details.getGastName())
						+ "', "
						+ details.getGuestEinstellung()
						+ ", "
						+ details.getGuestGoals()
						+ ", "
						+ details.getGuestLeftAtt()
						+ ", "
						+ details.getGuestLeftDef()
						+ ", "
						+ details.getGuestMidAtt()
						+ ", "
						+ details.getGuestMidDef()
						+ ", "
						+ details.getGuestMidfield()
						+ ", "
						+ details.getGuestRightAtt()
						+ ", "
						+ details.getGuestRightDef()
						+ ", "
						+ details.getGuestTacticSkill()
						+ ", "
						+ details.getGuestTacticType()
						+ ", "
						+ details.getHeimId()
						+ ", '"
						+ DBZugriff.insertEscapeSequences(details.getHeimName())
						+ "', "
						+ details.getHomeEinstellung()
						+ ", "
						+ details.getHomeGoals()
						+ ", "
						+ details.getHomeLeftAtt()
						+ ", "
						+ details.getHomeLeftDef()
						+ ", "
						+ details.getHomeMidAtt()
						+ ", "
						+ details.getHomeMidDef()
						+ ", "
						+ details.getHomeMidfield()
						+ ", "
						+ details.getHomeRightAtt()
						+ ", "
						+ details.getHomeRightDef()
						+ ", "
						+ details.getHomeTacticSkill()
						+ ", "
						+ details.getHomeTacticType()
						+ ", '"
						+ details.getSpielDatum().toString()
						+ "', "
						+ details.getWetterId()
						+ ", "
						+ details.getZuschauer()
						+ ", '"
						+ DBZugriff.insertEscapeSequences(details.getMatchreport())
						+ "', "
						+ details.getRegionId()
						+ ")";

				adapter.executeUpdate(sql);

				//Highlights
				DBZugriff.instance().storeMatchHighlights(details);

				//Workaround, wenn das Spiel nicht auf Finished gesetzt wird in den MatchKurzInfos
				if (details.getZuschauer() > 0) {
					//Spiel ist auf jeden Fall fertig!
					sql = "UPDATE MATCHESKURZINFO SET Status=1, HeimTore=" + details.getHomeGoals() + " , GastTore=" + details.getGuestGoals() + "  WHERE MatchID=" + details.getMatchID();
					adapter.executeUpdate(sql);
				}
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"DB.storeMatchDetails Error" + e);
				HOLogger.instance().log(getClass(),e);
			}
		}
	}	
}
