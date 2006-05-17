// %1127326047462:credits%
package de.hattrickorganizer;

import java.awt.image.RGBImageFilter;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
class TestFilter extends RGBImageFilter {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TestFilter object.
     */
    public TestFilter() {
        // The filter's operation does not depend on the
        // pixel's location, so IndexColorModels can be
        // filtered directly.
        canFilterIndexColorModel = true;
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
    public int filterRGB(int x, int y, int rgb) {
        return ((rgb & 0xff00ff00) | ((rgb & 0xff0000) >> 16) | ((rgb & 0xff) << 16));
    }
}
