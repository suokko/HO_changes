// %3247860593:hoplugins.transfers.dao%
package hoplugins.transfers.dao;

import hoplugins.Commons;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DAO to load from HO Database number of matches played by a player
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public final class PlayerMatchesDAO {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Private default constuctor to prevent class instantiation.
     */
    private PlayerMatchesDAO() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Method to load the number of matches player
     *
     * @param playerId Id of the Player to consider
     * @param official true for official matches, false for friendly
     *
     * @return number of matches
     */
    public static int getAppearance(int playerId, boolean official) {
        String sqlStmt = "select count(MATCHESKURZINFO.matchid) as MatchNumber FROM MATCHLINEUPPLAYER INNER JOIN MATCHESKURZINFO ON MATCHESKURZINFO.matchid = MATCHLINEUPPLAYER.matchid ";
        sqlStmt = sqlStmt + "where spielerId = " + playerId + " and FIELDPOS>-1 ";

        if (official) {
            sqlStmt = sqlStmt + "and matchtyp <8";
        } else {
            sqlStmt = sqlStmt + "and matchtyp >7";
        }

        final ResultSet rs = Commons.getModel().getAdapter().executeQuery(sqlStmt.toString());

        if (rs == null) {
            return 0;
        }

        int count = 0;

        try {
            while (rs.next()) {
                count = rs.getInt("MatchNumber");
            }
        } catch (SQLException e) {
        }

        return count;
    }
}
