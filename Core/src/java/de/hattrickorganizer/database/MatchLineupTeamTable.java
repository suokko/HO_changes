package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;

import plugins.IMatchLineupPlayer;
import plugins.ISubstitution;

import de.hattrickorganizer.model.matches.MatchLineupPlayer;
import de.hattrickorganizer.model.matches.MatchLineupTeam;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;

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
	protected MatchLineupTeam getMatchLineupTeam(int matchID, int teamID) {
		MatchLineupTeam team = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			sql = "SELECT * FROM "+getTableName()+" WHERE MatchID = " + matchID + " AND TeamID = " + teamID;

			rs = adapter.executeQuery(sql);

			rs.first();

			// Plan auslesen
			team = new MatchLineupTeam(DBZugriff.deleteEscapeSequences(rs.getString("TeamName")), teamID, rs.getInt("Erfahrung"));
			
			
			java.util.Vector<IMatchLineupPlayer> lineup = new Vector<IMatchLineupPlayer>();
			java.util.Vector<IMatchLineupPlayer> starters = new Vector<IMatchLineupPlayer>();
			java.util.Vector<IMatchLineupPlayer> all = DBZugriff.instance().getMatchLineupPlayers(matchID, teamID);
			IMatchLineupPlayer pl;
			for (int i = 0; i < all.size() ; i++) {
				pl = all.get(i);
				if (pl.getFieldPos() > 1000) {
					pl.setFieldPos(pl.getFieldPos() - 1000);
					starters.add(pl);
				} else {
					lineup.add(pl);
				}
				
			}
			
			team.setAufstellung(lineup);
			team.setStartingPlayers(starters);
			
			java.util.Vector<ISubstitution> subs = new java.util.Vector<ISubstitution>();
			Helper.copyArray2Vector(DBZugriff.instance().getMatchSubstitutionsByMatchTeam(teamID, matchID), subs);
			team.setSubstitutions(subs);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DB.getMatchLineupTeam Error" + e);

			//HOLogger.instance().log(getClass(),e);
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
	protected void storeMatchLineupTeam(MatchLineupTeam team, int matchID) {
		if (team != null) {
			final String[] where = { "MatchID" , "TeamID"};
			final String[] werte = { "" + matchID, "" +team.getTeamID()};			
			delete(where, werte);

			String sql = null;
			//saven
			try {
				//insert vorbereiten
				sql = "INSERT INTO "+getTableName()+" ( MatchID, Erfahrung, TeamName, TeamID ) VALUES(";
				sql += (matchID + "," + team.getErfahrung() + ", '" + DBZugriff.insertEscapeSequences(team.getTeamName()) + "'," + team.getTeamID() + " )");
				adapter.executeUpdate(sql);

				//Einträge noch saven
				for (int i = 0; i < team.getAufstellung().size(); i++) {
					DBZugriff.instance().storeMatchLineupPlayer((MatchLineupPlayer) team.getAufstellung().elementAt(i), matchID, team.getTeamID());
				}
				
				// Store Substitution
				ISubstitution sub;
				ISubstitution[] subs = new ISubstitution[team.getSubstitutions().size()];
				int arrayIndex = 0;
				for (int i = 0; i < team.getSubstitutions().size(); i++ ) {
					sub = team.getSubstitutions().get(i);
					if (sub != null) {
						subs[arrayIndex] = sub;
						arrayIndex++;
					}
				}
				DBZugriff.instance().storeMatchSubstitutionsByMatchTeam(matchID, team.getTeamID(), subs);
				
				// Store StartingLineup
				MatchLineupPlayer starter;
				plugins.IMatchLineupPlayer pl;
				for (int i = 0; i < team.getStartingPlayers().size(); i++) {
					pl = team.getStartingPlayers().get(i);
					// We add 1000 to starting lineup roleIds while in the db
					starter = new MatchLineupPlayer(1000 + pl.getFieldPos(), pl.getTaktik(), pl.getSpielerId(), 0, pl.getSpielerName(), pl.getStatus());
					DBZugriff.instance().storeMatchLineupPlayer(starter, matchID, team.getTeamID());
				}
				
				
				
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"DB.storeMatchLineupTeam Error" + e);
				HOLogger.instance().log(getClass(),e);
			}
		}
	}		
}
