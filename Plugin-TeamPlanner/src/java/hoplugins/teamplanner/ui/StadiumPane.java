// %599706334:hoplugins.teamplanner.ui%
package hoplugins.teamplanner.ui;

import hoplugins.Commons;
import hoplugins.TeamPlanner;

import hoplugins.teamplanner.ui.model.OperationData;
import hoplugins.teamplanner.ui.tabs.AbstractOperationPane;
import hoplugins.teamplanner.ui.tabs.stadium.StadiumExpansionInner;
import hoplugins.teamplanner.ui.tabs.stadium.listener.StadiumExpansionCalculator;
import hoplugins.teamplanner.ui.tabs.stadium.listener.StadiumExpansionListener;
import hoplugins.teamplanner.ui.tabs.stadium.listener.StadiumIncomeCalculator;
import hoplugins.teamplanner.ui.tabs.stadium.listener.StadiumMaintenanceCalculator;
import hoplugins.teamplanner.util.Util;
import hoplugins.teamplanner.vo.HTWeek;

import plugins.IFutureTrainingManager;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava </a>
 */
public class StadiumPane extends AbstractOperationPane {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -7412699016356489259L;

	/** Missing Parameter Documentation */
    public static final int SIZE_ROW = 0;

    /** Missing Parameter Documentation */
    public static final int MAINTENANCE_ROW = 1;

    /** Missing Parameter Documentation */
    public static final int EXPENSE_ROW = 2;

    /** Missing Parameter Documentation */
    public static final int INCOME_ROW = 3;

    //~ Methods ------------------------------------------------------------------------------------

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
        int total = Util.getOperationCell(model, MAINTENANCE_ROW, columnIndex).getBalance();
        total = total + Util.getOperationCell(model, INCOME_ROW, columnIndex).getBalance();
        return total;
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
        total = total
                + Util.getOperationCell(model, EXPENSE_ROW, getColumnIndex(week)).getBalance();
        return total;
    }

    /**
     * Missing Method Documentation
     *
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public final StadiumExpansionInner getStadiumSize(HTWeek week) {
        OperationData data = Util.getOperationData(model, SIZE_ROW, getColumnIndex(week));
        StadiumExpansionInner ss = (StadiumExpansionInner) data.getInner();
        return ss.getTotal();
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
     */
    @Override
	protected void setRows() {
        addInputRow("Size Change", new StadiumExpansionListener(), false,
                    new StadiumExpansionInner());
        addCalculatedRow("Maintenance", new StadiumMaintenanceCalculator());
        addCalculatedRow("Expenses", new StadiumExpansionCalculator());
        addCalculatedRow("Income", new StadiumIncomeCalculator());
    }

    /**
     * Missing Method Documentation
     */
    @Override
	protected void loadInputData() {
        for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            StadiumExpansionInner inner = (StadiumExpansionInner) Util.getOperationData(model,
                                                                                        SIZE_ROW, i)
                                                                      .getInner();
            StadiumExpansionInner total = inner.getTotal();
            total.setTerraces(Commons.getModel().getStadium().getStehplaetze());
            total.setSeats(Commons.getModel().getStadium().getSitzplaetze());
            total.setRoofs(Commons.getModel().getStadium().getUeberdachteSitzplaetze());
            total.setVips(Commons.getModel().getStadium().getLogen());
        }
    }
}
