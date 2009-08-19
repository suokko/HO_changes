// %1056836646:de.hattrickorganizer.model%
package de.hattrickorganizer.model;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import plugins.IBasics;
import plugins.IDBAdapter;
import plugins.IEPV;
import plugins.IExportMatchData;
import plugins.IFinanzen;
import plugins.IFutureTrainingManager;
import plugins.IFutureTrainingWeek;
import plugins.IHOFriendlyManager;
import plugins.IHOMiniModel;
import plugins.IJDBCAdapter;
import plugins.ILiga;
import plugins.IMatchDetails;
import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import plugins.IMatchPredictionManager;
import plugins.IPlayerData;
import plugins.ISpieler;
import plugins.ISpielplan;
import plugins.IStadium;
import plugins.ITeam;
import plugins.ITrainingWeek;
import plugins.ITrainingsManager;
import plugins.IVerein;
import plugins.IXtraData;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.logik.FutureTrainingManager;
import de.hattrickorganizer.logik.exporter.MatchExporter;


/**
 * Die Klasse bündelt alle anderen Model, die zu einer HRF Datei gehören. Die Daten können
 * natürlich auch aus der Datenbank kommen
 */
public class HOMiniModel implements IHOMiniModel {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static HOMiniModel m_clInstance;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new HOMiniModel object.
     */
    private HOMiniModel() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static HOMiniModel instance() {
        if (m_clInstance == null) {
            m_clInstance = new HOMiniModel();
        }

        return m_clInstance;
    }

    /**
     * Getter for property m_clDBAdapter.
     *
     * @return Value of property m_clDBAdapter.
     */
    public IJDBCAdapter getAdapter() {
        return de.hattrickorganizer.database.DBZugriff.instance().getAdapter();
    }

    /**
     * Get the the IDBAdapter instance
     *
     * @return a valid IDBAdapter instance
     */
    public IDBAdapter getDBAdapter() {
        return de.hattrickorganizer.database.DBZugriff.instance().getDBAdapter();
    }

