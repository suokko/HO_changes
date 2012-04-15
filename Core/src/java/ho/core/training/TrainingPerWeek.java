// %531033194:de.hattrickorganizer.model%
package ho.core.training;

import ho.core.model.HOVerwaltung;
import ho.core.util.HelperWrapper;

import java.util.Calendar;
import java.util.Locale;

/**
 * New Training Class
 *
 * @author Bernhard To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TrainingPerWeek  {
    //~ Instance fields ----------------------------------------------------------------------------

    private int hattrickSeason = -1;
    private int hattrickWeek = -1;
    private int hrfId;
    private int intensity = -1;
    private int staminaTrainingPart = -1;
    private int typ = -1;
    private int week = -1;
    private int year = -1;
    private int previousHrfId;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Training object.
     *
     * @param week
     * @param year
     * @param typ
     * @param intensity
     * @param staminaTrainingPart
     */
    public TrainingPerWeek(int week, int year, int typ, int intensity, int staminaTrainingPart) {
        this.week = week;
        this.year = year;
        this.typ = typ;
        this.intensity = intensity;
        this.staminaTrainingPart = staminaTrainingPart;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setHattrickSeason(int i) {
        hattrickSeason = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getHattrickSeason() {
        return hattrickSeason;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setHattrickWeek(int i) {
        hattrickWeek = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getHattrickWeek() {
        return hattrickWeek;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setHrfId(int i) {
        hrfId = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getHrfId() {
        return hrfId;
    }

    /**
     * DOCUMENT ME!
     *
     * @param staminaTrainingPart
     */
    public final void setStaminaTrainingPart(int staminaTrainingPart) {
        this.staminaTrainingPart = staminaTrainingPart;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getStaminaTrainingPart() {
        return this.staminaTrainingPart;
    }

    /**
     * DOCUMENT ME!
     *
     * @param intensitaet
     */
    public final void setTrainingIntensity(int intensitaet) {
        this.intensity = intensitaet;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getTrainingIntensity() {
        return this.intensity;
    }

    /**
     * DOCUMENT ME!
     *
     * @param typ
     */
    public final void setTrainingType(int typ) {
        this.typ = typ;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getTrainingType() {
        return this.typ;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getWeek() {
        return this.week;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getYear() {
        return this.year;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public final String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("TrainingPerWeek[");
        buffer.append("intensity = " + intensity);
        buffer.append(", staminaTrainingPart = " + staminaTrainingPart);
        buffer.append(", typ = " + typ);
        buffer.append(", week = " + week);
        buffer.append(", year = " + year);
        buffer.append(", hattrickWeek = " + hattrickWeek);
        buffer.append(", hattrickSeason = " + hattrickSeason);
        buffer.append(", trainDate = " + getTrainingDate().getTime().toLocaleString());
        buffer.append(", hrfId = " + hrfId);
        buffer.append("]");
        return buffer.toString();
    }
	public int getPreviousHrfId() {
		return previousHrfId;
	}

	public void setPreviousHrfId(int i) {
		previousHrfId = i;
	}

	/**
	 * calculate the training date for this week/year
	 *
	 * @return	training date
	 */
	public Calendar getTrainingDate () {
		// Kalenderwerte setzen
		// set calendar values
		final Calendar cal = Calendar.getInstance(Locale.UK);

		/**
		 * Start of Week is Sunday, because all Trainings in all Countries
		 * are before Sunday (actually the are on Thursday and Friday)
		 */
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		cal.setMinimalDaysInFirstWeek(1);
		/**
		 * Set year and week from instance fields
		 */
		cal.set(Calendar.YEAR, getYear());
		cal.set(Calendar.WEEK_OF_YEAR, getWeek());
		/**
		 * Set day of the week to saturday, so that the
		 * calculated training date is in this week
		 * (remember that a week starts at sunday as stated above)
		 */
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

		/**
		 * get last training date BEFORE this date
		 * Because our calendar date is a saturday and
		 * the trainings are on thursday and friday,
		 * the training date is is in the same week
		 */
        Calendar trainingDate = HelperWrapper.instance().getLastTrainingDate(cal.getTime(),
        		HOVerwaltung.instance().getModel().getXtraDaten().getTrainingDate());

        return trainingDate;
	}
}