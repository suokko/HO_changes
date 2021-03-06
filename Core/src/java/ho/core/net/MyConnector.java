package ho.core.net;

import ho.HO;
import ho.core.file.xml.Extension;
import ho.core.file.xml.XMLCHPPPreParser;
import ho.core.file.xml.XMLExtensionParser;
import ho.core.file.xml.XMLNewsParser;
import ho.core.file.xml.XMLTeamDetailsParser;
import ho.core.gui.CursorToolkit;
import ho.core.gui.HOMainFrame;
import ho.core.model.HOVerwaltung;
import ho.core.model.News;
import ho.core.model.UserParameter;
import ho.core.model.match.MatchType;
import ho.core.net.login.OAuthDialog;
import ho.core.net.login.ProxyDialog;
import ho.core.net.login.ProxySettings;
import ho.core.util.HOLogger;
import ho.core.util.Helper;
import ho.core.util.IOUtils;
import ho.core.util.StringUtils;
import ho.core.util.XMLUtils;
import ho.tool.updater.VersionInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
import org.w3c.dom.Document;

import sun.misc.BASE64Encoder;

/**
 * @author thomas.werth
 */
public class MyConnector {
	private final static SimpleDateFormat HT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final int chppID = 3330;
	private static final String htUrl = "http://chpp.hattrick.org/chppxml.ashx";
	public static String m_sIDENTIFIER = "HO! Hattrick Organizer V" + HO.VERSION;
	private static MyConnector m_clInstance;
	private final static String VERSION_TRAINING = "1.5";
	private final static String VERSION_MATCHORDERS = "2.1";
	private final static String VERSION_MATCHLINEUP = "1.8";
	private final static String VERSION_MATCHDETAILS = "2.3";
	private final static String VERSION_PLAYERS = "2.1";
	private final static String VERSION_PLAYERDETAILS = "2.0";
	private final static String VERSION_WORLDDETAILS = "1.4";
	private final static String CONSUMER_KEY = ">Ij-pDTDpCq+TDrKA^nnE9";
	private final static String CONSUMER_SECRET = "2/Td)Cprd/?q`nAbkAL//F+eGD@KnnCc>)dQgtP,p+p";
	private ProxySettings proxySettings;
	private boolean m_bAuthenticated;
	private OAuthService m_OAService;
	private Token m_OAAccessToken;
	private static boolean DEBUGSAVE = false;

	/**
	 * Creates a new instance of MyConnector.
	 */
	private MyConnector() {
		m_OAService = new ServiceBuilder().provider(HattrickAPI.class)
				.apiKey(Helper.decryptString(CONSUMER_KEY))
				.apiSecret(Helper.decryptString(CONSUMER_SECRET))
				.signatureType(SignatureType.Header).build();
		m_OAAccessToken = createOAAccessToken();
	}

	/**
	 * Get the MyConnector instance.
	 */
	public static MyConnector instance() {
		if (m_clInstance == null) {
			m_clInstance = new MyConnector();
		}
		return m_clInstance;
	}

	/**
	 * Sets the DEBUGSAVE flag. Setting the flag to true will save downloaded
	 * CHPP files.
	 * 
	 * @param debugSave
	 *            true to save downloaded CHPP files, false otherwise.
	 */
	public static void setDebugSave(boolean debugSave) {
		DEBUGSAVE = debugSave;
	}

	public static String getResourceSite() {
		return getPluginSite();
	}

	public static String getHOSite() {
		return "http://ho1.sourceforge.net/";
	}

	public static String getPluginSite() {
		return getHOSite() + "onlinefiles";
	}

	public static String getBetaSite() {
		return getHOSite() + "development";
	}

	public static String getFinalSite() {
		return getHOSite() + "final";
	}

	public static String getInitialHTConnectionUrl() {
		return htUrl;
	}

	/**
	 * Fetch our arena
	 * 
	 * @return arena xml
	 * 
	 * @throws IOException
	 */
	public String getArena() throws IOException {
		return getArena(-1);
	}

	/**
	 * Fetch a specific arena
	 * 
	 * @param arenaId
	 *            id of the arena to fetch (-1 = our arena)
	 * @return arena xml
	 * 
	 * @throws IOException
	 */
	public String getArena(int arenaId) throws IOException {
		String url = htUrl + "?file=arenadetails";
		if (arenaId > 0) {
			url += "&arenaID=" + arenaId;
		}
		return getCHPPWebFile(url);
	}

