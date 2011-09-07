// %1308103607:de.hattrickorganizer.net%
/*
 * MyConnector.java
 *
 * Created on 7. April 2003, 09:36
 */
package de.hattrickorganizer.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import javax.swing.JOptionPane;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import sun.misc.BASE64Encoder;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.login.OAuthDialog;
import de.hattrickorganizer.logik.xml.XMLExtensionParser;
import de.hattrickorganizer.logik.xml.XMLNewsParser;
import de.hattrickorganizer.logik.xml.xmlTeamDetailsParser;
import de.hattrickorganizer.model.Extension;
import de.hattrickorganizer.model.News;
import de.hattrickorganizer.net.rmiHOFriendly.ServerVerweis;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;
import de.hattrickorganizer.tools.updater.VersionInfo;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MyConnector implements plugins.IDownloadHelper {
	//~ Static fields/initializers -----------------------------------------------------------------
	static final private int chppID = 3330;
	static final private String htUrl = "http://chpp.hattrick.org/chppxml.ashx";
	public static String m_sIDENTIFIER =
		"HO! Hattrick Organizer V" + de.hattrickorganizer.gui.HOMainFrame.VERSION;
	private static MyConnector m_clInstance;
	private final static String VERSION_MATCHORDERS = "1.8";
	private final static String VERSION_TRAINING = "1.5";
	private final static String VERSION_MATCHLINEUP = "1.4";

	private final static String CONSUMER_KEY = ">Ij-pDTDpCq+TDrKA^nnE9";
	private final static String CONSUMER_SECRET = "2/Td)Cprd/?q`nAbkAL//F+eGD@KnnCc>)dQgtP,p+p";
	//~ Instance fields ----------------------------------------------------------------------------

	private static boolean requireSetMatchorder = false;

	private String m_ProxyUserName = "";
	private String m_ProxyUserPWD = "";
	private String m_sProxyHost = "";
	private String m_sProxyPort = "";
	private boolean m_bAuthenticated;
	private boolean m_bProxyAuthentifactionNeeded;
	private boolean m_bUseProxy;
	private int m_iUserID = -1;

	private OAuthService m_OAService;
	private Token m_OAAccessToken;

	final static private boolean DEBUGSAVE = false;
	final static private String SAVEDIR = "C:/temp/ho/";



	//~ Constructors -------------------------------------------------------------------------------
	/**
	 * Creates a new instance of MyConnector.
	 */
	private MyConnector() {
		m_OAService = new ServiceBuilder()
		.provider(HattrickAPI.class)
		.apiKey(Helper.decryptString(CONSUMER_KEY))
		.apiSecret(Helper.decryptString(CONSUMER_SECRET))
		.signatureType(SignatureType.Header)
		.build();
		
		m_OAAccessToken = new Token(Helper.decryptString(gui.UserParameter.instance().AccessToken),
			Helper.decryptString(gui.UserParameter.instance().TokenSecret));
		
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * Get the MyConnector instance.
	 */
	public static MyConnector instance() {
		if (m_clInstance == null) {
			m_clInstance = new MyConnector();
		}

		return m_clInstance;
	}

	public static String getResourceSite() {
		return getPluginSite();
	}

	public static String getHOSite() {
		return "http://www.hattrickorganizer.net";
	}

	public static String getPluginSite() {
		return "http://plugins.hattrickorganizer.net";
	}

	public static boolean isRequireSetMatchorder() {
		return requireSetMatchorder;
	}

	public static void setRequireSetMatchorder(boolean requireSetMatchorder) {
		MyConnector.requireSetMatchorder = requireSetMatchorder;
	}

	/**
	 * Fetch our arena
	 *
	 * @return 	arena xml
	 *
	 * @throws IOException
	 */
	public String getArena() throws IOException {
		return getArena(-1);
	}

	/**
	 * Fetch a specific arena
	 *
	 * @param arenaId	id of the arena to fetch (-1 = our arena)
	 * @return 	arena xml
	 *
	 * @throws IOException
	 */
	public String getArena(int arenaId) throws IOException {
		String url = htUrl +"?file=arenadetails";

		if (arenaId > 0)
			url += "&arenaID="+arenaId;
		return getPage(url);
	}

	/**
	 * holt die Finanzen
	 */
	public String getEconomy() throws IOException {
		final String url = htUrl + "?file=economy";

		return getPage(url);
	}

	/////////////////////////////////////////////////////////////////////////////////
	//get-XML-Files
	////////////////////////////////////////////////////////////////////////////////

	/**
	 * downloads an xml File from hattrick
	 * Behavior has changed with oauth, but we try to convert old syntaxes.
	 * 
	 * @param file ex. = "?file=leaguedetails&[leagueLevelUnitID = integer]"
	 *
	 * @return the complete file as String
	 */
	public String getHattrickXMLFile(String file) throws IOException {
		String url;

		// An attempt at solving old syntaxes.

		if (file.contains("chppxml.axd")) {
			file = file.substring(file.indexOf("?"));
		} else if (file.contains(".asp")) {
			String s = file.substring(0, file.indexOf("?")).replace(".asp", "").replace("/common/", "");
			file = "?file=" + s + "&" + file.substring(file.indexOf("?")+1); 
		} 

		url =  htUrl + file;
		return getPage(url);
	}

	/**
	 * lädt die Tabelle
	 */
	public String getLeagueDetails() throws IOException {
		final String url = htUrl + "?file=leaguedetails";

		return getPage(url);
	}

	/**
	 * lädt den Spielplan
	 */
	public String getLeagueFixtures(int season, int ligaID) throws IOException {
		String url = htUrl + "?file=leaguefixtures";

		if (season > 0) {
			url += ("&season=" + season);
		}

		if (ligaID > 0) {
			url += ("&leagueLevelUnitID=" + ligaID);
		}

		return getPage(url);
	}

	/**
	 * lädt das MatchArchiv als xml
	 */
	public String getMatchArchiv(int teamId, String firstDate, String lastDate)
	throws IOException {
		String url = htUrl + "?file=matchesarchive";

		if (teamId > 0) {
			url += ("&teamID=" + teamId);
		}

		if (firstDate != null) {
			url += ("&FirstMatchDate=" + firstDate);
		}

		if (lastDate != null) {
			url += ("&LastMatchDate=" + lastDate);
		}

		return getPage(url);
	}

	/**
	 * lädt die Aufstellungsbewertung zu einem Spiel
	 */
	public String getMatchLineup(int matchId, int teamId) throws IOException {
		String url = htUrl + "?file=matchlineup&version=" +
		VERSION_MATCHLINEUP;

		if (matchId > 0) {
			url += ("&matchID=" + matchId);
		}

		if (teamId > 0) {
			url += ("&teamID=" + teamId);
		}

		if (DEBUGSAVE) {
			final String ret = getPage(url);
			FileWriter fw = new FileWriter(new File(SAVEDIR+"matchlineup_m"
					+ matchId + "_t" + teamId + "_"
					+ System.currentTimeMillis() + ".xml"));
			fw.write(ret);
			fw.flush();
			fw.close();
			return ret;
		} else {
			return getPage(url);
		}
	}

	/**
	 * lädt die Aufstellung zu einem Spiel
	 */
	public String getMatchOrder(int matchId) throws IOException {
		String url = htUrl + "?file=matchorders&version="
		+ VERSION_MATCHORDERS + "&matchID=" + matchId + "&isYouth=false";

		if (DEBUGSAVE) {
			final String ret = getPage(url);
			FileWriter fw = new FileWriter(new File(SAVEDIR + "matchorders_m"
					+ matchId + "_" + System.currentTimeMillis() + ".xml"));
			fw.write(ret);
			fw.flush();
			fw.close();
			return ret;
		} else {
			return getPage(url);
		}
	}

	public String setMatchOrder(int matchId, String orderString) throws IOException {
		requireSetMatchorder = true;

		String urlpara = "?file=matchorders&version="
			+ VERSION_MATCHORDERS;
		if (matchId > 0) {
			urlpara += "&matchID=" + matchId;
		}
		urlpara += "&isYouth=false" + "&actionType=setmatchorder";

		HashMap<String, String> paras = new HashMap<String, String>();
		paras.put("lineup", orderString);
		String result = readStream(postWebFileWithBodyParameters(htUrl+urlpara, paras, true));
		requireSetMatchorder = false;
		return result;

	}


	/**
	 * lädt die Aufstellungsbewertung zu einem Spiel
	 */
	public String getMatchdetails(int matchId) throws IOException {
		String url = htUrl + "?file=matchdetails";
		if (matchId > 0) {
			url += ("&matchID=" + matchId);
		}
		url += "&matchEvents=true";

		if (DEBUGSAVE) {
			final String ret = getPage(url);
			FileWriter fw = new FileWriter(new File(SAVEDIR + "matchdetails_m"
					+ matchId + "_" + System.currentTimeMillis() + ".xml"));
			fw.write(ret);
			fw.flush();
			fw.close();
			return ret;
		} else {
			return getPage(url);
		}
	}

	/**
	 * lädt die matches.asp seite als xml
	 */
	public String getMatchesASP(int teamId, boolean forceRefresh) throws IOException {
		String url = htUrl + "?file=matches";

		if (teamId > 0) {
			url += ("&teamID=" + teamId);
		}

		if (forceRefresh) {
			url += "&actionType=refreshCache";
		}

		return getPage(url);
	}

	/////////////////////////////////////////////////////////////////////////////////
	//internel getPage Funcs
	////////////////////////////////////////////////////////////////////////////////
	public String getPage(String url) throws IOException {
		return getWebPage(url);
	}

	/**
	 * holt die Spielerdaten
	 */
	public String getPlayersAsp() throws IOException {
		final String url = htUrl + "?file=players";

		return getPage(url);
	}

	/**
	 * Setter for property m_bProxyAuthentifactionNeeded.
	 *
	 * @param m_bProxyAuthentifactionNeeded New value of property m_bProxyAuthentifactionNeeded.
	 */
	public void setProxyAuthentifactionNeeded(boolean m_bProxyAuthentifactionNeeded) {
		this.m_bProxyAuthentifactionNeeded = m_bProxyAuthentifactionNeeded;
	}

	/**
	 * Getter for property m_bProxyAuthentifactionNeeded.
	 *
	 * @return Value of property m_bProxyAuthentifactionNeeded.
	 */
	public boolean isProxyAuthentifactionNeeded() {
		return m_bProxyAuthentifactionNeeded;
	}

	/**
	 * Setter for property m_sProxyHost.
	 *
	 * @param m_sProxyHost New value of property m_sProxyHost.
	 */
	public void setProxyHost(java.lang.String m_sProxyHost) {
		this.m_sProxyHost = m_sProxyHost;
	}

	/**
	 * Getter for property m_sProxyHost.
	 *
	 * @return Value of property m_sProxyHost.
	 */
	public java.lang.String getProxyHost() {
		return m_sProxyHost;
	}

	/**
	 * Setter for property m_sProxyPort.
	 *
	 * @param m_sProxyPort New value of property m_sProxyPort.
	 */
	public void setProxyPort(java.lang.String m_sProxyPort) {
		this.m_sProxyPort = m_sProxyPort;
	}

	/**
	 * Getter for property m_sProxyPort.
	 *
	 * @return Value of property m_sProxyPort.
	 */
	public java.lang.String getProxyPort() {
		return m_sProxyPort;
	}

	/**
	 * Setter for property m_ProxyUserName.
	 *
	 * @param m_ProxyUserName New value of property m_ProxyUserName.
	 */
	public void setProxyUserName(java.lang.String m_ProxyUserName) {
		this.m_ProxyUserName = m_ProxyUserName;
	}

	/**
	 * Getter for property m_ProxyUserName.
	 *
	 * @return Value of property m_ProxyUserName.
	 */
	public java.lang.String getProxyUserName() {
		return m_ProxyUserName;
	}

	/**
	 * Setter for property m_ProxyUserPWD.
	 *
	 * @param m_ProxyUserPWD New value of property m_ProxyUserPWD.
	 */
	public void setProxyUserPWD(java.lang.String m_ProxyUserPWD) {
		this.m_ProxyUserPWD = m_ProxyUserPWD;
	}

	/**
	 * Getter for property m_ProxyUserPWD.
	 *
	 * @return Value of property m_ProxyUserPWD.
	 */
	public java.lang.String getProxyUserPWD() {
		return m_ProxyUserPWD;
	}

	/**
	 * holt die Liste der verfügbaren Server aus der I-Net-Server-DB
	 *
	 * @return Array-Liste mit ServerVerweis Objekten
	 */
	public ServerVerweis[] getServerList() {
		String[] list = new String[0];
		String s = null;
		ServerVerweis[] server = null;

		try {
			s = getWebPage("http://tooldesign.ch/ho/index.php?cmd=getServerList");
			list = Helper.generateStringArray(s, ';');

			if ((s != null) && (!s.trim().equals("")) && (s.length() > 0)) {
				//-1 da letzter Eintrag == "" ist
				server = new ServerVerweis[list.length - 1];
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"MyConnector.getServerList: Kein Connect zur FriendlyDB" + e);
		}

		//gleich Einträge erstellen
		for (int i = 0; i < (list.length - 1); i++) {
			if (server != null) {
				server[i] = new ServerVerweis(list[i]);
			}
		}

		return server;
	}

	/**
	 * holt die Teamdetails
	 */
	public String getTeamdetails(int teamId) throws IOException {
		String url = htUrl + "?file=teamdetails";
		if (teamId > 0) {
			url += ("&teamID=" + teamId);
		}

		return getPage(url);
	}

	/**
	 * Get the training XML data.
	 */
	public String getTraining() throws IOException {
		final String url =  htUrl + "?file=training&version="+ VERSION_TRAINING;

		return getPage(url);
	}

	/////////////////////////////////////////////////////////////////////////////////
	//get-HTML-Files
	////////////////////////////////////////////////////////////////////////////////

	/**
	 * lädt die Tabelle
	 */
	public String getTransferCompare(int playerID) throws IOException {
		
		String url =  htUrl + "?file=playerdetails&playerID="+playerID;

		return getPage(url);
	}

	/**
	 * Setter for property m_UseProxy.
	 *
	 * @param m_UseProxy New value of property m_UseProxy.
	 */
	public void setUseProxy(boolean m_UseProxy) {
		this.m_bUseProxy = m_UseProxy;
	}

	/////////////////////////////////////////////////////////////////////////////////
	//Accessor
	////////////////////////////////////////////////////////////////////////////////

	/**
	 * Getter for property m_UseProxy.
	 *
	 * @return Value of property m_UseProxy.
	 */
	public boolean isUseProxy() {
		return m_bUseProxy;
	}


	/**
	 * holt die Vereinsdaten
	 */
	public String getVerein() throws IOException {
		final String url = htUrl + "?file=club";
		return getPage(url);
	}

	public String getWebPage(String surl) throws IOException {
		return getWebPage(surl, true, false); // show connect error
	}

	/**
	 * Get the content of a web page in one string.
	 */
	public String getWebPage(String surl, boolean showError, boolean shortTimeOut) throws IOException {
		final InputStream resultingInputStream = getWebFile(surl, showError, shortTimeOut);
		
		return readStream(resultingInputStream);
	}

	/**
	 * holt die Weltdaten
	 */
	public String getWorldDetails() throws IOException {
		final String url =  htUrl + "?file=worlddetails";

		return getPage(url);
	}

	/////////////////////////////////////////////////////////////////////////////////
	//Update Checker
	////////////////////////////////////////////////////////////////////////////////
	public VersionInfo getLatestVersion() {
		VersionInfo ret = new VersionInfo();
		ret.setBeta(false);
		ret.setVersion(HOMainFrame.VERSION);
		try {
			final String s = getWebPage(MyConnector.getPluginSite() + "/version.htm", false, true);
			try {
				ret.setVersion(Double.parseDouble(s));
			} catch (NumberFormatException e) {
				HOLogger.instance().debug(getClass(), "Error parsing version '" + s + "': " + e);
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), "Unable to connect to the update server (HO): " + e);
		}
		return ret;
	}

	/**
	 * Get information about the latest HO beta.
	 */
	public VersionInfo getLatestBetaVersion() {
		BufferedReader br = null;
		InputStream is = null;
		try {
			is = getWebFile(MyConnector.getPluginSite()+"/betaversion.htm", false, true);
			if (is != null) {
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				VersionInfo ret = new VersionInfo();
				String line;

				while ((line = br.readLine()) != null) {
					int pos = line.indexOf("=");
					if (pos > 0) {
						String key = line.substring(0, pos).trim();
						String val = line.substring(pos+1).trim();
						ret.setValue(key, val);
					}
				}
				if (ret.isValid()) {
					HOLogger.instance().log(getClass(), "LatestBetaVersion: " + ret);
					return ret;
				}
			} else {
				HOLogger.instance().log(getClass(), "Unable to connect to the update server (HO).");
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), "Unable to connect to the update server (HO): " + e);
		} finally {
			try {
				if (br != null) br.close();
				if (is != null) is.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	public Extension getEpvVersion() {
		try {
			final String s =
				getWebPage(MyConnector.getResourceSite()+"/downloads/epv.xml");

			return (new XMLExtensionParser()).parseExtension(s);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"Unable to connect to the update server (EPV): " + e);
			return new Extension();
		}
	}

	public Extension getRatingsVersion() {
		try {
			final String s =
				getWebPage(MyConnector.getResourceSite()+"/downloads/ratings.xml");

			return (new XMLExtensionParser()).parseExtension(s);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"Unable to connect to the update server (Ratings): " + e);
			return new Extension();
		}
	}

	public News getLatestNews() {
		try {
			final String s = MyConnector.instance().getWebPage(MyConnector.getResourceSite()+"/downloads/news.xml", false, true);
			XMLNewsParser parser = new XMLNewsParser();
			return parser.parseNews(s);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"Unable to connect to the update server (News): " + e);
			return new News();
		}
	}


	/////////////////////////////////////////////////////////////////////////////////
	//Proxy
	////////////////////////////////////////////////////////////////////////////////
	public void enableProxy() {
		if (m_bUseProxy) {
			System.getProperties().put("https.proxyHost", m_sProxyHost);
			System.getProperties().put("https.proxyPort", m_sProxyPort);
			System.getProperties().put("http.proxyHost", m_sProxyHost);
			System.getProperties().put("http.proxyPort", m_sProxyPort);
		} else {
			System.getProperties().remove("https.proxyHost");
			System.getProperties().remove("https.proxyPort");
			System.getProperties().remove("http.proxyHost");
			System.getProperties().remove("http.proxyPort");
		}
	}

	/**
	 * Get the region id for a certain team.
	 */
	public String fetchRegionID(int teamID) {
		String xmlFile = "";

		try {
			xmlFile =  htUrl + "?file=teamdetails&teamID=" + teamID;
			xmlFile = getPage(xmlFile);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
			return "-1";
		}

		return new xmlTeamDetailsParser().fetchRegionID(xmlFile);
	}



	public InputStream getFileFromWeb(String url, boolean displaysettingsScreen) throws IOException {
		return getFileFromWeb(url, displaysettingsScreen, false);
	}

	/**
	 * Get a file from a web server as input stream.
	 */
	public InputStream getFileFromWeb(String url, boolean displaysettingsScreen, boolean showErrorMessage)
	throws IOException {
		if (displaysettingsScreen) {
			//Show Screen
			final de.hattrickorganizer.gui.login.ProxyDialog proxyDialog =
				new de.hattrickorganizer.gui.login.ProxyDialog(
						de.hattrickorganizer.gui.HOMainFrame.instance());
			proxyDialog.setVisible(true);
		}

		return getWebFile(url, showErrorMessage, false);
	}

	/**
	 * Get the content of a normal (non-HT) web page in one string.
	 */
	public String getUsalWebPage(String url, boolean displaysettingsScreen, boolean shortTimeOut) throws IOException {
		if (displaysettingsScreen) {
			//Show Screen
			final de.hattrickorganizer.gui.login.ProxyDialog proxyDialog =
				new de.hattrickorganizer.gui.login.ProxyDialog(
						de.hattrickorganizer.gui.HOMainFrame.instance());
			proxyDialog.setVisible(true);
		}

		return getWebPage(url, false, shortTimeOut);
	}

	public String getUsalWebPage(String url, boolean displaysettingsScreen) throws IOException {
		return getUsalWebPage(url, displaysettingsScreen, false);
	}

	/////////////////////////////////////////////////////////////////////////////////
	//HO-Friendly Funcs
	////////////////////////////////////////////////////////////////////////////////

	/**
	 * registriert einen HO! Friendly Server im Internet
	 */
	public int registerServer(String ipAdress, int port, String info) {
		int i = -1;
		String result = "";
		String request = "";

		try {
			request =
				"http://tooldesign.ch/ho/index.php?cmd=registerServer&ip=" + ipAdress
				/*123.123.123.123*/
				+"&port=" + port /*1234*/
				+"&info=" + info /*Infotext"*/;
			result = getWebPage(request);

			try {
				i = Integer.parseInt(result);
			} catch (NumberFormatException e) {
				i = -1;
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"MyConnector.registerServer: Kein Connect zur FriendlyDB" + e);
			HOLogger.instance().log(getClass(),e);
		}

		return i;
	}

	/**
	 * Informiert Inet Db das Friendly-Server weiterhin lauscht
	 */
	public boolean sendAlive(int matchId) {
		try {
			final String s =
				getWebPage("http://tooldesign.ch/ho/index.php?cmd=keepAlive&id=" + matchId);

			if (Helper.parseDate(s) == null) {
				return false;
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"MyConnector.sendAlive: Kein Connect zur FriendlyDB" + e);
			HOLogger.instance().log(getClass(),e);
		}

		return true;
	}

	/**
	 * holt die Liste der verfügbaren Server aus der I-Net-Server-DB
	 */
	public boolean sendSpielbericht(de.hattrickorganizer.model.Spielbericht sb, boolean isServer) {
		String request = "http://tooldesign.ch/ho/index.php?cmd=sendSB";
		String s = null;

		//Verhindern das eigen Spiele gesendet werden!
		if ((sb == null)
				|| (sb.Heim().getTeamName().trim().equals(sb.Gast().getTeamName().trim()))) {
			//Kein Send nötig return success
			return true;
		}

		try {
			request += "&teamID=";
			request
			+= de
			.hattrickorganizer
			.model
			.HOVerwaltung
			.instance()
			.getModel()
			.getBasics()
			.getTeamId();
			request += "&teamName=";
			request
			+= java.net.URLEncoder.encode(
					de
					.hattrickorganizer
					.model
					.HOVerwaltung
					.instance()
					.getModel()
					.getBasics()
					.getTeamName(),
					"UTF-8");
			request += "&goalsHome=";
			request += sb.ToreHeim();
			request += "&goalsGuest=";
			request += sb.ToreGast();
			request += "&Home=";
			request += java.net.URLEncoder.encode(sb.Heim().getTeamName(), "UTF-8");
			request += "&Guest=";
			request += java.net.URLEncoder.encode(sb.Gast().getTeamName(), "UTF-8");

			//Damit man einfach rausbekommt ob Absender Heim oder Gast ist
			request += "&isHome=";
			request += ((isServer) ? "1" : "0");

			//noch unused , für später reserviert
			request += "&CupID=0";

			//HOLogger.instance().log(getClass(), request );
			s = getWebPage(request);

			//HOLogger.instance().log(getClass(), s );
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"Send Fehlgeschlagen" + e);
		}

		return (s != null && s.trim().equalsIgnoreCase("True"));
	}

	/**
	 * Informiert die DB das der Server nicht mehr zur verfügung steht
	 */
	public boolean unregisterServer(int matchId) {
		try {
			final String s =
				getWebPage(
						"http://tooldesign.ch/ho/index.php?cmd=unregisterServer&id=" + matchId);

			if (!s.trim().equals("True")) {
				return false;
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"MyConnector.unregisterServer: Kein Connect zur FriendlyDB" + e);
			HOLogger.instance().log(getClass(),e);
		}

		return true;
	}

	/**
	 * Get a web page using a URLconnection.
	 */
	private InputStream getWebFile(String surl, boolean showErrorMessage, 
			boolean shortTimeOut) throws IOException {

		OAuthDialog authDialog = null;
		Response response = null;

		boolean tryAgain = true;
		try {
			while (tryAgain == true) {
				OAuthRequest request = new OAuthRequest(Verb.GET, surl);	

				infoHO(request);

				m_OAService.signRequest(m_OAAccessToken, request);
				response = request.send();
				if (response.getCode() == 401) {

					if (authDialog == null) {
						authDialog = new OAuthDialog(HOMainFrame.instance(), m_OAService);
					}
					authDialog.setVisible(true);
					// A way out for a user unable to authorize for some reason
					if (authDialog.getUserCancel() == true) {
						return null;
					}

					m_OAAccessToken = authDialog.getAccessToken();

					// Try again...
				} else {				
					tryAgain = false;

					// We are done!
				}	
			}	

		} catch (Exception sox) {
			HOLogger.instance().log(getClass(), sox);
			if (showErrorMessage) {
				JOptionPane.showMessageDialog(null, surl, "error", JOptionPane.ERROR_MESSAGE);
			}
			return null;

		}
		
		return getResultStream(response);
	}



	/**
	 * Post a web file containing body parameters
	 * 
	 * @param surl the full url with parameters
	 * @param bodyprop A hash map of string, string where key is parameter key and value is parameter value
	 * @param showErrorMessage Whether to show message on error or not
	 * @throws IOException 
	 */
	public InputStream postWebFileWithBodyParameters(String surl, HashMap<String, String> bodyParas, boolean showErrorMessage) throws IOException {

		OAuthDialog authDialog = null;
		Response response = null;

		boolean tryAgain = true;
		try {
			while (tryAgain == true) {
				OAuthRequest request = new OAuthRequest(Verb.POST, surl);	

				for (Map.Entry<String, String> entry : bodyParas.entrySet()) {
					request.addBodyParameter(entry.getKey(), entry.getValue());
				}

				infoHO(request);
				request.addHeader("Content-Type", "application/x-www-form-urlencoded");
				request.setConnectionKeepAlive(true);

				m_OAService.signRequest(m_OAAccessToken, request);
				response = request.send();
				if (response.getCode() == 401) {

					if (authDialog == null) {
						authDialog = new OAuthDialog(HOMainFrame.instance(), m_OAService);
					}
					authDialog.setVisible(true);
					// A way out for a user unable to authorize for some reason
					if (authDialog.getUserCancel() == true) {
						return null;
					}

					m_OAAccessToken = authDialog.getAccessToken();

					// Try again...
				} else {				
					tryAgain = false;

					// We are done!
				}	
			}
		} catch (Exception sox) {
			HOLogger.instance().log(getClass(), sox);
			if (showErrorMessage) {
				JOptionPane.showMessageDialog(null, surl, "error", JOptionPane.ERROR_MESSAGE);
			}
			return null;

		}

		return getResultStream(response);
	}


	private InputStream getResultStream(Response response) throws IOException{
		
		if (response == null) { 
			return null;
		}
		InputStream resultingInputStream;
		String encoding = response.getHeader("Content-Encoding");
		if ((encoding != null) && encoding.equalsIgnoreCase("gzip")) {
			resultingInputStream = new GZIPInputStream(response.getStream());
			HOLogger.instance().log(getClass(), " Read GZIP.");
		} else if ((encoding != null) && encoding.equalsIgnoreCase("deflate")) {
			resultingInputStream = new InflaterInputStream(response.getStream(), new Inflater(true));
			HOLogger.instance().log(getClass(), " Read Deflated.");
		} else {
			resultingInputStream = response.getStream();
			HOLogger.instance().log(getClass(), " Read Normal.");
		}
		return resultingInputStream;
	}
	
	
	private String readStream(InputStream stream) throws IOException {

		if (stream != null) {
			final BufferedReader bufferedreader =
				new BufferedReader(new InputStreamReader(stream, "UTF-8"));

			final StringBuffer s2 = new StringBuffer();
			String line;

			line = bufferedreader.readLine();

			if (line != null) {
				s2.append(line);

				while ((line = bufferedreader.readLine()) != null) {
					s2.append('\n');
					s2.append(line);
				}
			}
			bufferedreader.close();

			return s2.toString();
		} else {
			return "";
		}

	}

	/////////////////////////////////////////////////////////////////////////////////
	//Identifikation
	////////////////////////////////////////////////////////////////////////////////
	

	private void infoHO(OAuthRequest request) {
		//try
		//        {
		request.addHeader("accept-language", "de");
		request.addHeader("connection", "Keep-Alive");
		request.addHeader("accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, */*");

		request.addHeader("accept-encoding", "gzip, deflate");
		request.addHeader("user-agent", m_sIDENTIFIER);

		//ProxyAuth hier einbinden da diese Funk immer aufgerufen wird
		if (m_bProxyAuthentifactionNeeded) {
			final String pw = m_ProxyUserName + ":" + m_ProxyUserPWD;
			final String epw = (new BASE64Encoder()).encode(pw.getBytes());
			request.addHeader("Proxy-Authorization", "Basic " + epw);
		}
	}

	final public static String getInitialHTConnectionUrl() {
		return htUrl;
	}

	final public int getUserID() {
		return m_iUserID;
	}
}
