// %1206903388:de.hattrickorganizer.model%
/*
 * HOVerwaltung.java
 *
 * Created on 22. März 2003, 16:31
 */
package ho.core.model;

import ho.HO;
import ho.core.datatype.CBItem;
import ho.core.db.DBManager;
import ho.core.file.ExampleFileFilter;
import ho.core.gui.HOMainFrame;
import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.net.login.LoginWaitDialog;
import ho.core.training.TrainingManager;
import ho.core.util.HOLogger;
import ho.module.lineup.Lineup;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

/**
 * DOCUMENT ME!
 * 
 * @author tom
 */
public class HOVerwaltung {
	// ~ Static fields/initializers
	// -----------------------------------------------------------------

	/** singelton */
	protected static HOVerwaltung m_clInstance;

	// ~ Instance fields
	// ----------------------------------------------------------------------------

	/** das Model */
	protected HOModel m_clHoModel;

	/** Resource */
	protected Properties m_clResource;

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Creates a new HOVerwaltung object.
	 */
	private HOVerwaltung() {
	}

	// -----------------Hilfsmethoden---------------------------------------------

	/**
	 * Set the HOModel.
	 */
	public void setModel(HOModel model) {
		m_clHoModel = model;
	}

	public HOModel getModel() {
		return m_clHoModel;
	}

	/**
	 * Get the HOVerwaltung singleton instance.
	 */
	public static HOVerwaltung instance() {
		if (m_clInstance == null) {
			m_clInstance = new HOVerwaltung();

			// TODO : defaults für FaktorObjekte einladen
			DBManager.instance().getFaktorenFromDB();

			// Krücke bisher
			// berechnung.FormulaFactors.instance ().init ();
		}

		return m_clInstance;
	}

