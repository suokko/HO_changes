package ho.core.db;

import ho.module.teamAnalyzer.vo.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import de.hattrickorganizer.tools.HOLogger;
/**
 * The Table UserConfiguration contain all User properties.
 * CONFIG_KEY = Primary Key, fieldname of the class
 * CONFIG_VALUE = value of the field, save as VARCHAR. Convert to right datatype if loaded
 * 
 * @since 1.36
 *
 */
final class TAFavoriteTable extends AbstractTable {
	final static String TABLENAME = "TA_FAVORITE";

	protected TAFavoriteTable(JDBCAdapter adapter) {
		super(TABLENAME, adapter);
	}

	@Override
	protected void initColumns() {
		columns = new ColumnDescriptor[2];
		columns[0] = new ColumnDescriptor("TEAMID", Types.INTEGER, false, true);
		columns[1] = new ColumnDescriptor("NAME", Types.VARCHAR, true, 20);
	}


    void removeTeam(int teamId) {
        adapter.executeUpdate("delete from TA_FAVORITE where TEAMID=" + teamId);
    }
    
    void addTeam(Team team) {
        adapter.executeUpdate("insert into TA_FAVORITE (TEAMID, NAME) values ("
                                                      + team.getTeamId() + ", '" + team.getName()
                                                      + "')");
    }

    boolean isTAFavourite(int teamId) {
        ResultSet rs = adapter.executeQuery("select * from TA_FAVORITE where TEAMID=" + teamId);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            HOLogger.instance().debug(this.getClass(), e);
        }
        return false;
    }

    /**
     * Returns all favourite teams
     *
     * @return List of Teams Object
     */
    List<Team> getTAFavoriteTeams() {
        List<Team> list = new ArrayList<Team>();
        ResultSet rs = adapter.executeQuery("select * from TA_FAVORITE");

        try {
            while (rs.next()) {
                Team team = new Team();

                team.setTeamId(rs.getInt(1));
                team.setName(rs.getString(2));
                list.add(team);
            }
        } catch (SQLException e) {
            return new ArrayList<Team>();
        }

        return list;
    }
}
