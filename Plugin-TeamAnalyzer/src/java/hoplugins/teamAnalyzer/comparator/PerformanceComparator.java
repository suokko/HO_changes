// %583496668:hoplugins.teamAnalyzer.comparator%
package hoplugins.teamAnalyzer.comparator;

import hoplugins.teamAnalyzer.report.Report;

import java.util.Comparator;


/**
 * Comparator that orders based on number of performanceand rating as secondary parameter
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class PerformanceComparator implements Comparator {
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
        int s1 = ((Report) o1).getAppearance();
        int s2 = ((Report) o2).getAppearance();

        if (s1 > s2) {
            return -1;
        }

        if (s2 > s1) {
            return 1;
        }

        double n1 = ((Report) o1).getRating();
        double n2 = ((Report) o2).getRating();

        if (n1 > n2) {
            return -1;
        }

        if (n2 > n1) {
            return 1;
        }

        return 1;
    }
}
