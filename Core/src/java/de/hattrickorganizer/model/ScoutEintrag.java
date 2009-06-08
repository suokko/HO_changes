// %2778683404:de.hattrickorganizer.model%
/*
 * ScoutEintrag.java
 *
 * Created on 26. März 2003, 13:57
 */
package de.hattrickorganizer.model;

import de.hattrickorganizer.tools.HOLogger;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class ScoutEintrag {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Info */
    protected String m_sInfo = "";

    /** Name */
    protected String m_sName = "";

    /** Deadline */
    protected java.sql.Timestamp m_clDeadline = new java.sql.Timestamp(System.currentTimeMillis());

    /** soll wecker rappeln */
    protected boolean m_bWecker;

    /** Alter */
    protected int m_iAlter = 17;

    /** Age Days */
    protected int m_iAgeDays = 0;

    /** Erfahrung */
    protected int m_iErfahrung = 0;

    /** Fluegelspiel */
    protected int m_iFluegelspiel = 0;

    /** Form */
    protected int m_iForm = 0;

    /** Kondition */
    protected int m_iKondition = 0;

    /** Marktwert */
    protected int m_iTSI = 1000;

    /** Passpiel */
    protected int m_iPasspiel = 0;

    /** PlayerID */
    protected int m_iPlayerID;

    /** Price */
    protected int m_iPrice;

    /** Speciality */
    protected int m_iSpeciality;

    /** Spielaufbau */
    protected int m_iSpielaufbau = 0;

    /** Standards */
    protected int m_iStandards = 0;

    /** Torschuss */
    protected int m_iTorschuss = 0;

    /** Torwart */
    protected int m_iTorwart = 0;

    /** Verteidigung */
    protected int m_iVerteidigung = 0;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ScoutEintrag object.
     */
    public ScoutEintrag() {
    }

    /**
     * Creates a new instance of ScoutEintrag
     *
     * @param rs TODO Missing Constructuor Parameter Documentation
     */
    public ScoutEintrag(java.sql.ResultSet rs) {
        try {
            m_iPlayerID = rs.getInt("PlayerID");
            m_sName = de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("Name"));
            m_iAlter = rs.getInt("Age");
            m_iAgeDays = rs.getInt("AgeDays");
            m_iTSI = rs.getInt("Marktwert");
            m_iSpeciality = rs.getInt("Speciality");
            m_iKondition = rs.getInt("Kondition");
            m_iErfahrung = rs.getInt("Erfahrung");
            m_iForm = rs.getInt("Form");
            m_iTorwart = rs.getInt("Torwart");
            m_iVerteidigung = rs.getInt("Verteidigung");
            m_iSpielaufbau = rs.getInt("Spielaufbau");
            m_iPasspiel = rs.getInt("Passpiel");
            m_iFluegelspiel = rs.getInt("Fluegel");
            m_iTorschuss = rs.getInt("Torschuss");
            m_iStandards = rs.getInt("Standards");
            m_iPrice = rs.getInt("Price");
            m_clDeadline = rs.getTimestamp("Deadline");
            m_bWecker = rs.getBoolean("Wecker");
            m_sInfo = de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("Info"));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"Konstruktor ScoutEintrag : " + e.toString());
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_iAlter.
     *
     * @param m_iAlter New value of property m_iAlter.
     */
    public final void setAlter(int m_iAlter) {
        this.m_iAlter = m_iAlter;
    }

    /**
     * Getter for property m_iAlter.
     *
     * @return Value of property m_iAlter.
     */
    public final int getAlter() {
        return m_iAlter;
    }

    /**
     * Setter for property m_iAgeDays.
     *
     * @param m_iAgeDays New value of property m_iAgeDays.
     */
    public final void setAgeDays(int m_iAgeDays) {
        this.m_iAgeDays = m_iAgeDays;
    }

    /**
     * Getter for property m_iAlter.
     *
     * @return Value of property m_iAlter.
     */
    public final int getAgeDays() {
        return m_iAgeDays;
    }

    /**
     * Calculates full age with days
     * 
     * @return Double value of age & agedays combined,
     * 			i.e. age + agedays/112
     */
    public double getAlterWithAgeDays() {
    	double retVal = getAlter();
    	retVal += (double)getAgeDays()/112;
    	return retVal;
    }

    /**
     * Calculates String for full age with days
     * 
     * @return String of age & agedays combined,
     * 			format is "YY.DDD"
     */
    public String getAlterWithAgeDaysAsString() {
    	// format = yy.ddd
    	String retVal = getAlter() + "." + getAgeDays();
    	return retVal;
    }

    /**
     * Setter for property m_clDeadline.
     *
     * @param m_clDeadline New value of property m_clDeadline.
     */
    public final void setDeadline(java.sql.Timestamp m_clDeadline) {
        this.m_clDeadline = m_clDeadline;
    }

    /**
     * Getter for property m_clDeadline.
     *
     * @return Value of property m_clDeadline.
     */
    public final java.sql.Timestamp getDeadline() {
        return m_clDeadline;
    }

    /**
     * Setter for property m_iErfahrung.
     *
     * @param m_iErfahrung New value of property m_iErfahrung.
     */
    public final void setErfahrung(int m_iErfahrung) {
        this.m_iErfahrung = m_iErfahrung;
    }

    /**
     * Getter for property m_iErfahrung.
     *
     * @return Value of property m_iErfahrung.
     */
    public final int getErfahrung() {
        return m_iErfahrung;
    }

    /**
     * Setter for property m_iFluegelspiel.
     *
     * @param m_iFluegelspiel New value of property m_iFluegelspiel.
     */
    public final void setFluegelspiel(int m_iFluegelspiel) {
        this.m_iFluegelspiel = m_iFluegelspiel;
    }

    /**
     * Getter for property m_iFluegelspiel.
     *
     * @return Value of property m_iFluegelspiel.
     */
    public final int getFluegelspiel() {
        return m_iFluegelspiel;
    }

    /**
     * Setter for property m_iForm.
     *
     * @param m_iForm New value of property m_iForm.
     */
    public final void setForm(int m_iForm) {
        this.m_iForm = m_iForm;
    }

    /**
     * Getter for property m_iForm.
     *
     * @return Value of property m_iForm.
     */
    public final int getForm() {
        return m_iForm;
    }

    /**
     * Setter for property m_sInfo.
     *
     * @param m_sInfo New value of property m_sInfo.
     */
    public final void setInfo(java.lang.String m_sInfo) {
        this.m_sInfo = m_sInfo;
    }

    /**
     * Getter for property m_sInfo.
     *
     * @return Value of property m_sInfo.
     */
    public final java.lang.String getInfo() {
        return m_sInfo;
    }

    /**
     * Setter for property m_iKondition.
     *
     * @param m_iKondition New value of property m_iKondition.
     */
    public final void setKondition(int m_iKondition) {
        this.m_iKondition = m_iKondition;
    }

    /**
     * Getter for property m_iKondition.
     *
     * @return Value of property m_iKondition.
     */
    public final int getKondition() {
        return m_iKondition;
    }

    /**
     * Setter for property m_iMarktwert.
     *
     * @param m_iTSI New value of property m_iMarktwert.
     */
    public final void setTSI(int m_iTSI) {
        this.m_iTSI = m_iTSI;
    }

    /**
     * Getter for property m_iMarktwert.
     *
     * @return Value of property m_iMarktwert.
     */
    public final int getTSI() {
        return m_iTSI;
    }

    /**
     * Setter for property m_sName.
     *
     * @param m_sName New value of property m_sName.
     */
    public final void setName(java.lang.String m_sName) {
        this.m_sName = m_sName;
    }

    /**
     * Getter for property m_sName.
     *
     * @return Value of property m_sName.
     */
    public final java.lang.String getName() {
        return m_sName;
    }

    /**
     * Setter for property m_iPasspiel.
     *
     * @param m_iPasspiel New value of property m_iPasspiel.
     */
    public final void setPasspiel(int m_iPasspiel) {
        this.m_iPasspiel = m_iPasspiel;
    }

    /**
     * Getter for property m_iPasspiel.
     *
     * @return Value of property m_iPasspiel.
     */
    public final int getPasspiel() {
        return m_iPasspiel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param m_iPlayerID TODO Missing Method Parameter Documentation
     */
    public final void setPlayerID(int m_iPlayerID) {
        this.m_iPlayerID = m_iPlayerID;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getPlayerID() {
        return m_iPlayerID;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param m_iPrice TODO Missing Method Parameter Documentation
     */
    public final void setPrice(int m_iPrice) {
        this.m_iPrice = m_iPrice;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getPrice() {
        return m_iPrice;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param m_iSpeciality TODO Missing Method Parameter Documentation
     */
    public final void setSpeciality(int m_iSpeciality) {
        this.m_iSpeciality = m_iSpeciality;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getSpeciality() {
        return m_iSpeciality;
    }

    /**
     * Setter for property m_iSpielaufbau.
     *
     * @param m_iSpielaufbau New value of property m_iSpielaufbau.
     */
    public final void setSpielaufbau(int m_iSpielaufbau) {
        this.m_iSpielaufbau = m_iSpielaufbau;
    }

    /**
     * Getter for property m_iSpielaufbau.
     *
     * @return Value of property m_iSpielaufbau.
     */
    public final int getSpielaufbau() {
        return m_iSpielaufbau;
    }

    /**
     * Setter for property m_iStandards.
     *
     * @param m_iStandards New value of property m_iStandards.
     */
    public final void setStandards(int m_iStandards) {
        this.m_iStandards = m_iStandards;
    }

    /**
     * Getter for property m_iStandards.
     *
     * @return Value of property m_iStandards.
     */
    public final int getStandards() {
        return m_iStandards;
    }

    /**
     * Setter for property m_iTorschuss.
     *
     * @param m_iTorschuss New value of property m_iTorschuss.
     */
    public final void setTorschuss(int m_iTorschuss) {
        this.m_iTorschuss = m_iTorschuss;
    }

    /**
     * Getter for property m_iTorschuss.
     *
     * @return Value of property m_iTorschuss.
     */
    public final int getTorschuss() {
        return m_iTorschuss;
    }

    /**
     * Setter for property m_iTorwart.
     *
     * @param m_iTorwart New value of property m_iTorwart.
     */
    public final void setTorwart(int m_iTorwart) {
        this.m_iTorwart = m_iTorwart;
    }

    /**
     * Getter for property m_iTorwart.
     *
     * @return Value of property m_iTorwart.
     */
    public final int getTorwart() {
        return m_iTorwart;
    }

    /**
     * Setter for property m_iVerteidigung.
     *
     * @param m_iVerteidigung New value of property m_iVerteidigung.
     */
    public final void setVerteidigung(int m_iVerteidigung) {
        this.m_iVerteidigung = m_iVerteidigung;
    }

    /**
     * Getter for property m_iVerteidigung.
     *
     * @return Value of property m_iVerteidigung.
     */
    public final int getVerteidigung() {
        return m_iVerteidigung;
    }

    /**
     * Setter for property m_bWecker.
     *
     * @param m_bWecker New value of property m_bWecker.
     */
    public final void setWecker(boolean m_bWecker) {
        this.m_bWecker = m_bWecker;
    }

    /**
     * Getter for property m_bWecker.
     *
     * @return Value of property m_bWecker.
     */
    public final boolean isWecker() {
        return m_bWecker;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final ScoutEintrag duplicate() {
        final ScoutEintrag eintrag = new ScoutEintrag();
        eintrag.setPlayerID(getPlayerID());
        eintrag.setName(new String(getName()));
        eintrag.setAlter(getAlter());
        eintrag.setAgeDays(getAgeDays());
        eintrag.setTSI(getTSI());
        eintrag.setSpeciality(getSpeciality());
        eintrag.setErfahrung(getErfahrung());
        eintrag.setForm(getForm());
        eintrag.setKondition(getKondition());
        eintrag.setSpielaufbau(getSpielaufbau());
        eintrag.setFluegelspiel(getFluegelspiel());
        eintrag.setTorschuss(getTorschuss());
        eintrag.setTorwart(getTorwart());
        eintrag.setPasspiel(getPasspiel());
        eintrag.setVerteidigung(getVerteidigung());
        eintrag.setStandards(getStandards());
        eintrag.setInfo(new String(getInfo()));
        eintrag.setPrice(getPrice());
        eintrag.setWecker(isWecker());
        eintrag.setDeadline(new java.sql.Timestamp(getDeadline().getTime()));

        return eintrag;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final boolean equals(Object obj) {
        if (obj instanceof ScoutEintrag) {
            return ((ScoutEintrag) obj).getPlayerID() == getPlayerID();
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final int hashCode() {
        return getPlayerID();
    }
}
