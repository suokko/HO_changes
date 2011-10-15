// %4044085182:hoplugins.trainingExperience.constants%
package hoplugins.trainingExperience.constants;

import hoplugins.Commons;

import plugins.IHOMiniModel;
import plugins.ITeam;


/**
 * Class the manage relation between trainingtype codes and description
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class Trainings {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the training code given the training description
     *
     * @param selection The training name
     *
     * @return training code
     */
    public static int getTrainingCode(String selection) {
        int type = ITeam.TA_ALLGEMEIN;
        IHOMiniModel p_IHMM_miniModel = Commons.getModel();

        if (selection.equals(p_IHMM_miniModel.getLanguageString("training.set_pieces"))) { //$NON-NLS-1$
            type = ITeam.TA_STANDARD;
        }

        if (selection.equals(p_IHMM_miniModel.getLanguageString("training.defending"))) { //$NON-NLS-1$
            type = ITeam.TA_VERTEIDIGUNG;
        }

        if (selection.equals(p_IHMM_miniModel.getLanguageString("training.scoring"))) { //$NON-NLS-1$
            type = ITeam.TA_CHANCEN;
        }

        if (selection.equals(p_IHMM_miniModel.getLanguageString("training.crossing"))) { //$NON-NLS-1$
            type = ITeam.TA_FLANKEN;
        }

        if (selection.equals(p_IHMM_miniModel.getLanguageString("training.shooting"))) { //$NON-NLS-1$
            type = ITeam.TA_SCHUSSTRAINING;
        }

        if (selection.equals(p_IHMM_miniModel.getLanguageString("training.short_passes"))) { //$NON-NLS-1$
            type = ITeam.TA_PASSSPIEL;
        }

        if (selection.equals(p_IHMM_miniModel.getLanguageString("training.playmaking"))) { //$NON-NLS-1$
            type = ITeam.TA_SPIELAUFBAU;
        }

        if (selection.equals(p_IHMM_miniModel.getLanguageString("training.goalkeeping"))) { //$NON-NLS-1$
            type = ITeam.TA_TORWART;
        }

        if (selection.equals(p_IHMM_miniModel.getLanguageString("training.through_passes"))) { //$NON-NLS-1$
            type = ITeam.TA_STEILPAESSE;
        }

        if (selection.equals(p_IHMM_miniModel.getLanguageString("training.defensive_positions"))) { //$NON-NLS-1$
            type = ITeam.TA_ABWEHRVERHALTEN;
        }

        // TODO Real name
        if (selection.equals(p_IHMM_miniModel.getLanguageString("training.wing_attacks"))) { //$NON-NLS-1$
            type = ITeam.TA_EXTERNALATTACK;
        }

        return type;
    }

    /**
     * Returns training descriptions
     *
     * @param type training type
     *
     * @return String description
     */
    public static String getTrainingDescription(int type) {
        IHOMiniModel p_IHMM_miniModel = Commons.getModel();
        String selectedTrain = ""; //$NON-NLS-1$

        if (type == ITeam.TA_STANDARD) {
            selectedTrain = new String(p_IHMM_miniModel.getLanguageString("training.set_pieces")); //$NON-NLS-1$
        }

        if (type == ITeam.TA_VERTEIDIGUNG) {
            selectedTrain = new String(p_IHMM_miniModel.getLanguageString("training.defending")); //$NON-NLS-1$
        }

        if (type == ITeam.TA_CHANCEN) {
            selectedTrain = new String(p_IHMM_miniModel.getLanguageString("training.scoring")); //$NON-NLS-1$
        }

        if (type == ITeam.TA_FLANKEN) {
            selectedTrain = new String(p_IHMM_miniModel.getLanguageString("training.crossing")); //$NON-NLS-1$
        }

        if (type == ITeam.TA_SCHUSSTRAINING) {
            selectedTrain = new String(p_IHMM_miniModel.getLanguageString("training.shooting")); //$NON-NLS-1$
        }

        if (type == ITeam.TA_PASSSPIEL) {
            selectedTrain = new String(p_IHMM_miniModel.getLanguageString("training.short_passes")); //$NON-NLS-1$
        }

        if (type == ITeam.TA_SPIELAUFBAU) {
            selectedTrain = new String(p_IHMM_miniModel.getLanguageString("training.playmaking")); //$NON-NLS-1$
        }

        if (type == ITeam.TA_TORWART) {
            selectedTrain = new String(p_IHMM_miniModel.getLanguageString("training.goalkeeping")); //$NON-NLS-1$
        }

        if (type == ITeam.TA_STEILPAESSE) {
            selectedTrain = new String(p_IHMM_miniModel.getLanguageString("training.through_passes")); //$NON-NLS-1$
        }

        if (type == ITeam.TA_ABWEHRVERHALTEN) {
            selectedTrain = new String(p_IHMM_miniModel.getLanguageString("training.defensive_positions")); //$NON-NLS-1$
        }

        if (type == ITeam.TA_EXTERNALATTACK) {
            selectedTrain = new String(""
                                       + p_IHMM_miniModel.getLanguageString("training.wing_attacks")); //$NON-NLS-1$
        }

        return selectedTrain;
    }
}
