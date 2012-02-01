package ho.module;

import ho.core.db.DBManager;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;


public final class ModuleConfig  {
    
    

	
	
	private HashMap<String,Object> configMap = new HashMap<String,Object>();

	// singleton
	private static ModuleConfig configuration = null;
	
	private ModuleConfig(){

	}
	
	public static final ModuleConfig instance(){
		if(configuration == null){
			configuration = new ModuleConfig();
			configuration.load();
		}
		return configuration;
	}
	
	/**
	 * fill configuration values from database
	 * @return
	 */
	public void load(){
		configMap = DBManager.instance().loadModuleConfigs();
	}
	
	public void save(){
		DBManager.instance().saveModuleConfigs(configMap);
	}
	
	public void removeKey(String key) throws SQLException {
		DBManager.instance().deleteModuleConfigsKey(key);
		configMap.remove(key);
	}
	
	public Object getValue(String key){
		return configMap.get(key);
	}

	public Integer getInteger(String key){
		return (Integer)configMap.get(key);
	}
	
	public boolean getBoolean(String key){
		return ((Boolean)configMap.get(key)).booleanValue();
	}
	
	public String getString(String key){
		return (String)configMap.get(key);
	}
	
	public BigDecimal getBigDecimal(String key){
		return (BigDecimal)configMap.get(key);
	}
	
	public Timestamp getTimeStamp(String key){
		return (Timestamp)configMap.get(key);
	}
	
	public Date getDate(String key){
		return (Date)configMap.get(key);
	}
	
	public void setInteger(String key,Integer value){
		configMap.put(key,value);
	}
	
	public void setBoolean(String key,boolean value){
		configMap.put(key,Boolean.valueOf(value));
	}
	
	public void setString(String key,String value){
		configMap.put(key,value);
	}
	
	public void setBigDecimal(String key,BigDecimal value){
		configMap.put(key,value);
	}
	
	public void setTimeStamp(String key,Timestamp value){
		configMap.put(key,value);
	}

	public void setDate(String key,Date value){
		configMap.put(key,value);
	}
	public boolean containsKey(String key){
		return configMap.containsKey(key);
	}
	public final int[] getIntArray(String key){
		int[] values = new int[1];
		String value = getString(key);
		if(value==null || value.length()==0)
		    return null;
		
		if(value.contains(",")){
			String[] inttxt = value.split(",");
			values = new int[inttxt.length];
				for (int i = 0; i < values.length; i++) {
					values[i] = Integer.parseInt(inttxt[i]);
			}
		} else
			values[0] = Integer.parseInt(value);
		
		return values;
	}
	
	public void setIntArray(String key, int[] intArray){
		StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < intArray.length; i++) {
			tmp.append(intArray[i]);
			if(i < intArray.length-1)
				tmp.append(",");
		}
		configMap.put(key,tmp.toString());
	}
	
	

}
