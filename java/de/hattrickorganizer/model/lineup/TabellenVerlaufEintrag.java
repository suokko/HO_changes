// %1602616431:de.hattrickorganizer.model.lineup%
/*
 * TabellenVerlaufEintrag.java
 *
 * Created on 13. Oktober 2003, 07:47
 */
package de.hattrickorganizer.model.lineup;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class TabellenVerlaufEintrag implements plugins.ITabellenVerlaufEintrag {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected String m_sTeamName = "";

    /** TODO Missing Parameter Documentation */
    protected int[] m_iPlatzierungen = new int[0];

    /** TODO Missing Parameter Documentation */
    protected int m_iTeamId = -1;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of TabellenVerlaufEintrag
     */
    public TabellenVerlaufEintrag() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_iPlatzierungen.
     *
     * @param m_iPlatzierungen New value of property m_iPlatzierungen.
     */
    public final void setPlatzierungen(int[] m_iPlatzierungen) {
        this.m_iPlatzierungen = m_iPlatzierungen;
    }

    /**
     * Getter for property m_iPlatzierungen.
     *
     * @return Value of property m_iPlatzierungen.
     */
    public final int[] getPlatzierungen() {
        return this.m_iPlatzierungen;
    }

    /**
     * Setter for property m_iTeamId.
     *
     * @param m_iTeamId New value of property m_iTeamId.
     */
    public final void setTeamId(int m_iTeamId) {
        this.m_iTeamId = m_iTeamId;
    }

    /**
     * Getter for property m_iTeamId.
     *
     * @return Value of property m_iTeamId.
     */
    public final int getTeamId() {
        return m_iTeamId;
    }

    /**
     * Setter for property m_sTeamName.
     *
     * @param m_sTeamName New value of property m_sTeamName.
     */
    public final void setTeamName(java.lang.String m_sTeamName) {
        this.m_sTeamName = m_sTeamName;
    }

    /**
     * Getter for property m_sTeamName.
     *
     * @return Value of property m_sTeamName.
     */
    public final java.lang.String getTeamName() {
        return m_sTeamName;
    }
}
