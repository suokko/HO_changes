// %1126721451604:hoplugins.trainingExperience.ui.model%
package hoplugins.trainingExperience.ui.model;

import hoplugins.Commons;

import hoplugins.trainingExperience.constants.Trainings;

import plugins.IHOMiniModel;
import plugins.ITrainingWeek;

import java.util.Iterator;
import java.util.Vector;


/**
 * Customized table model for past trainings
 */
public class PastTrainingsTableModel extends AbstractTrainingsTableModel {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new PastTrainingsTableModel object.
     *
     * @param miniModel
     */
    public PastTrainingsTableModel(IHOMiniModel miniModel) {
        super(miniModel);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * When a value is updated, update the value in the right TrainingWeek in p_V_trainingsVector
     * store the change thru HO API. Refresh the main table, and deselect any player
     *
     * @param value
     * @param row
     * @param col
     */
    public void setValueAt(Object value, int row, int col) {
        Object[] aobj = (Object[]) p_V_data.get(row);

        aobj[col] = value;

        ITrainingWeek train = (ITrainingWeek) p_V_trainingsVector.get(p_V_data.size() - row - 1);

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

        Commons.getModel().saveTraining(train);
        fireTableCellUpdated(row, col);
    }

    /**
     * Populate the table with the old trainings week loaded from HO API
     */
    public void populate() {
        p_V_data = new Vector();

        // Stores ho trainings into training vector
        p_V_trainingsVector = Commons.getModel().getTrainingsManager().getTrainingsVector();

        Object[] aobj;

        // for each training week
        for (Iterator it = p_V_trainingsVector.iterator(); it.hasNext();) {
            ITrainingWeek train = (ITrainingWeek) it.next();
            String selectedTrain = Trainings.getTrainingDescription(train.getTyp());

            aobj = (new Object[]{
                       train.getHattrickWeek() + "", //$NON-NLS-1$
                       train.getHattrickSeason() + "", //$NON-NLS-1$
                       selectedTrain,
                       new Integer(train.getIntensitaet()),
                       new Integer(train.getStaminaTrainingPart())
                   });

            // add the data object into the table model
            p_V_data.add(0, (Object) aobj);
        }

        fireTableDataChanged();
    }
}
