// %771549764:de.hattrickorganizer.gui.lineup%
package ho.module.lineup;

import ho.core.db.DBManager;
import ho.core.file.extension.FileExtensionManager;
import ho.core.gui.HOMainFrame;
import ho.core.gui.model.AufstellungCBItem;
import ho.core.model.HOVerwaltung;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



/**
 * Erfragt einen Namen für die zu Speichernde Aufstellung und fügt sie in die Datenbank ein, wenn
 * gewünscht
 */
final class AufstellungsNameDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 7318780000118008882L;

    //~ Instance fields ----------------------------------------------------------------------------
	private Lineup m_clAufstellung;
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
                                     Lineup aufstellung, int x, int y) {
        super(owner, true);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        m_clAufstellung = aufstellung;

        m_jtfAufstellungsName = new JTextField("");

        if (checkName(aufstellungsName, false)) {
            m_jtfAufstellungsName.setText(aufstellungsName);
        }

        setTitle(ho.core.model.HOVerwaltung.instance().getLanguageString("AufstellungSpeichern"));
        m_jbOK = new JButton(ho.core.model.HOVerwaltung.instance().getLanguageString("ls.button.save"));
        m_jbAbbrechen = new JButton(ho.core.model.HOVerwaltung.instance().getLanguageString("ls.button.cancel"));

        setContentPane(new ho.core.gui.comp.panel.ImagePanel());
        getContentPane().setLayout(new GridLayout(2, 2, 4, 4));

        getContentPane().add(new JLabel(ho.core.model.HOVerwaltung.instance().getLanguageString("Name")));

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
                ho.core.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(ho.core.model.HOVerwaltung.instance().getLanguageString("Aufstellung")
                                                                                               + " "
                                                                                               + m_jtfAufstellungsName
                                                                                                 .getText()
                                                                                               + " "
                                                                                               + ho.core.model.HOVerwaltung.instance().getLanguageString("gespeichert"));

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
                                                                ho.core.model.HOVerwaltung.instance().getLanguageString("Aufstellung_NameSchonVorhanden")
                                                                , "", JOptionPane.YES_NO_OPTION);

                if (value == JOptionPane.YES_OPTION) {
                    m_clAufstellung.save(m_jtfAufstellungsName.getText());
                    ho.core.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(ho.core.model.HOVerwaltung.instance().getLanguageString("Aufstellung")
                                                                                                   + " "
                                                                                                   + m_jtfAufstellungsName
                                                                                                     .getText()
                                                                                                   + " "
                                                                                                   + ho.core.model.HOVerwaltung.instance().getLanguageString("gespeichert"));

                    //gui.RefreshManager.instance ().doReInit ();
                    AufstellungsVergleichHistoryPanel.setAngezeigteAufstellung(new AufstellungCBItem(m_jtfAufstellungsName
                                                                                                     .getText(),
                                                                                                     m_clAufstellung
                                                                                                     .duplicate()));

					HOMainFrame.instance().getAufstellungsPanel().getAufstellungsPositionsPanel().exportOldLineup(m_jtfAufstellungsName.getText());
					FileExtensionManager.extractLineup(m_jtfAufstellungsName.getText());

					HOMainFrame.instance().getAufstellungsPanel().update(); // Should prepare it for the new lineup
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
            aufstellungsNamen = DBManager.instance().getAufstellungsListe(Lineup.NO_HRF_VERBINDUNG);
        }

        //nicht Aktuelle Aufstellung
        aufstellungsNamen.add(HOVerwaltung.instance().getLanguageString("AktuelleAufstellung"));
        aufstellungsNamen.add(HOVerwaltung.instance().getLanguageString("LetzteAufstellung"));

        //nicht HO!
        aufstellungsNamen.add(Lineup.DEFAULT_NAME);

        return (!(aufstellungsNamen.contains(name)));
    }
}
