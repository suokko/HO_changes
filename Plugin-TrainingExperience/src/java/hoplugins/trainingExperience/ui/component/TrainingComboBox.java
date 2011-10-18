// %1126721451244:hoplugins.trainingExperience.ui.component%
package hoplugins.trainingExperience.ui.component;

import gui.NVPComboBox;
import hoplugins.trainingExperience.constants.Trainings;
import plugins.ITeam;

/**
 * ComboBox to edit the TrainingType
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 * Seb04 - Simplified and removed General and STamina training.
 */
public class TrainingComboBox extends NVPComboBox {
	private static final long serialVersionUID = 303608674207819922L;
	/**
     * Creates a new TrainingComboBox object.
     */
    public TrainingComboBox() {
        super();
        for (int i = ITeam.TA_STANDARD; i <= ITeam.TA_EXTERNALATTACK;  i++)
        {
        	addItem(i, Trainings.getTrainingDescription(i));
        }
    }
}
