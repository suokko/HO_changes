// %4244672601:hoplugins.teamplanner.ui.tabs.stadium%
package hoplugins.teamplanner.ui.tabs.stadium;

import hoplugins.teamplanner.ui.model.inner.InnerData;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class StadiumExpansionInner implements InnerData {
    //~ Instance fields ----------------------------------------------------------------------------

    private StadiumExpansionInner total = null;
    private int roofs;
    private int seats;
    private int terraces;
    private int vips;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public String getDescription() {
        if (getSize() > 0) {
            return "Build " + getSize();
        } else if (getSize() < 0) {
            return "Demolish " + (-getSize());
        }

        return "";
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getId() {
        return 1;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     *
     * @throws RuntimeException Missing Constructuor Exception Documentation
     */
    public void setMoney(int i) {
        throw new RuntimeException("Not Supported");
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getMoney() {
        double money = 0;

        if (getSize() != 0) {
            money = 10000;
        }

        money += getCost(terraces, 60, 6);
        money += getCost(seats, 100, 6);
        money += getCost(roofs, 120, 6);
        money += getCost(vips, 400, 6);
        return (int) money;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setRoofs(int i) {
        roofs = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getRoofs() {
        return roofs;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setSeats(int i) {
        seats = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getSize() {
        return vips + terraces + roofs + seats;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setTerraces(int i) {
        terraces = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getTerraces() {
        return terraces;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public StadiumExpansionInner getTotal() {
        if (total == null) {
            total = new StadiumExpansionInner();
        }

        return total;
    }

    /* (non-Javadoc)
     * @see hoplugins.teamplanner.ui.model.inner.InnerData#isValid()
     */
    public boolean isValid() {
        if (getTotal().roofs < 0) {
            return false;
        }

        if (getTotal().seats < 0) {
            return false;
        }

        if (getTotal().terraces < 0) {
            return false;
        }

        if (getTotal().vips < 0) {
            return false;
        }

        return true;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setVips(int i) {
        vips = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getVips() {
        return vips;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void addRoofs(int i) {
        roofs += i;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void addSeats(int i) {
        seats += i;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void addTerraces(int i) {
        terraces += i;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void addVips(int i) {
        vips += i;
    }

    /**
     * Missing Method Documentation
     *
     * @param number Missing Method Parameter Documentation
     * @param build Missing Method Parameter Documentation
     * @param destroy Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    private double getCost(double number, int build, int destroy) {
        if (number > 0) {
            return number * build;
        } else {
            return number * destroy;
        }
    }
}
