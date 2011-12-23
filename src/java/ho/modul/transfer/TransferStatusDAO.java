// %3478711534:hoplugins.transfers.dao%
package ho.modul.transfer;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.hattrickorganizer.database.DBZugriff;


/**
 * DB Access Manager for Transfer uploading status
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public final class TransferStatusDAO {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Name of the table in the HO database */
    private static final String TABLE_NAME = "TRANSFERS_UPLOAD"; //$NON-NLS-1$

    static {
        checkTable();
    }

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Private default constuctor to prevent class instantiation.
     */
    private TransferStatusDAO() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Set the uploaded status in the databse
     *
     * @param transferId the uploaded transfer id
     */
    public static void setUploaded(int transferId) {
        final String query = "update " + TABLE_NAME + " set STATUS = TRUE where TRANSFER_ID ="
                             + transferId; 
        final int count =DBZugriff.instance().getAdapter().executeUpdate(query);

        if (count == 0) {
        	DBZugriff.instance().getAdapter().executeUpdate("insert into " + TABLE_NAME
                                                          + " (TRANSFER_ID, STATUS) values ("
                                                          + transferId + ",TRUE)"); //$NON-NLS-1$ 
        }
    }

    /**
     * Returns if the tranfer has been already uploaded to server or not
     *
     * @param transferId the uploaded transfer id
     *
     * @return true if transferId has been already uploaded
     */
    public static boolean isUploaded(int transferId) {
        final String query = "select STATUS from " + TABLE_NAME + " where TRANSFER_ID="
                             + transferId; 
        final ResultSet rs =DBZugriff.instance().getAdapter().executeQuery(query);

        try {
            rs.next();
            return rs.getBoolean("STATUS"); //$NON-NLS-1$
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Returns if the tranfer has been already uploaded to server or not
     */
    public static void resetTransfers() {
        final String query = "DELETE from " + TABLE_NAME; //$NON-NLS-1$ 
        DBZugriff.instance().getAdapter().executeUpdate(query);
    }

    /**
     * Check if the table exists, if not create it  with default values
     */
    private static void checkTable() {
        try {
            final ResultSet rs = DBZugriff.instance().getAdapter().executeQuery("select * from "
                                                                              + TABLE_NAME);
            rs.next();
        } catch (Exception e) {
        	DBZugriff.instance().getAdapter().executeUpdate("CREATE TABLE " + TABLE_NAME
                                                          + " (TRANSFER_ID INTEGER,STATUS BOOLEAN)");
        	DBZugriff.instance().getAdapter().executeUpdate("CREATE INDEX plstatus_id ON "
                                                          + TABLE_NAME + " (TRANSFER_ID)"); //$NON-NLS-1$ 
        }

        try {
            final ResultSet rs =DBZugriff.instance().getAdapter().executeQuery("select STATUS from "
                                                                              + TABLE_NAME);
            rs.next();
        } catch (Exception e) {
        	DBZugriff.instance().getAdapter().executeUpdate("ALTER TABLE " + TABLE_NAME
                                                          + " ADD COLUMN STATUS BOOLEAN");
        	DBZugriff.instance().getAdapter().executeUpdate("UPDATE " + TABLE_NAME
                                                          + " SET STATUS=UPLOADED");
        	DBZugriff.instance().getAdapter().executeUpdate("ALTER TABLE " + TABLE_NAME
                                                          + " DROP COLUMN UPLOADED");
        }
    }
}
