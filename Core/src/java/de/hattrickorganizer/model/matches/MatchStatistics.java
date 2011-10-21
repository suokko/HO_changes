package de.hattrickorganizer.model.matches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import plugins.IMatchHighlight;
import plugins.IMatchLineupPlayer;
import plugins.ISpielerPosition;

import plugins.ISubstitution;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.tools.HOLogger;

public class MatchStatistics {

	protected Vector<IMatchLineupPlayer> m_vEndLineup = new Vector<IMatchLineupPlayer>();

	protected Vector<ISubstitution> m_vSubstitutions = new Vector<ISubstitution>();

	protected Vector<IMatchLineupPlayer> m_vStartLineup = new Vector<IMatchLineupPlayer>();

	private int m_iMatchID;

	public MatchStatistics(int matchid, Vector<IMatchLineupPlayer> endLineup, Vector<IMatchLineupPlayer> startLineup, Vector<ISubstitution> substitutions) {
		m_vEndLineup = endLineup;
		m_vSubstitutions = substitutions;
		m_vStartLineup = startLineup;
		m_iMatchID = matchid;
	}


	/**
	 * Returns the minutes a player has played in the specified positions
	 * 
	 * @param spielerId the id of the player
	 * @param accepted An array of integers specifying the positions which should be accepted
	 * @return the number of minutes played in the specified positions
	 */
	public int getMinutesPlayedInPositions(int spielerId, int[] accepted) {

		boolean inPosition = false;
		int enterMin = -1;
		int minPlayed = 0;

		// Those in the starting lineup entered at minute 0
		for (int i = 0; i < m_vStartLineup.size(); i++) {
			if ( (m_vStartLineup.get(i).getSpielerId() == spielerId) && (isInAcceptedPositions(m_vStartLineup.get(i).getFieldPos(), accepted))) {
				enterMin = 0;
				inPosition = true;
				break;
			}
		}

		// The substitutions are sorted on minute. Look for substitutions involving the player, and check his position
		// after the substitution (on the substitution minute). Work through the list, and add minutes depending on
		// entering/leaving the accepted position list.

		ISubstitution sub;
		for (int i = 0; i < m_vSubstitutions.size(); i++) {
			sub = m_vSubstitutions.get(i);
			if (sub == null) { 
				HOLogger.instance().debug(getClass(), "getMinutesPlayedError, null in substitution list");
				break; 
			}

			if ((sub.getPlayerIn() == spielerId) || (sub.getPlayerOut() == spielerId)) {

				int newpos = getPlayerFieldPositionAtMinute(spielerId, sub.getMatchMinuteCriteria());
				boolean newPosAccepted = isInAcceptedPositions(newpos, accepted);

				if (inPosition && newPosAccepted) {
					// He was in a counting position, and still is. Ignore
				} else if (!inPosition && !newPosAccepted) {
					// He was outside position, and still is. Ignore
				} else if (inPosition && !newPosAccepted) {
					// He left a counting position.
					minPlayed += sub.getMatchMinuteCriteria() - enterMin;
					inPosition = false;
				} else if (!inPosition && newPosAccepted) {
					// He entered a counting position
					enterMin = sub.getMatchMinuteCriteria();
					inPosition = true;
				}
			}
		}
		// Done with substitutions, add end if necessary
		
		if (inPosition) {
			minPlayed += getMatchEndMinute() - enterMin;
		}

		return minPlayed;
	}


	private boolean isInAcceptedPositions(int pos, int[] accepted) {
		boolean result = false;

		for (int i = 0; i < accepted.length ; i++) {
			if (accepted[i] == pos) {
				result = true;
				break;
			}
		}
		return result;
	}


