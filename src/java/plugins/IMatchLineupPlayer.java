// %2021912268:plugins%
/*
 * IMatchLineupPlayer.java
 *
 * Created on 18. Oktober 2004, 07:50
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface IMatchLineupPlayer {
    //~ Static fields/initializers -----------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////
    //Erweiterete ID's
    //////////////////////////////////////////////////////////////////////////////////

    /** TODO Missing Parameter Documentation */

    //	Set Pieces.	
    public static final int STANDARDS = 17;

    /** TODO Missing Parameter Documentation */

    //	Captain.	
    public static final int CAPITAN_ROLEID = 18;

    /** TODO Missing Parameter Documentation */

    //	Replaced player 1.	
    public static final int EINWECHSEL1 = 19;

    /** TODO Missing Parameter Documentation */

    //	Replaced player 2.	
    public static final int EINWECHSEL2 = 20;

    /** TODO Missing Parameter Documentation */

    //	Replaced player 3.
    public static final int EINWECHSEL3 = 21;

    /** TODO Missing Parameter Documentation */

    //	Status = 1
    public static final int SENT_OFF = 1;

    /** TODO Missing Parameter Documentation */

    //	Status=2
    public static final int SUBSTITUTED = 2;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Getter for property m_iFieldPos.
     *
     * @return Value of property m_iFieldPos.
     */
    public int getFieldPos();
    
    /**
     * Setter for property m_iFieldPos.
     *
     * @param pos Value of property m_iFieldPos.
     */
    public void setFieldPos(int pos);

    //Wrapper for ISpielerPosition

    /**
     * Getter for property m_iId.
     *
     * @return Value of property m_iId.
     */
    public int getId();

    /**
     * Getter for property m_sNickName.
     *
     * @return Value of property m_sNickName.
     */
    public java.lang.String getNickName();

    /**
     * get Position Code
     *
     * @return TODO Missing Return Method Documentation
     */
    public byte getPosition();

    /**
     * Getter for property m_iPositionCode.
     *
     * @return Value of property m_iPositionCode.
     */
    public int getPositionCode();

    /////////////////////////////////////////////////////////////////////////////////
    //ACCESSOR
    //////////////////////////////////////////////////////////////////////////////////    

    /**
     * Getter for property m_dRating.
     *
     * @return Value of property m_dRating.
     */
    public double getRating();
    
    /**
    * Getter for property m_dRatingStarsEndOfMatch.
    *
    * @return Value of property m_dRatingStarsEndOfMatch.
    */
   public double getRatingStarsEndOfMatch();

    //public int compareTo (Object obj);

    /**
     * liefert eine ID nach der Sortiert werden kann ( z.B. Spierlübersichtstabelle
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getSortId();

    /**
     * Getter for property m_iSpielerId.
     *
     * @return Value of property m_iSpielerId.
     */
    public int getSpielerId();

    /**
     * Getter for property m_sSpielerName.
     *
     * @return Value of property m_sSpielerName.
     */
    public java.lang.String getSpielerName();

    /**
     * Getter for property m_sSpielerVName.
     *
     * @return Value of property m_sSpielerVName.
     */
    public java.lang.String getSpielerVName();

    /**
     * Getter for property m_iStatus.
     *
     * @return Value of property m_iStatus.
     */
    public int getStatus();

    /**
     * Getter for property m_bTaktik.
     *
     * @return Value of property m_bTaktik.
     */
    public byte getTaktik();
}
