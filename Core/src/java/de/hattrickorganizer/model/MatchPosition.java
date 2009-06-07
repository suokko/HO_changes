// %4112174284:de.hattrickorganizer.model%
package de.hattrickorganizer.model;


// %1110567469328:hoplugins.commons.model%

/**
 * Player position class
 *
 * @author Draghetto HO
 */
public class MatchPosition {
    //~ Instance fields ----------------------------------------------------------------------------

    private String name;
    private int playerID;
    private int position;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param string TODO Missing Method Parameter Documentation
     */
    public final void setName(String string) {
        name = string;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the PlayerId
     *
     * @param playerId
     */
    public final void setPlayerID(int playerId) {
        this.playerID = playerId;
    }

    /**
     * Gets the PlayerId
     *
     * @return playerId
     */
    public final int getPlayerID() {
        return playerID;
    }

    /**
     * Set the player position
     *
     * @param pos
     */
    public final void setPosition(int pos) {
        position = pos;
    }

    /**
     * Get the player position
     *
     * @return the position
     */
    public final int getPosition() {
        return position;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    public final String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("MatchPosition[");
        buffer.append("name = " + name);
        buffer.append(", playerID = " + playerID);
        buffer.append(", position = " + position);
        buffer.append("]");
        return buffer.toString();
    }
}
