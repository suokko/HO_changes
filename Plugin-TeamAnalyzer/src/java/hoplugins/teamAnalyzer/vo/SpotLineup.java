// %1647522239:hoplugins.teamAnalyzer.vo%
package hoplugins.teamAnalyzer.vo;

import hoplugins.teamAnalyzer.report.Report;
import hoplugins.teamAnalyzer.report.SpotReport;

import java.util.List;


/**
 * Class that holds the real or calculated info for a position on the field (spot)
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class SpotLineup extends Report {
    //~ Instance fields ----------------------------------------------------------------------------

    /** List of tactics used by all who played in this spot */
    private List tactics;

    /** Name of the Player */
    private String name;

    /** Status of the player */
    private int status;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpotLineup object.
     */
    public SpotLineup() {
    }

    /**
     * Creates a new SpotLineup object.
     *
     * @param posReport the Spot Report from what the object must be created!!!
     */
    public SpotLineup(SpotReport posReport) {
        setRating(posReport.getRating());
        setAppearance(posReport.getAppearance());
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Document Me!
     *
     * @param string
     */
    public void setName(String string) {
        name = string;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setStatus(int i) {
        status = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * Document Me!
     *
     * @param list
     */
    public void setTactics(List list) {
        tactics = list;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public List getTactics() {
        return tactics;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("PositionLineup[");
        buffer.append("tactics = " + tactics);
        buffer.append(", name = " + name);
        buffer.append("]");

        return buffer.toString();
    }
}
