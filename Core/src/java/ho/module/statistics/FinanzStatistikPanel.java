// %3604286658:de.hattrickorganizer.gui.statistic%
package ho.module.statistics;

import ho.core.db.DBManager;
import ho.core.gui.HOMainFrame;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.model.StatistikModel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;
import ho.core.util.Helper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



/**
 * Das StatistikPanel
 */
public class FinanzStatistikPanel extends ImagePanel
    implements ActionListener, FocusListener, ho.core.gui.Refreshable
{
	private static final long serialVersionUID = 5245162268414878290L;

    //~ Static fields/initializers -----------------------------------------------------------------

	private Color cashColor 			= ThemeManager.getColor(HOColorName.STAT_CASH);
    private Color winLostColor 			= ThemeManager.getColor(HOColorName.STAT_WINLOST);
    private Color incomeSumColor 		= ThemeManager.getColor(HOColorName.STAT_INCOMESUM);
    private Color costSumColor 			= ThemeManager.getColor(HOColorName.STAT_COSTSUM);
    private Color incomeSpectatorsColor = ThemeManager.getColor(HOColorName.STAT_INCOMESPECTATORS);
    private Color incomeSponsorsColor 	= ThemeManager.getColor(HOColorName.STAT_INCOMESPONSORS);
//    private Color incomeFinancialColor 	= ThemeManager.getColor(HOColorName.STAT_INCOMEFINANCIAL);
    private Color incomeTemporaryColor 	= ThemeManager.getColor(HOColorName.STAT_INCOMETEMPORARY);
    private Color costArena 			= ThemeManager.getColor(HOColorName.STAT_COSTARENA);
    private Color costsPlayersColor 	= ThemeManager.getColor(HOColorName.STAT_COSTSPLAYERS);
//    private Color costFinancialColor 	= ThemeManager.getColor(HOColorName.STAT_COSTFINANCIAL);
    private Color costTemporaryColor 	= ThemeManager.getColor(HOColorName.STAT_COSTTEMPORARY);
    private Color costStaffColor 		= ThemeManager.getColor(HOColorName.STAT_COSTSTAFF);
    private Color costsYouthColor 		= ThemeManager.getColor(HOColorName.STAT_COSTSYOUTH);
    private Color fansColor 			= ThemeManager.getColor(HOColorName.STAT_FANS);
    private Color marketValueColor 		= ThemeManager.getColor(HOColorName.STAT_MARKETVALUE);

    //~ Instance fields ----------------------------------------------------------------------------

    private ImageCheckbox m_jchFans = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Fans"),
    		fansColor, ho.core.model.UserParameter.instance().statistikFananzahl);
    private ImageCheckbox m_jchGesamtausgaben = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Gesamtausgaben"),
    		costSumColor,ho.core.model.UserParameter.instance().statistikGesamtAusgaben);
    private ImageCheckbox m_jchGesamteinnahmen = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Gesamteinnahmen"),
    		incomeSumColor,ho.core.model.UserParameter.instance().statistikGesamtEinnahmen);
    private ImageCheckbox m_jchGewinnVerlust = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("GewinnVerlust"),
    		winLostColor,ho.core.model.UserParameter.instance().statistikGewinnVerlust);
    private ImageCheckbox m_jchJugend = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Jugend"),
    		costsYouthColor,ho.core.model.UserParameter.instance().statistikJugend);
    private ImageCheckbox m_jchKontostand = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Kontostand"),
    		cashColor,ho.core.model.UserParameter.instance().statistikKontostand);
    private ImageCheckbox m_jchMarktwert = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("TotalTSI"),
    		marketValueColor,ho.core.model.UserParameter.instance().statistikMarktwert);
    private ImageCheckbox m_jchSonstigeAusgaben = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Sonstiges"),
    		costTemporaryColor,ho.core.model.UserParameter.instance().statistikSonstigeAusgaben);
    private ImageCheckbox m_jchSonstigeEinnahmen = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Sonstiges"),
    		incomeTemporaryColor,ho.core.model.UserParameter.instance().statistikSonstigeEinnahmen);
    private ImageCheckbox m_jchSpielergehaelter = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Spielergehaelter"),
    		costsPlayersColor,ho.core.model.UserParameter.instance().statistikSpielergehaelter);
    private ImageCheckbox m_jchSponsoren = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Sponsoren"),
    		incomeSponsorsColor,ho.core.model.UserParameter.instance().statistikSponsoren);
    private ImageCheckbox m_jchStadion = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Stadion"),
    		costArena,ho.core.model.UserParameter.instance().statistikStadion);
    private ImageCheckbox m_jchTrainerstab = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Trainerstab"),
    		costStaffColor,ho.core.model.UserParameter.instance().statistikTrainerstab);
