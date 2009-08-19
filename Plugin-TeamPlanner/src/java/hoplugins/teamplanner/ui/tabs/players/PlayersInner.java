// %2273921580:hoplugins.teamplanner.ui.tabs.players%
package hoplugins.teamplanner.ui.tabs.players;

import hoplugins.teamplanner.ui.model.inner.InnerData;

import plugins.ISpieler;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class PlayersInner implements InnerData {
    //~ Instance fields ----------------------------------------------------------------------------

    private ISpieler player;
    private int money;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public String getDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append(player.getName());
        sb.append(" (");
        sb.append(player.getSpielerID());
        return sb.toString();
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getId() {
        return player.getSpielerID();
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setMoney(int i) {
        this.money = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getMoney() {
        return money;
    }

    /**
     * DOCUMENT ME!
     *
     * @param player
     */
    public void setPlayer(ISpieler player) {
        this.player = player;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public ISpieler getPlayer() {
        return player;
    }

    /* (non-Javadoc)
     * @see hoplugins.teamplanner.ui.model.inner.InnerData#isValid()
     */
    public boolean isValid() {
        return true;
    }
}
