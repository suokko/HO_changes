// %4044085182:hoplugins.trainingExperience.constants%
package ho.module.training;

import ho.core.model.HOVerwaltung;
import ho.core.model.ITeam;


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
        int type = ITeam.TA_STANDARD;

        // Compares the training name with all the training names in HO, 
        //when a match is found it returns the right code 
        if (selection.equals(HOVerwaltung.instance().getLanguageString("training.set_pieces"))) { //$NON-NLS-1$
            type = ITeam.TA_STANDARD;
        }
        else if (selection.equals(HOVerwaltung.instance().getLanguageString("training.defending"))) { //$NON-NLS-1$
            type = ITeam.TA_VERTEIDIGUNG;
        }
        else if (selection.equals(HOVerwaltung.instance().getLanguageString("training.scoring"))) { //$NON-NLS-1$
            type = ITeam.TA_CHANCEN;
        }
        else if (selection.equals(HOVerwaltung.instance().getLanguageString("training.crossing"))) { //$NON-NLS-1$
            type = ITeam.TA_FLANKEN;
        }
        else if (selection.equals(HOVerwaltung.instance().getLanguageString("training.shooting"))) { //$NON-NLS-1$
            type = ITeam.TA_SCHUSSTRAINING;
        }
        else if (selection.equals(HOVerwaltung.instance().getLanguageString("training.short_passes"))) { //$NON-NLS-1$
            type = ITeam.TA_PASSSPIEL;
        }
        else if (selection.equals(HOVerwaltung.instance().getLanguageString("training.playmaking"))) { //$NON-NLS-1$
            type = ITeam.TA_SPIELAUFBAU;
        }
        else if (selection.equals(HOVerwaltung.instance().getLanguageString("training.goalkeeping"))) { //$NON-NLS-1$
            type = ITeam.TA_TORWART;
        }
        else if (selection.equals(HOVerwaltung.instance().getLanguageString("training.through_passes"))) { //$NON-NLS-1$
            type = ITeam.TA_STEILPAESSE;
        }
        else if (selection.equals(HOVerwaltung.instance().getLanguageString("training.defensive_positions"))) { //$NON-NLS-1$
            type = ITeam.TA_ABWEHRVERHALTEN;
        }
        else if (selection.equals(HOVerwaltung.instance().getLanguageString("training.wing_attacks"))) { //$NON-NLS-1$
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
        String selectedTrain = ""; //$NON-NLS-1$

        if (type == ITeam.TA_STANDARD) {
            selectedTrain = new String(HOVerwaltung.instance().getLanguageString("training.set_pieces")); //$NON-NLS-1$
        }
        else if (type == ITeam.TA_VERTEIDIGUNG) {
            selectedTrain = new String(HOVerwaltung.instance().getLanguageString("training.defending")); //$NON-NLS-1$
        }
        else if (type == ITeam.TA_CHANCEN) {
            selectedTrain = new String(HOVerwaltung.instance().getLanguageString("training.scoring")); //$NON-NLS-1$
        }
        else if (type == ITeam.TA_FLANKEN) {
            selectedTrain = new String(HOVerwaltung.instance().getLanguageString("training.crossing")); //$NON-NLS-1$
        }
        else if (type == ITeam.TA_SCHUSSTRAINING) {
            selectedTrain = new String(HOVerwaltung.instance().getLanguageString("training.shooting")); //$NON-NLS-1$
        }
        else if (type == ITeam.TA_PASSSPIEL) {
            selectedTrain = new String(HOVerwaltung.instance().getLanguageString("training.short_passes")); //$NON-NLS-1$
        }
        else if (type == ITeam.TA_SPIELAUFBAU) {
            selectedTrain = new String(HOVerwaltung.instance().getLanguageString("training.playmaking")); //$NON-NLS-1$
        }
        else if (type == ITeam.TA_TORWART) {
            selectedTrain = new String(HOVerwaltung.instance().getLanguageString("training.goalkeeping")); //$NON-NLS-1$
        }
        else if (type == ITeam.TA_STEILPAESSE) {
            selectedTrain = new String(HOVerwaltung.instance().getLanguageString("training.through_passes")); //$NON-NLS-1$
        }
        else if (type == ITeam.TA_ABWEHRVERHALTEN) {
            selectedTrain = new String(HOVerwaltung.instance().getLanguageString("training.defensive_positions")); //$NON-NLS-1$
        }
        else if (type == ITeam.TA_EXTERNALATTACK) {
            selectedTrain = new String(HOVerwaltung.instance().getLanguageString("training.wing_attacks")); //$NON-NLS-1$
        }
        return selectedTrain;
    }
}
