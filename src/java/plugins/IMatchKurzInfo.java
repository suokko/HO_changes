// %1127326956275:plugins%
/*
 * IMatchKurzInfo.java
 *
 * Created on 18. Oktober 2004, 07:04
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface IMatchKurzInfo extends Comparable {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final int FINISHED = 1;

    /** TODO Missing Parameter Documentation */
    public static final int UPCOMING = 2;

    /** TODO Missing Parameter Documentation */
    public static final int ONGOING = 3;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Did user set a lineup for this match ?
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isAufstellung();

    /**
     * Getter for property Away ID
     *
     * @return Value of property m_iGastID.
     */
    public int getGastID();

    /**
     * Getter for property Away Name
     *
     * @return Value of property m_sGastName.
     */
    public java.lang.String getGastName();

    /**
     * Getter for property Away Goals
     *
     * @return Value of property m_iGastTore.
     */
    public int getGastTore();

    /**
     * Getter for property m_iHeimID.
     *
     * @return Value of property m_iHeimID.
     */
    public int getHeimID();

    /**
     * Getter for property m_sHeimName.
     *
     * @return Value of property m_sHeimName.
     */
    public java.lang.String getHeimName();

    /**
     * Getter for property #Home Goals.
     *
     * @return Value of property m_iHeimTore.
     */
    public int getHeimTore();

    /**
     * Getter for property m_sMatchDate.
     *
     * @return Value of property m_sMatchDate.
     */
    public java.lang.String getMatchDate();

    /**
     * Getter for property m_lDatum.
     *
     * @return Value of property m_lDatum.
     */
    public java.sql.Timestamp getMatchDateAsTimestamp();

    /**
     * Getter for property m_iMatchID.
     *
     * @return Value of property m_iMatchID.
     */
    public int getMatchID();

    /**
     * Getter for property m_iMatchStatus.
     *
     * @return Value of property m_iMatchStatus.
     */
    public int getMatchStatus();

    /**
     * Getter for property m_iMatchTyp.
     *
     * @return Value of property m_iMatchTyp.
     */
    public int getMatchTyp();

    /**
     * Getter for property m_lDatum.
     *
     * @return Value of property m_lDatum.
     */
    public java.sql.Timestamp getTimestampMatchDate();

    //--------------------------------------------------------------    
    //public int compareTo (Object obj); from compareable
}
