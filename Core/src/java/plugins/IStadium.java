// %1127327012150:plugins%
package plugins;

/**
 * Enthält die Stadiendaten
 */
/**
 * Interface to Access data stored in Stadium section of HRF
 */
public interface IStadium {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_bAusbau.
     *
     * @param m_bAusbau New value of property m_bAusbau.
     */
    public void setAusbau(boolean m_bAusbau);

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public byte getAusbau();

    /**
     * Getter for property m_bAusbau.
     *
     * @return Value of property m_bAusbau.
     */
    public boolean isAusbau();

    /**
     * Setter for property m_iAusbauKosten.
     *
     * @param m_iAusbauKosten New value of property m_iAusbauKosten.
     */
    public void setAusbauKosten(float m_iAusbauKosten);

    /**
     * Getter for property m_iAusbauKosten.
     *
     * @return Value of property m_iAusbauKosten.
     */
    public int getAusbauKosten();

    /**
     * Getter for property m_iAusbauLogen.
     *
     * @param m_iAusbauLogen TODO Missing Constructuor Parameter Documentation
     */
    /**
     * Setter for property m_iAusbauLogen.
     *
     * @param m_iAusbauLogen New value of property m_iAusbauLogen.
     */
    public void setAusbauLogen(int m_iAusbauLogen);

    /**
     * Setter for property m_iAusbauSitzplaetze.
     *
     * @param m_iAusbauSitzplaetze New value of property m_iAusbauSitzplaetze.
     */
    public void setAusbauSitzplaetze(int m_iAusbauSitzplaetze);

    /**
     * Getter for property m_iAusbauSitzplaetze.
     *
     * @return Value of property m_iAusbauSitzplaetze.
     */
    public int getAusbauSitzplaetze();

    /**
     * Setter for property m_iAusbauStehplaetze.
     *
     * @param m_iAusbauStehplaetze New value of property m_iAusbauStehplaetze.
     */
    public void setAusbauStehplaetze(int m_iAusbauStehplaetze);

    /**
     * Getter for property m_iAusbauStehplaetze.
     *
     * @return Value of property m_iAusbauStehplaetze.
     */
    public int getAusbauStehplaetze();

    /**
     * Setter for property m_iAusbauUeberdachteSitzplaetze.
     *
     * @param m_iAusbauUeberdachteSitzplaetze New value of property
     *        m_iAusbauUeberdachteSitzplaetze.
     */
    public void setAusbauUeberdachteSitzplaetze(int m_iAusbauUeberdachteSitzplaetze);

    /**
     * Getter for property m_iAusbauUeberdachteSitzplaetze.
     *
     * @return Value of property m_iAusbauUeberdachteSitzplaetze.
     */
    public int getAusbauUeberdachteSitzplaetze();

    /**
     * Setter for property m_iGesamtgroesse.
     *
     * @param m_iGesamtgroesse New value of property m_iGesamtgroesse.
     */
    public void setGesamtgroesse(int m_iGesamtgroesse);

    ////////////////////////////Accessor////////////////////////////////////////    

    /**
     * Getter for property m_iGesamtgroesse.
     *
     * @return Value of property m_iGesamtgroesse.
     */
    public int getGesamtgroesse();

    /**
     * Setter for property m_iLogen.
     *
     * @param m_iLogen New value of property m_iLogen.
     */
    public void setLogen(int m_iLogen);

    /**
     * Getter for property m_iLogen.
     *
     * @return Value of property m_iLogen.
     */
    public int getLogen();

    /**
     * Setter for property m_iSitzplaetze.
     *
     * @param m_iSitzplaetze New value of property m_iSitzplaetze.
     */
    public void setSitzplaetze(int m_iSitzplaetze);

    /**
     * Getter for property m_iSitzplaetze.
     *
     * @return Value of property m_iSitzplaetze.
     */
    public int getSitzplaetze();

    /**
     * Setter for property m_sStadienname.
     *
     * @param m_sStadienname New value of property m_sStadienname.
     */
    public void setStadienname(java.lang.String m_sStadienname);

    /**
     * Getter for property m_sStadienname.
     *
     * @return Value of property m_sStadienname.
     */
    public java.lang.String getStadienname();

    /**
     * Setter for property m_iStehplaetze.
     *
     * @param m_iStehplaetze New value of property m_iStehplaetze.
     */
    public void setStehplaetze(int m_iStehplaetze);

    /**
     * Getter for property m_iStehplaetze.
     *
     * @return Value of property m_iStehplaetze.
     */
    public int getStehplaetze();

    /**
     * Setter for property m_iUeberdachteSitzplaetze.
     *
     * @param m_iUeberdachteSitzplaetze New value of property m_iUeberdachteSitzplaetze.
     */
    public void setUeberdachteSitzplaetze(int m_iUeberdachteSitzplaetze);

    /**
     * Getter for property m_iUeberdachteSitzplaetze.
     *
     * @return Value of property m_iUeberdachteSitzplaetze.
     */
    public int getUeberdachteSitzplaetze();

    /**
     * berechnet die Maximalen EInahmen bei vollem HAus
     *
     * @return TODO Missing Return Method Documentation
     * @deprecated
     */
    public int calcMaxEinahmen();
}
