// %1126721452119:hoplugins.trainingExperience.ui%
package hoplugins.trainingExperience.ui;

import hoplugins.trainingExperience.ui.component.IntensityComboBox;
import hoplugins.trainingExperience.ui.component.TrainingComboBox;

import plugins.IHOMiniModel;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


/**
 * JTable class for past and future trainings table
 */
public class TrainingTable extends JTable {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -3824693600024962432L;
	private IHOMiniModel p_IHMM_HOMiniModel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingTable object.
     *
     * @param arg0 tableModel
     * @param miniModel HO MOdel
     */
    public TrainingTable(TableModel arg0, IHOMiniModel miniModel) {
        super(arg0);
        p_IHMM_HOMiniModel = null;
        p_IHMM_HOMiniModel = miniModel;
        setComboBoxEditor();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Initiates combo box and editor
     */
    private void setComboBoxEditor() {
        // Sets the combo box for selecting the training type
        JComboBox comboBox = new TrainingComboBox();
        TableColumn column = getColumnModel().getColumn(2);

        column.setCellEditor(new DefaultCellEditor(comboBox));
        column.setPreferredWidth(120);

        //		Sets the combo box for selecting the intensity
        JComboBox intensiBox = new IntensityComboBox();
        TableColumn column2 = getColumnModel().getColumn(3);

        column2.setCellEditor(new DefaultCellEditor(intensiBox));
        column2.setPreferredWidth(50);

        //		Sets the combo box for selecting the staminaTrainingPart
        JComboBox staminaTrainingPartBox = new IntensityComboBox();
        TableColumn column3 = getColumnModel().getColumn(4);

        column3.setCellEditor(new DefaultCellEditor(staminaTrainingPartBox));
        column3.setPreferredWidth(50);

        // Disable column resize 
        getColumnModel().getColumn(0).setResizable(false);
        getColumnModel().getColumn(1).setResizable(false);
        getColumnModel().getColumn(2).setResizable(false);
        getColumnModel().getColumn(3).setResizable(false);
        getColumnModel().getColumn(4).setResizable(false);
    }
}
