// %2709500570:de.hattrickorganizer.tools.updater%
package de.hattrickorganizer.tools.updater;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;


/**
 * DOCUMENT ME!
 *
 * @author tdietz
 */
public final class UpdaterCellRenderer implements TableCellRenderer {
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
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
		double site = 0;
		double ho = 0;		                                                   	
		try {
			site = Double.parseDouble(((JLabel)table.getModel().getValueAt(row,3)).getText());
		} catch (Exception e) {
		}
		try {
			ho = Double.parseDouble(((JLabel)table.getModel().getValueAt(row,2)).getText());
		} catch (Exception e1) {
		}		
		Color highlite = Color.RED;
		boolean color = false;
		if ( (ho>0) && (ho<site) ) {
			color = true;
		}
						
        if (value instanceof JTextField) {
            JTextField field = (JTextField) value;
            field.setBorder(null);

            if (isSelected) {
                field.setBackground(new Color(212, 175, 250));                
                field.setForeground(Color.black);
            }
            if (color) {
            	field.setForeground(highlite);
            }
            return field;
        }

        if (value instanceof JCheckBox) {
            JCheckBox check = (JCheckBox) value;
            return check;
        }

        if (value instanceof JButton) {
        	JButton b = (JButton) value; 
            b.setBorderPainted(false);
			if (color) {
				b.setForeground(highlite);
			}            
            return b;
        }

		JLabel label = null;
        if (value instanceof JLabel) {
        	label = (JLabel) value;
        } else {
        	label = new JLabel(value.toString()); 
        }
			
		if (color) {
			label.setForeground(highlite);
		}             
		return label;

    }
}
