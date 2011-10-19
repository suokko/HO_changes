// %638597353:hoplugins.trainingExperience.ui.model%
package hoplugins.trainingExperience.ui.model;

import hoplugins.Commons;
import hoplugins.TrainingExperience;
import hoplugins.trainingExperience.constants.Trainings;
import hoplugins.trainingExperience.ui.component.CBItem;

import plugins.IFutureTrainingWeek;
import plugins.IHOMiniModel;
import plugins.ITeam;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;


/**
 * Customized table model for future trainings
 */
public class FutureTrainingsTableModel extends AbstractTrainingsTableModel {
    //~ Constructors -------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 5448249533827333037L;

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
    @Override
	public void setValueAt(Object value, int row, int col) {
        Object[] aobj = (Object[]) p_V_data.get(row);

        aobj[col] = value;

        IFutureTrainingWeek train = (IFutureTrainingWeek) p_V_trainingsVector.get(row);

        if (col == 2) {
            CBItem sel = (CBItem)value;
            train.setTyp(sel.getId());
        }
        else if (col == 3) {
            Integer intense = (Integer) value;
            train.setIntensitaet(intense.intValue());
        }
        else if (col == 4) {
            Integer staminaTrainingPart = (Integer) value;
            train.setStaminaTrainingPart(staminaTrainingPart.intValue());
        }
        Commons.getModel().saveFutureTraining(train);
        fireTableCellUpdated(row, col);
        TrainingExperience.refreshPlayerDetail();
    }

    /**
     * Populate the table with the future training stored in the db,  if not present, create it
     * new and saves it
     */
    @Override
	public void populate() {
        p_V_data = new Vector<Object[]>();
        p_V_trainingsVector = new Vector();

        Object[] aobj;

        IFutureTrainingWeek oldTrain = null;

        List<IFutureTrainingWeek> futureTrainings = p_IHMM_miniModel.getFutureTrainingWeeks();

        for (Iterator<IFutureTrainingWeek> iter = futureTrainings.iterator(); iter.hasNext();) {
            IFutureTrainingWeek train = iter.next();

            // if not found create it and saves it
            if (train.getIntensitaet() == -1) {
                if (oldTrain != null) {
                    train.setIntensitaet(oldTrain.getIntensitaet());
                    train.setStaminaTrainingPart(oldTrain.getStaminaTrainingPart());
                    train.setTyp(oldTrain.getTyp());
                } else {
                    train.setIntensitaet(100);
                    train.setStaminaTrainingPart(5);
                    train.setTyp(ITeam.TA_STANDARD);
                }

                Commons.getModel().saveFutureTraining(train);
            }

            String selectedTrain = Trainings.getTrainingDescription(train.getTyp());

            aobj = (new Object[]{
                       train.getWeek() + "", //$NON-NLS-1$
                       train.getSeason() + "", //$NON-NLS-1$
                       new CBItem(selectedTrain, train.getTyp()), 
                       new Integer(train.getIntensitaet()), 
                       new Integer(train.getStaminaTrainingPart())
                   });

            // Add object to be visualized to the table model
            p_V_data.add(aobj);

            // Add training object to the proper vector for external use
            p_V_trainingsVector.add(train);

            oldTrain = train;
        }

        fireTableDataChanged();
    }
}
