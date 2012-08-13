package ho.module.ifa.table;

import ho.core.model.WorldDetailLeague;
import ho.module.ifa.DateHelper;
import ho.module.ifa.IfaMatch;

import java.util.Date;

class FriendlyStatistik {
	private WorldDetailLeague league;
	private int homeWon;
	private int draw;
	private int homeLost;
	private Date lastPlayedDate;
	private int homeGoals;
	private int awayGoals;
	
	FriendlyStatistik(WorldDetailLeague worldDetailLeague){
		this.league = worldDetailLeague;
	}
	
	void add(IfaMatch match){
		homeGoals += match.getHomeTeamGoals();
		awayGoals += match.getAwayTeamGoals();
		if( match.getHomeTeamGoals() == match.getAwayTeamGoals())
			draw++;
		if(match.getHomeTeamGoals() > match.getAwayTeamGoals())
			homeWon++;
		if(match.getHomeTeamGoals() < match.getAwayTeamGoals())
			homeLost++;
		
		lastPlayedDate = DateHelper.getDate(match.getPlayedDateString());
	}
	
	public final WorldDetailLeague getLeague() {
		return league;
	}
	public final int getHomeWon() {
		return homeWon;
	}
	public final int getDraw() {
		return draw;
	}
	public final int getHomeLost() {
		return homeLost;
	}
	public final int getCoolness() {
		return 0;
	}
	public final Date getLastPlayedDate() {
		return lastPlayedDate;
	}
	public final int getHomeGoals() {
		return homeGoals;
	}
	public final int getAwayGoals() {
		return awayGoals;
	}
	public final int getTotal(){
		return getHomeWon()+getDraw()+getHomeLost();
	}
	
}
