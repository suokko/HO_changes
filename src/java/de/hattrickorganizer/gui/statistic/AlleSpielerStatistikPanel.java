// %3661143817:de.hattrickorganizer.gui.statistic%
package de.hattrickorganizer.gui.statistic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.hattrickorganizer.gui.model.StatistikModel;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Das StatistikPanel
 */
public class AlleSpielerStatistikPanel extends de.hattrickorganizer.gui.templates.ImagePanel
    implements ActionListener, FocusListener, ItemListener, de.hattrickorganizer.gui.Refreshable
{
    //~ Static fields/initializers -----------------------------------------------------------------

    private static Color FUEHRUNG = Color.gray;
    private static Color ERFAHRUNG = Color.darkGray;
    private static Color FORM = Color.pink;
    private static Color KONDITION = Color.magenta;
    private static Color TORWART = Color.black;
    private static Color VERTEIDIGUNG = Color.blue;
    private static Color SPIELAUFBAU = Color.yellow;
    private static Color PASSPIEL = Color.green;
    private static Color FLUEGEL = Color.orange;
    private static Color TORSCHUSS = Color.red;
    private static Color STANDARDS = Color.cyan;

    //~ Instance fields ----------------------------------------------------------------------------

    private ImageCheckbox m_jchErfahrung = new ImageCheckbox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                    .getResource()
                                                                                                    .getProperty("Erfahrung"),
                                                             de.hattrickorganizer.tools.Helper
                                                             .getImageIcon4Color(ERFAHRUNG),
                                                             gui.UserParameter.instance().statistikAlleErfahrung);
    private ImageCheckbox m_jchFluegel = new ImageCheckbox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                  .getResource()
                                                                                                  .getProperty("Fluegelspiel"),
                                                           de.hattrickorganizer.tools.Helper
                                                           .getImageIcon4Color(FLUEGEL),
                                                           gui.UserParameter.instance().statistikAlleFluegel);
    private ImageCheckbox m_jchForm = new ImageCheckbox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                               .getResource()
                                                                                               .getProperty("Form"),
                                                        de.hattrickorganizer.tools.Helper
                                                        .getImageIcon4Color(FORM),
                                                        gui.UserParameter.instance().statistikAlleForm);
    private ImageCheckbox m_jchFuehrung = new ImageCheckbox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                   .getResource()
                                                                                                   .getProperty("Fuehrung"),
                                                            de.hattrickorganizer.tools.Helper
                                                            .getImageIcon4Color(FUEHRUNG),
                                                            gui.UserParameter.instance().statistikAlleFuehrung);
    private ImageCheckbox m_jchKondition = new ImageCheckbox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                    .getResource()
                                                                                                    .getProperty("Kondition"),
                                                             de.hattrickorganizer.tools.Helper
                                                             .getImageIcon4Color(KONDITION),
                                                             gui.UserParameter.instance().statistikAlleKondition);
    private ImageCheckbox m_jchPasspiel = new ImageCheckbox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                   .getResource()
                                                                                                   .getProperty("Passpiel"),
                                                            de.hattrickorganizer.tools.Helper
                                                            .getImageIcon4Color(PASSPIEL),
                                                            gui.UserParameter.instance().statistikAllePasspiel);
    private ImageCheckbox m_jchSpielaufbau = new ImageCheckbox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                      .getResource()
                                                                                                      .getProperty("Spielaufbau"),
                                                               de.hattrickorganizer.tools.Helper
                                                               .getImageIcon4Color(SPIELAUFBAU),
                                                               gui.UserParameter.instance().statistikAlleSpielaufbau);
    private ImageCheckbox m_jchStandards = new ImageCheckbox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                    .getResource()
                                                                                                    .getProperty("Standards"),
                                                             de.hattrickorganizer.tools.Helper
                                                             .getImageIcon4Color(STANDARDS),
                                                             gui.UserParameter.instance().statistikAlleStandards);
    private ImageCheckbox m_jchTorschuss = new ImageCheckbox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                    .getResource()
                                                                                                    .getProperty("Torschuss"),
                                                             de.hattrickorganizer.tools.Helper
                                                             .getImageIcon4Color(TORSCHUSS),
                                                             gui.UserParameter.instance().statistikAlleTorschuss);
    private ImageCheckbox m_jchTorwart = new ImageCheckbox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                  .getResource()
                                                                                                  .getProperty("Torwart"),
                                                           de.hattrickorganizer.tools.Helper
                                                           .getImageIcon4Color(TORWART),
                                                           gui.UserParameter.instance().statistikAlleTorwart);
    private ImageCheckbox m_jchVerteidigung = new ImageCheckbox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                       .getResource()
                                                                                                       .getProperty("Verteidigung"),
                                                                de.hattrickorganizer.tools.Helper
                                                                .getImageIcon4Color(VERTEIDIGUNG),
                                                                gui.UserParameter.instance().statistikAlleVerteidigung);
    private JButton m_jbDrucken = new JButton(new ImageIcon(de.hattrickorganizer.tools.Helper
                                                            .loadImage("gui/bilder/Drucken.png")));
    private JButton m_jbUbernehmen = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                        .getResource()
                                                                                        .getProperty("Uebernehmen"));
    private JCheckBox m_jchBeschriftung = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                               .getResource()
                                                                                               .getProperty("Beschriftung"),
                                                        gui.UserParameter.instance().statistikAlleBeschriftung);
    private JCheckBox m_jchHilflinien = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                             .getResource()
                                                                                             .getProperty("Hilflinien"),
                                                      gui.UserParameter.instance().statistikAlleHilfslinien);
    private JComboBox m_jcbGruppe = new JComboBox(de.hattrickorganizer.tools.Helper.TEAMSMILIES);
    private JTextField m_jtfAnzahlHRF = new JTextField(gui.UserParameter.instance().statistikAnzahlHRF
                                                       + "", 5);
    private StatistikPanel m_clStatistikPanel;
    private boolean m_bInitialisiert;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AlleSpielerStatistikPanel object.
     */
    public AlleSpielerStatistikPanel() {
        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(this);

        initComponents();

        //initStatistik();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param init TODO Missing Method Parameter Documentation
     */
    public final void setInitialisiert(boolean init) {
        m_bInitialisiert = init;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isInitialisiert() {
        return m_bInitialisiert;
    }

    //--------Listener-------------------------------    
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbUbernehmen)) {
            initStatistik();
        } else if (actionEvent.getSource().equals(m_jbDrucken)) {
            m_clStatistikPanel.doPrint(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                              .getResource()
                                                                              .getProperty("Verein"));
        } else if (actionEvent.getSource().equals(m_jchHilflinien)) {
            m_clStatistikPanel.setHilfslinien(m_jchHilflinien.isSelected());
            gui.UserParameter.instance().statistikAlleHilfslinien = m_jchHilflinien.isSelected();
        } else if (actionEvent.getSource().equals(m_jchBeschriftung)) {
            m_clStatistikPanel.setBeschriftung(m_jchBeschriftung.isSelected());
            gui.UserParameter.instance().statistikAlleBeschriftung = m_jchBeschriftung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchFuehrung.getCheckbox())) {
            m_clStatistikPanel.setShow("Fuehrung", m_jchFuehrung.isSelected());
            gui.UserParameter.instance().statistikAlleFuehrung = m_jchFuehrung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchErfahrung.getCheckbox())) {
            m_clStatistikPanel.setShow("Erfahrung", m_jchErfahrung.isSelected());
            gui.UserParameter.instance().statistikAlleErfahrung = m_jchErfahrung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchForm.getCheckbox())) {
            m_clStatistikPanel.setShow("Form", m_jchForm.isSelected());
            gui.UserParameter.instance().statistikAlleForm = m_jchForm.isSelected();
        } else if (actionEvent.getSource().equals(m_jchKondition.getCheckbox())) {
            m_clStatistikPanel.setShow("Kondition", m_jchKondition.isSelected());
            gui.UserParameter.instance().statistikAlleKondition = m_jchKondition.isSelected();
        } else if (actionEvent.getSource().equals(m_jchTorwart.getCheckbox())) {
            m_clStatistikPanel.setShow("Torwart", m_jchTorwart.isSelected());
            gui.UserParameter.instance().statistikAlleTorwart = m_jchTorwart.isSelected();
        } else if (actionEvent.getSource().equals(m_jchVerteidigung.getCheckbox())) {
            m_clStatistikPanel.setShow("Verteidigung", m_jchVerteidigung.isSelected());
            gui.UserParameter.instance().statistikAlleVerteidigung = m_jchVerteidigung.isSelected();
        } else if (actionEvent.getSource().equals(m_jchSpielaufbau.getCheckbox())) {
            m_clStatistikPanel.setShow("Spielaufbau", m_jchSpielaufbau.isSelected());
            gui.UserParameter.instance().statistikAlleSpielaufbau = m_jchSpielaufbau.isSelected();
        } else if (actionEvent.getSource().equals(m_jchPasspiel.getCheckbox())) {
            m_clStatistikPanel.setShow("Passpiel", m_jchPasspiel.isSelected());
            gui.UserParameter.instance().statistikAllePasspiel = m_jchPasspiel.isSelected();
        } else if (actionEvent.getSource().equals(m_jchFluegel.getCheckbox())) {
            m_clStatistikPanel.setShow("Fluegelspiel", m_jchFluegel.isSelected());
            gui.UserParameter.instance().statistikAlleFluegel = m_jchFluegel.isSelected();
        } else if (actionEvent.getSource().equals(m_jchTorschuss.getCheckbox())) {
            m_clStatistikPanel.setShow("Torschuss", m_jchTorschuss.isSelected());
            gui.UserParameter.instance().statistikAlleTorschuss = m_jchTorschuss.isSelected();
        } else if (actionEvent.getSource().equals(m_jchStandards.getCheckbox())) {
            m_clStatistikPanel.setShow("Standards", m_jchStandards.isSelected());
            gui.UserParameter.instance().statistikAlleStandards = m_jchStandards.isSelected();
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void doInitialisieren() {
        initStatistik();
        m_bInitialisiert = true;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param focusEvent TODO Missing Method Parameter Documentation
     */
    public void focusGained(java.awt.event.FocusEvent focusEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param focusEvent TODO Missing Method Parameter Documentation
     */
    public final void focusLost(java.awt.event.FocusEvent focusEvent) {
        de.hattrickorganizer.tools.Helper.parseInt(de.hattrickorganizer.gui.HOMainFrame.instance(),
                                                   ((JTextField) focusEvent.getSource()), false);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param itemEvent TODO Missing Method Parameter Documentation
     */
    public final void itemStateChanged(java.awt.event.ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            initStatistik();
        }
    }

    //-------Refresh---------------------------------    
    public final void reInit() {
        m_bInitialisiert = false;

        //initStatistik();
    }

    /**
     * TODO Missing Method Documentation
     */
    public void refresh() {
    }

    /**
     * TODO Missing Method Documentation
     */
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
        m_jbDrucken.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                          .getProperty("tt_Statistik_drucken"));
        m_jbDrucken.setPreferredSize(new Dimension(25, 25));
        m_jbDrucken.addActionListener(this);
        layout2.setConstraints(m_jbDrucken, constraints2);
        panel2.add(m_jbDrucken);

        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("Wochen"));
        constraints2.fill = GridBagConstraints.HORIZONTAL;
        constraints2.anchor = GridBagConstraints.WEST;
        constraints2.gridx = 0;
        constraints2.gridy = 1;
        constraints2.gridwidth = 1;
        layout2.setConstraints(label, constraints2);
        panel2.add(label);
        m_jtfAnzahlHRF.setHorizontalAlignment(JTextField.RIGHT);
        m_jtfAnzahlHRF.addFocusListener(this);
        constraints2.gridx = 1;
        constraints2.gridy = 1;
        layout2.setConstraints(m_jtfAnzahlHRF, constraints2);
        panel2.add(m_jtfAnzahlHRF);

        constraints2.gridx = 0;
        constraints2.gridy = 2;
        constraints2.gridwidth = 2;
        layout2.setConstraints(m_jbUbernehmen, constraints2);
        m_jbUbernehmen.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                             .getResource()
                                                                             .getProperty("tt_Statistik_HRFAnzahluebernehmen"));
        m_jbUbernehmen.addActionListener(this);
        panel2.add(m_jbUbernehmen);

        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                  .getProperty("Gruppe"));
        constraints2.gridx = 0;
        constraints2.gridy = 3;
        constraints2.gridwidth = 2;
        layout2.setConstraints(label, constraints2);
        panel2.add(label);
        constraints2.gridx = 0;
        constraints2.gridy = 4;
        m_jcbGruppe.setRenderer(new de.hattrickorganizer.gui.model.SmilieRenderer());
        m_jcbGruppe.setBackground(Color.white);
        m_jcbGruppe.setMaximumRowCount(25);
        m_jcbGruppe.addItemListener(this);
        m_jcbGruppe.setMaximumSize(new Dimension(200, 25));
        layout2.setConstraints(m_jcbGruppe, constraints2);
        panel2.add(m_jcbGruppe);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 5;
        m_jchHilflinien.setOpaque(false);
        m_jchHilflinien.setBackground(Color.white);
        m_jchHilflinien.addActionListener(this);
        layout2.setConstraints(m_jchHilflinien, constraints2);
        panel2.add(m_jchHilflinien);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 6;
        m_jchBeschriftung.setOpaque(false);
        m_jchBeschriftung.setBackground(Color.white);
        m_jchBeschriftung.addActionListener(this);
        layout2.setConstraints(m_jchBeschriftung, constraints2);
        panel2.add(m_jchBeschriftung);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 7;
        constraints2.weightx = 0.0;
        m_jchFuehrung.setOpaque(false);
        m_jchFuehrung.addActionListener(this);
        layout2.setConstraints(m_jchFuehrung, constraints2);
        panel2.add(m_jchFuehrung);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 8;
        m_jchErfahrung.setOpaque(false);
        m_jchErfahrung.addActionListener(this);
        layout2.setConstraints(m_jchErfahrung, constraints2);
        panel2.add(m_jchErfahrung);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 9;
        m_jchForm.setOpaque(false);
        m_jchForm.addActionListener(this);
        layout2.setConstraints(m_jchForm, constraints2);
        panel2.add(m_jchForm);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 10;
        m_jchKondition.setOpaque(false);
        m_jchKondition.addActionListener(this);
        layout2.setConstraints(m_jchKondition, constraints2);
        panel2.add(m_jchKondition);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 11;
        m_jchTorwart.setOpaque(false);
        m_jchTorwart.addActionListener(this);
        layout2.setConstraints(m_jchTorwart, constraints2);
        panel2.add(m_jchTorwart);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 12;
        m_jchVerteidigung.setOpaque(false);
        m_jchVerteidigung.addActionListener(this);
        layout2.setConstraints(m_jchVerteidigung, constraints2);
        panel2.add(m_jchVerteidigung);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 13;
        m_jchSpielaufbau.setOpaque(false);
        m_jchSpielaufbau.addActionListener(this);
        layout2.setConstraints(m_jchSpielaufbau, constraints2);
        panel2.add(m_jchSpielaufbau);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 14;
        m_jchPasspiel.setOpaque(false);
        m_jchPasspiel.addActionListener(this);
        layout2.setConstraints(m_jchPasspiel, constraints2);
        panel2.add(m_jchPasspiel);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 15;
        m_jchFluegel.setOpaque(false);
        m_jchFluegel.addActionListener(this);
        layout2.setConstraints(m_jchFluegel, constraints2);
        panel2.add(m_jchFluegel);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 16;
        m_jchTorschuss.setOpaque(false);
        m_jchTorschuss.addActionListener(this);
        layout2.setConstraints(m_jchTorschuss, constraints2);
        panel2.add(m_jchTorschuss);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 17;
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
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        layout.setConstraints(panel, constraints);
        add(panel);
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initStatistik() {
        try {
            int anzahlHRF = Integer.parseInt(m_jtfAnzahlHRF.getText());

            if (anzahlHRF <= 0) {
                anzahlHRF = 1;
            }

            gui.UserParameter.instance().statistikAnzahlHRF = anzahlHRF;

            final java.text.NumberFormat format = de.hattrickorganizer.tools.Helper.DEZIMALFORMAT_3STELLEN;

            final double[][] statistikWerte = de.hattrickorganizer.database.DBZugriff.instance()
                                                                                     .getDurchschnittlicheSpielerDaten4Statistik(anzahlHRF,
                                                                                                                                 m_jcbGruppe.getSelectedItem()
                                                                                                                                            .toString());
            final StatistikModel[] models = new StatistikModel[statistikWerte.length];

            //Es sind 12 Werte!
            if (statistikWerte.length > 0) {
                models[0] = new StatistikModel(statistikWerte[0], "Fuehrung",
                                               m_jchFuehrung.isSelected(), FUEHRUNG, format);
                models[1] = new StatistikModel(statistikWerte[1], "Erfahrung",
                                               m_jchErfahrung.isSelected(), ERFAHRUNG, format);
                models[2] = new StatistikModel(statistikWerte[2], "Form", m_jchForm.isSelected(),
                                               FORM, format);
                models[3] = new StatistikModel(statistikWerte[3], "Kondition",
                                               m_jchKondition.isSelected(), KONDITION, format);
                models[4] = new StatistikModel(statistikWerte[4], "Torwart",
                                               m_jchTorwart.isSelected(), TORWART, format);
                models[5] = new StatistikModel(statistikWerte[5], "Verteidigung",
                                               m_jchVerteidigung.isSelected(), VERTEIDIGUNG, format);
                models[6] = new StatistikModel(statistikWerte[6], "Spielaufbau",
                                               m_jchSpielaufbau.isSelected(), SPIELAUFBAU, format);
                models[7] = new StatistikModel(statistikWerte[7], "Passpiel",
                                               m_jchPasspiel.isSelected(), PASSPIEL, format);
                models[8] = new StatistikModel(statistikWerte[8], "Fluegelspiel",
                                               m_jchFluegel.isSelected(), FLUEGEL, format);
                models[9] = new StatistikModel(statistikWerte[9], "Torschuss",
                                               m_jchTorschuss.isSelected(), TORSCHUSS, format);
                models[10] = new StatistikModel(statistikWerte[10], "Standards",
                                                m_jchStandards.isSelected(), STANDARDS, format);
            }

            final String[] yBezeichnungen = de.hattrickorganizer.tools.Helper
                                            .convertTimeMillisToFormatString(statistikWerte[11]);

            m_clStatistikPanel.setAllValues(models, yBezeichnungen, format,
                                            de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("Wochen"),
                                            "", m_jchBeschriftung.isSelected(),
                                            m_jchHilflinien.isSelected());
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"SpielerStatistikPanel.initStatistik : " + e);
        }
    }
}
