// %612514260:plugins%
package plugins;

import ho.core.db.DBAdapter;
import ho.core.db.JDBCAdapter;
import ho.core.training.TrainingPerWeek;
import ho.tool.arenasizer.Stadium;

import java.util.Date;
import java.util.List;
import java.util.Vector;



/**
 * Interface to access all HO! functions
 */
public interface IHOMiniModel {
    //~ Methods ------------------------------------------------------------------------------------

    //-------------------------------------------------------

    /**
     * Getter for property m_clDBAdapter.
     *
     * @return Value of property m_clDBAdapter.
     */
    public JDBCAdapter getAdapter();

    /**
     * Get the the IDBAdapter instance
     *
     * @return a valid IDBAdapter instance
     */
    public DBAdapter getDBAdapter();

    /**
     * Returns all Player, you have ever had, but not the actual player. To get all player, you
     * have ever had, use getAllSpieler and getAllOldSpieler
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector<ISpieler> getAllOldSpieler();

    /**
     * Returns all Player of the actual HRF
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector<ISpieler> getAllSpieler();


    /**
     * gets manual added/changed Trainingsweeks from DB
     *
     * @return TODO Missing Return Method Documentation
     */
    public Vector<TrainingPerWeek> getDBManualTrainingsVector();

    /**
     * Returns the HO! Version
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getHOVersion();

    /**
     * Getter for property m_clHelper.
     *
     * @return Value of property m_clHelper.
     */
    public plugins.IHelper getHelper();

    /**
     * Get the Lineup shown in HO!
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.ILineUp getLineUP();

    /**
     * Gibt die MatchDetails zu einem Match zurück
     *
     * @param matchId TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchDetails getMatchDetails(int matchId);

    /**
     * gets lineup to a specified match
     *
     * @param matchID TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchLineup getMatchLineup(int matchID);

    /**
     * gets Match Prediction Manager
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchPredictionManager getMatchPredictionManager();

    /**
     * holt die MAtches zu einem Team aus der DB
     *
     * @param teamId Die Teamid oder -1 für alle
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchKurzInfo[] getMatchesKurzInfo(int teamId);

    /**
     * Important: if teamid = -1 then matchtyp must be ISpielePanel.ALLE_SPIELE !
     *
     * @param teamId Teamid oder -1 for all
     * @param matchtyp Const in ISpielePanel
     * @param asc Order by Matchdate (false= DESC)
     *
     * @return TODO Missing Return Method Documentation
     */
    public IMatchKurzInfo[] getMatchesKurzInfo(int teamId, int matchtyp, boolean asc);

    /**
     * Returns the String connected to the active language file or connected
     * to the english language file. Returns !key! if the key can not be found.
     *
     * @param key Key to be searched in language files
     *
     * @return String connected to the key or !key! if nothing can be found in language files
     */
    public String getLanguageString(String key);

    /**
     * Getter for property m_clResource. usage minimodel.getLanguageString("Download") to
     * get Name for download in current language
     *
     * @return Value of property m_clResource.
     *
     * @deprecated use getLanguageString()
     */
    @Deprecated
	public java.util.Properties getResource();

    /**
     * Returns the player with the id
     *
     * @param id TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public ISpieler getSpieler(int id);

    /**
     * Fetches players data to a given time
     *
     * @param spielerid ID of player
     * @param time timestamp to use
     *
     * @return ISpieler containing data for player, null if no match around that date can be found!
     */
    public ISpieler getSpielerAtDate(int spielerid, java.sql.Timestamp time);


    //----------Team----------------------------------------

    /**
     * Returns the team
     *
     * @return TODO Missing Return Method Documentation
     */
    public ITeam getTeam();

    /**
     * Returns the Coach
     *
     * @return TODO Missing Return Method Documentation
     */
    public ISpieler getTrainer();

    /**
     * gets ITrainingsManager
     *
     * @return TODO Missing Return Method Documentation
     */
    public ITrainingsManager getTrainingsManager();

    /**
     * Getter for property m_clUserSettings.
     *
     * @return Value of property m_clUserSettings.
     */
    public ho.core.model.UserParameter getUserSettings();

    //----------Verein----------------------------------------

     /**
     * Getter for property m_clXMLParser.
     *
     * @return Value of property m_clXMLParser.
     */
    public plugins.IXMLParser getXMLParser();

 
    /**
     * deletes HRF from DB a refresh is needed after this func is called! IGUI->doRefresh();
     *
     * @param hrfid TODO Missing Constructuor Parameter Documentation
     */
    public void deleteHRF(int hrfid);

    /**
     * stores TrainingWeek in database
     *
     * @param training TODO Missing Constructuor Parameter Documentation
     */
    public void saveTraining(ITrainingWeek training);


	/**
	 * Creates a ISpieler instance for external use
	 *
	 * @param data player data to be created
	 *
	 * @return Spieler instance
	 */
	public ISpieler createPlayer(IPlayerData data);

	/**
	 * List of useful data for export
	 *
	 * @param startingDate starting data to export from (for non friendlies)
	 * @param startingDateForFriendlies starting data to export from (for friendlies)
	 *
	 * @return List of IExportMatchData objects
	 */
	public List<IExportMatchData> getDataUsefullMatches(Date startingDate, Date startingDateForFriendlies);

	/**
	 * List of useful data for export
	 *
	 * @param startingDate starting data to export from (for non friendlies)
	 * @param startingDateForFriendlies starting data to export from (for friendlies)
	 * @param strict is true, export only matches *without* cards, injuries, tactical problems / overconfidence / weather SE...
	 *
	 * @return List of IExportMatchData objects
	 */
	public List<IExportMatchData> getDataUsefullMatches(Date startingDate, Date startingDateForFriendlies, boolean strict);

	/**
	 * Log something in HOs debug log.
	 *
	 * @param caller the logging class
	 * @param message the text to log
	 */
	public void log(Class<?> caller, String message);

	/**
	 * Log a throwable in HOs debug log.
	 *
	 * @param caller the logging class
	 * @param t the Throwable/Exception
	 */
	public void log(Class<?> caller, Throwable t);
}
