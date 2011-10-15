// %3416341132:hoplugins.teamplanner.ui%
package hoplugins.teamplanner.ui;

import hoplugins.Commons;
import hoplugins.TeamPlanner;

import hoplugins.teamplanner.ui.model.OperationData;
import hoplugins.teamplanner.ui.tabs.AbstractOperationPane;
import hoplugins.teamplanner.ui.tabs.extra.StaffInner;
import hoplugins.teamplanner.ui.tabs.extra.listener.SponsorCalculator;
import hoplugins.teamplanner.ui.tabs.extra.listener.StaffCalculator;
import hoplugins.teamplanner.ui.tabs.extra.listener.StaffListener;
import hoplugins.teamplanner.util.Util;
import hoplugins.teamplanner.vo.HTWeek;

import plugins.IFutureTrainingManager;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava </a>
 */
public class ExtrasPane extends AbstractOperationPane {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 712736126779796039L;

	/** Missing Parameter Documentation */
    public static final int YOUTH_ROW = 0;

    /** Missing Parameter Documentation */
    public static final int STAFF_ROW = 1;

    /** Missing Parameter Documentation */
    public static final int STAFFSALARY_ROW = 2;

    /** Missing Parameter Documentation */
    public static final int SPONSOR_ROW = 3;

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
        int money = Util.getOperationCell(model, YOUTH_ROW, columnIndex).getBalance();
        money = money + Util.getOperationCell(model, STAFFSALARY_ROW, columnIndex).getBalance();
        money = money + Util.getOperationCell(model, SPONSOR_ROW, columnIndex).getBalance();
        return money;
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
        int bal = getBaseBalance(week);
        int columnIndex = getColumnIndex(week);
        bal += Util.getOperationCell(model, STAFF_ROW, columnIndex).getBalance();
        return bal;
    }

    /**
     * Missing Method Documentation
     *
     * @param week Missing Method Parameter Documentation
     *
     * @return Missing Return Method Documentation
     */
    public final StaffInner getStaff(HTWeek week) {
        OperationData data = Util.getOperationData(model, STAFF_ROW, getColumnIndex(week));
        StaffInner ss = (StaffInner) data.getInner();
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
        addManualRow("Youth");
        addInputRow("Staff", new StaffListener(), false, new StaffInner());
        addCalculatedRow("Staff Expense", new StaffCalculator());
        addCalculatedRow("Sponsor Income", new SponsorCalculator());
    }

    /**
     * Missing Method Documentation
     */
    @Override
	protected void loadInputData() {
        int coTrainer = Commons.getModel().getVerein().getCoTrainer();
        int psico = Commons.getModel().getVerein().getPsychologen();
        int doctor = Commons.getModel().getVerein().getAerzte();
        int phisio = Commons.getModel().getVerein().getMasseure();
        int spokes = Commons.getModel().getVerein().getPRManager();

        for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            StaffInner inner = (StaffInner) Util.getOperationData(model, STAFF_ROW, i).getInner();
            StaffInner total = inner.getTotal();
            total.setAssistantCoaches(coTrainer);
            total.setDoctor(doctor);
            total.setPhisio(phisio);
            total.setPsico(psico);
            total.setSpokesman(spokes);
        }
    }
}
