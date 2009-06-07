// %1127325893431:credits%
package de.hattrickorganizer.credits;

/**
 * TODO Missing Interface Documentation
 *
 * @author TODO Author Name
 */
public interface DynamischesObjekt {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param time TODO Missing Method Parameter Documentation
     */
    public void setTime(long time);

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public long getTime();

    /**
     * TODO Missing Method Documentation
     *
     * @param time TODO Missing Method Parameter Documentation
     * @param gesamtzeit TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean action(int time, long gesamtzeit);

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     * @param xx TODO Missing Method Parameter Documentation
     * @param yy TODO Missing Method Parameter Documentation
     */
    public void render(java.awt.Graphics g, int xx, int yy);
}
