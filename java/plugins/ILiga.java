// %1127326955665:plugins%
package plugins;

/**
 * Access to league data
 */
public interface ILiga {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_sLiga.
     *
     * @param m_sLiga New value of property m_sLiga.
     */
    public void setLiga(java.lang.String m_sLiga);

    /**
     * Getter for property League
     *
     * @return Value of property m_sLiga.
     */
    public java.lang.String getLiga();

    /**
     * Setter for property m_iPlatzierung.
     *
     * @param m_iPlatzierung New value of property m_iPlatzierung.
     */
    public void setPlatzierung(int m_iPlatzierung);

    /**
     * Getter for property placering
     *
     * @return Value of property m_iPlatzierung.
     */
    public int getPlatzierung();

    /**
     * Setter for property m_iPunkte.
     *
     * @param m_iPunkte New value of property m_iPunkte.
     */
    public void setPunkte(int m_iPunkte);

    /**
     * Getter for property points
     *
     * @return Value of property m_iPunkte.
     */
    public int getPunkte();

    /**
     * Setter for property m_iSpieltag.
     *
     * @param m_iSpieltag New value of property m_iSpieltag.
     */
    public void setSpieltag(int m_iSpieltag);

    /**
     * Getter for property matchday number
     *
     * @return Value of property m_iSpieltag.
     */
    public int getSpieltag();

    /**
     * Setter for property m_iToreFuer.
     *
     * @param m_iToreFuer New value of property m_iToreFuer.
     */
    public void setToreFuer(int m_iToreFuer);

    /**
     * Getter for property Goals for.
     *
     * @return Value of property m_iToreFuer.
     */
    public int getToreFuer();

    /**
     * Setter for property m_iToreGegen.
     *
     * @param m_iToreGegen New value of property m_iToreGegen.
     */
    public void setToreGegen(int m_iToreGegen);

    /**
     * Getter for property Goals againgst.
     *
     * @return Value of property m_iToreGegen.
     */
    public int getToreGegen();
}
