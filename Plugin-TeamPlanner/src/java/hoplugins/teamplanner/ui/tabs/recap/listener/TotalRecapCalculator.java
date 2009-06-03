// %3971831552:hoplugins.teamplanner.ui.tabs.recap.listener%
package hoplugins.teamplanner.ui.tabs.recap.listener;

import hoplugins.teamplanner.ui.RecapPane;
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
public class TotalRecapCalculator extends Calculator {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param model Missing Method Parameter Documentation
     */
    public void doCalculate(int row, TableModel model) {
        int account = 10000;

        for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            account += Util.getOperationCell(model, RecapPane.WEEKTOTAL_ROW, i).getBalance();

            OperationCell cell = Util.getOperationCell(model, row, i);
            cell.getOperation().getInner().setMoney(account);
        }
    }
}
