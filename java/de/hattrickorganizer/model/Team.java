// %233313029:de.hattrickorganizer.model%
package de.hattrickorganizer.model;

import de.hattrickorganizer.tools.HRFFileParser;

/**
 * Enth�lt die Daten des Teams (nicht der Spieler!)
 */
public final class Team implements plugins.ITeam {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    private String m_sSelbstvertrauen = "";

    /** TODO Missing Parameter Documentation */
    private String m_sStimmung = "";

    /** TODO Missing Parameter Documentation */
    private String m_sTrainingsArt = "";

    /** Erfahrung 343 */
    private int m_iErfahrung343;

    /** Erfahrung 352 */
    private int m_iErfahrung352;

    /** Erfahrung 433 */
    private int m_iErfahrung433;

    /** Erfahrung 451 */
    private int m_iErfahrung451;

    /** Erfahrung 532 */
    private int m_iErfahrung532;

    /** Erfahrung 541 */
    private int m_iErfahrung541;

    /** Selbstvertrauen */
    private int m_iSelbstvertrauen;

    /** Stimmung */
    private int m_iStimmungInt;

    private int subStimmung;

    /** TrainingsArt */
    private int m_iTrainingsArt;

    ////////////////////////////////////////////////////////////////////////////////
    //Member
    ////////////////////////////////////////////////////////////////////////////////

    /** Trainingsintensit�t */
    private int m_iTrainingslevel;

    private int m_iStaminaTrainingPart;

    //~ Constructors -------------------------------------------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    ////////////////////////////////////////////////////////////////////////////////
    public Team(java.util.Properties properties) throws Exception {
        m_iTrainingslevel = Integer.parseInt(properties.getProperty("trlevel", "0"));
        m_iStaminaTrainingPart = Integer.parseInt(properties.getProperty("staminatrainingpart", "0"));
        m_sTrainingsArt = properties.getProperty("trtype", "");
		m_iStimmungInt = Integer.parseInt(properties.getProperty("stamningvalue", "0"));
        m_sStimmung = properties.getProperty("stamning", "");
        m_sSelbstvertrauen = properties.getProperty("sjalvfortroende", "");
        m_iSelbstvertrauen = Integer.parseInt(properties.getProperty("sjalvfortroendevalue", "0"));
        m_iErfahrung433 = Integer.parseInt(properties.getProperty("exper433", "0"));
        m_iErfahrung451 = Integer.parseInt(properties.getProperty("exper451", "0"));
        m_iErfahrung352 = Integer.parseInt(properties.getProperty("exper352", "0"));
        m_iErfahrung532 = Integer.parseInt(properties.getProperty("exper532", "0"));
        m_iErfahrung343 = Integer.parseInt(properties.getProperty("exper343", "0"));
        m_iErfahrung541 = Integer.parseInt(properties.getProperty("exper541", "0"));
        m_iTrainingsArt = Integer.parseInt(properties.getProperty("trtypevalue", "-1"));
		subStimmung = 2;
    }

