// %272607020:hoplugins.teamAnalyzer.vo%
package ho.module.teamAnalyzer.vo;

import ho.core.model.match.MatchLineupPlayer;


/**
 * This is a wrapper around IMatchLineupPlayer
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class PlayerPerformance {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Wrapped object */
    private MatchLineupPlayer mlp;

    /** Status of the player on the team. injured, sold etc */
    private int status;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new PlayerPerformance object around the loaded from HO
     *
     * @param _mlp The IMatchLineupPlayer object to be wrapped
     */
    public PlayerPerformance(MatchLineupPlayer _mlp) {
        mlp = _mlp;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Document Me!
     *
     * @return
     */
    public int getId() {
        return mlp.getId();
    }

    /**
     * Document Me!
     *
     * @return
     */
    public String getNickName() {
        return mlp.getNickName();
    }

    /**
     * Document Me!
     *
     * @return
     */
    public byte getPosition() {
        return mlp.getPosition();
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getPositionCode() {
        return mlp.getPositionCode();
    }

    /**
     * Document Me!
     *
     * @return
     */
    public double getRating() {
        return mlp.getRating();
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getSortId() {
        return mlp.getSortId();
    }

    /**
     * Document Me!
     *
     * @return
     */
    public int getSpielerId() {
        return mlp.getSpielerId();
    }

    /**
     * Document Me!
     *
     * @return
     */
    public String getSpielerName() {
        return mlp.getSpielerName();
    }

    /**
     * Document Me!
     *
     * @return
     */
    public String getSpielerVName() {
        return mlp.getSpielerVName();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setStatus(int i) {
        status = i;
    }

    /**
     * DOCUMENT ME!
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getStatus() {
        return status;
    }

    /**
     * Document Me!
     *
     * @return
     */
    public byte getTaktik() {
        return mlp.getTaktik();
    }
}
