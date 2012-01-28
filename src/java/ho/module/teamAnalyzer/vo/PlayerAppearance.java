// %381887291:hoplugins.teamAnalyzer.vo%
package ho.module.teamAnalyzer.vo;

/**
 * Class for collecting all the players appearance
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class PlayerAppearance {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Player Name */
    private String name;

    /** Number of appearence in that position */
    private int apperarence = 0;

    /** Player Id */
    private int playerId;

    /** Status of the player on the team. injured, sold etc */
    private int status;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Document Me!
     *
     * @return
     */
    public int getAppearance() {
        return apperarence;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setApperarence(int i) {
        apperarence = i;
    }

    /**
     * Document Me!
     *
     * @param string
     */
    public void setName(String string) {
        name = string;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setPlayerId(int i) {
        playerId = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setStatus(int i) {
        status = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * Increase number of Appearance for the player
     */
    public void addApperarence() {
        apperarence++;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Player[");
        buffer.append("apperarence = " + apperarence);
        buffer.append("playerId = " + playerId);
        buffer.append(", name = " + name);
        buffer.append("]");

        return buffer.toString();
    }
}
