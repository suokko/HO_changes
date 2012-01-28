// %229426064:hoplugins.teamAnalyzer.manager%
package ho.module.teamAnalyzer.manager;

import ho.core.db.DBManager;
import ho.module.teamAnalyzer.vo.PlayerInfo;

import java.util.Iterator;
import java.util.List;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class PlayerDataManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final int AVAILABLE = 0;

    /** TODO Missing Parameter Documentation */
    public static final int INJURED = 1;

    /** TODO Missing Parameter Documentation */
    public static final int SUSPENDED = 2;

    /** TODO Missing Parameter Documentation */
    public static final int SOLD = 3;


    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param playerId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static PlayerInfo getLatestPlayerInfo(int playerId) {
        PlayerInfo info = DBManager.instance().getPlayerInfo(playerId);

        if (info.getPlayerId() == 0) {
            info = DBManager.instance().getPreviousPlayeInfo(playerId);
        }

        return info;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     * @param week TODO Missing Method Parameter Documentation
     * @param season TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static PlayerInfo getPlayerInfo(int id, int week, int season) {
        return DBManager.instance().getPlayerInfo(id, week, season);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static PlayerInfo getPlayerInfo(int id) {
        return DBManager.instance().getPlayerInfo(id);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param playerId TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static PlayerInfo getPreviousPlayerInfo(int playerId) {
        return DBManager.instance().getPreviousPlayeInfo(playerId);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param players TODO Missing Method Parameter Documentation
     */
    public static void update(List<PlayerInfo> players) {
        for (Iterator<PlayerInfo> iter = players.iterator(); iter.hasNext();) {
            PlayerInfo parsedPlayer = iter.next();
            setPlayer(parsedPlayer);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param info TODO Missing Method Parameter Documentation
     */
    private static void setPlayer(PlayerInfo info) {
        if (info.getPlayerId() == 0) {
            return;
        }

        PlayerInfo actual = DBManager.instance().getPlayerInfo(info.getPlayerId());

        if (actual.getPlayerId() == 0) {
        	DBManager.instance().addPlayerInfo(info);
        } else {
        	DBManager.instance().updatePlayerInfo(info);
        }
    }
}
