// %1437271404:hoplugins.teamplanner.ui.tabs.extra.listener%
package hoplugins.teamplanner.ui.tabs.extra.listener;

import hoplugins.TeamPlanner;

import hoplugins.teamplanner.ui.controller.input.InputListener;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.tabs.WeekHeader;
import hoplugins.teamplanner.ui.tabs.extra.StaffDialog;
import hoplugins.teamplanner.ui.tabs.extra.StaffInner;
import hoplugins.teamplanner.vo.HTWeek;

import plugins.IFutureTrainingManager;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class StaffListener extends InputListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private HTWeek week = null;
    private OperationCell targetCell = null;

    //~ Methods ------------------------------------------------------------------------------------

    //    private StaffInner original = null;

    /**
     * Missing Method Documentation
     *
     * @param newStaff Missing Method Parameter Documentation
     */
    public void setStaff(StaffInner newStaff) {
        StaffInner inner = (StaffInner) targetCell.getOperation().getInner();
        inner.addAssistantCoaches(newStaff.getAssistantCoaches());
        inner.addPhisio(newStaff.getPhisio());
        inner.addPsico(newStaff.getPsico());
        inner.addDoctor(newStaff.getDoctor());
        inner.addSpokesman(newStaff.getSpokesman());

        int columnIndex = WeekHeader.instance().getColumnIndex(week);

        for (int i = columnIndex; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            HTWeek theWeek = WeekHeader.instance().getColumnWeek(i);
            StaffInner ss = TeamPlanner.getExtrasPane().getStaff(theWeek);
            ss.addAssistantCoaches(newStaff.getAssistantCoaches());
            ss.addPhisio(newStaff.getPhisio());
            ss.addPsico(newStaff.getPsico());
            ss.addDoctor(newStaff.getDoctor());
            ss.addSpokesman(newStaff.getSpokesman());
        }
    }

    /**
     * Missing Method Documentation
     *
     * @param cell Missing Method Parameter Documentation
     * @param week Missing Constructuor Parameter Documentation
     */
    @Override
	public void doExecute(OperationCell cell, HTWeek week) {
        this.targetCell = cell;
        this.week = week;

        StaffDialog dialog = new StaffDialog(this, (StaffInner) cell.getOperation().getInner());
        dialog.setVisible(true);
    }
}
