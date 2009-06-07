// %1486442998:hoplugins.teamAnalyzer.executor%
package hoplugins.teamAnalyzer.executor;

import java.io.File;


/**
 * Class that may launch a script under Windows OS
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class WindowsExec {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Show the guide by calling the proper script
     *
     * @param cmd the command
     *
     * @return true if guide was open
     */
    public static boolean showGuide(String[] cmd) {
        try {
            File f = new File("hoplugins\\teamAnalyzer\\guide\\" + cmd[3] + ".doc");

            if (!f.exists()) {
                cmd[3] = "English";
            }

            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);

            return (proc.waitFor() == 0) ? true : false;
        } catch (Throwable t) {
            return false;
        }
    }
}
