// %1127326958603:plugins%
/*
 * Spieler.java
 *
 * Created on 17. März 2003, 15:41
 */
package plugins;

import ho.core.model.ITeam;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
/**
 * Interface to access Player Data
 */
public interface ISpieler {
    //~ Static fields/initializers -----------------------------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////
    //Konstanten
    //////////////////////////////////////////////////////////////////////////////// 

	/** Number of specialties */
	public static final int NUM_SPECIALTIES = 7;

	/** No specialty */
    public static final int NO_SPECIALTY = 0;

    /** Specialty: Technical */
    public static final int BALLZAUBERER = 1;

    /** Specialty: Quick */
    public static final int SCHNELL = 2;

    /** Specialty: Powerful */
    public static final int DURCHSETZUGNSSTARK = 3;

    /** Specialty: Unpredictable */
    public static final int UNBERECHENBAR = 4;

    /** Specialty: Header */
    public static final int KOPFBALLSTARK = 5;

    /** Specialty: Regainer */
    public static final int REGAINER = 6;

    //Wetter

    /** TODO Missing Parameter Documentation */
    public static final int SONNIG = 1;

    /** TODO Missing Parameter Documentation */
    public static final int LEICHTBEWOELKT = 2;

    /** TODO Missing Parameter Documentation */
    public static final int BEWOELKT = 3;

    /** TODO Missing Parameter Documentation */
    public static final int REGEN = 4;

    //FähigkeitsKonstanten

    /** TODO Missing Parameter Documentation */
    public static final int goettlich = 20;

    /** TODO Missing Parameter Documentation */
    public static final int galaktisch = 19;

    /** TODO Missing Parameter Documentation */
    public static final int maerchenhaft = 18;

    /** TODO Missing Parameter Documentation */
    public static final int mythisch = 17;

    /** TODO Missing Parameter Documentation */
    public static final int ausserirdisch = 16;

    /** TODO Missing Parameter Documentation */
    public static final int gigantisch = 15;

    /** TODO Missing Parameter Documentation */
    public static final int uebernatuerlich = 14;

    /** TODO Missing Parameter Documentation */
    public static final int Weltklasse = 13;

    /** TODO Missing Parameter Documentation */
    public static final int fantastisch = 12;

    /** TODO Missing Parameter Documentation */
    public static final int brilliant = 11;

    /** TODO Missing Parameter Documentation */
    public static final int grossartig = 10;

    /** TODO Missing Parameter Documentation */
    public static final int hervorragend = 9;

    /** TODO Missing Parameter Documentation */
    public static final int sehr_gut = 8;

    /** TODO Missing Parameter Documentation */
    public static final int gut = 7;

    /** TODO Missing Parameter Documentation */
    public static final int passabel = 6;

    /** TODO Missing Parameter Documentation */
    public static final int durchschnittlich = 5;

    /** TODO Missing Parameter Documentation */
    public static final int schwach = 4;

    /** TODO Missing Parameter Documentation */
    public static final int armselig = 3;

    /** TODO Missing Parameter Documentation */
    public static final int erbaermlich = 2;

    /** TODO Missing Parameter Documentation */
    public static final int katastrophal = 1;
    
    public static final int nicht_vorhanden = 0;

    //TR-Typen , unfortunatly doubled due desgin error, main defs ar ein ITeam

    public static final int TA_EXTERNALATTACK = ITeam.TA_EXTERNALATTACK;

    /** TODO Missing Parameter Documentation */
    public static final int STANDARDS = ITeam.TA_STANDARD;

    /** TODO Missing Parameter Documentation */
    public static final int VERTEIDIGUNG = ITeam.TA_VERTEIDIGUNG;

    /** TODO Missing Parameter Documentation */
    public static final int CHANCENVERWERTUNG = ITeam.TA_CHANCEN;

    /** TODO Missing Parameter Documentation */
    public static final int FLUEGELSPIEL = ITeam.TA_FLANKEN;

    /** TODO Missing Parameter Documentation */
    public static final int SCHUSSTRAINING = ITeam.TA_SCHUSSTRAINING;

