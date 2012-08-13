package ho.module.ifa2;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.gui.theme.ImageUtilities;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.core.net.MyConnector;
import ho.core.util.HOLogger;
import ho.core.util.XMLUtils;
import ho.module.ifa.DateHelper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JWindow;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PluginIfaUtils {
	public static final boolean HOME = true;
	public static final boolean AWAY = false;
	public static final String PLUGINNAME = "Inter. Friendly Analyzer";
	public static final String TEAM_TABLENAME_NEW = "PLUGIN_IFA_TEAM";
	public static final String TEAM_TABLENAME_OLD = "";
	public static final String TEAM_PRIMARYKEY = "TEAMID";
	public static final String TEAM_ACTIVATION_DATE = "ACTIVATION_DATE";
	public static final String TEAM_LEAGUEID = "LEAGUEID";
	public static final String FLAG_TABLENAME_NEW = "PLUGIN_IFA_FLAGS";
	public static final String FLAG_TABLENAME_OLD = "FLAGCOLLECTOR_FLAGS";
	public static final String FLAG_PRIMARYKEY = "LEAGUEINDEX";
	public static final String FLAG_LEAGUEID = "LEAGUEID";
	public static final String FLAG_COUNTRYID = "COUNTRYID";
	public static final String FLAG_COUNTRYNAME = "COUNTRYNAME";
	public static final String MATCHES_TABLENAME = "IFA_MATCH";
	public static final String MATCHES_PRIMARYKEY = "MATCHID";
	public static final String MATCHES_DATE = "PLAYEDDATE";
	public static final String MATCHES_HOMETEAMID = "HOMETEAMID";
	public static final String MATCHES_AWAYTEAMID = "AWAYTEAMID";
	public static final String MATCHES_HOMETEAMGOALS = "HOMETEAMGOALS";
	public static final String MATCHES_AWAYTEAMGOALS = "AWAYTEAMGOALS";
	public static final String MATCHES_HOME_LEAGUEID = "HOME_LEAGUEID";
	public static final String MATCHES_AWAY_LEAGUEID = "AWAY_LEAGUEID";
	// public static final JWindow WAITWINDOW = PluginIFA.MINIMODEL.getGUI()
	// .createWaitDialog(PluginIFA.MINIMODEL.getGUI().getOwner4Dialog());
	public static final JWindow WAITWINDOW = null;

	// protected static Document getWorldDetails() throws Exception {
	// MyConnector.instance().get
	// String worldDetails = PluginIFA.MINIMODEL
	// .getDownloadHelper()
	// .getHattrickXMLFile(
	// "/common/chppxml.axd?file=worlddetails&actionType=leagues");
	// return PluginIFA.MINIMODEL.getXMLParser().parseString(worldDetails);
	// }

	protected static Document getMatchesArchive(Date from, Date to) throws IOException {
		String matchesArchive = MyConnector.instance().getMatchesArchive(
				HOVerwaltung.instance().getModel().getBasics().getTeamId(),
				from, to);
		return XMLManager.parseString(matchesArchive);
	}

	// protected static Document getOwnTeamDetails() throws Exception {
	// String teamDetails = PluginIFA.MINIMODEL.getDownloadHelper()
	// .getHattrickXMLFile("/common/chppxml.axd?file=teamdetails");
	// return PluginIFA.MINIMODEL.getXMLParser().parseString(teamDetails);
	// }

	protected static Document getTeamDetails(int teamID) throws Exception {
		String teamDetails = MyConnector.instance().getTeamdetails(teamID);
		return XMLManager.parseString(teamDetails);
	}

	protected static String parseXmlElement(Document doc, String element,
			int i, String eleText) {
		return parseXmlElement(doc, element, i, eleText, true);
	}

	protected static String parseXmlElement(Document doc, String element,
			int i, String eleText, boolean showDebugWindow) {
		String value = "";
		Element ele = doc.getDocumentElement();
		Element tmpEle = (Element) ele.getElementsByTagName(eleText).item(i);
		tmpEle = (Element) tmpEle.getElementsByTagName(element).item(0);
		return XMLManager.getFirstChildNodeValue(tmpEle);
	}

	protected static String parseXmlAttribute(Document doc, String attribute,
			int i, String eleText) {
		Element ele = null;
		String value = "";
		try {
			ele = doc.getDocumentElement();
			ele = (Element) ele.getElementsByTagName(eleText).item(i);
			value = ele.getAttribute(attribute);
		} catch (Exception e) {
			HOLogger.instance().error(PluginIfaUtils.class, e);
		}
		return value;
	}

	protected static int getIdFromFlagName(String flagName) {
		int length = 0;
		for (int i = 0; i < flagName.length(); i++) {
			if (!Character.isDigit(flagName.charAt(i)))
				break;
			length++;
		}

		if (length < 1) {
			return 0;
		}
		return Integer.parseInt(flagName.substring(0, length));
	}

	// protected static int getTeamID() {
	// StringBuffer team = new StringBuffer(100);
	// team.append("SELECT ");
	// team.append("TEAMID");
	// team.append(" FROM ");
	// team.append("PLUGIN_IFA_TEAM");
	// ResultSet rs = PluginIFA.MINIMODEL.getAdapter().executeQuery(
	// team.toString());
	// int teamID = 0;
	// try {
	// if (rs.next())
	// teamID = rs.getInt(1);
	// } catch (Exception e) {
	// cleanRessources(rs);
	// return 0;
	// }
	// return teamID;
	// }

	private static int getTeamID() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	// protected static int getLeagueID() {
	// StringBuffer team = new StringBuffer(100);
	// team.append("SELECT ");
	// team.append("LEAGUEID");
	// team.append(" FROM ");
	// team.append("PLUGIN_IFA_TEAM");
	// ResultSet rs = PluginIFA.MINIMODEL.getAdapter().executeQuery(
	// team.toString());
	// int teamID = 0;
	// try {
	// if (rs.next())
	// teamID = rs.getInt(1);
	// } catch (Exception e) {
	// cleanRessources(rs);
	// return 0;
	// }
	// return teamID;
	// }

	private static int getLeagueID() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	// private static String getActivationDate() {
	// StringBuffer team = new StringBuffer(100);
	// team.append("SELECT ");
	// team.append("ACTIVATION_DATE");
	// team.append(" FROM ");
	// team.append("PLUGIN_IFA_TEAM");
	// ResultSet rs = PluginIFA.MINIMODEL.getAdapter().executeQuery(
	// team.toString());
	// String date = "2000-01-01";
	// try {
	// if (rs.next())
	// date = rs.getString(1);
	// } catch (Exception e) {
	// System.out.println("Error " + e);
	// } finally {
	// cleanRessources(rs);
	// }
	// return date;
	// }

	// protected static int getLeagueCount() {
	// StringBuffer count = new StringBuffer(100);
	// count.append("SELECT COUNT(*) FROM ");
	// count.append("PLUGIN_IFA_FLAGS");
	// ResultSet rsCount = PluginIFA.MINIMODEL.getAdapter().executeQuery(
	// count.toString());
	// int rowCount = 0;
	// try {
	// if (rsCount.next())
	// rowCount = rsCount.getInt(1);
	// } catch (Exception e) {
	// cleanRessources(rsCount);
	// return 0;
	// }
	// return rowCount;
	// }

	protected static FlagLabel[] getAllCountries(boolean homeAway) {
		WorldDetailLeague[] leagues = WorldDetailsManager.instance()
				.getLeagues();
		FlagLabel[] flagLabels = null;
		flagLabels = new FlagLabel[leagues.length];
		for (int i = 0; i < leagues.length; i++) {
			FlagLabel flagLabel = new FlagLabel();
			flagLabel.setCountryId(leagues[i].getCountryId());
			flagLabel.setCountryName(leagues[i].getCountryName());
			flagLabel.setIcon(ImageUtilities.getFlagIcon(flagLabel
					.getCountryId()));
			flagLabel.setToolTipText(flagLabel.getCountryName());
			int flagLeagueID = leagues[i].getLeagueId();
			if (flagLeagueID == HOVerwaltung.instance().getModel().getBasics()
					.getLiga()) {
				flagLabel.setHomeCountry(true);
			} else {
				flagLabel.setEnabled(DBManager.instance().isIFALeagueIDinDB(
						flagLeagueID, homeAway));
			}
			flagLabels[i] = flagLabel;
		}

		Arrays.sort(flagLabels, new UniversalComparator(1));
		return flagLabels;
	}

	// protected static String getLastMatchDate() {
	// StringBuffer select = new StringBuffer(100);
	// select.append("SELECT MAX(");
	// select.append("PLAYEDDATE");
	// select.append(") FROM ");
	// select.append(MATCHES_TABLENAME);
	// ResultSet rs = PluginIFA.MINIMODEL.getAdapter().executeQuery(
	// select.toString());
	// String s = getActivationDate();
	// try {
	// if ((rs != null) && (rs.next())) {
	// String tmp = rs.getString(1);
	// if (tmp != null)
	// s = DateHelper.getDateString(DateHelper.getDate(tmp));
	// }
	// } catch (Exception e) {
	// System.out.println("Error: " + e.getMessage() + "\ns=" + s);
	// e.printStackTrace();
	// } finally {
	// cleanRessources(rs);
	// }
	// return s;
	// }

	protected static Vector getAwayMatchesForTable() {
		Vector vec = new Vector();
		StringBuffer select = new StringBuffer(100);
		select.append("SELECT ");
		select.append("HOME_LEAGUEID");
		select.append(", ");
		select.append("PLAYEDDATE");
		select.append(", ");
		select.append("HOMETEAMGOALS");
		select.append(", ");
		select.append("AWAYTEAMGOALS");
		select.append(" FROM ");
		select.append(MATCHES_TABLENAME);
		select.append(" WHERE ");
		select.append("AWAYTEAMID");
		select.append(" = ");
		select.append(HOVerwaltung.instance().getModel().getBasics()
				.getTeamId());
		select.append(" ORDER BY ");
		select.append("HOME_LEAGUEID");
		select.append(" ASC, ");
		select.append("PLAYEDDATE");
		select.append(" ASC");
		ResultSet rs = DBManager.instance().getAdapter()
				.executeQuery(select.toString());
		if (rs == null)
			return vec;
		try {
			while (rs.next()) {
				Object[] obj = new Object[4];
				obj[0] = rs.getString("HOME_LEAGUEID");
				obj[1] = rs.getString("PLAYEDDATE");
				obj[2] = rs.getString("HOMETEAMGOALS");
				obj[3] = rs.getString("AWAYTEAMGOALS");
				vec.add(obj);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			cleanRessources(rs);
		}
		return vec;
	}

	protected static Vector getHomeMatchesForTable() {
		Vector vec = new Vector();
		StringBuffer select = new StringBuffer(100);
		select.append("SELECT ");
		select.append("AWAY_LEAGUEID");
		select.append(", ");
		select.append("PLAYEDDATE");
		select.append(", ");
		select.append("HOMETEAMGOALS");
		select.append(", ");
		select.append("AWAYTEAMGOALS");
		select.append(" FROM ");
		select.append(MATCHES_TABLENAME);
		select.append(" WHERE ");
		select.append("HOMETEAMID");
		select.append(" = ");
		select.append(HOVerwaltung.instance().getModel().getBasics()
				.getTeamId());
		select.append(" ORDER BY ");
		select.append("AWAY_LEAGUEID");
		select.append(" ASC, ");
		select.append("PLAYEDDATE");
		select.append(" ASC");
		ResultSet rs = DBManager.instance().getAdapter()
				.executeQuery(select.toString());
		if (rs == null)
			return vec;
		try {
			while (rs.next()) {
				Object[] obj = new Object[4];
				obj[0] = rs.getString("AWAY_LEAGUEID");
				obj[1] = rs.getString("PLAYEDDATE");
				obj[2] = rs.getString("HOMETEAMGOALS");
				obj[3] = rs.getString("AWAYTEAMGOALS");
				vec.add(obj);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			return new Vector();
		} finally {
			cleanRessources(rs);
		}
		return vec;
	}

	// protected static boolean updateTeamTable() {
	// try {
	// StringBuffer drop = new StringBuffer(100);
	// drop.append("DROP TABLE ");
	// drop.append("");
	// drop.append(" IF EXISTS");
	// PluginIFA.MINIMODEL.getAdapter().executeUpdate(drop.toString());
	// StringBuffer create = new StringBuffer(100);
	// create.append("CREATE TABLE ");
	// create.append("PLUGIN_IFA_TEAM");
	// create.append(" (");
	// create.append("TEAMID");
	// create.append(" INTEGER, ");
	// create.append("LEAGUEID");
	// create.append(" INTEGER, ");
	// create.append("ACTIVATION_DATE");
	// create.append(" CHAR(10), PRIMARY KEY (");
	// create.append("TEAMID");
	// create.append(")) IF NOT EXIST");
	// PluginIFA.MINIMODEL.getAdapter().executeUpdate(create.toString());
	// Document doc = getOwnTeamDetails();
	// WAITWINDOW.setVisible(true);
	// int teamID = Integer.parseInt(parseXmlElement(doc, "TeamID", 0,
	// "Team"));
	// int leagueID = Integer.parseInt(parseXmlElement(doc, "LeagueID", 0,
	// "League"));
	// String activationString = parseXmlElement(doc, "ActivationDate", 0,
	// "User");
	// String activationDate = DateHelper.getDateString(DateHelper
	// .getDate(activationString));
	// StringBuffer insert = new StringBuffer(100);
	// insert.append("INSERT INTO ");
	// insert.append("PLUGIN_IFA_TEAM");
	// insert.append(" (");
	// insert.append("TEAMID");
	// insert.append(", ");
	// insert.append("LEAGUEID");
	// insert.append(", ");
	// insert.append("ACTIVATION_DATE");
	// insert.append(") VALUES (");
	// insert.append(teamID);
	// insert.append(", ");
	// insert.append(leagueID);
	// insert.append(", '");
	// insert.append(activationDate);
	// insert.append("')");
	// PluginIFA.MINIMODEL.getAdapter().executeUpdate(insert.toString());
	// WAITWINDOW.setVisible(false);
	// TEAMID = teamID;
	// } catch (Exception e) {
	// WAITWINDOW.setVisible(false);
	// HOLogger.instance().error(PluginIfaUtils.class, e);
	// return false;
	// }
	// return true;
	// }

	static boolean updateTeamTable() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	// protected static boolean updateFlagTable() {
	// try {
	// StringBuffer drop = new StringBuffer(100);
	// drop.append("DROP TABLE ");
	// drop.append("FLAGCOLLECTOR_FLAGS");
	// drop.append(" IF EXISTS");
	// PluginIFA.MINIMODEL.getAdapter().executeUpdate(drop.toString());
	// StringBuffer create = new StringBuffer(100);
	// create.append("CREATE TABLE ");
	// create.append("PLUGIN_IFA_FLAGS");
	// create.append(" (");
	// create.append("LEAGUEINDEX");
	// create.append(" INTEGER, ");
	// create.append("LEAGUEID");
	// create.append(" INTEGER, ");
	// create.append("COUNTRYID");
	// create.append(" INTEGER, ");
	// create.append("COUNTRYNAME");
	// create.append(" CHAR(256), PRIMARY KEY (");
	// create.append("LEAGUEINDEX");
	// create.append(")) IF NOT EXIST");
	// PluginIFA.MINIMODEL.getAdapter().executeUpdate(create.toString());
	// StringBuffer delete = new StringBuffer(100);
	// delete.append("DELETE FROM ");
	// delete.append("PLUGIN_IFA_FLAGS");
	// PluginIFA.MINIMODEL.getAdapter().executeUpdate(delete.toString());
	// Document doc = getWorldDetails();
	// WAITWINDOW.setVisible(true);
	//
	// COUNTRIES_COUNT = ((Element) doc.getDocumentElement()
	// .getElementsByTagName("LeagueList").item(0))
	// .getElementsByTagName("League").getLength();
	//
	// for (int i = 0; i < COUNTRIES_COUNT; i++) {
	// int countryID = Integer.parseInt(parseXmlElement(doc,
	// "CountryID", i, "Country"));
	// int leagueID = Integer.parseInt(parseXmlElement(doc,
	// "LeagueID", i, "League"));
	// String name = parseXmlElement(doc, "CountryName", i, "Country");
	//
	// name = name.replaceAll("'", "''");
	// StringBuffer insert = new StringBuffer(100);
	// insert.append("INSERT INTO ");
	// insert.append("PLUGIN_IFA_FLAGS");
	// insert.append(" (");
	// insert.append("LEAGUEINDEX");
	// insert.append(", ");
	// insert.append("LEAGUEID");
	// insert.append(", ");
	// insert.append("COUNTRYID");
	// insert.append(", ");
	// insert.append("COUNTRYNAME");
	// insert.append(") VALUES (");
	// insert.append(i);
	// insert.append(", ");
	// insert.append(leagueID);
	// insert.append(", ");
	// insert.append(countryID);
	// insert.append(", '");
	// insert.append(name);
	// insert.append("')");
	// PluginIFA.MINIMODEL.getAdapter().executeUpdate(
	// insert.toString());
	// }
	//
	// COUNTRIES_COUNT = getLeagueCount();
	// WAITWINDOW.setVisible(false);
	// } catch (Exception e) {
	// WAITWINDOW.setVisible(false);
	// HOLogger.instance().error(PluginIfaUtils.class, e);
	// return false;
	// }
	// return true;
	// }

	static boolean updateFlagTable() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	// protected static boolean updateMatchesTable() {
	// try {
	// StringBuffer drop = new StringBuffer(100);
	// drop.append("DROP TABLE ");
	// drop.append("PLUGIN_IFA_MATCHES");
	// drop.append(" IF EXISTS");
	// DBManager.instance().getAdapter().executeUpdate(drop.toString());
	// StringBuffer create = new StringBuffer(100);
	// create.append("CREATE TABLE ");
	// create.append(MATCHES_TABLENAME);
	// create.append(" (");
	// create.append("MATCHID");
	// create.append(" INTEGER, ");
	// create.append("PLAYEDDATE");
	// create.append(" VARCHAR(25), ");
	// create.append("HOMETEAMID");
	// create.append(" INTEGER, ");
	// create.append("AWAYTEAMID");
	// create.append(" INTEGER, ");
	// create.append("HOMETEAMGOALS");
	// create.append(" INTEGER, ");
	// create.append("AWAYTEAMGOALS");
	// create.append(" INTEGER, ");
	// create.append("HOME_LEAGUEID");
	// create.append(" INTEGER, ");
	// create.append("AWAY_LEAGUEID");
	// create.append(" INTEGER, PRIMARY KEY (");
	// create.append("MATCHID");
	// create.append(")) IF NOT EXIST");
	// PluginIFA.MINIMODEL.getAdapter().executeUpdate(create.toString());
	// WAITWINDOW.setVisible(true);
	//
	// String from = getLastMatchDate();
	// try {
	// List times = getTimeIntervalsForRetrieval(from);
	// for (Iterator i = times.iterator(); i.hasNext();) {
	// String[] fromTo = (String[]) i.next();
	// insertMatches(fromTo[0], fromTo[1]);
	// }
	// } catch (Exception e) {
	// insertMatches(from, DateHelper.getTodayDateString());
	// }
	// WAITWINDOW.setVisible(false);
	// } catch (Exception e) {
	// WAITWINDOW.setVisible(false);
	// HOLogger.instance().error(PluginIfaUtils.class, e);
	// return false;
	// }
	// return true;
	// }

	static void updateMatchesTable() throws IOException {
		Date from = DateHelper.getDate(DBManager.instance()
				.getLastIFAMatchDate());
		List<Date[]> times = getTimeIntervalsForRetrieval(from);
		for (Iterator<Date[]> i = times.iterator(); i.hasNext();) {
			Date[] fromTo = i.next();
			insertMatches(fromTo[0], fromTo[1]);
		}
	}

	private static void insertMatches(Date from, Date to) throws IOException {
		StringBuilder errors = new StringBuilder();
		String matchDateStr = null;

		Document doc = getMatchesArchive(from, to);

		int matchesCount = ((Element) doc.getDocumentElement()
				.getElementsByTagName("MatchList").item(0))
				.getElementsByTagName("Match").getLength();
		for (int i = 0; i < matchesCount; i++) {
			int matchType = Integer.parseInt(parseXmlElement(doc, "MatchType",
					i, "Match"));
			matchDateStr = parseXmlElement(doc, "MatchDate", i, "Match");
			if ((matchType == 8) || (matchType == 9)) {
				int homeTeamID = Integer.parseInt(parseXmlElement(doc,
						"HomeTeamID", i, "HomeTeam"));
				int awayTeamID = Integer.parseInt(parseXmlElement(doc,
						"AwayTeamID", i, "AwayTeam"));
				int matchID = Integer.parseInt(parseXmlElement(doc, "MatchID",
						i, "Match"));
				int homeTeamGoals = Integer.parseInt(parseXmlElement(doc,
						"HomeGoals", i, "Match"));
				int awayTeamGoals = Integer.parseInt(parseXmlElement(doc,
						"AwayGoals", i, "Match"));
				try {
					Document docHomeTeam = getTeamDetails(homeTeamID);
					Document docAwayTeam = getTeamDetails(awayTeamID);
					if ((docHomeTeam == null) || (docAwayTeam == null)
							|| (docHomeTeam.getDocumentElement() == null)
							|| (docAwayTeam.getDocumentElement() == null)) {
						errors.append("Error 2 getting data for match "
								+ matchID + " (" + matchDateStr
								+ " / HomeTeam " + homeTeamID
								+ " vs. AwayTeam " + awayTeamID + ")<br>");
					} else {
						int homeLeagueIndex = Integer.parseInt(parseXmlElement(
								docHomeTeam, "LeagueID", 0, "League", false));
						int awayLeagueIndex = Integer.parseInt(parseXmlElement(
								docAwayTeam, "LeagueID", 0, "League", false));
						StringBuilder insert = new StringBuilder(100);
						insert.append("INSERT INTO ");
						insert.append(MATCHES_TABLENAME);
						insert.append(" (");
						insert.append("MATCHID");
						insert.append(", ");
						insert.append("PLAYEDDATE");
						insert.append(", ");
						insert.append("HOMETEAMID");
						insert.append(", ");
						insert.append("AWAYTEAMID");
						insert.append(", ");
						insert.append("HOMETEAMGOALS");
						insert.append(", ");
						insert.append("AWAYTEAMGOALS");
						insert.append(", ");
						insert.append("HOME_LEAGUEID");
						insert.append(", ");
						insert.append("AWAY_LEAGUEID");
						insert.append(") VALUES (");
						insert.append(matchID);
						insert.append(", '");
						insert.append(matchDateStr);
						insert.append("', ");
						insert.append(homeTeamID);
						insert.append(", ");
						insert.append(awayTeamID);
						insert.append(", ");
						insert.append(homeTeamGoals);
						insert.append(", ");
						insert.append(awayTeamGoals);
						insert.append(", ");
						insert.append(homeLeagueIndex);
						insert.append(", ");
						insert.append(awayLeagueIndex);
						insert.append(")");
						DBManager.instance().getAdapter()
								.executeUpdate(insert.toString());
					}
				} catch (Exception e) {
					errors.append("Error 1 getting data for match " + matchID
							+ " (" + matchDateStr + " / HomeTeam " + homeTeamID
							+ " vs. AwayTeam " + awayTeamID + ")<br>");
				}
			}

		}

		if (errors.length() > 0) {
			HOLogger.instance().log(PluginIfaUtils.class, errors.toString());
		}

		if (matchesCount == 50)
			insertMatches(
					DateHelper.getDate(matchDateStr), to);
	}

	private static List<Date[]> getTimeIntervalsForRetrieval(Date from) {
		List<Date[]> ret = new ArrayList<Date[]>();
		Calendar tmpF = new GregorianCalendar();
		tmpF.setTime(from);
		tmpF.set(Calendar.HOUR_OF_DAY, 0);
		tmpF.set(Calendar.MINUTE, 0);
		tmpF.set(Calendar.SECOND, 0);
		tmpF.set(Calendar.MILLISECOND, 0);
		tmpF.setLenient(true);

		Calendar end = new GregorianCalendar();
		end.setLenient(true);
		end.add(5, 1);

		Calendar tmpT = new GregorianCalendar();
		tmpT.setLenient(true);
		while (tmpT.before(end)) {
			tmpT.add(2, 2);
			if (tmpT.after(end)) {
				tmpT = end;
			}
			ret.add(new Date[] { tmpF.getTime(), tmpT.getTime() });
			tmpF.setTime(tmpT.getTime());
		}
		return ret;
	}

	private static void cleanRessources(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException localSQLException) {
		}
	}

	private static String prepareForCompare(Object o) {
		return ((String) o).toLowerCase().replace('�', 'a').replace('�', 'o')
				.replace('�', 'u').replace('�', 's').replace('�', 'i')
				.replace('\u010D', 'c');// ?
	}
}
