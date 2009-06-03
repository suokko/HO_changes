// %951755279:hoplugins.trainingExperience.ui.renderer%
package hoplugins.trainingExperience.ui.renderer;

import hoplugins.trainingExperience.constants.Skills;
import hoplugins.trainingExperience.ui.TrainingLegendPanel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * TableCellRenderer for showing arrows representing the amount of change.
 *
 * @author NetHyperon
 */
public class ChangeTableRenderer extends DefaultTableCellRenderer {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    private static final Color SILVER = new Color(239, 239, 239);

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        boolean isOld = ((Boolean) table.getValueAt(row, 5)).booleanValue();

        if (!isSelected) {
            if (isOld) {
                this.setBackground(SILVER);
            } else {
                this.setBackground(Color.WHITE);
            }
        }

        if (column == 3) {
            try {
                int skill = Integer.parseInt((String) table.getValueAt(row, 3));
                setText(Skills.getSkillDescription(skill));
                setIcon(TrainingLegendPanel.getSkillupTypeIcon(skill, 1));
            } catch (NumberFormatException e) {
            }
        }

        return this;
    }
}
