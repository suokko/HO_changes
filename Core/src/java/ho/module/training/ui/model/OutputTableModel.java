// %3513105810:hoplugins.trainingExperience.ui.model%
package ho.module.training.ui.model;

import ho.core.constants.player.PlayerSkill;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;
import ho.core.model.player.SpielerPosition;
import ho.core.training.WeeklyTrainingType;
import ho.core.util.Helper;
import ho.module.training.Skills;
import ho.module.training.TrainingPanel;
import ho.module.training.ui.comp.VerticalIndicator;

import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;


/**
 * Table Model for the main table showing training results
 *
 * @author Mag. Bernhard Hödl  AH - Solutions Augsten & Hödl OEG Neubachgasse 12 A - 2325 Himberg
 *         Tabellenmodel und Daten für die dargestellte Tabelle für das HO Plugin
 */
public class OutputTableModel extends AbstractTableModel {
    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = -1695207352334612268L;

	/** TODO Missing Parameter Documentation */
    private static final String DEFAULT_VALUE = "";

    //private ITrainingsManager p_ITM_trainingsManager = null;
    private Vector<String> p_V_columnNames = null;

    //data enthält die berechneten Werte aller Spieler, wieviel Training sie schon hattenn
    private Vector<Spieler> p_V_data = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructor
     *
     * @param p_IHMM_HOMiniModel
     */
    public OutputTableModel() {
    	HOVerwaltung hoV = HOVerwaltung.instance();
        p_V_data = hoV.getModel().getAllSpieler();

        p_V_columnNames = new Vector<String>();

        //Spaltennamen festlegen
        p_V_columnNames.add(hoV.getLanguageString("Spieler"));
        p_V_columnNames.add(hoV.getLanguageString("ls.player.age"));
        p_V_columnNames.add(hoV.getLanguageString("BestePosition"));
        p_V_columnNames.add(hoV.getLanguageString("ls.player.skill.keeper"));
        p_V_columnNames.add(hoV.getLanguageString("ls.player.skill.defending"));
        p_V_columnNames.add(hoV.getLanguageString("ls.player.skill.playmaking"));
        p_V_columnNames.add(hoV.getLanguageString("ls.player.skill.passing"));
        p_V_columnNames.add(hoV.getLanguageString("ls.player.skill.winger"));
        p_V_columnNames.add(hoV.getLanguageString("ls.player.skill.scoring"));
        p_V_columnNames.add(hoV.getLanguageString("ls.player.skill.setpieces"));
        p_V_columnNames.add(hoV.getLanguageString("ls.player.skill.stamina"));
        p_V_columnNames.add(hoV.getLanguageString("ls.player.id"));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    @Override
	public Class<?> getColumnClass(int c) {
        if ((c > 2) && (c < 11)) {
            return JPanel.class;
        }

        return getValueAt(0, c).getClass();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return p_V_columnNames.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    @Override
	public String getColumnName(int c) {
        return p_V_columnNames.get(c);
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return p_V_data.size();
    }

    /**
     * Returns as toolTip for the cell, the last skillup for the proper player and skill
     *
     * @param rowIndex
     * @param columnIndex
     *
     * @return toolTip
     */
    public Object getToolTipAt(int rowIndex, int columnIndex) {
        VerticalIndicator vi = (VerticalIndicator) getValueAt(rowIndex, columnIndex);

        return vi.getToolTipText();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Spieler spieler = p_V_data.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return spieler.getName(); //Spielername

                case 1:
                    return spieler.getAlterWithAgeDaysAsString(); //Spieleralter

                case 2:
                    return SpielerPosition.getNameForPosition(spieler
                                                                             .getIdealPosition())
                           + " (" + spieler.getIdealPosStaerke(true) + ")"; //$NON-NLS-1$ //$NON-NLS-2$

                //Beste Postion
                case 3:
                    return createIcon(spieler, PlayerSkill.KEEPER);

                case 4:
                    return createIcon(spieler, PlayerSkill.DEFENDING);

                case 5:
                    return createIcon(spieler, PlayerSkill.PLAYMAKING);

                case 6:
                    return createIcon(spieler, PlayerSkill.PASSING);

                case 7:
                    return createIcon(spieler, PlayerSkill.WINGER);

                case 8:
                    return createIcon(spieler, PlayerSkill.SCORING);

                case 9:
                    return createIcon(spieler, PlayerSkill.SET_PIECES);

                case 10:
                    return createIcon(spieler, PlayerSkill.STAMINA);

                case 11:
                    return Integer.toString(spieler.getSpielerID());

                default:
                    return DEFAULT_VALUE;
            }
        } catch (Exception e) {
            return DEFAULT_VALUE;
        }
    }

    /**
     * Refill the table with the new training based on the last changes
     */
    public void fillWithData() {
        p_V_data = HOVerwaltung.instance().getModel().getAllSpieler();
        fireTableDataChanged();
    }

    /**
     * Get the training length for a player in a specific skill
     *
     * @param player player to be considered
     * @param skillIndex skill trained
     *
     * @return predicted training length
     */
    private double getTrainingLength (Spieler player, int skillIndex) {
    	double dReturn = 0;
    	WeeklyTrainingType wt = WeeklyTrainingType.instance(Skills.getTrainedSkillCode(skillIndex));
    	if (wt != null) {
	        dReturn = wt.getTrainingLength(player, TrainingPanel.getStaffPanel().getCoTrainerNumber(),
	        		TrainingPanel.getStaffPanel().getTrainerLevelNumber(),
	        		HOVerwaltung.instance().getModel().getTeam().getTrainingslevel(),
	        		HOVerwaltung.instance().getModel().getTeam().getStaminaTrainingPart());
    	}
    	return dReturn;
    }

    /**
     * Method that returns the offset in Training point
     *
     * @param player player to be considered
     * @param skill skill trained
     *
     * @return training point offset, if any
     */
    private double getOffset(Spieler player, int skill) {
        double offset = player.getSubskill4Pos(skill);
        double length = getTrainingLength(player, skill);
        return offset * length;
    }

    /**
     * Create a VerticalIndicator object
     *
     * @param spieler object from which create the indicator
     * @param skillIndex points to skillup
     *
     * @return the VerticalIndicator object
     */
    private VerticalIndicator createIcon(Spieler spieler, int skillIndex) {
        double point = getOffset(spieler, skillIndex);
        double trainingLength = getTrainingLength(spieler, skillIndex);

        VerticalIndicator vi = new VerticalIndicator(Helper.round(point, 1),Helper.round(trainingLength, 1));

        return vi;
    }
}