    /** TODO Missing Parameter Documentation */
    public static final int PASSPIEL = ITeam.TA_PASSSPIEL;

    /** TODO Missing Parameter Documentation */
    public static final int SPIELAUFBAU = ITeam.TA_SPIELAUFBAU;

    /** TODO Missing Parameter Documentation */
    public static final int TORWART = ITeam.TA_TORWART;

    /** TODO Missing Parameter Documentation */
    public static final int TA_ABWEHRVERHALTEN = ITeam.TA_ABWEHRVERHALTEN;

    /** TODO Missing Parameter Documentation */
    public static final int TA_STEILPAESSE = ITeam.TA_STEILPAESSE;

    //Beliebtheit	

    /** TODO Missing Parameter Documentation */
    public static final int BL_Integrationsfigur = 5;

    /** TODO Missing Parameter Documentation */
    public static final int BL_beliebt = 4;

    /** TODO Missing Parameter Documentation */
    public static final int BL_sympathisch = 3;

    /** TODO Missing Parameter Documentation */
    public static final int BL_angenehm = 2;

    /** TODO Missing Parameter Documentation */
    public static final int BL_umstritten = 1;

    /** TODO Missing Parameter Documentation */
    public static final int BL_Ekel = 0;

    //Charakter	

    /** TODO Missing Parameter Documentation */
    public static final int CK_herzensgut = 5;

    /** TODO Missing Parameter Documentation */
    public static final int CK_rechtschaffen = 4;

    /** TODO Missing Parameter Documentation */
    public static final int CK_aufrichtig = 3;

    /** TODO Missing Parameter Documentation */
    public static final int CK_ehrlich = 2;

    /** TODO Missing Parameter Documentation */
    public static final int CK_unehrlich = 1;

    /** TODO Missing Parameter Documentation */
    public static final int CK_niedertraechtig = 0;

    //Psyche	

    /** TODO Missing Parameter Documentation */
    public static final int PS_aufbrausend = 4;

    /** TODO Missing Parameter Documentation */
    public static final int PS_temparamentvoll = 3;

    /** TODO Missing Parameter Documentation */
    public static final int PS_ausgeglichen = 2;

    /** TODO Missing Parameter Documentation */
    public static final int PS_ruhig = 1;

    /** TODO Missing Parameter Documentation */
    public static final int PS_introvertiert = 0;

    //Skills

    /** TODO Missing Parameter Documentation */
    public static final int SKILL_TORWART = 0;

    /** TODO Missing Parameter Documentation */
    public static final int SKILL_VERTEIDIGUNG = 1;

    /** TODO Missing Parameter Documentation */
    public static final int SKILL_FLUEGEL = 2;

    /** TODO Missing Parameter Documentation */
    public static final int SKILL_SPIELAUFBAU = 3;

    /** TODO Missing Parameter Documentation */
    public static final int SKILL_TORSCHUSS = 4;

    /** TODO Missing Parameter Documentation */
    public static final int SKILL_PASSSPIEL = 5;

    /** TODO Missing Parameter Documentation */
    public static final int SKILL_KONDITION = 6;

    /** TODO Missing Parameter Documentation */
    public static final int SKILL_FORM = 7;

    /** TODO Missing Parameter Documentation */
    public static final int SKILL_STANDARDS = 8;

    /** TODO Missing Parameter Documentation */
    public static final int SKILL_EXPIERIENCE = 9;
    
    /** TODO Missing Parameter Documentation */
    public static final int SKILL_LEADERSHIP = 10;
    
    /** TODO Missing Parameter Documentation */
    public static final int SKILL_LOYALTY = 11;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_iAgressivitaet.
     *
     * @param m_iAgressivitaet New value of property m_iAgressivitaet.
     */
    public void setAgressivitaet(int m_iAgressivitaet);

    /**
     * Getter for property m_iAgressivitaet.
     *
     * @return Value of property m_iAgressivitaet.
     */
    public int getAgressivitaet();

    /**
     * Setter for property m_sAgressivitaet.
     *
     * @param m_sAgressivitaet New value of property m_sAgressivitaet.
     * @deprecated
     */
    public void setAgressivitaetString(java.lang.String m_sAgressivitaet);

