// %771549764:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.model.AufstellungCBItem;
import de.hattrickorganizer.model.Aufstellung;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.extension.FileExtensionManager;


/**
 * Erfragt einen Namen für die zu Speichernde Aufstellung und fügt sie in die Datenbank ein, wenn
 * gewünscht
 */
final class AufstellungsNameDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 7318780000118008882L;
	
    //~ Instance fields ----------------------------------------------------------------------------
	private Aufstellung m_clAufstellung;
    private JButton m_jbAbbrechen;
    private JButton m_jbOK;
    private JTextField m_jtfAufstellungsName;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungsNameDialog object.
     *
     * @param owner TODO Missing Constructuor Parameter Documentation
     * @param aufstellungsName TODO Missing Constructuor Parameter Documentation
     * @param aufstellung TODO Missing Constructuor Parameter Documentation
     * @param x TODO Missing Constructuor Parameter Documentation
     * @param y TODO Missing Constructuor Parameter Documentation
     */
    protected AufstellungsNameDialog(JFrame owner, String aufstellungsName,
                                     Aufstellung aufstellung, int x, int y) {
        super(owner, true);

        m_clAufstellung = aufstellung;

        m_jtfAufstellungsName = new JTextField("");

        if (checkName(aufstellungsName, false)) {
            m_jtfAufstellungsName.setText(aufstellungsName);
        }

        setTitle(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("AufstellungSpeichern"));
        m_jbOK = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Speichern"));
        m_jbAbbrechen = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Abbrechen"));

        setContentPane(new de.hattrickorganizer.gui.templates.ImagePanel());
        getContentPane().setLayout(new GridLayout(2, 2, 4, 4));

        getContentPane().add(new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Name")));

        getContentPane().add(m_jtfAufstellungsName);

        m_jbOK.addActionListener(this);
        getContentPane().add(m_jbOK);

        m_jbAbbrechen.addActionListener(this);
        getContentPane().add(m_jbAbbrechen);

        setSize(300, 80);
        setLocation(x - 350, y - 150);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbOK)) {
            if (!checkName(m_jtfAufstellungsName.getText(), false)) {
                //Name nicht erlaubt / Keine Meldung
                return;
            }

            if (checkName(m_jtfAufstellungsName.getText(), true)) {
                m_clAufstellung.save(m_jtfAufstellungsName.getText());
                de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Aufstellung")
                                                                                               + " "
                                                                                               + m_jtfAufstellungsName
                                                                                                 .getText()
                                                                                               + " "
                                                                                               + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("gespeichert"));

                //gui.RefreshManager.instance ().doReInit ();
                AufstellungsVergleichHistoryPanel.setAngezeigteAufstellung(new AufstellungCBItem(m_jtfAufstellungsName
                                                                                                 .getText(),
                                                                                                 m_clAufstellung
                                                                                                 .duplicate()));
				HOMainFrame.instance().getAufstellungsPanel().getAufstellungsPositionsPanel().exportOldLineup(m_jtfAufstellungsName.getText());
				FileExtensionManager.extractLineup(m_jtfAufstellungsName.getText());
                setVisible(false);
                
            } else {
                final int value = JOptionPane.showConfirmDialog(this,
                                                                de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Aufstellung_NameSchonVorhanden")
                                                                + " "
                                                                + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Ueberschreiben")
                                                                + "?", "", JOptionPane.YES_NO_OPTION);

                if (value == JOptionPane.YES_OPTION) {
                    m_clAufstellung.save(m_jtfAufstellungsName.getText());
                    de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Aufstellung")
                                                                                                   + " "
                                                                                                   + m_jtfAufstellungsName
                                                                                                     .getText()
                                                                                                   + " "
                                                                                                   + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("gespeichert"));

                    //gui.RefreshManager.instance ().doReInit ();
                    AufstellungsVergleichHistoryPanel.setAngezeigteAufstellung(new AufstellungCBItem(m_jtfAufstellungsName
                                                                                                     .getText(),
                                                                                                     m_clAufstellung
                                                                                                     .duplicate()));
					
					HOMainFrame.instance().getAufstellungsPanel().getAufstellungsPositionsPanel().exportOldLineup(m_jtfAufstellungsName.getText());
					FileExtensionManager.extractLineup(m_jtfAufstellungsName.getText());
                    setVisible(false);
                }
            }
        } else if (actionEvent.getSource().equals(m_jbAbbrechen)) {
            setVisible(false);
        }
    }

    //Name noch nicht in DB oder Aktuelle Aufstellung
    private boolean checkName(String name, boolean dbcheck) {
        Vector<String> aufstellungsNamen = new Vector<String>();

        //nicht schon vorhanden
        if (dbcheck) {
            aufstellungsNamen = DBZugriff.instance().getAufstellungsListe(Aufstellung.NO_HRF_VERBINDUNG);
        }

        //nicht Aktuelle Aufstellung
        aufstellungsNamen.add(HOVerwaltung.instance().getLanguageString("AktuelleAufstellung"));
        aufstellungsNamen.add(HOVerwaltung.instance().getLanguageString("LetzteAufstellung"));

        //nicht HO!
        aufstellungsNamen.add(Aufstellung.DEFAULT_NAME);

        return (!(aufstellungsNamen.contains(name)));
    }
}
