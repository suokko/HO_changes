// %2597556203:hoplugins.trainingExperience.ui.model%
package hoplugins.trainingExperience.ui.model;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.trainingExperience.vo.SkillChange;

import java.util.List;

import javax.swing.table.AbstractTableModel;


/**
 * TableModel representing skill changes for individual players.
 *
 * @author NetHyperon
 *
 * @see hoplugins.trainingExperience.vo.SkillChange
 */
public class ChangesTableModel extends AbstractTableModel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -9082549798814304017L;
	private List values;
    private String[] colNames = new String[7];

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ChangesTableModel object.
     *
     * @param values List of values to show in table.
     */
    public ChangesTableModel(List values) {
        super();
        this.colNames[0] = PluginProperty.getString("Week"); //$NON-NLS-1$
        this.colNames[1] = Commons.getModel().getLanguageString("Season"); //$NON-NLS-1$
        this.colNames[2] = Commons.getModel().getLanguageString("Spieler"); //$NON-NLS-1$
        this.colNames[3] = PluginProperty.getString("Skill"); //$NON-NLS-1$
        this.colNames[4] = PluginProperty.getString("TO"); //$NON-NLS-1$
        this.colNames[5] = "isOld"; //$NON-NLS-1$
        this.colNames[6] = "playerId"; //$NON-NLS-1$

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
        SkillChange change = (SkillChange) values.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return Integer.toString(change.getSkillup().getHtWeek());

            case 1:
                return Integer.toString(change.getSkillup().getHtSeason());

            case 2:
                return change.getPlayer().getName();

            case 3:
                return Integer.toString(change.getSkillup().getType());

            case 4:
                return Commons.getModel().getHelper().getNameForSkill(change.getSkillup().getValue(),
                                                                      true);

            case 5:
                return Boolean.valueOf(change.getPlayer().isOld());

            case 6:
                return Integer.toString(change.getPlayer().getSpielerID());

            default:
                return ""; //$NON-NLS-1$
        }
    }
}
