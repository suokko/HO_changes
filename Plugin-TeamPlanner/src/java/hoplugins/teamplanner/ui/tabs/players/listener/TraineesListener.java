// %2177664346:hoplugins.teamplanner.ui.tabs.players.listener%
package hoplugins.teamplanner.ui.tabs.players.listener;

import hoplugins.TeamPlanner;

import hoplugins.teamplanner.ui.controller.input.CallableListener;
import hoplugins.teamplanner.ui.controller.input.InputListener;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.tabs.players.PriceDialog;
import hoplugins.teamplanner.ui.tabs.players.TraineesDialog;
import hoplugins.teamplanner.vo.HTWeek;

import plugins.ISpieler;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class TraineesListener extends InputListener implements CallableListener {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Missing Parameter Documentation */
    HTWeek week = null;

    /** Missing Parameter Documentation */
    PriceDialog priceDialog = new PriceDialog(this);

    // ------------------------------------------------------------------------------------

    /** Missing Parameter Documentation */
    TraineesDialog traineesDialog = new TraineesDialog(this);

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param cell Missing Method Parameter Documentation
     * @param week Missing Constructuor Parameter Documentation
     */
    public void doExecute(OperationCell cell, HTWeek week) {
        this.week = week;

        if (cell.getOperationList().size() > 0) {
            traineesDialog.reload(cell.getOperationList());
            traineesDialog.setVisible(true);
        }
    }

    /**
     *
     */
    public void doTriggerEvent(int code) {
        if (code == SELECTION) {
            ISpieler tp = traineesDialog.getPlayer();
            priceDialog.reload(tp, week);
            priceDialog.setVisible(true);
        } else if (code == CONFIRMATION) {
            TeamPlanner.getPlayersPane().sellPlayer(priceDialog);
            traineesDialog.setVisible(false);
        }
    }
}
