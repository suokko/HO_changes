package ho.core.model;

public class WorldDetailLeague {
	private int leagueId;
	private int countryId;
	private String countryName;
	private String continent;
	private String zoneName;
	
	
	public final int getLeagueId() {
		return leagueId;
	}
	public final void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}
	public final int getCountryId() {
		return countryId;
	}
	public final void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public final String getCountryName() {
		return countryName;
	}
	public final void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public final String getContinent() {
		return continent;
	}
	public final void setContinent(String continent) {
		this.continent = continent;
	}
	public final String getZoneName() {
		return zoneName;
	}
	public final void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	
	@Override
	public String toString(){
		return getCountryName();
	}
}
