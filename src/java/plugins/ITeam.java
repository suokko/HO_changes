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
    public static final int TA_EXTERNALATTACK = 12;		// wing attacks
    public static final int TA_ABWEHRVERHALTEN = 11;	// def. positions
    public static final int TA_STEILPAESSE = 10;		// through passes
    public static final int TA_TORWART = 9;				// goalkeeping
    public static final int TA_SPIELAUFBAU = 8;			// playmaking
    public static final int TA_PASSSPIEL = 7;			// short passes
    public static final int TA_SCHUSSTRAINING = 6;		// shooting
    public static final int TA_FLANKEN = 5;				// crossing / winger
    public static final int TA_CHANCEN = 4;				// scoring
    public static final int TA_VERTEIDIGUNG = 3;		// defending
    public static final int TA_STANDARD = 2;			// set pieces
    @Deprecated
    public static final int TA_KONDITION = 1;			// stamina - deprecated
    @Deprecated
    public static final int TA_ALLGEMEIN = 0;			// general / form - deprecated

    //~ Methods ------------------------------------------------------------------------------------

	/**
	 * Sets the team's experience for the 3-4-3 formation.
	 * 
	 * @param exp the experience value
	 */
    public void setFormationExperience343(int exp);

	/**
	 * Gets the team's experience for the 3-4-3 formation.
	 * 
	 * @return the experience for the 3-4-3 formation
	 */
    public int getFormationExperience343();

	/**
	 * Sets the team's experience for the 3-5-2 formation.
	 * 
	 * @param exp the experience value
	 */
    public void setFormationExperience352(int exp);

	/**
	 * Gets the team's experience for the 3-5-2 formation.
	 * 
	 * @return the experience for the 3-5-2 formation
	 */
    public int getFormationExperience352();

	/**
	 * Sets the team's experience for the 4-3-3 formation.
	 * 
	 * @param exp the experience value
	 */
    public void setFormationExperience433(int exp);

	/**
	 * Gets the team's experience for the 4-3-3 formation.
	 * 
	 * @return the experience for the 4-3-3 formation
	 */
    public int getFormationExperience433();

	/**
	 * Sets the team's experience for the 4-5-1 formation.
	 * 
	 * @param exp the experience value
	 */
    public void setFormationExperience451(int exp);

	/**
	 * Gets the team's experience for the 4-5-1 formation.
	 * 
	 * @return the experience for the 4-5-1 formation
	 */
    public int getFormationExperience451();

	/**
	 * Sets the team's experience for the 5-3-2 formation.
	 * 
	 * @param exp the experience value
	 */
    public void setFormationExperience532(int exp);

	/**
	 * Gets the team's experience for the 5-3-2 formation.
	 * 
	 * @return the experience for the 5-3-2 formation
	 */
    public int getFormationExperience532();

	/**
	 * Sets the team's experience for the 5-4-1 formation.
	 * 
	 * @param exp the experience value
	 */
    public void setFormationExperience541(int exp);

	/**
	 * Gets the team's experience for the 5-4-1 formation.
	 * 
	 * @return the experience for the 5-4-1 formation
	 */
    public int getFormationExperience541();
    
	/**
	 * Gets the team's experience for the 4-4-2 formation.
	 * 
	 * @return the experience for the 4-4-2 formation
	 */
    public int getFormationExperience442();

	/**
	 * Sets the team's experience for the 4-4-2 formation.
	 * 
	 * @param exp the experience value
	 */
    public void setFormationExperience442(int exp);
    
	/**
	 * Gets the team's experience for the 5-2-3 formation.
	 * 
	 * @return the experience for the 5-2-3 formation
	 */
    public int getFormationExperience523();

	/**
	 * Sets the team's experience for the 5-2-3 formation.
	 * 
	 * @param exp the experience value
	 */
    public void setFormationExperience523(int exp);
    
	/**
	 * Gets the team's experience for the 5-5-0 formation.
	 * 
	 * @return the experience for the 5-5-0 formation
	 */
    public int getFormationExperience550();

	/**
	 * Sets the team's experience for the 5-5-0 formation.
	 * 
	 * @param exp the experience value
	 */
    public void setFormationExperience550(int exp);

	/**
	 * Gets the team's experience for the 2-5-3 formation.
	 * 
	 * @return the experience for the 2-5-3 formation
	 */
    public int getFormationExperience253();

	/**
	 * Sets the team's experience for the 2-5-3 formation.
	 * 
	 * @param exp the experience value
	 */
    public void setFormationExperience253(int exp);

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
