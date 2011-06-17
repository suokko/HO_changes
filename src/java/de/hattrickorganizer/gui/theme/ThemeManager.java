/**
 * 
 */
package de.hattrickorganizer.gui.theme;

import java.awt.Color;
import java.io.File;
import java.math.BigDecimal;
import java.util.zip.ZipFile;

import javax.swing.UIManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.backup.HOZip;



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
		}
		
		// TODO only in development phase for testing; will be removed when finsihed
		try {
			File themeFile = new File(themesDir,defaultTheme.getName()+".theme");
			if(!themeFile.exists())
				saveTheme(defaultTheme);
			
			setCurrentTheme(defaultTheme.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		ThemeManager.instance();
	}
	
	public static Color getColor(String key){
		return instance().getInstanceColor(key);
	}
	
	private Color getInstanceColor(String key){
		Color tmp = null;
		if(currentTheme != null)
			tmp = currentTheme.getThemeColor(key);
		
		if(tmp == null)
			tmp = defaultTheme.getThemeColor(key);
		
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
			HOZip zip = new HOZip(themesDir+File.separator+theme.getName()+".theme");
			zip.createNewFile();
			JAXBContext jc = JAXBContext.newInstance(Theme.class);
			Marshaller m = jc.createMarshaller();
			File tmpFile = new File(Theme.fileName);
			m.marshal( theme.toThemeData(), tmpFile );
			zip.addFile(tmpFile);
			zip.closeArchive();
		}  catch (Exception e) {
			HOLogger.instance().log( ThemeManager.class, "Theme: " + theme.getName() + e);
		}
	}
	 
	public Theme loadTheme(String name) throws Exception {
		Theme theme = null;
		File themeFile = new File(themesDir,name+".theme");
		if(themeFile.exists()){
			ZipFile zipFile = new ZipFile(themeFile);
			JAXBContext jc = JAXBContext.newInstance(Theme.class);
			Unmarshaller u = jc.createUnmarshaller();
			ThemeData data = (ThemeData)u.unmarshal(zipFile.getInputStream(zipFile.getEntry(Theme.fileName)));
			theme = new Theme(data);
		}
		return theme;
	}
	
	
	public void setCurrentTheme(String name) throws Exception {
		currentTheme = loadTheme(name);
	}
	
	public String[] getAvailableThemeNames(){
		return themesDir.list();
	}

}
