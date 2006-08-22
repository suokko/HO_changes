// %834608826:de.hattrickorganizer.model.matches%
/*
 * MatchLineup.java
 *
 * Created on 20. Oktober 2003, 08:47
 */
package de.hattrickorganizer.model.matches;

import plugins.IMatchLineupTeam;
import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MatchLineup implements plugins.IMatchLineup {
    //~ Instance fields ----------------------------------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////    
    //MEMBER
    ////////////////////////////////////////////////////////////////////////////////

    /** TODO Missing Parameter Documentation */
    protected int m_iHeimId = -1;

    /** TODO Missing Parameter Documentation */
    protected int m_iMatchID = -1;

    /** TODO Missing Parameter Documentation */
    protected int m_iMatchTyp = -1;

    /** TODO Missing Parameter Documentation */
    MatchLineupTeam m_clGast;

    /** TODO Missing Parameter Documentation */
    MatchLineupTeam m_clHeim;

    /** TODO Missing Parameter Documentation */
    private String m_sArenaName = "";

    /** TODO Missing Parameter Documentation */
    private String m_sFetchDatum = "";

    /** TODO Missing Parameter Documentation */
    private String m_sGastName = "";

    /** TODO Missing Parameter Documentation */
    private String m_sHeimName = "";

    /** TODO Missing Parameter Documentation */
    private String m_sSpielDatum = "";

    /** TODO Missing Parameter Documentation */
    private int m_iArenaID = -1;

    /** TODO Missing Parameter Documentation */
    private int m_iGastId = -1;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of MatchLineup
     */
    public MatchLineup() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_iArenaID.
     *
     * @param m_iArenaID New value of property m_iArenaID.
     */
    public final void setArenaID(int m_iArenaID) {
        this.m_iArenaID = m_iArenaID;
    }

    /**
     * Getter for property m_iArenaID.
     *
     * @return Value of property m_iArenaID.
     */
    public final int getArenaID() {
        return m_iArenaID;
    }

    /**
     * Setter for property m_sArenaName.
     *
     * @param m_sArenaName New value of property m_sArenaName.
     */
    public final void setArenaName(java.lang.String m_sArenaName) {
        this.m_sArenaName = m_sArenaName;
    }

    /**
     * Getter for property m_sArenaName.
     *
     * @return Value of property m_sArenaName.
     */
    public final java.lang.String getArenaName() {
        return m_sArenaName;
    }

    /**
     * Setter for property m_lDatum.
     *
     * @param date New value of property m_lDatum.
     */
    public final void setFetchDatum(String date) {
        if (date != null) {
            m_sFetchDatum = date;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //Accessor
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter for property m_lDatum.
     *
     * @return Value of property m_lDatum.
     */
    public final java.sql.Timestamp getFetchDatum() {
        try {
            //Hattrick
            final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                                                                           java.util.Locale.GERMANY);

            return new java.sql.Timestamp(simpleFormat.parse(m_sFetchDatum).getTime());
        } catch (Exception e) {
            try {
                //Hattrick
                final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd",
                                                                                               java.util.Locale.GERMANY);

                return new java.sql.Timestamp(simpleFormat.parse(m_sFetchDatum).getTime());
            } catch (Exception ex) {
                HOLogger.instance().log(getClass(),ex);
            }
        }

        return null;
    }

    /**
     * Setter for property m_clGast.
     *
     * @param m_clGast New value of property m_clGast.
     */
    public final void setGast(de.hattrickorganizer.model.matches.MatchLineupTeam m_clGast) {
        this.m_clGast = m_clGast;
    }

    /**
     * Getter for property m_clGast.
     *
     * @return Value of property m_clGast.
     */
    public final IMatchLineupTeam getGast() {
        return m_clGast;
    }

    /**
     * Setter for property m_iGastId.
     *
     * @param m_iGastId New value of property m_iGastId.
     */
    public final void setGastId(int m_iGastId) {
        this.m_iGastId = m_iGastId;
    }

    /**
     * Getter for property m_iGastId.
     *
     * @return Value of property m_iGastId.
     */
    public final int getGastId() {
        return m_iGastId;
    }

    /**
     * Setter for property m_sGastName.
     *
     * @param m_sGastName New value of property m_sGastName.
     */
    public final void setGastName(java.lang.String m_sGastName) {
        this.m_sGastName = m_sGastName;
    }

    /**
     * Getter for property m_sGastName.
     *
     * @return Value of property m_sGastName.
     */
    public final java.lang.String getGastName() {
        return m_sGastName;
    }

    /**
     * Setter for property m_clHeim.
     *
     * @param m_clHeim New value of property m_clHeim.
     */
    public final void setHeim(de.hattrickorganizer.model.matches.MatchLineupTeam m_clHeim) {
        this.m_clHeim = m_clHeim;
    }

    /**
     * Getter for property m_clHeim.
     *
     * @return Value of property m_clHeim.
     */
    public final IMatchLineupTeam getHeim() {
        return m_clHeim;
    }

    /**
     * Setter for property m_iHeimId.
     *
     * @param m_iHeimId New value of property m_iHeimId.
     */
    public final void setHeimId(int m_iHeimId) {
        this.m_iHeimId = m_iHeimId;
    }

    /**
     * Getter for property m_iHeimId.
     *
     * @return Value of property m_iHeimId.
     */
    public final int getHeimId() {
        return m_iHeimId;
    }

    /**
     * Setter for property m_sHeimName.
     *
     * @param m_sHeimName New value of property m_sHeimName.
     */
    public final void setHeimName(java.lang.String m_sHeimName) {
        this.m_sHeimName = m_sHeimName;
    }

    /**
     * Getter for property m_sHeimName.
     *
     * @return Value of property m_sHeimName.
     */
    public final java.lang.String getHeimName() {
        return m_sHeimName;
    }

    /**
     * Setter for property m_iMatchID.
     *
     * @param m_iMatchID New value of property m_iMatchID.
     */
    public final void setMatchID(int m_iMatchID) {
        this.m_iMatchID = m_iMatchID;
    }

    /**
     * Getter for property m_iMatchID.
     *
     * @return Value of property m_iMatchID.
     */
    public final int getMatchID() {
        return m_iMatchID;
    }

    /**
     * Setter for property m_iMatchTyp.
     *
     * @param m_iMatchTyp New value of property m_iMatchTyp.
     */
    public final void setMatchTyp(int m_iMatchTyp) {
        this.m_iMatchTyp = m_iMatchTyp;
    }

    /**
     * Getter for property m_iMatchTyp.
     *
     * @return Value of property m_iMatchTyp.
     */
    public final int getMatchTyp() {
        return m_iMatchTyp;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param typ TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getName4MatchTyp(int typ) {
        switch (typ) {
            case MatchLineup.LIGASPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("LigaSpiel");

            case MatchLineup.POKALSPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("PokalSpiel");

            case MatchLineup.QUALISPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("QualifikationSpiel");

            case MatchLineup.LAENDERCUPSPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("LaenderCupSpiel");

            case MatchLineup.INTCUPSPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("IntCupSpiel");

            case MatchLineup.LAENDERSPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("LaenderSpiel");

            case MatchLineup.INTSPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("IntSpiel");

            case MatchLineup.INT_TESTCUPSPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("IntFriendlyCupSpiel");

            case MatchLineup.INT_TESTSPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("IntFriendlySpiel");

            case MatchLineup.TESTLAENDERSPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("LaenderFriendlySpiel");

            case MatchLineup.TESTPOKALSPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("FriendlyCupSpiel");

            case MatchLineup.TESTSPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("FriendlySpiel");

            //Fehler?
            default:
                return "";
        }
    }

    /**
     * Setter for property m_lDatum.
     *
     * @param date New value of property m_lDatum.
     */
    public final void setSpielDatum(String date) {
        if (date != null) {
            m_sSpielDatum = date;
        }
    }

    /**
     * Getter for property m_lDatum.
     *
     * @return Value of property m_lDatum.
     */
    public final java.sql.Timestamp getSpielDatum() {
        try {
            //Hattrick
            final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                                                                           java.util.Locale.GERMANY);

            return new java.sql.Timestamp(simpleFormat.parse(m_sSpielDatum).getTime());
        } catch (Exception e) {
            try {
                //Hattrick
                final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd",
                                                                                               java.util.Locale.GERMANY);

                return new java.sql.Timestamp(simpleFormat.parse(m_sSpielDatum).getTime());
            } catch (Exception ex) {
                HOLogger.instance().log(getClass(),ex);
            }
        }

        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getStringFetchDate() {
        return m_sFetchDatum;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getStringSpielDate() {
        return m_sSpielDatum;
    }
}
