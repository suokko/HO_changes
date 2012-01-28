// %193722072:hoplugins.teamAnalyzer.vo%
package ho.module.teamAnalyzer.vo;

/**
 * Team Object Class
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class Team {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Name of the team */
    private String name;

    /** Team id */
    private int teamId;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param string
     */
    public void setName(String string) {
        name = string;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param i
     */
    public void setTeamId(int i) {
        teamId = i;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * Return description for the team
     *
     * @return
     */
    public String desc() {
        return name + " " + teamId;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public String toString() {
        return name;
    }
}
