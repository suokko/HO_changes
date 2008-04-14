// %2522043834:de.hattrickorganizer.database%
package de.hattrickorganizer.database;

import gui.UserParameter;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import plugins.IFutureTrainingWeek;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.model.ArenaStatistikTableModel;
import de.hattrickorganizer.gui.model.HOColumnModel;
import de.hattrickorganizer.model.Aufstellung;
import de.hattrickorganizer.model.Basics;
import de.hattrickorganizer.model.FactorObject;
import de.hattrickorganizer.model.Finanzen;
import de.hattrickorganizer.model.FormulaFactors;
import de.hattrickorganizer.model.HOModel;
import de.hattrickorganizer.model.HOParameter;
import de.hattrickorganizer.model.Liga;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.Stadium;
import de.hattrickorganizer.model.Team;
import de.hattrickorganizer.model.User;
import de.hattrickorganizer.model.Verein;
import de.hattrickorganizer.model.XtraData;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.model.matches.MatchLineup;
import de.hattrickorganizer.model.matches.MatchLineupPlayer;
import de.hattrickorganizer.model.matches.MatchLineupTeam;
import de.hattrickorganizer.model.matches.Matchdetails;
import de.hattrickorganizer.model.matchlist.Spielplan;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.backup.BackupDialog;

/**
 * DOCUMENT ME!
 *
 * @author tom 
 */
public class DBZugriff {
	//~ Static fields/initializers -----------------------------------------------------------------

	//Datum der TSI Umstellung. Alle Marktwerte der Spieler m�ssen vor dem Datum durch 1000 geteilt werden (ohne Sprachfaktor)
	/** TODO Missing Parameter Documentation */
	private static final int DBVersion = 6;

	/** TODO Missing Parameter Documentation 2004-06-14 11:00:00.0 */
	public static Timestamp TSIDATE = new Timestamp(1087203600000L);

	/** singelton */
	protected static DBZugriff m_clInstance;

	//~ Instance fields ----------------------------------------------------------------------------

	/** DB-Adapter */
	private JDBCAdapter m_clJDBCAdapter = null; //new JDBCAdapter();

	/** all Tables */
	private final Hashtable tables = new Hashtable();

	/** Erster Start */
	private boolean m_bFirstStart;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new instance of DBZugriff
	 */
	private DBZugriff() {
		m_clJDBCAdapter = new JDBCAdapter();
	}

	//~ Methods ------------------------------------------------------------------------------------

	////////////////////////////////////////////////////////////////////////////////
	//INSTANCE
	////////////////////////////////////////////////////////////////////////////////
	public static synchronized DBZugriff instance() {
		if (m_clInstance == null) {
			//HOLogger.instance().log(getClass(),"TSI: " + TSIDATE);

			// Create new instance
			DBZugriff tempInstance = new DBZugriff();

			tempInstance.initAllTables(tempInstance.getAdapter());
			// Try connecting to the DB
			try {
				tempInstance.connect();
			} catch (Exception e) {

				String msg = e.getMessage();
				boolean recover = User.getCurrentUser().isHSQLDB();
				
				if (msg.indexOf("The database is already in use by another process") > -1) {
					msg =
						"The database is already in use. You have another HO running or the database is still closing, wait and try again";
					recover = false;
				} else {
					msg =
						"Fatal DB Error. Exiting HO!\nYou should restore db folder from backup or delete folder.";
				}

				javax.swing.JOptionPane.showMessageDialog(
					null,
					msg,
					"Fatal DB Error",
					javax.swing.JOptionPane.ERROR_MESSAGE);

				if (recover) {
					BackupDialog dialog = new BackupDialog();
					dialog.setVisible(true);
					while (dialog.isVisible()) {
						// wait
					}
				}
				System.exit(-1);
			}

			//          Does DB already exists?
			final boolean existsDB = tempInstance.checkIfDBExists();

			// What is this for? jailbird.
			tempInstance.setFirstStart(!existsDB);

			//final FirstStart firstStart = new FirstStart(tempInstance);

			// Do we need to create the database from scratch?
			if (!existsDB) {
				//firstStart.createAllTables();
				tempInstance.createAllTables();				
				UserConfigurationTable configTable = (UserConfigurationTable) tempInstance.getTable(UserConfigurationTable.TABLENAME);
				configTable.store(UserParameter.instance());
				configTable.store(HOParameter.instance());
				configTable.store(FormulaFactors.instance());
			} else {
				tempInstance.updateDB();
			}
			//Check if there are any updates on the database to be done.

			m_clInstance = tempInstance;
		}

		return m_clInstance;
	}

	private void initAllTables(JDBCAdapter adapter) {
		tables.put(BasicsTable.TABLENAME, new BasicsTable(adapter));
		tables.put(TeamTable.TABLENAME, new TeamTable(adapter));
		tables.put(FaktorenTable.TABLENAME, new FaktorenTable(adapter));
		tables.put(PositionenTable.TABLENAME, new PositionenTable(adapter));
		tables.put(AufstellungTable.TABLENAME, new AufstellungTable(adapter));
		tables.put(HRFTable.TABLENAME, new HRFTable(adapter));
		tables.put(StadionTable.TABLENAME, new StadionTable(adapter));
		tables.put(VereinTable.TABLENAME, new VereinTable(adapter));
		tables.put(LigaTable.TABLENAME, new LigaTable(adapter));
		tables.put(SpielerTable.TABLENAME, new SpielerTable(adapter));
		tables.put(FinanzenTable.TABLENAME, new FinanzenTable(adapter));
		tables.put(ScoutTable.TABLENAME, new ScoutTable(adapter));
		tables.put(UserColumnsTable.TABLENAME, new UserColumnsTable(adapter));
		tables.put(SpielerNotizenTable.TABLENAME, new SpielerNotizenTable(adapter));
		tables.put(SpielplanTable.TABLENAME, new SpielplanTable(adapter));
		tables.put(PaarungTable.TABLENAME, new PaarungTable(adapter));
		tables.put(MatchLineupTeamTable.TABLENAME, new MatchLineupTeamTable(adapter));
		tables.put(MatchLineupTable.TABLENAME, new MatchLineupTable(adapter));
		tables.put(XtraDataTable.TABLENAME, new XtraDataTable(adapter));
		tables.put(MatchLineupPlayerTable.TABLENAME, new MatchLineupPlayerTable(adapter));
		tables.put(MatchesKurzInfoTable.TABLENAME, new MatchesKurzInfoTable(adapter));
		tables.put(MatchDetailsTable.TABLENAME, new MatchDetailsTable(adapter));
		tables.put(MatchHighlightsTable.TABLENAME, new MatchHighlightsTable(adapter));
		tables.put(TrainingsTable.TABLENAME, new TrainingsTable(adapter));
		tables.put(FutureTrainingTable.TABLENAME, new FutureTrainingTable(adapter));
		tables.put(UserConfigurationTable.TABLENAME, new UserConfigurationTable(adapter));
		tables.put(SpielerSkillupTable.TABLENAME, new SpielerSkillupTable(adapter));

	}

	protected AbstractTable getTable(String tableName) {
		return (AbstractTable) tables.get(tableName);
	}

	//Accessor
	public JDBCAdapter getAdapter() {
		return m_clJDBCAdapter;
	}

