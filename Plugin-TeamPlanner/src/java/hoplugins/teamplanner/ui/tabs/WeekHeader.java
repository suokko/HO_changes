// %389915548:hoplugins.teamplanner.ui.tabs%
package hoplugins.teamplanner.ui.tabs;

import hoplugins.TeamPlanner;

import hoplugins.teamplanner.vo.HTWeek;

import plugins.IFutureTrainingManager;

import java.util.Vector;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class WeekHeader {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static WeekHeader _me = null;

    //~ Instance fields ----------------------------------------------------------------------------

    private Vector<HTWeek> columns;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new WeekHeader object.
     */
    private WeekHeader() {
        reload();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public static WeekHeader instance() {
        if (_me == null) {
            _me = new WeekHeader();
        }

        return _me;
    }

    /**
     * Missing Method Documentation
     *
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getColumnIndex(HTWeek week) {
        return columns.indexOf(week);
    }

    /**
     * Missing Method Documentation
     *
     * @param columnIndex Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public HTWeek getColumnWeek(int columnIndex) {
        return columns.get(columnIndex);
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public Vector<HTWeek> getValues() {
        return columns;
    }

    /**
     * Missing Method Documentation
     */
    public void reload() {
        this.columns = new Vector<HTWeek>();

        for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            HTWeek week = TeamPlanner.ACTUALWEEK.copy();
            week.addWeek(i + 1);
            columns.add(week);
        }
    }
}
