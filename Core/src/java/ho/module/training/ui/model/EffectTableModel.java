// %1838430110:hoplugins.trainingExperience.ui.model%
package ho.module.training.ui.model;

import ho.core.model.HOVerwaltung;
import ho.module.training.TrainWeekEffect;

import java.text.NumberFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;



/**
 * TableModel representing the effect of training.
 *
 * @author NetHyperon
 *
 * @see hoplugins.trainingExperience.vo.TrainWeekEffect
 */
public class EffectTableModel extends AbstractTableModel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 6647124384624067021L;

	/** TODO Missing Parameter Documentation */
    private static final NumberFormat FORMATTER = NumberFormat.getInstance();

    //~ Instance fields ----------------------------------------------------------------------------

    private List<TrainWeekEffect> values;
    private String[] colNames = new String[9];

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new EffectTableModel object.
     *
     * @param values List of values to show in table.
     */
    public EffectTableModel(List<TrainWeekEffect> values) {
        super();
        FORMATTER.setMaximumFractionDigits(2);
        FORMATTER.setMinimumFractionDigits(2);
        HOVerwaltung hoV = HOVerwaltung.instance();
        this.colNames[0] = hoV.getLanguageString("Week"); //$NON-NLS-1$
        this.colNames[1] = hoV.getLanguageString("Season"); //$NON-NLS-1$
        this.colNames[2] = hoV.getLanguageString("TSI"); //$NON-NLS-1$
        this.colNames[3] = hoV.getLanguageString("AverageTSI"); //$NON-NLS-1$
        this.colNames[4] = hoV.getLanguageString("TSI") + " +/-"; //$NON-NLS-1$ //$NON-NLS-2$
        this.colNames[5] = hoV.getLanguageString("DurchschnittForm"); //$NON-NLS-1$
        this.colNames[6] = hoV.getLanguageString("Form") + " +/-"; //$NON-NLS-1$ //$NON-NLS-2$
        this.colNames[7] = hoV.getLanguageString("Skillups"); //$NON-NLS-1$
        this.colNames[8] = hoV.getLanguageString("Skill"); //$NON-NLS-1$

        this.values = values;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return colNames.length;
    }

    /**
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    @Override
	public String getColumnName(int column) {
        return colNames[column];
    }

    /**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return values.size();
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        TrainWeekEffect effect = values.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return Integer.toString(effect.getHattrickWeek());

            case 1:
                return Integer.toString(effect.getHattrickSeason());

            case 2:
                return Integer.toString(effect.getTotalTSI());

            case 3:
                return Integer.toString(effect.getAverageTSI());

            case 4:
                return "+" + effect.getTSIIncrease() + " / " //$NON-NLS-1$ //$NON-NLS-2$
                       + effect.getTSIDecrease();

            case 5:
                return FORMATTER.format(effect.getAverageForm());

            case 6:
                return "+" + effect.getFormIncrease() + " / " //$NON-NLS-1$ //$NON-NLS-2$
                       + effect.getFormDecrease();

            case 7:
                return Integer.toString(effect.getAmountSkillups());

            case 8:
                return Integer.toString(effect.getTrainingType());

            default:
                return ""; //$NON-NLS-1$
        }
    }
}
