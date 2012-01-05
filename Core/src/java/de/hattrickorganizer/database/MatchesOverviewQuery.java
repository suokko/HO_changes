package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import plugins.IMatchDetails;
import plugins.IMatchLineup;
import plugins.ISpielePanel;

import de.hattrickorganizer.gui.matches.MatchesOverviewCommonPanel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.model.matches.MatchesOverviewRow;
import de.hattrickorganizer.tools.HOLogger;

class MatchesOverviewQuery extends AbstractTable {
	final static String KEY = "MatchesOverviewQuery";
	MatchesOverviewQuery(JDBCAdapter adapter){
		super(KEY,adapter);
	}
	/**
	 *  
	 * @param teamId
	 * @param matchtype
	 * @param statistic
	 * @return count of matches
	 */
	int getMatchesKurzInfoStatisticsCount(int teamId, int matchtype, int statistic){
		int tmp = 0;
		StringBuilder sql = new StringBuilder(200);
		ResultSet rs = null;
		String whereHomeClause = "";
		String whereAwayClause = "";
		sql.append("SELECT COUNT(*) AS C ");
		sql.append(" FROM MATCHESKURZINFO ");
		sql.append(" WHERE ");
		switch(statistic){
			case MatchesOverviewCommonPanel.LeadingHTLosingFT:
			case MatchesOverviewCommonPanel.TrailingHTWinningFT:
				return getChangeGameStat(teamId,matchtype,statistic);
		
			case MatchesOverviewCommonPanel.WonWithoutOppGoal:
				whereHomeClause=" AND HEIMTORE > GASTTORE AND GASTTORE = 0 )";
				whereAwayClause=" AND HEIMTORE < GASTTORE AND HEIMTORE = 0 ))";
				break;
			case MatchesOverviewCommonPanel.LostWithoutOwnGoal:
				whereHomeClause=" AND HEIMTORE < GASTTORE AND HEIMTORE = 0 )";
				whereAwayClause=" AND HEIMTORE > GASTTORE AND GASTTORE = 0 ))";
				break;
			case MatchesOverviewCommonPanel.FiveGoalsDiffWin:
				whereHomeClause=" AND HEIMTORE > GASTTORE AND (HEIMTORE - GASTTORE ) >= 5 )";
				whereAwayClause=" AND HEIMTORE < GASTTORE AND (GASTTORE - HEIMTORE ) >= 5 ))";
				break;
			case MatchesOverviewCommonPanel.FiveGoalsDiffDefeat:
				whereHomeClause=" AND HEIMTORE < GASTTORE AND (GASTTORE - HEIMTORE ) >= 5 )";
				whereAwayClause=" AND HEIMTORE > GASTTORE AND (HEIMTORE - GASTTORE ) >= 5 ))";
				break;
			case MatchesOverviewCommonPanel.YELLOW_CARDS:
			case MatchesOverviewCommonPanel.RED_CARDS:
				return getHighlightStats(teamId, matchtype, statistic);
		}
		sql.append(" ((HEIMID = ").append(teamId).append(whereHomeClause);
		sql.append(" OR (GASTID = ").append(teamId).append(whereAwayClause);
		sql.append(getMatchTypWhereClause(matchtype));

		rs = adapter.executeQuery(sql.toString());
		try {
			if(rs.next()){
				tmp = rs.getInt("C");
			}
		} catch (SQLException e) {
			HOLogger.instance().log(getClass(),"DB.getMatchesKurzInfo Error" + e);
		}
		return tmp;
	}
	
	int getChangeGameStat(int teamId, int matchtype, int statistic){
		StringBuilder sql = new StringBuilder(200);
		ResultSet rs = null;
		int tmp = 0;
		sql.append("SELECT MatchTyp,(MATCHHIGHLIGHTS.HEIMTORE- MATCHHIGHLIGHTS.GASTTORE) AS DIFFH, (MATCHESKURZINFO.HEIMTORE-MATCHESKURZINFO.GASTTORE) AS DIFF, HEIMID, GASTID,MATCHID");
		sql.append(" FROM  MATCHHIGHLIGHTS join MATCHESKURZINFO ON MATCHHIGHLIGHTS.MATCHID = MATCHESKURZINFO.MATCHID ");
		sql.append(" WHERE TYP = 0 AND MINUTE = 45 AND MATCHHIGHLIGHTS.TEAMID = 0 ");
		switch(statistic){
		case MatchesOverviewCommonPanel.LeadingHTLosingFT:
			sql.append("AND ((HEIMID = "+teamId+" AND DIFFH >0 AND DIFF <0) or (GASTID = "+teamId+" AND DIFFH <0 AND DIFF >0))");
			break;
		case MatchesOverviewCommonPanel.TrailingHTWinningFT:
			sql.append("AND ((HEIMID = "+teamId+" AND DIFFH <0 AND DIFF >0) or (GASTID = "+teamId+" AND DIFFH >0 AND DIFF <0))");
			break;
		}
		sql.append(getMatchTypWhereClause(matchtype));
		
		rs = adapter.executeQuery(sql.toString());
		try {
			for (int i = 0; rs.next(); i++) {
				tmp=i;
			}
		} catch (SQLException e) {
			HOLogger.instance().log(getClass(),"DB.getMatchesKurzInfo Error" + e);
		}
		return tmp;
		
	}
	
	private int getHighlightStats( int teamId, int matchtype, int statistic){
		StringBuilder sql = new StringBuilder(200);
		ResultSet rs = null;
		int tmp = 0;
		sql.append("SELECT COUNT(*) AS C  FROM  MATCHHIGHLIGHTS join MATCHESKURZINFO ON MATCHHIGHLIGHTS.MATCHID = MATCHESKURZINFO.MATCHID WHERE ");
		sql.append(" TEAMID =").append(teamId).append(" AND ");
		switch(statistic){
		case MatchesOverviewCommonPanel.YELLOW_CARDS:
			sql.append(" TYP = 5 AND SUBTYP in(10,11) ");
			break;
		case MatchesOverviewCommonPanel.RED_CARDS:
			sql.append(" TYP = 5 AND SUBTYP in(12,13,14) ");
			break;
		}
		sql.append(getMatchTypWhereClause(matchtype));
		
		rs = adapter.executeQuery(sql.toString());
		try {
			if(rs.next())
				tmp = rs.getInt("C");
		} catch (SQLException e) {
			HOLogger.instance().log(getClass(),"DB.getMatchesKurzInfo Error" + e);
		}
		return tmp;
		
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
	
	MatchesOverviewRow[] getMatchesOverviewValues(int matchtype){
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
		whereClause.append(getMatchTypWhereClause(matchtype));
		setMatchesOverviewRow(rows.get(0), whereClause.toString(),home);
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
				String whereSpecial = " AND "+rows.get(i).getColumnName(home)+" = "+rows.get(i).getTypeValue() ;
				setMatchesOverviewRow(rows.get(i), whereClause+whereSpecial,home);
			}
		}
	}
	
	private void setMatchesOverviewRow(MatchesOverviewRow row,String whereClause, boolean home){
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
	@Override
	protected void initColumns() {
		// TODO Auto-generated method stub
		
	}
}
