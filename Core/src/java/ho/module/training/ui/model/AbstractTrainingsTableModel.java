// %1126721451323:hoplugins.trainingExperience.ui.model%
package ho.module.training.ui.model;

import ho.core.model.HOVerwaltung;
import ho.core.training.TrainingPerWeek;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;


/**
 * Basic training table model
 */
public abstract class AbstractTrainingsTableModel extends AbstractTableModel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 *
	 */
	private static final long serialVersionUID = 5399479264645517270L;

    /** Vector of ITrainingPerPlayer object */
    protected Vector<Object[]> p_V_data;

    /** Vector of ITrainingPerWeek object */
    protected List<TrainingPerWeek> p_V_trainingsVector;
    private String[] columnNames;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AbstractTrainingsTableModel object.
     *
     * @param miniModel
     */
    public AbstractTrainingsTableModel() {
        p_V_data = new Vector<Object[]>();
        p_V_trainingsVector = new Vector<TrainingPerWeek>();
        columnNames = new String[5];
        HOVerwaltung hoV = HOVerwaltung.instance();
        columnNames[0] = hoV.getLanguageString("Week"); //$NON-NLS-1$
        columnNames[1] = hoV.getLanguageString("Season"); //$NON-NLS-1$
        columnNames[2] = hoV.getLanguageString("ls.team.trainingtype"); //$NON-NLS-1$
        columnNames[3] = hoV.getLanguageString("Intensitaet"); //$NON-NLS-1$
        columnNames[4] = hoV.getLanguageString("training.staminashare"); //$NON-NLS-1$
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Method that returns if a cell is editable or not
     *
     * @param row
     * @param column
     *
     * @return
     */
    @Override
	public boolean isCellEditable(int row, int column) {
        return (column == 2 || column == 3 || column == 4);
    }

    /**
     * DOCUMENT ME!
     *
     * @param column
     *
     * @return
     */
    @Override
	public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    /**
     * Return number of columns
     *
     * @return
     */
    @Override
	public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Return header for the specified column
     *
     * @param column
     *
     * @return column header
     */
    @Override
	public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Returns row number
     *
     * @return
     */
    @Override
	public int getRowCount() {
        return p_V_data.size();
    }

    /**
     * Method that returns the Training vector
     *
     * @return actual training vector
     */
    public List<TrainingPerWeek> getTrainingsData() {
        return p_V_trainingsVector;
    }

    /**
     * Returns the cell value
     *
     * @param row
     * @param column
     *
     * @return Object representing the cell value
     */
    @Override
	public Object getValueAt(int row, int column) {
        Object[] aobj = (Object[]) p_V_data.get(row);

        return aobj[column];
    }

    /**
     * Method to be called to populate the table with the data from HO API
     */
    public abstract void populate();
}