	public void setResource(String pfad) {
		// Die Properies-Endung entfernen!
		// pfad = pfad.substring ( 0, pfad.indexOf ( ".properties" ) );

		/*
		 * //Protokoll entfernen pfad = pfad.replaceAll ( "file:", "" ); //! Aus
		 * Pfad entfernen pfad = pfad.replaceAll ( "!", "" );
		 * 
		 * 
		 * java.io.File sprachdatei = new java.io.File( pfad );
		 * HOLogger.instance().log(getClass(), "Sprachpfad " + pfad );
		 * 
		 * if ( ! sprachdatei.exists () ) { pfad = pfad.substring ( pfad.indexOf
		 * ( "sprache" ), pfad.length () ); sprachdatei = new java.io.File( pfad
		 * ); HOLogger.instance().log(getClass(), "2. Sprachpfad " + pfad ); }
		 * 
		 * 
		 * if ( sprachdatei.exists () ) {
		 */
		m_clResource = new java.util.Properties();
		final ClassLoader loader = this.getClass().getClassLoader();
		try {
			m_clResource.load(loader.getResourceAsStream("sprache/" + pfad + ".properties"));
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), e);
		}

		// m_clResource = java.util.ResourceBundle.getBundle ( pfad,
		// java.util.Locale.getDefault (), loader );
	}

	public Properties getResource() {
		return m_clResource;
	}

	/**
	 * ersetzt das aktuelle model durch das aus der DB mit der angegebenen ID
	 */
	public void loadHoModel(int id) {
		m_clHoModel = loadModel(id);
	}

	/**
	 * läadt das zuletzt importtiert model ein
	 */
	public void loadLatestHoModel() {
		int id = DBManager.instance().getLatestHrfId();
		m_clHoModel = loadModel(id);
	}

	/**
	 * Recalculate subskills since a certain HRF date. If the HRF date is null,
	 * the whole training history is recalculated.
	 */
	public void recalcSubskills(boolean showWait, Timestamp hrfDate) {
		HOLogger.instance().log(getClass(), "Start full subskill calculation. " + new Date());
		long start = System.currentTimeMillis();
		if (hrfDate == null) {
			hrfDate = new Timestamp(0);
		}

		LoginWaitDialog waitDialog = null;

		if (showWait) {
			waitDialog = new LoginWaitDialog(HOMainFrame.instance(), false);
			waitDialog.setVisible(true);
		}

		// Make sure the training week list is up to date.
		TrainingManager.instance().refreshTrainingWeeks();
		
		final Vector<CBItem> hrfListe = new Vector<CBItem>();
		hrfListe.addAll(DBManager.instance().getCBItemHRFListe(hrfDate));
		Collections.reverse(hrfListe);
		long s1, s2, lSum = 0, mSum = 0;
		HOLogger.instance().log(getClass(), "Subskill calculation prepared. " + new Date());
		for (int i = 0; i < hrfListe.size(); i++) {
			try {
				if (showWait && waitDialog != null) {
					waitDialog.setValue((int) ((i * 100d) / hrfListe.size()));
				}
				s1 = System.currentTimeMillis();
				final HOModel model = this.loadModel((hrfListe.get(i)).getId());
				lSum += (System.currentTimeMillis() - s1);
				s2 = System.currentTimeMillis();
				model.calcSubskills();
				mSum += (System.currentTimeMillis() - s2);
			} catch (Exception e) {
				HOLogger.instance().log(getClass(), "recalcSubskills : ");
				HOLogger.instance().log(getClass(), e);
			}
		}

		if (showWait && waitDialog != null) {
			waitDialog.setVisible(false);
		}

		// Erneut laden, da sich die Subskills geändert haben
		loadLatestHoModel();

		RefreshManager.instance().doReInit();
		HOLogger.instance().log(
				getClass(),
				"Subskill calculation done. " + new Date() + " - took "
						+ (System.currentTimeMillis() - start) + "ms ("
						+ (System.currentTimeMillis() - start) / 1000L + " sec), lSum=" + lSum
						+ ", mSum=" + mSum);
	}

	/**
	 * interne Func die ein Model aus der DB lädt
	 */
	protected HOModel loadModel(int id) {
		final HOModel model = new HOModel();
		model.setSpieler(DBManager.instance().getSpieler(id));
		model.setAllOldSpieler(DBManager.instance().getAllSpieler());
		model.setAufstellung(DBManager.instance().getAufstellung(id, Lineup.DEFAULT_NAME));
		model.setLastAufstellung(DBManager.instance().getAufstellung(id, Lineup.DEFAULT_NAMELAST));
		model.setBasics(DBManager.instance().getBasics(id));
		model.setFinanzen(DBManager.instance().getFinanzen(id));
		model.setLiga(DBManager.instance().getLiga(id));
		model.setStadium(DBManager.instance().getStadion(id));
		model.setTeam(DBManager.instance().getTeam(id));
		model.setVerein(DBManager.instance().getVerein(id));
		model.setID(id);
		model.setSpielplan(DBManager.instance().getSpielplan(-1, -1));
		model.setXtraDaten(DBManager.instance().getXtraDaten(id));

		return model;
	}

	/**
	 * Returns the String connected to the active language file or connected to
	 * the english language file. Returns !key! if the key can not be found.
	 * 
	 * @param key
	 *            Key to be searched in language files
	 * 
	 * @return String connected to the key or !key! if nothing can be found in
	 *         language files
	 */
	public String getLanguageString(String key) {
		String temp = getResource().getProperty(key);
		if (temp != null)
			return temp;
		// Search in english.properties if nothing found and active language not
		// english
		if (!ho.core.model.UserParameter.instance().sprachDatei.equalsIgnoreCase("english")) {
			Properties tempResource = new Properties();
			final ClassLoader loader = new ImagePanel().getClass().getClassLoader();
			try {
				tempResource.load(loader.getResourceAsStream("sprache/English.properties"));
			} catch (Exception e) {
				HOLogger.instance().log(getClass(), e);
			}
			temp = tempResource.getProperty(key);
			if (temp != null)
				return temp;
		}

		HOLogger.instance().warning(getClass(), "getLanguageString: '" + key + "' not found!");
		// HOLogger.instance().error(this.getClass(), new Exception());
		return "!" + key + "!";
	}

	/**
	 * Gets a parameterized message for the current language.
	 * 
	 * @param key
	 *            the key for the message in the language file.
	 * @param values
	 *            the values for the message
	 * @return the message for the specified key where the placeholders are
	 *         replaced by the given value(s).
	 */
	public String getLanguageString(String key, Object... values) {
		String str = getLanguageString(key);
		return MessageFormat.format(str, values);
	}

	public static String[] getLanguageFileNames() {
		String[] files = null;
		final Vector<String> sprachdateien = new Vector<String>();

		try {
			// java.net.URL resource = new
			// gui.vorlagen.ImagePanel().getClass().getClassLoader().getResource(
			// "sprache" );
			final java.io.File sprachverzeichnis = new java.io.File("sprache");

			final java.io.File[] moeglicheSprachdateien = sprachverzeichnis
					.listFiles(new ExampleFileFilter("properties"));

			for (int i = 0; (moeglicheSprachdateien != null) && (i < moeglicheSprachdateien.length); i++) {
				double sprachfileversion = 0;
				final java.util.Properties temp = new java.util.Properties();
				temp.load(new java.io.FileInputStream(moeglicheSprachdateien[i]));

				try {
					sprachfileversion = Double.parseDouble(temp.getProperty("Version"));
				} catch (Exception e) {
					HOLogger.instance().log(HOVerwaltung.class,
							"- " + moeglicheSprachdateien[i].getName());
				}

				if (sprachfileversion >= HO.SPRACHVERSION) {
					final String name = moeglicheSprachdateien[i].getName().substring(0,
							moeglicheSprachdateien[i].getName().indexOf('.'));
					sprachdateien.add(name);
					HOLogger.instance().log(HOVerwaltung.class,
							"+ " + moeglicheSprachdateien[i].getName());
				}
				// Nicht passende Version
				else {
					HOLogger.instance().log(HOVerwaltung.class,
							"- " + moeglicheSprachdateien[i].getName());
				}
			}

			// Umkopieren
			files = new String[sprachdateien.size()];

			for (int i = 0; i < sprachdateien.size(); i++) {
				files[i] = sprachdateien.get(i);
			}
		} catch (Exception e) {
			HOLogger.instance().log(HOVerwaltung.class, e);
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
			final java.io.File sprachdatei = new java.io.File("sprache/" + dateiname
					+ ".properties");

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
