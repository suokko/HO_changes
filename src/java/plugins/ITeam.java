// %2413336229:plugins%
package plugins;

/**
 * Enthält die Daten des Teams (nicht der Spieler!); Stores data from team section of HRF ( no
 * players are stored here )
 */
public interface ITeam {
    //team spirit
    public static final int TS_paradiesisch = 10;
    public static final int TS_auf_Wolke_sieben = 9;
    public static final int TS_euphorisch = 8;
    public static final int TS_ausgezeichnet = 7;
    public static final int TS_gut = 6;
    public static final int TS_zufrieden = 5;
    public static final int TS_ruhig = 4;
    public static final int TS_irritiert = 3;
    public static final int TS_wuetend = 2;
    public static final int TS_blutruenstig = 1;
    public static final int TS_wie_im_kalten_Krieg = 0;

    //self confidence
    public static final int SV_voellig_abgehoben = 9;
    public static final int SV_voellig_uebertrieben = 8;
    public static final int SV_etwas_ueberheblich = 7;
    public static final int SV_sehr_gross = 6;
    public static final int SV_stark = 5;
    public static final int SV_bescheiden = 4;
    public static final int SV_gering = 3;
    public static final int SV_armselig = 2;
    public static final int SV_katastrophal = 1;
    public static final int SV_nichtVorhanden = 0;

    //training types
    public static final int TA_EXTERNALATTACK = 10;		// wing attacks
    public static final int TA_ABWEHRVERHALTEN = 9;	// def. positions
    public static final int TA_STEILPAESSE = 8;		// through passes
    public static final int TA_TORWART = 7;				// goalkeeping
    public static final int TA_SPIELAUFBAU = 6;			// playmaking
    public static final int TA_PASSSPIEL = 5;			// short passes
    public static final int TA_SCHUSSTRAINING = 4;		// shooting
    public static final int TA_FLANKEN = 3;				// crossing / winger
    public static final int TA_CHANCEN = 2;				// scoring
    public static final int TA_VERTEIDIGUNG = 1;		// defending
    public static final int TA_STANDARD = 0;			// set pieces
    
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_iErfahrung343.
     *
     * @param m_iErfahrung343 New value of property m_iErfahrung343.
     */
    public void setErfahrung343(int m_iErfahrung343);

    /**
     * Getter for property m_iErfahrung343.
     *
     * @return Value of property m_iErfahrung343.
     */
    public int getErfahrung343();

    /**
     * Setter for property m_iErfahrung352.
     *
     * @param m_iErfahrung352 New value of property m_iErfahrung352.
     */
    public void setErfahrung352(int m_iErfahrung352);

    /**
     * Getter for property m_iErfahrung352.
     *
     * @return Value of property m_iErfahrung352.
     */
    public int getErfahrung352();

    /**
     * Setter for property m_iErfahrung433.
     *
     * @param m_iErfahrung433 New value of property m_iErfahrung433.
     */
    public void setErfahrung433(int m_iErfahrung433);

    /**
     * Getter for property m_iErfahrung433.
     *
     * @return Value of property m_iErfahrung433.
     */
    public int getErfahrung433();

    /**
     * Setter for property m_iErfahrung451.
     *
     * @param m_iErfahrung451 New value of property m_iErfahrung451.
     */
    public void setErfahrung451(int m_iErfahrung451);

    /**
     * Getter for property m_iErfahrung451.
     *
     * @return Value of property m_iErfahrung451.
     */
    public int getErfahrung451();

    /**
     * Setter for property m_iErfahrung532.
     *
     * @param m_iErfahrung532 New value of property m_iErfahrung532.
     */
    public void setErfahrung532(int m_iErfahrung532);

    /**
     * Getter for property m_iErfahrung532.
     *
     * @return Value of property m_iErfahrung532.
     */
    public int getErfahrung532();

    /**
     * Setter for property m_iErfahrung541.
     *
     * @param m_iErfahrung541 New value of property m_iErfahrung541.
     */
    public void setErfahrung541(int m_iErfahrung541);

