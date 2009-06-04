// %638597353:hoplugins.trainingExperience.ui.model%
package hoplugins.trainingExperience.ui.model;

import hoplugins.Commons;
import hoplugins.TrainingExperience;

import hoplugins.trainingExperience.constants.Trainings;

import plugins.IFutureTrainingWeek;
import plugins.IHOMiniModel;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;


/**
 * Customized table model for future trainings
 */
public class FutureTrainingsTableModel extends AbstractTrainingsTableModel {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FutureTrainingsTableModel object.
     *
     * @param miniModel
     */
    public FutureTrainingsTableModel(IHOMiniModel miniModel) {
        super(miniModel);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * When a value is updated, update the value in the right TrainingWeek in p_V_trainingsVector
     * store the change in the DB and then update the player prevision with the new training
     * setting
     *
     * @param value
     * @param row
     * @param col
     */
    public void setValueAt(Object value, int row, int col) {
        Object[] aobj = (Object[]) p_V_data.get(row);

        aobj[col] = value;

        IFutureTrainingWeek train = (IFutureTrainingWeek) p_V_trainingsVector.get(row);

        if (col == 2) {
            String selection = value.toString();

            train.setTyp(Trainings.getTrainingCode(selection));
        }

        if (col == 3) {
            Integer intense = (Integer) value;

            train.setIntensitaet(intense.intValue());
        }

        if (col == 4) {
            Integer staminaTrainingPart = (Integer) value;

            train.setStaminaTrainingPart(staminaTrainingPart.intValue());
        }

        Commons.getModel().saveFutureTraining(train);
        fireTableCellUpdated(row, col);
        TrainingExperience.refreshPlayerDetail();
    }

    /**
     * Populate the table with the future trainings stored in the db,  if not present, create it
     * new and saves it
     */
    public void populate() {
        p_V_data = new Vector();
        p_V_trainingsVector = new Vector();

        Object[] aobj;

        IFutureTrainingWeek oldTrain = null;

        List futureTrainings = p_IHMM_miniModel.getFutureTrainingWeeks();

        for (Iterator iter = futureTrainings.iterator(); iter.hasNext();) {
            IFutureTrainingWeek train = (IFutureTrainingWeek) iter.next();

            // if not found create it and saves it
            if (train.getIntensitaet() == -1) {
                if (oldTrain != null) {
                    train.setIntensitaet(oldTrain.getIntensitaet());
                    train.setStaminaTrainingPart(oldTrain.getStaminaTrainingPart());
                    train.setTyp(oldTrain.getTyp());
                } else {
                    train.setIntensitaet(100);
                    train.setStaminaTrainingPart(5);
                    train.setTyp(0);
                }

                Commons.getModel().saveFutureTraining(train);
            }

            String selectedTrain = Trainings.getTrainingDescription(train.getTyp());

            aobj = (new Object[]{
                       train.getWeek() + "", //$NON-NLS-1$
                       train.getSeason() + "", //$NON-NLS-1$
                       selectedTrain, 
                       new Integer(train.getIntensitaet()), 
                       new Integer(train.getStaminaTrainingPart())
                   });

            // Add object to be visualized to the table model
            p_V_data.add((Object) aobj);

            // Add training object to the proper vector for external use
            p_V_trainingsVector.add(train);

            oldTrain = train;
        }

        fireTableDataChanged();
    }
}
