// %1126721046260:hoplugins.commons.utils%
package hoplugins.commons.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * List Utility class
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public final class ListUtil {
    /**
     * Private default constuctor to prevent class instantiation.
     */
    private ListUtil() {
    }

    /**
     * Sort the collection and returns a SortedSet
     *
     * @param beans Collection to be sorted
     * @param comparator Comparator to be used
     *
     * @return a sorted set
     */
    public static<T> SortedSet<T> getSortedSet(Collection<T> beans, Comparator<T> comparator) {
        final SortedSet<T> set = new TreeSet<T>(comparator);

        if ((beans != null) && (beans.size() > 0)) {
            set.addAll(beans);
        }

        return set;
    }
}
