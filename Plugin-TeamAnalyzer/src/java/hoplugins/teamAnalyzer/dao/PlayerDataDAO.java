// %3311494495:hoplugins.teamAnalyzer.dao%
package hoplugins.teamAnalyzer.dao;

import gui.UserParameter;

import hoplugins.Commons;

import hoplugins.teamAnalyzer.vo.PlayerInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Calendar;


/**
 * DB Access Manager for Player Data
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class PlayerDataDAO {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TeamPlayerDAO object.
     */
    public PlayerDataDAO() {
        checkTable();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the specialEvent code for a player
     *
     * @param playerId the playerId
     * @param week TODO Missing Constructuor Parameter Documentation
     * @param season TODO Missing Constructuor Parameter Documentation
     *
     * @return a numeric code
     */
    public PlayerInfo getPlayerInfo(int playerId, int week, int season) {
        int weekNumber = week + (season * 16);

        String query = "select * from TEAMANALYZER_PLAYERDATA where PLAYERID=" + playerId
                       + " and week=" + weekNumber;

        ResultSet rs = Commons.getModel().getAdapter().executeQuery(query);

        try {
            rs.next();

            PlayerInfo info = new PlayerInfo();
            info.setPlayerId(playerId);
            info.setAge(rs.getInt("AGE"));
            info.setForm(rs.getInt("FORM"));
            info.setTSI(rs.getInt("TSI"));
            info.setSpecialEvent(rs.getInt("SPECIALEVENT"));
            info.setTeamId(rs.getInt("TEAMID"));
            info.setExperience(rs.getInt("EXPERIENCE"));
            info.setStatus(rs.getInt("STATUS"));
            return info;
        } catch (SQLException e) {
            return new PlayerInfo();
        }
    }

    /**
     * Returns the specialEvent code for a player
     *
     * @param playerId the playerId
     *
     * @return a numeric code
     */
    public PlayerInfo getPlayerInfo(int playerId) {
        Integer HTWeek = getCurrentHTWeek();
        Integer HTSeason = getCurrentHTWeek();
        return getPlayerInfo(playerId, HTWeek, HTSeason);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public PlayerInfo getPreviousPlayeInfo(int id) {
        ResultSet rs = Commons.getModel().getAdapter().executeQuery("SELECT WEEK FROM TEAMANALYZER_PLAYERDATA WHERE WEEK<"
                                                                    + getCurrentWeekNumber()
                                                                    + " AND PLAYERID=" + id
                                                                    + " ORDER BY WEEK DESC");

        try {
            if (rs.next()) {
                int week = rs.getInt("WEEK");
                int season = week / 16;
                int wk = week % 16;
                return getPlayerInfo(id, wk, season);
            }
        } catch (SQLException e) {
        }

        return new PlayerInfo();
    }

    /**
     * Add a player to a team
     *
     * @param info
     */
    public void addPlayer(PlayerInfo info) {
        Commons.getModel().getAdapter().executeUpdate("insert into TEAMANALYZER_PLAYERDATA values ("
                                                      + info.getTeamId() + ", "
                                                      + info.getPlayerId() + ", "
                                                      + info.getStatus() + " , "
                                                      + info.getSpecialEvent() + ", "
                                                      + info.getTSI() + ", " + info.getForm()
                                                      + ", " + info.getAge() + ", "
                                                      + info.getExperience() + ", "
                                                      + getCurrentWeekNumber() + ")");
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param info TODO Missing Method Parameter Documentation
     */
    public void updatePlayer(PlayerInfo info) {
        Commons.getModel().getAdapter().executeUpdate("update TEAMANALYZER_PLAYERDATA set "
                                                      + "   SPECIALEVENT=" + info.getSpecialEvent()
                                                      + " , TSI=" + info.getTSI() + " , FORM="
                                                      + +info.getForm() + " , AGE=" + info.getAge()
                                                      + " , EXPERIENCE=" + info.getExperience()
                                                      + " , STATUS=" + info.getStatus()
                                                      + " where PLAYERID=" + info.getPlayerId()
                                                      + " and WEEK=" + getCurrentWeekNumber());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Integer getCurrentHTSeason() {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR, UserParameter.instance().TimeZoneDifference);
        return Commons.getModel().getHelper().getHTSeason(date.getTime());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Integer getCurrentHTWeek() {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR, UserParameter.instance().TimeZoneDifference);
        return Commons.getModel().getHelper().getHTWeek(date.getTime());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private int getCurrentWeekNumber() {
        return getCurrentHTWeek() + (getCurrentHTSeason() * 16);
    }

    /**
     * Check if the table exists, if not create it  with default values
     */
    private void checkTable() {
        try {
            ResultSet rs = Commons.getModel().getAdapter().executeQuery("select * from TEAMANALYZER_PLAYERDATA");
            rs.next();
        } catch (Exception e) {
            // Drop old tables
            try {
                Commons.getModel().getAdapter().executeUpdate("DROP TABLE TEAMANALYZER_SPECIALEVENT");
            } catch (Exception e1) {
            }

            try {
                Commons.getModel().getAdapter().executeUpdate("DROP TABLE TEAMANALYZER_PLAYERS");
            } catch (Exception e1) {
            }

            try {
                Commons.getModel().getAdapter().executeUpdate("DROP TABLE TEAMANALYZER_TEAMPLAYERS");
            } catch (Exception e1) {
            }

            Commons.getModel().getAdapter().executeUpdate("CREATE TABLE TEAMANALYZER_PLAYERDATA(TEAMID integer,PLAYERID integer,STATUS integer,SPECIALEVENT integer, TSI integer, FORM integer, AGE integer, EXPERIENCE integer)");
        }

        // ADD WEEK Column if needed
        try {
            ResultSet rs = Commons.getModel().getAdapter().executeQuery("select WEEK from TEAMANALYZER_PLAYERDATA");
            rs.next();
        } catch (Exception e) {
            Commons.getModel().getAdapter().executeUpdate("ALTER TABLE TEAMANALYZER_PLAYERDATA ADD COLUMN WEEK INTEGER");

            Commons.getModel().getAdapter().executeUpdate("UPDATE TEAMANALYZER_PLAYERDATA SET WEEK = "
                                                          + getCurrentWeekNumber());
        }

        // Delete old files
        try {
            Commons.getModel().getAdapter().executeUpdate("DELETE FROM TEAMANALYZER_PLAYERDATA WHERE WEEK<"
                                                          + (getCurrentWeekNumber() - 10));
        } catch (Exception e) {
        }
    }
}
