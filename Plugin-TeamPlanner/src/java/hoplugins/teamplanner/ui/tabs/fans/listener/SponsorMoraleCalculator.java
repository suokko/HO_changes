// %1176467615:hoplugins.teamplanner.ui.tabs.fans.listener%
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
public class SponsorMoraleCalculator extends Calculator {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param row Missing Method Parameter Documentation
     * @param model Missing Method Parameter Documentation
     */
    @Override
	public void doCalculate(int row, TableModel model) {
        // TODO load actual sponsor Morale
        double sponsorMorale = 4;
        int weekNumber = TeamPlanner.ACTUALWEEK.getWeek();
        int season = TeamPlanner.ACTUALWEEK.getSeason();

        for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            weekNumber++;

            if (weekNumber == 17) {
                weekNumber = 1;
                sponsorMorale = 4;
                season++;
            }

            sponsorMorale = sponsorMorale
                            + getWeekChange(weekNumber,
                                            TeamPlanner.getFuturePane().getYearSetting(season)
                                                       .getSeasonResult());

            if (sponsorMorale < 1) {
                sponsorMorale = 1;
            } else if (sponsorMorale > 7) {
                sponsorMorale = 7;
            }

            OperationCell cell = Util.getOperationCell(model, row, i);
            NumericInner inner = (NumericInner) cell.getOperation().getInner();
            inner.setData(sponsorMorale, 0);
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