    /**
     * Creates a new Team object.
     *
     * @param rs TODO Missing Constructuor Parameter Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    public Team(java.sql.ResultSet rs) throws Exception {
        m_iTrainingslevel = rs.getInt("TrainingsIntensitaet");
        m_iStaminaTrainingPart = rs.getInt("StaminaTrainingPart");
        m_sTrainingsArt = de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("sTrainingsArt"));
        m_sStimmung = de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("sStimmung"));
        m_sSelbstvertrauen = de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("sSelbstvertrauen"));
        m_iSelbstvertrauen = rs.getInt("iSelbstvertrauen");
		m_iStimmungInt = rs.getInt("iStimmung");
        m_iErfahrung433 = rs.getInt("iErfahrung433");
        m_iErfahrung451 = rs.getInt("iErfahrung451");
        m_iErfahrung352 = rs.getInt("iErfahrung352");
        m_iErfahrung532 = rs.getInt("iErfahrung532");
        m_iErfahrung343 = rs.getInt("iErfahrung343");
        m_iErfahrung541 = rs.getInt("iErfahrung541");
        m_iTrainingsArt = rs.getInt("TrainingsArt");
		subStimmung = 2;
    }

    //~ Methods ------------------------------------------------------------------------------------

    public String getNameForSelfConfidence(int level) {
    	return getNameForSelbstvertrauen(level);
    }

    public String getNameForTeamSpirit(int level) {
    	return getNameForStimmung(level);
    }

    /**
     * Gibt den Namen zu einer Bewertungzur�ck
     *
     * @param level TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForSelbstvertrauen(int level) {
        switch (level) {
            case SV_voellig_abgehoben:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("extremely_exaggerated");

            case SV_voellig_uebertrieben:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("completely_exaggerated");

            case SV_etwas_ueberheblich:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("slightly_exaggerated");

            case SV_sehr_gross:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("wonderful");

            case SV_stark:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("strong");

            case SV_bescheiden:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("decent");

            case SV_gering:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("poor");

            case SV_armselig:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("wretched");

            case SV_katastrophal:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("disastrous");

            case SV_nichtVorhanden:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("nonexisting");

            default:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Unbestimmt");
        }
    }

    /**
     * Gibt den Namen zu einer Bewertungzur�ck
     *
     * @param level TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForStimmung(int level) {
        switch (level) {
            case TS_paradiesisch:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_Paradise_on_Earth");

            case TS_auf_Wolke_sieben:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_Walking_on_Clouds");

            case TS_euphorisch:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_delirious");

            case TS_ausgezeichnet:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_satisfied");

            case TS_gut:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_content");

            case TS_zufrieden:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_calm");

            case TS_ruhig:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_composed");

            case TS_irritiert:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_irritated");

            case TS_wuetend:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_furious");

            case TS_blutruenstig:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_murderous");

            case TS_wie_im_kalten_Krieg:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_like_the_cold_War");

            default: {
                if (level > TS_paradiesisch) {
                    return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ts_Paradise_on_Earth");
                }

                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Unbestimmt");
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Static
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Gibt den Namen zu einer Bewertungzur�ck
     *
     * @param type TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForTraining(int type) {
        switch (type) {
            case TA_TORWART:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Torwart");

            case TA_SPIELAUFBAU:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Spielaufbau");

            case TA_PASSSPIEL:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Passpiel");

            case TA_SCHUSSTRAINING:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Torschuss_Training");

            case TA_FLANKEN:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fluegelspiel");

            case TA_CHANCEN:

                //Fehlt
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Chancenverwertung");

            case TA_VERTEIDIGUNG:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Verteidigung");

            case TA_STANDARD:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Standards");

            case TA_KONDITION:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Kondition");

            case TA_ALLGEMEIN:

                //Fehlt
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Allgemein");

            default:
                return de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Unbestimmt");
        }
    }

    /**
     * Setter for property m_iErfahrung343.
     *
     * @param m_iErfahrung343 New value of property m_iErfahrung343.
     */
    public void setErfahrung343(int m_iErfahrung343) {
        this.m_iErfahrung343 = m_iErfahrung343;
    }

    /**
     * Getter for property m_iErfahrung343.
     *
     * @return Value of property m_iErfahrung343.
     */
    public int getErfahrung343() {
        return m_iErfahrung343;
    }

    /**
     * Setter for property m_iErfahrung352.
     *
     * @param m_iErfahrung352 New value of property m_iErfahrung352.
     */
    public void setErfahrung352(int m_iErfahrung352) {
        this.m_iErfahrung352 = m_iErfahrung352;
    }

    /**
     * Getter for property m_iErfahrung352.
     *
     * @return Value of property m_iErfahrung352.
     */
    public int getErfahrung352() {
        return m_iErfahrung352;
    }

    /**
     * Setter for property m_iErfahrung433.
     *
     * @param m_iErfahrung433 New value of property m_iErfahrung433.
     */
    public void setErfahrung433(int m_iErfahrung433) {
        this.m_iErfahrung433 = m_iErfahrung433;
    }

    /**
     * Getter for property m_iErfahrung433.
     *
     * @return Value of property m_iErfahrung433.
     */
    public int getErfahrung433() {
        return m_iErfahrung433;
    }

    /**
     * Setter for property m_iErfahrung451.
     *
     * @param m_iErfahrung451 New value of property m_iErfahrung451.
     */
    public void setErfahrung451(int m_iErfahrung451) {
        this.m_iErfahrung451 = m_iErfahrung451;
    }

    /**
     * Getter for property m_iErfahrung451.
     *
     * @return Value of property m_iErfahrung451.
     */
    public int getErfahrung451() {
        return m_iErfahrung451;
    }

    /**
     * Setter for property m_iErfahrung532.
     *
     * @param m_iErfahrung532 New value of property m_iErfahrung532.
     */
    public void setErfahrung532(int m_iErfahrung532) {
        this.m_iErfahrung532 = m_iErfahrung532;
    }

