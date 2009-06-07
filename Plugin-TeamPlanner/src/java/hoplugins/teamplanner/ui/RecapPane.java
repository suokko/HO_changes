// %954716945:hoplugins.teamplanner.ui%
package hoplugins.teamplanner.ui;

import hoplugins.teamplanner.ui.tabs.AbstractOperationPane;
import hoplugins.teamplanner.ui.tabs.recap.listener.TotalRecapCalculator;
import hoplugins.teamplanner.ui.tabs.recap.listener.WeekBaseRecapCalculator;
import hoplugins.teamplanner.ui.tabs.recap.listener.WeekRecapCalculator;
import hoplugins.teamplanner.vo.HTWeek;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava </a>
 */
public class RecapPane extends AbstractOperationPane {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Missing Parameter Documentation */
    public static final int WEEKBASE_ROW = 0;

    /** Missing Parameter Documentation */
    public static final int WEEKTOTAL_ROW = 1;

    /** Missing Parameter Documentation */
    public static final int TOTAL_ROW = 2;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getBaseBalance(HTWeek week) {
        return 0;
    }

    /**
     * Missing Method Documentation
     *
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getFullBalance(HTWeek week) {
        return getBaseBalance(week);
    }

    /**
     * Missing Method Documentation
     */
    public void onChange() {
        // No Panel need to be notified of this		
    }

    /**
     * Missing Method Documentation
     */
    protected void setRows() {
        addCalculatedRow("Week Base", new WeekBaseRecapCalculator());
        addCalculatedRow("Week Total", new WeekRecapCalculator());
        addCalculatedRow("Running Total", new TotalRecapCalculator());
    }

    /**
     * Missing Method Documentation
     */
    protected void loadInputData() {
    }
}
