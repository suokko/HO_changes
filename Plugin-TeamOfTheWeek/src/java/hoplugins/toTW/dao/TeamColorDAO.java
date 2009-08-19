// %2071073331:hoplugins.toTW.dao%
package hoplugins.toTW.dao;

import hoplugins.Commons;

import hoplugins.toTW.vo.TeamDetail;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DB Access Manager for Transfer uploading status
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public final class TeamColorDAO {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Name of the table in the HO database */
    private static final String TABLE_NAME = "TOTW_TEAMCOLOR"; //$NON-NLS-1$

    static {
        checkTable();
    }

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Private default constuctor to prevent class instantiation.
     */
    private TeamColorDAO() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param teamId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static TeamDetail load(int teamId) {
        TeamDetail td = new TeamDetail();
        String query = "select * from " + TABLE_NAME + " where TEAMID=" + teamId;
        ResultSet rs = Commons.getModel().getAdapter().executeQuery(query);

        try {
            rs.next();
            td.setTeamId(teamId);
            td.setPants(rs.getString("PANTS"));
            td.setShirt(rs.getString("SHIRT"));
            td.setSocks(rs.getString("SOCKS"));
            td.setName(rs.getString("NAME"));
        } catch (SQLException e) {
        }

        return td;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param td TODO Missing Method Parameter Documentation
     */
    public static void store(TeamDetail td) {
        String query = "DELETE from " + TABLE_NAME + " WHERE TEAMID=" + td.getTeamId();
        Commons.getModel().getAdapter().executeUpdate(query);
        query = " INSERT INTO " + TABLE_NAME + " VALUES (" + td.getTeamId() + ",'" + td.getName()
                + "','" + td.getShirt() + "','" + td.getPants() + "','" + td.getSocks() + "') ";
        Commons.getModel().getAdapter().executeUpdate(query);
    }

    /**
     * Check if the table exists, if not create it  with default values
     */
    private static void checkTable() {
        try {
            final ResultSet rs = Commons.getModel().getAdapter().executeQuery("select * from "
                                                                              + TABLE_NAME);
            rs.next();
        } catch (Exception e) {
            Commons.getModel().getAdapter().executeUpdate("CREATE TABLE " + TABLE_NAME
                                                          + " (TEAMID INTEGER,NAME VARCHAR(32), SHIRT VARCHAR(4),PANTS VARCHAR(4),SOCKS VARCHAR(4))");
            Commons.getModel().getAdapter().executeUpdate("CREATE INDEX teamcolor_id ON "
                                                          + TABLE_NAME + " (TEAMID)"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }
}
