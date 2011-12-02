// %3080703537:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import de.hattrickorganizer.tools.HOLogger;

/**
 * Hält die Daten für ein Spiel für die Arenastatistik
 */
public class ArenaStatistikModel {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Name des Teams zu dem die Matchinfo gehört */
    protected String m_sGastName = "";

    /** Name des Teams zu dem die Matchinfo gehört */
    protected String m_sHeimName = "";

    /** Datum des spiels */
    protected String m_sMatchDate = "";

    /** Gast Tore */
    protected int m_iGastTore = -1;

    /** Heim Tore */
    protected int m_iHeimTore = -1;

    /** ID des MAtches */
    protected int m_iMatchID = -1;

    /** Status des Spiels */
    protected int m_iMatchStatus = -1;

    /** Typ des Spiels */
    protected int m_iMatchTyp = -1;
    private int m_iArenaGroesse;
    private int m_iFanZufriedenheit;
    private int m_iFans;
    private int m_iLigaPlatz;
    private int m_iWetter;
    private int m_iZuschaueranzahl;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_iArenaGroesse.
     *
     * @param m_iArenaGroesse New value of property m_iArenaGroesse.
     */
    public final void setArenaGroesse(int m_iArenaGroesse) {
        this.m_iArenaGroesse = m_iArenaGroesse;
    }

    /**
     * Getter for property m_iArenaGroesse.
     *
     * @return Value of property m_iArenaGroesse.
     */
    public final int getArenaGroesse() {
        return m_iArenaGroesse;
    }

    /**
     * Setter for property m_iFanZufriedenheit.
     *
     * @param m_iFanZufriedenheit New value of property m_iFanZufriedenheit.
     */
    public final void setFanZufriedenheit(int m_iFanZufriedenheit) {
        this.m_iFanZufriedenheit = m_iFanZufriedenheit;
    }

    /**
     * Getter for property m_iFanZufriedenheit.
     *
     * @return Value of property m_iFanZufriedenheit.
     */
    public final int getFanZufriedenheit() {
        return m_iFanZufriedenheit;
    }

    /**
     * Setter for property m_iFans.
     *
     * @param m_iFans New value of property m_iFans.
     */
    public final void setFans(int m_iFans) {
        this.m_iFans = m_iFans;
    }

    /**
     * Getter for property m_iFans.
     *
     * @return Value of property m_iFans.
     */
    public final int getFans() {
        return m_iFans;
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
     * Setter for property m_iLigaPlatz.
     *
     * @param m_iLigaPlatz New value of property m_iLigaPlatz.
     */
    public final void setLigaPlatz(int m_iLigaPlatz) {
        this.m_iLigaPlatz = m_iLigaPlatz;
    }

    /**
     * Getter for property m_iLigaPlatz.
     *
     * @return Value of property m_iLigaPlatz.
     */
    public final int getLigaPlatz() {
        return m_iLigaPlatz;
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
    public final Timestamp getMatchDateAsTimestamp() {
        try {
            //Hattrick
            final java.text.SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMANY);
            return new Timestamp(simpleFormat.parse(m_sMatchDate).getTime());
        } catch (Exception e) {
            try {
                //Hattrick
                final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMANY);
                return new Timestamp(simpleFormat.parse(m_sMatchDate).getTime());
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
     */
    public final Timestamp getTimestampMatchDate() {
        try {
            //Hattrick
            final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMANY);
            return new Timestamp(simpleFormat.parse(m_sMatchDate).getTime());
        } catch (Exception e) {
            try {
                //Hattrick
                final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMANY);
                return new Timestamp(simpleFormat.parse(m_sMatchDate).getTime());
            } catch (Exception ex) {
                HOLogger.instance().log(getClass(),ex);
            }
        }
        return null;
    }

    /**
     * Setter for property m_iWetter.
     *
     * @param m_iWetter New value of property m_iWetter.
     */
    public final void setWetter(int m_iWetter) {
        this.m_iWetter = m_iWetter;
    }

    /**
     * Getter for property m_iWetter.
     *
     * @return Value of property m_iWetter.
     */
    public final int getWetter() {
        return m_iWetter;
    }

    /**
     * Setter for property m_iZuschaueranzahl.
     *
     * @param m_iZuschaueranzahl New value of property m_iZuschaueranzahl.
     */
    public final void setZuschaueranzahl(int m_iZuschaueranzahl) {
        this.m_iZuschaueranzahl = m_iZuschaueranzahl;
    }

    /**
     * Getter for property m_iZuschaueranzahl.
     *
     * @return Value of property m_iZuschaueranzahl.
     */
    public final int getZuschaueranzahl() {
        return m_iZuschaueranzahl;
    }

    //--------------------------------------------------------------
    public final int compareTo(Object obj) {
        if (obj instanceof ArenaStatistikModel) {
            final ArenaStatistikModel info = (ArenaStatistikModel) obj;

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