//    private ImageCheckbox m_jchZinsaufwendungen = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Zinsaufwendungen"),
//    costFinancialColor,gui.UserParameter.instance().statistikZinsaufwendungen);
//    private ImageCheckbox m_jchZinsertraege = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Zinsertraege"),
//    incomeFinancialColor,gui.UserParameter.instance().statistikZinsertraege);
    private ImageCheckbox m_jchZuschauer = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Zuschauer"),
    		incomeSpectatorsColor,ho.core.model.UserParameter.instance().statistikZuschauer);
    private JButton m_jbDrucken = new JButton(ThemeManager.getIcon(HOIconName.PRINTER));
    private JButton m_jbUbernehmen = new JButton(HOVerwaltung.instance().getLanguageString("ls.button.apply"));
    private JCheckBox m_jchBeschriftung = new JCheckBox(HOVerwaltung.instance().getLanguageString("Beschriftung"),
    		ho.core.model.UserParameter.instance().statistikFinanzenBeschriftung);
    private JCheckBox m_jchHilflinien = new JCheckBox(HOVerwaltung.instance().getLanguageString("Hilflinien"),
    		ho.core.model.UserParameter.instance().statistikFinanzenHilfslinien);
    private JTextField m_jtfAnzahlHRF = new JTextField(ho.core.model.UserParameter.instance().statistikFinanzenAnzahlHRF + "");
    private StatistikPanel m_clStatistikPanel;
    private boolean m_bInitialisiert;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FinanzStatistikPanel object.
     */
    public FinanzStatistikPanel() {
        ho.core.gui.RefreshManager.instance().registerRefreshable(this);

        initComponents();

        //initStatistik();
    }

    public final void setInitialisiert(boolean init) {
        m_bInitialisiert = init;
    }

    public final boolean isInitialisiert() {
        return m_bInitialisiert;
    }

    //--------Listener-------------------------------
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbUbernehmen)) {
            initStatistik();
        } else if (actionEvent.getSource().equals(m_jbDrucken)) {
            m_clStatistikPanel.doPrint(HOVerwaltung.instance().getLanguageString("Finanzen"));
        } else if (actionEvent.getSource().equals(m_jchHilflinien)) {
            m_clStatistikPanel.setHilfslinien(m_jchHilflinien.isSelected());
            ho.core.model.UserParameter.instance().statistikFinanzenHilfslinien = m_jchHilflinien.isSelected();
        } else if (actionEvent.getSource().equals(m_jchBeschriftung)) {
            m_clStatistikPanel.setBeschriftung(m_jchBeschriftung.isSelected());
            ho.core.model.UserParameter.instance().statistikFinanzenBeschriftung = m_jchBeschriftung
                                                                         .isSelected();
        } else if (actionEvent.getSource().equals(m_jchKontostand.getCheckbox())) {
            m_clStatistikPanel.setShow("Kontostand", m_jchKontostand.isSelected());
            ho.core.model.UserParameter.instance().statistikKontostand = m_jchKontostand.isSelected();
        } else if (actionEvent.getSource().equals(m_jchGewinnVerlust.getCheckbox())) {
            m_clStatistikPanel.setShow("GewinnVerlust", m_jchGewinnVerlust.isSelected());
            ho.core.model.UserParameter.instance().statistikGewinnVerlust = m_jchGewinnVerlust.isSelected();
        } else if (actionEvent.getSource().equals(m_jchGesamteinnahmen.getCheckbox())) {
            m_clStatistikPanel.setShow("Gesamteinnahmen", m_jchGesamteinnahmen.isSelected());
            ho.core.model.UserParameter.instance().statistikGesamtEinnahmen = m_jchGesamteinnahmen.isSelected();
        } else if (actionEvent.getSource().equals(m_jchGesamtausgaben.getCheckbox())) {
            m_clStatistikPanel.setShow("Gesamtausgaben", m_jchGesamtausgaben.isSelected());
            ho.core.model.UserParameter.instance().statistikGesamtAusgaben = m_jchGesamtausgaben.isSelected();
        } else if (actionEvent.getSource().equals(m_jchZuschauer.getCheckbox())) {
            m_clStatistikPanel.setShow("Zuschauer", m_jchZuschauer.isSelected());
            ho.core.model.UserParameter.instance().statistikZuschauer = m_jchZuschauer.isSelected();
        } else if (actionEvent.getSource().equals(m_jchSponsoren.getCheckbox())) {
            m_clStatistikPanel.setShow("Sponsoren", m_jchSponsoren.isSelected());
            ho.core.model.UserParameter.instance().statistikSponsoren = m_jchSponsoren.isSelected();
//        } else if (actionEvent.getSource().equals(m_jchZinsertraege.getCheckbox())) {
//            m_clStatistikPanel.setShow("Zinsertraege", m_jchZinsertraege.isSelected());
//            gui.UserParameter.instance().statistikZinsertraege = m_jchZinsertraege.isSelected();
        } else if (actionEvent.getSource().equals(m_jchSonstigeEinnahmen.getCheckbox())) {
            m_clStatistikPanel.setShow("SonstigeEinnahmen", m_jchSonstigeEinnahmen.isSelected());
            ho.core.model.UserParameter.instance().statistikSonstigeEinnahmen = m_jchSonstigeEinnahmen
                                                                      .isSelected();
        } else if (actionEvent.getSource().equals(m_jchStadion.getCheckbox())) {
            m_clStatistikPanel.setShow("Stadion", m_jchStadion.isSelected());
            ho.core.model.UserParameter.instance().statistikStadion = m_jchStadion.isSelected();
        } else if (actionEvent.getSource().equals(m_jchSpielergehaelter.getCheckbox())) {
            m_clStatistikPanel.setShow("Spielergehaelter", m_jchSpielergehaelter.isSelected());
            ho.core.model.UserParameter.instance().statistikSpielergehaelter = m_jchSpielergehaelter
                                                                     .isSelected();
//        } else if (actionEvent.getSource().equals(m_jchZinsaufwendungen.getCheckbox())) {
//            m_clStatistikPanel.setShow("Zinsaufwendungen", m_jchZinsaufwendungen.isSelected());
//            gui.UserParameter.instance().statistikZinsaufwendungen = m_jchZinsaufwendungen
//                                                                     .isSelected();
        } else if (actionEvent.getSource().equals(m_jchSonstigeAusgaben.getCheckbox())) {
            m_clStatistikPanel.setShow("SonstigeAusgaben", m_jchSonstigeAusgaben.isSelected());
            ho.core.model.UserParameter.instance().statistikSonstigeAusgaben = m_jchSonstigeAusgaben
                                                                     .isSelected();
        } else if (actionEvent.getSource().equals(m_jchTrainerstab.getCheckbox())) {
            m_clStatistikPanel.setShow("Trainerstab", m_jchTrainerstab.isSelected());
            ho.core.model.UserParameter.instance().statistikTrainerstab = m_jchTrainerstab.isSelected();
        } else if (actionEvent.getSource().equals(m_jchJugend.getCheckbox())) {
            m_clStatistikPanel.setShow("Jugend", m_jchJugend.isSelected());
            ho.core.model.UserParameter.instance().statistikJugend = m_jchJugend.isSelected();
        } else if (actionEvent.getSource().equals(m_jchMarktwert.getCheckbox())) {
            m_clStatistikPanel.setShow("Marktwert", m_jchMarktwert.isSelected());
            ho.core.model.UserParameter.instance().statistikMarktwert = m_jchMarktwert.isSelected();
        } else if (actionEvent.getSource().equals(m_jchFans.getCheckbox())) {
            m_clStatistikPanel.setShow("Fans", m_jchFans.isSelected());
            ho.core.model.UserParameter.instance().statistikFananzahl = m_jchFans.isSelected();
        }
    }


    public final void doInitialisieren() {
        initStatistik();
        m_bInitialisiert = true;
    }

    public void focusGained(java.awt.event.FocusEvent focusEvent) {
    }


    public final void focusLost(java.awt.event.FocusEvent focusEvent) {
        Helper.parseInt(HOMainFrame.instance(),((JTextField) focusEvent.getSource()), false);
    }

    //-------Refresh---------------------------------
    public final void reInit() {
        m_bInitialisiert = false;

        //initStatistik();
    }


    public void refresh() {
    }


    private void initComponents() {
        JLabel label;

        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(2, 0, 2, 0);

        setLayout(layout);

        final JPanel panel2 = new ImagePanel();
        final GridBagLayout layout2 = new GridBagLayout();
        final GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.fill = GridBagConstraints.HORIZONTAL;
        constraints2.weightx = 0.0;
        constraints2.weighty = 0.0;
        constraints2.insets = new Insets(2, 2, 2, 2);

        panel2.setLayout(layout2);

        constraints2.gridx = 0;
        constraints2.gridy = 0;
        constraints2.gridwidth = 2;
        constraints2.fill = GridBagConstraints.NONE;
        constraints2.anchor = GridBagConstraints.WEST;
        m_jbDrucken.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Statistik_drucken"));
        m_jbDrucken.setPreferredSize(new Dimension(25, 25));
        m_jbDrucken.addActionListener(this);
        layout2.setConstraints(m_jbDrucken, constraints2);
        panel2.add(m_jbDrucken);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Wochen"));
        constraints2.fill = GridBagConstraints.HORIZONTAL;
        constraints2.anchor = GridBagConstraints.WEST;
        constraints2.gridx = 0;
        constraints2.gridy = 1;
        constraints2.gridwidth = 1;
        layout2.setConstraints(label, constraints2);
        panel2.add(label);
        m_jtfAnzahlHRF.setHorizontalAlignment(SwingConstants.RIGHT);
        m_jtfAnzahlHRF.addFocusListener(this);
        constraints2.gridx = 1;
        constraints2.gridy = 1;
        layout2.setConstraints(m_jtfAnzahlHRF, constraints2);
        panel2.add(m_jtfAnzahlHRF);

        constraints2.gridx = 0;
        constraints2.gridy = 4;
        constraints2.gridwidth = 2;
        m_jbUbernehmen.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Statistik_HRFAnzahluebernehmen"));
        layout2.setConstraints(m_jbUbernehmen, constraints2);
        m_jbUbernehmen.addActionListener(this);
        panel2.add(m_jbUbernehmen);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 5;
        m_jchHilflinien.setOpaque(false);
        m_jchHilflinien.addActionListener(this);
        layout2.setConstraints(m_jchHilflinien, constraints2);
        panel2.add(m_jchHilflinien);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 6;
        m_jchBeschriftung.setOpaque(false);
        m_jchBeschriftung.addActionListener(this);
        layout2.setConstraints(m_jchBeschriftung, constraints2);
        panel2.add(m_jchBeschriftung);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 5;
        m_jchKontostand.setOpaque(false);
        m_jchKontostand.addActionListener(this);
        layout2.setConstraints(m_jchKontostand, constraints2);
        panel2.add(m_jchKontostand);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 6;
        m_jchGewinnVerlust.setOpaque(false);
        m_jchGewinnVerlust.addActionListener(this);
        layout2.setConstraints(m_jchGewinnVerlust, constraints2);
        panel2.add(m_jchGewinnVerlust);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 7;
        m_jchGesamteinnahmen.setOpaque(false);
        m_jchGesamteinnahmen.addActionListener(this);
        layout2.setConstraints(m_jchGesamteinnahmen, constraints2);
        panel2.add(m_jchGesamteinnahmen);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 7;
        m_jchGesamtausgaben.setOpaque(false);
        m_jchGesamtausgaben.addActionListener(this);
        layout2.setConstraints(m_jchGesamtausgaben, constraints2);
        panel2.add(m_jchGesamtausgaben);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 8;
        m_jchZuschauer.setOpaque(false);
        m_jchZuschauer.addActionListener(this);
        layout2.setConstraints(m_jchZuschauer, constraints2);
        panel2.add(m_jchZuschauer);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 9;
        m_jchSponsoren.setOpaque(false);
        m_jchSponsoren.addActionListener(this);
        layout2.setConstraints(m_jchSponsoren, constraints2);
        panel2.add(m_jchSponsoren);

//        constraints2.gridwidth = 1;
//        constraints2.gridx = 0;
//        constraints2.gridy = 10;
//        m_jchZinsertraege.setOpaque(false);
//        m_jchZinsertraege.addActionListener(this);
//        layout2.setConstraints(m_jchZinsertraege, constraints2);
//        panel2.add(m_jchZinsertraege);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 10;
        m_jchSonstigeEinnahmen.setOpaque(false);
        m_jchSonstigeEinnahmen.addActionListener(this);
        layout2.setConstraints(m_jchSonstigeEinnahmen, constraints2);
        panel2.add(m_jchSonstigeEinnahmen);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 8;
        m_jchStadion.setOpaque(false);
        m_jchStadion.addActionListener(this);
        layout2.setConstraints(m_jchStadion, constraints2);
        panel2.add(m_jchStadion);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 9;
        m_jchSpielergehaelter.setOpaque(false);
        m_jchSpielergehaelter.addActionListener(this);
        layout2.setConstraints(m_jchSpielergehaelter, constraints2);
        panel2.add(m_jchSpielergehaelter);

//        constraints2.gridwidth = 1;
//        constraints2.gridx = 1;
//        constraints2.gridy = 10;
//        m_jchZinsaufwendungen.setOpaque(false);
//        m_jchZinsaufwendungen.addActionListener(this);
//        layout2.setConstraints(m_jchZinsaufwendungen, constraints2);
//        panel2.add(m_jchZinsaufwendungen);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 10;
        m_jchSonstigeAusgaben.setOpaque(false);
        m_jchSonstigeAusgaben.addActionListener(this);
        layout2.setConstraints(m_jchSonstigeAusgaben, constraints2);
        panel2.add(m_jchSonstigeAusgaben);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 12;
        m_jchTrainerstab.setOpaque(false);
        m_jchTrainerstab.addActionListener(this);
        layout2.setConstraints(m_jchTrainerstab, constraints2);
        panel2.add(m_jchTrainerstab);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 13;
        m_jchJugend.setOpaque(false);
        m_jchJugend.addActionListener(this);
        layout2.setConstraints(m_jchJugend, constraints2);
        panel2.add(m_jchJugend);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 12;
        m_jchMarktwert.setOpaque(false);
        m_jchMarktwert.addActionListener(this);
        layout2.setConstraints(m_jchMarktwert, constraints2);
        panel2.add(m_jchMarktwert);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 13;
        m_jchFans.setOpaque(false);
        m_jchFans.addActionListener(this);
        layout2.setConstraints(m_jchFans, constraints2);
        panel2.add(m_jchFans);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 0.01;
        constraints.weighty = 0.001;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel2, constraints);
        add(panel2);

        //Table
        final JPanel panel = new ImagePanel();
        panel.setLayout(new BorderLayout());

        m_clStatistikPanel = new StatistikPanel(true);
        panel.add(m_clStatistikPanel);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.NORTH;
        panel.setBorder(BorderFactory.createLineBorder(ThemeManager.getColor(HOColorName.PANEL_BORDER)));
        layout.setConstraints(panel, constraints);
        add(panel);
    }

    private void initStatistik() {
        try {
            int anzahlHRF = Integer.parseInt(m_jtfAnzahlHRF.getText());

            if (anzahlHRF <= 0) {
                anzahlHRF = 1;
            }

            ho.core.model.UserParameter.instance().statistikFinanzenAnzahlHRF = anzahlHRF;

            final java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance();
            final java.text.NumberFormat format2 = java.text.NumberFormat.getInstance();

            final double[][] statistikWerte = DBManager.instance().getFinanzen4Statistik(anzahlHRF);
            StatistikModel[] models = null;
            models = new StatistikModel[14];

            if (statistikWerte.length > 0) {
                models[0] = new StatistikModel(statistikWerte[0], "Kontostand",
                                               m_jchKontostand.isSelected(), cashColor, format);
                models[1] = new StatistikModel(statistikWerte[1], "GewinnVerlust",
                                               m_jchGewinnVerlust.isSelected(), winLostColor,
                                               format);
                models[2] = new StatistikModel(statistikWerte[2], "Gesamteinnahmen",
                                               m_jchGesamteinnahmen.isSelected(), incomeSumColor,
                                               format);
                models[3] = new StatistikModel(statistikWerte[3], "Gesamtausgaben",
                                               m_jchGesamtausgaben.isSelected(), costSumColor,
                                               format);
                models[4] = new StatistikModel(statistikWerte[4], "Zuschauer",
                                               m_jchZuschauer.isSelected(), incomeSpectatorsColor, format);
                models[5] = new StatistikModel(statistikWerte[5], "Sponsoren",
                                               m_jchSponsoren.isSelected(), incomeSponsorsColor, format);
//                models[6] = new StatistikModel(statistikWerte[6], "Zinsertraege",
//                                               m_jchZinsertraege.isSelected(), incomeFinancialColor, format);
                models[6] = new StatistikModel(statistikWerte[7], "SonstigeEinnahmen",
                                               m_jchSonstigeEinnahmen.isSelected(),
                                               incomeTemporaryColor, format);
                models[7] = new StatistikModel(statistikWerte[8], "Stadion",
                                               m_jchStadion.isSelected(), costArena, format);
                models[8] = new StatistikModel(statistikWerte[9], "Spielergehaelter",
                                               m_jchSpielergehaelter.isSelected(),
                                               costsPlayersColor, format);
//                models[10] = new StatistikModel(statistikWerte[10], "Zinsaufwendungen",
//                                                m_jchZinsaufwendungen.isSelected(),
//                                                costFinancialColor, format);
                models[9] = new StatistikModel(statistikWerte[11], "SonstigeAusgaben",
                                                m_jchSonstigeAusgaben.isSelected(),
                                                costTemporaryColor, format);
                models[10] = new StatistikModel(statistikWerte[12], "Trainerstab",
                                                m_jchTrainerstab.isSelected(), costStaffColor, format);
                models[11] = new StatistikModel(statistikWerte[13], "Jugend",
                                                m_jchJugend.isSelected(), costsYouthColor, format);
                models[12] = new StatistikModel(statistikWerte[14], "Fans", m_jchFans.isSelected(),
                                                fansColor, format2, 100);
                models[13] = new StatistikModel(statistikWerte[15], "Marktwert",
                                                m_jchMarktwert.isSelected(), marketValueColor, format2, 10);
            }

            final String[] yBezeichnungen = ho.core.util.Helper
                                            .convertTimeMillisToFormatString(statistikWerte[16]);

            m_clStatistikPanel.setAllValues(models, yBezeichnungen, format,
                                            HOVerwaltung.instance().getLanguageString("Wochen"),
                                            "", m_jchBeschriftung.isSelected(),
                                            m_jchHilflinien.isSelected());
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"FinanzStatisikPanel.initStatistik : " + e);
        }
    }
}
