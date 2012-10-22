// %634087379:de.hattrickorganizer.gui.statistic%
package ho.module.statistics;

import ho.core.constants.player.PlayerSkill;
import ho.core.db.DBManager;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.model.SpielerCBItem;
import ho.core.gui.model.SpielerCBItemRenderer;
import ho.core.gui.model.StatistikModel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 * Das StatistikPanel
 */
class SpielerStatistikPanel extends ImagePanel
    implements ActionListener, FocusListener, Refreshable, ItemListener
{
	private static final long serialVersionUID = -5003282359250534295L;

    //~ Static fields/initializers -----------------------------------------------------------------


    private Color leadershipColor 	= ThemeManager.getColor(HOColorName.STAT_LEADERSHIP);//Color.gray;
    private Color experienceColor 	= ThemeManager.getColor(HOColorName.STAT_EXPERIENCE);//Color.darkGray;
    private Color formColor 		= ThemeManager.getColor(HOColorName.STAT_FORM);//Color.pink;
    private Color staminaColor 		= ThemeManager.getColor(HOColorName.STAT_STAMINA);//Color.magenta;
    private Color loyaltyColor		= ThemeManager.getColor(HOColorName.STAT_LOYALTY);
    private Color keeperColor 		= ThemeManager.getColor(HOColorName.STAT_KEEPER);//Color.black;
    private Color defendingColor 	= ThemeManager.getColor(HOColorName.STAT_DEFENDING);//Color.blue;
    private Color playmakingColor 	= ThemeManager.getColor(HOColorName.STAT_PLAYMAKING);//Color.yellow;
    private Color passingColor 		= ThemeManager.getColor(HOColorName.STAT_PASSING);//Color.green;
    private Color wingerColor 		= ThemeManager.getColor(HOColorName.STAT_WINGER);//Color.orange;
    private Color scoringColor 		= ThemeManager.getColor(HOColorName.STAT_SCORING);//Color.red;
    private Color setPiecesColor 	= ThemeManager.getColor(HOColorName.STAT_SET_PIECES);//Color.cyan;
    private Color ratingColor 		= ThemeManager.getColor(HOColorName.STAT_RATING);//new Color(100, 200, 0);
    private Color marketValueColor 	= ThemeManager.getColor(HOColorName.STAT_MARKETVALUE);
    private Color wageColor 		= ThemeManager.getColor(HOColorName.STAT_WAGE);//new Color(150, 20, 20);

    //~ Instance fields ----------------------------------------------------------------------------

    private ImageCheckbox m_jchBewertung = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("Rating"),
    		ratingColor,ho.core.model.UserParameter.instance().statistikBewertung);
    private ImageCheckbox m_jchErfahrung = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.experience"),
    		experienceColor,ho.core.model.UserParameter.instance().statistikErfahrung);
    private ImageCheckbox m_jchFluegel = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.skill.winger"),
    		wingerColor,ho.core.model.UserParameter.instance().statistikFluegel);
    private ImageCheckbox m_jchForm = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.form"),
    		formColor,ho.core.model.UserParameter.instance().statistikForm);
    private ImageCheckbox m_jchFuehrung = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.leadership"),
    		leadershipColor,ho.core.model.UserParameter.instance().statistikFuehrung);
    private ImageCheckbox m_jchLoyalty = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.loyalty"),
    		loyaltyColor, ho.core.model.UserParameter.instance().statistikLoyalty);
    private ImageCheckbox m_jchGehalt = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.wage"),
    		wageColor,ho.core.model.UserParameter.instance().statistikSpielerFinanzenGehalt);
    private ImageCheckbox m_jchKondition = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.skill.stamina"),
    		staminaColor,ho.core.model.UserParameter.instance().statistikKondition);
    private ImageCheckbox m_jchMarktwert = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.tsi"),
    		marketValueColor,ho.core.model.UserParameter.instance().statistikSpielerFinanzenMarktwert);
    private ImageCheckbox m_jchPasspiel = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.skill.passing"),
    		passingColor,ho.core.model.UserParameter.instance().statistikPasspiel);
    private ImageCheckbox m_jchSpielaufbau = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.skill.playmaking"),
    		playmakingColor,ho.core.model.UserParameter.instance().statistikSpielaufbau);
    private ImageCheckbox m_jchStandards = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.skill.setpieces"),
    		setPiecesColor,ho.core.model.UserParameter.instance().statistikStandards);
    private ImageCheckbox m_jchTorschuss = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.skill.scoring"),
    		scoringColor,ho.core.model.UserParameter.instance().statistikTorschuss);
    private ImageCheckbox m_jchTorwart = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.skill.keeper"),
    		keeperColor,ho.core.model.UserParameter.instance().statistikTorwart);
    private ImageCheckbox m_jchVerteidigung = new ImageCheckbox(HOVerwaltung.instance().getLanguageString("ls.player.skill.defending"),
    		defendingColor,ho.core.model.UserParameter.instance().statistikVerteidigung);
    private JButton m_jbDrucken = new JButton(ThemeManager.getIcon(HOIconName.PRINTER));
    private JButton m_jbUbernehmen = new JButton(HOVerwaltung.instance().getLanguageString("Uebernehmen"));
    private JCheckBox m_jchBeschriftung = new JCheckBox(HOVerwaltung.instance().getLanguageString("Beschriftung"),
    		ho.core.model.UserParameter.instance().statistikBeschriftung);
    private JCheckBox m_jchHilflinien = new JCheckBox(HOVerwaltung.instance().getLanguageString("Hilflinien"),
    		ho.core.model.UserParameter.instance().statistikHilfslinien);
    private JComboBox m_jcbSpieler = new JComboBox();
    private JTextField m_jtfAnzahlHRF = new JTextField(ho.core.model.UserParameter.instance().statistikAnzahlHRF
                                                       + "");
    private StatistikPanel m_clStatistikPanel;
    private boolean m_bInitialisiert;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerStatistikPanel object.
     */
    SpielerStatistikPanel() {
        ho.core.gui.RefreshManager.instance().registerRefreshable(this);

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
            ho.core.model.UserParameter.instance().statistikHilfslinien = m_jchHilflinien.isSelected();
        } else if (actionEvent.getSource().equals(m_jchBeschriftung)) {
            m_clStatistikPanel.setBeschriftung(m_jchBeschriftung.isSelected());
            ho.core.model.UserParameter.instance().statistikBeschriftung = m_jchBeschriftung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchFuehrung.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.leadership", m_jchFuehrung.isSelected());
            ho.core.model.UserParameter.instance().statistikFuehrung = m_jchFuehrung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchErfahrung.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.experience", m_jchErfahrung.isSelected());
            ho.core.model.UserParameter.instance().statistikErfahrung = m_jchErfahrung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchLoyalty.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.loyalty", m_jchLoyalty.isSelected());
            ho.core.model.UserParameter.instance().statistikLoyalty = m_jchLoyalty.isSelected();
        } else if (actionEvent.getSource().equals(m_jchForm.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.form", m_jchForm.isSelected());
            ho.core.model.UserParameter.instance().statistikForm = m_jchForm.isSelected();
        } else if (actionEvent.getSource().equals(m_jchKondition.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.stamina", m_jchKondition.isSelected());
            ho.core.model.UserParameter.instance().statistikKondition = m_jchKondition.isSelected();
        } else if (actionEvent.getSource().equals(m_jchTorwart.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.keeper", m_jchTorwart.isSelected());
            ho.core.model.UserParameter.instance().statistikTorwart = m_jchTorwart.isSelected();
        } else if (actionEvent.getSource().equals(m_jchVerteidigung.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.defending", m_jchVerteidigung.isSelected());
            ho.core.model.UserParameter.instance().statistikVerteidigung = m_jchVerteidigung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchSpielaufbau.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.playmaking", m_jchSpielaufbau.isSelected());
            ho.core.model.UserParameter.instance().statistikSpielaufbau = m_jchSpielaufbau.isSelected();
        } else if (actionEvent.getSource().equals(m_jchPasspiel.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.passing", m_jchPasspiel.isSelected());
            ho.core.model.UserParameter.instance().statistikPasspiel = m_jchPasspiel.isSelected();
        } else if (actionEvent.getSource().equals(m_jchFluegel.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.winger", m_jchFluegel.isSelected());
            ho.core.model.UserParameter.instance().statistikFluegel = m_jchFluegel.isSelected();
        } else if (actionEvent.getSource().equals(m_jchTorschuss.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.scoring", m_jchTorschuss.isSelected());
            ho.core.model.UserParameter.instance().statistikTorschuss = m_jchTorschuss.isSelected();
        } else if (actionEvent.getSource().equals(m_jchStandards.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.setpieces", m_jchStandards.isSelected());
            ho.core.model.UserParameter.instance().statistikStandards = m_jchStandards.isSelected();
        } else if (actionEvent.getSource().equals(m_jchBewertung.getCheckbox())) {
            m_clStatistikPanel.setShow("Rating", m_jchBewertung.isSelected());
            ho.core.model.UserParameter.instance().statistikBewertung = m_jchBewertung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchMarktwert.getCheckbox())) {
            m_clStatistikPanel.setShow("Marktwert", m_jchMarktwert.isSelected());
            ho.core.model.UserParameter.instance().statistikSpielerFinanzenMarktwert = m_jchMarktwert
                                                                             .isSelected();
        } else if (actionEvent.getSource().equals(m_jchGehalt.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.wage", m_jchGehalt.isSelected());
            ho.core.model.UserParameter.instance().statistikSpielerFinanzenGehalt = m_jchGehalt.isSelected();
        }
    }

    public final void doInitialisieren() {
        initStatistik();
        m_bInitialisiert = true;
    }

    public void focusGained(java.awt.event.FocusEvent focusEvent) {
    }

    public final void focusLost(java.awt.event.FocusEvent focusEvent) {
        Helper.parseInt(ho.core.gui.HOMainFrame.instance(),
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
        m_jchLoyalty.setOpaque(false);
        m_jchLoyalty.addActionListener(this);
        layout2.setConstraints(m_jchLoyalty, constraints2);
        panel2.add(m_jchLoyalty);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 8;
        m_jchTorwart.setOpaque(false);
        m_jchTorwart.addActionListener(this);
        layout2.setConstraints(m_jchTorwart, constraints2);
        panel2.add(m_jchTorwart);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 9;
        m_jchVerteidigung.setOpaque(false);
        m_jchVerteidigung.addActionListener(this);
        layout2.setConstraints(m_jchVerteidigung, constraints2);
        panel2.add(m_jchVerteidigung);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 10;
        m_jchSpielaufbau.setOpaque(false);
        m_jchSpielaufbau.addActionListener(this);
        layout2.setConstraints(m_jchSpielaufbau, constraints2);
        panel2.add(m_jchSpielaufbau);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 11;
        m_jchPasspiel.setOpaque(false);
        m_jchPasspiel.addActionListener(this);
        layout2.setConstraints(m_jchPasspiel, constraints2);
        panel2.add(m_jchPasspiel);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 12;
        m_jchFluegel.setOpaque(false);
        m_jchFluegel.addActionListener(this);
        layout2.setConstraints(m_jchFluegel, constraints2);
        panel2.add(m_jchFluegel);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 13;
        m_jchTorschuss.setOpaque(false);
        m_jchTorschuss.addActionListener(this);
        layout2.setConstraints(m_jchTorschuss, constraints2);
        panel2.add(m_jchTorschuss);

        constraints2.gridwidth = 1;
        constraints2.gridx = 1;
        constraints2.gridy = 14;
        m_jchStandards.setOpaque(false);
        m_jchStandards.addActionListener(this);
        layout2.setConstraints(m_jchStandards, constraints2);
        panel2.add(m_jchStandards);

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
        panel.setBorder(BorderFactory.createLineBorder(ThemeManager.getColor(HOColorName.PANEL_BORDER)));
        layout.setConstraints(panel, constraints);
        add(panel);
    }

    private void initSpielerCB() {
        final Vector<Spieler> spieler = HOVerwaltung.instance().getModel().getAllSpieler();
        final SpielerCBItem[] spielerCBItems = new SpielerCBItem[spieler.size()];

        for (int i = 0; i < spieler.size(); i++) {
            spielerCBItems[i] = new SpielerCBItem((spieler.get(i)).getName(),0f,spieler.get(i));
        }

        Arrays.sort(spielerCBItems);

        //Alte Spieler
        final Vector<Spieler> allSpieler = HOVerwaltung.instance().getModel().getAllOldSpieler();
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

            ho.core.model.UserParameter.instance().statistikAnzahlHRF = anzahlHRF;

            final java.text.NumberFormat format = Helper.DEFAULTDEZIMALFORMAT;
            final java.text.NumberFormat format2 = NumberFormat.getCurrencyInstance();

            if (m_jcbSpieler.getSelectedItem() != null) {
                final double[][] statistikWerte = DBManager.instance().getSpielerDaten4Statistik(((SpielerCBItem) m_jcbSpieler
                                                                                                                     .getSelectedItem()).getSpieler()
                                                                                                                     .getSpielerID(),
                                                                                                                    anzahlHRF);
                final StatistikModel[] models = new StatistikModel[statistikWerte.length];

                //Es sind 15 Werte!
                if (statistikWerte.length > 0) {
                    double faktor = 20 / getMaxValue(statistikWerte[0]);
                    models[0] = new StatistikModel(statistikWerte[0], "Marktwert",
                                                   m_jchMarktwert.isSelected(), marketValueColor, format,
                                                   faktor);
                    faktor = 20 / getMaxValue(statistikWerte[1]);
                    models[1] = new StatistikModel(statistikWerte[1], "ls.player.wage",
                                                   m_jchGehalt.isSelected(), wageColor, format2, faktor);
                    models[2] = new StatistikModel(statistikWerte[2], "ls.player.leadership",
                                                   m_jchFuehrung.isSelected(), leadershipColor, format);
                    models[3] = new StatistikModel(statistikWerte[3], "ls.player.experience",
                                                   m_jchErfahrung.isSelected(), experienceColor, format);
                    models[4] = new StatistikModel(statistikWerte[4], "ls.player.form",
                                                   m_jchForm.isSelected(), formColor, format);
                    models[5] = new StatistikModel(statistikWerte[5], "ls.player.skill.stamina",
                                                   m_jchKondition.isSelected(), staminaColor, format);
                    models[6] = new StatistikModel(statistikWerte[6], "ls.player.skill.keeper",
                                                   m_jchTorwart.isSelected(), keeperColor, format);
                    models[7] = new StatistikModel(statistikWerte[7], "ls.player.skill.defending",
                                                   m_jchVerteidigung.isSelected(), defendingColor,
                                                   format);
                    models[8] = new StatistikModel(statistikWerte[8], "ls.player.skill.playmaking",
                                                   m_jchSpielaufbau.isSelected(), playmakingColor,
                                                   format);
                    models[9] = new StatistikModel(statistikWerte[9], "ls.player.skill.passing",
                                                   m_jchPasspiel.isSelected(), passingColor, format);
                    models[10] = new StatistikModel(statistikWerte[10], "ls.player.skill.winger",
                                                    m_jchFluegel.isSelected(), wingerColor, format);
                    models[11] = new StatistikModel(statistikWerte[11], "ls.player.skill.scoring",
                                                    m_jchTorschuss.isSelected(), scoringColor, format);
                    models[12] = new StatistikModel(statistikWerte[12], "ls.player.skill.setpieces",
                                                    m_jchStandards.isSelected(), setPiecesColor, format);
                    models[13] = new StatistikModel(statistikWerte[13], "Rating",
                                                    m_jchBewertung.isSelected(), ratingColor, format);
                    models[14] = new StatistikModel(statistikWerte[14], "ls.player.loyalty",
                            						m_jchLoyalty.isSelected(), loyaltyColor, format);
                }

                final String[] yBezeichnungen = Helper
                                                .convertTimeMillisToFormatString(statistikWerte[15]);

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
