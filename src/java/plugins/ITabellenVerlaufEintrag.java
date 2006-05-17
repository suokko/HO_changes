// %1127327012181:plugins%
/*
 * ITabellenVerlaufEintrag.java
 *
 * Created on 21. Oktober 2004, 08:55
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface ITabellenVerlaufEintrag {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Getter for property m_iPlatzierungen.
     *
     * @return Value of property m_iPlatzierungen.
     */
    public int[] getPlatzierungen();

    /**
     * Getter for property m_iTeamId.
     *
     * @return Value of property m_iTeamId.
     */
    public int getTeamId();

    /**
     * Getter for property m_sTeamName.
     *
     * @return Value of property m_sTeamName.
     */
    public java.lang.String getTeamName();
}
