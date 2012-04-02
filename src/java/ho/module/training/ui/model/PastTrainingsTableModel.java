// %1126721451604:hoplugins.trainingExperience.ui.model%
package ho.module.training.ui.model;

import ho.core.constants.TrainingType;
import ho.core.datatype.CBItem;
import ho.core.db.DBManager;
import ho.core.training.TrainingPerWeek;
import ho.core.training.TrainingsManager;

import java.util.Iterator;
import java.util.Vector;


/**
 * Customized table model for past trainings
 */
public class PastTrainingsTableModel extends AbstractTrainingsTableModel {
    //~ Constructors -------------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -4741270987836161270L;

	/**
     * Creates a new PastTrainingsTableModel object.
     *
     * @param miniModel
     */
    public PastTrainingsTableModel() {
        super();
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
    @Override
	public void setValueAt(Object value, int row, int col) {
        Object[] aobj = (Object[]) p_V_data.get(row);

        aobj[col] = value;

        TrainingPerWeek train = (TrainingPerWeek) p_V_trainingsVector.get(p_V_data.size() - row - 1);
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

        DBManager.instance().saveTraining((TrainingPerWeek) train);
        fireTableCellUpdated(row, col);
    }

    /**
     * Populate the table with the old trainings week loaded from HO API
     */
    @Override
	public void populate() {
        p_V_data = new Vector<Object[]>();

        // Stores ho trainings into training vector
        p_V_trainingsVector = TrainingsManager.instance().getTrainingsVector();

        Object[] aobj;

        // for each training week
        for (Iterator<TrainingPerWeek> it = p_V_trainingsVector.iterator(); it.hasNext();) {
        	TrainingPerWeek train = it.next();
            String selectedTrain = TrainingType.toString(train.getTyp());

            aobj = (new Object[]{
                       train.getHattrickWeek() + "", //$NON-NLS-1$
                       train.getHattrickSeason() + "", //$NON-NLS-1$
                       new CBItem(selectedTrain, train.getTyp()),
                       new Integer(train.getIntensitaet()),
                       new Integer(train.getStaminaTrainingPart())
                   });

            // add the data object into the table model
            p_V_data.add(0, aobj);
        }

        fireTableDataChanged();
    }
}