    /**
     * Getter for property m_sAgressivitaet.
     *
     * @return Value of property m_sAgressivitaet.
     */
    public java.lang.String getAgressivitaetString();

    /**
     * liefert das Datum des letzen LevelAufstiegs für den angeforderten Skill Vector filled with
     * object[] [0] = Time der Änderung [1] = Boolean: false=Keine Änderung gefunden
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public java.util.Vector<Object[]> getAllLevelUp(int skill);

    /**
     * Setter for property m_iAlter.
     *
     * @param m_iAlter New value of property m_iAlter.
     */
    public void setAlter(int m_iAlter);

    /**
     * Getter for property m_iAlter.
     *
     * @return Value of property m_iAlter.
     */
    public int getAlter();

    /**
     * Setter for property m_iAgeDays.
     *
     * @param m_iAlter New value of property m_iAgeDays.
     */
    public void setAgeDays(int m_iAgeDays);

    /**
     * Getter for property m_iAgeDays.
     *
     * @return Value of property m_iAgeDays.
     */
    public int getAgeDays();

    /**
     * Calculates full age with days and offset
     * 
     * @return Double value of age & agedays & offset combined,
     * 			i.e. age + (agedays+offset)/112
     */
    public double getAlterWithAgeDays();

    /**
     * Calculates String for full age with days and offset
     * 
     * @return String of age & agedays & offset combined,
     * 			format is "YY.DDD" or
     * 			"YY.DDD+1" (if player had his birthday since last HRF)
     */
    public String getAlterWithAgeDaysAsString();

    /**
     * Setter for property m_iAnsehen.
     *
     * @param m_iAnsehen New value of property m_iAnsehen.
     */
    public void setAnsehen(int m_iAnsehen);

    /**
     * Getter for property m_iAnsehen.
     *
     * @return Value of property m_iAnsehen.
     */
    public int getAnsehen();

    /**
     * Setter for property m_sAnsehen.
     *
     * @param m_sAnsehen New value of property m_sAnsehen.
     * @deprecated
     */
    public void setAnsehenString(java.lang.String m_sAnsehen);

    /**
     * Getter for property m_sAnsehen.
     *
     * @return Value of property m_sAnsehen.
     */
    public java.lang.String getAnsehenString();

    /**
     * Setter for property m_iBewertung.
     *
     * @param m_iBewertung New value of property m_iBewertung.
     */
    public void setBewertung(int m_iBewertung);

    /**
     * Getter for property m_iBewertung.
     *
     * @return Value of property m_iBewertung.
     */
    public int getBewertung();

    /**
     * Getter for property m_iBonus.
     *
     * @return Value of property m_iBonus.
     */
    public int getBonus();

    /**
     * Setter for property m_iCharakter.
     *
     * @param m_iCharakter New value of property m_iCharakter.
     */
    public void setCharakter(int m_iCharakter);

    /**
     * Getter for property m_iCharackter.
     *
     * @return Value of property m_iCharackter.
     */
    public int getCharakter();

    /**
     * Setter for property m_sCharakter.
     *
     * @param m_sCharakter New value of property m_sCharakter.
     * @deprecated
     */
    public void setCharakterString(java.lang.String m_sCharakter);

    /**
     * Getter for property m_sCharakter.
     *
     * @return Value of property m_sCharakter.
     */
    public java.lang.String getCharakterString();

    /////////////////////////////////////////////////7
    //Training
    ///////////////////////////////////////////////////77
	public double getTrainingLength(int trTyp, int coTrainer, int trainerLvl, int intensitaet, int staminaTrainingPart);

    /**
     * Setter for property m_iErfahrung.
     *
     * @param m_iErfahrung New value of property m_iErfahrung.
     */
    public void setErfahrung(int m_iErfahrung);

    /**
     * Getter for property m_iErfahrung.
     *
     * @return Value of property m_iErfahrung.
     */
    public int getErfahrung();

    /**
     * get the experience bonus
     *
     * @param experience effective experience to calculate the bonus, use the xp from the player if set to 0
     *
     * @return experience bonus in percent
     */
    public float getErfahrungsBonus(float experience);

