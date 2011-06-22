// %1301807047:de.hattrickorganizer.gui.info%
package de.hattrickorganizer.gui.info;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.Basics;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Liga;


/**
 * Display basics
 */
class BasicsPanel extends JPanel {
	
	private static final long serialVersionUID = 358240701613667104L;
	
	//~ Instance fields ----------------------------------------------------------------------------

    private final ColorLabelEntry leagueLabel 	= new ColorLabelEntry("");
    private final ColorLabelEntry managerLabel 	= new ColorLabelEntry("");
    private final ColorLabelEntry posLabel		= new ColorLabelEntry("");
    private final ColorLabelEntry pointsLabel	= new ColorLabelEntry("");
    private final ColorLabelEntry seasonLabel	= new ColorLabelEntry("");
    private final ColorLabelEntry matchRoundLabel = new ColorLabelEntry("");
    private final ColorLabelEntry arenaLabel	= new ColorLabelEntry("");
    private final ColorLabelEntry teamLabel	 	= new ColorLabelEntry("");
    private final ColorLabelEntry goalsLabel	= new ColorLabelEntry("");

    final GridBagLayout layout = new GridBagLayout();
    final GridBagConstraints constraints = new GridBagConstraints();
 
    /**
     * Creates a new BasicsPanel object.
     */
    BasicsPanel() {
        initComponents();
     }

    void setLabels() {
        final Basics basics = HOVerwaltung.instance().getModel().getBasics();
        final Liga liga = HOVerwaltung.instance().getModel().getLiga();

        teamLabel.setText(basics.getTeamName());
        managerLabel.setText(basics.getManager());
        arenaLabel.setText(HOVerwaltung.instance().getModel().getStadium().getStadienname());
        seasonLabel.setText(basics.getSeason() + "");
        matchRoundLabel.setText(liga.getSpieltag() + "");
        leagueLabel.setText(liga.getLiga());
        posLabel.setText(liga.getPlatzierung() + "");
        pointsLabel.setText(liga.getPunkte() + "");
        goalsLabel.setText(liga.getToreFuer() + ":" + liga.getToreGegen());
    }

    private void initComponents() {
        JLabel label;
        
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        this.setBackground(ThemeManager.getColor("ho.panel.background"));
        setBorder(BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("Allgemein")));
        setLayout(layout);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Verein"));
        add(label,teamLabel.getComponent(false),1);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Manager"));
        add(label,managerLabel.getComponent(false),2);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Stadion"));
        add(label,arenaLabel.getComponent(false),3);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Season"));
        add(label,seasonLabel.getComponent(false),4);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Spieltag"));
        add(label,matchRoundLabel.getComponent(false),5);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Liga"));
        add(label,leagueLabel.getComponent(false),6);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Platzierung"));
        add(label,posLabel.getComponent(false),7);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Punkte"));
        add(label,pointsLabel.getComponent(false),8);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Torverhaeltnis"));
        add(label,goalsLabel.getComponent(false),9);

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
