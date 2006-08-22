// %3862884693:de.hattrickorganizer.gui.league%
package de.hattrickorganizer.gui.league;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.DoppelLabelEntry;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;


/**
 * Panel mit JTabel für die Arena anzeige und zum Testen
 */
public class LigaTabelle extends de.hattrickorganizer.gui.templates.ImagePanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final Color BG_AUFSTIEGSPLATZ = new Color(220, 255, 220);

    /** TODO Missing Parameter Documentation */
    public static final Color BG_RELEGATIONSPLATZ = new Color(255, 255, 200);

    /** TODO Missing Parameter Documentation */
    public static final Color BG_ABSTIEGSPLATZ = new Color(255, 220, 220);

    /** TODO Missing Parameter Documentation */
    public static final Color BG_TITEL = new Color(230, 230, 230);

    /** TODO Missing Parameter Documentation */
    public static final Color FG_EIGENESTEAM = new Color(50, 50, 150);

    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    private final String[] COLUMNNAMES = {
                                             de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Platz"),
                                             de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Verein"),
                                             de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Serie"),
                                             de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Spiele_kurz"),
                                             de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("SerieAuswaertsSieg"),
                                             de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("SerieAuswaertsUnendschieden"),
                                             de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("SerieAuswaertsNiederlage"),
                                             de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Tore"),
                                             de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Differenz_kurz"),
                                             de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Punkte_kurz"),
                                             "",
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Heim_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("SerieAuswaertsSieg"),
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Heim_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("SerieAuswaertsUnendschieden"),
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Heim_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("SerieAuswaertsNiederlage"),
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Heim_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("Tore"),
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Heim_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("Differenz_kurz"),
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Heim_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("Punkte_kurz"),
                                             "",
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Auswaerts_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("SerieAuswaertsSieg"),
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Auswaerts_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("SerieAuswaertsUnendschieden"),
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Auswaerts_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("SerieAuswaertsNiederlage"),
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Auswaerts_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("Tore"),
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Auswaerts_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("Differenz_kurz"),
                                             
    de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Auswaerts_kurz")
                                             + de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("Punkte_kurz")
                                         };
    private JTable m_jtLigaTabelle = new JTable();
    private Object[][] tabellenwerte;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new LigaTabelle object.
     */
    public LigaTabelle() {
        initComponents();

        //Entrys setzen
        initTabelle();

        //Stadien berechnen
        initLigaTabelle();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gibt den Namen des Markierten Teams zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getSelectedTeam() {
        String team = null;

        if (m_jtLigaTabelle.getSelectedRow() > -1) {
            team = ((ColorLabelEntry) tabellenwerte[m_jtLigaTabelle.getSelectedRow()][1]).getText();
        }

        return team;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param listener TODO Missing Method Parameter Documentation
     */
    public final void addKeyListener(KeyListener listener) {
        m_jtLigaTabelle.addKeyListener(listener);
    }

    //--Listener an Tabelle binden!------------------
    public final void addMouseListener(MouseListener listener) {
        m_jtLigaTabelle.addMouseListener(listener);
    }

    //-------Refresh---------------------------------    
    public final void changeSaison() {
        reinitTabelle();
    }

    /**
     * Gibt die Farbe für die Zeile in der Tabelle zurück
     *
     * @param row TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Color getColor4Row(int row) {
        switch (row) {
            case 1:
                return BG_AUFSTIEGSPLATZ;

            case 5:
            case 6:
                return BG_RELEGATIONSPLATZ;

            case 7:
            case 8:
                return BG_ABSTIEGSPLATZ;

            default:
                return Color.white;
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void setTableColumnWidth() {
        final TableColumnModel columnModel = m_jtLigaTabelle.getColumnModel();

        //Platz
        TableColumn column = columnModel.getColumn(0);
        column.setPreferredWidth(Helper.calcCellWidth(45));

        //Verein
        columnModel.getColumn(1).setPreferredWidth(Helper.calcCellWidth(200));

        //Serie
        columnModel.getColumn(2).setPreferredWidth(Helper.calcCellWidth(110));

        //Spiele
        columnModel.getColumn(3).setPreferredWidth(Helper.calcCellWidth(25));

        //Gewonnen
        columnModel.getColumn(4).setPreferredWidth(Helper.calcCellWidth(25));

        //Unendschieden
        columnModel.getColumn(5).setPreferredWidth(Helper.calcCellWidth(25));

        //Verloren
        columnModel.getColumn(6).setPreferredWidth(Helper.calcCellWidth(25));

        //Tore
        columnModel.getColumn(7).setPreferredWidth(Helper.calcCellWidth(45));

        //Differenz
       columnModel.getColumn(8).setPreferredWidth(Helper.calcCellWidth(30));

        //Punkte
        columnModel.getColumn(9).setPreferredWidth(Helper.calcCellWidth(30));

        //Unterteilung
        column = columnModel.getColumn(10);
        column.setMaxWidth(Helper.calcCellWidth(5));
        column.setMinWidth(Helper.calcCellWidth(5));
        column.setPreferredWidth(Helper.calcCellWidth(5));

        //zuhause
        //Gewonnen
        columnModel.getColumn(11).setPreferredWidth(Helper.calcCellWidth(25));

        //Unendschieden
        columnModel.getColumn(12).setPreferredWidth(Helper.calcCellWidth(25));

        //Verloren
        columnModel.getColumn(13).setPreferredWidth(Helper.calcCellWidth(25));

        //Tore
        columnModel.getColumn(14).setPreferredWidth(Helper.calcCellWidth(45));

        //Differenz
        columnModel.getColumn(15).setPreferredWidth(Helper.calcCellWidth(30));

        //Punkte
        columnModel.getColumn(16).setPreferredWidth(Helper.calcCellWidth(30));

        //Unterteilung
        column = columnModel.getColumn(17);
        column.setMaxWidth(Helper.calcCellWidth(5));
        column.setMinWidth(Helper.calcCellWidth(5));
        column.setPreferredWidth(Helper.calcCellWidth(5));

        //auswärts
        //Gewonnen
        columnModel.getColumn(18).setPreferredWidth(Helper.calcCellWidth(25));

        //Unendschieden
        columnModel.getColumn(19).setPreferredWidth(Helper.calcCellWidth(25));

        //Verloren
        columnModel.getColumn(20).setPreferredWidth(Helper.calcCellWidth(25));

        //Tore
        columnModel.getColumn(21).setPreferredWidth(Helper.calcCellWidth(45));

        //Differenz
        columnModel.getColumn(22).setPreferredWidth(Helper.calcCellWidth(30));

        //Punkte
        columnModel.getColumn(23).setPreferredWidth(Helper.calcCellWidth(30));
    }

    /**
     * Gibt eine String zurück, der die Tore darstellt
     *
     * @param heim TODO Missing Constructuor Parameter Documentation
     * @param gast TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private String createTorString(int heim, int gast) {
        final StringBuffer buffer = new StringBuffer();

        if (heim < 10) {
            buffer.append(" ");
        }

        buffer.append(heim);
        buffer.append(" : ");

        if (gast < 10) {
            buffer.append(" ");
        }

        buffer.append(gast);

        return buffer.toString();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(4, 4, 4, 4);

        setLayout(new BorderLayout());

        m_jtLigaTabelle.setDefaultRenderer(java.lang.Object.class,
                                           new de.hattrickorganizer.gui.model.SpielerTableRenderer());

        final JPanel panel = new ImagePanel(layout);
        layout.setConstraints(panel, constraints);
        panel.add(m_jtLigaTabelle);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel, constraints);
        add(panel);
    }

    //Init aus dem HRF-XML
    private void initLigaTabelle() {
        //Entrys mit Werten füllen
        //Ein Model vorhanden?
        if ((de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getSpielplan() != null)
            && (de.hattrickorganizer.model.HOVerwaltung.instance().getModel().getSpielplan()
                                                       .getSaison() > 0)) {
            //Daten in die Tabelle füllen
            reinitTabelle();
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initTabelle() {
        //Tablewerte setzen
        tabellenwerte = new Object[9][COLUMNNAMES.length];

        //Überschrift
        for (int i = 0; i < COLUMNNAMES.length; i++) {
            tabellenwerte[0][i] = new ColorLabelEntry(COLUMNNAMES[i], ColorLabelEntry.FG_STANDARD,
                                                      BG_TITEL, SwingConstants.CENTER);
        }

        for (int i = 1; i < 9; i++) {
            final Color bg_Color = getColor4Row(i);
            tabellenwerte[i][0] = new DoppelLabelEntry(new ColorLabelEntry("",
                                                                           ColorLabelEntry.FG_STANDARD,
                                                                           bg_Color, SwingConstants.RIGHT),
                                                       new ColorLabelEntry("",
                                                                           ColorLabelEntry.FG_STANDARD,
                                                                           bg_Color, SwingConstants.RIGHT));
            tabellenwerte[i][1] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.LEFT);
            tabellenwerte[i][2] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.LEFT);
            tabellenwerte[i][3] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][4] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][5] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][6] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][7] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.CENTER);
            tabellenwerte[i][8] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][9] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][10] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, BG_TITEL,SwingConstants.RIGHT);
            tabellenwerte[i][11] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][12] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][13] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][14] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.CENTER);
            tabellenwerte[i][15] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][16] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][17] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, BG_TITEL,SwingConstants.RIGHT);
            tabellenwerte[i][18] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][19] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][20] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][21] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.CENTER);
            tabellenwerte[i][22] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
            tabellenwerte[i][23] = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD, bg_Color,SwingConstants.RIGHT);
        }

        //Model setzen
        m_jtLigaTabelle.setModel(new de.hattrickorganizer.gui.model.VAPTableModel(COLUMNNAMES,
                                                                                  tabellenwerte));

        setTableColumnWidth();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void reinitTabelle() {
        try {
            if (LigaTabellePanel.getAktuellerSpielPlan() != null) {
                final Font monospacedFont = new Font("monospaced", Font.PLAIN,
                                                     gui.UserParameter.instance().schriftGroesse
                                                     + 1);

                final Vector tabelleneintraege = LigaTabellePanel.getAktuellerSpielPlan()
                                                                 .getTabelle().getEintraege();
                final int teamid = de.hattrickorganizer.model.HOVerwaltung.instance().getModel()
                                                                          .getBasics().getTeamId();
                int j = -1;

                for (int i = 0; i < tabelleneintraege.size(); i++) {
                    final de.hattrickorganizer.model.lineup.LigaTabellenEintrag eintrag = (de.hattrickorganizer.model.lineup.LigaTabellenEintrag) tabelleneintraege
                                                                                          .get(i);

                    if (eintrag.getPunkte() > -1) {
                        j = i + 1;

                        ((DoppelLabelEntry) tabellenwerte[j][0]).getLinks().setText(eintrag
                                                                                    .getPosition()
                                                                                    + ".");
                        ((DoppelLabelEntry) tabellenwerte[j][0]).getLinks().setFontStyle(Font.BOLD);
                        ((DoppelLabelEntry) tabellenwerte[j][0]).getRechts().setText("("
                                                                                     + eintrag
                                                                                       .getAltePosition()
                                                                                     + ")");
                        ((ColorLabelEntry) tabellenwerte[j][1]).setText(eintrag.getTeamName());
                        ((ColorLabelEntry) tabellenwerte[j][1]).setFontStyle(Font.BOLD);

                        if (eintrag.getTeamId() == teamid) {
                            ((ColorLabelEntry) tabellenwerte[j][1]).setFGColor(FG_EIGENESTEAM);
                        } else {
                            ((ColorLabelEntry) tabellenwerte[j][1]).setFGColor(Color.black);
                        }

                        ((ColorLabelEntry) tabellenwerte[j][2]).setText(eintrag.getSerieAsString());
                        ((ColorLabelEntry) tabellenwerte[j][2]).setFont(monospacedFont);
                        ((ColorLabelEntry) tabellenwerte[j][3]).setText(eintrag.getAnzSpiele() + "");
                        ((ColorLabelEntry) tabellenwerte[j][4]).setText(eintrag.getG_Siege() + "");
                        ((ColorLabelEntry) tabellenwerte[j][5]).setText(eintrag.getG_Un() + "");
                        ((ColorLabelEntry) tabellenwerte[j][6]).setText(eintrag.getG_Nied() + "");
                        ((ColorLabelEntry) tabellenwerte[j][7]).setText(createTorString(eintrag
                                                                                        .getToreFuer(),
                                                                                        eintrag
                                                                                        .getToreGegen()));
                        ((ColorLabelEntry) tabellenwerte[j][8]).setSpezialNumber(eintrag
                                                                                 .getGesamtTorDiff(),
                                                                                 false);
                        ((ColorLabelEntry) tabellenwerte[j][9]).setText(eintrag.getPunkte() + "");
                        ((ColorLabelEntry) tabellenwerte[j][9]).setFontStyle(Font.BOLD);
                        ((ColorLabelEntry) tabellenwerte[j][11]).setText(eintrag.getH_Siege() + "");
                        ((ColorLabelEntry) tabellenwerte[j][12]).setText(eintrag.getH_Un() + "");
                        ((ColorLabelEntry) tabellenwerte[j][13]).setText(eintrag.getH_Nied() + "");
                        ((ColorLabelEntry) tabellenwerte[j][14]).setText(createTorString(eintrag
                                                                                         .getH_ToreFuer(),
                                                                                         eintrag
                                                                                         .getH_ToreGegen()));
                        ((ColorLabelEntry) tabellenwerte[j][15]).setSpezialNumber(eintrag
                                                                                  .getHeimTorDiff(),
                                                                                  false);
                        ((ColorLabelEntry) tabellenwerte[j][16]).setText(eintrag.getH_Punkte() + "");
                        ((ColorLabelEntry) tabellenwerte[j][16]).setFontStyle(Font.BOLD);
                        ((ColorLabelEntry) tabellenwerte[j][18]).setText(eintrag.getA_Siege() + "");
                        ((ColorLabelEntry) tabellenwerte[j][19]).setText(eintrag.getA_Un() + "");
                        ((ColorLabelEntry) tabellenwerte[j][20]).setText(eintrag.getA_Nied() + "");
                        ((ColorLabelEntry) tabellenwerte[j][21]).setText(createTorString(eintrag
                                                                                         .getA_ToreFuer(),
                                                                                         eintrag
                                                                                         .getA_ToreGegen()));
                        ((ColorLabelEntry) tabellenwerte[j][22]).setSpezialNumber(eintrag
                                                                                  .getAwayTorDiff(),
                                                                                  false);
                        ((ColorLabelEntry) tabellenwerte[j][23]).setText(eintrag.getA_Punkte() + "");
                        ((ColorLabelEntry) tabellenwerte[j][23]).setFontStyle(Font.BOLD);
                    }
                }

                //Model setzen
                m_jtLigaTabelle.setModel(new de.hattrickorganizer.gui.model.VAPTableModel(COLUMNNAMES,
                                                                                          tabellenwerte));
            }

            setTableColumnWidth();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"Reinit Tabelle : " + e);
            HOLogger.instance().log(getClass(),e);
        }
    }
}