    /**
     * Setter for property m_iFluegelspiel.
     *
     * @param m_iFluegelspiel New value of property m_iFluegelspiel.
     */
    public void setFluegelspiel(int m_iFluegelspiel);

    /**
     * Getter for property m_iFluegelspiel.
     *
     * @return Value of property m_iFluegelspiel.
     */
    public int getFluegelspiel();

    /**
     * Setter for property m_iForm.
     *
     * @param m_iForm New value of property m_iForm.
     */
    public void setForm(int m_iForm);

    /**
     * Getter for property m_iForm.
     *
     * @return Value of property m_iForm.
     */
    public int getForm();

    /**
     * Setter for property m_iFuehrung.
     *
     * @param m_iFuehrung New value of property m_iFuehrung.
     */
    public void setFuehrung(int m_iFuehrung);

    /**
     * Getter for property m_iFuehrung.
     *
     * @return Value of property m_iFuehrung.
     */
    public int getFuehrung();

    /**
     * Setter for property m_iGehalt.
     *
     * @param m_iGehalt New value of property m_iGehalt.
     */
    public void setGehalt(int m_iGehalt);

    /**
     * Getter for property m_iGehalt.
     *
     * @return Value of property m_iGehalt.
     */
    public int getGehalt();

    /**
     * Setter for property m_iGelbeKarten.
     *
     * @param m_iGelbeKarten New value of property m_iGelbeKarten.
     */
    public void setGelbeKarten(int m_iGelbeKarten);

    /**
     * Getter for property m_iGelbeKarten.
     *
     * @return Value of property m_iGelbeKarten.
     */
    public int getGelbeKarten();

    /**
     * gibt an ob der spieler gesperrt ist
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isGesperrt();

    /**
     * Setter for property m_iHattrick.
     *
     * @param m_iHattrick New value of property m_iHattrick.
     */
    public void setHattrick(int m_iHattrick);

    /**
     * Getter for property m_iHattrick.
     *
     * @return Value of property m_iHattrick.
     */
    public int getHattrick();
    
    /**
     * Getter for m_bHomeGrown
     *
     * @return Value of property m_bHomeGrown
     */
    public boolean isHomeGrown();
    
    public void setHomeGrown(boolean hg);
    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public java.sql.Timestamp getHrfDate();

    /**
     * liefert die Stärke für die IdealPosition
     *
     * @param mitForm TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getIdealPosStaerke(boolean mitForm);

    /**
     * liefert die IdealPosition
     *
     * @return TODO Missing Return Method Documentation
     */
    public byte getIdealPosition();

    /**
     * Setter for property m_iKondition.
     *
     * @param m_iKondition New value of property m_iKondition.
     */
    public void setKondition(int m_iKondition);

    /**
     * Getter for property m_iKondition.
     *
     * @return Value of property m_iKondition.
     */
    public int getKondition();

    /**
     * Setter for property Caps.
     *
     * @param m_iLaenderspiele New value of property m_iLaenderspiele.
     */
    public void setLaenderspiele(int m_iLaenderspiele);

    /**
     * Getter for property Caps.
     *
     * @return Value of property Caps.
     */
    public int getLaenderspiele();

    /**
     * liefert das Datum des letzen LevelAufstiegs für den angeforderten Skill [0] = Time der
     * Änderung [1] = Boolean: false=Keine Änderung gefunden
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Object[] getLastLevelUp(int skill);

    /**
     * liefert die vergangenen Tage seit dem letzem LevelAufstieg für den angeforderten Skill
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return anzahl Tage seit dem letzen Aufstieg
     */
    public int getLastLevelUpInTage(int skill);

    /**
     * Gibt die Letzte Bewertung zurück, die der Spieler bekommen hat
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getLetzteBewertung();

    /**
     * Returns the loalty stat of the player
     *
     * @return TODO Missing Return Method Documentation
    */
    public int getLoyalty();
    
    public void setLoyalty(int lolayty);
    

    /**
     * Setter for property m_sManuellerSmilie.
     *
     * @param manuellerSmilie New value of property m_sManuellerSmilie.
     */
    public void setManuellerSmilie(java.lang.String manuellerSmilie);

