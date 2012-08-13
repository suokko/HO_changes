// %2054665773:de.hattrickorganizer.gui.playeroverview%
package ho.module.playerOverview;

import ho.core.datatype.CBItem;
import ho.core.db.DBManager;
import ho.core.gui.HOMainFrame;
import ho.core.gui.RefreshManager;
import ho.core.gui.Refreshable;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.model.AufstellungsListRenderer;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.model.player.Spieler;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;



/**
 * Hier kann eingestellt werden, mit welchem HRF die aktuelle Mannschaft verglichen werden soll
 */
public class SpielerTrainingsVergleichsPanel extends ImagePanel
    implements Refreshable, ListSelectionListener, ActionListener {

	private static final long serialVersionUID = 7090555271664890027L;

	//~ Static fields/initializers -----------------------------------------------------------------

    private static Vector<Spieler> vergleichsSpieler = new Vector<Spieler>();
    private static boolean vergleichsMarkierung;

    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbLoeschen = new JButton(HOVerwaltung.instance().getLanguageString("loeschen"));
    private JList m_jlHRFs = new JList();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerTrainingsVergleichsPanel object.
     */
    public SpielerTrainingsVergleichsPanel() {
        initComponents();
        RefreshManager.instance().registerRefreshable(this);
        loadHRFListe(true);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean isVergleichsMarkierung() {
        return vergleichsMarkierung;
    }

    /**
     * Gibt die Vergleichsspieler zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public static Vector<Spieler> getVergleichsSpieler() {
        return vergleichsSpieler;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        final Object[] hrfs = m_jlHRFs.getSelectedValues();
        String text = HOVerwaltung.instance().getLanguageString("loeschen");

        if (hrfs.length > 1) {
            text += (" (" + hrfs.length + " Files) : ");
        } else {
            text += " : ";
        }

        for (int i = 0; (i < hrfs.length) && (i < 11); i++) {
            text += ("\n" + hrfs[i].toString());

            if (i == 10) {
                text += "\n ... ";
            }
        }

        final int value = JOptionPane.showConfirmDialog(this, text,
				HOVerwaltung.instance().getLanguageString("loeschen"), JOptionPane.YES_NO_OPTION);

        if (value == JOptionPane.OK_OPTION) {
            for (int i = 0; i < hrfs.length; i++) {
                DBManager.instance().deleteHRF(((CBItem) hrfs[i]).getId());
            }

            loadHRFListe(false);
            vergleichsSpieler.removeAllElements();

            // HRF Deleted, recalculate Skillups
			DBManager.instance().reimportSkillup();

            //Nur manuelles Update der Tabelle, kein reInit, damit die Sortierung bleibt.
            HOMainFrame.instance().getSpielerUebersichtPanel().refreshHRFVergleich();
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void reInit() {
        loadHRFListe(false);
    }

    /**
     * TODO Missing Method Documentation
     */
    public void refresh() {
        //nix
    }

    /**
     * Handle vlaueChanged() events.
     */
	public final void valueChanged(javax.swing.event.ListSelectionEvent listSelectionEvent) {
		// Markierung vorhanden
		if (m_jlHRFs.getSelectedValue() != null) {
			vergleichsSpieler = DBManager.instance().getSpieler(((CBItem) m_jlHRFs.getSelectedValue()).getId());
			vergleichsMarkierung = true;

			if (m_jlHRFs.getSelectedIndex() > 0) {
				m_jbLoeschen.setEnabled(true);
			} else {
				m_jbLoeschen.setEnabled(false);
			}
		}
		// Keine Markierung -> Alles löschen
		else {
			vergleichsSpieler.removeAllElements();
			vergleichsMarkierung = false;
			m_jbLoeschen.setEnabled(false);
		}

		// Nur manuelles Update der Tabelle, kein reInit, damit die Sortierung
		// bleibt.
		HOMainFrame.instance().getSpielerUebersichtPanel().refreshHRFVergleich();

		// gui.RefreshManager.instance().doReInit();
	}

    /**
     * Init GUI components.
     */
	private void initComponents() {
		setLayout(new BorderLayout());

		add(new JLabel(HOVerwaltung.instance().getLanguageString("VergleichsHRF")), BorderLayout.NORTH);
		m_jlHRFs.setOpaque(false);
		// use the default renderer for all non-classic skins
		if ("Classic".equals(UserParameter.instance().skin)) {
			m_jlHRFs.setCellRenderer(new AufstellungsListRenderer());
		}

		m_jlHRFs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		m_jlHRFs.addListSelectionListener(this);
		add(new JScrollPane(m_jlHRFs), BorderLayout.CENTER);

		m_jbLoeschen.setEnabled(false);
		m_jbLoeschen.addActionListener(this);
		add(m_jbLoeschen, BorderLayout.SOUTH);
	}

    /**
     * TODO Missing Method Documentation
     *
     * @param init TODO Missing Method Parameter Documentation
     */
    private void loadHRFListe(boolean init) {
        final Vector<CBItem> hrfListe = DBManager.instance().getCBItemHRFListe(new Timestamp(0));

        m_jlHRFs.removeListSelectionListener(this);

        final Object letzteMarkierung = m_jlHRFs.getSelectedValue();

        DefaultListModel listmodel;

        if (m_jlHRFs.getModel() instanceof DefaultListModel) {
            listmodel = (DefaultListModel) m_jlHRFs.getModel();
            listmodel.removeAllElements();
        } else {
            listmodel = new DefaultListModel();
        }

        for (int i = 0; i < hrfListe.size(); i++) {
            listmodel.addElement(hrfListe.get(i));
        }

        m_jlHRFs.setModel(listmodel);

        //Bei der Initialisierung noch keinen Vergleich anzeigen!
        if (!init) {
            m_jlHRFs.addListSelectionListener(this);
        }

        if (letzteMarkierung != null) {
            m_jlHRFs.setSelectedValue(letzteMarkierung, true);
        } else if ((listmodel.size() > 1) && !init) {
            //Sonst das 2. HRF markieren, wenn es nicht direkt nach dem Start ist!
            m_jlHRFs.setSelectedIndex(1);
        }

        //Beim Initialisieren hier den Listener hinzufügen
        if (init) {
            m_jlHRFs.addListSelectionListener(this);
        }
    }
}
