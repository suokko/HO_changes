package de.hattrickorganizer.gui.theme;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;

import javax.xml.bind.annotation.XmlRegistry;



@XmlRegistry
public class Theme {

	/** cached all Objects Icon, Strings, Color **/
	private Hashtable<String, Object> cache = new Hashtable<String, Object>();
	
	static String fileName = "data.xml";
	
	
	Theme(){
		
	}
	
	Theme(ThemeData data){
		setThemeData(data);
	}
	

	/**
	 * Sets values from .xml-file
	 * @param data
	 */
	void setThemeData(ThemeData data){
		cache.clear();
		List<ThemeData.XmlValue> list = data.getXmlValue();
		for (ThemeData.XmlValue xmlValue : list) {
			cache.put(xmlValue.getKey(), createObjectFromXmlValue(xmlValue));
		}
	}
	
	private Object createObjectFromXmlValue(ThemeData.XmlValue xmlValue){
		final String type = xmlValue.getType();
		if (type.equals("Color")){
			String[] rgb = xmlValue.getValue().split(",");
			return new Color(Integer.parseInt(rgb[0]),Integer.parseInt(rgb[1]),Integer.parseInt(rgb[2]));
		}else if (type.equals("BigDecimal")){
			return new BigDecimal(xmlValue.getValue());
		}else if(type.equals("Timestamp")){
			return Timestamp.valueOf(xmlValue.getValue());
		}else if(type.equals("Boolean")){
			return Boolean.valueOf(xmlValue.getValue());
		}else if(type.equals("ImageIcon")){
				// TODO
		}
		return xmlValue.getValue();
	}
	
	public Object get(String key){
		return cache.get(key);
	}
	
	protected void put(String key, Object c){
		cache.put(key,c);
	}

	/**
	 * extra method because color value can be a name or a Color
	 */
	public Color getColor(String key){
		Object obj = cache.get(key);
		if(obj!= null && obj instanceof Color)
			return (Color)obj;
		if(obj != null && obj instanceof String)
			return getColor(obj.toString());
		return null;
	}
	
	public String getVersion() {
		return (String)cache.get("version");
	}

	public void setVersion(String version) {
		cache.put("version",version);
	}

	public String getName() {
		return (String)cache.get("name");
	}

	public void setName(String name) {
		cache.put("name",name);
	}

	
    /**
     * Create an instance of {@link ThemeData }
     * Necessary for marshal
     */
    public ThemeData createThemeData() {
        return new ThemeData();
    }

    /**
     * Create an instance of {@link ThemeData.XmlValue }
     * Necessary for marshal
     */
    public ThemeData.XmlValue createThemeDataXmlValue() {
        return new ThemeData.XmlValue();
    }
	
	/**
	 * fills a ThemeData Object
	 * for saving it into a xml file
	 * @return
	 */
	protected ThemeData toThemeData(){
		final ThemeData data = createThemeData();
		List<ThemeData.XmlValue> list = data.getXmlValue();
		
		for(String e : cache.keySet()){
			list.add(toXmlValue(e, cache.get(e)));
		}
		
		data.xmlValue = list;
		return data;
	}
	
	private ThemeData.XmlValue toXmlValue(String e,Object obj){
		ThemeData.XmlValue xmlValue = new ThemeData.XmlValue();
		xmlValue.setKey(e);
		xmlValue.setType(obj.getClass().getSimpleName());
		xmlValue.setValue(obj.toString());
		
		if(obj instanceof Color){
			Color c = (Color)obj;
			xmlValue.setValue(c.getRed()+","+c.getGreen()+","+c.getBlue());
		}
		
		return xmlValue;	
	}
	
}