    /**
     * Getter for property m_sManuellerSmilie.
     *
     * @return Value of property m_sManuellerSmilie.
     */
    public java.lang.String getManuellerSmilie();

    /**
     * Sets the TSI (aka Marktwert)
     *
     * @param m_iTSI New value of property m_iMarkwert.
     * @deprecated Use setTSI()
     */
    @Deprecated
	public void setMarkwert(int m_iTSI);

    /**
     * Returns the TSI (aka Marktwert)
     *
     * @return Value of property m_iMarkwert.
     * @deprecated use getTSI()
     */
    @Deprecated
	public int getMarkwert();

    /**
     * Sets the TSI
     *
     * @param m_iTSI New value of property m_iMarkwert.
     */
    public void setTSI(int m_iTSI);

    /**
     * Returns the TSI
     *
     * @return Value of property m_iMarkwert.
     */
    public int getTSI();
    
    /**
     * Returns the estimated value of this player (EPV)
     * 
     * @return EPV
     */
    public double getEPV();

    /**
     * Setter for property m_sName.
     *
     * @param m_sName New value of property m_sName.
     */
    public void setName(java.lang.String m_sName);

    /**
     * Getter for property m_sName.
     *
     * @return Value of property m_sName.
     */
    public java.lang.String getName();

    /**
     * Setter for property m_iNationalitaet.
     *
     * @param m_iNationalitaet New value of property m_iNationalitaet.
     */
    public void setNationalitaet(int m_iNationalitaet);

    /**
     * Getter for property m_iNationalitaet.
     *
     * @return Value of property m_iNationalitaet.
     */
    public int getNationalitaet();

    /**
     * TODO Missing Method Documentation
     *
     * @param notiz TODO Missing Method Parameter Documentation
     */
    public void setNotiz(String notiz);

    /**
     * liefert User Notiz zum Spieler
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNotiz();

    /**
     * Player, which is no longer in the squad
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isOld();

    /**
     * Setter for property m_iPasspiel.
     *
     * @param m_iPasspiel New value of property m_iPasspiel.
     */
    public void setPasspiel(int m_iPasspiel);

    /**
     * Getter for property m_iPasspiel.
     *
     * @return Value of property m_iPasspiel.
     */
    public int getPasspiel();

    /**
     * gibt an ob der angeforderte Skill beim diesem Trainingstyp trainiert wird
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     * @param trainingstype TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */

    public boolean isSkillTrained(int skill, int trainingstype);

    /**
     * Setter for property m_iSpezialitaet.
     *
     * @param m_iSpezialitaet New value of property m_iSpezialitaet.
     */
    public void setSpezialitaet(int m_iSpezialitaet);

    /**
     * Getter for property m_iSpezialitaet.
     *
     * @return Value of property m_iSpezialitaet.
     */
    public int getSpezialitaet();

    /**
     * Setter for property m_sSpezialitaet.
     *
     * @param m_sSpezialitaet New value of property m_sSpezialitaet.
     */
    public void setSpezialitaetString(java.lang.String m_sSpezialitaet);

    /**
     * Getter for property m_sSpezialitaet.
     *
     * @return Value of property m_sSpezialitaet.
     */
    public java.lang.String getSpezialitaetString();

    /**
     * Setter for property m_iSpielaufbau.
     *
     * @param m_iSpielaufbau New value of property m_iSpielaufbau.
     */
    public void setSpielaufbau(int m_iSpielaufbau);

    /**
     * Getter for property m_iSpielaufbau.
     *
     * @return Value of property m_iSpielaufbau.
     */
    public int getSpielaufbau();

    /**
     * setzt ob der User den Spieler zum Spiel zulässt
     *
     * @param flag TODO Missing Constructuor Parameter Documentation
     */
    public void setSpielberechtigt(boolean flag);

    /**
     * gibt an ob der User den Spieler zum Spiel zulässt
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isSpielberechtigt();

    /**
     * Setter for property m_iSpielerID.
     *
     * @param m_iSpielerID New value of property m_iSpielerID.
     */
    public void setSpielerID(int m_iSpielerID);

