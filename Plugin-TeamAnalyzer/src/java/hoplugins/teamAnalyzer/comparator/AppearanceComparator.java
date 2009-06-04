// %4122214879:hoplugins.teamAnalyzer.comparator%
package hoplugins.teamAnalyzer.comparator;

import hoplugins.teamAnalyzer.vo.PlayerAppearance;

import java.util.Comparator;


/**
 * Comparator that orders based on number of appearance
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class AppearanceComparator implements Comparator {
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
        int s1 = ((PlayerAppearance) o1).getAppearance();
        int s2 = ((PlayerAppearance) o2).getAppearance();

        if (s1 > s2) {
            return -1;
        }

        if (s2 > s1) {
            return 1;
        }

        return 1;
    }
}
