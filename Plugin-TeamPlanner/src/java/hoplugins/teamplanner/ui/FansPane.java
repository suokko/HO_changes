// %816366948:hoplugins.teamplanner.ui%
package hoplugins.teamplanner.ui;

import hoplugins.Commons;
import hoplugins.TeamPlanner;

import hoplugins.teamplanner.ui.model.OperationData;
import hoplugins.teamplanner.ui.model.inner.NumericInner;
import hoplugins.teamplanner.ui.tabs.AbstractOperationPane;
import hoplugins.teamplanner.ui.tabs.fans.listener.FansCalculator;
import hoplugins.teamplanner.ui.tabs.fans.listener.FansMoraleCalculator;
import hoplugins.teamplanner.ui.tabs.fans.listener.SponsorMoraleCalculator;
import hoplugins.teamplanner.util.Util;
import hoplugins.teamplanner.vo.HTWeek;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava </a>
 */
public class FansPane extends AbstractOperationPane {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Missing Parameter Documentation */
    public static final int SPONSORMORALE_ROW = 0;

    /** Missing Parameter Documentation */
    public static final int FANSMORALE_ROW = 1;

    /** Missing Parameter Documentation */
    public static final int FANS_ROW = 2;

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
    public int getFansAt(HTWeek week) {
        int fans = Commons.getModel().getVerein().getFans();
        int columnIndex = getColumnIndex(week);

        for (int i = 0; i < columnIndex; i++) {
            OperationData data = Util.getOperationData(model, FANS_ROW, i);
            NumericInner ss = (NumericInner) data.getInner();
            fans = fans + (int) ss.getValue();
        }

        return fans;
    }

    /**
     * Missing Method Documentation
     *
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public double getFansMoraleAt(HTWeek week) {
        int columnIndex = getColumnIndex(week);
        OperationData data = Util.getOperationData(model, FANSMORALE_ROW, columnIndex);
        NumericInner ss = (NumericInner) data.getInner();
        return ss.getValue();
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
        TeamPlanner.getStadiumPane().refreshTable();
    }

    /**
     * Missing Method Documentation
     */
    protected void setRows() {
        addCalculatedRow("Sponsor Morale", new SponsorMoraleCalculator());
        addCalculatedRow("Fans Morale", new FansMoraleCalculator());
        addCalculatedRow("Fans", new FansCalculator());
    }

    /**
     * Missing Method Documentation
     */
    protected void loadInputData() {
    }
}