    /**
     * Getter for property m_iSpielerID.
     *
     * @return Value of property m_iSpielerID.
     */
    public int getSpielerID();

    /**
     * Setter for property m_iStandards.
     *
     * @param m_iStandards New value of property m_iStandards.
     */
    public void setStandards(int m_iStandards);

    /**
     * Getter for property m_iStandards.
     *
     * @return Value of property m_iStandards.
     */
    public int getStandards();

    /**
     * get Subskill 4 Specific Skill
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getSubskill4Pos(int skill);

    /**
     * TODO Missing Method Documentation
     *
     * @param skill TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getSubskill4SkillWithOffset(int skill);

    /**
     * Setter for property m_sTeamInfoSmilie.
     *
     * @param teamInfoSmilie New value of property m_sTeamInfoSmilie.
     */
    public void setTeamInfoSmilie(String teamInfoSmilie);

    /**
     * Getter for property m_sTeamInfoSmilie.
     *
     * @return Value of property m_sTeamInfoSmilie.
     */
    public java.lang.String getTeamInfoSmilie();

    /**
     * Setter for property m_iToreFreund.
     *
     * @param m_iToreFreund New value of property m_iToreFreund.
     */
    public void setToreFreund(int m_iToreFreund);

    /**
     * Getter for property m_iToreFreund.
     *
     * @return Value of property m_iToreFreund.
     */
    public int getToreFreund();

    /**
     * Setter for property m_iToreGesamt.
     *
     * @param m_iToreGesamt New value of property m_iToreGesamt.
     */
    public void setToreGesamt(int m_iToreGesamt);

    /**
     * Getter for property m_iToreGesamt.
     *
     * @return Value of property m_iToreGesamt.
     */
    public int getToreGesamt();

    /**
     * Setter for property m_iToreLiga.
     *
     * @param m_iToreLiga New value of property m_iToreLiga.
     */
    public void setToreLiga(int m_iToreLiga);

    /**
     * Getter for property m_iToreLiga.
     *
     * @return Value of property m_iToreLiga.
     */
    public int getToreLiga();

    /**
     * Setter for property m_iTorePokal.
     *
     * @param m_iTorePokal New value of property m_iTorePokal.
     */
    public void setTorePokal(int m_iTorePokal);

    /**
     * Getter for property m_iTorePokal.
     *
     * @return Value of property m_iTorePokal.
     */
    public int getTorePokal();

    /**
     * Setter for property m_iTorschuss.
     *
     * @param m_iTorschuss New value of property m_iTorschuss.
     */
    public void setTorschuss(int m_iTorschuss);

    /**
     * Getter for property m_iTorschuss.
     *
     * @return Value of property m_iTorschuss.
     */
    public int getTorschuss();

    /**
     * Setter for property m_iTorwart.
     *
     * @param m_iTorwart New value of property m_iTorwart.
     */
    public void setTorwart(int m_iTorwart);

    /**
     * Getter for property m_iTorwart.
     *
     * @return Value of property m_iTorwart.
     */
    public int getTorwart();

    /**
     * Setter for property m_iTrainer.
     *
     * @param m_iTrainer New value of property m_iTrainer.
     */
    public void setTrainer(int m_iTrainer);

    /**
     * Getter for property m_iTrainer.
     *
     * @return Value of property m_iTrainer.
     */
    public int getTrainer();

    /**
     * gibt an ob der Spieler Trainer ist
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isTrainer();

    /**
     * Setter for property m_iTrainerTyp.
     *
     * @param m_iTrainerTyp New value of property m_iTrainerTyp.
     */
    public void setTrainerTyp(int m_iTrainerTyp);

    /**
     * Getter for property m_iTrainerTyp.
     *
     * @return Value of property m_iTrainerTyp.
     */
    public int getTrainerTyp();

    /**
     * get TrainingOffset 4 Specific Skill
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getTrainingOffset4Skill(int skill);

    /**
     * Setter for property m_dTrainingsOffsetFluegelspiel.
     *
     * @param m_dTrainingsOffsetFluegelspiel New value of property m_dTrainingsOffsetFluegelspiel.
     */
    public void setTrainingsOffsetFluegelspiel(double m_dTrainingsOffsetFluegelspiel);

