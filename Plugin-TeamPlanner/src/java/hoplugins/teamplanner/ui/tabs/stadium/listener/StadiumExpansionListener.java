// %3038496927:hoplugins.teamplanner.ui.tabs.stadium.listener%
package hoplugins.teamplanner.ui.tabs.stadium.listener;

import hoplugins.TeamPlanner;

import hoplugins.teamplanner.ui.controller.input.InputListener;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.tabs.WeekHeader;
import hoplugins.teamplanner.ui.tabs.stadium.StadiumExpansionDialog;
import hoplugins.teamplanner.ui.tabs.stadium.StadiumExpansionInner;
import hoplugins.teamplanner.vo.HTWeek;

import plugins.IFutureTrainingManager;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class StadiumExpansionListener extends InputListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private HTWeek week = null;
    private OperationCell targetCell = null;

    //~ Methods ------------------------------------------------------------------------------------

    //    private StadiumExpansionInner original = null;

    /**
     * Missing Method Documentation
     *
     * @param newSize Missing Method Parameter Documentation
     */
    public void setStadiumSize(StadiumExpansionInner newSize) {
        StadiumExpansionInner inner = (StadiumExpansionInner) targetCell.getOperation().getInner();
        inner.addTerraces(newSize.getTerraces());
        inner.addVips(newSize.getVips());
        inner.addRoofs(newSize.getRoofs());
        inner.addSeats(newSize.getSeats());

        int columnIndex = WeekHeader.instance().getColumnIndex(week);

        for (int i = columnIndex; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            HTWeek theWeek = WeekHeader.instance().getColumnWeek(i);
            StadiumExpansionInner ss = TeamPlanner.getStadiumPane().getStadiumSize(theWeek);
            ss.addRoofs(newSize.getRoofs());
            ss.addSeats(newSize.getSeats());
            ss.addTerraces(newSize.getTerraces());
            ss.addVips(newSize.getVips());
        }
    }

    /**
     * Missing Method Documentation
     *
     * @param cell Missing Method Parameter Documentation
     * @param week Missing Constructuor Parameter Documentation
     */
    public void doExecute(OperationCell cell, HTWeek week) {
        this.targetCell = cell;
        this.week = week;

        StadiumExpansionDialog dialog = new StadiumExpansionDialog(this,
                                                                   (StadiumExpansionInner) cell.getOperation()
                                                                                               .getInner());
        dialog.setVisible(true);
    }
}
