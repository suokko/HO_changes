// %400793514:de.hattrickorganizer.tools%
package de.hattrickorganizer.tools;

import java.awt.Color;
import java.awt.image.RGBImageFilter;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class LightGrayFilter extends RGBImageFilter {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    float graywert = 0.5f;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new LightGrayFilter object.
     *
     * @param graywert TODO Missing Constructuor Parameter Documentation
     */
    public LightGrayFilter(float graywert) {
        this.graywert = graywert;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param x TODO Missing Method Parameter Documentation
     * @param y TODO Missing Method Parameter Documentation
     * @param rgb TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int filterRGB(int x, int y, int rgb) {
        final Color color = new Color(rgb);
        final Color color2 = new Color((int) (color.getRed() * graywert),
                                       (int) (color.getGreen() * graywert),
                                       (int) (color.getBlue() * graywert));
        return color2.getRGB();
    }
}
