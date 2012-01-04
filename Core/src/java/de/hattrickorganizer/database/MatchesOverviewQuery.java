package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import plugins.IMatchLineup;
import plugins.ISpielePanel;

import de.hattrickorganizer.gui.matches.MatchesOverviewCommonPanel;
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
	@Override
	protected void initColumns() {
		// TODO Auto-generated method stub
		
	}
}
