// %3404360902:plugins%
/*
 * IMatchLineup.java
 *
 * Created on 18. Oktober 2004, 08:17
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface IMatchLineup {
    //~ Static fields/initializers -----------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //STATIC
    ////////////////////////////////////////////////////////////////////////////////

    /** TODO Missing Parameter Documentation */

    //	League match.	
    public static final int LIGASPIEL = 1;

    /** TODO Missing Parameter Documentation */

    //	Qualification match.	
    public static final int QUALISPIEL = 2;

    /** TODO Missing Parameter Documentation */

    //	Cup match (standard league cup).	
    public static final int POKALSPIEL = 3;

    /** TODO Missing Parameter Documentation */

    //	Friendly (normal rules).	
    public static final int TESTSPIEL = 4;

    /** TODO Missing Parameter Documentation */

    //	Friendly (cup rules).	
    public static final int TESTPOKALSPIEL = 5;

    /** TODO Missing Parameter Documentation */

    //	Not currently in use, but reserved for international competition matches with normal rules (may or may not be implemented at some future point).	
    public static final int INTSPIEL = 6;

    /** TODO Missing Parameter Documentation */

    //	Not currently in use, but reserved for international competition matches with cup rules (may or may not be implemented at some future point).	
    public static final int INTCUPSPIEL = 7;

    /** TODO Missing Parameter Documentation */

    //	International friendly (normal rules).	
    public static final int INT_TESTSPIEL = 8;

    /** TODO Missing Parameter Documentation */

    //	International friendly (cup rules).	
    public static final int INT_TESTCUPSPIEL = 9;

    /** TODO Missing Parameter Documentation */

    //	National teams competition match (normal rules).	
    public static final int LAENDERSPIEL = 10;

    /** TODO Missing Parameter Documentation */

    //	National teams competition match (cup rules).	
    public static final int LAENDERCUPSPIEL = 11;

    /** TODO Missing Parameter Documentation */

    //	National teams friendly.
    public static final int TESTLAENDERSPIEL = 12;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Getter for property m_iArenaID.
     *
     * @return Value of property m_iArenaID.
     */
    public int getArenaID();

    /**
     * Getter for property m_sArenaName.
     *
     * @return Value of property m_sArenaName.
     */
    public java.lang.String getArenaName();

    ///////////////////////////////////////////////////////////////////////////////////
    //Accessor
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter for property m_lDatum.
     *
     * @return Value of property m_lDatum.
     */
    public java.sql.Timestamp getFetchDatum();

    /**
     * Getter for property m_clGast.
     *
     * @return Value of property m_clGast.
     */
    public IMatchLineupTeam getGast();

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
     * Getter for property m_clHeim.
     *
     * @return Value of property m_clHeim.
     */
    public IMatchLineupTeam getHeim();

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
     * Getter for property m_iMatchID.
     *
     * @return Value of property m_iMatchID.
     */
    public int getMatchID();

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
    public java.sql.Timestamp getSpielDatum();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getStringFetchDate();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getStringSpielDate();
}
