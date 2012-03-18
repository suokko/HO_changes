// %1056836646:de.hattrickorganizer.model%
package ho.core.model;

import ho.core.db.DBAdapter;
import ho.core.db.DBManager;
import ho.core.db.JDBCAdapter;
import ho.core.training.TrainingPerWeek;
import ho.core.util.HOLogger;
import ho.module.series.model.Liga;

import java.util.Vector;

import plugins.IHOMiniModel;
import plugins.IMatchDetails;
import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import plugins.IMatchPredictionManager;
import plugins.IPlayerData;
import plugins.ISpieler;
import plugins.ITeam;
import plugins.ITrainingWeek;
import plugins.ITrainingsManager;


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
    public JDBCAdapter getAdapter() {
        return ho.core.db.DBManager.instance().getAdapter();
    }

    /**
     * Get the the IDBAdapter instance
     *
     * @return a valid IDBAdapter instance
     */
    public DBAdapter getDBAdapter() {
        return ho.core.db.DBManager.instance().getDBAdapter();
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

   
    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector<TrainingPerWeek> getDBManualTrainingsVector() {
        return DBManager.instance().getTrainingsVector();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getHOVersion() {
        return ho.core.gui.HOMainFrame.VERSION;
    }

    /**
     * Getter for property m_clHelper.
     *
     * @return Value of property m_clHelper.
     */
    public plugins.IHelper getHelper() {
        return ho.core.util.HelperWrapper.instance();
    }

    //----------Liga----------------------------------------

    /**
     * Gibt die Basics zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public Liga getLiga() {
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
        return ho.core.db.DBManager.instance().getMatchDetails(matchId);
    }

    /**
     * gets lineup to a specified match
     *
     * @param matchID TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchLineup getMatchLineup(int matchID) {
        return ho.core.db.DBManager.instance().getMatchLineup(matchID);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchPredictionManager getMatchPredictionManager() {
        return ho.tool.matchPrediction.engine.MatchPredictionManager.instance();
    }

    /**
     * holt die MAtches zu einem Team aus der DB
     *
     * @param teamId Die Teamid oder -1 für alle
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchKurzInfo[] getMatchesKurzInfo(int teamId) {
        return ho.core.db.DBManager.instance().getMatchesKurzInfo(teamId);
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
        return ho.core.db.DBManager.instance().getMatchesKurzInfo(teamId,
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
        return ho.core.db.DBManager.instance().getSpielerAtDate(spielerid, time);
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
        return ho.core.training.TrainingsManager.instance();
    }

    /**
     * Getter for property m_clUserSettings.
     *
     * @return Value of property m_clUserSettings.
     */
    public ho.core.model.UserParameter getUserSettings() {
        return ho.core.model.UserParameter.instance();
    }

    //----------Verein----------------------------------------

     /**
     * Getter for property m_clXMLParser.
     *
     * @return Value of property m_clXMLParser.
     */
    public plugins.IXMLParser getXMLParser() {
        return ho.core.file.xml.XMLManager.instance();
    }


    /**
     * TODO Missing Method Documentation
     *
     * @param hrfid TODO Missing Method Parameter Documentation
     */
    public void deleteHRF(int hrfid) {
        ho.core.db.DBManager.instance().deleteHRF(hrfid);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param training TODO Missing Method Parameter Documentation
     */
    public void saveTraining(ITrainingWeek training) {
        ho.core.db.DBManager.instance().saveTraining((ho.core.training.TrainingPerWeek) training);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private HOModel getModel() {
        return HOVerwaltung.instance().getModel();
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
	 * Log something in HOs debug log.
	 *
	 * @param caller the logging class
	 * @param message the text to log
	 */
	public void log(Class<?> caller, String message) {
		HOLogger.instance().log(caller, message);
	}

	/**
	 * Log a throwable in HOs debug log.
	 *
	 * @param caller the logging class
	 * @param t the Throwable/Exception
	 */
	public void log(Class<?> caller, Throwable t) {
		HOLogger.instance().log(caller, t);
		if (t != null && t.getCause() != null) {
			HOLogger.instance().log(caller, "Caused by: " + t.getCause());
		}
	}
}
