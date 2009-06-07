// %1903166259:hoplugins.teamplanner.ui.controller.reaction%
package hoplugins.teamplanner.ui.controller.calculator;

import hoplugins.teamplanner.ui.controller.OperationListener;

import javax.swing.table.TableModel;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public abstract class Calculator extends OperationListener {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param row Missing Constructuor Parameter Documentation
     * @param model Missing Constructuor Parameter Documentation
     */
    public abstract void doCalculate(int row, TableModel model);
}
