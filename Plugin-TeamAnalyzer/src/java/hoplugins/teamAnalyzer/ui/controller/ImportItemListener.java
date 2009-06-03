// %3047457688:hoplugins.teamAnalyzer.ui.controller%
package hoplugins.teamAnalyzer.ui.controller;

import hoplugins.teamAnalyzer.ht.HattrickManager;
import hoplugins.teamAnalyzer.manager.TeamManager;
import hoplugins.teamAnalyzer.vo.Team;

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
        for (Iterator iter = TeamManager.getTeams().iterator(); iter.hasNext();) {
            Team element = (Team) iter.next();
            System.out.println("Downloading " + element.getName());
            HattrickManager.downloadPlayers(element.getTeamId());
        }
    }
}
