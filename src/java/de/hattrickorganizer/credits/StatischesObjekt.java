// %1127326047415:credits%
package de.hattrickorganizer.credits;

import java.awt.Graphics;
import java.awt.Image;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
class StatischesObjekt {
    //~ Instance fields ----------------------------------------------------------------------------

    private Image image;
    private int offsetX;
    private int offsetY;
    private int x;
    private int y;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StatischesObjekt object.
     *
     * @param image TODO Missing Constructuor Parameter Documentation
     * @param x TODO Missing Constructuor Parameter Documentation
     * @param y TODO Missing Constructuor Parameter Documentation
     */
    public StatischesObjekt(Image image, int x, int y) {
        this(image, x, y, 0, 0);
    }

    /**
     * Creates a new StatischesObjekt object.
     *
     * @param image TODO Missing Constructuor Parameter Documentation
     * @param x TODO Missing Constructuor Parameter Documentation
     * @param y TODO Missing Constructuor Parameter Documentation
     * @param offsetX TODO Missing Constructuor Parameter Documentation
     * @param offsetY TODO Missing Constructuor Parameter Documentation
     */
    public StatischesObjekt(Image image, int x, int y, int offsetX, int offsetY) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     * @param xx TODO Missing Method Parameter Documentation
     * @param yy TODO Missing Method Parameter Documentation
     */
    public void render(Graphics g, int xx, int yy) {
        g.drawImage(image, x - xx + offsetX, y - yy + offsetY, null);
    }
}
