// %2702752272:hoplugins.transfers.dao%
package ho.modul.transfer;


import java.sql.ResultSet;
import java.sql.SQLException;

import de.hattrickorganizer.database.DBZugriff;


/**
 * DAO that store the Divider position into the HO Database
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public final class DividerDAO {
    //~ Static fields/initializers -----------------------------------------------------------------

    static {
        checkTable();
    }

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DividerDAO object.
     */
    private DividerDAO() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Store the new divider position value into the DB
     *
     * @param key Divider indentificaton
     * @param position New divider position value
     */
    public static void setDividerPosition(String key, int position) {
        final String query = "update TRANSFERS_DIVIDER set POSITIONE = " + position //$NON-NLS-1$
                             + " where NAME = '" + key + "'"; //$NON-NLS-1$ //$NON-NLS-2$
        final int count = DBZugriff.instance().getAdapter().executeUpdate(query);

        if (count == 0) {
        	DBZugriff.instance().getAdapter().executeUpdate("insert into TRANSFERS_DIVIDER (NAME, POSITIONE) values ('" //$NON-NLS-1$
                                                          + key + "', " + position + ")"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Return the divider position value from the DB
     *
     * @param key Divider indentificaton
     *
     * @return the divider position value
     */
    public static int getDividerPosition(String key) {
        final String query = "select POSITIONE from TRANSFERS_DIVIDER where NAME='" + key + "'"; //$NON-NLS-1$ //$NON-NLS-2$
        final ResultSet rs = DBZugriff.instance().getAdapter().executeQuery(query);

        try {
            rs.next();
            return rs.getInt("POSITIONE"); //$NON-NLS-1$
        } catch (SQLException e) {
            return 0;
        } finally {
            try {
                rs.close();
            } catch (SQLException e1) {
            }
        }
    }

    /**
     * Method that check if the table exists, if not creates it and sets the values to default
     */
    private static void checkTable() {
        try {
            final ResultSet rs = DBZugriff.instance().getAdapter().executeQuery("select * from TRANSFERS_DIVIDER"); //$NON-NLS-1$
            rs.next();
        } catch (Exception e) {
        	DBZugriff.instance().getAdapter().executeUpdate("CREATE TABLE TRANSFERS_DIVIDER(NAME varchar(20),POSITIONE integer)"); //$NON-NLS-1$
            setDividerPosition("HistoryTabDivider", 400); //$NON-NLS-1$
            setDividerPosition("TypeTabDivider", 400); //$NON-NLS-1$
        }

        try {
            final ResultSet rs = DBZugriff.instance().getAdapter().executeQuery("select NAME from TRANSFERS_DIVIDER"); //$NON-NLS-1$
            rs.next();
        } catch (Exception e) {
        	DBZugriff.instance().getAdapter().executeUpdate("ALTER TABLE TRANSFERS_DIVIDER ADD COLUMN NAME varchar(20)");
        	DBZugriff.instance().getAdapter().executeUpdate("UPDATE TRANSFERS_DIVIDER SET NAME=KEY");
        	DBZugriff.instance().getAdapter().executeUpdate("ALTER TABLE TRANSFERS_DIVIDER DROP COLUMN KEY");
        }
    }
}
