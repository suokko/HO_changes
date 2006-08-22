// %531033194:de.hattrickorganizer.model%
package de.hattrickorganizer.model;


// %1110567470171:hoplugins.commons.model%

/**
 * New Training Class
 *
 * @author Bernhard To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TrainingPerWeek implements plugins.ITrainingWeek {
    //~ Instance fields ----------------------------------------------------------------------------

    private int hattrickSeason = -1;
    private int hattrickWeek = -1;
    private int hrfId;
    private int intensity = -1;
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
     */
    public TrainingPerWeek(int week, int year, int typ, int intensity) {
        this.week = week;
        this.year = year;
        this.typ = typ;
        this.intensity = intensity;
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
     * @param intensitaet
     */
    public final void setIntensitaet(int intensitaet) {
        this.intensity = intensitaet;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getIntensitaet() {
        return this.intensity;
    }

    /**
     * DOCUMENT ME!
     *
     * @param typ
     */
    public final void setTyp(int typ) {
        this.typ = typ;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public final int getTyp() {
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
    public final String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("TrainingPerWeek[");
        buffer.append("intensity = " + intensity);
        buffer.append(", typ = " + typ);
        buffer.append(", week = " + week);
        buffer.append(", year = " + year);
        buffer.append(", hattrickWeek = " + hattrickWeek);
        buffer.append(", hattrickSeason = " + hattrickSeason);
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

}
