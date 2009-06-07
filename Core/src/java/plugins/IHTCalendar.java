package plugins;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public interface IHTCalendar {


    /**
     * Gets the calculated Hattrick season  or 0 if the date is before the league's first season.
     *
     * @return The calculated Hattrick season.
     */
    public int getHTSeason();

    /**
     * Gets the calculated Hattrick week. or 0 if the date is before the league's first season.
     *
     * @return The calculated Hattrick week.
     */
    public int getHTWeek();

    /**
     * Sets the date for which to calculate the Hattrick season and week.
     *
     * @param time Date for which to calculate the Hattrick season and week.
     */
    public void setTime(Timestamp time);

    /**
     * Sets the date for which to calculate the Hattrick season and week.
     *
     * @param time Date for which to calculate the Hattrick season and week.
     */
    public void setTime(Date time);

    /**
     * Sets the date for which to calculate the Hattrick season and week.
     *
     * @param cal Date for which to calculate the Hattrick season and week.
     */
    public void setTime(Calendar cal);

    /**
     * Gets the date for which the Hattrick season and week is calculated.
     *
     * @return Date for which the Hattrick season and week is calculated.
     */
    public Date getTime();

    /**
     * Initializes the HTCalendar for flip-over point.
     *
     * @param marker Calendar representing a day on which the week ends/starts
     */
    void initialize(Calendar marker);

    /**
     * Sets the season correction factor for the local league.
     *
     * @param correction TODO Missing Constructuor Parameter Documentation
     */
    void setSeasonCorrection(int correction);

}
