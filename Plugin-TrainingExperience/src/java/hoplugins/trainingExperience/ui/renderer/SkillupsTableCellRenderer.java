// %292731377:hoplugins.trainingExperience.ui.renderer%
package hoplugins.trainingExperience.ui.renderer;

import hoplugins.trainingExperience.ui.TrainingLegendPanel;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * TableCellRenderer for showing arrows representing the amount of change.
 *
 * @author NetHyperon
 */
public class SkillupsTableCellRenderer extends DefaultTableCellRenderer {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        try {
            int count = Integer.parseInt((String) value);
            int skill = Integer.parseInt((String) table.getValueAt(row, 8));

            setText(null);
            setIcon(TrainingLegendPanel.getSkillupTypeIcon(skill, count));
        } catch (NumberFormatException e) {
        }

        return this;
    }
}
