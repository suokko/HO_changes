package plugins;

import ho.module.matches.model.MatchKurzInfo;

import java.util.Vector;

/**
 * Helper class to retrieve match related information.
 *
 * @author aik
 */

public interface IMatchHelper {
	/* Unable to determine location type */
	public static final int UNKNOWN = -99;
	/* Match on neutral ground (we should try to detect if it's AWAY/HOME, eventually) */
	public static final int NEUTRAL_GROUND = -2;
	/* Not a users match */
	public static final int FOREIGN_MATCH = -1;
	/* Away match */
	public static final int AWAY_MATCH = IMatchDetails.LOCATION_AWAY;
	/* Home match */
	public static final int HOME_MATCH = IMatchDetails.LOCATION_HOME;
	/* Away derby */
	public static final int AWAY_DERBY = IMatchDetails.LOCATION_AWAYDERBY;

	/**
	 * Get the match location.
	 * (using constants from IMatchHelper)
	 * 
	 * @param match		match short info
	 */
	public short getLocation(MatchKurzInfo match);
	
	/**
	 * Get the match location.
	 * (using constants from IMatchHelper)
	 * 
	 * @param matchId	match Id
	 */
	public short getLocation(int matchId);
	
	/**
	 * Get the match location.
	 * (using constants from IMatchHelper)
	 * 
	 * @param homeTeamId	home team Id
	 * @param awayTeamId	away team Id
	 * @param matchId		match Id
	 * @param matchType		match Type (league, cup, friendly...) from IMatchLineup
	 */
	public short getLocation(int homeTeamId, int awayTeamId, int matchId, int matchType);

	public boolean hasOverConfidence (Vector<IMatchHighlight> highlights, int teamId);
	public boolean hasTacticalProblems (Vector<IMatchHighlight> highlights, int teamId);
	public boolean hasRedCard (Vector<IMatchHighlight> highlights, int teamId);
	public boolean hasInjury (Vector<IMatchHighlight> highlights, int teamId);
	public boolean hasWeatherSE (Vector<IMatchHighlight> highlights, int teamId);
	public boolean hasManualSubstitution (Vector<IMatchHighlight> highlights, int teamId);
	public boolean hasPullBack (Vector<IMatchHighlight> highlights, int teamId);
}
