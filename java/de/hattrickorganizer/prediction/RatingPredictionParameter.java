package de.hattrickorganizer.prediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import plugins.IRatingPredictionParameter;
import de.hattrickorganizer.tools.HOLogger;

public class RatingPredictionParameter implements IRatingPredictionParameter {
    private Hashtable<String, Properties> allProps = new Hashtable<String, Properties>();
    private long lastParse;
    private String filename; 
    
    public RatingPredictionParameter () {
    }
    
    public void readFromFile (String newFilename) {
		File file = new File(newFilename);
		/*
		 * If filename changed or the file was modified -> (re)-parse the parameter file 
		 */
		if (!newFilename.equals(filename) || lastParse < file.lastModified()) {
			try {
				lastParse = file.lastModified();
				filename = newFilename;
				allProps.clear();
				HOLogger.instance().debug(this.getClass(), "(Re-)initializing prediction parameters: "+newFilename);
				BufferedReader br = new BufferedReader(new FileReader(newFilename));
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
			} catch (FileNotFoundException e) {
				HOLogger.instance().error(RatingPredictionConfig.class, "File not found: "+newFilename);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
    }
    
    public boolean hasSection (String section) {
    	return (allProps.containsKey(section));
    }
    
    public Hashtable<String, Properties> getAllSections () {
    	Hashtable<String, Properties> sections = new Hashtable<String, Properties>();
    	Enumeration<String> allKeys = allProps.keys();
    	while (allKeys.hasMoreElements()) {
    		String curName = (String)allKeys.nextElement();
    		if (!curName.equals(GENERAL)) {
    			Properties curSection = (Properties)allProps.get(curName);
    			sections.put(curName, curSection);
    		}
    	}
    	return sections;
    }

    public double getParam (String key) {
    	return (getParam (GENERAL, key));
    }

    public double getParam (String key, double defVal) {
    	return (getParam (GENERAL, key, 0));
    }

    public double getParam (String section, String key) {
    	return (getParam (section, key, 0));
    }
    
    public double getParam (String section, String key, double defVal) {
    	key = key.toLowerCase();
    	section = section.toLowerCase();
    	if (allProps.containsKey(section)) {
    		Properties props = (Properties)allProps.get(section);
    		String propString = props.getProperty(key, "" + defVal);
    		if (propString != "") {
    			return Double.parseDouble(propString);
    		}
    	}
//		System.out.println ("Warning: Key "+key+" not found in section "+section);
   		return 0;
    }
    
    /**
     * Get the date of the last file parse
     * @return
     */
    public long getLastParse () {
    	return lastParse;
    }
}
