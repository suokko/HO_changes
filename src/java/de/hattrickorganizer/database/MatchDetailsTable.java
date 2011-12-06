package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Vector;

import plugins.IMatchDetails;
import plugins.IMatchHighlight;
import plugins.IMatchLineup;
import plugins.ISpielePanel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.model.matches.MatchesOverviewRow;
import de.hattrickorganizer.tools.HOLogger;

final class MatchDetailsTable extends AbstractTable {

	public final static String TABLENAME = "MATCHDETAILS";

	protected MatchDetailsTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}
	
	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[39];
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
		columns[35]= new ColumnDescriptor("soldTerraces",Types.INTEGER,false);
		columns[36]= new ColumnDescriptor("soldBasic",Types.INTEGER,false);
		columns[37]= new ColumnDescriptor("soldRoof",Types.INTEGER,false);
		columns[38]= new ColumnDescriptor("soldVIP",Types.INTEGER,false);
	}
	
	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] {
			"CREATE INDEX IMATCHDETAILS_1 ON " + getTableName() + "(" + columns[0].getColumnName() + ")"};
	}	

	
	public MatchesOverviewRow[] getMatchesOverviewValues(int matchtype){
		ArrayList<MatchesOverviewRow> rows = new ArrayList<MatchesOverviewRow>(20);
		rows.add(new MatchesOverviewRow(HOVerwaltung.instance().getLanguageString("AlleSpiele"), MatchesOverviewRow.TYPE_ALL));
		rows.add(new MatchesOverviewRow(HOVerwaltung.instance().getLanguageString("Aufstellung"), MatchesOverviewRow.TYPE_TITLE));
		rows.add(new MatchesOverviewRow("5-5-0", MatchesOverviewRow.TYPE_SYSTEM));
		rows.add(new MatchesOverviewRow("5-4-1", MatchesOverviewRow.TYPE_SYSTEM));
		rows.add(new MatchesOverviewRow("5-3-2", MatchesOverviewRow.TYPE_SYSTEM));
		rows.add(new MatchesOverviewRow("5-2-3", MatchesOverviewRow.TYPE_SYSTEM));
		rows.add(new MatchesOverviewRow("4-5-1", MatchesOverviewRow.TYPE_SYSTEM));
		rows.add(new MatchesOverviewRow("4-4-2", MatchesOverviewRow.TYPE_SYSTEM));
		rows.add(new MatchesOverviewRow("4-3-3",MatchesOverviewRow.TYPE_SYSTEM));
		rows.add(new MatchesOverviewRow("3-5-2", MatchesOverviewRow.TYPE_SYSTEM));
		rows.add(new MatchesOverviewRow(HOVerwaltung.instance().getLanguageString("Taktik"), MatchesOverviewRow.TYPE_TITLE));
		rows.add(new MatchesOverviewRow(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_NORMAL), MatchesOverviewRow.TYPE_TACTICS, IMatchDetails.TAKTIK_NORMAL));
		rows.add(new MatchesOverviewRow(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_PRESSING), MatchesOverviewRow.TYPE_TACTICS, IMatchDetails.TAKTIK_PRESSING));
		rows.add(new MatchesOverviewRow(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_KONTER), MatchesOverviewRow.TYPE_TACTICS, IMatchDetails.TAKTIK_KONTER));
		rows.add(new MatchesOverviewRow(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_MIDDLE), MatchesOverviewRow.TYPE_TACTICS, IMatchDetails.TAKTIK_MIDDLE));
		rows.add(new MatchesOverviewRow(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_WINGS), MatchesOverviewRow.TYPE_TACTICS, IMatchDetails.TAKTIK_WINGS));
		rows.add(new MatchesOverviewRow(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_CREATIVE), MatchesOverviewRow.TYPE_TACTICS, IMatchDetails.TAKTIK_CREATIVE));
		rows.add(new MatchesOverviewRow(Matchdetails.getNameForTaktik(IMatchDetails.TAKTIK_LONGSHOTS), MatchesOverviewRow.TYPE_TACTICS, IMatchDetails.TAKTIK_LONGSHOTS));
		rows.add(new MatchesOverviewRow(HOVerwaltung.instance().getLanguageString("Einstellung"),MatchesOverviewRow.TYPE_TITLE));
		rows.add(new MatchesOverviewRow(Matchdetails.getNameForEinstellung(IMatchDetails.EINSTELLUNG_PIC), MatchesOverviewRow.TYPE_MOT, IMatchDetails.EINSTELLUNG_PIC));
		rows.add(new MatchesOverviewRow(Matchdetails.getNameForEinstellung(IMatchDetails.EINSTELLUNG_NORMAL), MatchesOverviewRow.TYPE_MOT, IMatchDetails.EINSTELLUNG_NORMAL));
		rows.add(new MatchesOverviewRow(Matchdetails.getNameForEinstellung(IMatchDetails.EINSTELLUNG_MOTS), MatchesOverviewRow.TYPE_MOT, IMatchDetails.EINSTELLUNG_MOTS));
		rows.add(new MatchesOverviewRow(HOVerwaltung.instance().getLanguageString("Wetter"), MatchesOverviewRow.TYPE_TITLE));
		rows.add(new MatchesOverviewRow("IMatchDetails.WETTER_SONNE", MatchesOverviewRow.TYPE_WEATHER, IMatchDetails.WETTER_SONNE));
		rows.add(new MatchesOverviewRow("IMatchDetails.WETTER_WOLKIG",  MatchesOverviewRow.TYPE_WEATHER, IMatchDetails.WETTER_WOLKIG));
		rows.add(new MatchesOverviewRow("IMatchDetails.WETTER_BEWOELKT", MatchesOverviewRow.TYPE_WEATHER, IMatchDetails.WETTER_BEWOELKT));
		rows.add(new MatchesOverviewRow("IMatchDetails.WETTER_REGEN",  MatchesOverviewRow.TYPE_WEATHER, IMatchDetails.WETTER_REGEN));
		setMatchesOverviewValues(rows,matchtype,true);
		setMatchesOverviewValues(rows,matchtype,false);
		return rows.toArray(new MatchesOverviewRow[rows.size()]);
	}
	
	
	
	private void setMatchesOverviewValues(ArrayList<MatchesOverviewRow> rows,int matchtype, boolean home){
		int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
		StringBuilder whereClause = new StringBuilder(100);
		whereClause.append(" AND ").append(home?"HEIMID=":"GASTID=").append(teamId);
		switch(matchtype){
			case ISpielePanel.NUR_EIGENE_PFLICHTSPIELE :
				whereClause.append(" AND ( MatchTyp=" + IMatchLineup.QUALISPIEL);
				whereClause.append(" OR MatchTyp=" + IMatchLineup.LIGASPIEL);
				whereClause.append(" OR MatchTyp=" + IMatchLineup.POKALSPIEL + " )");
			break;

		case ISpielePanel.NUR_EIGENE_POKALSPIELE :
			whereClause.append(" AND MatchTyp=" + IMatchLineup.POKALSPIEL);
			break;

		case ISpielePanel.NUR_EIGENE_LIGASPIELE :
			whereClause.append(" AND MatchTyp=" + IMatchLineup.LIGASPIEL);
			break;

		case ISpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE :
			whereClause.append(" AND ( MatchTyp=" + IMatchLineup.TESTSPIEL);
			whereClause.append(" OR MatchTyp=" + IMatchLineup.TESTPOKALSPIEL);
			whereClause.append(" OR MatchTyp=" + IMatchLineup.INT_TESTCUPSPIEL);
			whereClause.append(" OR MatchTyp=" + IMatchLineup.INT_TESTSPIEL + " )");
			break;
		}
		
		setMatchesOverviewRow(rows.get(0), whereClause,home);
		setFormationRows(rows,whereClause, home);
		setRows(rows, whereClause, home);
	}
	
	private void setFormationRows(ArrayList<MatchesOverviewRow> rows,StringBuilder whereClause, boolean home){
		
		StringBuilder sql = new StringBuilder(500);
		sql.append("select MATCHID,HEIMTORE,GASTTORE, "); 
		sql.append("LOCATE('5-5-0',MATCHREPORT) AS F550,");
		sql.append("LOCATE('5-4-1',MATCHREPORT) AS F541,");
		sql.append("LOCATE('5-3-2',MATCHREPORT) AS F532,");
		sql.append("LOCATE('5-2-3',MATCHREPORT) AS F523,");
		sql.append("LOCATE('4-5-1',MATCHREPORT) AS F451,");
		sql.append("LOCATE('4-4-2',MATCHREPORT) AS F442,");
		sql.append("LOCATE('4-3-3',MATCHREPORT) AS F433,");
		sql.append("LOCATE('3-5-2',MATCHREPORT) AS F352");
		sql.append(" FROM MATCHDETAILS inner join MATCHESKURZINFO ON MATCHDETAILS.MATCHID = MATCHESKURZINFO.MATCHID ");
		sql.append(" where 1=1 ");
		sql.append(whereClause);
		try{
			ResultSet rs = adapter.executeQuery(sql.toString());
			
			while(rs.next()){
				String[] fArray = {"0","",""};
				setSystem(rs.getInt("F550"), "5-5-0", fArray);
				setSystem(rs.getInt("F541"), "5-4-1", fArray);
				setSystem(rs.getInt("F532"), "5-3-2", fArray);
				setSystem(rs.getInt("F523"), "5-2-3", fArray);
				setSystem(rs.getInt("F451"), "4-5-1", fArray);
				setSystem(rs.getInt("F442"), "4-4-2", fArray);
				setSystem(rs.getInt("F433"), "4-3-3", fArray);
				setSystem(rs.getInt("F352"), "3-5-2", fArray);
				for (int i = 1; i <rows.size(); i++) {
					String txt = home?fArray[1]:fArray[2].length()==0?fArray[1]:fArray[2];

					if(rows.get(i).getType() == 1 && rows.get(i).getDescription().equals(txt)){
						rows.get(i).setMatchResult(rs.getInt("HEIMTORE"), rs.getInt("GASTTORE"), home);
					}
				}
			}
			} catch(Exception e){
				HOLogger.instance().log(getClass(),"DatenbankZugriff.setMatchesOverviewRow : " + e);
				HOLogger.instance().log(getClass(),e);
			}
	}
	
	private void setSystem(int column,String formation, String[] fArray){
		int max = Integer.parseInt(fArray[0]);
		if(column > 0){
			if(max == 0){
				fArray[0] = String.valueOf(column);
				fArray[1] = formation;
			} else if(max > column){
				fArray[2] = fArray[1];
				fArray[1] = formation;
			} else {
				fArray[0] = String.valueOf(column);
				fArray[2] = formation;
			}
		}
	}
	
	private void setRows(ArrayList<MatchesOverviewRow> rows,StringBuilder whereClause,boolean home){
		for (int i = 1; i < rows.size(); i++) {
			if(rows.get(i).getTypeValue() > Integer.MIN_VALUE){
				String whereSpecial = " AND "+columns[rows.get(i).getColumnIndex(home)].getColumnName()+" = "+rows.get(i).getTypeValue() ;
				setMatchesOverviewRow(rows.get(i), whereClause.append(whereSpecial),home);
			}
		}
	}
	
	private void setMatchesOverviewRow(MatchesOverviewRow row,StringBuilder whereClause, boolean home){
		StringBuilder sql = new StringBuilder(500);
		String from = " FROM MATCHDETAILS inner join MATCHESKURZINFO ON MATCHDETAILS.MATCHID = MATCHESKURZINFO.MATCHID ";
		sql.append("SELECT SUM(ANZAHL) AS A1,SUM(G1) AS G,SUM(U1) AS U,SUM(V1) AS V, SUM(HTORE1) AS HEIMTORE, SUM(GTORE1) AS GASTTORE FROM (");
		sql.append("select  COUNT(*) AS ANZAHL, 0 AS G1,0 AS U1, 0 AS V1, SUM(HEIMTORE) AS HTORE1, SUM(GASTTORE) AS GTORE1 "+from+" where 1 = 1 ");
		sql.append(whereClause).append(" UNION ");
		sql.append("SELECT 0 AS ANZAHL,  COUNT(*) AS G1,0 AS U1, 0 AS V1, 0 AS HTORE1, 0 AS GTORE1 "+from+" where HEIMTORE "+(home?">":"<")+" GASTTORE ");
		sql.append(whereClause).append(" UNION ");
		sql.append("SELECT  0 AS ANZAHL,  0 AS G1,COUNT(*) AS U1, 0 AS V1, 0 AS HTORE1, 0 AS GTORE1 "+from+" where HEIMTORE = GASTTORE ");
		sql.append(whereClause).append(" UNION ");
		sql.append("select  0 AS ANZAHL,  0 AS G1, 0 AS U1, COUNT(*) AS V1, 0 AS HTORE1, 0 AS GTORE1 "+from+" where HEIMTORE "+(home?"<":">")+" GASTTORE ");
		sql.append(whereClause);
		sql.append(")");
		try{
		ResultSet rs = adapter.executeQuery(sql.toString());
		if(rs.next()){
			row.setCount(rs.getInt("A1"));
			row.setWin(rs.getInt("G"));
			row.setDraw(rs.getInt("U"));
			row.setLoss(rs.getInt("V"));
			row.setHomeGoals(rs.getInt("HEIMTORE"));
			row.setAwayGoals(rs.getInt("GASTTORE"));
		}
		} catch(Exception e){
			HOLogger.instance().log(getClass(),"DatenbankZugriff.setMatchesOverviewRow : " + e);
			HOLogger.instance().log(getClass(),e);
		}
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
				details.setSoldTerraces(rs.getInt("soldTerraces"));
				details.setSoldBasic(rs.getInt("soldBasic"));
				details.setSoldRoof(rs.getInt("soldRoof"));
				details.setSoldVIP(rs.getInt("soldVIP"));
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
						+ "Matchreport, RegionID, soldTerraces, soldBasic, soldRoof, soldVIP"
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
						+ ", "
						+ details.getSoldTerraces()
						+ ", "
						+ details.getSoldBasic()
						+ ", "
						+ details.getSoldRoof()
						+ ", "
						+ details.getSoldVIP()
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
