// %1259391691:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

/**
 * Item mit einer Grafik und einer Wartezeit (nach Anzeige des Bildes)
 */
public class ImageSequenzItem {
    //~ Instance fields ----------------------------------------------------------------------------

    //Das zu zeichnende Image
    private java.awt.Image m_clImage;

    //Offset X
    private int m_iOffsetX;

    //Offset Y          
    private int m_iOffsetY;

    //Die Wartezeit nach dem Anzeigen des Bildes
    private int m_iWait;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ImageSequenzItem object.
     *
     * @param image TODO Missing Constructuor Parameter Documentation
     * @param wait TODO Missing Constructuor Parameter Documentation
     * @param offsetX TODO Missing Constructuor Parameter Documentation
     * @param offsetY TODO Missing Constructuor Parameter Documentation
     */
    public ImageSequenzItem(java.awt.Image image, int wait, int offsetX, int offsetY) {
        m_clImage = image;
        m_iWait = wait;
        m_iOffsetX = offsetX;
        m_iOffsetY = offsetY;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final java.awt.Image getImage() {
        return m_clImage;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getOffsetX() {
        return m_iOffsetX;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getOffsetY() {
        return m_iOffsetY;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getWait() {
        return m_iWait;
    }
}