    /**
     * Getter for property m_iErfahrung541.
     *
     * @return Value of property m_iErfahrung541.
     */
    public int getErfahrung541();
    
    /**
     * Get the formation experience for the 442 system.
     */
    public int getFormationExperience442();

    /**
     * Set the formation experience for the 442 system.
     */
    public void setFormationExperience442(int formationXp442);
    
    /**
     * Get the formation experience for the 523 system.
     */
    public int getFormationExperience523();

    /**
     * Set the formation experience for the 523 system.
     */
    public void setFormationExperience523(int formationXp523);
    
    /**
     * Get the formation experience for the 550 system.
     */
    public int getFormationExperience550();

    /**
     * Set the formation experience for the 550 system.
     */
    public void setFormationExperience550(int formationXp550);

    /**
     * Get the formation experience for the 253 system.
     */
    public int getFormationExperience253();

    /**
     * Set the formation experience for the 253 system.
     */
    public void setFormationExperience253(int formationXp253);

    /**
     * Setter for property m_sSelbstvertrauen.
     *
     * @param m_sSelbstvertrauen New value of property m_sSelbstvertrauen.
     */
    public void setSelbstvertrauen(String m_sSelbstvertrauen);

    /**
     * Getter for property m_sSelbstvertrauen.
     *
     * @return Value of property m_sSelbstvertrauen.
     */
    public String getSelbstvertrauen();

    /**
     * Setter for property m_iSelbstvertrauen.
     *
     * @param m_iSelbstvertrauen New value of property m_iSelbstvertrauen.
     */
    public void setSelbstvertrauenAsInt(int m_iSelbstvertrauen);

    /**
     * Getter for property m_iSelbstvertrauen.
     *
     * @return Value of property m_iSelbstvertrauen.
     */
    public int getSelbstvertrauenAsInt();

    /**
     * Setter for property m_sStimmung.
     *
     * @param m_sStimmung New value of property m_sStimmung.
     */
    public void setStimmung(String m_sStimmung);

    /**
     * Getter for property m_sStimmung.
     *
     * @return Value of property m_sStimmung.
     */
    public String getStimmung();

    public int getSubStimmung();

    /**
     * Setter for property m_iStimmung.
     *
     * @param m_iStimmung New value of property m_iStimmung.
     */
    public void setStimmungAsInt(int m_iStimmung);

    /**
     * Get team spirit as int.
     */
    public int getStimmungAsInt();

    /**
     * Setter for property m_sTrainingsArt.
     *
     * @param m_sTrainingsArt New value of property m_sTrainingsArt.
     */
    public void setTrainingsArt(String m_sTrainingsArt);

    /**
     * Getter for property m_sTrainingsArt.
     *
     * @return Value of property m_sTrainingsArt.
     */
    public String getTrainingsArt();

    /**
     * Setter for property m_iTrainingsArt.
     *
     * @param m_iTrainingsArt New value of property m_iTrainingsArt.
     */
    public void setTrainingsArtAsInt(int m_iTrainingsArt);

    /**
     * Getter for property m_iTrainingsArt.
     *
     * @return Value of property m_iTrainingsArt.
     */
    public int getTrainingsArtAsInt();

    /**
     * Setter for property m_iTrainingslevel.
     *
     * @param m_iTrainingslevel New value of property m_iTrainingslevel.
     */
    public void setTrainingslevel(int m_iTrainingslevel);

    public void setStaminaTrainingPart(int m_iStaminaTrainingPart);

    ////////////////////////////////////////////////////////////////////////////////
    //Accessor
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter for property m_iTrainingslevel.
     *
     * @return Value of property m_iTrainingslevel.
     */
    public int getTrainingslevel();

    /**
     * Get stamina share in percent.
     */
    public int getStaminaTrainingPart();

    /**
     * Get the i18n string for a self confidence level.
     */
    public String getNameForSelfConfidence(int level);

    /**
     * Get the i18n string for a team spirit level.
     */
    public String getNameForTeamSpirit(int level);

}
