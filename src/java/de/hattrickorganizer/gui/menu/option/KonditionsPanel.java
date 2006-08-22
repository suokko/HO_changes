// %3828862549:de.hattrickorganizer.gui.menu.option%
package de.hattrickorganizer.gui.menu.option;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.FormulaFactors;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;


/**
 * Panel zum editieren der Formelwerte der Spielerstärkenberechnung
 */
final class KonditionsPanel extends ImagePanel implements ActionListener, ItemListener,
                                                          ChangeListener
{
    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbResetToDefaults;
    private SliderPanel m_jpFluegelspiel;
    private SliderPanel m_jpPasspiel;
    private SliderPanel m_jpSpielaufbau;
    private SliderPanel m_jpTorschuss;
    private SliderPanel m_jpTorwart;
    private SliderPanel m_jpVerteidigung;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new KonditionsPanel object.
     */
    protected KonditionsPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    //---------------Listener-------------------------------------------    
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        final int value = JOptionPane.showConfirmDialog(this,
                                                        de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                               .getResource()
                                                                                               .getProperty("FrageFormelwertReset"),
                                                        de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                               .getResource()
                                                                                               .getProperty("FormelwertReset"),
                                                        JOptionPane.YES_NO_OPTION);

        if (value == JOptionPane.YES_OPTION) {
            //Alle Werte reseten
            FormulaFactors.instance().initKondition();

            //Alle anderen Werte in GUI setzen
            refresh();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param itemEvent TODO Missing Method Parameter Documentation
     */
    public final void itemStateChanged(java.awt.event.ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            refresh();
        }
    }

    //---------------Hilfsmethoden--------------------------------------
    public final void refresh() {
        final FormulaFactors factorObject = FormulaFactors.instance();

        m_jpSpielaufbau.removeChangeListener(this);
        m_jpFluegelspiel.removeChangeListener(this);
        m_jpTorschuss.removeChangeListener(this);
        m_jpTorwart.removeChangeListener(this);
        m_jpPasspiel.removeChangeListener(this);
        m_jpVerteidigung.removeChangeListener(this);

        m_jpSpielaufbau.setValue(factorObject.m_fSP_Kondi_Faktor);
        m_jpFluegelspiel.setValue(factorObject.m_fFL_Kondi_Faktor);
        m_jpTorschuss.setValue(factorObject.m_fTS_Kondi_Faktor);
        m_jpTorwart.setValue(factorObject.m_fTW_Kondi_Faktor);
        m_jpPasspiel.setValue(factorObject.m_fPS_Kondi_Faktor);
        m_jpVerteidigung.setValue(factorObject.m_fVE_Kondi_Faktor);

        m_jpSpielaufbau.addChangeListener(this);
        m_jpFluegelspiel.addChangeListener(this);
        m_jpTorschuss.addChangeListener(this);
        m_jpTorwart.addChangeListener(this);
        m_jpPasspiel.addChangeListener(this);
        m_jpVerteidigung.addChangeListener(this);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param changeEvent TODO Missing Method Parameter Documentation
     */
    public final void stateChanged(javax.swing.event.ChangeEvent changeEvent) {
        //saven der aktuellen Einstellungen
        FormulaFactors.instance().m_fFL_Kondi_Faktor = m_jpFluegelspiel.getValue();
        FormulaFactors.instance().m_fSP_Kondi_Faktor = m_jpSpielaufbau.getValue();
        FormulaFactors.instance().m_fVE_Kondi_Faktor = m_jpVerteidigung.getValue();
        FormulaFactors.instance().m_fTW_Kondi_Faktor = m_jpTorwart.getValue();
        FormulaFactors.instance().m_fTS_Kondi_Faktor = m_jpTorschuss.getValue();
        FormulaFactors.instance().m_fPS_Kondi_Faktor = m_jpPasspiel.getValue();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        setLayout(layout);

        //----Slider -----------
        final JPanel panel = new ImagePanel();
        panel.setLayout(new GridLayout(6, 1, 4, 4));
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        m_jpSpielaufbau = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                 .getResource()
                                                                                 .getProperty("Spielaufbau"),
                                          100, 0, 100, 1.0f, 80);
        m_jpSpielaufbau.addChangeListener(this);
        panel.add(m_jpSpielaufbau);

        m_jpFluegelspiel = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                  .getResource()
                                                                                  .getProperty("Fluegelspiel"),
                                           100, 0, 100, 1.0f, 80);
        m_jpFluegelspiel.addChangeListener(this);
        panel.add(m_jpFluegelspiel);

        m_jpTorschuss = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                               .getResource()
                                                                               .getProperty("Torschuss"),
                                        100, 0, 100, 1.0f, 80);
        m_jpTorschuss.addChangeListener(this);
        panel.add(m_jpTorschuss);

        m_jpTorwart = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                             .getResource()
                                                                             .getProperty("Torwart"),
                                      100, 0, 100, 1.0f, 80);
        m_jpTorwart.addChangeListener(this);
        panel.add(m_jpTorwart);

        m_jpPasspiel = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                              .getResource()
                                                                              .getProperty("Passpiel"),
                                       100, 0, 100, 1.0f, 80);
        m_jpPasspiel.addChangeListener(this);
        panel.add(m_jpPasspiel);

        m_jpVerteidigung = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                  .getResource()
                                                                                  .getProperty("Verteidigung"),
                                           100, 0, 100, 1.0f, 80);
        m_jpVerteidigung.addChangeListener(this);
        panel.add(m_jpVerteidigung);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(panel, constraints);
        add(panel);

        m_jbResetToDefaults = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                 .getResource()
                                                                                 .getProperty("FormelwertReset"));
        m_jbResetToDefaults.addActionListener(this);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        layout.setConstraints(m_jbResetToDefaults, constraints);
        add(m_jbResetToDefaults);

        refresh();
    }
}
