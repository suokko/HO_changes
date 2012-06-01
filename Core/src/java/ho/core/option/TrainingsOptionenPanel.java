// %2563786322:de.hattrickorganizer.gui.menu.option%
package ho.core.option;


import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.training.type.CrossingWeeklyTraining;
import ho.core.training.type.DefendingWeeklyTraining;
import ho.core.training.type.GoalkeepingWeeklyTraining;
import ho.core.training.type.PlaymakingWeeklyTraining;
import ho.core.training.type.ScoringWeeklyTraining;
import ho.core.training.type.SetPiecesWeeklyTraining;
import ho.core.training.type.ShortPassesWeeklyTraining;
import ho.core.training.type.WeeklyTrainingType;

import java.awt.GridLayout;

import javax.swing.JLabel;

/**
 * Optionen für das Training
 */
final class TrainingsOptionenPanel extends ImagePanel {
    //~ Static / Instance fields ----------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;
	private TrainingAdjustmentPanel m_tapAgeFactor;
    private TrainingAdjustmentPanel m_jtapAssisstantFactor;
    private TrainingAdjustmentPanel m_jtapIntensityFactor;
    private TrainingAdjustmentPanel m_jtapCoachFactor;
    private TrainingAdjustmentPanel m_jtapWinger;
    private TrainingAdjustmentPanel m_jtapPassing;
    private TrainingAdjustmentPanel m_jtapPlaymaking;
    private TrainingAdjustmentPanel m_jtapSetPieces;
    private TrainingAdjustmentPanel m_jtapScoring;
    private TrainingAdjustmentPanel m_jtapGoalkeeping;
    private TrainingAdjustmentPanel m_jtapDefending;
    private TrainingAdjustmentPanel m_jtapOsmosis;
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingsOptionenPanel object.
     */
    protected TrainingsOptionenPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param changeEvent TODO Missing Method Parameter Documentation
     */
    public final void refresh() {
        UserParameter.temp().TRAINING_OFFSET_GOALKEEPING =  m_jtapGoalkeeping.getValue();
        UserParameter.temp().TRAINING_OFFSET_DEFENDING =  m_jtapDefending.getValue();
        UserParameter.temp().TRAINING_OFFSET_PLAYMAKING = m_jtapPlaymaking.getValue();
        UserParameter.temp().TRAINING_OFFSET_PASSING =  m_jtapPassing.getValue();
        UserParameter.temp().TRAINING_OFFSET_WINGER =  m_jtapWinger.getValue();
        UserParameter.temp().TRAINING_OFFSET_SCORING =  m_jtapScoring.getValue();
        UserParameter.temp().TRAINING_OFFSET_SETPIECES = m_jtapSetPieces.getValue();
        UserParameter.temp().TRAINING_OFFSET_OSMOSIS =  m_jtapOsmosis.getValue();
        UserParameter.temp().TRAINING_OFFSET_AGE = m_tapAgeFactor.getValue();
        UserParameter.temp().TrainerFaktor = m_jtapCoachFactor.getValue();
        UserParameter.temp().TRAINING_OFFSET_ASSISTANTS = m_jtapAssisstantFactor.getValue();
        UserParameter.temp().TRAINING_OFFSET_INTENSITY = m_jtapIntensityFactor.getValue();
        OptionManager.instance().setReInitNeeded();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
    	setLayout(new GridLayout(14, 1, 4, 4));

        JLabel label = new JLabel("   " + 
        		ho.core.model.HOVerwaltung.instance().getLanguageString("VoraussichtlicheTrainingwochen"));
        add(label);

        m_jtapGoalkeeping = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("training.goalkeeping"),
                   GoalkeepingWeeklyTraining.instance().getBaseTrainingLength(), UserParameter.temp().TRAINING_OFFSET_GOALKEEPING, this);
        add(m_jtapGoalkeeping);

        m_jtapDefending = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("training.defending"),
        			DefendingWeeklyTraining.instance().getBaseTrainingLength(),UserParameter.temp().TRAINING_OFFSET_DEFENDING, this);
        add(m_jtapDefending);

        m_jtapPlaymaking = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("training.playmaking"),
        		PlaymakingWeeklyTraining.instance().getBaseTrainingLength(), UserParameter.temp().TRAINING_OFFSET_PLAYMAKING, this);
        add(m_jtapPlaymaking);

        m_jtapPassing = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("training.short_passes"),
        		ShortPassesWeeklyTraining.instance().getBaseTrainingLength(), UserParameter.temp().TRAINING_OFFSET_PASSING, this);
        add(m_jtapPassing);

        m_jtapWinger = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("training.crossing"),
        		CrossingWeeklyTraining.instance().getBaseTrainingLength(), UserParameter.temp().TRAINING_OFFSET_WINGER, this);
        add(m_jtapWinger);

        m_jtapScoring = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("training.scoring"),
        		ScoringWeeklyTraining.instance().getBaseTrainingLength(), UserParameter.temp().TRAINING_OFFSET_SCORING, this);
        add(m_jtapScoring);
        
        m_jtapSetPieces = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("training.set_pieces"),
        		SetPiecesWeeklyTraining.instance().getBaseTrainingLength(), UserParameter.temp().TRAINING_OFFSET_SETPIECES, this);
        add(m_jtapSetPieces);
        
        label = new JLabel("   " + HOVerwaltung.instance().getLanguageString("TrainingFaktoren"));
        add(label);
        
        m_jtapOsmosis = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("training.osmosis"),
        		WeeklyTrainingType.OSMOSIS_BASE_PERCENTAGE, UserParameter.temp().TRAINING_OFFSET_OSMOSIS, this);
        add(m_jtapOsmosis);
        
        m_tapAgeFactor = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("FaktorSpieleralter"),
        		WeeklyTrainingType.BASE_AGE_FACTOR, UserParameter.temp().TRAINING_OFFSET_AGE, this);
        add(m_tapAgeFactor);

        m_jtapCoachFactor = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("FaktorTrainerfertigkeit"),
        		WeeklyTrainingType.BASE_COACH_FACTOR, UserParameter.temp().TrainerFaktor, this);
        add(m_jtapCoachFactor);

        m_jtapAssisstantFactor = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("FaktorCoTraineranzahl"),
        		WeeklyTrainingType.BASE_ASSISTANT_COACH_FACTOR, UserParameter.temp().TRAINING_OFFSET_ASSISTANTS, this);
        add(m_jtapAssisstantFactor);

        m_jtapIntensityFactor = new TrainingAdjustmentPanel(HOVerwaltung.instance().getLanguageString("FaktorIntensitaet"),
        		WeeklyTrainingType.BASE_INTENSITY_FACTOR, UserParameter.temp().TRAINING_OFFSET_INTENSITY, this);
        add(m_jtapIntensityFactor);
    }
}
