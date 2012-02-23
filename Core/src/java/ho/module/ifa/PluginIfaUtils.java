package ho.module.ifa;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.core.plugins.GUIPluginWrapper;

import java.awt.Dimension;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.swing.JWindow;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.IDebugWindow;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.net.MyConnector;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.HelperWrapper;

public class PluginIfaUtils {
	public static final boolean HOME = true;
	public static final boolean AWAY = false;

	protected static void showDebugWindow(String exception) {
		IDebugWindow debugWindow = GUIPluginWrapper.instance()
				.createDebugWindow(new Point(0, 0), new Dimension(400, 500));
		debugWindow.append(exception);
		debugWindow.setVisible(true);
	}

	private static Document getMatchesArchive(String from, String to)
			throws Exception {
		String matchesArchive = MyConnector.instance().getMatchArchiv(0,from,to);
		return XMLManager.instance().parseString(matchesArchive);
	}

	private static Document getTeamDetails(int teamID) throws Exception {
		String teamDetails = MyConnector.instance().getTeamdetails(teamID);
		return XMLManager.instance().parseString(teamDetails);
	}

	private static String parseXmlElement(Document doc, String element,
			int i, String eleText) {
		return parseXmlElement(doc, element, i, eleText, true);
	}

	private static String parseXmlElement(Document doc, String element,
			int i, String eleText, boolean showDebugWindow) {
		String value = "";
		try {
			Element ele = doc.getDocumentElement();
			Element tmpEle = (Element) ele.getElementsByTagName(eleText)
					.item(i);
			tmpEle = (Element) tmpEle.getElementsByTagName(element).item(0);
			value = XMLManager.instance().getFirstChildNodeValue(
					tmpEle);
		} catch (Exception e) {
			HOLogger.instance().error(PluginIfaUtils.class, e);
		}
		return value;
	}

	private static int getTeamID() {
		return HOVerwaltung.instance().getModel().getBasics().getTeamId();
	}

	static int getLeagueCount() {
		StringBuffer count = new StringBuffer(100);
		count.append("SELECT COUNT(*) FROM ");
		count.append("HT_WORLDDETAILS");
		ResultSet rsCount = DBManager.instance().getAdapter().executeQuery(
				count.toString());
		int rowCount = 0;
		try {
			if (rsCount.next())
				rowCount = rsCount.getInt(1);
		} catch (Exception e) {
			cleanRessources(rsCount);
			return 0;
		}
		return rowCount;
	}

	static FlagLabel[] getAllCountries(boolean homeAway) {
		WorldDetailLeague[] leagues =WorldDetailsManager.instance().getLeagues();
		FlagLabel[] flagLabels = null;
		// ArrayList ret = new ArrayList();
		flagLabels = new FlagLabel[leagues.length];
		try {
			for (int i = 0; i < leagues.length; i++) {
				FlagLabel flagLabel = new FlagLabel();
				flagLabel.setCountryId(leagues[i].getCountryId());
				flagLabel.setCountryName(leagues[i].getCountryName());
				try {
					flagLabel.setIcon(HelperWrapper.instance()
							.getImageIcon4Country(flagLabel.getCountryId()));
				} catch (Exception e) {
					System.out.println("Error getting image icon for country "
							+ flagLabel.getCountryId() + " "
							+ flagLabel.getCountryName() + "\n"
							+ e.getMessage());
					flagLabel.setIcon(HelperWrapper.instance()
							.getImageIcon4Country(-1));
				}
				flagLabel.setToolTipText(flagLabel.getCountryName());
				int flagLeagueID = leagues[i].getLeagueId();
				if (flagLeagueID == HOVerwaltung.instance().getModel().getBasics().getLiga())
					flagLabel.setHomeCountry(true);
				else {
					flagLabel.setEnabled(DBManager.instance().isLeagueIDinDB(flagLeagueID, homeAway));
				}
				flagLabels[i] = flagLabel;
			}

			Arrays.sort(flagLabels, new UniversalComparator(1));

		} catch (Exception e) {
			return new FlagLabel[0];
		} 
		return flagLabels;
	}


