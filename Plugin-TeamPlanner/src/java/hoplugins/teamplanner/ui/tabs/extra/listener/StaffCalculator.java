// %1015556396:hoplugins.teamplanner.ui.tabs.extra.listener%
package hoplugins.teamplanner.ui.tabs.extra.listener;

import hoplugins.TeamPlanner;

import hoplugins.teamplanner.ui.controller.calculator.Calculator;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.tabs.WeekHeader;
import hoplugins.teamplanner.util.Util;
import hoplugins.teamplanner.vo.HTWeek;

import plugins.IFutureTrainingManager;

import javax.swing.table.TableModel;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class StaffCalculator extends Calculator {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param model Missing Method Parameter Documentation
     */
    @Override
	public void doCalculate(int row, TableModel model) {
        for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            HTWeek week = WeekHeader.instance().getColumnWeek(i);
            int money = TeamPlanner.getExtrasPane().getStaff(week).getMoney();
            OperationCell cell = Util.getOperationCell(model, row, i);
            cell.getOperation().getInner().setMoney(money);
        }
    }
}
