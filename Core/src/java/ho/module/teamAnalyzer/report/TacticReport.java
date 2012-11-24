// %2258750519:hoplugins.teamAnalyzer.report%
package ho.module.teamAnalyzer.report;

import ho.module.teamAnalyzer.vo.PlayerPerformance;


/**
 * Report of all the different tactic used
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class TacticReport extends Report {
    //~ Instance fields ----------------------------------------------------------------------------

    /** The tactic code */
    private int tacticCode;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TacticReport object.
     *
     * @param pp
     */
    public TacticReport(PlayerPerformance pp) {
        super(pp);
        this.tacticCode = pp.getPosition();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Document me!
     *
     * @param i
     */
    public void setTacticCode(int i) {
        tacticCode = i;
    }

    /**
     * Document me!
     *
     * @return
     */
    public int getTacticCode() {
        return tacticCode;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("TacticReport[");
        buffer.append("tactic = " + tacticCode);
        buffer.append(", " + super.toString());
        buffer.append("]");

        return buffer.toString();
    }
}
