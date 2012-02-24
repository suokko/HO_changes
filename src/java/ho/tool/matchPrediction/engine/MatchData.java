package ho.tool.matchPrediction.engine;


import plugins.IMPActions;
import plugins.IMatchDetails;


import ho.core.util.HOLogger;

import java.util.List;


class MatchData implements plugins.IMPMatchData {

    private ActionGenerator generator = new ActionGenerator();
    private TeamData awayTeam;
    private TeamData homeTeam;
    private int minute;

    MatchData(TeamData home, TeamData away) {
        homeTeam = home;
        awayTeam = away;
        generator.setTeams(homeTeam, awayTeam);
    }

    public final List<IMPActions> getAwayTeamActionList() {
        return awayTeam.getActions();
    }

    public final List<IMPActions> getHomeTeamActionList() {
        return homeTeam.getActions();
    }

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

    public final void recap() {
        printRecap(homeTeam);
        printRecap(awayTeam);
    }

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
