// %2091670690:hoplugins.teamplanner.ui.tabs.stadium.listener%
package hoplugins.teamplanner.ui.tabs.stadium.listener;

import hoplugins.TeamPlanner;

import hoplugins.teamplanner.ui.controller.calculator.Calculator;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.tabs.WeekHeader;
import hoplugins.teamplanner.ui.tabs.stadium.StadiumExpansionInner;
import hoplugins.teamplanner.vo.HTWeek;

import plugins.IFutureTrainingManager;

import javax.swing.table.TableModel;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class StadiumMaintenanceCalculator extends Calculator {
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
            StadiumExpansionInner stadiumSize = TeamPlanner.getStadiumPane().getStadiumSize(week);
            double money = 0;
            money = money + (stadiumSize.getTerraces() * 0.5);
            money = money + (stadiumSize.getSeats() * 0.7);
            money = money + (stadiumSize.getRoofs() * 1.0);
            money = money + (stadiumSize.getVips() * 2.5);

            OperationCell cell = (OperationCell) model.getValueAt(row, i);
            cell.getOperation().getInner().setMoney((int) (-money));
        }
    }
}
