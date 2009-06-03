// %376589195:hoplugins.teamAnalyzer.ui.controller%
package hoplugins.teamAnalyzer.ui.controller;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.ui.component.AboutPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


/**
 * Action listener for the about menu item
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class AboutItemListener implements ActionListener {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Action performed event listener, show the about panel
     *
     * @param arg0 the event
     */
    public void actionPerformed(ActionEvent arg0) {
        JOptionPane.showMessageDialog(SystemManager.getPlugin().getPluginPanel(), new AboutPanel(),
                                      PluginProperty.getString("Menu.About"),
                                      JOptionPane.PLAIN_MESSAGE);
        ;
    }
}
