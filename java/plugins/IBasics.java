// %1127326826744:plugins%
package plugins;

/**
 * Benutzerdaten
 */
/**
 * Access to data stored in basic section of hrf
 */
public interface IBasics {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * sets the date
     *
     * @param m_clDatum TODO Missing Constructuor Parameter Documentation
     */
    public void setDatum(java.sql.Timestamp m_clDatum);

    /**
     * return date of current hrf
     *
     * @return TODO Missing Return Method Documentation
     */
    public java.sql.Timestamp getDatum();

    /**
     * Setter for property m_iLand.
     *
     * @param m_iLand New value of property m_iLand.
     */
    public void setLand(int m_iLand);

    /**
     * returns country of team
     *
     * @return Value of property m_iLand.
     */
    public int getLand();

    /**
     * Setter for property m_iLiga.
     *
     * @param m_iLiga New value of property m_iLiga.
     */
    public void setLiga(int m_iLiga);

    /**
     * Getter for property m_iLiga.
     *
     * @return Value of property m_iLiga.
     */
    public int getLiga();

    /**
     * Setter for property m_sManager.
     *
     * @param m_sManager New value of property m_sManager.
     */
    public void setManager(java.lang.String m_sManager);

    /**
     * Getter for property m_sManager.
     *
     * @return Value of property m_sManager.
     */
    public java.lang.String getManager();

    /**
     * Setter for property m_iSeason.
     *
     * @param m_iSeason New value of property m_iSeason.
     */
    public void setSeason(int m_iSeason);

    /**
     * Getter for property m_iSeason.
     *
     * @return Value of property m_iSeason.
     */
    public int getSeason();

    /**
     * Setter for property m_iSpieltag.
     *
     * @param m_iSpieltag New value of property m_iSpieltag.
     */
    public void setSpieltag(int m_iSpieltag);

    /**
     * return number of actual matchday
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getSpieltag();

    /**
     * Setter for property m_iTeamId.
     *
     * @param m_iTeamId New value of property m_iTeamId.
     */
    public void setTeamId(int m_iTeamId);

    /**
     * returns teamid
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getTeamId();

    /**
     * Setter for property m_sTeamName.
     *
     * @param m_sTeamName New value of property m_sTeamName.
     */
    public void setTeamName(java.lang.String m_sTeamName);

    /**
     * Getter for property m_sTeamName.
     *
     * @return Value of property m_sTeamName.
     */
    public java.lang.String getTeamName();
}
