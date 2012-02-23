package ho.core.model;

import ho.core.db.DBManager;

import java.util.HashMap;

public class WorldDetailsManager {

	private static WorldDetailsManager WMANAGER = null;
	private WorldDetailLeague[] leagues;
	private HashMap<Integer,WorldDetailLeague> countryMap = new HashMap<Integer,WorldDetailLeague>();
	private HashMap<Integer,WorldDetailLeague> leagueMap = new HashMap<Integer,WorldDetailLeague>();
	public static WorldDetailsManager instance(){
		if(WMANAGER == null){
			WMANAGER = new WorldDetailsManager();
		}
		return WMANAGER;
	}
	
	private WorldDetailsManager(){
		initialize();
	}
	
	private void initialize() {
		leagues = DBManager.instance().getAllWorldDetailLeagues();
		for (int i = 0; i < leagues.length; i++) {
			countryMap.put(Integer.valueOf(leagues[i].getCountryId()),leagues[i]);
			leagueMap.put(Integer.valueOf(leagues[i].getLeagueId()),leagues[i]);
		}
	}
	
	public int size(){
		return leagues.length;
	}
	
	public String getNameByCountryId(int countryId){
		return countryMap.get(Integer.valueOf(countryId)).getCountryName();
	}

	public WorldDetailLeague getWorldDetailLeagueByLeagueId(int leagueId){
		return leagueMap.get(Integer.valueOf(leagueId));
	}

	public final WorldDetailLeague[] getLeagues() {
		return leagues;
	}
	
	
	
}
