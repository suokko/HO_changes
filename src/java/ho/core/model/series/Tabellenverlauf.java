// %744452170:de.hattrickorganizer.model.lineup%
/*
 * Tabellenverlauf.java
 *
 * Created on 13. Oktober 2003, 07:51
 */
package ho.core.model.series;



/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class Tabellenverlauf {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    TabellenVerlaufEintrag[] m_aEintraege = new TabellenVerlaufEintrag[0];

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of Tabellenverlauf
     */
    public Tabellenverlauf() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_aEintraege.
     *
     * @param m_aEintraege New value of property m_aEintraege.
     */
    public final void setEintraege(TabellenVerlaufEintrag[] m_aEintraege) {
        this.m_aEintraege = m_aEintraege;
    }

    /**
     * Getter for property m_aEintraege.
     *
     * @return Value of property m_aEintraege.
     */
    public final TabellenVerlaufEintrag[] getEintraege() {
        return this.m_aEintraege;
    }
}
