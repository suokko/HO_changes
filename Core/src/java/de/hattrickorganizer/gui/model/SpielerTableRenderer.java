// %4093469452:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;

import de.hattrickorganizer.gui.templates.TableEntry;
import de.hattrickorganizer.gui.theme.ThemeManager;


/**
 * Render für Tabellen mit JLabels als Tabellenobjekte
 */
public class SpielerTableRenderer implements javax.swing.table.TableCellRenderer {
    //~ Static fields/initializers -----------------------------------------------------------------

    public static java.awt.Color SELECTION_BG = ThemeManager.getColor("ho.table.selection.background");//new java.awt.Color(235, 235, 235);

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
        } 
       
//        else if (value instanceof Integer) {
//        	System.out.println("SpielerTableRenderer: Integer");
//            final JComponent component = new JLabel(value.toString(), SwingConstants.LEFT);
//            component.setOpaque(true);
//
//            if (isSelected) {
//                component.setBackground(SELECTION_BG);
//            } else {
//                component.setBackground(Color.red);
//            }
//
//            return component;
//        } 
        else if (value instanceof JComponent) {
            final JComponent component = (JComponent) value;
            component.setOpaque(true);

            if (isSelected) {
                component.setBackground(SELECTION_BG);
            } else {
                component.setBackground(Color.white);
            }

            return component;
        }  else {
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
