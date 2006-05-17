// %1301807047:de.hattrickorganizer.gui.info%
package de.hattrickorganizer.gui.info;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.model.HOVerwaltung;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Zeigt die Sonstige Informationen an
 */
public class BasicsPanel extends JPanel implements de.hattrickorganizer.gui.Refreshable {
    //~ Instance fields ----------------------------------------------------------------------------

    private ColorLabelEntry m_jpLiga = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                           ColorLabelEntry.BG_STANDARD, JLabel.LEFT);
    private ColorLabelEntry m_jpManagername = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                  ColorLabelEntry.BG_STANDARD,
                                                                  JLabel.LEFT);
    private ColorLabelEntry m_jpPlatzierung = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                  ColorLabelEntry.BG_STANDARD,
                                                                  JLabel.LEFT);
    private ColorLabelEntry m_jpPunkte = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                             ColorLabelEntry.BG_STANDARD,
                                                             JLabel.LEFT);
    private ColorLabelEntry m_jpSeason = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                             ColorLabelEntry.BG_STANDARD,
                                                             JLabel.LEFT);
    private ColorLabelEntry m_jpSpieltag = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                               ColorLabelEntry.BG_STANDARD,
                                                               JLabel.LEFT);
    private ColorLabelEntry m_jpStadion = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                              ColorLabelEntry.BG_STANDARD,
                                                              JLabel.LEFT);
    private ColorLabelEntry m_jpTeamname = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                               ColorLabelEntry.BG_STANDARD,
                                                               JLabel.LEFT);
    private ColorLabelEntry m_jpTorverhaeltnis = new ColorLabelEntry("",
                                                                     ColorLabelEntry.FG_STANDARD,
                                                                     ColorLabelEntry.BG_STANDARD,
                                                                     JLabel.LEFT);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new BasicsPanel object.
     */
    public BasicsPanel() {
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
        final de.hattrickorganizer.model.Basics basics = HOVerwaltung.instance().getModel()
                                                                     .getBasics();
        final de.hattrickorganizer.model.Liga liga = HOVerwaltung.instance().getModel().getLiga();

        m_jpTeamname.setText(basics.getTeamName());
        m_jpManagername.setText(basics.getManager());
        m_jpStadion.setText(HOVerwaltung.instance().getModel().getStadium().getStadienname());
        m_jpSeason.setText(basics.getSeason() + "");
        m_jpSpieltag.setText(liga.getSpieltag() + "");
        m_jpLiga.setText(liga.getLiga());
        m_jpPlatzierung.setText(liga.getPlatzierung() + "");
        m_jpPunkte.setText(liga.getPunkte() + "");
        m_jpTorverhaeltnis.setText(liga.getToreFuer() + ":" + liga.getToreGegen());
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        final Properties properties = HOVerwaltung.instance().getResource();
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        this.setBackground(Color.white);

        setBorder(BorderFactory.createTitledBorder(properties.getProperty("Allgemein")));

        JLabel label;

        setLayout(layout);

        label = new JLabel(properties.getProperty("Verein"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpTeamname.getComponent(false), constraints);
        add(m_jpTeamname.getComponent(false));

        label = new JLabel(properties.getProperty("Manager"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpManagername.getComponent(false), constraints);
        add(m_jpManagername.getComponent(false));

        label = new JLabel(properties.getProperty("Stadion"));
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
        layout.setConstraints(m_jpStadion.getComponent(false), constraints);
        add(m_jpStadion.getComponent(false));

        label = new JLabel(properties.getProperty("Season"));
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
        layout.setConstraints(m_jpSeason.getComponent(false), constraints);
        add(m_jpSeason.getComponent(false));

        label = new JLabel(properties.getProperty("Spieltag"));
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
        layout.setConstraints(m_jpSpieltag.getComponent(false), constraints);
        add(m_jpSpieltag.getComponent(false));

        label = new JLabel(properties.getProperty("Liga"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpLiga.getComponent(false), constraints);
        add(m_jpLiga.getComponent(false));

        label = new JLabel(properties.getProperty("Platzierung"));
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
        layout.setConstraints(m_jpPlatzierung.getComponent(false), constraints);
        add(m_jpPlatzierung.getComponent(false));

        label = new JLabel(properties.getProperty("Punkte"));
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
        layout.setConstraints(m_jpPunkte.getComponent(false), constraints);
        add(m_jpPunkte.getComponent(false));

        label = new JLabel(properties.getProperty("Torverhaeltnis"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 9;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpTorverhaeltnis.getComponent(false), constraints);
        add(m_jpTorverhaeltnis.getComponent(false));
    }
}
