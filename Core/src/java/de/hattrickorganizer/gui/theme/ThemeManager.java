/**
 * 
 */
package de.hattrickorganizer.gui.theme;


import gui.HOIconName;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import de.hattrickorganizer.gui.theme.ho.HOClassicSchema;



public final class ThemeManager {
	private final static ThemeManager MANAGER = new ThemeManager();
	private File themesDir = new File("themes");
	
	HOClassicSchema 	classicSchema = new HOClassicSchema();
	private ExtSchema 	extSchema;

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
	}

	public static Color getColor(String key){
		Object obj = null;
		if(instance().extSchema != null){
			obj = instance().extSchema.getThemeColor(key);
			if(obj!= null && obj instanceof Color)
				return (Color)obj;
			if(obj != null && obj instanceof String)
				return getColor(obj.toString());
		}
		
		obj = instance().classicSchema.getThemeColor(key);
		if(obj!= null && obj instanceof Color)
			return (Color)obj;
		if(obj != null && obj instanceof String)
			return getColor(obj.toString());
		
		if(obj == null)
			obj = UIManager.getColor(key);
		
		if(obj == null)
			return instance().classicSchema.getDefaultColor(key);

		return (Color)obj;
	}

	public boolean isSet(String key){
		Boolean tmp = null;
		if(extSchema != null)
			tmp = (Boolean)extSchema.get(key);
		if(tmp == null)
			tmp = (Boolean)classicSchema.get(key);
		if(tmp == null)
			tmp = Boolean.FALSE;
		return tmp.booleanValue();
	}
	
	public void put(String key,Object value){
		classicSchema.put(key, value);
	}
	
	public Object get(String key){
		Object tmp = null;
		if(extSchema != null)
			tmp = extSchema.get(key);
		
		if(tmp == null)
			tmp = classicSchema.get(key);
		
		if(tmp == null)
			tmp =  UIManager.get(key);
		
		return tmp;
	}
	
	private ImageIcon getImageIcon(String key){
		Object tmp = null;
		if(extSchema != null){
			tmp = extSchema.get(key);
			if(tmp != null){
				return extSchema.loadImageIcon(tmp.toString());
			}
		}
		tmp = classicSchema.get(key);
		if(tmp == null)
			return null;
		if(tmp instanceof ImageIcon)
			return (ImageIcon)tmp;
		return classicSchema.loadImageIcon(tmp.toString());
	}
	
	private ImageIcon getScaledImageIcon(String key, int x, int y){
		ImageIcon tmp = null;
		if(extSchema != null){
			tmp = (ImageIcon)extSchema.get(key+"("+x+","+y+")");
			if(tmp == null){
				tmp = getImageIcon(key);
				
				if(tmp != null){
					tmp = new ImageIcon(tmp.getImage().getScaledInstance(x, y,Image.SCALE_SMOOTH));
					extSchema.put(key+"("+x+","+y+")",tmp);
				}
			}
			
		} else {
			tmp = (ImageIcon)classicSchema.get(key+"("+x+","+y+")");
			if(tmp == null){
				tmp = getImageIcon(key);
				
				if(tmp != null){
					tmp = new ImageIcon(tmp.getImage().getScaledInstance(x, y,Image.SCALE_SMOOTH));
					classicSchema.put(key+"("+x+","+y+")",tmp);
				}
			}
		}
		
		return tmp;
	}
	
	public static ImageIcon getIcon(String key){
		return instance().getImageIcon(key);
	}
	
	public static ImageIcon getScaledIcon(String key,int x,int y){
		return instance().getScaledImageIcon(key,x,y);
	}
	
	public static ImageIcon getTransparentIcon(String key,Color color){
		return instance().getTransparentImageIcon(key, color);
	}
	
	private ImageIcon getTransparentImageIcon(String key,Color color){
		ImageIcon tmp = null;
		if(extSchema != null){
			tmp = (ImageIcon)extSchema.get(key+"(T)");
			if(tmp == null){
				tmp = getImageIcon(key);
				
				if(tmp != null){
					tmp = new ImageIcon(ImageUtilities.makeColorTransparent(tmp.getImage(),color));
					extSchema.put(key+"(T)",tmp);
				}
			}
		} else {
			tmp = (ImageIcon)classicSchema.get(key+"(T)");
			if(tmp == null){
				tmp = getImageIcon(key);
				
				if(tmp != null){
					tmp = new ImageIcon(ImageUtilities.makeColorTransparent(tmp.getImage(),color));
					classicSchema.put(key+"(T)",tmp);
				}
			}
		}
		return tmp;
	}

	public static Image loadImage(String datei) {
		return instance().classicSchema.loadImageIcon(datei).getImage();
	}
	
	public ExtSchema loadSchema(String name) throws Exception {
		ExtSchema theme = null;
		File themeFile = new File(themesDir,name+".zip");
		if(themeFile.exists()){
			ZipFile zipFile = new ZipFile(themeFile);
			JAXBContext jc = JAXBContext.newInstance(Schema.class);
			Unmarshaller u = jc.createUnmarshaller();
			ZipEntry dataEntry = zipFile.getEntry(ExtSchema.fileName);
			if(dataEntry == null)
				throw new Exception("data.xml is missing");
			ThemeData data = (ThemeData)u.unmarshal(zipFile.getInputStream(dataEntry));
			theme = new ExtSchema(themeFile,data);
		}
		return theme;
	}
	
	
	public void setCurrentTheme(String name) throws Exception {
		if(name != null && !name.equals(classicSchema.getName()))
			extSchema = loadSchema(name);
	}
	
	
	public String[] getAvailableThemeNames(){
		final String[] fileList = themesDir.list();
		final String[] schemaNames = new String[fileList.length+1];
		schemaNames[0] = classicSchema.getName();
		for (int i = 0; i < fileList.length; i++) {
			schemaNames[i+1] = fileList[i].split("[.]")[0];
		}
		return schemaNames;
	}
	
	public void createNewSchemaFile(){
		ExtSchema tmp = new ExtSchema();
		tmp.save(themesDir);
	}
}
