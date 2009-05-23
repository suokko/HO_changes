// %1308103607:de.hattrickorganizer.net%
/*
 * MyConnector.java
 *
 * Created on 7. April 2003, 09:36
 */
package de.hattrickorganizer.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sun.misc.BASE64Encoder;
import de.hattrickorganizer.logik.xml.XMLExtensionParser;
import de.hattrickorganizer.logik.xml.XMLNewsParser;
import de.hattrickorganizer.model.Extension;
import de.hattrickorganizer.model.News;
import de.hattrickorganizer.net.rmiHOFriendly.ServerVerweis;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.MyHelper;
import de.hattrickorganizer.tools.xml.XMLManager;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MyConnector implements plugins.IDownloadHelper {
	//~ Static fields/initializers -----------------------------------------------------------------

	static final private int chppID = 55;
	static final private String chppKey = "A4A07F3F-9613-495B-82DC-C5C49903A044";
	static final private String htUrl = "www.hattrick.org";
	//static final private String htUrl = "stage.hattrick.org";
	/** TODO Missing Parameter Documentation */
	public static String m_sIDENTIFIER =
		"HO! Hattrick Organizer V" + de.hattrickorganizer.gui.HOMainFrame.VERSION;
	private static MyConnector m_clInstance;

	//~ Instance fields ----------------------------------------------------------------------------

	private String m_ProxyUserName = "";
	private String m_ProxyUserPWD = "";
	private String m_sCookie;
	private Map<String, String> cookie = new HashMap<String, String>();
	private String m_sProxyHost = "";
	private String m_sProxyPort = "";
	private String m_sUserName = "";
	private String m_sUserPwd = "";
	private boolean m_bAuthenticated;
	private boolean m_bProxyAuthentifactionNeeded;
	private boolean m_bUseProxy;
	private int m_iUserID = -1;

	//~ Constructors -------------------------------------------------------------------------------

	// public static       String          SERVER_NAME         = "195.149.159.156";//"www.hattrick.org";

	/**
	 * Creates a new instance of MyConnector
	 */
	private MyConnector() {
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
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
		String url =
			"http://" + gui.UserParameter.instance().htip + "/common/chppxml.axd?file=arenadetails";

		if (arenaId > 0)
			url += "&arenaID="+arenaId;
		return getPage(url, true);
	}

	/**
	 * um nach einem Fehler zu restten
	 *
	 * @param value TODO Missing Constructuor Parameter Documentation
	 */
	public void setAuthenticated(boolean value) {
		m_bAuthenticated = value;
		m_sCookie = null;
		cookie = new HashMap<String, String>();
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public boolean isAuthenticated() {
		return m_bAuthenticated;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @throws IOException TODO Missing Method Exception Documentation
	 */
	private void getCookie() throws IOException {
		final HttpURLConnection httpurlconnection =
			(HttpURLConnection) (new URL("http://" + gui.UserParameter.instance().htip))
				.openConnection();
		httpurlconnection.setRequestMethod("GET");
		infoHO(httpurlconnection);

		HttpURLConnection.setFollowRedirects(true);
		httpurlconnection.connect();
		extractCookie(httpurlconnection.getHeaderField("set-cookie"));

		httpurlconnection.disconnect();
	}

	private void extractCookie(String v) {
		m_sCookie = v;
		StringTokenizer st = new StringTokenizer(m_sCookie, ";");

		// the specification dictates that the first name/value pair
		// in the string is the cookie name and value, so let's handle
		// them as a special case:
		if (st.hasMoreTokens()) {
			String token = st.nextToken();
			String name = token.substring(0, token.indexOf("="));
			String value = token.substring(token.indexOf("=") + 1, token.length());
			cookie.put(name, value);
		}

		while (st.hasMoreTokens()) {
			String token = st.nextToken();
            if(token.indexOf("=")>0)
			    cookie.put(token.substring(0, token.indexOf("=")).toLowerCase(), token.substring(token.indexOf("=") + 1, token.length()));
            else {
                cookie.put(token, "");
            }
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	//Cookie Funcs
	////////////////////////////////////////////////////////////////////////////////
	public boolean isCookieSet() {
		return m_sCookie != null;
	}

	/**
	 * holt die Finanzen
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getEconomy() throws IOException {
		final String url =
			"http://" + gui.UserParameter.instance().htip + "/common/chppxml.axd?file=economy";

		return getPage(url, true);
	}

	/**
	 * lädt die IP Adress-Seite
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getHattrickIPAdress() throws IOException {
		final String surl = "http://"+htUrl+"/common/chppxml.axd?file=servers";
		final de.hattrickorganizer.logik.xml.XMLMenuParser worker =
			new de.hattrickorganizer.logik.xml.XMLMenuParser();
		final String page = getWebPage(surl, false);

		//tools.Helper.showMessage ( null, page, model.HOVerwaltung.instance().getLanguageString("Fehler"), javax.swing.JOptionPane.ERROR_MESSAGE );
		return worker.parseMenuFromString(page);
	}

	/////////////////////////////////////////////////////////////////////////////////
	//get-XML-Files
	////////////////////////////////////////////////////////////////////////////////

	/**
	 * downloads an xml File from hattrick
	 *
	 * @param file ex. = /common/leagueDetails.asp?outputType=XML&actionType=view
	 *
	 * @return the complete file as String
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getHattrickXMLFile(String file) throws IOException {
		if (!isAuthenticated()) {
			final de.hattrickorganizer.gui.login.LoginDialog ld =
				new de.hattrickorganizer.gui.login.LoginDialog(
					de.hattrickorganizer.gui.HOMainFrame.instance());
			ld.setVisible(true);
		}

		final String url = "http://" + gui.UserParameter.instance().htip + file;

		return getPage(url, true);
	}

	/**
	 * lädt die Tabelle
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getLeagueDetails() throws IOException {
		final String url =
			"http://" + gui.UserParameter.instance().htip + "/common/chppxml.axd?file=leaguedetails";

		return getPage(url, true);
	}

	/**
	 * lädt den Spielplan
	 *
	 * @param season angabe der Saison ( optinal &lt; 1 für aktuelle
	 * @param ligaID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getLeagueFixtures(int season, int ligaID) throws IOException {
		String url =
			"http://" + gui.UserParameter.instance().htip + "/common/chppxml.axd?file=leaguefixtures";

		if (season > 0) {
			url += ("&season=" + season);
		}

		if (ligaID > 0) {
			url += ("&leagueLevelUnitID=" + ligaID);
		}

		return getPage(url, true);
	}

	/**
	 * lädt das MatchArchiv als xml
	 *
	 * @param teamId TODO Missing Constructuor Parameter Documentation
	 * @param firstDate TODO Missing Constructuor Parameter Documentation
	 * @param lastDate TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getMatchArchiv(int teamId, String firstDate, String lastDate)
		throws IOException {
		String url =
			"http://" + gui.UserParameter.instance().htip + "/chppxml.axd?file=matchesarchive";

		if (teamId > 0) {
			url += ("&teamID=" + teamId);
		}

		if (firstDate != null) {
			url += ("&FirstMatchDate=" + firstDate);
		}

		if (lastDate != null) {
			url += ("&LastMatchDate=" + lastDate);
		}

		return getPage(url, true);
	}

	/**
	 * lädt den Die Aufstellungsbewertung zu einem Spiel
	 *
	 * @param matchId angabe der Saison ( optinal &lt; 1 für aktuelle
	 * @param teamId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getMatchLineup(int matchId, int teamId) throws IOException {
		String url =
			"http://" + gui.UserParameter.instance().htip + "/chppxml.axd?file=matchlineup";

		if (matchId > 0) {
			url += ("&matchID=" + matchId);
		}

		if (teamId > 0) {
			url += ("&teamID=" + teamId);
		}

		return getPage(url, true);
	}

	/**
	 * lädt den Die Aufstellung zu einem Spiel
	 *
	 * @param matchId angabe der Saison ( optinal &lt; 1 für aktuelle
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getMatchOrder(int matchId) throws IOException {
		String url =
			"http://"+ gui.UserParameter.instance().htip + "/common/chppxml.axd?file=matchorders&matchID="+matchId+"&isYouth=false";

		return getPage(url, true);
	}

	/**
	 * lädt den Die Aufstellungsbewertung zu einem Spiel
	 *
	 * @param matchId angabe der Saison ( optinal &lt; 1 für aktuelle
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getMatchdetails(int matchId) throws IOException {
		String url =
			"http://" + gui.UserParameter.instance().htip + "/chppxml.axd?file=matchdetails";
		if (matchId > 0) {
			url += ("&matchID=" + matchId);
		}
		url += "&matchEvents=true";

		return getPage(url, true);
	}

	/**
	 * lädt die matches.asp seite als xml
	 *
	 * @param teamId TODO Missing Constructuor Parameter Documentation
	 * @param forceRefresh TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getMatchesASP(int teamId, boolean forceRefresh) throws IOException {
		String url =
			"http://" + gui.UserParameter.instance().htip + "/chppxml.axd?file=matches";

		if (teamId > 0) {
			url += ("&teamID=" + teamId);
		}

		if (forceRefresh) {
			url += "&actionType=refreshCache";
		}

		return getPage(url, true);
	}

	/////////////////////////////////////////////////////////////////////////////////
	//internel getPage Funcs
	////////////////////////////////////////////////////////////////////////////////
	public String getPage(String url) throws IOException {
		return getPage(url, false);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param surl TODO Missing Method Parameter Documentation
	 * @param needLogin TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Method Exception Documentation
	 */
	public String getPage(String surl, boolean needLogin) throws IOException {
		if (needLogin && !isAuthenticated()) {
			login();
		}

		if (!isCookieSet()) {
			getCookie();
		}

		String page = getWebPage(surl, true);
		if ( (page!=null) && (page.length()>1) ) {
			return page;
		}

		// Timeout on an Hattrick page
		if (needLogin) {
			login();
			getCookie();
			return getWebPage(surl, true);
		}
		return "";
	}

	/**
	 * holt die Spielerdaten
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getPlayersAsp() throws IOException {
		final String url =
			"http://" + gui.UserParameter.instance().htip + "/common/chppxml.axd?file=players";

		return getPage(url, true);
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
			s = getWebPage("http://tooldesign.ch/ho/index.php?cmd=getServerList", false);
			list = MyHelper.generateStringArray(s, ';');

			if ((s != null) && (!s.trim().equals("")) && (s.length() > 0)) {
				//-1 da letzter Eintrag == "" ist
				server = new ServerVerweis[list.length - 1];
			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"MyConnector.getServerList: Kein Connect zur FriendlyDB" + e);
		}

		//gleich Einträge erstellen
		for (int i = 0; i < (list.length - 1); i++) {
			server[i] = new ServerVerweis(list[i]);
		}

		return server;
	}

	/**
	 * holt die Teamdetails
	 *
	 * @param teamId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getTeamdetails(int teamId) throws IOException {
		String url =
			"http://"+ gui.UserParameter.instance().htip + "/common/chppxml.axd?file=team";
		if (teamId > 0) {
			url += ("&teamID=" + teamId);
		}

		return getPage(url, true);
	}

	/**
	 * holt das Training
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getTraining() throws IOException {
		final String url =
			"http://" + gui.UserParameter.instance().htip + "/common/chppxml.axd?file=training";

		return getPage(url, true);
	}

	/////////////////////////////////////////////////////////////////////////////////
	//get-HTML-Files
	////////////////////////////////////////////////////////////////////////////////
	//    /**
	//     *lädt das HRF
	//     */
	//    public String getHRF() throws IOException
	//    {
	//        String s = "http://" + gui.UserParameter.instance ().htip + "/Common/hrf.asp";
	//        return getPage(s, !isAuthenticated() );
	//    }
	//
	//    public String getMatchReport(String machtId ) throws IOException
	//    {
	//        String s1 = "http://" + gui.UserParameter.instance ().htip + "/Common/matchDetails.asp?matchID=" + machtId + "&site=HT";
	//        String s2 = getPage(s1, true);
	//        s2 = s2.substring(s2.indexOf("</HEAD>") + 8, s2.length());
	//        String s3 = "<LINK REL=STYLESHEET HREF=\"Css/mac.css\" TYPE=\"text/css\">";
	//        s2 = "<HTML><HEAD>" + s3 + "</HEAD>" + s2;
	//
	//        return s2;
	//    }
	//
	//    /**
	//     *holt die html Seite
	//     */
	//    public String getMatchDetails(String machtId) throws Exception
	//    {
	//        String s1 = "http://" + gui.UserParameter.instance ().htip + "/Common/matchDetails.asp?matchID=" + machtId + "&site=HT";
	//        return getPage(s1, true);
	//    }
	//
	//    public String getMatchLineup(String machtId, String teamId)        throws Exception
	//    {
	//        String s2 = "http://" + gui.UserParameter.instance ().htip + "/Common/matchLineup.asp?matchID=" + machtId + "&teamID=" + teamId + "&site=HT";
	//        return getPage(s2, true);
	//    }
	//
	//    public String getAktuellerSpielplan(  String teamId ) throws Exception
	//    {
	//        String s1 = "http://" + gui.UserParameter.instance ().htip + "/Common/matches.asp?action=" + "&teamID=" + teamId + "&site=HT";
	//        return getPage(s1, true);
	//    }
	//
	//    public String getVereinschronik( String teamId ) throws Exception
	//    {
	//        String s1 = "http://" + gui.UserParameter.instance ().htip + "/Common/otherEvents.asp?action=teamHistory" + "&teamID=" + teamId + "&site=HT";
	//        return getPage(s1, true);
	//    }
	//
	//    public String getBisherigeTransfers( String teamId ) throws Exception
	//    {
	//        String s1 = "http://" + gui.UserParameter.instance ().htip + "/Common/transferHistory.asp?action=" + "&teamID=" + teamId + "&site=HT";
	//        return getPage(s1, true);
	//    }
	//
	//    public String getLetzteAufstellung( String teamId ) throws Exception
	//    {
	//        String s1 = "http://" + gui.UserParameter.instance ().htip + "/Common/matchLineup.asp?action=lastMatch" + "&teamID=" + teamId + "&site=HT";
	//        return getPage(s1, true);
	//    }
	//
	//
	//    public String getTabelle( String ligaId ) throws Exception
	//    {
	//        String s1 = "http://" + gui.UserParameter.instance ().htip + "/Common/leagueDetails.asp?LeagueLevelUnitID=" + ligaId + "&site=HT";
	//        return getPage(s1, true);
	//    }

	/**
	 * lädt die Tabelle
	 *
	 * @param playerID TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getTransferCompare(int playerID) throws IOException {
		if (!isAuthenticated()) {
			final de.hattrickorganizer.gui.login.LoginDialog ld =
				new de.hattrickorganizer.gui.login.LoginDialog(
					de.hattrickorganizer.gui.HOMainFrame.instance());
			ld.setVisible(true);
		}

		String url =
			"http://" + gui.UserParameter.instance().htip + "/common/chppxml.axd?file=playerdetails&playerID="+playerID;

		return getPage(url, true);
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
	 * Setter for property m_sUserName.
	 *
	 * @param m_sUserName New value of property m_sUserName.
	 */
	public void setUserName(java.lang.String m_sUserName) {
		this.m_sUserName = m_sUserName;
	}

	/**
	 * Getter for property m_sUserName.
	 *
	 * @return Value of property m_sUserName.
	 */
	public java.lang.String getUserName() {
		return m_sUserName;
	}

	/**
	 * Setter for property m_sUserPwd.
	 *
	 * @param m_sUserPwd New value of property m_sUserPwd.
	 */
	public void setUserPwd(java.lang.String m_sUserPwd) {
		this.m_sUserPwd = m_sUserPwd;
	}

	/**
	 * Getter for property m_sUserPwd.
	 *
	 * @return Value of property m_sUserPwd.
	 */
	public java.lang.String getUserPwd() {
		return m_sUserPwd;
	}

	/**
	 * holt die Vereinsdaten
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getVerein() throws IOException {
		final String url =
			"http://" + gui.UserParameter.instance().htip + "/common/chppxml.axd?file=club";
		return getPage(url, true);
	}

	public String getWebPage(String surl, boolean needCookie) throws IOException {
		return getWebPage(surl, needCookie, true, false); // show connect error
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param surl TODO Missing Method Parameter Documentation
	 * @param needCookie TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Method Exception Documentation
	 */
	public String getWebPage(String surl, boolean needCookie, boolean showError, boolean shortTimeOut) throws IOException {
		final InputStream resultingInputStream = getWebFile(surl, needCookie, showError, shortTimeOut);

		if (resultingInputStream != null) {
			final BufferedReader bufferedreader =
				new BufferedReader(new InputStreamReader(resultingInputStream, "UTF-8"));

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

	/**
	 * holt die Weltdaten
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Constructuor Exception Documentation
	 */
	public String getWorldDetails() throws IOException {
		final String url =
			"http://" + gui.UserParameter.instance().htip + "/common/chppxml.axd?file=worlddetails";

		return getPage(url, true);
	}

	/////////////////////////////////////////////////////////////////////////////////
	//Update Checker
	////////////////////////////////////////////////////////////////////////////////
	public double getLatestVersion() {
		try {
			final String s =
				getWebPage(MyConnector.getPluginSite()+"/version.htm", false, false, true);
			double d = de.hattrickorganizer.gui.HOMainFrame.VERSION;

			try {
				d = Double.parseDouble(s);
			} catch (NumberFormatException e) {
			}
			return d;
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"Unable to connect to the update server (HO): " + e);
			return de.hattrickorganizer.gui.HOMainFrame.VERSION;
		}
	}

	public Extension getEpvVersion() {
		try {
			final String s =
				getWebPage(MyConnector.getResourceSite()+"/downloads/epv.xml", false);

			return (new XMLExtensionParser()).parseExtension(s);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"Unable to connect to the update server (EPV): " + e);
			return new Extension();
		}
	}

	public Extension getRatingsVersion() {
		try {
			final String s =
				getWebPage(MyConnector.getResourceSite()+"/downloads/ratings.xml", false);

			return (new XMLExtensionParser()).parseExtension(s);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"Unable to connect to the update server (Ratings): " + e);
			return new Extension();
		}
	}

	public News getLatestNews() {
		try {
			final String s = MyConnector.instance().getWebPage(MyConnector.getResourceSite()+"/downloads/news.xml", false, false, true);
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
		//for test
		//boolean proxyAuth = false;
		///Proxy Quark
		if (m_bUseProxy) {
			System.getProperties().put("http.proxyHost", m_sProxyHost);
			System.getProperties().put("http.proxyPort", m_sProxyPort);
		} else {
			System.getProperties().remove("http.proxyHost");
			System.getProperties().remove("http.proxyPort");
		}
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param teamID TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public String fetchRegionID(int teamID) {
		String xmlFile = "";

		try {
			if (!isAuthenticated()) {
				final de.hattrickorganizer.gui.login.LoginDialog ld =
					new de.hattrickorganizer.gui.login.LoginDialog(
						de.hattrickorganizer.gui.HOMainFrame.instance());
				ld.setVisible(true);
			}
			xmlFile = "http://" + gui.UserParameter.instance().htip + "/common/chppxml.axd?file=team&teamID=" + teamID;
			xmlFile = getPage(xmlFile, true);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
			return "-1";
		}

		return new de.hattrickorganizer.logik.xml.xmlTeamDetailsParser().fetchRegionID(xmlFile);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public boolean hasSecLogin() {
		String url =
			"http://"
				+ gui.UserParameter.instance().htip
				+ "/common/chppxml.axd?file=login&actionType=checksecuritycode&chppID="+chppID
				+ "&chppKey="+chppKey+"&loginname="+m_sUserName;
		boolean checkOK = true;

		try {
			final Document doc = XMLManager.instance().parseString(getPage(url, false));
			Element ele = null;
			Element tmpEle = null;

			//get Root element :
			ele = doc.getDocumentElement();

			//get specific sub element of root element
			tmpEle = (Element) ele.getElementsByTagName("HasSecurityCode").item(0);

			//get it's value
			String value = XMLManager.instance().getFirstChildNodeValue(tmpEle);
			checkOK = value.trim().equalsIgnoreCase("True");

			//get specific sub element of team element
			//tmpEle = (Element) ele.getElementsByTagName("ActionSuccessful").item(0);
			//tmpEle = (Element) ele.getElementsByTagName("IsAuthenticated").item(0);

			//04.09.2008 aik: diabled the 2nd check, IsAuthenticated is always False upon initial check
			//get it's value
			//value = XMLManager.instance().getFirstChildNodeValue(tmpEle);
			//checkOK = checkOK && value.trim().equalsIgnoreCase("True");
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),e);
			checkOK = false;
		};
		return checkOK;
	}

	/////////////////////////////////////////////////////////////////////////////////
	//HT-LOGIN / Logout
	////////////////////////////////////////////////////////////////////////////////
	public boolean login() throws IOException {
		String page = "";
		boolean loggedIn = false;

		if ((m_sUserPwd == null) || (m_sUserPwd.length() == 0)) {
			throw new IOException("Password not set");
		}

		if (!isCookieSet()) {
			getCookie();
		}

		final HttpURLConnection httpurlconnection =
			(HttpURLConnection) (new URL("http://" + gui.UserParameter.instance().htip
				+ "/common/chppxml.axd?file=login&actionType=login&loginname=" + m_sUserName
				+ "&readonlypassword=" + m_sUserPwd + "&chppID=" + chppID + "&chppKey=" + chppKey)).openConnection();

		httpurlconnection.setRequestMethod("GET");

		httpurlconnection.setRequestProperty("cookie", getCookieString());

		infoHO(httpurlconnection);

		HttpURLConnection.setFollowRedirects(true);
		httpurlconnection.connect();

		String s2 = httpurlconnection.getHeaderField("set-cookie");

		if ((s2 != null) && !s2.equals(m_sCookie)) {
			//HOLogger.instance().log(getClass()," got a new Cookie: " + s2);
			extractCookie(s2);
			//m_sCookie = s2;

		}

		final String encoding = httpurlconnection.getContentEncoding();
		InputStream resultingInputStream = null;

		//create the appropriate stream wrapper based on
		//the encoding type
		if ((encoding != null) && encoding.equalsIgnoreCase("gzip")) {
			resultingInputStream =
				new java.util.zip.GZIPInputStream(httpurlconnection.getInputStream());
			HOLogger.instance().log(getClass()," Read GZIP.");
		} else if ((encoding != null) && encoding.equalsIgnoreCase("deflate")) {
			resultingInputStream =
				new java.util.zip.InflaterInputStream(
					httpurlconnection.getInputStream(),
					new java.util.zip.Inflater(true));
			HOLogger.instance().log(getClass()," Read Deflated.");
		} else {
			resultingInputStream = httpurlconnection.getInputStream();
			HOLogger.instance().log(getClass()," Read Normal.");
		}

		if (resultingInputStream != null) {
			final BufferedReader bufferedreader =
				new BufferedReader(new InputStreamReader(resultingInputStream, "UTF-8"));

			final StringBuffer sb2 = new StringBuffer();
			String line;

			line = bufferedreader.readLine();

			if (line != null) {
				sb2.append(line);

				while ((line = bufferedreader.readLine()) != null) {
					sb2.append('\n');
					sb2.append(line);
				}
			}

			bufferedreader.close();
			page = sb2.toString();
		}

		httpurlconnection.disconnect();

		if (page.length() > 0) {
			//xml auswerten
			try {
				final Document doc = XMLManager.instance().parseString(page);
				Element ele = null;
				Element tmpEle = null;

				//get Root element :
				ele = doc.getDocumentElement();

				//get specific sub element of root element
				tmpEle = (Element) ele.getElementsByTagName("IsAuthenticated").item(0);

				//get it's value
				String value = XMLManager.instance().getFirstChildNodeValue(tmpEle);
				loggedIn = value.trim().equalsIgnoreCase("True");

				tmpEle = (Element) ele.getElementsByTagName("LoginResult").item(0);
				value = XMLManager.instance().getFirstChildNodeValue(tmpEle);
				loggedIn = loggedIn && "0".equals(value.trim());

				tmpEle = (Element) ele.getElementsByTagName("UserID").item(0);

				//get it's value
				value = XMLManager.instance().getFirstChildNodeValue(tmpEle);

				try {
					m_iUserID = Integer.parseInt(value);
				} catch (NumberFormatException nfe) {
				}
			} catch (Exception e) {
				HOLogger.instance().log(getClass(),e);
				loggedIn = false;
			};
		}

		m_bAuthenticated = loggedIn;

		return loggedIn;
	}

	private String getCookieString() {
		StringBuffer cookieStringBuffer = new StringBuffer();
		Iterator<String> cookieNames = cookie.keySet().iterator();
		while (cookieNames.hasNext()) {
			String cookieName = cookieNames.next();
			cookieStringBuffer.append(cookieName);
			cookieStringBuffer.append("=");
			cookieStringBuffer.append((String) cookie.get(cookieName));
			if (cookieNames.hasNext())
				cookieStringBuffer.append(";");
		}
		return cookieStringBuffer.toString();
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @throws IOException TODO Missing Method Exception Documentation
	 */
	public void logout() throws IOException {
		try {
			getPage(
				"http://" + gui.UserParameter.instance().htip + "/common/chppxml.axd?file=login&action=logout",
				true);
			m_bAuthenticated = false;
			m_sCookie = null;
			cookie = new HashMap<String, String>();
		} catch (IOException ioexception) {
			m_bAuthenticated = false;
			m_sCookie = null;
			cookie = new HashMap<String, String>();
			throw ioexception;
		}
	}

	public InputStream getFileFromWeb(String url, boolean displaysettingsScreen) throws IOException {
		return getFileFromWeb(url, displaysettingsScreen, false);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param url TODO Missing Method Parameter Documentation
	 * @param displaysettingsScreen TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Method Exception Documentation
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

		return getWebFile(url, false, showErrorMessage, false);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param url TODO Missing Method Parameter Documentation
	 * @param displaysettingsScreen TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 *
	 * @throws IOException TODO Missing Method Exception Documentation
	 */
	public String getUsalWebPage(String url, boolean displaysettingsScreen) throws IOException {
		if (displaysettingsScreen) {
			//Show Screen
			final de.hattrickorganizer.gui.login.ProxyDialog proxyDialog =
				new de.hattrickorganizer.gui.login.ProxyDialog(
					de.hattrickorganizer.gui.HOMainFrame.instance());
			proxyDialog.setVisible(true);
		}

		return getWebPage(url, false);
	}

	/////////////////////////////////////////////////////////////////////////////////
	//HO-Friendly Funcs
	////////////////////////////////////////////////////////////////////////////////

	/**
	 * registriert einen HO! Friendly Server im Internet
	 *
	 * @param ipAdress TODO Missing Constructuor Parameter Documentation
	 * @param port TODO Missing Constructuor Parameter Documentation
	 * @param info TODO Missing Constructuor Parameter Documentation
	 *
	 * @return MatchId, -1 == Error
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
			result = getWebPage(request, false);

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
	 *
	 * @param matchId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public boolean sendAlive(int matchId) {
		try {
			final String s =
				getWebPage("http://tooldesign.ch/ho/index.php?cmd=keepAlive&id=" + matchId, false);

			if (MyHelper.parseDate(s) == null) {
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
	 *
	 * @param sb TODO Missing Constructuor Parameter Documentation
	 * @param isServer TODO Missing Constructuor Parameter Documentation
	 *
	 * @return Array-Liste mit ServerVerweis Objekten
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
			s = getWebPage(request, false);

			//HOLogger.instance().log(getClass(), s );
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),"Send Fehlgeschlagen" + e);
		}

		return (s.trim().equalsIgnoreCase("True"));
	}

	/**
	 * Informiert die DB das der Server nicht mehr zur verfügung steht
	 *
	 * @param matchId TODO Missing Constructuor Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public boolean unregisterServer(int matchId) {
		try {
			final String s =
				getWebPage(
					"http://tooldesign.ch/ho/index.php?cmd=unregisterServer&id=" + matchId,
					false);

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
	private InputStream getWebFile(String surl, boolean needCookie, boolean showErrorMessage, boolean shortTimeOut) throws IOException {
		final URL url = new URL(surl);
		final HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
		httpurlconnection.setRequestMethod("GET");
		infoHO(httpurlconnection);

		if (needCookie) {
			httpurlconnection.setRequestProperty("cookie", getCookieString());
		}

		try {
			if (shortTimeOut) {
				httpurlconnection.setConnectTimeout(7500);
				httpurlconnection.setReadTimeout(8500);
			} else {
				httpurlconnection.setConnectTimeout(45000);
				httpurlconnection.setReadTimeout(60000);
			}
			httpurlconnection.connect();
		} catch (Exception sox) {
			HOLogger.instance().log(getClass(),sox);
			if (showErrorMessage) {
				JOptionPane.showMessageDialog( null, surl, "error", JOptionPane.ERROR_MESSAGE);
			}
			return null;
		}

		if (needCookie) {
			String s1 = httpurlconnection.getHeaderField("set-cookie");

			if ((s1 != null) && !s1.equals(m_sCookie)) {
				HOLogger.instance().log(getClass()," got a new Cookie: " + s1);
				extractCookie(s1);
			}
		}

		final String encoding = httpurlconnection.getContentEncoding();
		InputStream resultingInputStream = null;

		//create the appropriate stream wrapper based on
		//the encoding type
		if ((encoding != null) && encoding.equalsIgnoreCase("gzip")) {
			resultingInputStream = new GZIPInputStream(httpurlconnection.getInputStream());
			HOLogger.instance().log(getClass()," Read GZIP.");
		} else if ((encoding != null) && encoding.equalsIgnoreCase("deflate")) {
			resultingInputStream =
				new InflaterInputStream(httpurlconnection.getInputStream(),	new Inflater(true));
			HOLogger.instance().log(getClass()," Read Deflated.");
		} else {
			resultingInputStream = httpurlconnection.getInputStream();
			HOLogger.instance().log(getClass()," Read Normal.");
		}

		return resultingInputStream;
	}

	/////////////////////////////////////////////////////////////////////////////////
	//Identifikation
	////////////////////////////////////////////////////////////////////////////////
	private void infoHO(URLConnection httpurlconnection) {
		//try
		//        {
		httpurlconnection.setRequestProperty("accept-language", "de");
		httpurlconnection.setRequestProperty("connection", "Keep-Alive");
		httpurlconnection.setRequestProperty("accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, */*");

		//httpurlconnection.setRequestProperty("accept-encoding", "compress, gzip");
		httpurlconnection.setRequestProperty("accept-encoding", "gzip, deflate");
		httpurlconnection.setRequestProperty("user-agent", m_sIDENTIFIER);

		//Host einfach übernehmen
		httpurlconnection.setRequestProperty("host", httpurlconnection.getURL().getHost());

		//ProxyAuth hier einbinden da diese Funk immer aufgerufen wird
		if (m_bProxyAuthentifactionNeeded) {
			final String pw = m_ProxyUserName + ":" + m_ProxyUserPWD;
			final String epw = (new BASE64Encoder()).encode(pw.getBytes());
			httpurlconnection.setRequestProperty("Proxy-Authorization", "Basic " + epw);
		}
	}

	final public static String getInitialHTConnectionUrl() {
		return htUrl;
	}

	final public int getUserID() {
		return m_iUserID;
	}
}
