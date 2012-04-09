package ho.core.db;

import ho.core.model.match.MatchLineupPlayer;
import ho.core.model.match.MatchLineupTeam;
import ho.core.util.HOLogger;
import ho.module.lineup.substitution.ISubstitution;

import java.sql.ResultSet;
import java.sql.Types;


public final class MatchLineupTeamTable extends AbstractTable {

	/** tablename **/
	public final static String TABLENAME = "MATCHLINEUPTEAM";
	
	protected MatchLineupTeamTable(JDBCAdapter  adapter){
		super(TABLENAME, adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[4];
		columns[0]= new ColumnDescriptor("MatchID",Types.INTEGER,false);
		columns[1]= new ColumnDescriptor("Erfahrung",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("TeamName",Types.VARCHAR,false,256);
		columns[3]= new ColumnDescriptor("TeamID",Types.INTEGER,false);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param matchID TODO Missing Method Parameter Documentation
	 * @param teamID TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	MatchLineupTeam getMatchLineupTeam(int matchID, int teamID) {
		MatchLineupTeam team = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			sql = "SELECT * FROM "+getTableName()+" WHERE MatchID = " + matchID + " AND TeamID = " + teamID;

			rs = adapter.executeQuery(sql);

			rs.first();

			team = new MatchLineupTeam(DBManager.deleteEscapeSequences(rs.getString("TeamName")), teamID, rs.getInt("Erfahrung"));
			team.setAufstellung(DBManager.instance().getMatchLineupPlayers(matchID, teamID));
			
			team.setSubstitutions(new java.util.ArrayList<ISubstitution>(DBManager.instance().getMatchSubstitutionsByMatchTeam(teamID, matchID)));
			
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DB.getMatchLineupTeam Error" + e);
			team = null;
		}

		return team;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param team TODO Missing Method Parameter Documentation
	 * @param matchID TODO Missing Method Parameter Documentation
	 */
	void storeMatchLineupTeam(MatchLineupTeam team, int matchID) {
		if (team != null) {
			final String[] where = { "MatchID" , "TeamID"};
			final String[] werte = { "" + matchID, "" +team.getTeamID()};			
			delete(where, werte);

			String sql = null;
			//saven
			try {
				//insert vorbereiten
				sql = "INSERT INTO "+getTableName()+" ( MatchID, Erfahrung, TeamName, TeamID ) VALUES(";
				sql += (matchID + "," + team.getErfahrung() + ", '" + DBManager.insertEscapeSequences(team.getTeamName()) + "'," + team.getTeamID() + " )");
				adapter.executeUpdate(sql);

				//Einträge noch saven
				for (int i = 0; i < team.getAufstellung().size(); i++) {
					DBManager.instance().storeMatchLineupPlayer((MatchLineupPlayer) team.getAufstellung().elementAt(i), matchID, team.getTeamID());
				}
				
				// Store Substitution
				
				DBManager.instance().storeMatchSubstitutionsByMatchTeam(matchID, team.getTeamID(), team.getSubstitutions());
				
				
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"DB.storeMatchLineupTeam Error" + e);
				HOLogger.instance().log(getClass(),e);
			}
		}
	}		
}
