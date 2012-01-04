// %1126721451604:hoplugins.trainingExperience.ui.model%
package hoplugins.trainingExperience.ui.model;

import hoplugins.Commons;

import hoplugins.trainingExperience.constants.Trainings;
import hoplugins.trainingExperience.ui.component.CBItem;

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
	 * 
	 */
	private static final long serialVersionUID = -4741270987836161270L;

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
    @Override
	public void setValueAt(Object value, int row, int col) {
        Object[] aobj = (Object[]) p_V_data.get(row);

        aobj[col] = value;

        ITrainingWeek train = (ITrainingWeek) p_V_trainingsVector.get(p_V_data.size() - row - 1);
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

        Commons.getModel().saveTraining(train);
        fireTableCellUpdated(row, col);
    }

    /**
     * Populate the table with the old trainings week loaded from HO API
     */
    @Override
	public void populate() {
        p_V_data = new Vector<Object[]>();

        // Stores ho trainings into training vector
        p_V_trainingsVector = Commons.getModel().getTrainingsManager().getTrainingsVector();

        Object[] aobj;

        // for each training week
        for (Iterator<ITrainingWeek> it = p_V_trainingsVector.iterator(); it.hasNext();) {
            ITrainingWeek train = it.next();
            String selectedTrain = Trainings.getTrainingDescription(train.getTyp());

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