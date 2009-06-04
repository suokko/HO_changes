// %1127326956447:plugins%
/*
 * IMPActions.java
 *
 * Created on 22. Dezember 2004, 14:09
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface IMPActions {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static short AREA_LEFTSIDE = -1;

    /** TODO Missing Parameter Documentation */
    public static short AREA_MIDDLE = 0;

    /** TODO Missing Parameter Documentation */
    public static short AREA_RIGHTSIDE = 1;

    /** TODO Missing Parameter Documentation */
    public static short TYPE_NORMAL = 0;

    /** TODO Missing Parameter Documentation */
    public static short TYPE_COUNTER = (short) IMatchDetails.TAKTIK_KONTER;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * where attack occured
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getArea();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isHomeTeam();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getMinute();

    /**
     * is Goal
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isScore();

    /**
     * string representation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getShortDesc();

    /**
     * what kind of attack (counter, normal)
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getType();
}