	/**
	 * holt die Finanzen
	 */
	public String getEconomy() throws IOException {
		String url = htUrl + "?file=economy";
		return getCHPPWebFile(url);
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// get-XML-Files
	// //////////////////////////////////////////////////////////////////////////////

	/**
	 * downloads an xml File from hattrick Behavior has changed with oauth, but
	 * we try to convert old syntaxes.
	 * 
	 * @param file
	 *            ex. = "?file=leaguedetails&[leagueLevelUnitID = integer]"
	 * 
	 * @return the complete file as String
	 */
	public String getHattrickXMLFile(String file) throws IOException {
		String url;

		// An attempt at solving old syntaxes.

		if (file.contains("chppxml.axd")) {
			file = file.substring(file.indexOf("?"));
		} else if (file.contains(".asp")) {
			String s = file.substring(0, file.indexOf("?")).replace(".asp", "")
					.replace("/common/", "");
			file = "?file=" + s + "&" + file.substring(file.indexOf("?") + 1);
		}

		url = htUrl + file;
		return getCHPPWebFile(url);
	}

	/**
	 * lädt die Tabelle
	 */
	public String getLeagueDetails() throws IOException {
		String url = htUrl + "?file=leaguedetails";
		return getCHPPWebFile(url);
	}

	/**
	 * lädt den Spielplan
	 */
	public String getLeagueFixtures(int season, int leagueID) throws IOException {
		String url = htUrl + "?file=leaguefixtures";
		if (season > 0) {
			url += "&season=" + season;
		}
		if (leagueID > 0) {
			url += "&leagueLevelUnitID=" + leagueID;
		}
		return getCHPPWebFile(url);
	}

	/**
	 * Fetches matches from Hattrick's matches archive (see 'matchesarchive' in
	 * Hattrick's CHPP API documentation) for the given team and a specified
	 * period of time.
	 * 
	 * @param teamId
	 *            the ID of the team to fetch the matches for.
	 * @param firstDate
	 *            the first date of the period of time.
	 * @param lastDate
	 *            the last date of the period of time.
	 * @return the a string containing the matches data in XML format.
	 * @throws IOException
	 *             if an io-error occurred when fetching the matches.
	 */
	public String getMatchesArchive(int teamId, Date firstDate, Date lastDate) throws IOException {
		StringBuilder url = new StringBuilder();
		url.append(htUrl).append("?file=matchesarchive");

		if (teamId > 0) {
			url.append("&teamID=").append(teamId);
		}

		if (firstDate != null) {
			url.append("&FirstMatchDate=").append(HT_FORMAT.format(firstDate));
		}

		if (lastDate != null) {
			url.append("&LastMatchDate=").append(HT_FORMAT.format(lastDate));
		}

		return getCHPPWebFile(url.toString());
	}

	/**
	 * lädt die Aufstellungsbewertung zu einem Spiel
	 */
	public String getMatchLineup(int matchId, int teamId, MatchType matchType) throws IOException {
		String url = htUrl + "?file=matchlineup&version=" + VERSION_MATCHLINEUP;

		if (matchId > 0) {
			url += ("&matchID=" + matchId);
		}

		if (teamId > 0) {
			url += ("&teamID=" + teamId);
		}

		url += "&sourceSystem=" + matchType.getSourceString();

		return getCHPPWebFile(url);
	}

	/**
	 * Fetches the match order xml from Hattrick
	 * 
	 * @param matchId
	 *            The match id to fetch the lineup for
	 * @param matchType
	 *            The match type connected to the match
	 * @return The api content (xml)
	 * @throws IOException
	 */
	public String getMatchOrder(int matchId, MatchType matchType) throws IOException {
		String url = htUrl + "?file=matchorders&version=" + VERSION_MATCHORDERS + "&matchID="
				+ matchId;
		url += "&sourceSystem=" + matchType.getSourceString();

		return getCHPPWebFile(url);
	}

	/**
	 * Sets the match order with the provided content to the provided match.
	 * 
	 * @param matchId
	 *            The match id to upload the order to
	 * @param matchType
	 *            The match type of the match to upload the order to
	 * @param orderString
	 *            The string with the actual orders. See the CHPP API
	 *            documentation.
	 * @return the result xml from the upload
	 * @throws IOException
	 */
	public String setMatchOrder(int matchId, MatchType matchType, String orderString)
			throws IOException {
		StringBuilder urlpara = new StringBuilder();
		urlpara.append("?file=matchorders&version=").append(VERSION_MATCHORDERS);
		if (matchId > 0) {
			urlpara.append("&matchID=").append(matchId);
		}
		urlpara.append("&actionType=setmatchorder");

		urlpara.append("&sourceSystem=" + matchType.getSourceString());

		Map<String, String> paras = new HashMap<String, String>();
		paras.put("lineup", orderString);
		String result = readStream(postWebFileWithBodyParameters(htUrl + urlpara, paras, true,
				"set_matchorder"));
		String sError = XMLCHPPPreParser.getError(result);
		if (sError.length() > 0) {
			throw new RuntimeException(sError);
		}
		return result;
	}

	/**
	 * lädt die Aufstellungsbewertung zu einem Spiel
	 */
	public String getMatchdetails(int matchId, MatchType matchType) throws IOException {
		String url = htUrl + "?file=matchdetails&version=" + VERSION_MATCHDETAILS;
		if (matchId > 0) {
			url += ("&matchID=" + matchId);
		}
		url += "&sourceSystem=" + matchType.getSourceString();
		url += "&matchEvents=true";
		return getCHPPWebFile(url);
	}

	/**
	 * Gets the most recent and upcoming matches for a given teamId and up to a
	 * specific date.
	 * 
	 * @param teamId
	 *            the id of the team.
	 * @param forceRefresh
	 *            <code>true</code> if cache should be refreshed,
	 *            <code>false</code> otherwise.
	 * @param date
	 *            last date (+time) to get matches to.
	 * @return a string containing the xml data for the downloaded matches (to
	 *         be used for MatchKurzInfo).
	 * @throws IOException
	 *             if an IO error occurs during download.
	 */
	public String getMatches(int teamId, boolean forceRefresh, Date date) throws IOException {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(htUrl).append("?file=matches&version=2.6");
		urlBuilder.append("&teamID=").append(teamId);
		if (forceRefresh) {
			urlBuilder.append("&actionType=refreshCache");
		}
		urlBuilder.append("&LastMatchDate=");
		String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		urlBuilder.append(URLEncoder.encode(dateString, "UTF-8"));
		return getCHPPWebFile(urlBuilder.toString());
	}

	/**
	 * Get Matches
	 */
	public String getMatches(int teamId, boolean forceRefresh, boolean upcoming) throws IOException {
		String url = htUrl + "?file=matches&version=2.6";

		if (teamId > 0) {
			url += "&teamID=" + teamId;
		}
		if (forceRefresh) {
			url += "&actionType=refreshCache";
		}

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(System.currentTimeMillis());
		if (upcoming) {
			cal.add(java.util.Calendar.MONTH, 5);
		}
		// Paranoia against inaccurate system clock.
		cal.add(java.util.Calendar.DAY_OF_MONTH, 1);

		url += "&LastMatchDate=";
		url += new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

		return getCHPPWebFile(url);
	}

	/**
	 * Get Players
	 */
	public String getPlayers() throws IOException {
		String url = htUrl + "?file=players&version=" + VERSION_PLAYERS;
		return getCHPPWebFile(url);
	}

	/**
	 * holt die Teamdetails
	 */
	public String getTeamdetails(int teamId) throws IOException {
		String url = htUrl + "?file=teamdetails";
		if (teamId > 0) {
			url += ("&teamID=" + teamId);
		}

		return getCHPPWebFile(url);
	}

	/**
	 * Get the training XML data.
	 */
	public String getTraining() throws IOException {
		final String url = htUrl + "?file=training&version=" + VERSION_TRAINING;

		return getCHPPWebFile(url);
	}

	/**
	 * Get the transfer data for a player
	 */
	public String getTransfersForPlayer(int playerId) {
		final String url = htUrl + "?file=transfersPlayer&playerID=" + playerId;

		return getCHPPWebFile(url);
	}

	/**
	 * holt die Vereinsdaten
	 */
	public String getVerein() throws IOException {
		final String url = htUrl + "?file=club";
		return getCHPPWebFile(url);
	}

	/**
	 * Get the content of a web page in one string.
	 */
	private String getWebPage(String surl, boolean showError) throws IOException {
		final InputStream resultingInputStream = getNonCHPPWebFile(surl, showError);
		return readStream(resultingInputStream);
	}

	/**
	 * holt die Weltdaten
	 */
	public String getWorldDetails(int leagueId) throws IOException {
		String url = htUrl + "?file=worlddetails&version=" + VERSION_WORLDDETAILS;
		if (leagueId > 0)
			url += "&leagueID=" + leagueId;
		return getCHPPWebFile(url);
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// Update Checker
	// //////////////////////////////////////////////////////////////////////////////
	public VersionInfo getLatestVersion() {
		VersionInfo ret = new VersionInfo();
		ret.setBeta(false);
		ret.setVersion(HO.VERSION);
		try {
			final String s = getWebPage(MyConnector.getPluginSite() + "/version.htm", false);
			try {
				ret.setVersion(Double.parseDouble(s));
			} catch (NumberFormatException e) {
				HOLogger.instance().debug(getClass(), "Error parsing version '" + s + "': " + e);
			}
		} catch (Exception e) {
			HOLogger.instance()
					.log(getClass(), "Unable to connect to the update server (HO): " + e);
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
			is = getNonCHPPWebFile(MyConnector.getBetaSite() + "/betaversion.htm", false);
			if (is != null) {
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				VersionInfo ret = new VersionInfo();
				String line;

				while ((line = br.readLine()) != null) {
					int pos = line.indexOf("=");
					if (pos > 0) {
						String key = line.substring(0, pos).trim();
						String val = line.substring(pos + 1).trim();
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
			HOLogger.instance()
					.log(getClass(), "Unable to connect to the update server (HO): " + e);
		} finally {
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(is);
		}
		return null;
	}

	public Extension getEpvVersion() {
		try {
			String s = getWebPage(MyConnector.getResourceSite() + "/downloads/epv.xml", false);
			return XMLExtensionParser.parseExtension(s);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),
					"Unable to connect to the update server (EPV): " + e);
			return new Extension();
		}
	}

	public Extension getRatingsVersion() {
		try {
			String s = getWebPage(MyConnector.getResourceSite() + "/downloads/ratings.xml", false);
			return XMLExtensionParser.parseExtension(s);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),
					"Unable to connect to the update server (Ratings): " + e);
			return new Extension();
		}
	}

	public News getLatestNews() {
		try {
			final String s = MyConnector.instance().getWebPage(
					MyConnector.getResourceSite() + "/downloads/news.xml", false);
			XMLNewsParser parser = new XMLNewsParser();
			return parser.parseNews(s);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(),
					"Unable to connect to the update server (News): " + e);
			return new News();
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// Proxy
	// //////////////////////////////////////////////////////////////////////////////
	public void enableProxy(ProxySettings proxySettings) {
		this.proxySettings = proxySettings;
		if (this.proxySettings != null && this.proxySettings.isUseProxy()) {
			System.getProperties().setProperty("https.proxyHost", proxySettings.getProxyHost());
			System.getProperties().setProperty("https.proxyPort",
					String.valueOf(proxySettings.getProxyPort()));
			System.getProperties().setProperty("http.proxyHost", proxySettings.getProxyHost());
			System.getProperties().setProperty("http.proxyPort",
					String.valueOf(proxySettings.getProxyPort()));
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
			xmlFile = htUrl + "?file=teamdetails&teamID=" + teamID;
			xmlFile = getCHPPWebFile(xmlFile);
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), e);
			return "-1";
		}

		return XMLTeamDetailsParser.fetchRegionID(xmlFile);
	}

