// %3002294435:hoplugins.teamAnalyzer.comparator%
package hoplugins.teamAnalyzer.comparator;

import hoplugins.teamAnalyzer.vo.Match;

import java.util.Comparator;
import java.util.Date;


/**
 * Comparator that orders based on match date
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class MatchComparator implements Comparator {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Compare two objects
     *
     * @param o1
     * @param o2
     *
     * @return
     */
    public int compare(Object o1, Object o2) {
        Date s1 = ((Match) o1).getMatchDate();
        Date s2 = ((Match) o2).getMatchDate();

        return s2.compareTo(s1);
    }
}
