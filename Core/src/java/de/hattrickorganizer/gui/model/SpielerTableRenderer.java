// %4093469452:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import gui.HOColorName;
import ho.core.gui.theme.ThemeManager;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.TableEntry;


/**
 * Render für Tabellen mit JLabels als Tabellenobjekte
 */
public class SpielerTableRenderer implements javax.swing.table.TableCellRenderer {
    //~ Static fields/initializers -----------------------------------------------------------------

    public static Color SELECTION_BG = ThemeManager.getColor(HOColorName.TABLE_SELECTION_BG);//new java.awt.Color(235, 235, 235);
    public static Color SELECTION_FG = ThemeManager.getColor(HOColorName.TABLE_SELECTION_FG);
    //~ Methods ------------------------------------------------------------------------------------

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
        }  else if (value instanceof JComponent) {
            final JComponent component = (JComponent) value;
            component.setOpaque(true);
            component.setBackground(isSelected?SELECTION_BG:ColorLabelEntry.BG_STANDARD);
            component.setForeground(isSelected?SELECTION_FG:ColorLabelEntry.FG_STANDARD);
            return component;
        }  else {
            JComponent component;
            
            if (value != null) {
                component = new JLabel(value.toString());
            } else {
                component = new JLabel("null");
            }

            component.setOpaque(true);
            component.setBackground(isSelected?SELECTION_BG:ColorLabelEntry.BG_STANDARD);
            component.setForeground(isSelected?SELECTION_FG:ColorLabelEntry.FG_STANDARD);
            
            return component;
        }
    }
}
