package de.hattrickorganizer.model.matches;

import hoplugins.Commons;

import java.util.Vector;

import plugins.IMatchDetails;
import plugins.IMatchHighlight;
import plugins.IMatchKurzInfo;
import plugins.IMatchLineup;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HOVerwaltung;

/**
 * Helper class to retrieve match related information.
 *
 * @author aik
 */
public class MatchHelper {

	/**
	 * Get the match location.
	 * -1 - not a users match
	 * 0 - away match
	 * 1 - home match
	 * 2 - away derby
	 */
	public static short getLocation(IMatchKurzInfo match) {
		return getLocation(match.getHeimID(), match.getGastID(), match.getMatchID(), match.getMatchTyp());
	}

	/**
	 * Get the match location.
	 * -1 - not a users match
	 * 0 - away match
	 * 1 - home match
	 * 2 - away derby
	 */
	public static short getLocation(int matchId) {
		IMatchLineup ml = Commons.getModel().getMatchLineup(matchId);
		return getLocation(ml.getHeimId(), ml.getGastId(), matchId, ml.getMatchTyp());
	}

	/**
	 * Get the match location.
	 * -1 - not a users match
	 * 0 - away match
	 * 1 - home match
	 * 2 - away derby
	 */
	public static short getLocation(int homeTeamId, int awayTeamId, int matchId, int matchType) {
		int userTeamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();

		if (userTeamId != homeTeamId && userTeamId != awayTeamId) {
			return -1; // foreign match
		}

		short location = (homeTeamId == userTeamId) ? (short)1 : 0;
        if (location == 0) { // check only, if it's an away match
        	//league or cup match -> check highlights
        	if (matchType == IMatchLineup.LIGASPIEL || matchType == IMatchLineup.POKALSPIEL) {
                IMatchDetails details = HOMiniModel.instance().getMatchDetails(matchId);
                Vector highlights = details.getHighlights();
                for (int i=0; i<highlights.size(); i++) {
	                IMatchHighlight curHighlight = (IMatchHighlight)highlights.get(i);
	                if (curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_SUB_DERBY) {
	                	location = IMatchDetails.LOCATION_AWAYDERBY;
	                	break;
	                }
	                if (i>5) { // 'derby' must be one of the first events
	                	break;
	                }
                }
        	} else {
        		//TODO: check region ids (both teams + stadium)

        	}
        }
        return location;
	}

}
