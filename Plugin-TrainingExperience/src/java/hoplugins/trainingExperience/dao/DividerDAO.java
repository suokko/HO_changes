// %3029746015:hoplugins.trainingExperience.dao%
package hoplugins.trainingExperience.dao;

import hoplugins.Commons;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DAO that store the Divider position into the HO Database
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class DividerDAO {
    //~ Static fields/initializers -----------------------------------------------------------------

    static {
        checkTable();
    }

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DividerDAO object.
     */
    public DividerDAO() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Store the new divider position value into the DB
     *
     * @param key Divider indentificaton
     * @param position New divider position value
     */
    public static void setDividerPosition(String key, int position) {
        String query = "update TRAININGEXPERIENCE_DIVIDER set POSITIONE = "
                       + position 
                       + " where NAME = '" + key + "'"; //$NON-NLS-1$ //$NON-NLS-2$
        int count = Commons.getModel().getAdapter().executeUpdate(query);

        if (count == 0) {
            Commons.getModel().getAdapter().executeUpdate("insert into TRAININGEXPERIENCE_DIVIDER (NAME, POSITIONE) values ('" //$NON-NLS-1$
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
        String query = "select POSITIONE from TRAININGEXPERIENCE_DIVIDER where NAME='" + key + "'"; //$NON-NLS-1$ //$NON-NLS-2$
        ResultSet rs = Commons.getModel().getAdapter().executeQuery(query);

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
            ResultSet rs = Commons.getModel().getAdapter().executeQuery("select * from TRAININGEXPERIENCE_DIVIDER"); //$NON-NLS-1$
            rs.next();
        } catch (Exception e) {
            Commons.getModel().getAdapter().executeUpdate("CREATE TABLE TRAININGEXPERIENCE_DIVIDER(NAME varchar(20),POSITIONE integer)"); //$NON-NLS-1$
            setDividerPosition("LowerLeftDivider", 115); //$NON-NLS-1$
            setDividerPosition("RightDivider", 778); //$NON-NLS-1$
            setDividerPosition("MainDivider", 429); //$NON-NLS-1$
            setDividerPosition("BottomDivider", 205); //$NON-NLS-1$
            setDividerPosition("TrainingDivider", 334); //$NON-NLS-1$
        }

        try {
            ResultSet rs = Commons.getModel().getAdapter().executeQuery("select NAME from TRAININGEXPERIENCE_DIVIDER"); //$NON-NLS-1$
            rs.next();
        } catch (Exception e) {
            Commons.getModel().getAdapter().executeUpdate("ALTER TABLE TRAININGEXPERIENCE_DIVIDER ADD COLUMN NAME varchar(20)");
            Commons.getModel().getAdapter().executeUpdate("UPDATE TRAININGEXPERIENCE_DIVIDER SET NAME=KEY");
            Commons.getModel().getAdapter().executeUpdate("ALTER TABLE TRAININGEXPERIENCE_DIVIDER DROP COLUMN KEY");
        }
    }
}
