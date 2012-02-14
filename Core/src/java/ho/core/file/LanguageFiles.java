// %35010405:de.hattrickorganizer.tools%
/*
 * LanguageFiles.java
 *
 * Created on 20. September 2005, 15:15
 */
package ho.core.file;

import gui.UserParameter;

import java.util.Vector;

import de.hattrickorganizer.HO;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.utils.ExampleFileFilter;
import de.hattrickorganizer.tools.HOLogger;

public class LanguageFiles {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of LanguageFiles
     */
    public LanguageFiles() {
    }

   public static String[] getSprachDateien() {
        String[] files = null;
        final Vector<String> sprachdateien = new Vector<String>();

        try {
            //java.net.URL resource = new gui.vorlagen.ImagePanel().getClass().getClassLoader().getResource( "sprache" );
            final java.io.File sprachverzeichnis = new java.io.File("sprache");

            final java.io.File[] moeglicheSprachdateien = sprachverzeichnis.listFiles(new ExampleFileFilter("properties"));

            for (int i = 0;
                 (moeglicheSprachdateien != null) && (i < moeglicheSprachdateien.length); i++) {
                double sprachfileversion = 0;
                final java.util.Properties temp = new java.util.Properties();
                temp.load(new java.io.FileInputStream(moeglicheSprachdateien[i]));

                try {
                    sprachfileversion = Double.parseDouble(temp.getProperty("Version"));
                } catch (Exception e) {
                    HOLogger.instance().log(LanguageFiles.class,"- " + moeglicheSprachdateien[i].getName());
                }

                if (sprachfileversion >= HO.SPRACHVERSION) {
                    final String name = moeglicheSprachdateien[i].getName().substring(0,
                                                                                      moeglicheSprachdateien[i].getName()
                                                                                                               .indexOf('.'));
                    sprachdateien.add(name);
                    HOLogger.instance().log(LanguageFiles.class,"+ " + moeglicheSprachdateien[i].getName());
                }
                //Nicht passende Version
                else {
                    HOLogger.instance().log(LanguageFiles.class,"- " + moeglicheSprachdateien[i].getName());
                }
            }

            //Umkopieren
            files = new String[sprachdateien.size()];

            for (int i = 0; i < sprachdateien.size(); i++) {
                files[i] = sprachdateien.get(i);
            }
        } catch (Exception e) {
            HOLogger.instance().log(LanguageFiles.class,"Fehler Sprachdatei " + e);
            HOLogger.instance().log(LanguageFiles.class,e);
        }

        return files;
    }

	/**
	 * Checked die Sprachdatei oder Fragt nach einer passenden
	 */
	public static void checkLanguageFile(String dateiname) {
		try {
			// java.net.URL resource = new
			// gui.vorlagen.ImagePanel().getClass().getClassLoader().getResource(
			// "sprache/"+dateiname+".properties" );
			final java.io.File sprachdatei = new java.io.File("sprache/" + dateiname + ".properties");
	
			if (sprachdatei.exists()) {
				double sprachfileversion = 0;
				final java.util.Properties temp = new java.util.Properties();
				temp.load(new java.io.FileInputStream(sprachdatei));
	
				try {
					sprachfileversion = Double.parseDouble(temp.getProperty("Version"));
				} catch (Exception e) {
					HOLogger.instance().log(HOMainFrame.class, "not use " + sprachdatei.getName());
				}
	
				if (sprachfileversion >= HO.SPRACHVERSION) {
					HOLogger.instance().log(HOMainFrame.class, "use " + sprachdatei.getName());
	
					// ok!!
					return;
				}
				
				HOLogger.instance().log(HOMainFrame.class, "not use " + sprachdatei.getName());
				
			}
		} catch (Exception e) {
			HOLogger.instance().log(HOMainFrame.class, "not use " + e);
		}
	
		// Irgendein Fehler -> neue Datei aussuchen!
		// new gui.menue.optionen.InitOptionsDialog();
		UserParameter.instance().sprachDatei = "English";
	}
}
