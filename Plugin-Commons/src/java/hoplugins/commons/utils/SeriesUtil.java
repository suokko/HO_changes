// %2811187855:hoplugins.commons.utils%
package hoplugins.commons.utils;

import java.util.StringTokenizer;


/**
 * Utility for Rating Formatting
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public final class SeriesUtil {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Private default constuctor to prevent class instantiation.
     */
    private SeriesUtil() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param seriesName TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static int getSeriesLevel(String seriesName) {
    	try {
    		StringTokenizer st = new StringTokenizer(seriesName, ".");
    		String level = st.nextToken();

    		if (level.equalsIgnoreCase("I")) {
    			return 1;
    		} else if (level.equalsIgnoreCase("II")) {
    			return 2;
    		} else if (level.equalsIgnoreCase("III")) {
    			return 3;
    		} else if (level.equalsIgnoreCase("IV")) {
    			return 4;
    		} else if (level.equalsIgnoreCase("V")) {
    			return 5;
    		} else if (level.equalsIgnoreCase("VI")) {
    			return 6;
    		} else if (level.equalsIgnoreCase("VII")) {
    			return 7;
    		} else if (level.equalsIgnoreCase("VIII")) {
    			return 8;
    		} else if (level.equalsIgnoreCase("IX")) {
    			return 9;
    		} else if (level.equalsIgnoreCase("X")) {
    			return 10;
    		} else if (level.equalsIgnoreCase("XI")) {
    			return 11;
    		} else if (level.equalsIgnoreCase("XII")) {
    			return 12;
    		} else if (level.equalsIgnoreCase("XIII")) {
    			return 13;
    		}
    	} catch (Exception e) {
    		/* nothing */
    	}

    	return 14;
    }
}
