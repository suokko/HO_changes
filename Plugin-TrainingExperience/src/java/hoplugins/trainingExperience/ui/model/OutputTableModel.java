// %3513105810:hoplugins.trainingExperience.ui.model%
package hoplugins.trainingExperience.ui.model;

import hoplugins.TrainingExperience;
import hoplugins.trainingExperience.constants.Skills;
import hoplugins.trainingExperience.ui.bar.VerticalIndicator;

import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import plugins.IHOMiniModel;
import plugins.ISpieler;


/**
 * Table Model for the main table showing training results
 *
 * @author Mag. Bernhard Hödl  AH - Solutions Augsten & Hödl OEG Neubachgasse 12 A - 2325 Himberg
 *         Tabellenmodel und Daten für die dargestellte Tabelle für das HO Plungin
 */
public class OutputTableModel extends AbstractTableModel {
    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = -1695207352334612268L;

	/** TODO Missing Parameter Documentation */
    private static final String DEFAULT_VALUE = ""; //$NON-NLS-1$

    //~ Instance fields ----------------------------------------------------------------------------

    private IHOMiniModel p_IHMM_HOMiniModel = null;

    //private ITrainingsManager p_ITM_trainingsManager = null;
    private Vector<String> p_V_columnNames = null; // entrys: Strings mit Spaltenname

    //data enthält die berechneten Werte aller Spieler, wieviel Training sie schon hattenn
    private Vector<ISpieler> p_V_data = null; //entrys: TrainingsManagerObjekte

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructor
     *
     * @param p_IHMM_HOMiniModel
     */
    public OutputTableModel(IHOMiniModel p_IHMM_HOMiniModel) {
        this.p_IHMM_HOMiniModel = p_IHMM_HOMiniModel;

        p_V_data = p_IHMM_HOMiniModel.getAllSpieler();

        p_V_columnNames = new Vector<String>();

        //Spaltennamen festlegen
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("Spieler"))); //$NON-NLS-1$
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("Alter"))); //$NON-NLS-1$
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("BestePosition"))); //$NON-NLS-1$
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("Torwart"))); //$NON-NLS-1$
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("Verteidigung"))); //$NON-NLS-1$
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("Spielaufbau"))); //$NON-NLS-1$
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("Passpiel"))); //$NON-NLS-1$
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("Fluegelspiel"))); //$NON-NLS-1$
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("Torschuss"))); //$NON-NLS-1$
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("Standards"))); //$NON-NLS-1$
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("Kondition"))); //$NON-NLS-1$
        p_V_columnNames.add(new String(this.p_IHMM_HOMiniModel.getLanguageString("ID"))); //$NON-NLS-1$
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
            ISpieler spieler = p_V_data.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return spieler.getName(); //Spielername

                case 1:
                    return spieler.getAlterWithAgeDaysAsString(); //Spieleralter

                case 2:
                    return p_IHMM_HOMiniModel.getHelper().getNameForPosition(spieler
                                                                             .getIdealPosition())
                           + " (" + spieler.getIdealPosStaerke(true) + ")"; //$NON-NLS-1$ //$NON-NLS-2$

                //Beste Postion
                case 3:
                    return createIcon(spieler, ISpieler.SKILL_TORWART);

                case 4:
                    return createIcon(spieler, ISpieler.SKILL_VERTEIDIGUNG);

                case 5:
                    return createIcon(spieler, ISpieler.SKILL_SPIELAUFBAU);

                case 6:
                    return createIcon(spieler, ISpieler.SKILL_PASSSPIEL);

                case 7:
                    return createIcon(spieler, ISpieler.SKILL_FLUEGEL);

                case 8:
                    return createIcon(spieler, ISpieler.SKILL_TORSCHUSS);

                case 9:
                    return createIcon(spieler, ISpieler.SKILL_STANDARDS);

                case 10:
                    return createIcon(spieler, ISpieler.SKILL_KONDITION);

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
        p_V_data = p_IHMM_HOMiniModel.getAllSpieler();
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
    private double getTrainingLength (ISpieler player, int skillIndex) {
        return player.getTrainingLength(Skills.getTrainedSkillCode(skillIndex),
                TrainingExperience.getStaffPanel()
                                  .getCoTrainerNumber(),
                                  TrainingExperience.getStaffPanel()
                                  .getTrainerLevelNumber(),
                p_IHMM_HOMiniModel.getTeam()
                                  .getTrainingslevel(),
                p_IHMM_HOMiniModel.getTeam()
                                  .getStaminaTrainingPart());    	
    }

    /**
     * Method that returns the offset in Training point
     *
     * @param player player to be considered
     * @param skill skill trained
     *
     * @return training point offset, if any
     */
    private double getOffset(ISpieler player, int skill) {
        double offset = player.getSubskill4SkillWithOffset(skill);
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
    private VerticalIndicator createIcon(ISpieler spieler, int skillIndex) {
        double point = getOffset(spieler, skillIndex);
        double trainingLength = getTrainingLength(spieler, skillIndex);

        VerticalIndicator vi = new VerticalIndicator(p_IHMM_HOMiniModel.getHelper().round(point, 1),
                                                     p_IHMM_HOMiniModel.getHelper().round(trainingLength, 1));

        return vi;
    }
}
