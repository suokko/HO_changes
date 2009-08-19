// %3016203037:hoplugins.teamplanner.ui.tabs.players.listener%
package hoplugins.teamplanner.ui.tabs.players.listener;

import hoplugins.TeamPlanner;

import hoplugins.teamplanner.manager.TeamPlayerManager;
import hoplugins.teamplanner.ui.controller.input.CallableListener;
import hoplugins.teamplanner.ui.controller.input.InputListener;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.tabs.players.PriceDialog;
import hoplugins.teamplanner.ui.tabs.players.SellDialog;
import hoplugins.teamplanner.vo.HTWeek;

import plugins.ISpieler;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class SellPlayerListener extends InputListener implements CallableListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private PriceDialog d = new PriceDialog(this);
    private SellDialog dialog = new SellDialog(this);

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param cell Missing Method Parameter Documentation
     * @param week Missing Constructuor Parameter Documentation
     */
    @Override
	public void doExecute(OperationCell cell, HTWeek week) {
        dialog.reload(week);
        dialog.setVisible(true);
    }

    /**
     *
     */
    public void doTriggerEvent(int code) {
        if (code == SELECTION) {
            ISpieler sp = TeamPlayerManager.instance().getPredictedPlayerAtWeek(dialog.getPlayer()
                                                                                      .getSpielerID(),
                                                                                dialog.getWeek());
            d.reload(sp, dialog.getWeek());
            d.setFixedSell();
            d.setVisible(true);
        } else if (code == CONFIRMATION) {
            TeamPlanner.getPlayersPane().sellPlayer(d);
        } else if (code == CANCEL) {
            TeamPlanner.getPlayersPane().cancelTransfer(dialog);
            d.setVisible(false);
        }
    }
}
