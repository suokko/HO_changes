package ho.core.util;

import java.util.Calendar;
import java.util.Date;

import plugins.IHOMiniModel;
import plugins.IHTCalendar;

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
    public static IHTCalendar createEconomyCalendar(IHOMiniModel model) {
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
    public static IHTCalendar createEconomyCalendar(IHOMiniModel model, Date date) {
        final IHTCalendar calendar = createEconomyCalendar(model);

        calendar.setTime(date);

        return calendar;
    }

    /**
     * Creates a HTCalendar to calculate global (Swedish) values.
     *
     * @return a HTCalendar.
     */
    public static IHTCalendar createGlobalCalendar() {
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
    public static IHTCalendar createGlobalCalendar(Date date) {
        final IHTCalendar calendar = createGlobalCalendar();

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
    public static IHTCalendar createTrainingCalendar(IHOMiniModel model) {
        final Calendar calMark = Calendar.getInstance();

        calMark.setTimeInMillis(model.getXtraDaten().getTrainingDate().getTime());

        final IHTCalendar calendar = new HTCalendar();

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
    public static IHTCalendar createTrainingCalendar(IHOMiniModel model,
        Date date) {
        final IHTCalendar calendar = createTrainingCalendar(model);

        calendar.setTime(date);

        return calendar;
    }
}
