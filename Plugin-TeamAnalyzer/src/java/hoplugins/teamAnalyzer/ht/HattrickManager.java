// %1667190662:hoplugins.teamAnalyzer.ht%
package hoplugins.teamAnalyzer.ht;

import hoplugins.Commons;

import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.manager.PlayerDataManager;
import hoplugins.teamAnalyzer.vo.PlayerInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import plugins.IXMLParser;

import java.util.ArrayList;
import java.util.List;


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
    public static void downloadMatches(int teamId) {
    	try {
	        String xml = "";
	        try {
	            xml = Commons.getModel().getDownloadHelper().getHattrickXMLFile("/chppxml.axd?file=matches&TeamID="
	                                                                            + teamId);
	        } catch (Exception e) {
	            System.out.println("Error(downloadMatches) 1: " + e.getMessage());
	            return;
	        }

	        IXMLParser parser = Commons.getModel().getXMLParser();
	        Document dom = parser.parseString(xml);
	        Node matchesList = dom.getElementsByTagName("MatchList").item(0);

	        for (int i = 0; i < matchesList.getChildNodes().getLength(); i++) {
	            String matchId = matchesList.getOwnerDocument().getElementsByTagName("MatchID").item(i)
	                                        .getFirstChild().getNodeValue();
	            String status = matchesList.getOwnerDocument().getElementsByTagName("Status").item(i)
	                                       .getFirstChild().getNodeValue();

	            if (status.equalsIgnoreCase("FINISHED")) {
	                boolean succ = Commons.getModel().getHelper().downloadMatchData(Integer.parseInt(matchId));
	            } else {
	                break;
	            }
	        }
    	} catch (Exception e2) {
    		System.out.println("Error(downloadMatches) 2: " + e2.getMessage());
    	}
    }

    /**
     * Method that download from Hattrick the current players for the team
     *
     * @param teamId teamid to download players for
     */
    public static void downloadPlayers(int teamId) {
        String xml = "";

        try {
            xml = Commons.getModel().getDownloadHelper().getHattrickXMLFile(
            		"/common/chppxml.axd?file=players&TeamID=" + teamId);

        } catch (Exception e) {
            return;
        }

        List<PlayerInfo> players = new ArrayList<PlayerInfo>();
        IXMLParser parser = Commons.getModel().getXMLParser();
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
        SystemManager.getPlugin().getMainPanel().getRosterPanel().reload();
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
        String xml = Commons.getModel().getDownloadHelper().getHattrickXMLFile(
        		"/common/chppxml.axd?file=team&teamID="+ teamId);
        IXMLParser parser = Commons.getModel().getXMLParser();
        Document dom = parser.parseString(xml);
        Document teamDocument = dom.getElementsByTagName("Team").item(0).getOwnerDocument();
        String teamName = teamDocument.getElementsByTagName("TeamName").item(0).getFirstChild()
                                      .getNodeValue();

        return teamName;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param node TODO Missing Method Parameter Documentation
     * @param i TODO Missing Method Parameter Documentation
     * @param tag TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
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
     * TODO Missing Method Documentation
     *
     * @param node TODO Missing Method Parameter Documentation
     * @param i TODO Missing Method Parameter Documentation
     * @param tag TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private static String getValue(Node node, int i, String tag) {
        String value = node.getOwnerDocument().getElementsByTagName(tag).item(i).getFirstChild()
                           .getNodeValue();
        return value;
    }
}
