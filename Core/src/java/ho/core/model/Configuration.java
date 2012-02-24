package ho.core.model;

import java.awt.Color;
import java.util.HashMap;

import plugins.IUserConfiguration;


public abstract class Configuration implements IUserConfiguration {

	public String getStringValue(HashMap<String, String> values,String key) {
		return String.valueOf(values.get(key)); 
	}
	
	public boolean getBooleanValue(HashMap<String, String> values,String key) {
		String value = String.valueOf(values.get(key));
		if (value.equalsIgnoreCase("true"))
			return true;
		return false;
	}

	public int getIntValue(HashMap<String, String> values,String key) {
		String value = String.valueOf(values.get(key));
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	public float getFloatValue(HashMap<String, String> values,String key) {
		String value = String.valueOf(values.get(key));
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException e) {
		}
		return 0f;
	}
	
	public Color getColorValue(HashMap<String, String> values, String key) {
		String value = String.valueOf(values.get(key));
		try {
			return new Color(Integer.parseInt(value));
		} catch (NumberFormatException e) {
		}
		return new Color(0);		
	}	

}
