// %3040931838:de.hattrickorganizer.logik.matchengine%
package de.hattrickorganizer.logik.matchengine;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TeamRatings implements plugins.IMPTeamRatings {
    //~ Instance fields ----------------------------------------------------------------------------

    private double leftAttack;
    private double leftDef;
    private double middleAttack;
    private double middleDef;
    private double midfield;
    private double rightAttack;
    private double rightDef;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TeamRatings object.
     */
    public TeamRatings() {
    }

    /**
     * Ratings for a team for the Matchprediction Use values from 1-80
     *
     * @param midfield TODO Missing Constructuor Parameter Documentation
     * @param leftDef TODO Missing Constructuor Parameter Documentation
     * @param middleDef TODO Missing Constructuor Parameter Documentation
     * @param rightDef TODO Missing Constructuor Parameter Documentation
     * @param leftAttack TODO Missing Constructuor Parameter Documentation
     * @param middleAttack TODO Missing Constructuor Parameter Documentation
     * @param rightAttack TODO Missing Constructuor Parameter Documentation
     */
    public TeamRatings(double midfield, double leftDef, double middleDef, double rightDef,
                       double leftAttack, double middleAttack, double rightAttack) {
        this.midfield = midfield;
        this.leftDef = leftDef;
        this.middleDef = middleDef;
        this.rightDef = rightDef;
        this.leftAttack = leftAttack;
        this.middleAttack = middleAttack;
        this.rightAttack = rightAttack;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param d TODO Missing Method Parameter Documentation
     */
    public final void setLeftAttack(double d) {
        leftAttack = d;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getLeftAttack() {
        return leftAttack;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param d TODO Missing Method Parameter Documentation
     */
    public final void setLeftDef(double d) {
        leftDef = d;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getLeftDef() {
        return leftDef;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param d TODO Missing Method Parameter Documentation
     */
    public final void setMiddleAttack(double d) {
        middleAttack = d;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getMiddleAttack() {
        return middleAttack;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param d TODO Missing Method Parameter Documentation
     */
    public final void setMiddleDef(double d) {
        middleDef = d;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getMiddleDef() {
        return middleDef;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param d TODO Missing Method Parameter Documentation
     */
    public final void setMidfield(double d) {
        midfield = d;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getMidfield() {
        return midfield;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param d TODO Missing Method Parameter Documentation
     */
    public final void setRightAttack(double d) {
        rightAttack = d;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getRightAttack() {
        return rightAttack;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param d TODO Missing Method Parameter Documentation
     */
    public final void setRightDef(double d) {
        rightDef = d;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getRightDef() {
        return rightDef;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    public final String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("TeamRatings[");
        buffer.append("leftDef = " + leftDef);
        buffer.append(", middleDef = " + middleDef);
        buffer.append(", rightDef = " + rightDef);
        buffer.append(", leftAttack = " + leftAttack);
        buffer.append(", middleAttack = " + middleAttack);
        buffer.append(", rightAttack = " + rightAttack);
        buffer.append(", midfield = " + midfield);
        buffer.append("]");
        return buffer.toString();
    }
}
