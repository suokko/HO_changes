/**
 * 
 */
package de.hattrickorganizer.gui.theme;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;

import javax.swing.UIManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.hattrickorganizer.tools.HOLogger;



/**
 * 
 * @date 2011-06-11
 */
public final class ThemeManager {
	private final static ThemeManager MANAGER = new ThemeManager();
	public static final BigDecimal VERSION = new BigDecimal("0.1");
	private File themesDir = new File("themes");
	
	private ClassicTheme defaultTheme = new ClassicTheme();
	private Theme currentTheme;
	
	private ThemeManager(){
		initialize();
	}

	public static ThemeManager instance(){
		return MANAGER;
	}
	
	private void initialize(){
		if(!themesDir.exists()){
			themesDir.mkdir();
			saveTheme(defaultTheme);
		}
		
		// TODO only in development phase for testing will be removed when finsihed
		try {
			setCurrentTheme(defaultTheme.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Color getColor(String key){
		Color tmp = null;
		if(currentTheme != null)
			tmp = currentTheme.getColor(key);
		
		if(tmp == null)
			tmp = defaultTheme.getColor(key);
		
		if(tmp == null)
			tmp =  UIManager.getColor(key);
		
		// when nothing matches return a defaultColor
		// if return null, maybe HO didn´t start
		if(tmp == null)
			tmp = defaultTheme.getDefaultColor(key);
		
		return tmp;
	}
	
	public Object get(String key){
		Object tmp = null;
		if(currentTheme != null)
			tmp = currentTheme.get(key);
		
		if(tmp == null)
			tmp = defaultTheme.get(key);
		
		if(tmp == null)
			tmp =  UIManager.get(key);
		
		return tmp;
	}
	
	public void saveTheme(Theme theme){
		try {
			File themeDir = new File(themesDir,theme.getName());
			if(!themeDir.exists())
				themeDir.mkdir();
			JAXBContext jc = JAXBContext.newInstance(Theme.class);
			Marshaller m = jc.createMarshaller();
			m.marshal( theme.toThemeData(), new File(themeDir,Theme.fileName) );
		}  catch (JAXBException e) {
			HOLogger.instance().log( ThemeManager.class, "Theme: " + theme.getName() + e);
		}
	}
	 
	public Theme loadTheme(String name) throws Exception {
		File themeDir = new File(themesDir,name);
		Theme theme = null;

			JAXBContext jc = JAXBContext.newInstance(Theme.class);
	        Unmarshaller u = jc.createUnmarshaller();
	        ThemeData data = (ThemeData)u.unmarshal(new FileInputStream(new File(themeDir,Theme.fileName)));
			theme = new Theme(data);
		return theme;
	}
	
	
	public void setCurrentTheme(String name) throws Exception {
		currentTheme = loadTheme(name);
	}
	
	public String[] getAvailableThemeNames(){
		return themesDir.list();
	}

}
