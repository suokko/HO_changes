// %1127326826822:plugins%
package plugins;

/**
 * EPV Data Holder interface
 *
 * @author draghetto
 */
public interface IEPVData extends IPlayerData {
	public static final int KEEPER = 1;
	public static final int DEFENDER = 2;
	public static final int MIDFIELDER = 3;
	public static final int WINGER = 4;
	public static final int ATTACKER = 5;
	//~ Methods ------------------------------------------------------------------------------------
    
    public abstract double getMaxSkill();
	public abstract int getPlayerType();
     
}
