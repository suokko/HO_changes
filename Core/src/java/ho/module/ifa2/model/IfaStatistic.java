package ho.module.ifa2.model;

import java.util.Date;

public class IfaStatistic {

	private int matchesPlayed;
	private int matchesWon;
	private int matchesDraw;
	private int matchesLost;
	private Country country;

	public Country getCountry() {
		return this.country;
	}

	public void setLeague(Country league) {
		this.country = league;
	}

	public int getMatchesPlayed() {
		return this.matchesPlayed;
	}

	public int getMatchesWon() {
		return this.matchesWon;
	}

	public int getMatchesDraw() {
		return this.matchesDraw;
	}

	public int getMatchesLost() {
		return this.matchesLost;
	}

	public Date getLastMatchDate() {
		return new Date();
	}

	public void increasePlayed() {
		this.matchesPlayed++;
	}

	public void increaseWon() {
		this.matchesWon++;
	}

	public void increaseDraw() {
		this.matchesDraw++;
	}

	public void increaseLost() {
		this.matchesLost++;
	}
}
