package ho.core.util;

import ho.core.model.HOModel;
import ho.core.model.HOVerwaltung;

import java.util.Calendar;
import java.util.Date;

/**
 * Factory for creating HTCalendar instances.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public final class HTCalendarFactory {
    /**
     * Constructs a HTCalendar.
     */
    private HTCalendarFactory() {
    }

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the economy date to flip
     * over to the next week.
     *
     * @param model HO data model
     *
     * @return a HTCalendar.
     */
    public static HTCalendar createEconomyCalendar() {
    	HOModel model = HOVerwaltung.instance().getModel();
    	final Calendar calMark = Calendar.getInstance();
    	if (model == null || model.getXtraDaten() == null || model.getXtraDaten().getEconomyDate() == null) {
    		return new HTCalendar();
    	}
    	calMark.setTimeInMillis(model.getXtraDaten().getEconomyDate().getTime());
    	final HTCalendar calendar = new HTCalendar();
    	calendar.initialize(calMark);
    	calendar.setTime(model.getBasics().getDatum());

    	final int correction = calendar.getHTSeason() - model.getBasics().getSeason();

    	calendar.setSeasonCorrection(correction);
    	return calendar;
    }

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the economy date to flip
     * over to the next week.
     *
     * @param model HO data model
     * @param date Date to set the calendar
     *
     * @return a HTCalendar.
     */
    public static HTCalendar createEconomyCalendar( Date date) {
        final HTCalendar calendar = createEconomyCalendar();

        calendar.setTime(date);

        return calendar;
    }

    /**
     * Creates a HTCalendar to calculate global (Swedish) values.
     *
     * @return a HTCalendar.
     */
    public static HTCalendar createGlobalCalendar() {
        return new HTCalendar();
    }

    /**
     * Creates a HTCalendar to calculate global (Swedish) values  and presets it with he specified
     * date a date.
     *
     * @param date Date to set the calendar
     *
     * @return a HTCalendar.
     */
    public static HTCalendar createGlobalCalendar(Date date) {
        final HTCalendar calendar = createGlobalCalendar();

        calendar.setTime(date);

        return calendar;
    }

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the training date to
     * flip over to the next week.
     *
     * @param model HO data model
     *
     * @return a HTCalendar.
     */
    public static HTCalendar createTrainingCalendar() {
    	HOModel model = HOVerwaltung.instance().getModel();
        final Calendar calMark = Calendar.getInstance();

        calMark.setTimeInMillis(model.getXtraDaten().getTrainingDate().getTime());

        final HTCalendar calendar = new HTCalendar();

        calendar.initialize(calMark);
        calendar.setTime(model.getBasics().getDatum());

        final int correction = calendar.getHTSeason()
            - model.getBasics().getSeason();

        calendar.setSeasonCorrection(correction);

        return calendar;
    }

    /**
     * Creates a HTCalendar to calculate local values for a league,  using the training date to
     * flip over to the next week.
     *
     * @param model HO data model
     * @param date Date to set the calendar
     *
     * @return a HTCalendar.
     */
    public static HTCalendar createTrainingCalendar(Date date) {
        final HTCalendar calendar = createTrainingCalendar();
        calendar.setTime(date);

        return calendar;
    }
}
