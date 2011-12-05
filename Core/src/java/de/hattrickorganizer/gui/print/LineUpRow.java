// %1676803186:de.hattrickorganizer.gui.print%
/*
 * Created on 14.03.2004
 *
 */
package de.hattrickorganizer.gui.print;

import java.awt.Graphics2D;
import java.awt.Point;


/**
 * DOCUMENT ME!
 *
 * @author Thorsten Schmidt
 */
public class LineUpRow {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */

    // für den Anfang erstmal statisch
    protected static int width = 100;

    //~ Instance fields ----------------------------------------------------------------------------

    private LineUpBox[] boxes;

    // für den Anfang erstmal statisch
    private int margin = 20;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * constructor
     *
     * @param boxCount
     */
    protected LineUpRow(int boxCount) {
        boxes = new LineUpBox[boxCount];

        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new LineUpBox();
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param position TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected final LineUpBox getBox(int position) {
        return boxes[position - 1];
    }

    /**
     * gibt die Höhe der größten Box wieder
     *
     * @return
     */
    protected final int getTotalHeight() {
        int maxHeight = margin;

        for (int i = 0; i < boxes.length; i++) {
            if (maxHeight < boxes[i].getHeight()) {
                maxHeight = boxes[i].getHeight();
            }
        }

        return maxHeight;
    }

    /**
     * zeichnet alle Boxen einer Reihe
     *
     * @param y
     * @param pageWidth
     * @param g2
     */
    protected final void draw(int y, int pageWidth, Graphics2D g2) {
        for (int i = 0; i < boxes.length; i++) {
            boxes[i].draw(g2, getBoxStartPoint(y, pageWidth, i));
        }
    }

    /**
     * ermittelt die linke obere Ecke der zu zeichnenden Box.
     *
     * @param y
     * @param pageWidth
     * @param i
     *
     * @return
     */
    private Point getBoxStartPoint(int y, int pageWidth, int i) {
        final int center = pageWidth / 2;
        int oddNumbered = margin / 2 * -1;

        if ((boxes.length % 2) == 1) {
            oddNumbered = width / 2;
        }

        final int x = center - (((boxes.length / 2) * (width + margin)) + oddNumbered)
                      + (i * (width + margin));

        return new Point(x, y);
    }

    //~ Inner Classes ------------------------------------------------------------------------------

    /**
     * die Box als innere Klasse
     *
     * @author Thorsten Schmidt
     */
    protected final class LineUpBox {
        //~ Instance fields ------------------------------------------------------------------------

        /** TODO Missing Parameter Documentation */
        int rowHeight = 10;
        private String title;
        private String[] rows;
        private int height = rowHeight;

        //~ Methods --------------------------------------------------------------------------------

        /**
         * gibt die Höhe der Box wieder
         *
         * @return int
         */
        protected int getHeight() {
            return height;
        }

        /**
         * setzt die Zeilen der Box. Die Anzahl kann sich von den anderen innerhalb einer Reihe
         * ruhig unterscheiden
         *
         * @param objects
         */
        protected void setRows(String[] objects) {
            rows = objects;
        }

        /**
         * setzt den Titel der Box
         *
         * @param string
         */
        protected void setTitle(String string) {
            title = string;
        }

        /**
         * zeichnet die Box
         *
         * @param g2
         * @param startPoint
         */
        protected void draw(Graphics2D g2, Point startPoint) {
            final int margin = 2;
            final int x = (int) startPoint.getX();
            final int y = (int) startPoint.getY();

            if (rows != null) {
                height = (rows.length + 1) * rowHeight;
            } else {
                height = rowHeight;
            }

            g2.drawRect(x, y, width, height);
            g2.drawLine(x, y + rowHeight, x + width, y + rowHeight);

            if (title == null) {
                title = "";
            }

            g2.drawString(title, x + margin, (y + rowHeight) - margin);

            if (rows != null) {
                for (int i = 0; i < rows.length; i++) {
                    g2.drawString(convertToBox(rows[i]), x + margin,
                                  ((y + (rowHeight * (i + 2))) - margin));
                }
            }
        }

        /**
         * passt den String auf die Breite der Box an. Es wird von der Schrift (monospaced,Plain,5)
         * ausgegangen
         *
         * @param input
         *
         * @return String
         */
        private String convertToBox(String input) {
            final int maxLength = width / 5;

            if (input.length() > maxLength) {
                input = input.substring(0, maxLength - 3) + "...";
            }

            return input;
        }
    }
}
