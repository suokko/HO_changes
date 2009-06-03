// %1127327011994:plugins%
/*
 * ISpielplan.java
 *
 * Created on 21. Oktober 2004, 07:40
 */
package plugins;

import java.util.Vector;


/**
 * encapsulats League Fixtures
 *
 * @author thomas.werth
 */
public interface ISpielplan {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Getter for property m_iLigaId.
     *
     * @return Value of property m_iLigaId.
     */
    public int getLigaId();

    /**
     * Getter for property m_sLigaName.
     *
     * @return Value of property m_sLigaName.
     */
    public java.lang.String getLigaName();

    /**
     * get ALL IPaarung for one matchday liefert die Spiele zu einem Spieltag
     *
     * @param spieltag TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector getPaarungenBySpieltag(int spieltag);

    /////////////////////////////////////////////////////////////////////////////////7
    //Logik
    ///////////////////////////////////////////////////////////////////////////////7

    /**
     * liefert die Spiele eines bestimmten Teams, sortiert nach Spieltagen
     *
     * @param id TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IPaarung[] getPaarungenByTeamId(int id);

    /**
     * Getter for property m_iSaison.
     *
     * @return Value of property m_iSaison.
     */
    public int getSaison();

    ////////////////////////////////////////////////////////////////////////////////
    //Liga Tabelle
    ////////////////////////////////////////////////////////////////////////////////    
    public ILigaTabelle getTabelle();

    ////////////////////////////////////////////////////////////////////////////////
    //Tabellenverlauf
    ////////////////////////////////////////////////////////////////////////////////    

    /**
     * DOCUMENT ME!
     *
     * @return TableGraph
     */
    public ITabellenverlauf getVerlauf();

    /**
     * generates Table Graph shouldn't be necessary to be called outside of ho
     */

    //public ITabellenverlauf generateTabellenVerlauf( );
}
