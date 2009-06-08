// %2439848109:hoplugins.teamplanner.ui.tabs.fans.listener%
package hoplugins.teamplanner.ui.tabs.fans.listener;

import hoplugins.TeamPlanner;

import hoplugins.teamplanner.ui.controller.calculator.Calculator;
import hoplugins.teamplanner.ui.model.OperationCell;
import hoplugins.teamplanner.ui.model.inner.NumericInner;
import hoplugins.teamplanner.util.Util;

import plugins.IFutureTrainingManager;

import javax.swing.table.TableModel;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class FansMoraleCalculator extends Calculator {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param model Missing Method Parameter Documentation
     */
    @Override
	public void doCalculate(int row, TableModel model) {
        // TODO load actual fans Morale
        double fansMorale = 4;
        int weekNumber = TeamPlanner.ACTUALWEEK.getWeek();
        int season = TeamPlanner.ACTUALWEEK.getSeason();

        for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            weekNumber++;

            if (weekNumber == 17) {
                weekNumber = 1;
                fansMorale = 4;
                season++;
            }

            fansMorale = fansMorale
                         + getWeekChange(weekNumber,
                                         TeamPlanner.getFuturePane().getYearSetting(season)
                                                    .getSeasonResult());

            if (fansMorale < 1) {
                fansMorale = 1;
            } else if (fansMorale > 7) {
                fansMorale = 7;
            }

            OperationCell cell = Util.getOperationCell(model, row, i);
            NumericInner inner = (NumericInner) cell.getOperation().getInner();
            inner.setData(fansMorale, 0);
        }
    }

    /**
     * Missing Method Documentation
     *
     * @param weekNumber Missing Method Parameter Documentation
     * @param results Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    private double getWeekChange(int weekNumber, int results) {
        //TODO morale depends also on spokesman,
        if (weekNumber < 2) {
            return 0;
        }

        // TODO Depends on weeknumber also? like log?
        switch (results) {
            case 1:
                return -0.8;

            case 2:
                return -0.25d;

            case 3:
                return -0.15;

            case 4:
                return 0.15;

            case 5:
                return 0.3d;

            case 6:
                return 0.5d;

            case 7:
                return 1.0d;

            default:
                return 0.0d;
        }
    }
}
