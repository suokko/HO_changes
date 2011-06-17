// %3066887473:de.hattrickorganizer.gui.league%
package de.hattrickorganizer.gui.league;

//import java.awt.Color;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * Zeigt den Tabellenverlauf der Saison als Grafik an
 */
final class TabellenverlaufStatistikPanel extends JPanel {
	
	private static final long serialVersionUID = -8411572558790555924L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	/** TODO Missing Parameter Documentation */
    private final Color[] COLOR4LINES = {
    			ThemeManager.getColor("panel.leaguehistory.line1.foreground"), // Color.green
    			ThemeManager.getColor("panel.leaguehistory.line2.foreground"), // Color.cyan
    			ThemeManager.getColor("panel.leaguehistory.line3.foreground"), // Color.gray
    			ThemeManager.getColor("panel.leaguehistory.line4.foreground"), // Color.black
    			ThemeManager.getColor("panel.leaguehistory.line5.foreground"), // Color.orange
    			ThemeManager.getColor("panel.leaguehistory.line6.foreground"), // Color.PINK
    			ThemeManager.getColor("panel.leaguehistory.line7.foreground"), // Color.red
    			ThemeManager.getColor("panel.leaguehistory.line8.foreground") // Color.MAGENTA
                                        };
    
    private Color USERSTEAM_FOREGROUND = ThemeManager.getColor("panel.league.usersteam.foreground");
    private Color STANDARD_FOREGROUND = ThemeManager.getColor("table.league.foreground");
    private plugins.ITabellenVerlaufEintrag[] m_clVerlaufeintraege;

