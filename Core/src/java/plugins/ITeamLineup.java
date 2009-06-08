// %1127327012431:plugins%
package plugins;

import java.util.List;


/**
 * TODO Missing Interface Documentation
 *
 * @author TODO Author Name
 */
public interface ITeamLineup {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Index for Players that played Keeper */
    public static final int KEEPER = 0;

    /** Index for Players that played in Defence */
    public static final int DEFENCE = 1;

    /** Index for Players that played in Midfield */
    public static final int MIDFIELD = 2;

    /** Index for Players that played in Attack */
    public static final int ATTACK = 3;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the list of players that plays in the specified area
     *
     * @param area
     *
     * @return list of players
     */
    public abstract List<String> getArea(int area);

    /**
     * Add a player to the lineup
     *
     * @param playerId
     * @param area
     */
    public void add(String playerId, int area);
}
