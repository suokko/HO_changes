// %1126721046276:hoplugins.commons.utils%
package hoplugins.commons.utils;


/**
 * Math Utility Class
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public final class MathUtil {
    /**
     * Private default constuctor to prevent class instantiation.
     */
    private MathUtil() {
    }

    /**
     * Method to generate a random integer, from 0 to number-1
     *
     * @param number Multiplier for randomizer.
     *
     * @return random integer number
     */
    public static int random(int number) {
        return (int) (Math.random() * number);
    }
}
