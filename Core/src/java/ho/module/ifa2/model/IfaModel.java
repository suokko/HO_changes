package ho.module.ifa2.model;

import ho.core.db.DBManager;
import ho.core.model.WorldDetailsManager;
import ho.module.ifa.IfaMatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IfaModel {

	public List<IfaMatch> visited = new ArrayList<IfaMatch>();
	public List<IfaMatch> hosted = new ArrayList<IfaMatch>();

	public IfaModel() {
		init();
	}

	private void init() {
		this.visited.addAll(Arrays.asList(DBManager.instance().getIFAMatches(false)));
		this.hosted.addAll(Arrays.asList(DBManager.instance().getIFAMatches(true)));
	}

	public boolean isHosted(int leagueId) {
		for (IfaMatch match : this.hosted) {
			if (leagueId == match.getAwayLeagueId()) {
				return true;
			}
		}
		return false;
	}

	public boolean isVisited(int leagueId) {
		for (IfaMatch match : this.visited) {
			if (leagueId == match.getHomeLeagueId()) {
				return true;
			}
		}
		return false;
	}

	public List<IfaStatistic> getVisitedStatistic() {
		Map<Integer, IfaStatistic> map = new HashMap<Integer, IfaStatistic>();
		for (IfaMatch match : this.visited) {
			Integer id = Integer.valueOf(WorldDetailsManager.instance()
					.getWorldDetailLeagueByLeagueId(match.getHomeLeagueId()).getCountryId());
			IfaStatistic stat = map.get(id);
			if (stat == null) {
				stat = new IfaStatistic();
				stat.setLeague(new Country(id));
				map.put(id, stat);
			}

			updateStats(stat, match, true);
		}
		return new ArrayList<IfaStatistic>(map.values());
	}

	public List<IfaStatistic> getHostedStatistic() {
		Map<Integer, IfaStatistic> map = new HashMap<Integer, IfaStatistic>();
		for (IfaMatch match : this.hosted) {
			Integer id = Integer.valueOf(WorldDetailsManager.instance()
					.getWorldDetailLeagueByLeagueId(match.getHomeLeagueId()).getCountryId());
			IfaStatistic stat = map.get(id);
			if (stat == null) {
				stat = new IfaStatistic();
				stat.setLeague(new Country(id));
				map.put(id, stat);
			}

			updateStats(stat, match, false);
		}
		return new ArrayList<IfaStatistic>(map.values());
	}

	private void updateStats(IfaStatistic stat, IfaMatch match, boolean away) {
		stat.increasePlayed();

		if (match.getHomeTeamGoals() == match.getAwayTeamGoals()) {
			stat.increaseDraw();
		} else {
			if (match.getHomeTeamGoals() < match.getAwayTeamGoals()) {
				if (away) {
					stat.increaseWon();
				} else {
					stat.increaseLost();
				}
			} else if (match.getHomeTeamGoals() > match.getAwayTeamGoals()) {
				if (away) {
					stat.increaseLost();
				} else {
					stat.increaseWon();
				}
			}
		}
	}
}