	private int getPlayerFieldPostitionAfterSubstitution(int spielerId, int arrIndex) {
		// arrIndex should be the index of the sub in the substitution vector. We have 100%
		// trust in our caller (this is a private method), and never verify that.

		// This is the api logic:
		// The sub order contains a position, pos.
		// - Player swap: Pos contains data for the new playerOut position
		// - Normal sub: Pos contains data for the new playerIn position
		// - Red card: Pos contains data (0) for playerOut position
		//             PlayerIn is empty
		// - Repositioning: The player is both playerIn and playerOut, 
		//                 pos contains is his new one.


		if (arrIndex < 0) {
			// We have run out of substitutions. Start lineup got answer
			return getStartlineupPositionForSpielerId(spielerId);
		}

		ISubstitution tmpSub = m_vSubstitutions.get(arrIndex);

		if ((tmpSub.getPlayerIn() != spielerId) && (tmpSub.getPlayerOut() != spielerId)) {
			// This substitution is not exciting, check the next one
			return getPlayerFieldPostitionAfterSubstitution(spielerId, arrIndex -1);
		}

		for (int i = arrIndex ; i >= 0 ; i--)	{
			tmpSub = m_vSubstitutions.get(i);
			if (tmpSub.getPlayerOut() == spielerId) {

				if (tmpSub.getPlayerIn() == spielerId) {
					// Repositioning
					return tmpSub.getPos();
				}

				if ((tmpSub.getPlayerIn() == 0) && (tmpSub.getPos() == 0)) {
					// Sent off or no sub after injury
					return -1;
				}

				if (tmpSub.getOrderType() == 1) {
					// Normal substitution and he left the field
					return -1;
				}

				if (tmpSub.getOrderType() == 3) {
					// Player swap
					// The sub object got his new position
					return tmpSub.getPos();
				}

				HOLogger.instance().debug(getClass(),"getPlayerFieldPostitionAfterSubstitution had a playerOut fall through. " + 
						m_iMatchID + " " + spielerId + " " + tmpSub.getPlayerOrderId());
			}

			if (tmpSub.getPlayerIn() == spielerId) {
				// Repositioning is already caught.
				// Sent off does not exist here.

				if (tmpSub.getOrderType() == 1) {
					//A sub entering. His position is in the sub object.
					return tmpSub.getPos();
				}

				if (tmpSub.getOrderType() == 3) {
					//A player swap. We need to know where the other player came from.
					//We figure this out by asking where he was at the end of the previous sub
					//object (it is safe no matter the value of i).
					return getPlayerFieldPostitionAfterSubstitution(tmpSub.getPlayerOut(), i-1);
				}

				HOLogger.instance().debug(getClass(),"getPlayerFieldPostitionAfterSubstitution had a playerIn fall through. " + 
						m_iMatchID + " " + spielerId + " " + tmpSub.getPlayerOrderId());
			}
		} // End for loop

		HOLogger.instance().debug(getClass(), "getPlayerFieldPostitionAfterSubstitution reached the end, which should never happen " + 
				m_iMatchID + " " + spielerId + " " + tmpSub.getPlayerOrderId());
		return -1;
	}


	public int getPlayerFieldPositionAtMinute(int spielerId, int minute) {
		// Captain and set piece taker don't count...

		if ((minute >= getMatchEndMinute()) || (minute < 0)) {
			// The player is at home (they travel fast)...
			return -1;
		}

		// Look for the last substitution before the given minute
		// Check if the player is involved. If not keep checking back in time until match start.

		ISubstitution tmpSub = null;
		for (int i = m_vSubstitutions.size() -1 ; i >= 0 ; i--)	{

			tmpSub = m_vSubstitutions.get(i);
			if (tmpSub.getMatchMinuteCriteria() > minute) {
				//This is after our minute. Next, please.
				continue;
			}

			if ((tmpSub.getPlayerOut() == spielerId) || (tmpSub.getPlayerIn() == spielerId)) {
				return getPlayerFieldPostitionAfterSubstitution(spielerId, i);
			}
		} 

		// We survived all the subs, lets see if we found him in the starting lineup.
		return getStartlineupPositionForSpielerId(spielerId);
	}


