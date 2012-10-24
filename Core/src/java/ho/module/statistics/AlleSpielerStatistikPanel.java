// %3661143817:de.hattrickorganizer.gui.statistic%
package ho.module.statistics;

import ho.core.constants.player.PlayerSkill;
import ho.core.db.DBManager;
import ho.core.gui.HOMainFrame;
import ho.core.gui.RefreshManager;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.model.StatistikModel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.util.HOLogger;
import ho.core.util.Helper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



/**
 * The Team statistics panel
 */
public class AlleSpielerStatistikPanel extends ImagePanel
    implements ActionListener, FocusListener, ItemListener, Refreshable
{
	private static final long serialVersionUID = -6588840565958987842L;

    //~ Static fields/initializers -----------------------------------------------------------------

    private Color leadershipColor 	= ThemeManager.getColor(HOColorName.STAT_LEADERSHIP);//Color.gray;
    private Color experienceColor 	= ThemeManager.getColor(HOColorName.STAT_EXPERIENCE);//Color.darkGray;
    private Color formColor 		= ThemeManager.getColor(HOColorName.STAT_FORM);//Color.pink;
    private Color staminaColor 		= ThemeManager.getColor(HOColorName.STAT_STAMINA);//Color.magenta;
    private Color loyaltyColor 		= ThemeManager.getColor(HOColorName.STAT_LOYALTY);
    private Color keeperColor 		= ThemeManager.getColor(HOColorName.STAT_KEEPER);//Color.black;
    private Color defendingColor 	= ThemeManager.getColor(HOColorName.STAT_DEFENDING);//Color.blue;
    private Color playmakingColor 	= ThemeManager.getColor(HOColorName.STAT_PLAYMAKING);//Color.yellow;
    private Color passingColor 		= ThemeManager.getColor(HOColorName.STAT_PASSING);//Color.green;
    private Color wingerColor 		= ThemeManager.getColor(HOColorName.STAT_WINGER);//Color.orange;
    private Color scoringColor 		= ThemeManager.getColor(HOColorName.STAT_SCORING);//Color.red;
    private Color setPiecesColor 	= ThemeManager.getColor(HOColorName.STAT_SET_PIECES);//Color.cyan;
    private Color TSIColor 			= ThemeManager.getColor(HOColorName.STAT_MARKETVALUE);
    private Color wageColor 		= ThemeManager.getColor(HOColorName.STAT_WAGE);
    //~ Instance fields ----------------------------------------------------------------------------
    private UserParameter gup = ho.core.model.UserParameter.instance();
    private HOVerwaltung hov = HOVerwaltung.instance();
    private ImageCheckbox m_jchExperience = new ImageCheckbox(hov.getLanguageString("DurchschnittErfahrung"),
    		experienceColor, gup.statistikAlleErfahrung);
    private ImageCheckbox m_jchWinger = new ImageCheckbox(hov.getLanguageString("ls.player.skill.winger"),
    		wingerColor, gup.statistikAlleFluegel);
    private ImageCheckbox m_jchForm = new ImageCheckbox(hov.getLanguageString("DurchschnittForm"),
    		formColor, gup.statistikAlleForm);
    private ImageCheckbox m_jchLeadership = new ImageCheckbox(hov.getLanguageString("ls.player.leadership"),
    		leadershipColor, gup.statistikAlleFuehrung);
    private ImageCheckbox m_jchLoyalty = new ImageCheckbox(hov.getLanguageString("ls.player.loyalty"),
    		loyaltyColor, gup.statistikAllLoyalty);
    private ImageCheckbox m_jchStamina = new ImageCheckbox(hov.getLanguageString("ls.player.skill.stamina"),
    		staminaColor, gup.statistikAlleKondition);
    private ImageCheckbox m_jchPassing = new ImageCheckbox(hov.getLanguageString("ls.player.skill.passing"),
    		passingColor, gup.statistikAllePasspiel);
    private ImageCheckbox m_jchPlaymaking = new ImageCheckbox(hov.getLanguageString("ls.player.skill.playmaking"),
    		playmakingColor, gup.statistikAlleSpielaufbau);
    private ImageCheckbox m_jchSetPieces = new ImageCheckbox(hov.getLanguageString("ls.player.skill.setpieces"),
    		setPiecesColor, gup.statistikAlleStandards);
    private ImageCheckbox m_jchScoring = new ImageCheckbox(hov.getLanguageString("ls.player.skill.scoring"),
    		scoringColor, gup.statistikAlleTorschuss);
    private ImageCheckbox m_jchKeeper = new ImageCheckbox(hov.getLanguageString("ls.player.skill.keeper"),
    		keeperColor,gup.statistikAlleTorwart);
    private ImageCheckbox m_jchVerteidigung = new ImageCheckbox(hov.getLanguageString("ls.player.skill.defending"),
    		defendingColor,gup.statistikAlleVerteidigung);
    private ImageCheckbox m_jchTSI = new ImageCheckbox(hov.getLanguageString("AverageTSI"),
    		TSIColor, gup.statistikAllTSI);
    private ImageCheckbox m_jchWages = new ImageCheckbox(hov.getLanguageString("ls.player.wage"),
    		wageColor,gup.statistikAllWages);


    private JButton m_jbPrint = new JButton(ThemeManager.getIcon(HOIconName.PRINTER));
    private JButton m_jbUbernehmen = new JButton(hov.getLanguageString("ls.button.apply"));
    private JCheckBox m_jchInscription = new JCheckBox(hov.getLanguageString("Beschriftung"),
    		gup.statistikAlleBeschriftung);
    private JCheckBox m_jchHelpLines = new JCheckBox(hov.getLanguageString("Hilflinien"),
    		gup.statistikAlleHilfslinien);
    private JComboBox m_jcbGruppe = new JComboBox(HOIconName.TEAMSMILIES);
    private JTextField m_jtfNumberOfHRF = new JTextField(gup.statistikAnzahlHRF + "", 5);
    private StatistikPanel m_clStatistikPanel;
    private boolean m_bInitialisiert;

    final GridBagLayout layout = new GridBagLayout();
    final GridBagConstraints constraints = new GridBagConstraints();
    final JPanel panel2 = new ImagePanel();
    final GridBagLayout layout2 = new GridBagLayout();
    final GridBagConstraints constraints2 = new GridBagConstraints();


    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AlleSpielerStatistikPanel object.
     */
    public AlleSpielerStatistikPanel() {
        RefreshManager.instance().registerRefreshable(this);
        initComponents();
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
        } else if (actionEvent.getSource().equals(m_jbPrint)) {
            m_clStatistikPanel.doPrint(hov.getLanguageString("Verein"));
        } else if (actionEvent.getSource().equals(m_jchHelpLines)) {
            m_clStatistikPanel.setHilfslinien(m_jchHelpLines.isSelected());
            gup.statistikAlleHilfslinien = m_jchHelpLines.isSelected();
        } else if (actionEvent.getSource().equals(m_jchInscription)) {
            m_clStatistikPanel.setBeschriftung(m_jchInscription.isSelected());
            gup.statistikAlleBeschriftung = m_jchInscription.isSelected();
        } else if (actionEvent.getSource().equals(m_jchLeadership.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.leadership", m_jchLeadership.isSelected());
            gup.statistikAlleFuehrung = m_jchLeadership.isSelected();
        } else if (actionEvent.getSource().equals(m_jchTSI.getCheckbox())) {
            m_clStatistikPanel.setShow("Marktwert", m_jchTSI.isSelected());
            gup.statistikAllTSI = m_jchTSI.isSelected();
        } else if (actionEvent.getSource().equals(m_jchWages.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.wage", m_jchWages.isSelected());
            gup.statistikAllWages = m_jchWages.isSelected();
        } else if (actionEvent.getSource().equals(m_jchExperience.getCheckbox())) {
            m_clStatistikPanel.setShow("DurchschnittErfahrung", m_jchExperience.isSelected());
            gup.statistikAlleErfahrung = m_jchExperience.isSelected();
        } else if (actionEvent.getSource().equals(m_jchForm.getCheckbox())) {
            m_clStatistikPanel.setShow("DurchschnittForm", m_jchForm.isSelected());
            gup.statistikAlleForm = m_jchForm.isSelected();
        } else if (actionEvent.getSource().equals(m_jchStamina.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.stamina", m_jchStamina.isSelected());
            gup.statistikAlleKondition = m_jchStamina.isSelected();
        } else if (actionEvent.getSource().equals(m_jchLoyalty.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.loyalty", m_jchLoyalty.isSelected());
            gup.statistikAllLoyalty = m_jchLoyalty.isSelected();
        } else if (actionEvent.getSource().equals(m_jchKeeper.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.keeper", m_jchKeeper.isSelected());
            gup.statistikAlleTorwart = m_jchKeeper.isSelected();
        } else if (actionEvent.getSource().equals(m_jchVerteidigung.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.defending", m_jchVerteidigung.isSelected());
            gup.statistikAlleVerteidigung = m_jchVerteidigung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchPlaymaking.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.playmaking", m_jchPlaymaking.isSelected());
            gup.statistikAlleSpielaufbau = m_jchPlaymaking.isSelected();
        } else if (actionEvent.getSource().equals(m_jchPassing.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.passing", m_jchPassing.isSelected());
            gup.statistikAllePasspiel = m_jchPassing.isSelected();
        } else if (actionEvent.getSource().equals(m_jchWinger.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.winger", m_jchWinger.isSelected());
            gup.statistikAlleFluegel = m_jchWinger.isSelected();
        } else if (actionEvent.getSource().equals(m_jchScoring.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.scoring", m_jchScoring.isSelected());
            gup.statistikAlleTorschuss = m_jchScoring.isSelected();
        } else if (actionEvent.getSource().equals(m_jchSetPieces.getCheckbox())) {
            m_clStatistikPanel.setShow("ls.player.skill.setpieces", m_jchSetPieces.isSelected());
            gup.statistikAlleStandards = m_jchSetPieces.isSelected();
        }
    }

    public final void doInitialisieren() {
        initStatistik();
        m_bInitialisiert = true;
    }

    public void focusGained(FocusEvent focusEvent) {
    }

    public final void focusLost(FocusEvent focusEvent) {
        Helper.parseInt(HOMainFrame.instance(),((JTextField) focusEvent.getSource()), false);
    }

    public final void itemStateChanged(ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
            initStatistik();
        }
    }

    //-------Refresh---------------------------------
    public final void reInit() {
        m_bInitialisiert = false;
    }

    public void refresh() {
    }

    private void initComponents() {
        JLabel label;

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(2, 0, 2, 0);

        setLayout(layout);

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
        m_jbPrint.setToolTipText(hov.getLanguageString("tt_Statistik_drucken"));
        m_jbPrint.setPreferredSize(new Dimension(25, 25));
        m_jbPrint.addActionListener(this);
        layout2.setConstraints(m_jbPrint, constraints2);
        panel2.add(m_jbPrint);

        label = new JLabel(hov.getLanguageString("Wochen"));
        constraints2.fill = GridBagConstraints.HORIZONTAL;
        constraints2.anchor = GridBagConstraints.WEST;
        constraints2.gridx = 0;
        constraints2.gridy = 1;
        constraints2.gridwidth = 1;
        layout2.setConstraints(label, constraints2);
        panel2.add(label);
        m_jtfNumberOfHRF.setHorizontalAlignment(SwingConstants.RIGHT);
        m_jtfNumberOfHRF.addFocusListener(this);
        constraints2.gridx = 1;
        constraints2.gridy = 1;
        layout2.setConstraints(m_jtfNumberOfHRF, constraints2);
        panel2.add(m_jtfNumberOfHRF);

        constraints2.gridx = 0;
        constraints2.gridy = 2;
        constraints2.gridwidth = 2;
        layout2.setConstraints(m_jbUbernehmen, constraints2);
        m_jbUbernehmen.setToolTipText(hov.getLanguageString("tt_Statistik_HRFAnzahluebernehmen"));
        m_jbUbernehmen.addActionListener(this);
        panel2.add(m_jbUbernehmen);

        label = new JLabel(hov.getLanguageString("Gruppe"));
        constraints2.gridx = 0;
        constraints2.gridy = 3;
        constraints2.gridwidth = 2;
        layout2.setConstraints(label, constraints2);
        panel2.add(label);
        constraints2.gridx = 0;
        constraints2.gridy = 4;
        m_jcbGruppe.setRenderer(new ho.core.gui.comp.renderer.SmilieListCellRenderer());
        m_jcbGruppe.setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));
        m_jcbGruppe.setMaximumRowCount(25);
        m_jcbGruppe.addItemListener(this);
        m_jcbGruppe.setMaximumSize(new Dimension(200, 25));
        layout2.setConstraints(m_jcbGruppe, constraints2);
        panel2.add(m_jcbGruppe);

        m_jchHelpLines.addActionListener(this);
        add(m_jchHelpLines,5);

        m_jchInscription.addActionListener(this);
        m_jchInscription.setOpaque(false);
        add(m_jchInscription,6);

        constraints2.weightx = 0.0;
        m_jchLeadership.addActionListener(this);
        add(m_jchLeadership,7);

        m_jchExperience.addActionListener(this);
        add(m_jchExperience,8);

        m_jchTSI.addActionListener(this);
        add(m_jchTSI, 9);

        m_jchWages.addActionListener(this);
        add(m_jchWages, 10);

        m_jchForm.addActionListener(this);
        add(m_jchForm, 11);

        m_jchStamina.addActionListener(this);
        add(m_jchStamina, 12);

        m_jchLoyalty.addActionListener(this);
        add(m_jchLoyalty, 13);

        m_jchKeeper.addActionListener(this);
        add(m_jchKeeper, 14);

        m_jchVerteidigung.addActionListener(this);
        add(m_jchVerteidigung, 15);

        m_jchPlaymaking.addActionListener(this);
        add(m_jchPlaymaking, 16);

        m_jchPassing.addActionListener(this);
        add(m_jchPassing, 17);

        m_jchWinger.addActionListener(this);
        add(m_jchWinger, 18);

        m_jchScoring.addActionListener(this);
        add(m_jchScoring, 19);

        m_jchSetPieces.addActionListener(this);
        add(m_jchSetPieces, 20);

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

    private void add(JComponent comp,int y){
    	constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = y;
        layout2.setConstraints(comp, constraints2);
        panel2.add(comp);
    }

    private void initStatistik() {
        try {
            int anzahlHRF = Integer.parseInt(m_jtfNumberOfHRF.getText());
            if (anzahlHRF <= 0) {
                anzahlHRF = 1;
            }
            gup.statistikAnzahlHRF = anzahlHRF;
            final java.text.NumberFormat format = ho.core.util.Helper.DEFAULTDEZIMALFORMAT;
            final java.text.NumberFormat format2 = NumberFormat.getCurrencyInstance();
            final double[][] statistikWerte = DBManager.instance().
            	getDurchschnittlicheSpielerDaten4Statistik(anzahlHRF, m_jcbGruppe.getSelectedItem().toString());
            final StatistikModel[] models = new StatistikModel[statistikWerte.length];

            //There are 14 values
            if (statistikWerte.length > 0) {
            	models[0] = new StatistikModel(statistikWerte[0], "ls.player.leadership",
                                               m_jchLeadership.isSelected(), leadershipColor, format);
                models[1] = new StatistikModel(statistikWerte[1], "DurchschnittErfahrung",
                                               m_jchExperience.isSelected(), experienceColor, format);
                models[2] = new StatistikModel(statistikWerte[2], "DurchschnittForm", m_jchForm.isSelected(),
                                               formColor, format);
                models[3] = new StatistikModel(statistikWerte[3], "ls.player.skill.stamina",
                                               m_jchStamina.isSelected(), staminaColor, format);
                models[4] = new StatistikModel(statistikWerte[4], "ls.player.skill.keeper",
                                               m_jchKeeper.isSelected(), keeperColor, format);
                models[5] = new StatistikModel(statistikWerte[5], "ls.player.skill.defending",
                                               m_jchVerteidigung.isSelected(), defendingColor, format);
                models[6] = new StatistikModel(statistikWerte[6], "ls.player.skill.playmaking",
                                               m_jchPlaymaking.isSelected(), playmakingColor, format);
                models[7] = new StatistikModel(statistikWerte[7], "ls.player.skill.passing",
                                               m_jchPassing.isSelected(), passingColor, format);
                models[8] = new StatistikModel(statistikWerte[8], "ls.player.skill.winger",
                                               m_jchWinger.isSelected(), wingerColor, format);
                models[9] = new StatistikModel(statistikWerte[9], "ls.player.skill.scoring",
                                               m_jchScoring.isSelected(), scoringColor, format);
                models[10] = new StatistikModel(statistikWerte[10], "ls.player.skill.setpieces",
                                                m_jchSetPieces.isSelected(), setPiecesColor, format);
                models[11] = new StatistikModel(statistikWerte[11], "ls.player.loyalty",
                        						m_jchLoyalty.isSelected(), loyaltyColor, format);
                double faktor = 20 / getMaxValue(statistikWerte[12]);
                models[12] = new StatistikModel(statistikWerte[12], "Marktwert",
						m_jchTSI.isSelected(), TSIColor, format, faktor);
                faktor = 20 / getMaxValue(statistikWerte[13]);
                models[13] = new StatistikModel(statistikWerte[13], "ls.player.wage",
						m_jchTSI.isSelected(), wageColor, format2, faktor);
            }

            final String[] yBezeichnungen = Helper.convertTimeMillisToFormatString(statistikWerte[14]);

            m_clStatistikPanel.setAllValues(models, yBezeichnungen, format,
                                            hov.getLanguageString("Wochen"),
                                            "", m_jchInscription.isSelected(),
                                            m_jchHelpLines.isSelected());
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"SpielerStatistikPanel.initStatistik : " + e);
        }
    }
    public final double getMaxValue(double[] werte) {
        double max = 0;
        for (int i = 0; (werte != null) && (i < werte.length); i++) {
            if (werte[i] > max) {
                max = werte[i];
            }
        }
        return (max);
    }
}
