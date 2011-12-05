// %3183714917:hoplugins.teamAnalyzer.dao%
package hoplugins.teamAnalyzer.dao;

import hoplugins.Commons;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DB Access Manager for Configuration
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class PluginConfiguration {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static boolean isNumericRating;
    private static boolean isLineup;
    private static boolean tacticDetail;
    private static boolean isDescriptionRating;
    private static boolean isShowUnavailable;
    private static boolean isMixedLineup;
    private static boolean isStars;
    private static boolean isTotalStrength;
    private static boolean isSquad;
    private static boolean isSmartSquad;
    private static boolean isLoddarStats;
    private static boolean isShowPlayerInfo;
    private static boolean isCheckTeamName;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new PluginConfiguration object.
     */
    public PluginConfiguration() {
        checkTable();

        isNumericRating = getValue("numericRating", false);
        isDescriptionRating = getValue("descriptionRating", true);
        isLineup = getValue("lineupCompare", true);
        isMixedLineup = getValue("mixedLineup", false);
        tacticDetail = getValue("tacticDetail", false);
        isStars = getValue("isStars", true);
        isTotalStrength = getValue("isTotalStrength", true);
        isSquad = getValue("isSquad", true);
        isSmartSquad = getValue("isSmartSquad", true);
        isLoddarStats = getValue("isLoddarStats", true);
        isShowPlayerInfo = getValue("isShowPlayerInfo", false);
        isCheckTeamName = getValue("isCheckTeamName", true);

        //isShowUnavailable = getValue("showUnavailable", true);
        isShowUnavailable = true;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param b TODO Missing Method Parameter Documentation
     */
    public void setCheckTeamName(boolean b) {
        isCheckTeamName = b;
        setValue("isCheckTeamName", b);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isCheckTeamName() {
        return isCheckTeamName;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setDescriptionRating(boolean b) {
        isDescriptionRating = b;
        setValue("descriptionRating", b);
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isDescriptionRating() {
        return isDescriptionRating;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setLineup(boolean b) {
        isLineup = b;
        setValue("lineupCompare", b);
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isLineup() {
        return isLineup;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param b TODO Missing Method Parameter Documentation
     */
    public void setLoddarStats(boolean b) {
        isLoddarStats = b;
        setValue("isLoddarStats", b);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isLoddarStats() {
        return isLoddarStats;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setMixedLineup(boolean b) {
        isMixedLineup = b;
        setValue("mixedLineup", b);
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isMixedLineup() {
        return isMixedLineup;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setNumericRating(boolean b) {
        isNumericRating = b;
        setValue("numericRating", b);
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isNumericRating() {
        return isNumericRating;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param b TODO Missing Method Parameter Documentation
     */
    public void setShowPlayerInfo(boolean b) {
        isShowPlayerInfo = b;
        setValue("isShowPlayerInfo", b);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isShowPlayerInfo() {
        return isShowPlayerInfo;
    }

    /**
     * Document Me!
     *
     * @param b TODO Missing Constructuor Parameter Documentation
     */
    public void setShowUnavailable(boolean b) {
        isShowUnavailable = b;
        setValue("showUnavailable", b);
    }

    /**
     * Document Me!
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isShowUnavailable() {
        return isShowUnavailable;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setSmartSquad(boolean b) {
        isSmartSquad = b;
        setValue("isSmartSquad", b);
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isSmartSquad() {
        return isSmartSquad;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setSquad(boolean b) {
        isSquad = b;
        setValue("isSquad", b);
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isSquad() {
        return isSquad;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setStars(boolean b) {
        isStars = b;
        setValue("isStars", b);
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isStars() {
        return isStars;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setTacticDetail(boolean b) {
        tacticDetail = b;
        setValue("tacticDetail", b);
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isTacticDetail() {
        return tacticDetail;
    }

    /**
     * Document Me!
     *
     * @param b
     */
    public void setTotalStrength(boolean b) {
        isTotalStrength = b;
        setValue("isTotalStrength", b);
    }

    /**
     * Document Me!
     *
     * @return
     */
    public boolean isTotalStrength() {
        return isTotalStrength;
    }

    /**
     * Set the value in the databse
     *
     * @param key The key
     * @param value the value
     */
    private void setValue(String key, boolean value) {
        String val = (value) ? "1" : "0";

        String query = "update TEAMANALYZER_SETTINGS set VALUE = " + val + " where NAME = '" + key
                       + "'";
        int count = Commons.getModel().getAdapter().executeUpdate(query);

        if (count == 0) {
            Commons.getModel().getAdapter().executeUpdate("insert into TEAMANALYZER_SETTINGS (NAME, VALUE) values ('"
                                                          + key + "', " + val + ")");
        }
    }

    /**
     * Returns a value
     *
     * @param key the key to be returned
     * @param defaultValue to be used if not existing
     *
     * @return the value
     */
    private boolean getValue(String key, boolean defaultValue) {
        String query = "select VALUE from TEAMANALYZER_SETTINGS where NAME='" + key + "'";
        ResultSet rs = Commons.getModel().getAdapter().executeQuery(query);

        try {
            rs.next();

            return rs.getBoolean("VALUE");
        } catch (SQLException e) {
            return defaultValue;
        }
    }

    /**
     * Check if the table exists, if not create it  with default values
     */
    private void checkTable() {
        try {
            ResultSet rs = Commons.getModel().getAdapter().executeQuery("select * from TEAMANALYZER_SETTINGS");
            rs.next();
        } catch (Exception e) {
            Commons.getModel().getAdapter().executeUpdate("CREATE TABLE TEAMANALYZER_SETTINGS(NAME varchar(32),VALUE BOOLEAN)");
        }

        try {
            ResultSet rs = Commons.getModel().getAdapter().executeQuery("select NAME from TEAMANALYZER_SETTINGS");
            rs.next();
        } catch (Exception e) {
            Commons.getModel().getAdapter().executeUpdate("ALTER TABLE TEAMANALYZER_SETTINGS ADD COLUMN NAME varchar(20)");
            Commons.getModel().getAdapter().executeUpdate("UPDATE TEAMANALYZER_SETTINGS SET NAME=KEY");
            Commons.getModel().getAdapter().executeUpdate("ALTER TABLE TEAMANALYZER_SETTINGS DROP COLUMN KEY");
        }
    }
}
