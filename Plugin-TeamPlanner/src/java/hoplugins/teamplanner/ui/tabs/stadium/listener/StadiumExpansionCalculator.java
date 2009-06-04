// %3854205197:hoplugins.teamplanner.ui.tabs.stadium.listener%
package hoplugins.teamplanner.ui.tabs.stadium.listener;

import hoplugins.teamplanner.ui.StadiumPane;
import hoplugins.teamplanner.ui.controller.calculator.Calculator;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.util.Util;

import plugins.IFutureTrainingManager;

import javax.swing.table.TableModel;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class StadiumExpansionCalculator extends Calculator {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param model Missing Method Parameter Documentation
     */
    public void doCalculate(int row, TableModel model) {
        for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            int money = Util.getOperationCell(model, StadiumPane.SIZE_ROW, i).getBalance();
            OperationCell cell = Util.getOperationCell(model, row, i);
            cell.getOperation().getInner().setMoney(-money);
        }
    }
}
