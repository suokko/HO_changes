package hoplugins.feedback.dao;

import hoplugins.Commons;
import hoplugins.Feedback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public final class FeedbackSettingDAO {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Name of the table in the HO database */
    private static final String TABLE_NAME = "FEEDBACK_SETTINGS";

    static {
        checkTable();
    }

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Private default constuctor to prevent class instantiation.
     */
    private FeedbackSettingDAO() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Set the toggle to enable/disable upload
     *
     * @param type	the feedback type
     * @param auto 	Boolean value
     */
    public static void setAutomatic(int type, boolean auto) {
        setBoolean("AUTOMATIC"+type, auto);
    }

    /**
     * Gets the settings for automatic upload
     *
     * @return true if enabled
     */
    public static boolean isAutomatic(int type) {
        return getAsBoolean("AUTOMATIC"+type, true);
    }

    /**
     * Set the toggle to enable/disable stop switch (set by the feedback db)
     *
     * @param type	the feedback type
     * @param stop 	Boolean value
     */
    public static void setStopUpload(int type, boolean stop) {
        setBoolean("STOPUPLOAD"+type, stop);
    }

    /**
     * Gets the settings for stop upload switch
     *
     * @return true if enabled
     */
    public static boolean isStopUpload(int type) {
        return getAsBoolean("STOPUPLOAD"+type, false);
    }

    /**
     * Set the last HRF date
     *
     * @param timestamp		the timestamp to set
     */
    public static void setLastHrfDate (Timestamp timestamp) {
        setTimestamp("LASTHRFDATE", timestamp);
    }

    /**
     * Gets the HRF timestamp for the last rebuild list
     *
     * @return	the HRF timestamp of the last rebuild list
     * 			if never rebuilt, use the current HRF date to avoid immediate upload
     * 			(i.e. the upload will start after the next HRF download) 
     */
    public static Timestamp getLastHrfDate () {
        return getAsTimestamp("LASTHRFDATE", Feedback.getMiniModel().getBasics().getDatum());
    }

    /**
     * Set the Plugin Status to started
     */
    public static void setStarted() {
        setBoolean("STARTED", true);
    }

    /**
     * Gets the plugin status
     *
     * @return true if already started
     */
    public static boolean isStarted() {
        return getAsBoolean("STARTED", false);
    }

    /**
     * Set the value in the databse
     *
     * @param key The key
     * @param value the value
     */
    public static void setString(String key, String value) {
//        String val = (value) ? "1" : "0";
        String query = "update " + TABLE_NAME + " set VALUE = " + value + " where NAME = '" + key
                       + "'";
        int count = Commons.getModel().getAdapter().executeUpdate(query);

        if (count == 0) {
            Commons.getModel().getAdapter().executeUpdate("insert into " + TABLE_NAME
                                                          + " (NAME, VALUE) values ('" + key
                                                          + "', " + value + ")");
        }
    }

    /**
     * Returns a value
     *
     * @param key the key to be returned
     * @param defaultValue to be used if not existing
     *
     * @return the value
     */
    public static String getAsString(String key, String defaultValue) {
//    	System.out.println ("getAsString, key="+key+", defVal="+defaultValue);
        String query = "select VALUE from " + TABLE_NAME + " where NAME='" + key + "'";
        ResultSet rs = Commons.getModel().getAdapter().executeQuery(query);

        try {
            rs.next();
//        	System.out.println ("  getAsString, key="+key+", val="+rs.getString("VALUE"));
            return rs.getString("VALUE");
        } catch (SQLException e) {
//        	System.out.println ("  getAsString, key="+key+", exception!");
            return defaultValue;
        }
    }

    /**
     * Set the value in the databse
     *
     * @param key The key
     * @param value the value
     */
    public static void setBoolean(String key, boolean value) {
    	setString (key, (value?"TRUE":"FALSE"));
    }

    /**
     * Returns a value
     *
     * @param key the key to be returned
     * @param defaultValue to be used if not existing
     *
     * @return the value
     */
    public static boolean getAsBoolean(String key, boolean defaultValue) {
    	try {
        	String tmpBool = getAsString (key, (defaultValue?"TRUE":"FALSE"));
        	return Boolean.valueOf(tmpBool).booleanValue();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return defaultValue;
    }

    /**
     * Set the value in the databse
     *
     * @param key The key
     * @param value the value
     */
    public static void setTimestamp(String key, Timestamp value) {
    	setLong (key, value.getTime());
    }

    /**
     * Returns a value
     *
     * @param key the key to be returned
     * @param defaultValue to be used if not existing
     *
     * @return the value
     */
    public static Timestamp getAsTimestamp(String key, Timestamp defaultValue) {
    	try {
    		return new Timestamp (getAsLong (key, defaultValue.getTime()));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return defaultValue;
    }

    /**
     * Set the value in the databse
     *
     * @param key The key
     * @param value the value
     */
    public static void setLong(String key, long value) {
    	setString (key, ""+value);
    }

    /**
     * Returns a value
     *
     * @param key the key to be returned
     * @param defaultValue to be used if not existing
     *
     * @return the value
     */
    public static long getAsLong(String key, long defaultValue) {
//    	System.out.println ("getAsLong, key="+key+", defVal="+defaultValue);
    	try {
        	String tmpTS = getAsString (key, ""+defaultValue);
//        	System.out.println ("getAsLong, key="+key+", defVal="+defaultValue+", val="+tmpTS);
        	if (tmpTS.equals("") || tmpTS.equals("null"))
        		return defaultValue;
        	return Long.parseLong(tmpTS);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return defaultValue;
    }

    /**
     * Check if the table exists, if not create it  with default values
     */
    public static void checkTable() {
        try {
            final ResultSet rs = Commons.getModel().getAdapter().executeQuery("select * from "
                                                                              + TABLE_NAME);
            rs.next();
        } catch (Exception e) {
            Commons.getModel().getAdapter().executeUpdate("CREATE TABLE " + TABLE_NAME
                                                          + " (NAME VARCHAR(25),VALUE VARCHAR(25))");
            Commons.getModel().getAdapter().executeUpdate("CREATE INDEX IDX_NAME ON "
                                                          + TABLE_NAME + " (NAME)");
        }
    }
}
