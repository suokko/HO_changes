// %1883458382:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

/**
 * Faktor f�r Geld mit Id f�rs Land
 */
public class GeldFaktorCBItem extends CBItem implements Comparable {
    //~ Instance fields ----------------------------------------------------------------------------

    private float m_fFaktor;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new GeldFaktorCBItem object.
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     * @param faktor TODO Missing Constructuor Parameter Documentation
     * @param id TODO Missing Constructuor Parameter Documentation
     */
    public GeldFaktorCBItem(String text, float faktor, int id) {
        super(text, id);
        m_fFaktor = faktor;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param faktor TODO Missing Method Parameter Documentation
     */
    public final void setFaktor(float faktor) {
        m_fFaktor = faktor;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float getFaktor() {
        return m_fFaktor;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int compareTo(Object obj) {
        if (obj instanceof GeldFaktorCBItem) {
            final GeldFaktorCBItem item = (GeldFaktorCBItem) obj;
            return this.getText().compareTo(item.getText());
        }

        return 0;
    }
}
