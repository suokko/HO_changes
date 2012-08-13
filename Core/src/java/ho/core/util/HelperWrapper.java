// %2519751489:de.hattrickorganizer.tools%
/*
 * HelperWrapper.java
 *
 * Created on 29. März 2004, 08:10
 */
package ho.core.util;

import ho.core.file.xml.XMLMatchdetailsParser;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchType;
import ho.core.model.match.Matchdetails;
import ho.core.model.player.ISpielerPosition;
import ho.core.net.MyConnector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;




/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class HelperWrapper {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static HelperWrapper m_clInstance;
    final static long WEEK = 24 * 7 * 3600 * 1000L;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of HelperWrapper
     */
    private HelperWrapper() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    public static HelperWrapper instance() {
        if (m_clInstance == null) {
            m_clInstance = new HelperWrapper();
        }

        return m_clInstance;
    }

    public Date getHattrickDate(String string) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.UK);
        final Date d = sdf.parse(string);
        final String hh = string.substring(11, 13);

        if (hh.equalsIgnoreCase("12")) {
            final Calendar c = Calendar.getInstance(Locale.UK);
            c.setFirstDayOfWeek(Calendar.SUNDAY);
            c.setTime(d);
            c.add(Calendar.HOUR_OF_DAY, 12);
            return c.getTime();
        }

        return d;
    }

    /**
     * Calculate the last valid training date for a certain date (skillupDate)
     *
     * @param skillupDate the skillupdate or HRF date
     * @param refTrainingDate a reference containing a valid training time and day of week
     *
     * @return the last valid training date for the given 'skillupDate'
     */
    public Calendar getLastTrainingDate(Date skillupDate, Date refTrainingDate) {
        // Calendar for TrainingDate
        final Calendar trDate = Calendar.getInstance(Locale.US);

        // IF refTrainingDate is null first run of HO
        if (refTrainingDate == null) {
            return trDate;
        }

        trDate.setTime(refTrainingDate);
        trDate.setFirstDayOfWeek(Calendar.SUNDAY);
        trDate.setLenient(true);
        trDate.setMinimalDaysInFirstWeek(1);

        // Calendar for Skillup Date
        final Calendar suDate = Calendar.getInstance(Locale.US);
        suDate.setFirstDayOfWeek(Calendar.SUNDAY);
        suDate.setMinimalDaysInFirstWeek(1);
        suDate.setLenient(true);
        suDate.setTime(skillupDate);

        // Move TrainingDate back to skillup week
        trDate.set(Calendar.YEAR, suDate.get(Calendar.YEAR));
        trDate.set(Calendar.WEEK_OF_YEAR, suDate.get(Calendar.WEEK_OF_YEAR));

        // Check that is fine
        long diff = suDate.getTimeInMillis() - trDate.getTimeInMillis();

        // training date must be within one week (handle end of year)
        while (diff > WEEK) {
        	trDate.add(Calendar.WEEK_OF_YEAR, +1);
        	diff = suDate.getTimeInMillis() - trDate.getTimeInMillis();
        }

        // training date must not be after skillup date
        while (diff != 0 && trDate.after(suDate)) {
        	trDate.add(Calendar.WEEK_OF_YEAR, -1);
        }

        final Calendar c = Calendar.getInstance(Locale.UK);
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setMinimalDaysInFirstWeek(1);
        c.setTime(trDate.getTime());
        return c;
    }

     /**
     * Utility Method that returns the field position from the HO Position Code (hoposcode) It is
     * impossible to make difference between left and right so always the left position is
     * returned
     *
     * @param hoposcode
     *
     * @return the field position
     */
    public int getPosition(int hoposcode) {
        if (hoposcode == 0) {
            return ISpielerPosition.keeper;
        }

        if (hoposcode < 4) {
            return ISpielerPosition.rightCentralDefender;
        }

        if ((hoposcode < 8)) {
            return ISpielerPosition.leftBack;
        }

        if ((hoposcode < 12)) {
            return ISpielerPosition.rightInnerMidfield;
        }

        if ((hoposcode < 16)) {
            return ISpielerPosition.leftWinger;
        }

        if ((hoposcode < 18) || (hoposcode == 21)) {
            return ISpielerPosition.rightForward;
        }

        if (hoposcode < 0) {
            return 0;
        }

        return hoposcode;
    }

    @Deprecated
    public boolean isUserMatch(String matchID, MatchType matchType) {
    	try {
          String input = MyConnector.instance().getMatchdetails(Integer.parseInt(matchID), matchType);
          Matchdetails mdetails = XMLMatchdetailsParser.parseMachtdetailsFromString(input, null);
          int teamID = HOVerwaltung.instance().getModel().getBasics().getTeamId();
          return ((mdetails.getHeimId() == teamID) || (mdetails.getGastId() == teamID));
      } catch (Exception e) {
      	HOLogger.instance().warning(Helper.class, "Err: " + e);
      }

      return false;
    }
}
