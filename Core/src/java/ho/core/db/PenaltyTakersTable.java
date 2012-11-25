package ho.core.db;

import ho.core.model.player.SpielerPosition;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class PenaltyTakersTable extends AbstractTable {

	public final static String TABLENAME = "PENALTYTAKERS";

	protected PenaltyTakersTable(JDBCAdapter adapter) {
		super(TABLENAME, adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[6];
		columns[0] = new ColumnDescriptor("MatchID", Types.INTEGER, true);
		columns[1] = new ColumnDescriptor("TeamID", Types.INTEGER, false);
		columns[2] = new ColumnDescriptor("HrfID", Types.INTEGER, true);
		columns[3] = new ColumnDescriptor("PlayerID", Types.INTEGER, false);
		columns[4] = new ColumnDescriptor("Pos", Types.INTEGER, false);
		columns[5] = new ColumnDescriptor("LineupName", Types.VARCHAR, false, 256);
	}

	@Override
	protected String[] getCreateIndizeStatements() {
		return new String[] {
				"CREATE INDEX PENALTYTAKERS_2 ON " + getTableName() + "("
						+ columns[0].getColumnName() + "," + columns[1].getColumnName() + ")",
				"CREATE INDEX PENALTYTAKERS_3 ON " + getTableName() + "("
						+ columns[5].getColumnName() + ")" };
	}

	void storePenaltyTakers(Integer teamId, List<SpielerPosition> penaltyTakers, String lineupName)
			throws SQLException {
		storePenaltyTakers(null, teamId, null, penaltyTakers, lineupName);
	}

	private void storePenaltyTakers(Integer matchId, Integer teamId, Integer hrfId,
			List<SpielerPosition> penaltyTakers, String lineupName) throws SQLException {
		String sql = null;

		String[] where = { "MatchID", "TeamID", "HrfID", "LineupName" };
		String[] values = { asString(matchId), asString(teamId), asString(hrfId),
				"'" + lineupName + "'" };

		delete(where, values);

		for (int i = 0; i < penaltyTakers.size(); i++) {
			SpielerPosition penaltyTaker = penaltyTakers.get(i);

			sql = "INSERT INTO " + getTableName()
					+ " (  MatchID, TeamID, HrfID, PlayerID, Pos, LineupName ) VALUES (";
			sql += matchId + "," + teamId + "," + hrfId + "," + penaltyTaker.getSpielerId() + ","
					+ i + "," + "'" + lineupName + "')";

			adapter.executeUpdate_(sql);
		}
	}

	private String asString(Object o) {
		if (o != null) {
			return String.valueOf(o);
		}
		return null;
	}
}
