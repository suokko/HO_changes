// %1126721451244:hoplugins.trainingExperience.ui.component%
package hoplugins.trainingExperience.ui.component;

import hoplugins.Commons;

import plugins.IHOMiniModel;

import javax.swing.JComboBox;


/**
 * ComboBox to edit the TrainingType
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class TrainingComboBox extends JComboBox {
    //~ Constructors -------------------------------------------------------------------------------

	private static final long serialVersionUID = 303608674207819922L;

	/**
     * Creates a new TrainingComboBox object.
     */
    public TrainingComboBox() {
        super();

        IHOMiniModel p_IHMM_HOMiniModel = Commons.getModel();

        addItem(p_IHMM_HOMiniModel.getLanguageString("training.general")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("Kondition")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("training.set_pieces")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("training.defending")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("training.scoring")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("training.crossing")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("training.shooting")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("training.short_passes")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("training.playmaking")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("training.goalkeeping")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("training.through_passes")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("training.defensive_positions")); //$NON-NLS-1$
        addItem(p_IHMM_HOMiniModel.getLanguageString("training.wing_attacks")); //$NON-NLS-1$        
    }
}
