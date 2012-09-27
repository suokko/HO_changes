package ho.module.ifa;

public class IfaMatch {

	private int matchId;
	private String playedDateString;
	private int homeTeamId;
	private int awayTeamId;
	private int homeLeagueId;
	private int awayLeagueId;
	private int awayTeamGoals;
	private int homeTeamGoals;

	public final int getMatchId() {
		return matchId;
	}

	public final void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public final String getPlayedDateString() {
		return playedDateString;
	}

	public final void setPlayedDateString(String playedDateString) {
		this.playedDateString = playedDateString;
	}

	public final int getHomeTeamId() {
		return homeTeamId;
	}

	public final void setHomeTeamId(int homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public final int getAwayTeamId() {
		return awayTeamId;
	}

	public final void setAwayTeamId(int awayTeamId) {
		this.awayTeamId = awayTeamId;
	}

	public final int getHomeLeagueId() {
		return homeLeagueId;
	}

	public final void setHomeLeagueId(int homeLeagueId) {
		this.homeLeagueId = homeLeagueId;
	}

	public final int getAwayLeagueId() {
		return awayLeagueId;
	}

	public final void setAwayLeagueId(int awayLeagueId) {
		this.awayLeagueId = awayLeagueId;
	}

	public final int getAwayTeamGoals() {
		return awayTeamGoals;
	}

	public final void setAwayTeamGoals(int awayTeamGoals) {
		this.awayTeamGoals = awayTeamGoals;
	}

	public final int getHomeTeamGoals() {
		return homeTeamGoals;
	}

	public final void setHomeTeamGoals(int homeTeamGoals) {
		this.homeTeamGoals = homeTeamGoals;
	}
}
