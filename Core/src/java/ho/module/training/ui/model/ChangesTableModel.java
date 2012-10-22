// %2597556203:hoplugins.trainingExperience.ui.model%
package ho.module.training.ui.model;

import ho.core.constants.player.PlayerAbility;
import ho.core.model.HOVerwaltung;
import ho.module.training.SkillChange;

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
	private List<SkillChange> values;
    private String[] colNames = new String[7];

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ChangesTableModel object.
     *
     * @param values List of values to show in table.
     */
    public ChangesTableModel(List<SkillChange> values) {
        super();
        HOVerwaltung hoV = HOVerwaltung.instance();
        this.colNames[0] = hoV.getLanguageString("Week"); //$NON-NLS-1$
        this.colNames[1] = hoV.getLanguageString("Season"); //$NON-NLS-1$
        this.colNames[2] = hoV.getLanguageString("Spieler"); //$NON-NLS-1$
        this.colNames[3] = hoV.getLanguageString("ls.player.skill"); //$NON-NLS-1$
        this.colNames[4] = hoV.getLanguageString("TO"); //$NON-NLS-1$
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
        SkillChange change = values.get(rowIndex);

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
                return PlayerAbility.getNameForSkill(change.getSkillup().getValue(),
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
