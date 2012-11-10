// %1645621351:de.hattrickorganizer.gui.info%
package ho.module.misc;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.misc.Finanzen;

import java.awt.Component;
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
final class FinanzenPanel extends JPanel {

	private static final long serialVersionUID = 5220006612961140628L;

    //~ Instance fields ----------------------------------------------------------------------------
	private final ColorLabelEntry m_jpAGehaelter 		= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpAGesamt 			= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpAJugend 			= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpASonstiges 		= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpAStadion 			= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpATrainerstab 		= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpAZinsaufwendungen = new ColorLabelEntry("");
    private final ColorLabelEntry m_jpEGesamt 			= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpESonstiges 		= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpESponsoren 		= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpEZuschauer 		= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpGesamt 			= new ColorLabelEntry("");
    private final ColorLabelEntry m_jpKontostand 		= new ColorLabelEntry("");
    private boolean m_bAktuelleFinanzen = true;

    final GridBagLayout layout = new GridBagLayout();
    final GridBagConstraints constraints = new GridBagConstraints();
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FinanzenPanel object.
     *
     */
    protected FinanzenPanel(boolean aktuelleFinanzen) {
        m_bAktuelleFinanzen = aktuelleFinanzen;

        initComponents();
    }

    void setLabels() {
        final Finanzen finanzen = HOVerwaltung.instance().getModel().getFinanzen();
        final float faktor = ho.core.model.UserParameter.instance().faktorGeld;

        if (m_bAktuelleFinanzen) {
            m_jpKontostand.setSpezialNumber((finanzen.getFinanzen() / faktor)
                                            + (finanzen.getGewinnVerlust() / faktor), true);
            m_jpEZuschauer.setSpezialNumber(finanzen.getEinnahmenZuschauer() / faktor, true);
            m_jpAStadion.setSpezialNumber(-finanzen.getKostenStadion() / faktor, true);
            m_jpESponsoren.setSpezialNumber(finanzen.getEinnahmenSponsoren() / faktor, true);
            m_jpAGehaelter.setSpezialNumber(-finanzen.getKostenSpieler() / faktor, true);
            m_jpESonstiges.setSpezialNumber(finanzen.getEinnahmenSonstige() / faktor, true);
            m_jpASonstiges.setSpezialNumber(-finanzen.getKostenSonstige() / faktor, true);
            m_jpATrainerstab.setSpezialNumber(-finanzen.getKostenTrainerstab() / faktor, true);
            m_jpAJugend.setSpezialNumber(-finanzen.getKostenJugend() / faktor, true);
            m_jpAZinsaufwendungen.setSpezialNumber(-finanzen.getKostenZinsen() / faktor, true);
            m_jpEGesamt.setSpezialNumber(finanzen.getEinnahmenGesamt() / faktor, true);
            m_jpAGesamt.setSpezialNumber(-finanzen.getKostenGesamt() / faktor, true);
            m_jpGesamt.setSpezialNumber(finanzen.getGewinnVerlust() / faktor, true);
        } else {
            m_jpKontostand.setSpezialNumber(finanzen.getFinanzen() / faktor, true);
            m_jpEZuschauer.setSpezialNumber(finanzen.getLetzteEinnahmenZuschauer() / faktor, true);
            m_jpAStadion.setSpezialNumber(-finanzen.getLetzteKostenStadion() / faktor, true);
            m_jpESponsoren.setSpezialNumber(finanzen.getLetzteEinnahmenSponsoren() / faktor, true);
            m_jpAGehaelter.setSpezialNumber(-finanzen.getLetzteKostenSpieler() / faktor, true);
            m_jpESonstiges.setSpezialNumber(finanzen.getLetzteEinnahmenSonstige() / faktor, true);
            m_jpASonstiges.setSpezialNumber(-finanzen.getLetzteKostenSonstige() / faktor, true);
            m_jpATrainerstab.setSpezialNumber(-finanzen.getLetzteKostenTrainerstab() / faktor, true);
            m_jpAJugend.setSpezialNumber(-finanzen.getLetzteKostenJugend() / faktor, true);
            m_jpAZinsaufwendungen.setSpezialNumber(-finanzen.getLetzteKostenZinsen() / faktor, true);
            m_jpEGesamt.setSpezialNumber(finanzen.getLetzteEinnahmenGesamt() / faktor, true);
            m_jpAGesamt.setSpezialNumber(-finanzen.getLetzteKostenGesamt() / faktor, true);
            m_jpGesamt.setSpezialNumber(finanzen.getLetzteGewinnVerlust() / faktor, true);
        }
    }

    private void initComponents() {

        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        setBackground(ThemeManager.getColor(HOColorName.PANEL_BG));

        if (m_bAktuelleFinanzen) {
            setBorder(BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("DieseWoche")));
        } else {
            setBorder(BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("Vorwoche")));
        }

        JLabel label;

        setLayout(layout);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Kontostand"));
        add(label,m_jpKontostand.getComponent(false),0,0);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Einnahmen"),SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(label, constraints);
        add(label);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Ausgaben"),SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(label, constraints);
        add(label);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Zuschauer"));
        add(label,m_jpEZuschauer.getComponent(false),0,2);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Stadion"));
        add(label,m_jpAStadion.getComponent(false),2,2);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Sponsoren"));
        add(label,m_jpESponsoren.getComponent(false),0,3);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Spielergehaelter"));
        add(label,m_jpAGehaelter.getComponent(false),2,3);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Sonstiges"));
        add(label,m_jpESonstiges.getComponent(false),0,4);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Sonstiges"));
        add(label,m_jpASonstiges.getComponent(false),2,4);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Trainerstab"));
        add(label,m_jpATrainerstab.getComponent(false),2,5);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Jugend"));
        add(label,m_jpAJugend.getComponent(false),2,6);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Zinsaufwendungen"));
        add(label,m_jpAZinsaufwendungen.getComponent(false),2,7);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gesamteinnahmen"));
        add(label,m_jpEGesamt.getComponent(false),0,8);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Gesamtausgaben"));
        add(label,m_jpAGesamt.getComponent(false),2,8);

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

    private void add(JLabel label,Component comp, int x, int y){
    	constraints.anchor = GridBagConstraints.WEST;
    	constraints.gridx = x;
    	constraints.gridy = y;
    	constraints.gridwidth = 1;
    	layout.setConstraints(label, constraints);
    	add(label);
    	constraints.anchor = GridBagConstraints.EAST;
    	constraints.gridx = x+1;
    	constraints.gridy = y;
    	constraints.gridwidth = 1;
    	layout.setConstraints(comp, constraints);
    	add(comp);
    }
}
