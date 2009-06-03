// %3663921258:de.hattrickorganizer.gui.templates%
/*
 * SkillEntry.java
 *
 * Created on 4. September 2004, 14:22
 */
package de.hattrickorganizer.gui.templates;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Skillanzeige eines Spielers (Nachkommastellen in Grau)
 *
 * @author Pirania
 */
public class SkillEntry extends TableEntry {
    //~ Instance fields ----------------------------------------------------------------------------

    private Color m_clBGColor = ColorLabelEntry.BG_STANDARD;
    private Color m_clFGColor = ColorLabelEntry.FG_STANDARD;
    private Color m_clFGColor2 = ColorLabelEntry.FG_GRAU;
    private JComponent m_clComponent;
    private JLabel m_jlLabel1;
    private JLabel m_jlLabel2;
    private String m_sNachkomma = "";
    private String m_sText = "";

    //Für Compareto
    private double m_dZahl = Double.NEGATIVE_INFINITY;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SkillEntry object.
     */
    public SkillEntry() {
        createComponent();
    }

    /**
     * Creates a new SkillEntry object.
     *
     * @param zahl TODO Missing Constructuor Parameter Documentation
     */
    public SkillEntry(double zahl) {
        m_dZahl = zahl;
        createText();
        createComponent();
    }

    /**
     * Creates a new SkillEntry object.
     *
     * @param zahl TODO Missing Constructuor Parameter Documentation
     * @param foreground TODO Missing Constructuor Parameter Documentation
     * @param foreground2 TODO Missing Constructuor Parameter Documentation
     * @param background TODO Missing Constructuor Parameter Documentation
     */
    public SkillEntry(double zahl, Color foreground, Color foreground2, Color background) {
        m_dZahl = zahl;
        m_clFGColor = foreground;
        m_clFGColor2 = foreground2;
        m_clBGColor = background;
        createText();
        createComponent();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param bgcolor TODO Missing Method Parameter Documentation
     */
    public final void setBGColor(Color bgcolor) {
        m_clBGColor = bgcolor;
        updateComponent();
    }

    /**
     * Gibt eine passende Komponente zurück
     *
     * @param isSelected TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JComponent getComponent(boolean isSelected) {
        if (isSelected) {
            m_clComponent.setOpaque(true);
            m_clComponent.setBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
        } else {
            m_clComponent.setOpaque(true);
            m_clComponent.setBackground(m_clBGColor);
        }

        return m_clComponent;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param fgcolor TODO Missing Method Parameter Documentation
     */
    public final void setFGColor(Color fgcolor) {
        m_clFGColor = fgcolor;
        updateComponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param zahl TODO Missing Method Parameter Documentation
     */
    public final void setZahl(double zahl) {
        m_dZahl = zahl;
        updateComponent();
    }

    /**
     * Gibt die Zahl zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getZahl() {
        return m_dZahl;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void clear() {
        m_dZahl = 0d;
        updateComponent();
    }

    /**
     * Vergleich zum Sortieren
     *
     * @param obj TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int compareTo(Object obj) {
        if (obj instanceof SkillEntry) {
            final SkillEntry entry = (SkillEntry) obj;

            //Zahl?
            final double zahl1 = m_dZahl;
            final double zahl2 = entry.getZahl();

            if (zahl1 < zahl2) {
                return -1;
            } else if (zahl1 > zahl2) {
                return 1;
            } else {
                return 0;
            }
        }

        return 0;
    }

    /**
     * Erstellt eine passende Komponente
     */
    public final void createComponent() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(0, 0, 0, 0);

        JPanel panel = new JPanel(layout);

        m_jlLabel1 = new JLabel(m_sText, JLabel.RIGHT);
        m_jlLabel1.setForeground(m_clFGColor);
        constraints.anchor = GridBagConstraints.EAST;
        layout.setConstraints(m_jlLabel1, constraints);
        panel.add(m_jlLabel1);

        m_jlLabel2 = new JLabel(m_sNachkomma, JLabel.LEFT);
        m_jlLabel2.setForeground(m_clFGColor2);
        m_jlLabel2.setFont(m_jlLabel1.getFont().deriveFont(m_jlLabel1.getFont().getSize2D() - 1f));
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        layout.setConstraints(m_jlLabel2, constraints);
        panel.add(m_jlLabel2);

        m_clComponent = panel;
    }

    /**
     * Erzeugt die beiden Texte aus der Zahl
     */
    public final void createText() {
        m_sText = Integer.toString((int) m_dZahl);

        if (gui.UserParameter.instance().anzahlNachkommastellen == 1) {
            m_sNachkomma = de.hattrickorganizer.tools.Helper.DEFAULTDEZIMALFORMAT.format(de.hattrickorganizer.tools.Helper
                                                                                         .round(m_dZahl
                                                                                                - (double) (int) m_dZahl,
                                                                                                gui.UserParameter
                                                                                                .instance().anzahlNachkommastellen));
        } else {
            m_sNachkomma = de.hattrickorganizer.tools.Helper.DEZIMALFORMAT_2STELLEN.format(de.hattrickorganizer.tools.Helper
                                                                                           .round(m_dZahl
                                                                                                  - (double) (int) m_dZahl,
                                                                                                  gui.UserParameter
                                                                                                  .instance().anzahlNachkommastellen));
        }

        int index = m_sNachkomma.indexOf(',');

        if (index < 0) {
            index = m_sNachkomma.indexOf('.');
        }

        if (index >= 0) {
            m_sNachkomma = m_sNachkomma.substring(index);
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void updateComponent() {
        m_jlLabel1.setText(m_sText);
        m_jlLabel2.setText(m_sNachkomma);
        m_jlLabel1.setBackground(m_clBGColor);
        m_jlLabel1.setForeground(m_clFGColor);
        m_jlLabel2.setBackground(m_clBGColor);
        m_jlLabel2.setForeground(m_clFGColor2);
        m_jlLabel2.setFont(m_jlLabel1.getFont().deriveFont(m_jlLabel1.getFont().getSize2D() - 1f));
    }
}
