// %428559634:hoplugins.teamAnalyzer.ui.controller%
package hoplugins.teamAnalyzer.ui.controller;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.manager.TeamManager;
import hoplugins.teamAnalyzer.vo.Team;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Action listener for the favourite teams menu item
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class FavoriteItemListener implements ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    /** The favourite team */
    Team team;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FavoriteItemListener object.
     *
     * @param _team the team of the item
     */
    public FavoriteItemListener(Team _team) {
        super();
        team = _team;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Action performed event listener If item is clicked show the team in the main comboBox
     *
     * @param arg0 the event
     */
    public void actionPerformed(ActionEvent arg0) {
        boolean isInList = TeamManager.isTeamInList(team.getTeamId());

        if (!isInList) {
            TeamManager.addFavouriteTeam(team);
        }

        Team t = TeamManager.getTeam(team.getTeamId());

        SystemManager.setActiveTeam(t);
        TeamManager.forceUpdate();
        SystemManager.refresh();
    }
}
