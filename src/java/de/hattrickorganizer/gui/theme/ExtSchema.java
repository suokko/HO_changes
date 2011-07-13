package de.hattrickorganizer.gui.theme;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ImageIcon;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRegistry;

import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.backup.HOZip;

@XmlRegistry
public class ExtSchema extends Schema {

	private Hashtable<String, Object> contents = new Hashtable<String, Object>();
	private File themeFile;
	static String fileName = "data.xml";

	ExtSchema() {

	}

	public ExtSchema(File fileName, ThemeData data) {
		super(data);
		this.themeFile = fileName;
		init();
	}

	public byte[] getResource(String name) {
		return (byte[]) contents.get(name);
	}

	private void init() {
		try {
			FileInputStream fis = new FileInputStream(themeFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ZipInputStream zis = new ZipInputStream(bis);
			ZipEntry ze = null;
			while ((ze = zis.getNextEntry()) != null) {
				if (ze.isDirectory() || ze.getName().equals(fileName)) {
					continue;
				}
				int size = (int) ze.getSize();
				byte[] b = new byte[(int) size];
				int rb = 0;
				int chunk = 0;
				while (((int) size - rb) > 0) {
					chunk = zis.read(b, rb, (int) size - rb);
					if (chunk == -1) {
						break;
					}
					rb += chunk;
				}

				contents.put(ze.getName(), b);
			}
		} catch (Exception e) {
			HOLogger.instance().log(ExtSchema.class, e);
		}
	}

	@Override
	public ImageIcon loadImageIcon(String path) {
		ImageIcon image = null;

		image = (ImageIcon) cache.get(path);
		if (image == null) {
			try {

				Image logo = Toolkit.getDefaultToolkit().createImage(
						getResource(path));

				if (logo == null) {
					HOLogger.instance().log(Schema.class,
							path + " Not Found!!!");
					return ThemeManager.instance().classicSchema
							.loadImageIcon("gui/bilder/Unknownflag.png");
				}
				image = new ImageIcon(logo);
				cache.put(path, image);

				return image;
			} catch (Throwable e) {
				HOLogger.instance().log(Schema.class, e);
			}
		}
		return image;
	}
	
	public void save(File dir){
		try {
			HOZip zip = new HOZip(dir+File.separator+getName()+".zip");
			zip.createNewFile();
			JAXBContext jc = JAXBContext.newInstance(Schema.class);
			Marshaller m = jc.createMarshaller();
			File tmpFile = new File(fileName);
			m.marshal( toThemeData(), tmpFile );
			zip.addFile(tmpFile);
			zip.closeArchive();
		}  catch (Exception e) {
			HOLogger.instance().log( ThemeManager.class, "Schema: " + getName() + e);
		}
	}
}
