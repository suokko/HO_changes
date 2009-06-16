// %2530051946:de.hattrickorganizer.logik.matchengine.engine.common%
package de.hattrickorganizer.logik.matchengine.engine.common;

import de.hattrickorganizer.logik.matchengine.TeamData;
import de.hattrickorganizer.logik.matchengine.engine.core.ActionGenerator;
import de.hattrickorganizer.tools.HOLogger;

import plugins.IMPActions;
import plugins.IMatchDetails;

import java.util.List;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MatchData implements plugins.IMPMatchData {
    //~ Instance fields ----------------------------------------------------------------------------

    private ActionGenerator generator = new ActionGenerator();
    private TeamData awayTeam;
    private TeamData homeTeam;
    private int minute;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MatchData object.
     *
     * @param home TODO Missing Constructuor Parameter Documentation
     * @param away TODO Missing Constructuor Parameter Documentation
     */
    public MatchData(TeamData home, TeamData away) {
        homeTeam = home;
        awayTeam = away;
        generator.setTeams(homeTeam, awayTeam);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final List<IMPActions> getAwayTeamActionList() {
        return awayTeam.getActions();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final List<IMPActions> getHomeTeamActionList() {
        return homeTeam.getActions();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final plugins.IMPActions[] advance() {
        final Action[] actions = generator.predict(minute);

        for (int i = 0; i < actions.length; i++) {
            final Action action = actions[i];

            if (action.isHomeTeam()) {
                homeTeam.addAction(action);
            } else {
                awayTeam.addAction(action);
            }
        }

        minute++;
        return actions;
    }

	/**
	 * TODO Missing Method Documentation
	 *
	 * @return TODO Missing Return Method Documentation
	 */
	public final plugins.IMPActions[] simulate() {
		final Action[] actions = generator.simulate();
		for (int i = 0; i < actions.length; i++) {
			final Action action = actions[i];
			if (action.isHomeTeam()) {
				homeTeam.addAction(action);
			} else {
				awayTeam.addAction(action);
			}
		}
		return actions;
	}
    /**
     * TODO Missing Method Documentation
     */
    public final void recap() {
        printRecap(homeTeam);
        printRecap(awayTeam);
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public final String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("Match[");
        buffer.append("generator = " + generator);
        buffer.append(", minute = " + minute);
        buffer.append(", team1 = " + homeTeam);
        buffer.append(", team2 = " + awayTeam);
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param team TODO Missing Method Parameter Documentation
     */
    private void printRecap(TeamData team) {
        final List<IMPActions> actions = team.getActions();
        HOLogger.instance().log(getClass(),team.getTeamName());

        int c = 0;
        int g = 0;
        int ca = 0;

        for (int i = 0; i < actions.size(); i++) {
            final Action ac = (Action) actions.get(i);

            if (ac.isScore()) {
                g++;
            }

            if (ac.getType() == IMatchDetails.TAKTIK_KONTER) {
                ca++;
            } else {
                c++;
            }

            HOLogger.instance().log(getClass(),ac);
        }

        HOLogger.instance().log(getClass(),"Chances " + c);
        HOLogger.instance().log(getClass(),"Goals   " + g);
        HOLogger.instance().log(getClass(),"counter " + ca);
    }
}
