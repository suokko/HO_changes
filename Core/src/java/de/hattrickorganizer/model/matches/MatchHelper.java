package de.hattrickorganizer.model.matches;

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
		IMatchLineup ml = HOMiniModel.instance().getMatchLineup(matchId);
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
		 * Friendlies:					Home, Away, AwayDerby are recognized correctly
		 * 									(for downloads with HO >= 1.401,
		 * 									for other downloads only HOME is recognized)
		 */
		int userTeamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();
		String userStadiumName = HOVerwaltung.instance().getModel().getStadium().getStadienname();
		int userStadiumId = HOVerwaltung.instance().getModel().getStadium().getArenaId();
		// TODO: It would be better to use the user region at match date, because
		// in the meantime the user may have changed his region
		int userRegion = HOVerwaltung.instance().getModel().getBasics().getRegionId();

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
   				// TODO: For now, we check the arena name and the id
   				// because old users don't have the arena id in the DB (new since 1.401)
   				// We can remove the name compare later
   				if (details.getArenaName().equals(userStadiumName) 
   						|| (userStadiumId > 0 && details.getArenaID() == userStadiumId)) {
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
   		   		Vector<IMatchHighlight> highlights = details.getHighlights();
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
   				int stadiumRegion = details.getRegionId();
   				if (stadiumRegion > 0 && userRegion > 0) {
   					// Stadium region & user Region valid
   	   				if (userRegion == stadiumRegion)
   	   					location = AWAY_DERBY;
   	   				else
   	   					location = AWAY_MATCH;
   				} else {
   					// Stadium region or user region invalid 
   					// (old data, downloaded with HO<1.401)
   					location = UNKNOWN;
   				}
   			}
   		}
   		return (short)location;
	}

}
