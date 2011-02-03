// %2109680998:de.hattrickorganizer.gui.info%
package de.hattrickorganizer.gui.info;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import plugins.IVerein;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * Zeigt die Vereininformationen an
 */
final class TrainerstabPanel extends JPanel implements de.hattrickorganizer.gui.Refreshable {

	private static final long serialVersionUID = 8873968321073527819L;

	//~ Instance fields ----------------------------------------------------------------------------

    private final ColorLabelEntry m_jpAerzte = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                             ColorLabelEntry.BG_STANDARD,
                                                             SwingConstants.LEFT);
    private final ColorLabelEntry m_jpCoTrainer = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_STANDARD,
                                                                SwingConstants.LEFT);
    private final ColorLabelEntry m_jpFinanzberater = new ColorLabelEntry("",
                                                                    ColorLabelEntry.FG_STANDARD,
                                                                    ColorLabelEntry.BG_STANDARD,
                                                                    SwingConstants.LEFT);
    private final ColorLabelEntry m_jpPRManager = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_STANDARD,
                                                                SwingConstants.LEFT);
    private final ColorLabelEntry m_jpPhysiotherapeuten = new ColorLabelEntry("",
                                                                        ColorLabelEntry.FG_STANDARD,
                                                                        ColorLabelEntry.BG_STANDARD,
                                                                        SwingConstants.LEFT);
    private final ColorLabelEntry m_jpPsychologen = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                  ColorLabelEntry.BG_STANDARD,
                                                                  SwingConstants.LEFT);
    private final ColorLabelEntry m_jpTWTrainer = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_STANDARD,
                                                                SwingConstants.LEFT);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainerstabPanel object.
     */
    protected TrainerstabPanel() {
        initComponents();

        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     */
    public final void reInit() {
        setLabels();
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void refresh() {
        setLabels();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void setLabels() {
        final IVerein verein = HOVerwaltung.instance().getModel()
                                                                     .getVerein();

        m_jpTWTrainer.setText(verein.getTorwartTrainer() + "");
        m_jpCoTrainer.setText(verein.getCoTrainer() + "");
        m_jpPsychologen.setText(verein.getPsychologen() + "");
        m_jpPRManager.setText(verein.getPRManager() + "");
        m_jpFinanzberater.setText(verein.getFinanzberater() + "");
        m_jpPhysiotherapeuten.setText(verein.getMasseure() + "");
        m_jpAerzte.setText(verein.getAerzte() + "");
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        this.setBackground(Color.white);

        setBorder(BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("Trainerstab")));

        
        JLabel label;

        setLayout(layout);

//		 -- GK trainers are no longer a part of Hattrick
//
//        label = new JLabel(HOVerwaltung.instance().getLanguageString("Torwarttrainer"));
//        constraints.anchor = GridBagConstraints.WEST;
//        constraints.gridx = 0;
//        constraints.gridy = 2;
//        constraints.gridwidth = 1;
//        layout.setConstraints(label, constraints);
//        add(label);
//
//        constraints.anchor = GridBagConstraints.EAST;
//        constraints.gridx = 1;
//        constraints.gridy = 2;
//        constraints.gridwidth = 1;
//        layout.setConstraints(m_jpTWTrainer.getComponent(false), constraints);
//        add(m_jpTWTrainer.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("CoTrainer"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpCoTrainer.getComponent(false), constraints);
        add(m_jpCoTrainer.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Psychologen"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpPsychologen.getComponent(false), constraints);
        add(m_jpPsychologen.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("PRManager"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpPRManager.getComponent(false), constraints);
        add(m_jpPRManager.getComponent(false));

// 		  Economists are long gone...
//
//        label = new JLabel(HOVerwaltung.instance().getLanguageString("Finanzberater"));
//        constraints.anchor = GridBagConstraints.WEST;
//        constraints.gridx = 0;
//        constraints.gridy = 6;
//        constraints.gridwidth = 1;
//        layout.setConstraints(label, constraints);
//        add(label);
//
//        constraints.anchor = GridBagConstraints.EAST;
//        constraints.gridx = 1;
//        constraints.gridy = 6;
//        constraints.gridwidth = 1;
//        layout.setConstraints(m_jpFinanzberater.getComponent(false), constraints);
//        add(m_jpFinanzberater.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Physiotherapeuten"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpPhysiotherapeuten.getComponent(false), constraints);
        add(m_jpPhysiotherapeuten.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Aerzte"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 8;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpAerzte.getComponent(false), constraints);
        add(m_jpAerzte.getComponent(false));
    }
}