    /** TODO Missing Parameter Documentation */
    private final int VEREINSNAMENBREITE = 180;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TabellenverlaufStatistikPanel object.
     */
    protected TabellenverlaufStatistikPanel() {
        setPreferredSize(new Dimension(700, 130));

        initValues();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     */
    @Override
	public void paint(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        //Antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Hintergrund
        g2d.setColor(ThemeManager.getColor("table.league.background"));//Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if ((m_clVerlaufeintraege != null)
            && (m_clVerlaufeintraege.length > 0)
            && (m_clVerlaufeintraege[0] != null)) {
            final int aktuelleTeamId = HOVerwaltung.instance().getModel().getBasics().getTeamId();

            //Wegen Überschrift!
            final int anzahlPlaetze = m_clVerlaufeintraege.length + 1;
            final int anzahlSpieltage = m_clVerlaufeintraege[0].getPlatzierungen().length;
            final int abstandVertikal = getHeight() / (anzahlPlaetze + 1);
            final int abstandHorizontal = (getWidth() - VEREINSNAMENBREITE) / anzahlSpieltage;

            final int fontsize = getHeight() / (anzahlPlaetze + 2);
            final Font fettFont = new Font("sansserif", Font.BOLD, fontsize);
            final Font normalFont = new Font("sansserif", Font.PLAIN, fontsize);

            //Koordinatenkreuz
            g2d.setColor(ThemeManager.getColor("panel.leaguehistory.cross.foreground"));

            //Vertikal
            g2d.drawLine(getWidth() - VEREINSNAMENBREITE, 0, getWidth() - VEREINSNAMENBREITE,
                         getHeight());
            g2d.drawLine(getWidth() - VEREINSNAMENBREITE + 1, 0,
                         getWidth() - VEREINSNAMENBREITE + 1, getHeight());

            //Horizontal
            g2d.drawLine(0, getHeight() / anzahlPlaetze, getWidth(), getHeight() / anzahlPlaetze);
            g2d.drawLine(0, (getHeight() / anzahlPlaetze) + 1, getWidth(),
                         (getHeight() / anzahlPlaetze) + 1);

            //Hilfslinien
            g2d.setColor(ThemeManager.getColor("panel.leaguehistory.grid.foreground"));

            //Horizontal
            for (int i = 1; i < anzahlPlaetze; i++) {
                g2d.drawLine(0, getHeight() / anzahlPlaetze * i, getWidth(),
                             getHeight() / anzahlPlaetze * i);
            }

            //Vertikal
            for (int i = 1; i < anzahlSpieltage; i++) {
                g2d.drawLine(abstandHorizontal * i, 0, abstandHorizontal * i, getHeight());
            }

            //Platzierung und Vereinsnamen
            for (int i = 0; i < m_clVerlaufeintraege.length; i++) {
                //Platzierung
                if (m_clVerlaufeintraege[i].getTeamId() == aktuelleTeamId) {
                    g2d.setColor(USERSTEAM_FOREGROUND);
                } else if (i < COLOR4LINES.length) {
                    g2d.setColor(COLOR4LINES[i]);
                } else {
                    g2d.setColor(STANDARD_FOREGROUND);
                }

                g2d.setFont(fettFont);
                g2d.drawString((i + 1) + ".", (getWidth() + 7) - VEREINSNAMENBREITE,
                               (getHeight() / anzahlPlaetze * (i + 1)) + abstandVertikal);

                //Vereinsnamen
                g2d.setFont(normalFont);

                //Eigenes Team blau machen
                if (m_clVerlaufeintraege[i].getTeamId() == aktuelleTeamId) {
                    g2d.setColor(USERSTEAM_FOREGROUND);
                } else {
                    g2d.setColor(STANDARD_FOREGROUND);
                }

                //Maximal 30 Zeichen als Namen
                g2d.drawString(m_clVerlaufeintraege[i].getTeamName().substring(0,
                                                                               Math.min(30,
                                                                                        m_clVerlaufeintraege[i].getTeamName()
                                                                                                               .length())),
                               (getWidth() + 20) - VEREINSNAMENBREITE,
                               (getHeight() / anzahlPlaetze * (i + 1)) + abstandVertikal);
            }

            //Spieltage
            for (int i = 1; i <= anzahlSpieltage; i++) {
                g2d.setColor(Color.black);
                g2d.drawString(i + ".", ((abstandHorizontal * (i - 0.5f)) - 2),
                               abstandVertikal - 2);
            }

            //Linien zeichnen
            for (int i = 0; i < m_clVerlaufeintraege.length; i++) {
                final int[] platzierungen = m_clVerlaufeintraege[i].getPlatzierungen();

                if (m_clVerlaufeintraege[i].getTeamId() == aktuelleTeamId) {
                    g2d.setColor(USERSTEAM_FOREGROUND);
                } else if (i < COLOR4LINES.length) {
                    g2d.setColor(COLOR4LINES[i]);
                } else {
                    g2d.setColor(STANDARD_FOREGROUND);
                }

                //Erste Linie vom Namen
                g2d.drawLine(getWidth() - VEREINSNAMENBREITE - 5,
                             (int) ((getHeight() / anzahlPlaetze * (i + 0.7f)) + abstandVertikal),
                             (int) (abstandHorizontal * (platzierungen.length - 0.5)),
                             (int) ((getHeight() / anzahlPlaetze * (platzierungen[platzierungen.length
                             - 1] - 0.3f)) + abstandVertikal));

                if (m_clVerlaufeintraege[i].getTeamId() == aktuelleTeamId) {
                    g2d.drawLine(getWidth() - VEREINSNAMENBREITE - 5,
                                 (int) ((getHeight() / anzahlPlaetze * (i + 0.7f))
                                 + abstandVertikal) - 1,
                                 (int) (abstandHorizontal * (platzierungen.length - 0.5)),
                                 (int) ((getHeight() / anzahlPlaetze * (platzierungen[platzierungen.length
                                 - 1] - 0.3f)) + abstandVertikal) - 1);
                }

                for (int j = 0; j < (platzierungen.length - 1); j++) {
                    g2d.drawLine((int) (abstandHorizontal * (j + 0.5)),
                                 (int) ((getHeight() / anzahlPlaetze * (platzierungen[j] - 0.3f))
                                 + abstandVertikal), (int) (abstandHorizontal * (j + 1.5)),
                                 (int) ((getHeight() / anzahlPlaetze * (platzierungen[j + 1] - 0.3f))
                                 + abstandVertikal));

                    if (m_clVerlaufeintraege[i].getTeamId() == aktuelleTeamId) {
                        g2d.drawLine((int) (abstandHorizontal * (j + 0.5)),
                                     (int) ((getHeight() / anzahlPlaetze * (platzierungen[j] - 0.3f))
                                     + abstandVertikal) - 1, (int) (abstandHorizontal * (j + 1.5)),
                                     (int) ((getHeight() / anzahlPlaetze * (platzierungen[j + 1]
                                     - 0.3f)) + abstandVertikal) - 1);
                    }
                }

                //Letzte Linie bis zu Ende
                g2d.drawLine(5,
                             (int) ((getHeight() / anzahlPlaetze * (platzierungen[0] - 0.3f))
                             + abstandVertikal), (int) (abstandHorizontal * 0.5),
                             (int) ((getHeight() / anzahlPlaetze * (platzierungen[0] - 0.3f))
                             + abstandVertikal));

                if (m_clVerlaufeintraege[i].getTeamId() == aktuelleTeamId) {
                    g2d.drawLine(5,
                                 (int) ((getHeight() / anzahlPlaetze * (platzierungen[0] - 0.3f))
                                 + abstandVertikal) - 1, (int) (abstandHorizontal * 0.5),
                                 (int) ((getHeight() / anzahlPlaetze * (platzierungen[0] - 0.3f))
                                 + abstandVertikal) - 1);
                }
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    protected void changeSaison() {
        initValues();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initValues() {
        if (LigaTabellePanel.getAktuellerSpielPlan() != null) {
            m_clVerlaufeintraege = LigaTabellePanel.getAktuellerSpielPlan().getVerlauf()
                                                   .getEintraege();
        }

        repaint();
    }
}
