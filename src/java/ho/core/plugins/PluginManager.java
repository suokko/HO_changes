package ho.core.plugins;

import ho.core.db.DBManager;
import ho.core.util.HOLogger;

import java.io.File;
import java.util.ArrayList;


public final class PluginManager {
	private static String HOPLUGINS = "hoplugins";
	//private static Vector<IPlugin> m_vPlugins = new Vector<IPlugin>();
	private static int[] deprecatedlist = {	1,  //MatchesOverview
											14, // TeamAnalyzer
											16, // ExperienceViewer
											23, // trainingExperience
											25, //Transfer-Plugin*/
											45, // PlayerCompare
											
		};
	public static String HOPLUGINS_DIRECTORY = System.getProperty("user.dir") + File.separator + HOPLUGINS;
	
	
//	public static void startPluginModuls(SplashFrame interuptionWindow, int step) {
//		try {
//			// Den Ordner mit den Plugins holen
//			final File folder = new File(HOPLUGINS);
//			HOLogger.instance().info(PluginManager.class,
//					folder.getAbsolutePath() + " " + folder.exists() + " " + folder.isDirectory());
//
//			// Filter, nur class-Datein in dem Ordner interessant
//			final ExampleFileFilter filter = new ExampleFileFilter("class");
//			filter.setIgnoreDirectories(true);
//
//			// Alle class-Dateien in den Ordner holen
//			final File[] files = folder.listFiles(filter);
//
//			// Libs -> Alle Dateien durchlaufen
//			for (int i = 0; (files != null) && (i < files.length); i++) {
//				try {
//					// Name der Klasse erstellen und Class-Object erstellen
//					final String name = HOPLUGINS + "."
//							+ files[i].getName().substring(0, files[i].getName().lastIndexOf('.'));
//					final Class<?> fileclass = Class.forName(name);
//					// Das Class-Object definiert kein Interface ...
//					if (!fileclass.isInterface()) {
//						// ... und ist von ILib abgeleitet
//						if (plugins.ILib.class.isAssignableFrom(fileclass)) {
//							// Object davon erstellen und starten
//							final plugins.IPlugin modul = (plugins.IPlugin) fileclass.newInstance();
//
//							// Plugin im Vector gespeichert
//							m_vPlugins.add(modul);
//							HOLogger.instance().log(PluginManager.class,"start " + files[i].getName() );
//							interuptionWindow.setInfoText(step,"start library: " + modul.getName());
//							modul.start(HOMiniModel.instance());
//
//							HOLogger.instance().log(PluginManager.class,files[i].getName() + " started as lib");
//						} 
//					} else {
//						HOLogger.instance().log(PluginManager.class,files[i].getName() + " is interface");
//					}
//				} catch (Throwable e2) {
//					HOLogger.instance().log(PluginManager.class,files[i].getName() + " skipped: " + e2);
//				}
//			}
//
//			// Plugins -> Alle Dateien durchlaufen
//			for (int i = 0; (files != null) && (i < files.length); i++) {
//				boolean deprecated = false;
//				try {
//					// Name der Klasse erstellen und Class-Object erstellen
//					final String name = HOPLUGINS + "."+ files[i].getName().substring(0, files[i].getName().lastIndexOf('.'));
//					final Class<?> fileclass = Class.forName(name);
//					// Das Class-Object definiert kein Interface ...
//					if (!fileclass.isInterface()) {
//						// ... und ist von IPlugin abgeleitet, nicht die Libs
//						// nochmal starten!
//						if (plugins.IPlugin.class.isAssignableFrom(fileclass)
//								&& !plugins.ILib.class.isAssignableFrom(fileclass)) {
//							// Object davon erstellen und starten
//							final plugins.IPlugin modul = (plugins.IPlugin) fileclass.newInstance();
//							
//							if(modul instanceof IOfficialPlugin){
//								int pluginId = ((IOfficialPlugin)modul).getPluginID();
//								for (int j = 0; j < deprecatedlist.length; j++) {
//									if(pluginId ==deprecatedlist[j] ){
//										deprecated = true;
//										deletePlugin(modul, true);
//										HOLogger.instance().log(PluginManager.class,files[i].getName() + " deleted");
//									} 
//								}
//							}
//							if(! deprecated){
//								m_vPlugins.add(modul);
//								HOLogger.instance().log(PluginManager.class,"start " + files[i].getName());
//								interuptionWindow.setInfoText(step,"Start Plugin: " + modul.getName());
//								modul.start(HOMiniModel.instance());
//
//							HOLogger.instance().log(PluginManager.class,files[i].getName() + " started");
//							}
//						} else {
//							HOLogger.instance().log(PluginManager.class, files[i].getName() + " is not a plugin");
//						}
//					}
//				} catch (Throwable e2) {
//					HOLogger.instance().log(PluginManager.class,
//							"- " + files[i].getName() + " skipped: " );
//					HOLogger.instance().log(PluginManager.class,e2);
//				}
//			}
//		} catch (Exception e) {
//			HOLogger.instance().log(PluginManager.class, e);
//		}
//	}
//	

//    public static void deletePlugin(Object plugin, boolean withTables) {
//        File[] unquenchableFiles = new File[0];
//        String pluginName = plugin.getClass().getName();
//        pluginName = pluginName.substring(pluginName.indexOf(".") + 1);
//
//        // nur beim richtiges Löschen und nicht beim Update
//        if (withTables) {
//            deletePluginTables(pluginName);
//        }
//
//        File classFile = new File(HOPLUGINS_DIRECTORY + File.separator + pluginName + ".class");
//
//        if (classFile.exists()) {
//            classFile.delete();
//
//            if (plugin instanceof IOfficialPlugin) {
//                unquenchableFiles = ((IOfficialPlugin) plugin).getUnquenchableFiles();
//            }
//
//            clearDirectory(HOPLUGINS_DIRECTORY + File.separator + pluginName, unquenchableFiles);
//            classFile = new File(HOPLUGINS_DIRECTORY + File.separator + pluginName);
//
//            classFile.delete();
//        }
//    }

    private static void deletePluginTables(String pluginname) {
        try {
            ArrayList<String> droptables = new ArrayList<String>();
            Object [] tables = DBManager.instance().getAdapter().getDBInfo().getAllTablesNames();
    
            for (int i = 0; i < tables.length; i++) {
				if(tables[i].toString().toUpperCase(java.util.Locale.ENGLISH).startsWith(pluginname.toUpperCase(java.util.Locale.ENGLISH))) {
                    droptables.add(tables[i].toString());
                }
			}
    
            for (int i = 0; i < droptables.size(); i++) {
                DBManager.instance().getAdapter().executeUpdate("DROP TABLE " + droptables.get(i));
            }
        } catch (Exception e) {
           HOLogger.instance().log(PluginManager.class,e);
        }
    }
    
    private static  boolean isUnquenchable(File file, File[] files) {
        if (files == null) {
            return false;
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].exists() && file.getName().equals(files[i].getName())) {
                return true;
            }
        }

        return false;
    }

    private static void clearDirectory(String path, File[] unquenchablesFiles) {
        File dir = new File(path);

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    clearDirectory(files[i].getAbsolutePath(), unquenchablesFiles);
                }

                if (!isUnquenchable(files[i], unquenchablesFiles)) {
                    files[i].delete();
                }
            }
        }
    }
}
