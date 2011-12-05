package plugins;

import java.util.HashMap;

/**
 * Interface for saving objects into UserConfigurationTable
 * @author Thorsten Dietz
 *
 */
public interface IUserConfiguration {
	
	/**
	 * Values for saving in db.
	 * 
	 * @return HashMap
	 */
	public HashMap<String, String> getValues();
	
	/**
	 * load values to set properties in object
	 * @param values
	 */
	public void setValues(HashMap<String, String> values);
}
