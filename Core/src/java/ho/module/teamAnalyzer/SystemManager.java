// %2940960156:hoplugins.teamAnalyzer%
package ho.module.teamAnalyzer;

import ho.module.teamAnalyzer.manager.MatchManager;
import ho.module.teamAnalyzer.manager.MatchPopulator;
import ho.module.teamAnalyzer.manager.NameManager;
import ho.module.teamAnalyzer.manager.ReportManager;
import ho.module.teamAnalyzer.manager.TeamManager;
import ho.module.teamAnalyzer.ui.TeamAnalyzerPanel;
import ho.module.teamAnalyzer.vo.Team;

import java.util.ArrayList;

import de.hattrickorganizer.model.HOVerwaltung;


/**
 * This is a class where all the relevant and shared plugin info are kept
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class SystemManager {
	public final static String ISNUMERICRATING = "TA_numericRating";
    public final static String  ISLINEUP= "TA_lineupCompare"; 
    public final static String  ISTACTICDETAIL= "TA_tacticDetail";
    public final static String  ISDESCRIPTIONRATING= "TA_descriptionRating";
    public final static String  ISSHOWUNAVAILABLE= "TA_isShowPlayerInfo";
    public final static String  ISMIXEDLINEUP= "TA_mixedLineup";
    public final static String  ISSTARS= "TA_isStars";
    public final static String  ISTOTALSTRENGTH= "TA_isTotalStrength";
    public final static String  ISSQUAD= "TA_isSquad";
    public final static String  ISSMARTSQUAD= "TA_isSmartSquad";
    public final static String  ISLODDARSTATS= "TA_isLoddarStats";
    public final static String  ISSHOWPLAYERINFO= "TA_isShowPlayerInfo";
    public final static String  ISCHECKTEAMNAME= "TA_isCheckTeamName";
	//~ Static fields/initializers -----------------------------------------------------------------

    /** The Selected Team */
    private static Team selectedTeam;

    /** The next league opponent team */
    private static Team leagueOpponent;

    /** The next cup/friendly opponent team */
    private static Team cupOpponent;


    /** Boolean for the updating process being ongoing */
    private static boolean updating = false;

    /** Reference to the plugin itself */
    private static TeamAnalyzerPanel plugin;


    /**
     * Set the active team
     *
     * @param team
     */
    public static void setActiveTeam(Team team) {
        selectedTeam = team;
    }

    /**
     * Get the active team ID
     *
     * @return
     */
    public static int getActiveTeamId() {
        return selectedTeam.getTeamId();
    }

    /**
     * Get the active team Name
     *
     * @return
     */
    public static String getActiveTeamName() {
        return selectedTeam.getName();
    }

    /**
     * Get next cup/friendly opponent team Id
     *
     * @return
     */
    public static int getCupOpponentId() {
        return cupOpponent.getTeamId();
    }

     /**
     * Get next league opponent team Id
     *
     * @return
     */
    public static int getLeagueOpponentId() {
        return leagueOpponent.getTeamId();
    }

    /**
     * Returns the main Plugin class
     *
     * @return
     */
    public static TeamAnalyzerPanel getPlugin() {
        return plugin;
    }

    /**
     * Initialize the instance
     *
     * @param aPlugin main plugin class
     */
    public static void initialize(TeamAnalyzerPanel aPlugin) {
        plugin = aPlugin;
        leagueOpponent = TeamManager.getNextLeagueOpponent();
        cupOpponent = TeamManager.getNextCupOpponent();

        if (leagueOpponent.getTeamId() != 0) {
            setActiveTeam(leagueOpponent);
        } else if (cupOpponent.getTeamId() != 0) {
            setActiveTeam(cupOpponent);
        } else {
            Team team = new Team();

            team.setName(HOVerwaltung.instance().getModel().getBasics().getTeamName());
            team.setTeamId(HOVerwaltung.instance().getModel().getBasics().getTeamId());
            setActiveTeam(team);
        }
    }

    /**
     * Refresh all the plugins data after an event
     */
    public static void refresh() {
        NameManager.clean();
        TeamAnalyzerPanel.filter.setMatches(new ArrayList<String>());

        if (getActiveTeamId() == getCupOpponentId()) {
        	 TeamAnalyzerPanel.filter.setLeague(false);
        	 TeamAnalyzerPanel.filter.setQualifier(false);
        	 TeamAnalyzerPanel.filter.setCup(true);
        	 TeamAnalyzerPanel.filter.setFriendly(true);
        } else {
        	 TeamAnalyzerPanel.filter.setLeague(true);
        	 TeamAnalyzerPanel.filter.setQualifier(true);
        	 TeamAnalyzerPanel.filter.setCup(false);
        	 TeamAnalyzerPanel.filter.setFriendly(false);
        }

        ReportManager.clean();
        MatchPopulator.clean();
        MatchManager.clean();
        plugin.getMainPanel().reload(null, 0, 0);
        updateUI();
    }

    /**
     * Refrwsh only the data without recalculating everything
     */
    public static void refreshData() {
        if (!updating) {
            leagueOpponent = TeamManager.getNextLeagueOpponent();
            cupOpponent = TeamManager.getNextCupOpponent();
            NameManager.clean();
            TeamManager.clean();
            refresh();
        }
    }

    /**
     * Recalculate the report
     */
    public static void updateReport() {
        updating = true;
        ReportManager.updateReport();
        updating = false;
        updateUI();
    }

    /**
     * Update the UI
     */
    public static void updateUI() {
        plugin.reload();

    }
}
