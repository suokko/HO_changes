// %612514260:plugins%
package plugins;

import java.util.List;


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
    public IJDBCAdapter getAdapter();

    /**
     * Returns all Player, you have ever had, but not the actual player. To get all player, you
     * have ever had, use getAllSpieler and getAllOldSpieler
     *
     * @return TODO Missing Return Method Documentation
     */
    public java.util.Vector getAllOldSpieler();

    /**
     * Returns all Player of the actual HRF
     *
     * @return TODO Missing Return Method Documentation
     */
    public java.util.Vector getAllSpieler();

    //----------Basics----------------------------------------

    /**
     * Returns Basics
     *
     * @return TODO Missing Return Method Documentation
     */
    public IBasics getBasics();

    /**
     * gets manual added/changed Trainingsweeks from DB
     *
     * @return TODO Missing Return Method Documentation
     */
    public java.util.Vector getDBManualTrainingsVector();

    /**
     * Getter for property m_lcDownloadHelper.
     *
     * @return Value of property m_lcDownloadHelper.
     */
    public plugins.IDownloadHelper getDownloadHelper();

	/**
	 * Returns the EPV Manager
	 * 
	 * @return The EPV Manager 
	 */
    public IEPV getEPV();
    //-------Finanzen---------------------------------------

    /**
     * Returns economics
     *
     * @return TODO Missing Return Method Documentation
     */
    public IFinanzen getFinanzen();

    /**
     * Access the HO! GUI
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.IGUI getGUI();

    /**
     * get HOFriendlyManager
     *
     * @return TODO Missing Return Method Documentation
     */
    public IHOFriendlyManager getHOFriendlyManager();

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

    //----------Liga----------------------------------------

    /**
     * Returns the league
     *
     * @return TODO Missing Return Method Documentation
     */
    public ILiga getLiga();

    /**
     * Get the Lineup shown in HO!
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.ILineUp getLineUP();

    /**
     * Gibt die MatchDetails zu einem Match zur�ck
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
     * @param teamId Die Teamid oder -1 f�r alle
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
     * Getter for property m_clResource. usage minimodel.getResource().getProperty("Download") to
     * get Name for download in current language
     *
     * @return Value of property m_clResource.
     */
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

    /**
     * get ALL League Fixtures stored in DB
     *
     * @return TODO Missing Return Method Documentation
     */
    public ISpielplan[] getSpielplaene();

    /**
     * get League Fixtures for specified league and season
     *
     * @param ligaId TODO Missing Constructuor Parameter Documentation
     * @param saison TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public ISpielplan getSpielplan(int ligaId, int saison);

    //--------Stadium----------------------------------------

    /**
     * Gibt das Stadium zur�ck
     *
     * @return TODO Missing Return Method Documentation
     */
    public IStadium getStadium();

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
    public gui.UserParameter getUserSettings();

    //----------Verein----------------------------------------

    /**
     * Gibt den Verein zur�ck
     *
     * @return TODO Missing Return Method Documentation
     */
    public IVerein getVerein();

    /**
     * Getter for property m_clXMLParser.
     *
     * @return Value of property m_clXMLParser.
     */
    public plugins.IXMLParser getXMLParser();

    /**
     * gets XtraData Object
     *
     * @return TODO Missing Return Method Documentation
     */
    public IXtraData getXtraDaten();

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
	 * gets IFutureTrainingManager
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public IFutureTrainingManager getFutureTrainingManager(ISpieler p, List trainings, int cotrainer, int keeper, int trainerLvl);
	
	
	/**
	 * gets future Trainingsweeks from DB
	 *
	 * @return TODO Missing Return Method Documentation
	 */	
	public List getFutureTrainingWeeks();

	/**
	 * stores FutureTrainingWeek in database
	 *
	 * @param training TODO Missing Constructuor Parameter Documentation
	 */	
	public void saveFutureTraining(IFutureTrainingWeek training);

	/**
	 * Creates a ISpieler instance for external use
	 *
	 * @param data player data to be created
	 * 
	 * @return Spieler instance
	 */
	public ISpieler createPlayer(IPlayerData data);	
}