	public InputStream getFileFromWeb(String url, boolean displaysettingsScreen) throws IOException {
		if (displaysettingsScreen) {
			// Show Screen
			new ProxyDialog(HOMainFrame.instance());
		}
		return getNonCHPPWebFile(url, false);
	}

	public String getUsalWebPage(String url, boolean displaysettingsScreen) throws IOException {
		if (displaysettingsScreen) {
			// Show Proxy Screen
			new ProxyDialog(HOMainFrame.instance());
		}

		return getWebPage(url, true);
	}

	/**
	 * Get a web page using a URLconnection.
	 */
	private String getCHPPWebFile(String surl) {
		String returnString = "";
		OAuthDialog authDialog = null;
		Response response = null;
		int iResponse = 200;
		boolean tryAgain = true;
		try {
			while (tryAgain == true) {
				OAuthRequest request = new OAuthRequest(Verb.GET, surl);
				infoHO(request);
				if (m_OAAccessToken == null || m_OAAccessToken.getToken().length() == 0) {
					iResponse = 401;
				} else {
					m_OAService.signRequest(m_OAAccessToken, request);
					response = request.send();
					iResponse = response.getCode();
				}
				switch (iResponse) {
				case 200:
				case 201:
					// We are done!
					returnString = readStream(getResultStream(response));
					if (DEBUGSAVE) {
						saveCHPP(surl, returnString);
					}
					String sError = XMLCHPPPreParser.getError(returnString);
					if (sError.length() > 0) {
						throw new RuntimeException(sError);
					}
					tryAgain = false;
					break;
				case 401:
					if (authDialog == null) {
						// disable WaitCursor to unblock GUI
						CursorToolkit.stopWaitCursor(HOMainFrame.instance().getRootPane());
						authDialog = new OAuthDialog(HOMainFrame.instance(), m_OAService, "");
					}
					authDialog.setVisible(true);
					// A way out for a user unable to authorize for some reason
					if (authDialog.getUserCancel() == true) {
						return null;
					}
					m_OAAccessToken = authDialog.getAccessToken();
					if (m_OAAccessToken == null) {
						m_OAAccessToken = createOAAccessToken();
					}
					break;
				case 407:
					throw new RuntimeException(
							"HTTP Response Code 407: Proxy authentication required.");
				default:
					throw new RuntimeException("HTTP Response Code: " + iResponse);
				}
			}
		} catch (Exception sox) {
			HOLogger.instance().error(getClass(), sox);
			JOptionPane.showMessageDialog(null, surl + "\n" + sox.getMessage(), HOVerwaltung
					.instance().getLanguageString("Fehler"), JOptionPane.ERROR_MESSAGE);
			returnString = "";
		}
		return returnString;
	}