	public int getStartlineupPositionForSpielerId(int spielerId) {
		int tmpPos;
		for (int i = 0; i < m_vStartLineup.size(); i++) {
			if (m_vStartLineup.get(i).getSpielerId() == spielerId) {
				tmpPos = m_vStartLineup.get(i).getFieldPos();
				if ((tmpPos >= ISpielerPosition.keeper) && (tmpPos <= ISpielerPosition.substForward)) {
					// Not some captain or set piece taker
					// This starting lineup position is our answer.
					return tmpPos;
				}
			}
		}
		return -1;
	}



	public boolean hasPlayed(int spielerId){
		boolean ret = false;

		// TODO change to use minutes played instead
		if (getPlayerMap(-1).get(new Integer(spielerId)) != null) {
			ret = true;
		}
		return ret;
	}

	/**
	 * Returns the last minute of the match. Usually 90, but there could be overtime.
	 * 
	 * @return the last minute or -1 if not found
	 */
	public int getMatchEndMinute() {
		Vector<IMatchHighlight> hls = DBZugriff.instance().getMatchDetails(m_iMatchID).getHighlights();
		for (int i = 0; i < hls.size() ; i++) {
			if ((hls.get(i).getHighlightTyp() == 0) && (hls.get(i).getHighlightSubTyp() == IMatchHighlight.HIGHLIGHT_SUB_BESTER_SPIELER)) {
				return hls.get(i).getMinute();
			}
		}
		return -1;
	}


	private HashMap<Integer, IMatchLineupPlayer> getPlayerMap(int roleId) {
		// Is this needed?
		HashMap<Integer, IMatchLineupPlayer> map = new HashMap<Integer, IMatchLineupPlayer>(); 
		boolean addAll = false;
		if (roleId == -1) {
			addAll = true;
		}


		// First startlineup, then subarrivals, last endlineup. That should give the "best" data quality in the map.

		for (int i = 0; i < m_vStartLineup.size() ; i++) {
			if ((addAll == true) || (m_vStartLineup.get(i).getFieldPos() == roleId)) {
				map.put(new Integer(m_vStartLineup.get(i).getSpielerId()), m_vStartLineup.get(i));
			}
		}

		for (int i = 0; i > m_vSubstitutions.size() ; i++) {
			ISubstitution sub = m_vSubstitutions.get(i);
			if (sub != null) {
				if ((addAll == true) || (sub.getPos() == roleId)) {
					int playerId = sub.getPlayerIn();
					if (playerId > 0) {
						map.put(new Integer(playerId), new MatchLineupPlayer((int)sub.getPos(), (int)sub.getBehaviour(), playerId, 0,  "", 0));
					}
				}
			}
		}

		for (int i = 0; i < m_vEndLineup.size() ; i++) {
			if ((addAll == true) || (m_vEndLineup.get(i).getFieldPos() == roleId)) {
				map.put(new Integer(m_vEndLineup.get(i).getSpielerId()), m_vEndLineup.get(i));
			}
		}
		return map;
	}

	public java.util.List<Integer> getAllPlayersThatPlayed() {
		// Consider name and if objects should be returned when feedback needs it...
		HashMap<Integer, IMatchLineupPlayer> map = getPlayerMap(-1);

		java.util.ArrayList<Integer> list = new ArrayList<Integer>();

		java.util.Iterator<IMatchLineupPlayer> it = map.values().iterator();
		IMatchLineupPlayer pl = null;
		while (it.hasNext()) {
			pl = it.next();
			list.add(new Integer(pl.getSpielerId()));
		}
		return list;
	}


	// TODO Do we want this one? Duplicate from the team lineup.
	private MatchLineupPlayer getPlayerByPosition(int id) {
		MatchLineupPlayer player = null;

		for (int i = 0; (m_vEndLineup != null) && (i < m_vEndLineup.size()); i++) {
			player = (MatchLineupPlayer) m_vEndLineup.elementAt(i);

			if (player.getId() == id) {
				return player;
			}
		}
		return new MatchLineupPlayer(-1, 0, -1, -1d, "", 0);
	}

}
