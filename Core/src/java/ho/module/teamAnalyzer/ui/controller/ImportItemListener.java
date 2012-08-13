// %3047457688:hoplugins.teamAnalyzer.ui.controller%
package ho.module.teamAnalyzer.ui.controller;

import ho.module.teamAnalyzer.ht.HattrickManager;
import ho.module.teamAnalyzer.manager.TeamManager;
import ho.module.teamAnalyzer.vo.Team;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;


/**
 * Action listener for the help menu item
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class ImportItemListener implements ActionListener {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ImportItemListener object.
     */
    public ImportItemListener() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param arg0 TODO Missing Method Parameter Documentation
     */
    public void actionPerformed(ActionEvent arg0) {
        for (Iterator<Team> iter = TeamManager.getTeams().iterator(); iter.hasNext();) {
            Team element = iter.next();
            System.out.println("Downloading " + element.getName());
            HattrickManager.downloadPlayers(element.getTeamId());
        }
    }
}
