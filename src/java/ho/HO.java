package ho;

import ho.core.db.DBManager;
import ho.core.db.User;
import ho.core.db.backup.BackupHelper;
import ho.core.gui.HOMainFrame;
import ho.core.gui.SplashFrame;
import ho.core.gui.model.UserColumnController;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.net.MyConnector;
import ho.core.training.TrainingManager;
import ho.core.util.HOLogger;
import ho.core.util.IOUtilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 * Main HO starter class.
 * 
 * @author thomas.werth
 */
public class HO {

	/**
	 * Release Notes: ============== The first SVN commit AFTER a release should
	 * include an increased VERSION number with DEVELOPMENT set to true an
	 * updated VERSION number in conf/addToZip/version.txt new headers in
	 * conf/addToZip/release_notes.txt and conf/addToZip/changelog.txt The last
	 * SVN commit BEFORE a release should set DEVELOPMENT to false and set
	 * WARN_DATE to 12 (?) months after the release date
	 */

	/**
	 * HO Version
	 */
	public static final double VERSION = 1.432d;
	/**
	 * language version
	 */
	public static final int SPRACHVERSION = 2;
	private static int revision = 0;
	/**
	 * After that date, the user gets a nag screen if he starts his old HO
	 * version, set to empty string for no warning (DEVELOPMENT versions do not
	 * show the nag screen)
	 */
	private static final String WARN_DATE = "2012-06-30 00:00:00.0";
	/**
	 * Is this a development version? Note that a "development" version can a
	 * release ("Beta" or "DEV" version). The DEVELOPMENT flag is used by the
	 * ant build script. Keep around.
	 */
	private static final boolean DEVELOPMENT = false;
	/**
	 * A RELEASE is when a build artifact gets delivered to users. Note that
	 * even a DEVELOPMENT version can be a RELEASE ("Beta"). So when a version
	 * is build (no matter if DEVELOPMENT or not), this flag should be set to
	 * true. The main purpose for the flag is to disable code (unfinished new
	 * features, debug code) which should not be seen in a release.
	 */
	private static final boolean RELEASE = false;

	public static boolean isDevelopment() {
		return DEVELOPMENT;
	}

	public boolean isRelease() {
		return RELEASE;
	}

