// %937290090:hoplugins.trainingExperience.ui.renderer%
/*
 * Created on 14-mar-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ho.module.training.ui.renderer;

import ho.module.training.ui.comp.VerticalIndicator;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * DOCUMENT ME!
 *
 * @author Mirtillo To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OutputTableRenderer extends DefaultTableCellRenderer {
    //~ Methods ------------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 7179773036740605371L;

	/* (non-Javadoc)
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                                                             row, column);

        if ((column > 2) && (column < 11)) {
            VerticalIndicator vi = (VerticalIndicator) value;

            // Set background and make it visible.
            vi.setBackground(cell.getBackground());
            vi.setOpaque(true);

            return vi;
        }

        return cell;
    }
}