	static boolean updateMatchesTable() {
		JWindow waitWindow = GUIPluginWrapper.instance().createWaitDialog(HOMainFrame.instance());
		try {
			waitWindow.setVisible(true);

			String from = DBManager.instance().getLastIfaMatchDate();
			try {
				ArrayList<String[]> times = getTimeIntervalsForRetrieval(from);
				for (Iterator<String[]> i = times.iterator(); i.hasNext();) {
					String[] fromTo = i.next();
					insertMatches(fromTo[0], fromTo[1]);
				}
			} catch (Exception e) {
				insertMatches(from, DateHelper.getTodayDateString());
			}
			waitWindow.setVisible(false);
		} catch (Exception e) {
			waitWindow.setVisible(false);
			HOLogger.instance().error(PluginIfaUtils.class, e);
			return false;
		}
		return true;
	}

	private static void insertMatches(String from, String to) throws Exception {
		StringBuilder errors = new StringBuilder();
		String matchDate = from;
		Document doc = getMatchesArchive(from, to);
		int matchesCount = ((Element) doc.getDocumentElement()
				.getElementsByTagName("MatchList").item(0))
				.getElementsByTagName("Match").getLength();
		for (int i = 0; i < matchesCount; i++) {
			IfaMatch match = new IfaMatch();
			
			int matchType = Integer.parseInt(parseXmlElement(doc, "MatchType",
					i, "Match"));
			matchDate = parseXmlElement(doc, "MatchDate", i, "Match");
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
								+ matchID + " (" + matchDate + " / HomeTeam "
								+ homeTeamID + " vs. AwayTeam " + awayTeamID
								+ ")<br>");
					} else {
						int homeLeagueIndex = Integer.parseInt(parseXmlElement(
								docHomeTeam, "LeagueID", 0, "League", false));
						int awayLeagueIndex = Integer.parseInt(parseXmlElement(
								docAwayTeam, "LeagueID", 0, "League", false));
						
						
						match.setMatchId(matchID);
						match.setPlayedDateString(matchDate);
						match.setHomeLeagueId(homeLeagueIndex);
						match.setHomeTeamId(homeTeamID);
						match.setAwayLeagueId(awayLeagueIndex);
						match.setAwayTeamId(awayTeamID);
						match.setHomeTeamGoals(homeTeamGoals);
						match.setAwayTeamGoals(awayTeamGoals);
						
						DBManager.instance().insertMatch(match);
					}
				} catch (Exception e) {
					errors.append("Error 1 getting data for match " + matchID
							+ " (" + matchDate + " / HomeTeam " + homeTeamID
							+ " vs. AwayTeam " + awayTeamID + ")<br>");
				}
			}

		}

		if (errors.length() > 0) {
			showDebugWindow(errors.toString());
		}

		if (matchesCount == 50)
			insertMatches(
					DateHelper.getDateString(DateHelper.getDate(matchDate)), to);
	}

	private static ArrayList<String[]> getTimeIntervalsForRetrieval(String from) {
		ArrayList<String[]> ret = new ArrayList<String[]>();
		Date start = DateHelper.getDate(from);
		start.setHours(0);
		start.setMinutes(0);
		start.setSeconds(0);
		Calendar end = new GregorianCalendar();
		end.setLenient(true);
		end.add(5, 1);

		Calendar tmpF = new GregorianCalendar();
		tmpF.setTime(start);
		Calendar tmpT = new GregorianCalendar();
		tmpT.setTime(tmpF.getTime());
		tmpF.setLenient(true);
		tmpT.setLenient(true);
		while (tmpT.before(end)) {
			tmpT.add(2, 2);
			if (tmpT.after(end)) {
				tmpT = end;
			}
			ret.add(new String[] { DateHelper.getDateString(tmpF.getTime()),
					DateHelper.getDateString(tmpT.getTime()) });
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
}
