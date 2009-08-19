// %2331467101:hoplugins.teamAnalyzer.dao%
package hoplugins.teamAnalyzer.dao;

import hoplugins.Commons;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DB Access Manager for Divider locations
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
     * Set the value in the databse
     *
     * @param key The key
     * @param position the value
     */
    public static void setDividerPosition(String key, int position) {
        String query = "update TEAMANALYZER_DIVIDER set POSITIONE = " + position
                       + " where NAME = '" + key + "'";
        int count = Commons.getModel().getAdapter().executeUpdate(query);

        if (count == 0) {
            Commons.getModel().getAdapter().executeUpdate("insert into TEAMANALYZER_DIVIDER (NAME, POSITIONE) values ('"
                                                          + key + "', " + position + ")");
        }
    }

    /**
     * Returns a value
     *
     * @param key the key to be returned
     *
     * @return the value
     */
    public static int getDividerPosition(String key) {
        String query = "select POSITIONE from TEAMANALYZER_DIVIDER where NAME='" + key + "'";
        ResultSet rs = Commons.getModel().getAdapter().executeQuery(query);

        try {
            rs.next();

            return rs.getInt("POSITIONE");
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * Check if the table exists, if not create it  with default values
     */
    private static void checkTable() {
        try {
            ResultSet rs = Commons.getModel().getAdapter().executeQuery("select * from TEAMANALYZER_DIVIDER");
            rs.next();
        } catch (Exception e) {
            Commons.getModel().getAdapter().executeUpdate("CREATE TABLE TEAMANALYZER_DIVIDER(NAME varchar(20),POSITIONE integer)");
            setDividerPosition("LowerLeftDivider", 115);
            setDividerPosition("UpperLeftDivider", 380);
            setDividerPosition("MainDivider", 230);
            setDividerPosition("BottomDivider", 550);
        }

        try {
            ResultSet rs = Commons.getModel().getAdapter().executeQuery("select NAME from TEAMANALYZER_DIVIDER");
            rs.next();
        } catch (Exception e) {
            Commons.getModel().getAdapter().executeUpdate("ALTER TABLE TEAMANALYZER_DIVIDER ADD COLUMN NAME varchar(20)");
            Commons.getModel().getAdapter().executeUpdate("UPDATE TEAMANALYZER_DIVIDER SET NAME=KEY");
            Commons.getModel().getAdapter().executeUpdate("ALTER TABLE TEAMANALYZER_DIVIDER DROP COLUMN KEY");
        }
    }
}
