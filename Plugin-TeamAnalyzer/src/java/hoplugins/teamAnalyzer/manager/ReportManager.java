// %3860311605:hoplugins.teamAnalyzer.manager%
package hoplugins.teamAnalyzer.manager;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.report.TeamReport;
import hoplugins.teamAnalyzer.vo.Match;
import hoplugins.teamAnalyzer.vo.MatchDetail;
import hoplugins.teamAnalyzer.vo.TeamLineup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class ReportManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static TeamLineup lineup;
    private static List<MatchDetail> matchDetails;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param gameNumber TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static TeamLineup getLineup(int gameNumber) {
        TeamReport report = new TeamReport();
        int i = 1;

        for (Iterator<MatchDetail> iter = matchDetails.iterator(); iter.hasNext();) {
            MatchDetail match = iter.next();

            if (i == gameNumber) {
                report.addMatch(match, true);

                break;
            }

            i++;
        }

        TeamLineupBuilder builder = new TeamLineupBuilder(report);

        return builder.getLineup();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static TeamLineup getLineup() {
        return lineup;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param matchDetails TODO Missing Method Parameter Documentation
     */
    public static void buildReport(List<?> matchDetails) {
        TeamReport report = new TeamReport();

        for (Iterator<?> iter = matchDetails.iterator(); iter.hasNext();) {
            MatchDetail match = (MatchDetail) iter.next();

            report.addMatch(match, SystemManager.getConfig().isShowUnavailable());
        }

        TeamLineupBuilder builder = new TeamLineupBuilder(report);

        lineup = builder.getLineup();
    }

    /**
     *
     */
    public static void clean() {
        lineup = null;
        matchDetails = new ArrayList<MatchDetail>();
    }

    /**
     * TODO Missing Method Documentation
     */
    public static void updateReport() {
        matchDetails = MatchManager.getMatchDetails();

        if (MatchPopulator.getAnalyzedMatch().size() > 0) {
            buildReport(matchDetails);
        } else {
            lineup = null;
        }

        updateFilteredMatches();
    }

    /**
     * TODO Missing Method Documentation
     */
    private static void updateFilteredMatches() {
        List<String> filterList = new ArrayList<String>();

        for (Iterator<Match> iter = MatchManager.getSelectedMatches().iterator(); iter.hasNext();) {
            Match match = iter.next();

            filterList.add("" + match.getMatchId());
        }

        SystemManager.getFilter().setMatches(filterList);
    }
}
