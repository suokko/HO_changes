// %2136143411:hoplugins.teamAnalyzer.ui.controller%
package hoplugins.teamAnalyzer.ui.controller;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.ui.component.DownloadPanel;
import hoplugins.teamAnalyzer.vo.Team;

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
        JOptionPane.showMessageDialog(SystemManager.getPlugin().getPluginPanel(),
                                      new DownloadPanel(),
                                      PluginProperty.getString("Menu.DownloadMatch"),
                                      JOptionPane.PLAIN_MESSAGE);
        ;

        Team selectedTeam = SystemManager.getPlugin().getFilterPanel().getSelectedTeam();

        SystemManager.setActiveTeam(selectedTeam);
        SystemManager.refresh();
    }
}
