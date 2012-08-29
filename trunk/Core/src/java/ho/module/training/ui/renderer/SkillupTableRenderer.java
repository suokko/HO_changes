// %169758679:hoplugins.trainingExperience.ui.renderer%
/*
 * Created on 14-mar-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ho.module.training.ui.renderer;

import ho.core.model.player.ISkillup;
import ho.module.training.Skills;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;



/**
 * DOCUMENT ME!
 *
 * @author Mirtillo To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SkillupTableRenderer extends DefaultTableCellRenderer {
    //~ Methods ------------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 4941016016981672099L;

	/* (non-Javadoc)
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                                                             row, column);

        int type = ISkillup.SKILLUP_REAL;

        try {
            type = Integer.parseInt((String) table.getValueAt(row, 3));
        } catch (NumberFormatException e) {
        }

        if (type == ISkillup.SKILLUP_FUTURE) {
            try {
                int skill = Integer.parseInt((String) table.getValueAt(row, 5));

                cell.setForeground(Skills.getSkillColor(skill));

                return cell;
            } catch (NumberFormatException e) {
                cell.setForeground(Color.BLACK);
            }
        } else {
            cell.setForeground(Color.BLACK);
        }

        return cell;
    }
}
