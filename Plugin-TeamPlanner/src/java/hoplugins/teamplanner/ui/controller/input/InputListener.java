// %2652626499:hoplugins.teamplanner.ui.controller.input%
package hoplugins.teamplanner.ui.controller.input;

import hoplugins.teamplanner.ui.controller.OperationListener;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.vo.HTWeek;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public abstract class InputListener extends OperationListener {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param cell Missing Constructuor Parameter Documentation
     */
    public abstract void doExecute(OperationCell cell, HTWeek week);
}
