// %2248761047:hoplugins.teamplanner.ui.tabs.recap.listener%
package hoplugins.teamplanner.ui.tabs.recap.listener;

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
public class WeekBaseRecapCalculator extends Calculator {
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
            int account = TeamPlanner.getStadiumPane().getBaseBalance(week);
            account = account + TeamPlanner.getPlayersPane().getBaseBalance(week);
            account = account + TeamPlanner.getExtrasPane().getBaseBalance(week);

            OperationCell cell = Util.getOperationCell(model, row, i);
            cell.getOperation().getInner().setMoney(account);
        }
    }
}
