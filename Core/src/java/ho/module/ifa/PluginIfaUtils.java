package ho.module.ifa;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.gui.HOMainFrame;
import ho.core.model.HOVerwaltung;
import ho.core.net.MyConnector;
import ho.core.net.login.LoginWaitDialog;
import ho.core.util.HOLogger;
import ho.module.ifa.gif.Quantize;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JWindow;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PluginIfaUtils {
	private static final String MATCHES_TABLENAME = "IFA_MATCH";

	protected static Document getMatchesArchive(Date from, Date to) throws IOException {
		String matchesArchive = MyConnector.instance().getMatchesArchive(
				HOVerwaltung.instance().getModel().getBasics().getTeamId(), from, to);
		return XMLManager.parseString(matchesArchive);
	}

	protected static Document getTeamDetails(int teamID) throws Exception {
		String teamDetails = MyConnector.instance().getTeamdetails(teamID);
		return XMLManager.parseString(teamDetails);
	}

	protected static String parseXmlElement(Document doc, String element, int i, String eleText) {
		return parseXmlElement(doc, element, i, eleText, true);
	}

	protected static String parseXmlElement(Document doc, String element, int i, String eleText,
			boolean showDebugWindow) {
		String value = "";
		Element ele = doc.getDocumentElement();
		Element tmpEle = (Element) ele.getElementsByTagName(eleText).item(i);
		tmpEle = (Element) tmpEle.getElementsByTagName(element).item(0);
		return XMLManager.getFirstChildNodeValue(tmpEle);
	}

	static boolean updateTeamTable() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	// static void updateMatchesTable() throws IOException {
	// Date from =
	// DateHelper.getDate(DBManager.instance().getLastIFAMatchDate());
	// List<Date[]> times = getTimeIntervalsForRetrieval(from);
	// for (Iterator<Date[]> i = times.iterator(); i.hasNext();) {
	// Date[] fromTo = i.next();
	// insertMatches(fromTo[0], fromTo[1]);
	// }
	// }

	static boolean updateMatchesTable() {
		JWindow waitWindow = new LoginWaitDialog(HOMainFrame.instance());
		try {
			waitWindow.setVisible(true);

			Date from = DateHelper.getDate(DBManager.instance().getLastIFAMatchDate());
			try {
				List<Date[]> times = getTimeIntervalsForRetrieval(from);
				for (Iterator<Date[]> i = times.iterator(); i.hasNext();) {
					Date[] fromTo = i.next();
					insertMatches(fromTo[0], fromTo[1]);
				}
			} catch (Exception e) {
				insertMatches(from, new Date());
			}
			waitWindow.setVisible(false);
		} catch (Exception e) {
			waitWindow.setVisible(false);
			HOLogger.instance().error(PluginIfaUtils.class, e);
			return false;
		}
		return true;
	}

	private static void insertMatches(Date from, Date to) throws IOException {
		StringBuilder errors = new StringBuilder();
		String matchDateStr = null;

		Document doc = getMatchesArchive(from, to);

		int matchesCount = ((Element) doc.getDocumentElement().getElementsByTagName("MatchList")
				.item(0)).getElementsByTagName("Match").getLength();
		for (int i = 0; i < matchesCount; i++) {
			int matchType = Integer.parseInt(parseXmlElement(doc, "MatchType", i, "Match"));
			matchDateStr = parseXmlElement(doc, "MatchDate", i, "Match");
			if ((matchType == 8) || (matchType == 9)) {
				int homeTeamID = Integer
						.parseInt(parseXmlElement(doc, "HomeTeamID", i, "HomeTeam"));
				int awayTeamID = Integer
						.parseInt(parseXmlElement(doc, "AwayTeamID", i, "AwayTeam"));
				int matchID = Integer.parseInt(parseXmlElement(doc, "MatchID", i, "Match"));

				if (!DBManager.instance().isIFAMatchinDB(matchID)) {
					int homeTeamGoals = Integer.parseInt(parseXmlElement(doc, "HomeGoals", i,
							"Match"));
					int awayTeamGoals = Integer.parseInt(parseXmlElement(doc, "AwayGoals", i,
							"Match"));
					try {
						Document docHomeTeam = getTeamDetails(homeTeamID);
						Document docAwayTeam = getTeamDetails(awayTeamID);
						if ((docHomeTeam == null) || (docAwayTeam == null)
								|| (docHomeTeam.getDocumentElement() == null)
								|| (docAwayTeam.getDocumentElement() == null)) {
							errors.append("Error 2 getting data for match " + matchID + " ("
									+ matchDateStr + " / HomeTeam " + homeTeamID + " vs. AwayTeam "
									+ awayTeamID + ")<br>");
						} else {
							int homeLeagueIndex = Integer.parseInt(parseXmlElement(docHomeTeam,
									"LeagueID", 0, "League", false));
							int awayLeagueIndex = Integer.parseInt(parseXmlElement(docAwayTeam,
									"LeagueID", 0, "League", false));
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
							DBManager.instance().getAdapter().executeUpdate(insert.toString());
						}
					} catch (Exception e) {
						errors.append("Error 1 getting data for match " + matchID + " ("
								+ matchDateStr + " / HomeTeam " + homeTeamID + " vs. AwayTeam "
								+ awayTeamID + ")<br>");
					}
				}
			}

		}

		if (errors.length() > 0) {
			HOLogger.instance().log(PluginIfaUtils.class, errors.toString());
		}

		if (matchesCount == 50)
			insertMatches(DateHelper.getDate(matchDateStr), to);
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
		return ((String) o).toLowerCase().replace('�', 'a').replace('�', 'o').replace('�', 'u')
				.replace('�', 's').replace('�', 'i').replace('\u010D', 'c');// ?
	}

	static BufferedImage quantizeBufferedImage(BufferedImage bufferedImage) throws IOException {
		int[][] pixels = getPixels(bufferedImage);
		int[] palette = Quantize.quantizeImage(pixels, 256);
		int w = pixels.length;
		int h = pixels[0].length;
		int[] pix = new int[w * h];

		BufferedImage bufIma = new BufferedImage(w, h, 1);

		for (int x = w; x-- > 0;) {
			for (int y = h; y-- > 0;) {
				pix[(y * w + x)] = palette[pixels[x][y]];
				bufIma.setRGB(x, y, palette[pixels[x][y]]);
			}
		}
		return bufIma;
	}

	private static int[][] getPixels(Image image) throws IOException {
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		int[] pix = new int[w * h];
		PixelGrabber grabber = new PixelGrabber(image, 0, 0, w, h, pix, 0, w);
		try {
			if (!grabber.grabPixels())
				throw new IOException("Grabber returned false: " + grabber.status());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int[][] pixels = new int[w][h];
		for (int x = w; x-- > 0;) {
			for (int y = h; y-- > 0;) {
				pixels[x][y] = pix[(y * w + x)];
			}
		}

		return pixels;
	}
}
