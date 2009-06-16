// %2944446152:hoplugins.teamAnalyzer.manager%
package hoplugins.teamAnalyzer.manager;

import hoplugins.teamAnalyzer.util.MatchUtil;
import hoplugins.teamAnalyzer.vo.Filter;
import hoplugins.teamAnalyzer.vo.Match;

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

                if (isAutomaticIncluded(filter, match)) {
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
     * TODO Missing Method Documentation
     *
     * @param filter TODO Missing Method Parameter Documentation
     * @param match TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private boolean isAutomaticIncluded(Filter filter, Match match) {
        boolean isHome = MatchUtil.isHome(match);
        int result = MatchUtil.result(match);
        int matchType = match.getMatchType();

        if (!filter.isHomeGames() && isHome) {
            return false;
        }

        if (!filter.isAwayGames() && !isHome) {
            return false;
        }

        if (!filter.isDefeat() && (result == -1)) {
            return false;
        }

        if (!filter.isDraw() && (result == 0)) {
            return false;
        }

        if (!filter.isWin() && (result == 1)) {
            return false;
        }

        if (!filter.isLeague() && (matchType == 1)) {
            return false;
        }

        if (!filter.isCup() && (matchType == 3)) {
            return false;
        }

        if (!filter.isQualifier() && (matchType == 2)) {
            return false;
        }

        if (!filter.isFriendly() && (matchType > 3)) {
            return false;
        }

        return true;
    }
}
