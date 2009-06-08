// %1953378511:de.hattrickorganizer.tools%
package de.hattrickorganizer.tools;

import java.awt.Color;
import java.awt.image.RGBImageFilter;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TransparentFilter extends RGBImageFilter {
    //~ Instance fields ----------------------------------------------------------------------------

    // the color we are looking for... Alpha bits are set to opaque

    /** TODO Missing Parameter Documentation */
    public int markerRGB;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TransparentFilter object.
     *
     * @param transparentColor TODO Missing Constructuor Parameter Documentation
     */
    public TransparentFilter(Color transparentColor) {
        markerRGB = transparentColor.getRGB() | 0xFF000000;
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
        if ((rgb | 0xFF000000) == markerRGB) {
            // Mark the alpha bits as zero - transparent
            return 0x00FFFFFF & rgb;
        } else {
            // nothing to do
            return rgb;
        }
    }
}
