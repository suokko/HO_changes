// %2940960156:hoplugins.teamAnalyzer%
package hoplugins.teamAnalyzer;

import hoplugins.Commons;
import hoplugins.TeamAnalyzer;

import hoplugins.teamAnalyzer.dao.PluginConfiguration;
import hoplugins.teamAnalyzer.manager.MatchManager;
import hoplugins.teamAnalyzer.manager.MatchPopulator;
import hoplugins.teamAnalyzer.manager.NameManager;
import hoplugins.teamAnalyzer.manager.ReportManager;
import hoplugins.teamAnalyzer.manager.TeamManager;
import hoplugins.teamAnalyzer.vo.Filter;
import hoplugins.teamAnalyzer.vo.Team;
import hoplugins.teamAnalyzer.vo.TeamLineup;

import java.util.ArrayList;


/**
 * This is a class where all the relevant and shared plugin info are kept
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class SystemManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** The Selected Team */
    private static Team selectedTeam;

    /** The next league opponent team */
    private static Team leagueOpponent;

    /** The next cup/friendly opponent team */
    private static Team cupOpponent;

    /** The filters */
    private static Filter filter = new Filter();

    /** Boolean for the updating process being ongoing */
    private static boolean updating = false;

    /** Reference to the plugin itself */
    private static TeamAnalyzer plugin;

    /** The Plugin configuration */
    private static PluginConfiguration config;

    //~ Methods ------------------------------------------------------------------------------------

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
     * Update the plugin configuration
     *
     * @param configuration
     */
    public static void setConfig(PluginConfiguration configuration) {
        config = configuration;
    }

    /**
     * Returns the plugin configuration
     *
     * @return
     */
    public static PluginConfiguration getConfig() {
        return config;
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
     * Returns the actual filter settings
     *
     * @return
     */
    public static Filter getFilter() {
        return filter;
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
    public static TeamAnalyzer getPlugin() {
        return plugin;
    }

    /**
     * Initialize the instance
     *
     * @param aPlugin main plugin class
     */
    public static void initialize(TeamAnalyzer aPlugin) {
        config = new PluginConfiguration();
        plugin = aPlugin;
        leagueOpponent = TeamManager.getNextLeagueOpponent();
        cupOpponent = TeamManager.getNextCupOpponent();

        if (leagueOpponent.getTeamId() != 0) {
            setActiveTeam(leagueOpponent);
        } else if (cupOpponent.getTeamId() != 0) {
            setActiveTeam(cupOpponent);
        } else {
            Team team = new Team();

            team.setName(Commons.getModel().getBasics().getTeamName());
            team.setTeamId(Commons.getModel().getBasics().getTeamId());
            setActiveTeam(team);
        }
    }

    /**
     * Refresh all the plugins data after an event
     */
    public static void refresh() {
        NameManager.clean();
        filter.setMatches(new ArrayList<String>());

        if (getActiveTeamId() == getCupOpponentId()) {
            filter.setLeague(false);
            filter.setQualifier(false);
            filter.setCup(true);
            filter.setFriendly(true);
        } else {
            filter.setLeague(true);
            filter.setQualifier(true);
            filter.setCup(false);
            filter.setFriendly(false);
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
        TeamLineup lineup = ReportManager.getLineup();

        if (lineup != null) {
            plugin.getFilterPanel().reload();
            plugin.getMainPanel().reload(lineup, 0, 0);
            plugin.getRecapPanel().reload(lineup);
            plugin.getRatingPanel().reload(lineup);
        } else {
            plugin.getFilterPanel().reload();
            plugin.getMainPanel().reload(null, 0, 0);
            plugin.getRecapPanel().reload(null);
            plugin.getRatingPanel().reload(null);
        }

        if (config.isLineup()) {
            plugin.getSimButton().setVisible(true);
        } else {
            plugin.getSimButton().setVisible(false);
        }
    }
}
