// %1126721329979:hoplugins.transfers.dao%
package hoplugins.transfers.dao;

import hoplugins.Commons;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DB Access Manager for Transfer type manual override
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public final class TransferTypeDAO {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Name of the table in the HO database */
    private static final String TABLE_NAME = "TRANSFERS_TYPE"; //$NON-NLS-1$

    static {
        checkTable();
    }

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Private default constuctor to prevent class instantiation. 
     */
    private TransferTypeDAO() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Set the transfer type
     *
     * @param playerId the transfer id
     * @param type TODO Missing Constructuor Parameter Documentation
     */
    public static void setType(int playerId, int type) {
        final String query = "update " + TABLE_NAME + " set TYPE = " + type + " where PLAYER_ID ="
                       + playerId; 
        final int count = Commons.getModel().getAdapter().executeUpdate(query);

        if (count == 0) {
            Commons.getModel().getAdapter().executeUpdate("insert into " + TABLE_NAME
                                                          + " (PLAYER_ID, TYPE) values ("
                                                          + playerId + "," + type + ")"); //$NON-NLS-1$ //$NON-NLS-2$ 
        }
    }

    /**
     * Returns the transfer type
     *
     * @param playerId the playerId to know the transfer type
     *
     * @return true if transferId has been already uploaded
     */
    public static int getType(int playerId) {
        final String query = "select TYPE from " + TABLE_NAME + " where PLAYER_ID=" + playerId; //$NON-NLS-1$ //$NON-NLS-2$
        final ResultSet rs = Commons.getModel().getAdapter().executeQuery(query);

        try {
            rs.next();
            return rs.getInt("TYPE"); //$NON-NLS-1$
        } catch (SQLException e) {
            return -2;
        }
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
                                                          + " (PLAYER_ID INTEGER,TYPE INTEGER)"); //$NON-NLS-1$ 
            Commons.getModel().getAdapter().executeUpdate("CREATE INDEX pltype_id ON " + TABLE_NAME
                                                          + " (PLAYER_ID)"); //$NON-NLS-1$ 
        }
    }
}
