// %3852537837:de.hattrickorganizer.gui%
package ho.core.gui;

import ho.HO;
import ho.core.db.DBManager;
import ho.core.file.hrf.HRFImport;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.comp.tabbedPane.HOTabbedPane;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.gui.theme.ho.HOTheme;
import ho.core.gui.theme.jgoodies.JGoodiesTheme;
import ho.core.gui.theme.nimbus.NimbusTheme;
import ho.core.model.FormulaFactors;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.module.IModule;
import ho.core.module.ModuleManager;
import ho.core.net.DownloadDialog;
import ho.core.net.MyConnector;
import ho.core.net.OnlineWorker;
import ho.core.option.OptionenDialog;
import ho.core.util.HOLogger;
import ho.core.util.HelperWrapper;
import ho.module.lineup.AufstellungsAssistentPanel;
import ho.module.lineup.LineupPanel;
import ho.module.matches.SpielePanel;
import ho.module.playerOverview.SpielerUebersichtsPanel;
import ho.module.playeranalysis.PlayerAnalysisPanel;
import ho.module.transfer.TransfersPanel;
import ho.tool.ToolManager;
import ho.tool.updater.UpdateController;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
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

import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalLookAndFeel;

import plugins.ISpieler;

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

	private static HOMainFrame m_clHOMainFrame;


	public static final int BUSY = 0;
	public static final int READY = 1;

	private static int status = READY;

	// tabs
	
	private InfoPanel m_jpInfoPanel;

	private final JMenuBar m_jmMenuBar = new JMenuBar();
	// Top level Menu
	private final JMenu m_jmAbout = new JMenu(HOVerwaltung.instance().getLanguageString("About"));
	private final JMenu m_jmDatei = new JMenu(HOVerwaltung.instance().getLanguageString("Datei"));
	private final JMenu m_jmVerschiedenes = new JMenu(HOVerwaltung.instance().getLanguageString("Funktionen"));
	private final JMenu m_jmModuleMenu = new JMenu(HOVerwaltung.instance().getLanguageString("Module"));
	
	// Menus
	private final JMenu m_jmPluginsRefresh = new JMenu(HOVerwaltung.instance().getLanguageString("Plugins"));
	private final JMenu m_jmUpdating = new JMenu(HOVerwaltung.instance().getLanguageString("Refresh"));
	private final JMenuItem m_jmBeendenItem = new JMenuItem(HOVerwaltung.instance().getLanguageString("Beenden"));
	private final JMenuItem m_jmCreditsItem = new JMenuItem(HOVerwaltung.instance().getLanguageString("Credits"));
	private final JMenuItem m_jmDownloadItem = new JMenuItem(HOVerwaltung.instance().getLanguageString("Download"));
	private final JMenuItem m_jmForumItem = new JMenuItem(HOVerwaltung.instance().getLanguageString("Forum"));
	private final JMenuItem m_jmHattrickItem = new JMenuItem(HOVerwaltung.instance().getLanguageString("Hattrick"));
	private final JMenuItem m_jmWikiItem = new JMenuItem(HOVerwaltung.instance().getLanguageString("Help"));
	private final JMenuItem m_jmHomepageItem = new JMenuItem(HOVerwaltung.instance().getLanguageString("Homepage"));
	private final JMenuItem m_jmFullScreenItem = new JMenuItem(HOVerwaltung.instance().getLanguageString("FullScreen.toggle"));

	private final JMenuItem m_jmImportItem = new JMenuItem(HOVerwaltung.instance().getLanguageString("HRFImportieren"));
	private final JMenuItem m_jmOptionen = new JMenuItem(HOVerwaltung.instance().getLanguageString("Optionen"));
	private final JMenuItem m_jmTraining = new JMenuItem(HOVerwaltung.instance().getLanguageString("SubskillsBerechnen"));
	private final JMenuItem m_jmTraining2 = new JMenuItem(HOVerwaltung.instance().getLanguageString("SubskillsBerechnen")
			+ " (7 " + HOVerwaltung.instance().getLanguageString("Wochen") + ")");
	private final JMenuItem m_jmiFlags = new JMenuItem(HOVerwaltung.instance().getLanguageString("Flaggen"));
	private final JMenuItem m_jmiHO = new JMenuItem(HOVerwaltung.instance().getLanguageString("HO"));
	private final JMenuItem m_jmiHObeta = new JMenuItem(HOVerwaltung.instance().getLanguageString("HO") + " ("
			+ HOVerwaltung.instance().getLanguageString("Beta") + ")");
	private final JMenuItem m_jmiEPV = new JMenuItem(HOVerwaltung.instance().getLanguageString("EPV"));
	private final JMenuItem m_jmiRatings = new JMenuItem(HOVerwaltung.instance().getLanguageString("Ratings"));

	private final JMenuItem m_jmiLanguages = new JMenuItem(HOVerwaltung.instance().getLanguageString("Sprachdatei"));
	private final JMenuItem m_jmiPluginsDelete = new JMenuItem(HOVerwaltung.instance().getLanguageString("loeschen"));
	private final JMenuItem m_jmiPluginsLibrary = new JMenuItem(HOVerwaltung.instance().getLanguageString("Libraries"));
	private final JMenuItem m_jmiPluginsNormal = new JMenuItem(HOVerwaltung.instance().getLanguageString("Normal"));
	// Components
	private HOTabbedPane m_jtpTabbedPane;
	private OnlineWorker m_clOnlineWorker = new OnlineWorker();

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

		if (HO.isDevelopment()) {
			txt += " DEV";
			final int r = getRevisionNumber();
			if (r > 1) {
				txt += " (r" + getRevisionNumber() + ")";
			}
		}
		return txt;
	}

