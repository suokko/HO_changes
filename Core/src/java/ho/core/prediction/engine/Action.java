// %1125186933:de.hattrickorganizer.logik.matchengine.engine.common%
package ho.core.prediction.engine;

import ho.core.model.match.IMatchDetails;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class Action implements  Comparable<Object> {
    //~ Instance fields ----------------------------------------------------------------------------

    private boolean homeTeam;
    private boolean score;
    private int area;
    private int minute;
    private int type;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setArea(int i) {
        area = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getArea() {
        return area;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param b TODO Missing Method Parameter Documentation
     */
    public final void setHomeTeam(boolean b) {
        homeTeam = b;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isHomeTeam() {
        return homeTeam;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setMinute(int i) {
        minute = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getMinute() {
        return minute;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param b TODO Missing Method Parameter Documentation
     */
    public final void setScore(boolean b) {
        score = b;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isScore() {
        return score;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getShortDesc() {
        final StringBuffer buffer = new StringBuffer();

        if (type == IMatchDetails.TAKTIK_KONTER) {
            buffer.append(ho.core.model.HOVerwaltung.instance().getLanguageString("Counter"));
        } else {
            buffer.append(ho.core.model.HOVerwaltung.instance().getLanguageString("Attack"));
        }

        buffer.append(" ");

        if (area == -1) {
            buffer.append(ho.core.model.HOVerwaltung.instance().getLanguageString("on_the_left"));
        } else if (area == 0) {
            buffer.append(ho.core.model.HOVerwaltung.instance().getLanguageString("on_the_middle"));
        } else {
            buffer.append(ho.core.model.HOVerwaltung.instance().getLanguageString("on_the_right"));
        }

        buffer.append(". ");

        if (score) {
            buffer.append(ho.core.model.HOVerwaltung.instance().getLanguageString("TOR"));
            buffer.append("!");
        }

        return buffer.toString();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setType(int i) {
        type = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getType() {
        return type;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param o TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int compareTo(Object o) {
        if (o instanceof Action) {
            final Action action = (Action) o;

            if (action.getMinute() < this.getMinute()) {
                return -1;
            } else if (action.getMinute() < this.getMinute()) {
                return 1;
            } else {
                return 0;
            }
        }

        return 0;
    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    @Override
	public final String toString() {
        final StringBuffer buffer = new StringBuffer();

        if (homeTeam) {
            buffer.append("Team1 has a chance ");
        } else {
            buffer.append("Team2 has a chance ");
        }

        if (area == -1) {
            buffer.append(" on the left");
        } else if (area == 0) {
            buffer.append(" on the middle");
        } else {
            buffer.append(" on the right");
        }

        buffer.append(", at minute ");
        buffer.append(minute);

        if (type == IMatchDetails.TAKTIK_KONTER) {
            buffer.append(". It's a counter!!");
        }

        if (score) {
            buffer.append("and it is a goal");
        }

        return buffer.toString();
    }
}
