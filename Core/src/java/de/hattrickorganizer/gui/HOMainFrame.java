// %3852537837:de.hattrickorganizer.gui%
package de.hattrickorganizer.gui;

import gui.HOIconName;
import gui.UserParameter;
import ho.modul.arenasizer.ArenaSizerDialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalLookAndFeel;

import plugins.IPlugin;
import plugins.ISpieler;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.dbcleanup.DBCleanupTool;
import de.hattrickorganizer.gui.exporter.CsvPlayerExport;
import de.hattrickorganizer.gui.exporter.XMLExporter;
import de.hattrickorganizer.gui.info.InformationsPanel;
import de.hattrickorganizer.gui.injury.InjuryDialog;
import de.hattrickorganizer.gui.keepertool.KeeperToolDialog;
import de.hattrickorganizer.gui.league.LigaTabellePanel;
import de.hattrickorganizer.gui.lineup.AufstellungsAssistentPanel;
import de.hattrickorganizer.gui.lineup.LineupMasterPanel;
import de.hattrickorganizer.gui.lineup.LineupPanel;
import de.hattrickorganizer.gui.lineup.substitution.SubstitutionOverview;
import de.hattrickorganizer.gui.matches.SpielePanel;
import de.hattrickorganizer.gui.menu.DownloadDialog;
import de.hattrickorganizer.gui.menu.HRFImport;
import de.hattrickorganizer.gui.menu.option.OptionenDialog;
import de.hattrickorganizer.gui.model.UserColumnController;
import de.hattrickorganizer.gui.notepad.NotepadDialog;
import de.hattrickorganizer.gui.playeranalysis.SpielerAnalyseMainPanel;
import de.hattrickorganizer.gui.playeroverview.SpielerUebersichtsPanel;
import de.hattrickorganizer.gui.statistic.StatistikMainPanel;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.gui.theme.ho.HOTheme;
import de.hattrickorganizer.gui.theme.jgoodies.JGoodiesTheme;
import de.hattrickorganizer.gui.theme.nimbus.NimbusTheme;
import de.hattrickorganizer.gui.transferscout.TransferScoutPanel;
import de.hattrickorganizer.gui.utils.FullScreen;
import de.hattrickorganizer.gui.utils.OnlineWorker;
import de.hattrickorganizer.logik.GebChecker;
import de.hattrickorganizer.logik.TrainingsManager;
import de.hattrickorganizer.model.FormulaFactors;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.User;
import de.hattrickorganizer.net.MyConnector;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.HelperWrapper;
import de.hattrickorganizer.tools.backup.BackupHelper;
import de.hattrickorganizer.tools.extension.ExtensionListener;
import de.hattrickorganizer.tools.extension.FileExtensionManager;
import de.hattrickorganizer.tools.updater.UpdateController;

/**
 * The Main HO window
 */
