// %3619247323:de.hattrickorganizer.tools%
package de.hattrickorganizer.tools;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the HO logger
 *
 * @author Marco Senn
 */
public class HOLogger {
    //~ Static fields/initializers -----------------------------------------------------------------

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private static HOLogger clLogger;

    /** TODO Missing Parameter Documentation */
    public static final int DEBUG = 0;

    /** TODO Missing Parameter Documentation */
    public static final int INFORMATION = 1;

    /** TODO Missing Parameter Documentation */
    public static final int WARNING = 2;

    /** TODO Missing Parameter Documentation */
    public static final int ERROR = 3;

    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    FileWriter logWriter = null;
    private int logLevel = DEBUG;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of Logger
     */
    private HOLogger() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dirName = "logs";
        String fileName = "HO-" + dateFormat.format(new Date()) + ".log";
        try {
        	File dir = new File(dirName);
        	dir.mkdirs();

        	File logFile = new File(dir, fileName);

        	if (logFile.exists()) {
        		logFile.delete();
        	}

            logWriter = new FileWriter(logFile);
        } catch (Exception e) {
        	String msg = "Unable to create logfile: " + dirName + "/" + fileName;
            System.err.println (msg);
            e.printStackTrace();
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static HOLogger instance() {
        if (clLogger == null) {
            clLogger = new HOLogger();
        }

        return clLogger;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setLogLevel(int i) {
        logLevel = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getLogLevel() {
        return logLevel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param caller TODO Missing Method Parameter Documentation
     * @param obj TODO Missing Constructuor Parameter Documentation
     */
    public void log(Class caller, Object obj) {
    	logMessage(caller, obj.toString(), DEBUG);
    }

    public void info(Class caller, Object obj) {
    	logMessage(caller, obj.toString(), INFORMATION);
    }

    public void warning(Class caller, Object obj) {
    	logMessage(caller, obj.toString(), WARNING);
    }

    public void error(Class caller, Object obj) {
    	logMessage(caller, obj.toString(), ERROR);
    }

    public void debug(Class caller, Object obj) {
    	logMessage(caller, obj.toString(), DEBUG);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param caller TODO Missing Method Parameter Documentation
     * @param e TODO Missing Method Parameter Documentation
     */
    public void log(Class caller, Throwable e) {
    	logMessage(caller, e.toString(), ERROR);

        for (int i = 0; i < e.getStackTrace().length; i++) {
            StackTraceElement array_element = e.getStackTrace()[i];
            logMessage(caller, array_element.toString(), ERROR);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param caller TODO Missing Constructuor Parameter Documentation
     * @param text TODO Missing Method Parameter Documentation
     * @param level TODO Missing Method Parameter Documentation
     */
    private void logMessage(Class caller, String text, int level) {
        String msg = "";

        switch (level) {
            case DEBUG:
                msg = " [Debug]   ";
                break;

            case WARNING:
                msg = " [Warning] ";
                break;

            case ERROR:
                msg = " [Error]   ";
                break;

            default:
                msg = " [Info]    ";
        }

        if (level < logLevel) {
            return;
        }

		System.out.println(msg + text);

		if (logWriter != null) {
			try {
				Date d = new Date();
				String txt = (sdf.format(d) + msg + caller.getName() + ": " + text + "\r\n");
				logWriter.write(txt);
				logWriter.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }

    /**
     * TODO Missing Method Documentation
     *
     * @throws Throwable TODO Missing Method Exception Documentation
     */
    @Override
	protected void finalize() throws Throwable {
        super.finalize();

        try {
            logWriter.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
