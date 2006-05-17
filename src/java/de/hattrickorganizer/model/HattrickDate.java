// %1862325673:de.hattrickorganizer.model%
package de.hattrickorganizer.model;

/**
 * Hattrick Date Object
 */
public class HattrickDate {
    //~ Instance fields ----------------------------------------------------------------------------

    /** season */
    private int season;

    /** Week */
    private int week;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Set Hattrick Season
     *
     * @param season
     */
    public final void setSeason(int season) {
        this.season = season;
    }

    /**
     * Get Hattrick Season
     *
     * @return season
     */
    public final int getSeason() {
        return season;
    }

    /**
     * Set Hattrick week
     *
     * @param week
     */
    public final void setWeek(int week) {
        this.week = week;
    }

    /**
     * Get Hattrick
     *
     * @return week
     */
    public final int getWeek() {
        return week;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    public final String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("Skillup[");
        buffer.append("week = " + week);
        buffer.append(", season = " + season);
        buffer.append("]");
        return buffer.toString();
    }
}
