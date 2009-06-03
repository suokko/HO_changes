// %909162289:de.hattrickorganizer.gui.matchprediction%
package de.hattrickorganizer.gui.matchprediction;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class RatingItem {
    //~ Instance fields ----------------------------------------------------------------------------

    private String description;
    private int value;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RatingItem object.
     *
     * @param string TODO Missing Constructuor Parameter Documentation
     * @param i TODO Missing Constructuor Parameter Documentation
     */
    public RatingItem(String string, int i) {
        description = string;
        value = i;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param string TODO Missing Method Parameter Documentation
     */
    public final void setDescription(String string) {
        description = string;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getDescription() {
        return description;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public final void setValue(int i) {
        value = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getValue() {
        return value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String toString() {
        return description;
    }
}
