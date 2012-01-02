package ho.core.plugins;

import java.util.Vector;

import plugins.IPlugin;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.SplashFrame;
import de.hattrickorganizer.tools.HOLogger;

public class PluginManager {
	private static Vector<IPlugin> m_vPlugins = new Vector<IPlugin>();
	
	// ///////////////////////////////////////////////////////////////////////////////////////////////77
	// helper
	// ///////////////////////////////////////////////////////////////////////////////////////////////77
	public static void startPluginModuls(SplashFrame interuptionWindow, int step) {
		try {
			// Den Ordner mit den Plugins holen
			final java.io.File folder = new java.io.File("hoplugins");
			HOLogger.instance().log(HOMainFrame.class,
					folder.getAbsolutePath() + " " + folder.exists() + " " + folder.isDirectory());

			// Filter, nur class-Datein in dem Ordner interessant
			final de.hattrickorganizer.gui.utils.ExampleFileFilter filter = new de.hattrickorganizer.gui.utils.ExampleFileFilter();
			filter.addExtension("class");
			filter.setDescription("Java Class File");
			filter.setIgnoreDirectories(true);

			// Alle class-Dateien in den Ordner holen
			final java.io.File[] files = folder.listFiles(filter);

			// Libs -> Alle Dateien durchlaufen
			for (int i = 0; (files != null) && (i < files.length); i++) {
				try {
					// Name der Klasse erstellen und Class-Object erstellen
					final String name = "hoplugins."
							+ files[i].getName().substring(0, files[i].getName().lastIndexOf('.'));
					final Class<?> fileclass = Class.forName(name);
					// Das Class-Object definiert kein Interface ...
					if (!fileclass.isInterface()) {
						// ... und ist von ILib abgeleitet
						if (plugins.ILib.class.isAssignableFrom(fileclass)) {
							// Object davon erstellen und starten
							final plugins.IPlugin modul = (plugins.IPlugin) fileclass.newInstance();

							// Plugin im Vector gespeichert
							m_vPlugins.add(modul);
							HOLogger.instance().log(HOMainFrame.class,
									" Starte " + files[i].getName() + "  (init MiniModel)");
							interuptionWindow.setInfoText(step,"Start Plugin: " + modul.getName());
							modul.start(de.hattrickorganizer.model.HOMiniModel.instance());

							HOLogger.instance().log(HOMainFrame.class,
									"+ " + files[i].getName() + " gestartet als lib");
						} else {
							HOLogger.instance().log(HOMainFrame.class,
									"- " + files[i].getName() + " nicht von ILib abgeleitet");
						}
					} else {
						HOLogger.instance().log(HOMainFrame.class,
								"- " + files[i].getName() + " ist Interface");
					}
				} catch (Throwable e2) {
					HOLogger.instance().log(HOMainFrame.class,
							"- " + files[i].getName() + " wird übersprungen: " + e2);
					// HOLogger.instance().log(HOMainFrame.class, e2);
				}
			}

			// Plugins -> Alle Dateien durchlaufen
			for (int i = 0; (files != null) && (i < files.length); i++) {
				try {
					// Name der Klasse erstellen und Class-Object erstellen
					final String name = "hoplugins."
							+ files[i].getName().substring(0, files[i].getName().lastIndexOf('.'));
					final Class<?> fileclass = Class.forName(name);
					// Das Class-Object definiert kein Interface ...
					if (!fileclass.isInterface()) {
						// ... und ist von IPlugin abgeleitet, nicht die Libs
						// nochmal starten!
						if (plugins.IPlugin.class.isAssignableFrom(fileclass)
								&& !plugins.ILib.class.isAssignableFrom(fileclass)) {
							// Object davon erstellen und starten
							final plugins.IPlugin modul = (plugins.IPlugin) fileclass.newInstance();

							// Plugin im Vector gespeichert
							m_vPlugins.add(modul);
							HOLogger.instance().log(HOMainFrame.class,
									" Starte " + files[i].getName() + "  (init MiniModel)");
							interuptionWindow.setInfoText(step,"Start Plugin: " + modul.getName());
							modul.start(de.hattrickorganizer.model.HOMiniModel.instance());

							HOLogger.instance().log(HOMainFrame.class,
									"+ " + files[i].getName() + " gestartet");
						} else {
							HOLogger.instance().log(HOMainFrame.class,
									"- " + files[i].getName() + " nicht von IPlugin abgeleitet");
						}
					} else {
						HOLogger.instance().log(HOMainFrame.class,
								"- " + files[i].getName() + " ist Interface");
					}
				} catch (Throwable e2) {
					HOLogger.instance().log(HOMainFrame.class,
							"- " + files[i].getName() + " wird übersprungen: " + e2);
					// HOLogger.instance().log(HOMainFrame.class, e2);
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(HOMainFrame.class, e);
		}
	}
	
	/**
	 * Returns the Vector with the started Plugins
	 */
	public static Vector<IPlugin> getPlugins() {
		return m_vPlugins;
	}
}