//	public static boolean isDevelopment() {
//		return DEVELOPMENT;
//	}

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
		return ((LineupPanel)getTabbedPane().getModulePanel(IModule.LINEUP));
	}

	public InfoPanel getInfoPanel() {
		if(m_jpInfoPanel == null)
			m_jpInfoPanel = new InfoPanel();
		return m_jpInfoPanel;
	}

	public PlayerAnalysisPanel getSpielerAnalyseMainPanel() {
		return ((PlayerAnalysisPanel)getTabbedPane().getModulePanel(IModule.PLAYERANALYSIS));
	}

	public SpielerUebersichtsPanel getSpielerUebersichtPanel() {
		return ((SpielerUebersichtsPanel)getTabbedPane().getModulePanel(IModule.PLAYEROVERVIEW));
	}

	public HOTabbedPane getTabbedPane() {
		return m_jtpTabbedPane;
	}

	/**
	 * Get the current weather.
	 */
	public static int getWetter() {
		if (m_clHOMainFrame == null) {
			return ISpieler.LEICHTBEWOELKT;
		}
		return instance().getAufstellungsPanel().getAufstellungsAssitentPanel().getWetter();

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
	public TransfersPanel getTransferScoutPanel() {
		return ((TransfersPanel)getTabbedPane().getModulePanel(IModule.TRANSFERS));
	}

	public SpielePanel getMatchesPanel(){
		return ((SpielePanel)getTabbedPane().getModulePanel(IModule.MATCHES));
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
			new DownloadDialog();
		} else if (source.equals(m_jmOptionen)) { // Options
			new OptionenDialog(this).setVisible(true);
		} else if (source.equals(m_jmTraining)) { // recalc training
			if (JOptionPane.showConfirmDialog(this,
					"Depending on database volume this process takes several minutes. Start recalculation ?",
					"Subskill Recalculation", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				HOVerwaltung.instance().recalcSubskills(true, null);
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
				HOVerwaltung.instance().recalcSubskills(true, from);
			}
		} else if (source.equals(m_jmFullScreenItem)) { // Toggle full screen
														// mode
			FullScreen.instance().toggle(this);
		} else if (source.equals(m_jmBeendenItem)) { // Quit
			// Restore normal window mode (i.e. leave full screen)
			FullScreen.instance().restoreNormalMode(this);
			// Fire CloseEvent, so all Plugins get informed
			this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}   else if (source.equals(m_jmCreditsItem)) { 
			StringBuilder text = new StringBuilder(200);
			text.append("Hattrick Organizer ").append(VERSION).append("\n\n");
			text.append("2003 development started by Thomas Werth & Volker Fischer.\n");
			text.append("Since 2006 this project is open source and developed by changing developers.");
			JOptionPane.showMessageDialog(null, text.toString(), "Credits", JOptionPane.INFORMATION_MESSAGE);
		} else if (source.equals(m_jmHomepageItem)) { // Homepage
			HelperWrapper.instance().openUrlInUserBRowser(MyConnector.getHOSite());
		} else if (source.equals(m_jmWikiItem)) { // Forum
			HelperWrapper.instance().openUrlInUserBRowser("https://sourceforge.net/apps/trac/ho1/wiki/Manual");
		} else if (source.equals(m_jmForumItem)) { // Forum
			HelperWrapper.instance().openUrlInUserBRowser("https://sourceforge.net/apps/phpbb/ho1/index.php");
		} else if (source.equals(m_jmHattrickItem)) { // Hattrick
			HelperWrapper.instance().openUrlInUserBRowser("http://www.hattrick.org");
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
		// model.HOVerwaltung.instance().getLanguageString("BeendenMeldung"),
		// model.HOVerwaltung.instance().getLanguageString("BeendenTitel"),
		// JOptionPane.YES_NO_OPTION);
		// int value = JOptionPane.OK_OPTION; //Doof aber schnell zu schreiben!
		// if ( value == JOptionPane.OK_OPTION )
		// aktuelle UserParameter speichern
		saveUserParameter();

		HOLogger.instance().debug(getClass(), "UserParameters saved");

		// Scoutliste speichern
		getTransferScoutPanel().getScoutPanel().saveScoutListe();

		HOLogger.instance().debug(getClass(), "ScoutList saved");

		// Faktoren saven
		FormulaFactors.instance().save();

		HOLogger.instance().debug(getClass(), "FormulaFactors saved");

		// Disconnect
		DBManager.instance().disconnect();

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
	 * Frame aufbauen
	 */
	public void initComponents() {
		javax.swing.ToolTipManager.sharedInstance().setDismissDelay(5000);

		setContentPane(new ImagePanel());
		getContentPane().setLayout(new BorderLayout());

		m_jtpTabbedPane = new HOTabbedPane();

		IModule[] activeModules = ModuleManager.instance().getModules(true);
		for (int i = 0; i < activeModules.length; i++) {
			if(activeModules[i].hasMainTab() && activeModules[i].isStartup())
				m_jtpTabbedPane.showTab(activeModules[i].getModuleId());
		}


		getContentPane().add(m_jtpTabbedPane, BorderLayout.CENTER);
		m_jtpTabbedPane.setSelectedIndex(0);
		//m_jtpTabbedPane.addChangeListener(this);

		
		getContentPane().add(getInfoPanel(), BorderLayout.SOUTH);

		
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

		//m_jmUpdating.add(m_jmPluginsRefresh);
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
		IModule[] activeModules = ModuleManager.instance().getModules(true);
		for (int i = 0; i < activeModules.length; i++) {
			if(activeModules[i].hasMainTab()){
				JMenuItem showTabMenuItem = new JMenuItem(activeModules[i].getDescription());
				showTabMenuItem.setAccelerator(activeModules[i].getKeyStroke());
				showTabMenuItem.putClientProperty("MODULE", activeModules[i]);
				showTabMenuItem.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						JMenuItem item = (JMenuItem)e.getSource();
						IModule module = (IModule)item.getClientProperty("MODULE");
						getTabbedPane().showTab(module.getModuleId());
						
					}
				});
				m_jmVerschiedenes.add(showTabMenuItem);
			}
			if(activeModules[i].hasMenu()){
				m_jmModuleMenu.add(activeModules[i].getMenu());
			}
		}
		

		// About
		m_jmHomepageItem.addActionListener(this);
		m_jmAbout.add(m_jmHomepageItem);

		m_jmForumItem.addActionListener(this);
		m_jmAbout.add(m_jmForumItem);

		m_jmHattrickItem.addActionListener(this);
		m_jmAbout.add(m_jmHattrickItem);

		m_jmWikiItem.addActionListener(this);
		m_jmAbout.add(m_jmWikiItem);
		
		m_jmAbout.addSeparator();

		m_jmCreditsItem.addActionListener(this);
		m_jmAbout.add(m_jmCreditsItem);

		
		// add Top Level Menus
		m_jmMenuBar.add(m_jmVerschiedenes);
		m_jmMenuBar.add(new ToolManager().getToolMenu());
		m_jmMenuBar.add(m_jmModuleMenu);

		m_jmMenuBar.add(m_jmAbout);
		
		if (DeveloperMode.DEVELOPER_MODE) {
			m_jmMenuBar.add(DeveloperMode.getDeveloperMenu());
		}
		
		SwingUtilities.updateComponentTreeUI(m_jmMenuBar);

		// Adden
		this.setJMenuBar(m_jmMenuBar);
	}

	/**
	 * Proxyeinstellungen
	 */
	public void initProxy() {
		if (UserParameter.instance().ProxyAktiv) {
			MyConnector.instance().setProxyHost(UserParameter.instance().ProxyHost);
			MyConnector.instance().setUseProxy(UserParameter.instance().ProxyAktiv);
			MyConnector.instance().setProxyPort(UserParameter.instance().ProxyPort);
			MyConnector.instance().setProxyAuthentifactionNeeded(UserParameter.instance().ProxyAuthAktiv);
			MyConnector.instance().setProxyUserName(UserParameter.instance().ProxyAuthName);
			MyConnector.instance().setProxyUserPWD(UserParameter.instance().ProxyAuthPassword);
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
			float faktorgeld = (float) HOVerwaltung.instance().getModel().getXtraDaten().getCurrencyRate();

			if (faktorgeld > -1) {
				UserParameter.instance().faktorGeld = faktorgeld;
			}
		} catch (Exception e) {
			HOLogger.instance().log(HOMainFrame.class, "Währungsanpassung gescheitert!");
		}

		// Tabs prüfen
		//checkTabs();
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
		m_jtpTabbedPane.showTab(IModule.MATCHES);

		getMatchesPanel().showMatch(matchid);
	}

	// ----------------Hilfsmethoden---------------------------------

	/**
	 * Zeigt das Tab an (Nicht Index, sondern Konstante benutzen!
	 * 
	 * @param tabnumber
	 *            number of the tab to show
	 */
	public void showTab(int tabnumber) {
		m_jtpTabbedPane.showTab(tabnumber);
	}


	/**
	 * React on state changed events.
	 * No need anymore
	 */
	public void stateChanged(ChangeEvent changeEvent) {
//		// Wenn ein Tab als Temp gespeichert wurde dieses entfernen
//		if (m_sToRemoveTabName != null) {
//			final int index = m_jtpTabbedPane.indexOfTab(m_sToRemoveTabName);
//
//			if ((index >= 0) && (m_jtpTabbedPane.getTabCount() > index)) {
//				// hier wegen rekursion
//				m_sToRemoveTabName = null;
//				m_jtpTabbedPane.removeTabAt(index);
//			} else {
//				HOLogger.instance().log(
//						HOMainFrame.class,
//						"Fehler Tabremove: " + m_sToRemoveTabName + " " + index + "/"
//								+ m_jtpTabbedPane.getTabCount());
//				m_sToRemoveTabName = null;
//			}
//		}
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
	 * Holt die Parameter aus den Dialogen und speichert sie in der DB
	 */
	private void saveUserParameter() {
		UserParameter parameter = UserParameter.instance();

		parameter.hoMainFrame_PositionX =  Math.max(getLocation().x, 0);
		parameter.hoMainFrame_PositionY = Math.max(getLocation().y, 0);
		parameter.hoMainFrame_width = Math.min(getSize().width, getToolkit().getScreenSize().width - parameter.hoMainFrame_PositionX);
		parameter.hoMainFrame_height = Math.min(getSize().height, getToolkit().getScreenSize().height - parameter.hoMainFrame_PositionY);

		
		
		
		
		
		final AufstellungsAssistentPanel aap = getAufstellungsPanel().getAufstellungsAssitentPanel();

		parameter.bestPostWidth = Math.max(getSpielerUebersichtPanel().getBestPosWidth(),
				getAufstellungsPanel().getBestPosWidth());

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
		if(getTabbedPane().isModuleTabVisible(IModule.PLAYEROVERVIEW)){
			final int[] sup = getSpielerUebersichtPanel().getDividerLocations();
			parameter.spielerUebersichtsPanel_horizontalLeftSplitPane = sup[0];
			parameter.spielerUebersichtsPanel_horizontalRightSplitPane = sup[1];
			parameter.spielerUebersichtsPanel_verticalSplitPane = sup[2];
			getSpielerUebersichtPanel().saveColumnOrder();
		}

		// AufstellungsPanel
		if(getTabbedPane().isModuleTabVisible(IModule.LINEUP)){
			final int[] ap = getAufstellungsPanel().getDividerLocations();
			parameter.aufstellungsPanel_verticalSplitPaneLow = ap[0];
			parameter.aufstellungsPanel_horizontalLeftSplitPane = ap[1];
			parameter.aufstellungsPanel_horizontalRightSplitPane = ap[2];
			parameter.aufstellungsPanel_verticalSplitPane = ap[3];
			getAufstellungsPanel().saveColumnOrder();
		}

		// SpielePanel
		if(getTabbedPane().isModuleTabVisible(IModule.MATCHES)){
			final int[] sp = getMatchesPanel().getDividerLocations();
			parameter.spielePanel_horizontalLeftSplitPane = sp[0];
			parameter.spielePanel_verticalSplitPane = sp[1];
			getMatchesPanel().saveColumnOrder();
		}

		// SpielerAnalyse
		if(getTabbedPane().isModuleTabVisible(IModule.PLAYERANALYSIS)){
			final int spa = getSpielerAnalyseMainPanel().getDividerLocation();
			parameter.spielerAnalysePanel_horizontalSplitPane = spa;
			getSpielerAnalyseMainPanel().saveColumnOrder();
		}

		// TransferScoutPanel
		if(getTabbedPane().isModuleTabVisible(IModule.TRANSFERS)){
			final int tsp = getTransferScoutPanel().getScoutPanel().getDividerLocation();
			parameter.transferScoutPanel_horizontalSplitPane = tsp;
		}

		DBManager.instance().saveUserParameter();
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
