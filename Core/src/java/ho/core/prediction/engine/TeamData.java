// %2070140242:de.hattrickorganizer.logik.matchengine%
package ho.core.prediction.engine;

import java.util.ArrayList;
import java.util.List;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TeamData  {
    //~ Instance fields ----------------------------------------------------------------------------

    private List<Action> actions;
    private String teamName;
    private TeamRatings ratings;
    private int tacticLevel;
    private int tacticType;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Create the Data for an Team
     *
     * @param name Name of the team
     * @param _ratings The teamratings of the team
     * @param _tactic The tactic, Uses the TAKTIK - Constants from IMatchDetails
     * @param _level Tacticlevel from 1 to 20
     */
    public TeamData(String name, TeamRatings _ratings, int _tactic, int _level) {
        actions = new ArrayList<Action>();
        ratings = _ratings;
        tacticLevel = _level;
        tacticType = _tactic;
        teamName = name;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final List<Action> getActions() {
        return actions;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param ratings TODO Missing Method Parameter Documentation
     */
    public final void setRatings(TeamRatings ratings) {
        this.ratings = ratings;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final TeamRatings getRatings() {
        return ratings;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setTacticLevel(int i) {
        tacticLevel = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getTacticLevel() {
        return tacticLevel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setTacticType(int i) {
        tacticType = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getTacticType() {
        return tacticType;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getTeamName() {
        return teamName;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param action TODO Missing Method Parameter Documentation
     */
    public final void addAction(Action action) {
        actions.add(action);
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public final String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("TeamData[");
        buffer.append("actions = " + actions);
        buffer.append(", ratings = " + ratings);
        buffer.append(", tacticType = " + tacticType);
        buffer.append(", tacticLevel = " + tacticLevel);
        buffer.append(", teamName = " + teamName);
        buffer.append("]");
        return buffer.toString();
    }
}
