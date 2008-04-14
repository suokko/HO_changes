package de.hattrickorganizer.prediction;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import plugins.IRatingPredictionParameter;

public class RatingPredictionParameter
    implements IRatingPredictionParameter
{
    private Hashtable allProps;
    
    public RatingPredictionParameter (Hashtable allProps) {
    	this.allProps = allProps;
    }
    
    public boolean hasSection (String section) {
    	return (allProps.containsKey(section));
    }
    
    public Hashtable getAllSections () {
    	Hashtable sections = new Hashtable();
    	Enumeration allKeys = allProps.keys();
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

}