public final class HOMainFrame extends JFrame implements Refreshable, WindowListener, ActionListener,
		ChangeListener {
	// ~ Static fields/initializers
	// -----------------------------------------------------------------

	private static final long serialVersionUID = -6333275250973872365L;

	/**
	 * Release Notes: ============== The first SVN commit AFTER a release should
	 * include an increased VERSION number with DEVELOPMENT set to true an
	 * updated VERSION number in conf/addToZip/version.txt new headers in
	 * conf/addToZip/release_notes.txt and conf/addToZip/changelog.txt The last
	 * SVN commit BEFORE a release should set DEVELOPMENT to false and set
	 * WARN_DATE to 12 (?) months after the release date
	 */

	/** HO Version */
	public static final double VERSION = 1.432d;

	private static int revision = 0;

	/** Is this a development version? */
	private static final boolean DEVELOPMENT = true;

	/** enable functions for the developers */
	private static final boolean DEVELOPER_MODE = false;

	/**
	 * After that date, the user gets a nag screen if he starts his old HO
	 * version, set to empty string for no warning (DEVELOPMENT versions do not
	 * show the nag screen)
	 */
	private static final String WARN_DATE = "2012-06-30 00:00:00.0";

	public static final int SPRACHVERSION = 2; // language version
	private static HOMainFrame m_clHOMainFrame;
	private static final boolean LIMITED = false;
	private static final String LIMITED_DATE = "2004-09-01 00:00:00.0";
	private static Vector<IPlugin> m_vPlugins = new Vector<IPlugin>();

	// ---------Konstanten----------------------
	public static final int SPIELERUEBERSICHT = 0; // player overview
	public static final int AUFSTELLUNG = 1; // lineup
	public static final int LIGATABELLE = 2; // league table
	public static final int SPIELE = 3; // matches
	public static final int SPIELERANALYSE = 4; // player analysis
	public static final int STATISTIK = 5; // statistics
	public static final int TRANSFERSCOUT = 6;
	public static final int ARENASIZER = 7;
	public static final int INFORMATIONEN = 8;

	public static final int BUSY = 0;
	public static final int READY = 1;

	private static int status = READY;
	// ~ Instance fields
	// ----------------------------------------------------------------------------

	private LineupMasterPanel lineupMasterPanel;
	private InfoPanel m_jpInfoPanel;

	private static HOVerwaltung m_hov = HOVerwaltung.instance();
	private static UserParameter m_up = UserParameter.instance();
	private InformationsPanel m_jpInformation;
	private InjuryDialog injuryTool;
	private final JMenu m_jmAbout = new JMenu(m_hov.getLanguageString("About"));
	private final JMenu m_jmDatei = new JMenu(m_hov.getLanguageString("Datei"));
	private final JMenu m_jmPluginMenu = new JMenu(m_hov.getLanguageString("Plugins"));
	private final JMenu m_jmPluginsRefresh = new JMenu(m_hov.getLanguageString("Plugins"));
	private final JMenu m_jmToolsMenu = new JMenu(m_hov.getLanguageString("Tools"));
	private final JMenu m_jmUpdating = new JMenu(m_hov.getLanguageString("Refresh"));
	private final JMenu m_jmVerschiedenes = new JMenu(m_hov.getLanguageString("Funktionen"));

	// Menus
	private final JMenuBar m_jmMenuBar = new JMenuBar();
	private final JMenuItem m_jmBeendenItem = new JMenuItem(m_hov.getLanguageString("Beenden"));
	private final JMenuItem m_jmCreditsItem = new JMenuItem(m_hov.getLanguageString("Credits"));
	private final JMenuItem m_jmDownloadItem = new JMenuItem(m_hov.getLanguageString("Download"));
	private final JMenuItem m_jmForumItem = new JMenuItem(m_hov.getLanguageString("Forum"));
	private final JMenuItem m_jmHattrickItem = new JMenuItem(m_hov.getLanguageString("Hattrick"));
	private final JMenuItem m_jmHomepageItem = new JMenuItem(m_hov.getLanguageString("Homepage"));
	private final JMenuItem m_jmFullScreenItem = new JMenuItem(m_hov.getLanguageString("FullScreen.toggle"));

	private final JMenuItem m_jmImportItem = new JMenuItem(m_hov.getLanguageString("HRFImportieren"));
	private final JMenuItem m_jmOptionen = new JMenuItem(m_hov.getLanguageString("Optionen"));
	private final JMenuItem m_jmTraining = new JMenuItem(m_hov.getLanguageString("SubskillsBerechnen"));
	private final JMenuItem m_jmTraining2 = new JMenuItem(m_hov.getLanguageString("SubskillsBerechnen")
			+ " (7 " + m_hov.getLanguageString("Wochen") + ")");
//	private final JMenuItem m_jmiArena = new JMenuItem(m_hov.getLanguageString("ArenaSizer"));
	private final JMenuItem m_jmiAufstellung = new JMenuItem(m_hov.getLanguageString("Aufstellung"));
	private final JMenuItem m_jmiFlags = new JMenuItem(m_hov.getLanguageString("Flaggen"));
	private final JMenuItem m_jmiHO = new JMenuItem(m_hov.getLanguageString("HO"));
	private final JMenuItem m_jmiHObeta = new JMenuItem(m_hov.getLanguageString("HO") + " ("
			+ m_hov.getLanguageString("Beta") + ")");
	private final JMenuItem m_jmiEPV = new JMenuItem(m_hov.getLanguageString("EPV"));
	private final JMenuItem m_jmiRatings = new JMenuItem(m_hov.getLanguageString("Ratings"));

	private final JMenuItem m_jmiInjuryCalculator = new JMenuItem(m_hov.getLanguageString("InjuryCalculator"));
	private final JMenuItem m_jmiKeeperTool = new JMenuItem(m_hov.getLanguageString("KeeperTool"));
	private final JMenuItem m_jmiNotepad = new JMenuItem(m_hov.getLanguageString("Notizen"));
	private final JMenuItem m_jmiExporter = new JMenuItem(m_hov.getLanguageString("XMLExporter"));
	private final JMenuItem m_jmiCsvPlayerExporter = new JMenuItem(m_hov.getLanguageString("CSVExporter"));
	private final JMenuItem m_jmiDbCleanupTool = new JMenuItem(m_hov.getLanguageString("dbcleanup"));

	private final JMenuItem m_jmiLanguages = new JMenuItem(m_hov.getLanguageString("Sprachdatei"));
	private final JMenuItem m_jmiLigatabelle = new JMenuItem(m_hov.getLanguageString("Ligatabelle"));
	private final JMenuItem m_jmiPluginsDelete = new JMenuItem(m_hov.getLanguageString("loeschen"));
	private final JMenuItem m_jmiPluginsLibrary = new JMenuItem(m_hov.getLanguageString("Libraries"));
	private final JMenuItem m_jmiPluginsNormal = new JMenuItem(m_hov.getLanguageString("Normal"));
	private final JMenuItem m_jmiSpiele = new JMenuItem(m_hov.getLanguageString("Spiele"));
	private final JMenuItem m_jmiSpieleranalyse = new JMenuItem(m_hov.getLanguageString("SpielerAnalyse"));
	private final JMenuItem m_jmiSpieleruebersicht = new JMenuItem(
			m_hov.getLanguageString("Spieleruebersicht"));
	private final JMenuItem m_jmiStatistik = new JMenuItem(m_hov.getLanguageString("Statistik"));
	private final JMenuItem m_jmiTransferscout = new JMenuItem(m_hov.getLanguageString("TransferScout"));
	private final JMenuItem m_jmiVerschiedenes = new JMenuItem(m_hov.getLanguageString("Verschiedenes"));
	private final JMenuItem m_jmiArenaSizer = new JMenuItem(m_hov.getLanguageString("ArenaSizer"));

	// Components
	private JTabbedPane m_jtpTabbedPane;
	private KeeperToolDialog keeperTool;

	private LigaTabellePanel m_jpLigaTabelle;

	private OnlineWorker m_clOnlineWorker = new OnlineWorker();
	private SpielePanel m_jpSpielePanel;
	private SpielerAnalyseMainPanel m_jpSpielerAnalysePanel;
	private SpielerUebersichtsPanel m_jpSpielerUebersicht;
	private StatistikMainPanel m_jpStatistikPanel;
	private String m_sToRemoveTabName;
	private TransferScoutPanel m_jpTransferScout;
	private Vector<String> m_vOptionPanelNames = new Vector<String>();
	private Vector<JPanel> m_vOptionPanels = new Vector<JPanel>();

	private boolean isAppTerminated = false; // /< set when HO should be
												// terminated

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Singleton
	 */
	private HOMainFrame() {

		// Log HO! version
		HOLogger.instance().info(getClass(), "This is HO! version " + getVersionString() + ", have fun!");

		// Log Operating System
		HOLogger.instance().info(
				getClass(),
				"Operating system found: " + System.getProperty("os.name") + " on "
						+ System.getProperty("os.arch") + " (" + System.getProperty("os.version") + ")");

		// Log Java version
		HOLogger.instance().info(
				getClass(),
				"Using java: " + System.getProperty("java.version") + " ("
						+ System.getProperty("java.vendor") + ")");

		RefreshManager.instance().registerRefreshable(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setDefaultFont(UserParameter.instance().schriftGroesse);

		setTitle("HO! - Hattrick Organizer " + getVersionString());
		this.setIconImage(ThemeManager.getIcon(HOIconName.LOGO16).getImage());

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		// Catch Apple-Q for MacOS
		if (isMac()) {
			addMacOSListener();
		}

		initProxy();
		initComponents();
		initMenue();

		RefreshManager.instance().doRefresh();
	}

	final public static boolean isMac() {
		return (System.getProperty("os.name").toLowerCase(java.util.Locale.ENGLISH).indexOf("mac") != -1);
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------

	/**
	 * This method creates a MacOS specific listener for the quit operation
	 * ("Command-Q")
	 * 
	 * We need to use reflections here, because the com.apple.eawt.* classes are
	 * Apple specific
	 * 
	 * @author flattermann <flattermannHO@gmail.com>
	 */
	private void addMacOSListener() {
		HOLogger.instance().debug(getClass(), "Mac OS detected. Activating specific listeners...");
		try {
			// Create the Application
			Class<?> applicationClass = Class.forName("com.apple.eawt.Application");
			Object appleApp = applicationClass.newInstance();

			// Create the ApplicationListener
			Class<?> applicationListenerClass = Class.forName("com.apple.eawt.ApplicationListener");
			Object appleListener = Proxy.newProxyInstance(getClass().getClassLoader(),
					new Class[] { applicationListenerClass }, new InvocationHandler() {
						public Object invoke(Object proxy, Method method, Object[] args) {
							if (method.getName().equals("handleQuit")) {
								HOLogger.instance()
										.debug(getClass(),
												"ApplicationListener.handleQuit() fired! Quitting MacOS Application!");
								beenden();
							}
							return null;
						}
					});

			// Register the ApplicationListener
			Method addApplicationListenerMethod = applicationClass.getDeclaredMethod(
					"addApplicationListener", new Class[] { applicationListenerClass });
			addApplicationListenerMethod.invoke(appleApp, new Object[] { appleListener });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getVersionString() {
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMinimumFractionDigits(3);
		String txt = nf.format(VERSION);

		if (isDevelopment()) {
			txt += " DEV";
			final int r = getRevisionNumber();
			if (r > 1) {
				txt += " (r" + getRevisionNumber() + ")";
			}
		}
		return txt;
	}

	public static boolean isDevelopment() {
		return DEVELOPMENT;
	}

	public static boolean isDeveloperMode() {
		return DEVELOPER_MODE;
	}
	
	/**
	 * Getter for the singleton HOMainFrame instance.
	 */
	public static HOMainFrame instance() {
		if (m_clHOMainFrame == null) {
			m_clHOMainFrame = new HOMainFrame();
		}

		return m_clHOMainFrame;
	}

	public void setActualSpieler(int playerID) {
		getAufstellungsPanel().setPlayer(playerID);
		getSpielerUebersichtPanel().setPlayer(playerID);
		getSpielerUebersichtPanel().newSelectionInform();
	}


	public LineupPanel getAufstellungsPanel() {
		return this.lineupMasterPanel.getLineupPanel();
	}

	public InfoPanel getInfoPanel() {
		return m_jpInfoPanel;
	}

	public InformationsPanel getInformationsPanel() {
		return m_jpInformation;
	}

	/**
	 * Returns the Vector with the started Plugins
	 */
	public static Vector<IPlugin> getPlugins() {
		return m_vPlugins;
	}

	public SpielerAnalyseMainPanel getSpielerAnalyseMainPanel() {
		return m_jpSpielerAnalysePanel;
	}

	public SpielerUebersichtsPanel getSpielerUebersichtPanel() {
		return m_jpSpielerUebersicht;
	}

	/**
	 * Get the main statistics panel.
	 */
	public StatistikMainPanel getStatistikMainPanel() {
		return m_jpStatistikPanel;
	}

	public JTabbedPane getTabbedPane() {
		return m_jtpTabbedPane;
	}

	/**
	 * Get the current weather.
	 */
	public static int getWetter() {
		if (m_clHOMainFrame == null) {
			return ISpieler.LEICHTBEWOELKT;
		}
		return HOMainFrame.instance().getAufstellungsPanel().getAufstellungsAssitentPanel().getWetter();

	}

	/**
	 * Get the online worker object.
	 */
	public OnlineWorker getOnlineWorker() {
		return m_clOnlineWorker;
	}

	/**
	 * Get the transfer scout panel.
	 */
	public TransferScoutPanel getTransferScoutPanel() {
		return m_jpTransferScout;
	}

	/**
	 * Handle action events.
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		HOMainFrame.setHOStatus(HOMainFrame.BUSY);
		final Object source = actionEvent.getSource();

		if (source.equals(m_jmImportItem)) { // HRF Import
			new HRFImport(this);
		} else if (source.equals(m_jmDownloadItem)) { // HRF Download
			System.out.println("new DownloadDialog");
			new DownloadDialog();
		} else if (source.equals(m_jmOptionen)) { // Options
			new OptionenDialog(this).setVisible(true);
		} else if (source.equals(m_jmTraining)) { // recalc training
			if (JOptionPane.showConfirmDialog(this,
					"Depending on database volume this process takes several minutes. Start recalculation ?",
					"Subskill Recalculation", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				m_hov.recalcSubskills(true, null);
			}
		} else if (source.equals(m_jmTraining2)) { // recalc training (7 weeks)
			Calendar cal = Calendar.getInstance();
			cal.setLenient(true);
			cal.add(Calendar.WEEK_OF_YEAR, -7); // half season
			if (JOptionPane.showConfirmDialog(this,
					"Start recalculation subskill recalculation for the last 7 weeks (since "
							+ new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(cal.getTime())
							+ ")?", "Subskill Recalculation", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				Timestamp from = new Timestamp(cal.getTimeInMillis());
				m_hov.recalcSubskills(true, from);
			}
		} else if (source.equals(m_jmFullScreenItem)) { // Toggle full screen
														// mode
			FullScreen.instance().toggle(this);
		} else if (source.equals(m_jmBeendenItem)) { // Quit
			// Restore normal window mode (i.e. leave full screen)
			FullScreen.instance().restoreNormalMode(this);
			// Fire CloseEvent, so all Plugins get informed
			this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} else if (source.equals(m_jmiSpieleruebersicht)) { // Player overview
			showTab(HOMainFrame.SPIELERUEBERSICHT);
		} else if (source.equals(m_jmiAufstellung)) { // Lineup
			showTab(HOMainFrame.AUFSTELLUNG);
		} else if (source.equals(m_jmiLigatabelle)) { // League table
			showTab(HOMainFrame.LIGATABELLE);
		} else if (source.equals(m_jmiSpiele)) { // Matches
			showTab(HOMainFrame.SPIELE);
		} else if (source.equals(m_jmiSpieleranalyse)) { // Player Analysis
			showTab(HOMainFrame.SPIELERANALYSE);
		} else if (source.equals(m_jmiStatistik)) { // Statistics
			showTab(HOMainFrame.STATISTIK);
		} else if (source.equals(m_jmiTransferscout)) { // Transferscout
			showTab(HOMainFrame.TRANSFERSCOUT);
//		} else if (source.equals(m_jmiArena)) { // Arena
//			showTab(HOMainFrame.ARENASIZER);
		} else if (source.equals(m_jmiVerschiedenes)) { // Misc
			showTab(HOMainFrame.INFORMATIONEN);
		}else if(source.equals(m_jmiArenaSizer)){
			new ArenaSizerDialog(this).setVisible(true);
		} else if (source.equals(m_jmCreditsItem)) { 
			StringBuilder text = new StringBuilder(200);
			text.append("Hattrick Organizer ").append(VERSION).append("\n\n");
			text.append("2003 development started by Thomas Werth & Volker Fischer.\n");
			text.append("Since 2006 this project is open source and developed by changing developers.");
			JOptionPane.showMessageDialog(null, text.toString(), "Credits", JOptionPane.INFORMATION_MESSAGE);
			//new VAPCredits(this);
		} else if (source.equals(m_jmHomepageItem)) { // Homepage
			HelperWrapper.instance().openUrlInUserBRowser(MyConnector.getHOSite());
		} else if (source.equals(m_jmForumItem)) { // Forum
			HelperWrapper.instance().openUrlInUserBRowser("https://sourceforge.net/apps/phpbb/ho1/index.php");
		} else if (source.equals(m_jmHattrickItem)) { // Hattrick
			HelperWrapper.instance().openUrlInUserBRowser("http://www.hattrick.org");
		} else if (source.equals(m_jmiKeeperTool)) {
			keeperTool.reload();
			keeperTool.setVisible(true);
		} else if (source.equals(m_jmiNotepad)) {
			NotepadDialog notepad = new NotepadDialog(this, m_hov.getLanguageString("Notizen"));
			notepad.setVisible(true);
		} else if (source.equals(m_jmiExporter)) {
			XMLExporter exporter = new XMLExporter();
			exporter.doExport();
		} else if (source.equals(m_jmiCsvPlayerExporter)) {
			CsvPlayerExport csvExporter = new CsvPlayerExport();
			csvExporter.showSaveDialog();
		} else if (source.equals(m_jmiDbCleanupTool)) {
			DBCleanupTool dbCleanupTool = new DBCleanupTool();
			dbCleanupTool.showDialog(this);
		} else if (source.equals(m_jmiInjuryCalculator)) {
			injuryTool.reload();
			injuryTool.setVisible(true);
		} else if (source.equals(m_jmiLanguages)) {
			UpdateController.showLanguageUpdateDialog();
		} else if (source.equals(m_jmiFlags)) {
			UpdateController.updateFlags();
		} else if (source.equals(m_jmiHO)) {
			UpdateController.check4update();
		} else if (source.equals(m_jmiHObeta)) {
			UpdateController.check4latestbeta();
		} else if (source.equals(m_jmiEPV)) {
			UpdateController.check4EPVUpdate();
		} else if (source.equals(m_jmiRatings)) {
			UpdateController.check4RatingsUpdate();
		} else if (source.equals(m_jmiPluginsDelete)) {
			UpdateController.showDeletePluginDialog();
		} else if (source.equals(m_jmiPluginsNormal)) {
			UpdateController.showPluginUpdaterNormal();
		} else if (source.equals(m_jmiPluginsLibrary)) {
			UpdateController.showPluginUpdaterLibraries();
		}
		HOMainFrame.setHOStatus(HOMainFrame.READY);
	}

	/**
	 * Für Plugins zur Info
	 */
	public void addMainFrameListener(WindowListener listener) {
		addWindowListener(listener);
	}

	/**
	 * Add plugin menu.
	 */
	public void addMenu(JMenu menu) {
		m_jmPluginMenu.add(menu);
	}

	public void addTopLevelMenu(JMenu menu) {
		m_jmMenuBar.add(menu);
	}

	/**
	 * Beendet HO
	 */
	public void beenden() {
		HOLogger.instance().debug(getClass(), "Shutting down HO!");

		// Keine Sicherheitsabfrage mehr
		// int value = JOptionPane.showConfirmDialog( this,
		// model.m_hov.getLanguageString("BeendenMeldung"),
		// model.m_hov.getLanguageString("BeendenTitel"),
		// JOptionPane.YES_NO_OPTION);
		// int value = JOptionPane.OK_OPTION; //Doof aber schnell zu schreiben!
		// if ( value == JOptionPane.OK_OPTION )
		// aktuelle UserParameter speichern
		saveUserParameter();

		HOLogger.instance().debug(getClass(), "UserParameters saved");

		// Scoutliste speichern
		m_jpTransferScout.saveScoutListe();

		HOLogger.instance().debug(getClass(), "ScoutList saved");

		// Faktoren saven
		FormulaFactors.instance().save();

		HOLogger.instance().debug(getClass(), "FormulaFactors saved");

		// Disconnect
		DBZugriff.instance().disconnect();

		HOLogger.instance().debug(getClass(), "Disconnected");

		// //Ausloggen
		// try {
		// if ((UserParameter.instance().logoutOnExit)
		// && (MyConnector.instance().isAuthenticated())) {
		// MyConnector.instance().logout();
		// }
		// } catch (Exception e) {
		// }

		HOLogger.instance().debug(getClass(), "Shutdown complete!");
		// Dispose führt zu einem windowClosed, sobald alle windowClosing
		// (Plugins) durch sind
		isAppTerminated = true; // enable System.exit in windowClosed()
		try {
			dispose();
		} catch (Exception e) {
		}
	}

	/**
	 * Checked die Sprachdatei oder Fragt nach einer passenden
	 */
	public static void checkSprachFile(String dateiname) {
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

				if (sprachfileversion >= de.hattrickorganizer.gui.HOMainFrame.SPRACHVERSION) {
					HOLogger.instance().log(HOMainFrame.class, "use " + sprachdatei.getName());

					// Alles ok!!
					return;
				}
				// Nicht passende Version
				else {
					HOLogger.instance().log(HOMainFrame.class, "not use " + sprachdatei.getName());
				}
			}
		} catch (Exception e) {
			HOLogger.instance().log(HOMainFrame.class, "not use " + e);
		}

		// Irgendein Fehler -> neue Datei aussuchen!
		// new gui.menue.optionen.InitOptionsDialog();
		m_up.sprachDatei = "English";
	}

	/**
	 * Frame aufbauen
	 */
	public void initComponents() {
		javax.swing.ToolTipManager.sharedInstance().setDismissDelay(5000);

		setContentPane(new ImagePanel());
		getContentPane().setLayout(new BorderLayout());

		m_jtpTabbedPane = new JTabbedPane();

		// Hinzufügen der Tabs nur, wenn von der Userparameter gewünscht
		// Spieler
		m_jpSpielerUebersicht = new SpielerUebersichtsPanel();

		if (!m_up.tempTabSpieleruebersicht) {
			m_jtpTabbedPane.addTab(m_hov.getLanguageString("Spieleruebersicht"), m_jpSpielerUebersicht);
		}

		// Aufstellung
		this.lineupMasterPanel = new LineupMasterPanel();

		if (!m_up.tempTabAufstellung) {
			m_jtpTabbedPane.addTab(m_hov.getLanguageString("Aufstellung"), this.lineupMasterPanel);
		}

		// Tabelle
		m_jpLigaTabelle = new LigaTabellePanel();

		if (!m_up.tempTabLigatabelle) {
			m_jtpTabbedPane.addTab(m_hov.getLanguageString("Ligatabelle"), m_jpLigaTabelle);
		}

		// Spiele
		m_jpSpielePanel = new SpielePanel();

		if (!m_up.tempTabSpiele) {
			m_jtpTabbedPane.addTab(m_hov.getLanguageString("Spiele"), m_jpSpielePanel);
		}

		// SpielerAnalyse
		m_jpSpielerAnalysePanel = new SpielerAnalyseMainPanel();

		if (!m_up.tempTabSpieleranalyse) {
			m_jtpTabbedPane.addTab(m_hov.getLanguageString("SpielerAnalyse"), m_jpSpielerAnalysePanel);
		}

		// //Training
		// m_jtpTraining = new JTabbedPane();
		// //Trainingshilfe
		// m_jpTrainingshelfer = new TrainingsPanel();
		// m_jtpTraining.addTab ( model.m_hov.getLanguageString("Training"),
		// m_jpTrainingshelfer );
		// //SkillAenderung
		// m_jpSkillAenderungsPanel = new SkillAenderungsPanel();
		// m_jtpTraining.addTab ( model.m_hov.getLanguageString("Training") +
		// " 2", m_jpSkillAenderungsPanel );
		// //Adden
		// m_jtpTabbedPane.addTab ( model.m_hov.getLanguageString("Training"),
		// m_jtpTraining );
		//
		// Transferscout
		m_jpTransferScout = new TransferScoutPanel();

		if (!m_up.tempTabTransferscout) {
			m_jtpTabbedPane.addTab(m_hov.getLanguageString("TransferScout"), m_jpTransferScout);
		}

//		if (!m_up.tempTabArenasizer) {
//			m_jtpTabbedPane.addTab(m_hov.getLanguageString("ArenaSizer"), m_jpArenaSizer);
//		}

		// Sonstiges
		m_jpInformation = new InformationsPanel();

		if (!m_up.tempTabInformation) {
			m_jtpTabbedPane.addTab(m_hov.getLanguageString("Verschiedenes"), m_jpInformation);
		}

		// Statistiken
		m_jpStatistikPanel = new StatistikMainPanel();

		if (!m_up.tempTabStatistik) {
			m_jtpTabbedPane.addTab(m_hov.getLanguageString("Statistik"), m_jpStatistikPanel);
		}

		// Matchpaneltest
		/*
		 * logik.matchEngine.TeamData a = new
		 * logik.matchEngine.TeamData("Team1",new
		 * logik.matchEngine.TeamRatings(8, 18, 15, 13, 12, 8,
		 * 13),plugins.IMatchDetails.TAKTIK_KONTER,10);
		 * logik.matchEngine.TeamData b = new
		 * logik.matchEngine.TeamData("Team2",new
		 * logik.matchEngine.TeamRatings(12, 10, 12, 12, 10, 8,
		 * 10),plugins.IMatchDetails.TAKTIK_NORMAL,0);
		 * //gui.matchprediction.MatchEnginePanel enginepanel = new
		 * gui.matchprediction.MatchEnginePanel( a, b );
		 * 
		 * JDialog dialog = new JDialog( ); dialog.getContentPane().setLayout(
		 * new BorderLayout() ); dialog.getContentPane().add(
		 * model.HOMiniModel.instance ().getGUI().createMatchPredictionPanel( a,
		 * b ), BorderLayout.CENTER ); dialog.setSize( 800, 600 );
		 * dialog.setVisible( true );
		 */
		getContentPane().add(m_jtpTabbedPane, BorderLayout.CENTER);

		m_jtpTabbedPane.addChangeListener(this);

		m_jpInfoPanel = new InfoPanel();
		getContentPane().add(m_jpInfoPanel, BorderLayout.SOUTH);

		injuryTool = new InjuryDialog(this);
		keeperTool = new KeeperToolDialog(this);
		setLocation(UserParameter.instance().hoMainFrame_PositionX,
				UserParameter.instance().hoMainFrame_PositionY);
		setSize(UserParameter.instance().hoMainFrame_width, UserParameter.instance().hoMainFrame_height);
	}

	/**
	 * Initialize the menu.
	 */
	public void initMenue() {
		// Kein F10!
		((InputMap) UIManager.get("Table.ancestorInputMap"))
				.remove(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));

		// Falsch ( (InputMap)UIManager.get("Menu.ancestorInputMap") ).remove(
		// KeyStroke.getKeyStroke( KeyEvent.VK_F10, 0 ) );
		// m_jmMenuBar.getInputMap().remove( KeyStroke.getKeyStroke(
		// KeyEvent.VK_F10, 0 ) );
		// Datei
		// Download HRF
		m_jmDownloadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
		m_jmDownloadItem.addActionListener(this);
		m_jmDatei.add(m_jmDownloadItem);

		// Import HRF
		m_jmImportItem.addActionListener(this);
		m_jmDatei.add(m_jmImportItem);

		// Updating Menu
		m_jmiLanguages.addActionListener(this);
		m_jmiPluginsDelete.addActionListener(this);
		m_jmiFlags.addActionListener(this);
		if (isMac()) { // update doesn't work on MacOs' strange packet structure
			m_jmiHO.setEnabled(false);
			m_jmiHObeta.setEnabled(false);
		} else {
			m_jmiHO.addActionListener(this);
			m_jmiHObeta.addActionListener(this);
		}
		m_jmiEPV.addActionListener(this);
		m_jmiRatings.addActionListener(this);
		m_jmiPluginsNormal.addActionListener(this);
		m_jmiPluginsLibrary.addActionListener(this);

		m_jmPluginsRefresh.add(m_jmiPluginsNormal);
		m_jmPluginsRefresh.add(m_jmiPluginsLibrary);
		m_jmPluginsRefresh.add(m_jmiPluginsDelete);

		m_jmUpdating.add(m_jmPluginsRefresh);
		m_jmUpdating.add(m_jmiHO);
		m_jmUpdating.add(m_jmiHObeta);
		m_jmUpdating.add(m_jmiEPV);
		m_jmUpdating.add(m_jmiRatings);
		m_jmUpdating.add(m_jmiLanguages);
		m_jmUpdating.add(m_jmiFlags);

		m_jmDatei.add(m_jmUpdating);

		// Download Spielplan
		// m_jmFixturesItem.addActionListener ( this );
		// m_jmDatei.add ( m_jmFixturesItem );
		m_jmDatei.addSeparator();

		// Training
		m_jmTraining.addActionListener(this);
		m_jmDatei.add(m_jmTraining);
		m_jmTraining2.addActionListener(this);
		m_jmDatei.add(m_jmTraining2);

		m_jmDatei.addSeparator();

		// Optionen
		m_jmOptionen.addActionListener(this);
		m_jmDatei.add(m_jmOptionen);

		m_jmDatei.addSeparator();

		// Toggle full screen mode
		if (FullScreen.instance().isFullScreenSupported(this)) {
			m_jmFullScreenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11,
					KeyEvent.SHIFT_DOWN_MASK));
		} else {
			m_jmFullScreenItem.setEnabled(false);
		}
		m_jmFullScreenItem.addActionListener(this);
		m_jmDatei.add(m_jmFullScreenItem);

		m_jmDatei.addSeparator();

		// Beenden
		m_jmBeendenItem.addActionListener(this);
		m_jmDatei.add(m_jmBeendenItem);

		m_jmMenuBar.add(m_jmDatei);

		// ///
		// Verschiedenes
		// Spieleruebersicht
		m_jmiSpieleruebersicht.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		m_jmiSpieleruebersicht.addActionListener(this);
		m_jmVerschiedenes.add(m_jmiSpieleruebersicht);

		// Aufstellung
		m_jmiAufstellung.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		m_jmiAufstellung.addActionListener(this);
		m_jmVerschiedenes.add(m_jmiAufstellung);

		// Ligatabelle
		m_jmiLigatabelle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		m_jmiLigatabelle.addActionListener(this);
		m_jmVerschiedenes.add(m_jmiLigatabelle);

		// Spiele
		m_jmiSpiele.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		m_jmiSpiele.addActionListener(this);
		m_jmVerschiedenes.add(m_jmiSpiele);

		// Spieleranalyse
		m_jmiSpieleranalyse.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		m_jmiSpieleranalyse.addActionListener(this);
		m_jmVerschiedenes.add(m_jmiSpieleranalyse);

		// Statistik
		m_jmiStatistik.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
		m_jmiStatistik.addActionListener(this);
		m_jmVerschiedenes.add(m_jmiStatistik);

		// Transferscout
		m_jmiTransferscout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
		m_jmiTransferscout.addActionListener(this);
		m_jmVerschiedenes.add(m_jmiTransferscout);

		// ArenaSizer
//		m_jmiArena.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
//		m_jmiArena.addActionListener(this);
//		m_jmVerschiedenes.add(m_jmiArena);

		// Verschiedenes
		m_jmiVerschiedenes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
		m_jmiVerschiedenes.addActionListener(this);
		m_jmVerschiedenes.add(m_jmiVerschiedenes);

		m_jmMenuBar.add(m_jmVerschiedenes);

		if (isDeveloperMode()) {
			m_jmMenuBar.add(DeveloperMode.getDeveloperMenu());
		}

		// Tool Menu
		m_jmiKeeperTool.addActionListener(this);
		m_jmToolsMenu.add(m_jmiKeeperTool);

		m_jmiInjuryCalculator.addActionListener(this);
		m_jmToolsMenu.add(m_jmiInjuryCalculator);

		m_jmiExporter.addActionListener(this);
		m_jmToolsMenu.add(m_jmiExporter);

		m_jmiCsvPlayerExporter.addActionListener(this);
		m_jmToolsMenu.add(m_jmiCsvPlayerExporter);

		m_jmiNotepad.addActionListener(this);
		m_jmToolsMenu.add(m_jmiNotepad);

		m_jmiDbCleanupTool.addActionListener(this);
		m_jmToolsMenu.add(m_jmiDbCleanupTool);

		m_jmiArenaSizer.addActionListener(this);
		m_jmToolsMenu.add(m_jmiArenaSizer);
		
		m_jmMenuBar.add(m_jmToolsMenu);

		// Plugin Menu
		m_jmMenuBar.add(m_jmPluginMenu);

		// About
		m_jmHomepageItem.addActionListener(this);
		m_jmAbout.add(m_jmHomepageItem);

		m_jmForumItem.addActionListener(this);
		m_jmAbout.add(m_jmForumItem);

		m_jmHattrickItem.addActionListener(this);
		m_jmAbout.add(m_jmHattrickItem);

		m_jmAbout.addSeparator();

		m_jmCreditsItem.addActionListener(this);
		m_jmAbout.add(m_jmCreditsItem);

		Action substitutionTest = new AbstractAction("Substitution test") {

			public void actionPerformed(ActionEvent e) {
				JDialog dlg = new JDialog();
				dlg.getContentPane().add(new SubstitutionOverview());
				dlg.setSize(new Dimension(800, 600));
				dlg.setLocationRelativeTo(getContentPane());
				dlg.setVisible(true);
			}
		};
		// UNCOMMENT FOR Substitution test (Menu About->Substitution test)
//		m_jmAbout.add(substitutionTest);

		m_jmMenuBar.add(m_jmAbout);

		SwingUtilities.updateComponentTreeUI(m_jmMenuBar);

		// Adden
		this.setJMenuBar(m_jmMenuBar);
	}

	/**
	 * Proxyeinstellungen
	 */
	public void initProxy() {
		if (m_up.ProxyAktiv) {
			MyConnector.instance().setProxyHost(m_up.ProxyHost);
			MyConnector.instance().setUseProxy(m_up.ProxyAktiv);
			MyConnector.instance().setProxyPort(m_up.ProxyPort);
			MyConnector.instance().setProxyAuthentifactionNeeded(m_up.ProxyAuthAktiv);
			MyConnector.instance().setProxyUserName(m_up.ProxyAuthName);
			MyConnector.instance().setProxyUserPWD(m_up.ProxyAuthPassword);
			MyConnector.instance().enableProxy();
		}
	}

	/**
	 * Get all option panel names.
	 */
	public Vector<String> getOptionPanelNames() {
		return m_vOptionPanelNames;
	}

	/**
	 * Get all option panels.
	 */
	public Vector<JPanel> getOptionPanels() {
		return m_vOptionPanels;
	}

	/**
	 * OptionsPanels für Plugins
	 */
	public void addOptionPanel(String name, JPanel optionpanel) {
		m_vOptionPanels.add(optionpanel);
		m_vOptionPanelNames.add(name);
	}

	/**
	 * Reinit, set currency.
	 */
	public void reInit() {
		// Die Währung auf die aus dem HRF setzen
		try {
			float faktorgeld = (float) m_hov.getModel().getXtraDaten().getCurrencyRate();

			if (faktorgeld > -1) {
				m_up.faktorGeld = faktorgeld;
			}
		} catch (Exception e) {
			HOLogger.instance().log(HOMainFrame.class, "Währungsanpassung gescheitert!");
		}

		// Tabs prüfen
		checkTabs();
	}

	// ------Refreshfunktionen-------------------------------

	/**
	 * Wird bei einer Datenänderung aufgerufen
	 */
	public void refresh() {
		// nix?
	}

	/**
	 * Für Plugins zur Info
	 */
	public void removeMainFrameListener(WindowListener listener) {
		removeWindowListener(listener);
	}

	// --------------------------------------------------------------
	public void showMatch(int matchid) {
		showTab(de.hattrickorganizer.gui.HOMainFrame.SPIELE);

		m_jpSpielePanel.showMatch(matchid);
	}

	// ----------------Hilfsmethoden---------------------------------

	/**
	 * Zeigt das Tab an (Nicht Index, sondern Konstante benutzen!
	 * 
	 * @param tabnumber
	 *            number of the tab to show
	 */
	public void showTab(int tabnumber) {
		// Erstmal weg damit
		m_jtpTabbedPane.removeChangeListener(this);

		Component component;
		String titel;
		boolean temporaer = false;
		String removeTabName = null;

		switch (tabnumber) {
		case SPIELERUEBERSICHT:
			component = m_jpSpielerUebersicht;
			titel = m_hov.getLanguageString("Spieleruebersicht");
			temporaer = m_up.tempTabSpieleruebersicht;
			break;

		case AUFSTELLUNG:
			component = this.lineupMasterPanel.getLineupPanel();
			this.lineupMasterPanel.getLineupPanel().update(); // - blaghaid
			titel = m_hov.getLanguageString("Aufstellung");
			temporaer = m_up.tempTabAufstellung;
			break;

		case LIGATABELLE:
			component = m_jpLigaTabelle;
			titel = m_hov.getLanguageString("Ligatabelle");
			temporaer = m_up.tempTabLigatabelle;
			break;

		case SPIELE:
			component = m_jpSpielePanel;
			titel = m_hov.getLanguageString("Spiele");
			temporaer = m_up.tempTabSpiele;
			break;

		case SPIELERANALYSE:
			component = m_jpSpielerAnalysePanel;
			titel = m_hov.getLanguageString("SpielerAnalyse");
			temporaer = m_up.tempTabSpieleranalyse;
			break;

		case STATISTIK:
			component = m_jpStatistikPanel;
			titel = m_hov.getLanguageString("Statistik");
			temporaer = m_up.tempTabStatistik;
			break;

		case TRANSFERSCOUT:
			component = m_jpTransferScout;
			titel = m_hov.getLanguageString("TransferScout");
			temporaer = m_up.tempTabTransferscout;
			break;

//		case ARENASIZER:
//			component = m_jpArenaSizer;
//			titel = m_hov.getLanguageString("ArenaSizer");
//			temporaer = m_up.tempTabArenasizer;
//			break;

		case INFORMATIONEN:
			component = m_jpInformation;
			titel = m_hov.getLanguageString("Verschiedenes");
			temporaer = m_up.tempTabInformation;
			break;

		default:
			return;
		}

		// Wenn Temp, dann jetzt Hinzufügen
		int index = m_jtpTabbedPane.indexOfTab(titel);

		// Hinzufügen, aber später per ChangeListener löschen
		if (index < 0) {
			m_jtpTabbedPane.addTab(titel, component);
			index = m_jtpTabbedPane.indexOfTab(titel);

			// Name von Tab merken, um ihn nachher zu entfernen
			if (temporaer) {
				removeTabName = titel;
			}
		}

		// Und Listener wieder hinzu
		m_jtpTabbedPane.addChangeListener(this);

		// Tab markieren
		if (m_jtpTabbedPane.getTabCount() > index) {
			m_jtpTabbedPane.setSelectedIndex(index);
		}

		// Damit das setSelectedIndex nicht sofort das neue Tab killt erst hier
		// den Namen setzen, wenn temp
		m_sToRemoveTabName = removeTabName;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////77
	// helper
	// ///////////////////////////////////////////////////////////////////////////////////////////////77
	public void startPluginModuls(SplashFrame interuptionWindow) {
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
							interuptionWindow.setInfoText(8,"Start Plugin: " + modul.getName());
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
							interuptionWindow.setInfoText(8,"Start Plugin: " + modul.getName());
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
	 * React on state changed events.
	 */
	public void stateChanged(ChangeEvent changeEvent) {
		// Wenn ein Tab als Temp gespeichert wurde dieses entfernen
		if (m_sToRemoveTabName != null) {
			final int index = m_jtpTabbedPane.indexOfTab(m_sToRemoveTabName);

			if ((index >= 0) && (m_jtpTabbedPane.getTabCount() > index)) {
				// hier wegen rekursion
				m_sToRemoveTabName = null;
				m_jtpTabbedPane.removeTabAt(index);
			} else {
				HOLogger.instance().log(
						HOMainFrame.class,
						"Fehler Tabremove: " + m_sToRemoveTabName + " " + index + "/"
								+ m_jtpTabbedPane.getTabCount());
				m_sToRemoveTabName = null;
			}
		}
	}

	// ----------------Unused Listener----------------------------
	public void windowActivated(WindowEvent windowEvent) {
	}

	/**
	 * Finally shutting down the application when the main window is closed.
	 * This is initiated through the call to dispose(). System.exit is called
	 * only in the case when @see beenden() is called in advance. This event is
	 * called when switching into full screen mode, too.
	 * 
	 * @param windowEvent
	 *            is ignored
	 */
	public void windowClosed(WindowEvent windowEvent) {
		if (isAppTerminated) {
			System.exit(0);
		}
	}

	// ----------------Listener--------------------------------------

	/**
	 * Close HO window.
	 */
	public void windowClosing(WindowEvent windowEvent) {
		beenden();
	}

	public void windowDeactivated(WindowEvent windowEvent) {
	}

	public void windowDeiconified(WindowEvent windowEvent) {
	}

	public void windowIconified(WindowEvent windowEvent) {
	}

	public void windowOpened(WindowEvent windowEvent) {
	}

	/**
	 * Set the default font size.
	 */
	private void setDefaultFont(int size) {
		try {
			boolean succ = false;
			if (UserParameter.instance().skin != null && UserParameter.instance().skin.startsWith("JGoodies")) {
				succ = JGoodiesTheme.enableJGoodiesTheme(UserParameter.instance().skin, size);
			} else if ("System".equalsIgnoreCase(UserParameter.instance().skin)) {
				try {
					LookAndFeelInfo win = null;
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Windows".equals(info.getName())) {
							win = info;
							break;
						}
					}
					if (win != null) {
						HOLogger.instance().log(getClass(), "Use " + win.getName() + " l&f");
						UIManager.setLookAndFeel(win.getClassName());
					} else {
						HOLogger.instance().log(getClass(), "Use System l&f...");
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					}
					// TODO: font size
					SwingUtilities.updateComponentTreeUI(this);
					succ = true;
				} catch (Exception e) {
					succ = false;
				}
			} else if (!"Classic".equalsIgnoreCase(UserParameter.instance().skin)) { // Nimbus
																						// is
																						// the
																						// default
																						// theme
				succ = NimbusTheme.enableNimbusTheme(size);
			}
			if (!succ) {
				final MetalLookAndFeel laf = new MetalLookAndFeel();
				MetalLookAndFeel.setCurrentTheme(new HOTheme(UserParameter.instance().schriftGroesse));

				// Um die systemweite MenuBar von Mac OS X zu verwenden
				// http://www.pushing-pixels.org/?p=366
				if (System.getProperty("os.name").toLowerCase(java.util.Locale.ENGLISH).startsWith("mac")) {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Object mbUI = UIManager.get("MenuBarUI");
					Object mUI = UIManager.get("MenuUI");
					Object cbmiUI = UIManager.get("CheckBoxMenuItemUI");
					Object rbmiUI = UIManager.get("RadioButtonMenuItemUI");
					Object pmUI = UIManager.get("PopupMenuUI");

					UIManager.setLookAndFeel(laf);

					UIManager.put("MenuBarUI", mbUI);
					UIManager.put("MenuUI", mUI);
					UIManager.put("CheckBoxMenuItemUI", cbmiUI);
					UIManager.put("RadioButtonMenuItemUI", rbmiUI);
					UIManager.put("PopupMenuUI", pmUI);
				} else {
					UIManager.setLookAndFeel(laf);
				}
			}
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			HOLogger.instance().log(HOMainFrame.class, e);
		}
	}

	/**
	 * Remove all temporary tabs.
	 */
	private void checkTabs() {
		int index;

		m_jtpTabbedPane.removeChangeListener(this);

		if (m_up.tempTabSpieleruebersicht) {
			index = m_jtpTabbedPane.indexOfTab(m_hov.getLanguageString("Spieleruebersicht"));

			if ((index > 0) && (m_jtpTabbedPane.getTabCount() > index)) {
				m_jtpTabbedPane.removeTabAt(index);
			}
		}

		if (m_up.tempTabAufstellung) {
			index = m_jtpTabbedPane.indexOfTab(m_hov.getLanguageString("Aufstellung"));

			if ((index > 0) && (m_jtpTabbedPane.getTabCount() > index)) {
				m_jtpTabbedPane.removeTabAt(index);
			}
		}

		if (m_up.tempTabLigatabelle) {
			index = m_jtpTabbedPane.indexOfTab(m_hov.getLanguageString("Ligatabelle"));

			if ((index > 0) && (m_jtpTabbedPane.getTabCount() > index)) {
				m_jtpTabbedPane.removeTabAt(index);
			}
		}

		if (m_up.tempTabSpiele) {
			index = m_jtpTabbedPane.indexOfTab(m_hov.getLanguageString("Spiele"));

			if ((index > 0) && (m_jtpTabbedPane.getTabCount() > index)) {
				m_jtpTabbedPane.removeTabAt(index);
			}
		}

		if (m_up.tempTabSpieleranalyse) {
			index = m_jtpTabbedPane.indexOfTab(m_hov.getLanguageString("SpielerAnalyse"));

			if ((index > 0) && (m_jtpTabbedPane.getTabCount() > index)) {
				m_jtpTabbedPane.removeTabAt(index);
			}
		}

		if (m_up.tempTabStatistik) {
			index = m_jtpTabbedPane.indexOfTab(m_hov.getLanguageString("Statistik"));

			if ((index > 0) && (m_jtpTabbedPane.getTabCount() > index)) {
				m_jtpTabbedPane.removeTabAt(index);
			}
		}

		if (m_up.tempTabTransferscout) {
			index = m_jtpTabbedPane.indexOfTab(m_hov.getLanguageString("TransferScout"));

			if ((index > 0) && (m_jtpTabbedPane.getTabCount() > index)) {
				m_jtpTabbedPane.removeTabAt(index);
			}
		}

//		if (m_up.tempTabArenasizer) {
//			index = m_jtpTabbedPane.indexOfTab(m_hov.getLanguageString("ArenaSizer"));
//
//			if ((index > 0) && (m_jtpTabbedPane.getTabCount() > index)) {
//				m_jtpTabbedPane.removeTabAt(index);
//			}
//		}

		if (m_up.tempTabInformation) {
			index = m_jtpTabbedPane.indexOfTab(m_hov.getLanguageString("Verschiedenes"));

			if ((index > 0) && (m_jtpTabbedPane.getTabCount() > index)) {
				m_jtpTabbedPane.removeTabAt(index);
			}
		}

		m_jtpTabbedPane.addChangeListener(this);
	}

	/**
	 * Holt die Parameter aus den Dialogen und speichert sie in der DB
	 */
	private void saveUserParameter() {
		UserParameter parameter = UserParameter.instance();

		final int[] sup = m_jpSpielerUebersicht.getDividerLocations();
		final int[] ap = this.lineupMasterPanel.getLineupPanel().getDividerLocations();
		final int[] sp = m_jpSpielePanel.getDividerLocations();
		final int spa = m_jpSpielerAnalysePanel.getDividerLocation();
		final AufstellungsAssistentPanel aap = this.lineupMasterPanel.getLineupPanel().getAufstellungsAssitentPanel();
		final int tsp = m_jpTransferScout.getDividerLocation();

		final int locx = Math.max(getLocation().x, 0);
		final int locy = Math.max(getLocation().y, 0);
		parameter.hoMainFrame_PositionX = locx;
		parameter.hoMainFrame_PositionY = locy;
		parameter.hoMainFrame_width = Math.min(getSize().width, getToolkit().getScreenSize().width - locx);
		parameter.hoMainFrame_height = Math.min(getSize().height, getToolkit().getScreenSize().height - locy);
		parameter.bestPostWidth = Math.max(m_jpSpielerUebersicht.getBestPosWidth(),
				this.lineupMasterPanel.getLineupPanel().getBestPosWidth());

		parameter.aufstellungsAssistentPanel_gruppe = aap.getGruppe();
		parameter.aufstellungsAssistentPanel_reihenfolge = aap.getReihenfolge();
		parameter.aufstellungsAssistentPanel_not = aap.isNotGruppe();
		parameter.aufstellungsAssistentPanel_cbfilter = aap.isGruppenFilter();
		parameter.aufstellungsAssistentPanel_idealPosition = aap.isIdealPositionZuerst();
		parameter.aufstellungsAssistentPanel_form = aap.isFormBeruecksichtigen();
		parameter.aufstellungsAssistentPanel_verletzt = aap.isVerletztIgnorieren();
		parameter.aufstellungsAssistentPanel_gesperrt = aap.isGesperrtIgnorieren();
		parameter.aufstellungsAssistentPanel_notLast = aap.isExcludeLastMatch();

		// SpielerÜbersichtsPanel
		parameter.spielerUebersichtsPanel_horizontalLeftSplitPane = sup[0];
		parameter.spielerUebersichtsPanel_horizontalRightSplitPane = sup[1];
		parameter.spielerUebersichtsPanel_verticalSplitPane = sup[2];

		// AufstellungsPanel
		parameter.aufstellungsPanel_verticalSplitPaneLow = ap[0];
		parameter.aufstellungsPanel_horizontalLeftSplitPane = ap[1];
		parameter.aufstellungsPanel_horizontalRightSplitPane = ap[2];
		parameter.aufstellungsPanel_verticalSplitPane = ap[3];

		// SpielePanel
		parameter.spielePanel_horizontalLeftSplitPane = sp[0];
		parameter.spielePanel_verticalSplitPane = sp[1];

		// SpielerAnalyse
		parameter.spielerAnalysePanel_horizontalSplitPane = spa;

		// TransferScoutPanel
		parameter.transferScoutPanel_horizontalSplitPane = tsp;

		DBZugriff.instance().saveUserParameter();

		m_jpSpielerUebersicht.saveColumnOrder();
		m_jpSpielePanel.saveColumnOrder();
		this.lineupMasterPanel.getLineupPanel().saveColumnOrder();
		m_jpSpielerAnalysePanel.saveColumnOrder();

	}

	public static void main(String[] args) {
		final long start = System.currentTimeMillis();

		// Schnauze!
		// nur wenn Kein Debug
		if ((args != null) && (args.length > 0)) {
			String debugLvl = args[0].trim();

			if (debugLvl.equalsIgnoreCase("INFO")) {
				HOLogger.instance().setLogLevel(HOLogger.INFORMATION);
			} else if (debugLvl.equalsIgnoreCase("DEBUG")) {
				HOLogger.instance().setLogLevel(HOLogger.DEBUG);
			} else if (debugLvl.equalsIgnoreCase("WARNING")) {
				HOLogger.instance().setLogLevel(HOLogger.WARNING);
			} else if (debugLvl.equalsIgnoreCase("ERROR")) {
				HOLogger.instance().setLogLevel(HOLogger.ERROR);
			}
		}

		// Set HOE file
		// This creates a file called ho.dir in $home
		// Do we really need this? Removed by flattermann 2009-01-18
		// FileExtensionManager.createDirFile();

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

		// //Spoofing test
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
				MyConnector.m_sIDENTIFIER = ids.get((int) Math.floor(Math.random() * ids.size())).toString();
			}
		} catch (Exception e) {
		}

		// Check if this HO version is (soft) expired
		if (!DEVELOPMENT && WARN_DATE != null && WARN_DATE.length() > 0) {
			final Timestamp datum = new Timestamp(System.currentTimeMillis());

			if (datum.after(Timestamp.valueOf(WARN_DATE))) {
				JOptionPane.showMessageDialog(
						null,
						"Your HO version is very old!\nPlease download a new version at "
								+ MyConnector.getHOSite(), "Update strongly recommended",
						JOptionPane.WARNING_MESSAGE);
			}
		}

		// Check if this HO version is (hard) expired
		if (LIMITED) {
			final Timestamp datum = new Timestamp(System.currentTimeMillis());

			if (datum.after(Timestamp.valueOf(LIMITED_DATE))) {
				JOptionPane.showMessageDialog(null, "Download new Version at " + MyConnector.getHOSite(),
						"Update required", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}

		// Startbild
		final SplashFrame interuptionsWindow = new SplashFrame();

		// Backup
		if (User.getCurrentUser().isHSQLDB()) {
			interuptionsWindow.setInfoText(1,"Backup Database");
			BackupHelper.backup(new File(User.getCurrentUser().getDBPath()));
		}

		// Standardparameter aus der DB holen
		interuptionsWindow.setInfoText(2,"Initialize Database");
		DBZugriff.instance().loadUserParameter();

		// init Theme
		try {
			ThemeManager.instance().setCurrentTheme(UserParameter.instance().theme);
		} catch (Exception e) {
			HOLogger.instance().log(HOMainFrame.class, "Can´t load Theme:" + UserParameter.instance().theme);
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Can´t load Theme: " + UserParameter.instance().theme, JOptionPane.WARNING_MESSAGE);
		}
		// Init!
		interuptionsWindow.setInfoText(3,"Initialize Data-Administration");

		// Beim ersten Start Sprache erfragen
		if (DBZugriff.instance().isFirstStart()) {
			interuptionsWindow.setVisible(false);
			new de.hattrickorganizer.gui.menu.option.InitOptionsDialog();
			JOptionPane.showMessageDialog(null,
					"To load your team data into HO! select File > Download from the main menu.",
					"Team Data", JOptionPane.INFORMATION_MESSAGE);
			interuptionsWindow.setVisible(true);
		}

		// Check -> Sprachdatei in Ordnung?
		interuptionsWindow.setInfoText(4,"Check Languagefiles");
		checkSprachFile(UserParameter.instance().sprachDatei);

		// font switch, because the default font doesn't support Georgian and
		// Chinese characters
		// TODO
		final ClassLoader loader = new ImagePanel().getClass().getClassLoader();

		m_hov.setResource(UserParameter.instance().sprachDatei, loader);
		interuptionsWindow.setInfoText(5,"Load latest Data");
		m_hov.loadLatestHoModel();
		interuptionsWindow.setInfoText(6,"Load  XtraDaten");

		// TableColumn
		UserColumnController.instance().load();

		// Die Währung auf die aus dem HRF setzen
		float faktorgeld = (float) m_hov.getModel().getXtraDaten().getCurrencyRate();

		if (faktorgeld > -1) {
			m_up.faktorGeld = faktorgeld;
		}

		// Training
		interuptionsWindow.setInfoText(7,"Initialize Training");

		// Training erstellen -> dabei Trainingswochen berechnen auf Grundlage
		// der manuellen DB Einträge
		TrainingsManager.instance().calculateTrainings(DBZugriff.instance().getTrainingsVector());

		// INIT + Dann Pluginsstarten , sonst endlos loop da instance() sich
		// selbst aufruft!
		interuptionsWindow.setInfoText(8,"Starting Plugins");
		HOMainFrame.instance().startPluginModuls(interuptionsWindow);

		HOMainFrame.instance().getAufstellungsPanel().getAufstellungsPositionsPanel()
				.exportOldLineup("Actual");
		FileExtensionManager.extractLineup("Actual");
		// Anzeigen
		interuptionsWindow.setInfoText(9,"Prepare to show");
		HOMainFrame.instance().setVisible(true);

		// Startbild weg
		interuptionsWindow.setVisible(false);

		if (GebChecker.checkTWGeb()) {
			new de.hattrickorganizer.gui.birthday.GebDialog(HOMainFrame.instance(), "birthdayTom");
		}

		if (GebChecker.checkVFGeb()) {
			new de.hattrickorganizer.gui.birthday.GebDialog(HOMainFrame.instance(), "birthdayVolker");
		}

		new ExtensionListener().run();
		HOLogger.instance().log(HOMainFrame.class, "Zeit:" + (System.currentTimeMillis() - start));

	}

	public static int getHOStatus() {
		return status;
	}

	public static void setHOStatus(int i) {
		status = i;
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
					if (br != null && (line = br.readLine()) != null) { // expect
																		// one
																		// line
																		// only
						revision = Integer.parseInt(line.trim());
					}
				} else {
					HOLogger.instance().debug(HOMainFrame.class, "revision.num not found");
				}
			} catch (Exception e) {
				HOLogger.instance().warning(HOMainFrame.class, "getRevisionNumber failed: " + e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
					}
				}
			}
		}
		if (revision == 0) { // to avoid multiple errors
			revision = 1;
		} else {
			HOLogger.instance().info(HOMainFrame.class, "HO! revision " + revision);
		}
		return revision;
	}
}
