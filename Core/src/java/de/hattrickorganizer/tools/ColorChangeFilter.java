// %2517597608:de.hattrickorganizer.tools%
package de.hattrickorganizer.tools;

import java.awt.Color;
import java.awt.image.RGBImageFilter;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class ColorChangeFilter extends RGBImageFilter {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public int changeRGB;

    /** TODO Missing Parameter Documentation */
    public int sourceRGB;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ColorChangeFilter object.
     *
     * @param source TODO Missing Constructuor Parameter Documentation
     * @param change TODO Missing Constructuor Parameter Documentation
     */
    public ColorChangeFilter(Color source, Color change) {
        sourceRGB = source.getRGB();
        changeRGB = change.getRGB();
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
    @Override
	public final int filterRGB(int x, int y, int rgb) {
        if (rgb == sourceRGB) {
            return changeRGB;
        } else {
            // nothing to do
            return rgb;
        }
    }
}
