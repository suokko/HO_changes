// %2725479759:hoplugins.teamAnalyzer.util%
package hoplugins.teamAnalyzer.util;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.vo.Match;

import plugins.IMatchDetails;


/**
 * Match Utility Class
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class MatchUtil {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Check if the match is an homeMatch for the selected team
     *
     * @param match Match to be analyzed
     *
     * @return true, if active team playes at home in that match or false if away
     */
    public static boolean isHome(Match match) {
        boolean isHome = false;

        if (match.getHomeId() == SystemManager.getActiveTeamId()) {
            isHome = true;
        }

        return isHome;
    }

    /**
     * Check if the match is an homeMatch for the selected team
     *
     * @param match IMatchDetails to be analyzed
     *
     * @return true, if active team playes at home in that match or false if away
     */
    public static boolean isHome(IMatchDetails match) {
        boolean isHome = false;

        if (match.getHeimId() == SystemManager.getActiveTeamId()) {
            isHome = true;
        }

        return isHome;
    }

    /**
     * Returns the description  code for the match type
     *
     * @param type int type of match
     *
     * @return A string code
     */
    public static String getMatchType(int type) {
        if (type == 1) {
            return " ";
        } else if (type == 2) {
            return "Q";
        } else if (type == 3) {
            return "C";
        } else {
            return "F";
        }
    }

    /**
     * Returns the result type, as 0 for draw, 1 for wins and -1 for defeat
     *
     * @param match Match to be analyzed
     *
     * @return result code
     */
    public static int result(Match match) {
        boolean isHome = MatchUtil.isHome(match);

        if (match.getHomeGoals() == match.getAwayGoals()) {
            return 0;
        }

        if ((match.getHomeGoals() > match.getAwayGoals()) && isHome) {
            return 1;
        }

        if ((match.getHomeGoals() < match.getAwayGoals()) && !isHome) {
            return 1;
        }

        return -1;
    }

    /**
     * Returns the result type, as 0 for draw, 1 for wins and -1 for defeat
     *
     * @param match IMatchDetails to be analyzed
     *
     * @return result code
     */
    public static int result(IMatchDetails match) {
        boolean isHome = MatchUtil.isHome(match);

        if (match.getHomeGoals() == match.getGuestGoals()) {
            return 0;
        }

        if ((match.getHomeGoals() > match.getGuestGoals()) && isHome) {
            return 1;
        }

        if ((match.getHomeGoals() < match.getGuestGoals()) && !isHome) {
            return 1;
        }

        return -1;
    }
}
