// %1683810490:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import de.hattrickorganizer.model.Lineup;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class AufstellungCBItem {
    //~ Instance fields ----------------------------------------------------------------------------

    private Lineup m_clAufstellung;
    private String m_sText;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungCBItem object.
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     * @param aufstellung TODO Missing Constructuor Parameter Documentation
     */
    public AufstellungCBItem(String text, Lineup aufstellung) {
        m_sText = text;
        m_clAufstellung = aufstellung;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isAngezeigt() {
        return de.hattrickorganizer.gui.lineup.AufstellungsVergleichHistoryPanel.isAngezeigt(this);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param aufstellung TODO Missing Method Parameter Documentation
     */
    public final void setAufstellung(Lineup aufstellung) {
        m_clAufstellung = aufstellung;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Lineup getAufstellung() {
        return m_clAufstellung;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void setText(String text) {
        m_sText = text;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getText() {
        return m_sText;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final AufstellungCBItem duplicate() {
        return new AufstellungCBItem(this.getText(), this.getAufstellung().duplicate());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final boolean equals(Object obj) {
        if (obj instanceof AufstellungCBItem) {
            final AufstellungCBItem temp = (AufstellungCBItem) obj;

            if ((temp.getText() != null) && temp.getText().equals(getText())) {
                return true;
            }
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final String toString() {
        return m_sText;
    }
}
