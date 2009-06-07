// %4067251681:hoplugins.teamAnalyzer.ui.controller%
package hoplugins.teamAnalyzer.ui.controller;

import hoplugins.Commons;

import hoplugins.teamAnalyzer.executor.UnixExec;
import hoplugins.teamAnalyzer.executor.WindowsExec;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Action listener for the help menu item
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class HelpItemListener implements ActionListener {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Action performed event listener Lauch a script that open the guide
     *
     * @param arg0 the event
     */
    public void actionPerformed(ActionEvent arg0) {
        String osName = System.getProperty("os.name");
        String language = Commons.getModel().getHelper().getLanguageName();

        if (osName.equals("Windows 95")) {
            String[] cmd = new String[4];

            cmd[0] = "command.com";
            cmd[1] = "/C";
            cmd[2] = "hoplugins\\teamAnalyzer\\guide.bat";
            cmd[3] = language;
            WindowsExec.showGuide(cmd);
        } else if (osName.startsWith("Windows")) {
            String[] cmd = new String[4];

            cmd[0] = "cmd.exe";
            cmd[1] = "/C";
            cmd[2] = "hoplugins\\teamAnalyzer\\guide.bat";
            cmd[3] = language;
            WindowsExec.showGuide(cmd);
        } else {
            String[] cmd = new String[3];

            cmd[0] = "sh";
            cmd[1] = "./hoplugins/teamAnalyzer/guide.sh";
            cmd[2] = language;
            UnixExec.showGuide(cmd);
        }
    }
}
