// %2020218070:de.hattrickorganizer.gui.info%
package de.hattrickorganizer.gui.info;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.model.Finanzen;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Verein;
import de.hattrickorganizer.tools.PlayerHelper;


/**
 * Zeigt die Sonstige Informationen an
 */
final class SonstigesPanel extends JPanel implements de.hattrickorganizer.gui.Refreshable {
    //~ Instance fields ----------------------------------------------------------------------------

	private static final long serialVersionUID = -6838769293704726932L;
	private final ColorLabelEntry m_jpAnzahlSpieler = new ColorLabelEntry("",
                                                                    ColorLabelEntry.FG_STANDARD,
                                                                    ColorLabelEntry.BG_STANDARD,
                                                                    SwingConstants.LEFT);
    private final ColorLabelEntry m_jpDAlter = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                             ColorLabelEntry.BG_STANDARD,
                                                             SwingConstants.LEFT);
    private final ColorLabelEntry m_jpDErfahrung = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_STANDARD,
                                                                 SwingConstants.LEFT);
    private final ColorLabelEntry m_jpDForm = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                            ColorLabelEntry.BG_STANDARD,
                                                            SwingConstants.LEFT);
    private final ColorLabelEntry m_jpAvgEPV = new ColorLabelEntry("",
                                                                       ColorLabelEntry.FG_STANDARD,
                                                                       ColorLabelEntry.BG_STANDARD,
                                                                       SwingConstants.LEFT);
    private final ColorLabelEntry m_jpAvgTSI = new ColorLabelEntry("",
            														ColorLabelEntry.FG_STANDARD,
            														ColorLabelEntry.BG_STANDARD,
            														SwingConstants.LEFT);
    private final ColorLabelEntry m_jpFans = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                           ColorLabelEntry.BG_STANDARD,
                                                           SwingConstants.LEFT);
    private final ColorLabelEntry m_jpFansAnzahl = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_STANDARD,
                                                                 SwingConstants.LEFT);
    private final ColorLabelEntry m_jpSumEPV = new ColorLabelEntry("",
                                                                           ColorLabelEntry.FG_STANDARD,
                                                                           ColorLabelEntry.BG_STANDARD,
                                                                           SwingConstants.LEFT);
    private final ColorLabelEntry m_jpSumTSI = new ColorLabelEntry("",
    																ColorLabelEntry.FG_STANDARD,
    																ColorLabelEntry.BG_STANDARD,
    																SwingConstants.LEFT);
    private final ColorLabelEntry m_jpJugend = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                             ColorLabelEntry.BG_STANDARD,
                                                             SwingConstants.LEFT);
    private final ColorLabelEntry m_jpSiegeInFolge = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_STANDARD,
                                                                   SwingConstants.LEFT);
    private final ColorLabelEntry m_jpSponsoren = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                ColorLabelEntry.BG_STANDARD,
                                                                SwingConstants.LEFT);
    private final ColorLabelEntry m_jpUngeschlagen = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_STANDARD,
                                                                   SwingConstants.LEFT);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SonstigesPanel object.
     */
    protected SonstigesPanel() {
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
        final Verein verein = HOVerwaltung.instance().getModel().getVerein();
        final Finanzen finanzen = HOVerwaltung.instance().getModel().getFinanzen();

        DecimalFormat df = new DecimalFormat("###,###,###,##0");

        m_jpJugend.setText(PlayerHelper.getNameForSkill(verein.getJugend()));
        m_jpFansAnzahl.setText(verein.getFans() + "");
        m_jpFans.setText(Finanzen.getNameForLevelFans(finanzen.getSupporter()));
        m_jpSponsoren.setText(Finanzen.getNameForLevelSponsors(finanzen.getSponsoren()));
        m_jpUngeschlagen.setText(verein.getUngeschlagen() + "");
        m_jpSiegeInFolge.setText(verein.getSiege() + "");
        m_jpAnzahlSpieler.setText(HOVerwaltung.instance().getModel().getAllSpieler().size() + "");
        m_jpAvgTSI.setText(df.format(HOVerwaltung.instance().getAvgTSI()));
        m_jpSumTSI.setText(df.format(HOVerwaltung.instance().getSumTSI()));
        m_jpAvgEPV.setSpezialNumber(Math.round(HOVerwaltung.instance().getAvgEPV()), true);
        m_jpSumEPV.setSpezialNumber(Math.round(HOVerwaltung.instance().getSumEPV()), true);
        m_jpDAlter.setText(HOVerwaltung.instance().getDurchschnittsAlter() + "");
        m_jpDForm.setText(HOVerwaltung.instance().getDurchschnittsForm() + "");
        m_jpDErfahrung.setText(HOVerwaltung.instance().getDurchschnittsErfahrung() + "");
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

        setBorder(BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("Verschiedenes")));

        JLabel label;

        setLayout(layout);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Jugend"));
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
        layout.setConstraints(m_jpJugend.getComponent(false), constraints);
        add(m_jpJugend.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Fans"));
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
        layout.setConstraints(m_jpFansAnzahl.getComponent(false), constraints);
        add(m_jpFansAnzahl.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Fans"));
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
        layout.setConstraints(m_jpFans.getComponent(false), constraints);
        add(m_jpFans.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Sponsoren"));
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
        layout.setConstraints(m_jpSponsoren.getComponent(false), constraints);
        add(m_jpSponsoren.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Ungeschlagen"));
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
        layout.setConstraints(m_jpUngeschlagen.getComponent(false), constraints);
        add(m_jpUngeschlagen.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("SiegeInFolge"));
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
        layout.setConstraints(m_jpSiegeInFolge.getComponent(false), constraints);
        add(m_jpSiegeInFolge.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("AnzahlSpieler"));
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
        layout.setConstraints(m_jpAnzahlSpieler.getComponent(false), constraints);
        add(m_jpAnzahlSpieler.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("AverageTSI"));
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
        layout.setConstraints(m_jpAvgTSI.getComponent(false), constraints);
        add(m_jpAvgTSI.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("TotalTSI"));
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
        layout.setConstraints(m_jpSumTSI.getComponent(false), constraints);
        add(m_jpSumTSI.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("AverageEPV"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 10;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpAvgEPV.getComponent(false), constraints);
        add(m_jpAvgEPV.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("TotalEPV"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 11;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 11;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpSumEPV.getComponent(false), constraints);
        add(m_jpSumEPV.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("DurchschnittForm"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 12;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 12;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpDForm.getComponent(false), constraints);
        add(m_jpDForm.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("DurchschnittErfahrung"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 13;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 13;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpDErfahrung.getComponent(false), constraints);
        add(m_jpDErfahrung.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("DurchschnittAlter"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 14;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 14;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpDAlter.getComponent(false), constraints);
        add(m_jpDAlter.getComponent(false));
    }
}
