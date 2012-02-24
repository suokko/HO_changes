// %233313029:de.hattrickorganizer.model%
package ho.core.model;

import ho.core.db.DBManager;
import ho.core.util.HOLogger;

import java.sql.ResultSet;
import java.util.Properties;


/**
 * Enthält die Daten des Teams (nicht der Spieler!)
 */
public final class Team implements plugins.ITeam {
	// ~ Instance fields
	// ----------------------------------------------------------------------------

	/** confidence */
	private String m_sSelbstvertrauen = "";

	/** team spirit */
	private String m_sStimmung = "";

	/** training type */
	private String m_sTrainingsArt = "";

	/** formation xp 343 */
	private int formationXp343;

	/** formation xp 352 */
	private int formationXp352;

	/** formation xp 433 */
	private int formationXp433;

	/** formation xp 451 */
	private int formationXp451;

	/** formation xp 532 */
	private int formationXp532;

	/** formation xp 541 */
	private int formationXp541;

	/** formation xp 442 */
	private int formationXp442;

	/** formation xp 523 */
	private int formationXp523;

	/** formation xp 550 */
	private int formationXp550;

	/** formation xp 253 */
	private int formationXp253;

	/** Selbstvertrauen */
	private int m_iSelbstvertrauen;

	/** Stimmung */
	private int m_iStimmungInt;

	private int subStimmung;

	/** TrainingsArt */
	private int m_iTrainingsArt;

	// //////////////////////////////////////////////////////////////////////////////
	// Member
	// //////////////////////////////////////////////////////////////////////////////

	/** Trainingsintensität */
	private int m_iTrainingslevel;

	private int m_iStaminaTrainingPart;

	// ~ Constructors
	// -------------------------------------------------------------------------------

	// //////////////////////////////////////////////////////////////////////////////
	// Konstruktor
	// //////////////////////////////////////////////////////////////////////////////
	public Team(Properties properties) throws Exception {
		m_iTrainingslevel = Integer.parseInt(properties.getProperty("trlevel", "0"));
		m_iStaminaTrainingPart = Integer.parseInt(properties.getProperty("staminatrainingpart", "0"));
		m_sTrainingsArt = properties.getProperty("trtype", "");
		m_iStimmungInt = Integer.parseInt(properties.getProperty("stamningvalue", "0"));
		m_sStimmung = properties.getProperty("stamning", "");
		m_sSelbstvertrauen = properties.getProperty("sjalvfortroende", "");
		m_iSelbstvertrauen = Integer.parseInt(properties.getProperty("sjalvfortroendevalue", "0"));
		formationXp433 = Integer.parseInt(properties.getProperty("exper433", "0"));
		formationXp451 = Integer.parseInt(properties.getProperty("exper451", "0"));
		formationXp352 = Integer.parseInt(properties.getProperty("exper352", "0"));
		formationXp532 = Integer.parseInt(properties.getProperty("exper532", "0"));
		formationXp343 = Integer.parseInt(properties.getProperty("exper343", "0"));
		formationXp541 = Integer.parseInt(properties.getProperty("exper541", "0"));
		formationXp442 = Integer.parseInt(properties.getProperty("exper442", "0"));
		formationXp523 = Integer.parseInt(properties.getProperty("exper523", "0"));
		formationXp550 = Integer.parseInt(properties.getProperty("exper550", "0"));
		formationXp253 = Integer.parseInt(properties.getProperty("exper253", "0"));
		m_iTrainingsArt = Integer.parseInt(properties.getProperty("trtypevalue", "-1"));
		subStimmung = 2;
	}

