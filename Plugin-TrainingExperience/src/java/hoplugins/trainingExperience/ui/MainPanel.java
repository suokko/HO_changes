// %2330994818:hoplugins.trainingExperience.ui%
package hoplugins.trainingExperience.ui;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


/**
 * The Main Tab Panel
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class MainPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private AnalyzerPanel analyzer;
    private EffectPanel effect;
    private OutputPanel output;
    private TrainingRecapPanel recap;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new OutputPanel object.
     */
    public MainPanel() {
        super();
        output = new OutputPanel();
        recap = new TrainingRecapPanel();
        analyzer = new AnalyzerPanel();
        effect = new EffectPanel();
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public OutputPanel getOutput() {
        return output;
    }

    /**
     * Return Recap Panel
     *
     * @return TrainingRecapPanel
     */
    public TrainingRecapPanel getRecap() {
        return recap;
    }

    /**
     * update the panel with the new value
     */
    public void reload() {
        output.reload();
        recap.reload();
        analyzer.reload();
        effect.reload();
    }

    /**
     * Initialize the GUI
     */
    private void jbInit() {
        setLayout(new BorderLayout());

        JTabbedPane pane = new JTabbedPane();

        pane.addTab(Commons.getModel().getLanguageString("Training"), output);
        pane.addTab(PluginProperty.getString("MainPanel.Prediction"), recap);
        pane.addTab(PluginProperty.getString("MainPanel.Analyzer"), analyzer);
        pane.addTab(PluginProperty.getString("MainPanel.Effect"), effect);
        add(pane, BorderLayout.CENTER);
    }
}
