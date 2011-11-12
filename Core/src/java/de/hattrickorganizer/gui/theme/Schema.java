package de.hattrickorganizer.gui.theme;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.xml.bind.annotation.XmlRegistry;



@XmlRegistry
public abstract class Schema  {

	/** cached all Strings, Boolean, Integer,  Color, Icons **/
	protected Hashtable<String, Object> cache = new Hashtable<String, Object>();

	
	public Schema(){
		
	}
	
	Schema(ThemeData data){
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
		final String value = xmlValue.getValue().trim();
		if (Pattern.matches("\\d{1,3}(,\\d{1,3}){1,3}", value)){
			String[] rgb = value.split(",");
			int r = Integer.parseInt(rgb[0].trim());
			int g = Integer.parseInt(rgb[1].trim());
			int b = Integer.parseInt(rgb[2].trim());
			if(rgb.length==3)
				return new Color(r,g,b);
			else if(rgb.length==4){
				int a = Integer.parseInt(rgb[3].trim());
				return new Color(r,g,b,a);
			}
		}else if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")){
			return Boolean.valueOf(value);
		}
		return value;
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
	public Object getThemeColor(String key){
		return cache.get(key);
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
		xmlValue.setValue(obj.toString());
		
		if(obj instanceof Color){
			Color c = (Color)obj;
			xmlValue.setValue(c.getRed()+","+c.getGreen()+","+c.getBlue()+(c.getAlpha()<255?","+c.getAlpha():""));
		}
		
		return xmlValue;	
	}
	
	public abstract ImageIcon loadImageIcon(String path);
	
}