	/**
	 * Creates a new Team object.
	 */
	public Team(ResultSet rs) throws Exception {
		m_iTrainingslevel = rs.getInt("TrainingsIntensitaet");
		m_iStaminaTrainingPart = rs.getInt("StaminaTrainingPart");
		m_sTrainingsArt = DBManager.deleteEscapeSequences(rs.getString("sTrainingsArt"));
		m_sStimmung = DBManager.deleteEscapeSequences(rs.getString("sStimmung"));
		m_sSelbstvertrauen = DBManager.deleteEscapeSequences(rs.getString("sSelbstvertrauen"));
		m_iSelbstvertrauen = rs.getInt("iSelbstvertrauen");
		m_iStimmungInt = rs.getInt("iStimmung");
		formationXp433 = rs.getInt("iErfahrung433");
		formationXp451 = rs.getInt("iErfahrung451");
		formationXp352 = rs.getInt("iErfahrung352");
		formationXp532 = rs.getInt("iErfahrung532");
		formationXp343 = rs.getInt("iErfahrung343");
		formationXp541 = rs.getInt("iErfahrung541");
		try {
			formationXp442 = rs.getInt("iErfahrung442");
			formationXp523 = rs.getInt("iErfahrung523");
			formationXp550 = rs.getInt("iErfahrung550");
			formationXp253 = rs.getInt("iErfahrung253");
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), "Error(Team rs): " + e);
		}
		m_iTrainingsArt = rs.getInt("TrainingsArt");
		subStimmung = 2;
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------

	public String getNameForSelfConfidence(int level) {
		return getNameForSelbstvertrauen(level);
	}

	public String getNameForTeamSpirit(int level) {
		return getNameForStimmung(level);
	}

	/**
	 * Get the name for a confidence contant.
	 */
	public static String getNameForSelbstvertrauen(int level) {
		switch (level) {
		case SV_voellig_abgehoben:
			return HOVerwaltung.instance().getLanguageString("extremely_exaggerated");

		case SV_voellig_uebertrieben:
			return HOVerwaltung.instance().getLanguageString("completely_exaggerated");

		case SV_etwas_ueberheblich:
			return HOVerwaltung.instance().getLanguageString("slightly_exaggerated");

		case SV_sehr_gross:
			return HOVerwaltung.instance().getLanguageString("wonderful");

		case SV_stark:
			return HOVerwaltung.instance().getLanguageString("strong");

		case SV_bescheiden:
			return HOVerwaltung.instance().getLanguageString("decent");

		case SV_gering:
			return HOVerwaltung.instance().getLanguageString("poor");

		case SV_armselig:
			return HOVerwaltung.instance().getLanguageString("wretched");

		case SV_katastrophal:
			return HOVerwaltung.instance().getLanguageString("disastrous");

		case SV_nichtVorhanden:
			return HOVerwaltung.instance().getLanguageString("nonexisting");

		default:
			return HOVerwaltung.instance().getLanguageString("Unbestimmt");
		}
	}

	/**
	 * Get the name for a team spirit contant.
	 */
	public static String getNameForStimmung(int level) {
		switch (level) {
		case TS_paradiesisch:
			return HOVerwaltung.instance().getLanguageString("ts_Paradise_on_Earth");

		case TS_auf_Wolke_sieben:
			return HOVerwaltung.instance().getLanguageString("ts_Walking_on_Clouds");

		case TS_euphorisch:
			return HOVerwaltung.instance().getLanguageString("ts_delirious");

		case TS_ausgezeichnet:
			return HOVerwaltung.instance().getLanguageString("ts_satisfied");

		case TS_gut:
			return HOVerwaltung.instance().getLanguageString("ts_content");

		case TS_zufrieden:
			return HOVerwaltung.instance().getLanguageString("ts_calm");

		case TS_ruhig:
			return HOVerwaltung.instance().getLanguageString("ts_composed");

		case TS_irritiert:
			return HOVerwaltung.instance().getLanguageString("ts_irritated");

		case TS_wuetend:
			return HOVerwaltung.instance().getLanguageString("ts_furious");

		case TS_blutruenstig:
			return HOVerwaltung.instance().getLanguageString("ts_murderous");

		case TS_wie_im_kalten_Krieg:
			return HOVerwaltung.instance().getLanguageString("ts_like_the_cold_War");

		default: {
			if (level > TS_paradiesisch) {
				return HOVerwaltung.instance().getLanguageString("ts_Paradise_on_Earth");
			}

			return HOVerwaltung.instance().getLanguageString("Unbestimmt");
		}
		}
	}

	// //////////////////////////////////////////////////////////////////////////////
	// Static
	// //////////////////////////////////////////////////////////////////////////////

	/**
	 * Get the name for a training type constant.
	 */
	public static String getNameForTraining(int type) {
		switch (type) {

		// What about missing training types?
		// Defensive Positions, Wing Attacks, Through Passes
		case TA_TORWART:
			return HOVerwaltung.instance().getLanguageString("training.goalkeeping");

		case TA_SPIELAUFBAU:
			return HOVerwaltung.instance().getLanguageString("training.playmaking");

		case TA_PASSSPIEL:
			return HOVerwaltung.instance().getLanguageString("training.short_passes");

		case TA_SCHUSSTRAINING:
			return HOVerwaltung.instance().getLanguageString("training.shooting");

		case TA_FLANKEN:
			return HOVerwaltung.instance().getLanguageString("training.crossing");

		case TA_CHANCEN:

			// Fehlt
			return HOVerwaltung.instance().getLanguageString("training.scoring");

		case TA_VERTEIDIGUNG:
			return HOVerwaltung.instance().getLanguageString("training.defending");

		case TA_STANDARD:
			return HOVerwaltung.instance().getLanguageString("training.set_pieces");

		default:
			return HOVerwaltung.instance().getLanguageString("Unbestimmt");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFormationExperience343(int exp) {
		this.formationXp343 = exp;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getFormationExperience343() {
		return formationXp343;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFormationExperience352(int exp) {
		this.formationXp352 = exp;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getFormationExperience352() {
		return formationXp352;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFormationExperience433(int exp) {
		this.formationXp433 = exp;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getFormationExperience433() {
		return formationXp433;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFormationExperience451(int exp) {
		this.formationXp451 = exp;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getFormationExperience451() {
		return formationXp451;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFormationExperience532(int exp) {
		this.formationXp532 = exp;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getFormationExperience532() {
		return formationXp532;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFormationExperience541(int exp) {
		this.formationXp541 = exp;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getFormationExperience541() {
		return formationXp541;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFormationExperience442(int exp) {
		this.formationXp442 = exp;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getFormationExperience442() {
		return formationXp442;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setFormationExperience523(int exp) {
		this.formationXp523 = exp;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getFormationExperience523() {
		return formationXp523;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFormationExperience550(int exp) {
		this.formationXp550 = exp;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getFormationExperience550() {
		return formationXp550;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setFormationExperience253(int exp) {
		this.formationXp253 = exp;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getFormationExperience253() {
		return formationXp253;
	}

	/**
	 * Setter for property m_sSelbstvertrauen.
	 * 
	 * @param m_sSelbstvertrauen
	 *            New value of property m_sSelbstvertrauen.
	 */
	public void setSelbstvertrauen(String m_sSelbstvertrauen) {
		this.m_sSelbstvertrauen = m_sSelbstvertrauen;
	}

	/**
	 * Getter for property m_sSelbstvertrauen.
	 * 
	 * @return Value of property m_sSelbstvertrauen.
	 */
	public String getSelbstvertrauen() {
		return m_sSelbstvertrauen;
	}

	/**
	 * Setter for property m_iSelbstvertrauen.
	 * 
	 * @param m_iSelbstvertrauen
	 *            New value of property m_iSelbstvertrauen.
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
	 * @param m_sStimmung
	 *            New value of property m_sStimmung.
	 */
	public void setStimmung(String m_sStimmung) {
		this.m_sStimmung = m_sStimmung;
	}

	/**
	 * Getter for property m_sStimmung.
	 * 
	 * @return Value of property m_sStimmung.
	 */
	public String getStimmung() {
		return m_sStimmung;
	}

	/**
	 * Setter for property m_iStimmung.
	 * 
	 * @param m_iStimmung
	 *            New value of property m_iStimmung.
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
	 * @param m_sTrainingsArt
	 *            New value of property m_sTrainingsArt.
	 */
	public void setTrainingsArt(String m_sTrainingsArt) {
		this.m_sTrainingsArt = m_sTrainingsArt;
	}

	/**
	 * Getter for property m_sTrainingsArt.
	 * 
	 * @return Value of property m_sTrainingsArt.
	 */
	public String getTrainingsArt() {
		return m_sTrainingsArt;
	}

	/**
	 * Setter for property m_iTrainingsArt.
	 * 
	 * @param m_iTrainingsArt
	 *            New value of property m_iTrainingsArt.
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
	 * @param m_iTrainingslevel
	 *            New value of property m_iTrainingslevel.
	 */
	public void setTrainingslevel(int m_iTrainingslevel) {
		this.m_iTrainingslevel = m_iTrainingslevel;
	}

	/**
	 * Set the stamina share amount in percent.
	 */
	public void setStaminaTrainingPart(int m_iStaminaTrainingPart) {
		this.m_iStaminaTrainingPart = m_iStaminaTrainingPart;
	}

	/**
	 * Getter for property m_iTrainingslevel.
	 * 
	 * @return Value of property m_iTrainingslevel.
	 */
	public int getTrainingslevel() {
		return m_iTrainingslevel;
	}

	/**
	 * Get the stamina share amount in percent.
	 */
	public int getStaminaTrainingPart() {
		return m_iStaminaTrainingPart;
	}

	/**
	 * Get the sublevel of the team spirit.
	 */
	public int getSubStimmung() {
		return subStimmung;
	}

	/**
	 * Set the sublevel of the team spirit.
	 */
	public void setSubStimmung(int i) {
		subStimmung = i;
	}

}