    /**
     * Getter for property m_dTrainingsOffsetFluegelspiel.
     *
     * @return Value of property m_dTrainingsOffsetFluegelspiel.
     */
    public double getTrainingsOffsetFluegelspiel();

    /**
     * Setter for property m_dTrainingsOffsetPasspiel.
     *
     * @param m_dTrainingsOffsetPasspiel New value of property m_dTrainingsOffsetPasspiel.
     */
    public void setTrainingsOffsetPasspiel(double m_dTrainingsOffsetPasspiel);

    /**
     * Getter for property m_dTrainingsOffsetPasspiel.
     *
     * @return Value of property m_dTrainingsOffsetPasspiel.
     */
    public double getTrainingsOffsetPasspiel();

    /**
     * Setter for property m_dTrainingsOffsetSpielaufbau.
     *
     * @param m_dTrainingsOffsetSpielaufbau New value of property m_dTrainingsOffsetSpielaufbau.
     */
    public void setTrainingsOffsetSpielaufbau(double m_dTrainingsOffsetSpielaufbau);

    /**
     * Getter for property m_dTrainingsOffsetSpielaufbau.
     *
     * @return Value of property m_dTrainingsOffsetSpielaufbau.
     */
    public double getTrainingsOffsetSpielaufbau();

    /**
     * Setter for property m_dTrainingsOffsetStandards.
     *
     * @param m_dTrainingsOffsetStandards New value of property m_dTrainingsOffsetStandards.
     */
    public void setTrainingsOffsetStandards(double m_dTrainingsOffsetStandards);

    /**
     * Getter for property m_dTrainingsOffsetStandards.
     *
     * @return Value of property m_dTrainingsOffsetStandards.
     */
    public double getTrainingsOffsetStandards();

    /**
     * Setter for property m_dTrainingsOffsetTorschuss.
     *
     * @param m_dTrainingsOffsetTorschuss New value of property m_dTrainingsOffsetTorschuss.
     */
    public void setTrainingsOffsetTorschuss(double m_dTrainingsOffsetTorschuss);

    /**
     * Getter for property m_dTrainingsOffsetTorschuss.
     *
     * @return Value of property m_dTrainingsOffsetTorschuss.
     */
    public double getTrainingsOffsetTorschuss();

    /**
     * Setter for property m_dTrainingsOffsetTorwart.
     *
     * @param m_dTrainingsOffsetTorwart New value of property m_dTrainingsOffsetTorwart.
     */
    public void setTrainingsOffsetTorwart(double m_dTrainingsOffsetTorwart);

    /**
     * Getter for property m_dTrainingsOffsetTorwart.
     *
     * @return Value of property m_dTrainingsOffsetTorwart.
     */
    public double getTrainingsOffsetTorwart();

    /**
     * Setter for property m_dTrainingsOffsetVerteidigung.
     *
     * @param m_dTrainingsOffsetVerteidigung New value of property m_dTrainingsOffsetVerteidigung.
     */
    public void setTrainingsOffsetVerteidigung(double m_dTrainingsOffsetVerteidigung);

    /**
     * Getter for property m_dTrainingsOffsetVerteidigung.
     *
     * @return Value of property m_dTrainingsOffsetVerteidigung.
     */
    public double getTrainingsOffsetVerteidigung();

    /**
     * Setter for property m_iTransferlisted.
     *
     * @param m_iTransferlisted New value of property m_iTransferlisted.
     */
    public void setTransferlisted(int m_iTransferlisted);

    /**
     * Getter for property m_iTransferlisted.
     *
     * @return Value of property m_iTransferlisted.
     */
    public int getTransferlisted();

    /**
     * Setter for property Playernumber.
     *
     * @param m_iTrikotnummer New value of property Playernumber.
     */
    public void setTrikotnummer(int m_iTrikotnummer);

    /**
     * Getter for property Playernumber.
     *
     * @return Value of property Playernumber.
     */
    public int getTrikotnummer();

