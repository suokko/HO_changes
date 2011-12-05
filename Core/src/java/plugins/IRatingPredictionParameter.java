package plugins;

import java.util.Hashtable;
import java.util.Properties;

public interface IRatingPredictionParameter 
{
    public static final int THISSIDE = 0;
    public static final int OTHERSIDE = 1;
    public static final int ALLSIDES = 2;
    public static final int MIDDLE = 3;
    public static final int LEFT = 4;
    public static final int RIGHT = 5;

    public static final String GENERAL = "general";

    public Hashtable<String, Properties> getAllSections ();
    public boolean hasSection (String section);

    public double getParam (String key);
    public double getParam (String key, double defVal);
    public double getParam (String section, String key);
    public double getParam (String section, String key, double defVal);

    /**
     * Get the date of the last file parse
     * @return
     */
    public long getLastParse ();
}
