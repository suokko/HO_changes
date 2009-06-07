// %3414899912:hoplugins.teamAnalyzer.vo%
package hoplugins.teamAnalyzer.vo;

import hoplugins.commons.vo.MatchRating;

import java.util.Arrays;


/**
 * Object that holds the Lineup for a certain formation, real or calculated
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class TeamLineup {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Rating of the team on the field */
    private MatchRating rating;

    /** Array of the 11 SpotLineup object representing the single spot */
    private SpotLineup[] spotLineups = new SpotLineup[11];

    /** Number of stars */
    private double stars;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the SpotLineup for the spot
     *
     * @param spot desired spot
     *
     * @return a spot lineup
     */
    public final SpotLineup getSpotLineup(int spot) {
        return spotLineups[spot - 1];
    }

    /**
     * DOCUMENT ME!
     *
     * @param rating
     */
    public void setRating(MatchRating rating) {
        this.rating = rating;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public MatchRating getRating() {
        return rating;
    }

    /**
     * Sets the spot place with the passes SpotLineup
     *
     * @param detail SpotLineup object
     * @param spot spot to be filled with the object
     */
    public void setSpotLineup(SpotLineup detail, int spot) {
        spotLineups[spot - 1] = detail;
    }

    /**
     * document me!
     *
     * @param d
     */
    public void setStars(double d) {
        stars = d;
    }

    /**
     * Document me!
     *
     * @return
     */
    public double getStars() {
        return stars;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("TeamLineup[");

        if (spotLineups == null) {
            buffer.append("spotLineups = " + "null");
        } else {
            buffer.append("spotLineups = " + Arrays.asList(spotLineups).toString());
        }

        buffer.append("]");

        return buffer.toString();
    }
}
