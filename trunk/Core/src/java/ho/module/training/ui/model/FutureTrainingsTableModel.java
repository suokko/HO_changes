// %638597353:hoplugins.trainingExperience.ui.model%
package ho.module.training.ui.model;

import ho.core.constants.TrainingType;
import ho.core.datatype.CBItem;
import ho.core.db.DBManager;
import ho.core.training.TrainingPerWeek;
import ho.module.training.TrainingPanel;

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
    public FutureTrainingsTableModel() {
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

        TrainingPerWeek train = (TrainingPerWeek) p_V_trainingsVector.get(row);

        if (col == 2) {
            CBItem sel = (CBItem)value;
            train.setTrainingType(sel.getId());
        }
        else if (col == 3) {
            Integer intense = (Integer) value;
            train.setTrainingIntensity(intense.intValue());
        }
        else if (col == 4) {
            Integer staminaTrainingPart = (Integer) value;
            train.setStaminaPart(staminaTrainingPart.intValue());
        }
        DBManager.instance().saveFutureTraining(train);
        fireTableCellUpdated(row, col);
        TrainingPanel.refreshPlayerDetail();
    }

    /**
     * Populate the table with the future training stored in the db,  if not present, create it
     * new and saves it
     */
    @Override
	public void populate() {
        p_V_data = new Vector<Object[]>();
        p_V_trainingsVector = new Vector<TrainingPerWeek>();

        Object[] aobj;

        TrainingPerWeek oldTrain = null;

        List<TrainingPerWeek> futureTrainings = DBManager.instance().getFutureTrainingsVector();

        for (Iterator<TrainingPerWeek> iter = futureTrainings.iterator(); iter.hasNext();) {
        	TrainingPerWeek train = iter.next();

            // if not found create it and saves it
            if (train.getTrainingIntensity() == -1) {
                if (oldTrain != null) {
                    train.setTrainingIntensity(oldTrain.getTrainingIntensity());
                    train.setStaminaPart(oldTrain.getStaminaPart());
                    train.setTrainingType(oldTrain.getTrainingType());
                } else {
                    train.setTrainingIntensity(100);
                    train.setStaminaPart(5);
                    train.setTrainingType(TrainingType.SET_PIECES);
                }

                DBManager.instance().saveFutureTraining(train);
            }

            String selectedTrain = TrainingType.toString(train.getTrainingType());

            aobj = (new Object[]{
                       train.getHattrickWeek() + "", //$NON-NLS-1$
                       train.getHattrickSeason() + "", //$NON-NLS-1$
                       new CBItem(selectedTrain, train.getTrainingType()), 
                       new Integer(train.getTrainingIntensity()), 
                       new Integer(train.getStaminaPart())
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
