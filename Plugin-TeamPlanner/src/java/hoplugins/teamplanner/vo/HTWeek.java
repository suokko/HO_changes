// %2685801291:hoplugins.teamplanner.vo%
package hoplugins.teamplanner.vo;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class HTWeek implements Comparable<HTWeek> {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Missing Parameter Documentation */
    public static final String SEASON_WEEK_SEPARATOR = " / ";

    //~ Instance fields ----------------------------------------------------------------------------

    private int season;
    private int week;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new HTWeek object.
     */
    public HTWeek() {
        super();
        season = 1;
        week = 1;
    }

    /**
     * Creates a new HTWeek object.
     *
     * @param season Missing Constructuor Parameter Documentation
     * @param week Missing Constructuor Parameter Documentation
     */
    public HTWeek(int season, int week) {
        this.season = season;
        this.week = week;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getSeason() {
        return season;
    }

    /**
     * Missing Method Documentation
     *
     * @param season Missing Method Parameter Documentation
     * @param week Missing Method Parameter Documentation
     */
    public void setSeasonAndWeek(int season, int week) {
        this.season = season;
        this.week = week;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getWeek() {
        return week;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getWeekNumber() {
        return week + (season * 16);
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void addWeek(int i) {
        week = week + i;

        while (week < 1) {
            season--;
            week = week + 16;
        }

        while (week > 16) {
            season++;
            week = week - 16;
        }
    }

    /**
     * Missing Method Documentation
     *
     * @param obj Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int compareTo(HTWeek obj) {
        HTWeek week2 = obj;

        if (season > week2.season) {
            return 1;
        }

        if (season < week2.season) {
            return -1;
        }

        if (this.week > week2.week) {
            return 1;
        }

        if (this.week < week2.week) {
            return -1;
        }

        return 0;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public HTWeek copy() {
        return new HTWeek(season, week);
    }

    /**
     * Missing Method Documentation
     *
     * @param obj Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    @Override
	public boolean equals(Object obj) {
        HTWeek week2 = (HTWeek) obj;

        if ((this.season == week2.season) && (this.week == week2.week)) {
            return true;
        }

        return false;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    @Override
	public String toString() {
        return season + SEASON_WEEK_SEPARATOR + week;
    }
}