	private InputStream getNonCHPPWebFile(String surl, boolean showErrorMessage) {
		InputStream returnStream = null;
		try {
			Response response = null;
			OAuthRequest request = new OAuthRequest(Verb.GET, surl);
			infoHO(request);
			response = request.send();
			int iResponse = response.getCode();
			switch (iResponse) {
			case 200:
			case 201:
				returnStream = getResultStream(response);
				break;
			case 407:
				throw new RuntimeException(
						"Download Error\nHTTP Response Code 407: Proxy authentication required.");
			default:
				throw new RuntimeException("Download Error\nHTTP Response Code: " + iResponse);
			}
		} catch (Exception sox) {
			HOLogger.instance().error(getClass(), sox);
			if (showErrorMessage)
				JOptionPane.showMessageDialog(null, sox.getMessage() + "\nURL: " + surl,
						HOVerwaltung.instance().getLanguageString("Fehler"),
						JOptionPane.ERROR_MESSAGE);
			returnStream = null;
		}
		return returnStream;
	}

	/**
	 * Post a web file containing body parameters
	 * 
	 * @param surl
	 *            the full url with parameters
	 * @param bodyprop
	 *            A hash map of string, string where key is parameter key and
	 *            value is parameter value
	 * @param showErrorMessage
	 *            Whether to show message on error or not
	 * @param scope
	 *            The scope of the request is required, if no scope, put "".
	 *            Example: "set_matchorder".
	 */
	public InputStream postWebFileWithBodyParameters(String surl, Map<String, String> bodyParas,
			boolean showErrorMessage, String scope) {

		OAuthDialog authDialog = null;
		Response response = null;
		int iResponse = 200;
		boolean tryAgain = true;
		try {
			while (tryAgain == true) {
				OAuthRequest request = new OAuthRequest(Verb.POST, surl);
				for (Map.Entry<String, String> entry : bodyParas.entrySet()) {
					request.addBodyParameter(entry.getKey(), entry.getValue());
				}
				infoHO(request);
				request.addHeader("Content-Type", "application/x-www-form-urlencoded");
				if (m_OAAccessToken == null || m_OAAccessToken.getToken().length() == 0) {
					iResponse = 401;
				} else {
					m_OAService.signRequest(m_OAAccessToken, request);
					response = request.send();
					iResponse = response.getCode();
				}
				switch (iResponse) {
				case 200:
				case 201:
					// We are done!
					return getResultStream(response);
				case 401:
					// disable WaitCursor to unblock GUI
					CursorToolkit.stopWaitCursor(HOMainFrame.instance().getRootPane());
					if (authDialog == null) {
						authDialog = new OAuthDialog(HOMainFrame.instance(), m_OAService, scope);
					}
					authDialog.setVisible(true);
					// A way out for a user unable to authorize for some reason
					if (authDialog.getUserCancel() == true) {
						return null;
					}
					m_OAAccessToken = authDialog.getAccessToken();
					if (m_OAAccessToken == null) {
						m_OAAccessToken = new Token(
								Helper.decryptString(ho.core.model.UserParameter.instance().AccessToken),
								Helper.decryptString(ho.core.model.UserParameter.instance().TokenSecret));
					}
					// Try again...
					break;
				case 407:
					throw new RuntimeException(
							"Download Error\nHTTP Response Code 407: Proxy authentication required.");
				default:
					throw new RuntimeException("Download Error\nHTTP Response Code: " + iResponse);
				}
			}
		} catch (Exception sox) {
			HOLogger.instance().error(getClass(), sox);
			if (showErrorMessage) {
				JOptionPane.showMessageDialog(null, sox.getMessage() + "\nURL: " + surl,
						HOVerwaltung.instance().getLanguageString("Fehler"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return null;
	}

	private InputStream getResultStream(Response response) throws IOException {
		InputStream resultingInputStream = null;
		if (response != null) {
			String encoding = response.getHeader("Content-Encoding");
			if ((encoding != null) && encoding.equalsIgnoreCase("gzip")) {
				resultingInputStream = new GZIPInputStream(response.getStream());
				HOLogger.instance().log(getClass(), " Read GZIP.");
			} else if ((encoding != null) && encoding.equalsIgnoreCase("deflate")) {
				resultingInputStream = new InflaterInputStream(response.getStream(), new Inflater(
						true));
				HOLogger.instance().log(getClass(), " Read Deflated.");
			} else {
				resultingInputStream = response.getStream();
				HOLogger.instance().log(getClass(), " Read Normal.");
			}
		}
		return resultingInputStream;
	}

	private String readStream(InputStream stream) throws IOException {
		StringBuilder builder = new StringBuilder();
		if (stream != null) {
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(stream,
					"UTF-8"));
			String line = bufferedreader.readLine();
			if (line != null) {
				builder.append(line);
				while ((line = bufferedreader.readLine()) != null) {
					builder.append('\n');
					builder.append(line);
				}
			}
			bufferedreader.close();
		}
		return builder.toString();
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// Identifikation
	// //////////////////////////////////////////////////////////////////////////////

	private void infoHO(OAuthRequest request) {
		request.addHeader("accept-language", "de");
		request.setConnectionKeepAlive(true);
		request.setConnectTimeout(60, TimeUnit.SECONDS);
		request.addHeader("accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, */*");
		request.addHeader("accept-encoding", "gzip, deflate");
		request.addHeader("user-agent", m_sIDENTIFIER);

		// ProxyAuth hier einbinden da diese Funk immer aufgerufen wird
		if (this.proxySettings != null && this.proxySettings.isAuthenticationNeeded()) {
			final String pw = this.proxySettings.getUsername() + ":"
					+ this.proxySettings.getPassword();
			final String epw = (new BASE64Encoder()).encode(pw.getBytes());
			request.addHeader("Proxy-Authorization", "Basic " + epw);
		}
	}

	/**
	 * Save downloaded data to a temp-file for debugging purposes.
	 * 
	 * @param url
	 *            the url where the content was downloaded from
	 * @param content
	 *            the content to save
	 */
	private void saveCHPP(String url, String content) {
		File outDir = new File("tmp");
		if (!outDir.exists()) {
			outDir.mkdirs();
		}

		String xmlName = null;
		try {
			Document doc = XMLUtils.createDocument(content);
			xmlName = XMLUtils.getTagData(doc, "FileName");
			if (xmlName != null && xmlName.indexOf('.') != -1) {
				xmlName = xmlName.substring(0, xmlName.lastIndexOf('.'));
			}
		} catch (Exception ex) {
			HOLogger.instance().error(getClass(), ex);
		}

		Date downloadDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss-S");
		String outFileName = df.format(downloadDate) + ".txt";
		if (!StringUtils.isEmpty(xmlName)) {
			outFileName = xmlName + "_" + outFileName;
		}
		File outFile = new File(outDir, outFileName);

		df = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder.append("Downloaded at ").append(df.format(downloadDate)).append('\n');
		builder.append("From ").append(url).append("\n\n");
		builder.append(content);

		try {
			IOUtils.writeToFile(builder.toString(), outFile, "UTF-8");
		} catch (Exception e) {
			HOLogger.instance().error(MyConnector.class, e);
		}
	}

	private Token createOAAccessToken() {
		return new Token(Helper.decryptString(UserParameter.instance().AccessToken),
				Helper.decryptString(UserParameter.instance().TokenSecret));
	}
}
