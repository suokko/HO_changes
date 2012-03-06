// %1607162836:de.hattrickorganizer.model%
/*
 * Spieler.java
 *
 * Created on 17. März 2003, 15:41
 */
package ho.core.model;

import ho.core.db.DBManager;
import ho.core.rating.RatingPredictionManager;
import ho.core.training.TrainingsManager;
import ho.core.training.TrainingsWeekManager;
import ho.core.util.HOLogger;
import ho.core.util.Helper;
import ho.core.util.PlayerHelper;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import plugins.IEPVData;
import plugins.ISpieler;
import plugins.ISpielerPosition;
import plugins.ITrainingWeek;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public final class Spieler implements plugins.ISpieler {
    //~ Class fields -------------------------------------------------------------------------------

	/** Cache for star ratings (Hashtable<String, Float>) */
    private static Hashtable<String,Object> starRatingCache = new Hashtable<String,Object>();

    //~ Instance fields ----------------------------------------------------------------------------

    /** Spielberechtigt */
    private Boolean m_bSpielberechtigt;

    /** AgressivitätName */
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

    /** SpezialitätName */
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

    /** Agressivität */
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

    /** Führungsqualität */
    private int m_iFuehrung = 1;

    /** Gehalt */
    private int m_iGehalt = 1;

    /** Gelbe Karten */
    private int m_iGelbeKarten;

    /** Hattricks */
    private int m_iHattrick;
    
    /** Home Grown */
    private boolean m_bHomeGrown = false;

    /** Kondition */
    private int m_iKondition = 1;

    /** Länderspiele */
    private int m_iLaenderspiele;

    //Cache

    /** Letzte Bewertung */
    private int m_iLastBewertung = -1;

    /** Loyalty */
    private int m_iLoyalty = 0;

    /** Markwert */
    private int m_iTSI;

    /** bonus in Prozent */

    //protected int       m_iBonus            =   0;

    /** Aus welchem Land kommt der Spieler */
    private int m_iNationalitaet = 49;

    /** Passpiel */
    private int m_iPasspiel = 1;

    /** SpezialitätID */
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

    /** Trainerfähigkeit */
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

    /** Länderspiele */
    private int m_iU20Laenderspiele;

    /** Verletzt Wochen */
    private int m_iVerletzt = -1;

    /** Verteidigung */
    private int m_iVerteidigung = 1;

    /** Training block */
    private boolean m_bTrainingBlock = false;

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
            m_sName = DBManager.deleteEscapeSequences(rs.getString("Name"));
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
            m_sSpezialitaet = DBManager.deleteEscapeSequences(rs.getString("sSpezialitaet"));
            m_iCharakter = rs.getInt("iCharakter");
            m_sCharakter = DBManager.deleteEscapeSequences(rs.getString("sCharakter"));
            m_iAnsehen = rs.getInt("iAnsehen");
            m_sAnsehen = DBManager.deleteEscapeSequences(rs.getString("sAnsehen"));
            m_iAgressivitaet = rs.getInt("iAgressivitaet");
            m_sAgressivitaet = DBManager.deleteEscapeSequences(rs.getString("sAgressivitaet"));
            m_iErfahrung = rs.getInt("Erfahrung");
            m_iLoyalty = rs.getInt("Loyalty");
            m_bHomeGrown = rs.getBoolean("HomeGrown");
            m_iFuehrung = rs.getInt("Fuehrung");
            m_iGehalt = rs.getInt("Gehalt");
            m_iNationalitaet = rs.getInt("Land");
            m_iTSI = rs.getInt("Marktwert");

            //TSI, alles vorher durch 1000 teilen
            m_clhrfDate = rs.getTimestamp("Datum");

            if (m_clhrfDate.before(DBManager.TSIDATE)) {
                m_iTSI /= 1000d;
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

            // Training block
            m_bTrainingBlock = rs.getBoolean("TrainingBlock");
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
    	// Separate first, nick and last names are available. Utilize them?
    	
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
        m_bHomeGrown = Boolean.parseBoolean(properties.getProperty("homegr", "FALSE"));
        m_iLoyalty = Integer.parseInt(properties.getProperty("loy", "0"));
        m_iFuehrung = Integer.parseInt(properties.getProperty("led", "0"));
        m_iGehalt = Integer.parseInt(properties.getProperty("sal", "0"));
        m_iNationalitaet = Integer.parseInt(properties.getProperty("countryid", "0"));
        m_iTSI = Integer.parseInt(properties.getProperty("mkt", "0"));

        //TSI, alles vorher durch 1000 teilen
        m_clhrfDate = hrfdate;

        if (hrfdate.before(DBManager.TSIDATE)) {
            m_iTSI /= 1000d;
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
        final ho.core.model.HOModel oldmodel = ho.core.model.HOVerwaltung.instance()
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

            // Training block
            m_bTrainingBlock = oldSpieler.hasTrainingBlock();

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
     * liefert das Datum des letzen LevelAufstiegs für den angeforderten Skill Vector filled with
     * object[] [0] = Time der Änderung [1] = Boolean: false=Keine Änderung gefunden
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector<Object[]> getAllLevelUp(int skill) {
        return DBManager.instance().getAllLevelUp(skill,m_iSpielerID);
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
      	long hrftime = HOVerwaltung.instance().getModel().getBasics().getDatum().getTime();
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
     * 			format is "YY.DDD"
     */
    public String getAlterWithAgeDaysAsString() {
    	// format = yy.ddd
      	long hrftime = HOVerwaltung.instance().getModel().getBasics().getDatum().getTime();
    	long now = new Date().getTime();
    	long diff = (now - hrftime) / (1000*60*60*24);
    	int years = getAlter();
    	int days = getAgeDays();
    	days += diff;
    	while (days > 111) {
    		days -= 112;
    		years++;
    	}
    	String retVal = years + "." + days;
    	return retVal;
    }

    /**
     * Get the full i18n'd string represention the players age. Includes
     * the birthay indicator as well.
     * @return the full i18n'd string represention the players age
     */
    public String getAgeStringFull() {
      	long hrftime = HOVerwaltung.instance().getModel().getBasics().getDatum().getTime();
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
    	ret.append(HOVerwaltung.instance().getLanguageString("age.years"));
    	ret.append(" ");
    	ret.append(days);
    	ret.append(" ");
    	ret.append(HOVerwaltung.instance().getLanguageString("age.days"));
    	if (birthday) {
    		ret.append(" (");
    		ret.append(HOVerwaltung.instance().getLanguageString("age.birthday"));
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

    /////////////////////////////////////////////////7
    //Training ( reflected to skills)
    //TORSCHUSS is skipped due it trains two skills, won't be displayed in gui. Real calc for this is done in Trainingsmanager
    //TA_EXTERNALATTACK skipped due it trains two skills, calc must be done in trainingmanager for each skill seperate like Torschuss.no display in options gui.
    // @return duration of training based on settings and tr type, calls calcTraining for nested calculation
    ///////////////////////////////////////////////////77
    public double getTrainingLength(int trTyp, int coTrainer, int trainerLvl, int intensitaet, int staminaTrainingPart) {
      
    	switch (trTyp) {
            case TORWART:
            	// Blaghaid sends coTrainer rather than the never present twTrainers
                return calcTraining(gui.UserParameter.instance().TRAINING_OFFSET_GOALKEEPING + TrainingsManager.BASE_DURATION_GOALKEEPING, 
                		m_iAlter, coTrainer, trainerLvl, intensitaet, staminaTrainingPart, getTorwart());

            case SPIELAUFBAU:
                return calcTraining(gui.UserParameter.instance().TRAINING_OFFSET_PLAYMAKING + TrainingsManager.BASE_DURATION_PLAYMAKING,
                		m_iAlter, coTrainer, trainerLvl, intensitaet, staminaTrainingPart, getSpielaufbau());

            case FLUEGELSPIEL:
                return calcTraining(gui.UserParameter.instance().TRAINING_OFFSET_WINGER + TrainingsManager.BASE_DURATION_WINGER,
                		m_iAlter, coTrainer, trainerLvl, intensitaet, staminaTrainingPart, getFluegelspiel());

            case CHANCENVERWERTUNG:
                return calcTraining(gui.UserParameter.instance().TRAINING_OFFSET_SCORING + TrainingsManager.BASE_DURATION_SCORING,
                		m_iAlter, coTrainer, trainerLvl, intensitaet, staminaTrainingPart, getTorschuss());

            case VERTEIDIGUNG:
                return calcTraining(gui.UserParameter.instance().TRAINING_OFFSET_DEFENDING + TrainingsManager.BASE_DURATION_DEFENDING,
                		m_iAlter, coTrainer, trainerLvl, intensitaet, staminaTrainingPart, getVerteidigung());

            case PASSPIEL:
				return calcTraining(gui.UserParameter.instance().TRAINING_OFFSET_PASSING + TrainingsManager.BASE_DURATION_PASSING,
						m_iAlter, coTrainer, trainerLvl, intensitaet, staminaTrainingPart, getPasspiel());

            case TA_STEILPAESSE:
                return calcTraining((100d/85d)*(gui.UserParameter.instance().TRAINING_OFFSET_PASSING + TrainingsManager.BASE_DURATION_PASSING), 
                		m_iAlter, coTrainer, trainerLvl, intensitaet, staminaTrainingPart, getPasspiel());

            case STANDARDS:
                return calcTraining(gui.UserParameter.instance ().TRAINING_OFFSET_SETPIECES + TrainingsManager.BASE_DURATION_SET_PIECES,
                		m_iAlter, coTrainer, trainerLvl, intensitaet, staminaTrainingPart, getStandards());

            case TA_ABWEHRVERHALTEN:
                return calcTraining(2 * (gui.UserParameter.instance().TRAINING_OFFSET_DEFENDING + TrainingsManager.BASE_DURATION_DEFENDING),
                		m_iAlter, coTrainer, trainerLvl, intensitaet, staminaTrainingPart, getVerteidigung());

			case TA_EXTERNALATTACK:
				return calcTraining((100d/60d) * (gui.UserParameter.instance().TRAINING_OFFSET_WINGER + TrainingsManager.BASE_DURATION_WINGER),
						m_iAlter, coTrainer, trainerLvl, intensitaet, staminaTrainingPart, getFluegelspiel());

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
     * get the experience bonus
     *
     * @param experience effective experience to calculate the bonus, use the xp from the player if set to 0
     *
     * @return experience bonus in percent
     */
    public float getErfahrungsBonus(float experience) {
        if (experience <= 0)
        	// take xp from player (use medium xp sub, i.e. add 0.5)
        	experience = m_iErfahrung + 0.5f;

        // normalize xp [1,20] -> [0,19]
        experience -= 1;

		if ( experience <= 0 )
			return 0; /*If experience is non-existent, the bonus is zero!*/

		// Use hardcorded values here,
		// make sure to apply the same values as in prediction/*/playerStrength.dat
		//
		// We return the experience bonus in percent (0% = no bonus, 100% = doubled player strength...)
		float bonus = (float) (0.0716 * Math.sqrt(experience));

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
     * Setter for m_bHomeGrown
     *
     * @return Value of property m_bHomeGrown.
     */
    public void setHomeGrown(boolean hg) {
    	m_bHomeGrown = hg;
    }

    /**
     * Getter for m_bHomeGrown
     *
     * @return Value of property m_bHomeGrown
     */
    public boolean isHomeGrown() {
    	return m_bHomeGrown;
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
     * liefert die Stärke für die IdealPosition
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

        if (flag == ISpielerPosition.UNKNOWN) {
            final FactorObject[] allPos = FormulaFactors.instance().getAllObj();
            byte idealPos = ISpielerPosition.UNKNOWN;
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
     * liefert das Datum des letzen LevelAufstiegs für den angeforderten Skill [0] = Time der
     * Änderung [1] = Boolean: false=Keine Änderung gefunden
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Object[] getLastLevelUp(int skill) {
        return DBManager.instance().getLastLevelUp(skill, m_iSpielerID);
    }

    /**
     * liefert die vergangenen Tage seit dem letzem LevelAufstieg für den angeforderten Skill
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
     * Gibt die Letzte Bewertung zurück, die der Spieler bekommen hat
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getLetzteBewertung() {
        if (m_iLastBewertung < 0) {
            m_iLastBewertung = DBManager.instance().getLetzteBewertung4Spieler(m_iSpielerID);
        }

        return m_iLastBewertung;
    }

    
    /**
     * Returns the loyalty stat
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getLoyalty() {
    	return m_iLoyalty;
    }
    
    /**
     * Sets the loyalty stat
     *
     * @return TODO Missing Return Method Documentation
     */
    public void  setLoyalty(int loy) {
    	m_iLoyalty = loy;
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
        DBManager.instance().saveManuellerSmilie(m_iSpielerID, manuellerSmilie);
    }

    /**
     * Getter for property m_sManuellerSmilie.
     *
     * @return Value of property m_sManuellerSmilie.
     */
    public java.lang.String getManuellerSmilie() {
        if (m_sManuellerSmilie == null) {
            m_sManuellerSmilie = DBManager.instance().getManuellerSmilie(m_iSpielerID);

            //Steht null in der DB?
            if (m_sManuellerSmilie == null) {
                m_sManuellerSmilie = "";
            }
        }

        //database.DBZugriff.instance ().getManuellerSmilie( m_iSpielerID );
        return m_sManuellerSmilie;
    }

    /**
     * Sets the TSI (aka Marktwert)
     *
     * @param m_iTSI New value of property m_iMarkwert.
     * @deprecated Use setTSI()
     */
    @Deprecated
	public void setMarkwert(int m_iTSI) {
        this.m_iTSI = m_iTSI;
    }

    /**
     * Returns the TSI (aka Marktwert)
     *
     * @return Value of property m_iMarkwert.
     * @deprecated use getTSI()
     */
    @Deprecated
	public int getMarkwert() {
        return m_iTSI;
    }

    /**
     * Sets the TSI
     *
     * @param m_iTSI New value of property m_iMarkwert.
     */
    public void setTSI(int m_iTSI) {
        this.m_iTSI = m_iTSI;
    }

    /**
     * Returns the TSI
     *
     * @return Value of property m_iMarkwert.
     */
    public int getTSI() {
        return m_iTSI;
    }

    /**
     * Returns the estimated value of this player (EPV)
     *
     * @return EPV
     */
    public double getEPV() {
		IEPVData data = HOVerwaltung.instance().getModel().getEPV().getEPVData(this);
		return HOVerwaltung.instance().getModel().getEPV().getPrice(data);
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
        return DBManager.deleteEscapeSequences(m_sName);
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
        DBManager.instance().saveSpielerNotiz(m_iSpielerID, notiz);
    }

    /**
     * liefert User Notiz zum Spieler
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getNotiz() {
        if (m_sNotiz == null) {
            m_sNotiz = DBManager.instance().getSpielerNotiz(m_iSpielerID);
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
     * Zum speichern! Die Reduzierung des Marktwerts auf TSI wird rückgängig gemacht
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getSaveMarktwert() {
        if (m_clhrfDate == null || m_clhrfDate.before(DBManager.TSIDATE)) {
            //Echter Marktwert
            return m_iTSI * 1000;
        }

        //TSI
        return m_iTSI;
    }

    /**
     * gibt an ob der angeforderte Skill beim diesem Trainingstyp trainiert wird
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     * @param trainingstype TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     * @deprecated Never used, better system integrated in Trainingmanager
     */
    @Deprecated
	public boolean isSkillTrained(int skill, int trainingstype) {
        return false;
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
     * setzt ob der User den Spieler zum Spiel zulässt
     *
     * @param flag TODO Missing Constructuor Parameter Documentation
     */
    public void setSpielberechtigt(boolean flag) {
        m_bSpielberechtigt = Boolean.valueOf(flag);
        DBManager.instance().saveSpielerSpielberechtigt(m_iSpielerID,
                                                                                      flag);
    }

    /**
     * gibt an ob der User den Spieler zum Spiel zulässt
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean isSpielberechtigt() {
        //Nur pr�fen, wenn nicht Spielberechtigt: Reduziert Zugriffe!
        if (m_bSpielberechtigt == null) {
            m_bSpielberechtigt = Boolean.valueOf(DBManager.instance().getSpielerSpielberechtigt(m_iSpielerID));
        }

        return m_bSpielberechtigt.booleanValue();

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

            case SKILL_LOYALTY:
            	value = 0;
            
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
        DBManager.instance().saveTeamInfoSmilie(m_iSpielerID, teamInfoSmilie);
    }

    /**
     * Getter for property m_sTeamInfoSmilie.
     *
     * @return Value of property m_sTeamInfoSmilie.
     */
    public java.lang.String getTeamInfoSmilie() {
        if (m_sTeamInfoSmilie == null) {
            m_sTeamInfoSmilie = DBManager.instance().getTeamInfoSmilie(m_iSpielerID);

            //Steht null in der DB?
            if (m_sTeamInfoSmilie == null) {
                m_sTeamInfoSmilie = "";
            }
        }

        //database.DBZugriff.instance ().getTeamInfoSmilie( m_iSpielerID );
        return m_sTeamInfoSmilie;
    }

    /**
     * Gibt das Datum des ersten HRFs mit dem Spieler zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public Timestamp getTimestamp4FirstPlayerHRF() {
        if (m_tsTime4FirstHRF == null) {
            m_tsTime4FirstHRF = DBManager.instance().getTimestamp4FirstPlayerHRF(m_iSpielerID);
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
        DBManager.instance().saveSpielerUserPosFlag(m_iSpielerID, m_bUserPosFlag);
    }

    /**
     * liefert User Notiz zum Spieler
     *
     * @return TODO Missing Return Method Documentation
     */
    public byte getUserPosFlag() {
        if (m_bUserPosFlag < SpielerPosition.UNKNOWN) {
            m_bUserPosFlag = DBManager.instance().getSpielerUserPosFlag(m_iSpielerID);
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

            case SKILL_KONDITION:
                return m_iKondition;

            case SKILL_EXPIERIENCE:
                return m_iErfahrung;

            case SKILL_FORM:
                return m_iForm;

            case SKILL_LEADERSHIP:
                return m_iFuehrung;
                
            case SKILL_LOYALTY:
            	return m_iLoyalty;

            default:
                return 0;
        }
    }

    /**
     * set Skillvalue 4 skill
     *
     * @param skill the skill to change
     * @param value the new skill value
     */
	public void setValue4Skill4(int skill, int value) {
		switch (skill) {
			case SKILL_TORWART :
				setTorwart(value);
				break;

			case SKILL_SPIELAUFBAU :
				setSpielaufbau(value);
				break;

			case SKILL_PASSSPIEL :
				setPasspiel(value);
				break;

			case SKILL_FLUEGEL :
				setFluegelspiel(value);
				break;

			case SKILL_VERTEIDIGUNG :
				setVerteidigung(value);
				break;

			case SKILL_TORSCHUSS :
				setTorschuss(value);
				break;

			case SKILL_STANDARDS :
				setStandards(value);
				break;

			case SKILL_KONDITION :
				setKondition(value);
				break;

			case SKILL_EXPIERIENCE:
				setErfahrung(value);
				break;

			case SKILL_FORM:
				setForm(value);
				break;

			case SKILL_LEADERSHIP:
				setFuehrung(value);
				break;
				
			case SKILL_LOYALTY:
				setLoyalty(value);
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
       Wetterabhängige Sonderereignisse
       Bestimmte Spezialfähigkeiten können in Zusammenhang mit einem bestimmten Wetter
        zu Sonderereignissen führen. Die Auswirkung dieses Sonderereignisses tritt
        von dem Zeitpunkt in Kraft, an dem es im Spielbericht erwähnt wird,
        und hat bis zum Spielende Einfluß auf die Leistung des Spielers.
        Diese Auswirkung wird nach dem Spiel an der Spielerbewertung (Anzahl Sterne) sichtbar.
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
    public int getWetterEffekt(int wetter) {
        return PlayerHelper.getWeatherEffect(wetter, m_iSpezialitaet);
    }

    /**
     * Berechnet die Subskills  Wird beim Speichern des HRFs in der Datenbank aufgerufen direkt
     * bevor die Spieler gespeichert werden.
     *
     * @param old TODO Missing Constructuor Parameter Documentation
     * @param coTrainer TODO Missing Constructuor Parameter Documentation
     * @param trainerlevel TODO Missing Constructuor Parameter Documentation
     * @param intensitaet TODO Missing Constructuor Parameter Documentation
     * @param staminaTrainingPart TODO Missing Constructuor Parameter Documentation
     * @param hrftimestamp TODO Missing Constructuor Parameter Documentation
     */
    public void calcFullSubskills(ISpieler old, int coTrainer, int trainerlevel,
                                  int intensitaet, int staminaTrainingPart, Timestamp hrftimestamp) {
        final plugins.ITrainingPerPlayer trainingPerPlayer = ho.core.training.TrainingsManager.instance()
        		.calculateFullTrainingForPlayer(this, ho.core.training.TrainingsManager.instance().getTrainingsVector(), hrftimestamp);

        //TODO Training pro Woche berechenen
        //alten subskill holen und neuen addieren, jedoch nur wenn auch training stattfand seit dem letzten hrf ...
        if (!check4SkillUp(SKILL_TORWART, old)) {
            m_dSubTorwart = ho.core.util.Helper.round(trainingPerPlayer.getTW() / getTrainingLength(TORWART,
                                                                                                                          coTrainer,
                                                                                                                          trainerlevel,
                                                                                                                          intensitaet,
                                                                                                                          staminaTrainingPart),
                                                                    2);

            if (m_dSubTorwart >= 1.0d) {
                m_dSubTorwart = 0.99d;

                //                m_dTrainingsOffsetTorwart   =   0.0d;
            }
        } else {
            m_dSubTorwart = 0.0d;
            m_dTrainingsOffsetTorwart = 0.0d;
        }

        if (!check4SkillUp(SKILL_VERTEIDIGUNG, old)) {
            m_dSubVerteidigung = ho.core.util.Helper.round(trainingPerPlayer.getVE() / getTrainingLength(VERTEIDIGUNG,
                                                                                                                               coTrainer,
                                                                                                                               trainerlevel,
                                                                                                                               intensitaet,
                                                                                                                               staminaTrainingPart),
                                                                         2);

            if (m_dSubVerteidigung >= 1.0d) {
                m_dSubVerteidigung = 0.99d;

                //                m_dTrainingsOffsetVerteidigung   =   0.0d;
            }
        } else {
            m_dSubVerteidigung = 0.0d;
            m_dTrainingsOffsetVerteidigung = 0.0d;
        }

        if (!check4SkillUp(SKILL_SPIELAUFBAU, old)) {
            m_dSubSpielaufbau = ho.core.util.Helper.round(trainingPerPlayer.getSA() / getTrainingLength(SPIELAUFBAU,
                                                                                                                              coTrainer,
                                                                                                                              trainerlevel,
                                                                                                                              intensitaet,
                                                                                                                              staminaTrainingPart),
                                                                        2);

            if (m_dSubSpielaufbau >= 1.0d) {
                m_dSubSpielaufbau = 0.99d;

                //                m_dTrainingsOffsetSpielaufbau   =   0.0d;
            }
        } else {
            m_dSubSpielaufbau = 0.0d;
            m_dTrainingsOffsetSpielaufbau = 0.0d;
        }

        if (!check4SkillUp(SKILL_PASSSPIEL, old)) {
            m_dSubPasspiel = ho.core.util.Helper.round(trainingPerPlayer.getPS() / getTrainingLength(PASSPIEL,
                                                                                                                           coTrainer,
                                                                                                                           trainerlevel,
                                                                                                                           intensitaet,
                                                                                                                           staminaTrainingPart),
                                                                     2);

            if (m_dSubPasspiel >= 1.0d) {
                m_dSubPasspiel = 0.99d;

                //                m_dTrainingsOffsetPasspiel   =   0.0d;
            }
        } else {
            m_dSubPasspiel = 0.0d;
            m_dTrainingsOffsetPasspiel = 0.0d;
        }

        if (!check4SkillUp(SKILL_FLUEGEL, old)) {
            m_dSubFluegelspiel = ho.core.util.Helper.round(trainingPerPlayer.getFL() / getTrainingLength(FLUEGELSPIEL,
                                                                                                                               coTrainer,
                                                                                                                               trainerlevel,
                                                                                                                               intensitaet,
                                                                                                                               staminaTrainingPart),
                                                                         2);

            if (m_dSubFluegelspiel >= 1.0d) {
                m_dSubFluegelspiel = 0.99d;

                //                m_dTrainingsOffsetFluegelspiel   =   0.0d;
            }
        } else {
            m_dSubFluegelspiel = 0.0d;
            m_dTrainingsOffsetFluegelspiel = 0.0d;
        }

        if (!check4SkillUp(SKILL_TORSCHUSS, old)) {
            m_dSubTorschuss = ho.core.util.Helper.round(trainingPerPlayer.getTS() / getTrainingLength(CHANCENVERWERTUNG,
                                                                                                                            coTrainer,
                                                                                                                            trainerlevel,
                                                                                                                            intensitaet,
                                                                                                                            staminaTrainingPart),
                                                                      2);

            if (m_dSubTorschuss >= 1.0d) {
                m_dSubTorschuss = 0.99d;

                //                m_dTrainingsOffsetTorschuss   =   0.0d;
            }
        } else {
            m_dSubTorschuss = 0.0d;
            m_dTrainingsOffsetTorschuss = 0.0d;
        }

        if (!check4SkillUp(SKILL_STANDARDS, old)) {
            m_dSubStandards = ho.core.util.Helper.round(trainingPerPlayer.getST() / getTrainingLength(STANDARDS,
                                                                                                                            coTrainer,
                                                                                                                            trainerlevel,
                                                                                                                            intensitaet,
                                                                                                                            staminaTrainingPart),
                                                                      2);

            if (m_dSubStandards >= 1.0d) {
                m_dSubStandards = 0.99d;

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
     * @param trainerlevel TODO Missing Constructuor Parameter Documentation
     * @param intensitaet TODO Missing Constructuor Parameter Documentation
     * @param hrfID TODO Missing Constructuor Parameter Documentation
     */
    public void calcIncrementalSubskills(ISpieler old, int coTrainer, 
                                         int trainerlevel, int intensitaet, int staminaTrainingPart, int hrfID) {
    	final ITrainingWeek trainingWeek = TrainingsWeekManager.instance().getTrainingWeek(hrfID);
        final plugins.ITrainingPerPlayer trainingPerPlayer =
        	ho.core.training.TrainingsManager.instance().calculateWeeklyTrainingForPlayer(this, trainingWeek);

        if (!check4SkillUp(SKILL_TORWART, old)) {
            m_dSubTorwart = ho.core.util.Helper.round(trainingPerPlayer.getTW() / getTrainingLength(TORWART,
                                                                                                                          coTrainer,
                                                                                                                          trainerlevel,
                                                                                                                          intensitaet,
                                                                                                                          staminaTrainingPart),
                                                                    2);
            m_dSubTorwart = m_dSubTorwart + old.getSubskill4Pos(SKILL_TORWART);

            if (m_dSubTorwart >= 1.0d) {
                m_dSubTorwart = 0.99d;
            }
        } else {
            m_dSubTorwart = 0.0d;
            m_dTrainingsOffsetTorwart = 0.0d;
        }

        if (!check4SkillUp(SKILL_VERTEIDIGUNG, old)) {
            m_dSubVerteidigung = ho.core.util.Helper.round(trainingPerPlayer.getVE() / getTrainingLength(VERTEIDIGUNG,
                                                                                                                               coTrainer,
                                                                                                                               trainerlevel,
                                                                                                                               intensitaet,
                                                                                                                               staminaTrainingPart),
                                                                         2);
            m_dSubVerteidigung = m_dSubVerteidigung + old.getSubskill4Pos(SKILL_VERTEIDIGUNG);

            if (m_dSubVerteidigung >= 1.0d) {
                m_dSubVerteidigung = 0.99d;
            }
        } else {
            m_dSubVerteidigung = 0.0d;
            m_dTrainingsOffsetVerteidigung = 0.0d;
        }

        if (!check4SkillUp(SKILL_SPIELAUFBAU, old)) {
            m_dSubSpielaufbau = ho.core.util.Helper.round(trainingPerPlayer.getSA() / getTrainingLength(SPIELAUFBAU,
                                                                                                                              coTrainer,
                                                                                                                              trainerlevel,
                                                                                                                              intensitaet,
                                                                                                                              staminaTrainingPart),
                                                                        2);
            m_dSubSpielaufbau = m_dSubSpielaufbau + old.getSubskill4Pos(SKILL_SPIELAUFBAU);

            if (m_dSubSpielaufbau >= 1.0d) {
                m_dSubSpielaufbau = 0.99d;

                //				  m_dTrainingsOffsetSpielaufbau   =   0.0d;
            }
        } else {
            m_dSubSpielaufbau = 0.0d;
            m_dTrainingsOffsetSpielaufbau = 0.0d;
        }

        if (!check4SkillUp(SKILL_PASSSPIEL, old)) {
            m_dSubPasspiel = ho.core.util.Helper.round(trainingPerPlayer.getPS() / getTrainingLength(PASSPIEL,
                                                                                                                           coTrainer,
                                                                                                                           trainerlevel,
                                                                                                                           intensitaet,
                                                                                                                           staminaTrainingPart),
                                                                     2);
            m_dSubPasspiel = m_dSubPasspiel + old.getSubskill4Pos(SKILL_PASSSPIEL);

            if (m_dSubPasspiel >= 1.0d) {
                m_dSubPasspiel = 0.99d;

                //				  m_dTrainingsOffsetPasspiel   =   0.0d;
            }
        } else {
            m_dSubPasspiel = 0.0d;
            m_dTrainingsOffsetPasspiel = 0.0d;
        }

        if (!check4SkillUp(SKILL_FLUEGEL, old)) {
            m_dSubFluegelspiel = ho.core.util.Helper.round(trainingPerPlayer.getFL() / getTrainingLength(FLUEGELSPIEL,
                                                                                                                               coTrainer,
                                                                                                                               trainerlevel,
                                                                                                                               intensitaet,
                                                                                                                               staminaTrainingPart),
                                                                         2);
            m_dSubFluegelspiel = m_dSubFluegelspiel + old.getSubskill4Pos(SKILL_FLUEGEL);

            if (m_dSubFluegelspiel >= 1.0d) {
                m_dSubFluegelspiel = 0.99d;

                //				  m_dTrainingsOffsetFluegelspiel   =   0.0d;
            }
        } else {
            m_dSubFluegelspiel = 0.0d;
            m_dTrainingsOffsetFluegelspiel = 0.0d;
        }

        if (!check4SkillUp(SKILL_TORSCHUSS, old)) {
            m_dSubTorschuss = ho.core.util.Helper.round(trainingPerPlayer.getTS() / getTrainingLength(CHANCENVERWERTUNG,
                                                                                                                            coTrainer,
                                                                                                                            trainerlevel,
                                                                                                                            intensitaet,
                                                                                                                            staminaTrainingPart),
                                                                      2);
            m_dSubTorschuss = m_dSubTorschuss + old.getSubskill4Pos(SKILL_TORSCHUSS);

            if (m_dSubTorschuss >= 1.0d) {
                m_dSubTorschuss = 0.99d;

                //				  m_dTrainingsOffsetTorschuss   =   0.0d;
            }
        } else {
            m_dSubTorschuss = 0.0d;
            m_dTrainingsOffsetTorschuss = 0.0d;
        }

        if (!check4SkillUp(SKILL_STANDARDS, old)) {
            m_dSubStandards = ho.core.util.Helper.round(trainingPerPlayer.getST() / getTrainingLength(STANDARDS,
                                                                                                                            coTrainer,
                                                                                                                            trainerlevel,
                                                                                                                            intensitaet,
                                                                                                                            staminaTrainingPart),
                                                                      2);
            m_dSubStandards = m_dSubStandards + old.getSubskill4Pos(SKILL_STANDARDS);

            if (m_dSubStandards >= 1.0d) {
                m_dSubStandards = 0.99d;

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
     * Calculate the player strength on a specific lineup position
     * with or without form
     *
     * @param fo 		FactorObject with the skill weights for this position
     * @param useForm	consider form?
     *
     * @return 			the player strength on this position
     */
    public float calcPosValue(FactorObject fo, boolean useForm) {
        if ((fo == null) || (fo.getSum() == 0.0f)) {
            return -1.0f;
        }

        // The stars formulas are changed by the user -> clear the cache
        if (!starRatingCache.containsKey("lastChange") || ((Date)starRatingCache.get("lastChange")).before(FormulaFactors.getLastChange())) {
//    		System.out.println ("Clearing stars cache");
        	starRatingCache.clear();
        	starRatingCache.put("lastChange", new Date());
        }
        /**
         * Create a key for the Hashtable cache
         * We cache every star rating to speed up calculation
         * (calling RPM.calcPlayerStrength() is quite expensive and
         * this method is used very often)
         */
        
        float loy = RatingPredictionManager.getLoyaltyHomegrownBonus(this);
        
        String key = fo.getPosition() + ":"
        					+ Helper.round(getTorwart() + getSubskill4SkillWithOffset(SKILL_TORWART) + loy, 2) + "|"
        					+ Helper.round(getSpielaufbau() + getSubskill4SkillWithOffset(SKILL_SPIELAUFBAU) + loy, 2) + "|"
        					+ Helper.round(getVerteidigung() + getSubskill4SkillWithOffset(SKILL_VERTEIDIGUNG) + loy, 2) + "|"
        					+ Helper.round(getFluegelspiel() + getSubskill4SkillWithOffset(SKILL_FLUEGEL) + loy, 2) + "|"
        					+ Helper.round(getPasspiel() + getSubskill4SkillWithOffset(SKILL_PASSSPIEL) + loy, 2) + "|"
        					+ Helper.round(getStandards() + getSubskill4SkillWithOffset(SKILL_STANDARDS) + loy, 2) + "|"
        					+ Helper.round(getTorschuss() + getSubskill4SkillWithOffset(SKILL_TORSCHUSS) + loy, 2) + "|"
        					+ getForm() + "|"
        					+ getKondition() + "|"
        					+ getErfahrung() + "|"
        					// We need to add the specialty, because of Technical DefFW
        					+ getSpezialitaet();

        // Check if the key already exists in cache
        if (starRatingCache.containsKey(key)) {
//        	System.out.println ("Using star rating from cache, key="+key+", tablesize="+starRatingCache.size());
        	return ((Float)starRatingCache.get(key)).floatValue();
        }
    	final boolean normalized = false;

        float gkValue = fo.getTorwartScaled(normalized) * RatingPredictionManager.calcPlayerStrength(this, SKILL_TORWART, useForm);

        float pmValue = fo.getSpielaufbauScaled(normalized) * RatingPredictionManager.calcPlayerStrength(this, SKILL_SPIELAUFBAU, useForm);

        float deValue = fo.getVerteidigungScaled(normalized) * RatingPredictionManager.calcPlayerStrength(this, SKILL_VERTEIDIGUNG, useForm);

        float wiValue = fo.getFluegelspielScaled(normalized) * RatingPredictionManager.calcPlayerStrength(this, SKILL_FLUEGEL, useForm);

        float psValue = fo.getPasspielScaled(normalized) * RatingPredictionManager.calcPlayerStrength(this, SKILL_PASSSPIEL, useForm);

        // Fix for new Defensive Attacker position
		if (fo.getPosition()==ISpielerPosition.FORWARD_DEF && getSpezialitaet()==ISpieler.BALLZAUBERER) {
			psValue *= 1.30f;
		}

        float spValue = fo.getStandardsScaled(normalized) * RatingPredictionManager.calcPlayerStrength(this, SKILL_STANDARDS, useForm);

        float scValue = fo.getTorschussScaled(normalized) * RatingPredictionManager.calcPlayerStrength(this, SKILL_TORSCHUSS, useForm);

        float val = gkValue + pmValue + deValue + wiValue + psValue + spValue + scValue;

        // Put to cache
        starRatingCache.put(key, new Float(val));
//    	System.out.println ("Star rating put to cache, key="+key+", val="+val+", tablesize="+starRatingCache.size());
        return val;
    }

    /**
     * Calculate the player strength on a specific lineup position
     * with or without form
     *
     * @param pos		position from ISpielerPosition (TORWART.. POS_ZUS_INNENV)
     * @param useForm	consider form?
     *
     * @return 			the player strength on this position
     */
    public float calcPosValue(byte pos, boolean useForm) {
    	float es = -1.0f;
    	final FactorObject factor = FormulaFactors.instance().getPositionFactor(pos);

    	if(factor != null)
    		es = calcPosValue(factor, useForm);
    	 else{
    		 //	For Coach or factor not found return 0
    		 return 0.0f;
    	 }

        return ho.core.util.Helper.round(es / 2.0f,
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
    @Override
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
     * @param baseLength base length
     * 		(i.e. the normalized length for a skillup for a 17Y trainee,
     * 		solid coach, 10 co, 100% TI, 0% Stamina)
     * @param age player's age
     * @param trTyp training type
     * @param cotrainer number of assistants
     * @param trainerLvl trainer level
     * @param intensitaet training intensity
     * @param staminaTrainingPart stamina share
     *
     * @return length for a single skillup
     */
    protected static double calcTraining(double baseLength, int age, int cotrainer, int trainerLvl,
                               int intensitaet, int staminaTrainingPart, int curSkill) {
//    	System.out.println ("calcTraining for "+getName()+", base="+baseLength+", alter="+age+", anzCo="+cotrainer+", train="+trainerLvl+", ti="+intensitaet+", ss="+staminaTrainingPart+", curSkill="+curSkill);
    	double ageFactor = Math.pow(1.0404, age-17) * (gui.UserParameter.instance().TRAINING_OFFSET_AGE + TrainingsManager.BASE_AGE_FACTOR);
//    	double skillFactor = 1 + Math.log((curSkill+0.5)/7) / Math.log(5);
    	double skillFactor = - 1.4595 * Math.pow((curSkill+1d)/20, 2) + 3.7535 * (curSkill+1d)/20 - 0.1349d;
    	if (skillFactor < 0)
    		skillFactor = 0;
    	double trainerFactor = (1 + (7 - Math.min(trainerLvl, 7.5)) * 0.091) * (gui.UserParameter.instance().TrainerFaktor + TrainingsManager.BASE_COACH_FACTOR);
    	double coFactor = (1 + (Math.log(11)/Math.log(10) - Math.log(cotrainer+1)/Math.log(10)) * 0.2749) * (gui.UserParameter.instance().TRAINING_OFFSET_ASSISTANTS + TrainingsManager.BASE_ASSISTANT_COACH_FACTOR);
    	double tiFactor = Double.MAX_VALUE;
    	if (intensitaet > 0)
    		tiFactor = (1 / (intensitaet/100d)) * (gui.UserParameter.instance().TRAINING_OFFSET_INTENSITY + TrainingsManager.BASE_INTENSITY_FACTOR);
    	double staminaFactor = Double.MAX_VALUE;
    	if (staminaTrainingPart < 100)
    		staminaFactor = 1 / (1 - staminaTrainingPart/100d);
    	double trainLength = baseLength * ageFactor * skillFactor * trainerFactor * coFactor * tiFactor * staminaFactor;
    	if (trainLength < 1)
    		trainLength = 1;
//    	System.out.println ("Factors for "+getName()+": "+ageFactor+"/"+skillFactor+"/"+trainerFactor+"/"+coFactor+"/"+tiFactor+"/"+staminaFactor+" -> "+trainLength);
    	return trainLength;
    }

    /**
     * prüft ob Skillup vorliegt
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     * @param oldPlayer TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected boolean check4SkillUp(int skill, ISpieler oldPlayer) {
        return PlayerHelper.check4SkillUp(skill, oldPlayer, this);
    }

    /**
     * Does this player have a training block?
     * @return training block
     */
    public boolean hasTrainingBlock () {
    	return m_bTrainingBlock;
    }

    /**
     * Set the training block of this player (true/false)
     * @param isBlocked	new value
     */
    public void setTrainingBlock (boolean isBlocked) {
    	this.m_bTrainingBlock = isBlocked;
    }

}
