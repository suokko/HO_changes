package ho.module.ifa.model;

import ho.core.db.DBManager;
import ho.core.model.WorldDetailsManager;
import ho.module.ifa.IfaMatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IfaModel {

	private final List<IfaMatch> visited = new ArrayList<IfaMatch>();
	private final List<IfaMatch> hosted = new ArrayList<IfaMatch>();
	private List<IfaStatistic> hostedStatistic;
	private List<IfaStatistic> visitedStatistic;
	private final List<ModelChangeListener> listeners = new ArrayList<ModelChangeListener>();

	public IfaModel() {
		init();
	}

	private void init() {
		this.visited.clear();
		this.hosted.clear();
		this.visited.addAll(Arrays.asList(DBManager.instance().getIFAMatches(false)));
		this.hosted.addAll(Arrays.asList(DBManager.instance().getIFAMatches(true)));
		fireModelChanged();
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
		if (this.visitedStatistic == null) {
			createVisitedStatistic();
		}
		return this.visitedStatistic;
	}

	public List<IfaStatistic> getHostedStatistic() {
		if (this.hostedStatistic == null) {
			createHostedStatistic();
		}
		return this.hostedStatistic;
	}

	public void addModelChangeListener(ModelChangeListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	public void removeModelChangeListener(ModelChangeListener listener) {
		this.listeners.remove(listener);
	}

	public void reload() {
		this.visitedStatistic = null;
		this.hostedStatistic = null;
		init();
	}

	public int getVistedCountriesCount() {
		return getVisitedStatistic().size();
	}

	public int getHostedCountriesCount() {
		return getHostedStatistic().size();
	}

	private void fireModelChanged() {
		for (int i = this.listeners.size() - 1; i >= 0; i--) {
			this.listeners.get(i).modelChanged();
		}
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

		long matchTimestamp = match.getPlayedDate().getTime();
		if (stat.getLastMatchDate() < matchTimestamp) {
			stat.setLastMatchDate(matchTimestamp);
		}
	}

	private void createVisitedStatistic() {
		Map<Integer, IfaStatistic> map = new HashMap<Integer, IfaStatistic>();
		for (IfaMatch match : this.visited) {
			Integer id = Integer.valueOf(WorldDetailsManager.instance()
					.getWorldDetailLeagueByLeagueId(match.getHomeLeagueId()).getCountryId());
			IfaStatistic stat = map.get(id);
			if (stat == null) {
				stat = new IfaStatistic();
				stat.setCountry(new Country(id));
				map.put(id, stat);
			}

			updateStats(stat, match, true);
		}
		this.visitedStatistic = new ArrayList<IfaStatistic>(map.values());
	}

	private void createHostedStatistic() {
		Map<Integer, IfaStatistic> map = new HashMap<Integer, IfaStatistic>();
		for (IfaMatch match : this.hosted) {
			Integer id = Integer.valueOf(WorldDetailsManager.instance()
					.getWorldDetailLeagueByLeagueId(match.getHomeLeagueId()).getCountryId());
			IfaStatistic stat = map.get(id);
			if (stat == null) {
				stat = new IfaStatistic();
				stat.setCountry(new Country(id));
				map.put(id, stat);
			}

			updateStats(stat, match, false);
		}
		this.hostedStatistic = new ArrayList<IfaStatistic>(map.values());
	}

}