    /**
     * Getter for property m_iErfahrung532.
     *
     * @return Value of property m_iErfahrung532.
     */
    public int getErfahrung532() {
        return m_iErfahrung532;
    }

    /**
     * Setter for property m_iErfahrung541.
     *
     * @param m_iErfahrung541 New value of property m_iErfahrung541.
     */
    public void setErfahrung541(int m_iErfahrung541) {
        this.m_iErfahrung541 = m_iErfahrung541;
    }

    /**
     * Getter for property m_iErfahrung541.
     *
     * @return Value of property m_iErfahrung541.
     */
    public int getErfahrung541() {
        return m_iErfahrung541;
    }

    /**
     * Setter for property m_sSelbstvertrauen.
     *
     * @param m_sSelbstvertrauen New value of property m_sSelbstvertrauen.
     */
    public void setSelbstvertrauen(java.lang.String m_sSelbstvertrauen) {
        this.m_sSelbstvertrauen = m_sSelbstvertrauen;
    }

    /**
     * Getter for property m_sSelbstvertrauen.
     *
     * @return Value of property m_sSelbstvertrauen.
     */
    public java.lang.String getSelbstvertrauen() {
        return m_sSelbstvertrauen;
    }

    /**
     * Setter for property m_iSelbstvertrauen.
     *
     * @param m_iSelbstvertrauen New value of property m_iSelbstvertrauen.
     */
    public void setSelbstvertrauenAsInt(int m_iSelbstvertrauen) {
        this.m_iSelbstvertrauen = m_iSelbstvertrauen;
    }

    /**
     * Getter for property m_iSelbstvertrauen.
     *
     * @return Value of property m_iSelbstvertrauen.
     */
    public int getSelbstvertrauenAsInt() {
        return m_iSelbstvertrauen;
    }

    /**
     * Setter for property m_sStimmung.
     *
     * @param m_sStimmung New value of property m_sStimmung.
     */
    public void setStimmung(java.lang.String m_sStimmung) {
        this.m_sStimmung = m_sStimmung;
    }

    /**
     * Getter for property m_sStimmung.
     *
     * @return Value of property m_sStimmung.
     */
    public java.lang.String getStimmung() {
        return m_sStimmung;
    }

    /**
     * Setter for property m_iStimmung.
     *
     * @param m_iStimmung New value of property m_iStimmung.
     */
    public void setStimmungAsInt(int m_iStimmung) {
        this.m_iStimmungInt = m_iStimmung;
    }

    /**
     * Getter for property m_iStimmung.
     *
     * @return Value of property m_iStimmung.
     */
    public int getStimmungAsInt() {
        return m_iStimmungInt;
    }

    /**
     * Setter for property m_sTrainingsArt.
     *
     * @param m_sTrainingsArt New value of property m_sTrainingsArt.
     */
    public void setTrainingsArt(java.lang.String m_sTrainingsArt) {
        this.m_sTrainingsArt = m_sTrainingsArt;
    }

    /**
     * Getter for property m_sTrainingsArt.
     *
     * @return Value of property m_sTrainingsArt.
     */
    public java.lang.String getTrainingsArt() {
        return m_sTrainingsArt;
    }

    /**
     * Setter for property m_iTrainingsArt.
     *
     * @param m_iTrainingsArt New value of property m_iTrainingsArt.
     */
    public void setTrainingsArtAsInt(int m_iTrainingsArt) {
        this.m_iTrainingsArt = m_iTrainingsArt;
    }

    /**
     * Getter for property m_iTrainingsArt.
     *
     * @return Value of property m_iTrainingsArt.
     */
    public int getTrainingsArtAsInt() {
        return m_iTrainingsArt;
    }

    /**
     * Setter for property m_iTrainingslevel.
     *
     * @param m_iTrainingslevel New value of property m_iTrainingslevel.
     */
    public void setTrainingslevel(int m_iTrainingslevel) {
        this.m_iTrainingslevel = m_iTrainingslevel;
    }

    public void setStaminaTrainingPart(int m_iStaminaTrainingPart) {
        this.m_iStaminaTrainingPart = m_iStaminaTrainingPart;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Accessor
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter for property m_iTrainingslevel.
     *
     * @return Value of property m_iTrainingslevel.
     */
    public int getTrainingslevel() {
        return m_iTrainingslevel;
    }
    public int getStaminaTrainingPart() {
        return m_iStaminaTrainingPart;
    }
	public int getSubStimmung() {
		return subStimmung;
	}

	public void setSubStimmung(int i) {
		subStimmung = i;
	}

}
