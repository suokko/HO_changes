// %2109680998:de.hattrickorganizer.gui.info%
package de.hattrickorganizer.gui.info;

import gui.HOColorName;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import plugins.IVerein;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * Zeigt die Vereininformationen an
 */
final class TrainerstabPanel extends JPanel {

	private static final long serialVersionUID = 8873968321073527819L;

	//~ Instance fields ----------------------------------------------------------------------------

    private final ColorLabelEntry m_jpAerzte 		= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpCoTrainer 	= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpFinanzberater = new ColorLabelEntry("");
    private final ColorLabelEntry m_jpPRManager 	= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpPhysiotherapeuten = new ColorLabelEntry("");
    private final ColorLabelEntry m_jpPsychologen 	= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpTWTrainer 	= new ColorLabelEntry("");

    final GridBagLayout layout = new GridBagLayout();
    final GridBagConstraints constraints = new GridBagConstraints();
    /**
     * Creates a new TrainerstabPanel object.
     */
    protected TrainerstabPanel() {
        initComponents();
     }

    void setLabels() {
        final IVerein verein = HOVerwaltung.instance().getModel().getVerein();

        m_jpTWTrainer.setText(verein.getTorwartTrainer() + "");
        m_jpCoTrainer.setText(verein.getCoTrainer() + "");
        m_jpPsychologen.setText(verein.getPsychologen() + "");
        m_jpPRManager.setText(verein.getPRManager() + "");
        m_jpFinanzberater.setText(verein.getFinanzberater() + "");
        m_jpPhysiotherapeuten.setText(verein.getMasseure() + "");
        m_jpAerzte.setText(verein.getAerzte() + "");
    }

    private void initComponents() {
        

        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        this.setBackground(ThemeManager.getColor(HOColorName.PANEL_BG));

        setBorder(BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("Trainerstab")));

        
        JLabel label;

        setLayout(layout);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("CoTrainer"));
        add(label,m_jpCoTrainer.getComponent(false),0);
 
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Psychologen"));
        add(label,m_jpPsychologen.getComponent(false),1);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("PRManager"));
        add(label,m_jpPRManager.getComponent(false),2);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Physiotherapeuten"));
        add(label,m_jpPhysiotherapeuten.getComponent(false),3 );

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Aerzte"));
        add(label,m_jpAerzte.getComponent(false),4 );

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
