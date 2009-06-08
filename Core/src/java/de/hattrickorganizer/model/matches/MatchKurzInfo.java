// %16308149:de.hattrickorganizer.model.matches%
/*
 * MatchKurzInfo.java
 *
 * Created on 22. Oktober 2003, 09:03
 */
package de.hattrickorganizer.model.matches;

import de.hattrickorganizer.tools.HOLogger;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MatchKurzInfo implements plugins.IMatchKurzInfo {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Name des Teams zu dem die Matchinfo gehört */
    private String m_sGastName = "";

    /** Name des Teams zu dem die Matchinfo gehört */
    private String m_sHeimName = "";

    /** Datum des spiels */
    private String m_sMatchDate = "";

    /** Aufstellung erstellt */
    private boolean m_bAufstellung = true;

    /** ID des Teams zu dem die info gehört */
    private int m_iGastID = -1;

    /** Gast Tore */
    private int m_iGastTore = -1;

    /** ID des Teams zu dem die info gehört */
    private int m_iHeimID = -1;

    /** Heim Tore */
    private int m_iHeimTore = -1;

    /** ID des MAtches */
    private int m_iMatchID = -1;

    /** Status des Spiels */
    private int m_iMatchStatus = -1;

    /** Typ des Spiels */
    private int m_iMatchTyp = -1;

    //~ Constructors -------------------------------------------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////
    //Konstruktor   
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a new instance of MatchKurzInfo
     */
    public MatchKurzInfo() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_bAufstellung.
     *
     * @param m_bAufstellung New value of property m_bAufstellung.
     */
    public final void setAufstellung(boolean m_bAufstellung) {
        this.m_bAufstellung = m_bAufstellung;
    }

    /**
     * Getter for property m_bAufstellung.
     *
     * @return Value of property m_bAufstellung.
     */
    public final boolean isAufstellung() {
        return m_bAufstellung;
    }

    /**
     * Setter for property m_iGastID.
     *
     * @param m_iGastID New value of property m_iGastID.
     */
    public final void setGastID(int m_iGastID) {
        this.m_iGastID = m_iGastID;
    }

    /**
     * Getter for property m_iGastID.
     *
     * @return Value of property m_iGastID.
     */
    public final int getGastID() {
        return m_iGastID;
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
     * Setter for property m_iGastTore.
     *
     * @param m_iGastTore New value of property m_iGastTore.
     */
    public final void setGastTore(int m_iGastTore) {
        this.m_iGastTore = m_iGastTore;
    }

    /**
     * Getter for property m_iGastTore.
     *
     * @return Value of property m_iGastTore.
     */
    public final int getGastTore() {
        return m_iGastTore;
    }

    /**
     * Setter for property m_iHeimID.
     *
     * @param m_iHeimID New value of property m_iHeimID.
     */
    public final void setHeimID(int m_iHeimID) {
        this.m_iHeimID = m_iHeimID;
    }

    /**
     * Getter for property m_iHeimID.
     *
     * @return Value of property m_iHeimID.
     */
    public final int getHeimID() {
        return m_iHeimID;
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
     * Setter for property m_iHeimTore.
     *
     * @param m_iHeimTore New value of property m_iHeimTore.
     */
    public final void setHeimTore(int m_iHeimTore) {
        this.m_iHeimTore = m_iHeimTore;
    }

    /**
     * Getter for property m_iHeimTore.
     *
     * @return Value of property m_iHeimTore.
     */
    public final int getHeimTore() {
        return m_iHeimTore;
    }

    /**
     * Setter for property m_sMatchDate.
     *
     * @param m_sMatchDate New value of property m_sMatchDate.
     */
    public final void setMatchDate(java.lang.String m_sMatchDate) {
        this.m_sMatchDate = m_sMatchDate;
    }

    /**
     * Getter for property m_sMatchDate.
     *
     * @return Value of property m_sMatchDate.
     */
    public final java.lang.String getMatchDate() {
        return m_sMatchDate;
    }

    /**
     * Getter for property m_lDatum.
     *
     * @return Value of property m_lDatum.
     */
    public final java.sql.Timestamp getMatchDateAsTimestamp() {
        try {
            //Hattrick
            final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                                                                           java.util.Locale.GERMANY);

            return new java.sql.Timestamp(simpleFormat.parse(m_sMatchDate).getTime());
        } catch (Exception e) {
            try {
                //Hattrick
                final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd",
                                                                                               java.util.Locale.GERMANY);

                return new java.sql.Timestamp(simpleFormat.parse(m_sMatchDate).getTime());
            } catch (Exception ex) {
                HOLogger.instance().log(getClass(),ex);
            }
        }

        return null;
    }

    /**
     * Setter for property m_iMatchID.
     *
     * @param m_iMatchID New value of property m_iMatchID.
     */
    public final void setMatchID(int m_iMatchID) {
        this.m_iMatchID = m_iMatchID;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Accessor
    ////////////////////////////////////////////////////////////////////////////////    

    /**
     * Getter for property m_iMatchID.
     *
     * @return Value of property m_iMatchID.
     */
    public final int getMatchID() {
        return m_iMatchID;
    }

    /**
     * Setter for property m_iMatchStatus.
     *
     * @param m_iMatchStatus New value of property m_iMatchStatus.
     */
    public final void setMatchStatus(int m_iMatchStatus) {
        this.m_iMatchStatus = m_iMatchStatus;
    }

    /**
     * Getter for property m_iMatchStatus.
     *
     * @return Value of property m_iMatchStatus.
     */
    public final int getMatchStatus() {
        return m_iMatchStatus;
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
     * Getter for property m_lDatum.
     *
     * @return Value of property m_lDatum.
     * 
     * @deprecated use getMatchDateAsTimestamp()
     */
    @Deprecated
	public final java.sql.Timestamp getTimestampMatchDate() {
    	return getMatchDateAsTimestamp();
    }

    //--------------------------------------------------------------    
    public final int compareTo(Object obj) {
        if (obj instanceof MatchKurzInfo) {
            final MatchKurzInfo info = (MatchKurzInfo) obj;

            if (info.getMatchDateAsTimestamp().before(this.getMatchDateAsTimestamp())) {
                return -1;
            } else if (info.getMatchDateAsTimestamp().after(this.getMatchDateAsTimestamp())) {
                return 1;
            } else {
                return 0;
            }
        }

        return 0;
    }
}
