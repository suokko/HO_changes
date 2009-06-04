// %1272568856:de.hattrickorganizer.tools%
package de.hattrickorganizer.tools;

import java.awt.Color;
import java.awt.image.RGBImageFilter;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class FuzzyTransparentFilter extends RGBImageFilter {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public int m_iMaxBlue;

    /** TODO Missing Parameter Documentation */
    public int m_iMaxGreen;

    /** TODO Missing Parameter Documentation */
    public int m_iMaxRed;

    /** TODO Missing Parameter Documentation */
    public int m_iMinBlue;

    /** TODO Missing Parameter Documentation */
    public int m_iMinGreen;

    /** TODO Missing Parameter Documentation */
    public int m_iMinRed;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FuzzyTransparentFilter object.
     *
     * @param minred TODO Missing Constructuor Parameter Documentation
     * @param mingreen TODO Missing Constructuor Parameter Documentation
     * @param minblue TODO Missing Constructuor Parameter Documentation
     * @param maxred TODO Missing Constructuor Parameter Documentation
     * @param maxgreen TODO Missing Constructuor Parameter Documentation
     * @param maxblue TODO Missing Constructuor Parameter Documentation
     */
    public FuzzyTransparentFilter(int minred, int mingreen, int minblue, int maxred, int maxgreen,
                                  int maxblue) {
        //markerRGB = transparentColor.getRGB() | 0xFF000000;
        m_iMinRed = minred;
        m_iMinGreen = mingreen;
        m_iMinBlue = minblue;
        m_iMaxRed = maxred;
        m_iMaxGreen = maxgreen;
        m_iMaxBlue = maxblue;
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
        final Color rgbColor = new Color(rgb);

        if (((rgbColor.getRed() >= m_iMinRed) && (rgbColor.getRed() <= m_iMaxRed))
            && ((rgbColor.getGreen() >= m_iMinGreen) && (rgbColor.getGreen() <= m_iMaxGreen))
            && ((rgbColor.getBlue() >= m_iMinBlue) && (rgbColor.getBlue() <= m_iMaxBlue))) {
            // Mark the alpha bits as zero - transparent
            return 0x00FFFFFF & rgb;
        } else {
            // nothing to do
            return rgb;
        }
    }
}
