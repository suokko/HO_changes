// %1136886551:de.hattrickorganizer.gui.statistic%
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
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.tools.HOLogger;


/**
 * Das StatistikPanel  #### Wird nicht mehr benutzt###
 */
public class SpielerFinanzenStatistikPanel extends de.hattrickorganizer.gui.templates.ImagePanel
    implements ActionListener, FocusListener, de.hattrickorganizer.gui.Refreshable, ItemListener
{
    //~ Static fields/initializers -----------------------------------------------------------------

    private static Color MARKTWERT = Color.blue;
    private static Color GEHALT = Color.red;

    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbDrucken = new JButton(new ImageIcon(de.hattrickorganizer.tools.Helper
                                                            .loadImage("gui/bilder/Drucken.png")));
    private JButton m_jbUbernehmen = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Uebernehmen"));
    private JCheckBox m_jchBeschriftung = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Beschriftung"),
                                                        gui.UserParameter.instance().statistikSpielerFinanzenBeschriftung);
    private JCheckBox m_jchGehalt = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Gehalt"),
                                                  gui.UserParameter.instance().statistikSpielerFinanzenGehalt);
    private JCheckBox m_jchHilflinien = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Hilflinien"),
                                                      gui.UserParameter.instance().statistikSpielerFinanzenHilfslinien);
    private JCheckBox m_jchMarktwert = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Marktwert"),
                                                     gui.UserParameter.instance().statistikSpielerFinanzenMarktwert);
    private JComboBox m_jcbSpieler = new JComboBox();
    private JTextField m_jtfAnzahlHRF = new JTextField(gui.UserParameter.instance().statistikSpielerFinanzenAnzahlHRF
                                                       + "");
    private StatistikPanel m_clStatistikPanel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerFinanzenStatistikPanel object.
     */
    public SpielerFinanzenStatistikPanel() {
        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(this);

        initSpielerCB();

        initComponents();

        initStatistik();
    }

    //~ Methods ------------------------------------------------------------------------------------

    //--------Listener-------------------------------    
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbUbernehmen)) {
            initStatistik();
        } else if (actionEvent.getSource().equals(m_jbDrucken)) {
            m_clStatistikPanel.doPrint(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Spieler")
                                       + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Finanzen"));
        } else if (actionEvent.getSource().equals(m_jchHilflinien)) {
            m_clStatistikPanel.setHilfslinien(m_jchHilflinien.isSelected());
            gui.UserParameter.instance().statistikSpielerFinanzenHilfslinien = m_jchHilflinien
                                                                               .isSelected();
        } else if (actionEvent.getSource().equals(m_jchBeschriftung)) {
            m_clStatistikPanel.setBeschriftung(m_jchBeschriftung.isSelected());
            gui.UserParameter.instance().statistikSpielerFinanzenBeschriftung = m_jchBeschriftung
                                                                                .isSelected();
        } else if (actionEvent.getSource().equals(m_jchMarktwert)) {
            m_clStatistikPanel.setShow("Marktwert", m_jchMarktwert.isSelected());
            gui.UserParameter.instance().statistikSpielerFinanzenMarktwert = m_jchMarktwert
                                                                             .isSelected();
        } else if (actionEvent.getSource().equals(m_jchGehalt)) {
            m_clStatistikPanel.setShow("Gehalt", m_jchGehalt.isSelected());
            gui.UserParameter.instance().statistikSpielerFinanzenGehalt = m_jchGehalt.isSelected();
        }
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
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            initStatistik();
        }
    }

    //-------Refresh---------------------------------    
    public final void reInit() {
        initSpielerCB();

        initStatistik();
    }

    /**
     * TODO Missing Method Documentation
     */
    public void refresh() {
        //initSpielerCB();
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
        m_jbDrucken.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_Statistik_drucken"));
        m_jbDrucken.setPreferredSize(new Dimension(25, 25));
        m_jbDrucken.addActionListener(this);
        layout2.setConstraints(m_jbDrucken, constraints2);
        panel2.add(m_jbDrucken);

        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("AnzahlHRF"));
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
        m_jbUbernehmen.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_Statistik_HRFAnzahluebernehmen"));
        layout2.setConstraints(m_jbUbernehmen, constraints2);
        m_jbUbernehmen.addActionListener(this);
        panel2.add(m_jbUbernehmen);

        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Spieler"));
        constraints2.gridx = 0;
        constraints2.gridy = 3;
        constraints2.gridwidth = 2;
        layout2.setConstraints(label, constraints2);
        panel2.add(label);
        constraints2.gridx = 0;
        constraints2.gridy = 4;
        m_jcbSpieler.setRenderer(new de.hattrickorganizer.gui.model.SpielerCBItemRenderer());
        m_jcbSpieler.setBackground(Color.white);
        m_jcbSpieler.setMaximumRowCount(25);
        m_jcbSpieler.addItemListener(this);
        m_jcbSpieler.setMaximumSize(new Dimension(200, 25));
        layout2.setConstraints(m_jcbSpieler, constraints2);
        panel2.add(m_jcbSpieler);

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
        m_jchMarktwert.setOpaque(false);
        m_jchMarktwert.setFont(m_jchMarktwert.getFont().deriveFont(java.awt.Font.BOLD));
        m_jchMarktwert.setBackground(Color.white);
        m_jchMarktwert.setForeground(MARKTWERT);
        m_jchMarktwert.addActionListener(this);
        layout2.setConstraints(m_jchMarktwert, constraints2);
        panel2.add(m_jchMarktwert);

        constraints2.gridwidth = 2;
        constraints2.gridx = 0;
        constraints2.gridy = 8;
        m_jchGehalt.setOpaque(false);
        m_jchGehalt.setFont(m_jchGehalt.getFont().deriveFont(java.awt.Font.BOLD));
        m_jchGehalt.setBackground(Color.white);
        m_jchGehalt.setForeground(GEHALT);
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

        m_clStatistikPanel = new StatistikPanel(true);
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
    private void initSpielerCB() {
        final java.util.Vector spieler = de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                .getModel()
                                                                                .getAllSpieler();
        final de.hattrickorganizer.gui.model.SpielerCBItem[] spielerCBItems = new de.hattrickorganizer.gui.model.SpielerCBItem[spieler
                                                                                                                               .size()];

        for (int i = 0; i < spieler.size(); i++) {
            spielerCBItems[i] = new de.hattrickorganizer.gui.model.SpielerCBItem(((Spieler) spieler
                                                                                  .get(i)).getName(),
                                                                                 0f,
                                                                                 (Spieler) spieler
                                                                                 .get(i));
        }

        java.util.Arrays.sort(spielerCBItems);

        //Alte Spieler
        final java.util.Vector allSpieler = de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getModel()
                                                                                   .getAllOldSpieler();
        final de.hattrickorganizer.gui.model.SpielerCBItem[] spielerAllCBItems = new de.hattrickorganizer.gui.model.SpielerCBItem[allSpieler
                                                                                                                                  .size()];

        for (int i = 0; i < allSpieler.size(); i++) {
            spielerAllCBItems[i] = new de.hattrickorganizer.gui.model.SpielerCBItem(((Spieler) allSpieler
                                                                                     .get(i))
                                                                                    .getName(), 0f,
                                                                                    (Spieler) allSpieler
                                                                                    .get(i));
        }

        java.util.Arrays.sort(spielerAllCBItems);

        //Zusammenfügen
        final de.hattrickorganizer.gui.model.SpielerCBItem[] cbItems = new de.hattrickorganizer.gui.model.SpielerCBItem[spielerCBItems.length
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

        final javax.swing.DefaultComboBoxModel cbModel = new javax.swing.DefaultComboBoxModel(cbItems);

        m_jcbSpieler.setModel(cbModel);
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

            gui.UserParameter.instance().statistikSpielerFinanzenAnzahlHRF = anzahlHRF;

            final java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance();

            if (m_jcbSpieler.getSelectedItem() != null) {
                final double[][] statistikWerte = de.hattrickorganizer.database.DBZugriff.instance()
                                                                                         .getSpielerFinanzDaten4Statistik(((de.hattrickorganizer.gui.model.SpielerCBItem) m_jcbSpieler
                                                                                                                           .getSelectedItem()).getSpieler()
                                                                                                                           .getSpielerID(),
                                                                                                                          anzahlHRF);
                final StatistikModel[] models = new StatistikModel[statistikWerte.length];

                //Es sind 3 Werte!
                if (statistikWerte.length > 0) {
                    models[0] = new StatistikModel(statistikWerte[0], "Marktwert",
                                                   m_jchMarktwert.isSelected(), MARKTWERT, format);
                    models[1] = new StatistikModel(statistikWerte[1], "Gehalt",
                                                   m_jchGehalt.isSelected(), GEHALT, format);
                }

                final String[] yBezeichnungen = de.hattrickorganizer.tools.Helper
                                                .convertTimeMillisToFormatString(statistikWerte[2]);

                m_clStatistikPanel.setAllValues(models, yBezeichnungen, format,
                                                de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Wochen"),
                                                "", m_jchBeschriftung.isSelected(),
                                                m_jchHilflinien.isSelected());
            }
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"SpielerStatistikPanel.initStatistik : " + e);
        }

        //Test
        //double[] werte = { 1d, 2d, 1.5d, 3d, 2.5d };
        //StatistikModel[] model   = { new StatistikModel( werte, "Fuehrung", true, FUEHRUNG ) };
        //m_clStatistikPanel.setModel ( model );
    }
}
