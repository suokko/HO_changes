// %272003758:de.hattrickorganizer.model.matches%
/*
 * MatchLineupPlayer.java
 *
 * Created on 20. Oktober 2003, 08:55
 */
package de.hattrickorganizer.model.matches;

import plugins.ISpielerPosition;
import de.hattrickorganizer.model.SpielerPosition;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MatchLineupPlayer extends SpielerPosition implements plugins.IMatchLineupPlayer {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -5986419471284091148L;

	/** TODO Missing Parameter Documentation */
    private String m_sNickName = "";

    /** TODO Missing Parameter Documentation */
    private String m_sSpielerName = "";

    /** TODO Missing Parameter Documentation */
    private String m_sSpielerVName = "";

    /////////////////////////////////////////////////////////////////////////////////
    //MEMBER
    //////////////////////////////////////////////////////////////////////////////////    
    //Super.ID = RoleID
    //Super.SpielerID = SpielerID
    //Super.taktik= behaivior

    /** TODO Missing Parameter Documentation */
    private double m_dRating;
    private double m_dRatingStarsEndOfMatch;

    /** TODO Missing Parameter Documentation */
    private int m_iFieldPos;

    /** TODO Missing Parameter Documentation */
    private int m_iPositionCode;

    /** TODO Missing Parameter Documentation */
    private int m_iStatus;

    //~ Constructors -------------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////
    //KONSTRUKTOR
    //////////////////////////////////////////////////////////////////////////////////    

    /**
     * Creates a new instance of MatchLineupPlayer
     *
     * @param roleID TODO Missing Constructuor Parameter Documentation
     * @param behaivior TODO Missing Constructuor Parameter Documentation
     * @param spielerID TODO Missing Constructuor Parameter Documentation
     * @param rating TODO Missing Constructuor Parameter Documentation
     * @param name TODO Missing Constructuor Parameter Documentation
     * @param positionsCode TODO Missing Constructuor Parameter Documentation
     * @param status TODO Missing Constructuor Parameter Documentation
     * @param fieldPos TODO Missing Constructuor Parameter Documentation
     */
    public MatchLineupPlayer(int roleID, int behavior, int spielerID, double rating, String name,
                             int status) {
        super(roleID, spielerID, (byte) behavior);

        //erst mit neuer Version Namen aufsplitten
        //setName( name );
        m_sSpielerName = name;
        m_dRating = rating;
        m_iPositionCode = roleID;
        m_iStatus = status;
        m_iFieldPos = roleID; // Why the two positions? Also the same input before 553 change.
    }

    /**
     * Creates a new instance of MatchLineupPlayer
     *
     * @param roleID TODO Missing Constructuor Parameter Documentation
     * @param behaivior TODO Missing Constructuor Parameter Documentation
     * @param spielerID TODO Missing Constructuor Parameter Documentation
     * @param rating TODO Missing Constructuor Parameter Documentation
     * @param vname TODO Missing Constructuor Parameter Documentation
     * @param nickName TODO Missing Constructuor Parameter Documentation
     * @param name TODO Missing Constructuor Parameter Documentation
     * @param positionsCode TODO Missing Constructuor Parameter Documentation
     * @param status TODO Missing Constructuor Parameter Documentation
     * @param fieldPos TODO Missing Constructuor Parameter Documentation
     */
    public MatchLineupPlayer(int roleID, int behaivior, int spielerID, double rating, String vname,
                             String nickName, String name, int status) {
        super(roleID, spielerID, (byte) behaivior);

        m_sSpielerName = name;
        m_sNickName = nickName;
        m_sSpielerVName = vname;
        m_dRating = rating;
        m_iPositionCode = roleID;
        m_iStatus = status;
        m_iFieldPos = roleID;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_iFieldPos.
     *
     * @param m_iFieldPos New value of property m_iFieldPos.
     */
    public final void setFieldPos(int m_iFieldPos) {
        this.m_iFieldPos = m_iFieldPos;
    }

    /**
     * Getter for property m_iFieldPos.
     *
     * @return Value of property m_iFieldPos.
     */
    public final int getFieldPos() {
        return m_iFieldPos;
    }

    /**
     * Setter for property m_sNickName.
     *
     * @param m_sNickName New value of property m_sNickName.
     */
    public final void setNickName(java.lang.String m_sNickName) {
        this.m_sNickName = m_sNickName;
    }

    /**
     * Getter for property m_sNickName.
     *
     * @return Value of property m_sNickName.
     */
    public final java.lang.String getNickName() {
        return m_sNickName;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Overwrite
    ////////////////////////////////////////////////////////////////////////////////    
    @Override
	public final byte getPosition() {
        byte ret = super.getPosition();

        ///wenn pos nicht bestimmt werden kann dann die roleID zurückwerfen
        if (ret == ISpielerPosition.UNKNOWN) {
            //m_iId;
            ret = (byte) getId();
        }

        return ret;
    }

    /**
     * Setter for property m_iPositionCode.
     *
     * @param m_iPositionCode New value of property m_iPositionCode.
     */
    public final void setPositionCode(int m_iPositionCode) {
        this.m_iPositionCode = m_iPositionCode;
    }

    /**
     * Getter for property m_iPositionCode.
     *
     * @return Value of property m_iPositionCode.
     */
    public final int getPositionCode() {
        return m_iPositionCode;
    }

    /**
     * Setter for property m_dRating.
     *
     * @param m_dRating New value of property m_dRating.
     */
    public final void setRating(double m_dRating) {
        this.m_dRating = m_dRating;
    }
    
    /**
     * Setter for property m_dRatingStarsEndOfMatch.
     *
     * @param m_dRating New value of property m_dRatingStarsEndOfMatch.
     */
    public final void setRatingStarsEndOfMatch(double m_dRatingStarsEndOfMatch) {
        this.m_dRatingStarsEndOfMatch = m_dRatingStarsEndOfMatch;
    }
    

    /////////////////////////////////////////////////////////////////////////////////
    //ACCESSOR
    //////////////////////////////////////////////////////////////////////////////////    

    /**
     * Getter for property RatingStarsEndOfMatch.
     *
     * @return Value of property RatingStarsEndOfMatch.
     */
    public final double getRatingStarsEndOfMatch() {
        return m_dRatingStarsEndOfMatch;
    }
    
    /**
     * Getter for property m_dRating.
     *
     * @return Value of property m_dRating.
     */
    public final double getRating() {
        return m_dRating;
    }

    /**
     * Setter for property m_sSpielerName.
     *
     * @param m_sSpielerName New value of property m_sSpielerName.
     */
    public final void setSpielerName(java.lang.String m_sSpielerName) {
        this.m_sSpielerName = m_sSpielerName;
    }

    /**
     * Getter for property m_sSpielerName.
     *
     * @return Value of property m_sSpielerName.
     */
    public final java.lang.String getSpielerName() {
        return m_sSpielerName;
    }

    /**
     * Setter for property m_sSpielerVName.
     *
     * @param m_sSpielerVName New value of property m_sSpielerVName.
     */
    public final void setSpielerVName(java.lang.String m_sSpielerVName) {
        this.m_sSpielerVName = m_sSpielerVName;
    }

    /**
     * Getter for property m_sSpielerVName.
     *
     * @return Value of property m_sSpielerVName.
     */
    public final java.lang.String getSpielerVName() {
        return m_sSpielerVName;
    }

    /**
     * Setter for property m_iStatus.
     *
     * @param m_iStatus New value of property m_iStatus.
     */
    public final void setStatus(int m_iStatus) {
        this.m_iStatus = m_iStatus;
    }

    /**
     * Getter for property m_iStatus.
     *
     * @return Value of property m_iStatus.
     */
    public final int getStatus() {
        return m_iStatus;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Helper
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * bricht den Namen auf
     *
     * @param name TODO Missing Constructuor Parameter Documentation
     */
    protected final void setName(String name) {
        final int index = name.indexOf("  ");

        if (index > -1) {
            m_sSpielerVName = name.substring(0, index);
            m_sSpielerName = name.substring(index + 2);
        }
    }
}
