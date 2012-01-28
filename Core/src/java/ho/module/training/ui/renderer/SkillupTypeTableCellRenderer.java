// %3903747163:hoplugins.trainingExperience.ui.renderer%
package ho.module.training.ui.renderer;

import ho.module.training.Skills;
import ho.module.training.ui.TrainingLegendPanel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import de.hattrickorganizer.tools.extension.PlayerCreator;


/**
 * TableCellRenderer for showing arrows representing the amount of change.
 *
 * @author NetHyperon
 */
public class SkillupTypeTableCellRenderer extends ChangeTableRenderer {
	private static final long serialVersionUID = -8584898772728443298L;

 
    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    @Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        try {
            int skill = Integer.parseInt((String) value);

            setText(PlayerCreator.getSkillDescription(skill));
            setIcon(TrainingLegendPanel.getSkillupTypeIcon(skill, 1));
            setForeground(Skills.getSkillColor(skill));
        } catch (NumberFormatException e) {
        }

        return this;
    }
}
