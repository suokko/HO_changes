// %651501138:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import gui.UserParameter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.model.AufstellungCBItem;
import de.hattrickorganizer.gui.model.AufstellungsListRenderer;
import de.hattrickorganizer.gui.model.LineupListRenderer;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.Aufstellung;
import de.hattrickorganizer.model.HOVerwaltung;

import de.hattrickorganizer.tools.extension.FileExtensionManager;


/**
 * Aufstellungen können hier gespeichert werden oder mit anderen verglichen werden
 */
public class AufstellungsVergleichHistoryPanel extends ImagePanel
    implements de.hattrickorganizer.gui.Refreshable, ListSelectionListener, ActionListener,
               MouseListener
{
	
	private static final long serialVersionUID = 7313614630687892362L;
	
    //~ Static fields/initializers -----------------------------------------------------------------

    private static AufstellungCBItem m_clAngezeigteAufstellung;
    private static AufstellungCBItem m_clVergleichsAufstellung;
    private static AufstellungCBItem m_clHRFNextAufstellung;
    private static AufstellungCBItem m_clHRFLastAufstellung;
    private static boolean m_bVergleichAngestossen;

    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbAufstellungAnzeigen = new JButton(HOVerwaltung.instance().getLanguageString("AufstellungAnzeigen"));
    private JButton m_jbAufstellungLoeschen = new JButton(HOVerwaltung.instance().getLanguageString("AufstellungLoeschen"));
    private JButton m_jbAufstellungSpeichern = new JButton(HOVerwaltung.instance().getLanguageString("AufstellungSpeichern"));
    private JList m_jlAufstellungen = new JList();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungsVergleichHistoryPanel object.
     */
    public AufstellungsVergleichHistoryPanel() {
        initComponents();

        de.hattrickorganizer.gui.RefreshManager.instance().registerRefreshable(this);

		// Gab mal ne Nullpointer ...
		try {
			m_clHRFNextAufstellung = new AufstellungCBItem(HOVerwaltung.instance().getLanguageString("AktuelleAufstellung"), 
					HOVerwaltung.instance().getModel().getAufstellung().duplicate());
			m_clHRFLastAufstellung = new AufstellungCBItem(HOVerwaltung.instance().getLanguageString("LetzteAufstellung"), 
					HOVerwaltung.instance().getModel().getLastAufstellung().duplicate());

			// 2. Aufstellung
		} catch (Exception e) {
		}

        createAufstellungsListe();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Ist die Übergebene Aufstellung angezeigt?
     *
     * @param aufstellung TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean isAngezeigt(AufstellungCBItem aufstellung) {
        if (aufstellung != null) {
            return aufstellung.equals(m_clAngezeigteAufstellung);
        } else {
            return false;
        }
    }

    /**
     * Setzt die angezeige Aufstellung
     *
     * @param aufstellung TODO Missing Constructuor Parameter Documentation
     */
    public static void setAngezeigteAufstellung(AufstellungCBItem aufstellung) {
        m_clAngezeigteAufstellung = aufstellung.duplicate();
    }

    /**
     * Setzt die HRFAufstellung nach dem Import einen HRFs
     *
     * @param nextAufstellung TODO Missing Constructuor Parameter Documentation
     * @param lastAufstellung TODO Missing Constructuor Parameter Documentation
     */
    public static void setHRFAufstellung(Aufstellung nextAufstellung, Aufstellung lastAufstellung) {
		if (nextAufstellung != null) {
			m_clHRFNextAufstellung = new AufstellungCBItem(HOVerwaltung.instance().getLanguageString("AktuelleAufstellung"),
					nextAufstellung.duplicate());
		}

		if (lastAufstellung != null) {
			m_clHRFLastAufstellung = new AufstellungCBItem(HOVerwaltung.instance().getLanguageString("LetzteAufstellung"), 
					lastAufstellung.duplicate());
		}
    }

    /**
     * Returns Last Lineup
     *
     * @return TODO Missing Return Method Documentation
     */
    public static AufstellungCBItem getLastLineup() {
        return m_clHRFLastAufstellung;
    }

    /**
     * Wird vom AufstellungsDetailPanel aufgerufen, um mit der Vergleichsaufstellung anzuzeigen.
     * Wird danach wieder auf false gesetzt
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean isVergleichgefordert() {
        final boolean vergleichgefordert = m_bVergleichAngestossen;
        m_bVergleichAngestossen = false;
        return vergleichgefordert;
    }

    /**
     * Gibt die VergleichsAufstellung zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public static AufstellungCBItem getVergleichsAufstellung() {
        return m_clVergleichsAufstellung;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
		final int x = HOMainFrame.instance().getLocation().x + HOMainFrame.instance().getSize().width;
		final int y = HOMainFrame.instance().getLocation().y + HOMainFrame.instance().getSize().height;

        if (actionEvent.getSource().equals(m_jbAufstellungAnzeigen)) {
			m_clAngezeigteAufstellung = ((AufstellungCBItem) m_jlAufstellungen.getSelectedValue()).duplicate();
			HOVerwaltung.instance().getModel().setAufstellung(m_clAngezeigteAufstellung.getAufstellung().duplicate());

            //Alles Updaten
            HOMainFrame.instance().getAufstellungsPanel().update();
        } else if (actionEvent.getSource().equals(m_jbAufstellungSpeichern)) {
            String aufstellungsname = "";

			if (m_jlAufstellungen.getSelectedIndex() > 1) {
				aufstellungsname = ((AufstellungCBItem) m_jlAufstellungen.getSelectedValue()).getText();
			}

			AufstellungsNameDialog temp = new AufstellungsNameDialog(HOMainFrame.instance(), aufstellungsname,
					HOVerwaltung.instance().getModel().getAufstellung(), x, y);
			temp.setVisible(true);
            reInit();
            temp = null;
        } else if (actionEvent.getSource().equals(m_jbAufstellungLoeschen)) {
			String aufstellungsname = "";

			if (m_jlAufstellungen.getSelectedIndex() > 0) {
				aufstellungsname = ((AufstellungCBItem) m_jlAufstellungen.getSelectedValue()).getText();
			}
        	
            //Abfrage##
			DBZugriff.instance().deleteAufstellung(Aufstellung.NO_HRF_VERBINDUNG,
					((AufstellungCBItem) m_jlAufstellungen.getSelectedValue()).getText());
			HOMainFrame.instance().getInfoPanel().setLangInfoText(
					HOVerwaltung.instance().getLanguageString("Aufstellung") + " "
							+ ((de.hattrickorganizer.gui.model.AufstellungCBItem) m_jlAufstellungen.getSelectedValue()).getText() + " "
							+ HOVerwaltung.instance().getLanguageString("geloescht"));
                                                                                                    
			File f = new File("Lineups/" + HOVerwaltung.instance().getModel().getBasics().getManager() + "/" + aufstellungsname + ".dat");
			f.delete();
			FileExtensionManager.deleteLineup(aufstellungsname);

			((DefaultListModel) m_jlAufstellungen.getModel()).removeElement(m_jlAufstellungen.getSelectedValue());
		}

        repaint();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
	public final void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
		if (mouseEvent.getClickCount() >= 2) {
			m_clAngezeigteAufstellung = ((AufstellungCBItem) m_jlAufstellungen.getSelectedValue())
					.duplicate();
			HOVerwaltung.instance().getModel().setAufstellung(m_clAngezeigteAufstellung.getAufstellung().duplicate());

			// Alles Updaten
			de.hattrickorganizer.gui.HOMainFrame.instance().getAufstellungsPanel().update();

			repaint();
		}
	}

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseEvent TODO Missing Method Parameter Documentation
     */
    public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void reInit() {
        createAufstellungsListe();
    }

    /**
     * TODO Missing Method Documentation
     */
    public void refresh() {
        //nix
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param listSelectionEvent TODO Missing Method Parameter Documentation
     */
    public final void valueChanged(javax.swing.event.ListSelectionEvent listSelectionEvent) {
        //Aufstellung markiert
        if ((m_jlAufstellungen.getSelectedValue() != null)
            && m_jlAufstellungen.getSelectedValue() instanceof AufstellungCBItem) {
			final AufstellungCBItem aufstellungCB = (AufstellungCBItem) m_jlAufstellungen.getSelectedValue();

            //"Aktuelle Aufstellung" nicht zu löschen!
            if (aufstellungCB.getText().equals(HOVerwaltung.instance().getLanguageString("AktuelleAufstellung"))
                || aufstellungCB.getText().equals(HOVerwaltung.instance().getLanguageString("LetzteAufstellung"))) {
                m_jbAufstellungAnzeigen.setEnabled(true);
                m_jbAufstellungLoeschen.setEnabled(false);
                m_jbAufstellungSpeichern.setEnabled(true);
            }
            //Geladen
            else {
                m_jbAufstellungAnzeigen.setEnabled(true);
                m_jbAufstellungSpeichern.setEnabled(true);
                m_jbAufstellungLoeschen.setEnabled(true);
            }

            m_bVergleichAngestossen = true;
            m_clVergleichsAufstellung = aufstellungCB.duplicate();
        }
        //Keine Vergleich!
        else {
            m_jbAufstellungAnzeigen.setEnabled(false);
            m_jbAufstellungSpeichern.setEnabled(true);
            m_jbAufstellungLoeschen.setEnabled(false);

            m_clVergleichsAufstellung = null;
        }

		// gui.RefreshManager.instance ().doRefresh();
		HOMainFrame.instance().getAufstellungsPanel().getAufstellungsDetailPanel().refresh();
    }

	/**
	 * TODO Missing Method Documentation
	 */
	private void createAufstellungsListe() {
		final Vector<AufstellungCBItem> aufstellungsListe = loadAufstellungsListe();

		m_jlAufstellungen.removeListSelectionListener(this);

		final Object letzteMarkierung = m_jlAufstellungen.getSelectedValue();

		if (letzteMarkierung == null) {
			m_clAngezeigteAufstellung = m_clHRFNextAufstellung;
		}

		DefaultListModel listmodel;

		if (m_jlAufstellungen.getModel() instanceof DefaultListModel) {
			listmodel = (DefaultListModel) m_jlAufstellungen.getModel();
			listmodel.removeAllElements();
		} else {
			listmodel = new DefaultListModel();
		}

		// HRF Aufstellung
		if ((m_clHRFNextAufstellung != null) && (m_clHRFNextAufstellung.getAufstellung() != null)) {
			listmodel.addElement(m_clHRFNextAufstellung);
		}

		if ((m_clHRFLastAufstellung != null) && (m_clHRFLastAufstellung.getAufstellung() != null)) {
			listmodel.addElement(m_clHRFLastAufstellung);
		}

		// Temporäre geladene Aufstellungen
		for (int i = 0; i < aufstellungsListe.size(); i++) {
			listmodel.addElement(aufstellungsListe.get(i));
		}

		m_jlAufstellungen.setModel(listmodel);

		if (letzteMarkierung != null) {
			m_jlAufstellungen.setSelectedValue(letzteMarkierung, true);
		}

		m_jlAufstellungen.addListSelectionListener(this);
	}

    /**
     * Initialize the GUI components.
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        //add( new JLabel( model.HOVerwaltung.instance().getLanguageString( "VergleichsHRF" ) ), BorderLayout.NORTH );
        m_jlAufstellungen.setOpaque(false);

		if ("Classic".equals(UserParameter.instance().skin)) {
			m_jlAufstellungen.setCellRenderer(new AufstellungsListRenderer());
		} else {
			m_jlAufstellungen.setCellRenderer(new LineupListRenderer(m_jlAufstellungen));
		}
        m_jlAufstellungen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m_jlAufstellungen.addListSelectionListener(this);
        m_jlAufstellungen.addMouseListener(this);
        add(new JScrollPane(m_jlAufstellungen), BorderLayout.CENTER);

        final JPanel panel = new ImagePanel();
        panel.setLayout(new GridLayout(3, 1, 2, 2));

        m_jbAufstellungAnzeigen.setToolTipText(HOVerwaltung.instance().getLanguageString("AufstellungAnzeigen"));
        m_jbAufstellungAnzeigen.addActionListener(this);
        m_jbAufstellungAnzeigen.setEnabled(false);
        panel.add(m_jbAufstellungAnzeigen);
        m_jbAufstellungSpeichern.setToolTipText(HOVerwaltung.instance().getLanguageString("AufstellungSpeichern"));
        m_jbAufstellungSpeichern.addActionListener(this);
        m_jbAufstellungSpeichern.setEnabled(true);
        panel.add(m_jbAufstellungSpeichern);
        m_jbAufstellungLoeschen.setToolTipText(HOVerwaltung.instance().getLanguageString("AufstellungLoeschen"));
        m_jbAufstellungLoeschen.addActionListener(this);
        m_jbAufstellungLoeschen.setEnabled(false);
        panel.add(m_jbAufstellungLoeschen);

        add(panel, BorderLayout.SOUTH);
    }

    /**
     * Load the linup list.
     */
	private Vector<AufstellungCBItem> loadAufstellungsListe() {
		final Vector<String> aufstellungsnamen = DBZugriff.instance().getUserAufstellungsListe();
		final Vector<AufstellungCBItem> aufstellungsCBItems = new Vector<AufstellungCBItem>();

		for (int i = 0; i < aufstellungsnamen.size(); i++) {
			aufstellungsCBItems.add(new AufstellungCBItem(aufstellungsnamen.get(i).toString(), DBZugriff.instance().getAufstellung(
					Aufstellung.NO_HRF_VERBINDUNG, aufstellungsnamen.get(i).toString())));
		}

		return aufstellungsCBItems;
	}
}