    /**
     * Setter for property CapsU20.
     *
     * @param m_iU20Laenderspiele New value of property m_iU20Laenderspiele.
     */
    public void setU20Laenderspiele(int m_iU20Laenderspiele);

    /**
     * Getter for property CapsU20.
     *
     * @return Value of property CapsU20.
     */
    public int getU20Laenderspiele();

    /**
     * TODO Missing Method Documentation
     *
     * @param flag TODO Missing Method Parameter Documentation
     */
    public void setUserPosFlag(byte flag);

    /**
     * liefert User Notiz zum Spieler
     *
     * @return TODO Missing Return Method Documentation
     */
    public byte getUserPosFlag();

    ////////////////////////////////////////////////////////////////////////////////
    //Accessor
    ////////////////////////////////////////////////////////////////////////////////      

    /**
     * get Skillvalue 4 skill
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getValue4Skill4(int skill);

    /**
     * set Skillvalue 4 skill
     *
     * @param skill the skill to change
     * @param value the new skill value
     */
	public void setValue4Skill4(int skill, int value);

	/**
     * Setter for property m_iVerletzt.
     *
     * @param m_iVerletzt New value of property m_iVerletzt.
     */
    public void setVerletzt(int m_iVerletzt);

    /**
     * Getter for property m_iVerletzt.
     *
     * @return Value of property m_iVerletzt.
     */
    public int getVerletzt();

    /**
     * Setter for property m_iVerteidigung.
     *
     * @param m_iVerteidigung New value of property m_iVerteidigung.
     */
    public void setVerteidigung(int m_iVerteidigung);

    /**
     * Getter for property m_iVerteidigung.
     *
     * @return Value of property m_iVerteidigung.
     */
    public int getVerteidigung();

    /*
       Wetterabhängige Sonderereignisse
       Bestimmte Spezialfähigkeiten können in Zusammenhang mit einem bestimmten Wetter
        zu Sonderereignissen führen. Die Auswirkung dieses Sonderereignisses tritt
        von dem Zeitpunkt in Kraft, an dem es im Spielbericht erwähnt wird,
        und hat bis zum Spielende Einfluß auf die Leistung des Spielers.
        Diese Auswirkung wird nach dem Spiel an der Spielerbewertung (Anzahl Sterne); sichtbar.
       Die Torschuß- und die Spielaufbau-Fähigkeit von Ballzauberern kann sich bei Regen verschlechtern,
        während sich die gleichen Fähigkeiten bei Sonnenschein verbessern können.
       Bei Regen gibt es die Möglichkeit, daß sich die Torschuß-, Verteidigungs- und Spielaufbau-Fähigkeit
        von durchsetzungsstarken Spielern verbessert.
        Auf der anderen Seite kann sich die Torschußfähigkeit bei Sonnenschein verschlechtern.
       Schnelle Spieler laufen bei Regen Gefahr, daß sich ihre Torschuß- und
        Verteidigungsfähigkeiten verschlechtern. Bei Sonnenschein besteht das Risiko
        , daß ihre Torschußfähigkeit unter dem Wetter leidet.
     */
    /*
       Liefert die mögliche Auswirkung des Wetters auf den Spieler
       return 0 bei keine auswirkung
       1 bei positiv
       -1 bei negativ
     */
    public int getWetterEffekt(int wetter);

    ////////////////////////////////////////////////////////////////////////////////
    //Helper
    ////////////////////////////////////////////////////////////////////////////////       

    /**
     * berechnet die Stärke eines Spieler für angegebene Position
     *
     * @param pos die Position
     * @param mitForm gibt an ob Form berücksichtigt werden soll
     *
     * @return TODO Missing Return Method Documentation
     */
    public float calcPosValue(byte pos, boolean mitForm);

    //////////////////////////////////////////////////////////////////////////////////
    //equals
    /////////////////////////////////////////////////////////////////////////////////
    public boolean equals(Object obj);
    
    /**
     * Does this player have a training block?
     * @return training block
     */
    public boolean hasTrainingBlock ();
    
    /**
     * Set the training block of this player (true/false)
     * @param isBlocked	new value
     */
    public void setTrainingBlock (boolean isBlocked);
    
}
