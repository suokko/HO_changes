// %2944446152:hoplugins.teamAnalyzer.manager%
package ho.module.teamAnalyzer.manager;

import ho.module.teamAnalyzer.ui.TeamAnalyzerPanel;
import ho.module.teamAnalyzer.vo.Filter;
import ho.module.teamAnalyzer.vo.Match;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MatchList {
    //~ Instance fields ----------------------------------------------------------------------------

    private List<Match> matchList;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MatchList object.
     */
    public MatchList() {
        matchList = new ArrayList<Match>();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public List<Match> getMatches() {
        return matchList;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param match TODO Missing Method Parameter Documentation
     */
    public void addMatch(Match match) {
        matchList.add(match);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param filter TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public List<Match> filterMatches(Filter filter) {
        int counter = 0;
        List<Match> list = new ArrayList<Match>();

        if (filter.isAutomatic()) {
            for (Iterator<Match> iter = matchList.iterator(); iter.hasNext();) {
                Match match = iter.next();

                if (TeamAnalyzerPanel.filter.isAcceptedMatch(match)) {
                    list.add(match);
                    counter++;
                }

                if (counter >= filter.getNumber()) {
                    break;
                }
            }
        } else {
            List<String> filterMatches = filter.getMatches();

            for (Iterator<Match> iter = matchList.iterator(); iter.hasNext();) {
                Match match = iter.next();

                if (filterMatches.contains("" + match.getMatchId())) {
                    list.add(match);
                }
            }
        }

        return list;
    }
    
    /**
     * Returns the result type, as 0 for draw, 1 for wins and -1 for defeat
     *
     * @param match Match to be analyzed
     *
     * @return result code
     */
    private int result(Match match) {
        boolean isHome = match.isHome();

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
}
