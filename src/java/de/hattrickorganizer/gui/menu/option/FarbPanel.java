// %3889649867:de.hattrickorganizer.gui.menu.option%
package de.hattrickorganizer.gui.menu.option;

import de.hattrickorganizer.gui.templates.ImagePanel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Panel zum editieren der Farben
 */
final class FarbPanel extends ImagePanel implements ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbAngeschlagen;
    private JButton m_jbGesperrt;
    private JButton m_jbTransfermarkt;
    private JButton m_jbVerletzt;
    private JButton m_jbZweiKarten;
    private boolean m_bNeedRestart;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new FarbPanel object.
     */
    protected FarbPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    //---------------Listener-------------------------------------------    
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbAngeschlagen)) {
            Color color = JColorChooser.showDialog(this,
                                                   de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("Angeschlagen"),
                                                   gui.UserParameter.instance().FG_ANGESCHLAGEN);

            if (color != null) {
                gui.UserParameter.instance().FG_ANGESCHLAGEN = color;
                m_bNeedRestart = true;
                refresh();
            }
        } else if (actionEvent.getSource().equals(m_jbVerletzt)) {
            Color color = JColorChooser.showDialog(this,
                                                   de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("Verletzt"),
                                                   gui.UserParameter.instance().FG_VERLETZT);

            if (color != null) {
                gui.UserParameter.instance().FG_VERLETZT = color;
                m_bNeedRestart = true;
                refresh();
            }
        } else if (actionEvent.getSource().equals(m_jbZweiKarten)) {
            Color color = JColorChooser.showDialog(this,
                                                   de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("Verwarnt"),
                                                   gui.UserParameter.instance().FG_ZWEIKARTEN);

            if (color != null) {
                gui.UserParameter.instance().FG_ZWEIKARTEN = color;
                m_bNeedRestart = true;
                refresh();
            }
        } else if (actionEvent.getSource().equals(m_jbGesperrt)) {
            Color color = JColorChooser.showDialog(this,
                                                   de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("Gesperrt"),
                                                   gui.UserParameter.instance().FG_GESPERRT);

            if (color != null) {
                gui.UserParameter.instance().FG_GESPERRT = color;
                m_bNeedRestart = true;
                refresh();
            }
        } else if (actionEvent.getSource().equals(m_jbTransfermarkt)) {
            Color color = JColorChooser.showDialog(this,
                                                   de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("Transfermarkt"),
                                                   gui.UserParameter.instance().FG_TRANSFERMARKT);

            if (color != null) {
                gui.UserParameter.instance().FG_TRANSFERMARKT = color;
                m_bNeedRestart = true;
                refresh();
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean needRestart() {
        return m_bNeedRestart;
    }

    //---------------Hilfsmethoden--------------------------------------
    public final void refresh() {
        m_jbAngeschlagen.setBackground(gui.UserParameter.instance().FG_ANGESCHLAGEN);
        m_jbVerletzt.setBackground(gui.UserParameter.instance().FG_VERLETZT);
        m_jbZweiKarten.setBackground(gui.UserParameter.instance().FG_ZWEIKARTEN);
        m_jbGesperrt.setBackground(gui.UserParameter.instance().FG_GESPERRT);
        m_jbTransfermarkt.setBackground(gui.UserParameter.instance().FG_TRANSFERMARKT);
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(150, 4, 150, 4);

        setLayout(layout);

        //----Slider -----------
        final JPanel panel = new ImagePanel();
        panel.setLayout(new GridLayout(5, 2, 4, 10));
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        JLabel label = new JLabel("  "
                                  + de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                           .getProperty("Angeschlagen"));
        panel.add(label);

        m_jbAngeschlagen = new JButton();
        m_jbAngeschlagen.setBackground(gui.UserParameter.instance().FG_ANGESCHLAGEN);
        m_jbAngeschlagen.addActionListener(this);
        panel.add(m_jbAngeschlagen);

        label = new JLabel("  "
                           + de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                    .getProperty("Verletzt"));
        panel.add(label);

        m_jbVerletzt = new JButton();
        m_jbVerletzt.setBackground(gui.UserParameter.instance().FG_VERLETZT);
        m_jbVerletzt.addActionListener(this);
        panel.add(m_jbVerletzt);

        label = new JLabel("  "
                           + de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                    .getProperty("Verwarnt"));
        panel.add(label);

        m_jbZweiKarten = new JButton();
        m_jbZweiKarten.setBackground(gui.UserParameter.instance().FG_ZWEIKARTEN);
        m_jbZweiKarten.addActionListener(this);
        panel.add(m_jbZweiKarten);

        label = new JLabel("  "
                           + de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                    .getProperty("Gesperrt"));
        panel.add(label);

        m_jbGesperrt = new JButton();
        m_jbGesperrt.setBackground(gui.UserParameter.instance().FG_GESPERRT);
        m_jbGesperrt.addActionListener(this);
        panel.add(m_jbGesperrt);

        label = new JLabel("  "
                           + de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                    .getProperty("Transfermarkt"));
        panel.add(label);

        m_jbTransfermarkt = new JButton();
        m_jbTransfermarkt.setBackground(gui.UserParameter.instance().FG_TRANSFERMARKT);
        m_jbTransfermarkt.addActionListener(this);
        panel.add(m_jbTransfermarkt);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(panel, constraints);
        add(panel);

        refresh();
    }
}
