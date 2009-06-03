// %1494718773:de.hattrickorganizer.gui.utils%
package de.hattrickorganizer.gui.utils;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


/**
 * Panel, das gedruckt werden soll
 */
public class PrintablePanel extends javax.swing.JPanel implements java.awt.print.Printable {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected AffineTransform printTransform;

    /** TODO Missing Parameter Documentation */
    protected BufferedImage printImage;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     * @param pageFormat TODO Missing Method Parameter Documentation
     * @param pageIndex TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws java.awt.print.PrinterException TODO Missing Method Exception Documentation
     */
    public final int print(java.awt.Graphics g, java.awt.print.PageFormat pageFormat, int pageIndex)
      throws java.awt.print.PrinterException
    {
        if (pageIndex > 0) {
            return (java.awt.print.Printable.NO_SUCH_PAGE);
        } else {
            final java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;

            //g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            printImage = new BufferedImage(getWidth() - 1, getHeight(), BufferedImage.TYPE_INT_RGB);

            final Rectangle2D.Double pS = new Rectangle2D.Double(pageFormat.getImageableX(),
                                                                 pageFormat.getImageableY(),
                                                                 pageFormat.getImageableWidth(),
                                                                 pageFormat.getImageableHeight());
            final Rectangle2D.Double iS = new Rectangle2D.Double(0, 0, printImage.getWidth(null),
                                                                 printImage.getHeight(null));
            final double xScale = pS.getWidth() / iS.getWidth();
            final double yScale = pS.getHeight() / iS.getHeight();
            final double maxScale = Math.max(xScale, yScale);
            printTransform = new AffineTransform();

            //printTransform.translate (-iS.getX (), -iS.getY ());
            printTransform.scale(maxScale, maxScale);

            //printTransform.translate ( pS.getX ()/maxScale, pS.getY ()/maxScale );
            printTransform.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            g2d.scale((pageFormat.getWidth() - (pageFormat.getImageableX() * 2)) / getWidth(),
                      (pageFormat.getWidth() - (pageFormat.getImageableX() * 2)) / getWidth());

            //g2d.setTransform ( printTransform );
            paint(g2d);

            //g2d.scale ( maxScale, maxScale );
            return (java.awt.print.Printable.PAGE_EXISTS);
        }
    }
}
