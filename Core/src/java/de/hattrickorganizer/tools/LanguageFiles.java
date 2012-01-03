// %35010405:de.hattrickorganizer.tools%
/*
 * LanguageFiles.java
 *
 * Created on 20. September 2005, 15:15
 */
package de.hattrickorganizer.tools;

import java.util.Vector;

/**
 * DOCUMENT ME!
 *
 * @author Marco Senn
 */
public class LanguageFiles {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of LanguageFiles
     */
    public LanguageFiles() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns a list of possible language files
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String[] getSprachDateien() {
        String[] files = null;
        final Vector<String> sprachdateien = new Vector<String>();

        try {
            //java.net.URL resource = new gui.vorlagen.ImagePanel().getClass().getClassLoader().getResource( "sprache" );
            final java.io.File sprachverzeichnis = new java.io.File("sprache");

            final java.io.File[] moeglicheSprachdateien = sprachverzeichnis.listFiles(new SprachFileFilter());

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

                if (sprachfileversion >= de.hattrickorganizer.gui.HOMainFrame.SPRACHVERSION) {
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
                files[i] = (String) sprachdateien.get(i);
            }
        } catch (Exception e) {
            HOLogger.instance().log(LanguageFiles.class,"Fehler Sprachdatei " + e);
            HOLogger.instance().log(LanguageFiles.class,e);
        }

        return files;
    }
}
