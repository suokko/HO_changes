// %634087379:de.hattrickorganizer.gui.statistic%
package de.hattrickorganizer.gui.statistic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import plugins.ISpieler;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.Refreshable;
import de.hattrickorganizer.gui.model.SpielerCBItem;
import de.hattrickorganizer.gui.model.SpielerCBItemRenderer;
import de.hattrickorganizer.gui.model.StatistikModel;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;


/**
 * Das StatistikPanel
 */
class SpielerStatistikPanel extends ImagePanel
    implements ActionListener, FocusListener, Refreshable, ItemListener
{
	private static final long serialVersionUID = -5003282359250534295L;
	
    //~ Static fields/initializers -----------------------------------------------------------------


    private Color leadershipColor 	= ThemeManager.getColor("ho.statistics.leadership");//Color.gray;
    private Color experienceColor 	= ThemeManager.getColor("ho.statistics.experience");//Color.darkGray;
    private Color formColor 		= ThemeManager.getColor("ho.statistics.form");//Color.pink;
    private Color staminaColor 		= ThemeManager.getColor("ho.statistics.stamina");//Color.magenta;
    private Color keeperColor 		= ThemeManager.getColor("ho.statistics.keeper");//Color.black;
    private Color defendingColor 	= ThemeManager.getColor("ho.statistics.defending");//Color.blue;
    private Color playmakingColor 	= ThemeManager.getColor("ho.statistics.playmaking");//Color.yellow;
    private Color passingColor 		= ThemeManager.getColor("ho.statistics.passing");//Color.green;
    private Color wingerColor 		= ThemeManager.getColor("ho.statistics.winger");//Color.orange;
    private Color scoringColor 		= ThemeManager.getColor("ho.statistics.scoring");//Color.red;
    private Color setPiecesColor 	= ThemeManager.getColor("ho.statistics.setPieces");//Color.cyan;
    private Color ratingColor 		= ThemeManager.getColor("ho.statistics.rating");//new Color(100, 200, 0);
    private Color marketValueColor 	= ThemeManager.getColor("ho.statistics.marketValue");
    private Color wageColor 		= ThemeManager.getColor("ho.statistics.wage");//new Color(150, 20, 20);

    //~ Instance fields ----------------------------------------------------------------------------

    private ImageCheckbox m_jchBewertung = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Bewertung"),
                                                             ratingColor,gui.UserParameter.instance().statistikBewertung);
    private ImageCheckbox m_jchErfahrung = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("skill.experience"),
                                                             experienceColor,gui.UserParameter.instance().statistikErfahrung);
    private ImageCheckbox m_jchFluegel = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("skill.winger"),
                                                           wingerColor,gui.UserParameter.instance().statistikFluegel);
    private ImageCheckbox m_jchForm = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Form"),
                                                        formColor,gui.UserParameter.instance().statistikForm);
    private ImageCheckbox m_jchFuehrung = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Fuehrung"),
    		leadershipColor,gui.UserParameter.instance().statistikFuehrung);
    private ImageCheckbox m_jchGehalt = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Gehalt"),
                                                          wageColor,gui.UserParameter.instance().statistikSpielerFinanzenGehalt);
    private ImageCheckbox m_jchKondition = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("skill.stamina"),
                                                             staminaColor,gui.UserParameter.instance().statistikKondition);
    private ImageCheckbox m_jchMarktwert = new ImageCheckbox("TSI",marketValueColor,
                                                             gui.UserParameter.instance().statistikSpielerFinanzenMarktwert);
    private ImageCheckbox m_jchPasspiel = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("skill.passing"),
                                                            passingColor,gui.UserParameter.instance().statistikPasspiel);
    private ImageCheckbox m_jchSpielaufbau = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("skill.playmaking"),
                                                               playmakingColor,gui.UserParameter.instance().statistikSpielaufbau);
    private ImageCheckbox m_jchStandards = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("skill.set_pieces"),
                                                             setPiecesColor,gui.UserParameter.instance().statistikStandards);
    private ImageCheckbox m_jchTorschuss = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("skill.scoring"),
                                                             scoringColor,gui.UserParameter.instance().statistikTorschuss);
    private ImageCheckbox m_jchTorwart = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("skill.keeper"),
                                                           keeperColor,gui.UserParameter.instance().statistikTorwart);
    private ImageCheckbox m_jchVerteidigung = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("skill.defending"),
                                                               defendingColor,gui.UserParameter.instance().statistikVerteidigung);
    private JButton m_jbDrucken = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/Drucken.png")));
    private JButton m_jbUbernehmen = new JButton(HOVerwaltung.instance().getLanguageString("Uebernehmen"));
    private JCheckBox m_jchBeschriftung = new JCheckBox(HOVerwaltung.instance().getLanguageString("Beschriftung"),
                                                        gui.UserParameter.instance().statistikBeschriftung);
    private JCheckBox m_jchHilflinien = new JCheckBox(HOVerwaltung.instance().getLanguageString("Hilflinien"),
                                                      gui.UserParameter.instance().statistikHilfslinien);
    private JComboBox m_jcbSpieler = new JComboBox();
    private JTextField m_jtfAnzahlHRF = new JTextField(gui.UserParameter.instance().statistikAnzahlHRF
                                                       + "");
    private StatistikPanel m_clStatistikPanel;
    private boolean m_bInitialisiert;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerStatistikPanel object.
     */
    SpielerStatistikPanel() {
        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(this);

        initSpielerCB();

        initComponents();

    }

     public final void setAktuelleSpieler(int spielerid) {
        final ComboBoxModel model = m_jcbSpieler.getModel();

        for (int i = 0; i < model.getSize(); ++i) {
            if (model.getElementAt(i) instanceof SpielerCBItem) {
                if (((SpielerCBItem) model.getElementAt(i)).getSpieler()
                     .getSpielerID() == spielerid) {
                    //Spieler gefunden -> Auswählen und fertig
                    m_jcbSpieler.setSelectedIndex(i);
                    return;
                }
            }
        }
    }

    public final void setInitialisiert(boolean init) {
        m_bInitialisiert = init;
    }

    public final boolean isInitialisiert() {
        return m_bInitialisiert;
    }

    //-- Helper
    public final double getMaxValue(double[] werte) {
        double max = 0;

        for (int i = 0; (werte != null) && (i < werte.length); i++) {
            if (werte[i] > max) {
                max = werte[i];
            }
        }

        return (max);
    }

    //--------Listener-------------------------------    
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbUbernehmen)) {
            initStatistik();
        } else if (actionEvent.getSource().equals(m_jbDrucken)) {
            if (((SpielerCBItem) m_jcbSpieler.getSelectedItem() != null)) {
                final String name = ((SpielerCBItem) m_jcbSpieler
                                     .getSelectedItem()).getSpieler().getName();
                m_clStatistikPanel.doPrint(name);
            }
        } else if (actionEvent.getSource().equals(m_jchHilflinien)) {
            m_clStatistikPanel.setHilfslinien(m_jchHilflinien.isSelected());
            gui.UserParameter.instance().statistikHilfslinien = m_jchHilflinien.isSelected();
        } else if (actionEvent.getSource().equals(m_jchBeschriftung)) {
            m_clStatistikPanel.setBeschriftung(m_jchBeschriftung.isSelected());
            gui.UserParameter.instance().statistikBeschriftung = m_jchBeschriftung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchFuehrung.getCheckbox())) {
            m_clStatistikPanel.setShow("Fuehrung", m_jchFuehrung.isSelected());
            gui.UserParameter.instance().statistikFuehrung = m_jchFuehrung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchErfahrung.getCheckbox())) {
            m_clStatistikPanel.setShow("Erfahrung", m_jchErfahrung.isSelected());
            gui.UserParameter.instance().statistikErfahrung = m_jchErfahrung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchForm.getCheckbox())) {
            m_clStatistikPanel.setShow("Form", m_jchForm.isSelected());
            gui.UserParameter.instance().statistikForm = m_jchForm.isSelected();
        } else if (actionEvent.getSource().equals(m_jchKondition.getCheckbox())) {
            m_clStatistikPanel.setShow("Kondition", m_jchKondition.isSelected());
            gui.UserParameter.instance().statistikKondition = m_jchKondition.isSelected();
        } else if (actionEvent.getSource().equals(m_jchTorwart.getCheckbox())) {
            m_clStatistikPanel.setShow("Torwart", m_jchTorwart.isSelected());
            gui.UserParameter.instance().statistikTorwart = m_jchTorwart.isSelected();
        } else if (actionEvent.getSource().equals(m_jchVerteidigung.getCheckbox())) {
            m_clStatistikPanel.setShow("Verteidigung", m_jchVerteidigung.isSelected());
            gui.UserParameter.instance().statistikVerteidigung = m_jchVerteidigung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchSpielaufbau.getCheckbox())) {
            m_clStatistikPanel.setShow("Spielaufbau", m_jchSpielaufbau.isSelected());
            gui.UserParameter.instance().statistikSpielaufbau = m_jchSpielaufbau.isSelected();
        } else if (actionEvent.getSource().equals(m_jchPasspiel.getCheckbox())) {
            m_clStatistikPanel.setShow("Passpiel", m_jchPasspiel.isSelected());
            gui.UserParameter.instance().statistikPasspiel = m_jchPasspiel.isSelected();
        } else if (actionEvent.getSource().equals(m_jchFluegel.getCheckbox())) {
            m_clStatistikPanel.setShow("Fluegelspiel", m_jchFluegel.isSelected());
            gui.UserParameter.instance().statistikFluegel = m_jchFluegel.isSelected();
        } else if (actionEvent.getSource().equals(m_jchTorschuss.getCheckbox())) {
            m_clStatistikPanel.setShow("Torschuss", m_jchTorschuss.isSelected());
            gui.UserParameter.instance().statistikTorschuss = m_jchTorschuss.isSelected();
        } else if (actionEvent.getSource().equals(m_jchStandards.getCheckbox())) {
            m_clStatistikPanel.setShow("Standards", m_jchStandards.isSelected());
            gui.UserParameter.instance().statistikStandards = m_jchStandards.isSelected();
        } else if (actionEvent.getSource().equals(m_jchBewertung.getCheckbox())) {
            m_clStatistikPanel.setShow("Bewertung", m_jchBewertung.isSelected());
            gui.UserParameter.instance().statistikBewertung = m_jchBewertung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchMarktwert.getCheckbox())) {
            m_clStatistikPanel.setShow("Marktwert", m_jchMarktwert.isSelected());
            gui.UserParameter.instance().statistikSpielerFinanzenMarktwert = m_jchMarktwert
                                                                             .isSelected();
        } else if (actionEvent.getSource().equals(m_jchGehalt.getCheckbox())) {
            m_clStatistikPanel.setShow("Gehalt", m_jchGehalt.isSelected());
            gui.UserParameter.instance().statistikSpielerFinanzenGehalt = m_jchGehalt.isSelected();
        }
    }

    public final void doInitialisieren() {
        initStatistik();
        m_bInitialisiert = true;
    }

    public void focusGained(java.awt.event.FocusEvent focusEvent) {
    }

    public final void focusLost(java.awt.event.FocusEvent focusEvent) {
        Helper.parseInt(de.hattrickorganizer.gui.HOMainFrame.instance(),
                                                   ((JTextField) focusEvent.getSource()), false);
    }

    public final void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            initStatistik();
        }
    }

    //-------Refresh---------------------------------    
    public final void reInit() {
        initSpielerCB();

        m_bInitialisiert = false;

        //initStatistik();
    }

    public void refresh() {
        //initSpielerCB();
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
        m_jbDrucken.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Statistik_drucken"));
        m_jbDrucken.setPreferredSize(new Dimension(25, 25));
        m_jbDrucken.addActionListener(this);
        layout2.setConstraints(m_jbDrucken, constraints2);
        panel2.add(m_jbDrucken);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Wochen"));
        constraints2.gridwidth = 1;
        constraints2.fill = GridBagConstraints.HORIZONTAL;
        constraints2.anchor = GridBagConstraints.WEST;
        constraints2.gridx = 0;
        constraints2.gridy = 1;
        layout2.setConstraints(label, constraints2);
        panel2.add(label);
        m_jtfAnzahlHRF.setHorizontalAlignment(SwingConstants.RIGHT);
        m_jtfAnzahlHRF.addFocusListener(this);
        constraints2.gridx = 1;
        constraints2.gridy = 1;
        layout2.setConstraints(m_jtfAnzahlHRF, constraints2);
        panel2.add(m_jtfAnzahlHRF);

        constraints2.gridx = 0;
        constraints2.gridy = 2;
        constraints2.gridwidth = 2;
        m_jbUbernehmen.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Statistik_HRFAnzahluebernehmen"));
        layout2.setConstraints(m_jbUbernehmen, constraints2);
        m_jbUbernehmen.addActionListener(this);
        panel2.add(m_jbUbernehmen);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Spieler"));
        label.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Statistik_Spieler"));
        constraints2.gridx = 0;
        constraints2.gridy = 3;
        constraints2.gridwidth = 2;
        layout2.setConstraints(label, constraints2);
        panel2.add(label);
        constraints2.gridx = 0;
        constraints2.gridy = 4;
        m_jcbSpieler.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Statistik_Spieler"));
        m_jcbSpieler.setRenderer(new SpielerCBItemRenderer());
        m_jcbSpieler.setBackground(ColorLabelEntry.BG_STANDARD);
        m_jcbSpieler.setMaximumRowCount(25);
        m_jcbSpieler.addItemListener(this);
        m_jcbSpieler.setMaximumSize(new Dimension(200, 25));
        layout2.setConstraints(m_jcbSpieler, constraints2);
        panel2.add(m_jcbSpieler);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 5;
        m_jchHilflinien.setOpaque(false);
        m_jchHilflinien.setBackground(Color.white);
        m_jchHilflinien.addActionListener(this);
        layout2.setConstraints(m_jchHilflinien, constraints2);
        panel2.add(m_jchHilflinien);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 6;
        m_jchBeschriftung.setOpaque(false);
        m_jchBeschriftung.setBackground(Color.white);
        m_jchBeschriftung.addActionListener(this);
        layout2.setConstraints(m_jchBeschriftung, constraints2);
        panel2.add(m_jchBeschriftung);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 7;
        m_jchBewertung.setOpaque(false);
        m_jchBewertung.addActionListener(this);
        layout2.setConstraints(m_jchBewertung, constraints2);
        panel2.add(m_jchBewertung);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 8;
        m_jchFuehrung.setOpaque(false);
        m_jchFuehrung.addActionListener(this);
        layout2.setConstraints(m_jchFuehrung, constraints2);
        panel2.add(m_jchFuehrung);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 9;
        m_jchErfahrung.setOpaque(false);
        m_jchErfahrung.addActionListener(this);
        layout2.setConstraints(m_jchErfahrung, constraints2);
        panel2.add(m_jchErfahrung);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 5;
        m_jchForm.setOpaque(false);
        m_jchForm.addActionListener(this);
        layout2.setConstraints(m_jchForm, constraints2);
        panel2.add(m_jchForm);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 6;
        m_jchKondition.setOpaque(false);
        m_jchKondition.addActionListener(this);
        layout2.setConstraints(m_jchKondition, constraints2);
        panel2.add(m_jchKondition);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 7;
        m_jchTorwart.setOpaque(false);
        m_jchTorwart.addActionListener(this);
        layout2.setConstraints(m_jchTorwart, constraints2);
        panel2.add(m_jchTorwart);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 8;
        m_jchVerteidigung.setOpaque(false);
        m_jchVerteidigung.addActionListener(this);
        layout2.setConstraints(m_jchVerteidigung, constraints2);
        panel2.add(m_jchVerteidigung);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 9;
        m_jchSpielaufbau.setOpaque(false);
        m_jchSpielaufbau.addActionListener(this);
        layout2.setConstraints(m_jchSpielaufbau, constraints2);
        panel2.add(m_jchSpielaufbau);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 10;
        m_jchPasspiel.setOpaque(false);
        m_jchPasspiel.addActionListener(this);
        layout2.setConstraints(m_jchPasspiel, constraints2);
        panel2.add(m_jchPasspiel);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 11;
        m_jchFluegel.setOpaque(false);
        m_jchFluegel.addActionListener(this);
        layout2.setConstraints(m_jchFluegel, constraints2);
        panel2.add(m_jchFluegel);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 12;
        m_jchTorschuss.setOpaque(false);
        m_jchTorschuss.addActionListener(this);
        layout2.setConstraints(m_jchTorschuss, constraints2);
        panel2.add(m_jchTorschuss);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 13;
        m_jchStandards.setOpaque(false);
        m_jchStandards.addActionListener(this);
        layout2.setConstraints(m_jchStandards, constraints2);
        panel2.add(m_jchStandards);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 10;
        m_jchMarktwert.setOpaque(false);
        m_jchMarktwert.addActionListener(this);
        layout2.setConstraints(m_jchMarktwert, constraints2);
        panel2.add(m_jchMarktwert);

        constraints2.gridwidth = 1;
        constraints2.gridx = 0;
        constraints2.gridy = 11;
        m_jchGehalt.setOpaque(false);
        m_jchGehalt.addActionListener(this);
        layout2.setConstraints(m_jchGehalt, constraints2);
        panel2.add(m_jchGehalt);

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

        m_clStatistikPanel = new StatistikPanel(false);
        panel.add(m_clStatistikPanel);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.NORTH;
        panel.setBorder(BorderFactory.createLineBorder(ThemeManager.getColor("ho.panel.border")));
        layout.setConstraints(panel, constraints);
        add(panel);
    }

    private void initSpielerCB() {
        final Vector<ISpieler> spieler = HOVerwaltung.instance().getModel().getAllSpieler();
        final SpielerCBItem[] spielerCBItems = new SpielerCBItem[spieler.size()];

        for (int i = 0; i < spieler.size(); i++) {
            spielerCBItems[i] = new SpielerCBItem((spieler.get(i)).getName(),0f,spieler.get(i));
        }

        Arrays.sort(spielerCBItems);

        //Alte Spieler
        final Vector<ISpieler> allSpieler = HOVerwaltung.instance().getModel().getAllOldSpieler();
        final SpielerCBItem[] spielerAllCBItems = new SpielerCBItem[allSpieler
                                                                                                                                  .size()];

        for (int i = 0; i < allSpieler.size(); i++) {
            spielerAllCBItems[i] = new SpielerCBItem((allSpieler.get(i)).getName(), 0f,allSpieler.get(i));
        }

        Arrays.sort(spielerAllCBItems);

        //Zusammenfügen
        final SpielerCBItem[] cbItems = new SpielerCBItem[spielerCBItems.length
                                                                       + spielerAllCBItems.length
                                                                       + 1];
        int i = 0;

        for (; i < spielerCBItems.length; i++) {
            cbItems[i] = spielerCBItems[i];
        }

        //Fur die Leerzeile;
        i++;

        for (int j = 0; j < spielerAllCBItems.length; j++) {
            cbItems[i + j] = spielerAllCBItems[j];
        }

        final DefaultComboBoxModel cbModel = new DefaultComboBoxModel(cbItems);

        m_jcbSpieler.setModel(cbModel);
        m_jcbSpieler.removeItemListener(this);

        //Kein Spieler selektiert
        m_jcbSpieler.setSelectedItem(null);
        m_jcbSpieler.addItemListener(this);
    }


    private void initStatistik() {
        try {
            int anzahlHRF = Integer.parseInt(m_jtfAnzahlHRF.getText());

            if (anzahlHRF <= 0) {
                anzahlHRF = 1;
            }

            gui.UserParameter.instance().statistikAnzahlHRF = anzahlHRF;

            final java.text.NumberFormat format = Helper.DEFAULTDEZIMALFORMAT;
            final java.text.NumberFormat format2 = NumberFormat.getCurrencyInstance();

            if (m_jcbSpieler.getSelectedItem() != null) {
                final double[][] statistikWerte = DBZugriff.instance().getSpielerDaten4Statistik(((SpielerCBItem) m_jcbSpieler
                                                                                                                     .getSelectedItem()).getSpieler()
                                                                                                                     .getSpielerID(),
                                                                                                                    anzahlHRF);
                final StatistikModel[] models = new StatistikModel[statistikWerte.length];

                //Es sind 13 Werte!
                if (statistikWerte.length > 0) {
                    double faktor = 20 / getMaxValue(statistikWerte[0]);
                    models[0] = new StatistikModel(statistikWerte[0], "Marktwert",
                                                   m_jchMarktwert.isSelected(), marketValueColor, format,
                                                   faktor);
                    faktor = 20 / getMaxValue(statistikWerte[1]);
                    models[1] = new StatistikModel(statistikWerte[1], "Gehalt",
                                                   m_jchGehalt.isSelected(), wageColor, format2, faktor);
                    models[2] = new StatistikModel(statistikWerte[2], "Fuehrung",
                                                   m_jchFuehrung.isSelected(), leadershipColor, format);
                    models[3] = new StatistikModel(statistikWerte[3], "Erfahrung",
                                                   m_jchErfahrung.isSelected(), experienceColor, format);
                    models[4] = new StatistikModel(statistikWerte[4], "Form",
                                                   m_jchForm.isSelected(), formColor, format);
                    models[5] = new StatistikModel(statistikWerte[5], "Kondition",
                                                   m_jchKondition.isSelected(), staminaColor, format);
                    models[6] = new StatistikModel(statistikWerte[6], "Torwart",
                                                   m_jchTorwart.isSelected(), keeperColor, format);
                    models[7] = new StatistikModel(statistikWerte[7], "Verteidigung",
                                                   m_jchVerteidigung.isSelected(), defendingColor,
                                                   format);
                    models[8] = new StatistikModel(statistikWerte[8], "Spielaufbau",
                                                   m_jchSpielaufbau.isSelected(), playmakingColor,
                                                   format);
                    models[9] = new StatistikModel(statistikWerte[9], "Passpiel",
                                                   m_jchPasspiel.isSelected(), passingColor, format);
                    models[10] = new StatistikModel(statistikWerte[10], "Fluegelspiel",
                                                    m_jchFluegel.isSelected(), wingerColor, format);
                    models[11] = new StatistikModel(statistikWerte[11], "Torschuss",
                                                    m_jchTorschuss.isSelected(), scoringColor, format);
                    models[12] = new StatistikModel(statistikWerte[12], "Standards",
                                                    m_jchStandards.isSelected(), setPiecesColor, format);
                    models[13] = new StatistikModel(statistikWerte[13], "Bewertung",
                                                    m_jchBewertung.isSelected(), ratingColor, format);
                }

                final String[] yBezeichnungen = Helper
                                                .convertTimeMillisToFormatString(statistikWerte[14]);

                m_clStatistikPanel.setAllValues(models, yBezeichnungen, format,
                                                HOVerwaltung.instance().getLanguageString("Wochen"),
                                                "", m_jchBeschriftung.isSelected(),
                                                m_jchHilflinien.isSelected());
            } else {
                m_clStatistikPanel.setAllValues(null, new String[0], format,
                                                HOVerwaltung.instance().getLanguageString("Wochen"),
                                                "", m_jchBeschriftung.isSelected(),
                                                m_jchHilflinien.isSelected());
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"SpielerStatistikPanel.initStatistik : " + e);
        }
    }
}
