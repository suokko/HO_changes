// %1667190662:hoplugins.teamAnalyzer.ht%
package ho.module.teamAnalyzer.ht;

import ho.core.file.xml.XMLManager;
import ho.core.gui.HOMainFrame;
import ho.core.model.HOVerwaltung;
import ho.core.net.MyConnector;
import ho.core.util.HOLogger;
import ho.module.teamAnalyzer.SystemManager;
import ho.module.teamAnalyzer.manager.PlayerDataManager;
import ho.module.teamAnalyzer.vo.Match;
import ho.module.teamAnalyzer.vo.PlayerInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


/**
 * Hattrick Download Helper class
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class HattrickManager {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Method that download from Hattrick the available matches for the team
     *
     * @param teamId teamid to download matches for
     */
    public static void downloadMatches(final int teamId) {
    		final GregorianCalendar start = new GregorianCalendar();
    		start.add(Calendar.MONTH, -3);
    		HOMainFrame.instance().getOnlineWorker().getMatchArchive(teamId, start);
    }

    /**
     * Method that download from Hattrick the current players for the team
     *
     * @param teamId teamid to download players for
     */
    public static void downloadPlayers(int teamId) {
        String xml = "";

        try {
			xml = MyConnector.instance().getHattrickXMLFile("/common/chppxml.axd?file=players&TeamID=" + teamId);
        } catch (Exception e) {
            return;
        }

        List<PlayerInfo> players = new ArrayList<PlayerInfo>();
        XMLManager parser = XMLManager.instance();
        Document dom = parser.parseString(xml);
        Node matchesList = dom.getElementsByTagName("PlayerList").item(0);

        for (int i = 0; i < (matchesList.getChildNodes().getLength() / 2); i++) {
            PlayerInfo player = new PlayerInfo();
            player.setTeamId(teamId);

            int id = getIntValue(matchesList, i, "PlayerID");
            player.setPlayerId(id);

            int card = getIntValue(matchesList, i, "Cards");
            int injury = getIntValue(matchesList, i, "InjuryLevel");
            int status = PlayerDataManager.AVAILABLE;

            if (card == 3) {
                status = PlayerDataManager.SUSPENDED;
            }

            if (injury > 0) {
                status = PlayerDataManager.INJURED;
            }

            player.setStatus(status);

            int se = getIntValue(matchesList, i, "Specialty");
            player.setSpecialEvent(se);

            int form = getIntValue(matchesList, i, "PlayerForm");
            player.setForm(form);

            int exp = getIntValue(matchesList, i, "Experience");
            player.setExperience(exp);

            int age = getIntValue(matchesList, i, "Age");
            player.setAge(age);

            int tsi = getIntValue(matchesList, i, "TSI");
            player.setTSI(tsi);

            String name = getValue(matchesList, i, "PlayerName");
            player.setName(name);

            players.add(player);
        }

        PlayerDataManager.update(players);
    }

    /**
     * Method that download from Hattrick the team name
     *
     * @param teamId Tteamid to download name for
     *
     * @return Team Name
     *
     * @throws Exception if error occurs
     */
    public static String downloadTeam(int teamId) throws Exception {
		String xml = MyConnector.instance().getHattrickXMLFile("/common/chppxml.axd?file=team&teamID=" + teamId);
		XMLManager parser = XMLManager.instance();
        Document dom = parser.parseString(xml);
        Document teamDocument = dom.getElementsByTagName("Team").item(0).getOwnerDocument();
        String teamName = teamDocument.getElementsByTagName("TeamName").item(0).getFirstChild().getNodeValue();

        return teamName;
    }

    /**
     * Helper method to get a value from a Node.
     */
    private static int getIntValue(Node node, int i, String tag) {
        try {
            String value = getValue(node, i, tag);
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
        }

        return 0;
    }

    /**
     * Helper method to get a value from a Node.
     */
    private static String getValue(Node node, int i, String tag) {
        String value = node.getOwnerDocument().getElementsByTagName(tag).item(i).getFirstChild()
                           .getNodeValue();
        return value;
    }

    /**
     * Log to HOLogger.
     */
	public final static void log(String message) {
		try {
			HOLogger.instance().log(HattrickManager.class, message);
		} catch (Exception e) {
			System.out.println(HattrickManager.class + " - Error during log(): " + e);
		}
	}
	
	   /**
     * Check if CHPP rules approve download for a match
     *
     * @param match Match to be downloaded
     *
     * @return true if allowed
     */
    public static boolean isDownloadAllowed(Match match) {
        boolean isNextOpponent = ((match.getHomeId() == SystemManager.getLeagueOpponentId())
                                 || (match.getAwayId() == SystemManager.getLeagueOpponentId()));
        boolean last2Weeks = false;
        int actualWeek = getWeekNumber(HOVerwaltung.instance().getModel().getBasics().getSeason(),
        		HOVerwaltung.instance().getModel().getBasics().getSpieltag());
        int gameWeek = getWeekNumber(match.getSeason(), match.getWeek());

        if ((actualWeek - gameWeek) < 3) {
            last2Weeks = true;
        }

        return (isNextOpponent && last2Weeks);
    }

    /**
     * Return the week number from week and season, used for CHPP compatibility
     *
     * @param season
     * @param week
     *
     * @return
     */
    private static int getWeekNumber(int season, int week) {
        return ((season - 1) * 16) + week;
    }
}
