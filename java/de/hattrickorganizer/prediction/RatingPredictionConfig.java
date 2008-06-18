package de.hattrickorganizer.prediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

import plugins.IRatingPredictionConfig;
import plugins.IRatingPredictionParameter;
import de.hattrickorganizer.tools.HOLogger;

// Referenced classes of package prediction:
//            HOEncrypter, RatingPredictionParameter, ZipHelper

public class RatingPredictionConfig
    implements IRatingPredictionConfig
{
	private static Date lastParse = new Date();
	private static double parseInterval = 5000; // in millisecs
	
    private static RatingPredictionConfig config = null;
    
    private RatingPredictionParameter sideDefenseParam;
    private RatingPredictionParameter centralDefenseParam;
    private RatingPredictionParameter midfieldParam;
    private RatingPredictionParameter sideAttackParam;
    private RatingPredictionParameter centralAttackParam;
    private RatingPredictionParameter playerStrengthParam;
    
    private String predictionName;
    private static String[] allPredictionNames = null;
    
    private static final String predDir = "prediction";
    private static final String predConfigFile = predDir + File.separatorChar + "predictionTypes.conf";
    

    private RatingPredictionConfig(String predictionName)
    {
    	this.predictionName = predictionName;
    	initArrays();
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
    	Date now = new Date();
        if(config == null || !config.getPredictionName().equalsIgnoreCase(predictionName) ||
        		now.getTime() > lastParse.getTime() + parseInterval) {
    		config = new RatingPredictionConfig(predictionName);
    		lastParse = now;
        }
       	return config;
    }

    public static String[] getAllPredictionNames() {
    	if (allPredictionNames != null)
    		return allPredictionNames;
    	else {
    		ArrayList list = new ArrayList();
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
    				allPredictionNames[i] = (String)list.get(i);
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
    
    private void initArrays() {
		HOLogger.instance().debug(this.getClass(), "(Re-)initializing prediction parameter arrays for type "+getPredictionName());
    	sideDefenseParam = parsePredictionProperties(predictionName 
    			+ File.separatorChar + "sidedefense.dat");
    	centralDefenseParam = parsePredictionProperties(predictionName 
    			+ File.separatorChar + "centraldefense.dat");
    	midfieldParam = parsePredictionProperties (predictionName 
    			+ File.separatorChar + "midfield.dat");
    	sideAttackParam = parsePredictionProperties (predictionName 
    			+ File.separatorChar + "sideattack.dat");
    	centralAttackParam = parsePredictionProperties(predictionName 
    			+ File.separatorChar + "centralattack.dat");
       	playerStrengthParam = parsePredictionProperties(predictionName 
    			+ File.separatorChar + "playerstrength.dat");
    }

    private static RatingPredictionParameter parsePredictionProperties (String filename)
    {
		String fullFilename = predDir + File.separatorChar + filename;
    	try {
    		Hashtable allProps = new Hashtable();
//    		System.out.println ("Using prediction file: "+fullFilename);
    		BufferedReader br = new BufferedReader(new FileReader(fullFilename));
            String line = null;
            Properties curProperties = null;
            while((line = br.readLine()) != null) {
            	line = line.toLowerCase();
            	// # begins a Comment
            	line = line.replaceFirst ("#.*", "");
            	// Trim
            	line = line.trim();
            	if (line.startsWith("[")) {
            		// new Section
            		String sectionName = line.replaceFirst ("^\\[(.*)\\].*", "$1");
            		if (allProps.containsKey(sectionName)) {
            			curProperties = (Properties)allProps.get(sectionName);
            		} else {
            			curProperties = new Properties();
            			allProps.put(sectionName, curProperties);
            		}
            	}
           		String temp[] = line.split("=");
           		if (temp.length == 2) {
           			String key = temp[0].trim();
           			String value = temp[1].trim();
//         			System.out.println ("Found new property: "+key+" -> "+value);
            		curProperties.setProperty(key, value);
            	}
            }
//            System.out.println ("All Props: "+allProps);
            return new RatingPredictionParameter(allProps);
		} catch (FileNotFoundException e) {
			HOLogger.instance().error(RatingPredictionConfig.class, "File not found: "+fullFilename);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        return new RatingPredictionParameter(new Hashtable());
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
    
    public IRatingPredictionParameter getPlayerStrenghParameters()
    {
    	return playerStrengthParam;
    }
}
