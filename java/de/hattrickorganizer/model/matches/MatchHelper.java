package de.hattrickorganizer.model.matches;

import hoplugins.Commons;

import java.util.Vector;

import plugins.IMatchDetails;
import plugins.IMatchHelper;
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
public class MatchHelper implements IMatchHelper {
	private static IMatchHelper m_clInstance;

	public static IMatchHelper instance() {
		if (m_clInstance == null)
			m_clInstance = new MatchHelper();
		return m_clInstance;
	}

	/**
	 * Get the match location.
	 * (using constants from IMatchHelper)
	 * 
	 * @param match		match short info
	 */
	public short getLocation(IMatchKurzInfo match) {
		return getLocation(match.getHeimID(), match.getGastID(), match.getMatchID(), match.getMatchTyp());
	}

	/**
	 * Get the match location.
	 * (using constants from IMatchHelper)
	 * 
	 * @param matchId	match Id
	 */
	public short getLocation(int matchId) {
		IMatchLineup ml = Commons.getModel().getMatchLineup(matchId);
		return getLocation(ml.getHeimId(), ml.getGastId(), matchId, ml.getMatchTyp());
	}

	/**
	 * Get the match location.
	 * (using constants from IMatchHelper)
	 * 
	 * @param homeTeamId	home team Id
	 * @param awayTeamId	away team Id
	 * @param matchId		match Id
	 * @param matchType		match Type (league, cup, friendly...) from IMatchLineup
	 */
	public short getLocation(int homeTeamId, int awayTeamId, int matchId, int matchType) {
		/**
		 * Current progress:
		 * =================
		 * League/Cup/Qualification: 	Home, Away, AwayDerby are recognized correctly
		 * 
		 * Friendlies:					Only Home is recognized correctly (if the stadium name has not changed) 
		 * 									-> for now, return NEUTRAL_GROUND for all other location types
		 * 
		 * 
		 * TODO:
		 * - Implement Stadium.getArenaId()
		 * - Implement Basics.getRegionId()
		 * - Implement getRegionForStadium(int arenaId) 
		 */
		int userTeamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
		String userStadiumName = HOVerwaltung.instance().getModel().getStadium().getStadienname();
		// FIXME: fetch our stadium Id as this would be much more reliable than the stadium name
//		int userStadiumId = HOVerwaltung.instance().getModel().getStadium().getArenaId();
		int userStadiumId = -1;
		// FIXME: fetch our region Id
//		int userRegion = HOVerwaltung.instance().getModel().getBasics().getRegionId();

		int location = UNKNOWN;

		if (userTeamId != homeTeamId && userTeamId != awayTeamId) {
			return FOREIGN_MATCH; // foreign match
		}

   		IMatchDetails details = HOMiniModel.instance().getMatchDetails(matchId);

   		// For a league/qualification/cup game, the home team always has the home advantage (no neutral grounds) 
   		// (exception for cup finals, see below)
   		if (matchType == IMatchLineup.LIGASPIEL || matchType == IMatchLineup.POKALSPIEL || matchType == IMatchLineup.QUALISPIEL) {
   			if (homeTeamId == userTeamId)
   				location = HOME_MATCH;
   		}
   		
   		// For friendlies, also check the stadium name, because we may play on neutral ground
   		if (matchType == IMatchLineup.TESTSPIEL 
   				|| matchType == IMatchLineup.TESTPOKALSPIEL 
   				|| matchType == IMatchLineup.INT_TESTSPIEL
   				|| matchType == IMatchLineup.INT_TESTCUPSPIEL) {
   			if (homeTeamId == userTeamId) {
   				if (details.getArenaName().equals(userStadiumName) 
   						|| details.getArenaID() == userStadiumId) {
   					// our teamID and our stadium name/stadium Id -> home match
   					location = HOME_MATCH;
   				} else {
   					// our teamID is home team, but not our stadium
   					// i.e. neutral ground
   					location = NEUTRAL_GROUND;
   				}
   			}
   		}
   		
   		// Don't check home matches, exept for the cup (because the cup finals are on neutral ground)
   		if (location != HOME_MATCH || matchType == IMatchLineup.POKALSPIEL) {
   			if (matchType == IMatchLineup.LIGASPIEL || matchType == IMatchLineup.QUALISPIEL || matchType == IMatchLineup.POKALSPIEL) {
   	   			/**
   	   			 * league or cup match -> check highlights
   	   			 */
   		   		Vector highlights = details.getHighlights();
   				for (int i=0; i<highlights.size(); i++) {
   					IMatchHighlight curHighlight = (IMatchHighlight)highlights.get(i);
   					if (curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_INFORMATION 
   							&& curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_DERBY) {
   						location = AWAY_DERBY;
   						break;
   					}
   					if (curHighlight.getHighlightTyp() == IMatchHighlight.HIGHLIGHT_INFORMATION 
   							&& curHighlight.getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_NEUTRAL_GROUND) {
   						// A cup match on neutral ground (finals!) can't be a derby
   						location = NEUTRAL_GROUND;
   						break;
   					}
   					if (i>5) { // 'derby' and 'neutral ground' must be one of the first events
   						break;
   					}
   				}
   				// No home match, no derby -> away match
   				if (location != HOME_MATCH && location != AWAY_DERBY)
   					location = AWAY_MATCH;
   			} else {
   				/**
   				 * Friendy match (not in our stadium)
   				 */
   				// TODO: Fetch region of stadium
   				// For now, return NEUTRAL_GROUND to avoid false datasets in Feedback upload
//   				int stadiumRegion = getRegionForStadium(details.getArenaID());
//   				if (userRegion == stadiumRegion)
//   					location = AWAY_DERBY;
//   				else
//   					location = AWAY_MATCH;
   			}
   		}
   		return (short)location;
	}

}
