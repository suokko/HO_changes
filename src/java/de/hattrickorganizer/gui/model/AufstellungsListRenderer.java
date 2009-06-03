// %2449251406:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ListCellRenderer;


/**
 * Für 2 Markierungen
 */
public class AufstellungsListRenderer extends JLabel implements ListCellRenderer {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static Color bgColor = new Color(220, 220, 255);

    /** TODO Missing Parameter Documentation */
    public static Color angezeigtColor = new Color(0, 0, 150);

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param jList TODO Missing Method Parameter Documentation
     * @param value TODO Missing Method Parameter Documentation
     * @param row TODO Missing Method Parameter Documentation
     * @param isSelected TODO Missing Method Parameter Documentation
     * @param hasFocus TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final java.awt.Component getListCellRendererComponent(javax.swing.JList jList,
                                                                 Object value, int row,
                                                                 boolean isSelected,
                                                                 boolean hasFocus) {
        if (value instanceof de.hattrickorganizer.gui.model.AufstellungCBItem) {
            setText(value.toString());
            setOpaque(true);

            if (isSelected) {
                setOpaque(true);
                setBackground(bgColor);
            } else {
                setOpaque(false);
                setBackground(Color.lightGray);
            }

            if (((AufstellungCBItem) value).isAngezeigt()) {
                setForeground(angezeigtColor);
            } else {
                setForeground(Color.black);
            }
        } else {
            setText(value.toString());
            setOpaque(true);

            if (isSelected) {
                setOpaque(true);
                setBackground(bgColor);
            } else {
                setOpaque(false);
                setBackground(Color.lightGray);
            }
        }

        return this;
    }
}
