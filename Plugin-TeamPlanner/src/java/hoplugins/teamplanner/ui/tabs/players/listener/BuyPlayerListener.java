// %3762500085:hoplugins.teamplanner.ui.tabs.players.listener%
package hoplugins.teamplanner.ui.tabs.players.listener;

import hoplugins.TeamPlanner;

import hoplugins.teamplanner.ui.controller.input.CallableListener;
import hoplugins.teamplanner.ui.controller.input.InputListener;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.tabs.players.BuyPlayerDialog;
import hoplugins.teamplanner.ui.tabs.players.PriceDialog;
import hoplugins.teamplanner.vo.HTWeek;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class BuyPlayerListener extends InputListener implements CallableListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private BuyPlayerDialog dialog = new BuyPlayerDialog(this);
    private HTWeek week;
    private PriceDialog d = new PriceDialog(this);

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param cell Missing Method Parameter Documentation
     * @param week Missing Constructuor Parameter Documentation
     */
    @Override
	public void doExecute(OperationCell cell, HTWeek week) {
        this.week = week;
        dialog.reload(week);
        dialog.setVisible(true);
    }

    /**
     *
     */
    public void doTriggerEvent(int code) {
        if (code == SELECTION) {
            d.reload(dialog.getPlayer(), week);
            d.setVisible(true);
        } else if (code == CONFIRMATION) {
            TeamPlanner.getPlayersPane().buyPlayer(d);
        } else if (code == CANCEL) {
            TeamPlanner.getPlayersPane().cancelTransfer(dialog);
            d.setVisible(false);
        }
    }
}
