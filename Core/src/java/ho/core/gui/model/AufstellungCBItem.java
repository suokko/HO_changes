// %1683810490:de.hattrickorganizer.gui.model%
package ho.core.gui.model;

import ho.module.lineup.AufstellungsVergleichHistoryPanel;
import ho.module.lineup.Lineup;


/**
 * Named Lineup item.
 */
public class AufstellungCBItem {
    //~ Instance fields ----------------------------------------------------------------------------

    private Lineup m_clAufstellung;
    private String m_sText;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungCBItem object.
     */
    public AufstellungCBItem(String text, Lineup aufstellung) {
        m_sText = text;
        m_clAufstellung = aufstellung;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Check, if displayed.
     */
    public final boolean isAngezeigt() {
        return AufstellungsVergleichHistoryPanel.isAngezeigt(this);
    }

    /**
     * Set the Lineup.
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
     * Set a name.
     */
    public final void setText(String text) {
        m_sText = text;
    }

    /**
     * Get the name.
     */
    public final String getText() {
        return m_sText;
    }

    /**
     * Duplicate a AufstellungCBItem.
     */
    public final AufstellungCBItem duplicate() {
        return new AufstellungCBItem(this.getText(), this.getAufstellung().duplicate());
    }

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

    @Override
	public final String toString() {
        return m_sText;
    }
}
