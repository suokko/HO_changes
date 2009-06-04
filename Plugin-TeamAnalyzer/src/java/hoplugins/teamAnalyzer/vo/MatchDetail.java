// %1534136644:hoplugins.teamAnalyzer.vo%
package hoplugins.teamAnalyzer.vo;

import hoplugins.commons.vo.MatchRating;

import java.util.ArrayList;
import java.util.List;


/**
 * Match Detail containing data about the players that played in that game
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class MatchDetail {
    //~ Instance fields ----------------------------------------------------------------------------

    /** ArrayList of Player performance for this game */
    private List playerPerf = new ArrayList();

    /** Match to which the details are reffered */
    private Match match;

    /** Rating on the 7 areas of the pitch */
    private MatchRating rating = new MatchRating();

    /** Starting Lineup */
    private String formation;

    /** Total number of stars */
    private double stars;

    /** Tactic used in the game */
    private int tacticCode;

    /** Tactic LEvel achieved in the game */
    private int tacticLevel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MatchDetail object.
     *
     * @param aMatch match for which the details are
     */
    public MatchDetail(Match aMatch) {
        match = aMatch;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Document Me!
     *
     * @return
     */
    public final List getPerformances() {
        return playerPerf;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setFormation(String i) {
        formation = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public String getFormation() {
        return formation;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public Match getMatchDetail() {
        return match;
    }

    /**
     * Document Me!
     *
     * @param rating
     */
    public void setRating(MatchRating rating) {
        this.rating = rating;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public MatchRating getRating() {
        return rating;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setStars(double i) {
        stars = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public double getStars() {
        return stars;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setTacticCode(int i) {
        tacticCode = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getTacticCode() {
        return tacticCode;
    }

    /**
     * Document Me!
     *
     * @param i
     */
    public void setTacticLevel(int i) {
        tacticLevel = i;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getTacticLevel() {
        return tacticLevel;
    }

    /**
     * Add a player performance to the List
     *
     * @param pp PlayerPerformance of a player in the game
     */
    public void addMatchLineupPlayer(PlayerPerformance pp) {
        playerPerf.add(pp);
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("MatchDetail[");
        buffer.append("playerPerf = " + playerPerf);
        buffer.append(", match = " + match);
        buffer.append(", rating = " + rating);
        buffer.append("]");

        return buffer.toString();
    }
}
