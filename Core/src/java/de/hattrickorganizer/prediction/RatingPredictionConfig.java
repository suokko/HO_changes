package de.hattrickorganizer.prediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

import plugins.IRatingPredictionConfig;
import plugins.IRatingPredictionParameter;
import de.hattrickorganizer.tools.HOLogger;

public class RatingPredictionConfig
    implements IRatingPredictionConfig
{
	/* We check for changed rating parameter files regularily */
	private static long lastCheck = new Date().getTime();
	private static long checkInterval = 5000; // in millisecs

	private static long lastParse;
	
    private static RatingPredictionConfig config = null;
    
    private RatingPredictionParameter sideDefenseParam = new RatingPredictionParameter ();
    private RatingPredictionParameter centralDefenseParam = new RatingPredictionParameter ();
    private RatingPredictionParameter midfieldParam = new RatingPredictionParameter ();
    private RatingPredictionParameter sideAttackParam = new RatingPredictionParameter ();
    private RatingPredictionParameter centralAttackParam = new RatingPredictionParameter ();
    private RatingPredictionParameter playerStrengthParam = new RatingPredictionParameter ();
    private RatingPredictionParameter tacticsParam = new RatingPredictionParameter ();
    
    private String predictionName;
    private static String[] allPredictionNames = null;
    
    private static final String predDir = "prediction";
    private static final String predConfigFile = predDir + File.separatorChar + "predictionTypes.conf";
    

    private RatingPredictionConfig() {
    }

    public static IRatingPredictionConfig getInstance()
    {
    	if (config == null)
    		return getInstance (0);
    	else
    		return getInstance(getInstancePredictionType());
    }

    public static IRatingPredictionConfig getInstance (int type) {
    	if (getAllPredictionNames() != null) {
    		if (type < getAllPredictionNames().length) 
    			return getInstance (getAllPredictionNames()[type]);
    		else
    			return getInstance (getAllPredictionNames()[0]);
    	} else
    		return null;    		
    }
    
    public static IRatingPredictionConfig getInstance(String predictionName)
    {
    	if (config == null) {
        	config = new RatingPredictionConfig();
        }
    	long now = new Date().getTime();
    	if (!predictionName.equals(config.getPredictionName()) || now > lastCheck + checkInterval) {
        	config.initArrays(predictionName);
    		lastCheck = now;
        }
       	return config;
    }

    public static String[] getAllPredictionNames() {
    	if (allPredictionNames != null)
    		return allPredictionNames;
    	else {
    		ArrayList<String> list = new ArrayList<String>();
    		try {
    			BufferedReader br = new BufferedReader(new FileReader(predConfigFile));
    			while (br.ready()) {
    				String line = br.readLine();
    				// Remove Comments
    				line = line.replaceFirst("#.*", "");
    				// Trim
    				line = line.trim();
    				if (line.length() != 0) {
    					list.add(line);
    				}
    			}
    			HOLogger.instance().debug(RatingPredictionConfig.class, "Found predictionTypes: "+list);
    			allPredictionNames = new String[list.size()];
    			for (int i=0; i < allPredictionNames.length; i++) {
    				allPredictionNames[i] = list.get(i);
    			}
    		} catch (FileNotFoundException e) {
    			HOLogger.instance().error(RatingPredictionConfig.class, "File not found: "+predConfigFile);
    		} catch (Exception e) {
    			// TODO: handle exception
    			e.printStackTrace();
    		}
    		if (allPredictionNames == null) {
    			allPredictionNames = new String[1];
    			allPredictionNames[0] = "not available";
    		}
    		return allPredictionNames;
    	}
    }
    
    public static String getInstancePredictionName () {
    	if (config == null)
    		return null;
    	else
    		return config.getPredictionName();
    }
    
    public static int getInstancePredictionType () {
    	if (config == null)
    		return 0;
    	else 
    		return config.getPredictionType ();
    }
    
    public static void setInstancePredictionName (String name) {
    	getInstance(name);
    }
    
    public static void setInstancePredictionType (int type) {
    	getInstance(type);    	
    }

    public int getPredictionType () {
    	String[] allPredictionNames = getAllPredictionNames();
    	for (int i=0; i <allPredictionNames.length; i++)
    		if (allPredictionNames[i].equalsIgnoreCase(config.getPredictionName()))
    			return i;
    	return 0;
    }
    
    public String getPredictionName () {
    	return predictionName;
    }
    
    private void initArrays (String predictionName) {
    	this.predictionName = predictionName;
//		HOLogger.instance().debug(this.getClass(), "Checking for changed prediction files for type "+predictionName);
		String prefix = predDir + File.separatorChar + predictionName + File.separatorChar;
    	sideDefenseParam.readFromFile(prefix + "sidedefense.dat");
    	centralDefenseParam.readFromFile(prefix + "centraldefense.dat");
    	midfieldParam.readFromFile(prefix + "midfield.dat");
    	sideAttackParam.readFromFile(prefix + "sideattack.dat");
    	centralAttackParam.readFromFile(prefix + "centralattack.dat");
    	playerStrengthParam.readFromFile(prefix + "playerstrength.dat");
    	tacticsParam.readFromFile(prefix + "tactics.dat");
    	
    	// Check all params for re-parsed files
    	IRatingPredictionParameter allParams [] = 
    		{sideDefenseParam, centralDefenseParam, midfieldParam, 
    			sideAttackParam, centralAttackParam, playerStrengthParam, tacticsParam};
    	
    	for (IRatingPredictionParameter curParam : allParams) {
        	if (curParam.getLastParse() > lastParse)
        		lastParse = curParam.getLastParse();    		
    	}
    }

      public IRatingPredictionParameter getCentralAttackParameters()
    {
        return centralAttackParam;
    }

    public IRatingPredictionParameter getSideAttackParameters()
    {
        return sideAttackParam;
    }

    public IRatingPredictionParameter getCentralDefenseParameters()
    {
        return centralDefenseParam;
    }

    public IRatingPredictionParameter getSideDefenseParameters()
    {
        return sideDefenseParam;
    }

    public IRatingPredictionParameter getMidfieldParameters()
    {
        return midfieldParam;
    }
    
    public IRatingPredictionParameter getPlayerStrengthParameters()
    {
    	return playerStrengthParam;
    }
    
    public IRatingPredictionParameter getTacticsParameters()
    {
    	return tacticsParam;
    }
    
    /**
     * Get the date of the last file parse
     * @return
     */
    public long getLastParse () {
    	return lastParse;
    }
}
