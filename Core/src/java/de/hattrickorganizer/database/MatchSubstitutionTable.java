package de.hattrickorganizer.database;

import java.sql.ResultSet;
import java.sql.Types;

import plugins.ISubstitution;
import de.hattrickorganizer.model.lineup.Substitution;
import de.hattrickorganizer.tools.HOLogger;

public class MatchSubstitutionTable extends AbstractTable{
	/** tablename **/
	public final static String TABLENAME = "MATCHSUBSTITUTION";

	// Dummy value for ids not used (hrf, team, match)
	private final static int DUMMY = -101;

	protected MatchSubstitutionTable(JDBCAdapter  adapter){
		super(TABLENAME,adapter);
	}

	@Override
	protected void initColumns() {
	
		columns = new ColumnDescriptor[12];
		columns[0]= new ColumnDescriptor("MatchID",Types.INTEGER,false);
		columns[1]= new ColumnDescriptor("TeamID",Types.INTEGER,false);
		columns[2]= new ColumnDescriptor("HrfID",Types.INTEGER, false);
		columns[3]= new ColumnDescriptor("PlayerOrderID",Types.INTEGER,false);
		columns[4]= new ColumnDescriptor("PlayerIn",Types.INTEGER,false);
		columns[5]= new ColumnDescriptor("PlayerOut",Types.INTEGER,false);
		columns[6]= new ColumnDescriptor("OrderType",Types.INTEGER,false);
		columns[7]= new ColumnDescriptor("MatchMinuteCriteria",Types.INTEGER,false);
		columns[8]= new ColumnDescriptor("Pos",Types.INTEGER,false);
		columns[9]= new ColumnDescriptor("Behaviour",Types.INTEGER,false);
		columns[10]= new ColumnDescriptor("Card",Types.INTEGER,false);
		columns[11]= new ColumnDescriptor("Standing",Types.INTEGER,false);
	}

	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] {  
				"CREATE INDEX IMATCHSUBSTITUTION_1 ON "+getTableName()+"("+columns[3].getColumnName()+")",
				"CREATE INDEX IMATCHSUBSTITUTION_2 ON "+getTableName()+"("+columns[0].getColumnName()+","+columns[1].getColumnName()+")",
				"CREATE INDEX IMATCHSUBSTITUTION_3 ON "+getTableName()+"("+columns[2].getColumnName()+")"
		};
	}

	/**
	 * Returns an array with substitution belonging to the match-team.
	 *
	 * @param teamId The teamId for the team in question
	 * @param matchId The matchId for the match in question
	 *
	 */
	public java.util.List<ISubstitution> getMatchSubstitutionsByMatchTeam(int teamId, int matchId) {
		return getSubBySql("SELECT * FROM "+getTableName()+" WHERE MatchID = " + matchId + " AND TeamID = " + teamId);

	}


	/**
	 * Returns an array with substitution belonging to given hrfId
	 *
	 * @param hrfId The teamId for the team in question
	 *
	 */
	public java.util.List<ISubstitution> getMatchSubstitutionsByHrf(int hrfId) {
		return getSubBySql("SELECT * FROM "+getTableName()+" WHERE HrfID = " + hrfId);
	}


	/**
	 * Stores the substitutions in the database. The ID for each substitution must be unique for the match.
	 * All previous substitutions for the team/match combination will be deleted.
	 */
	public void storeMatchSubstitutionsByMatchTeam(int matchId, int teamId, java.util.List<ISubstitution> subs) {
		if ((matchId == DUMMY) || (teamId == DUMMY)) {
			// Rather not...
			return;
		}
		storeSub(matchId, teamId, DUMMY, subs);
	}

	/**
	 * Stores the substitutions in the database. The ID for each substitution must be unique for the match.
	 * All previous substitutions for the hrf will be deleted.
	 */
	public void storeMatchSubstitutionsByHrf(int hrfId, java.util.List<ISubstitution> subs) {
		if (hrfId == DUMMY)  {
			// Rather not...
			return;
		}
		storeSub(DUMMY, DUMMY, hrfId, subs);
	}


	private void storeSub(int matchId, int teamId, int hrfId, java.util.List<ISubstitution> subs) {
		String sql = null;
		final String[] where = { "MatchID", "TeamID", "HrfID" };
		final String[] values = { "" + matchId, "" + teamId, "" + hrfId } ;

		// Get rid of any old subs for the inputs.
		delete(where, values);

		for (ISubstitution sub: subs) {

			if (sub == null) {
				continue;
			}

			try {
				sql = "INSERT INTO "+getTableName()+" (  MatchID, TeamID, HrfID, PlayerOrderID, PlayerIn, PlayerOut, OrderType,";
				sql += " MatchMinuteCriteria, Pos, Behaviour, Card, Standing ) VALUES(";
				sql += matchId + "," + teamId + "," + hrfId + "," +
					sub.getPlayerOrderId() + "," +
					sub.getPlayerIn() + "," +
					sub.getPlayerOut() + "," +
					sub.getOrderType() + "," +
					sub.getMatchMinuteCriteria() + "," +
					sub.getPos() + "," +
					sub.getBehaviour() + "," +
					sub.getCard() + "," +
					sub.getStanding() + " )";

				adapter.executeUpdate(sql);
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),"DB.storeMatchSubstitution Error" + e);
				HOLogger.instance().log(getClass(),e);
			}
		}
	}


	private java.util.List<ISubstitution> getSubBySql(String sql) {
		Substitution sub = null;
		ResultSet rs = null;
		final java.util.ArrayList<ISubstitution> subst = new java.util.ArrayList<ISubstitution>();

		try {
			rs = adapter.executeQuery(sql);
			rs.beforeFirst();
			while (rs.next()) {
				sub = new Substitution(
						rs.getInt("PlayerOrderID"),
						rs.getInt("PlayerIn"),
						rs.getInt("PlayerOut"),
						(byte)rs.getInt("OrderType"),
						(byte)rs.getInt("MatchMinuteCriteria"),
						(byte)rs.getInt("Pos"),
						(byte)rs.getInt("Behaviour"),
						(byte)rs.getInt("Card"),
						(byte)rs.getInt("Standing"));
				subst.add(sub);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"DB.getMatchSubstitutions Error" + e);
		}
	
		return subst;
	}

}



