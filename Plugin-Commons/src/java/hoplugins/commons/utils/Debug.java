// %1267532864:hoplugins.commons.utils%
package hoplugins.commons.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.util.Date;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class Debug {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    private static final String logFile = "PluginsErrorLog.txt";

    /** TODO Missing Parameter Documentation */
    private static final String separator = "--- ";

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Debug object.
     */
    public Debug() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param s TODO Missing Method Parameter Documentation
     */
    public static synchronized void log(String s) {
        try {
            PrintStream log = openLog();
            log.println(s);
            closeLog(log);
        } catch (Exception e2) {
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public static synchronized void logException(Exception e) {
        try {
            PrintStream log = openLog();
            e.printStackTrace(log);
            closeLog(log);
        } catch (Exception e2) {
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param log TODO Missing Method Parameter Documentation
     */
    private static synchronized void closeLog(PrintStream log) {
        log.println();
        log.close();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private static synchronized PrintStream openLog() {
        PrintStream log = null;

        try {
            // Open log file in append mode.
            File file = new File(logFile);
            log = new PrintStream(new FileOutputStream(file, true));

            // Added timestamp.
            Date date = new Date();
            log.print(separator);
            log.println(date.toString());
        } catch (Exception e) {
        }

        return log;
    }
}
