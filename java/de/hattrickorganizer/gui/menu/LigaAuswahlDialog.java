// %2333829392:de.hattrickorganizer.gui.menu%
package de.hattrickorganizer.gui.menu;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import de.hattrickorganizer.gui.templates.ImagePanel;


/**
 * Auswahl der Liga zu einer Season, um den richtigen Spielplan zu ziehen
 */
public class LigaAuswahlDialog extends JDialog implements ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbAbbrechen = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                       .getResource()
                                                                                       .getProperty("Abbrechen"));
    private JButton m_jbOk = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                .getResource()
                                                                                .getProperty("Download"));
    private JComboBox m_jcbLiga;
    private JRadioButton m_jrbLigaAktuell = new JRadioButton(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                    .getResource()
                                                                                                    .getProperty("AktuelleLiga"),
                                                             true);
    private JRadioButton m_jrbLigaAndere = new JRadioButton(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                   .getResource()
                                                                                                   .getProperty("AndereLiga"),
                                                            false);
    private int m_iLigaId = -2;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new LigaAuswahlDialog object.
     *
     * @param owner TODO Missing Constructuor Parameter Documentation
     * @param seasonid TODO Missing Constructuor Parameter Documentation
     */
    public LigaAuswahlDialog(JDialog owner, int seasonid) {
        super(owner,
              de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Liga"),
              true);

        initComponents(seasonid);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gibt die Ligaid zurück oder -1 für aktulleLigaid oder -2 für abbruch
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getLigaID() {
        return m_iLigaId;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(m_jbOk)) {
            //Ist es eine Zahl
            if (m_jrbLigaAktuell.isSelected()) {
                //Aktuelle Liga benutzen
                m_iLigaId = -1;
                setVisible(false);
            }
            //Andere Liga
            else if (m_jcbLiga.getSelectedItem() != null) {
                m_iLigaId = parseInt(this, m_jcbLiga.getSelectedItem().toString(), false);

                if (m_iLigaId > -1) {
                    setVisible(false);
                }
            }
        } else if (e.getSource().equals(m_jbAbbrechen)) {
            setVisible(false);
        } else if ((e.getSource().equals(m_jrbLigaAktuell))
                   || (e.getSource().equals(m_jrbLigaAndere))) {
            if (m_jrbLigaAndere.isSelected()) {
                m_jcbLiga.setEnabled(true);
            } else {
                m_jcbLiga.setEnabled(false);
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Integer[] fillCB() {
        //Alle möglichen LigaIDs holen
        return de.hattrickorganizer.database.DBZugriff.instance().getAllLigaIDs();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param seasonid TODO Missing Method Parameter Documentation
     */
    private void initComponents(int seasonid) {
        setContentPane(new ImagePanel(new GridLayout(4, 2, 4, 4)));

        JLabel label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                         .getProperty("Season"));
        getContentPane().add(label);

        final JTextField textfield = new JTextField(seasonid + "");
        textfield.setEditable(false);
        getContentPane().add(textfield);

        final ButtonGroup bg = new ButtonGroup();

        m_jrbLigaAktuell.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                               .getResource()
                                                                               .getProperty("tt_LigaDownload_Aktuell"));
        m_jrbLigaAktuell.setOpaque(false);
        m_jrbLigaAktuell.addActionListener(this);
        bg.add(m_jrbLigaAktuell);
        getContentPane().add(m_jrbLigaAktuell);

        //Platzhalter
        label = new JLabel();
        getContentPane().add(label);

        m_jrbLigaAndere.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                              .getResource()
                                                                              .getProperty("tt_LigaDownload_Andere"));
        m_jrbLigaAndere.setOpaque(false);
        m_jrbLigaAndere.addActionListener(this);
        bg.add(m_jrbLigaAndere);
        getContentPane().add(m_jrbLigaAndere);

        m_jcbLiga = new JComboBox(fillCB());
        m_jcbLiga.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                        .getProperty("tt_LigaDownload_LigaID"));
        m_jcbLiga.setEnabled(false);
        m_jcbLiga.setSelectedItem(new Integer(de.hattrickorganizer.database.DBZugriff.instance()
                                                                                     .getLigaID4SaisonID(seasonid)));
        m_jcbLiga.setEditable(true);
        getContentPane().add(m_jcbLiga);

        m_jbOk.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                     .getProperty("tt_Download_Start"));
        m_jbOk.addActionListener(this);
        getContentPane().add(m_jbOk);

        m_jbAbbrechen.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                            .getProperty("tt_Download_Abbrechen"));
        m_jbAbbrechen.addActionListener(this);
        getContentPane().add(m_jbAbbrechen);

        setSize(250, 150);

        final Dimension size = getToolkit().getScreenSize();

        if (size.width > this.getSize().width) {
            //Mittig positionieren
            this.setLocation((size.width / 2) - (this.getSize().width / 2),
                             (size.height / 2) - (this.getSize().height / 2));
        }

        setVisible(true);
    }

    //-------------------------------------------------------------------
    //Quick and Dirty!
    private int parseInt(Window parent, String text, boolean negativErlaubt) {
        String message = "";

        try {
            final int temp = Integer.parseInt(text);

            if (!negativErlaubt && (temp < 0)) {
                message = de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("negativVerboten");
                throw new NumberFormatException();
            }

            return temp;
        } catch (NumberFormatException nfe) {
            if (message.equals("")) {
                message = de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("keineZahl");
            }

            de.hattrickorganizer.tools.Helper.showMessage(parent, message,
                                                          de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                 .getResource()
                                                                                                 .getProperty("Fehler"),
                                                          javax.swing.JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
}
