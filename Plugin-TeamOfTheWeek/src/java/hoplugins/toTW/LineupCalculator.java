// %661713423:hoplugins.toTW%
package hoplugins.toTW;

import hoplugins.toTW.vo.MatchLineupPlayer;

import java.util.Map;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class LineupCalculator {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param week TODO Missing Method Parameter Documentation
     * @param season TODO Missing Constructuor Parameter Documentation
     * @param best TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static MatchLineupPlayer[] calcBestLineup(int week, int season, boolean best) {
        Map<String, MatchLineupPlayer> spieler = DBManager.getPlayers(week, season, best);
        MatchLineupPlayer[] mlp = new MatchLineupPlayer[11];

        for (int i = 0; i < 11; i++) {
            mlp[i] = (MatchLineupPlayer) spieler.get("" + (i + 1));
        }

        return mlp;
    }
}
