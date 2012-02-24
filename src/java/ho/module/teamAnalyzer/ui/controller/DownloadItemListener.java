// %2136143411:hoplugins.teamAnalyzer.ui.controller%
package ho.module.teamAnalyzer.ui.controller;

import ho.core.model.HOVerwaltung;
import ho.module.teamAnalyzer.SystemManager;
import ho.module.teamAnalyzer.ui.component.DownloadPanel;
import ho.module.teamAnalyzer.vo.Team;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;



/**
 * Action listener for the download menu item
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class DownloadItemListener implements ActionListener {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Action performed event listener Show the download dialog
     *
     * @param arg0 the event
     */
    public void actionPerformed(ActionEvent arg0) {
        JOptionPane.showMessageDialog(SystemManager.getPlugin(),
                                      new DownloadPanel(),
                                      HOVerwaltung.instance().getLanguageString("Menu.DownloadMatch"),
                                      JOptionPane.PLAIN_MESSAGE);
        ;

        Team selectedTeam = SystemManager.getPlugin().getFilterPanel().getSelectedTeam();

        SystemManager.setActiveTeam(selectedTeam);
        SystemManager.refresh();
    }
}
