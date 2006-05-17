// %4093469452:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;

import de.hattrickorganizer.gui.templates.TableEntry;


/**
 * Render für Tabellen mit JLabels als Tabellenobjekte
 */
public class SpielerTableRenderer implements javax.swing.table.TableCellRenderer {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static java.awt.Color SELECTION_BG = new java.awt.Color(235, 235, 235);

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param table TODO Missing Method Parameter Documentation
     * @param value TODO Missing Method Parameter Documentation
     * @param isSelected TODO Missing Method Parameter Documentation
     * @param hasFocus TODO Missing Method Parameter Documentation
     * @param row TODO Missing Method Parameter Documentation
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                                                                  boolean isSelected,
                                                                  boolean hasFocus, int row,
                                                                  int column) {
        if (value instanceof TableEntry) {
            final JComponent component = ((TableEntry) value).getComponent(isSelected);

            if (isSelected) {
                component.setOpaque(true);
            }

            return component;
        } else if (value instanceof Integer) {
            final JComponent component = new JLabel(value.toString(), JLabel.LEFT);
            component.setOpaque(true);

            if (isSelected) {
                component.setBackground(SELECTION_BG);
            } else {
                component.setBackground(Color.red);
            }

            return component;
        } else if (value instanceof JComponent) {
            final JComponent component = (JComponent) value;
            component.setOpaque(true);

            if (isSelected) {
                component.setBackground(SELECTION_BG);
            } else {
                component.setBackground(Color.white);
            }

            return component;
        } else {
            JComponent component;

            if (value != null) {
                component = new JLabel(value.toString());
            } else {
                component = new JLabel("null");
            }

            component.setOpaque(true);

            if (isSelected) {
                component.setBackground(Color.red);
            } else {
                component.setBackground(Color.red);
            }

            return component;
        }
    }
}
