// %2109680998:de.hattrickorganizer.gui.info%
package ho.module.misc;

import gui.HOColorName;
import ho.core.gui.theme.ThemeManager;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import plugins.IVerein;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * Zeigt die Vereininformationen an
 */
final class StaffPanel extends JPanel {

	private static final long serialVersionUID = 8873968321073527819L;

	//~ Instance fields ----------------------------------------------------------------------------

    private final ColorLabelEntry doctorsLabel 			= new ColorLabelEntry("");
    private final ColorLabelEntry assistantCoachesLabel = new ColorLabelEntry("");
    private final ColorLabelEntry spokepersonsLabel 	= new ColorLabelEntry("");
    private final ColorLabelEntry physiotherapistsLabel = new ColorLabelEntry("");
    private final ColorLabelEntry psychologistsLabel 	= new ColorLabelEntry("");

    final GridBagLayout layout = new GridBagLayout();
    final GridBagConstraints constraints = new GridBagConstraints();
    /**
     * Creates a new TrainerstabPanel object.
     */
    protected StaffPanel() {
        initComponents();
     }

    void setLabels() {
        final IVerein verein = HOVerwaltung.instance().getModel().getVerein();
        if(verein != null){
	        assistantCoachesLabel.setText(verein.getCoTrainer() + "");
	        psychologistsLabel.setText(verein.getPsychologen() + "");
	        spokepersonsLabel.setText(verein.getPRManager() + "");
	        physiotherapistsLabel.setText(verein.getMasseure() + "");
	        doctorsLabel.setText(verein.getAerzte() + "");
        }
    }

    private void initComponents() {
        

        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        this.setBackground(ThemeManager.getColor(HOColorName.PANEL_BG));

        HOVerwaltung hoV = HOVerwaltung.instance();
        setBorder(BorderFactory.createTitledBorder(hoV.getLanguageString("Trainerstab")));

        
        JLabel label;

        setLayout(layout);

        label = new JLabel(hoV.getLanguageString("CoTrainer"));
        add(label,assistantCoachesLabel.getComponent(false),0);
 
        label = new JLabel(hoV.getLanguageString("Psychologen"));
        add(label,psychologistsLabel.getComponent(false),1);

        label = new JLabel(hoV.getLanguageString("PRManager"));
        add(label,spokepersonsLabel.getComponent(false),2);

        label = new JLabel(hoV.getLanguageString("Physiotherapeuten"));
        add(label,physiotherapistsLabel.getComponent(false),3 );

        label = new JLabel(hoV.getLanguageString("Aerzte"));
        add(label,doctorsLabel.getComponent(false),4 );

    }
    
    private void add(JLabel label,Component comp, int y){
    	constraints.anchor = GridBagConstraints.WEST;
    	constraints.gridx = 0;
    	constraints.gridy = y;
    	constraints.gridwidth = 1;
    	layout.setConstraints(label, constraints);
    	add(label);
    	constraints.anchor = GridBagConstraints.EAST;
    	constraints.gridx = 1;
    	constraints.gridy = y;
    	constraints.gridwidth = 1;
    	layout.setConstraints(comp, constraints);
    	add(comp);
    }
}
