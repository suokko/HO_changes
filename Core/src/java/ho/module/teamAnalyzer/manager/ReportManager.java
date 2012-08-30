// %3860311605:hoplugins.teamAnalyzer.manager%
package ho.module.teamAnalyzer.manager;

import ho.core.module.config.ModuleConfig;
import ho.core.util.HOLogger;
import ho.module.teamAnalyzer.SystemManager;
import ho.module.teamAnalyzer.report.TeamReport;
import ho.module.teamAnalyzer.ui.TeamAnalyzerPanel;
import ho.module.teamAnalyzer.vo.Match;
import ho.module.teamAnalyzer.vo.MatchDetail;
import ho.module.teamAnalyzer.vo.TeamLineup;

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
            
            HOLogger.instance().log(ReportManager.class, "build: " + match.toString());

            report.addMatch(match, ModuleConfig.instance().getBoolean(SystemManager.ISSHOWUNAVAILABLE));
        }

        TeamLineupBuilder builder = new TeamLineupBuilder(report);
        HOLogger.instance().log(ReportManager.class, "build("+ matchDetails.size() + "): " + builder.toString());
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

        TeamAnalyzerPanel.filter.setMatches(filterList);
    }
}
