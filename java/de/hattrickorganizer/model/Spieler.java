// %1607162836:de.hattrickorganizer.model%
/*
 * Spieler.java
 *
 * Created on 17. M�rz 2003, 15:41
 */
package de.hattrickorganizer.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import plugins.ITeam;
import plugins.ITrainingWeek;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.logik.TrainingsWeekManager;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;
import de.hattrickorganizer.tools.PlayerHelper;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public final class Spieler implements plugins.ISpieler {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Spielberechtigt */
    private Boolean m_bSpielberechtigt;

    /** Agressivit�tName */
    private String m_sAgressivitaet = "";

    /** Ansehen Name */
    private String m_sAnsehen = "";

    /** charakterName */
    private String m_sCharakter = "";

    /** ManuellerSmilie Dateiname */
    private String m_sManuellerSmilie;

    /** Name */
    private String m_sName = "";

    /** Spielernotizen */
    private String m_sNotiz;

    /** Spezialit�tName */
    private String m_sSpezialitaet = "";

    /** TeamInfoSmilie Dateiname */
    private String m_sTeamInfoSmilie;
    private java.sql.Timestamp m_clhrfDate;

    /** Datum des ersten HRFs mit dem Spieler */
    private Timestamp m_tsTime4FirstHRF;

    /** Der Spieler ist nicht mehr im aktuellen HRF vorhanden */
    private boolean m_bOld;
    private byte m_bUserPosFlag = -2;

    /** Fluegelspiel */
    private double m_dSubFluegelspiel;

    /** Passpiel */
    private double m_dSubPasspiel;

    /** Spielaufbau */
    private double m_dSubSpielaufbau;

    /** Standards */
    private double m_dSubStandards;

    /** Torschuss */
    private double m_dSubTorschuss;

    //Subskills
    private double m_dSubTorwart;

    /** Verteidigung */
    private double m_dSubVerteidigung;
    private double m_dTrainingsOffsetFluegelspiel;
    private double m_dTrainingsOffsetPasspiel;
    private double m_dTrainingsOffsetSpielaufbau;
    private double m_dTrainingsOffsetStandards;
    private double m_dTrainingsOffsetTorschuss;

    /** trainingsOffset */
    private double m_dTrainingsOffsetTorwart;
    private double m_dTrainingsOffsetVerteidigung;

    /** Agressivit�t */
    private int m_iAgressivitaet;

    /** Alter */
    private int m_iAlter;

    /** Age Days */
    private int m_iAgeDays;

    /** Ansehen (ekel usw. ) */
    private int m_iAnsehen = 1;

    /** Bewertung */
    private int m_iBewertung;

    /** charakter ( ehrlich) */
    private int m_iCharakter = 1;

    /** Erfahrung */
    private int m_iErfahrung = 1;

    /** Fluegelspiel */
    private int m_iFluegelspiel = 1;

    /** Form */
    private int m_iForm = 1;

    /** F�hrungsqualit�t */
    private int m_iFuehrung = 1;

    /** Gehalt */
    private int m_iGehalt = 1;

    /** Gelbe Karten */
    private int m_iGelbeKarten;

    /** Hattricks */
    private int m_iHattrick;

    /** Kondition */
    private int m_iKondition = 1;

    /** L�nderspiele */
    private int m_iLaenderspiele;

    //Cache

    /** Letzte Bewertung */
    private int m_iLastBewertung = -1;

    /** Markwert */
    private int m_iMarkwert;

    /** bonus in Prozent */

    //protected int       m_iBonus            =   0;

    /** Aus welchem Land kommt der Spieler */
    private int m_iNationalitaet = 49;

    /** Passpiel */
    private int m_iPasspiel = 1;

    /** Spezialit�tID */
    private int m_iSpezialitaet;

    /** Spielaufbau */
    private int m_iSpielaufbau = 1;

    ////////////////////////////////////////////////////////////////////////////////
    //Member
    ////////////////////////////////////////////////////////////////////////////////

    /** SpielerID */
    private int m_iSpielerID;

    /** Standards */
    private int m_iStandards = 1;

    /** Tore Freundschaftspiel */
    private int m_iToreFreund;

    /** Tore Gesamt */
    private int m_iToreGesamt;

    /** Tore Liga */
    private int m_iToreLiga;

    /** Tore Pokalspiel */
    private int m_iTorePokal;

    /** Torschuss */
    private int m_iTorschuss = 1;

    /** Torwart */
    private int m_iTorwart = 1;

    /** Trainerf�higkeit */
    private int m_iTrainer;

    /** Trainertyp */
    private int m_iTrainerTyp = -1;

    /** Transferlisted */
    private int m_iTransferlisted;

    //TODO Noch in DB adden

    /** Fetchdate */

    //    protected Timestamp m_clFetchDate       =   new Timestamp( System.currentTimeMillis () );

    /*TrikotNummer*/
    private int m_iTrikotnummer = -1;

    /** L�nderspiele */
    private int m_iU20Laenderspiele;

    /** Verletzt Wochen */
    private int m_iVerletzt = -1;

    /** Verteidigung */
    private int m_iVerteidigung = 1;

    //~ Constructors -------------------------------------------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a new instance of Spieler
     */
    public Spieler() {
    }

    /**
     * Erstellt einen Spieler aus der Datenbank
     *
     * @param rs TODO Missing Constructuor Parameter Documentation
     */
    public Spieler(java.sql.ResultSet rs) {
        try {
            m_iSpielerID = rs.getInt("SpielerID");
            m_sName = de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("Name"));
            m_iAlter = rs.getInt("Age");
            m_iAgeDays = rs.getInt("AgeDays");
            m_iKondition = rs.getInt("Kondition");
            m_iForm = rs.getInt("Form");
            m_iTorwart = rs.getInt("Torwart");
            m_iVerteidigung = rs.getInt("Verteidigung");
            m_iSpielaufbau = rs.getInt("Spielaufbau");
            m_iPasspiel = rs.getInt("Passpiel");
            m_iFluegelspiel = rs.getInt("Fluegel");
            m_iTorschuss = rs.getInt("Torschuss");
            m_iStandards = rs.getInt("Standards");
            m_iSpezialitaet = rs.getInt("iSpezialitaet");
            m_sSpezialitaet = de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("sSpezialitaet"));
            m_iCharakter = rs.getInt("iCharakter");
            m_sCharakter = de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("sCharakter"));
            m_iAnsehen = rs.getInt("iAnsehen");
            m_sAnsehen = de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("sAnsehen"));
            m_iAgressivitaet = rs.getInt("iAgressivitaet");
            m_sAgressivitaet = de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(rs.getString("sAgressivitaet"));
            m_iErfahrung = rs.getInt("Erfahrung");
            m_iFuehrung = rs.getInt("Fuehrung");
            m_iGehalt = rs.getInt("Gehalt");
            m_iNationalitaet = rs.getInt("Land");
            m_iMarkwert = rs.getInt("Marktwert");

            //TSI, alles vorher durch 1000 teilen
            m_clhrfDate = rs.getTimestamp("Datum");

            if (m_clhrfDate.before(de.hattrickorganizer.database.DBZugriff.TSIDATE)) {
                m_iMarkwert /= 1000d;
            }

            //Subskills
            m_dSubTorwart = rs.getFloat("SubTorwart");
            m_dSubVerteidigung = rs.getFloat("SubVerteidigung");
            m_dSubSpielaufbau = rs.getFloat("SubSpielaufbau");
            m_dSubPasspiel = rs.getFloat("SubPasspiel");
            m_dSubFluegelspiel = rs.getFloat("SubFluegel");
            m_dSubTorschuss = rs.getFloat("SubTorschuss");
            m_dSubStandards = rs.getFloat("SubStandards");

            //Offset
            m_dTrainingsOffsetTorwart = rs.getFloat("OffsetTorwart");
            m_dTrainingsOffsetVerteidigung = rs.getFloat("OffsetVerteidigung");
            m_dTrainingsOffsetSpielaufbau = rs.getFloat("OffsetSpielaufbau");
            m_dTrainingsOffsetPasspiel = rs.getFloat("OffsetPasspiel");
            m_dTrainingsOffsetFluegelspiel = rs.getFloat("OffsetFluegel");
            m_dTrainingsOffsetTorschuss = rs.getFloat("OffsetTorschuss");
            m_dTrainingsOffsetStandards = rs.getFloat("OffsetStandards");

            m_iGelbeKarten = rs.getInt("GelbeKarten");
            m_iVerletzt = rs.getInt("Verletzt");
            m_iToreFreund = rs.getInt("ToreFreund");
            m_iToreLiga = rs.getInt("ToreLiga");
            m_iTorePokal = rs.getInt("TorePokal");
            m_iToreGesamt = rs.getInt("ToreGesamt");
            m_iHattrick = rs.getInt("Hattrick");
            m_iBewertung = rs.getInt("Bewertung");
            m_iTrainerTyp = rs.getInt("TrainerTyp");
            m_iTrainer = rs.getInt("Trainer");

            m_iTrikotnummer = rs.getInt("PlayerNumber");
            m_iTransferlisted = rs.getInt("TransferListed");
            m_iLaenderspiele = rs.getInt("Caps");
            m_iU20Laenderspiele = rs.getInt("CapsU20");
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    /**
     * Erstellt einen Spieler aus den Properties einer HRF Datei
     *
     * @param properties TODO Missing Constructuor Parameter Documentation
     * @param hrfdate TODO Missing Constructuor Parameter Documentation
     *
     * @throws Exception TODO Missing Constructuor Exception Documentation
     */
    public Spieler(java.util.Properties properties, Timestamp hrfdate)
      throws Exception
    {
        m_iSpielerID = Integer.parseInt(properties.getProperty("id", "0"));
        m_sName = properties.getProperty("name", "");
        m_iAlter = Integer.parseInt(properties.getProperty("ald", "0"));
        m_iAgeDays = Integer.parseInt(properties.getProperty("agedays", "0"));
        m_iKondition = Integer.parseInt(properties.getProperty("uth", "0"));
        m_iForm = Integer.parseInt(properties.getProperty("for", "0"));
        m_iTorwart = Integer.parseInt(properties.getProperty("mlv", "0"));
        m_iVerteidigung = Integer.parseInt(properties.getProperty("bac", "0"));
        m_iSpielaufbau = Integer.parseInt(properties.getProperty("spe", "0"));
        m_iPasspiel = Integer.parseInt(properties.getProperty("fra", "0"));
        m_iFluegelspiel = Integer.parseInt(properties.getProperty("ytt", "0"));
        m_iTorschuss = Integer.parseInt(properties.getProperty("mal", "0"));
        m_iStandards = Integer.parseInt(properties.getProperty("fas", "0"));
        m_iSpezialitaet = Integer.parseInt(properties.getProperty("speciality", "0"));
        m_sSpezialitaet = properties.getProperty("specialitylabel", "");
        m_iCharakter = Integer.parseInt(properties.getProperty("gentleness", "0"));
        m_sCharakter = properties.getProperty("gentlenesslabel", "");
        m_iAnsehen = Integer.parseInt(properties.getProperty("honesty", "0"));
        m_sAnsehen = properties.getProperty("honestylabel", "");
        m_iAgressivitaet = Integer.parseInt(properties.getProperty("aggressiveness", "0"));
        m_sAgressivitaet = properties.getProperty("aggressivenesslabel", "");
        m_iErfahrung = Integer.parseInt(properties.getProperty("rut", "0"));
        m_iFuehrung = Integer.parseInt(properties.getProperty("led", "0"));
        m_iGehalt = Integer.parseInt(properties.getProperty("sal", "0"));
        m_iNationalitaet = Integer.parseInt(properties.getProperty("countryid", "0"));
        m_iMarkwert = Integer.parseInt(properties.getProperty("mkt", "0"));

        //TSI, alles vorher durch 1000 teilen
        m_clhrfDate = hrfdate;

        if (hrfdate.before(de.hattrickorganizer.database.DBZugriff.TSIDATE)) {
            m_iMarkwert /= 1000d;
        }

        m_iGelbeKarten = Integer.parseInt(properties.getProperty("warnings", "0"));
        m_iVerletzt = Integer.parseInt(properties.getProperty("ska", "0"));
        m_iToreFreund = Integer.parseInt(properties.getProperty("gtt", "0"));
        m_iToreLiga = Integer.parseInt(properties.getProperty("gtl", "0"));
        m_iTorePokal = Integer.parseInt(properties.getProperty("gtc", "0"));
        m_iToreGesamt = Integer.parseInt(properties.getProperty("gev", "0"));
        m_iHattrick = Integer.parseInt(properties.getProperty("hat", "0"));

        if (properties.get("rating") != null) {
            m_iBewertung = Integer.parseInt(properties.getProperty("rating", "0"));
        }

        String temp = properties.getProperty("trainertype", "-1");

        if ((temp != null) && !temp.equals("")) {
            m_iTrainerTyp = Integer.parseInt(temp);
        }

        temp = properties.getProperty("trainerskill", "0");

        if ((temp != null) && !temp.equals("")) {
            m_iTrainer = Integer.parseInt(temp);
        }

        temp = properties.getProperty("playernumber", "");

        if ((temp != null) && !temp.equals("") && !temp.equals("null")) {
            m_iTrikotnummer = Integer.parseInt(temp);
        }

        m_iTransferlisted = Integer.parseInt(properties.getProperty("transferlisted", "0"));
        m_iLaenderspiele = Integer.parseInt(properties.getProperty("caps", "0"));
        m_iU20Laenderspiele = Integer.parseInt(properties.getProperty("capsU20", "0"));

        //Subskills berechnen
        //Wird beim Speichern des HRFs aufgerufen, da hier nicht unbedingt die notwendigen Daten vorhanden sind
        //Alte Offsets holen!
        //Offsets aus dem aktuellen HRF holen
        final de.hattrickorganizer.model.HOModel oldmodel = de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                   .getModel();
        final Spieler oldSpieler = oldmodel.getSpieler(m_iSpielerID);

        if (oldSpieler != null) {
            m_dTrainingsOffsetTorwart = oldSpieler.getTrainingsOffsetTorwart();
            m_dTrainingsOffsetVerteidigung = oldSpieler.getTrainingsOffsetVerteidigung();
            m_dTrainingsOffsetSpielaufbau = oldSpieler.getTrainingsOffsetSpielaufbau();
            m_dTrainingsOffsetPasspiel = oldSpieler.getTrainingsOffsetPasspiel();
            m_dTrainingsOffsetFluegelspiel = oldSpieler.getTrainingsOffsetFluegelspiel();
            m_dTrainingsOffsetTorschuss = oldSpieler.getTrainingsOffsetTorschuss();
            m_dTrainingsOffsetStandards = oldSpieler.getTrainingsOffsetStandards();
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_iAgressivitaet.
     *
     * @param m_iAgressivitaet New value of property m_iAgressivitaet.
     */
    public void setAgressivitaet(int m_iAgressivitaet) {
        this.m_iAgressivitaet = m_iAgressivitaet;
    }

    /**
     * Getter for property m_iAgressivitaet.
     *
     * @return Value of property m_iAgressivitaet.
     */
    public int getAgressivitaet() {
        return m_iAgressivitaet;
    }

    /**
     * Setter for property m_sAgressivitaet.
     *
     * @param m_sAgressivitaet New value of property m_sAgressivitaet.
     */
    public void setAgressivitaetString(java.lang.String m_sAgressivitaet) {
        this.m_sAgressivitaet = m_sAgressivitaet;
    }

    /**
     * Getter for property m_sAgressivitaet.
     *
     * @return Value of property m_sAgressivitaet.
     */
    public java.lang.String getAgressivitaetString() {
        return m_sAgressivitaet;
    }

    /**
     * liefert das Datum des letzen LevelAufstiegs f�r den angeforderten Skill Vector filled with
     * object[] [0] = Time der �nderung [1] = Boolean: false=Keine �nderung gefunden
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector getAllLevelUp(int skill) {
        return DBZugriff.instance().getAllLevelUp(skill,m_iSpielerID);
    }

    /**
     * Setter for property m_iAlter.
     *
     * @param m_iAlter New value of property m_iAlter.
     */
    public void setAlter(int m_iAlter) {
        this.m_iAlter = m_iAlter;
    }

    /**
     * Getter for property m_iAlter.
     *
     * @return Value of property m_iAlter.
     */
    public int getAlter() {
        return m_iAlter;
    }

    /**
     * Setter for property m_iAgeDays.
     *
     * @param m_iAlter New value of property m_iAgeDays.
     */
    public void setAgeDays(int m_iAgeDays) {
    	this.m_iAgeDays = m_iAgeDays;
    }

    /**
     * Getter for property m_iAgeDays.
     *
     * @return Value of property m_iAgeDays.
     */
    public int getAgeDays() {
    	return m_iAgeDays;
    }

    /**
     * Calculates full age with days and offset
     *
     * @return Double value of age & agedays & offset combined,
     * 			i.e. age + (agedays+offset)/112
     */
    public double getAlterWithAgeDays() {
      	long hrftime = HOMiniModel.instance().getBasics().getDatum().getTime();
    	long now = new Date().getTime();
    	long diff = (now - hrftime) / (1000*60*60*24);
    	int years = getAlter();
    	int days = getAgeDays();
    	double retVal = years + (double)(days+diff)/112;
    	return retVal;
    }

    /**
     * Calculates String for full age with days and offset
     *
     * @return String of age & agedays & offset combined,
     * 			format is "YY.DDD" or
     * 			"YY.DDD+1" (if player had his birthday since last HRF)
     */
    public String getAlterWithAgeDaysAsString() {
    	// format = yy.ddd
      	long hrftime = HOMiniModel.instance().getBasics().getDatum().getTime();
    	long now = new Date().getTime();
    	long diff = (now - hrftime) / (1000*60*60*24);
    	int years = getAlter();
    	int days = getAgeDays();
    	days += diff;
    	boolean birthday = false;
    	while (days > 111) {
    		days -= 112;
    		years++;
    		birthday = true;
    	}
    	String retVal = years + "." + days;
    	if (birthday)
    		retVal += "+1";
    	return retVal;
    }

    /**
     * Get the full i18n'd string represention the players age. Includes
     * the birthay indicator as well.
     * @return the full i18n'd string represention the players age
     */
    public String getAgeStringFull() {
      	long hrftime = HOMiniModel.instance().getBasics().getDatum().getTime();
    	long now = new Date().getTime();
    	long diff = (now - hrftime) / (1000*60*60*24);
    	int years = getAlter();
    	int days = getAgeDays();
    	days += diff;
    	boolean birthday = false;
    	while (days > 111) {
    		days -= 112;
    		years++;
    		birthday = true;
    	}
    	StringBuffer ret = new StringBuffer();
    	ret.append(years);
    	ret.append(" ");
    	ret.append(HOVerwaltung.instance().getResource().getProperty("age.years"));
    	ret.append(" ");
    	ret.append(days);
    	ret.append(" ");
    	ret.append(HOVerwaltung.instance().getResource().getProperty("age.days"));
    	if (birthday) {
    		ret.append(" (");
    		ret.append(HOVerwaltung.instance().getResource().getProperty("age.birthday"));
    		ret.append(")");
    	}
    	return ret.toString();
    }

    /**
     * Setter for property m_iAnsehen.
     *
     * @param m_iAnsehen New value of property m_iAnsehen.
     */
    public void setAnsehen(int m_iAnsehen) {
        this.m_iAnsehen = m_iAnsehen;
    }

    /**
     * Getter for property m_iAnsehen.
     *
     * @return Value of property m_iAnsehen.
     */
    public int getAnsehen() {
        return m_iAnsehen;
    }

    /**
     * Setter for property m_sAnsehen.
     *
     * @param m_sAnsehen New value of property m_sAnsehen.
     */
    public void setAnsehenString(java.lang.String m_sAnsehen) {
        this.m_sAnsehen = m_sAnsehen;
    }

    /**
     * Getter for property m_sAnsehen.
     *
     * @return Value of property m_sAnsehen.
     */
    public java.lang.String getAnsehenString() {
        return m_sAnsehen;
    }

    /**
     * Setter for property m_iBewertung.
     *
     * @param m_iBewertung New value of property m_iBewertung.
     */
    public void setBewertung(int m_iBewertung) {
        this.m_iBewertung = m_iBewertung;
    }

    /**
     * Getter for property m_iBewertung.
     *
     * @return Value of property m_iBewertung.
     */
    public int getBewertung() {
        return m_iBewertung;
    }

    /**
     * Getter for property m_iBonus.
     *
     * @return Value of property m_iBonus.
     */
    public int getBonus() {
        int bonus = 0;

        if (m_iNationalitaet != HOVerwaltung.instance().getModel().getBasics().getLand()) {
            bonus = 20;
        }

        return bonus;
    }

    /**
     * Setter for property m_iCharakter.
     *
     * @param m_iCharakter New value of property m_iCharakter.
     */
    public void setCharakter(int m_iCharakter) {
        this.m_iCharakter = m_iCharakter;
    }

    /**
     * Getter for property m_iCharackter.
     *
     * @return Value of property m_iCharackter.
     */
    public int getCharakter() {
        return m_iCharakter;
    }

    /**
     * Setter for property m_sCharakter.
     *
     * @param m_sCharakter New value of property m_sCharakter.
     */
    public void setCharakterString(java.lang.String m_sCharakter) {
        this.m_sCharakter = m_sCharakter;
    }

    /**
     * Getter for property m_sCharakter.
     *
     * @return Value of property m_sCharakter.
     */
    public java.lang.String getCharakterString() {
        return m_sCharakter;
    }

	/**
	 * @deprecated
	 */
	public int getDauerTraining(int trTyp, int coTrainer, int twTrainer, int trainerLvl,
								int intensitaet, int staminaTrainingPart) {
			return (int) Math.round(getTrainingLength(trTyp,coTrainer,twTrainer,trainerLvl,intensitaet, staminaTrainingPart));
	}

    /////////////////////////////////////////////////7
    //Training ( reflected to skills)
    //TORSCHUSS is skipped due it trains two skills, won't be displayed in gui. Real calc for this is done in Trainingsmanager
    //TA_EXTERNALATTACK skipped due it trains two skills, calc must be done in trainingmanager for each skill seperate like Torschuss.no display in options gui.
    // @return duration of training based on settings and tr type, calls calcTraining for nested calculation
    ///////////////////////////////////////////////////77
    public double getTrainingLength(int trTyp, int coTrainer, int twTrainer, int trainerLvl,
                                int intensitaet, int staminaTrainingPart) {
        switch (trTyp) {
            case TORWART:
                return calcTraining(gui.UserParameter.instance().DAUER_TORWART, m_iAlter,
                                    twTrainer, trainerLvl, intensitaet, staminaTrainingPart);

            case ALLGEMEIN:
                return calcTraining(gui.UserParameter.instance().DAUER_ALLGEMEIN, m_iAlter,
                                    coTrainer, trainerLvl, intensitaet, staminaTrainingPart);

            case KONDITION:
                return gui.UserParameter.instance().DAUER_KONDITION;

            case SPIELAUFBAU:
                return calcTraining(gui.UserParameter.instance().DAUER_SPIELAUFBAU, m_iAlter,
                                     coTrainer, trainerLvl, intensitaet, staminaTrainingPart);

            case FLUEGELSPIEL:
                return calcTraining(gui.UserParameter.instance().DAUER_FLUEGELSPIEL, m_iAlter,
                                     coTrainer, trainerLvl, intensitaet, staminaTrainingPart);

            case CHANCENVERWERTUNG:
                return calcTraining(gui.UserParameter.instance().DAUER_CHANCENVERWERTUNG, m_iAlter,
                                     coTrainer, trainerLvl, intensitaet, staminaTrainingPart);

            case VERTEIDIGUNG:
                return calcTraining(gui.UserParameter.instance().DAUER_VERTEIDIGUNG, m_iAlter,
                                     coTrainer, trainerLvl, intensitaet, staminaTrainingPart);

            case PASSPIEL:
				return calcTraining(gui.UserParameter.instance().DAUER_PASSPIEL, m_iAlter,
								coTrainer, trainerLvl, intensitaet, staminaTrainingPart);

            case TA_STEILPAESSE:
                return calcTraining(gui.UserParameter.instance().DAUER_PASSPIEL*100d/85d, m_iAlter,
                                    coTrainer, trainerLvl, intensitaet, staminaTrainingPart);

            case STANDARDS:

                //calcTraining( gui.UserParameter.instance ().DAUER_STANDARDS, m_iAlter,  coTrainer, trainerLvl, intensitaet );
                return gui.UserParameter.instance().DAUER_STANDARDS;

            case TA_ABWEHRVERHALTEN:
                return calcTraining(gui.UserParameter.instance().DAUER_VERTEIDIGUNG * 2, m_iAlter,
                                     coTrainer, trainerLvl, intensitaet, staminaTrainingPart);

			case TA_EXTERNALATTACK:
				return calcTraining(gui.UserParameter.instance().DAUER_FLUEGELSPIEL*100d/60d, m_iAlter,
								 coTrainer, trainerLvl, intensitaet, staminaTrainingPart);

            default:
                return -1;
        }
    }

    /**
     * Setter for property m_iErfahrung.
     *
     * @param m_iErfahrung New value of property m_iErfahrung.
     */
    public void setErfahrung(int m_iErfahrung) {
        this.m_iErfahrung = m_iErfahrung;
    }

    /**
     * Getter for property m_iErfahrung.
     *
     * @return Value of property m_iErfahrung.
     */
    public int getErfahrung() {
        return m_iErfahrung;
    }

    /**
     * liefert den ErfaHRUNGSbonus
     *
     * @param es Effektive Bezugs St�rke
     *
     * @return 0 - 2.6 float
     */
    public float getErfahrungsBonus(float es) {
        float bonus = 0;

        //Bonus = log  (Erfharung) zur Basis 10 * 2 * Userfaktor , 0.0 - 2.6 ca 0 bis 1.5 Sterne

        /*Modified by Catrone in order to avoid the non-existant value resulting in a player's negative evaluation*/
		if ( m_iErfahrung == 0 )
		{
			return bonus; /*If experience is non-existent, the bonus is zero!*/
		}

        bonus = (float) ((Math.log(m_iErfahrung) / Math.log(10)) * 2.0f * FormulaFactors.instance().getErfahrungs_Faktor());

        return bonus;
    }

    /**
     * Setter for property m_iFluegelspiel.
     *
     * @param m_iFluegelspiel New value of property m_iFluegelspiel.
     */
    public void setFluegelspiel(int m_iFluegelspiel) {
        this.m_iFluegelspiel = m_iFluegelspiel;
    }

    /**
     * Getter for property m_iFluegelspiel.
     *
     * @return Value of property m_iFluegelspiel.
     */
    public int getFluegelspiel() {
        return m_iFluegelspiel;
    }

    /**
     * Setter for property m_iForm.
     *
     * @param m_iForm New value of property m_iForm.
     */
    public void setForm(int m_iForm) {
        this.m_iForm = m_iForm;
    }

    /**
     * Getter for property m_iForm.
     *
     * @return Value of property m_iForm.
     */
    public int getForm() {
        return m_iForm;
    }

    /**
     * Setter for property m_iFuehrung.
     *
     * @param m_iFuehrung New value of property m_iFuehrung.
     */
    public void setFuehrung(int m_iFuehrung) {
        this.m_iFuehrung = m_iFuehrung;
    }

    /**
     * Getter for property m_iFuehrung.
     *
     * @return Value of property m_iFuehrung.
     */
    public int getFuehrung() {
        return m_iFuehrung;
    }

    /**
     * Setter for property m_iGehalt.
     *
     * @param m_iGehalt New value of property m_iGehalt.
     */
    public void setGehalt(int m_iGehalt) {
        this.m_iGehalt = m_iGehalt;
    }

    /**
     * Getter for property m_iGehalt.
     *
     * @return Value of property m_iGehalt.
     */
    public int getGehalt() {
        return m_iGehalt;
    }

    /**
     * Setter for property m_iGelbeKarten.
     *
     * @param m_iGelbeKarten New value of property m_iGelbeKarten.
     */
    public void setGelbeKarten(int m_iGelbeKarten) {
        this.m_iGelbeKarten = m_iGelbeKarten;
    }

    /**
     * Getter for property m_iGelbeKarten.
     *
     * @return Value of property m_iGelbeKarten.
     */
    public int getGelbeKarten() {
        return m_iGelbeKarten;
    }

    /**
     * gibt an ob der spieler gesperrt ist
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isGesperrt() {
        return (m_iGelbeKarten > 2);
    }

    /**
     * Setter for property m_iHattrick.
     *
     * @param m_iHattrick New value of property m_iHattrick.
     */
    public void setHattrick(int m_iHattrick) {
        this.m_iHattrick = m_iHattrick;
    }

    /**
     * Getter for property m_iHattrick.
     *
     * @return Value of property m_iHattrick.
     */
    public int getHattrick() {
        return m_iHattrick;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Timestamp getHrfDate() {
        return m_clhrfDate;
    }

    /**
     * liefert die St�rke f�r die IdealPosition
     *
     * @param mitForm TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getIdealPosStaerke(boolean mitForm) {
        return calcPosValue(getIdealPosition(), mitForm);
    }

    /**
     * liefert die IdealPosition
     *
     * @return TODO Missing Return Method Documentation
     */
    public byte getIdealPosition() {
        //Usr Vorgabe aus DB holen
        final byte flag = getUserPosFlag();

        if (flag == ISpielerPosition.UNBESTIMMT) {
            final FactorObject[] allPos = FormulaFactors.instance().getAllObj();
            byte idealPos = ISpielerPosition.UNBESTIMMT;
            float maxStk = -1.0f;

            for (int i = 0; (allPos != null) && (i < allPos.length); i++) {
// 31.03.2008, aik: this normalized version to determine the best position is
//   a bit irritationg/misleading. We decided to use the 'raw' star values from now one.
//                if (calcPosValue(allPos[i],true) > maxStk) {
//                	maxStk = calcPosValue(allPos[i],true);
                if (calcPosValue(allPos[i].getPosition(),true) > maxStk) {
                	maxStk = calcPosValue(allPos[i].getPosition(),true);
                    idealPos = allPos[i].getPosition();
                }
            }

            return idealPos;
        }

        return flag;
    }

    /**
     * Setter for property m_iKondition.
     *
     * @param m_iKondition New value of property m_iKondition.
     */
    public void setKondition(int m_iKondition) {
        this.m_iKondition = m_iKondition;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Accessor
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter for property m_iKondition.
     *
     * @return Value of property m_iKondition.
     */
    public int getKondition() {
        return m_iKondition;
    }

    /**
     * Setter for property m_iLaenderspiele.
     *
     * @param m_iLaenderspiele New value of property m_iLaenderspiele.
     */
    public void setLaenderspiele(int m_iLaenderspiele) {
        this.m_iLaenderspiele = m_iLaenderspiele;
    }

    /**
     * Getter for property m_iLaenderspiele.
     *
     * @return Value of property m_iLaenderspiele.
     */
    public int getLaenderspiele() {
        return m_iLaenderspiele;
    }

    /**
     * liefert das Datum des letzen LevelAufstiegs f�r den angeforderten Skill [0] = Time der
     * �nderung [1] = Boolean: false=Keine �nderung gefunden
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Object[] getLastLevelUp(int skill) {
        return DBZugriff.instance().getLastLevelUp(skill, m_iSpielerID);
    }

    /**
     * liefert die vergangenen Tage seit dem letzem LevelAufstieg f�r den angeforderten Skill
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return anzahl Tage seit dem letzen Aufstieg
     */
    public int getLastLevelUpInTage(int skill) {
        int tage = 0;
        final Timestamp datum = (Timestamp) getLastLevelUp(skill)[0];
        final Timestamp heute = new Timestamp(System.currentTimeMillis());
        long diff = 0;

        if (datum != null) {
            diff = heute.getTime() - datum.getTime();

            //In Tage umrechnen
            tage = (int) (diff / 86400000);
        }

        return tage;
    }

    /**
     * Gibt die Letzte Bewertung zur�ck, die der Spieler bekommen hat
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getLetzteBewertung() {
        if (m_iLastBewertung < 0) {
            m_iLastBewertung = de.hattrickorganizer.database.DBZugriff.instance()
                                                                      .getLetzteBewertung4Spieler(m_iSpielerID);
        }

        return m_iLastBewertung;
    }

    /**
     * Setter for property m_sManuellerSmilie.
     *
     * @param manuellerSmilie New value of property m_sManuellerSmilie.
     */
    public void setManuellerSmilie(java.lang.String manuellerSmilie) {
        if (manuellerSmilie == null) {
            manuellerSmilie = "";
        }

        m_sManuellerSmilie = manuellerSmilie;
        de.hattrickorganizer.database.DBZugriff.instance().saveManuellerSmilie(m_iSpielerID,
                                                                               manuellerSmilie);
    }

    /**
     * Getter for property m_sManuellerSmilie.
     *
     * @return Value of property m_sManuellerSmilie.
     */
    public java.lang.String getManuellerSmilie() {
        if (m_sManuellerSmilie == null) {
            m_sManuellerSmilie = de.hattrickorganizer.database.DBZugriff.instance()
                                                                        .getManuellerSmilie(m_iSpielerID);

            //Steht null in der DB?
            if (m_sManuellerSmilie == null) {
                m_sManuellerSmilie = "";
            }
        }

        //database.DBZugriff.instance ().getManuellerSmilie( m_iSpielerID );
        return m_sManuellerSmilie;
    }

    /**
     * Setter for property m_iMarkwert.
     *
     * @param m_iMarkwert New value of property m_iMarkwert.
     */
    public void setMarkwert(int m_iMarkwert) {
        this.m_iMarkwert = m_iMarkwert;
    }

    /**
     * Getter for property m_iMarkwert.
     *
     * @return Value of property m_iMarkwert.
     */
    public int getMarkwert() {
        return m_iMarkwert;
    }

    /**
     * Setter for property m_sName.
     *
     * @param m_sName New value of property m_sName.
     */
    public void setName(java.lang.String m_sName) {
        this.m_sName = m_sName;
    }

    /**
     * Getter for property m_sName.
     *
     * @return Value of property m_sName.
     */
    public java.lang.String getName() {
        return de.hattrickorganizer.database.DBZugriff.deleteEscapeSequences(m_sName);
    }

    /**
     * Setter for property m_iNationalitaet.
     *
     * @param m_iNationalitaet New value of property m_iNationalitaet.
     */
    public void setNationalitaet(int m_iNationalitaet) {
        this.m_iNationalitaet = m_iNationalitaet;
    }

    /**
     * Setter for property m_iBonus.
     *
     * @return TODO Missing Return Method Documentation
     */

    /* public void setBonus (int m_iBonus)
       {
           this.m_iBonus = m_iBonus;
       }
     */

    /**
     * Getter for property m_iNationalitaet.
     *
     * @return Value of property m_iNationalitaet.
     */
    public int getNationalitaet() {
        return m_iNationalitaet;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param notiz TODO Missing Method Parameter Documentation
     */
    public void setNotiz(String notiz) {
        if (notiz == null) {
            notiz = "";
        }

        m_sNotiz = notiz;
        de.hattrickorganizer.database.DBZugriff.instance().saveSpielerNotiz(m_iSpielerID, notiz);
    }

    /**
     * liefert User Notiz zum Spieler
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNotiz() {
        if (m_sNotiz == null) {
            m_sNotiz = de.hattrickorganizer.database.DBZugriff.instance().getSpielerNotiz(m_iSpielerID);
        }

        //database.DBZugriff.instance ().getSpielerNotiz ( m_iSpielerID );
        return m_sNotiz;
    }

    /**
     * Setter for property m_bOld.
     *
     * @param m_bOld New value of property m_bOld.
     */
    public void setOld(boolean m_bOld) {
        this.m_bOld = m_bOld;
    }

    /**
     * Getter for property m_bOld.
     *
     * @return Value of property m_bOld.
     */
    public boolean isOld() {
        return m_bOld;
    }

    /**
     * Setter for property m_iPasspiel.
     *
     * @param m_iPasspiel New value of property m_iPasspiel.
     */
    public void setPasspiel(int m_iPasspiel) {
        this.m_iPasspiel = m_iPasspiel;
    }

    /**
     * Getter for property m_iPasspiel.
     *
     * @return Value of property m_iPasspiel.
     */
    public int getPasspiel() {
        return m_iPasspiel;
    }

    /**
     * Zum speichern! Die Reduzierung des Marktwerts auf TSI wird r�ckg�ngig gemacht
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getSaveMarktwert() {
        if (m_clhrfDate.before(de.hattrickorganizer.database.DBZugriff.TSIDATE)) {
            //Echter Marktwert
            return m_iMarkwert * 1000;
        }

        //TSI
        return m_iMarkwert;
    }

    /**
     * liefert die Anzahl der Wochen die der Spieler den angeforderten Skill trainiert hat TODO :
     * Unfertig!
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     * @param trainingstype TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */

    /*public int getWeeksTrained( int skill )
       {
           int         trainedWeeks    =   0;
           Timestamp   max             =   HOVerwaltung.instance ().getModel ().getBasics ().getDatum ();
           Timestamp   min             =   getLastLevelUp( skill );
           Hashtable   weeksList       =   new Hashtable();//Liste der Id's die in Frage kommen
           Hashtable   hrfIdList       =   new Hashtable(); //liste aller HRF die in diesem Zeitraum liegen
           Enumeration enum            =   null;
           Integer     i               =   null;
           Integer     key             =   null;
           Timestamp   worker          =   null;

           //1. von aktuellem HRF-Date bis zum dortigen Datum Liste der Trainings-Wochen erstellen
           hrfIdList   =   DBZugriff.instance ().getHRFList ( min, max );
           enum        =   hrfIdList.keys ();
           while ( enum.hasMoreElements () )
           {
               i = (Integer) enum.nextElement ();
               weeksList.put ( i, new Integer (DBZugriff.instance ().getTrainingsartByHRFID ( i.intValue () ) ) );
           }
           //2a. Alle Wochen entfernen die nicht diesen Skill trainiert haben
           enum    =   weeksList.keys ();
           while( enum.hasMoreElements () )
           {
               key =   (Integer) enum.nextElement ();
               i   =   (Integer) weeksList.get ( key );

               //alle entfernen die nicht das passende Training haben...
               if ( !isSkillTrained( skill, i.intValue () ) )
               {
                   weeksList.remove ( key );
               }
           }
           //2b. F�r jede Trainingswoche nur ein HRF behalten
           enum    =   weeksList.keys ();
           while( enum.hasMoreElements () )
           {
               key     =   (Integer) enum.nextElement ();
               i       =   (Integer) weeksList.get ( key );
               worker  =   (Timestamp) hrfIdList.get ( key );

               //worker.
               //alle entfernen die nicht mehr ben�tigt werden
           }
           //3. Nun Liste der TR-Weeks durchlaufen und pr�fen ob Spieler gespielt hat und nicht verletzt ist
           //4. Dann pr�fen ob gespielte Pos trainiert wurde
           //5. Dann Training + 1;
           return trainedWeeks;
       }
     */

    /**
     * gibt an ob der angeforderte Skill beim diesem Trainingstyp trainiert wird
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     * @param trainingstype TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     * @deprecated Never used, better system integrated in Trainingmanager
     */
    public boolean isSkillTrained(int skill, int trainingstype) {
        boolean isTrained = false;

        switch (trainingstype) {
            case ITeam.TA_ALLGEMEIN:
                isTrained = skill == SKILL_FORM;
                break;

            case ITeam.TA_CHANCEN:
                isTrained = skill == SKILL_TORSCHUSS;
                break;

            case ITeam.TA_SCHUSSTRAINING:
                isTrained = (skill == SKILL_TORSCHUSS) || (skill == SKILL_STANDARDS);
                break;

            case ITeam.TA_FLANKEN:
                isTrained = skill == SKILL_FLUEGEL;
                break;

            case ITeam.TA_KONDITION:
                isTrained = skill == SKILL_KONDITION;
                break;

            case ITeam.TA_PASSSPIEL:
                isTrained = skill == SKILL_PASSSPIEL;
                break;

            case ITeam.TA_SPIELAUFBAU:
                isTrained = skill == SKILL_SPIELAUFBAU;
                break;

            case ITeam.TA_STANDARD:
                isTrained = skill == SKILL_STANDARDS;
                break;

            case ITeam.TA_TORWART:
                isTrained = skill == SKILL_TORWART;
                break;

            case ITeam.TA_VERTEIDIGUNG:
                isTrained = skill == SKILL_VERTEIDIGUNG;
                break;

            case TA_STEILPAESSE:
                isTrained = skill == SKILL_PASSSPIEL;
                break;

            case TA_ABWEHRVERHALTEN:
                isTrained = skill == SKILL_VERTEIDIGUNG;
                break;
        }

        return isTrained;
    }

    /**
     * Setter for property m_iSpezialitaet.
     *
     * @param m_iSpezialitaet New value of property m_iSpezialitaet.
     */
    public void setSpezialitaet(int m_iSpezialitaet) {
        this.m_iSpezialitaet = m_iSpezialitaet;
    }

    /**
     * Getter for property m_iSpezialitaet.
     *
     * @return Value of property m_iSpezialitaet.
     */
    public int getSpezialitaet() {
        return m_iSpezialitaet;
    }

    /**
     * Setter for property m_sSpezialitaet.
     *
     * @param m_sSpezialitaet New value of property m_sSpezialitaet.
     */
    public void setSpezialitaetString(java.lang.String m_sSpezialitaet) {
        this.m_sSpezialitaet = m_sSpezialitaet;
    }

    /**
     * Getter for property m_sSpezialitaet.
     *
     * @return Value of property m_sSpezialitaet.
     */
    public java.lang.String getSpezialitaetString() {
        return m_sSpezialitaet;
    }

    /**
     * Setter for property m_iSpielaufbau.
     *
     * @param m_iSpielaufbau New value of property m_iSpielaufbau.
     */
    public void setSpielaufbau(int m_iSpielaufbau) {
        this.m_iSpielaufbau = m_iSpielaufbau;
    }

    /**
     * Getter for property m_iSpielaufbau.
     *
     * @return Value of property m_iSpielaufbau.
     */
    public int getSpielaufbau() {
        return m_iSpielaufbau;
    }

    /**
     * setzt ob der User den Spieler zum Spiel zul�sst
     *
     * @param flag TODO Missing Constructuor Parameter Documentation
     */
    public void setSpielberechtigt(boolean flag) {
        m_bSpielberechtigt = Boolean.valueOf(flag);
        de.hattrickorganizer.database.DBZugriff.instance().saveSpielerSpielberechtigt(m_iSpielerID,
                                                                                      flag);
    }

    /**
     * gibt an ob der User den Spieler zum Spiel zul�sst
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isSpielberechtigt() {
        //Nur pr�fen, wenn nicht Spielberechtigt: Reduziert Zugriffe!
        if (m_bSpielberechtigt == null) {
            m_bSpielberechtigt = Boolean.valueOf(de.hattrickorganizer.database.DBZugriff.instance()
                                                                                        .getSpielerSpielberechtigt(m_iSpielerID));
        }

        return m_bSpielberechtigt.booleanValue();

        /*
           try
           {
               return database.DBZugriff.instance ().getSpielerSpielberechtigt ( m_iSpielerID );
           }
           catch ( Exception e )
           {
               return true;
           }
         **/
    }

    /**
     * Setter for property m_iSpielerID.
     *
     * @param m_iSpielerID New value of property m_iSpielerID.
     */
    public void setSpielerID(int m_iSpielerID) {
        this.m_iSpielerID = m_iSpielerID;
    }

    /**
     * Getter for property m_iSpielerID.
     *
     * @return Value of property m_iSpielerID.
     */
    public int getSpielerID() {
        return m_iSpielerID;
    }

    /**
     * Setter for property m_iStandards.
     *
     * @param m_iStandards New value of property m_iStandards.
     */
    public void setStandards(int m_iStandards) {
        this.m_iStandards = m_iStandards;
    }

    /**
     * Getter for property m_iStandards.
     *
     * @return Value of property m_iStandards.
     */
    public int getStandards() {
        return m_iStandards;
    }

    /**
     * berechnet den Subskill pro position
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getSubskill4Pos(int skill) {
        switch (skill) {
            case SKILL_TORWART:
                return Math.min(0.99f, (float) m_dSubTorwart);

            case SKILL_SPIELAUFBAU:
                return Math.min(0.99f, (float) m_dSubSpielaufbau);

            case SKILL_VERTEIDIGUNG:
                return Math.min(0.99f, (float) m_dSubVerteidigung);

            case SKILL_PASSSPIEL:
                return Math.min(0.99f, (float) m_dSubPasspiel);

            case SKILL_FLUEGEL:
                return Math.min(0.99f, (float) m_dSubFluegelspiel);

            case SKILL_TORSCHUSS:
                return Math.min(0.99f, (float) m_dSubTorschuss);

            case SKILL_STANDARDS:
                return Math.min(0.99f, (float) m_dSubStandards);

            default:
                return 0f;
        }
    }

    /**
     * berechnet den Subskill pro position
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getSubskill4SkillWithOffset(int skill) {
        float value = 0.0f;

        switch (skill) {
            case SKILL_TORWART:
                value = Math.min(0.99f, (float) m_dSubTorwart + (float) m_dTrainingsOffsetTorwart);
                break;

            case SKILL_SPIELAUFBAU:
                value = Math.min(0.99f,
                                 (float) m_dSubSpielaufbau + (float) m_dTrainingsOffsetSpielaufbau);
                break;

            case SKILL_VERTEIDIGUNG:
                value = Math.min(0.99f,
                                 (float) m_dSubVerteidigung
                                 + (float) m_dTrainingsOffsetVerteidigung);
                break;

            case SKILL_PASSSPIEL:
                value = Math.min(0.99f, (float) m_dSubPasspiel + (float) m_dTrainingsOffsetPasspiel);
                break;

            case SKILL_FLUEGEL:
                value = Math.min(0.99f,
                                 (float) m_dSubFluegelspiel
                                 + (float) m_dTrainingsOffsetFluegelspiel);
                break;

            case SKILL_TORSCHUSS:
                value = Math.min(0.99f,
                                 (float) m_dSubTorschuss + (float) m_dTrainingsOffsetTorschuss);
                break;

            case SKILL_STANDARDS:
                value = Math.min(0.99f,
                                 (float) m_dSubStandards + (float) m_dTrainingsOffsetStandards);
                break;

            default:
                return 0f;
        }

        return value;
    }

    /**
     * Setter for property m_sTeamInfoSmilie.
     *
     * @param teamInfoSmilie New value of property m_sTeamInfoSmilie.
     */
    public void setTeamInfoSmilie(String teamInfoSmilie) {
        if (teamInfoSmilie == null) {
            teamInfoSmilie = "";
        }

        m_sTeamInfoSmilie = teamInfoSmilie;
        de.hattrickorganizer.database.DBZugriff.instance().saveTeamInfoSmilie(m_iSpielerID,
                                                                              teamInfoSmilie);
    }

    /**
     * Getter for property m_sTeamInfoSmilie.
     *
     * @return Value of property m_sTeamInfoSmilie.
     */
    public java.lang.String getTeamInfoSmilie() {
        if (m_sTeamInfoSmilie == null) {
            m_sTeamInfoSmilie = de.hattrickorganizer.database.DBZugriff.instance()
                                                                       .getTeamInfoSmilie(m_iSpielerID);

            //Steht null in der DB?
            if (m_sTeamInfoSmilie == null) {
                m_sTeamInfoSmilie = "";
            }
        }

        //database.DBZugriff.instance ().getTeamInfoSmilie( m_iSpielerID );
        return m_sTeamInfoSmilie;
    }

    /**
     * Gibt das Datum des ersten HRFs mit dem Spieler zur�ck
     *
     * @return TODO Missing Return Method Documentation
     */
    public Timestamp getTimestamp4FirstPlayerHRF() {
        if (m_tsTime4FirstHRF == null) {
            m_tsTime4FirstHRF = de.hattrickorganizer.database.DBZugriff.instance()
                                                                       .getTimestamp4FirstPlayerHRF(m_iSpielerID);
        }

        return m_tsTime4FirstHRF;
    }

    /**
     * Setter for property m_iToreFreund.
     *
     * @param m_iToreFreund New value of property m_iToreFreund.
     */
    public void setToreFreund(int m_iToreFreund) {
        this.m_iToreFreund = m_iToreFreund;
    }

    /**
     * Getter for property m_iToreFreund.
     *
     * @return Value of property m_iToreFreund.
     */
    public int getToreFreund() {
        return m_iToreFreund;
    }

    /**
     * Setter for property m_iToreGesamt.
     *
     * @param m_iToreGesamt New value of property m_iToreGesamt.
     */
    public void setToreGesamt(int m_iToreGesamt) {
        this.m_iToreGesamt = m_iToreGesamt;
    }

    /**
     * Getter for property m_iToreGesamt.
     *
     * @return Value of property m_iToreGesamt.
     */
    public int getToreGesamt() {
        return m_iToreGesamt;
    }

    /**
     * Setter for property m_iToreLiga.
     *
     * @param m_iToreLiga New value of property m_iToreLiga.
     */
    public void setToreLiga(int m_iToreLiga) {
        this.m_iToreLiga = m_iToreLiga;
    }

    /**
     * Getter for property m_iToreLiga.
     *
     * @return Value of property m_iToreLiga.
     */
    public int getToreLiga() {
        return m_iToreLiga;
    }

    /**
     * Setter for property m_iTorePokal.
     *
     * @param m_iTorePokal New value of property m_iTorePokal.
     */
    public void setTorePokal(int m_iTorePokal) {
        this.m_iTorePokal = m_iTorePokal;
    }

    /**
     * Getter for property m_iTorePokal.
     *
     * @return Value of property m_iTorePokal.
     */
    public int getTorePokal() {
        return m_iTorePokal;
    }

    /**
     * Setter for property m_iTorschuss.
     *
     * @param m_iTorschuss New value of property m_iTorschuss.
     */
    public void setTorschuss(int m_iTorschuss) {
        this.m_iTorschuss = m_iTorschuss;
    }

    /**
     * Getter for property m_iTorschuss.
     *
     * @return Value of property m_iTorschuss.
     */
    public int getTorschuss() {
        return m_iTorschuss;
    }

    /**
     * Setter for property m_iTorwart.
     *
     * @param m_iTorwart New value of property m_iTorwart.
     */
    public void setTorwart(int m_iTorwart) {
        this.m_iTorwart = m_iTorwart;
    }

    /**
     * Getter for property m_iTorwart.
     *
     * @return Value of property m_iTorwart.
     */
    public int getTorwart() {
        return m_iTorwart;
    }

    /**
     * Setter for property m_iTrainer.
     *
     * @param m_iTrainer New value of property m_iTrainer.
     */
    public void setTrainer(int m_iTrainer) {
        this.m_iTrainer = m_iTrainer;
    }

    /**
     * Getter for property m_iTrainer.
     *
     * @return Value of property m_iTrainer.
     */
    public int getTrainer() {
        return m_iTrainer;
    }

    /**
     * gibt an ob der Spieler Trainer ist
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isTrainer() {
        return ((m_iTrainer > 0) && (m_iTrainerTyp >= 0));
    }

    /**
     * Setter for property m_iTrainerTyp.
     *
     * @param m_iTrainerTyp New value of property m_iTrainerTyp.
     */
    public void setTrainerTyp(int m_iTrainerTyp) {
        this.m_iTrainerTyp = m_iTrainerTyp;
    }

    /**
     * Getter for property m_iTrainerTyp.
     *
     * @return Value of property m_iTrainerTyp.
     */
    public int getTrainerTyp() {
        return m_iTrainerTyp;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param skill TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getTrainingOffset4Skill(int skill) {
        switch (skill) {
            case SKILL_TORWART:
                return m_dTrainingsOffsetTorwart;

            case SKILL_SPIELAUFBAU:
                return m_dTrainingsOffsetSpielaufbau;

            case SKILL_VERTEIDIGUNG:
                return m_dTrainingsOffsetVerteidigung;

            case SKILL_PASSSPIEL:
                return m_dTrainingsOffsetPasspiel;

            case SKILL_FLUEGEL:
                return m_dTrainingsOffsetFluegelspiel;

            case SKILL_TORSCHUSS:
                return m_dTrainingsOffsetTorschuss;

            case SKILL_STANDARDS:
                return m_dTrainingsOffsetStandards;

            default:
                return 0f;
        }
    }

    /**
     * Setter for property m_dTrainingsOffsetFluegelspiel.
     *
     * @param m_dTrainingsOffsetFluegelspiel New value of property m_dTrainingsOffsetFluegelspiel.
     */
    public void setTrainingsOffsetFluegelspiel(double m_dTrainingsOffsetFluegelspiel) {
        this.m_dTrainingsOffsetFluegelspiel = m_dTrainingsOffsetFluegelspiel;
    }

    /**
     * Getter for property m_dTrainingsOffsetFluegelspiel.
     *
     * @return Value of property m_dTrainingsOffsetFluegelspiel.
     */
    public double getTrainingsOffsetFluegelspiel() {
        return m_dTrainingsOffsetFluegelspiel;
    }

    /**
     * Setter for property m_dTrainingsOffsetPasspiel.
     *
     * @param m_dTrainingsOffsetPasspiel New value of property m_dTrainingsOffsetPasspiel.
     */
    public void setTrainingsOffsetPasspiel(double m_dTrainingsOffsetPasspiel) {
        this.m_dTrainingsOffsetPasspiel = m_dTrainingsOffsetPasspiel;
    }

    /**
     * Getter for property m_dTrainingsOffsetPasspiel.
     *
     * @return Value of property m_dTrainingsOffsetPasspiel.
     */
    public double getTrainingsOffsetPasspiel() {
        return m_dTrainingsOffsetPasspiel;
    }

    /**
     * Setter for property m_dTrainingsOffsetSpielaufbau.
     *
     * @param m_dTrainingsOffsetSpielaufbau New value of property m_dTrainingsOffsetSpielaufbau.
     */
    public void setTrainingsOffsetSpielaufbau(double m_dTrainingsOffsetSpielaufbau) {
        this.m_dTrainingsOffsetSpielaufbau = m_dTrainingsOffsetSpielaufbau;
    }

    /**
     * Getter for property m_dTrainingsOffsetSpielaufbau.
     *
     * @return Value of property m_dTrainingsOffsetSpielaufbau.
     */
    public double getTrainingsOffsetSpielaufbau() {
        return m_dTrainingsOffsetSpielaufbau;
    }

    /**
     * Setter for property m_dTrainingsOffsetStandards.
     *
     * @param m_dTrainingsOffsetStandards New value of property m_dTrainingsOffsetStandards.
     */
    public void setTrainingsOffsetStandards(double m_dTrainingsOffsetStandards) {
        this.m_dTrainingsOffsetStandards = m_dTrainingsOffsetStandards;
    }

    /**
     * Getter for property m_dTrainingsOffsetStandards.
     *
     * @return Value of property m_dTrainingsOffsetStandards.
     */
    public double getTrainingsOffsetStandards() {
        return m_dTrainingsOffsetStandards;
    }

    /**
     * Setter for property m_dTrainingsOffsetTorschuss.
     *
     * @param m_dTrainingsOffsetTorschuss New value of property m_dTrainingsOffsetTorschuss.
     */
    public void setTrainingsOffsetTorschuss(double m_dTrainingsOffsetTorschuss) {
        this.m_dTrainingsOffsetTorschuss = m_dTrainingsOffsetTorschuss;
    }

    /**
     * Getter for property m_dTrainingsOffsetTorschuss.
     *
     * @return Value of property m_dTrainingsOffsetTorschuss.
     */
    public double getTrainingsOffsetTorschuss() {
        return m_dTrainingsOffsetTorschuss;
    }

    /**
     * Setter for property m_dTrainingsOffsetTorwart.
     *
     * @param m_dTrainingsOffsetTorwart New value of property m_dTrainingsOffsetTorwart.
     */
    public void setTrainingsOffsetTorwart(double m_dTrainingsOffsetTorwart) {
        this.m_dTrainingsOffsetTorwart = m_dTrainingsOffsetTorwart;
    }

    /**
     * Getter for property m_dTrainingsOffsetTorwart.
     *
     * @return Value of property m_dTrainingsOffsetTorwart.
     */
    public double getTrainingsOffsetTorwart() {
        return m_dTrainingsOffsetTorwart;
    }

    /**
     * Setter for property m_dTrainingsOffsetVerteidigung.
     *
     * @param m_dTrainingsOffsetVerteidigung New value of property m_dTrainingsOffsetVerteidigung.
     */
    public void setTrainingsOffsetVerteidigung(double m_dTrainingsOffsetVerteidigung) {
        this.m_dTrainingsOffsetVerteidigung = m_dTrainingsOffsetVerteidigung;
    }

    /**
     * Getter for property m_dTrainingsOffsetVerteidigung.
     *
     * @return Value of property m_dTrainingsOffsetVerteidigung.
     */
    public double getTrainingsOffsetVerteidigung() {
        return m_dTrainingsOffsetVerteidigung;
    }

    /**
     * Setter for property m_iTransferlisted.
     *
     * @param m_iTransferlisted New value of property m_iTransferlisted.
     */
    public void setTransferlisted(int m_iTransferlisted) {
        this.m_iTransferlisted = m_iTransferlisted;
    }

    /**
     * Getter for property m_iTransferlisted.
     *
     * @return Value of property m_iTransferlisted.
     */
    public int getTransferlisted() {
        return m_iTransferlisted;
    }

    /**
     * Setter for property m_iTrikotnummer.
     *
     * @param m_iTrikotnummer New value of property m_iTrikotnummer.
     */
    public void setTrikotnummer(int m_iTrikotnummer) {
        this.m_iTrikotnummer = m_iTrikotnummer;
    }

    /**
     * Getter for property m_clFetchDate.
     *
     * @return Value of property m_clFetchDate.
     */

    /*    public java.sql.Timestamp getFetchDate ()
       {
           return m_clFetchDate;
       }
     */

    /**
     * Setter for property m_clFetchDate.
     *
     * @return TODO Missing Return Method Documentation
     */

    /*    public void setFetchDate (java.sql.Timestamp m_clFetchDate)
       {
           this.m_clFetchDate = m_clFetchDate;
       }
     */

    /**
     * parsed nen String ins DateFormat
     *
     * @return TODO Missing Return Method Documentation
     */

    /*    public void setFetchDateFromString( String date )
       {
           try
           {
               //Hattrick
               java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMANY );
               m_clFetchDate   =    new java.sql.Timestamp(  simpleFormat.parse ( date ).getTime () );
           }
           catch ( Exception e )
           {
               try
               {
                   //Hattrick
                   java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat ( "yyyy-MM-dd", java.util.Locale.GERMANY );
                   m_clFetchDate   =    new java.sql.Timestamp(  simpleFormat.parse ( date ).getTime () );
               }
               catch ( Exception ex )
               {
               }
           }
       }
     */

    /**
     * Getter for property m_iTrikotnummer.
     *
     * @return Value of property m_iTrikotnummer.
     */
    public int getTrikotnummer() {
        return m_iTrikotnummer;
    }

    /**
     * Setter for property m_iU20Laenderspiele.
     *
     * @param m_iU20Laenderspiele New value of property m_iU20Laenderspiele.
     */
    public void setU20Laenderspiele(int m_iU20Laenderspiele) {
        this.m_iU20Laenderspiele = m_iU20Laenderspiele;
    }

    /**
     * Getter for property m_iU20Laenderspiele.
     *
     * @return Value of property m_iU20Laenderspiele.
     */
    public int getU20Laenderspiele() {
        return m_iU20Laenderspiele;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param flag TODO Missing Method Parameter Documentation
     */
    public void setUserPosFlag(byte flag) {
        m_bUserPosFlag = flag;
        de.hattrickorganizer.database.DBZugriff.instance().saveSpielerUserPosFlag(m_iSpielerID,
                                                                                  m_bUserPosFlag);
    }

    /**
     * liefert User Notiz zum Spieler
     *
     * @return TODO Missing Return Method Documentation
     */
    public byte getUserPosFlag() {
        if (m_bUserPosFlag < SpielerPosition.UNBESTIMMT) {
            m_bUserPosFlag = de.hattrickorganizer.database.DBZugriff.instance()
                                                                    .getSpielerUserPosFlag(m_iSpielerID);
        }

        //database.DBZugriff.instance ().getSpielerNotiz ( m_iSpielerID );
        return m_bUserPosFlag;
    }

    /**
     * get Skillvalue 4 skill
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getValue4Skill4(int skill) {
        switch (skill) {
            case SKILL_TORWART:
                return m_iTorwart;

            case SKILL_SPIELAUFBAU:
                return m_iSpielaufbau;

            case SKILL_VERTEIDIGUNG:
                return m_iVerteidigung;

            case SKILL_PASSSPIEL:
                return m_iPasspiel;

            case SKILL_FLUEGEL:
                return m_iFluegelspiel;

            case SKILL_TORSCHUSS:
                return m_iTorschuss;

            case SKILL_STANDARDS:
                return m_iStandards;

            default:
                return 0;
        }
    }

    /**
     * Setter for property m_iVerletzt.
     *
     * @param m_iVerletzt New value of property m_iVerletzt.
     */
    public void setVerletzt(int m_iVerletzt) {
        this.m_iVerletzt = m_iVerletzt;
    }

    /**
     * Getter for property m_iVerletzt.
     *
     * @return Value of property m_iVerletzt.
     */
    public int getVerletzt() {
        return m_iVerletzt;
    }

    /**
     * Setter for property m_iVerteidigung.
     *
     * @param m_iVerteidigung New value of property m_iVerteidigung.
     */
    public void setVerteidigung(int m_iVerteidigung) {
        this.m_iVerteidigung = m_iVerteidigung;
    }

    /**
     * Getter for property m_iVerteidigung.
     *
     * @return Value of property m_iVerteidigung.
     */
    public int getVerteidigung() {
        return m_iVerteidigung;
    }

    /*
       Wetterabh�ngige Sonderereignisse
       Bestimmte Spezialf�higkeiten k�nnen in Zusammenhang mit einem bestimmten Wetter
        zu Sonderereignissen f�hren. Die Auswirkung dieses Sonderereignisses tritt
        von dem Zeitpunkt in Kraft, an dem es im Spielbericht erw�hnt wird,
        und hat bis zum Spielende Einflu� auf die Leistung des Spielers.
        Diese Auswirkung wird nach dem Spiel an der Spielerbewertung (Anzahl Sterne) sichtbar.
       Die Torschu�- und die Spielaufbau-F�higkeit von Ballzauberern kann sich bei Regen verschlechtern,
        w�hrend sich die gleichen F�higkeiten bei Sonnenschein verbessern k�nnen.
       Bei Regen gibt es die M�glichkeit, da� sich die Torschu�-, Verteidigungs- und Spielaufbau-F�higkeit
        von durchsetzungsstarken Spielern verbessert.
        Auf der anderen Seite kann sich die Torschu�f�higkeit bei Sonnenschein verschlechtern.
       Schnelle Spieler laufen bei Regen Gefahr, da� sich ihre Torschu�- und
        Verteidigungsf�higkeiten verschlechtern. Bei Sonnenschein besteht das Risiko
        , da� ihre Torschu�f�higkeit unter dem Wetter leidet.
     */
    /*
       Liefert die m�gliche Auswirkung des Wetters auf den Spieler
       return 0 bei keine auswirkung
       1 bei positiv
       -1 bei negativ
     */
    public int getWetterEffekt(int wetter) {
        return PlayerHelper.getWeatherEffect(wetter, m_iSpezialitaet);
    }

    /**
     * Liefert den Faktor den die Form auf die Spielerst�rke nimmt
     *
     * @return TODO Missing Return Method Documentation
     */
    public float calcFormFaktor() {
        return (1.0f - ((float) m_iForm / 8.0f)) * FormulaFactors.instance().getForm_Faktor();

        //return f - f * (1.0F - f2 / 8F) * f1;//f = effektive Player st�rke ohne Form

        /*
         *double playersForm = 1;
                           double y = A + B1 * playersForm + B2 * Math.pow( playersForm, 2 ) + B3 * Math.pow( playersForm, 3 ) + B4 * Math.pow( playersForm, 4 ) + B5 * Math.pow( playersForm, 5 );
         */
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param hrftimestamp TODO Missing Method Parameter Documentation
     * @param coTrainer TODO Missing Method Parameter Documentation
     * @param twTrainer TODO Missing Method Parameter Documentation
     * @param trainerlevel TODO Missing Method Parameter Documentation
     * @param intensitaet TODO Missing Method Parameter Documentation
     * @param staminaTrainingPart TODO Missing Method Parameter Documentation
     */
    public void calcFullSubskills(java.sql.Timestamp hrftimestamp, int coTrainer, int twTrainer,
                                  int trainerlevel, int intensitaet, int staminaTrainingPart) {
        final Spieler oldspieler = de.hattrickorganizer.database.DBZugriff.instance()
                                                                          .getSpielerBeforeDate(hrftimestamp,
                                                                                                getSpielerID());
        calcFullSubskills(oldspieler, coTrainer, twTrainer, trainerlevel, intensitaet, staminaTrainingPart, hrftimestamp);
    }

    /**
     * Berechnet die Subskills  Wird beim Speichern des HRFs in der Datenbank aufgerufen direkt
     * bevor die Spieler gespeichert werden.
     *
     * @param old TODO Missing Constructuor Parameter Documentation
     * @param coTrainer TODO Missing Constructuor Parameter Documentation
     * @param twTrainer TODO Missing Constructuor Parameter Documentation
     * @param trainerlevel TODO Missing Constructuor Parameter Documentation
     * @param intensitaet TODO Missing Constructuor Parameter Documentation
     * @param staminaTrainingPart TODO Missing Constructuor Parameter Documentation
     * @param hrftimestamp TODO Missing Constructuor Parameter Documentation
     */
    public void calcFullSubskills(ISpieler old, int coTrainer, int twTrainer, int trainerlevel,
                                  int intensitaet, int staminaTrainingPart, Timestamp hrftimestamp) {
        final plugins.ITrainingPerPlayer trainingPerPlayer = de.hattrickorganizer.logik.TrainingsManager.instance()
                                                                                                        .calculateFullTrainingForPlayer(this,
                                                                                                                                        de.hattrickorganizer.logik.TrainingsManager.instance()
                                                                                                                                                                                   .getTrainingsVector(),
                                                                                                                                        hrftimestamp);

        //TODO Training pro Woche berechenen
        //alten subskill holen und neuen addieren, jedoch nur wenn auch training stattfand seit dem letzten hrf ...
        if (!check4SkillUp(SKILL_TORWART, old)) {
            m_dSubTorwart = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getTW() / getTrainingLength(TORWART,
                                                                                                                          coTrainer,
                                                                                                                          twTrainer,
                                                                                                                          trainerlevel,
                                                                                                                          intensitaet,
                                                                                                                          staminaTrainingPart),
                                                                    2);

            if (m_dSubTorwart >= 1.0d) {
                m_dSubTorwart = 0.94d;

                //                m_dTrainingsOffsetTorwart   =   0.0d;
            }
        } else {
            m_dSubTorwart = 0.0d;
            m_dTrainingsOffsetTorwart = 0.0d;
        }

        if (!check4SkillUp(SKILL_VERTEIDIGUNG, old)) {
            m_dSubVerteidigung = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getVE() / getTrainingLength(VERTEIDIGUNG,
                                                                                                                               coTrainer,
                                                                                                                               twTrainer,
                                                                                                                               trainerlevel,
                                                                                                                               intensitaet,
                                                                                                                               staminaTrainingPart),
                                                                         2);

            if (m_dSubVerteidigung >= 1.0d) {
                m_dSubVerteidigung = 0.94d;

                //                m_dTrainingsOffsetVerteidigung   =   0.0d;
            }
        } else {
            m_dSubVerteidigung = 0.0d;
            m_dTrainingsOffsetVerteidigung = 0.0d;
        }

        if (!check4SkillUp(SKILL_SPIELAUFBAU, old)) {
            m_dSubSpielaufbau = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getSA() / getTrainingLength(SPIELAUFBAU,
                                                                                                                              coTrainer,
                                                                                                                              twTrainer,
                                                                                                                              trainerlevel,
                                                                                                                              intensitaet,
                                                                                                                              staminaTrainingPart),
                                                                        2);

            if (m_dSubSpielaufbau >= 1.0d) {
                m_dSubSpielaufbau = 0.94d;

                //                m_dTrainingsOffsetSpielaufbau   =   0.0d;
            }
        } else {
            m_dSubSpielaufbau = 0.0d;
            m_dTrainingsOffsetSpielaufbau = 0.0d;
        }

        if (!check4SkillUp(SKILL_PASSSPIEL, old)) {
            m_dSubPasspiel = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getPS() / getTrainingLength(PASSPIEL,
                                                                                                                           coTrainer,
                                                                                                                           twTrainer,
                                                                                                                           trainerlevel,
                                                                                                                           intensitaet,
                                                                                                                           staminaTrainingPart),
                                                                     2);

            if (m_dSubPasspiel >= 1.0d) {
                m_dSubPasspiel = 0.94d;

                //                m_dTrainingsOffsetPasspiel   =   0.0d;
            }
        } else {
            m_dSubPasspiel = 0.0d;
            m_dTrainingsOffsetPasspiel = 0.0d;
        }

        if (!check4SkillUp(SKILL_FLUEGEL, old)) {
            m_dSubFluegelspiel = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getFL() / getTrainingLength(FLUEGELSPIEL,
                                                                                                                               coTrainer,
                                                                                                                               twTrainer,
                                                                                                                               trainerlevel,
                                                                                                                               intensitaet,
                                                                                                                               staminaTrainingPart),
                                                                         2);

            if (m_dSubFluegelspiel >= 1.0d) {
                m_dSubFluegelspiel = 0.94d;

                //                m_dTrainingsOffsetFluegelspiel   =   0.0d;
            }
        } else {
            m_dSubFluegelspiel = 0.0d;
            m_dTrainingsOffsetFluegelspiel = 0.0d;
        }

        if (!check4SkillUp(SKILL_TORSCHUSS, old)) {
            m_dSubTorschuss = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getTS() / getTrainingLength(CHANCENVERWERTUNG,
                                                                                                                            coTrainer,
                                                                                                                            twTrainer,
                                                                                                                            trainerlevel,
                                                                                                                            intensitaet,
                                                                                                                            staminaTrainingPart),
                                                                      2);

            if (m_dSubTorschuss >= 1.0d) {
                m_dSubTorschuss = 0.94d;

                //                m_dTrainingsOffsetTorschuss   =   0.0d;
            }
        } else {
            m_dSubTorschuss = 0.0d;
            m_dTrainingsOffsetTorschuss = 0.0d;
        }

        if (!check4SkillUp(SKILL_STANDARDS, old)) {
            m_dSubStandards = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getST() / getTrainingLength(STANDARDS,
                                                                                                                            coTrainer,
                                                                                                                            twTrainer,
                                                                                                                            trainerlevel,
                                                                                                                            intensitaet,
                                                                                                                            staminaTrainingPart),
                                                                      2);

            if (m_dSubStandards >= 1.0d) {
                m_dSubStandards = 0.94d;

                //                m_dTrainingsOffsetStandards   =   0.0d;
            }
        } else {
            m_dSubStandards = 0.0d;
            m_dTrainingsOffsetStandards = 0.0d;
        }
    }

    /**
     * Berechnet die Subskills  Wird beim Speichern des HRFs in der Datenbank aufgerufen direkt
     * bevor die Spieler gespeichert werden.
     *
     * @param old TODO Missing Constructuor Parameter Documentation
     * @param coTrainer TODO Missing Constructuor Parameter Documentation
     * @param twTrainer TODO Missing Constructuor Parameter Documentation
     * @param trainerlevel TODO Missing Constructuor Parameter Documentation
     * @param intensitaet TODO Missing Constructuor Parameter Documentation
     * @param hrfID TODO Missing Constructuor Parameter Documentation
     */
    public void calcIncrementalSubskills(ISpieler old, int coTrainer, int twTrainer,
                                         int trainerlevel, int intensitaet, int staminaTrainingPart, int hrfID) {
        final ITrainingWeek trainingWeek = TrainingsWeekManager.instance().getTrainingWeek(hrfID);
        final plugins.ITrainingPerPlayer trainingPerPlayer = de.hattrickorganizer.logik.TrainingsManager.instance()
                                                                                                        .calculateWeeklyTrainingForPlayer(this,
                                                                                                                                          trainingWeek);

        if (old.getSpielerID() == 40591141) {
            HOLogger.instance().log(getClass(),trainingPerPlayer + " " + hrfID + " " + trainingWeek);
        }

        if (!check4SkillUp(SKILL_TORWART, old)) {
            m_dSubTorwart = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getTW() / getTrainingLength(TORWART,
                                                                                                                          coTrainer,
                                                                                                                          twTrainer,
                                                                                                                          trainerlevel,
                                                                                                                          intensitaet,
                                                                                                                          staminaTrainingPart),
                                                                    2);
            m_dSubTorwart = m_dSubTorwart + old.getSubskill4Pos(SKILL_TORWART);

            if (m_dSubTorwart >= 1.0d) {
                m_dSubTorwart = 0.94d;
            }
        } else {
            m_dSubTorwart = 0.0d;
            m_dTrainingsOffsetTorwart = 0.0d;
        }

        if (!check4SkillUp(SKILL_VERTEIDIGUNG, old)) {
            m_dSubVerteidigung = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getVE() / getTrainingLength(VERTEIDIGUNG,
                                                                                                                               coTrainer,
                                                                                                                               twTrainer,
                                                                                                                               trainerlevel,
                                                                                                                               intensitaet,
                                                                                                                               staminaTrainingPart),
                                                                         2);
            m_dSubVerteidigung = m_dSubVerteidigung + old.getSubskill4Pos(SKILL_VERTEIDIGUNG);

            if (m_dSubVerteidigung >= 1.0d) {
                m_dSubVerteidigung = 0.94d;
            }
        } else {
            m_dSubVerteidigung = 0.0d;
            m_dTrainingsOffsetVerteidigung = 0.0d;
        }

        if (!check4SkillUp(SKILL_SPIELAUFBAU, old)) {
            m_dSubSpielaufbau = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getSA() / getTrainingLength(SPIELAUFBAU,
                                                                                                                              coTrainer,
                                                                                                                              twTrainer,
                                                                                                                              trainerlevel,
                                                                                                                              intensitaet,
                                                                                                                              staminaTrainingPart),
                                                                        2);
            m_dSubSpielaufbau = m_dSubSpielaufbau + old.getSubskill4Pos(SKILL_SPIELAUFBAU);

            if (m_dSubSpielaufbau >= 1.0d) {
                m_dSubSpielaufbau = 0.94d;

                //				  m_dTrainingsOffsetSpielaufbau   =   0.0d;
            }
        } else {
            m_dSubSpielaufbau = 0.0d;
            m_dTrainingsOffsetSpielaufbau = 0.0d;
        }

        if (!check4SkillUp(SKILL_PASSSPIEL, old)) {
            m_dSubPasspiel = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getPS() / getTrainingLength(PASSPIEL,
                                                                                                                           coTrainer,
                                                                                                                           twTrainer,
                                                                                                                           trainerlevel,
                                                                                                                           intensitaet,
                                                                                                                           staminaTrainingPart),
                                                                     2);
            m_dSubPasspiel = m_dSubPasspiel + old.getSubskill4Pos(SKILL_PASSSPIEL);

            if (m_dSubPasspiel >= 1.0d) {
                m_dSubPasspiel = 0.94d;

                //				  m_dTrainingsOffsetPasspiel   =   0.0d;
            }
        } else {
            m_dSubPasspiel = 0.0d;
            m_dTrainingsOffsetPasspiel = 0.0d;
        }

        if (!check4SkillUp(SKILL_FLUEGEL, old)) {
            m_dSubFluegelspiel = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getFL() / getTrainingLength(FLUEGELSPIEL,
                                                                                                                               coTrainer,
                                                                                                                               twTrainer,
                                                                                                                               trainerlevel,
                                                                                                                               intensitaet,
                                                                                                                               staminaTrainingPart),
                                                                         2);
            m_dSubFluegelspiel = m_dSubFluegelspiel + old.getSubskill4Pos(SKILL_FLUEGEL);

            if (m_dSubFluegelspiel >= 1.0d) {
                m_dSubFluegelspiel = 0.94d;

                //				  m_dTrainingsOffsetFluegelspiel   =   0.0d;
            }
        } else {
            m_dSubFluegelspiel = 0.0d;
            m_dTrainingsOffsetFluegelspiel = 0.0d;
        }

        if (!check4SkillUp(SKILL_TORSCHUSS, old)) {
            m_dSubTorschuss = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getTS() / getTrainingLength(CHANCENVERWERTUNG,
                                                                                                                            coTrainer,
                                                                                                                            twTrainer,
                                                                                                                            trainerlevel,
                                                                                                                            intensitaet,
                                                                                                                            staminaTrainingPart),
                                                                      2);
            m_dSubTorschuss = m_dSubTorschuss + old.getSubskill4Pos(SKILL_TORSCHUSS);

            if (m_dSubTorschuss >= 1.0d) {
                m_dSubTorschuss = 0.94d;

                //				  m_dTrainingsOffsetTorschuss   =   0.0d;
            }
        } else {
            m_dSubTorschuss = 0.0d;
            m_dTrainingsOffsetTorschuss = 0.0d;
        }

        if (!check4SkillUp(SKILL_STANDARDS, old)) {
            m_dSubStandards = de.hattrickorganizer.tools.Helper.round(trainingPerPlayer.getST() / getTrainingLength(STANDARDS,
                                                                                                                            coTrainer,
                                                                                                                            twTrainer,
                                                                                                                            trainerlevel,
                                                                                                                            intensitaet,
                                                                                                                            staminaTrainingPart),
                                                                      2);
            m_dSubStandards = m_dSubStandards + old.getSubskill4Pos(SKILL_STANDARDS);

            if (m_dSubStandards >= 1.0d) {
                m_dSubStandards = 0.94d;

                //				  m_dTrainingsOffsetStandards   =   0.0d;
            }
        } else {
            m_dSubStandards = 0.0d;
            m_dTrainingsOffsetStandards = 0.0d;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Helper
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * liefert den Kapit�nswert f�r den Spieler
     *
     * @return TODO Missing Return Method Documentation
     */
    public float calcKapitaensValue() {
        return ((getFuehrung() * 0.7f) + (getErfahrung() * 0.3f));
    }

    /**
     * berechnet die Effektive St�rke eines Spielers f�r �bergene  Positionsfaktoren, ohne
     * Formber�cksichtigung
     *
     * @param fo TODO Missing Constructuor Parameter Documentation
     * @param normalized Ratings must be normalized or not?
     *
     * @return TODO Missing Return Method Documentation
     */
    public float calcPosValue(FactorObject fo, boolean normalized) {
        if ((fo == null) || (fo.getSum() == 0.0f)) {
            return -1.0f;
        }

        final float koeffizient = (1.0f - ((float) m_iKondition / 8.0f));

        float twValue = fo.getTorwartScaled(normalized) * (m_iTorwart
                        + getSubskill4SkillWithOffset(SKILL_TORWART));
        twValue -= (twValue * (koeffizient * FormulaFactors.instance().m_fTW_Kondi_Faktor));

        float spValue = (fo.getSpielaufbauScaled(normalized) * (m_iSpielaufbau
                        + getSubskill4SkillWithOffset(SKILL_SPIELAUFBAU)));
        spValue -= (spValue * (koeffizient * FormulaFactors.instance().m_fSP_Kondi_Faktor));

        float veValue = (fo.getVerteidigungScaled(normalized) * (m_iVerteidigung
                        + getSubskill4SkillWithOffset(SKILL_VERTEIDIGUNG)));
        veValue -= (veValue * (koeffizient * FormulaFactors.instance().m_fVE_Kondi_Faktor));

        float flValue = (fo.getFluegelspielScaled(normalized) * (m_iFluegelspiel
                        + getSubskill4SkillWithOffset(SKILL_FLUEGEL)));
        flValue -= (flValue * (koeffizient * FormulaFactors.instance().m_fFL_Kondi_Faktor));


        float paValue = (fo.getPasspielScaled(normalized) * (m_iPasspiel
                        + getSubskill4SkillWithOffset(SKILL_PASSSPIEL)));
		// Fix for new Defensive Attacker position
		if (fo.getPosition()==ISpielerPosition.STURM_DEF && getSpezialitaet()==ISpieler.BALLZAUBERER) {
			paValue = paValue * 1.5f;
		}
        paValue -= (paValue * (koeffizient * FormulaFactors.instance().m_fPS_Kondi_Faktor));



        float stValue = (fo.getStandardsScaled(normalized) * (m_iStandards
                        + getSubskill4SkillWithOffset(SKILL_STANDARDS)));
        stValue -= (stValue * (koeffizient * FormulaFactors.instance().m_fST_Kondi_Faktor));

        float toValue = (fo.getTorschussScaled(normalized) * (m_iTorschuss
                        + getSubskill4SkillWithOffset(SKILL_TORSCHUSS)));
        toValue -= (toValue * (koeffizient * FormulaFactors.instance().m_fTS_Kondi_Faktor));

        return twValue + spValue + veValue + flValue + paValue + stValue + toValue;
    }

    /**
     * berechnet die St�rke eines Spieler f�r angegebene Position
     *
     * @param pos die Position
     * @param mitForm gibt an ob Form ber�cksichtigt werden soll
     *
     * @return TODO Missing Return Method Documentation
     */
    public float calcPosValue(byte pos, boolean mitForm) {
    	float es = -1.0f;
    	final FactorObject factor = FormulaFactors.instance().getPositionFactor(pos);

    	if(factor != null)
    		es = calcPosValue(factor,false);
    	 else{
    		 //	For Coach or factor not found return 0
    		 return 0.0f;
    	 }


        if ((es != -1.0f) && (mitForm)) {
            es -= (es * calcFormFaktor());

            //Erfahrung Bonus
            es += getErfahrungsBonus(es);
        }

        return de.hattrickorganizer.tools.Helper.round(es / 2.0f,
                                                       gui.UserParameter.instance().anzahlNachkommastellen);
    }

    /**
     * Copy old player offset status
     *
     * @param old
     */
    public void copySubSkills(ISpieler old) {
        if (!check4SkillUp(SKILL_TORWART, old)) {
            m_dSubTorwart = old.getSubskill4Pos(SKILL_TORWART);
            m_dTrainingsOffsetTorwart = old.getTrainingsOffsetTorwart();
        } else {
            m_dSubTorwart = 0.0d;
            m_dTrainingsOffsetTorwart = 0.0d;
        }

        if (!check4SkillUp(SKILL_VERTEIDIGUNG, old)) {
            m_dSubVerteidigung = old.getSubskill4Pos(SKILL_VERTEIDIGUNG);
            m_dTrainingsOffsetVerteidigung = old.getTrainingsOffsetVerteidigung();
        } else {
            m_dSubVerteidigung = 0.0d;
            m_dTrainingsOffsetVerteidigung = 0.0d;
        }

        if (!check4SkillUp(SKILL_SPIELAUFBAU, old)) {
            m_dSubSpielaufbau = old.getSubskill4Pos(SKILL_SPIELAUFBAU);
            m_dTrainingsOffsetSpielaufbau = old.getTrainingsOffsetSpielaufbau();
        } else {
            m_dSubSpielaufbau = 0.0d;
            m_dTrainingsOffsetSpielaufbau = 0.0d;
        }

        if (!check4SkillUp(SKILL_PASSSPIEL, old)) {
            m_dSubPasspiel = old.getSubskill4Pos(SKILL_PASSSPIEL);
            m_dTrainingsOffsetPasspiel = old.getTrainingsOffsetPasspiel();
        } else {
            m_dSubPasspiel = 0.0d;
            m_dTrainingsOffsetPasspiel = 0.0d;
        }

        if (!check4SkillUp(SKILL_FLUEGEL, old)) {
            m_dSubFluegelspiel = old.getSubskill4Pos(SKILL_FLUEGEL);
            m_dTrainingsOffsetFluegelspiel = old.getTrainingsOffsetFluegelspiel();
        } else {
            m_dSubFluegelspiel = 0.0d;
            m_dTrainingsOffsetFluegelspiel = 0.0d;
        }

        if (!check4SkillUp(SKILL_TORSCHUSS, old)) {
            m_dSubTorschuss = old.getSubskill4Pos(SKILL_TORSCHUSS);
            m_dTrainingsOffsetTorschuss = old.getTrainingsOffsetTorschuss();
        } else {
            m_dSubTorschuss = 0.0d;
            m_dTrainingsOffsetTorschuss = 0.0d;
        }

        if (!check4SkillUp(SKILL_STANDARDS, old)) {
            m_dSubStandards = old.getSubskill4Pos(SKILL_STANDARDS);
            m_dTrainingsOffsetStandards = old.getTrainingsOffsetStandards();
        } else {
            m_dSubStandards = 0.0d;
            m_dTrainingsOffsetStandards = 0.0d;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //equals
    /////////////////////////////////////////////////////////////////////////////////
    public boolean equals(Object obj) {
        boolean equals = false;

        if (obj instanceof Spieler) {
            equals = ((Spieler) obj).getSpielerID() == m_iSpielerID;
        }

        return equals;
    }

    /**
     * internal calculation of training duration
     *
     * @param dauer TODO Missing Method Parameter Documentation
     * @param alter TODO Missing Method Parameter Documentation
     * @param trTyp TODO Missing Method Parameter Documentation
     * @param anzTrainer TODO Missing Method Parameter Documentation
     * @param trainerLvl TODO Missing Method Parameter Documentation
     * @param intensitaet TODO Missing Method Parameter Documentation
     * @param staminaTrainingPart TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
//    protected double calcTraining1(double dauer, int alter, int anzTrainer, int trainerLvl, int intensitaet) {
//        //return dauer + Math.round( dauer * ( ( ( alter - 17 ) * gui.UserParameter.instance ().AlterFaktor ) + ( ( 7 - trainerLvl ) * gui.UserParameter.instance ().TrainerFaktor ) + ( ( 10 - anzTrainer ) * gui.UserParameter.instance ().CoTrainerFaktor ) +  ( ( 100 - intensitaet ) * gui.UserParameter.instance ().IntensitaetFaktor ) ) );
//        float aBonus = 0;
//        float trBonus = 0;
//        float coBonus = 0;
//        float intBonus = 0;
//
//        //f�r alter �ber 19 1 woche drauf
//        if (alter == 19) {
//            //19 J�hrige brauchen in etwa 1 Woche mehr...
//            aBonus = 0.6f;
//        } else if (alter > 19) {
//            //faktor optimal 1.0
//            aBonus = (alter - 19) * gui.UserParameter.instance().AlterFaktor;
//        }
//
//        if (trainerLvl < 8) {
//            //faktor optimal 1.0
//            trBonus = (7 - trainerLvl) * gui.UserParameter.instance().TrainerFaktor;
//        }
//
//        coBonus = (Helper.getLogarithmus(11, 10)
//                  - Helper.getLogarithmus(anzTrainer + 1, 10)) * gui.UserParameter.instance().CoTrainerFaktor;
//
//        //faktor optimal 0.1
//        intBonus = ((100 - intensitaet) * gui.UserParameter.instance().IntensitaetFaktor);
//
//        return dauer + aBonus + trBonus + coBonus + intBonus;
//    }

    // new training caculator for testing
    protected double calcTraining(double dauer, int alter, int anzTrainer, int trainerLvl,
                               int intensitaet, int staminaTrainingPart) {
        float trBonus = 0;
        float coBonus = 0;
        double intBonus = 0;
        double weeks = 0;
        // percentage increase per year should be different for every TriningType and passed as argument!
        double INCREASE = 8;

        // at the moment this can be modified by the simple (single) options Faktor...
        double percentage = INCREASE * gui.UserParameter.instance().AlterFaktor;

        // THIS SHOULD BE REPLACED BY THE TRAINING MODEL FUNCTION!
        weeks = dauer * Math.pow(1d+percentage/100d, (double)(alter - 17));

        if (trainerLvl < 8) {
            //faktor optimal 1.0
            trBonus = (7 - trainerLvl) * gui.UserParameter.instance().TrainerFaktor;
        }

        coBonus = (Helper.getLogarithmus(11, 10)
                  - Helper.getLogarithmus(anzTrainer + 1, 10)) * gui.UserParameter.instance().CoTrainerFaktor;

        //faktor optimal 0.1
        intBonus = ((100 - (intensitaet * (100d-staminaTrainingPart)/100d)) * gui.UserParameter.instance().IntensitaetFaktor);
        return weeks + trBonus + coBonus + intBonus;
    }

    /**
     * pr�ft ob Skillup vorliegt
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     * @param oldPlayer TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected boolean check4SkillUp(int skill, ISpieler oldPlayer) {
        return PlayerHelper.check4SkillUp(skill, oldPlayer, this);
    }
}
