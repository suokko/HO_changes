// %1667190662:hoplugins.teamAnalyzer.ht%
package ho.module.teamAnalyzer.ht;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.gui.HOMainFrame;
import ho.core.model.match.MatchKurzInfo;
import ho.core.net.MyConnector;
import ho.core.net.OnlineWorker;
import ho.core.util.HOLogger;
import ho.module.teamAnalyzer.manager.PlayerDataManager;
import ho.module.teamAnalyzer.vo.Filter;
import ho.module.teamAnalyzer.vo.Match;
import ho.module.teamAnalyzer.vo.PlayerInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
     * If manual filter, the last 30 is made available.
     * If auto filter, enough matches to supply the filter needs are available.
     * Recent tournament are added if on manual, or if they are wanted, in addition to
     * the number specified.
     *
     * @param teamId teamid to download matches for
     * @param filter the match filter object.
     */
    public static void downloadMatches(final int teamId, Filter filter) {
   		final GregorianCalendar start = new GregorianCalendar();
   		List<MatchKurzInfo> matches;
   		
   		int limit = Math.min(filter.getNumber(), 50);
   		
   		// If on manual, disable all filters, and download 30 matches.
   		if (!filter.isAutomatic()) {
   			limit = 30;
   		}
   		
	    start.add(Calendar.MONTH, -8);
	    matches = OnlineWorker.getMatchArchive(teamId, start, false);
	    Collections.reverse(matches); // Newest first
	    for (MatchKurzInfo match : matches) {
	    	if (match.getMatchStatus() != MatchKurzInfo.FINISHED) {
	    		continue;
	    	}
	    	
	    	if (!filter.isAutomatic() 
	    		||	(filter.isAcceptedMatch(new Match(match))
	    				&& !DBManager.instance().isMatchLineupVorhanden(match.getMatchID()))) {
	    		
	    		OnlineWorker.downloadMatchData(match.getMatchID(), match.getMatchTyp(), false);
   			}
	    	
	    	limit--;
	    	if (limit < 1) {
	    		break;
	    	}
   		}
	    
	    // Look for tournament matches if they are included in filter.
	    
	    if (!filter.isAutomatic() || filter.isTournament()) {
		    // Current matches includes tournament matches
	    	matches = OnlineWorker.getMatches(teamId, true, false, false);
	   		// newest first
	   		Collections.reverse(matches);
	   		
	   		// Only store tournament matches
	   		for (MatchKurzInfo match : matches) {
	   			if (match.getMatchStatus() != MatchKurzInfo.FINISHED) {
		    		continue;
		    	}
	   			if (filter.isAcceptedMatch(new Match(match)) 
	   					&& match.getMatchTyp().isTournament()
	   					&& !DBManager.instance().isMatchLineupVorhanden(match.getMatchID())) {
	   				
	   				OnlineWorker.downloadMatchData(match.getMatchID(), match.getMatchTyp(), false);
	   			}
	   		}
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
			xml = MyConnector.instance().getHattrickXMLFile("/common/chppxml.axd?file=players&TeamID=" + teamId);
        } catch (Exception e) {
            return;
        }

        List<PlayerInfo> players = new ArrayList<PlayerInfo>();
        Document dom = XMLManager.parseString(xml);
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
        Document dom = XMLManager.parseString(xml);
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
    	
    	// CHPP-Teles confirms in staff message to bingeling (blaghaid) that this is not a problem
    	// We don't have to worry much about traffic anymore, but may want to check for new functionality.
    	// The team analyzer was discussed.

    	return true;
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
