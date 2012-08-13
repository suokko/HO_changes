// %317440327:hoplugins.teamAnalyzer.manager%
package ho.module.teamAnalyzer.manager;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


/**
 * DOCUMENT ME!
 *
 * @author Massimiliano Amato
 */
public class NameManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static Hashtable<String, String> names = new Hashtable<String, String>();

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param name TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getLastName(String name) {
        String lastName = "";

        for (Iterator<String> iter = names.keySet().iterator(); iter.hasNext();) {
            String storedName = iter.next();

            if (name.indexOf(storedName) > 0) {
                return storedName;
            }
        }

        StringTokenizer st = new StringTokenizer(name, " ");

        while (st.hasMoreTokens()) {
            lastName = st.nextToken();
        }

        return lastName;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param name TODO Missing Method Parameter Documentation
     */
    public static void addName(String name) {
        names.put(name, name);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param nameList TODO Missing Method Parameter Documentation
     */
    public static void addNames(List<?> nameList) {
        for (Iterator<?> iter = nameList.iterator(); iter.hasNext();) {
            String name = (String) iter.next();

            names.put(name, name);
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public static void clean() {
        names = new Hashtable<String, String>();
    }
}
