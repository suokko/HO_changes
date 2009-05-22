// %155607735:de.hattrickorganizer.gui.league%
package de.hattrickorganizer.gui.league;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.Refreshable;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.matchlist.Spielplan;


/**
 * Panel, das die Ligatabelle sowie das letzte und das nächste Spiel enthält
 */
public class LigaTabellePanel extends ImagePanel implements Refreshable, ItemListener,
                                                            ActionListener, MouseListener,
                                                            KeyListener
{
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected static Spielplan AKTUELLER_SPIELPLAN;

    /** TODO Missing Parameter Documentation */
    protected static String MARKIERTER_VEREIN;

    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbDrucken = new JButton(new ImageIcon(de.hattrickorganizer.tools.Helper
                                                            .loadImage("gui/bilder/Drucken.png")));
    private JButton m_jbLoeschen = new JButton(new ImageIcon(de.hattrickorganizer.tools.Helper
                                                             .getImageDurchgestrichen(new java.awt.image.BufferedImage(20,
                                                                                                                       20,
                                                                                                                       java.awt.image.BufferedImage.TYPE_INT_ARGB),
                                                                                      Color.red,
                                                                                      new Color(200,
                                                                                                0, 0))));
    private JComboBox m_jcbSaison;
    private LigaTabelle m_jpLigaTabelle;
    private SpieltagPanel m_jpSpielPlan1;
    private SpieltagPanel m_jpSpielPlan10;
    private SpieltagPanel m_jpSpielPlan11;
    private SpieltagPanel m_jpSpielPlan12;
    private SpieltagPanel m_jpSpielPlan13;
    private SpieltagPanel m_jpSpielPlan14;
    private SpieltagPanel m_jpSpielPlan2;
    private SpieltagPanel m_jpSpielPlan3;
    private SpieltagPanel m_jpSpielPlan4;
    private SpieltagPanel m_jpSpielPlan5;
    private SpieltagPanel m_jpSpielPlan6;
    private SpieltagPanel m_jpSpielPlan7;
    private SpieltagPanel m_jpSpielPlan8;
    private SpieltagPanel m_jpSpielPlan9;
    private TabellenverlaufStatistikPanel m_jpTabellenverlaufStatistik;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new LigaTabellePanel object.
     */
    public LigaTabellePanel() {
        RefreshManager.instance().registerRefreshable(this);
        initComponents();

        fillSaisonCB();
    }

    //~ Methods ------------------------------------------------------------------------------------

    //--------static---------------------------
    public static Spielplan getAktuellerSpielPlan() {
        return AKTUELLER_SPIELPLAN;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getMarkierterVerein() {
        return MARKIERTER_VEREIN;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(m_jbLoeschen)) {
            if (m_jcbSaison.getSelectedItem() != null) {
                final Spielplan spielplan = (Spielplan) m_jcbSaison.getSelectedItem();
                final int value = JOptionPane.showConfirmDialog(this,
                                                                de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Ligatabelle")
                                                                + " "
                                                                + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("loeschen")
                                                                + ":\n" + spielplan.toString(), "",
                                                                JOptionPane.YES_NO_OPTION);

                if (value == JOptionPane.YES_OPTION) {
                    final String[] dbkey = {"Saison", "LigaID"};
                    final String[] dbvalue = {spielplan.getSaison() + "", spielplan.getLigaId()
                                             + ""};

                    de.hattrickorganizer.database.DBZugriff.instance().deleteSpielplanTabelle(dbkey,
                                                                                              dbvalue);
                    de.hattrickorganizer.database.DBZugriff.instance().deletePaarungTabelle(dbkey,
                                                                                            dbvalue);
                    AKTUELLER_SPIELPLAN = null;

                    RefreshManager.instance().doReInit();
                }
            }
        } else if (e.getSource().equals(m_jbDrucken)) {
            final java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            final String titel = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Ligatabelle")
                                 + " - "
                                 + de.hattrickorganizer.model.HOVerwaltung.instance().getModel()
                                                                          .getBasics().getTeamName()
                                 + " - "
                                 + java.text.DateFormat.getDateTimeInstance().format(calendar
                                                                                     .getTime());

            final LigaTabellePrintDialog printDialog = new LigaTabellePrintDialog();
            printDialog.doPrint(titel);
            printDialog.setVisible(false);
            printDialog.dispose();
        }
    }

    //Listener--------------
    public final void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            //Aktuellen Spielplan bestimmen
            if (m_jcbSaison.getSelectedItem() instanceof Spielplan) {
                AKTUELLER_SPIELPLAN = (Spielplan) m_jcbSaison.getSelectedItem();
            } else {
                AKTUELLER_SPIELPLAN = null;
            }

            //Alle Panels informieren
            informSaisonChange();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void keyPressed(KeyEvent e) {
        if (((MARKIERTER_VEREIN == null) && (m_jpLigaTabelle.getSelectedTeam() != null))
            || !MARKIERTER_VEREIN.equals(m_jpLigaTabelle.getSelectedTeam())) {
            MARKIERTER_VEREIN = m_jpLigaTabelle.getSelectedTeam();
            markierungInfo();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void keyReleased(KeyEvent e) {
        if (((MARKIERTER_VEREIN == null) && (m_jpLigaTabelle.getSelectedTeam() != null))
            || !MARKIERTER_VEREIN.equals(m_jpLigaTabelle.getSelectedTeam())) {
            MARKIERTER_VEREIN = m_jpLigaTabelle.getSelectedTeam();
            markierungInfo();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void mouseClicked(MouseEvent e) {
        if (((MARKIERTER_VEREIN == null) && (m_jpLigaTabelle.getSelectedTeam() != null))
            || !MARKIERTER_VEREIN.equals(m_jpLigaTabelle.getSelectedTeam())) {
            MARKIERTER_VEREIN = m_jpLigaTabelle.getSelectedTeam();
            markierungInfo();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void mouseReleased(MouseEvent e) {
        if (((MARKIERTER_VEREIN == null) && (m_jpLigaTabelle.getSelectedTeam() != null))
            || !MARKIERTER_VEREIN.equals(m_jpLigaTabelle.getSelectedTeam())) {
            MARKIERTER_VEREIN = m_jpLigaTabelle.getSelectedTeam();
            markierungInfo();
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void reInit() {
        fillSaisonCB();
    }

    /**
     * TODO Missing Method Documentation
     */
    public void refresh() {
    }

    /**
     * TODO Missing Method Documentation
     */
    private void fillSaisonCB() {
        //Die Spielpläne als Objekte mit den Paarungen holen
        final Spielplan[] spielplaene = de.hattrickorganizer.database.DBZugriff.instance()
                                                                               .getAllSpielplaene(true);

        m_jcbSaison.removeItemListener(this);

        final Spielplan markierterPlan = (Spielplan) m_jcbSaison.getSelectedItem();

        //Alle alten Saisons entfernen
        m_jcbSaison.removeAllItems();

        //Neue füllen
        for (int i = 0; (spielplaene != null) && (i < spielplaene.length); i++) {
            m_jcbSaison.addItem(spielplaene[i]);
        }

        //Alte markierung wieder herstellen
        m_jcbSaison.setSelectedItem(markierterPlan);

        if ((m_jcbSaison.getSelectedIndex() < 0) && (m_jcbSaison.getItemCount() > 0)) {
            m_jcbSaison.setSelectedIndex(0);
        }

        m_jcbSaison.addItemListener(this);

        //Aktuellen Spielplan bestimmen
        if (m_jcbSaison.getSelectedItem() instanceof Spielplan) {
            AKTUELLER_SPIELPLAN = (Spielplan) m_jcbSaison.getSelectedItem();
        } else {
            AKTUELLER_SPIELPLAN = null;
        }

        //Alle Panels informieren
        informSaisonChange();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void informSaisonChange() {
        m_jpLigaTabelle.changeSaison();
        m_jpTabellenverlaufStatistik.changeSaison();

        m_jpSpielPlan1.changeSaison();
        m_jpSpielPlan2.changeSaison();
        m_jpSpielPlan3.changeSaison();
        m_jpSpielPlan4.changeSaison();
        m_jpSpielPlan5.changeSaison();
        m_jpSpielPlan6.changeSaison();
        m_jpSpielPlan7.changeSaison();
        m_jpSpielPlan8.changeSaison();
        m_jpSpielPlan9.changeSaison();
        m_jpSpielPlan10.changeSaison();
        m_jpSpielPlan11.changeSaison();
        m_jpSpielPlan12.changeSaison();
        m_jpSpielPlan13.changeSaison();
        m_jpSpielPlan14.changeSaison();
    }

    //----------init-----------------------------------------------
    private void initComponents() {
        setLayout(new BorderLayout());

        //ComboBox für Saisonauswahl
        final JPanel panel = new ImagePanel(new BorderLayout());

        final JPanel cbpanel = new ImagePanel(null);
        m_jcbSaison = new JComboBox();
        m_jcbSaison.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_Ligatabelle_Saisonauswahl"));
        m_jcbSaison.addItemListener(this);
        m_jcbSaison.setSize(200, 25);
        m_jcbSaison.setLocation(10, 5);
        cbpanel.add(m_jcbSaison);

        m_jbLoeschen.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_Ligatabelle_SaisonLoeschen"));
        m_jbLoeschen.addActionListener(this);
        m_jbLoeschen.setSize(25, 25);
        m_jbLoeschen.setLocation(220, 5);
        m_jbLoeschen.setBackground(Color.WHITE);
        cbpanel.add(m_jbLoeschen);

        m_jbDrucken.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_Ligatabelle_SaisonDrucken"));
        m_jbDrucken.addActionListener(this);
        m_jbDrucken.setSize(25, 25);
        m_jbDrucken.setLocation(255, 5);
        cbpanel.add(m_jbDrucken);

        cbpanel.setPreferredSize(new Dimension(240, 35));
        panel.add(cbpanel, BorderLayout.NORTH);

        final JPanel panel2 = new ImagePanel(new BorderLayout());
        Component component = initLigaTabelle();
        panel2.add(component, BorderLayout.NORTH);

        final JPanel panel3 = new ImagePanel(new BorderLayout());

        component = initTabellenverlaufStatistik();
        panel3.add(component, BorderLayout.NORTH);

        component = initSpielPlan();
        panel3.add(component, BorderLayout.CENTER);

        panel2.add(panel3, BorderLayout.CENTER);

        panel.add(panel2, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initLigaTabelle() {
        m_jpLigaTabelle = new LigaTabelle();
        m_jpLigaTabelle.addMouseListener(this);
        m_jpLigaTabelle.addKeyListener(this);

        final JScrollPane scrollpane = new JScrollPane(m_jpLigaTabelle);
        scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        scrollpane.setPreferredSize(new Dimension((int) m_jpLigaTabelle.getPreferredSize().getWidth(),
                                                  (int) m_jpLigaTabelle.getPreferredSize()
                                                                       .getHeight() + 22));

        return scrollpane;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initSpielPlan() {
        JLabel label = null;

        m_jpSpielPlan1 = new SpieltagPanel(1);
        m_jpSpielPlan2 = new SpieltagPanel(2);
        m_jpSpielPlan3 = new SpieltagPanel(3);
        m_jpSpielPlan4 = new SpieltagPanel(4);
        m_jpSpielPlan5 = new SpieltagPanel(5);
        m_jpSpielPlan6 = new SpieltagPanel(6);
        m_jpSpielPlan7 = new SpieltagPanel(7);
        m_jpSpielPlan8 = new SpieltagPanel(8);
        m_jpSpielPlan9 = new SpieltagPanel(9);
        m_jpSpielPlan10 = new SpieltagPanel(10);
        m_jpSpielPlan11 = new SpieltagPanel(11);
        m_jpSpielPlan12 = new SpieltagPanel(12);
        m_jpSpielPlan13 = new SpieltagPanel(13);
        m_jpSpielPlan14 = new SpieltagPanel(14);

        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridy = 0;
        constraints.insets = new Insets(4, 4, 4, 4);

        final JPanel panel = new ImagePanel(layout);

        label = new JLabel();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        layout.setConstraints(m_jpSpielPlan1, constraints);
        panel.add(m_jpSpielPlan1);

        constraints.gridx = 1;
        constraints.gridy = 1;
        layout.setConstraints(m_jpSpielPlan2, constraints);
        panel.add(m_jpSpielPlan2);

        constraints.gridx = 1;
        constraints.gridy = 2;
        layout.setConstraints(m_jpSpielPlan3, constraints);
        panel.add(m_jpSpielPlan3);

        constraints.gridx = 1;
        constraints.gridy = 3;
        layout.setConstraints(m_jpSpielPlan4, constraints);
        panel.add(m_jpSpielPlan4);

        constraints.gridx = 1;
        constraints.gridy = 4;
        layout.setConstraints(m_jpSpielPlan5, constraints);
        panel.add(m_jpSpielPlan5);

        constraints.gridx = 1;
        constraints.gridy = 5;
        layout.setConstraints(m_jpSpielPlan6, constraints);
        panel.add(m_jpSpielPlan6);

        constraints.gridx = 1;
        constraints.gridy = 6;
        layout.setConstraints(m_jpSpielPlan7, constraints);
        panel.add(m_jpSpielPlan7);

        label = new JLabel();
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(label, constraints);
        panel.add(label);

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        layout.setConstraints(m_jpSpielPlan8, constraints);
        panel.add(m_jpSpielPlan8);

        constraints.gridx = 3;
        constraints.gridy = 1;
        layout.setConstraints(m_jpSpielPlan9, constraints);
        panel.add(m_jpSpielPlan9);

        constraints.gridx = 3;
        constraints.gridy = 2;
        layout.setConstraints(m_jpSpielPlan10, constraints);
        panel.add(m_jpSpielPlan10);

        constraints.gridx = 3;
        constraints.gridy = 3;
        layout.setConstraints(m_jpSpielPlan11, constraints);
        panel.add(m_jpSpielPlan11);

        constraints.gridx = 3;
        constraints.gridy = 4;
        layout.setConstraints(m_jpSpielPlan12, constraints);
        panel.add(m_jpSpielPlan12);

        constraints.gridx = 3;
        constraints.gridy = 5;
        layout.setConstraints(m_jpSpielPlan13, constraints);
        panel.add(m_jpSpielPlan13);

        constraints.gridx = 3;
        constraints.gridy = 6;
        layout.setConstraints(m_jpSpielPlan14, constraints);
        panel.add(m_jpSpielPlan14);

        label = new JLabel();
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.gridheight = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(label, constraints);
        panel.add(label);

        final JScrollPane scrollpane = new JScrollPane(panel);
        scrollpane.getVerticalScrollBar().setBlockIncrement(100);
        scrollpane.getVerticalScrollBar().setUnitIncrement(20);
        scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        return scrollpane;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initTabellenverlaufStatistik() {
        m_jpTabellenverlaufStatistik = new TabellenverlaufStatistikPanel();

        final JPanel panel = new ImagePanel();
        panel.add(m_jpTabellenverlaufStatistik);

        final JScrollPane scrollpane = new JScrollPane(panel);
        scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        scrollpane.setPreferredSize(new Dimension((int) m_jpLigaTabelle.getPreferredSize().getWidth(),
                                                  (int) m_jpLigaTabelle.getPreferredSize()
                                                                       .getHeight()));

        return scrollpane;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void markierungInfo() {
        m_jpSpielPlan1.changeSaison();
        m_jpSpielPlan2.changeSaison();
        m_jpSpielPlan3.changeSaison();
        m_jpSpielPlan4.changeSaison();
        m_jpSpielPlan5.changeSaison();
        m_jpSpielPlan6.changeSaison();
        m_jpSpielPlan7.changeSaison();
        m_jpSpielPlan8.changeSaison();
        m_jpSpielPlan9.changeSaison();
        m_jpSpielPlan10.changeSaison();
        m_jpSpielPlan11.changeSaison();
        m_jpSpielPlan12.changeSaison();
        m_jpSpielPlan13.changeSaison();
        m_jpSpielPlan14.changeSaison();
    }
}
