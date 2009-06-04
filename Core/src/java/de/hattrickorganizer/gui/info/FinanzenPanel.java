// %1645621351:de.hattrickorganizer.gui.info%
package de.hattrickorganizer.gui.info;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.model.HOVerwaltung;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * Zeigt die Finanzen für die aktuelle oder die vorherige Woche an
 */
final class FinanzenPanel extends JPanel implements de.hattrickorganizer.gui.Refreshable {
	
	private static final long serialVersionUID = 5220006612961140628L;
	
    //~ Instance fields ----------------------------------------------------------------------------
	private final ColorLabelEntry m_jpAGehaelter = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_STANDARD,
                                                                 SwingConstants.LEFT);
    private final ColorLabelEntry m_jpAGesamt = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                              ColorLabelEntry.BG_STANDARD,
                                                              SwingConstants.LEFT);
    private final ColorLabelEntry m_jpAJugend = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                              ColorLabelEntry.BG_STANDARD,
                                                              SwingConstants.LEFT);
    private final ColorLabelEntry m_jpASonstiges = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_STANDARD,
                                                                 SwingConstants.LEFT);
    private final ColorLabelEntry m_jpAStadion = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                               ColorLabelEntry.BG_STANDARD,
                                                               SwingConstants.LEFT);
    private final ColorLabelEntry m_jpATrainerstab = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                   ColorLabelEntry.BG_STANDARD,
                                                                   SwingConstants.LEFT);
    private final ColorLabelEntry m_jpAZinsaufwendungen = new ColorLabelEntry("",
                                                                        ColorLabelEntry.FG_STANDARD,
                                                                        ColorLabelEntry.BG_STANDARD,
                                                                        SwingConstants.LEFT);
    private final ColorLabelEntry m_jpEGesamt = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                              ColorLabelEntry.BG_STANDARD,
                                                              SwingConstants.LEFT);
    private final ColorLabelEntry m_jpESonstiges = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_STANDARD,
                                                                 SwingConstants.LEFT);
    private final ColorLabelEntry m_jpESponsoren = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_STANDARD,
                                                                 SwingConstants.LEFT);
    private final ColorLabelEntry m_jpEZinsertraege = new ColorLabelEntry("",
                                                                    ColorLabelEntry.FG_STANDARD,
                                                                    ColorLabelEntry.BG_STANDARD,
                                                                    SwingConstants.LEFT);
    private final ColorLabelEntry m_jpEZuschauer = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_STANDARD,
                                                                 SwingConstants.LEFT);
    private final ColorLabelEntry m_jpGesamt = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                             ColorLabelEntry.BG_STANDARD,
                                                             SwingConstants.LEFT);
    private final ColorLabelEntry m_jpKontostand = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                                 ColorLabelEntry.BG_STANDARD,
                                                                 SwingConstants.LEFT);
    private boolean m_bAktuelleFinanzen = true;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FinanzenPanel object.
     *
     * @param aktuelleFinanzen TODO Missing Constructuor Parameter Documentation
     */
    protected FinanzenPanel(boolean aktuelleFinanzen) {
        m_bAktuelleFinanzen = aktuelleFinanzen;

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
        final de.hattrickorganizer.model.Finanzen finanzen = HOVerwaltung.instance().getModel()
                                                                         .getFinanzen();
        final float faktor = gui.UserParameter.instance().faktorGeld;

        if (m_bAktuelleFinanzen) {
            m_jpKontostand.setSpezialNumber((finanzen.getFinanzen() / faktor)
                                            + (finanzen.getGewinnVerlust() / faktor), true);
            m_jpEZuschauer.setSpezialNumber(finanzen.getEinnahmenZuschauer() / faktor, true);
            m_jpAStadion.setSpezialNumber(-finanzen.getKostenStadion() / faktor, true);
            m_jpESponsoren.setSpezialNumber(finanzen.getEinnahmenSponsoren() / faktor, true);
            m_jpAGehaelter.setSpezialNumber(-finanzen.getKostenSpieler() / faktor, true);
            m_jpEZinsertraege.setSpezialNumber(finanzen.getEinnahmenZinsen() / faktor, true);
            m_jpAZinsaufwendungen.setSpezialNumber(-finanzen.getKostenZinsen() / faktor, true);
            m_jpESonstiges.setSpezialNumber(finanzen.getEinnahmenSonstige() / faktor, true);
            m_jpASonstiges.setSpezialNumber(-finanzen.getKostenSonstige() / faktor, true);
            m_jpATrainerstab.setSpezialNumber(-finanzen.getKostenTrainerstab() / faktor, true);
            m_jpAJugend.setSpezialNumber(-finanzen.getKostenJugend() / faktor, true);
            m_jpEGesamt.setSpezialNumber(finanzen.getEinnahmenGesamt() / faktor, true);
            m_jpAGesamt.setSpezialNumber(-finanzen.getKostenGesamt() / faktor, true);
            m_jpGesamt.setSpezialNumber(finanzen.getGewinnVerlust() / faktor, true);
        } else {
            m_jpKontostand.setSpezialNumber(finanzen.getFinanzen() / faktor, true);
            m_jpEZuschauer.setSpezialNumber(finanzen.getLetzteEinnahmenZuschauer() / faktor, true);
            m_jpAStadion.setSpezialNumber(-finanzen.getLetzteKostenStadion() / faktor, true);
            m_jpESponsoren.setSpezialNumber(finanzen.getLetzteEinnahmenSponsoren() / faktor, true);
            m_jpAGehaelter.setSpezialNumber(-finanzen.getLetzteKostenSpieler() / faktor, true);
            m_jpEZinsertraege.setSpezialNumber(finanzen.getLetzteEinnahmenZinsen() / faktor, true);
            m_jpAZinsaufwendungen.setSpezialNumber(-finanzen.getLetzteKostenZinsen() / faktor, true);
            m_jpESonstiges.setSpezialNumber(finanzen.getLetzteEinnahmenSonstige() / faktor, true);
            m_jpASonstiges.setSpezialNumber(-finanzen.getLetzteKostenSonstige() / faktor, true);
            m_jpATrainerstab.setSpezialNumber(-finanzen.getLetzteKostenTrainerstab() / faktor, true);
            m_jpAJugend.setSpezialNumber(-finanzen.getLetzteKostenJugend() / faktor, true);
            m_jpEGesamt.setSpezialNumber(finanzen.getLetzteEinnahmenGesamt() / faktor, true);
            m_jpAGesamt.setSpezialNumber(-finanzen.getLetzteKostenGesamt() / faktor, true);
            m_jpGesamt.setSpezialNumber(finanzen.getLetzteGewinnVerlust() / faktor, true);
        }
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

        if (m_bAktuelleFinanzen) {
            setBorder(BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("DieseWoche")));
        } else {
            setBorder(BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("Vorwoche")));
        }

        JLabel label;

        setLayout(layout);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Kontostand"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpKontostand.getComponent(false), constraints);
        add(m_jpKontostand.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Einnahmen"),
        		SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(label, constraints);
        add(label);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Ausgaben"),
        		SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(label, constraints);
        add(label);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Zuschauer"));
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
        layout.setConstraints(m_jpEZuschauer.getComponent(false), constraints);
        add(m_jpEZuschauer.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Stadion"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpAStadion.getComponent(false), constraints);
        add(m_jpAStadion.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Sponsoren"));
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
        layout.setConstraints(m_jpESponsoren.getComponent(false), constraints);
        add(m_jpESponsoren.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Spielergehaelter"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpAGehaelter.getComponent(false), constraints);
        add(m_jpAGehaelter.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Zinsertraege"));
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
        layout.setConstraints(m_jpEZinsertraege.getComponent(false), constraints);
        add(m_jpEZinsertraege.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Zinsaufwendungen"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 3;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpAZinsaufwendungen.getComponent(false), constraints);
        add(m_jpAZinsaufwendungen.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Sonstiges"));
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
        layout.setConstraints(m_jpESonstiges.getComponent(false), constraints);
        add(m_jpESonstiges.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Sonstiges"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 3;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpASonstiges.getComponent(false), constraints);
        add(m_jpASonstiges.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Trainerstab"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 2;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 3;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpATrainerstab.getComponent(false), constraints);
        add(m_jpATrainerstab.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Jugend"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 2;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 3;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpAJugend.getComponent(false), constraints);
        add(m_jpAJugend.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gesamteinnahmen"));
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
        layout.setConstraints(m_jpEGesamt.getComponent(false), constraints);
        add(m_jpEGesamt.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gesamtausgaben"));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 2;
        constraints.gridy = 8;
        constraints.gridwidth = 1;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 3;
        constraints.gridy = 8;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpAGesamt.getComponent(false), constraints);
        add(m_jpAGesamt.getComponent(false));

        if (m_bAktuelleFinanzen) {
            label = new JLabel(HOVerwaltung.instance().getLanguageString("ErwarteterGewinnVerlust"));
        } else {
            label = new JLabel(HOVerwaltung.instance().getLanguageString("VorwocheGewinnVerlust"));
        }

        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridwidth = 3;
        layout.setConstraints(label, constraints);
        add(label);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 3;
        constraints.gridy = 9;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpGesamt.getComponent(false), constraints);
        add(m_jpGesamt.getComponent(false));
    }
}
