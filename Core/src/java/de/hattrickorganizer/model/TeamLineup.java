// %2621114898:de.hattrickorganizer.model%
package de.hattrickorganizer.model;

import java.util.ArrayList;
import java.util.List;

import plugins.ITeamLineup;


/**
 * Hattrick Lineup Object
 *
 * @author Draghetto HO
 */
public class TeamLineup implements ITeamLineup {
    //~ Instance fields ----------------------------------------------------------------------------

    /** List of players divided by area */
    private List<String>[] players = new List[4];
	//private ArrayList<String>[] players; TODO enable this line because it is more accurate with respect to the constructor

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Lineup object.
     */
    public TeamLineup() {
    	//players = new ArrayList[4]; TODO why is the following performed!? the static array is already initialized above!? But with wrong type
        for (int i = 0; i < 4; i++) {
            players[i] = new ArrayList<String>();
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the list of players that plays in the specified area
     *
     * @param area
     *
     * @return list of players
     */
    public final List<String> getArea(int area) {
        return players[area];
    }

    /**
     * Add a player to the lineup
     *
     * @param playerId
     * @param area
     */
    public final void add(String playerId, int area) {
        players[area].add(playerId);
    }
}
