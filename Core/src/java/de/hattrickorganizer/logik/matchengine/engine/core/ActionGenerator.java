// %2981324713:de.hattrickorganizer.logik.matchengine.engine.core%
package de.hattrickorganizer.logik.matchengine.engine.core;

import de.hattrickorganizer.logik.matchengine.TeamData;
import de.hattrickorganizer.logik.matchengine.engine.common.Action;
import de.hattrickorganizer.logik.matchengine.engine.common.TeamGameData;

import plugins.IMatchDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class ActionGenerator extends BaseActionGenerator {
	//~ Instance fields ----------------------------------------------------------------------------

	private CounterAttackGenerator caGenerator;
	private TeamGameData awayTeamGameData;
	private TeamGameData homeTeamGameData;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new ActionGenerator object.
	 */
	public ActionGenerator() {
	}

	/**
	 * Creates a new ActionGenerator object.
	 *
	 * @param homeTeamData TODO Missing Constructuor Parameter Documentation
	 * @param awayTeamData TODO Missing Constructuor Parameter Documentation
	 */
	public ActionGenerator(TeamData homeTeamData, TeamData awayTeamData) {
		setTeams(homeTeamData, awayTeamData);
		caGenerator = new CounterAttackGenerator(homeTeamData, awayTeamData);
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param homeTeamData TODO Missing Method Parameter Documentation
	 * @param awayTeamData TODO Missing Method Parameter Documentation
	 */
	public final void setTeams(TeamData homeTeamData, TeamData awayTeamData) {
		caGenerator = new CounterAttackGenerator(homeTeamData, awayTeamData);
		homeTeamGameData = compare(homeTeamData, awayTeamData);
		homeTeamGameData.setHome(true);
		awayTeamGameData = compare(awayTeamData, homeTeamData);
		awayTeamGameData.setHome(false);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param minute TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public final Action[] predict(int minute) {
		final List actions = new ArrayList();
		actions.addAll(calculateActions(minute, homeTeamGameData, awayTeamGameData));
		actions.addAll(calculateActions(minute, awayTeamGameData, homeTeamGameData));
		return (Action[]) actions.toArray(new Action[0]);
	}

	public Action[] simulate() {
		final List actions = new ArrayList();
		int midfieldPossession = (int) getEffectiveness(homeTeamGameData.getRatings().getMidfield());
		int pressing = getPressing(homeTeamGameData, awayTeamGameData);
		int succesfulPressing = 0;
						
		for(int i = 0; i < 10; i++) {
			// Pressing modifier
			if ( (succesfulPressing<7) && ((succesfulPressing*2)<=pressing) ) {			
				if (Utility.random(14)<pressing) {
					succesfulPressing++;
					continue;			
				}								
			}			
									
			boolean homeAction = false;
			if (Utility.random(100)<midfieldPossession) {
				homeAction = true;			
			} 		
									
			if (homeAction) {
				actions.addAll(calculateAction(homeTeamGameData, awayTeamGameData));
			} else {
				actions.addAll(calculateAction(awayTeamGameData, homeTeamGameData));		
			}
		}						
		return (Action[]) actions.toArray(new Action[0]);
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param td1 TODO Missing Method Parameter Documentation
	 * @param td2 TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	private int getPressing(TeamData td1, TeamData td2) {
		int lvl = 0;

		if ( (td1.getTacticType() == IMatchDetails.TAKTIK_PRESSING) && (td1.getTacticLevel()>4) )  {
			lvl = lvl + td1.getTacticLevel() - 4;
		}

		if ( (td2.getTacticType() == IMatchDetails.TAKTIK_PRESSING) && (td2.getTacticLevel()>4) )  {
			lvl = lvl + td2.getTacticLevel() - 4;
		}
		return lvl;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param minute TODO Missing Method Parameter Documentation
	 * @param team TODO Missing Method Parameter Documentation
	 * @param opponent TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	private List calculateActions(int minute, TeamGameData team, TeamGameData opponent) {
		final List actions = new ArrayList();
		boolean hasChance = hasChance(team, minute);

		if (hasChance) {
			final int rnd = Utility.random(20);

			if (rnd < getPressing(team, opponent)) {
				hasChance = false;
				team.addActionPlayed();
			}
		}

		if (hasChance) {
			final Action action = new Action();
			action.setArea(getArea(team.getTacticType(), team.getTacticLevel()));
			action.setMinute(minute);
			action.setType(0);
			action.setHomeTeam(team.isHome());
			actions.add(action);

			if (isScore(team, action.getArea())) {
				action.setScore(true);
			} else if (opponent.getTacticType() == IMatchDetails.TAKTIK_KONTER) {
				final Action ca = caGenerator.calculateCounterAttack(minute, opponent);

				if (ca != null) {
					actions.add(ca);
				}
			}

			team.addActionPlayed();
		}

		return actions;
	}

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param minute TODO Missing Method Parameter Documentation
	 * @param team TODO Missing Method Parameter Documentation
	 * @param opponent TODO Missing Method Parameter Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	private List calculateAction(TeamGameData team, TeamGameData opponent) {
		final List actions = new ArrayList();
		final Action action = new Action();
		action.setArea(getArea(team.getTacticType(), team.getTacticLevel()));
		action.setType(0);
		action.setHomeTeam(team.isHome());
		actions.add(action);
		
		if (Utility.random(10)<1) {
			// SP event
			int type = Utility.random(2);
		
			int successRate = 75;
			if (type==0) {
				successRate = 25;
			}
			boolean isScore = false;
			if (Utility.random(100)<successRate) {
				isScore = true;
			}
			
			action.setScore(isScore);			
			return actions;		
		}
		
		if (isScore(team, action.getArea())) {
			action.setScore(true);
		} else if (opponent.getTacticType() == IMatchDetails.TAKTIK_KONTER) {
			final Action ca = caGenerator.calculateCounterAttack(0, opponent);

			if (ca != null) {
				actions.add(ca);
			}
		}
		return actions;
	}

}
