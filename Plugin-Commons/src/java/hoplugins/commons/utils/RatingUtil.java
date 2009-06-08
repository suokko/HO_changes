// %1126721046323:hoplugins.commons.utils%
package hoplugins.commons.utils;

import plugins.IHOMiniModel;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Utility for Rating Formatting
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public final class RatingUtil {
    /**
     * Private default constuctor to prevent class instantiation.
     */
    private RatingUtil() {
    }

    /**
     * Utility Method that returns a string representation of the rating
     *
     * @param rating int value of the rating
     * @param showNumber is the numberical representation being shown
     * @param showText is the textual representation being shown
     * @param model IHOMinimodel
     *
     * @return String with rating as configured
     */
    public static String getRating(int rating, boolean showNumber,
        boolean showText, IHOMiniModel model) {
        if (rating == 0) {
            return "";
        }

        final String value = model.getHelper().getNameForBewertung(rating,
                showNumber, true);
        String level = value;
        String subLevel = "";
        final StringTokenizer st = new StringTokenizer(value, "(");
        if (value != null && value.indexOf("(")>-1 && value.indexOf(")")>-1) {
	        level = st.nextToken().trim();
	        StringTokenizer st2 = new StringTokenizer(st.nextToken(), ")");
	        subLevel = st2.nextToken();
        }

        if (subLevel.indexOf(model.getLanguageString("veryhigh")) > -1) {
            level = level + "++";
        }
        else if (subLevel.indexOf(model.getLanguageString("high")) > -1) {
            level = level + "+";
        }
        else if (subLevel.indexOf(model.getLanguageString("verylow")) > -1) {
            level = level + "--";
        }
        else if (subLevel.indexOf(model.getLanguageString("low")) > -1) {
            level = level + "-";
        }

        if (!showText) {
            level = "";
        }

        if (showNumber) {
        	if (value.indexOf("(")>-1 && value.indexOf(")")>-1) {
	        	StringTokenizer st2 = new StringTokenizer(st.nextToken(), ")");
	
	            final String number = st2.nextToken();
	
	            if (level.length() > 0) {
	                level = level + " (" + number + ")";
	            }
	            else {
	                level = number;
	            }
        	} else {
        		level = level + " (0)";
        	}
        }

        return level;
    }

    /**
     * Utility Method that returns a double representation of the rating
     *
     * @param desc String representation of the rating
     * @param isNumeric Indicator if the string includes the numerical value
     * @param isDescription Indicator if the string includes the description
     * @param skills List of skills
     *
     * @return ouble with raing
     */
    public static double getRating(String desc, boolean isNumeric,
        boolean isDescription, List skills) {
        if (isNumeric && !isDescription) {
            return Double.parseDouble(desc);
        }

        if (isNumeric && isDescription) {
            final StringTokenizer st = new StringTokenizer(desc, "(");

            st.nextToken();

            String s = st.nextToken();

            s = s.substring(0, s.length() - 1);

            return Double.parseDouble(s);
        }

        desc.trim();

        double extra = 0.5;

        String valueStr = null;

        if (desc.indexOf("++") > -1) {
            extra = 0.7;
            valueStr = desc.substring(0, desc.indexOf("++"));
        }
        else if (desc.indexOf("+") > -1) {
            extra = 0.6;
            valueStr = desc.substring(0, desc.indexOf("+"));
        }
        else if (desc.indexOf("--") > -1) {
            extra = 0.3;
            valueStr = desc.substring(0, desc.indexOf("--"));
        }
        else if (desc.indexOf("-") > -1) {
            extra = 0.4;
            valueStr = desc.substring(0, desc.indexOf("-"));
        }
        else {
            // Unable to determine value
            return 0.0;
        }

        final int value = skills.indexOf(valueStr);

        return extra + value;
    }
}
