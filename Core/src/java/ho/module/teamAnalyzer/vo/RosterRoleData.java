// %2218737670:hoplugins.teamAnalyzer.vo%
package ho.module.teamAnalyzer.vo;

import ho.core.model.player.SpielerPosition;
import ho.core.util.HelperWrapper;



/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class RosterRoleData {
    //~ Instance fields ----------------------------------------------------------------------------

    private double avg;
    private double max;
    private double min;
    private int app;
    private int pos;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RoleData object.
     *
     * @param pos TODO Missing Constructuor Parameter Documentation
     */
    public RosterRoleData(int pos) {
        this.pos = pos;
        this.max = -1;
        this.min = 10000;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getApp() {
        return app;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getAvg() {
        return avg;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getMax() {
        return max;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getMin() {
        return min;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getPos() {
        return pos;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getPositionDesc() {
        int posCode = HelperWrapper.instance().getPosition(pos);

        return SpielerPosition.getNameForPosition((byte) posCode);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param rating TODO Missing Method Parameter Documentation
     */
    public void addMatch(double rating) {
        if (rating > max) {
            max = rating;
        }

        if (rating < min) {
            min = rating;
        }

        avg = ((avg * app) + rating) / (app + 1d);
        app++;
    }
}
