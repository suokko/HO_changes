package ho.core.db;

import ho.core.model.HOVerwaltung;
import ho.core.model.match.IMatchDetails;
import ho.core.model.match.MatchType;
import ho.core.model.match.Matchdetails;
import ho.core.model.match.MatchesHighlightsStat;
import ho.core.model.match.MatchesOverviewRow;
import ho.core.util.HOLogger;
import ho.module.matches.SpielePanel;
import ho.module.matches.statistics.MatchesOverviewCommonPanel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


class MatchesOverviewQuery  {
	final static String KEY = "MatchesOverviewQuery";
	
	
	/**
	 *  
	 * @param teamId
	 * @param matchtype
	 * @param statistic
	 * @return count of matches
	 */
	static int getMatchesKurzInfoStatisticsCount(int teamId, int matchtype, int statistic){
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
		}
		sql.append(" ((HEIMID = ").append(teamId).append(whereHomeClause);
		sql.append(" OR (GASTID = ").append(teamId).append(whereAwayClause);
		sql.append(getMatchTypWhereClause(matchtype));

		rs = DBManager.instance().getAdapter().executeQuery(sql.toString());
		try {
			if(rs.next()){
				tmp = rs.getInt("C");
			}
		} catch (SQLException e) {
			HOLogger.instance().log(MatchesOverviewQuery.class, e);
		}
		return tmp;
	}
	
	static int getChangeGameStat(int teamId, int matchtype, int statistic){
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
		
		rs = DBManager.instance().getAdapter().executeQuery(sql.toString());
		try {
			for (int i = 0; rs.next(); i++) {
				tmp=i;
			}
		} catch (SQLException e) {
			HOLogger.instance().log(MatchesOverviewQuery.class,e);
		}
		return tmp;
		
	}
	
	/**
	 * SELECT TYP, COUNT(*)  FROM  MATCHHIGHLIGHTS join MATCHESKURZINFO ON MATCHHIGHLIGHTS.MATCHID = MATCHESKURZINFO.MATCHID 
WHERE TEAMID = 1247417 AND SubTyp in(0,10,20,30,50,60,70,80) GROUP BY TYP HAVING TYP in (1,2) ORDER BY TYP
	 * @param teamId
	 * @return
	 */
	
	public static MatchesHighlightsStat[] getChancesStat(boolean ownTeam, int matchtype ){
		int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
		
		MatchesHighlightsStat[] rows = new MatchesHighlightsStat[12];
		rows[0] = new MatchesHighlightsStat("highlight_penalty", "4,14,24,34,54,64,74,84");
		rows[1] = new MatchesHighlightsStat("highlight_freekick", "0,10,20,30,50,60,70,80");
		rows[2] = new MatchesHighlightsStat("highlight_links", "2,12,22,32,52,62,72,82");
		rows[3] = new MatchesHighlightsStat("highlight_middle", "1,11,21,31,51,61,71,81");
		rows[4] = new MatchesHighlightsStat("highlight_rechts", "3,13,23,33,53,63,73,83");
		rows[5] = new MatchesHighlightsStat("highlight_freekick", "85,86");
		rows[5].appendDescription("indirect");
		rows[6] = new MatchesHighlightsStat("Tactic.LongShots", "87");
		rows[7] = new MatchesHighlightsStat("highlight_counter", "40,41,42,43");
		rows[8] = new MatchesHighlightsStat("highlight_special","5,6,7,8,9,15,16,17,18,19,25,35,36,37,38,39");
		rows[9] = new MatchesHighlightsStat("highlight_yellowcard","5", "10,11");
		rows[10] = new MatchesHighlightsStat("highlight_redcard","5", "12,13,14");
		rows[11] = new MatchesHighlightsStat("Verletzt","0","90,91,92,93,94,95,96,97");
		
		for (int i = 0; i < rows.length; i++) {
			if(!rows[i].isTitle())
				fillMatchesOverviewChanceRow(ownTeam, teamId, rows[i], matchtype);
		}
		return rows;
	}
	
	
	private static void fillMatchesOverviewChanceRow(boolean ownTeam, int teamId, MatchesHighlightsStat row, int matchtype){
		StringBuilder sql = new StringBuilder(200);
		ResultSet rs = null;
		sql.append("SELECT TYP, COUNT(*) AS C  FROM  MATCHHIGHLIGHTS join MATCHESKURZINFO ON MATCHHIGHLIGHTS.MATCHID = MATCHESKURZINFO.MATCHID ");
		sql.append("WHERE TEAMID ");
		if(!ownTeam)
			sql.append("!");
		sql.append("=").append(teamId).append(" AND SUBTYP IN(");
		sql.append(row.getSubtyps()).append(")");
		sql.append(getMatchTypWhereClause(matchtype));
		sql.append("GROUP BY TYP HAVING TYP in (");
		sql.append(row.getTypes());
		sql.append(") ORDER BY TYP");
		rs = DBManager.instance().getAdapter().executeQuery(sql.toString());
		try {
			int typ = 0;
			while(rs.next()){
				typ = rs.getInt("TYP");
				if(typ == 1 )
					row.setGoals(rs.getInt("C"));
				if(typ == 2 || typ == 5 || typ == 0)
					row.setNoGoals(rs.getInt("C"));
			}
			rs.close();
		} catch (SQLException e) {
			HOLogger.instance().log(MatchesOverviewQuery.class, e);
		}
	}
	
	private static StringBuilder getMatchTypWhereClause(int matchtype){
		StringBuilder sql = new StringBuilder(50);
		switch (matchtype) {
			case SpielePanel.NUR_EIGENE_SPIELE :

				//Nix zu tun, da die teamId die einzige Einschränkung ist
				break;
			case SpielePanel.NUR_EIGENE_PFLICHTSPIELE :
				sql.append(" AND ( MatchTyp=" + MatchType.QUALIFICATION.getId());
				sql.append(" OR MatchTyp=" + MatchType.LEAGUE.getId());
				sql.append(" OR MatchTyp=" + MatchType.CUP.getId() + " )");
				break;
			case SpielePanel.NUR_EIGENE_POKALSPIELE :
				sql.append(" AND MatchTyp=" + MatchType.CUP.getId());
				break;
			case SpielePanel.NUR_EIGENE_LIGASPIELE :
				sql.append(" AND MatchTyp=" + MatchType.LEAGUE.getId());
				break;
			case SpielePanel.NUR_EIGENE_FREUNDSCHAFTSSPIELE :
				sql.append(" AND ( MatchTyp=" + MatchType.FRIENDLYNORMAL.getId());
				sql.append(" OR MatchTyp=" + MatchType.FRIENDLYCUPRULES.getId());
				sql.append(" OR MatchTyp=" + MatchType.INTFRIENDLYCUPRULES.getId());
				sql.append(" OR MatchTyp=" + MatchType.INTFRIENDLYNORMAL.getId() + " )");
				break;
			case SpielePanel.NUR_EIGENE_TOURNAMENTSPIELE :
				sql.append(" AND ( MatchTyp=" + MatchType.TOURNAMENTGROUP.getId());
				sql.append(" OR MatchTyp=" + MatchType.TOURNAMENTPLAYOFF.getId() + " )");
				break;
			}
		return sql;
	}
	
	static MatchesOverviewRow[] getMatchesOverviewValues(int matchtype){
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
	
	
	
	private static void setMatchesOverviewValues(ArrayList<MatchesOverviewRow> rows,int matchtype, boolean home){
		int teamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
		StringBuilder whereClause = new StringBuilder(100);
		whereClause.append(" AND ").append(home?"HEIMID=":"GASTID=").append(teamId);
		whereClause.append(getMatchTypWhereClause(matchtype));
		setMatchesOverviewRow(rows.get(0), whereClause.toString(),home);
		setFormationRows(rows,whereClause, home);
		setRows(rows, whereClause, home);
	}
	
	private static void setFormationRows(ArrayList<MatchesOverviewRow> rows,StringBuilder whereClause, boolean home){
		
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
			ResultSet rs = DBManager.instance().getAdapter().executeQuery(sql.toString());
			
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
				
				HOLogger.instance().log(MatchesOverviewQuery.class,e);
			}
	}
	
	private static void setSystem(int column,String formation, String[] fArray){
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
	
	private static void setRows(ArrayList<MatchesOverviewRow> rows,StringBuilder whereClause,boolean home){
		for (int i = 1; i < rows.size(); i++) {
			if(rows.get(i).getTypeValue() > Integer.MIN_VALUE){
				String whereSpecial = " AND "+rows.get(i).getColumnName(home)+" = "+rows.get(i).getTypeValue() ;
				setMatchesOverviewRow(rows.get(i), whereClause+whereSpecial,home);
			}
		}
	}
	
	private static void setMatchesOverviewRow(MatchesOverviewRow row,String whereClause, boolean home){
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
		ResultSet rs = DBManager.instance().getAdapter().executeQuery(sql.toString());
		if(rs.next()){
			row.setCount(rs.getInt("A1"));
			row.setWin(rs.getInt("G"));
			row.setDraw(rs.getInt("U"));
			row.setLoss(rs.getInt("V"));
			row.setHomeGoals(rs.getInt("HEIMTORE"));
			row.setAwayGoals(rs.getInt("GASTTORE"));
		}
		} catch(Exception e){
			HOLogger.instance().log(MatchesOverviewQuery.class,e);
		}
	}

}
