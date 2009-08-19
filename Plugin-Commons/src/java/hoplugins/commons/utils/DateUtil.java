// %1117664764859:hoplugins.commons.utils%
package hoplugins.commons.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Collection of Date Utility
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public final class DateUtil {
    /**
     * Private default constuctor to prevent class instantiation.
     */
    private DateUtil() {
    }

    /**
     * Returns the number of full days between two dates
     *
     * @param startDate Start date
     * @param endDate End date
     *
     * @return number of days between start and end date
     */
    public static int getDiffDays(Date startDate, Date endDate) {
        final Calendar start = new GregorianCalendar();

        start.setTime(startDate);

        final Calendar end = new GregorianCalendar();

        end.setTime(endDate);

        int i;

        for (i = 0; start.before(end); i++) {
            start.add(Calendar.DAY_OF_MONTH, 1);
        }

        return i;
    }

    /**
     * Check if the two dates are in the same day
     *
     * @param date1 Date 1
     * @param date2 Date 2
     *
     * @return true, if both dates are in the same day, even at different time
     */
    public static boolean equalsAsDays(Date date1, Date date2) {
        final Date d1 = resetDay(date1);
        final Date d2 = resetDay(date2);

        return d1.equals(d2);
    }

    /**
     * Reset the Date to midnight time
     *
     * @param date Date to reset
     *
     * @return a new Date at the same day but time set to midnight
     */
    public static Date resetDay(Date date) {
        final Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        resetDay(cal);

        return cal.getTime();
    }

    /**
     * Reset the Calendar to midnight time
     *
     * @param cal Calendar to reset
     *
     * @return a new Calendar at the same day but time set to midnight
     */
    static Calendar resetDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }
}
