// %1127327012634:plugins%
/*
 * IXtraData.java
 *
 * Created on 10. Mai 2004, 14:45
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface IXtraData {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_sCurrencyName.
     *
     * @param m_sCurrencyName New value of property m_sCurrencyName.
     */
    public void setCurrencyName(java.lang.String m_sCurrencyName);

    /**
     * Getter for property m_sCurrencyName.
     *
     * @return Value of property m_sCurrencyName.
     */
    public java.lang.String getCurrencyName();

    /**
     * Setter for property m_dCurrencyRate.
     *
     * @param m_dCurrencyRate New value of property m_dCurrencyRate.
     */
    public void setCurrencyRate(double m_dCurrencyRate);

    /**
     * Getter for property m_dCurrencyRate.
     *
     * @return Value of property m_dCurrencyRate.
     */
    public double getCurrencyRate();

    /**
     * Setter for property m_clEconomyDate.
     *
     * @param m_clEconomyDate New value of property m_clEconomyDate.
     */
    public void setEconomyDate(java.sql.Timestamp m_clEconomyDate);

    /**
     * Getter for property m_clEconomyDate.
     *
     * @return Value of property m_clEconomyDate.
     */
    public java.sql.Timestamp getEconomyDate();

    /**
     * Setter for property m_bHasPromoted.
     *
     * @param m_bHasPromoted New value of property m_bHasPromoted.
     */
    public void setHasPromoted(boolean m_bHasPromoted);

    /**
     * Getter for property m_bHasPromoted.
     *
     * @return Value of property m_bHasPromoted.
     */
    public boolean isHasPromoted();

    /**
     * Setter for property m_iLeagueLevelUnitID.
     *
     * @param m_iLeagueLevelUnitID New value of property m_iLeagueLevelUnitID.
     */
    public void setLeagueLevelUnitID(int m_iLeagueLevelUnitID);

    /**
     * get LeagueLevelUnitID
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getLeagueLevelUnitID();

    /**
     * Setter for property m_sLogoURL.
     *
     * @param m_sLogoURL New value of property m_sLogoURL.
     */
    public void setLogoURL(java.lang.String m_sLogoURL);

    /**
     * Getter for property m_sLogoURL.
     *
     * @return Value of property m_sLogoURL.
     */
    public java.lang.String getLogoURL();

    /**
     * Setter for property m_clSeriesMatchDate.
     *
     * @param m_clSeriesMatchDate New value of property m_clSeriesMatchDate.
     */
    public void setSeriesMatchDate(java.sql.Timestamp m_clSeriesMatchDate);

    /**
     * Getter for property m_clSeriesMatchDate.
     *
     * @return Value of property m_clSeriesMatchDate.
     */
    public java.sql.Timestamp getSeriesMatchDate();

    /**
     * Setter for property m_clTrainingDate.
     *
     * @param m_clTrainingDate New value of property m_clTrainingDate.
     */
    public void setTrainingDate(java.sql.Timestamp m_clTrainingDate);

    /**
     * Getter for property m_clTrainingDate.
     *
     * @return Value of property m_clTrainingDate.
     */
    public java.sql.Timestamp getTrainingDate();
}
