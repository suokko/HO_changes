// %850087811:hoplugins.teamAnalyzer.ht%
package hoplugins.teamAnalyzer.ht;

import hoplugins.Commons;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.vo.Match;


/**
 * Class that check for CHPP approval in operations
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class CHPPManager {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Check if CHPP rules approve download for a match
     *
     * @param match Match to be downloaded
     *
     * @return true if allowed
     */
    public static boolean isDownloadAllowed(Match match) {
        boolean isNextOpponent = ((match.getHomeId() == SystemManager.getLeagueOpponentId())
                                 || (match.getAwayId() == SystemManager.getLeagueOpponentId()));
        boolean last2Weeks = false;
        int actualWeek = getWeekNumber(Commons.getModel().getBasics().getSeason(),
                                       Commons.getModel().getBasics().getSpieltag());
        int gameWeek = getWeekNumber(match.getSeason(), match.getWeek());

        if ((actualWeek - gameWeek) < 3) {
            last2Weeks = true;
        }

        return (isNextOpponent && last2Weeks);
    }

    /**
     * Return the week number from week and season, used for CHPP compatibility
     *
     * @param season
     * @param week
     *
     * @return
     */
    private static int getWeekNumber(int season, int week) {
        return ((season - 1) * 16) + week;
    }
}