	/**
	 * Main method to start a HOMainFrame.
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		final long start = System.currentTimeMillis();

		if ((args != null) && (args.length > 0)) {
			String debugLvl = args[0].trim().toUpperCase();

			if (debugLvl.equals("INFO")) {
				HOLogger.instance().setLogLevel(HOLogger.INFORMATION);
			} else if (debugLvl.equals("DEBUG")) {
				HOLogger.instance().setLogLevel(HOLogger.DEBUG);
			} else if (debugLvl.equals("WARNING")) {
				HOLogger.instance().setLogLevel(HOLogger.WARNING);
			} else if (debugLvl.equals("ERROR")) {
				HOLogger.instance().setLogLevel(HOLogger.ERROR);
			}
		}

		// Usermanagement Login-Dialog
		try {
			if (!User.getCurrentUser().isSingleUser()) {
				JComboBox comboBox = new JComboBox(User.getAllUser().toArray());
				int choice = JOptionPane.showConfirmDialog(null, comboBox, "Login",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (choice == JOptionPane.OK_OPTION) {
					User.INDEX = comboBox.getSelectedIndex();
				} else {
					System.exit(0);
				}
			}
		} catch (Exception ex) {
			HOLogger.instance().log(HOMainFrame.class, ex);
		}

		// Spoofing test
		try {
			final BufferedReader buffy = new BufferedReader(new java.io.FileReader("ident.txt"));
			final Vector<String> ids = new Vector<String>();
			String tmp = "";

			while (buffy.ready()) {
				tmp = buffy.readLine();

				if (!tmp.startsWith("#") && !tmp.trim().equals("")) {
					ids.add(tmp);
				}
			}

			buffy.close();

			if (ids.size() > 0) {
				// Math.floor(Math.random()*10)
				MyConnector.m_sIDENTIFIER = ids.get((int) Math.floor(Math.random() * ids.size()))
						.toString();
			}
		} catch (Exception e) {
		}

		// Check if this HO version is (soft) expired
		if (!DEVELOPMENT && WARN_DATE != null && WARN_DATE.length() > 0) {
			final Timestamp datum = new Timestamp(System.currentTimeMillis());

			if (datum.after(Timestamp.valueOf(WARN_DATE))) {
				JOptionPane.showMessageDialog(null,
						"Your HO version is very old!\nPlease download a new version at "
								+ MyConnector.getHOSite(), "Update strongly recommended",
						JOptionPane.WARNING_MESSAGE);
			}
		}

		// Startbild
		final SplashFrame interuptionsWindow = new SplashFrame();

		// Backup
		if (User.getCurrentUser().isHSQLDB()) {
			interuptionsWindow.setInfoText(1, "Backup Database");
			BackupHelper.backup(new File(User.getCurrentUser().getDBPath()));
		}

		// Standardparameter aus der DB holen
		interuptionsWindow.setInfoText(2, "Initialize Database");
		DBManager.instance().loadUserParameter();

		// init Theme
		try {
			ThemeManager.instance().setCurrentTheme(UserParameter.instance().theme);
		} catch (Exception e) {
			HOLogger.instance().log(HOMainFrame.class,
					"Can´t load Theme:" + UserParameter.instance().theme);
			JOptionPane.showMessageDialog(null, e.getMessage(), "Can´t load Theme: "
					+ UserParameter.instance().theme, JOptionPane.WARNING_MESSAGE);
		}
		// Init!
		interuptionsWindow.setInfoText(3, "Initialize Data-Administration");

		// Beim ersten Start Sprache erfragen
		if (DBManager.instance().isFirstStart()) {
			interuptionsWindow.setVisible(false);
			new ho.core.option.InitOptionsDialog();
			JOptionPane.showMessageDialog(null,
					"To load your team data into HO! select File > Download from the main menu.",
					"Team Data", JOptionPane.INFORMATION_MESSAGE);
			interuptionsWindow.setVisible(true);
		}

		// Check -> Sprachdatei in Ordnung?
		interuptionsWindow.setInfoText(4, "Check Languagefiles");
		HOVerwaltung.checkLanguageFile(UserParameter.instance().sprachDatei);

		HOVerwaltung.instance().setResource(UserParameter.instance().sprachDatei);
		interuptionsWindow.setInfoText(5, "Load latest Data");
		HOVerwaltung.instance().loadLatestHoModel();
		interuptionsWindow.setInfoText(6, "Load  XtraDaten");

		// TableColumn
		UserColumnController.instance().load();

		// Die Währung auf die aus dem HRF setzen
		float faktorgeld = (float) HOVerwaltung.instance().getModel().getXtraDaten()
				.getCurrencyRate();

		if (faktorgeld > -1) {
			UserParameter.instance().faktorGeld = faktorgeld;
		}

		// Training
		interuptionsWindow.setInfoText(7, "Initialize Training");

		// Training erstellen -> dabei Trainingswochen berechnen auf Grundlage
		// der manuellen DB Einträge
		TrainingManager.instance().calculateTraining(DBManager.instance().getTrainingsVector());

		// Anzeigen
		interuptionsWindow.setInfoText(8, "Prepare to show");
		HOMainFrame.instance().setVisible(true);

		// Startbild weg
		interuptionsWindow.setVisible(false);

		HOLogger.instance().log(HOMainFrame.class, "Zeit:" + (System.currentTimeMillis() - start));
	}

	public static int getRevisionNumber() {
		if (revision == 0) {
			InputStream is = null;
			BufferedReader br = null;
			try {
				is = HOMainFrame.class.getResourceAsStream("/revision.num");
				if (is != null) {
					br = new BufferedReader(new InputStreamReader(is));
					String line = null;
					// expect one line only
					if (br != null && (line = br.readLine()) != null) {
						revision = Integer.parseInt(line.trim());
					}
				} else {
					HOLogger.instance().debug(HO.class, "revision.num not found");
				}
			} catch (Exception e) {
				HOLogger.instance().warning(HO.class, "getRevisionNumber failed: " + e);
			} finally {
				IOUtilities.closeQuietly(is);
			}
		}
		if (revision == 0) { // to avoid multiple errors
			revision = 1;
		} else {
			HOLogger.instance().info(HO.class, "HO! revision " + HO.revision);
		}
		return revision;
	}
}
