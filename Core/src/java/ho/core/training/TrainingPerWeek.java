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

    private int _HTSeason = -1;
    private int _HTWeek = -1;
    private int _HRFID;
    private int _Intensity = -1;
    private int _Stamina = -1;
    private int _TrainingType = -1;
    private int _Week = -1;
    private int _Year = -1;
    private int _PreviousHRFID;
    
    //~ Constructors -------------------------------------------------------------------------------
    public TrainingPerWeek() {
    	
    }
    /**
     * Creates a new Training object.
     *
     * @param week
     * @param year
     * @param trType
     * @param intensity
     * @param stamina
     */
    public TrainingPerWeek(int week, int year, int trType, int intensity, int stamina) {
        this._Week = week;
        this._Year = year;
        this._TrainingType = trType;
        this._Intensity = intensity;
        this._Stamina = stamina;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setHattrickSeason(int i) {
        _HTSeason = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getHattrickSeason() {
        return _HTSeason;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setHattrickWeek(int i) {
        _HTWeek = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getHattrickWeek() {
        return _HTWeek;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setHrfId(int i) {
        _HRFID = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getHrfId() {
        return _HRFID;
    }

    /**
     * DOCUMENT ME!
     *
     * @param stamina
     */
    public final void setStaminaPart(int stamina) {
        this._Stamina = stamina;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getStaminaPart() {
        return this._Stamina;
    }

    /**
     * DOCUMENT ME!
     *
     * @param intensity
     */
    public final void setTrainingIntensity(int intensity) {
        this._Intensity = intensity;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getTrainingIntensity() {
        return this._Intensity;
    }

    /**
     * DOCUMENT ME!
     *
     * @param trType
     */
    public final void setTrainingType(int trType) {
        this._TrainingType = trType;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getTrainingType() {
        return this._TrainingType;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getWeek() {
        return this._Week;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getYear() {
        return this._Year;
    }

    /**
     * toString method: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public final String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("TrainingPerWeek[");
        buffer.append("intensity = " + _Intensity);
        buffer.append(", staminaTrainingPart = " + _Stamina);
        buffer.append(", typ = " + _TrainingType);
        buffer.append(", week = " + _Week);
        buffer.append(", year = " + _Year);
        buffer.append(", hattrickWeek = " + _HTWeek);
        buffer.append(", hattrickSeason = " + _HTSeason);
        buffer.append(", trainDate = " + getTrainingDate().getTime().toString());
        buffer.append(", hrfId = " + _HRFID);
        buffer.append("]");
        return buffer.toString();
    }
	public int getPreviousHrfId() {
		return _PreviousHRFID;
	}

	public void setPreviousHrfId(int i) {
		_PreviousHRFID = i;
	}

	/**
	 * calculate the training date for this week/year
	 *
	 * @return	training date
	 */
	public Calendar getTrainingDate () {
		// set calendar values
		final Calendar cal = Calendar.getInstance(Locale.UK);

		/**
		 * Start of Week is Sunday, because all Trainings in all Countries
		 * are before Sunday (actually they are on Thursday and Friday)
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
