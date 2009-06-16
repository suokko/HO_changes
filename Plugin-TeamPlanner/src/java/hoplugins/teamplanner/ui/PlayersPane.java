// %618078072:hoplugins.teamplanner.ui%
package hoplugins.teamplanner.ui;

import hoplugins.Commons;
import hoplugins.TeamPlanner;

import hoplugins.teamplanner.manager.TeamPlayerManager;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.tabs.AbstractOperationPane;
import hoplugins.teamplanner.ui.tabs.WeekHeader;
import hoplugins.teamplanner.ui.tabs.players.BuyPlayerDialog;
import hoplugins.teamplanner.ui.tabs.players.PlayersInner;
import hoplugins.teamplanner.ui.tabs.players.PriceDialog;
import hoplugins.teamplanner.ui.tabs.players.SellDialog;
import hoplugins.teamplanner.ui.tabs.players.listener.BuyPlayerListener;
import hoplugins.teamplanner.ui.tabs.players.listener.SalaryCalculator;
import hoplugins.teamplanner.ui.tabs.players.listener.SellPlayerListener;
import hoplugins.teamplanner.ui.tabs.players.listener.TraineesListener;
import hoplugins.teamplanner.util.Util;
import hoplugins.teamplanner.vo.HTWeek;

import plugins.IFutureTrainingManager;
import plugins.ISpieler;

import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava </a>
 */
public class PlayersPane extends AbstractOperationPane {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -7018115766401880271L;

	/** Missing Parameter Documentation */
    public static final int TRAINEES_ROW = 0;

    /** Missing Parameter Documentation */
    public static final int BUY_ROW = 1;

    /** Missing Parameter Documentation */
    public static final int SELL_ROW = 2;

    /** Missing Parameter Documentation */
    public static final int EXTRA_ROW = 3;

    /** Missing Parameter Documentation */
    public static final int TRADING_ROW = 4;

    /** Missing Parameter Documentation */
    public static final int SALARY_ROW = 5;

    //~ Methods ------------------------------------------------------------------------------------

    // ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    @Override
	public int getBaseBalance(HTWeek week) {
        int columnIndex = getColumnIndex(week);
        return Util.getOperationCell(model, SALARY_ROW, columnIndex).getBalance();
    }

    /**
     * Missing Method Documentation
     *
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    @Override
	public int getFullBalance(HTWeek week) {
        int total = getBaseBalance(week);
        int columnIndex = getColumnIndex(week);

        for (int row = 1; row < 5; row++) {
            total = total + Util.getOperationCell(model, row, columnIndex).getBalance();
        }

        return total;
    }

    /**
     * DOCUMENT ME!
     *
     * @param pd
     */
    public void buyPlayer(PriceDialog pd) {
        ISpieler player = pd.getPlayer();
        HTWeek week = pd.getweek();
        TeamPlayerManager.instance().buyPlayer(week, player);

        double price = pd.getPrice();
        pd.setVisible(false);
        refreshSkillups();

        OperationCell cell = Util.getOperationCell(model, BUY_ROW, getColumnIndex(week));
        PlayersInner inner = new PlayersInner();
        inner.setPlayer(player);
        inner.setMoney((int) -price);
        cell.addOperation(inner);

        refreshTable();
    }

    /**
     * DOCUMENT ME!
     *
     * @param dialog
     */
    public void cancelTransfer(SellDialog dialog) {
        ISpieler player = dialog.getPlayer();
        HTWeek week = dialog.getWeek();
        TeamPlayerManager.instance().cancelTransfer(player.getSpielerID(), true);
        dialog.setVisible(false);
        refreshSkillups();

        OperationCell cell = Util.getOperationCell(model, SELL_ROW, getColumnIndex(week));
        cell.removeOperation(player.getSpielerID());
        refreshTable();
    }

    /**
     * DOCUMENT ME!
     *
     * @param dialog
     */
    public void cancelTransfer(BuyPlayerDialog dialog) {
        ISpieler player = dialog.getPlayer();
        HTWeek week = dialog.getWeek();
        TeamPlayerManager.instance().cancelTransfer(player.getSpielerID(), false);
        dialog.setVisible(false);
        refreshSkillups();

        OperationCell cell = Util.getOperationCell(model, BUY_ROW, getColumnIndex(week));
        cell.removeOperation(player.getSpielerID());
        refreshTable();
    }

    /**
     * Missing Method Documentation
     */
    @Override
	public void onChange() {
        TeamPlanner.getRecapPane().refreshTable();
    }

    /**
     * Missing Method Documentation
     *
     * @param pd Missing Constructuor Parameter Documentation
     */
    public void sellPlayer(PriceDialog pd) {
        ISpieler player = pd.getPlayer();
        HTWeek week = pd.getweek();
        HTWeek old = TeamPlayerManager.instance().getSellingWeek(player.getSpielerID());

        // Player was already scheduled to be sold on another date
        boolean insert = true;

        if (old != null) {
            int update = JOptionPane.showConfirmDialog(Commons.getModel().getGUI().getOwner4Dialog(),
                                                       "Conflict",
                                                       "Player Already scheduled to be sold. Move it?",
                                                       JOptionPane.YES_NO_OPTION);

            if (update == JOptionPane.YES_OPTION) {
                insert = true;
            }

            if (insert) {
                OperationCell cell = Util.getOperationCell(model, SELL_ROW, getColumnIndex(old));
                cell.removeOperation(player.getSpielerID());
            }
        }

        if (insert) {
            TeamPlayerManager.instance().sellPlayer(week, player.getSpielerID());
        }

        double price = pd.getPrice();
        pd.setVisible(false);
        refreshSkillups();

        OperationCell cell = Util.getOperationCell(model, SELL_ROW, getColumnIndex(week));
        PlayersInner inner = new PlayersInner();
        inner.setPlayer(player);
        inner.setMoney((int) price);
        cell.addOperation(inner);

        refreshTable();
    }

    /**
     * Missing Method Documentation
     */
    @Override
	protected void setRows() {
        addInputRow("Trainees", new TraineesListener(), true, new PlayersInner());
        addInputRow("Buy", new BuyPlayerListener(), true, new PlayersInner());
        addInputRow("Sell", new SellPlayerListener(), true, new PlayersInner());
        addManualRow("Extra");
        addManualRow("Trading");
        addCalculatedRow("Salary", new SalaryCalculator());
    }

    /**
     * Missing Method Documentation
     */
    @Override
	protected void loadInputData() {
        refreshSkillups();
    }

    /**
     *
     */
    private void refreshSkillups() {
        for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            OperationCell data = Util.getOperationCell(model, TRAINEES_ROW, i);
            data.clean();
        }

        int columnIndex = 0;

        for (Iterator<List<String>> iter = TeamPlayerManager.instance().getPredictedSkillup().iterator();
             iter.hasNext();) {
            List<String> weekList = iter.next();
            OperationCell cell = Util.getOperationCell(model, TRAINEES_ROW, columnIndex);

            for (Iterator<String> iterator = weekList.iterator(); iterator.hasNext();) {
                String playerId = iterator.next();
                ISpieler player = TeamPlayerManager.instance().getPredictedPlayerAtWeek(Integer
                                                                                        .parseInt(playerId),
                                                                                        WeekHeader.instance()
                                                                                                  .getColumnWeek(columnIndex));
                PlayersInner inner = new PlayersInner();
                inner.setPlayer(player);
                cell.addOperation(inner);
            }

            columnIndex++;
        }
    }
}
