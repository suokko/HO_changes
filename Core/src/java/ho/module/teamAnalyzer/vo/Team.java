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
 
    // A hack for custom coloring of tournament teams in a renderer
    private boolean tournament = false;

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

    
    public boolean isTournament() {
		return tournament;
	}

	public void setTournament(boolean tournament) {
		this.tournament = tournament;
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
