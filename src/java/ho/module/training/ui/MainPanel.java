// %2330994818:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.hattrickorganizer.model.HOVerwaltung;


/**
 * The Main Tab Panel
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class MainPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 8122222543546746654L;
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
        HOVerwaltung hoV = HOVerwaltung.instance();
        pane.addTab(hoV.getLanguageString("Training"), output);
        pane.addTab(hoV.getLanguageString("MainPanel.Prediction"), recap);
        pane.addTab(hoV.getLanguageString("MainPanel.Analyzer"), analyzer);
        pane.addTab(hoV.getLanguageString("MainPanel.Effect"), effect);
        add(pane, BorderLayout.CENTER);
    }
}
