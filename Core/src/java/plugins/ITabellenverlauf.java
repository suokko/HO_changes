// %1127327012165:plugins%
/*
 * ITabellenverlauf.java
 *
 * Created on 21. Oktober 2004, 08:57
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface ITabellenverlauf {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Getter for property m_aEintraege.
     *
     * @return Array of ITabellenVerlaufEintrag
     */
    public ITabellenVerlaufEintrag[] getEintraege();
}
