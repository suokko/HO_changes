// %2670312793:hoplugins.teamAnalyzer.executor%
package hoplugins.teamAnalyzer.executor;

import java.io.File;


/**
 * Class that may launch a script under unix OS
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class UnixExec {
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
            File f = new File("./hoplugins/teamAnalyzer/guide/" + cmd[2] + ".doc");

            if (!f.exists()) {
                cmd[2] = "English";
            }

            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);

            return (proc.waitFor() == 0) ? true : false;
        } catch (Throwable t) {
            return false;
        }
    }
}