    /**
     * Gibt alle alte Spieler zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector<ISpieler> getAllOldSpieler() {
        return getModel().getAllOldSpieler();
    }

    //---------Spieler--------------------------------------

    /**
     * Gibt alle Spieler zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector<ISpieler> getAllSpieler() {
        return getModel().getAllSpieler();
    }

    //----------Basics----------------------------------------

    /**
     * Gibt die Basics zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public IBasics getBasics() {
        return getModel().getBasics();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector<TrainingPerWeek> getDBManualTrainingsVector() {
        return DBZugriff.instance().getTrainingsVector();
    }

    /**
     * Getter for property m_lcDownloadHelper.
     *
     * @return Value of property m_lcDownloadHelper.
     */
    public plugins.IDownloadHelper getDownloadHelper() {
        return de.hattrickorganizer.net.MyConnector.instance();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IEPV getEPV() {
        return getModel().getEPV();
    }

    //-------Finanzen---------------------------------------

    /**
     * Gibt die Finanzen zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public IFinanzen getFinanzen() {
        return getModel().getFinanzen();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.IGUI getGUI() {
        return de.hattrickorganizer.gui.pluginWrapper.GUIPluginWrapper.instance();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IHOFriendlyManager getHOFriendlyManager() {
        return getModel().getHOFriendlyManager();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getHOVersion() {
        return de.hattrickorganizer.gui.HOMainFrame.VERSION;
    }

    /**
     * Getter for property m_clHelper.
     *
     * @return Value of property m_clHelper.
     */
    public plugins.IHelper getHelper() {
        return de.hattrickorganizer.tools.HelperWrapper.instance();
    }

    //----------Liga----------------------------------------

    /**
     * Gibt die Basics zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public ILiga getLiga() {
        return getModel().getLiga();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.ILineUp getLineUP() {
        return getModel().getAufstellung();
    }

    /**
     * Gibt die MatchDetails zu einem Match zurück
     *
     * @param matchId TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchDetails getMatchDetails(int matchId) {
        return de.hattrickorganizer.database.DBZugriff.instance().getMatchDetails(matchId);
    }

    /**
     * gets lineup to a specified match
     *
     * @param matchID TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchLineup getMatchLineup(int matchID) {
        return de.hattrickorganizer.database.DBZugriff.instance().getMatchLineup(matchID);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchPredictionManager getMatchPredictionManager() {
        return de.hattrickorganizer.logik.matchengine.MatchPredictionManager.instance();
    }

    /**
     * holt die MAtches zu einem Team aus der DB
     *
     * @param teamId Die Teamid oder -1 für alle
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchKurzInfo[] getMatchesKurzInfo(int teamId) {
        return de.hattrickorganizer.database.DBZugriff.instance().getMatchesKurzInfo(teamId);
    }

    /**
     * Wichtig: Wenn die Teamid = -1 ist muss der Matchtyp ALLE_SPIELE sein!
     *
     * @param teamId Die Teamid oder -1 für alle
     * @param matchtyp Const in ISpielePanel!
     * @param asc TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchKurzInfo[] getMatchesKurzInfo(int teamId, int matchtyp, boolean asc) {
        return de.hattrickorganizer.database.DBZugriff.instance().getMatchesKurzInfo(teamId,
                                                                                     matchtyp, asc);
    }
    
    /**
     * Returns the String connected to the active language file or connected
     * to the english language file. Returns !key! if the key can not be found. 
     *  
     * @param key Key to be searched in language files
     * 
     * @return String connected to the key or !key! if nothing can be found in language files
     */
    public String getLanguageString(String key) {
    	return HOVerwaltung.instance().getLanguageString(key);
    }

    /**
     * Getter for property m_clResource.
     * 
     * !!!PLEASE USE getLanguageString from now on!!!
     *
     * @return Value of property m_clResource. usage
     *         minimodel.getLanguageString("Download") to get Name for download in current
     *         language
     *         
     * @deprecated use getLanguageString()
     */
    @Deprecated
	public java.util.Properties getResource() {
        return HOVerwaltung.instance().getResource();
    }

    /**
     * Gibt den Spieler mit der ID zurück
     *
     * @param id TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public ISpieler getSpieler(int id) {
        return getModel().getSpieler(id);
    }

    /**
     * Fetches players data to a given time
     *
     * @param spielerid ID of player
     * @param time timestamp to use
     *
     * @return ISpieler containing data to player
     */
    public ISpieler getSpielerAtDate(int spielerid, java.sql.Timestamp time) {
        return de.hattrickorganizer.database.DBZugriff.instance().getSpielerAtDate(spielerid, time);
    }

    /**
     * get ALL League Fixtures stored in DB
     *
     * @return TODO Missing Return Method Documentation
     */
    public ISpielplan[] getSpielplaene() {
        return de.hattrickorganizer.database.DBZugriff.instance().getAllSpielplaene(true);
    }

    /**
     * get League Fixtures for specified league and season
     *
     * @param ligaId LeagueLevelUnitID -> XtraData
     * @param saison Season If both params are set to -1 latestet downloaded league Fixtures is
     *        fetched. This must not be the actual one! that can be a former season or a league
     *        not of user Team , just the latest downloaded by User!
     *
     * @return TODO Missing Return Method Documentation
     */
    public ISpielplan getSpielplan(int ligaId, int saison) {
        return de.hattrickorganizer.database.DBZugriff.instance().getSpielplan(ligaId, saison);
    }

    //--------Stadium----------------------------------------

    /**
     * Gibt das Stadium zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public IStadium getStadium() {
        return getModel().getStadium();
    }

    //----------Team----------------------------------------

    /**
     * Gibt das Team zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public ITeam getTeam() {
        return getModel().getTeam();
    }

    /**
     * Gibt den Trainer zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public ISpieler getTrainer() {
        return getModel().getTrainer();
    }

    /**
     * gets ITrainingsManager
     *
     * @return TODO Missing Return Method Documentation
     */
    public ITrainingsManager getTrainingsManager() {
        return de.hattrickorganizer.logik.TrainingsManager.instance();
    }

    /**
     * Getter for property m_clUserSettings.
     *
     * @return Value of property m_clUserSettings.
     */
    public gui.UserParameter getUserSettings() {
        return gui.UserParameter.instance();
    }

    //----------Verein----------------------------------------

    /**
     * Gibt den Verein zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public IVerein getVerein() {
        return getModel().getVerein();
    }

    /**
     * Getter for property m_clXMLParser.
     *
     * @return Value of property m_clXMLParser.
     */
    public plugins.IXMLParser getXMLParser() {
        return de.hattrickorganizer.tools.xml.XMLManager.instance();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IXtraData getXtraDaten() {
        return getModel().getXtraDaten();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param hrfid TODO Missing Method Parameter Documentation
     */
    public void deleteHRF(int hrfid) {
        de.hattrickorganizer.database.DBZugriff.instance().deleteHRF(hrfid);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param training TODO Missing Method Parameter Documentation
     */
    public void saveTraining(ITrainingWeek training) {
        de.hattrickorganizer.database.DBZugriff.instance().saveTraining((de.hattrickorganizer.model.TrainingPerWeek) training);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private HOModel getModel() {
        return HOVerwaltung.instance().getModel();
    }

	public IFutureTrainingManager getFutureTrainingManager(ISpieler p, List<IFutureTrainingWeek> trainings, int cotrainer, int keeper, int trainerLvl) {
		FutureTrainingManager ftm = new FutureTrainingManager(p,trainings,cotrainer,keeper,trainerLvl);
		return ftm;
	}

	public List<IFutureTrainingWeek> getFutureTrainingWeeks() {
		return DBZugriff.instance().getFutureTrainingsVector();
	}

	public void saveFutureTraining(IFutureTrainingWeek training) {
		DBZugriff.instance().saveFutureTraining(training);
	}

	public ISpieler createPlayer(IPlayerData data) {
		final Spieler tempSpieler = new Spieler();
		tempSpieler.setNationalitaet(HOVerwaltung.instance().getModel().getBasics().getLand());
		tempSpieler.setSpielerID(data.getPlayerId());
		tempSpieler.setName(data.getPlayerName());
		tempSpieler.setTSI(data.getTSI());
		tempSpieler.setSpezialitaet(data.getSpeciality());
		tempSpieler.setAlter(data.getAge());
		tempSpieler.setErfahrung(data.getExperience());
		tempSpieler.setForm(data.getForm());
		tempSpieler.setKondition((int) data.getStamina());

		tempSpieler.setVerteidigung((int) data.getDefense());
		tempSpieler.setTrainingsOffsetVerteidigung(data.getDefense()%1);

		tempSpieler.setTorschuss((int) data.getAttack());
		tempSpieler.setTrainingsOffsetTorschuss(data.getAttack()%1);

		tempSpieler.setTorwart((int) data.getGoalKeeping());
		tempSpieler.setTrainingsOffsetTorwart(data.getAttack()%1);

		tempSpieler.setFluegelspiel((int) data.getWing());
		tempSpieler.setTrainingsOffsetFluegelspiel(data.getWing()%1);

		tempSpieler.setPasspiel((int) data.getPassing());
		tempSpieler.setTrainingsOffsetPasspiel(data.getPassing()%1);

		tempSpieler.setStandards((int) data.getSetPieces());
		tempSpieler.setTrainingsOffsetStandards(data.getSetPieces()%1);

		tempSpieler.setSpielaufbau((int) data.getPlayMaking());
		tempSpieler.setTrainingsOffsetSpielaufbau(data.getPlayMaking()%1);

		return tempSpieler;
	}

	/**
	 * List of useful data for export
	 *
	 * @param startingDate starting data to export from (for non friendlies)
	 * @param startingDateForFriendlies starting data to export from (for friendlies)
	 *
	 * @return List of ExportMatchData objects
	 */
	public List<IExportMatchData> getDataUsefullMatches(Date startingDate, Date startingDateForFriendlies) {
		return MatchExporter.getDataUsefullMatches(startingDate, startingDateForFriendlies);
	}

	/**
	 * List of useful data for export
	 *
	 * @param startingDate starting data to export from (for non friendlies)
	 * @param startingDateForFriendlies starting data to export from (for friendlies)
	 * @param strict is true, export only matches *without* tactical problems / overconfidence / weather SE
	 *
	 * @return List of IExportMatchData objects
	 */
	public List<IExportMatchData> getDataUsefullMatches(Date startingDate, Date startingDateForFriendlies, boolean strict) {
		return MatchExporter.getDataUsefullMatches(startingDate, startingDateForFriendlies, strict);
	}

}