	/**
	 * Set First start flag
	 */
	private void setFirstStart(boolean firststart) {
		m_bFirstStart = firststart;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public boolean isFirstStart() {
		return m_bFirstStart;
	}

	/**
	 * trennt die Verbindung zur Datenbank
	 */
	public void disconnect() {
		m_clJDBCAdapter.disconnect();
		m_clJDBCAdapter = null;
		m_clInstance = null;
	}

	/**
	 * stellt die Verbindung zur DB her
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	protected void connect() throws Exception {
		User user = User.getCurrentUser();
		m_clJDBCAdapter.connect(user.getUrl(), user.getUser(), user.getPwd(), user.getDriver());
	}

	/**
	 * check if tables in DB exists
	 *
	 * @return boolean
	 */
	private boolean checkIfDBExists() {
		if (m_clJDBCAdapter.executeQuery("SELECT * FROM HRF WHERE 1=2") == null) {
			return false;
		}
		return true;
	}

	// ------------------------------- SpielerSkillupTable -------------------------------------------------

	/**
	 * liefert das Datum des letzen Levelups anhand key und value
	 *
	 * @param key Name des Skills
	 * @param value Wert des Skills Vor dem LevelUp
	 * @param spielerId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return [0] = Time der �nderung [1] = Boolean: false=Keine �nderung gefunden
	 */
	public Object[] getLastLevelUp(int skill, int spielerId) {
		return ((SpielerSkillupTable) getTable(SpielerSkillupTable.TABLENAME)).getLastLevelUp(
			skill,
			spielerId);
	}

	/**
	* liefert das Datum des letzen LevelAufstiegs f�r den angeforderten Skill Vector filled with
	* Skillup Objects
	*
	* @param skill TODO Missing Constructuor Parameter Documentation
	*
	* @return TODO Missing Return Method Documentation
	*/
	public Vector getAllLevelUp(int skill, int m_iSpielerID) {
		return ((SpielerSkillupTable) getTable(SpielerSkillupTable.TABLENAME)).getAllLevelUp(
			skill,
			m_iSpielerID);
	}

	public void saveSkillup(int hrfId, Timestamp datum, int m_iSpielerID, int skill, int value) {
		((SpielerSkillupTable) getTable(SpielerSkillupTable.TABLENAME)).saveSkillup(
			hrfId,
			m_iSpielerID,
			datum,
			skill,
			value);
	}

	public void reimportSkillup() {
		((SpielerSkillupTable) getTable(SpielerSkillupTable.TABLENAME)).importFromSpieler();
	}

	public void checkSkillup(HOModel homodel) {
		((SpielerSkillupTable) getTable(SpielerSkillupTable.TABLENAME)).importNewSkillup(homodel);
	}
	// ------------------------------- SpielerTable -------------------------------------------------

	/**
	 * gibt alle Spieler zur�ck, auch ehemalige
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getAllSpieler() {
		return ((SpielerTable) getTable(SpielerTable.TABLENAME)).getAllSpieler();
	}

	/**
	 * Gibt die letzte Bewertung f�r den Spieler zur�ck // HRF
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getLetzteBewertung4Spieler(int spielerid) {
		return ((SpielerTable) getTable(SpielerTable.TABLENAME)).getLetzteBewertung4Spieler(
			spielerid);
	}

	/**
	 * l�dt die Spieler zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getSpieler(int hrfID) {
		return ((SpielerTable) getTable(SpielerTable.TABLENAME)).getSpieler(hrfID);
	}

	/**
	 * Gibt einen Spieler zur�ck mit den Daten kurz vor dem Timestamp
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 * @param time TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Spieler getSpielerAtDate(int spielerid, Timestamp time) {
		return ((SpielerTable) getTable(SpielerTable.TABLENAME)).getSpielerAtDate(spielerid, time);
	}

	/**
	 * 
	 * @param time
	 * @param spielerid
	 * @return
	 */
	public Spieler getSpielerBeforeDate(Timestamp time, int spielerid) {
		return ((SpielerTable) getTable(SpielerTable.TABLENAME)).getSpielerBeforeDate(
			time,
			spielerid);
	}

	/**
	 * Gibt einen Spieler zur�ck aus dem ersten HRF
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Spieler getSpielerFirstHRF(int spielerid) {
		return ((SpielerTable) getTable(SpielerTable.TABLENAME)).getSpielerFirstHRF(spielerid);
	}

	/**
	 * Gibt das Datum des ersten HRFs zur�ck, in dem der Spieler aufgetaucht ist
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Timestamp getTimestamp4FirstPlayerHRF(int spielerid) {
		return ((SpielerTable) getTable(SpielerTable.TABLENAME)).getTimestamp4FirstPlayerHRF(
			spielerid);
	}
	/**
	 * Returns the trainer code for the specified hrf. -99 if error
	 * @param hrfID HRF for which to load TrainerType
	 * @return
	 */
	public int getTrainerType(int hrfID) {
		return ((SpielerTable) getTable(SpielerTable.TABLENAME)).getTrainerType(hrfID);
	}
	/**
	 * speichert die Spieler
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param spieler TODO Missing Constructuor Parameter Documentation
	 * @param date TODO Missing Constructuor Parameter Documentation
	 */
	public void saveSpieler(int hrfId, Vector spieler, Timestamp date) {
		((SpielerTable) getTable(SpielerTable.TABLENAME)).saveSpieler(hrfId, spieler, date);
	}

	// ------------------------------- LigaTable -------------------------------------------------	

	/**
	 * Gibt alle bekannten Ligaids zur�ck
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Integer[] getAllLigaIDs() {
		return ((LigaTable) getTable(LigaTable.TABLENAME)).getAllLigaIDs();
	}

	/**
	 * l�dt die Basics zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Liga getLiga(int hrfID) {
		return ((LigaTable) getTable(LigaTable.TABLENAME)).getLiga(hrfID);
	}

	/**
	 * speichert die Basdics
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param liga TODO Missing Constructuor Parameter Documentation
	 */
	public void saveLiga(int hrfId, Liga liga) {
		((LigaTable) getTable(LigaTable.TABLENAME)).saveLiga(hrfId, liga);
	}

	// ------------------------------- SpielplanTable -------------------------------------------------

	/**
	 * Gibt eine Ligaid zu einer Seasonid zur�ck, oder -1, wenn kein Eintrag in der DB gefunden
	 * wurde
	 *
	 * @param seasonid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getLigaID4SaisonID(int seasonid) {
		return ((SpielplanTable) getTable(SpielplanTable.TABLENAME)).getLigaID4SaisonID(seasonid);
	}

	/**
	 * holt einen Spielplan aus der DB, -1 bei den params holt den zuletzt gesavten Spielplan
	 *
	 * @param ligaId Id der Liga
	 * @param saison die Saison
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Spielplan getSpielplan(int ligaId, int saison) {
		return ((SpielplanTable) getTable(SpielplanTable.TABLENAME)).getSpielplan(ligaId, saison);
	}

	/**
	 * speichert einen Spielplan mitsamt Paarungen
	 *
	 * @param plan TODO Missing Constructuor Parameter Documentation
	 */
	public void storeSpielplan(Spielplan plan) {
		((SpielplanTable) getTable(SpielplanTable.TABLENAME)).storeSpielplan(plan);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param whereSpalten TODO Missing Method Parameter Documentation
	 * @param whereValues TODO Missing Method Parameter Documentation
	 */
	public void deleteSpielplanTabelle(String[] whereSpalten, String[] whereValues) {
		getTable(SpielplanTable.TABLENAME).delete(whereSpalten, whereValues);
	}

	/**
	 * l�dt alle Spielpl�ne aus der DB
	 *
	 * @param mitPaarungen inklusive der Paarungen ja/nein
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Spielplan[] getAllSpielplaene(boolean mitPaarungen) {
		return ((SpielplanTable) getTable(SpielplanTable.TABLENAME)).getAllSpielplaene(
			mitPaarungen);
	}

	// ------------------------------- MatchLineupPlayerTable -------------------------------------------------

	/**
	 * Gibt eine Liste an Ratings zur�ck, auf denen der Spieler gespielt hat: 0 = Max 1 = Min 2 =
	 * Durchschnitt 3 = posid
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getAlleBewertungen(int spielerid) {
		return (
			(MatchLineupPlayerTable) getTable(
				MatchLineupPlayerTable.TABLENAME)).getAlleBewertungen(
			spielerid);
	}

	/**
	 * Gibt die beste, schlechteste und durchschnittliche Bewertung f�r den Spieler, sowie die
	 * Anzahl der Bewertungen zur�ck // Match
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public float[] getBewertungen4Player(int spielerid) {
		return (
			(MatchLineupPlayerTable) getTable(
				MatchLineupPlayerTable.TABLENAME)).getBewertungen4Player(
			spielerid);
	}

	/**
	 * Gibt die beste, schlechteste und durchschnittliche Bewertung f�r den Spieler, sowie die
	 * Anzahl der Bewertungen zur�ck // Match
	 *
	 * @param spielerid Spielerid
	 * @param position Usere positionscodierung mit taktik
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public float[] getBewertungen4PlayerUndPosition(int spielerid, byte position) {
		return (
			(MatchLineupPlayerTable) getTable(
				MatchLineupPlayerTable.TABLENAME)).getBewertungen4PlayerUndPosition(
			spielerid,
			position);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param matchID TODO Missing Method Parameter Documentation
	 * @param teamID TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getMatchLineupPlayers(int matchID, int teamID) {
		return (
			(MatchLineupPlayerTable) getTable(
				MatchLineupPlayerTable.TABLENAME)).getMatchLineupPlayers(
			matchID,
			teamID);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param player TODO Missing Method Parameter Documentation
	 * @param matchID TODO Missing Method Parameter Documentation
	 * @param teamID TODO Missing Method Parameter Documentation
	 */
	protected void storeMatchLineupPlayer(MatchLineupPlayer player, int matchID, int teamID) {
		(
			(MatchLineupPlayerTable) getTable(
				MatchLineupPlayerTable.TABLENAME)).storeMatchLineupPlayer(
			player,
			matchID,
			teamID);
	}

	// ------------------------------- AufstellungTable -------------------------------------------------

	/**
	 * l�dt System Positionen
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 * @param name TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Aufstellung getAufstellung(int hrfID, String name) {
		return ((AufstellungTable) getTable(AufstellungTable.TABLENAME)).getAufstellung(
			hrfID,
			name);
	}

	/**
	 * gibt liste f�r Aufstellungen
	 *
	 * @param hrfID -1 f�r default = hrf unabh�ngig
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getAufstellungsListe(int hrfID) {
		return ((AufstellungTable) getTable(AufstellungTable.TABLENAME)).getAufstellungsListe(
			hrfID);
	}

	/**
	 * Gibt eine Liste aller Usergespeicherten Aufstellungsnamen zur�ck
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getUserAufstellungsListe() {
		return ((AufstellungTable) getTable(AufstellungTable.TABLENAME)).getUserAufstellungsListe();
	}

	/**
	 * speichert die Aufstellung und die aktuelle Aufstellung als STANDARD
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param aufstellung TODO Missing Constructuor Parameter Documentation
	 * @param name TODO Missing Constructuor Parameter Documentation
	 */
	public void saveAufstellung(int hrfId, Aufstellung aufstellung, String name) {
		((AufstellungTable) getTable(AufstellungTable.TABLENAME)).saveAufstellung(
			hrfId,
			aufstellung,
			name);
	}

	// ------------------------------- BasicsTable -------------------------------------------------

	/**
	 * l�dt die Basics zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Basics getBasics(int hrfID) {
		return ((BasicsTable) getTable(BasicsTable.TABLENAME)).getBasics(hrfID);
	}

	/**
	 * Gibt eine Vector mit HRF-CBItems zur�ck
	 *
	 * @param datum from which hrf has to be returned, used to load a subset of hrf
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public java.util.Vector getCBItemHRFListe(Timestamp datum) {
		return ((BasicsTable) getTable(BasicsTable.TABLENAME)).getCBItemHRFListe(datum);
	}

	/**
	 * Returns an HRF before the matchData and after previous TrainingTime
	 * @param timestamp matchData
	 * @return hrfId
	 */
	public int getHrfIDSameTraining(Timestamp matchTime) {
		return ((BasicsTable) getTable(BasicsTable.TABLENAME)).getHrfIDSameTraining(matchTime);
	}
	/**
	 * speichert die Basdics
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param basics TODO Missing Constructuor Parameter Documentation
	 */
	public void saveBasics(int hrfId, de.hattrickorganizer.model.Basics basics) {
		((BasicsTable) getTable(BasicsTable.TABLENAME)).saveBasics(hrfId, basics);
	}

	//	------------------------------- FaktorenTable -------------------------------------------------
	/**
	 * TODO Missing Method Documentation
	 *
	 * @param fo TODO Missing Method Parameter Documentation
	 */
	public void setFaktorenFromDB(FactorObject fo) {
		((FaktorenTable) getTable(FaktorenTable.TABLENAME)).setFaktorenFromDB(fo);
	}

	/**
		* TODO Missing Method Documentation
		*
		* @param fo TODO Missing Method Parameter Documentation
		*/
	public void getFaktorenFromDB() {
		((FaktorenTable) getTable(FaktorenTable.TABLENAME)).getFaktorenFromDB();
	}

	//	------------------------------- FinanzenTable -------------------------------------------------

	/**
	 * l�dt die Finanzen zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Finanzen getFinanzen(int hrfID) {
		return ((FinanzenTable) getTable(FinanzenTable.TABLENAME)).getFinanzen(hrfID);
	}

	/**
	 * speichert die Finanzen
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param finanzen TODO Missing Constructuor Parameter Documentation
	 * @param date TODO Missing Constructuor Parameter Documentation
	 */
	public void saveFinanzen(int hrfId, Finanzen finanzen, Timestamp date) {
		((FinanzenTable) getTable(FinanzenTable.TABLENAME)).saveFinanzen(hrfId, finanzen, date);
	}

	//	------------------------------- HRFTable -------------------------------------------------

	/**
	 * liefert die aktuelle Id des neuesten HRF-Files
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getMaxHrfId() {
		return ((HRFTable) getTable(HRFTable.TABLENAME)).getMaxHrf().getHrfId();
	}

	/**
	 * liefert die Maximal Vergebene Id eines HRF-Files
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getLatestHrfId() {
		return ((HRFTable) getTable(HRFTable.TABLENAME)).getLatestHrf().getHrfId();
	}

	/**
	 * Sucht das letzte HRF zwischen dem angegebenen Datum und 6 Tagen davor Wird kein HRF gefunden
	 * wird -1 zur�ckgegeben
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getPreviousHRF(int hrfId) {
		return ((HRFTable) getTable(HRFTable.TABLENAME)).getPreviousHRF(hrfId);
	}

	/**
	 * Sucht das letzte HRF zwischen dem angegebenen Datum und 6 Tagen davor Wird kein HRF gefunden
	 * wird -1 zur�ckgegeben
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int getFollowingHRF(int hrfId) {
		return ((HRFTable) getTable(HRFTable.TABLENAME)).getFollowingHRF(hrfId);
	}

	/**
	 * speichert das Verein
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param name TODO Missing Constructuor Parameter Documentation
	 * @param datum TODO Missing Constructuor Parameter Documentation
	 */
	public void saveHRF(int hrfId, String name, Timestamp datum) {
		((HRFTable) getTable(HRFTable.TABLENAME)).saveHRF(hrfId, name, datum);
	}

	/**
	 * 
	 */
	public int getHRFID4Date(Timestamp time) {
		return ((HRFTable) getTable(HRFTable.TABLENAME)).getHrfId4Date(time);
	}

	/**
	 * gibt es ein HRFFile in der Datenbank mit dem gleichen Dateimodifieddatum schon?
	 *
	 * @param date der letzten Datei�nderung der zu vergleichenden Datei
	 *
	 * @return Das Datum der Datei, an den die Datei importiert wurde oder null, wenn keine
	 *         passende Datei vorhanden ist
	 */
	public String getHRFName4Date(Timestamp date) {
		return ((HRFTable) getTable(HRFTable.TABLENAME)).getHrfName4Date(date);
	}

	//	------------------------------- SpielerNotizenTable -------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param spielerId TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public String getManuellerSmilie(int spielerId) {
		return ((SpielerNotizenTable) getTable(SpielerNotizenTable.TABLENAME)).getManuellerSmilie(
			spielerId);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param spielerId TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public String getTeamInfoSmilie(int spielerId) {
		return ((SpielerNotizenTable) getTable(SpielerNotizenTable.TABLENAME)).getTeamInfoSmilie(
			spielerId);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param spielerId TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public String getSpielerNotiz(int spielerId) {
		return ((SpielerNotizenTable) getTable(SpielerNotizenTable.TABLENAME)).getSpielerNotiz(
			spielerId);
	}

	public boolean getSpielerSpielberechtigt(int spielerId) {
		return (
			(SpielerNotizenTable) getTable(
				SpielerNotizenTable.TABLENAME)).getSpielerSpielberechtigt(
			spielerId);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param spielerId TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public byte getSpielerUserPosFlag(int spielerId) {
		return (
			(SpielerNotizenTable) getTable(SpielerNotizenTable.TABLENAME)).getSpielerUserPosFlag(
			spielerId);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param spielerId TODO Missing Method Parameter Documentation
	 * @param smilie TODO Missing Method Parameter Documentation
	 */
	public void saveManuellerSmilie(int spielerId, String smilie) {
		((SpielerNotizenTable) getTable(SpielerNotizenTable.TABLENAME)).saveManuellerSmilie(
			spielerId,
			smilie);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param spielerId TODO Missing Method Parameter Documentation
	 * @param notiz TODO Missing Method Parameter Documentation
	 */
	public void saveSpielerNotiz(int spielerId, String notiz) {
		((SpielerNotizenTable) getTable(SpielerNotizenTable.TABLENAME)).saveSpielerNotiz(
			spielerId,
			notiz);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param spielerId TODO Missing Method Parameter Documentation
	 * @param spielberechtigt TODO Missing Method Parameter Documentation
	 */
	public void saveSpielerSpielberechtigt(int spielerId, boolean spielberechtigt) {
		((SpielerNotizenTable) getTable(SpielerNotizenTable.TABLENAME)).saveSpielerSpielberechtigt(
			spielerId,
			spielberechtigt);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param spielerId TODO Missing Method Parameter Documentation
	 * @param flag TODO Missing Method Parameter Documentation
	 */
	public void saveSpielerUserPosFlag(int spielerId, byte flag) {
		((SpielerNotizenTable) getTable(SpielerNotizenTable.TABLENAME)).saveSpielerUserPosFlag(
			spielerId,
			flag);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param spielerId TODO Missing Method Parameter Documentation
	 * @param smilie TODO Missing Method Parameter Documentation
	 */
	public void saveTeamInfoSmilie(int spielerId, String smilie) {
		((SpielerNotizenTable) getTable(SpielerNotizenTable.TABLENAME)).saveTeamInfoSmilie(
			spielerId,
			smilie);
	}

	//	------------------------------- MatchLineupTable -------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param matchID TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public MatchLineup getMatchLineup(int matchID) {
		return ((MatchLineupTable) getTable(MatchLineupTable.TABLENAME)).getMatchLineup(matchID);
	}

	/**
	 * Ist das Match schon in der Datenbank vorhanden?
	 *
	 * @param matchid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public boolean isMatchLineupVorhanden(int matchid) {
		return ((MatchLineupTable) getTable(MatchLineupTable.TABLENAME)).isMatchLineupVorhanden(
			matchid);
	}

	/**
	 * speichert ein Matchlineup
	 *
	 * @param lineup TODO Missing Constructuor Parameter Documentation
	 */
	public void storeMatchLineup(MatchLineup lineup) {
		((MatchLineupTable) getTable(MatchLineupTable.TABLENAME)).storeMatchLineup(lineup);
	}

	//	------------------------------- MatchesKurzInfoTable -------------------------------------------------

	/**
	 * Ist das Match schon in der Datenbank vorhanden?
	 *
	 * @param matchid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public boolean isMatchVorhanden(int matchid) {
		return ((MatchesKurzInfoTable) getTable(MatchesKurzInfoTable.TABLENAME)).isMatchVorhanden(
			matchid);
	}

	/**
	 * holt die MAtches zu einem Team aus der DB
	 *
	 * @param teamId Die Teamid oder -1 f�r alle
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public MatchKurzInfo[] getMatchesKurzInfo(int teamId) {
		return (
			(MatchesKurzInfoTable) getTable(MatchesKurzInfoTable.TABLENAME)).getMatchesKurzInfo(
			teamId);
	}

	/**
	 * Wichtig: Wenn die Teamid = -1 ist muss der Matchtyp ALLE_SPIELE sein!
	 *
	 * @param teamId Die Teamid oder -1 f�r alle
	 * @param matchtyp Welche Matches? Konstanten im SpielePanel!
	 * @param asc TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public MatchKurzInfo[] getMatchesKurzInfo(int teamId, int matchtyp, boolean asc) {
		return (
			(MatchesKurzInfoTable) getTable(MatchesKurzInfoTable.TABLENAME)).getMatchesKurzInfo(
			teamId,
			matchtyp,
			asc);
	}

	/**
	 * speichert die Matches
	 *
	 * @param matches TODO Missing Constructuor Parameter Documentation
	 */
	public void storeMatchKurzInfos(MatchKurzInfo[] matches) {
		((MatchesKurzInfoTable) getTable(MatchesKurzInfoTable.TABLENAME)).storeMatchKurzInfos(
			matches);
	}

	//	------------------------------- ScoutTable -------------------------------------------------

	/**
	 * Load player list for insertion into TransferScout
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getScoutList() {
		return ((ScoutTable) getTable(ScoutTable.TABLENAME)).getScoutList();
	}

	/**
	 * Save players from TransferScout
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param list TODO Missing Constructuor Parameter Documentation
	 */
	public void saveScoutList(Vector list) {
		((ScoutTable) getTable(ScoutTable.TABLENAME)).saveScoutList(list);
	}

	//	------------------------------- StadionTable -------------------------------------------------

	/**
	 * l�dt die Finanzen zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Stadium getStadion(int hrfID) {
		return ((StadionTable) getTable(StadionTable.TABLENAME)).getStadion(hrfID);
	}

	/**
	 * speichert die Finanzen
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param stadion TODO Missing Constructuor Parameter Documentation
	 */
	public void saveStadion(int hrfId, de.hattrickorganizer.model.Stadium stadion) {
		((StadionTable) getTable(StadionTable.TABLENAME)).saveStadion(hrfId, stadion);
	}

	//	------------------------------- TeamTable -------------------------------------------------

	/**
	 * Gibt die Teamstimmung und das Selbstvertrauen f�r ein HRFID zur�ck [0] = Stimmung [1] =
	 * Selbstvertrauen
	 *
	 * @param hrfid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public String[] getStimmmungSelbstvertrauen(int hrfid) {
		return ((TeamTable) getTable(TeamTable.TABLENAME)).getStimmmungSelbstvertrauen(hrfid);
	}

	/**
	 * Gibt die Teamstimmung und das Selbstvertrauen f�r ein HRFID zur�ck [0] = Stimmung [1] =
	 * Selbstvertrauen
	 *
	 * @param hrfid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public int[] getStimmmungSelbstvertrauenValues(int hrfid) {
		return ((TeamTable) getTable(TeamTable.TABLENAME)).getStimmmungSelbstvertrauenValues(hrfid);
	}

	/**
	 * l�dt die Basics zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Team getTeam(int hrfID) {
		return ((TeamTable) getTable(TeamTable.TABLENAME)).getTeam(hrfID);
	}

	/**
	 * speichert das Team
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param team TODO Missing Constructuor Parameter Documentation
	 */
	public void saveTeam(int hrfId, Team team) {
		((TeamTable) getTable(TeamTable.TABLENAME)).saveTeam(hrfId, team);
	}

	//	------------------------------- PositionenTable -------------------------------------------------

	/**
	 * l�dt System Positionen
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 * @param sysName TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getSystemPositionen(int hrfID, String sysName) {
		return ((PositionenTable) getTable(PositionenTable.TABLENAME)).getSystemPositionen(
			hrfID,
			sysName);
	}

	/**
	 * speichert System Positionen
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param positionen TODO Missing Constructuor Parameter Documentation
	 * @param sysName TODO Missing Constructuor Parameter Documentation
	 */
	public void saveSystemPositionen(int hrfId, Vector positionen, String sysName) {
		((PositionenTable) getTable(PositionenTable.TABLENAME)).saveSystemPositionen(
			hrfId,
			positionen,
			sysName);
	}

	/**
	 * delete das System
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param sysName TODO Missing Constructuor Parameter Documentation
	 */
	public void deleteSystem(int hrfId, String sysName) {
		final String[] whereS = { "Aufstellungsname", "HRF_ID" };
		final String[] whereV = { "'" + sysName + "'", "" + hrfId };

		//erst vorhandene eintr�ge f�r diesen Posnamen entfernen
		getTable(PositionenTable.TABLENAME).delete(whereS, whereV);
	}

	//	------------------------------- TrainingsTable -------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public java.util.Vector getTrainingsVector() {
		return ((TrainingsTable) getTable(TrainingsTable.TABLENAME)).getTrainingsVector();
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param training TODO Missing Method Parameter Documentation
	 */
	public void saveTraining(de.hattrickorganizer.model.TrainingPerWeek training) {
		((TrainingsTable) getTable(TrainingsTable.TABLENAME)).saveTraining(training);
	}

	//	------------------------------- FutureTrainingTable -------------------------------------------------

	public List getFutureTrainingsVector() {
		return ((FutureTrainingTable) getTable(FutureTrainingTable.TABLENAME))
			.getFutureTrainingsVector();
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param training TODO Missing Method Parameter Documentation
	 */
	public void saveFutureTraining(IFutureTrainingWeek training) {
		((FutureTrainingTable) getTable(FutureTrainingTable.TABLENAME)).saveFutureTraining(
			training);
	}

	//	------------------------------- VereinTable -------------------------------------------------

	/**
	 * l�dt die Basics zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Verein getVerein(int hrfID) {
		return ((VereinTable) getTable(VereinTable.TABLENAME)).getVerein(hrfID);
	}

	/**
	 * speichert das Verein
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param verein TODO Missing Constructuor Parameter Documentation
	 */
	public void saveVerein(int hrfId, Verein verein) {
		((VereinTable) getTable(VereinTable.TABLENAME)).saveVerein(hrfId, verein);
	}

	//	------------------------------- XtraDataTable -------------------------------------------------

	/**
	 * l�dt die Basics zum angegeben HRF file ein
	 *
	 * @param hrfID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public XtraData getXtraDaten(int hrfID) {
		return ((XtraDataTable) getTable(XtraDataTable.TABLENAME)).getXtraDaten(hrfID);
	}

	/**
	 * speichert das Team
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param xtra TODO Missing Constructuor Parameter Documentation
	 */
	public void saveXtraDaten(int hrfId, XtraData xtra) {
		((XtraDataTable) getTable(XtraDataTable.TABLENAME)).saveXtraDaten(hrfId, xtra);
	}

	//	------------------------------- MatchLineupTeamTable -------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param matchID TODO Missing Method Parameter Documentation
	 * @param teamID TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	protected MatchLineupTeam getMatchLineupTeam(int matchID, int teamID) {
		return (
			(MatchLineupTeamTable) getTable(MatchLineupTeamTable.TABLENAME)).getMatchLineupTeam(
			matchID,
			teamID);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param team TODO Missing Method Parameter Documentation
	 * @param matchID TODO Missing Method Parameter Documentation
	 */
	protected void storeMatchLineupTeam(MatchLineupTeam team, int matchID) {
		((MatchLineupTeamTable) getTable(MatchLineupTeamTable.TABLENAME)).storeMatchLineupTeam(
			team,
			matchID);
	}

	//	------------------------------- UserParameterTable -------------------------------------------------

	/**
	 * Speichert die Spaltenreihenfolge der Tabellen private void saveTabellenSpaltenReihenfolge(
	 * int[][] spieleruebersicht, int[][] aufstellung ) {}
	 */
	/**
	 * L�dt die UserParameter direkt in das UserParameter-SingeltonObjekt
	 */
	public void loadUserParameter() {
		UserConfigurationTable table = (UserConfigurationTable) getTable(UserConfigurationTable.TABLENAME);
		table.load(UserParameter.instance());
		table.load(FormulaFactors.instance());
		table.load(HOParameter.instance());
	}

	/**
	 * Speichert die UserParameter in der Datenbank
	 *
	 */
	public void saveUserParameter() {

		UserConfigurationTable table =
			(UserConfigurationTable) getTable(UserConfigurationTable.TABLENAME);
		table.store(UserParameter.instance());
		table.store(FormulaFactors.instance());
		table.store(HOParameter.instance());
	}

	//	------------------------------- PaarungTable -------------------------------------------------

	/**
	 * holt die Paarungen zum Plan aus der DB und added sie
	 *
	 * @param plan TODO Missing Constructuor Parameter Documentation
	 */
	protected void getPaarungen(Spielplan plan) {
		((PaarungTable) getTable(PaarungTable.TABLENAME)).getPaarungen(plan);
	}

	/**
	 * speichert die Paarungen zu einem Spielplan
	 *
	 * @param paarungen TODO Missing Constructuor Parameter Documentation
	 * @param ligaId TODO Missing Constructuor Parameter Documentation
	 * @param saison TODO Missing Constructuor Parameter Documentation
	 */
	protected void storePaarung(Vector paarungen, int ligaId, int saison) {
		((PaarungTable) getTable(PaarungTable.TABLENAME)).storePaarung(paarungen, ligaId, saison);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param whereSpalten TODO Missing Method Parameter Documentation
	 * @param whereValues TODO Missing Method Parameter Documentation
	 */
	public void deletePaarungTabelle(String[] whereSpalten, String[] whereValues) {
		getTable(PaarungTable.TABLENAME).delete(whereSpalten, whereValues);
	}

	//	------------------------------- MatchDetailsTable -------------------------------------------------	

	/**
	 * Gibt die MatchDetails zu einem Match zur�ck
	 *
	 * @param matchId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Matchdetails getMatchDetails(int matchId) {

		return ((MatchDetailsTable) getTable(MatchDetailsTable.TABLENAME)).getMatchDetails(matchId);
	}

	/**
	 * speichert die MatchDetails
	 *
	 * @param details TODO Missing Constructuor Parameter Documentation
	 */
	public void storeMatchDetails(Matchdetails details) {
		((MatchDetailsTable) getTable(MatchDetailsTable.TABLENAME)).storeMatchDetails(details);
	}

	//	------------------------------- MatchHighlightsTable -------------------------------------------------

	/**
	 * Gibt die MatchHighlights zu einem Match zur�ck
	 *
	 * @param matchId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	protected Vector getMatchHighlights(int matchId) {
		return (
			(MatchHighlightsTable) getTable(MatchHighlightsTable.TABLENAME)).getMatchHighlights(
			matchId);
	}

	/**
	 * Speichert die Highlights zu einem Spiel
	 *
	 * @param details TODO Missing Constructuor Parameter Documentation
	 */
	protected void storeMatchHighlights(Matchdetails details) {
		((MatchHighlightsTable) getTable(MatchHighlightsTable.TABLENAME)).storeMatchHighlights(
			details);
	}

	//	--------------------------------------------------------------------------------
	//	-------------------------------- Statistik Part --------------------------------
	//	--------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param spielerId TODO Missing Method Parameter Documentation
	 * @param anzahlHRF TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public double[][] getSpielerDaten4Statistik(int spielerId, int anzahlHRF) {
		return StatisticQuery.getSpielerDaten4Statistik(spielerId,anzahlHRF);
	}

	public double[][] getFinanzen4Statistik(int anzahlHRF) {
		return StatisticQuery.getFinanzen4Statistik(anzahlHRF);
	}

	public double[][] getSpielerFinanzDaten4Statistik(int spielerId, int anzahlHRF) {
		return StatisticQuery.getSpielerFinanzDaten4Statistik(spielerId,anzahlHRF);
	}

	public ArenaStatistikTableModel getArenaStatistikModel(int matchtyp) {
		return StatisticQuery.getArenaStatistikModel(matchtyp);
	}

	public double[][] getDurchschnittlicheSpielerDaten4Statistik(int anzahlHRF, String group) {
		return StatisticQuery.getDurchschnittlicheSpielerDaten4Statistik(anzahlHRF,group);		
	}
	// --------------------------------- TODO ---------------------------

	/**
	 * Sucht das letzte HRF zwischen dem angegebenen Datum und 6 Tagen davor Wird kein HRF gefunden
	 * wird -1 zur�ckgegeben
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Timestamp getPreviousTrainingDate(int hrfId) {
		String sql =
			"select trainingdate from HRF, XTRADATA where HRF.hrf_id=XTRADATA.hrf_id and trainingdate < (select trainingdate from XTRADATA where hrf_id="
				+ hrfId
				+ ") order by datum desc limit 1";
		final ResultSet rs = m_clJDBCAdapter.executeQuery(sql);

		try {
			if (rs != null) {
				if (rs.first()) {
					return rs.getTimestamp("trainingdate");
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(
				getClass(),
				"DBZugriff.getPreviousTrainingDate: " + e.toString());
		}
		return null;
	}

	/**
	 * Gibt eine Liste mit SpielerMatchCBItems zu den einzelnen Matches zur�ck
	 *
	 * @param spielerid TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public Vector getSpieler4Matches(int spielerid) {
		final Vector spielerMatchCBItems = new Vector();

		//Liste aller Matchplayer mit der spielerid holen
		try {
			final Vector tempSpielerMatchCBItems = new Vector();

			final String sql =
				"SELECT DISTINCT MATCHLINEUPPLAYER.MatchID, MATCHLINEUPPLAYER.MatchID, MATCHLINEUPPLAYER.Rating, MATCHLINEUP.MatchDate, MATCHLINEUP.HeimName, MATCHLINEUP.HeimID, MATCHLINEUP.GastName, MATCHLINEUP.GastID, MATCHLINEUPPLAYER.HoPosCode, MATCHLINEUP.MatchTyp FROM MATCHLINEUPPLAYER, MATCHLINEUP WHERE MATCHLINEUPPLAYER.SpielerID="
					+ spielerid
					+ " AND MATCHLINEUPPLAYER.Rating>-1 AND MATCHLINEUPPLAYER.MatchID=MATCHLINEUP.MatchID ORDER BY MATCHLINEUP.MatchDate DESC";
			final ResultSet rs = m_clJDBCAdapter.executeQuery(sql);
			rs.beforeFirst();

			//Alle Daten zu dem Spieler holen
			while (rs.next()) {
				final de.hattrickorganizer.gui.model.SpielerMatchCBItem temp =
					new de.hattrickorganizer.gui.model.SpielerMatchCBItem(
						null,
						rs.getInt("MatchID"),
						rs.getFloat("Rating") * 2,
						rs.getInt("HoPosCode"),
						rs.getString("MatchDate"),
						DBZugriff.deleteEscapeSequences(rs.getString("HeimName")),
						rs.getInt("HeimID"),
						DBZugriff.deleteEscapeSequences(rs.getString("GastName")),
						rs.getInt("GastID"),
						rs.getInt("MatchTyp"),
						null,
						"",
						"");
				tempSpielerMatchCBItems.add(temp);
			}

			final java.text.SimpleDateFormat simpleFormat =
				new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMANY);
			final java.text.SimpleDateFormat simpleFormat2 =
				new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMANY);
			Timestamp filter = null;
			java.util.Date datum = null;

			//Die Spielerdaten zu den Matches holen
			for (int i = 0; i < tempSpielerMatchCBItems.size(); i++) {
				final de.hattrickorganizer.gui.model.SpielerMatchCBItem item =
					(
						de
							.hattrickorganizer
							.gui
							.model
							.SpielerMatchCBItem) tempSpielerMatchCBItems
							.get(
						i);
				try {
					datum = simpleFormat.parse(item.getMatchdate());
				} catch (Exception e1) {
				}

				if (datum == null) {
					datum = simpleFormat2.parse(item.getMatchdate());
				}

				if (datum != null) {
					filter = new Timestamp(datum.getTime());
				}

				//Spieler
				final de.hattrickorganizer.model.Spieler player =
					getSpielerAtDate(spielerid, filter);

				//Matchdetails
				final de.hattrickorganizer.model.matches.Matchdetails details =
					getMatchDetails(item.getMatchID());

				//Stimmung und Selbstvertrauen
				final String[] stimmungSelbstvertrauen =
					getStimmmungSelbstvertrauen(getHRFID4Date(filter));

				//Nur wenn Spielerdaten gefunden wurden diese in den R�ckgabeVector �bergeben
				if ((player != null) && (details != null) && (stimmungSelbstvertrauen != null)) {
					item.setSpieler(player);
					item.setMatchdetails(details);
					item.setStimmung(stimmungSelbstvertrauen[0]);
					item.setSelbstvertrauen(stimmungSelbstvertrauen[1]);
					spielerMatchCBItems.add(item);
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), "DatenbankZugriff.getSpieler4Matches : " + e);
		}

		return spielerMatchCBItems;
	}

	//------------------------------------- Delete -------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param hrfid TODO Missing Method Parameter Documentation
	 */
	public void deleteHRF(int hrfid) {
		final String[] where = { "HRF_ID" };
		final String[] value = { hrfid + "" };

		getTable(StadionTable.TABLENAME).delete(where, value);
		getTable(HRFTable.TABLENAME).delete(where, value);
		getTable(LigaTable.TABLENAME).delete(where, value);
		getTable(VereinTable.TABLENAME).delete(where, value);
		getTable(AufstellungTable.TABLENAME).delete(where, value);

		getTable(PositionenTable.TABLENAME).delete(where, value);
		getTable(TeamTable.TABLENAME).delete(where, value);
		getTable(FinanzenTable.TABLENAME).delete(where, value);
		getTable(BasicsTable.TABLENAME).delete(where, value);
		getTable(SpielerTable.TABLENAME).delete(where, value);
		getTable(SpielerSkillupTable.TABLENAME).delete(where, value);
		getTable(XtraDataTable.TABLENAME).delete(where, value);
	}

	/**
	 * L�scht alle Daten zu dem Match
	 *
	 * @param matchid TODO Missing Constructuor Parameter Documentation
	 */
	public void deleteMatch(int matchid) {
		final String[] whereSpalten = { "MatchID" };
		final String[] whereValues = { "" + matchid };
		getTable(MatchDetailsTable.TABLENAME).delete(whereSpalten, whereValues);
		getTable(MatchHighlightsTable.TABLENAME).delete(whereSpalten, whereValues);
		getTable(MatchLineupTable.TABLENAME).delete(whereSpalten, whereValues);
		getTable(MatchLineupTeamTable.TABLENAME).delete(whereSpalten, whereValues);
		getTable(MatchLineupPlayerTable.TABLENAME).delete(whereSpalten, whereValues);
		getTable(MatchesKurzInfoTable.TABLENAME).delete(whereSpalten, whereValues);

	}

	/**
	 * delete eine Aufstellung + Positionen
	 *
	 * @param hrfId TODO Missing Constructuor Parameter Documentation
	 * @param name TODO Missing Constructuor Parameter Documentation
	 */
	public void deleteAufstellung(int hrfId, String name) {
		final String[] whereS = { "HRF_ID", "Aufstellungsname" };
		final String[] whereV = { "" + hrfId, "'" + name + "'" };

		//erst Vorhandene Aufstellung l�schen
		getTable(AufstellungTable.TABLENAME).delete(whereS, whereV);

		//Standard sys resetten
		getTable(PositionenTable.TABLENAME).delete(whereS, whereV);
	}


	private void createAllTables() {
		Object[] allTables = tables.values().toArray();
		for (int i = 0; i < allTables.length; i++) {
			AbstractTable table = (AbstractTable) allTables[i];
			table.createTable();
			String[] statements = table.getCreateIndizeStatements();
			for (int j = 0; j < statements.length; j++) {
				m_clJDBCAdapter.executeUpdate(statements[j]);
			}
		}
	}

	/**
	 * Set a single UserParameter in the DB
	 *
	 * @param fieldName TODO Missing Constructuor Parameter Documentation
	 * @param value TODO Missing Constructuor Parameter Documentation
	 */
	private void saveUserParameter(String fieldName, int value) {
		((UserConfigurationTable) getTable(UserConfigurationTable.TABLENAME)).update(fieldName,value + "");
	}

	public void saveHOColumnModel(HOColumnModel model) {
		((UserColumnsTable) getTable(UserColumnsTable.TABLENAME)).saveModel(model);
	}

	public void loadHOColumModel(HOColumnModel model) {
		((UserColumnsTable) getTable(UserColumnsTable.TABLENAME)).loadModel(model);
	}

	protected void updateDB() {
		// I introduce some new db versioning system.
		// Just add new version cases in the switch..case part
		// and leave the old ones active, so also users which
		// have skipped a version get their database updated.
		// jailbird.
		int version = 0;

		version =
			((UserConfigurationTable) getTable(UserConfigurationTable.TABLENAME)).getDBVersion();

		// If development always force to rebuild latest DB
		if (version == DBVersion && HOMainFrame.isDevelopment()) {
			version = DBVersion - 1;
		}

		// We may now update depending on the version identifier!	        
		if (version != DBVersion) {
			try {
				HOLogger.instance().log(getClass(), "Updating DB to version " + DBVersion + "...");

				switch (version) {
					case 0 :
						updateDBv1();
					case 1 :
						updateDBv2();
					case 2 :	
						updateDBv3();
					case 3 :	
						updateDBv4();
					case 4 :	
						updateDBv5();
					case 5 :	
						updateDBv6();
						
						//case 2: updateDB_v3(); // For future versions!
				}

				HOLogger.instance().log(getClass(), "done.");
			} catch (Exception e) {
				HOLogger.instance().log(getClass(), "failed.");
			}
		} else {
			HOLogger.instance().log(getClass(), "No DB update necessary.");
		}
	}

	/**
	  * TODO Missing Method Documentation
	  *
	  * @throws Exception TODO Missing Method Exception Documentation
	  */
	private void updateDBv1() throws Exception {
		// Add TimeZone and DBVersion field
		m_clJDBCAdapter.executeUpdate("ALTER TABLE UserParameter ADD COLUMN DBVersion INTEGER");
		m_clJDBCAdapter.executeUpdate(
			"ALTER TABLE UserParameter ADD COLUMN TimeZoneDifference INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE UserParameter SET TimeZoneDifference = 0");

		// Add fields to Scout and set default values for existing entries
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN PlayerID INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SCOUT SET PlayerID = 0");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN Speciality INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SCOUT SET Speciality = 0");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN Price INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SCOUT SET Price = 0");

		m_clJDBCAdapter.executeUpdate("ALTER TABLE UserParameter ADD COLUMN HOUsers INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE UserParameter SET HOUsers = 0");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE UserParameter ADD COLUMN HOUsersTot INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE UserParameter SET HOUsersTot = 0");
		m_clJDBCAdapter.executeUpdate(
			"ALTER TABLE UserParameter ADD COLUMN AAP_ExcludeLastLineup BIT");
		m_clJDBCAdapter.executeUpdate("UPDATE UserParameter SET AAP_ExcludeLastLineup = false");

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		m_clJDBCAdapter.executeUpdate("UPDATE UserParameter SET DBVersion = 1");
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @throws Exception TODO Missing Method Exception Documentation
	 */
	private void updateDBv2() throws Exception {

		// Move Future Weeks from TA into Main HO Database
		//			createFutureTrainingsTabelle();
		getTable(SpielerSkillupTable.TABLENAME).createTable();
		getTable(FutureTrainingTable.TABLENAME).createTable();
		getTable(UserConfigurationTable.TABLENAME).createTable();
		getTable(UserColumnsTable.TABLENAME).createTable();
		m_clJDBCAdapter.executeUpdate(
			"INSERT INTO FUTURETRAINING select * from TRAININGEXPERIENCE_TRAINWEEK");
		m_clJDBCAdapter.executeUpdate("DROP TABLE TRAININGEXPERIENCE_TRAINWEEK");

		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN AGE INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SCOUT SET AGE=ALTER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT DROP COLUMN ALTER");

		m_clJDBCAdapter.executeUpdate("ALTER TABLE SPIELER ADD COLUMN AGE INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE SPIELER SET AGE=ALTER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SPIELER DROP COLUMN ALTER");

		DBUserParameter userParameter = new DBUserParameter();
		userParameter.loadUserParameter(m_clJDBCAdapter);
		userParameter = null;

		UserConfigurationTable configTable = (UserConfigurationTable) getTable(UserConfigurationTable.TABLENAME);
		configTable.store(UserParameter.instance());
		configTable.store(HOParameter.instance());
		configTable.store(FormulaFactors.instance());

		m_clJDBCAdapter.executeUpdate("DROP TABLE UserParameter");
		m_clJDBCAdapter.executeUpdate("DROP TABLE SpaltenReihenfolge");

		m_clJDBCAdapter.executeUpdate(
			"UPDATE MATCHLINEUPPLAYER SET HOPOSCODE = 21 WHERE POSITIONCODE = 11 AND TAKTIK = 4");
		m_clJDBCAdapter.executeUpdate(
			"UPDATE MATCHLINEUPPLAYER SET HOPOSCODE = 21 WHERE POSITIONCODE = 10 AND TAKTIK = 4");

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		saveUserParameter("DBVersion", 2);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @throws Exception TODO Missing Method Exception Documentation
	 */
	private void updateDBv3() throws Exception {
				
		changeColumnType("MATCHDETAILS","Matchreport","Matchreport","VARCHAR(8196)");
		changeColumnType("MatchHighlights","EventText","EventText","VARCHAR(512)");
		changeColumnType("FAKTOREN","HOPosition","HOPosition","INTEGER");
		changeColumnType("POSITIONEN","Taktik","Taktik","INTEGER");
		changeColumnType("SPIELERNOTIZ","userPos","userPos","INTEGER");
		
		m_clJDBCAdapter.executeUpdate("ALTER TABLE MatchHighLights RENAME TO MATCHHIGHLIGHTS");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SpielerSkillup RENAME TO SPIELERSKILLUP");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE USERCOLUMNS ADD COLUMN COLUMN_WIDTH INTEGER");
		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		saveUserParameter("DBVersion", 3);
	}
	
	/**
	 * TODO Missing Method Documentation
	 *
	 * @throws Exception TODO Missing Method Exception Documentation
	 */
	private void updateDBv4() throws Exception {
				
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT DROP COLUMN Finanzberater");

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		saveUserParameter("DBVersion", 4);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @throws Exception TODO Missing Method Exception Documentation
	 */
	private void updateDBv5() throws Exception {
				
		m_clJDBCAdapter.executeUpdate("ALTER TABLE TEAM ADD COLUMN STAMINATRAININGPART INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE TRAINING ADD COLUMN STAMINATRAININGPART INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE FUTURETRAINING ADD COLUMN STAMINATRAININGPART INTEGER");
		m_clJDBCAdapter.executeUpdate("UPDATE FUTURETRAINING SET STAMINATRAININGPART=5");		

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		saveUserParameter("DBVersion", 5);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @throws Exception TODO Missing Method Exception Documentation
	 */
	private void updateDBv6() throws Exception {
				
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SPIELER ADD COLUMN AGEDAYS INTEGER");
		m_clJDBCAdapter.executeUpdate("ALTER TABLE SCOUT ADD COLUMN AGEDAYS INTEGER");

		HOLogger.instance().info(this.getClass(), "Resetting training parameters to default values");
		// Reset Training Speed Parameters for New Training
		UserConfigurationTable userConf = (UserConfigurationTable) getTable(UserConfigurationTable.TABLENAME); 
		userConf.update("DAUER_TORWART", "3.7");
		userConf.update("DAUER_VERTEIDIGUNG", "6.1");
		userConf.update("DAUER_SPIELAUFBAU", "5.5");
		userConf.update("DAUER_PASSPIEL", "4.5");
		userConf.update("DAUER_FLUEGELSPIEL", "3.8");
		userConf.update("DAUER_CHANCENVERWERTUNG", "5.2");
		userConf.update("DAUER_STANDARDS", "2.0");

		userConf.update("AlterFaktor", "1.0");
		userConf.update("TrainerFaktor", "1.0");
		userConf.update("CoTrainerFaktor", "1.0");
		userConf.update("IntensitaetFaktor", "1.0");

		// Always set field DBVersion to the new value as last action.
		// Do not use DBVersion but the value, as update packs might
		// do version checking again before applying!
		saveUserParameter("DBVersion", 6);
	}
	private void changeColumnType(String table,String oldName, String newName, String type) {
		m_clJDBCAdapter.executeUpdate("ALTER TABLE "+table+" ADD COLUMN TEMPCOLUMN "+ type);
		m_clJDBCAdapter.executeUpdate("UPDATE "+table+" SET TEMPCOLUMN="+oldName);
		m_clJDBCAdapter.executeUpdate("ALTER TABLE "+table+" DROP COLUMN "+oldName);		
		m_clJDBCAdapter.executeUpdate("ALTER TABLE "+table+" ADD COLUMN "+newName+" "+ type);
		m_clJDBCAdapter.executeUpdate("UPDATE "+table+" SET "+newName+"=TEMPCOLUMN");	
		m_clJDBCAdapter.executeUpdate("ALTER TABLE "+table+" DROP COLUMN TEMPCOLUMN");	
	}	
	/**
	 * Alle \ entfernen
	 *
	 * @param text TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public static String deleteEscapeSequences(String text) {
		if (text == null) {
			return "";
		}

		final StringBuffer buffer = new StringBuffer();
		final char[] chars = text.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '�') {
				buffer.append("\\");
			} else if (chars[i] != '#') {
				buffer.append("" + chars[i]);
			} else {
				buffer.append("'");
			}
		}

		return buffer.toString();
	}

	/**
	 * ' " und � codieren durch \
	 *
	 * @param text TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public static String insertEscapeSequences(String text) {
		if (text == null) {
			return "";
		}
		final StringBuffer buffer = new StringBuffer();
		final char[] chars = text.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			int code = (int) chars[i];		
			if ((chars[i] == '"') || (chars[i] == '\'') || (chars[i] == '�')) {							
				buffer.append("#");
			} else if ( code == 92) {
				buffer.append("�");
			} else {										
				buffer.append("" + chars[i]);
			}
		}

		return buffer.toString();
	}





}
