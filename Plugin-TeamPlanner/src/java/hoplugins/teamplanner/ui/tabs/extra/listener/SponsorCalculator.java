// %495398834:hoplugins.teamplanner.ui.tabs.extra.listener%
package hoplugins.teamplanner.ui.tabs.extra.listener;

import hoplugins.Commons;
import hoplugins.TeamPlanner;

import hoplugins.commons.utils.SeriesUtil;

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
public class SponsorCalculator extends Calculator {
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

            int spokesman = TeamPlanner.getExtrasPane().getStaff(week).getSpokesman();

            // Bonus due to spokesman 		
            double sponsors = spokesman * 1000;
            String liga = Commons.getModel().getLiga().getLiga();
            int level = getLevelSponsor(SeriesUtil.getSeriesLevel(liga));

            // Bonus due to series
            sponsors = sponsors + (level * getWeekMultiplier(spokesman, week.getWeek()));

            int fans = TeamPlanner.getFansPane().getFansAt(week);

            // Bonus due to fans
            sponsors = sponsors + (fans * 27.5);

            OperationCell cell = Util.getOperationCell(model, row, i);
            cell.getOperation().getInner().setMoney((int) sponsors);
        }
    }

    /**
     * Missing Method Documentation
     *
     * @param level Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    private int getLevelSponsor(int level) {
        switch (level) {
            case 1:
                return 120000;

            case 2:
                return 60000;

            case 3:
                return 25000;

            case 4:
                return 18000;

            case 5:
                return 14000;

            case 6:
                return 11000;
        }

        return 10000;
    }

    /**
     * Missing Method Documentation
     *
     * @param spokesman Missing Method Parameter Documentation
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    private double getWeekMultiplier(int spokesman, int week) {
        // TODO DO it properly as an incremental curve over the season
        double max = 1.0 + (spokesman / 20.0);
        return 1.0 + ((max * week) / 16.0d);
    }
}
