package plugins;

/**
 * Value Holder objects that holds all the match simulation data
 * 
 * @author Draghetto
 */
public interface IMatchResult {
	
	public abstract int getGuestChances();
	public abstract int[] getGuestFailed();
	public abstract int getGuestGoals();
	public abstract int[] getGuestSuccess();
	public abstract int getHomeChances();
	public abstract int[] getHomeFailed();
	public abstract int getHomeGoals();
	public abstract int[] getHomeSuccess();
	public abstract int getHomeWin();
	public abstract int getAwayWin();
	public abstract int getDraw();	
	public abstract int getMatchNumber();
	public abstract int[] getResultDetail();	
}
