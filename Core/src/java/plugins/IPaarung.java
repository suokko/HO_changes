// %1127326956540:plugins%
/*
 * IPaarung.java
 *
 * Created on 21. Oktober 2004, 07:34
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface IPaarung extends Comparable<IPaarung> {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Getter for property m_lDatum.
     *
     * @return Value of property m_lDatum.
     */
    public java.sql.Timestamp getDatum();

    /**
     * Getter for property m_iGastId.
     *
     * @return Value of property m_iGastId.
     */
    public int getGastId();

    /**
     * Getter for property m_sGastName.
     *
     * @return Value of property m_sGastName.
     */
    public java.lang.String getGastName();

    /**
     * Getter for property m_iHeimId.
     *
     * @return Value of property m_iHeimId.
     */
    public int getHeimId();

    /**
     * Getter for property m_sHeimName.
     *
     * @return Value of property m_sHeimName.
     */
    public java.lang.String getHeimName();

    /**
     * Getter for property m_iLigaId.
     *
     * @return Value of property m_iLigaId.
     */
    public int getLigaId();

    /**
     * Getter for property m_iMatchId.
     *
     * @return Value of property m_iMatchId.
     */
    public int getMatchId();

    /**
     * Getter for property m_iSaison.
     *
     * @return Value of property m_iSaison.
     */
    public int getSaison();

    /**
     * Getter for property Matchround.
     *
     * @return Value of property m_iSpieltag.
     */
    public int getSpieltag();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getStringDate();

    /**
     * Getter for property m_iToreGast.
     *
     * @return Value of property m_iToreGast.
     */
    public int getToreGast();

    /**
     * Getter for property m_iToreHeim.
     *
     * @return Value of property m_iToreHeim.
     */
    public int getToreHeim();

    /**
     * has been played ?
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean hatStattgefunden();
}
