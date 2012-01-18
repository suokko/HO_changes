// %1956788941:hoplugins.trainingExperience.ui.component%
package hoplugins.trainingExperience.ui.component;

import hoplugins.Commons;
import hoplugins.TrainingExperience;

import hoplugins.trainingExperience.constants.Trainings;
import hoplugins.trainingExperience.ui.model.FutureTrainingsTableModel;

import plugins.IFutureTrainingWeek;
import plugins.IHOMiniModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;


/**
 * Panel for Settings all the future training week
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class FutureSettingPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 4872598003436712955L;
	private FutureTrainingsTableModel futureModel;
    private JComboBox intensity;
    private JComboBox staminaTrainingPart;
    private JComboBox training;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FutureSettingPanel object.
     *
     * @param fm The futureTraining table model, used to update it when needed
     */
    public FutureSettingPanel(FutureTrainingsTableModel fm) {
        super();
        futureModel = fm;
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Populate the Future training table with the future training
     */
    protected void resetFutureTrainings() {
        IHOMiniModel p_IHMM_miniModel = Commons.getModel();
        List<IFutureTrainingWeek> futureTrainings = p_IHMM_miniModel.getFutureTrainingWeeks();

        for (Iterator<IFutureTrainingWeek> iter = futureTrainings.iterator(); iter.hasNext();) {
            IFutureTrainingWeek train = iter.next();
            train.setIntensitaet(intensity.getSelectedIndex());
            train.setStaminaTrainingPart(staminaTrainingPart.getSelectedIndex());
            train.setTyp(((CBItem)training.getSelectedItem()).getId());
            p_IHMM_miniModel.saveFutureTraining(train);
        }

        futureModel.populate();
        TrainingExperience.getTrainPanel().updateUI();
        TrainingExperience.refreshPlayerDetail();
    }

    /**
     * Initializes the state of this instance.
     */
    private void jbInit() {
        IHOMiniModel p_IHMM_miniModel = Commons.getModel();
        List<IFutureTrainingWeek> futureTrainings = p_IHMM_miniModel.getFutureTrainingWeeks();
    	IFutureTrainingWeek firstFutureTraining = futureTrainings.get(0);
        training = new TrainingComboBox();
        final int ttyp = firstFutureTraining.getTyp();
        training.setSelectedItem(new CBItem(Trainings.getTrainingDescription(ttyp), ttyp));
        intensity = new IntensityComboBox();
        intensity.setSelectedIndex(firstFutureTraining.getIntensitaet());
        staminaTrainingPart = new IntensityComboBox();
        staminaTrainingPart.setSelectedIndex(firstFutureTraining.getStaminaTrainingPart());

        JButton button = new JButton(Commons.getModel().getLanguageString("Aendern")); //$NON-NLS-1$

        button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    resetFutureTrainings();
                }
            });
        add(training);
        add(intensity);
        add(staminaTrainingPart);
        add(button);
    }
}